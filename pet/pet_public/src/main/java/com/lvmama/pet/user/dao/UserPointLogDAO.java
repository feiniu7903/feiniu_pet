package com.lvmama.pet.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserPointLog;
import com.lvmama.comm.pet.vo.UserPointLogWithDescription;


/**
 * 用户积分日志数据库操作实现类
 * @author Brian
 *
 */
public class UserPointLogDAO extends BaseIbatisDAO {
	/**
	 * 日志记录器
	 */
	private static final Log LOG = LogFactory.getLog(UserPointLogDAO.class);

	/**
	 * 插入用户积分记录
	 * @param userPointLog 积分记录
	 */
	public void insert(final UserPointLog userPointLog) {
		super.insert("USER_POINT_LOG.insert", userPointLog);
		if (LOG.isDebugEnabled()) {
			LOG.debug("成功插入用户积分记录" + userPointLog);
		}
	}

	/**
	 * 根据条件，统计积分总数
	 * @param parameters 查询条件
	 * @return 积分总数
	 */
	public Long getSumUserPoint(final Map<String, Object> parameters) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("查询：" + parameters + "的积分总和");
		}
		return (Long) super.queryForObject("USER_POINT_LOG.sumPoint", parameters);
	}

	@SuppressWarnings("unchecked")
	public List<UserPointLogWithDescription> getUserPointLog(final Map<String, Object> parameters) {
		return  (List<UserPointLogWithDescription>)
				super.queryForList("USER_POINT_LOG.queryUserPointLogWithDesc", parameters);

	}

	/**
	 * 根据查询条件，对积分日志计数
	 * @param parameters 查询条件 
	 * @return 计数值
	 */
	public Long getCountUserPointLog(final Map<String, Object> parameters) {
		return (Long) super.queryForObject("USER_POINT_LOG.getCountUserPointLog", parameters);
	}

}
