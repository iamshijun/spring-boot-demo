package com.kibou.common.repository.jdbc.impl;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kibou.common.domain.User;
import com.kibou.common.repository.IUserRepository;

@Repository(UserRepositoryImpl.COMPONENT_NAME)
public class UserRepositoryImpl implements IUserRepository {

	public final static String COMPONENT_NAME = "jdbcUserRepository";

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<User> listUsers(int start, int limit) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		sqlParameterSource.addValue("start", start, Types.INTEGER);
		sqlParameterSource.addValue("limit", limit, Types.INTEGER);
		
		return namedParameterJdbcTemplate.query("select * from users limit :start , :limit", 
				sqlParameterSource, new BeanPropertyRowMapper<User>(User.class));
	}

	@Override
	public void updateUser(User user) {
		namedParameterJdbcTemplate.update("update users set name=:name , age=:age where id = :id",
				new BeanPropertySqlParameterSource(user));
	}

	@Override
	public boolean deleteUser(User user) {
		if(user == null)
			return false;
		int update = namedParameterJdbcTemplate.update("delete from users where id = " + user.getId(),
				Collections.<String,Object>emptyMap());
		return update > 0;
	}

	@Override
	public User addUser(User user) {
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update("insert into users(name,age) values(:name,:age)", 
				new BeanPropertySqlParameterSource(user), keyHolder);
		long key = keyHolder.getKey().longValue();
		user.setId(key);
		return user;
	}

	@Override
	public User getUser(Long id) {
		try{
			return namedParameterJdbcTemplate.queryForObject("select * from users where id = :id",
					new MapSqlParameterSource("id", id), new BeanPropertyRowMapper<User>(User.class));
		}catch(DataAccessException dae){//IncorrectResultSizeDataAccessException
			dae.printStackTrace();
			return null;
			//或者如果不想有异常的话 使用 queryForList在根据情况取出第一个或者空的时候返回null.
		}
	}

}
