package com.crm.service.imp;

import java.util.List;


import com.crm.dao.BaseDictDao;
import com.crm.domain.BaseDict;
import com.crm.service.BaseDictService;

public class BaseDictServiceImp implements BaseDictService{
	private BaseDictDao baseDictDao;

	public void setBaseDictDao(BaseDictDao baseDictDao) {
		this.baseDictDao = baseDictDao;
	}

	@Override
	public List<BaseDict> findByTypeCode(String dict_type_code) {
		return baseDictDao.findByTypeCode(dict_type_code);
	}
	
}
