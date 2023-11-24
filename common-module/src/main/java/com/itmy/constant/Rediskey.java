package com.itmy.constant;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 13:59
 */
public class Rediskey {

    /**
     * key = key + account
     *
     * value = 密码错误次数
     */
    public static final String CLOUD_LOGIN_PWD_ERROR = "cloud.login.pwd.error.";

    /**
     * key = key + account
     *
     * value = 账号登陆的图片验证码
     */
    public static final String ACCOUNT_LOGIN_CODE = "cloud.login.code.";

    /**
     * key = key + account
     *
     * value = 账号登陆后生成的token
     */
    public static final String ACCOUNT_LOGIN_TOKEN = "cloud.login.token.";

}
