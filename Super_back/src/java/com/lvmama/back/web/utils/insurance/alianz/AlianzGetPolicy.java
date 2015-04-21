package com.lvmama.back.web.utils.insurance.alianz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.XStream;

/**
 * 安联的投保实体Bean
 * @author Brian
 *
 */
public class AlianzGetPolicy {
	private static final SimpleDateFormat SDF_TRANSDATE = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat SDF_TRANSTIME = new SimpleDateFormat("yyyyMMddHHmmSS");
	
	private final String method = "getPolicy";
	private final String type = "request";
	private String batchNo;
	private final String infoType = "0002";
	private String transDate;
	private List<AlianzPolicyRecord> dataSet = new ArrayList<AlianzPolicyRecord>();
	private String transTime;
	private String merchantID = "";
	private String commentRes = "";
	private String reserved1;
	private String reserved2;
	
	public AlianzGetPolicy(final String batchNo, final AlianzPolicyRecord policy, final String merchantID) {
		Date now = new Date();
		this.batchNo = batchNo;
		transDate = SDF_TRANSDATE.format(now);
		dataSet.add(policy);
		transTime = SDF_TRANSTIME.format(now);
		this.merchantID = merchantID;
	}
	
	public String toXML() {
		XStream xstream = new XStream();
		xstream.alias("message", AlianzGetPolicy.class);
		xstream.alias("record", AlianzPolicyRecord.class);
		xstream.aliasAttribute(AlianzGetPolicy.class, "method", "method");
		xstream.aliasAttribute(AlianzGetPolicy.class, "type", "type");
		return xstream.toXML(this);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(new AlianzGetPolicy("123", new AlianzPolicyRecord("ASCY_1","20111001","郑","致力","M","310101197706032415","13917677725"), "LVMAMA").toXML());
	}
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}


	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getCommentRes() {
		return commentRes;
	}
	public void setCommentRes(String commentRes) {
		this.commentRes = commentRes;
	}
	public String getReserved1() {
		return reserved1;
	}
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	public String getReserved2() {
		return reserved2;
	}
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	public String getInfoType() {
		return infoType;
	}
}
