/**
 * 
 */
package com.lvmama.comm.pet.vo.favor.strategy;

import java.util.Date;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.vo.favor.ProductFavorStrategy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 满X份后，每份优惠Y元
 * @author liuyi
 *
 */
public class ProductFavorStrategyForAmountMoreQuantityPre extends ProductFavorStrategy {

	
	public ProductFavorStrategyForAmountMoreQuantityPre(BusinessCoupon businessCoupon) {
		super(businessCoupon);
	}
	
	
	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.vo.favor.FavorStrategy#getFavorType()
	 */
	@Override
	public String getFavorType() {
		// TODO Auto-generated method stub
		return Constant.FAVOR_TYPE.AMOUNT_MORE_QUANTITY_PRE.name();
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.vo.favor.ProductFavorStrategy#isApply(com.lvmama.comm.bee.po.ord.OrdOrderItemProd, java.lang.Long)
	 */
	@Override
	public boolean isApply(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		boolean flag=true;
		if(this.getBusinessCoupon().getPlayBeginTime()!=null&&this.getBusinessCoupon().getPlayEndTime()!=null){
			Date bt=this.getBusinessCoupon().getPlayBeginTime();
			Date et=this.getBusinessCoupon().getPlayEndTime();
			flag=(DateUtil.inAdvance(bt, ordOrderItemProd.getVisitTime())||bt.equals(ordOrderItemProd.getVisitTime()))&&DateUtil.inAdvance(ordOrderItemProd.getVisitTime(),et);
		}
		return null != ordOrderItemProd && ordOrderItemProd.getQuantity() >= this.getArgumentX()&&flag;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.vo.favor.ProductFavorStrategy#getDiscountAmount(com.lvmama.comm.bee.po.ord.OrdOrderItemProd, java.lang.Long)
	 */
	@Override
	public Long getDiscountAmount(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		if (isApply(ordOrderItemProd, discountAmount)) {
			return null == this.specialDiscountAmount ? this.getArgumentY() * ordOrderItemProd.getQuantity() : this.specialDiscountAmount  * ordOrderItemProd.getQuantity();
		} else {
			return 0L;
		}
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.vo.favor.ProductFavorStrategy#getDisplayInfo()
	 */
	@Override
	public String getDisplayInfo(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		Long price = this.getDiscountAmount(ordOrderItemProd, discountAmount);
		return "订购满"+this.getArgumentX()+"份，已立减<font style='color: #FF6600;font-style: normal;font-weight:bold;'>"+PriceUtil.convertToYuan(price)+"</font>元。";
	}


	@Override
	public boolean isApply(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		boolean flag=true;
		if(this.getBusinessCoupon().getPlayBeginTime()!=null&&this.getBusinessCoupon().getPlayEndTime()!=null){
			Date bt=this.getBusinessCoupon().getPlayBeginTime();
			Date et=this.getBusinessCoupon().getPlayEndTime();
			flag=(DateUtil.inAdvance(bt, ordOrderItemMeta.getVisitTime())||bt.equals(ordOrderItemMeta.getVisitTime()))&&DateUtil.inAdvance(ordOrderItemMeta.getVisitTime(),et);
		}
		return null != ordOrderItemMeta && (ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity()  >= this.getArgumentX())&&flag;
	}


	@Override
	public Long getDiscountAmount(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		if (isApply(ordOrderItemMeta, discountAmount)) {
			return null == this.specialDiscountAmount ? this.getArgumentZ() * ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity()  : this.specialDiscountAmount  * ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity();
		} else {
			return 0L;
		}
	}
	
	
	@Override
	public  String getTimePriceDisplayParams(ProdBranchSearchInfo prodBranchSearchInfo, Long discountAmount){
		return "{\"index\":\""+Constant.FAVOR_TIME_PRICE_TEMPLATE_INDEX.AMOUNT_MORE_QUANTITY_WHOLE.getIndex()+"\",\"param\":\""+prodBranchSearchInfo.getBranchName()+"|"+this.getArgumentX()+"|"+PriceUtil.convertToYuan(this.getArgumentY())+"\"}";
	}
	@Override
	public  Float getTimePriceDisplayDiscountAmount(){
		return PriceUtil.convertToYuan(this.getArgumentY());
	}

}
