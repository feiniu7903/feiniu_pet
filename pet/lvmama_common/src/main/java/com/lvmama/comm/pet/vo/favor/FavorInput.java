/**
 * 
 */
package com.lvmama.comm.pet.vo.favor;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;

/**
 * 优惠系统计算输入参数
 * @author liuyi
 *
 */
public class FavorInput {
	
	//当前订单应付值
	private Long orderOughtPay;
	
	//当前订单主产品订购人数/间数/夜数 
	private Long totalPersonQuantity;
	
	//当前订单要计算优惠的产品
	private Item currentCalculateProduct;
	
	public Long getOrderOughtPay() {
		return orderOughtPay;
	}

	public void setOrderOughtPay(Long orderOughtPay) {
		this.orderOughtPay = orderOughtPay;
	}


	public Long getTotalPersonQuantity() {
		return totalPersonQuantity;
	}

	public void setTotalPersonQuantity(Long totalPersonQuantity) {
		this.totalPersonQuantity = totalPersonQuantity;
	}

	public Item getCurrentCalculateProduct() {
		return currentCalculateProduct;
	}

	public void setCurrentCalculateProduct(Item currentCalculateProduct) {
		this.currentCalculateProduct = currentCalculateProduct;
	}

}
