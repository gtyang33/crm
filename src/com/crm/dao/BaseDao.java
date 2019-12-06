package com.crm.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;



public interface BaseDao<T>{
	public void update(T t);
	
	public void save(T t);
	
	public void delete(T t);
	
	public List<T> findAll();
	
	public T findById(Serializable id);
	
	public Integer findCount(DetachedCriteria detachedCriteria);
	
	public List<T> findByPage(DetachedCriteria detachedCriteria, Integer begin, Integer pageSize);
	
}
