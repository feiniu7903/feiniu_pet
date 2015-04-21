package com.lvmama.clutter.utils;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;

public class EbkUserUtils {
	
	public static EbkUser availableUser(EbkUserService ebkUserService,long userId) {
		EbkUser u = ebkUserService.getEbkUserById(userId);
		EbkUser parent = null;
		if (u != null && "false".equals(u.getIsAdmin())) {
			parent = ebkUserService.getEbkUserById(u.getParentUserId());
		}

		boolean available = !(	u == null || 
								"false".equals(u.getValid()) || 
								("false".equals(u.getIsAdmin()) && (parent == null || "false".equals(parent.getValid())))
							);
		if(available){
			return u;
		}else{
			return null;
		}
	}
	
	public static EbkUser availableUser(EbkUserService ebkUserService,String userName,String password) {
		EbkUser u = ebkUserService.getEbkUserByUserName(userName);
		EbkUser parent = null;
		if (u != null && "false".equals(u.getIsAdmin())) {
			parent = ebkUserService.getEbkUserById(u.getParentUserId());
		}
		boolean available =  !(u == null
								|| "false".equals(u.getValid())
								|| StringUtil.isEmptyString(password)
								|| !u.getPassword().equals(MD5.code(password, MD5.KEY_EBK_USER_PASSWORD))
								|| ("false".equals(u.getIsAdmin()) && (parent == null || "false".equals(parent.getValid()))));
		if(available){
			return u;
		}else{
			return null;
		}
	}
	
	public static boolean hasBeenBindingToDevice(EbkUserService ebkUserService ,EbkUser u, String udid){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", u.getUserId());
		params.put("udid", udid);
		params.put("supplierId", u.getSupplierId());
		EbkUser user =  ebkUserService.getEbkUserWithTargetAndDeviceForEplace(params);
		return user!=null;
	}
}
