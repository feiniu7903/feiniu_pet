package com.lvmama.comm.pet.vo.favor;

import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;

/**
 * 抽象优惠策略的实现类
 * @author Brian
 * 所有优惠策略实现类都应以继承此类。
 */
public abstract class AbstractFavorStrategy implements FavorStrategy {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -7977240352310668564L;
	
	/**
	 * 构造器
	 * @param markCoupon
	 */
	public AbstractFavorStrategy() {

	}
	

}
