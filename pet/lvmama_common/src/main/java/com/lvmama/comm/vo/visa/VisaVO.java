package com.lvmama.comm.vo.visa;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;
import com.lvmama.comm.vo.Constant;

public class VisaVO implements Serializable{
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 8843828L;
	
	/**
	 * 人群
	 */
	private String occupation;
	/**
	 * 标识
	 */
	private Long documentId;
	

	private List<VisaApplicationDocumentDetails> visaApplicationDocumentDetailsList;


	public String getCnOccupation() {
		if (null != occupation) {
			return Constant.VISA_OCCUPATION.VISA_FOR_EMPLOYEE.getCnName(occupation);
		} else {
			return "";
		}		
	}
	public String getOccupation() {
		return occupation;
	}


	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}


	public Long getDocumentId() {
		return documentId;
	}


	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}


	public List<VisaApplicationDocumentDetails> getVisaApplicationDocumentDetailsList() {
		return visaApplicationDocumentDetailsList;
	}


	public void setVisaApplicationDocumentDetailsList(
			List<VisaApplicationDocumentDetails> visaApplicationDocumentDetailsList) {
		this.visaApplicationDocumentDetailsList = visaApplicationDocumentDetailsList;
	}
	

}
