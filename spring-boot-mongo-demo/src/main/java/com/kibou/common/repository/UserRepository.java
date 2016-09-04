package com.kibou.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kibou.common.domain.User;

//和Jpa一样通过继承 指定的Repository接口  即可实现实际对MongoDB的操作 
public interface UserRepository extends MongoRepository<User, Long> /*,IUserRepository*/{
	//spring根据MongoRepository的第一个泛型的具体类型 在mongo创建对应的db e.g: db.user
	
	User findByName(String name);
}