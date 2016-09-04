package com.kibou.common.repository;

import java.util.List;

import com.kibou.common.domain.User;

public interface IUserRepository {

	public abstract List<User> listUsers(int start, int limit);

	public abstract void updateUser(User user);

	public abstract boolean deleteUser(User user);

	public abstract User addUser(User user);

	public abstract User getUser(Long id);

	
}
