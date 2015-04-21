package com.lvmama.back.web.utils.insurance.sinosig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.utils.DateUtil;

import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.vo.Constant;

public class SinosigPolicyRequest extends SinosigPolicy {
	private Long orderId;
	private List<InsInsurant> insurants;
	private Date startDate;
	private Date endDate;
	private int days;
	private String productCode;
	private float preium;
	private String amount;
	
	/**
	 * 
	 * @param orderId 订单号
	 * @param insurants 保险人列表
	 * @param startDate 保险开始时间
	 * @param endDate 保险结束时间
	 * @param days 保险天数
	 * @param productCode 产品编号
	 * @param preium 保费(元)
	 * @param amount 保额
	 */
	public SinosigPolicyRequest(Long orderId, List<InsInsurant> insurants, Date startDate, Date endDate, int days, String productCode, String amount, float preium) {
		this.orderId = orderId;
		this.insurants = insurants;
		this.startDate = startDate;
		this.endDate = endDate;
		this.days = days;
		this.productCode = productCode;
		this.preium = preium;
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		Date now = new Date(System.currentTimeMillis());
		
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"gbk\"?>");
		sb.append("<INSURENCEINFO>");
		sb.append("<USERNAME>" + USERNAME + "</USERNAME>");
		sb.append("<PASSWORD>" + PASSWORD + "</PASSWORD>");
		sb.append("<ORDER>");
		sb.append("<ORDERID>" + orderId + "</ORDERID>");
		sb.append("<PAYTIME></PAYTIME>");
		sb.append("<PAYTYPE></PAYTYPE>");
		sb.append("<PAYID></PAYID>");
		int i = 1;
		for (InsInsurant insurant : insurants) {
			if (Constant.POLICY_PERSON.INSURANT.name().equalsIgnoreCase(insurant.getPersonType())) {
				sb.append("<POLICYINFO NUM=\"" + (i++) + "\">");
				sb.append("<POLICYNO></POLICYNO>");
				sb.append("<PRODUCTCODE>" + productCode + "</PRODUCTCODE>");
				sb.append("<PLANCODE></PLANCODE>");
				sb.append("<INSURDATE>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now) + "</INSURDATE>");
				sb.append("<INSURSTARTDATE>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.startDate) + "</INSURSTARTDATE>");
				sb.append("<INSURENDDATE>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endDate) + "</INSURENDDATE>");
				sb.append("<INSURPERIOD>" + this.days + "</INSURPERIOD>");
				sb.append("<PERIODFLAG>D</PERIODFLAG>");
				sb.append("<MULT>1</MULT>");
				sb.append("<AGREEMENTNO></AGREEMENTNO>");
				sb.append("<PREMIUM>" + getPreiumStr() + "</PREMIUM>");
				sb.append("<PAIDINPREMIUM></PAIDINPREMIUM>");
				sb.append("<TOTALDISCOUNT></TOTALDISCOUNT>");
				sb.append("<AMOUNT>" + amount + "</AMOUNT>");
				sb.append("<PROVINCE></PROVINCE>");
				sb.append("<REGION></REGION>");
				sb.append("<BENEFMODE>0</BENEFMODE>");
				sb.append("<OPERATOR></OPERATOR>");
				
				sb.append("<AIRLINEDATE></AIRLINEDATE>");
				sb.append("<AIRARRITIME></AIRARRITIME>");
				sb.append("<AIRLINENO></AIRLINENO>");
				sb.append("<ORIGIN></ORIGIN>");
				sb.append("<DESTINATION></DESTINATION>");
				sb.append("<AIRLINECOMPANY></AIRLINECOMPANY>");	

				sb.append("<TRAVELAGENCY></TRAVELAGENCY>");
				sb.append("<TOURROUTE></TOURROUTE>");
				sb.append("<TOURROUTETYPE></TOURROUTETYPE>");
				sb.append("<TRAVELPURPOSE></TRAVELPURPOSE>");
				sb.append("<TRAVELCOUNTRY></TRAVELCOUNTRY>");
				
				sb.append("<APPNTNAME>" + insurant.getName() + "</APPNTNAME>");
				sb.append("<APPNTSEX>" + ("M".equals(insurant.getSex()) ? "1" : "2") + "</APPNTSEX>");
				sb.append("<APPNTBIRTHDAY>" + (null != insurant.getBirthday() ? new SimpleDateFormat("yyyy-MM-dd").format(insurant.getBirthday()) : "")+ "</APPNTBIRTHDAY>");
				sb.append("<APPNTIDTYPE>" + changeCertType(insurant.getCertType()) + "</APPNTIDTYPE>");
				sb.append("<APPNTIDNO>" + insurant.getCertNo() + "</APPNTIDNO>");
				sb.append("<APPNTADDRESS></APPNTADDRESS>");
				sb.append("<APPNTPHONE></APPNTPHONE>");
				sb.append("<APPNTEMAIL></APPNTEMAIL>");
				sb.append("<APPNTMOBILE>" + insurant.getMobileNumber() + "</APPNTMOBILE>");	
				sb.append("<INSUREDLIST>");
				sb.append("<INSURED>");
				sb.append("<INSUREDNAME>" + insurant.getName() + "</INSUREDNAME>");
				sb.append("<RELATIONSHIP>10</RELATIONSHIP>");			
				sb.append("<INSUREDSEX>" + ("M".equals(insurant.getSex()) ? "1" : "2") + "</INSUREDSEX>");
				sb.append("<INSUREDBIRTHDAY>" + (null != insurant.getBirthday() ? new SimpleDateFormat("yyyy-MM-dd").format(insurant.getBirthday()) : "")+ "</INSUREDBIRTHDAY>");
				sb.append("<INSUREDIDNO>" + insurant.getCertNo() + "</INSUREDIDNO>");
				sb.append("<INSUREDIDTYPE>" + changeCertType(insurant.getCertType()) + "</INSUREDIDTYPE>");
				sb.append("<INSUREDADDRESS></INSUREDADDRESS>");
				sb.append("<INSUREDPHONE></INSUREDPHONE>");
				sb.append("<INSUREDEMAIL></INSUREDEMAIL>");
				sb.append("<INSUREDMOBILE>" + insurant.getMobileNumber() + "</INSUREDMOBILE>");
				sb.append("<INSUREDZIP></INSUREDZIP>");
				sb.append("</INSURED>");
				sb.append("</INSUREDLIST>");
				sb.append("</POLICYINFO>");
			}
		}
		sb.append("</ORDER>");
		sb.append("</INSURENCEINFO>");
		return sb.toString();
	}

	private String getPreiumStr() {
		String l = String.valueOf(preium);
		return l.substring(0, l.indexOf(".") == -1 ? l.length() : l.indexOf("."));
	}
	
	private String changeCertType(String certType) {
		if (Constant.CERT_TYPE.ID_CARD.name().equalsIgnoreCase(certType)) {
			return "10";
		}
		if (Constant.CERT_TYPE.HUZHAO.name().equalsIgnoreCase(certType)) {
			return "60";
		}
		return "";
		
	}
	
	public static void main(String[] args) {
		InsInsurant insurant = new InsInsurant();
		insurant.setName(new String("王二麻子"));
		insurant.setSex("M");
		insurant.setMobileNumber("13917677725");
		try {
			insurant.setBirthday(DateUtil.toDate("1977-06-03","yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		insurant.setCertType(Constant.CERT_TYPE.ID_CARD.name());
		insurant.setCertNo("310101197706032415");
		insurant.setPersonType(Constant.POLICY_PERSON.INSURANT.name());
		List<InsInsurant> insurants = new ArrayList<InsInsurant>();
		insurants.add(insurant);
		
		insurant = new InsInsurant();
		insurant.setName(new String("猫三狗四"));
		insurant.setSex("M");
		insurant.setMobileNumber("13917677725");
		try {
			insurant.setBirthday(DateUtil.toDate("1977-06-03","yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		insurant.setCertType(Constant.CERT_TYPE.ID_CARD.name());
		insurant.setCertNo("310101197706032417");
		insurant.setPersonType(Constant.POLICY_PERSON.INSURANT.name());
		insurants.add(insurant);		
		
		insurant = new InsInsurant();
		insurant.setName(new String("王二麻子"));
		insurant.setSex("M");
		insurant.setMobileNumber("13917677725");
		try {
			insurant.setBirthday(DateUtil.toDate("1977-06-03","yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		insurant.setCertType(Constant.CERT_TYPE.ID_CARD.name());
		insurant.setCertNo("310101197706032416");
		insurant.setPersonType(Constant.POLICY_PERSON.APPLICANT.name());
		insurants.add(insurant);		
		
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(DateUtil.toDate("2013-06-29 00:00:00", "yyyy-MM-dd HH:mm:SS"));
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(DateUtil.toDate("2013-06-29 23:59:59", "yyyy-MM-dd HH:mm:SS"));
		
		
		
		SinosigPolicyRequest policy = new SinosigPolicyRequest(1401L, insurants, startDate.getTime(), endDate.getTime(), 1, "PP231303", "460050", 29F);
		//policy = new SinosigPolicyRequest(1402L, insurants, startDate.getTime(), endDate.getTime(), 1, "PP231302", "250050", 17F);
		System.out.println(policy.toString());
		System.out.println(policy.request(policy.toString(), false));
//		policy = new SinosigPolicyRequest(401L, insurants, startDate.getTime(), endDate.getTime(), 1, "PP231302", "250050", 17F);
//		System.out.println(policy.request(policy.toString(), false));
//		policy = new SinosigPolicyRequest(402L, insurants, startDate.getTime(), endDate.getTime(), 1, "PP231303", "460050", 29F);
//		System.out.println(policy.request(policy.toString(), false));
//		policy = new SinosigPolicyRequest(403L, insurants, startDate.getTime(), endDate.getTime(), 1, "PP231304", "630050", 69F);
//		System.out.println(policy.request(policy.toString(), false));
//		policy = new SinosigPolicyRequest(1404L, insurants, startDate.getTime(), endDate.getTime(), 1, "PP231305", "500100", 95F);
//		System.out.println(policy.request(policy.toString(), false));
//		policy = new SinosigPolicyRequest(405L, insurants, startDate.getTime(), endDate.getTime(), 1, "PP231306", "500100", 95F);
//		System.out.println(policy.request(policy.toString(), false));
//		policy = new SinosigPolicyRequest(406L, insurants, startDate.getTime(), endDate.getTime(), 1, "PP231307", "500100", 95F);
//		System.out.println(policy.request(policy.toString(), false));
//		policy = new SinosigPolicyRequest(607L, insurants, startDate.getTime(), endDate.getTime(), 1, "PP231308", "900100", 171F);
//		System.out.println(policy.request(policy.toString(), false));
			
//		String response = policy.request(policy.toString(), false);
//		System.out.println(response);
		
//		if (response.indexOf("RETURN=\"true\"") != -1) {
//			
//			String policyNo = null;
//			int start = response.indexOf("POLICYNO=\"");
//			int end = response.substring(start + "POLICYNO=\"".length()).indexOf("\"");
//			if (-1 != start && -1 != end) {
//				policyNo = response.substring(start + "POLICYNO=\"".length() , (start +"POLICYNO=\"".length() + end));
//			}
//			System.out.println("policyNo:" + policyNo);
//		} else {
//			String msg = null;
//			int start = response.indexOf("INFO=\"");
//			int end = response.substring(start + "INFO=\"".length()).indexOf("\"");
//			if (-1 != start && -1 != end) {
//				msg = response.substring(start + "INFO=\"".length() , start + "INFO=\"".length() + end);
//			}
//			System.out.println("msg:" + msg);
//		}
		
	}
}
