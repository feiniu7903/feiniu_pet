package com.lvmama.back.web.utils.insurance.pinan;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Header implements PinAnRequest {
	private static SimpleDateFormat DateSDF = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat TimeSDF = new SimpleDateFormat("HH:mm:ss");
	
	private String tranCode;
	private String bkOrgnSril;
	
	public Header() {}
	
	public Header(String tranCode, String bkOrgnSril) {
		this.tranCode = tranCode;
		this.bkOrgnSril = bkOrgnSril;
	}
	
	@Override
	public String toXMLString() {
		Date now = new Date();
		StringBuffer buff = new StringBuffer();
		buff.append("<Header>");
		if (null == tranCode) {
			buff.append("<TRAN_CODE>100083</TRAN_CODE>");
		} else {
			buff.append("<TRAN_CODE>" + tranCode + "</TRAN_CODE>");
		}
		buff.append("<BANK_CODE>9929</BANK_CODE>");
		buff.append("<BRNO>000000</BRNO>");
		buff.append("<TELLERNO/>");
		buff.append("<BK_ACCT_DATE>" + DateSDF.format(now) + "</BK_ACCT_DATE>");
		buff.append("<BK_ACCT_TIME>" + TimeSDF.format(now) + "</BK_ACCT_TIME>");
		buff.append("<BK_SERIAL>" + System.currentTimeMillis() * 1000 + (int) Math.ceil((Math.random() * 1000)) + "</BK_SERIAL>");
		if (null == bkOrgnSril) {
			buff.append("<BK_ORGN_SRIL/>");
		} else {
			buff.append("<BK_ORGN_SRIL>" + bkOrgnSril + "</BK_ORGN_SRIL>");
		}
		buff.append("<BK_TRAN_CHNL>WEB</BK_TRAN_CHNL>");
		buff.append("<REGION_CODE>000000</REGION_CODE>");		
		buff.append("</Header>");
		return buff.toString();
	}
}
