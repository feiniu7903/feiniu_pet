package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EbkMultiJourney implements Serializable {

	/**
	 * ebk  多行程 表  
	 */
	private static final long serialVersionUID = -113109729014870438L;
	
	private Long multiJourneyId;
	
	private Long productId;
	
	private String journeyName;
	
	private Long days;
	
	private Long nights;
	
	private String content;
	
	private Date createTime;
	
	private String valid;
	
	private String specDate;
	
	private List<EbkProdJourney> viewJourneyList;

	private List<EbkProdContent> ebkProdContentList;

	public List<EbkProdContent> getEbkProdContentList() {
		return ebkProdContentList;
	}

	public void setEbkProdContentList(List<EbkProdContent> ebkProdContentList) {
		this.ebkProdContentList = ebkProdContentList;
	}
	
	public Long getMultiJourneyId() {
		return multiJourneyId;
	}

	public void setMultiJourneyId(Long multiJourneyId) {
		this.multiJourneyId = multiJourneyId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getJourneyName() {
		return journeyName;
	}

	public void setJourneyName(String journeyName) {
		this.journeyName = journeyName;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public Long getNights() {
		return nights;
	}

	public void setNights(Long nights) {
		this.nights = nights;
	}

	public String getZhValid() {
		return "Y".equals(valid) ? "有效" : "无效";
 	}


	public List<EbkProdJourney> getViewJourneyList() {
		return viewJourneyList;
	}

	public void setViewJourneyList(List<EbkProdJourney> viewJourneyList) {
		this.viewJourneyList = viewJourneyList;
	}

	public String getSpecDate() {
		return specDate;
	}

	public void setSpecDate(String specDate) {
		this.specDate = specDate;
	}
}