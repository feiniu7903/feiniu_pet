package com.lvmama.comm.vo;

import java.io.Serializable;

/**
 * 银行POS员工实体类.
 * @author huyunyan
 * 
 */
public class PayPosUserVO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5955638786179417405L;
	/**
	 * POS员工ID.
	 */
	private Long posUserId;
	/**
	 * POS员工编号.
	 */
	private String empNo;
	private String trueName;
	/**
	 * POS员工密码.
	 */
	private String empPasswd;
	/**
	 * POS员工公司编号.
	 */
	private String empCompanyNo;
	/**
	 * POS员工公司名称.
	 */
	private String empCompanyName;
	/**
	 * POS员工姓名.
	 */
	private String empName;
	/**
	 * 员工所在地.
	 */
	private String empLocation;
	/**
	 * 员工状态.
	 */
	private String empStatus;
	/**
	 * 所属终端ID.
	 */
	private Long commPosId;
	/**
	 * 所属商户ID.
	 */
	private Long commercialId;
	/**
	 * 商户名.
	 */
    private String commercialName;
    /**
     * 商户号.
     */
    private String commercialNo;
    /**
     * 商户状态.
     */
    private String commercialStatus;
    /**
     * pos号.
     */
    private String terminalNo;
    /**
     * pos状态.
     */
    private String terminalStatus;
    
    private String supplier;
    
    private String zhSupplier;
    
	public Long getPosUserId() {
		return posUserId;
	}
	public void setPosUserId(Long posUserId) {
		this.posUserId = posUserId;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEmpPasswd() {
		return empPasswd;
	}
	public void setEmpPasswd(String empPasswd) {
		this.empPasswd = empPasswd;
	}
	public String getEmpCompanyNo() {
		return empCompanyNo;
	}
	public void setEmpCompanyNo(String empCompanyNo) {
		this.empCompanyNo = empCompanyNo;
	}
	public String getEmpCompanyName() {
		return empCompanyName;
	}
	public void setEmpCompanyName(String empCompanyName) {
		this.empCompanyName = empCompanyName;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpLocation() {
		return empLocation;
	}
	public void setEmpLocation(String empLocation) {
		this.empLocation = empLocation;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	public Long getCommPosId() {
		return commPosId;
	}
	public void setCommPosId(Long commPosId) {
		this.commPosId = commPosId;
	}
	public Long getCommercialId() {
		return commercialId;
	}
	public void setCommercialId(Long commercialId) {
		this.commercialId = commercialId;
	}
	public String getCommercialName() {
		return commercialName;
	}
	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}
	public String getCommercialNo() {
		return commercialNo;
	}
	public void setCommercialNo(String commercialNo) {
		this.commercialNo = commercialNo;
	}
	public String getCommercialStatus() {
		return commercialStatus;
	}
	public void setCommercialStatus(String commercialStatus) {
		this.commercialStatus = commercialStatus;
	}
	public String getTerminalNo() {
		return terminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}
	public String getTerminalStatus() {
		return terminalStatus;
	}
	public void setTerminalStatus(String terminalStatus) {
		this.terminalStatus = terminalStatus;
	}
	
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getZhSupplier() {
		if (this.supplier != null) {
			return Constant.PAY_POS_SUPPLIER_TYPE.getCnName(supplier);
		}
		return "";
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	
}
