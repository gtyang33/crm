package com.crm.web.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.crm.domain.Customer;
import com.crm.domain.User;
import com.crm.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class UserAction extends ActionSupport implements ModelDriven<User>{
	
//	注入service
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	private User user = new User();
	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	public String regist(){
		System.out.println("regist");
		userService.regist(user);
		
		return LOGIN;
	}
	public String login(){
		
		User existUser = userService.login(user);
		System.out.println(existUser);
		if(existUser == null){
			//登陆失败
			//添加错误信息
			this.addActionError("用户名或密码错误");
			//返回登录页面
			return LOGIN;
		}else{
			//登录成功，天剑session
			
			ServletActionContext.getRequest().getSession().setAttribute("existUser", existUser);
			System.out.println(ServletActionContext.getRequest().getSession().getAttribute("existUser"));
			return SUCCESS;
		}
	}
	public String findAllCustomer() throws Exception{
		List<User> list = userService.findAll();
		//将list装JSON;
		JsonConfig jsonConfig = new JsonConfig();
		// jsonConfig.setExcludes(new String[]{""});
		//转成JSON
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
		return NONE;
	}
}
