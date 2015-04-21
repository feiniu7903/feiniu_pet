package com.lvmama.pet.vo;

import java.util.ArrayList;
import java.util.List;
/**
 * 宁波银行对账Bean
 * @author ZHANG Nan
 */
public class NingboResponseBean {

	private String repServiceId;
	
	private String repFlowNo;
	
	private String ec;
	
	private String em;
	
	private String sd;
	
	private String count;
	private String batchNum;
	private String gmtCreateStart;
	private String gmtCreateEnd;
	
	
	
	private List<NingboPaymentReconVO> ningboPaymentReconVOList = new ArrayList<NingboPaymentReconVO>();
	
	private List<NingboRefundReconVO> ningboRefundReconVOList = new ArrayList<NingboRefundReconVO>();

	public void addNingboPaymentReconVOList(NingboPaymentReconVO ningboPaymentReconVO) {
		ningboPaymentReconVOList.add(ningboPaymentReconVO);
	}
	public void addNingboRefundReconVOList(NingboRefundReconVO ningboRefundReconVO) {
		ningboRefundReconVOList.add(ningboRefundReconVO);
	}
	
	
	
	
	public String getRepServiceId() {
		return repServiceId;
	}
	public void setRepServiceId(String repServiceId) {
		this.repServiceId = repServiceId;
	}
	public String getRepFlowNo() {
		return repFlowNo;
	}
	public void setRepFlowNo(String repFlowNo) {
		this.repFlowNo = repFlowNo;
	}
	public String getEc() {
		return ec;
	}
	public void setEc(String ec) {
		this.ec = ec;
	}
	public String getEm() {
		return em;
	}
	public void setEm(String em) {
		this.em = em;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	public List<NingboPaymentReconVO> getNingboPaymentReconVOList() {
		return ningboPaymentReconVOList;
	}
	public void setNingboPaymentReconVOList(List<NingboPaymentReconVO> ningboPaymentReconVOList) {
		this.ningboPaymentReconVOList = ningboPaymentReconVOList;
	}
	public List<NingboRefundReconVO> getNingboRefundReconVOList() {
		return ningboRefundReconVOList;
	}
	public void setNingboRefundReconVOList(List<NingboRefundReconVO> ningboRefundReconVOList) {
		this.ningboRefundReconVOList = ningboRefundReconVOList;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getGmtCreateStart() {
		return gmtCreateStart;
	}
	public void setGmtCreateStart(String gmtCreateStart) {
		this.gmtCreateStart = gmtCreateStart;
	}
	public String getGmtCreateEnd() {
		return gmtCreateEnd;
	}
	public void setGmtCreateEnd(String gmtCreateEnd) {
		this.gmtCreateEnd = gmtCreateEnd;
	}
	
}
