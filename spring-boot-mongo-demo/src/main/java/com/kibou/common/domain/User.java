package com.kibou.common.domain;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

	private static final long serialVersionUID = -4564939067876079885L;

	private Long id;
	private String name;
	private Integer age;

	public User() {
	}

	public static User create(String name, Integer age) {
		return new User(null, name, age);
	}

	public static User create(Long id, String name, Integer age) {
		return new User(id, name, age);
	}

	public User(Long id, String name, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User other = (User) obj;
			return Objects.equals(this.id, other.id)
					&& Objects.equals(this.name, other.name)
					&& Objects.equals(this.age, other.age);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.age);
	}

}
