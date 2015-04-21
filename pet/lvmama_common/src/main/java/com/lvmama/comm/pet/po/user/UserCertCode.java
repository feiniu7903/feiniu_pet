/**
 * 
 */
package com.lvmama.comm.pet.po.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件验证码/手机验证码/未来各种验证码
 * @author liuyi
 *
 */
public class UserCertCode  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2630230108712147384L;

	/**
	 * 主键
	 */
	private Long authCodeId;
	
	/**
	 * 用户标识
	 */
	private Long userId;
	
	/**
	 * 验证目标: 手机号或EMAIL或未来各种可能
	 */
	private String identityTarget;
	
	/**
	 * 验证目标类型
	 */
	private String type;
	
	/**
	 * 验证码
	 */
	private String code;
	
	
	/**
	 * 创建日期
	 */
	private Date createTime;



	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getIdentityTarget() {
		return identityTarget;
	}


	public void setIdentityTarget(String identityTarget) {
		this.identityTarget = identityTarget;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Long getAuthCodeId() {
		return authCodeId;
	}


	public void setAuthCodeId(Long authCodeId) {
		this.authCodeId = authCodeId;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
