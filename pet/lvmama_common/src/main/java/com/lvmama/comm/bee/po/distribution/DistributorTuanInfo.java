package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.lvmama.comm.vo.Constant.PAYMENT_TARGET;
/**
 * 团购预约分销商
 * @author gaoxin
 *
 */
public class DistributorTuanInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long distributorTuanInfoId;
	
	private String distributorCode;
	
	private String distributorName;
	/**
	 * 渠道类型
	 */
	private String channelType;
	/**
	 * 支付渠道
	 */
	private String paymentCode;
	/**
	 * 备注
	 */
	private String memo;
	
	/**
	 * 分销渠道类型前缀
	 * @author gaoxin
	 */
	public static enum PAYMENT_PREFIX {
		/** 导码 */
		DIST_DAOMA_("分销导码"),
		/** 预约  */
		DIST_YUYUE_("分销预约");
		
		private String cnName;
		PAYMENT_PREFIX(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCnName(String code){
			for(PAYMENT_TARGET item:PAYMENT_TARGET.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
	}
	public Long getDistributorTuanInfoId() {
		return distributorTuanInfoId;
	}
	public void setDistributorTuanInfoId(Long distributorTuanInfoId) {
		this.distributorTuanInfoId = distributorTuanInfoId;
	}
	public String getDistributorCode() {
		return distributorCode;
	}
	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getZnChannelType() {
		if("DIST_YUYUE".equalsIgnoreCase(channelType)){
			return "分销预约平台";
		}
		return "批量导码";
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("DistributorTuanInfoId",getDistributorTuanInfoId())
			.append("DistributorCode",getDistributorCode())
			.append("DistributorName",getDistributorName())
			.append("ChannelType",getChannelType())
			.append("PaymentCode",getPaymentCode())
			.append("Memo",getMemo())
			.toString();
	}
	
	
	public boolean equals(Object obj) {
		if(obj instanceof DistributorTuanInfo == false) return false;
		if(this == obj) return true;
		DistributorTuanInfo other = (DistributorTuanInfo)obj;
		return new EqualsBuilder()
			.append(getDistributorTuanInfoId(),other.getDistributorTuanInfoId())
			.isEquals();
	}
	
}
