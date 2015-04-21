package com.lvmama.pet.payment.phonepay;

public class RequestHeadObject {
	private String version = "";/* 版本号，暂定为1 */
	private String sequenceid = "";/* 消息序列号 */
	private String signed = "";/* 消息签名 */

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSequenceid() {
		return sequenceid;
	}

	public void setSequenceid(String sequenceid) {
		this.sequenceid = sequenceid;
	}

	public String getSigned() {
		return signed;
	}

	public void setSigned(String signed) {
		this.signed = signed;
	}
}
