package com.lvmama.report.po;

import com.lvmama.comm.vo.Constant;




/**
 * 订单转化分析
 * @author yangchen
 */
public class OrderTransformBasiceMV {
	/** 产品Id **/
	private Long productId;
	/** 产品名称 **/
	private String productName;
	/** 产品类型 **/
	private String productType;
	/** 前台订单数 **/
	private Long frontOrder;
	/** 前台已支付数 **/
	private Long frontPayed;
	/** 后台订单数 **/
	private Long backOrder;
	/** 后台支付数 **/
	private Long backPayed;
	/** 分公司 **/
	private String filialeName;
	/** 产品经理 **/
	private String realName;

	/**
	 * 前台订单转化率 订单转化率0.15(保留2位小数)
	 * @return 前台订单转化率
	 * */
	public String getFPercentOfConvert() {
		if (frontOrder == 0) {
			return "0.0%";
		} else {
			String str = (this.frontPayed * 100.0 / this.frontOrder) + "";
			str = str.substring(0, str.indexOf(".") + 2);
			return str + "%";
		}
	}

	/**
	 * 后台台订单转化率 订单转化率0.15(保留2位小数)
	 * @return 后台订单转化率
	 */
	public String getBPercentOfConvert() {
		if (backOrder == 0) {
			return "0.0%";
		} else {
			String str = (this.backPayed * 100.0 / this.backOrder) + "";
			str = str.substring(0, str.indexOf(".") + 2);
			return str + "%";
		}
	}
	/**
	 * 计算平均支付转化率
	 * @return 平均支付转化率
	 */
	public String getAvgPercentOfConvert() {
		if ((frontOrder + backOrder) == 0) {
			return "0.0%";
		} else {
			String str = ((this.frontPayed + this.backPayed) * 100.0 / (this.frontOrder + this.backOrder))
					+ "";
			str = str.substring(0, str.indexOf(".") + 2);
			return str + "%";
		}
	}

	public String getZhProductType() {
		return Constant.PRODUCT_TYPE.getCnName(productType);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(final Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(final String productType) {
		this.productType = productType;
	}

	public Long getFrontOrder() {
		return frontOrder;
	}

	public void setFrontOrder(final Long frontOrder) {
		this.frontOrder = frontOrder;
	}

	public Long getFrontPayed() {
		return frontPayed;
	}

	public void setFrontPayed(final Long frontPayed) {
		this.frontPayed = frontPayed;
	}

	public Long getBackOrder() {
		return backOrder;
	}

	public void setBackOrder(final Long backOrder) {
		this.backOrder = backOrder;
	}

	public Long getBackPayed() {
		return backPayed;
	}

	public void setBackPayed(final Long backPayed) {
		this.backPayed = backPayed;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(final String filialeName) {
		this.filialeName = filialeName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(final String realName) {
		this.realName = realName;
	}
	
	public String getZhFilialeName()
	{
		return Constant.FILIALE_NAME.getCnName(filialeName); 
	}
}
