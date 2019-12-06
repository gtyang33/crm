package com.crm.dao.imp;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.crm.dao.LinkManDao;
import com.crm.domain.Customer;
import com.crm.domain.LinkMan;

public class LinkManDaoImp extends BaseDaoImp<LinkMan> implements LinkManDao {
	//通过有参构造提供class
//	public LinkManDaoImp() {
//		super(LinkMan.class);
//		// TODO Auto-generated constructor stub
//	}


}
