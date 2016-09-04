package com.kibou.java_config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kibou.common.repository.PersonRepository;

@Configuration
public class RepositoryConf {

	private static Logger logger = LoggerFactory.getLogger(RepositoryConf.class);
	@Bean
	public PersonRepository personRepository(){
		return new PersonRepository();
	}
	
	public RepositoryConf() {
		logger.info("RepositoryConf.<init>()");
	}
	
	@Configuration
	static class ResourceConf {
		
		public ResourceConf(PersonRepository repository) {//@Autowired
			System.out.println("WebAppConf.ResourceConf.ResourceConf()..");
			System.out.println("PersonRepository" + repository); //这里如果@Configuration存在含有参数的构造函数,参数会在beanFactory中查找
		}
	}
}
