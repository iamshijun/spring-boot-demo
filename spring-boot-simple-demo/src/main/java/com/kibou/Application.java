package com.kibou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@RestController

@EnableAutoConfiguration
@ComponentScan
@Configuration //可省略 , 因为有@RestController -> @Controller -> @Component 所以还是可以作为配置对象存在的
//上述三个注解的作用如同 SpringBootApplication 
public class Application {
	//Usually the class that defines the main method is also a good candidate as the primary @Configuration.
	
	/*
	 * We generally recommend that you locate your main application class in a
	 * root package above other classes. The @EnableAutoConfiguration annotation
	 * is often placed on your main class, and it implicitly defines a base
	 * “search package” for certain items
	 * -------------
	 * Using a root package also allows the @ComponentScan annotation to be used
	 * without needing to specify a basePackage attribute. You can also use
	 * the @SpringBootApplication annotation if your main class is in the root
	 * package.
	 */

	public static void main(String[] args) throws Exception {
		// SpringApplication.run(Application.class, args);
		
		//or
		
		SpringApplication sa = new SpringApplication(Application.class);
//		sa.setBannerMode(Mode.OFF);// customize banner mode , 或者在配置文件中 设置 spring.main.bannerMode=off
		sa.setWebEnvironment(false);//如果有引入sprong-boot-starter-web默认就会以web形式启动,可以强制设置以普通应用的形式启动
		sa.run(args);
	}

	
	/*
	 * @SpringBootApplication is a convenience annotation that adds all of the
	 * following:
	 * 
	 * @Configuration tags the class as a source of bean definitions for the
	 * application context.
	 * 
	 * @EnableAutoConfiguration tells Spring Boot to start adding beans based on
	 * classpath settings, other beans, and various property settings. Normally
	 * you would add @EnableWebMvc for a Spring MVC app, but Spring Boot adds it
	 * automatically when it sees spring-webmvc on the classpath. This flags the
	 * application as a web application and activates key behaviors such as
	 * setting up a DispatcherServlet.
	 * 
	 * @ComponentScan tells Spring to look for other components, configurations,
	 * and services in the the hello package, allowing it to find the
	 * HelloController.
	 */
}