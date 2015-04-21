package com.lvmama.comm.bee.po.ord;
/**
 * @author shangzhengyuan
 * @description 电子合同签约日志
 * @version 在线预售权
 * @time 20120727
 */
import java.io.Serializable;
import java.util.Date;

public class OrdEcontractSignLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8457731929565097462L;

	/**
	 * 电子合同签约日志编码
	 */
	private Long signLogId;
	/**
	 * 电子合同编码
	 */
	private String econtractNo;
	/**
	 * 签约方式
	 */
	private String signMode;
	/**
	 * 签约时间
	 */
	private Date signDate;
	/**
	 * 签约人
	 */
	private String signUser;
	public Long getSignLogId() {
		return signLogId;
	}
	public void setSignLogId(Long signLogId) {
		this.signLogId = signLogId;
	}
	public String getEcontractNo() {
		return econtractNo;
	}
	public void setEcontractNo(String econtractNo) {
		this.econtractNo = econtractNo;
	}
	public String getSignMode() {
		return signMode;
	}
	public void setSignMode(String signMode) {
		this.signMode = signMode;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getSignUser() {
		return signUser;
	}
	public void setSignUser(String signUser) {
		this.signUser = signUser;
	}
}
