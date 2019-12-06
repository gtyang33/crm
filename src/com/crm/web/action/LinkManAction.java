package com.crm.web.action;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.crm.domain.Customer;
import com.crm.domain.LinkMan;
import com.crm.domain.PageBean;
import com.crm.service.CustomerService;
import com.crm.service.LinkManService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LinkManAction extends ActionSupport implements ModelDriven<LinkMan>{
	private LinkMan linkMan = new LinkMan();
	@Override
	public LinkMan getModel() {
		// TODO Auto-generated method stub
		return linkMan;
	}
	private CustomerService customerService;
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	private LinkManService linkManService;
	public void setLinkManService(LinkManService linkManService) {
		this.linkManService = linkManService;
	}
	//设置分页
	private Integer currPage=1;
	private Integer pageSize=3;
	
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
		//创建离线查询
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LinkMan.class);
		if(linkMan.getLkm_name() != null){
			detachedCriteria.add(Restrictions.like("lkm_name", "%"+linkMan.getLkm_name()+"%"));
		}
		if(linkMan.getLkm_gender() != null && ! "".equals(linkMan.getLkm_gender())){
			detachedCriteria.add(Restrictions.eq("lkm_gender", linkMan.getLkm_gender()));
		}
		//设置条件
		//调用业务层
		PageBean<LinkMan> pageBean = linkManService.findAll(detachedCriteria, currPage, pageSize);
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}
	
	public String saveUI(){
		//同步查询客户信息
		List<Customer> list = customerService.findAll();
		ActionContext.getContext().getValueStack().set("list", list);
		return "saveUI";
	}
	public String save(){
		System.out.println(linkMan);
		linkManService.save(linkMan);
		return "saveSuccess";
	}
	
	public String edit(){
		//查询所有客户
		List<Customer> list = customerService.findAll();
		ActionContext.getContext().getValueStack().set("list", list);
		//根据id查联系人
		linkMan = linkManService.findById(linkMan.getLkm_id());
		//将list和对象传入页面上
		ActionContext.getContext().getValueStack().set("list", list);
		//将数据存在值栈中，回显时，如果name属性和value值一样，不用写value，
		//如果没有手动存入值栈中，需要使用value=model.去调用
		ActionContext.getContext().getValueStack().push(linkMan);
		return "editSuccess";
	}
	public String update(){
		linkManService.update(linkMan);
		return "updateSuccess";
	}
	public String delete(){
		//先查询再删除
		linkMan = linkManService.findById(linkMan.getLkm_id());
		
		linkManService.delete(linkMan);
		return "deleteSuccess";
	}

}
