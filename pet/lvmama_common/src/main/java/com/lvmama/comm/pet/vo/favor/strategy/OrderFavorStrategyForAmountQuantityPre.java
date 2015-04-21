package com.lvmama.comm.pet.vo.favor.strategy;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.vo.Constant;

/** 满X份后，每份优惠Y元**/
public class OrderFavorStrategyForAmountQuantityPre extends OrderFavorStrategy {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 9096652369525756584L;

	public OrderFavorStrategyForAmountQuantityPre(MarkCoupon markCoupon, MarkCouponCode markCouponCode) {
		super(markCoupon, markCouponCode);
	}

	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.AMOUNT_QUANTITY_PRE.name();
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
			return null == this.discountAmount ? this.getArgumentY() * order.getMainProduct().getQuantity() : this.discountAmount  * order.getMainProduct().getQuantity();
		} else {
			return 0L;
		}
	}

}
