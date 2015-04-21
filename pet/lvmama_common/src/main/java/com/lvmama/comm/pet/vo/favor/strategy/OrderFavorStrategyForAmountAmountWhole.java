
package com.lvmama.comm.pet.vo.favor.strategy;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 对应优惠策略
 * 满X元后，一次性优惠Y元 AMOUNT_AMOUNT_WHOLE
 * @author liuyi
 *
 */
public class OrderFavorStrategyForAmountAmountWhole extends OrderFavorStrategy {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -5374872917604592588L;

	public OrderFavorStrategyForAmountAmountWhole(MarkCoupon markCoupon, MarkCouponCode markCouponCode) {
		super(markCoupon, markCouponCode);
	}
	
	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.AMOUNT_AMOUNT_WHOLE.name();
	}


	@Override
	public boolean isApply(OrdOrder order, Long discountAmount) {
		return null != order && (order.getOughtPay() - discountAmount) >= getArgumentX(); 
	}

	@Override
	public String getInvalidDesc() {
		return "订单金额至少达到" + PriceUtil.convertToYuan(this.getArgumentX()) + "元以上，才能享受该优惠";
	}
	
	@Override
	public Long getDiscountAmount(OrdOrder order, Long discountAmount) {
		if (isApply(order, discountAmount)) {
			return null == this.discountAmount ? this.getArgumentY() : this.discountAmount;
		} else {
			return 0L;
		}
	}
}
