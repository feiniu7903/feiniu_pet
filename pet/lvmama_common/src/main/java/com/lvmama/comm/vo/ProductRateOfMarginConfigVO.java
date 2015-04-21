package com.lvmama.comm.vo;
/**
 * 毛利率配置
 * @author ranlongfei
 * @version
 */
public class ProductRateOfMarginConfigVO {

	private String subProductType;
	
	private Long rate;
	
	private String emailAddress;

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public Long getRate() {
		return rate;
	}

	public void setRate(Long rate) {
		this.rate = rate;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
