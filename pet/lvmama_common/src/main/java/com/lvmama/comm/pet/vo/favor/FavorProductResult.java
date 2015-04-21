/**
 * 
 */
package com.lvmama.comm.pet.vo.favor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.pet.po.businessCoupon.ValidateBusinessCouponInfo;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 产品优惠结果返回对象
 * @author liuyi
 *
 */
public class FavorProductResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3431225239025510676L;
	private Long productId;
	private Long productBranchId;
	private List<ProductFavorStrategy> favorStrategyList = new ArrayList<ProductFavorStrategy>();
	private static Logger logger = Logger.getLogger(FavorProductResult.class);
	
	/**
	 * 优惠总金额
	 */
	private Long discountAmount;
	
	
	public Long getDiscountAmount(final OrdOrderItemProd ordOrderItemProd,final long existedAmount, final boolean refresh, List<ValidateBusinessCouponInfo> infoList) {
		if (discountAmount == null || refresh) {
			long amount = 0L;
			List<ProductFavorStrategy> adoptFavorStrategyList = new ArrayList<ProductFavorStrategy>();
			amount += calculateMostFavorStrategy(Constant.BUSINESS_COUPON_TYPE.EARLY.getCode(),  ordOrderItemProd, existedAmount, infoList, favorStrategyList, adoptFavorStrategyList);
			amount += calculateMostFavorStrategy(Constant.BUSINESS_COUPON_TYPE.MORE.getCode(),  ordOrderItemProd, existedAmount + amount, infoList, favorStrategyList, adoptFavorStrategyList);
			discountAmount = amount;
			favorStrategyList = adoptFavorStrategyList;
		}
		return discountAmount;
	}
	
	public Long getDiscountAmount(final OrdOrderItemMeta ordOrderItemMeta,final long existedAmount, final boolean refresh) {
		if (discountAmount == null || refresh) {
			long amount = 0L;
			List<ProductFavorStrategy> adoptFavorStrategyList = new ArrayList<ProductFavorStrategy>();
			amount += calculateMostFavorStrategy(Constant.BUSINESS_COUPON_TYPE.EARLY.getCode(),  ordOrderItemMeta, existedAmount, favorStrategyList, adoptFavorStrategyList);
			amount += calculateMostFavorStrategy(Constant.BUSINESS_COUPON_TYPE.MORE.getCode(),  ordOrderItemMeta, existedAmount + amount,favorStrategyList, adoptFavorStrategyList);
			discountAmount = amount;
			favorStrategyList = adoptFavorStrategyList;
		}
		return discountAmount;
	}
	
	private Long calculateMostFavorStrategy(String couponType, final OrdOrderItemMeta ordOrderItemMeta, final long existedAmount, 
			final List<ProductFavorStrategy> favorStrategyList, List<ProductFavorStrategy> adoptFavorStrategyList){
		ProductFavorStrategy adoptStrategy = null ;//将被使用的策略
		Long adoptAmount = 0l;//将被使用的策略的优惠金额
		for (ProductFavorStrategy strategy : favorStrategyList) {
			if (couponType.equalsIgnoreCase(strategy.getBusinessCoupon().getCouponType()) && strategy.isApply(ordOrderItemMeta, existedAmount)) {
				Long strategyAmount = strategy.getDiscountAmount(ordOrderItemMeta, existedAmount);
				if(adoptStrategy == null && strategyAmount > 0){
					adoptAmount = strategyAmount;
					adoptStrategy = strategy;
				}else if(adoptStrategy != null && adoptAmount < strategyAmount){
					adoptAmount = strategyAmount;
					adoptStrategy = strategy;
				}
			} else {
				//do nothing
			}
		}
		if(adoptStrategy != null){
			//根据现在的逻辑
			//策略我们只选最优惠的， 所以最后只可能剩下一条优惠策略对应一个类别
			adoptFavorStrategyList.add(adoptStrategy);
			if(ordOrderItemMeta.getOrderId() != null){
				logger.info(ordOrderItemMeta.getOrderId()+" adopt strategy "+adoptStrategy.getFavorType()+","+ordOrderItemMeta.getOrderItemMetaId());
			}
			return adoptAmount;
		}
		return 0l;
	}
	
	
	
	
	/**
	 * 计算每一类策略中最优惠的，并返回
	 * @param couponType
	 * @param ordOrderItemProd
	 * @param existedAmount
	 * @param infoList
	 * @param favorStrategyList
	 * @param adoptFavorStrategyList
	 * @return
	 */
	private Long calculateMostFavorStrategy(String couponType, final OrdOrderItemProd ordOrderItemProd, final long existedAmount, List<ValidateBusinessCouponInfo> infoList, 
			final List<ProductFavorStrategy> favorStrategyList, List<ProductFavorStrategy> adoptFavorStrategyList){
		ProductFavorStrategy adoptStrategy = null ;//将被使用的策略
		Long adoptAmount = 0l;//将被使用的策略的优惠金额
		for (ProductFavorStrategy strategy : favorStrategyList) {
			if (couponType.equalsIgnoreCase(strategy.getBusinessCoupon().getCouponType()) && strategy.isApply(ordOrderItemProd, existedAmount)) {
				Long strategyAmount = strategy.getDiscountAmount(ordOrderItemProd, existedAmount);
				if(adoptStrategy == null && strategyAmount > 0){
					adoptAmount = strategyAmount;
					adoptStrategy = strategy;
				}else if(adoptStrategy != null && adoptAmount < strategyAmount){
					adoptAmount = strategyAmount;
					adoptStrategy = strategy;
				}
			} else {
				//do nothing
			}
		}
		if(adoptStrategy != null){
			ValidateBusinessCouponInfo info = new ValidateBusinessCouponInfo();
			info.setProductId(ordOrderItemProd.getProductId());
			info.setProductBranchId(ordOrderItemProd.getProdBranchId());
			info.setDisplayInfo(adoptStrategy.getDisplayInfo(ordOrderItemProd, existedAmount));
			info.setAmount(adoptAmount);
			info.setCouponType(adoptStrategy.getBusinessCoupon().getCouponType());
			infoList.add(info);
			//根据现在的逻辑
			//策略我们只选最优惠的， 所以最后只可能剩下一条优惠策略对应一个类别
			adoptFavorStrategyList.add(adoptStrategy);
			if(ordOrderItemProd.getOrderId() != null){
				logger.info(ordOrderItemProd.getOrderId()+" adopt strategy "+adoptStrategy.getFavorType()+","+ordOrderItemProd.getOrderItemProdId());
			}
			return adoptAmount;
		}
		return 0l;
	}
	
	
	
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductBranchId() {
		return productBranchId;
	}
	public void setProductBranchId(Long productBranchId) {
		this.productBranchId = productBranchId;
	}
	public List<ProductFavorStrategy> getFavorStrategyList() {
		return favorStrategyList;
	}
	public void setFavorStrategyList(List<ProductFavorStrategy> favorStrategyList) {
		this.favorStrategyList = favorStrategyList;
	}
	
	public void addProductFavorStrategry(ProductFavorStrategy favorStrategy){
		 if(null!=favorStrategyList){
			 favorStrategyList.add(favorStrategy);
		 }
	}
	
	/**
	 * 获取时间价格表上显示的JSON参数信息
	 * @param prodBranchSearchInfo
	 * @param visitTime
	 * @param couponType
	 * @return
	 */
	public String getTimePriceJsonParams(ProdBranchSearchInfo prodBranchSearchInfo, Date visitTime, String couponType){
		if(this.favorStrategyList.size() == 0){
			return "";
		}
		Float bestEarlyDiscountAmount = 0f;
		String strategyJsonInfo = "";
		for(int i = 0; i < this.favorStrategyList.size(); i++){
			ProductFavorStrategy productFavorStrategy = favorStrategyList.get(i);
			//设置多定多惠/早定早惠提示信息
			if(couponType.equals(productFavorStrategy.getBusinessCoupon().getCouponType())){
				if(couponType.equals(Constant.BUSINESS_COUPON_TYPE.EARLY.getCode())){
					if(bestEarlyDiscountAmount < productFavorStrategy.getTimePriceDisplayDiscountAmount()){
						bestEarlyDiscountAmount = productFavorStrategy.getTimePriceDisplayDiscountAmount();
						strategyJsonInfo = productFavorStrategy.getTimePriceDisplayParams(prodBranchSearchInfo, 0l);
					}else{
						//do nothing
					}	
				}else{//MORE
					if(strategyJsonInfo.length() > 0){
						strategyJsonInfo += ",";
					}
					strategyJsonInfo += productFavorStrategy.getTimePriceDisplayParams(prodBranchSearchInfo, 0l);
				}
			}
		}
		return strategyJsonInfo;
	}
	
	/**
	 * 获取时间价格表上早订早慧优惠价格
	 * @param prodBranchSearchInfo
	 * @param visitTime
	 * @param couponType
	 * @return
	 */
	public Float getEarlyTimePrice(){
		if(this.favorStrategyList.size() == 0){
			return 0f;
		}
		Float bestEarlyDiscountAmount = 0f;
		for(int i = 0; i < this.favorStrategyList.size(); i++){
			ProductFavorStrategy productFavorStrategy = favorStrategyList.get(i);
			//早定早惠信息
			if(Constant.BUSINESS_COUPON_TYPE.EARLY.getCode().equals(productFavorStrategy.getBusinessCoupon().getCouponType())){
				if(bestEarlyDiscountAmount < productFavorStrategy.getTimePriceDisplayDiscountAmount()){
					bestEarlyDiscountAmount = productFavorStrategy.getTimePriceDisplayDiscountAmount();
				}
			}
		}
		return bestEarlyDiscountAmount;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("FavorProductResult:[");
		builder.append("productId: "+productId+",");
		builder.append("productBranchId: "+productBranchId+",");
		builder.append("discountAmount: "+discountAmount+",");
		builder.append("Strategys:[");
		for(int i = 0; i < favorStrategyList.size(); i++){
			builder.append(favorStrategyList.get(i).getFavorType()+":"+favorStrategyList.get(i)+",");
		}
		builder.append("]");
		builder.append("]");
		return builder.toString();
	}
}
