/**
 * 
 */
package com.lvmama.comm.pet.vo.favor.strategy;

import java.text.SimpleDateFormat;
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
 * 提前X天后，每份优惠Y元
 * @author dingming
 */
public class ProductFavorStrategyForAmountEarlydayQuantityPre extends ProductFavorStrategy {
	private static final long serialVersionUID = 1L;
	public ProductFavorStrategyForAmountEarlydayQuantityPre(BusinessCoupon businessCoupon) {
		super(businessCoupon);
	}
	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.AMOUNT_EARLYDAY_QUANTITY_PRE.name();
	}
	@Override
	public boolean isApply(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		if(this.getBusinessCoupon().getPlayBeginTime()!=null&&this.getBusinessCoupon().getPlayEndTime()!=null && this.getArgumentX()==null){
			return (DateUtil.inAdvance(this.getBusinessCoupon().getPlayBeginTime(), ordOrderItemProd.getVisitTime())||this.getBusinessCoupon().getPlayBeginTime().equals(ordOrderItemProd.getVisitTime()))&&DateUtil.inAdvance(ordOrderItemProd.getVisitTime(),this.getBusinessCoupon().getPlayEndTime());
		}else if(this.getBusinessCoupon().getPlayBeginTime()==null&&this.getBusinessCoupon().getPlayEndTime()==null && this.getArgumentX()!=null){
			return null != ordOrderItemProd && (DateUtil.getDaysBetween(new Date(),ordOrderItemProd.getVisitTime())) >= this.getArgumentX();
		}else{//同时存在游玩日期和提前天数
			Date newPlayTime=DateUtil.getDateAfterDays(new Date(), this.getBusinessCoupon().getArgumentX().intValue()-1);
			if(newPlayTime.compareTo(this.getBusinessCoupon().getPlayBeginTime())>=0 && newPlayTime.compareTo(this.getBusinessCoupon().getPlayEndTime())<=0){
				//设置新的游玩日期
				this.getBusinessCoupon().setPlayBeginTime(newPlayTime);
			}else if(newPlayTime.compareTo(this.getBusinessCoupon().getPlayEndTime())>0){
				return false;
			}
			return (DateUtil.inAdvance(this.getBusinessCoupon().getPlayBeginTime(), ordOrderItemProd.getVisitTime())||this.getBusinessCoupon().getPlayBeginTime().equals(ordOrderItemProd.getVisitTime()))&&DateUtil.inAdvance(ordOrderItemProd.getVisitTime(),this.getBusinessCoupon().getPlayEndTime());
		}
	}
	@Override
	public Long getDiscountAmount(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		if (isApply(ordOrderItemProd, discountAmount)) {
				return null == this.specialDiscountAmount ? this.getArgumentY() * ordOrderItemProd.getQuantity() : this.specialDiscountAmount  * ordOrderItemProd.getQuantity();
		} else {
			return 0L;
		}
	}
	@Override
	public String getDisplayInfo(OrdOrderItemProd ordOrderItemProd, Long discountAmount) {
		Long price = this.getDiscountAmount(ordOrderItemProd, discountAmount);
		String desc = "";
		if(this.getBusinessCoupon().getPlayBeginTime()==null&&this.getBusinessCoupon().getPlayEndTime()==null&&this.getArgumentX()!=null){
			desc= "提前"+this.getArgumentX()+"天预订，已立减<font style='color: #FF6600;font-style: normal;font-weight:bold;'>"+PriceUtil.convertToYuan(price)+"</font>元。";
		}else{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			desc= "游玩日期在"+sdf.format(this.getBusinessCoupon().getPlayBeginTime())+"--"+sdf.format(this.getBusinessCoupon().getPlayEndTime())+"期间预订，已立减<font style='color: #FF6600;font-style: normal;font-weight:bold;'>"+PriceUtil.convertToYuan(price)+"</font>元。";
		}
		return desc;
	}
	
	@Override
	public boolean isApply(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		if(this.getBusinessCoupon().getPlayBeginTime()!=null&&this.getBusinessCoupon().getPlayEndTime()!=null && this.getBusinessCoupon().getArgumentX()==null){
			return (DateUtil.inAdvance(this.getBusinessCoupon().getPlayBeginTime(), ordOrderItemMeta.getVisitTime())||this.getBusinessCoupon().getPlayBeginTime().equals(ordOrderItemMeta.getVisitTime()))&&DateUtil.inAdvance(ordOrderItemMeta.getVisitTime(),this.getBusinessCoupon().getPlayEndTime());
		}else if(this.getBusinessCoupon().getPlayBeginTime()==null&&this.getBusinessCoupon().getPlayEndTime()==null && this.getBusinessCoupon().getArgumentX()!=null){
			return null != ordOrderItemMeta && (DateUtil.getDaysBetween(new Date(),ordOrderItemMeta.getVisitTime())) >= this.getArgumentX();
		}else{//同时存在游玩日期和提前天数
			Date newPlayTime=DateUtil.getDateAfterDays(new Date(), this.getBusinessCoupon().getArgumentX().intValue()-1);
			if(newPlayTime.compareTo(this.getBusinessCoupon().getPlayBeginTime())>=0&& newPlayTime.compareTo(this.getBusinessCoupon().getPlayEndTime())<=0){
				//设置新的游玩日期
				this.getBusinessCoupon().setPlayBeginTime(newPlayTime);
			}else if(newPlayTime.compareTo(this.getBusinessCoupon().getPlayEndTime())>0){
				return false;
			}
			return (DateUtil.inAdvance(this.getBusinessCoupon().getPlayBeginTime(), ordOrderItemMeta.getVisitTime())||this.getBusinessCoupon().getPlayBeginTime().equals(ordOrderItemMeta.getVisitTime()))&&DateUtil.inAdvance(ordOrderItemMeta.getVisitTime(),this.getBusinessCoupon().getPlayEndTime());
		}
	}
	@Override
	public Long getDiscountAmount(OrdOrderItemMeta ordOrderItemMeta,Long discountAmount) {
		if (isApply(ordOrderItemMeta, discountAmount)) {
			return null == this.specialDiscountAmount ? this.getArgumentZ() * ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity() : this.specialDiscountAmount  * ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity() ;
	} else {
		return 0L;
	}
	}
	
	@Override
	public  String getTimePriceDisplayParams(ProdBranchSearchInfo prodBranchSearchInfo, Long discountAmount){
		return "{\"index\":\""+Constant.FAVOR_TIME_PRICE_TEMPLATE_INDEX.AMOUNT_EARLYDAY_QUANTITY_PRE.getIndex()+"\",\"param\":\""+prodBranchSearchInfo.getBranchName()+"|"+PriceUtil.convertToYuan(this.getArgumentY())+"\"}";
	}
	
	@Override
	public  Float getTimePriceDisplayDiscountAmount(){
		return PriceUtil.convertToYuan(this.getArgumentY());
	}

}
