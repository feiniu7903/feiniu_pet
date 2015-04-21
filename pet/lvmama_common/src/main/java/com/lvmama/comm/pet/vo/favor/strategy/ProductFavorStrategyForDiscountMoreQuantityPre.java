/**
 * 
 */
package com.lvmama.comm.pet.vo.favor.strategy;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.vo.favor.ProductFavorStrategy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 满X份后，每份优惠折扣率Y%
 * @author dingming
 *
 */
public class ProductFavorStrategyForDiscountMoreQuantityPre extends ProductFavorStrategy {

	private static final long serialVersionUID = 1L;

	public ProductFavorStrategyForDiscountMoreQuantityPre(BusinessCoupon businessCoupon) {
		super(businessCoupon);
	}
	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.DISCOUNT_MORE_QUANTITY_PRE.name();
	}
	@Override
	public boolean isApply(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		return null != ordOrderItemProd && ordOrderItemProd.getQuantity() >= this.getArgumentX();
	}
	@Override
	public Long getDiscountAmount(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		if (isApply(ordOrderItemProd, discountAmount)) {
			Long actualY = null == this.specialDiscountAmount ? this.getArgumentY() : this.specialDiscountAmount;
			return ordOrderItemProd.getQuantity()*ordOrderItemProd.getPrice() * (100 - actualY) / 100;
		} else {
			return 0L;
		}
	}
	@Override
	public String getDisplayInfo(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		Long price = this.getDiscountAmount(ordOrderItemProd, discountAmount);
		return "订购已满足满"+this.getArgumentX()+"天份，已立减优惠"+PriceUtil.convertToYuan(price)+"元。";
	}
	@Override
	public boolean isApply(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		return null != ordOrderItemMeta && (ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity() >= this.getArgumentX());
	}
	@Override
	public Long getDiscountAmount(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		if (isApply(ordOrderItemMeta, discountAmount)) {
			Long actualY = null == this.specialDiscountAmount ? this.getArgumentZ() : this.specialDiscountAmount;
			return ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity()*ordOrderItemMeta.getSettlementPrice() * (100 - actualY) / 100;
		} else {
			return 0L;
		}
	}

	@Override
	public  String getTimePriceDisplayParams(ProdBranchSearchInfo prodBranchSearchInfo, Long discountAmount){
		return "";
	}
	
	@Override
	public  Float getTimePriceDisplayDiscountAmount(){
		return 0f;
	}
}
