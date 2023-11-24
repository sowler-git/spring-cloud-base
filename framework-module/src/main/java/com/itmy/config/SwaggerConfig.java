package com.itmy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * api接口包扫描路径
	 */
	private static final String SWAGGER_SCAN_BASE_PACKAGE = "com.itmy";

	private static final String VERSION = "1.0.0";

	@Value("${swagger.enable:false}")
	private boolean swaggerEnable;

	@Bean
	public Docket api() {
		ParameterBuilder ticketPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		ticketPar.name("Authorization").description("user token").modelRef(new ModelRef("string"))
				.parameterType("header").required(false).build();
		pars.add(ticketPar.build());
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(swaggerEnable)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars);
	}



	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				//设置文档的标题
				.title("api文档")
				// 设置文档的描述
				.description("RESTful 风格接口")
				// 设置文档的版本信息-> 1.0.0 Version information
				.version(VERSION)
				// 设置文档的License信息->1.3 License information
				.termsOfServiceUrl("http://localhost:8800")
				.build();
	}

}