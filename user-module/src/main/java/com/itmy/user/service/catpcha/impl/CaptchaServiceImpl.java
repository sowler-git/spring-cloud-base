package com.itmy.user.service.catpcha.impl;


import com.itmy.constant.Consts;
import com.itmy.enums.ErrorEnum;
import com.itmy.exception.ParamException;
import com.itmy.user.service.catpcha.ICaptchaImageService;
import com.itmy.user.service.catpcha.ICaptchaService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 图片验证码实现接口
 * </p>
 *
 */

@Service
public class CaptchaServiceImpl implements ICaptchaService {

	private static final Logger logger = LoggerFactory.getLogger(CaptchaImageServiceImpl.class);

	@Autowired
	private ICaptchaImageService imageService;

	@Autowired
	private RedissonClient redissonClient;

	@Override
	public String generateCode() {
		String val = "TRUE";
		boolean onlyNum = Boolean.parseBoolean(val);
		char[] chars = new char[Consts.DEFAULT_CODE_LENGTH];
		for (int i = 0; i < Consts.DEFAULT_CODE_LENGTH; i++) {
			if(onlyNum) {
				int p = RandomUtils.nextInt(0, Consts.POSSIBLE_NUM_CHARS.length);
				chars[i] = Consts.POSSIBLE_NUM_CHARS[p];
			} else {
				int p = RandomUtils.nextInt(0, Consts.POSSIBLE_CHARS.length);
				chars[i] = Consts.POSSIBLE_CHARS[p];
			}

		}
		return new String(chars);
	}

	@Override
	public String getImg(String redisKey) {
		String code = generateCode();
		String base64Image;
		try {
			base64Image = imageService.getImageBase64(code);
		}
		catch (Exception ex) {
			logger.error("CAPTCHA|getImg|N|{}", code, ex);
			throw new ParamException(ErrorEnum.CAPTCHA_CREATE_ERROR);
		}

		if (StringUtils.isEmpty(base64Image)) {
			throw new ParamException(ErrorEnum.CAPTCHA_CREATE_ERROR);
		}

		// 将图片验证码code 存在相应的key中 四分钟内有效
		RBucket<String> bucket = redissonClient.getBucket(redisKey);
		bucket.set(code.toLowerCase(), Consts.DEFAULT_CODE_LENGTH, TimeUnit.MINUTES);
		return base64Image;
	}

}
