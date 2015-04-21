package com.lvmama.tnt.order.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户信息
 * 
 * @author gaoxin
 * 
 */
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1356033484831162103L;
	/**
	 * 
	 */
	private String lastName;
	private String firstName;
	private String fullName;
	private String mobile;
	private String idType;
	private String idNo;
	private String birthday;
	private String email;
	private String fax;
	private String gender;
	private String nationality;
	private String phone;
	private String pinyin;

	private String province;
	private String city;
	private String address;
	private String postCode;
	private String certNo;
	private String certType;
	private String personType;
	
	
	private String cnCertType;
	private String hiddenIDCard;
	private String hiddenEmail;
	private String hiddenMobile;
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isNotEmpty() {
		return StringUtils.isNotEmpty(fullName)
				|| StringUtils.isNotEmpty(lastName)
				|| StringUtils.isNotEmpty(firstName);

	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCnCertType() {
		return cnCertType;
	}
	
	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getZhBrithday() {
		if(this.birthday!=null){
			return new SimpleDateFormat("yyyy-MM-dd").format(this.birthday);
		}
		return "";
	}
	
	public void setCnCertType(String cnCertType) {
		this.cnCertType = cnCertType;
	}
	
	
	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public void setHiddenIDCard(String hiddenIDCard) {
		this.hiddenIDCard = hiddenIDCard;
	}

	public void setHiddenEmail(String hiddenEmail) {
		this.hiddenEmail = hiddenEmail;
	}

	public String getHiddenIDCard(){
		return this.hiddenIDCard;
	}
	
	public String getHiddenEmail(){
		return this.hiddenEmail;
	}

	public String getHiddenMobile() {
		return hiddenMobile;
	}

	public void setHiddenMobile(String hiddenMobile) {
		this.hiddenMobile = hiddenMobile;
	}
	
}
