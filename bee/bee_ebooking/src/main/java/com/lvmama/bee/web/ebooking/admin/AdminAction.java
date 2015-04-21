package com.lvmama.bee.web.ebooking.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupplierService;

/**
 * lvmama管理员登录后的首页
 * @author zhangkexing
 *
 */
@Results(value={
		@Result(name="index",location="/WEB-INF/pages/admin/index.jsp")
	})
public class AdminAction extends EbkBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2551329884683646609L;

	
	private String supplierName;
	private String supplierId; 
	
	private EbkUserService ebkUserService;
	private SupplierService supplierService;
	
	@Action("/admin/supplierList")
	public String supplierList(){
		pagination=initPage();
		pagination.setPageSize(20);
		Map<String,Object> param=buildParam();
		if(param.isEmpty()){
			return "index"; 
		}
		pagination.setTotalResultSize(supplierService.selectRowCount(param));
		if(pagination.getTotalResultSize()>0){
			param.put("_startRow", pagination.getStartRows());
			param.put("_endRow", pagination.getEndRows());
			List<SupSupplier> list=supplierService.getSupSuppliers(param);
			for(SupSupplier ss:list){
				if(ss.getParentId()!=null){
					ss.setParentSupplier(supplierService.getSupplier(ss.getParentId()));
				}
			}
			pagination.setItems(list);
		}
		pagination.buildUrl(getRequest());
		
		return "index";
	}
	
	@Action("/admin/userCheck")
	public void hasEbkUser(){
		String sid = getRequest().getParameter("sid");
		if(StringUtils.isEmpty(sid)){
			sendAjaxMsg("0");
			return;
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("supplierId", sid);
		List<EbkUser> ebkuserList = ebkUserService.getEbkUser(params);
		
		EbkUser loginuser = null;
		for(EbkUser ebkuser : ebkuserList){
			if("true".equals(ebkuser.getIsAdmin())){
				loginuser = ebkuser;
				break;
			}
		}
		if(loginuser == null){
			for(EbkUser ebkuser : ebkuserList){
				if(ebkuser.getParentUserId() != null ){
					loginuser = ebkUserService.getEbkUserById(ebkuser.getParentUserId());
					break;
				}
			}
		}
		
		if(loginuser!=null){
			sendAjaxMsg("1");
		}else{
			sendAjaxMsg("0");
		}
	}
	
	private Map<String,Object> buildParam(){
		Map<String,Object> map=new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(supplierName)){
			map.put("supplierName", supplierName);			
		}
		if(StringUtils.isNotEmpty(supplierId)){
			map.put("supplierId", supplierId);
		}
		
		return map;
	}
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public SupplierService getSupplierService() {
		return supplierService;
	}
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public EbkUserService getEbkUserService() {
		return ebkUserService;
	}
	public void setEbkUserService(EbkUserService ebkUserService) {
		this.ebkUserService = ebkUserService;
	}
	
}
