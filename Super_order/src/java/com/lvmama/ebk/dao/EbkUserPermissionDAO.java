package com.lvmama.ebk.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.eplace.EbkUserPermission;

public class EbkUserPermissionDAO extends BaseIbatisDAO{
	public Long insert(EbkUserPermission up){
		return (Long)super.insert("EBK_USER_PERMISSION.insert",up);
	}
	/**
	 * 删除用户绑定的权限
	 */
	public void deleteByUserId(Long userId){
		super.delete("EBK_USER_PERMISSION.deleteByUserId",userId);
	}
}
