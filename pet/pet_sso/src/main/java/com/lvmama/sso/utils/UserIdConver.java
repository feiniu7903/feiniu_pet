/**
 * 
 */
package com.lvmama.sso.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;

/**
 * @author yangbin
 *
 */
public abstract class UserIdConver {

	public static Long converNoToId(UserUserProxy userUserProxy,final String userId){
		Long id=null;
		if(StringUtils.isNotEmpty(userId)){
			if(userId.length()==32){
				UserUser user=userUserProxy.getUserUserByUserNo(userId);
				if(user!=null){
					id=user.getId();
				}
			}else{
				id = NumberUtils.toLong(userId);
			}
		}
		return id;
	}
}
