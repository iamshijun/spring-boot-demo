package com.kibou.appconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kibou.service.ServiceA;
import com.kibou.service.ServiceB;
import com.kibou.service.ServiceC;

@Configuration
public class JavaConfig {
	
	@Bean
	public ServiceA serviceA(){
		return new ServiceA();
	}
	
	//可以看到serviceB,C的创建都是用到了serviceA,并使用方法调用的方式而不是通过参数注入的
	@Bean
	public ServiceB serviceB(){
		return new ServiceB(serviceA());
	}
	
	@Bean
	public ServiceC serviceC(){
		return new ServiceC(serviceA());
	}
}
