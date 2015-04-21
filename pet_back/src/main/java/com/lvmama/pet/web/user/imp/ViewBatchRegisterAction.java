package com.lvmama.pet.web.user.imp;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Paging;

import com.lvmama.comm.pet.po.user.UserBatchRegister;
import com.lvmama.comm.pet.po.user.UserBatchUser;
import com.lvmama.comm.pet.service.user.UserBatchRegisterService;
import com.lvmama.comm.pet.service.user.UserBatchUserService;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.web.BaseAction;

public class ViewBatchRegisterAction extends BaseAction {
	private UserBatchUserService userBatchUserService;
	private UserBatchRegisterService userBatchRegisterService;
	
	private List<UserBatchUser> userList;
	private String batchId;					//批次ID
	private String mobileNumber;			//手机
	private Map searchConds = new HashMap();
	private UserBatchRegister batchRegister;
	
	public void doBefore(){
		if (null != batchId && !"".equals(batchId.trim())) {
			batchRegister = userBatchRegisterService.getBatchRegisterByPk(Long.parseLong(batchId));
		}
	}
	
	public void doAfter() {
		search();
	}
	public void search(){
		if(null != batchRegister && null != batchRegister.getBatchId()){
			searchConds.put("batchRegisterId", batchId);
		}
		
		Long totalRowCount = userBatchUserService.count(searchConds);

		((Label)this.getComponent().getFellow("_totalRowCountLabel")).setValue(totalRowCount.toString()); 
		Paging paging = (Paging)this.getComponent().getFellow("_paging");
		paging.setTotalSize(totalRowCount.intValue());
		
		searchConds.put("skipResults", paging.getActivePage()*paging.getPageSize()+1);
		searchConds.put("maxResults", paging.getActivePage()*paging.getPageSize()+paging.getPageSize());
		userList=userBatchUserService.query(searchConds);
	}
	
	/**
	 * 导出报表
	 */
	public void export() {
		Map _s = new HashMap();
		_s.putAll(searchConds);
		_s.remove("skipResults");
		_s.remove("maxResults");
		try {
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/batchUserTemplate.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/importUsers.xls";
			Map beans = new HashMap();
			beans.put("userList", userBatchUserService.query(searchConds));
			
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, beans, destFileName);
			File file = new File(destFileName);
			if (file != null && file.exists()) {
				Filedownload.save(file, "application/vnd.ms-excel");
			} else {
				alert("操作失败");
				return;
			}
			alert("操作成功");
		} catch (Exception e) {
			alert(e.getMessage());
		}	
	}	
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Map getSearchConds() {
		return searchConds;
	}
	public void setSearchConds(Map searchConds) {
		this.searchConds = searchConds;
	}

	public List<UserBatchUser> getUserList() {
		return userList;
	}

	public void setUserList(List<UserBatchUser> userList) {
		this.userList = userList;
	}

	public UserBatchRegister getBatchRegister() {
		return batchRegister;
	}

	public void setBatchRegister(UserBatchRegister batchRegister) {
		this.batchRegister = batchRegister;
	}

	public void setUserBatchUserService(UserBatchUserService userBatchUserService) {
		this.userBatchUserService = userBatchUserService;
	}

	public void setUserBatchRegisterService(
			UserBatchRegisterService userBatchRegisterService) {
		this.userBatchRegisterService = userBatchRegisterService;
	}

}
