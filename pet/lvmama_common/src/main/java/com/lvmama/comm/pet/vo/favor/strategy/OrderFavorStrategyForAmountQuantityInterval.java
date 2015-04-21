package com.lvmama.comm.pet.vo.favor.strategy;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.vo.Constant;

/** 满X份后，每满Y份，优惠Z元**/
public class OrderFavorStrategyForAmountQuantityInterval extends OrderFavorStrategy{
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -4943474049693618364L;

	public OrderFavorStrategyForAmountQuantityInterval(MarkCoupon markCoupon, MarkCouponCode markCouponCode) {
		super(markCoupon, markCouponCode);
	}
	
	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.AMOUNT_QUANTITY_INTERVAL.name();
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
		long amount = 0;
		if (isApply(order, discountAmount) && null != this.getArgumentY() && 0 < this.getArgumentY()) {
			long acutalZ = null == this.discountAmount ? this.getArgumentZ() : this.discountAmount;
			amount = ((order.getMainProduct().getQuantity() - this.getArgumentX()) / ((null != this.getArgumentY() && 0 < getArgumentY()) ? getArgumentY() : 1L)) * acutalZ; 
		} 
		return amount;
	}

}
