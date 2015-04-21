package com.lvmama.pet.user.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserPointRule;


/**
 * 用户积分规则的数据库操作实现类
 * @author Brian
 *
 */
public class UserPointRuleDAO extends BaseIbatisDAO {
	/**
	 * 日志记录器
	 */
	private static final Log LOG = LogFactory.getLog(UserPointRuleDAO.class);

	/**
	 * 根据规则标识查找相依的积分规则
	 * @param ruleId 规则标识
	 * @return 积分规则
	 */
	public UserPointRule getRulesByID(final String ruleId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("查询用户积分规则：" + ruleId);
		}
		return (UserPointRule) super.queryForObject("USER_POINT_RULE.queryByPK", ruleId);
	}
}
