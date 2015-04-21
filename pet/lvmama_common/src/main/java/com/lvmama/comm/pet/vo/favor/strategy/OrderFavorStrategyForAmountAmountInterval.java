package com.lvmama.comm.pet.vo.favor.strategy;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/** 满X元后，每满Y元，优惠Z元**/
public class OrderFavorStrategyForAmountAmountInterval extends OrderFavorStrategy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8072434296778600786L;

	public OrderFavorStrategyForAmountAmountInterval(MarkCoupon markCoupon, MarkCouponCode markCouponCode) {
		super(markCoupon, markCouponCode);
	}

	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.AMOUNT_AMOUNT_INTERVAL.name();
	}

	@Override
	public boolean isApply(OrdOrder order, Long discountAmount) {
		return null != order && (order.getOughtPay() - discountAmount) -getArgumentX() >= getArgumentY(); 
	}
	
	@Override
	public String getInvalidDesc() {
		return "订单金额至少达到" + (PriceUtil.convertToYuan(this.getArgumentX()) + PriceUtil.convertToYuan(this.getArgumentY())) + "元以上，才能享受该优惠";
	}

	//注意事项，传递进来的参数discountAmount最好不与当前对象的属性名称一样
	@Override
	public Long getDiscountAmount(OrdOrder order, Long discountAmount) {
		long amount = 0L;
		if (isApply(order, discountAmount) && null != getArgumentY() && 0 < getArgumentY()) {
			long acutalZ = (null == this.discountAmount ? this.getArgumentZ() : this.discountAmount);
			amount = (((order.getOughtPay() - discountAmount) -getArgumentX()) / ((null != this.getArgumentY() && 0 < getArgumentY()) ? getArgumentY() : 1L)) * acutalZ; 
		} 
		return amount;
	}
}
