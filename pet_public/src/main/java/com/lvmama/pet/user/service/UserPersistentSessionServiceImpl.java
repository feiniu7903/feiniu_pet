/**
 * 
 */
package com.lvmama.pet.user.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.user.UserPersistentSession;
import com.lvmama.comm.pet.service.user.UserPersistentSessionService;
import com.lvmama.pet.user.dao.UserPersistentSessionDAO;

/**
 * 持久化SESSION SERVICE（目前移动客户端用户会使用）
 * @author liuyi
 *
 */
public class UserPersistentSessionServiceImpl implements
		UserPersistentSessionService {
	
	@Autowired
	private UserPersistentSessionDAO userPersistentSessionDAO;

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.user.UserPersistentSessionService#insert(com.lvmama.comm.pet.po.user.UserPersistentSession)
	 */
	@Override
	public UserPersistentSession insert(UserPersistentSession userPersistentSession) {
		userPersistentSessionDAO.insert(userPersistentSession);
		return userPersistentSession;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.user.UserPersistentSessionService#update(com.lvmama.comm.pet.po.user.UserPersistentSession)
	 */
	@Override
	public void update(UserPersistentSession userPersistentSession) {
		userPersistentSessionDAO.update(userPersistentSession);
	}
	
	@Override
	public UserPersistentSession selectBySessionKey(String sessionKey){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sessionKey", sessionKey);
		return userPersistentSessionDAO.select(map);
	}
}
