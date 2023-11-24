package com.itmy.user.service.catpcha;

/**
 * <p>
 * 图片验证码二进制格式
 * </p>
 */
public interface ICaptchaImageService {

	/**
	 * 获取验证码图片 字符串
	 * @param code
	 * @return
	 */
	String getImageBase64(String code);

}
