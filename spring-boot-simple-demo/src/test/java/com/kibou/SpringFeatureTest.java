package com.kibou;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;

import com.kibou.java_config.RepositoryConf;

//@ContextConfiguration
//@Configuration
public class SpringFeatureTest {
	
	@Test
	public void testAnnotationAliasFor(){
		Method[] methods = ComponentScan.class.getMethods();
		for(Method method : methods){
			AliasFor aliasFor = method.getAnnotation(AliasFor.class);
			if(aliasFor != null){
				System.out.println(method.getName() + " =alias= " + aliasFor.value());
			}
		}
	}
	
	@Test
	public void testAnnotationConfigApplicationContext(){
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RepositoryConf.class);
		context.close();
	}
	
}
