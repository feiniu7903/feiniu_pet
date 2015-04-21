package com.lvmama.tnt.comm.po;


/**
 * 用户邮件或手机验证码
 * @author gaoxin
 * @version 1.0
 */
public class TntCertCode implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 *  主键
	 */
	private java.lang.Long authCodeId;
	/**
	 *  用户id
	 */
	private java.lang.Long userId;
	/**
	 *  手机号或者邮箱(标识)
	 */
	private java.lang.String identityTarget;
	/**
	 *  时间
	 */
	private java.util.Date createTime;
	/**
	 *  手机或者邮箱的验证码
	 */
	private java.lang.String code;
	/**
	 *  类型（EMAIL/MOBILE）
	 */
	private java.lang.String type;


	public TntCertCode(){
	}

	public TntCertCode(
		java.lang.Long authCodeId
	){
		this.authCodeId = authCodeId;
	}

	public void setAuthCodeId(java.lang.Long value) {
		this.authCodeId = value;
	}
	
	public java.lang.Long getAuthCodeId() {
		return this.authCodeId;
	}
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	
	public java.lang.Long getUserId() {
		return this.userId;
	}
	public void setIdentityTarget(java.lang.String value) {
		this.identityTarget = value;
	}
	
	public java.lang.String getIdentityTarget() {
		return this.identityTarget;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	public java.lang.String getCode() {
		return this.code;
	}
	public void setType(java.lang.String value) {
		this.type = value;
	}
	
	public java.lang.String getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "TntCertCode [authCodeId=" + authCodeId + ", userId=" + userId + ", identityTarget=" + identityTarget + ", createTime=" + createTime + ", code=" + code + ", type=" + type + "]";
	}
	
}

