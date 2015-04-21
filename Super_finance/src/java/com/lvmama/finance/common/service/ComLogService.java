package com.lvmama.finance.common.service;

import java.util.List;

import com.lvmama.finance.common.ibatis.po.ComLog;
/**
 * 系统日志
 * 
 * @author yanggan
 *
 */
public interface ComLogService {
	
	public List<ComLog> searchLog(Long objectId,String objectType);
	
}
