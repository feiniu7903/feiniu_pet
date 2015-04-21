package com.lvmama.pet.user.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserCooperationUser;


/**
 * 第三方合作商用户和驴妈妈用户的对应表的数据库操作接口实现类
 *
 */
public class UserCooperationUserDAO extends BaseIbatisDAO {
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(UserCooperationUserDAO.class);

	/**
	 * 保存第三方联合登录的用户信息
	 * @param userCooperationUsers 第三方联合登录的用户信息
	 */
	public void save(final UserCooperationUser userCooperationUsers) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("尝试保存" + userCooperationUsers);
		}
		super.insert("USER_COOPERATION_USER.insert",
				userCooperationUsers);
		if (LOG.isDebugEnabled()) {
			LOG.debug("保存" + userCooperationUsers + "成功!");
		}
	}

	/**
	 * 更新第三方联合登录的用户信息
	 * @param cooperationUsers 第三方联合登录的用户信息
	 */
	public void update(final UserCooperationUser userCooperationUsers) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("尝试更新" + userCooperationUsers);
		}
		super.update("USER_COOPERATION_USER.update",
				userCooperationUsers);
		if (LOG.isDebugEnabled()) {
			LOG.debug("更新" + userCooperationUsers + "成功!");
		}
	}

	/**
	 * 根据主键查找第三方联合登录用户信息
	 * @param id 主键
	 * @return 第三方联合登录的用户信息
	 */
	public UserCooperationUser getObjectByPrimaryKey(final Serializable id) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("尝试查找id = " + id + "的记录");
		}
		Object o = super.queryForObject(
				"USER_COOPERATION_USER.queryByPk", id);
		if (null == o || !(o instanceof UserCooperationUser)) {
			LOG.warn("id=" + id + "的记录未找到或者不能成功转换成目标类，直接抛弃");
			return null;
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("发现纪录" + o);
			}
			return (UserCooperationUser) o;
		}

	}

	/**
	 * 根据查询条件查询第三方联合登录用户信息
	 * @param parameters 参数列表
	 * @return 第三方联合登录用户信息的集合
	 */
	@SuppressWarnings("unchecked")
	public List<UserCooperationUser> getList(final Map<String, Object> parameters) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("尝试查找parameters = " + parameters + "的记录");
		}	
		List<UserCooperationUser> list = super.queryForList(
				"USER_COOPERATION_USER.query", parameters);
		if (null == list || list.size() == 0) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("未能找到相应的记录。条件:" + parameters);
			}
			return null;
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("找到了" + list.size() + "条记录。条件:" + parameters);
			}
		}
		return list;
	}
}
