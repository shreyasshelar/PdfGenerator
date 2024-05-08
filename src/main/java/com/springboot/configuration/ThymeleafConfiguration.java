package com.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfiguration {
	
	@Bean
	static ClassLoaderTemplateResolver pdfTemplateResolver()
	{
		 ClassLoaderTemplateResolver pdfTemplateResolver = new ClassLoaderTemplateResolver();
	        pdfTemplateResolver.setPrefix("templates/");
	        pdfTemplateResolver.setSuffix(".html");
	        pdfTemplateResolver.setTemplateMode("HTML5");
	        pdfTemplateResolver.setCharacterEncoding("UTF-8");
	        pdfTemplateResolver.setOrder(1);
	        return pdfTemplateResolver;
	}
}
