package com.lvmama.back.web.utils.insurance.sinosig;

public class SinosigPolicyCancel extends SinosigPolicy {
	/**
	 * 保单号
	 */
	private String policyNo;
	
	public SinosigPolicyCancel(String policyNo) {
		this.policyNo = policyNo;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		sb.append("<INSURENCEINFO>");
		sb.append("<USERNAME>" + USERNAME + "</USERNAME>");
		sb.append("<PASSWORD>" + PASSWORD + "</PASSWORD>");
		sb.append("<POLICYNO>" + "</POLICYNO>");
		sb.append("<ORDERID>" + policyNo + "</ORDERID>");
		sb.append("</INSURENCEINFO>");		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		SinosigPolicyCancel policy = new SinosigPolicyCancel("1401");
			
		System.out.println(policy.request(policy.toString(),true));
		
	}	
}
