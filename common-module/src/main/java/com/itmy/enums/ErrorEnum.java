package com.itmy.enums;

/**
 * @Author: niusaibo
 * @date: 2023-10-10 15:41
 */
public enum ErrorEnum {
    /**
     * 正常请求返回码
     */
    OK(200, "success"),

    /**
     * 操作失败
     */
    FAIL(-1, "fail"),

    /**
     * 系统异常返回码
     */
    ERROR(500, "error"),

    /**
     * 会话过期
     */
    SESSION_EXPIRED(1, "会话过期"),
    OPERATING_TOO_FAST(200003, "操作太频繁"),
    RESOURCE_FORBIDDEN(40300, "您没有查看该资源的权限"),
    SYSTEM_IS_BUSY(40500, "系统繁忙请稍后再试"),
    /**
     * 参数错误 返回码
     */
    INVALID_PARAM(100001, "参数错误"),
    INVALID_PARAM_LANGUAGE_TYPE(400002, "参数缺少语言类型"),
    INVALID_PARAM_EXCEL_MODEL(400003, "导入模板错误"),

    //UUC模块 10
    UUC_USERNAME_OR_PASSWD_INVALID(10400, "用户名或密码不正确"),
    UUC_USERNAME_EXIST(10401, "用户名已存在"),
    UUC_TOKEN_INVALID(10402, "TOKEN无效"),
    UUC_USER_NOT_LOGIN(10404, "你还没有登录"),
    UUC_USER_IS_DISABLED(10505, "用户已被禁用"),
    UUC_USER_NON_EXISTENT(10506, "用户不存在"),
    UUC_TENANT_NON_EXISTENT(10507, "租户不存在"),
    UUC_PRODUCT_NON_EXISTENT(10508, "产品不存在"),
    UUC_TENANT_PRODUCT_RELATION_NON_EXISTENT(10509, "租户产品关系不存在"),
    UUC_TENANT_ZHNAME_EXISTENT(10510, "企业名称已存在"),
    UUC_TENANT_ENNAME_EXISTENT(10511, "企业英文名称已存在"),
    UUC_EMAIL_EXISTENT(10512, "邮箱已存在,不能使用该邮箱"),
    USER_EMAIL_NOT_EXISTENT(10513, "邮箱不存在"),
    LDAP_LOGIN_ERROR(10520, "LDAP is error"),
    USERACCOUNT_PWDERROR_LOCK(10521, "密码错误次数过多被锁定"),
    LOGIN_TOO_FAST(10522, "登陆太频繁"),
    CAPTCHA_NEED_REFRESH(10523, "图片验证码需要刷新"),
    CAPTCHA_CREATE_ERROR(10524, "图片验证码获取失败"),
    CAPTCHA_CHECK_ERROR(10525, "图片验证码输入错误"),
    CODE_CHECK_ERROR(10526, "验证码输入错误"),
    CODE_IS_INVALID(10527, "验证码失效"),
    PASSWORD_DOES_NOT_MATCH(10528, "密码错误"),
    LOGIN_ERROR(10600, "登录失败"),

    //用户管理
    USER_NOT_FOUND(40102, "用户不存在"),
    USER_IS_DISABLED(40103, "用户禁用中"),
    USER_THIRD_PARTY_ERROR(40104, "用户第三方登录失败"),
    EMAIL_ADDRESS_ERROR(40105, "邮箱格式错误"),
    USER_NOT_ALLOW_DELETE(40106, "内置账号不能被删除"),
    USER_PWD_RULE_ERROR(40107, "密码长度不符合"),
    USER_PASSWD_INVALID(40108, "密码错误"),
    USER_PASSWD_DIFFERENT(40109, "两次输入的密码不一致"),
    USER_PASSWD_TYPE_ERROR(40205,"密码至少包含数字、字母、字符其中的两种"),

    USER_LOGIN_BY_LDAP(10530, "该账号为第三方账号，请用Ldap登录"),
    USER_LOGIN_BY_O365(10531, "该账号为第三方账号，请用O365登录"),
    USER_LOGIN_BY_OA(10532, "该账号为第三方账号，请用OA登录"),
    USER_LOGIN_BY_DINGDING(10533, "该账号为第三方账号，请用钉钉登录"),
    USER_LOGIN_BY_WECHAT(10534, "该账号为第三方账号，请用微信登录"),
    USER_LOGIN_BY_QQ(10535, "该账号为第三方账号，请用QQ登录"),


    // 文件
    FILE_FORMAT_ERROR(70706, "文件格式错误"),
    FILE_PARSING_EXCEPTION(70707, "文件数据解析异常"),
    FILE_CREATE_FAILER(70708, "文件创建失败"),
    FILE_CREATE_FAILER_TEMPLATE(70709, "模板错误"),
    FILE_SIZE_200MB(70710, "文件大小超过200MB"),

    ;
    private Integer code;

    private String msg;

    ErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ErrorEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
