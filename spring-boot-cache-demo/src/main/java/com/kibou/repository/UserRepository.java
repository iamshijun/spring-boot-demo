package com.kibou.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kibou.domain.User;

/* http://blog.didispace.com/springbootcache1/
//在Spring Boot中通过@EnableCaching注解自动化配置合适的缓存管理器（CacheManager），Spring Boot根据下面的顺序去侦测缓存提供者：
 (具体对应着CacheType)
	Generic
	JCache (JSR-107)
	EhCache 2.x
	Hazelcast
	Infinispan
	Redis
	Guava
	Simple
*/
@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<User,Long>{

	@Cacheable
	public User findByName(String name);
	
	public void deleteByName(String name);
}
