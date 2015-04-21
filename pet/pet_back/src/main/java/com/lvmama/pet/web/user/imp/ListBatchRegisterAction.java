package com.lvmama.pet.web.user.imp;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Filedownload;

import com.lvmama.comm.pet.po.user.UserBatchRegister;
import com.lvmama.comm.pet.service.user.UserBatchRegisterService;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;

public class ListBatchRegisterAction extends UserChannelBaseAction {
	private static final long serialVersionUID = -7655463061183973649L;

	private UserBatchRegisterService userBatchRegisterService;
	
	private List<UserBatchRegister> userBatchRegisterList;				
					
	/**
	 * 查询批次列表
	 */
	@SuppressWarnings("unchecked")
	public void search(){
		searchConds=initialPageInfoByMap(userBatchRegisterService.count(searchConds), searchConds);
		if (null != searchConds.get("_startRow")) {
			searchConds.put("skipResults", searchConds.get("_startRow"));
		}
		if (null != searchConds.get("_endRow")) {
			searchConds.put("maxResults", searchConds.get("_endRow"));
		}
		userBatchRegisterList=userBatchRegisterService.query(searchConds);
	}
	
	/**
	 * 导出报表
	 */
	public void report() {
		Map _s = new HashMap();
		_s.putAll(searchConds);
		_s.remove("skipResults");
		_s.remove("maxResults");
		try {
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/batchRegisterTemplate.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/importUsers.xls";
			Map beans = new HashMap();
			beans.put("batchRegister", userBatchRegisterService.query(_s));
			
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
	
	/**
	 * 设置注册类型
	 * @param value
	 */
	public void setRegisterType(String value) {
		if (StringUtils.isEmpty(value)) {
			searchConds.remove("registerType");
		} else {
			searchConds.put("registerType", value);
		}
	}

	public List<UserBatchRegister> getUserBatchRegisterList() {
		return userBatchRegisterList;
	}

	public void setUserBatchRegisterService(
			UserBatchRegisterService userBatchRegisterService) {
		this.userBatchRegisterService = userBatchRegisterService;
	}

}
