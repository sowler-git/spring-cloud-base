package com.itmy.user.controller;


import com.itmy.constant.Consts;
import com.itmy.controller.BaseController;
import com.itmy.entity.CurrentUser;
import com.itmy.entity.User;
import com.itmy.constant.Rediskey;
import com.itmy.entity.base.Request;
import com.itmy.entity.base.Response;
import com.itmy.entity.dto.ChangePasswordReqVO;
import com.itmy.entity.dto.LoginDTO;
import com.itmy.entity.vo.LoginReqVO;
import com.itmy.entity.vo.LoginResVO;
import com.itmy.enums.ErrorEnum;
import com.itmy.user.service.IAccountService;
import com.itmy.user.service.IUserService;
import com.itmy.user.service.catpcha.ICaptchaService;
import com.itmy.utils.DateTimeUtils;
import com.itmy.utils.EncryptUtils;
import com.itmy.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.IntegerCodec;
import org.redisson.client.codec.LongCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: niusaibo
 * @date: 2023-10-12 11:08
 */
@RestController
@RequestMapping("/user/account")
@Api(tags = "认证管理")
@Slf4j
public class AccountController extends BaseController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IAccountService accountService;


    @Autowired
    private ICaptchaService captchaService;

    @Autowired
    private IUserService userService;

    @ApiOperation("登陆")
    @PostMapping("/login")
    public Response login(@RequestBody @Valid Request<LoginReqVO> request) {
        checkRequest(request);
        LoginReqVO reqVO = request.getModel();
        // 密码解密
        //reqVO.setPassword(EncryptUtils.decodeAES(reqVO.getPassword()));
        if (StringUtils.isBlank(reqVO.getPassword())) {
            return Response.fail(ErrorEnum.INVALID_PARAM);
        }
        User user = accountService.login(LoginDTO.builder()
                .account(reqVO.getAccount())
                .password(reqVO.getPassword())
                .tenantId(reqVO.getTenantId())
                .code(reqVO.getCode())
                .build());
//        CurrentUser currentUser = CurrentUser.valueOf(user.getId(), user.getAccount(), user.getUserName(),
//                user.getTenantId(), (Objects.isNull(request.getModel().getDeviceType())
//                        ? LoginDeviceTypeEnum.PC.getCode() : request.getModel().getDeviceType()),
//                user.getEmail());
        String token = JwtUtils.geneJsonWebToken(user);
        // 不同平台有相应的唯一token 0pc端 1移动端
        String key = Rediskey.ACCOUNT_LOGIN_TOKEN + reqVO.getDeviceType()
                + Consts.POINT + user.getId();
        RBucket<Long> bucket = redissonClient.getBucket(key, LongCodec.INSTANCE);
        bucket.set(System.currentTimeMillis(), 1000L * 60 * 30, TimeUnit.MILLISECONDS);
        LoginResVO resVO = LoginResVO.builder().token(token).isFirstLogin(Objects.isNull(user.getLastLoginTime()))
                .isMiddleLogin(false).build();
        // 更新登陆时间
        user.setLastLoginTime(DateTimeUtils.utcNow());
        userService.updateById(user);

        return Response.success(resVO);
    }



    @ApiOperation("密码错误后获取图片验证码")
    @GetMapping("/login/captcha")
    @Deprecated
    public Response getLoginCode(@RequestParam("account") String account) {
        String img = captchaService.getImg(Rediskey.ACCOUNT_LOGIN_CODE + account.toLowerCase());
//        BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//        try {
//            byte[] bytes1 = decoder.decodeBuffer(img);
//            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
//            BufferedImage bi1 = ImageIO.read(bais);
//            File w2 = new File("D:\\Desktop\\123.png");//可以是jpg,png,gif格式
//            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return Response.success(img);
    }

    @ApiOperation("该账号是否需要图片验证码")
    @GetMapping("/login/user/captcha")
    public Response getUserCode(@RequestParam("account") String account) {
        // 密码错误次数
        RBucket<Integer> bucketNum = redissonClient.getBucket(Rediskey.CLOUD_LOGIN_PWD_ERROR + account.toLowerCase(), IntegerCodec.INSTANCE);
        if (Objects.isNull(bucketNum.get())) {
            return Response.success();
        }
        return Response.success(captchaService.getImg(Rediskey.ACCOUNT_LOGIN_CODE + account.toLowerCase()));
    }

    @ApiOperation("登出")
    @DeleteMapping("/logout")
    public Response logout(HttpServletRequest httpServletRequest) {
        // 将当前登陆用户的token从redis中清除
       // String loginUser = httpServletRequest.getHeader("LOGIN_USER");
       // CurrentUser currentUser = JSONObject.parseObject(loginUser, CurrentUser.class);
//        CurrentUser currentUser = UserHolder.getCurrentUser();
//        System.out.println("Local："+currentUser);
//        System.out.println(login_user);
        CurrentUser currentUser = getCurrentUser(httpServletRequest);
        HttpSession session = httpServletRequest.getSession();
        session.invalidate();
        log.info("-----------登出操作：清空session,{}",currentUser);
        if (Objects.isNull(currentUser)){
            return Response.fail();
        }
        RBucket<Long> bucket = redissonClient.getBucket(
                Rediskey.ACCOUNT_LOGIN_TOKEN + currentUser.getLoginDeviceType() + Consts.POINT
                        + currentUser.getId(), LongCodec.INSTANCE);
        bucket.delete();
        return Response.success();
    }

    @ApiOperation("修改密码")
    @PostMapping("/password/change")
    public Response changePassword(@RequestBody @Valid Request<ChangePasswordReqVO> request,
                                   HttpServletRequest httpServletRequest) {
        checkRequest(request);
        ChangePasswordReqVO changePasswordReqVO = request.getModel();
        // 解密密码
        String oldPwd = EncryptUtils.decodeAES(changePasswordReqVO.getOldPassword());
        String password = EncryptUtils.decodeAES(changePasswordReqVO.getPassword());
        String confirmPwd = EncryptUtils.decodeAES(changePasswordReqVO.getConfirmPassword());
        if (StringUtils.isBlank(oldPwd) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPwd)) {
            return Response.fail(ErrorEnum.INVALID_PARAM);
        }

        return Response
                .success(accountService.changePassword(oldPwd, password, confirmPwd, changePasswordReqVO.getEmail(), getCurrentUser(httpServletRequest)));
    }



    @ApiOperation("恢复密码错误被锁定的账号")
    @DeleteMapping("/unlock/{userId}")
    public Response unlock(@PathVariable Long userId) {
        accountService.unlock(userId);
        return Response.success();
    }



}
