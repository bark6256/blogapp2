package com.cos.blogapp2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cos.blogapp2.handler.SessionIntercepter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	public WebMvcConfig() {
		
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SessionIntercepter())
			.addPathPatterns("/api/**");
	}
}
