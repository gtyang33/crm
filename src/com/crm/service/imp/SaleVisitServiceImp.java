package com.crm.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.crm.dao.SaleVisitDao;
import com.crm.domain.PageBean;
import com.crm.domain.SaleVisit;
import com.crm.service.SaleVisitService;

@Transactional
public class SaleVisitServiceImp implements SaleVisitService {
	@Resource(name="saleVisitDao")
	private SaleVisitDao saleVisitDao;

	@Override
	public PageBean<SaleVisit> findByPage(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize) {
		PageBean<SaleVisit> pageBean = new PageBean<SaleVisit>();
		//当前页面
		pageBean.setCurrPage(currPage);
		//每页显示记录数
		pageBean.setPageSize(pageSize);
		Integer totalCount = saleVisitDao.findCount(detachedCriteria);
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);
		//总的记录数
		pageBean.setTotalCount(totalCount);
		//总的页数
		pageBean.setTotalPage(num.intValue());
		Integer begin = (currPage - 1) * pageSize;
		List<SaleVisit> list = saleVisitDao.findByPage(detachedCriteria, begin, pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public void save(SaleVisit saleVisit) {
		
		saleVisitDao.save(saleVisit);
	}

	@Override
	public void delete(SaleVisit saleVisit) {
		
		saleVisitDao.delete(saleVisit);
	}

	@Override
	public SaleVisit findById(String visit_id) {
		return saleVisitDao.findById(visit_id);
	}

	@Override
	public void update(SaleVisit saleVisit) {
		
		saleVisitDao.update(saleVisit);
	}
	
	

}
