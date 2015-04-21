package com.lvmama.pet.user.dao;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserCooperationCache;

/**
 * 用户联合登陆CACHE
 * @author liuyi
 *
 */
public class UserCooperationCacheDAO extends BaseIbatisDAO {
    /**
     * 日志打印
     */
	private static final Log LOG = LogFactory.getLog(UserCooperationCacheDAO.class);

	/**
	 * 插入或更新用户的激活记录
	 * @param userCooperationCache
	 */
	public void insert(final UserCooperationCache userCooperationCache) {
		super.insert("USER_COOPERATION_CACHE.insert", userCooperationCache);

	}


	/**
	 * 查询用户联合登陆CACHE
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserCooperationCache> query(final Map<String, Object> parameters) {
		return super.queryForList("USER_COOPERATION_CACHE.query", parameters);
	}	

	/**
	 * 删除联合登陆CACHE
	 * @param parameters
	 */
	public void delete(final Map<String, Object> parameters)
	{
		super.delete("USER_COOPERATION_CACHE.delete", parameters);		
	}
}
