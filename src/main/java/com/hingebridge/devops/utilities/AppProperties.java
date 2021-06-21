package com.hingebridge.devops.utilities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("app.prop")
public class AppProperties {
	private String grantTypePassword;
	private String grantTypeClientCredentials;
	private String clientId;
	private String clientSecret;
	private String threadPrefix;
	private int maxPoolSize;
	private int corePoolSize;
	private String encryptKey;
	private String authServerUrl;
	private String providerRole;
	private String consumerRole;
	private String webChannelCode;
	private String logDir;
}
