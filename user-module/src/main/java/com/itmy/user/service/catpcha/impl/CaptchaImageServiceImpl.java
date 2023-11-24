package com.itmy.user.service.catpcha.impl;

import com.google.code.kaptcha.Producer;
import com.itmy.user.service.catpcha.ICaptchaImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * <p>
 * 图片验证码二进制格式
 * </p>
 *
 */

@Service
public class CaptchaImageServiceImpl implements ICaptchaImageService {

	private static final Logger logger = LoggerFactory.getLogger(CaptchaImageServiceImpl.class);

	@Autowired
	private Producer kaptchaProduer;

	@Override
	public String getImageBase64(String code) {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			BufferedImage image = kaptchaProduer.createImage(code);
			ImageIO.write(image, "PNG", bos);
			return Base64Utils.encodeToString(bos.toByteArray());
		}
		catch (Exception ex) {
			logger.error("CAPTCHA|genImg|N|{}", code, ex);
			return null;
		}
	}

}