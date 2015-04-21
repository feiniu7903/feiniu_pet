package com.lvmama.comm.vo.visa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;
import com.lvmama.comm.pet.po.visa.VisaApprovalDetails;

public class GroupVisaInformation implements Serializable {

	// 序列值
	private static final long serialVersionUID = 88438258L;
	
	/**
	 * 团号
	 */
	private String travelGroupCode;
	
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 签证类型
	 */
	private String visaType;
	/**
	 * 出签城市
	 */
	private String city;
	 
	/**
	 * 签证产品名字
	 */
	private String productName;
	
	/**
	 * 游玩时间.
	 */
	private String visitTime;
	
	/**
	 * 导出时间
	 */
	private String nowDate;
	
 
	/**
	 * 在职人员表头
	 */
	private List<String> employeeTableHeads;
	/**
	 * 退休人员表头
	 */
	private List<String> retireTableHeads;
	/**
	 * 在校学生表头
	 */
	private List<String> studentTableHeads;
	/**
	 * 学龄前儿童表头
	 */
	private List<String> preschoolsTableHeads;
	/**
	 * 自由职业者表头
	 */
	private List<String> freelanceTableHeads;
	/**
	 * 适用所有表头
	 */
	private List<String> allTableHeads;
	
	/**
	 * 在职人员表数据
	 */
	private List<VisaApprovalDetailsVO> employeeList;
	/**
	 * 退休人员表数据
	 */
	private List<VisaApprovalDetailsVO> retireList;
	/**
	 * 在校学生表数据
	 */
	private List<VisaApprovalDetailsVO> studentList;
	/**
	 * 学龄前儿童表数据
	 */
	private List<VisaApprovalDetailsVO> preschoolsList;
	/**
	 * 自由职业者表数据
	 */
	private List<VisaApprovalDetailsVO> freelanceList;
	/**
	 * 适用所有数据
 	 */
 	 private List<VisaApprovalDetailsVO> allList;
 
	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getTravelGroupCode() {
		return travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List getEmployeeTableHeads() {
		return employeeTableHeads;
	}

	public void setEmployeeTableHeads(List<String> employeeTableHeads) {
		this.employeeTableHeads = employeeTableHeads;
	}

	public List getRetireTableHeads() {
		return retireTableHeads;
	}

	public void setRetireTableHeads(List<String> retireTableHeads) {
		this.retireTableHeads = retireTableHeads;
	}

	public List<String> getStudentTableHeads() {
		return studentTableHeads;
	}

	public void setStudentTableHeads(
			List<String> studentTableHeads) {
 			this.studentTableHeads = studentTableHeads;
 	}

	public List<String> getPreschoolsTableHeads() {
		return preschoolsTableHeads;
	}

	public void setPreschoolsTableHeads(
			List<String> preschoolsTableHeads) {
 			this.preschoolsTableHeads = preschoolsTableHeads;
 	}

	public List<String> getFreelanceTableHeads() {
		return freelanceTableHeads;
	}

	public void setFreelanceTableHeads(
			List<String> freelanceTableHeads) {
 			this.freelanceTableHeads = freelanceTableHeads;
 	}

	public List<VisaApprovalDetailsVO> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<VisaApprovalDetailsVO> employeeList) {
		if(employeeList!=null&&!employeeList.isEmpty())
		this.employeeList = employeeList;
	}

	public List<VisaApprovalDetailsVO> getRetireList() {
		return retireList;
	}

	public void setRetireList(List<VisaApprovalDetailsVO> retireList) {
		if(retireList!=null&&!retireList.isEmpty())
		this.retireList = retireList;
	}

	public List<VisaApprovalDetailsVO> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<VisaApprovalDetailsVO> studentList) {
		if(studentList!=null&&!studentList.isEmpty())
		this.studentList = studentList;
	}

	public List<VisaApprovalDetailsVO> getPreschoolsList() {
		return preschoolsList;
	}

	public void setPreschoolsList(List<VisaApprovalDetailsVO> preschoolsList) {
		if(preschoolsList!=null&&!preschoolsList.isEmpty())
		this.preschoolsList = preschoolsList;
	}

	public List<VisaApprovalDetailsVO> getFreelanceList() {
		return freelanceList;
	}

	public void setFreelanceList(List<VisaApprovalDetailsVO> freelanceList) {
		if(freelanceList!=null&&!freelanceList.isEmpty())
		this.freelanceList = freelanceList;
	}

	public void setAllList(List<VisaApprovalDetailsVO> allList) {
		if(allList!=null&&!allList.isEmpty()) 
		this.allList=allList;
	}

	public List<String> getAllTableHeads() {
		return allTableHeads;
	}

	public void setAllTableHeads(List<String> allTableHeads) {
		this.allTableHeads = allTableHeads;
	}

	public List<VisaApprovalDetailsVO> getAllList() {
		return allList;
	}
}
