package com.itmy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.itmy.entity.base.BaseClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: niusaibo
 * @date: 2023-10-09 10:54
 */
@Data
@TableName("tb_user")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends BaseClass implements Serializable {

    private Long tenantId;


    private String userName;

    private String account;

    private String email;

    private String password;

    /**
     * 用户来源
     */
    private Integer userSource;

    private String phoneNumber;

    private Integer status;

    private Long lastLoginTime;


    public static final String TENANT_ID = "tenant_id";


    public static final String ACCOUNT = "account";

    public static final String PASSWORD = "password";

    public static final String EMAIL = "email";

}
