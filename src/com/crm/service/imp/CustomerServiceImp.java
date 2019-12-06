package com.crm.service.imp;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.crm.dao.CustomerDao;
import com.crm.domain.Customer;
import com.crm.domain.PageBean;
import com.crm.service.CustomerService;

@Transactional
public class CustomerServiceImp implements CustomerService{
	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public void save(Customer customer) {
		customerDao.save(customer);
	}

	@Override
	public PageBean<Customer> findByPage(DetachedCriteria detacheedCriteria, Integer currPage, Integer pageSize) {
		PageBean<Customer> pageBean = new PageBean<>();
		// 封装当前页面
		pageBean.setCurrPage(currPage);
		//封装每页记录数
		pageBean.setPageSize(pageSize);
		//封装总记录数
		//调用DAO
		Integer totalCount = customerDao.findCount(detacheedCriteria);
		pageBean.setTotalCount(totalCount);
		
		//封装总页数；
		Double tc = totalCount.doubleValue();
		Double num = Math.ceil(tc / pageSize);
		pageBean.setTotalPage(num.intValue());
		//封装每页显示数据的集合
		Integer begin = (currPage - 1) * pageSize;
		List<Customer> list = customerDao.findByPage(detacheedCriteria, begin, pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public Customer findById(Long cust_id) {
		return customerDao.findById(cust_id);
	}

	@Override
	public void delete(Customer customer) {
		customerDao.delete(customer);
	}

	@Override
	public void update(Customer customer) {
		customerDao.update(customer);
	}

	@Override
	public List<Customer> findAll() {
		return customerDao.findAll();
		
	}
}
