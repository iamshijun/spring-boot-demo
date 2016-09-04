package com.kibou.springboot.data;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kibou.common.Application;
import com.kibou.common.domain.User;
import com.kibou.common.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class,webEnvironment = WebEnvironment.NONE)
public class SpringDataMongoTest{

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void setUp(){
		userRepository.deleteAll();
	}
	
	@Test
	public void testMongoRepository(){
		
		User user = userRepository.save(User.create(1L,"shijun",27));
		userRepository.save(User.create(2L,"reina",22));
		userRepository.save(User.create(3L,"mimi",25));
		
		Assert.assertThat(userRepository.count(), Matchers.equalTo(3L));
		
		User findOne = userRepository.findOne(user.getId());
		Assert.assertEquals(user, findOne);
		
		User findByName = userRepository.findByName(user.getName());
		Assert.assertEquals(user, findByName);
		
		userRepository.delete(user);
		Assert.assertThat(userRepository.findAll(), Matchers.hasSize(2));
		
		//userRepository.deleteAll();
	}
}
