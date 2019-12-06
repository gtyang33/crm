package com.crm.dao;

import com.crm.domain.User;

public interface UserDao extends BaseDao<User> {

	User login(User user);


}
