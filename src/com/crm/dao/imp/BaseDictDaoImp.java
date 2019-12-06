package com.crm.dao.imp;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.crm.dao.BaseDictDao;
import com.crm.domain.BaseDict;

public class BaseDictDaoImp extends HibernateDaoSupport implements BaseDictDao{

	@Override
	public List<BaseDict> findByTypeCode(String dict_type_code) {
		return (List<BaseDict>) this.getHibernateTemplate().find("from BaseDict where dict_type_code=?", dict_type_code);
	}

}
