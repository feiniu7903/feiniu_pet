package com.lvmama.comm.pet.service.shop;

import com.lvmama.comm.pet.vo.ShopUser;

/**
 * 积分商城用户的逻辑接口层
 * @author Brian
 *
 */
public interface ShopUserService {
	/**
	 * 根据用户标识查找用户
	 * @param userId 用户标识
	 * @return 用户
	 */
	ShopUser getUserByPK(Long userId);

	/**
	 * 用户使用积分
	 * @param userId  用户标识
	 * @param ruleId  规则标识
	 * @param point 积分
	 * @param memo 备注
	 */
	void reducePoint(Long userId, String ruleId, Long point, String memo);
}
