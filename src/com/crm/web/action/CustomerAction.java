package com.crm.web.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.crm.domain.BaseDict;
import com.crm.domain.Customer;
import com.crm.domain.PageBean;
import com.crm.service.CustomerService;
import com.crm.utils.UploadUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
	private Customer customer = new Customer();
	@Override
	public Customer getModel() {
		return customer;
	}
	private CustomerService customerService;
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	private Integer currPage = 1;
	private Integer pageSize = 3;
	
	public void setCurrPage(Integer currPage) {
		if(currPage == null){
			currPage=1;
		}
		this.currPage = currPage;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/*
	 * 文件上传的三个属性
	 * 注意这里的upload的是和add.jsp中表单的name属性一致
	 * */
	private String uploadFileName; //文件名称
	private File upload;			//上传的文件
	private String uploadContetType; //文件类型
	
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadContetType(String uploadContetType) {
		this.uploadContetType = uploadContetType;
	}
	
	
	
	public String saveUI(){
		return "saveUI";
	}
	public String save() throws Exception{
		//上传文件
		if(upload != null){
			//进行文件上传
			String path = "F:\\java\\25.SSH\\03\\资料";
			//得到随机文件名
			String uuidFileName = UploadUtils.getUuidFileName(uploadFileName);
			//一个目录下存放文件太多，目录分离
			String realPath = UploadUtils.getPath(uuidFileName);
			//创建目录
			String url = path + realPath;
			File file = new File(url);
			if(!file.exists()){
				file.mkdirs();
			}
			//文件上传
			File dictFile = new File(url + "/" + uuidFileName);
			FileUtils.copyFile(upload, dictFile);
			//设置image的属性
			customer.setCust_image(url + "/" + uuidFileName);
			
		}
		//保存客户
		customerService.save(customer);
		return "saveSuccess";
	}
	/*
	 * 分页查询客户的方法
	 * */
	public String findAll(){
		//接受参数：分页参数
		//最好使用DetachedCriteria对象
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
		//添加查询条件
		if(customer.getCust_name() != null){
			// 输入名称
			detachedCriteria.add(Restrictions.like("cust_name", "%"+customer.getCust_name()+"%"));
		}
		if(customer.getBaseDictSource() != null && customer.getBaseDictSource().getDict_id() != null){
			if(!"".equals(customer.getBaseDictSource().getDict_id())){
				detachedCriteria.add(Restrictions.eq("baseDictSource.dict_id", customer.getBaseDictSource().getDict_id()));
			}
		}
		if(customer.getBaseDictLevel() != null && customer.getBaseDictLevel().getDict_id() != null){
			if(!"".equals(customer.getBaseDictLevel().getDict_id())){
				detachedCriteria.add(Restrictions.eq("baseDictLevel.dict_id", customer.getBaseDictLevel().getDict_id()));
			}
			
		}
		if(customer.getBaseDictIndustry() != null && customer.getBaseDictIndustry().getDict_id() != null){
			if(!"".equals(customer.getBaseDictIndustry().getDict_id())){
				detachedCriteria.add(Restrictions.eq("baseDictIndustry.dict_id", customer.getBaseDictIndustry().getDict_id()));
			}
		}
		//调用业务层查询
		PageBean<Customer> pageBean = customerService.findByPage(detachedCriteria, currPage, pageSize);
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}
	
	public String delete(){
		//先查询，再删除
		customer = customerService.findById(customer.getCust_id());
		if(customer.getCust_image() != null){
			File file = new File(customer.getCust_image());
			if(file.exists()){
				file.delete();
			}	
		}
		customerService.delete(customer);
		return "deleteSuccess";
	}
	/*
	 * 编辑客户
	 * */
	public String edit(){
		//根据id查询，跳转页面，回显数据
		customer = customerService.findById(customer.getCust_id());
		//将customer传递到页面
		//两种方法：1手动压栈    2因为啥模型驱动的对象，默认在栈顶
		//第一种方法： 回显数据：   <s:property value="cust_name"/>
		// ActionContext.getContext().getValueStack().push(customer);
		// 第二种方法： 回显数据： <s:property value="model.cust_name"/>89
		return "editSuccess";
	}
	
	public String update() throws Exception{
		//查看文件项是否已经选择
		//选择了就删除源文件，如果没有选就直接原有的文件即可
		if(upload != null){
			String cust_image = customer.getCust_image();
			if(cust_image != null || !"".equals(cust_image)){
				File file = new File(cust_image);
				file.delete();
			}
			//进行文件上传
			String path = "F:\\java\\25.SSH\\03\\资料";
			//得到随机文件名
			String uuidFileName = UploadUtils.getUuidFileName(uploadFileName);
			//一个目录下存放文件太多，目录分离
			String realPath = UploadUtils.getPath(uuidFileName);
			//创建目录
			String url = path + realPath;
			File file = new File(url);
			if(!file.exists()){
				file.mkdirs();
			}
			//文件上传
			File dictFile = new File(url + "/" + uuidFileName);
			FileUtils.copyFile(upload, dictFile);
			//设置image的属性
			customer.setCust_image(url + "/" + uuidFileName);
		}
		customerService.update(customer);
		return "updateSuccess";
	}
	
	public String findAllCustomer() throws Exception{
		List<Customer> list = customerService.findAll();
		//将list装JSON;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"linkMans", "baseDictSource", "baseDictIndustry", "baseDictLevel"});
		//转成JSON
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
		return NONE;
	}
}
