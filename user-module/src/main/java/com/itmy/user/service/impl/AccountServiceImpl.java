package com.itmy.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.util.concurrent.RateLimiter;
import com.itmy.constant.Consts;
import com.itmy.constant.Rediskey;
import com.itmy.entity.CurrentUser;
import com.itmy.entity.User;
import com.itmy.entity.dto.LoginDTO;
import com.itmy.entity.dto.PasswordCheckResVO;
import com.itmy.entity.dto.UserPasswordRule;
import com.itmy.enums.ErrorEnum;
import com.itmy.enums.LoginDeviceTypeEnum;
import com.itmy.enums.UserSourceEnum;
import com.itmy.exception.ModelException;
import com.itmy.exception.ParamException;
import com.itmy.user.service.IAccountService;
import com.itmy.user.service.IUserService;
import com.itmy.utils.EncryptUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.IntegerCodec;
import org.redisson.client.codec.LongCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.itmy.enums.ErrorEnum.USER_NOT_FOUND;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 14:24
 */
@Service
public class AccountServiceImpl implements IAccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IUserService userService;

    // 登陆限流
    private RateLimiter loginLimiter = RateLimiter.create(10);

    @Override
    public User login(LoginDTO dto) {
        // 密码错误次数
        RBucket<Integer> bucketNum = redissonClient.getBucket(Rediskey.CLOUD_LOGIN_PWD_ERROR + dto.getAccount().toLowerCase(), IntegerCodec.INSTANCE);
        Integer errNum = bucketNum.get();
        if (Objects.isNull(bucketNum.get())) {
            errNum = 0;
        }
        // 密码错误超过限制
        if (errNum >= 5) {
            throw new ParamException(ErrorEnum.USERACCOUNT_PWDERROR_LOCK);
        }
        // 验证用户账号
        User user = userService.getByAccount(dto.getAccount(), dto.getTenantId());
        if (Objects.isNull(user)) {
            bucketNum.set(++errNum, Consts.ACCOUNT_PWDERRORNUM, TimeUnit.MINUTES);
            if (errNum >= 5) {
                throw new ParamException(ErrorEnum.USERACCOUNT_PWDERROR_LOCK);
            } else {
                throw new ModelException(ErrorEnum.PASSWORD_DOES_NOT_MATCH, errNum);
            }
        }
        // 账号是否被禁用
        if (user.getStatus() == 0) {
            throw new ParamException(ErrorEnum.UUC_USER_IS_DISABLED);
        }
        // 第三方账号
        if (!UserSourceEnum.localUser().contains(user.getUserSource())) {
            if (UserSourceEnum.LDAP.getCode().equals(user.getUserSource())) {
                throw new ParamException(ErrorEnum.USER_LOGIN_BY_LDAP);
            }
            if (UserSourceEnum.O365.getCode().equals(user.getUserSource())) {
                throw new ParamException(ErrorEnum.USER_LOGIN_BY_O365);
            }
            if (UserSourceEnum.OA.getCode().equals(user.getUserSource())) {
                throw new ParamException(ErrorEnum.USER_LOGIN_BY_OA);
            }
            if (UserSourceEnum.DINGDING.getCode().equals(user.getUserSource())) {
                throw new ParamException(ErrorEnum.USER_LOGIN_BY_DINGDING);
            }
            if (UserSourceEnum.WECHAT.getCode().equals(user.getUserSource())) {
                throw new ParamException(ErrorEnum.USER_LOGIN_BY_WECHAT);
            }
            if (UserSourceEnum.QQ.getCode().equals(user.getUserSource())) {
                throw new ParamException(ErrorEnum.USER_LOGIN_BY_QQ);
            }
        }

        // 登陆限流
        int flag = 0;
        try {
            if (!loginLimiter.tryAcquire()) {
                flag = 1;
            }
        }
        catch (Exception ex) {
            logger.error("Login|Limiter|{}", dto.getAccount(), ex);
        }
        if (flag == 1) {
            throw new ParamException(ErrorEnum.SYSTEM_IS_BUSY);
        }

        // 图片验证码
        if (errNum > 0) {
            if (StringUtils.isNotBlank(dto.getCode())) {
                RBucket<String> bucketCode = redissonClient
                        .getBucket(Rediskey.ACCOUNT_LOGIN_CODE + dto.getAccount().toLowerCase());
                String code = bucketCode.get();
                // 验证码已过期
                if (StringUtils.isBlank(code)) {
                    throw new ParamException(ErrorEnum.CAPTCHA_NEED_REFRESH);
                }
                // 验证码错误 不区分大小写
                if (!dto.getCode().toLowerCase().equals(code)) {
                    throw new ParamException(ErrorEnum.CAPTCHA_CHECK_ERROR);
                }
                // 图片验证码设置过期 只允许用一次
                bucketCode.delete();
            }
            else {
                throw new ParamException(ErrorEnum.CAPTCHA_NEED_REFRESH);
            }
        }


        // 验证密码是否正确
        if (StringUtils.isBlank(user.getPassword())
                || !user.getPassword().equals(EncryptUtils.encodeAES(dto.getPassword()))) {
            bucketNum.set(++errNum, Consts.ACCOUNT_PWDERRORNUM, TimeUnit.MINUTES);
            if (errNum >= 5) {
                // 设置过期时间 锁定30分钟
                bucketNum.set(errNum, 30, TimeUnit.MINUTES);
                throw new ParamException(ErrorEnum.USERACCOUNT_PWDERROR_LOCK);
            } else {
                throw new ModelException(ErrorEnum.PASSWORD_DOES_NOT_MATCH, errNum);
            }
        }

        // 登陆成功清除之前错误次数
        if (Objects.nonNull(bucketNum.get())) {
            bucketNum.delete();
        }

        return user;
    }

    @Override
    public Boolean changePassword(String oldPassword, String password, String confirmPassword, String email, CurrentUser currentUser) {
        if (!confirmPassword.equals(password)) {
            throw new ParamException(ErrorEnum.USER_PASSWD_DIFFERENT);
        }
        // 自定义密码验证
        checkPassword(password);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(User.EMAIL, email);
        wrapper.eq(User.PASSWORD, EncryptUtils.encodeMD5(oldPassword));
        List<User> userList = userService.list(wrapper);
        if (CollectionUtils.isEmpty(userList)) {
            throw new ParamException(USER_NOT_FOUND);
        }
        User user = userList.get(0);
        // 第三方登陆账号不允许更改密码
        if (!UserSourceEnum.localUser().contains(user.getUserSource())) {
            throw new ParamException(ErrorEnum.INVALID_PARAM);
        }
        user.setPassword(EncryptUtils.encodeMD5(password));
        boolean updateResult = userService.updateById(user);

        return updateResult;
    }

    @Override
    public void deleteToken(Long userId) {
        // PC端 token失效
        RBucket<Long> bucket = redissonClient.getBucket(
                Rediskey.ACCOUNT_LOGIN_TOKEN + LoginDeviceTypeEnum.PC.getCode() + Consts.POINT + userId, LongCodec.INSTANCE);
        bucket.delete();

        // 移动端 token失效
        RBucket<Long> bucket1 = redissonClient.getBucket(Rediskey.ACCOUNT_LOGIN_TOKEN
                + LoginDeviceTypeEnum.PHONE.getCode() + Consts.POINT + userId, LongCodec.INSTANCE);
        bucket1.delete();
    }

    @Override
    public void checkPassword(String password) {
        PasswordCheckResVO passwordCheckResVO = getPasswordRuleCheckResult(password);
        if(!passwordCheckResVO.getLengthResult()){
            UserPasswordRule.PasswordLen requiredPasswordLen = passwordCheckResVO.getRequiredPasswordLen();
            if(requiredPasswordLen != null ) {
                throw new ParamException(ErrorEnum.USER_PWD_RULE_ERROR.getCode(), requiredPasswordLen.errorMsg());
            } else {
                throw new ParamException(ErrorEnum.USER_PWD_RULE_ERROR);
            }
        }
        if(!passwordCheckResVO.getTypeResult()) {
            UserPasswordRule.PassWordComplexity requiredPassWordComplexity = passwordCheckResVO.getRequiredPassWordComplexity();
            if(requiredPassWordComplexity != null ) {
                throw new ParamException(ErrorEnum.USER_PASSWD_TYPE_ERROR.getCode(), requiredPassWordComplexity.errorMsg());
            } else {
                throw new ParamException(ErrorEnum.USER_PASSWD_TYPE_ERROR);
            }
        }
    }

    private PasswordCheckResVO getPasswordRuleCheckResult(String password){
        //UserPasswordRule rule = sysConfigService.getUserPasswordRule();
        String pwdRule = "{\n" +
                "         \"accountLockRule\":{\"lockMinutes\":15,\"lockable\":false,\"maxPasswordErrTime\":3},\n" +
                "         \"passWordComplexity\":{\n" +
                "         \"containsDigit\":true,\n" +
                "         \"containsLowerCase\":false,\n" +
                "         \"containsSpecial\":false,\n" +
                "         \"containsUpperCase\":false\n" +
                "         },\n" +
                "         \"passwordLen\":{\"max\":10,\"min\":6},\n" +
                "         \"rememberMeDays\":30\n" +
                "         }";
        UserPasswordRule rule = JSONObject.parseObject(pwdRule, UserPasswordRule.class);
        if (rule.getAccountLockRule() == null) {
            if(password == null) {
                return PasswordCheckResVO.builder().lengthResult(false).typeResult(false).build();
            }
            boolean lengthFault = password.length() < Consts.PWD_MIN_LENGTH
                    || password.length() > Consts.PWD_MAX_LENGTH;
            boolean typeFault = !((password.matches("^.*[a-zA-Z]+.*$") && password.matches("^.*[0-9]+.*$"))
                    || (password.matches("^.*[a-zA-Z]+.*$") && password.matches("^.*[/^/$/.//,;:'!@#%&/*/|/?/+/(/)/[/]/{/}]+.*$"))
                    || (password.matches("^.*[0-9]+.*$") && password.matches("^.*[/^/$/.//,;:'!@#%&/*/|/?/+/(/)/[/]/{/}]+.*$")));

            return PasswordCheckResVO.builder().lengthResult(!lengthFault).typeResult(!typeFault).build();
        }
        return rule.check(password);
    }


    @Override
    public void unlock(Long userId) {
        User user = userService.getById(userId);
        if (Objects.isNull(user)) {
            return;
        }
        RBucket<Integer> bucketNum = redissonClient
                .getBucket(Rediskey.CLOUD_LOGIN_PWD_ERROR + user.getAccount().toLowerCase(), IntegerCodec.INSTANCE);
        if (Objects.nonNull(bucketNum.get())) {
            // 解除锁定
            bucketNum.delete();
        }
    }


}
