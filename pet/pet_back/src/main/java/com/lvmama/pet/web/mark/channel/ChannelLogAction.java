package com.lvmama.pet.web.mark.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.pet.web.BaseAction;

public class ChannelLogAction extends BaseAction{
	private Map<String, Object> map = new HashMap<String, Object>();
	
	private List<ComLog> comLogList = new ArrayList<ComLog>();
	
	private ComLogService comLogService;  
	private String firstId; // 一级渠道ID
	private String secondId;// 二级渠道ID
	private String threeId; // 三级渠道ID
	
	/**
	 *查询渠道日志
	 */
	protected void doBefore(){
		List<String> list = new ArrayList<String>();
		list.add(firstId);
		list.add(secondId);
		list.add(threeId);
		map.put("keyList", list);	
		map.put("objectType", "MARK_CHANNEL");
		comLogList= comLogService.queryListAll(map);
	} 


	public Map<String, Object> getMap() {
		return map;
	}


	public void setMap(Map<String, Object> map) {
		this.map = map;
	}


	public List<ComLog> getComLogList() {
		return comLogList;
	}


	public void setComLogList(List<ComLog> comLogList) {
		this.comLogList = comLogList;
	}


	public ComLogService getComLogService() {
		return comLogService;
	}


	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}


	public String getFirstId() {
		return firstId;
	}


	public void setFirstId(String firstId) {
		this.firstId = firstId;
	}


	public String getSecondId() {
		return secondId;
	}


	public void setSecondId(String secondId) {
		this.secondId = secondId;
	}


	public String getThreeId() {
		return threeId;
	}


	public void setThreeId(String threeId) {
		this.threeId = threeId;
	}



	
	
	
}
