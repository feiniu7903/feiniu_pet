package com.lvmama.bee.web.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.StringUtil;
@Results({ @Result(name = "logMessage", location = "/WEB-INF/pages/ebooking/logMessage.jsp")
})
public class EbkComLogAction extends EbkBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5316983687990070633L;
	private ComLogService comLogService;
	private Long parentId;
	private Long objectId;
	private String objectType;
	private String parentType;
	private List<ComLog> logList = new ArrayList<ComLog>();
	@Action(value="/log/queryLogMessage")
	public String queryEbkComLogByobjectTypeAndObjectId(){
		Long supplierId = super.getCurrentSupplierId();
		initPage();
		Map<String,Object> paramMap =new HashMap<String,Object>();
		if(objectId!=null){
			paramMap.put("objectId", objectId);
		}
		if(!StringUtil.isEmptyString(objectType)){
			paramMap.put("objectType", objectType);
		}
		if(parentId!=null){
			paramMap.put("objectId", parentId);
		}
		if(!StringUtil.isEmptyString(parentType)){
			paramMap.put("objectType", parentType);
		}
		initPageSize(paramMap);
		if(null!=supplierId && null!=objectType && null!=objectId){
			pagination.setTotalResultSize(this.comLogService.queryByObjectIdMapCount(paramMap));
		}else if(null!=supplierId && parentId!=null && !StringUtil.isEmptyString(parentType)){
			pagination.setTotalResultSize(this.comLogService.queryByParentIdMapCount(paramMap));
		}
		
		paramMap.put("currentPage",pagination.getCurrentPage());
		paramMap.put("pageSize", pagination.getPageSize());
		paramMap.put("maxResults", pagination.getCurrentPage()*pagination.getPageSize());
		paramMap.put("skipResults", (pagination.getCurrentPage()-1)*pagination.getPageSize());
		if(null!=supplierId && null!=objectType && null!=objectId){
			logList = comLogService.queryByObjectIdMap(paramMap);
		}else if(null!=supplierId && parentId!=null && !StringUtil.isEmptyString(parentType)){
			logList = comLogService.queryByParentIdMap(paramMap);
		}
		pagination.buildUrl(getRequest());
		return "logMessage";
	}
	private void initPageSize(Map<String,Object> paramMap) {
		paramMap.put("skipResults",pagination.getStartRows()-1);
		paramMap.put("maxResults",pagination.getEndRows());
	}
	public ComLogService getComLogService() {
		return comLogService;
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getParentType() {
		return parentType;
	}
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
	public List<ComLog> getLogList() {
		return logList;
	}
	public void setLogList(List<ComLog> logList) {
		this.logList = logList;
	}
	
}
