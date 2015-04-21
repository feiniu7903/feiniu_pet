package com.lvmama.comm.bee.service.eplace;

import com.lvmama.comm.bee.po.eplace.EbkPerformLog;


public interface EbkPerformLogService{
	public Long insert(EbkPerformLog ebkPerformLog);

	public Integer update(EbkPerformLog ebkPerformLog);	
	
	EbkPerformLog getEbkPerformLogByUUID(String uuid);
}
