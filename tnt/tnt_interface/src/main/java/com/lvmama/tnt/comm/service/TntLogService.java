package com.lvmama.tnt.comm.service;

import com.lvmama.comm.pet.po.pub.ComLog;

public interface TntLogService {

	public void save(String objectType, Long parentId, Long objectId, String operatorName,
			String logType, String logName, String content, String parentType);
	public void save(ComLog comlog);
}
