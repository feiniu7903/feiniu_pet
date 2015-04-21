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
 * 满X份后，每满Y份后，优惠Z元
 * @author dingming
 */
public class ProductFavorStrategyForAmountMoreQuantityWhole extends ProductFavorStrategy {
	private static final long serialVersionUID = 1L;
	public ProductFavorStrategyForAmountMoreQuantityWhole(BusinessCoupon businessCoupon) {
		super(businessCoupon);
	}
	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.AMOUNT_MORE_QUANTITY_WHOLE.name();
	}
	@Override
	public boolean isApply(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		boolean flag=true;
		if(this.getBusinessCoupon().getPlayBeginTime()!=null&&this.getBusinessCoupon().getPlayEndTime()!=null){
			Date bt=this.getBusinessCoupon().getPlayBeginTime();
			Date et=this.getBusinessCoupon().getPlayEndTime();
			flag=(DateUtil.inAdvance(bt, ordOrderItemProd.getVisitTime())||bt.equals(ordOrderItemProd.getVisitTime()))&&DateUtil.inAdvance(ordOrderItemProd.getVisitTime(),et);
		}
		return null != ordOrderItemProd &&ordOrderItemProd.getQuantity()>= (this.getArgumentX()+this.getArgumentY())&&flag;
	}
	@Override
	public Long getDiscountAmount(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		long amount = 0;
		if (isApply(ordOrderItemProd, discountAmount)) {
			long acutalZ = null == this.specialDiscountAmount ? this.getArgumentZ() : this.specialDiscountAmount;
			amount =(long) (Math.floor(((ordOrderItemProd.getQuantity()-this.getArgumentX())/((null != this.getArgumentY() && 0 < getArgumentY()) ? getArgumentY() : 1L))) * acutalZ); 
		}
			return amount;
	}
	@Override
	public String getDisplayInfo(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		Long price = this.getDiscountAmount(ordOrderItemProd, discountAmount);
		return "订购满"+this.getArgumentX()+"份，每满"+this.getArgumentY()+"份后，已立减<font style='color: #FF6600;font-style: normal;font-weight:bold;'>"+PriceUtil.convertToYuan(price)+"</font>元。";
	}
	@Override
	public boolean isApply(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		boolean flag=true;
		if(this.getBusinessCoupon().getPlayBeginTime()!=null&&this.getBusinessCoupon().getPlayEndTime()!=null){
			Date bt=this.getBusinessCoupon().getPlayBeginTime();
			Date et=this.getBusinessCoupon().getPlayEndTime();
			flag=(DateUtil.inAdvance(bt, ordOrderItemMeta.getVisitTime())||bt.equals(ordOrderItemMeta.getVisitTime()))&&DateUtil.inAdvance(ordOrderItemMeta.getVisitTime(),et);
		}
		return null != ordOrderItemMeta && (ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity()>= (this.getArgumentX()+this.getArgumentY()))&&flag;
	}
	@Override
	public Long getDiscountAmount(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		long amount = 0;
		if (isApply(ordOrderItemMeta, discountAmount)) {
			long acutalZ = null == this.specialDiscountAmount ? this.getArgumentZ() : this.specialDiscountAmount;
			amount =(long) (Math.floor(((ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity()-this.getArgumentX())/((null != this.getArgumentY() && 0 < getArgumentY()) ? getArgumentY() : 1L))) * acutalZ); 
		}
			return amount;
	}
	
	
	@Override
	public  String getTimePriceDisplayParams(ProdBranchSearchInfo prodBranchSearchInfo, Long discountAmount){
		return "{\"index\":\""+Constant.FAVOR_TIME_PRICE_TEMPLATE_INDEX.AMOUNT_MORE_QUANTITY_PRE.getIndex()+"\",\"param\":\""+prodBranchSearchInfo.getBranchName()+"|"+this.getArgumentX()+"|"+this.getArgumentY()
				+"|"+PriceUtil.convertToYuan(this.getArgumentZ())+"|"+2*this.getArgumentY()+"|"+2*PriceUtil.convertToYuan(this.getArgumentZ())+"\"}";
	}
	
	@Override
	public  Float getTimePriceDisplayDiscountAmount(){
		return PriceUtil.convertToYuan(this.getArgumentZ());
	}
}
