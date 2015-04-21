package com.lvmama.order.service;

public interface OrderLogService {

	void insertLog(Long objectId, String objectType, Long parentId, String parentType, String operatorName, String logName, String logType, String content);
	
}
