package com.kibou.springboot.data;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kibou.Application;
import com.kibou.domain.User;
import com.kibou.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringBootDataJpaTest{
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void setUp(){
		userRepository.deleteAll();
	}
	
	@Test
	@Transactional
//	@Commit or @Rollback
	public void testJpaRepository(){
		
		List<User> users = new ArrayList<User>();
		for(int i = 0; i < 100;++i){
			users.add(User.create(null, i+"", i));
		}
		userRepository.save(users);
		userRepository.flush();
		
		
		String username = "shijun.shi";
		int age = 27;
		
		User newUser = userRepository.save(User.create(null, username, age));
		Assert.assertNotNull(newUser);
		Assert.assertNotNull("id should not be null",newUser.getId());
		
		//注意和NamedParameter类似,这里必须是有且只有一个记录(可以为空/不存在),即名称唯一
		User findByName = userRepository.findByName(username);
		Assert.assertThat(findByName, Matchers.equalTo(newUser));
		
		User findOne = userRepository.findOne(newUser.getId()); // CrudRepositry
//		userRepository.getOne(id); //JpaRespository
		Assert.assertThat(findOne, Matchers.equalTo(newUser));
		
		long count = userRepository.count(Example.of(User.create(null, null, age)));
		Assert.assertThat(2L, Matchers.equalTo(count));
		
		String chineseName = "阿莲.沃克";
		newUser.setName(chineseName);
		newUser.setAge(28);
		userRepository.saveAndFlush(newUser);
		
		User getOne = userRepository.getOne(newUser.getId());
		Assert.assertEquals(chineseName, getOne.getName());
		
		userRepository.delete(newUser.getId());
		
		long totalCount = userRepository.count();
		Assert.assertEquals(100, totalCount);
		
		Page<User> page = userRepository.findAll(new PageRequest(5, 10,new Sort(Direction.ASC,"age")));
		System.out.println(page.getContent());
		
		while(!page.isLast()){
			Pageable nextPageable = page.nextPageable();
			Page<User> nextPage = userRepository.findAll(nextPageable);
			
			page = nextPage;
			System.out.println(nextPage.getContent());
		}
		
//		userRepository.deleteAll();//CrudRepository , 好狠 
		userRepository.deleteAllInBatch();//Jpa
		
		boolean exists = userRepository.exists(1L);
		Assert.assertFalse(exists);
	}
}
