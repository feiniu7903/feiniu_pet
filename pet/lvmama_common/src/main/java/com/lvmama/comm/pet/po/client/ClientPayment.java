package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

public class ClientPayment implements Serializable{

	private static final long serialVersionUID = 4833800011591311171L;

	private String paymentTitle;
	
	private String paymentTarget;


	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}


	public String getPaymentTitle() {
		return paymentTitle;
	}

	public void setPaymentTitle(String paymentTitle) {
		this.paymentTitle = paymentTitle;
	}
}
