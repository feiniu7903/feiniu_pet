package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;
import java.util.Date;

public class Child implements Serializable {
	private static final long serialVersionUID = 1L;
	private String firstName;//姓
	private String lastName;//名
	private String mobilePhone;//手机号码
	private String identityType;//证件类型
	private String IdentityNumb;//证件号码
	private String age;//年龄
	private Date birthday;//生日
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getIdentityNumb() {
		return IdentityNumb;
	}
	public void setIdentityNumb(String identityNumb) {
		IdentityNumb = identityNumb;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	

}
