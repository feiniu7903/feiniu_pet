package com.lvmama.report.po;

import java.math.BigDecimal;
import java.math.MathContext;

public class OrderBasicMV {

	private String productType;

	private String createTime;

	private Long quantity;

	private Long unpayedQuantity;

	private Long unpayedAmount;

	private Long payedQuantity;

	private Long payedAmount;
	
	private String itemName;
	
	private String filialeName;
	
	/**
	 * 订单数量计算转化率
	 * 订单转化率0.15(保留2位小数)
	 * */
	public BigDecimal getPercentOfConvert() {
		return BigDecimal.valueOf(payedQuantity).divide(BigDecimal.valueOf(quantity), new MathContext(2));
	}
	
	public Long getPayedAmountYuan() {
		if(payedAmount!=null){
			return payedAmount/100;
		}
		return 0l;
	}
	
	public Long getUnpayedAmountYuan() {
		if(unpayedAmount!=null){
			return unpayedAmount/100;
		}
		return 0l;
	}

	public String getZhProductType() {
		if ("TICKET".equals(productType)) {
			return "门票";
		}
		if ("HOTEL".equals(productType)) {
			return "酒店";
		}
		if ("GROUP".equals(productType)) {
			return "短途跟团游";
		}
		if ("GROUP_LONG".equals(productType)) {
			return "长途跟团游";
		}
		if ("GROUP_FOREIGN".equals(productType)) {
			return "出境跟团游";
		}
		if ("FREENESS".equals(productType)) {
			return "目的地自由行";
		}
		if ("FREENESS_LONG".equals(productType)) {
			return "长途自由行";
		}
		if ("FREENESS_FOREIGN".equals(productType)) {
			return "出境自由行";
		}
		if ("SELFHELP_BUS".equals(productType)) {
			return "自助巴士班";
		}
		if ("OTHER".equals(productType)) {
			return "其他";
		}
		return productType;
	}
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getUnpayedQuantity() {
		return unpayedQuantity;
	}

	public void setUnpayedQuantity(Long unpayedQuantity) {
		this.unpayedQuantity = unpayedQuantity;
	}

	public Long getUnpayedAmount() {
		return unpayedAmount;
	}

	public void setUnpayedAmount(Long unpayedAmount) {
		this.unpayedAmount = unpayedAmount;
	}

	public Long getPayedQuantity() {
		return payedQuantity;
	}

	public void setPayedQuantity(Long payedQuantity) {
		this.payedQuantity = payedQuantity;
	}

	public Long getPayedAmount() {
		return payedAmount;
	}

	public void setPayedAmount(Long payedAmount) {
		this.payedAmount = payedAmount;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}
	
}
