package com.kibou.common.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.kibou.common.cache.serialize.KryoDeserializer;
import com.kibou.common.cache.serialize.KryoSerializer;
import com.kibou.common.cache.serialize.RedisObjectSerializer;
import com.kibou.common.cache.serialize.RedisObjectSerializer.RedisObjectSerializerBuilder;
import com.kibou.common.domain.User;

/**
 * 自定义 User类型为value的RedisTemplate
 * @author shijun.shi
 */
@Configuration
public class RedisCustomizeConfig {
	
	private final JedisConnectionFactory jedisConnectionFactory;
	
	//@see RedisConnectionConfiguration
	// 当前没有定义任何的 RedisConnectionFactory的话就会自己创建一个JedisConnectionFactory实例
	public  RedisCustomizeConfig(
			JedisConnectionFactory jedisConnectionFactory) {
		this.jedisConnectionFactory = jedisConnectionFactory;
	}
	
	@Bean
	public RedisTemplate<String, User> userRedisTemplate(){
		
		RedisTemplate<String, User> redisTemplate = new RedisTemplate<String, User>();
		
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		//默认的 keySerializer 为 JdkSerializationRedisSerializer,如果你使用redis-cli直接查看的话就会发现key前面有些特殊字节输出的字符
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		
//		redisTemplate.setValueSerializer(new RedisObjectSerializer());//use default serializer
		
		RedisObjectSerializer redisObjectSerializer = 
				RedisObjectSerializerBuilder.create()
					.serializer(KryoSerializer.create())
					.deserializer(KryoDeserializer.create())
				.build();
		redisTemplate.setValueSerializer(redisObjectSerializer);//use default serializer
		
		return redisTemplate;
	}
}
