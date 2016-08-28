package com.kibou.appconfig;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kibou.service.DependentService;
import com.kibou.service.FooService;
import com.kibou.service.BarService;

@Configuration
@EnableConfigurationProperties(ConnectionProperties.class)
//EnableConfigurationProperties就是将指定value上的类(注意是有@ConfigurationProperties注解的类 被注册到beanFactory中)
//  又或者不使用EnableConfigurationProperties 直接在@ConfigurationProperties注解的类 标注为@Component让它在被扫描的时候加入到beanFactory中
public class JavaConfig {
	
	@Bean
	public DependentService serviceA(){
		return new DependentService();
	}
	
	//可以看到serviceB,C的创建都是用到了serviceA,并使用方法调用的方式而不是通过参数注入的
	@Bean
	public FooService serviceB(){
		return new FooService(serviceA());
	}
	
	@Bean
	public BarService serviceC(){
		return new BarService(serviceA());
	}
}
