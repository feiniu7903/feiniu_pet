package com.lvmama.comm.vo.comment;

import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class CmtProdTitleStatisticsVO extends CmtTitleStatisticsVO {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = -1256870262703866252L;
	 
	/**
	 * 订单ID
	 */
	private long orderId;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 下单时间
	 */
	private Date orderCreateTime;
	/**
	 * 订单是否返现
	 */
	private String isCashRefund;
	/**
	 * 订单返现金额
	 */
	private long cashRefund;
	/**
	 * 订单返现金额，单位：元
	 */
	private float cashRefundYuan;
	/**
	 * 产品大图片
	 */
	private String  productLargeImage;
	/**
	 * 订单的包含的产品数量
	 */
	private long countOfProduct;
	/**
	 * 产品是否在线
	 */
	private String  onLine;
	
	/**
	 * 订单游玩时间
	 * @return
	 * @author nixianjun 2013-11-11
	 */
	private Date orderVisitTime;

	/**
	 * @return the orderVisitTime
	 */
	public Date getOrderVisitTime() {
		return orderVisitTime;
	}


	/**
	 * @param orderVisitTime the orderVisitTime to set
	 */
	public void setOrderVisitTime(Date orderVisitTime) {
		this.orderVisitTime = orderVisitTime;
	}


	public String getProductLargeImgUrl() {
		//return Constant.getInstance().getPrifix580x290Pic() + getPlaceSmallImage();
		return Constant.getInstance().getPrifix580x290Pic() + getProductLargeImage();
	}
	
	
	/**
	 *  ----------------------  get and set property -------------------------------
	 */

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Date getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setIsCashRefund(String isCashRefund) {
		this.isCashRefund = isCashRefund;
	}

	public String getIsCashRefund() {
		return isCashRefund;
	}

	public void setCashRefund(long cashRefund) {
		this.cashRefund = cashRefund;
	}

	public long getCashRefund() {
		return cashRefund;
	}
	/**
	 * 获取返现金额，单位：元
	 * @return
	 */
	public float getCashRefundYuan() {
		this.cashRefundYuan=PriceUtil.convertToYuan(cashRefund);
		return cashRefundYuan;
	}
	/**
	 * 设置返现金额，单位：元
	 * @param cashRefundYuan 返现金额，单位：元
	 */
	public void setCashRefundYuan(float cashRefundYuan) {
		this.cashRefundYuan = cashRefundYuan;
	}
	

	public long getCountOfProduct() {
		return countOfProduct;
	}

	public void setCountOfProduct(long countOfProduct) {
		this.countOfProduct = countOfProduct;
	}

	public String getOnLine() {
		return onLine;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductLargeImage() {
		return productLargeImage;
	}

	public void setProductLargeImage(String productLargeImage) {
		this.productLargeImage = productLargeImage;
	}
}
