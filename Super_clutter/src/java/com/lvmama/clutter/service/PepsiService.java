package com.lvmama.clutter.service;
/**
 * 百事优惠券服务接口
 * @author chenlinjun
 *
 */
public interface PepsiService {
	/**
	 * 百事兑换优惠券接口
	 * @param mobile 手机号
	 * @param serialNo 百事流水号
	 * @param email 邮箱
	 * @return
	 */
	public int exchangeCoupon(String mobile, String serialNo, String email);
}
