package com.itmy.user.service.catpcha;

/**
 * <p>
 * 图片验证码接口
 * </p>
 *
 */

public interface ICaptchaService {

	/**
	 * 获取随机图验证码
	 * @return
	 */
	String generateCode();

	/**
	 * 获取图片验证码二进制格式字符串 并存在响应的redisKey中
	 * @param rediskey
	 * @return
	 */
	String getImg(String rediskey);

}
