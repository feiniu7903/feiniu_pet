package com.lvmama.back.web.utils.insurance.dazhong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.vo.Constant;

public class DaZhongPolicy {
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String PHName;
	private String PlanID;
	private String DateStart;
	private String DateEnd;
	private List<DaZhongInsurance> Insured = new ArrayList<DaZhongInsurance>();
	
	public DaZhongPolicy(String PlanID, Date DateStart, Date DateEnd, List<InsInsurant> insurants) {
		this.PlanID = PlanID;
		this.DateStart = SDF.format(DateStart);
		this.DateEnd = SDF.format(DateEnd);
		for (InsInsurant insurant : insurants) {
			if (Constant.POLICY_PERSON.APPLICANT.name().equals(insurant.getPersonType())) {
				this.PHName = insurant.getName();
			}
			if (Constant.POLICY_PERSON.INSURANT.name().equals(insurant.getPersonType())) {
				Insured.add(new DaZhongInsurance(insurant.getName(), changeType(insurant.getCertType()) , insurant.getCertNo(), insurant.getBirthday()));
			}
		}
	}
	
	public String toJSONString() {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(PHName)) {
			sb.append("\"PHName\":\"" + PHName + "\",");
		}
		if (StringUtils.isNotBlank(PlanID)) {
			sb.append("\"PlanID\":\"" + PlanID + "\",");
		}
		if (StringUtils.isNotBlank(PHName)) {
			sb.append("\"DateStart\":\"" + DateStart + "\",");
		}
		if (StringUtils.isNotBlank(PHName)) {
			sb.append("\"DateEnd\":\"" + DateEnd + "\",");
		}
		if (null != Insured && !Insured.isEmpty()) {
			sb.append("\"Insured\":[");
			for (DaZhongInsurance insurance : Insured) {
				sb.append(insurance.toJSONString()).append(",");
			}
			sb.setLength(sb.length() - 1);
			sb.append("],");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return  "{" + sb.toString() + "}";
	}
	
	private String changeType(final String type) {
//		if ("ID_CARD".equals(type)) {
//			return "IDcard";
//		}
//		if ("HUZHAO".equals(type)) {
//			return "Passport";
//		}
		return "Officer";
	}
}
