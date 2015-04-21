package com.lvmama.comm.pet.vo.favor.strategy;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.vo.Constant;

/** 满X份，一次性享受Y折扣**/
public class OrderFavorStrategyForDiscountQuantityWhole extends OrderFavorStrategy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7308815088032651186L;

	public OrderFavorStrategyForDiscountQuantityWhole(MarkCoupon markCoupon, MarkCouponCode markCouponCode) {
		super(markCoupon, markCouponCode);
	}

	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_WHOLE.name();
	}

	@Override
	public boolean isApply(OrdOrder order, Long discountAmount) {
		return null != order && null != order.getMainProduct() && order.getMainProduct().getQuantity() >= this.getArgumentX();
	}
	
	@Override
	public String getInvalidDesc() {
		return "订购份数至少达到" + this.getArgumentX() + "份以上，才能享受该优惠";
	}	

	@Override
	public Long getDiscountAmount(OrdOrder order, Long discountAmount) {
		if (isApply(order, discountAmount)) {
			return order.getMainProduct().getPrice()* order.getMainProduct().getQuantity()  * (100 - (null == this.discountAmount ? this.getArgumentY() : this.discountAmount)) / 100;
		} else {
			return 0L;
		}
	}
}

