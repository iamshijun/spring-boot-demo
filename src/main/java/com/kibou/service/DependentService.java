package com.kibou.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependentService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public DependentService() {
		logger.info("DependentService.<init>");
	}

	public void sayHello() {
		System.out.println("Hello i'm DependentService ");
	}
}
