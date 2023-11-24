package com.itmy.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * <p>
 * 图片验证码配置
 * </p>
 *
 */

@Configuration
public class KaptchaConfig {

	@Bean
	public Config captchaConfig() {
		Properties p = new Properties();
		p.setProperty("kaptcha.image.width", "100");
		p.setProperty("kaptcha.image.height", "48");
		return new Config(p);
	}

	@Bean
	public Producer captchaProducer() {
		DefaultKaptcha p = new DefaultKaptcha();
		p.setConfig(captchaConfig());
		return p;
	}

}
