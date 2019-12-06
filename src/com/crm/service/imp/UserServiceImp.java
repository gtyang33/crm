package com.crm.service.imp;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.crm.dao.UserDao;
import com.crm.domain.User;
import com.crm.service.UserService;
import com.crm.utils.MD5Utils;

@Transactional
public class UserServiceImp implements UserService {
//	注入DAO
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public void regist(User user) {
		user.setUser_password(MD5Utils.md5(user.getUser_password()));
		user.setUser_state("1");
		userDao.save(user);
	}

	@Override
	public User login(User user) {
		
		user.setUser_password(MD5Utils.md5(user.getUser_password()));
		User existUser = userDao.login(user);
		return existUser;
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}
}
