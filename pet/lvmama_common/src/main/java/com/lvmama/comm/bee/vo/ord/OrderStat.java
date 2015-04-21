package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;

import com.lvmama.comm.utils.PriceUtil;

/**
 * 订单统计信息
 * 
 * @author user
 * 
 */
public class OrderStat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7162920180302726082L;
	private Integer orderCount;// 订单数量
	private Integer totalAmount;// 订单总金额
	private float totalAmountFloat;

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		if(this.totalAmount!=null){
			this.totalAmountFloat = PriceUtil.convertToYuan(totalAmount.longValue());
		}
		this.totalAmount = totalAmount;
	}

	public float getTotalAmountFloat() {
		return totalAmountFloat;
	}

	public void setTotalAmountFloat(float totalAmountFloat) {
		this.totalAmountFloat = totalAmountFloat;
	}

}
