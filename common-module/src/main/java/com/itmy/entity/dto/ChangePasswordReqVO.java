package com.itmy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 修改密码
 * </p>
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordReqVO {

	@NotBlank
	private String email;

	/**
	 * 旧密码
	 */
	@NotBlank
	private String oldPassword;

	/**
	 * 新密码
	 */
	@NotBlank
	private String password;

	/**
	 * 确认新密码
	 */
	@NotBlank
	private String confirmPassword;

}
