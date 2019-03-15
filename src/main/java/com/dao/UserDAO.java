package com.dao;

import com.bean.UserInformation;

public interface UserDAO {

	public UserInformation findByUsername(String username);

}
