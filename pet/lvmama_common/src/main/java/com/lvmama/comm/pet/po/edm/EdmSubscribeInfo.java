package com.lvmama.comm.pet.po.edm;

/**
 * 邮件订阅信息
 */
import java.io.Serializable;
import java.util.Date;

public class EdmSubscribeInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 501356392641090416L;
	/**
	 * 订阅邮件信息ID
	 */
	private Long id;
	/**
	 * 订阅用户ID
	 */
	private Long edmUserId;
	/**
	 * 订阅邮件类型
	 */
	private String type;
	/**
	 * 订阅邮件类型名称
	 */
	private String typeName;
	/**
	 * 
	 */
	private String isValid = "Y";
	/**
	 * 订阅时间
	 */
	private Date createDate;
	/**
	 * 退订时间
	 */
	private Date cancelDate;
	/**
	 * 退订原因(使用;分割)
	 */
	private String cancelRemark;
	
	/**
	 * 用户邮箱地址
	 */
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEdmUserId() {
		return edmUserId;
	}

	public void setEdmUserId(Long edmUserId) {
		this.edmUserId = edmUserId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
