package com.lvmama.comm.pet.vo.favor.strategy;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/** 满X元后，一次性享受Y折扣**/
public class OrderFavorStrategyForDiscountAmountWhole extends OrderFavorStrategy {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -6973373110266575399L;

	public OrderFavorStrategyForDiscountAmountWhole(MarkCoupon markCoupon, MarkCouponCode markCouponCode) {
		super(markCoupon, markCouponCode);
	}

	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.DISCOUNT_AMOUNT_WHOLE.name();
	}

	@Override
	public boolean isApply(OrdOrder order, Long discountAmount) {
		return (order.getOughtPay() - discountAmount) >= this.getArgumentX();
	}
	
	@Override
	public String getInvalidDesc() {
		return "订单金额至少达到" + PriceUtil.convertToYuan(this.getArgumentX()) + "元以上，才能享受该优惠";
	}	

	@Override
	public Long getDiscountAmount(OrdOrder order, Long discountAmount) {
		if (isApply(order, discountAmount)) {
			Long actualY = null == this.discountAmount ? this.getArgumentY() : this.discountAmount;
			return (order.getOughtPay() - discountAmount) * (100 - actualY) / 100;
		} else {
			return 0L;
		}
	}
}

