package com.crm.web.action;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.crm.domain.PageBean;
import com.crm.domain.SaleVisit;
import com.crm.service.SaleVisitService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class SaleVisitAction extends ActionSupport implements ModelDriven<SaleVisit>{
	private SaleVisit saleVisit = new SaleVisit();
	@Override
	public SaleVisit getModel() {
		// TODO Auto-generated method stub
		return saleVisit;
	}
	//使用注解属性注入不用配置和set方法
	@Resource(name="saleVisitService")
	private SaleVisitService saleVisitService;
	
	//筛选日期
	private Date visit_end_time;
	
	public Date getVisit_end_time() {
		return visit_end_time;
	}

	public void setVisit_end_time(Date visit_end_time) {
		this.visit_end_time = visit_end_time;
	}
	//接受分页数据
	private Integer currPage = 1;
	private Integer pageSize = 3;
	
	
	public void setCurrPage(Integer currPage) {
		if(currPage == null){
			currPage = 1;
		}
		this.currPage = currPage;
	}


	public void setPageSize(Integer pageSize) {
		if(pageSize == null){
			pageSize = 3;
		}
		this.pageSize = pageSize;
	}


	public String findAll(){
		//设置离线查询对象
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SaleVisit.class);
		//设置条件
		if(saleVisit.getVisit_time() != null){
			detachedCriteria.add(Restrictions.ge("visit_time", saleVisit.getVisit_time()));
		}
		if(visit_end_time != null){
			detachedCriteria.add(Restrictions.le("visit_time", visit_end_time));
		}
		//调用业务层
		PageBean<SaleVisit> pageBean = saleVisitService.findByPage(detachedCriteria, currPage, pageSize);
		//存入值栈
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}
	
	public String saveUI(){
		return "saveUI";
	}
	
	public String save(){
		saleVisitService.save(saleVisit);
		return "saveSuccess";
	}
	
	public String delete(){
		saleVisit = saleVisitService.findById(saleVisit.getVisit_id());
		
		saleVisitService.delete(saleVisit);
		return "deleteSuccess";
	}
	public String update(){
		saleVisitService.update(saleVisit);
		return "updateSuccess";
	}
	public String edit(){
		saleVisit = saleVisitService.findById(saleVisit.getVisit_id());
		ActionContext.getContext().getValueStack().push(saleVisit);
		return "editSuccess";
	}

}
