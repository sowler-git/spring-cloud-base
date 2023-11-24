package com.itmy.entity;

import com.itmy.enums.LoginDeviceTypeEnum;
import lombok.Data;

import java.util.Objects;


/**
 * @author nsbo
 * @Date 2021/12/3 11:37
 */
@Data
public class CurrentUser {

	/**
	 * 用户id
	 */
    private Long id;

	private Long tenantId;

	/**
	 * 用户登录账号
	 */
	private String account;

	/**
	 *  用户名称
	 */
	private String userName;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String phoneNumber;

	/**
	 * 用户登陆设备
	 */
	private Integer loginDeviceType;


	public static CurrentUser valueOf(Long userId, String account, String name, Long tenantId, Integer deviceType,
									  String email) {
		CurrentUser currentUser = new CurrentUser();
		currentUser.setId(userId);
		currentUser.setAccount(account);
		currentUser.setUserName(name);
		currentUser.setTenantId(Objects.isNull(tenantId) ? 0L : tenantId);
		currentUser.setLoginDeviceType(Objects.isNull(deviceType) ? LoginDeviceTypeEnum.PC.getCode() : deviceType);
		currentUser.setEmail(email);
		return currentUser;
	}

}
