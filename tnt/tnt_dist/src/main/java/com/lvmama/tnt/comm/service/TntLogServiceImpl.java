package com.lvmama.tnt.comm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;

@Repository("tntLogService")
public class TntLogServiceImpl implements TntLogService {

	@Autowired
	private ComLogService comLogService;
	
	@Override
	public void save(String objectType, Long parentId, Long objectId,
			String operatorName, String logType, String logName,
			String content, String parentType) {
		ComLog log = new ComLog();
		log.setParentId(parentId);
		log.setParentType(parentType);
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		log.setContent(content);
		comLogService.addComLog(log);

	}

	@Override
	public void save(ComLog comlog) {
		comLogService.addComLog(comlog);

	}

}
