package com.kibou.jdbc;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringBootDataJdbcTest {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private DataSource defaultDataSource;
	@Autowired
	private DataSource sencondaryDataSource;
	
	@Test
	public void testDataSourceType(){
		System.out.println(dataSource.getClass());
		System.out.println(defaultDataSource.getClass());
		System.out.println(sencondaryDataSource.getClass());
	}
	
}
