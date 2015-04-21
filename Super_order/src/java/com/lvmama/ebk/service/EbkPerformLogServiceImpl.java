package com.lvmama.ebk.service;

import com.lvmama.comm.bee.po.eplace.EbkPerformLog;
import com.lvmama.comm.bee.service.eplace.EbkPerformLogService;
import com.lvmama.ebk.dao.EbkPerformLogDAO;

public class EbkPerformLogServiceImpl implements EbkPerformLogService {

	private EbkPerformLogDAO ebkPerformLogDAO;
	@Override
	public Long insert(EbkPerformLog ebkPerformLog) {
		return ebkPerformLogDAO.insert(ebkPerformLog);
	}

	@Override
	public Integer update(EbkPerformLog ebkPerformLog) {
		return ebkPerformLogDAO.update(ebkPerformLog);
	}

	
	
	

	@Override
	public EbkPerformLog getEbkPerformLogByUUID(String uuid) {
		return ebkPerformLogDAO.getEbkPerformLogByUUID(uuid);
	}



	public EbkPerformLogDAO getEbkPerformLogDAO() {
		return ebkPerformLogDAO;
	}
	public void setEbkPerformLogDAO(EbkPerformLogDAO ebkPerformLogDAO) {
		this.ebkPerformLogDAO = ebkPerformLogDAO;
	}

}
