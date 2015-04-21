package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;

public class PassCode implements Serializable {

	private static final long serialVersionUID = -5267413836706858470L;
	private Long codeId;
	private String serialNo;
	private Date createTime;
	private String status;
	private String code;
	private String addCode;
	private Date usedTime;
	private String mobile;
	private String smsContent;
	private String terminalContent;
	private Date validTime;
	private Date invalidTime;
	private String url;
	private String updateTerminalContent;
	private String addCodeMd5;
	private List<PassPortCode> passPortList;

	private String extId;
	private Long objectId;
	private String objectType;
	private String statusNo;
	private String statusExplanation;
	private String reapply;

	private String sendOrderid;
	private String sendSms;
	private Date updateTime;
	private byte[] codeImage;
	private String providerName;
	private Long orderId;
	private Long targetId;
	/**
	 * 重新申请次数
	 */
	private Long reapplyCount;
	/**
	 * 重新申请时间
	 */
	private Date reapplyTime;
	//此订单申请码的个数
	private Long codeTotal;
	//废单时间
	private Date failedTime;
	
	public Date getFailedTime() {
		return failedTime;
	}

	public void setFailedTime(Date failedTime) {
		this.failedTime = failedTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getStatusNo() {
		return statusNo;
	}

	public List<PassPortCode> getPassPortList() {
		return passPortList;
	}

	public void setPassPortList(List<PassPortCode> passPortList) {
		this.passPortList = passPortList;
	}

	public void setStatusNo(String statusNo) {
		this.statusNo = statusNo;
	}

	public String getStatusExplanation() {
		return statusExplanation;
	}

	public void setStatusExplanation(String statusExplanation) {
		this.statusExplanation = statusExplanation;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddCode() {
		return addCode;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public boolean isForOrder() {
		return "ORD_ORDER".equalsIgnoreCase(objectType);
	}

	public boolean isForOrderItemMeta() {
		return "ORD_ORDER_ITEM_META".equalsIgnoreCase(objectType);
	}

	public boolean isApplySuccess() {
		return Constant.PASSCODE_APPLY_STATUS.SUCCESS.name().equalsIgnoreCase(
				status);
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getTerminalContent() {
		return terminalContent;
	}

	public void setTerminalContent(String terminalContent) {
		this.terminalContent = terminalContent;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUpdateTerminalContent() {
		return updateTerminalContent;
	}

	public void setUpdateTerminalContent(String updateTerminalContent) {
		this.updateTerminalContent = updateTerminalContent;
	}

	public String getAddCodeMd5() {
		return addCodeMd5;
	}

	public void setAddCodeMd5(String addCodeMd5) {
		this.addCodeMd5 = addCodeMd5;
	}

	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	public String getReapply() {
		return reapply;
	}

	public void setReapply(String reapply) {
		this.reapply = reapply;
	}

	public byte[] getCodeImage() {
		return codeImage;
	}

	public void setCodeImage(byte[] codeImage) {
		this.codeImage = codeImage;
	}

	public boolean isNeedSendSms() {
		return PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name().equals(sendSms);
	}

	public String getSendSms() {
		return sendSms;
	}

	public void setSendSms(String sendSms) {
		this.sendSms = sendSms;
	}
	
	/**
	 * 是否由合作伙伴发送短信
	 */
	public boolean isSendSmsByPartner() {
		return PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name().equals(sendSms);
	}
	
	/**
	 * 是否由驴妈妈发送短信
	 */
	public boolean isSendSmsByLvmama() {
		return PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name().equals(sendSms);
	}

	public boolean isNeedSendOrderid() {
		return !"false".equalsIgnoreCase(sendOrderid); // 除非明确地设置了不发送订单号，否则一概发送订单号
	}

	public String getSendOrderid() {
		return sendOrderid;
	}

	public void setSendOrderid(String sendOrderid) {
		this.sendOrderid = sendOrderid;
	}

	public boolean isCanReapply() {
		return (!Constant.PASSCODE_REAPPLY_STATUS.TRUE.name().equalsIgnoreCase(
				this.reapply))
				&& (Constant.PASSCODE_APPLY_STATUS.FAILED.name()
						.equalsIgnoreCase(this.status));
	}

	public String getZhStatus() {
		if (this.status == null) {
			return "";
		} else {
			return Constant.PASSCODE_STATUS.getCnName(this.status.trim());
		}
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getReapplyCount() {
		return reapplyCount;
	}

	public void setReapplyCount(Long reapplyCount) {
		this.reapplyCount = reapplyCount;
	}

	public Date getReapplyTime() {
		return reapplyTime;
	}

	public void setReapplyTime(Date reapplyTime) {
		this.reapplyTime = reapplyTime;
	}

	public Long getCodeTotal() {
		return codeTotal;
	}

	public void setCodeTotal(Long codeTotal) {
		this.codeTotal = codeTotal;
	}

	@Override
	public String toString() {
		return "PassCode [codeId=" + codeId + ", serialNo=" + serialNo
				+ ", createTime=" + createTime + ", status=" + status
				+ ", code=" + code + ", addCode=" + addCode + ", usedTime="
				+ usedTime + ", mobile=" + mobile + ", smsContent="
				+ smsContent + ", terminalContent=" + terminalContent
				+ ", validTime=" + validTime + ", invalidTime=" + invalidTime
				+ ", url=" + url + ", updateTerminalContent="
				+ updateTerminalContent + ", addCodeMd5=" + addCodeMd5
				+ ", passPortList=" + passPortList + ", extId=" + extId
				+ ", objectId=" + objectId + ", objectType=" + objectType
				+ ", statusNo=" + statusNo + ", statusExplanation="
				+ statusExplanation + ", reapply=" + reapply + ", sendOrderid="
				+ sendOrderid + ", sendSms=" + sendSms + ", updateTime="
				+ updateTime + ", codeImage=" + Arrays.toString(codeImage)
				+ ", providerName=" + providerName + ", orderId=" + orderId
				+ ", targetId=" + targetId + ", reapplyCount=" + reapplyCount
				+ ", reapplyTime=" + reapplyTime + ", codeTotal=" + codeTotal
				+ ", failedTime=" + failedTime
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeId == null) ? 0 : codeId.hashCode());
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PassCode other = (PassCode) obj;
		if (codeId == null) {
			if (other.codeId != null)
				return false;
		} else if (!codeId.equals(other.codeId))
			return false;
		if (serialNo == null) {
			if (other.serialNo != null)
				return false;
		} else if (!serialNo.equals(other.serialNo))
			return false;
		return true;
	}
	
}
