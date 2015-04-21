package com.lvmama.back.web.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.DateUtil;

public class ComLogAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 448280202516049213L;
	
	/**
	 * 日志接口
	 */
	private ComLogService comLogService;
	
	/**
	 * 内容
	 */
	private List<ComLog> logList=new ArrayList<ComLog>();
	
	private List<CodeItem> objectTypes=new ArrayList<CodeItem>();
	
	/**
	 * 参数列表
	 */
	private Map<String,Object> params=new HashMap<String, Object>();
	
	
	/* (non-Javadoc)
	 * @see com.lvmama.back.web.BaseAction#doBefore()
	 */
	@Override
	protected void doBefore() throws Exception {
		// TODO Auto-generated method stub
		super.doBefore();
		objectTypes=CodeSet.getInstance().getCodeListAndBlank("COM_LOG_OBJECT_TYPE");
	}


	/**
	 * 搜索按钮
	 */
	public void search(){
	
		if(params.containsKey("beginTime")){
			params.put("beginTime", DateUtil.getDayStart((Date)params.get("beginTime")));
		}
		
		if(params.containsKey("endTime")){
			params.put("endTime", DateUtil.getDayEnd((Date)params.get("endTime")));
		}
	
		if(params.containsKey("objectType")){
			try{
				CodeItem ci=(CodeItem)params.get("objectType");
				params.put("objectType", ci.getCode());
			}catch(Exception ex){
				
			}
		}
		
		initialPageInfoByMap(comLogService.queryCountByMap(params),params);	
		
		logList=comLogService.queryByMap(params);
	}
	
	
	/**
	 * @param comLogService the comLogService to set
	 */
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}


	/**
	 * @return the logList
	 */
	public List<ComLog> getLogList() {
		return logList;
	}


	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}


	/**
	 * @return the comLogService
	 */
	public ComLogService getComLogService() {
		return comLogService;
	}


	/**
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}


	/**
	 * @param logList the logList to set
	 */
	public void setLogList(List<ComLog> logList) {
		this.logList = logList;
	}


	/**
	 * @return the objectTypes
	 */
	public List<CodeItem> getObjectTypes() {
		return objectTypes;
	}


	
	
}
