package com.crm.dao.imp;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.crm.dao.UserDao;
import com.crm.domain.User;

public class UserDaoImp extends BaseDaoImp<User> implements UserDao{
	

	@Override
	public User login(User user) {
		List<User> list = (List<User>) this.getHibernateTemplate().find("from User where user_code=? and user_password=?", user.getUser_code(), user.getUser_password());
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
		
	}

}
