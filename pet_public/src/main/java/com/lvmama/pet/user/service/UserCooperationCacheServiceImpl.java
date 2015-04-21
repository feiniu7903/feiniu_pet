/**
 * 
 */
package com.lvmama.pet.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.user.UserCooperationCache;
import com.lvmama.comm.pet.service.user.UserCooperationCacheService;
import com.lvmama.pet.user.dao.UserCooperationCacheDAO;

/**
 * 联合登陆cache接口
 * @author liuyi
 *
 */
public class UserCooperationCacheServiceImpl implements UserCooperationCacheService {
	
	private UserCooperationCacheDAO  userCooperationCacheDAO;
	
	@Override
	public void insert(final UserCooperationCache userCooperationCache){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cooperationType", userCooperationCache.getCooperationType());
		parameters.put("cooperationUserAccount", userCooperationCache.getCooperationUserAccount());
		userCooperationCacheDAO.delete(parameters);
		userCooperationCacheDAO.insert(userCooperationCache);;
	}
	
	@Override
	public UserCooperationCache queryLatestCache(final Map<String, Object> parameters){
		List<UserCooperationCache>  userCooperationCacheList = userCooperationCacheDAO.query(parameters);
		if(userCooperationCacheList != null && userCooperationCacheList.size() > 0){
			return userCooperationCacheList.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public void delete(final Map<String, Object> parameters){
		userCooperationCacheDAO.delete(parameters);
	}

	public void setUserCooperationCacheDAO(
			UserCooperationCacheDAO userCooperationCacheDAO) {
		this.userCooperationCacheDAO = userCooperationCacheDAO;
	}
}
