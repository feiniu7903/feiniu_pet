package com.lvmama.order.service.impl;

import org.apache.log4j.Logger;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.order.service.OrderLogService;

public class OrderServiceImpl implements OrderLogService {
	
	protected Logger LOG = Logger.getLogger(this.getClass());
	protected ComLogDAO comLogDAO;

	public void insertLog(Long objectId, String objectType, Long parentId, String parentType, String operatorName, String logName, String logType, String content) {
		ComLog log = new ComLog();
		log.setObjectId(objectId);
		log.setObjectType(objectType);
		log.setParentId(parentId);
		log.setParentType(parentType);
		log.setOperatorName(operatorName);
		log.setLogName(logName);
		log.setLogType(logType);
		log.setContent(content);
		comLogDAO.insert(log);
	}
	
	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	public void throwException(String content, Logger logger)	{
		logger.error(content);
		throw new RuntimeException(content);
	}

}
