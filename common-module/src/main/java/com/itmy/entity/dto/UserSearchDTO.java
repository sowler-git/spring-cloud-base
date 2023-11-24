package com.itmy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 查询用户dto
 * @Author: niusaibo
 * @date: 2023-10-13 11:27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchDTO {

	/**
	 * 当前页数
	 */
	private Integer pageNum;

	/**
	 * 页的大小
	 */
	private Integer pageSize;

	/**
	 * 查询关键字
	 */
	private String keyword;

	/**
	 * 部门id
	 */
	private Long departmentId;

	/**
	 * 租户Id
	 */
	private Long tenantId;

	/**
	 * 是否注册
	 */
	private Boolean isRegisteredFace;

	/**
	 * 状态{0:禁用,1:正常}
	 */
	private Integer status;



}
