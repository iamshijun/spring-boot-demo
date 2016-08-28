package com.kibou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@ComponentScan
@Configuration //可省略 , 因为有@RestController -> @Controller -> @Component 所以还是可以作为配置对象存在的
//上述三个注解的作用如同 SpringBootApplication 
public class Example {
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

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	public static void main(String[] args) throws Exception {
		// SpringApplication.run(Example.class, args);
		
		//or
		
		SpringApplication sa = new SpringApplication(Example.class);
		//sa.setBannerMode(Mode.OFF);// customize banner mode
		sa.setWebEnvironment(false);
		sa.run(args);
	}

}