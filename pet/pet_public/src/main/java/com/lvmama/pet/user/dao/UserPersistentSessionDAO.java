/**
 * 
 */
package com.lvmama.pet.user.dao;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserPersistentSession;

/**
 * 持久化SESSION DAO（目前移动客户端用户会使用）
 * @author liuyi
 *
 */
public class UserPersistentSessionDAO extends BaseIbatisDAO {

    /**
     * 日志打印
     */
	private static final Log LOG = LogFactory.getLog(UserPersistentSessionDAO.class);
	
	public void insert(UserPersistentSession userPersistentSession){
		super.insert("USER_PERSISTENT_SESSION.insert", userPersistentSession);
	}
	
	
	public void update(UserPersistentSession userPersistentSession){
		super.insert("USER_PERSISTENT_SESSION.update", userPersistentSession);
	}
	
	public UserPersistentSession select(Map<String, Object> map){
		return (UserPersistentSession)super.queryForObject("USER_PERSISTENT_SESSION.query", map);
	}
}
