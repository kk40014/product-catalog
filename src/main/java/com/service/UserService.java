package com.service;

import org.springframework.security.core.userdetails.User;

import com.bean.UserInformation;

public interface UserService {
	void save(UserInformation user);

	User findByUsername(String username);
}
