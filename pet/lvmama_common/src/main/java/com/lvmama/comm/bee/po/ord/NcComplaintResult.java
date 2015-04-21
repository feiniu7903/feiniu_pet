package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author zhushuying
 *
 */
public class NcComplaintResult implements Serializable {

    private static final long serialVersionUID = 7922175272046311612L;

    private Long resultId;
    private Long complaintId;
    private String treatmentType;
    private String integralCompensation;
    private String gifiCompendation;
    private String gifisWorth;
    private String cashCompensation;
    private String remark;
	public Long getResultId() {
		return resultId;
	}
	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}
	public Long getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}
	public String getTreatmentType() {
		return treatmentType;
	}
	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	} 
	public String getStrTreatmentType() {
		if (null != treatmentType) {
			if (Constant.NC_COMPLAINT_CASH_COMPLAINT.COMMUNICATION.name().equals(treatmentType)) {
				return "沟通";
			}else if(Constant.NC_COMPLAINT_CASH_COMPLAINT.APOLOGY.name().equals(treatmentType)){
				return "道歉";
			} else {
				return "现金补偿";
			}
		}
		return "";
	}
	public String getIntegralCompensation() {
		return integralCompensation;
	}
	public void setIntegralCompensation(String integralCompensation) {
		this.integralCompensation = integralCompensation;
	}
	public String getGifiCompendation() {
		return gifiCompendation;
	}
	public void setGifiCompendation(String gifiCompendation) {
		this.gifiCompendation = gifiCompendation;
	}
	public String getGifisWorth() {
		return gifisWorth;
	}
	public void setGifisWorth(String gifisWorth) {
		this.gifisWorth = gifisWorth;
	}
	public String getCashCompensation() {
		return cashCompensation;
	}
	public void setCashCompensation(String cashCompensation) {
		this.cashCompensation = cashCompensation;
	}
	public String getStrCashCompensation() {
		if (null != cashCompensation && "YES".equals(cashCompensation)) {
			return "是";
		}
		return "否";
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
