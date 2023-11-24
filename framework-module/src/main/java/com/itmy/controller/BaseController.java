package com.itmy.controller;

import com.itmy.constant.Consts;
import com.itmy.constant.LanguageConsts;
import com.itmy.constant.Rediskey;
import com.itmy.entity.CurrentUser;
import com.itmy.entity.base.Request;
import com.itmy.enums.ErrorEnum;
import com.itmy.exception.ParamException;
import com.itmy.utils.JwtUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.LongCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 13:57
 */
public abstract class BaseController {


    @Autowired
    private RedissonClient redissonClient;

    /**
     * 获取当前用户
     * @return
     */
    protected CurrentUser getCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            throw new ParamException(ErrorEnum.UUC_USER_NOT_LOGIN);
        }
        CurrentUser user = JwtUtils.getCurrentUser(token);
        if (Objects.isNull(user.getId())
                || Objects.isNull(user.getAccount())) {
             // || Objects.isNull(user.getTenantId())
            throw new ParamException(ErrorEnum.INVALID_PARAM);
        }
        // token 是否存在
        RBucket<Long> bucket = redissonClient.getBucket(Rediskey.ACCOUNT_LOGIN_TOKEN
                + user.getLoginDeviceType() + Consts.POINT + user.getId(), LongCodec.INSTANCE);
        if (Objects.isNull(bucket.get())) {
            throw new ParamException(ErrorEnum.UUC_TOKEN_INVALID);
        }
        return user;
    }

    /**
     * 是否为中文环境
     * @param request
     * @return
     */
    protected Boolean isZh(HttpServletRequest request) {
        // 语言环境
        String languageTypeStr = request.getHeader(LanguageConsts.LANGUAGE_TYPE);
        return LanguageConsts.LANGUAGE_TYPE_ZH_STR.equals(languageTypeStr);
    }


    /**
     * 校验model 如果model没问题，则返回model
     *
     * @author yehao
     * @date 2019-06-27 17:34
     */
    protected <T> T checkRequest(Request<T> request) {
        if (request.getModel() == null) {
            throw new ParamException(ErrorEnum.INVALID_PARAM);
        }
        return request.getModel();
    }

}
