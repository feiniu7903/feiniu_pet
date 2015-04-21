/**
 * 
 */
package com.lvmama.comm.pet.service.user;

import com.lvmama.comm.pet.po.user.UserPersistentSession;

/**
 * 持久化SESSION SERVICE（目前移动客户端用户会使用）
 * @author liuyi
 *
 */
public interface UserPersistentSessionService {
	
	public UserPersistentSession insert(UserPersistentSession userPersistentSession);
	
	public void update(UserPersistentSession userPersistentSession);
	
	public UserPersistentSession selectBySessionKey(String sessionKey);

}
