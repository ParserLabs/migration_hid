package com.migration.migration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import feign.auth.BasicAuthRequestInterceptor;
import javassist.bytecode.analysis.Executor;

@Configuration
public class ClientConfiguration {

	@Value("${core_pool_size:30}")
	private   int CORE_POOL_SIZE;
	
	@Value("${max_pool_size:40}")
	private   int MAX_POOL_SIZE;
	
	@Value("${queue_capacity:500}")
	private   int QUEUE_CAPICITY;
	
	@Value("${user.kyc.service.username}")
	private String username;
	
	@Value("${user.kyc.service.password}")
	private String password;

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
		return new BasicAuthRequestInterceptor(username, password);
	}
	
	@Bean("IO_Thread_Executor")
	  public ThreadPoolTaskExecutor taskExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(CORE_POOL_SIZE);
	    executor.setMaxPoolSize(MAX_POOL_SIZE);
	    executor.setQueueCapacity(QUEUE_CAPICITY);
	    executor.setThreadNamePrefix("IO");
	    executor.initialize();
	    return executor;
	  }
}
