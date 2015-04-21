package com.lvmama.back.web.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Label;
import org.zkoss.zul.Paging;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;

/**
 * 跳转显示日志详情.
 * 
 * @author huangl
 */
@SuppressWarnings("unchecked")
public class ViewSuperLogAction  extends BaseAction{
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
	/**
	 * 日志结果集.
	 */
	private List<ComLog> comLogList;
	/**
	 * 分页Label
	 */
	protected Label _totalRowLabel;
	/**
	 * 分页组件
	 */
	protected Paging _page;
	private ComLog comLog=new ComLog();
	private Map paramMap=new HashMap();
	public void doAfter(){
		if (parentId!=null && parentType!=null && objectType!=null){
			paramMap.put("parentId",parentId);
			paramMap.put("parentType",parentType);
			paramMap.put("objectType",objectType);
			initPageInfoByMap(this.comLogService.queryByParentIdAndObjectTypeMapCount(paramMap),paramMap);
			initPageSize();
			comLogList = comLogService.queryByParentIdAndObjectTypeMap(paramMap);
			comLog.setParentId(parentId);
			return;
		}
		
		if(parentType!=null && parentId!=null){
			paramMap.put("objectId",parentId);
			paramMap.put("objectType",parentType);
			initPageInfoByMap(this.comLogService.queryByParentIdMapCount(paramMap),paramMap);
			initPageSize();
			comLogList = comLogService.queryByParentIdMap(paramMap);
			comLog.setParentId(parentId);
			return;
		}
		
		if (objectType!=null && objectId!=null) {
			paramMap.put("objectId",objectId);
			paramMap.put("objectType",objectType);
			initPageInfoByMap(this.comLogService.queryByObjectIdMapCount(paramMap),paramMap);
			initPageSize();
			comLogList = comLogService.queryByObjectIdMap(paramMap);
			comLog.setParentId(parentId);
			return;
		}
	
	}

	private Map initPageInfoByMap(Long totalRowCount,Map map){
		_totalRowLabel.setValue(totalRowCount.toString());
		_page.setTotalSize(totalRowCount.intValue());
		map.put("skipResults", _page.getActivePage()*_page.getPageSize());
		map.put("maxResults", _page.getActivePage()*_page.getPageSize()+_page.getPageSize());
		return map;
	}

	private void initPageSize() {
		int skipResults=0;
		int maxResults=10;
		if(paramMap.get("skipResults")!=null){
			skipResults=Integer.parseInt(paramMap.get("skipResults").toString());
		}
		if(paramMap.get("maxResults")!=null){
			maxResults=Integer.parseInt(paramMap.get("maxResults").toString());
		}
		paramMap.put("skipResults",skipResults);
		paramMap.put("maxResults",maxResults);
	}

	public String getParentType() {
		return parentType;
	}


	public void setParentType(String parentType) {
		this.parentType = parentType;
	}


	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<ComLog> getComLogList() {
		return comLogList;
	}

	public void setComLogList(List<ComLog> comLogList) {
		this.comLogList = comLogList;
	}
	public String getObjectType() {
		return objectType;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public ComLog getComLog() {
		return comLog;
	}

	public void setComLog(ComLog comLog) {
		this.comLog = comLog;
	}


	public Map getParamMap() {
		return paramMap;
	}


	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

	public Label get_totalRowLabel() {
		return _totalRowLabel;
	}

	public void set_totalRowLabel(Label totalRowLabel) {
		_totalRowLabel = totalRowLabel;
	}

	public Paging get_page() {
		return _page;
	}

	public void set_page(Paging page) {
		_page = page;
	}
}
