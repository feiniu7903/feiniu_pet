package com.lvmama.comm.pet.vo.mark;

import com.lvmama.comm.pet.po.mark.MarkMembershipCard;

public class MarkMembershipCardDetails extends MarkMembershipCard {
	protected String channelName;
	protected String channelCode;
	
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	
	public String getChIsBindingDiscount() {
		if ("TRUE".equalsIgnoreCase(this.isBindingDiscount)) {
			return "已绑定";
		} else {
			return "未绑定";
		}
	}
	
	public String getChIsIsActivate() {
		if ("TRUE".equalsIgnoreCase(this.isActivate)) {
			return "已激活";
		} else {
			return "未激活";
		}
	}
	
	public String getDisabledBindingChannelButton() {
		if (null == this.bindingChannelTime) {
			return "false";
		} else {
			return "true";
		}
	}
	
	public String getDisabledBindingDiscountButton() {
		if (null == this.bindingDiscountTime) {
			return "false";
		} else {
			return "true";
		}		
	}
	
	public String getDisabledCancelButton() {
		if ("FALSE".equalsIgnoreCase(this.isActivate)) {
			return "false";
		} else {
			return "true";
		}		
	}
}
