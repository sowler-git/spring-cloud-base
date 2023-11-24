package com.itmy.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 登录请求VO
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqVO {

	@NotNull
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

	/**
	 * 认证来源
	 */
	private Integer authType;

	/**
	 * 用户登陆设备
	 */
	private Integer deviceType;

}
