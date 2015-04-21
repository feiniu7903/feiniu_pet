package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.utils.DateUtil;

/**
 * @author ShiHui
 */
public class PassPortCode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4274900366484755166L;
	private Long targetId;
	private Long codeId;
	private String serialNo;
	private String portName;
	private String status;
	private String addCode;
	private Date usedTime;
	private String providerName;
	private Long providerId;
	private Long outPortId;
	private String processor;
	private String terminalContent;
	private Date validTime;
	private Date invalidTime;
	private Long supplierId;
    private String statusNo;
    private String statusExplanation;
    private String name;
    private Long orderId;
    
    private Long extId;
    
    private String ZkStatusEdit;//允许用户修改
    private String invalidDate;//不定期产品不可游玩日日期
    private String invalidDateMemo;//不定期产品不可游玩日日期描述信息
    
    private String mergesms;//当前码对应的凭证短信是否合并发送
    private String supplierThread;
    
    private String reApplayWay;
    
	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
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

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getOutPortId() {
		return outPortId;
	}

	public void setOutPortId(Long outPortId) {
		this.outPortId = outPortId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getTerminalContent() {
		return terminalContent;
	}

	public void setTerminalContent(String terminalContent) {
		this.terminalContent = terminalContent;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getStatusNo() {
		return statusNo;
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

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getZkStatusEdit() {
		String visible="false";
		if(this.status.trim().indexOf("USED")!=-1){
			visible="true";
		}
		return visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getExtId() {
		return extId;
	}

	public void setExtId(Long extId) {
		this.extId = extId;
	}

	public String getAddCode() {
		return addCode;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}

	/**
	 * 针对不定期订单,校验不可游玩日期 add by shihui
	 * 
	 * true:校验通过
	 * 
	 * false:今日不可游玩
	 * */
	public boolean validateInvalidDate() {
		if(StringUtils.isNotEmpty(invalidDate)) {
			Date nowDate = DateUtil.getDayStart(new Date());
			String[] dateStrs = invalidDate.split(",");
			for (int i = 0; i < dateStrs.length; i++) {
				String[] dStrs = dateStrs[i].split("-");
				Date dStart = DateUtil.toDate(dStrs[0], "yyMMdd");
				if(dStrs.length == 1) {
					if(DateUtils.isSameDay(nowDate, dStart)) {
						return false;
					}
				} else if(dStrs.length> 1){
					Date dEnd = DateUtil.toDate(dStrs[dStrs.length - 1], "yyMMdd");
					if(!nowDate.before(dStart) && !nowDate.after(dEnd)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public String getInvalidDateMemo() {
		return invalidDateMemo;
	}

	public void setInvalidDateMemo(String invalidDateMemo) {
		this.invalidDateMemo = invalidDateMemo;
	}

	public String getMergesms() {
		return mergesms;
	}

	public void setMergesms(String mergesms) {
		this.mergesms = mergesms;
	}

	public String getReApplayWay() {
		return reApplayWay;
	}

	public void setReApplayWay(String reApplayMay) {
		this.reApplayWay = reApplayMay;
	}

	public boolean isMergeCertificateSMS() {
		return "true".equalsIgnoreCase(this.mergesms);
	}

	public boolean isUseSameSerialNo() {
		return "true".equalsIgnoreCase(this.reApplayWay);
	}
	public String getSupplierThread() {
		return supplierThread;
	}

	public void setSupplierThread(String supplierThread) {
		this.supplierThread = supplierThread;
	}
	
	public boolean hasSupplierThread(){
		return "true".equals(this.supplierThread);
	}
}
