package com.example.cloudconfigprop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import com.example.cloudconfigprop.config.AppProperties;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = AppProperties.class)
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
}