package com.lvmama.comm.bee.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;

/**
 * 通关系统数据参数对象
 * 
 * @author chenlinjun
 * 
 */
public class Passport implements Serializable {
	private static final long serialVersionUID = -3672646004720401099L;

	/**
	 * 申请流水号
	 */
	private String serialno;
	/**
	 * 履行对象ID [List] ( List(Long) )
	 */
	private List<Long> performId;
	/**
	 * 通关点
	 */
	private Long portId;
	/**
	 * 展示(打印)内容
	 */

	private List<String> printContent;
	/**
	 * 修改展示(打印)内容
	 */

	private String updatePrintContent;
	/**
	 * 手机（可选）（String）
	 */
	private String mobile;
	/**
	 * 短信内容（可选）（String）
	 */
	private String msg;
	/**
	 * 回调URL （String）
	 */
	private String url;
	/**
	 * 通关码
	 */
	private String code;
	/**
	 * 辅助码
	 */
	private String addCode;
	/**
	 * 辅助码MD5
	 */
	private String addCodeMd5;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 服务商
	 */
	private String provider;
	/**
	 * 加密信息
	 */
	private String md5;
	/**
	 * 客户端名称，更加密有关
	 */
	private String clientName;
	/**
	 * 儿童数
	 */
	private String child;
	/**
	 * 成人数
	 */
	private String adult;
	/**
	 * 错误代码
	 */
	private String errorNO;
	/**
	 * 错误信息
	 */
	private String comLogContent;
	/**
	 * 有效时间
	 */
	private List<String> validTime;
	/**
	 * 失效时间
	 */
	private List<String> invalidTime;
	/**
	 * 事件类型，申请码，废码，回收码，更新人数，更新内容
	 */
	private String eventType;
	/**
	 * 履行对象编号
	 */
	private String outPortId;

	/**
	 * 是否发送订单号 默认值为true
	 */
	private boolean sendOrderid = true;

	/**
	 * 产品供应商ID
	 */
	private String supplierId;
	/**
	 * 扩展编号
	 */
	private String extId;
	/**
	 * 通关码编号
	 */
	private Long codeId;
	/**
	 * 苏州乐园二维码图片
	 */
	private byte[] codeImage;
	/**
	 * 重新申请次数
	 */
	private Long reapplyCount;
	/**
	 * 重新申请时间
	 */
	private Date reapplyTime;
	/**
	 * 离线刷码时间
	 */
	private Date usedDate;
	/**
	 * 设备号
	 */
	private String deviceId;

	/**
	 * 由谁（驴妈妈/合作伙伴）发送短信
	 */
	private String sendSms;
	
	/***
	 * 部分履行
	 */
	private boolean isPartPerform;
	
	public boolean isPartPerform() {
		return isPartPerform;
	}

	public void setPartPerform(boolean isPartPerform) {
		this.isPartPerform = isPartPerform;
	}

	private String messageWhenApplySuccess;

	public String getMessageWhenApplySuccess() {
		return messageWhenApplySuccess;
	}

	public void setMessageWhenApplySuccess(String messageWhenApplySuccess) {
		this.messageWhenApplySuccess = messageWhenApplySuccess;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getOutPortId() {
		return outPortId;
	}

	public void setOutPortId(String outPortId) {
		this.outPortId = outPortId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getAdult() {
		return adult;
	}

	public void setAdult(String adult) {
		this.adult = adult;
	}

	public String getMd5() {
		return md5;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public List<Long> getPerformId() {
		return performId;
	}

	public void setPerformId(List<Long> performId) {
		this.performId = performId;
	}

	public List<String> getPrintContent() {
		return printContent;
	}

	public void setPrintContent(List<String> printContent) {
		this.printContent = printContent;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Long getPortId() {
		return portId;
	}

	public void setPortId(Long portId) {
		this.portId = portId;
	}

	public String getUpdatePrintContent() {
		return updatePrintContent;
	}

	public void setUpdatePrintContent(String updatePrintContent) {
		this.updatePrintContent = updatePrintContent;
	}

	public String getErrorNO() {
		return errorNO;
	}

	public void setErrorNO(String errorNO) {
		this.errorNO = errorNO;
	}

	public String getComLogContent() {
		return this.comLogContent;
	}

	public String getZhErrorMessage() {
		if (this.errorNO == null) {
			return "";
		} else {
			return Constant.PASSCODE_ERROR.getCnName(errorNO);
		}
	}

	public void setComLogContent(String comLogContent) {
		this.comLogContent = comLogContent;
	}

	public List<String> getValidTime() {
		return validTime;
	}

	public void setValidTime(List<String> validTime) {
		this.validTime = validTime;
	}

	public List<String> getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(List<String> invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getAddCodeMd5() {
		return addCodeMd5;
	}

	public void setAddCodeMd5(String addCodeMd5) {
		this.addCodeMd5 = addCodeMd5;
	}

	public boolean isSendOrderid() {
		return sendOrderid;
	}

	public void setSendOrderid(boolean sendOrderid) {
		this.sendOrderid = sendOrderid;
	}

	public byte[] getCodeImage() {
		return codeImage;
	}

	public void setCodeImage(byte[] codeImage) {
		this.codeImage = codeImage;
	}

	public boolean isStatusSuccess() {
		return PassportConstant.PASSCODE_STATUS.SUCCESS.name().equalsIgnoreCase(status);
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

	public Date getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}

	public String getSendSms() {
		return sendSms;
	}

	public void setSendSms(String sendSms) {
		this.sendSms = sendSms;
	}
}
