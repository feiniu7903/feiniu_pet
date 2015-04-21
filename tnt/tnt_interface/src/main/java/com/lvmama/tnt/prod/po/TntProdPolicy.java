package com.lvmama.tnt.prod.po;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.user.po.TntUser;

/**
 * B2B分销产品策略表
 * 
 * @author gaoxin
 * @version 1.0
 */
public class TntProdPolicy implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * tntProductId
	 */
	private java.lang.Long tntPolicyId;
	/**
	 * 产品Id
	 */
	private java.lang.Long productId;
	/**
	 * 类别Id
	 */
	private java.lang.Long branchId;
	/**
	 * 分销商Id/渠道Id
	 */
	private java.lang.Long targetId;
	/**
	 * target类型 （分销商/渠道）
	 */
	private java.lang.String targetType;
	/**
	 * 策略类型
	 */
	private java.lang.String policyType;
	/**
	 * 折扣点/利润点（1.1% 为 110）
	 */
	private java.lang.Long discount;

	private String productType;

	private TntProduct tntProdBlacklist;

	/**
	 * 临时字段 是否分销
	 */
	private boolean canDist = true;

	/**
	 * 临时字段 是否有销售返佣
	 */
	private boolean rebate;

	/**
	 * 临时字段 用户产品分销商策略查询
	 */
	private TntUser user;
	private String userId;// 临时字段分销商userId
	private String realName;// 临时字段分销商名称
	private String companyTypeId, companyName;// 临时字段分销商类型，公司名称
	private Long channelId;// 临时字段渠道类型

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyTypeId() {
		return companyTypeId;
	}

	public void setCompanyTypeId(String companyTypeId) {
		this.companyTypeId = companyTypeId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public boolean isRebate() {
		return rebate;
	}

	public TntUser getUser() {
		return user;
	}

	public void setUser(TntUser user) {
		this.user = user;
	}

	public void setRebate(boolean rebate) {
		this.rebate = rebate;
	}

	public boolean isCanDist() {
		return canDist;
	}

	public void setCanDist(boolean canDist) {
		this.canDist = canDist;
	}

	public TntProdPolicy() {
	}

	public TntProdPolicy(java.lang.Long tntPolicyId) {
		this.tntPolicyId = tntPolicyId;
	}

	public void setProductId(java.lang.Long value) {
		this.productId = value;
	}

	public java.lang.Long getProductId() {
		return this.productId;
	}

	public void setBranchId(java.lang.Long value) {
		this.branchId = value;
	}

	public java.lang.Long getBranchId() {
		return this.branchId;
	}

	public void setTargetId(java.lang.Long value) {
		this.targetId = value;
	}

	public java.lang.Long getTargetId() {
		return this.targetId;
	}

	public void setTargetType(java.lang.String value) {
		this.targetType = value;
	}

	public java.lang.String getTargetType() {
		return this.targetType;
	}

	public void setPolicyType(java.lang.String value) {
		this.policyType = value;
	}

	public java.lang.String getPolicyType() {
		return this.policyType;
	}

	public void setDiscount(java.lang.Long value) {
		this.discount = value;
	}

	public java.lang.Long getDiscount() {
		return this.discount;
	}

	public String getDiscountY() {
		return PriceUtil.convertToYuan(getDiscount()) + "";
	}

	public float getFloatDiscountY() {
		return PriceUtil.convertToYuan(getDiscount());
	}

	public void setDiscountY(String discountY) {
		this.setDiscount(PriceUtil.convertToFen(discountY));
	}

	public TntProdPolicy(String targetType, Long targetId, String productType) {
		this.targetType = targetType;
		this.targetId = targetId;
		this.productType = productType;
	}

	public TntProduct getTntProdBlacklist() {
		return tntProdBlacklist;
	}

	public void setTntProdBlacklist(TntProduct tntProdBlacklist) {
		this.tntProdBlacklist = tntProdBlacklist;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPriceRule() {
		String rule = TntConstant.PROD_POLICY_TYPE.getRule(policyType);
		return rule != null ? String.format(rule, getDiscountY()) : "";
	}

	public String getCalPriceRule() {
		String rule = TntConstant.PROD_POLICY_TYPE.getCalRule(policyType);
		Float discount = getFloatDiscountY();
		discount = discount != null && discount > 0 ? discount / 100 : 0;
		return rule != null ? String.format(rule, discount + "") : "";
	}

	public java.lang.Long getTntPolicyId() {
		return tntPolicyId;
	}

	public void setTntPolicyId(java.lang.Long tntPolicyId) {
		this.tntPolicyId = tntPolicyId;
	}

	public String getCompanyName() {
		return companyName != null ? companyName : "个人";
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "TntProdPolicy [tntPolicyId=" + tntPolicyId + ", productId="
				+ productId + ", branchId=" + branchId + ", targetId="
				+ targetId + ", targetType=" + targetType + ", policyType="
				+ policyType + ", discount=" + discount + ", productType="
				+ productType + ", tntProdBlacklist=" + tntProdBlacklist
				+ ", canDist=" + canDist + ", rebate=" + rebate + ", user="
				+ user + ", userId=" + userId + ", realName=" + realName
				+ ", companyTypeId=" + companyTypeId + ", channelId="
				+ channelId + "]";
	}

}
