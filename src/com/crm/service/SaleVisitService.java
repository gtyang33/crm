package com.crm.service;

import org.hibernate.criterion.DetachedCriteria;

import com.crm.domain.PageBean;
import com.crm.domain.SaleVisit;

public interface SaleVisitService {

	PageBean<SaleVisit> findByPage(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize);

	void save(SaleVisit saleVisit);

	void delete(SaleVisit saleVisit);

	SaleVisit findById(String visit_id);

	void update(SaleVisit saleVisit);

}
