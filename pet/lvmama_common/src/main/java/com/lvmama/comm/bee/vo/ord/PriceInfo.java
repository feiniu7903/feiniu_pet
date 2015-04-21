package com.lvmama.comm.bee.vo.ord;

import com.lvmama.comm.pet.po.businessCoupon.ValidateBusinessCouponInfo;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;
import com.lvmama.comm.utils.PriceUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PriceInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4307684227976491506L;
	
	/**
	 * 市场价
	 */
	private Long marketPrice;
	
	/**
	 * 销售价
	 */
	private Long price;
	
	/**
	 * 优惠金额
	 */
	private Long coupon;
	
	/**
	 * 订单应付金额
	 */
	private Long oughtPay;
	/**
	 * 奖金
	 */
	private Long bonus;
	/**
	 * 
	 */
	private Long orderQuantity;
	
	/**
	 * 新的全部优惠金额
	 */
	private Long orderDiscountPrice;

    private Float cashValue = 0F;
	
	//操作是否成功
	private boolean success=true;
	private String msg;
	private ValidateCodeInfo info;
	private List<ValidateBusinessCouponInfo> validateBusinessCouponInfoList = new ArrayList<ValidateBusinessCouponInfo>();

	public Long getMarketPrice() {
		return marketPrice/100;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Float getPrice() {
		return PriceUtil.convertToYuan(price);	
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Float getCoupon() {
		return PriceUtil.convertToYuan(this.coupon);	
	}

	public void setCoupon(Long coupon) {
		this.coupon = coupon;
	}

	public Float getOughtPay() {
		return PriceUtil.convertToYuan(this.oughtPay);
	}

	public Long getOughtPayFen() {
		return oughtPay;
	}
	
	public void setOughtPay(Long oughtPay) {
		this.oughtPay = oughtPay;
	}

	public Long getBonus() {
		if(bonus!=null&&bonus>0) {
			return bonus/100;
		} else {
			return 0L;
		}
	}

	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}

	public Long getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	
	
	public void sendError(String msg){
		this.msg=msg;
		this.success=false;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	public Long getOrderDiscountPrice() {
		if(orderDiscountPrice==null||orderDiscountPrice==0L){
			return 0L;
		}
 		return orderDiscountPrice/100;
	}

	public void setOrderDiscountPrice(Long orderDiscountPrice) {
		this.orderDiscountPrice = orderDiscountPrice;
	}

	public ValidateCodeInfo getInfo() {
		return info;
	}

	public void setInfo(ValidateCodeInfo info) {
		this.info = info;
	}

	public List<ValidateBusinessCouponInfo> getValidateBusinessCouponInfoList() {
		return validateBusinessCouponInfoList;
	}

	public void setValidateBusinessCouponInfoList(
			List<ValidateBusinessCouponInfo> validateBusinessCouponInfoList) {
		this.validateBusinessCouponInfoList = validateBusinessCouponInfoList;
	}
	
	public void addValidateBusinessCouponInfo(ValidateBusinessCouponInfo validateBusinessCouponInfo){
		this.validateBusinessCouponInfoList.add(validateBusinessCouponInfo);
	}

    public Float getCashValue() {
        return cashValue;
    }

    public void setCashValue(Float cashValue) {
        this.cashValue = cashValue;
    }
}
