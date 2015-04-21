package com.lvmama.distribution.model.jd;

import com.lvmama.distribution.util.JdUtil;

public class User {
	 private String name;//客户姓名
	 private String mobile;//客户电话
	 private String sex;//客户性别
	 private String idCard;//身分证号

	 public String buildUserToXml(){
		 StringBuilder sb=new StringBuilder();
		 sb.append("<user>")
		 .append(JdUtil.buildXmlElementInCheck("name", name))
		 .append(JdUtil.buildXmlElement("mobile", mobile))
		 .append(JdUtil.buildXmlElementInCheck("sex", sex))
		 .append(JdUtil.buildXmlElementInCheck("idCard", idCard))
		 .append("</user>");
		 return sb.toString();
	 }
	 
	 
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	 
}
