package com.crm.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.crm.domain.BaseDict;
import com.crm.service.BaseDictService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class BaseDictAction extends ActionSupport implements ModelDriven<BaseDict> {
	
	private BaseDictService baseDictService;
	public void setBaseDictService(BaseDictService baseDictService) {
		this.baseDictService = baseDictService;
	}
	
	private BaseDict baseDict = new BaseDict();
	@Override
	public BaseDict getModel() {
		// TODO Auto-generated method stub
		return baseDict;
	}
	
	
	public String findByTypeCode() throws Exception{
		System.out.println("basefind执行了");
		System.out.println(baseDict.getDict_type_code());
		List<BaseDict> list = baseDictService.findByTypeCode(baseDict.getDict_type_code());
		//将list转JSON ----jsonlib   fastjson
		/*
		 * JSONArray : 将数组和list集合转JSON
		 * JSONConfig : 转JSON的配置对象
		 * JSONObject : 将对象和Map集合转JSON
		 * */
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"dict_sort", "dict_enable", "dict_memo"});
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
		System.out.println(list.toString());
		return NONE;
	}

}
