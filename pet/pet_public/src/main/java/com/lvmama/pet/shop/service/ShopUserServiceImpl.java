package com.lvmama.pet.shop.service;

import com.lvmama.comm.pet.po.user.UserPointLog;
import com.lvmama.comm.pet.service.shop.ShopUserService;
import com.lvmama.comm.pet.vo.ShopUser;
import com.lvmama.pet.shop.dao.ShopUserDAO;
import com.lvmama.pet.user.dao.UserPointLogDAO;

/**
 * 积分商城用户的逻辑实现层
 * @author Brian
 *
 */
class ShopUserServiceImpl implements ShopUserService {
	/**
	 * 积分商城用户的数据库层
	 */
	private ShopUserDAO shopUserDAO;
	/**
	 * 积分日志
	 */
	private UserPointLogDAO userPointLogDAO;

	@Override
	public ShopUser getUserByPK(final Long userId) {
		if (null == userId) {
			return null;
		}
		return shopUserDAO.getUser(userId);
	}

	@Override
	public void reducePoint(final Long userId, final String ruleId, final Long point, final String memo) {
		if (null == userId || null == ruleId || null == point) {
			return;
		}
		UserPointLog upl = new UserPointLog();
		upl.setUserId(userId);
		upl.setRuleId(ruleId);
		upl.setPoint(point);
		upl.setMemo(memo);
		userPointLogDAO.insert(upl);
		shopUserDAO.refresh(userId);
	}


	public void setShopUserDAO(final ShopUserDAO shopUserDAO) {
		this.shopUserDAO = shopUserDAO;
	}

	public void setUserPointLogDAO(final UserPointLogDAO userPointLogDAO) {
		this.userPointLogDAO = userPointLogDAO;
	}

}
