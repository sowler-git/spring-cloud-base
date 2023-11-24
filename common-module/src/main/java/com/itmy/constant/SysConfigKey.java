package com.itmy.constant;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 14:01
 */
public class SysConfigKey {


    /**
     * 前端页面的地址, 用于拼接链接资源
     */
    public static final String DM_FRONT_ADDRESS = "DM_FRONT_ADDRESS";

    /**
     * MINIO里面保存的对象的访问路径的前缀, 可以不填, 不填默认是"", 也就是以相对路径开头
     */
    public static final String DM_MINIO_URL_PREFIX = "DM_MINIO_URL_PREFIX";

    /**
     * MINIO本身调用的Endpoint地址, 必填
     */
    public static final String DM_MINIO_ENDPOINT = "DM_MINIO_ENDPOINT";

    /**
     *  验证码只包含数字
     */
    public static final String IS_CAPTCHA_ONLY_NUM = "IS_CAPTCHA_ONLY_NUM";

    /**
     * 用户密码规则
     */
    public static final String USER_PASSWORD_RULE = "USER_PASSWORD_RULE";

}
