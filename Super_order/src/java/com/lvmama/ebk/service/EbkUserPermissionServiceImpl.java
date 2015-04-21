package com.lvmama.ebk.service;

import java.util.List;

import com.lvmama.comm.bee.po.eplace.EbkUserPermission;
import com.lvmama.comm.bee.service.eplace.EbkUserPermissionService;
import com.lvmama.ebk.dao.EbkUserPermissionDAO;

public class EbkUserPermissionServiceImpl implements EbkUserPermissionService{
	private EbkUserPermissionDAO ebkUserPermissionDAO;
	
	public Long insert(EbkUserPermission up){
		return ebkUserPermissionDAO.insert(up);
	}

	public void update(Long userId,List<Long> permIdList){
		ebkUserPermissionDAO.deleteByUserId(userId);
		if(null != permIdList){
			for(Long permId : permIdList){
				EbkUserPermission up = new EbkUserPermission();
				up.setUserId(userId);
				up.setPermissionId(permId);
				ebkUserPermissionDAO.insert(up);
			}
		}
	}

	public EbkUserPermissionDAO getEbkUserPermissionDAO() {
		return ebkUserPermissionDAO;
	}

	public void setEbkUserPermissionDAO(EbkUserPermissionDAO ebkUserPermissionDAO) {
		this.ebkUserPermissionDAO = ebkUserPermissionDAO;
	}
	
}
