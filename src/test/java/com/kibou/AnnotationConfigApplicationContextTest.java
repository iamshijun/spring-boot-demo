package com.kibou;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kibou.appconfig.JavaConfig;
import com.kibou.service.DependentService;

public class AnnotationConfigApplicationContextTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				JavaConfig.class);
		DependentService a = applicationContext.getBean(DependentService.class);
		a.sayHello();
		
		applicationContext.close();
	}
}
