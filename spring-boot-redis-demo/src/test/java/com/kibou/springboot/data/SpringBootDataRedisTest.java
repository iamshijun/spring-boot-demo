package com.kibou.springboot.data;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kibou.Application;
import com.kibou.common.cache.RedisCustomizeConfig;
import com.kibou.common.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringBootDataRedisTest{

	@Autowired
	private StringRedisTemplate stringRedisTemplate; 
	// ==> RedisTemplate<String,String> + StringSerializaer
	
	/** @see RedisCustomizeConfig */
	@Autowired 
	private RedisTemplate<String, User> userRedisTemplate;
	
	@Test
	public void testStringRedisTemplate(){
		ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
		valueOps.set("name", "shijun.shi");
		Assert.assertEquals("shijun.shi", valueOps.get("name"));
	}
	
	@Test
	public void tesRedisValueSerializer(){
		User newUser = User.create(1L, "iamshijun", 27);
		String key = "springboot:data:redis:cache:user:" + newUser.getId();
		userRedisTemplate.opsForValue().set(key, newUser);
		
		Assert.assertEquals(newUser, userRedisTemplate.opsForValue().get(key));
	}
	
	/*
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Test
	public void testOpsUsage(){
		
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
		
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
		ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
		
		ClusterOperations<String, String> opsForCluster = redisTemplate.opsForCluster();
		
		HyperLogLogOperations<String, String> opsForHyperLogLog = redisTemplate.opsForHyperLogLog();
		//当然除了使用 redisTemplate.opsForXXX() 得到对应的Operations之外,其实也可以直接在bean属性中直接指定
		//相应类型的Operation进行注入
		//e.g @Autowired private HashOperations<String,String,Object> hashOperation;
		
		
		//还有相对应的boundXXXops(key) -- 绑定到某个key上的ops操作
		BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps("key");
		//.....
	}*/
}
