package com.crm.service.imp;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.crm.dao.LinkManDao;
import com.crm.domain.Customer;
import com.crm.domain.LinkMan;
import com.crm.domain.PageBean;
import com.crm.service.LinkManService;

@Transactional
public class LinkManServiceImp implements LinkManService {
	private LinkManDao linkManDao;

	public void setLinkManDao(LinkManDao linkManDao) {
		this.linkManDao = linkManDao;
	}

	@Override
	public PageBean<LinkMan> findAll(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize) {
		PageBean<LinkMan> pageBean = new PageBean<>();
		// 封装当前页面
		pageBean.setCurrPage(currPage);
		//封装每页记录数
		pageBean.setPageSize(pageSize);
		//封装总记录数
		//调用DAO
		Integer totalCount = linkManDao.findCount(detachedCriteria);
		pageBean.setTotalCount(totalCount);
		
		//封装总页数；
		Double tc = totalCount.doubleValue();
		Double num = Math.ceil(tc / pageSize);
		pageBean.setTotalPage(num.intValue());
		//封装每页显示数据的集合
		Integer begin = (currPage - 1) * pageSize;
		List<LinkMan> list = linkManDao.findByPage(detachedCriteria, begin, pageSize);
		pageBean.setList(list);
		return pageBean;
		
	}

	@Override
	public void save(LinkMan linkMan) {
		linkManDao.save(linkMan);
	}

	@Override
	public LinkMan findById(Long lkm_id) {
		return linkManDao.findById(lkm_id);
	}

	@Override
	public void update(LinkMan linkMan) {
		linkManDao.update(linkMan);
	}

	@Override
	public void delete(LinkMan linkMan) {

		linkManDao.delete(linkMan);
	}
	
}
