package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;

public class Fax implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long ebkCertificateId;
	//该传真的发送状态.
	private String sendStatus;
	//传真标题.
	private String title;
	//模板路径.
	private String templatePath;
	//
	private String toName;
	//目标接收(供应商)的传真号码.
	private String toFax;
	//目标接收(供应商)的电话号码.
	private String toTel;
	//
	private String fromName;
	//发送方(驴妈妈)的传真号码.
	private String fromFax;
	//发送方(驴妈妈)的电话号码.
	private String fromTel;
	//凭证对象.
	private SupBCertificateTarget bcertTarget;
	
	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToFax() {
		return toFax;
	}

	public void setToFax(String toFax) {
		this.toFax = toFax;
	}

	public String getToTel() {
		return toTel;
	}

	public void setToTel(String toTel) {
		this.toTel = toTel;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromFax() {
		return fromFax;
	}

	public void setFromFax(String fromFax) {
		this.fromFax = fromFax;
	}

	public String getFromTel() {
		return fromTel;
	}

	public void setFromTel(String fromTel) {
		this.fromTel = fromTel;
	}

	public SupBCertificateTarget getBcertTarget() {
		return bcertTarget;
	}

	public void setBcertTarget(SupBCertificateTarget bcertTarget) {
		this.bcertTarget = bcertTarget;
	}

	public Long getEbkCertificateId() {
		return ebkCertificateId;
	}

	public void setEbkCertificateId(Long ebkCertificateId) {
		this.ebkCertificateId = ebkCertificateId;
	}

}
