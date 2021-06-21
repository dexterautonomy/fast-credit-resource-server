package com.hingebridge.devops.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.hingebridge.devops.utilities.AppProperties;
import com.hingebridge.devops.utilities.DatasourceProperties;
import com.hingebridge.devops.utilities.EncryptUtil;

@Configuration
public class UtilConfig {
	@Autowired
	private AppProperties prop;
	@Autowired
	private EncryptUtil encryptUtil;
	@Autowired
	DatasourceProperties datasourceProperties;

	@Bean
	public EncryptUtil encryptUtil() {
		return new EncryptUtil(prop.getEncryptKey());
	}

	@Bean
	@SuppressWarnings("rawtypes")
	public DataSource getDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(datasourceProperties.getDriver());
		dataSourceBuilder.url(datasourceProperties.getUrl());
		dataSourceBuilder.username(encryptUtil.decryptStringEncoded(datasourceProperties.getUsername()));
		dataSourceBuilder.password(encryptUtil.decryptStringEncoded(datasourceProperties.getPassword()));

		return dataSourceBuilder.build();
	}
	
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
	    LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
	    localValidatorFactoryBean.setValidationMessageSource(messageSource());
	    return localValidatorFactoryBean;
	}
}