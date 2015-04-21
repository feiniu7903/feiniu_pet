package com.lvmama.tnt.user.po;

/**
 * 分销商企业类型
 * 
 * @author gaoxin
 * 
 */
public class TntCompanyType implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	
	public static final String PERSON_TYPE_CODE = "PERSON";

	/**
	 * 渠道类型
	 */
	private Long channelId;

	private Long companyTypeId;

	private String companyTypeName;

	private String companyTypeCode;

	/**
	 * 供销商数量 不持久化到数据库，根据汇总获取
	 */
	private Integer total;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public TntCompanyType() {
	}

	public void setCompanyTypeId(Long value) {
		this.companyTypeId = value;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Long getCompanyTypeId() {
		return this.companyTypeId;
	}

	public void setCompanyTypeName(String value) {
		this.companyTypeName = value;
	}

	public String getCompanyTypeName() {
		return this.companyTypeName;
	}

	public void setCompanyTypeCode(String value) {
		this.companyTypeCode = value;
	}

	public String getCompanyTypeCode() {
		return this.companyTypeCode;
	}

	@Override
	public String toString() {
		return "TntCompanyType [channelId=" + channelId + ",companyTypeId="
				+ companyTypeId + ", companyTypeName=" + companyTypeName
				+ ", companyTypeCode=" + companyTypeCode + "]";
	}

}
