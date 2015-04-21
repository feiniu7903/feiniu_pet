package com.lvmama.report.vo;

import java.math.BigDecimal;
import java.math.MathContext;
/**
 * 订单分析报表的统计数据
 * @author yuzhizeng
 * **/
public class OrderBasicVO {
	
	/**
	 * 订单总数
	 * */
	private Long orderQuantityTotal;

	/**
	 * 未支付和取消总数
	 * */
	private Long unpayedQuantityTotal;

	/**
	 * 未支付和取消总金额
	 * */
	private Long unpayedAmountTotal;

	/**
	 * 支付总数
	 * */
	private Long payedQuantityTotal;

	/**
	 * 支付总金额
	 * */
	private Long payedAmountTotal;

	/**
	 * 订单数量计算转化率
	 * 总体订单转化率0.15(保留2位小数)
	 * */
	public BigDecimal getTotalPercentOfConvert() {
		if(orderQuantityTotal == 0){
			return new BigDecimal(0.00);
		}else{
			return BigDecimal.valueOf(payedQuantityTotal).divide(BigDecimal.valueOf(orderQuantityTotal), new MathContext(2));
		}
	}
	
	public Long getPayedAmountTotalYuan() {
		if(payedAmountTotal != null){
			return payedAmountTotal/100;
		}
		return 0l;
	}
	
	public Long getUnpayedAmountTotalYuan() {
		if(unpayedAmountTotal != null){
			return unpayedAmountTotal/100;
		}
		return 0l;
	}

	public Long getUnpayedQuantityTotal() {
		return unpayedQuantityTotal;
	}

	public void setUnpayedQuantityTotal(Long unpayedQuantityTotal) {
		this.unpayedQuantityTotal = unpayedQuantityTotal;
	}

	public Long getUnpayedAmountTotal() {
		return unpayedAmountTotal;
	}

	public void setUnpayedAmountTotal(Long unpayedAmountTotal) {
		this.unpayedAmountTotal = unpayedAmountTotal;
	}

	public Long getPayedQuantityTotal() {
		return payedQuantityTotal;
	}

	public void setPayedQuantityTotal(Long payedQuantityTotal) {
		this.payedQuantityTotal = payedQuantityTotal;
	}

	public Long getPayedAmountTotal() {
		return payedAmountTotal;
	}

	public void setPayedAmountTotal(Long payedAmountTotal) {
		this.payedAmountTotal = payedAmountTotal;
	}

	public Long getOrderQuantityTotal() {
		return orderQuantityTotal;
	}

	public void setOrderQuantityTotal(Long orderQuantityTotal) {
		this.orderQuantityTotal = orderQuantityTotal;
	}
	
}
