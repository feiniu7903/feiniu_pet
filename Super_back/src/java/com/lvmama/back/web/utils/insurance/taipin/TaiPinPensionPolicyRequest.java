package com.lvmama.back.web.utils.insurance.taipin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.vo.Constant;

public class TaiPinPensionPolicyRequest extends TaiPinPensionPolicy {
	private Long orderId;
	private Date dateStart;
	private Date dateEnd;
	/**
	 * 险种计划代码
	 */
	private String xzdm;
	/**
	 * 计划代码
	 */
	private String jhdm;
	/**
	 * 保费
	 */
	private long jsbf;
	List<InsInsurant> insurants;
	
	public TaiPinPensionPolicyRequest(final Long orderId, Date DateStart, Date DateEnd, List<InsInsurant> insurants, String xzdm, String jhdm, Long jsbf) {
		this.orderId = orderId;
		this.dateStart = DateStart;
		this.dateEnd = DateEnd;
		this.xzdm = xzdm;
		this.jhdm = jhdm;
		this.jsbf = jsbf;
		this.insurants = insurants;
	}
	
	@Override
	public String toString() {
		Date now = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		sb.append("<REQUEST>");
		sb.append("<DIST>");
		sb.append("<DLBH>" + DLBH + "</DLBH>");
		sb.append("<TBBXDDH>" + orderId + System.currentTimeMillis() +"</TBBXDDH>");
		sb.append("</DIST>");
		sb.append("<BUSI>");
		sb.append("<DZHM></DZHM>");
		sb.append("<TBRQ>" + new SimpleDateFormat("yyyyMMdd").format(now) + "</TBRQ>");
		sb.append("<TBSJ>" + new SimpleDateFormat("HHmmss").format(now) +"</TBSJ>");
		sb.append("<TBKHLX>1</TBKHLX>");
		sb.append("<TBR>");
		for (InsInsurant insurant : insurants) {
			if (Constant.POLICY_PERSON.APPLICANT.name().equalsIgnoreCase(insurant.getPersonType())) {
				sb.append("<TBRXM>" + insurant.getName() +"</TBRXM>");
				sb.append("<TBRXB>" + ("M".equals(insurant.getSex()) ? "1" : "2") +"</TBRXB>");
				sb.append("<TBRSR>" + (null != insurant.getBirthday() ? new SimpleDateFormat("yyyyMMdd").format(insurant.getBirthday()) : "")+ "</TBRSR>");
				sb.append("<TBRZJLX>" + changeCertType(insurant.getCertType()) +"</TBRZJLX>");
				sb.append("<TBRZJH>" + insurant.getCertNo() + "</TBRZJH>");
				sb.append("<TBRSJH>" + (StringUtils.isEmpty(insurant.getMobileNumber()) ? "" : insurant.getMobileNumber()) +"</TBRSJH>");
				sb.append("<TBRYXDZ></TBRYXDZ>");
				sb.append("<TBRADDR></TBRADDR>");
				sb.append("<TBRTEL></TBRTEL>");					
			}		
		}
		sb.append("</TBR>");
		sb.append("<COMP>");
		sb.append("<DWMC></DWMC>");
		sb.append("<LXRMC></LXRMC>");
		sb.append("<LXRSJH></LXRSJH>");
		sb.append("<LXRDZYJ></LXRDZYJ>");
		sb.append("</COMP>");
		sb.append("<BBRLIST>");
		for (InsInsurant insurant : insurants) {
			if (Constant.POLICY_PERSON.INSURANT.name().equalsIgnoreCase(insurant.getPersonType())) {
				sb.append("<BBR>");
				try {
					sb.append("<BBXRXM>" + insurant.getName() + "</BBXRXM>");	
				} catch (Exception e) {
					sb.append("<BBXRXM/>");
				}
				sb.append("<BBXRXB>" + ("M".equals(insurant.getSex()) ? "1" : "2") + "</BBXRXB>");
				sb.append("<BBXRSR>" + (null != insurant.getBirthday() ? new SimpleDateFormat("yyyyMMdd").format(insurant.getBirthday()) : "") + "</BBXRSR>");
				//sb.append("<BBXRSR>" + "19770603" + "</BBXRSR>");
				sb.append("<ZJLX>" + changeCertType(insurant.getCertType()) + "</ZJLX>");
				sb.append("<BBXRZJH>" + insurant.getCertNo() + "</BBXRZJH>");
				sb.append("<BBXRSJH>" + (StringUtils.isEmpty(insurant.getMobileNumber()) ? "" : insurant.getMobileNumber()) + "</BBXRSJH>");
				sb.append("<BBXRYXDZ></BBXRYXDZ>");
				sb.append("<OCCUP></OCCUP>");
				sb.append("<BBXRADDR></BBXRADDR>");
				sb.append("<BBXRTEL></BBXRTEL>");
				sb.append("<BENE>");
				sb.append("<SYRXM></SYRXM>");
				sb.append("</BENE>");
				sb.append("</BBR>");
			}
		}
		sb.append("</BBRLIST>");
		sb.append("<TRIP>");
		sb.append("<HBH></HBH >");
		sb.append("<HBRQ></HBRQ >");
		sb.append("<LXMDD></LXMDD>");
		sb.append("<FCHBH></FCHBH>");
		sb.append("<FCHBRQ></FCHBRQ>");
		sb.append("<KPHM></KPHM>");
		sb.append("<CFD></CFD>");
		sb.append("<CGMD></CGMD>");
		sb.append("</TRIP>");
		sb.append("<PT>");
		sb.append("<XZDM>" + this.xzdm + "</XZDM>");
		sb.append("<XZMC></XZMC>");
		sb.append("<TBSL>1</TBSL>");
		sb.append("<JSBF>" + jsbf * (insurants.size() - 1)+ "</JSBF>");
		sb.append("<BXJE>" + "</BXJE>");
		sb.append("<BXZRQSSJ>" + new SimpleDateFormat("yyyyMMddHHmmss").format(dateStart) + "</BXZRQSSJ>");
		sb.append("<BXZRZZSJ>" + new SimpleDateFormat("yyyyMMddHHmmss").format(dateEnd) + "</BXZRZZSJ>");
		sb.append("<JHDM>" + "2000" + "</JHDM>");
		sb.append("<CZDM>1</CZDM>");
		sb.append("<BDZT></BDZT>");
		sb.append("</PT>");
		sb.append("<LOAN>");
		sb.append("<DEBTOR/>");
		sb.append("<NO></NO>");
		sb.append("<AMOUNT></AMOUNT>");
		sb.append("<BGN></BGN>");
		sb.append("<END></END>");
		sb.append("<ACCT/>");
		sb.append("<BANK_NAME></BANK_NAME>");
		sb.append("</LOAN>");
		sb.append("</BUSI>");
		sb.append("</REQUEST>");
		return sb.toString();
	}
	
