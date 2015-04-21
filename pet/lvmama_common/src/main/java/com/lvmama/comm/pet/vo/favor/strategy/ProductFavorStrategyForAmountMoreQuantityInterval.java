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
 * 每满X份后，优惠Y元
 * @author dingming
 */
public class ProductFavorStrategyForAmountMoreQuantityInterval extends ProductFavorStrategy {
	private static final long serialVersionUID = 1L;
	public ProductFavorStrategyForAmountMoreQuantityInterval(BusinessCoupon businessCoupon) {
		super(businessCoupon);
	}
	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.AMOUNT_MORE_QUANTITY_INTERVAL.name();
	}
	@Override
	public boolean isApply(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		boolean flag=true;
		if(this.getBusinessCoupon().getPlayBeginTime()!=null&&this.getBusinessCoupon().getPlayEndTime()!=null){
			Date bt=this.getBusinessCoupon().getPlayBeginTime();
			Date et=this.getBusinessCoupon().getPlayEndTime();
			flag=(DateUtil.inAdvance(bt, ordOrderItemProd.getVisitTime())||bt.equals(ordOrderItemProd.getVisitTime()))&&DateUtil.inAdvance(ordOrderItemProd.getVisitTime(),et);
		}
		return null != ordOrderItemProd &&ordOrderItemProd.getQuantity()>= this.getArgumentX()&&flag;
	}
	@Override
	public Long getDiscountAmount(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		long amount = 0;
		if (isApply(ordOrderItemProd, discountAmount)) {
			long acutalY = null == this.specialDiscountAmount ? this.getArgumentY() : this.specialDiscountAmount;
			amount =(long) (Math.floor((ordOrderItemProd.getQuantity()/((null != this.getArgumentX() && 0 < getArgumentX()) ? getArgumentX() : 1L))) * acutalY); 
		}
			return amount;
	}
	@Override
	public String getDisplayInfo(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		Long price = this.getDiscountAmount(ordOrderItemProd, discountAmount);
		return "订购每满"+this.getArgumentX()+"份，已立减<font style='color: #FF6600;font-style: normal;font-weight:bold;'>"+PriceUtil.convertToYuan(price)+"</font>元。";
	}
	@Override
	public boolean isApply(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		boolean flag=true;
		if(this.getBusinessCoupon().getPlayBeginTime()!=null&&this.getBusinessCoupon().getPlayEndTime()!=null){
			Date bt=this.getBusinessCoupon().getPlayBeginTime();
			Date et=this.getBusinessCoupon().getPlayEndTime();
			flag=(DateUtil.inAdvance(bt, ordOrderItemMeta.getVisitTime())||bt.equals(ordOrderItemMeta.getVisitTime()))&&DateUtil.inAdvance(ordOrderItemMeta.getVisitTime(),et);
		}
		return null!=ordOrderItemMeta && (ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity() >=this.getArgumentX())&&flag;
	}
	@Override
	public Long getDiscountAmount(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		long amount = 0;
		if (isApply(ordOrderItemMeta, discountAmount)) {
			long acutalY = null == this.specialDiscountAmount ? this.getArgumentZ() : this.specialDiscountAmount;
			amount =(long) (Math.floor((ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity() /((null != this.getArgumentX() && 0 < getArgumentX()) ? getArgumentX() : 1L))) * acutalY); 
		}
			return amount;
	}
	
	@Override
	public  String getTimePriceDisplayParams(ProdBranchSearchInfo prodBranchSearchInfo, Long discountAmount){
		return "{\"index\":\""+Constant.FAVOR_TIME_PRICE_TEMPLATE_INDEX.AMOUNT_MORE_QUANTITY_INTERVAL.getIndex()+"\",\"param\":\""+prodBranchSearchInfo.getBranchName()+"|"+this.getArgumentX()+"|"+PriceUtil.convertToYuan(this.getArgumentY())
				+"|"+2*this.getArgumentX()+"|"+2*PriceUtil.convertToYuan(this.getArgumentY())+"\"}";
	}
	
	@Override
	public  Float getTimePriceDisplayDiscountAmount(){
		return PriceUtil.convertToYuan(this.getArgumentY());
	}

}
