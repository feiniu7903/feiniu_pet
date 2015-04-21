/**
 * 
 */
package com.lvmama.back.sweb.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;

/**
 * 显示日志使用,ajax操作
 * @author yangbin
 *
 */
@Results({ 
	@Result(name="log",location="/WEB-INF/pages/back/log/log_page.jsp") 
})
public class LogAction extends BaseAction{

	/**
	 * 日志service.
	 */
	private ComLogService comLogService;
	/**
	 * 对象查看的类型.例:订单表ORD_ORDER
	 */
	private String objectType;
	/**
	 * 日志查看对像编号.例：订单编号.
	 */
	private Long objectId;
	/**
	 * 父类编号.
	 */
	private Long parentId;
	
	/**
	 * 
	 */
	private String parentType;
	
	private String logType;
	/**
	 * 日志结果集.
	 */
	private List<ComLog> comLogList;

	private Map paramMap=new HashMap();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1102496921821767344L;
	
	
	@Action("/log/loadLogs")
	public String getPage(){
		boolean flag=true;
		if(parentId!=null&&StringUtils.isNotEmpty(parentType)&&StringUtils.isNotEmpty(objectType)){
			paramMap.put("parentId", parentId);
			paramMap.put("parentId",parentId);
			paramMap.put("parentType",parentType);
			paramMap.put("objectType",objectType);
			initPagination();
			pagination.setTotalRecords(this.comLogService.queryByParentIdAndObjectTypeMapCount(paramMap));
			initPageSize();
			comLogList = comLogService.queryByParentIdAndObjectTypeMap(paramMap);
		}else if(StringUtils.isNotEmpty(parentType)&&parentId!=null){
			paramMap.put("objectId",parentId);
			paramMap.put("objectType",parentType);
			initPagination();
			pagination.setTotalRecords(this.comLogService.queryByParentIdMapCount(paramMap));
			initPageSize();
			comLogList = comLogService.queryByParentIdMap(paramMap);
		}else if(StringUtils.isNotEmpty(objectType)&&objectId!=null&&StringUtils.isNotEmpty(logType)){
			paramMap.put("objectId",objectId);
			paramMap.put("objectType",objectType);
			paramMap.put("logType",logType);
			initPagination();
			pagination.setTotalRecords(this.comLogService.queryByObjectIdMapCount(paramMap));
			initPageSize();
			comLogList = comLogService.queryByObjectIdMap(paramMap);
		}else if(StringUtils.isNotEmpty(objectType)&&objectId!=null){
			paramMap.put("objectId",objectId);
			paramMap.put("objectType",objectType);
			initPagination();
			pagination.setTotalRecords(this.comLogService.queryByObjectIdMapCount(paramMap));
			initPageSize();
			comLogList = comLogService.queryByObjectIdMap(paramMap);
		}else{
			flag=false;
		}
		
		if(flag){
			pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		}
		
		return "log";
	}

	private void initPageSize() {
		paramMap.put("skipResults",pagination.getFirstRow()-1);
		paramMap.put("maxResults",pagination.getLastRow());
	}

	/**
	 * @return the comLogList
	 */
	public List<ComLog> getComLogList() {
		return comLogList;
	}

	/**
	 * @param comLogService the comLogService to set
	 */
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @param parentType the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	/**
	 * @param paramMap the paramMap to set
	 */
	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	
}
