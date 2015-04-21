package com.lvmama.passport.processor.impl.client.zhiyoubao.model;
 
import com.lvmama.passport.processor.impl.client.zhiyoubao.ZhiyoubaoUtil;
/**
 * 身份信息
 * @author gaoxin
 *
 */
public class IdentityInfo {
	/** 企业码 */
	private String corpCode;
	/** 用户名 */
	private String userName;
	
	public IdentityInfo() {}



	public IdentityInfo(String corpCode, String userName) {
		this.corpCode = corpCode;
		this.userName = userName;
	}



	/**
	 * 分销商信息
	 * @return
	 */
	public String toidentityInfoXml(){
		StringBuilder sbi=new StringBuilder();
		sbi.append("<identityInfo>");
		sbi.append(ZhiyoubaoUtil.buildElement("corpCode",corpCode)); //分销商企业编码
		sbi.append(ZhiyoubaoUtil.buildElement("userName",userName));// 分销商用户名	
    	sbi.append("</identityInfo>");
		return sbi.toString();
	}
	
	
	
	public String getCorpCode() {
		return corpCode;
	}
	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
