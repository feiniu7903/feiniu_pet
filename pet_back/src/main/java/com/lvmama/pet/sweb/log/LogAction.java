/**
 * 
 */
package com.lvmama.pet.sweb.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.LOG_STORAGE;

/**
 * 显示日志使用,ajax操作
 * @author yangbin
 *
 */
@Results({ 
	@Result(name="log",location="/WEB-INF/pages/back/log/log_page.jsp"),
	@Result(name="logStorageCard",location="/WEB-INF/pages/back/lvmamacard/logStorageCardPre.jsp")
})
public class LogAction extends BackBaseAction{

	/**
	 * 日志service.
	 */
	private ComLogService comLogRemoteService;
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
	/**
	 * 日志结果集.
	 */
	private List<ComLog> comLogList;
	private String operatorName;
	private String logType;

	private Map paramMap=new HashMap();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1102496921821767344L;
	
	
	@Action("/log/loadLogs")
	public String loadPage(){
		boolean flag=true;
		if(parentId!=null&&StringUtils.isNotEmpty(parentType)&&StringUtils.isNotEmpty(objectType)){
			paramMap.put("parentId",parentId);
			paramMap.put("parentType",parentType);
			paramMap.put("objectType",objectType);
			initPage();
			pagination.setTotalResultSize(this.comLogRemoteService.queryByParentIdAndObjectTypeMapCount(paramMap));
			initPageSize();
			comLogList = comLogRemoteService.queryByParentIdAndObjectTypeMap(paramMap);
		}else if(StringUtils.isNotEmpty(parentType)&&parentId!=null){
			paramMap.put("objectId",parentId);
			paramMap.put("objectType",parentType);
			initPage();
			pagination.setTotalResultSize(this.comLogRemoteService.queryByParentIdMapCount(paramMap));
			initPageSize();
			comLogList = comLogRemoteService.queryByParentIdMap(paramMap);
		}else if(StringUtils.isNotEmpty(objectType)&&objectId!=null){
			paramMap.put("objectId",objectId);
			paramMap.put("objectType",objectType);
			initPage();
			pagination.setTotalResultSize(this.comLogRemoteService.queryByObjectIdMapCount(paramMap));
			initPageSize();
			comLogList = comLogRemoteService.queryByObjectIdMap(paramMap);
		}else{
			flag=false;
		}
		
		if(flag){
			pagination.setUrl(WebUtils.getUrl(getRequest()));
		}
		
		return "log";
	}
	
	@Action("/log/logStorageCard")
	public String logStorageCard(){
		paramMap.put("logType", Constant.COM_LOG_OBJECT_TYPE.INSTOREDCARD.getCode());
		if(this.logType!=null){ 
			paramMap.put("logType", this.logType);
		}
		if(this.operatorName != null){
			paramMap.put("operatorName", this.operatorName);
		}
		initPreTagsResPage();
		comLogList = this.comLogRemoteService.queryByMap(paramMap);
		return "logStorageCard";
	}
	private void initPreTagsResPage(){
        pagination=initPage();
        pagination.setPageSize(10);
        pagination.setTotalResultSize(this.comLogRemoteService.queryCountByMap(paramMap));
        if(pagination.getTotalResultSize()>0){
            paramMap.put("startRows", pagination.getStartRows());
            paramMap.put("endRows", pagination.getEndRows());
            paramMap.put("skipResults", pagination.getStartRows()-1);
            paramMap.put("maxResults", pagination.getEndRows());
        }
        pagination.buildUrl(getRequest());
    }

	private void initPageSize() {
		paramMap.put("skipResults",pagination.getStartRows()-1);
		paramMap.put("maxResults",pagination.getEndRows());
	}

	/**
	 * @return the comLogList
	 */
	public List<ComLog> getComLogList() {
		return comLogList;
	}


	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
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

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getLogType() {
		return logType;
	}
	
	public Map<String,String> getLogStatusList(){
		return LOG_STORAGE.getMap();
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}
}
