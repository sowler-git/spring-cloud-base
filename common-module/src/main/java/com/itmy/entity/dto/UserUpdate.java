package com.itmy.entity.dto;

import lombok.Data;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 13:22
 */
@Data
public class UserUpdate {

    private Long id;

    private String userName;

    private String account;

    private String email;

    private String password;

    private String phoneNumber;

}
