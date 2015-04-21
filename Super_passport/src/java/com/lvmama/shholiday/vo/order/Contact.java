package com.lvmama.shholiday.vo.order;

import com.lvmama.comm.bee.po.ord.OrdPerson;

public class Contact {

	private String uniqueId;
	
	private String name;
	private String mobile;
	private String cerType;
	private String certNo;
	private String tel;
	private String fax;
	private String email;
	private String contactMethod;
	private String addr;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
		this.cerType = cerType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactMethod() {
		return contactMethod;
	}
	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public Contact getContact(OrdPerson person){
		if(person!=null){
			this.name=person.getName();
			this.addr=person.getFullAddress();
			this.cerType=person.getCertType();
			this.email=person.getEmail();
			this.fax=person.getFax();
			this.mobile=person.getMobile();
			this.tel=person.getTel();
		}
		
		return this;
	}
	
}
