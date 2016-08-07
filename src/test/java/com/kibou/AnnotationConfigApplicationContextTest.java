package com.kibou;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kibou.appconfig.JavaConfig;
import com.kibou.service.ServiceA;

public class AnnotationConfigApplicationContextTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				JavaConfig.class);
		ServiceA a = applicationContext.getBean(ServiceA.class);
		a.sayHello();
		
		applicationContext.close();
	}
}
