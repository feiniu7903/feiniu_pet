package com.lvmama.back.web.utils.insurance.pinan;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.pet.po.ins.InsInsurant;

public class EaiAhsXml implements PinAnRequest {
	private Header header;
	private Request request;
	
	public static EaiAhsXml getInstanceForRequest(double price, Date insuranceBeginTime, Date insuranceEndTime, String applyTime, String productCode, List<InsInsurant> insurants) {
		return new EaiAhsXml(price,insuranceBeginTime,insuranceEndTime,applyTime,productCode,insurants);
	}
	
	public static EaiAhsXml getInstanceForCancel(String tranCode, String bkOrgnSril, String policyNo, String validateCode) {
		return new EaiAhsXml(tranCode, bkOrgnSril,policyNo,validateCode);
	}
	
	private EaiAhsXml(double price, Date insuranceBeginTime, Date insuranceEndTime, String applyTime, String productCode, List<InsInsurant> insurants) {
		header = new Header();
		request = new Request(price * (insurants.size() - 1), insuranceBeginTime, insuranceEndTime, applyTime, productCode, insurants);
	}
	
	private EaiAhsXml(String tranCode, String bkOrgnSril, String policyNo, String validateCode) {
		header = new Header(tranCode, bkOrgnSril);
		request = new Request(policyNo, validateCode);
	}
	
	@Override
	public String toXMLString() {
		StringBuffer buff = new StringBuffer();
		buff.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buff.append("<eaiAhsXml>");
		buff.append(header.toXMLString());
		buff.append(request.toXMLString());
		buff.append("</eaiAhsXml>");
		return buff.toString();
	}
	
//	public static void main(String[] args) {
//		Date now = new Date();
//		Date insuranceBeginTime = new Date();
//		insuranceBeginTime.setDate(10);
//		Date insuranceEndTime = new Date();
//		insuranceEndTime.setDate(15);
//		int applyTime = 6;
//		String productCode = "00722";
//		
//		List<InsInsurant> insurants = new ArrayList<InsInsurant>();
//		InsInsurant insurant_1 = new InsInsurant();
//		insurant_1.setName("郑致力");
//		insurant_1.setSex("M");
//		insurant_1.setBirthday(new Date());
//		insurant_1.setCertNo("310101197706032415");
//		insurant_1.setCertType("01");
//		InsInsurant insurant_2 = new InsInsurant();
//		insurant_2.setName("高婕");
//		insurant_2.setSex("F");
//		insurant_2.setBirthday(new Date());
//		insurant_2.setCertNo("310101198301012415");
//		insurant_2.setCertType("01");
//		insurants.add(insurant_1);
//		insurants.add(insurant_2);
//		double totalPremium = 60 * insurants.size();
//		
//		System.out.println(new EaiAhsXml(totalPremium, insuranceBeginTime, insuranceEndTime, applyTime, productCode, insurants).toXMLString());
//	}
	
}
