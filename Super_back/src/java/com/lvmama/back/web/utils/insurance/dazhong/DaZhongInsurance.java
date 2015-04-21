package com.lvmama.back.web.utils.insurance.dazhong;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DaZhongInsurance {
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	private String Name;
    private String IdNoType; //":"IDcard",	被保险人证件类型('IDcard'->身份证,'Passport'->护照,'Officer'->其他)	必填
    private String IdNo; //":"664466787545545",	被保险人证件号码(只对Idcard会有严格的校验)	必填
    private String BirthDt; //":"1987-01-01",	被保险人生日(YYYY-MM-DD)(如果证件类型为Idcard,则以证件号码中的生日为准,此时的生日为可选项)	必填
	
    public DaZhongInsurance(String Name, String IdNoType, String IdNo, Date BirthDt) {
    	this.Name = Name;
    	this.IdNo = IdNo;
    	this.IdNoType = IdNoType;
    	this.BirthDt = SDF.format(BirthDt); 
    }
   
	public String toJSONString() {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(Name)) {
			sb.append("\"Name\":\"" + Name + "\",");
		}
		if (StringUtils.isNotBlank(IdNoType)) {
			sb.append("\"IdNoType\":\"" + IdNoType + "\",");
		}
		if (StringUtils.isNotBlank(IdNo)) {
			sb.append("\"IdNo\":\"" + IdNo + "\",");
		}
		if (StringUtils.isNotBlank(BirthDt)) {
			sb.append("\"BirthDt\":\"" + BirthDt + "\",");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return  "{" + sb.toString() + "}";
	}
}
