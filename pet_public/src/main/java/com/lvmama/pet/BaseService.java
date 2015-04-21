/**
 * 
 */
package com.lvmama.pet;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.pet.pub.dao.ComLogDAO;

/**
 * service基类，公共操作方法
 * @author yangbin
 *
 */
public abstract class BaseService {

	@Autowired
	protected ComLogDAO comLogDAO;
	
	/**
	 * @param objectType
	 *            对象类型.
	 * @param parentId
	 *            parentId.
	 * @param objectId
	 *            对象ID.
	 * @param operatorName
	 *            操作者名字.
	 * @param logType
	 *            日志类型.
	 * @param logName
	 *            日志表名字.
	 * @param content
	 *            日志内容.
	 * @param parentType
	 *            parentType.
	 * @return list 日志的列表.
	 * @return
	 */
	protected void insertLog(String objectType, Long parentId, Long objectId,
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
		comLogDAO.insert(log);
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
	
	
	
}
