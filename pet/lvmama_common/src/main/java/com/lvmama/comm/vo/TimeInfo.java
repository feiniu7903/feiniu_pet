package com.lvmama.comm.vo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.DateUtil;

public class TimeInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2803016277371607859L;
	private Long productId;
	private Long prodBranchId;
	private Long journeyProductId;
	private String visitTime;
	private String leaveTime;
	private Long quantity;
	private String productType;
	
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Date getVisitDate() throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.parse(this.visitTime);
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * @return the prodBranchId
	 */
	public Long getProdBranchId() {
		return prodBranchId;
	}
	/**
	 * @param prodBranchId the prodBranchId to set
	 */
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}
	/**
	 * @return the journeyProductId
	 */
	public Long getJourneyProductId() {
		return journeyProductId;
	}
	/**
	 * @param journeyProductId the journeyProductId to set
	 */
	public void setJourneyProductId(Long journeyProductId) {
		this.journeyProductId = journeyProductId;
	}
	/**
	 * @return the leaveTime
	 */
	public String getLeaveTime() {
		return leaveTime;
	}
	/**
	 * @param leaveTime the leaveTime to set
	 */
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	public Date getLeaveDate(){
		return DateUtil.toDate(leaveTime,"yyyy-MM-dd");
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public boolean hasHotel(){
		return StringUtils.equals("hotel", productType);
	}
	public long getDays(){
		try{
			Date date=getVisitDate();
			Date leave=getLeaveDate();
			return (leave.getTime()-date.getTime())/(24*60*60*1000L);
		}catch(Exception ex){
			return 0L;
		}
	}
	/**
	 * 检查数据是否可用.
	 * @return
	 */
	public boolean checkedSuccess(){
		if(StringUtils.isEmpty(visitTime)){
			return false;
		}
		if(getQuantity()==null||getQuantity()<1L){
			return false;
		}
		
		if(getProdBranchId()==null||getProdBranchId()<1L){
			return false;
		}
		return true;
	}
	
	public long getKey(){
		StringBuffer sb=new StringBuffer();
		sb.append(getProdBranchId());
		sb.append("_");
		if(journeyProductId!=null){
			sb.append(journeyProductId);
			sb.append("_");
		}
		sb.append(getVisitTime());
		return sb.toString().hashCode();
	}
}
