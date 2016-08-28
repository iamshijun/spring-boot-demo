package com.kibou.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public BarService(DependentService a) {
		logger.info("BarService.<init>");
	}
}
