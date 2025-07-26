package com.example.cloudconfigprop.services;

import org.springframework.stereotype.Service;

import com.example.cloudconfigprop.config.AppProperties;

@Service
public class AppService {

	private final AppProperties appProperties;

	public AppService(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public AppProperties getConfiguracoes() {
		return appProperties;
	}
}