package com.lvmama.comm.pet.vo.favor.strategy;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.vo.Constant;

/** 满X份，每满Y份，超出部分销售价享受Z折扣 **/
public class OrderFavorStrategyForDiscountQuantityPre extends OrderFavorStrategy {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -7947511658940927817L;

	public OrderFavorStrategyForDiscountQuantityPre(MarkCoupon markCoupon, MarkCouponCode markCouponCode) {
		super(markCoupon, markCouponCode);
	}

	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_PRE.name();
	}

	@Override
	public boolean isApply(OrdOrder order, Long discountAmount) {
		return null != order && null != order.getMainProduct() && order.getMainProduct().getQuantity() >= (this.getArgumentX() + this.getArgumentY());
	}
	
	@Override
	public String getInvalidDesc() {
		return "订购份数至少达到" + (this.getArgumentX() + this.getArgumentY()) + "份以上，才能享受该优惠";
	}	

	@Override
	public Long getDiscountAmount(OrdOrder order, Long discountAmount) {
		if (isApply(order, discountAmount)) {
			Long amount = null == this.discountAmount ? this.getArgumentZ() : this.discountAmount;
			return order.getMainProduct().getPrice() * ((order.getMainProduct().getQuantity() - this.getArgumentX()) / ((null != this.getArgumentY() && 0 < getArgumentY()) ? getArgumentY() : 1L)) * (100 - amount) / 100;
		} else {
			return 0L;
		}
	}
}
