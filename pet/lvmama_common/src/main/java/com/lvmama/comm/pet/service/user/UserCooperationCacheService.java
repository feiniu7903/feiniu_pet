/**
 * 
 */
package com.lvmama.comm.pet.service.user;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.user.UserCooperationCache;

/**
 * 联合登陆cache接口
 * @author liuyi
 *
 */
public interface UserCooperationCacheService {
	
	public void insert(final UserCooperationCache userCooperationCache);
	
	public UserCooperationCache queryLatestCache(final Map<String, Object> parameters);
	
	public void delete(final Map<String, Object> parameters);
}
