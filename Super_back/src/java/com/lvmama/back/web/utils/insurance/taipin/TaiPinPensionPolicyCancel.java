package com.lvmama.back.web.utils.insurance.taipin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaiPinPensionPolicyCancel extends TaiPinPensionPolicy {
	private Long orderId;
	private String policyNo;
	
	public TaiPinPensionPolicyCancel(Long orderId, String policyNo) {
		this.orderId = orderId;
		this.policyNo = policyNo;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		sb.append("<REQUEST>");
		sb.append("<DIST>");
		sb.append("<DLBH>" + DLBH + "</DLBH>");
		sb.append("<TBBXDDH>" + orderId + "</TBBXDDH>");
		sb.append("</DIST>");
		sb.append("<BUSI>");
		sb.append("<TBRQ>" + new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis())) + "</TBRQ>");
		sb.append("<TBSJ>" + new SimpleDateFormat("HHmmss").format(new Date(System.currentTimeMillis())) + "</TBSJ>");
		sb.append("<CZDM>2</CZDM>");
		sb.append("<BDH>" + policyNo +"</BDH>");
		sb.append("<OLD_TBBXDDH>" + orderId + "</OLD_TBBXDDH>");
		sb.append("</BUSI>");
		sb.append("</REQUEST>");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		TaiPinPensionPolicyCancel policy = new TaiPinPensionPolicyCancel(123L, "002079290697388");
//		System.out.println(text);
//		System.out.println(new String((text + "230bfa3c85c4713c718994f6e32e12lm").getBytes("GBK")));
//		System.out.println(MD5.md5(new String((text + "230bfa3c85c4713c718994f6e32e12lm").getBytes("GBK"))).toLowerCase());
			
		System.out.println(policy.request(policy.toString()));
		
	}	
}
