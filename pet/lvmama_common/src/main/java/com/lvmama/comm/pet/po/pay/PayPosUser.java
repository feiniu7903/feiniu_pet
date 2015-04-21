package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;

/**
 * 银行POS员工实体类.
 * @author huyunyan
 * 
 */
public class PayPosUser implements Serializable {

	private static final long serialVersionUID = -5653669235109013649L;
	/**
	 * POS员工ID.
	 */
	private Long posUserId;
	/**
	 * POS员工编号.
	 */
	private String empNo;
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

}
