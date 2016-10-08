package com.kibou;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kibou.domain.User;
import com.kibou.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void before() {
		userRepository.save(new User("AAA", 10));
	}
	@Test
	@Transactional
	public void test() throws Exception {
		/*验证 : 通过查看控制台上的sql输出 判断是否使用到了缓存cache - spring.test.caching*/
		User u1 = userRepository.findByName("AAA");
		System.out.println("第一次查询：" + u1.getAge());
		
		User u2 = userRepository.findByName("AAA");
		System.out.println("第二次查询：" + u2.getAge());
		
		
		userRepository.delete(u1);
	}
	
}
