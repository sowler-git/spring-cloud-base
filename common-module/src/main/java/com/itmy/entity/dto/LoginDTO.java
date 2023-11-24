package com.itmy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 本地登陆请求参数
 * @Author: niusaibo
 * @date: 2023-10-13 14:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    /**
     * 租户Id
     */
    private Long tenantId;

    /**
     * 账号
     */
    @NotBlank
    private String account;

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 图片验证码
     */
    private String code;

}
