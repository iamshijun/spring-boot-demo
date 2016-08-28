package com.kibou.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FooService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public FooService(DependentService a) {
		logger.info("FooService.<init>");
	}
}
