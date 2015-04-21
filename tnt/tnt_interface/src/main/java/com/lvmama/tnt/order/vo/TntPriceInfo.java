package com.lvmama.tnt.order.vo;

import java.io.Serializable;

import com.lvmama.tnt.comm.util.PriceUtil;

public class TntPriceInfo implements Serializable {

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
	private Float price;

	/**
	 * 优惠金额
	 */
	private Float coupon;

	/**
	 * 订单应付金额
	 */
	private Float oughtPay;

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

	/**
	 * 分销价
	 */
	private Long distPrice;

	// 操作是否成功
	private boolean success = true;
	private String msg;

	public Long getMarketPrice() {
		return PriceUtil.getLongPriceYuan(marketPrice);
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getCoupon() {
		return coupon;
	}

	public void setCoupon(Float coupon) {
		this.coupon = coupon;
	}

	public Float getOughtPay() {
		return oughtPay;
	}

	public Long getOughtPayFen() {
		return PriceUtil.convertToFen(oughtPay);
	}

	public void setOughtPay(Float oughtPay) {
		this.oughtPay = oughtPay;
	}

	public Long getBonus() {
		if (bonus != null && bonus > 0) {
			return PriceUtil.getLongPriceYuan(bonus);
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

	public void sendError(String msg) {
		this.msg = msg;
		this.success = false;
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
		if (orderDiscountPrice == null || orderDiscountPrice == 0L) {
			return 0L;
		}
		return PriceUtil.getLongPriceYuan(orderDiscountPrice);
	}

	public void setOrderDiscountPrice(Long orderDiscountPrice) {
		this.orderDiscountPrice = orderDiscountPrice;
	}

	public Long getDistPrice() {
		return distPrice;
	}

	public void setDistPrice(Long distPrice) {
		this.distPrice = distPrice;
	}

	public Float getDistPriceYuan() {
		return PriceUtil.convertToYuan(distPrice);
	}
}
