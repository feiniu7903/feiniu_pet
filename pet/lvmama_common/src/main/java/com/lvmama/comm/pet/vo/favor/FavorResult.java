/**
 * 
 */
package com.lvmama.comm.pet.vo.favor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.pet.po.businessCoupon.ValidateBusinessCouponInfo;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;
import com.lvmama.comm.vo.Constant;

/**
 * 优惠计算返回最终结果对象
 * @author liuyi
 *
 */
public class FavorResult implements Serializable{
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 4786053741547334211L;
	/**
	 * 作用在订单上的优惠策略
	 */
	private FavorOrderResult favorOrderResult = new FavorOrderResult();
	/**
	 * 作用在产品上的优惠策略
	 */
	private List<FavorProductResult> favorProductList = new ArrayList<FavorProductResult>();
	/**
	 * 优惠券使用的说明(供前台页面调用使用)
	 */
	private ValidateCodeInfo info = new ValidateCodeInfo();
	/**
	 * 产品优惠使用的说明(供前台页面调用使用)
	 */
	private List<ValidateBusinessCouponInfo> validateBusinessCouponInfoList = new ArrayList<ValidateBusinessCouponInfo>();
	
	/**
	 * 返回订单优惠结果的优惠总额
	 * @param order 原始订单
	 * @return 优惠总额
	 */
	public long sumAllFavorDiscountAmount(final OrdOrder order) {
		long sumFavorProductDiscountAmount = sumFavorProductDiscountAmount(order);
		long sumFavorOrderDiscountAmount = favorOrderResult.getDiscountAmount(order, sumFavorProductDiscountAmount, true, info);
		if (favorOrderResult.getFavorStrategyList().isEmpty()) {
			if (null == info.getKey()) {
				info.setKey("ERROR_INFO");
				info.setValid(false);
			}
		} else {
			info.setCouponId(favorOrderResult.getFavorStrategyList().get(0).getMarkCoupon().getCouponId());
			info.setValid(true);
			info.setKey(Constant.COUPON_INFO.OK.name());	
			info.setYouhuiAmount(sumFavorOrderDiscountAmount);
			info.setPaymentChannel(favorOrderResult.getFavorStrategyList().get(0).getMarkCoupon().getPaymentChannel());
		}
		return sumFavorProductDiscountAmount + sumFavorOrderDiscountAmount;	
	}
	
	
	/**
	 * 返回所有作用在销售产品的优惠总额
	 * @return 作用在销售产品上的优惠总额
	 */
	private long sumFavorProductDiscountAmount(final OrdOrder order) {
		long amount = 0;
		for (FavorProductResult r : favorProductList) {
			for(int i = 0; i < order.getOrdOrderItemProds().size();i++){
				OrdOrderItemProd ordOrderItemProd = order.getOrdOrderItemProds().get(i);
				if(ordOrderItemProd.getProductId().equals(r.getProductId())  && ordOrderItemProd.getProdBranchId().equals(r.getProductBranchId())){
					List<ValidateBusinessCouponInfo> infoList = new ArrayList<ValidateBusinessCouponInfo>();
					amount += r.getDiscountAmount(ordOrderItemProd, 0, true, infoList);
					if (!r.getFavorStrategyList().isEmpty()){
						this.addValidateBusinessCouponInfo(infoList);
					}
				}
			}
		}
		return amount;
	}
	
	
	/**
	 * 新增订单类的优惠策略
	 * @param strategy 优惠策略
	 * 将针对订单的优惠策略加入到优惠结果中，此时添加的订单类优惠策略只代表可能适用的订单策略，并不是订单实际使用的优惠</p>
	 */
	public void addOrderFavorStrategy(OrderFavorStrategy strategy) {
		if (null != strategy) {
			favorOrderResult.addFavorStrategy(strategy);
		}
	}
	
	public void addProductFavorResult(FavorProductResult favorProductResult){
		 if(null!=favorProductResult){
			 favorProductList.add(favorProductResult);
		 }
	}
	
	/**
	 * 根据订单销售类别返回对应的favorProductResult
	 * @param ordOrderItemProd
	 * @return
	 */
	public FavorProductResult getMatchingFavorProductResultByOrderItem(OrdOrderItemProd ordOrderItemProd){
		if(this.favorProductList != null && this.favorProductList.size() > 0){
			for(int  i = 0; i < this.favorProductList.size(); i++){
				FavorProductResult favorProductResult = favorProductList.get(i);
				if(ordOrderItemProd.getProductId().equals(favorProductResult.getProductId()) &&
						ordOrderItemProd.getProdBranchId().equals(favorProductResult.getProductBranchId())){
					return favorProductResult;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 返回优惠券验证信息
	 * @return 优惠券验证信息
	 */
	public ValidateCodeInfo getValidateCodeInfo() {
		return info;
	}

	public List<ValidateBusinessCouponInfo> getValidateBusinessCouponInfoList() {
		return validateBusinessCouponInfoList;
	}

	public void setValidateBusinessCouponInfoList(
			List<ValidateBusinessCouponInfo> validateBusinessCouponInfoList) {
		this.validateBusinessCouponInfoList = validateBusinessCouponInfoList;
	}
	
	public void addValidateBusinessCouponInfo(List<ValidateBusinessCouponInfo> infoList){
		validateBusinessCouponInfoList.addAll(infoList);
	}

	public FavorOrderResult getFavorOrderResult() {
		return favorOrderResult;
	}

	public List<FavorProductResult> getFavorProductList() {
		return favorProductList;
	}

	public void setFavorProductList(List<FavorProductResult> favorProductList) {
		this.favorProductList = favorProductList;
	}
	

	@Override
	public String toString(){
		return new ToStringBuilder(this).append("favorProductList", favorProductList).append("favorOrderResult", favorOrderResult).toString();
	}
}
