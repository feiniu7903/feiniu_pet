/**
 * 
 */
package com.lvmama.comm.pet.vo.favor.strategy;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.vo.favor.ProductFavorStrategy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 每满X份后，每份优惠折扣率为Y%
 *  @author dingming
 */
public class ProductFavorStrategyForDiscountMoreQuantityInterval extends ProductFavorStrategy {
	private static final long serialVersionUID = 1L;
	public ProductFavorStrategyForDiscountMoreQuantityInterval(BusinessCoupon businessCoupon) {
		super(businessCoupon);
	}
	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.DISCOUNT_MORE_QUANTITY_INTERVAL.name();
	}
	@Override
	public boolean isApply(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		return null != ordOrderItemProd &&ordOrderItemProd.getQuantity()>= this.getArgumentX();
	}
	@Override
	public Long getDiscountAmount(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		long amount = 0;
		if (isApply(ordOrderItemProd, discountAmount)) {
			long acutalY = null == this.specialDiscountAmount ? this.getArgumentY() : this.specialDiscountAmount;
			amount = (long)(ordOrderItemProd.getPrice() * Math.floor(((ordOrderItemProd.getQuantity()) / ((null != this.getArgumentX() && 0 < getArgumentX()) ? getArgumentX() : 1L)))*((null != this.getArgumentX() && 0 < getArgumentX()) ? getArgumentX() : 1L) * (100-acutalY)/100); 
		}
			return amount;
	}
	@Override
	public String getDisplayInfo(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		Long price = this.getDiscountAmount(ordOrderItemProd, discountAmount);
		return "订购已满足每满"+this.getArgumentX()+"份，已立减优惠"+PriceUtil.convertToYuan(price)+"元。";
	}
	@Override
	public boolean isApply(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		return null != ordOrderItemMeta && (ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity()>= this.getArgumentX());
	}
	@Override
	public Long getDiscountAmount(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		long amount = 0;
		if (isApply(ordOrderItemMeta, discountAmount)) {
			long acutalY = null == this.specialDiscountAmount ? this.getArgumentZ() : this.specialDiscountAmount;
			amount = (long)(ordOrderItemMeta.getSettlementPrice() * Math.floor(((ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity()) / ((null != this.getArgumentX() && 0 < getArgumentX()) ? getArgumentX() : 1L)))*((null != this.getArgumentX() && 0 < getArgumentX()) ? getArgumentX() : 1L) * (100-acutalY)/100); 
		}
			return amount;
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
