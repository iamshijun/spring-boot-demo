package com.kibou;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
//	private static Logger logger = LoggerFactory.getLogger("com.kibou.AppLogger");//use slf4j facade
	private static Logger logger = LogManager.getLogger("com.kibou.AppLogger");

	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.traceEntry();
		logger.debug("Application is running");
	}
	/*
	 * ps : Log4j will provide a default configuration if it cannot locate
	 * a configuration file. The default configuration, provided in the
	 * DefaultConfiguration class, will set up:
	 * 
	 * A ConsoleAppender attached to the root logger.
	 * 
	 * A PatternLayout set to the pattern "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n" attached to the ConsoleAppender
	 * 
	 * Note that by default Log4j assigns the root logger to Level.ERROR.
	 */
}
