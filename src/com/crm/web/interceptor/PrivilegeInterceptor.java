package com.crm.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.crm.domain.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class PrivilegeInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		System.out.println(existUser);
		if(existUser == null){
			ActionSupport actionSupport = (ActionSupport) invocation.getAction();
			actionSupport.addActionError("您还没有登录，没有访问权限！");
			return actionSupport.LOGIN;
		}else{
			return invocation.invoke();
		}
		
	}

}
