package com.itmy.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

@Configuration
//@ConditionalOnProperty(value = "db_type", havingValue = "mysql")
//@MapperScan(basePackages = "com.itmy.")
public class DataSourceMysqlConfig {
	private static final List<String> MAPPER_LOCATIONS = new ArrayList<String>(){{
		add("classpath:/mapper/base/*.xml");
		add("classpath:/mapper/mysql/*.xml");
	}};

	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix="spring.datasource.mysql")
	public DataSource dataSource(){
		//DataSource dataSource = DataSourceBuilder.create().build();
		//return dataSource;
		return new DruidDataSource();

	}

	@Bean(name = "mybatisSqlSessionFactory")
	public MybatisSqlSessionFactoryBean mybatisSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource,
																 @Qualifier("globalConfig") GlobalConfig globalConfig,
																 @Qualifier("paginationInterceptor") PaginationInterceptor paginationInterceptor){
		MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
	 	sessionFactory.setDataSource(dataSource);
	 	sessionFactory.setMapperLocations(resolveMapperLocations());
		sessionFactory.setGlobalConfig(globalConfig);

		//控制台输出 生产时注释
		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setLogImpl(StdOutImpl.class);
		//configuration.setLogImpl(NoLoggingImpl.class);
		sessionFactory.setConfiguration(configuration);

		Interceptor[] plugins = {paginationInterceptor};
		sessionFactory.setPlugins(plugins);
	 	return sessionFactory;
	}

	@Bean("sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mybatisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}

	private Resource[] resolveMapperLocations() {
		ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		List<Resource> resources = new ArrayList();
		for (String mapperLocation : MAPPER_LOCATIONS) {
			try {
				Resource[] mappers = resourceResolver.getResources(mapperLocation);
				resources.addAll(Arrays.asList(mappers));
			} catch (IOException e) {
				// ignore
			}
		}
		return resources.toArray(new Resource[resources.size()]);
	}

	//配置Druid的监控
	//1、配置一个管理后台的Servlet
	@Bean
	public ServletRegistrationBean statViewServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		Map<String, String> initParams = new HashMap<>();

		initParams.put("loginUsername", "admin");
		initParams.put("loginPassword", "123456");
		initParams.put("allow", "");//默认就是允许所有访问

		bean.setInitParameters(initParams);
		return bean;
	}


	//2、配置一个web监控的filter
	@Bean
	public FilterRegistrationBean webStatFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new WebStatFilter());

		Map<String, String> initParams = new HashMap<>();
		initParams.put("exclusions", "*.vue,*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");

		bean.setInitParameters(initParams);

		bean.setUrlPatterns(Arrays.asList("/*"));

		return bean;
	}
}
