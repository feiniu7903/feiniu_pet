package com.lvmama.ebk.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.eplace.EbkUserTarget;
import com.lvmama.comm.bee.service.eplace.EbkUserTargetService;
import com.lvmama.ebk.dao.EbkUserTargetDAO;

/**
 * EbkUserMeta 的基本的业务流程逻辑的接口
 * 
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class EbkUserTargetServiceImpl implements EbkUserTargetService {
	private EbkUserTargetDAO ebkUserTargetDAO;

	@Override
	public Long insert(EbkUserTarget ebkUserTarget) {
		return ebkUserTargetDAO.insert(ebkUserTarget);
	}

	
	public void delete(EbkUserTarget ebkUserTarget) {
		ebkUserTargetDAO.delete(ebkUserTarget);
	}


	@Override
	public List<EbkUserTarget> getEbkUserTargetList(Map<String, Object> params) {
		return ebkUserTargetDAO.getEbkUserTargetList(params);
	}


	public EbkUserTargetDAO getEbkUserTargetDAO() {
		return ebkUserTargetDAO;
	}


	public void setEbkUserTargetDAO(EbkUserTargetDAO ebkUserTargetDAO) {
		this.ebkUserTargetDAO = ebkUserTargetDAO;
	}
}
