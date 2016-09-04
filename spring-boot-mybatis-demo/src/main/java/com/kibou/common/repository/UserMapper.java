package com.kibou.common.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kibou.common.domain.User;

@Mapper //from : http://blog.didispace.com/mybatisinfo/
public interface UserMapper {
	// 注意这里的@Param是ibatis下的 不是data.jpa包下的
	@Select("SELECT * FROM USERS WHERE NAME = #{name}")
	User findByName(@Param("name") String name);

	@Insert("INSERT INTO USERS(NAME, AGE) VALUES(#{name}, #{age})")
	int insert(@Param("name") String name, @Param("age") Integer age);
	
	/*通过map对象作为传递参数的容器*/
	@Insert("INSERT INTO USERS(NAME, AGE) VALUES(#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
	int insertByMap(Map<String, Object> map); 

	/*使用对象*/
	@Update("UPDATE USERS SET age=#{age} WHERE name=#{name}")
	void update(User user);

	@Delete("DELETE FROM USERS WHERE id =#{id}")
	void delete(Long id);
	
	@Delete("DELETE FROM USERS") //only for test
	void deleteAll();//
}
