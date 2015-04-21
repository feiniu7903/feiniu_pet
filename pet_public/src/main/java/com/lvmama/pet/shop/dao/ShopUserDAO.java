package com.lvmama.pet.shop.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.vo.ShopUser;

/**
 * 积分商城用户的数据库实现类
 * @author Brian
 *
 */
public class ShopUserDAO extends BaseIbatisDAO {

	/**
	 * 根据用户标识查找用户
	 * @param userId 用户标识
	 * @return 积分用户
	 */
	public ShopUser getUser(final Long userId) {
		if (null == userId) {
			return null;
		}
		return (ShopUser) super.queryForObject("SHOP_USER.queryByPK", userId);
	}

	/**
	 * 刷新用户数据
	 * @param userId 用户标识
	 */
	public void refresh(final Long userId) {
		super.update("SHOP_USER.refresh", userId);
	}
}
