package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;

/**
 * @author ShiHui
 */
public class PassProvider implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4532120457734801062L;
	private Long providerId;
	private String name;
	private String memo;
	private String separate;	//是否每一个订单子子项都独立申码
	private String sendSms;//是否由供应商发送短信
	private String autoPerform;
	private String processor;
	private String mergeSMS; //凭证短信是否合并发送
	private String supplierThread;
	private String reApplayWay;
	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSeparate() {
		return separate;
	}

	public void setSeparate(String separate) {
		this.separate = separate;
	}

	public boolean isSeparatelyApply() {
		return "true".equalsIgnoreCase(separate);
	}
	public boolean isSendSmsByProvider(){
		return "true".equalsIgnoreCase(this.sendSms);
	}
	public boolean isMergeSMS(){
		return "true".equalsIgnoreCase(this.mergeSMS);
	}
	
	public boolean isUseSameSerialNo(){
		return "true".equals(this.reApplayWay);
	}
	public boolean isGmedia() {
		return this.providerId==1;
	}
	
	public int compareTo(Object arg0 ) {
		if (arg0 instanceof PassProvider) {
			PassProvider sup = (PassProvider)arg0;
			if (providerId<sup.getProviderId()) {
				return -1;
			}else if(providerId==sup.getProviderId()) {
				return 0;
			}else {
				return 1;
			}
		}
		return -1;
	}
	public boolean equals(Object obj) {
		if (obj instanceof PassProvider) {
			PassProvider sup = (PassProvider) obj;
			if (this.providerId == null) {
				return sup.getProviderId() == null;
			} else {
				return providerId.longValue() == sup.getProviderId();
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		if (providerId != null)
			return providerId.hashCode();
		else
			return 0;
	}

	@Override
	public String toString() {
		if (providerId != null)
			return "PassProvider_" + providerId.toString();
		else
			return "PassProvider_null";
	}

	public String getSendSms() {
		return sendSms;
	}

	public void setSendSms(String sendSms) {
		this.sendSms = sendSms;
	}

	public String getAutoPerform() {
		return autoPerform;
	}

	public void setAutoPerform(String autoPerform) {
		this.autoPerform = autoPerform;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getMergeSMS() {
		return mergeSMS;
	}

	public void setMergeSMS(String mergeSMS) {
		this.mergeSMS = mergeSMS;
	}

	public String getSupplierThread() {
		return supplierThread;
	}

	public void setSupplierThread(String supplierThread) {
		this.supplierThread = supplierThread;
	}

	public String getReApplayWay() {
		return reApplayWay;
	}

	public void setReApplayWay(String reApplayWay) {
		this.reApplayWay = reApplayWay;
	}

}
