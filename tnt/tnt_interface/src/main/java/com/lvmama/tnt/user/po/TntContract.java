package com.lvmama.tnt.user.po;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author hupeipei
 * 
 */
public class TntContract implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long userId;// 分销商
	private Long contractId;
	private String contractName;
	private String contractNo;
	private Date startTime;// 生效时间
	private Date endTime;
	private Date uploadTime;// 上传日期
	private String status;
	private Date signTime;// 签署日期

	// 临时字段----------
	// 附件id
	private Long attachment;

	public Long getAttachment() {
		return attachment;
	}

	public void setAttachment(Long attachment) {
		this.attachment = attachment;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getStrUploadTime() {
		Date date = getUploadTime();
		if (date != null) {
			SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
			return SDF.format(date);
		}
		return "";
	}

	public static enum CONTACT_STATUS {

		ACTIVATE("1", "有效"), DELETED("0", "已删除");

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		private String value, desc;

		private CONTACT_STATUS(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

	}

}
