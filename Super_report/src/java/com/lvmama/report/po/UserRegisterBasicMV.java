package com.lvmama.report.po;


public class UserRegisterBasicMV {

	private String registerTime;

	private String registerChannel;

	private String subRegisterChannelId;

	private String subRegisterChannel;

	private Long newUserQuantity;

	private Long newPayedUserQuantity;

	private Long new2PayedUserQuantity;

	private Long newPayedUserAmount;

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getRegisterChannel() {
		return registerChannel;
	}

	public void setRegisterChannel(String registerChannel) {
		this.registerChannel = registerChannel;
	}

	public String getSubRegisterChannelId() {
		if("0".equals(subRegisterChannelId)){
			return null;
		}
		return subRegisterChannelId;
	}

	public void setSubRegisterChannelId(String subRegisterChannelId) {
		this.subRegisterChannelId = subRegisterChannelId;
	}

	public String getSubRegisterChannel() {
		if(this.subRegisterChannel==null && subRegisterChannelId==null||"null".equals(subRegisterChannelId)){
			return "前台注册";
		}
		return subRegisterChannel;
	}

	public void setSubRegisterChannel(String subRegisterChannel) {
		this.subRegisterChannel = subRegisterChannel;
	}

	public Long getNewUserQuantity() {
		if(newUserQuantity==null){
			return 0l;
		}else
			return newUserQuantity;
	}

	public void setNewUserQuantity(Long newUserQuantity) {
		this.newUserQuantity = newUserQuantity;
	}

	public Long getNewPayedUserQuantity() {
		if(newPayedUserQuantity==null){
			return 0l;
		}else
			return newPayedUserQuantity;
	}

	public void setNewPayedUserQuantity(Long newPayedUserQuantity) {
		this.newPayedUserQuantity = newPayedUserQuantity;
	}

	public Long getNew2PayedUserQuantity() {
		if(new2PayedUserQuantity==null){
			return 0l;
		}else
			return new2PayedUserQuantity;
	}

	public void setNew2PayedUserQuantity(Long new2PayedUserQuantity) {
		this.new2PayedUserQuantity = new2PayedUserQuantity;
	}

	public Long getNewPayedUserAmount() {
		return newPayedUserAmount;
	}

	public void setNewPayedUserAmount(Long newPayedUserAmount) {
		this.newPayedUserAmount = newPayedUserAmount;
	}
	
	public Long getNewPayedUserAmountYuan() {
		if(newPayedUserAmount!=null){
			return newPayedUserAmount/100;
		}
		return 0l;
	}
}