	private String changeCertType(String certType) {
		if (Constant.CERT_TYPE.ID_CARD.name().equalsIgnoreCase(certType)) {
			return "1";
		}
		if (Constant.CERT_TYPE.JUNGUAN.name().equalsIgnoreCase(certType)) {
			return "2";
		}
		if (Constant.CERT_TYPE.HUZHAO.name().equalsIgnoreCase(certType)) {
			return "3";
		}
		return "9";
		
	}
	
	public static void main(String[] args) {
		InsInsurant insurant = new InsInsurant();
		insurant.setName(new String("郑致力"));
		insurant.setSex("M");
		insurant.setBirthday(new Date());
		insurant.setCertType(Constant.CERT_TYPE.ID_CARD.name());
		insurant.setCertNo("310101197706032415");
		insurant.setMobileNumber("13917677725");
		insurant.setPersonType(Constant.POLICY_PERSON.INSURANT.name());
		List<InsInsurant> insurants = new ArrayList<InsInsurant>();
		insurants.add(insurant);
		
		insurant = new InsInsurant();
		insurant.setName(new String("郑致力"));
		insurant.setSex("M");
		insurant.setBirthday(new Date());
		insurant.setCertType(Constant.CERT_TYPE.ID_CARD.name());
		insurant.setCertNo("310101197706032416");
		insurant.setMobileNumber("13917677725");
		insurant.setPersonType(Constant.POLICY_PERSON.INSURANT.name());
		insurants.add(insurant);
		
		insurant = new InsInsurant();
		insurant.setName(new String("郑致力"));
		insurant.setSex("M");
		insurant.setBirthday(new Date());
		insurant.setCertType(Constant.CERT_TYPE.ID_CARD.name());
		insurant.setCertNo("310101197706032415");
		insurant.setMobileNumber("13917677725");
		insurant.setPersonType(Constant.POLICY_PERSON.APPLICANT.name());
		insurants.add(insurant);
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(new Date(System.currentTimeMillis()));
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(new Date(System.currentTimeMillis()));
		endDate.add(Calendar.DAY_OF_MONTH, 1);
		TaiPinPensionPolicyRequest policy = new TaiPinPensionPolicyRequest(123L, startDate.getTime(), endDate.getTime(), insurants, "1", "2000", 1300L);
		
		System.out.println(policy.toString());	
		System.out.println(policy.request2(policy.toString()));
		
	}
}
