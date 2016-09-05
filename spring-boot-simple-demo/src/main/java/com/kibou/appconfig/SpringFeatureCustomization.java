package com.kibou.appconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
public class SpringFeatureCustomization {
	
	private static Logger logger = LoggerFactory.getLogger(SpringFeatureCustomization.class);
	
	@EnableAsync /*根据配置里是否含有 spring.test.async=on 来决定是否开启异步调用的功能*/
	@ConditionalOnProperty(prefix = "spring.test", name = "async", havingValue = "on")
	public static class AsyncEnableConfiguration{
		public AsyncEnableConfiguration(){
			logger.info(AsyncEnableConfiguration.class.getName() + " activated");
		}
	}
	
	@EnableScheduling /* spring.test.task=on */
	@ConditionalOnProperty(prefix = "spring.test", name = "task", havingValue = "on")
	public static class ScheduledEnableConfiguration{
		public ScheduledEnableConfiguration(){
			logger.info(ScheduledEnableConfiguration.class.getName() + " activated");
		}
	}
}

