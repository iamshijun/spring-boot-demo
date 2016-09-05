package com.kibou.common.configuration;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(DefaultDataSourceProperties.class)
public class DataSourceConfig {

	/*
	 * 这里和下面的区别就是这里先build完返回后 有具体的ConfiguationProperties对应的PostProcessor对返回的bean再次进行属性设置 (postBeforeInitialization)
	 * 另外 好处是不用再次创建对应前缀的ConfigurationProperties注解的配置类
	 */
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.secondary")
	public DataSource secondaryDataSource(){
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@Primary
	public DataSource defaultDataSource(DefaultDataSourceProperties dataSourceProperties){
		return DataSourceBuilder.create() //可以看到DataSourceBuilder中的数据源类型有四种默认的类型,"org.apache.tomcat.jdbc.pool.DataSource"最优先
					.driverClassName(dataSourceProperties.getDriverClassName())
					.url(dataSourceProperties.getUrl())
					.username(dataSourceProperties.getUsername())
					.password(dataSourceProperties.getPassword())
//					.type(type)
				.build();
	}
	
}
