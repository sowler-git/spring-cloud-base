package com.itmy.entity.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 接口请求
 *
 * @author yehao
 * @date 2019-04-28 15:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request<T> {

	/**
	 * 参数model
	 */
	@Valid
	@NotNull
	private T model;

}
