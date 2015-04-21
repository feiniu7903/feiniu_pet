/**
 * 
 */
package com.lvmama.comm.pet.vo.favor.strategy;

import java.util.Date;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.vo.favor.ProductFavorStrategy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 提前X天后，每份优惠折扣率为Y
 * @author dingming
 */
public class ProductFavorStrategyForDiscountEarlydayQuantityPre extends ProductFavorStrategy {
	private static final long serialVersionUID = 1L;
	public ProductFavorStrategyForDiscountEarlydayQuantityPre(BusinessCoupon businessCoupon) {
		super(businessCoupon);
	}
	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.DISCOUNT_EARLYDAY_QUANTITY_PRE.name();
	}
	@Override
	public boolean isApply(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		if(this.getBusinessCoupon().getPlayBeginTime()!=null&&this.getBusinessCoupon().getPlayEndTime()!=null){
			return DateUtil.inAdvance(this.getBusinessCoupon().getPlayBeginTime(), ordOrderItemProd.getVisitTime())&&DateUtil.inAdvance(ordOrderItemProd.getVisitTime(),this.getBusinessCoupon().getPlayEndTime());
		}else{
			return null != ordOrderItemProd && (DateUtil.getDaysBetween(ordOrderItemProd.getVisitTime(), new Date())) >= this.getArgumentX();
		}
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
		return "订购已满足提前"+this.getArgumentX()+"天预订，已立减优惠"+PriceUtil.convertToYuan(price)+"元。";
	}
	@Override
	public boolean isApply(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		if(this.getBusinessCoupon().getPlayBeginTime()!=null&&this.getBusinessCoupon().getPlayEndTime()!=null){
			return DateUtil.inAdvance(this.getBusinessCoupon().getPlayBeginTime(), ordOrderItemMeta.getVisitTime())&&DateUtil.inAdvance(ordOrderItemMeta.getVisitTime(),this.getBusinessCoupon().getPlayEndTime());
		}else{
			return null != ordOrderItemMeta && (DateUtil.getDaysBetween(ordOrderItemMeta.getVisitTime(), new Date())) >= this.getArgumentX();
		}
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
