package com.lvmama.back.utils;

import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;

public class ComLogAction extends BaseAction{
	
	String objectType;
	Long  objectId;
	private ComLogService comLogService;
	private List<ComLog> logs;
	
	protected void doBefore() throws Exception {
		logs = comLogService.queryByObjectId(objectType, objectId);
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	public List<ComLog> getLogs() {
		return logs;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
}
