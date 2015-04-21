package com.lvmama.distribution.model.lv;

import java.util.List;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.distribution.util.DistributionUtil;

/**
 * 产品游玩人字段验证列表
 * @author yanzhirong
 *
 */
public class VisitCustomerValidator {
	
	/**游玩人姓名是否必填*/
	private String name;
	
	/**游玩人姓名拼音是否必填*/
	private String pinyin;
	
	/**游玩人手机号是否必填*/
	private String mobile;
	
	/**游玩人证件号是否必填*/
	private String credentials;
	
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLocalSigned(){
		return this.getName()+this.getPinyin()+this.getMobile()+this.getCredentials();
	}
	
	public VisitCustomerValidator(){}
	
	public VisitCustomerValidator(List<String> validatorList,boolean flag){
		if(flag){
			this.name = "true";
			this.mobile = "true";
			if(validatorList !=null){
				this.pinyin = validatorList.contains("PINYIN")?"true":"false";
				this.credentials = validatorList.contains("CARD_NUMBER")?"true":"false";
			}else{
				this.pinyin = "false";
				this.credentials = "false";
			}
		}else{
			if(validatorList !=null){
				this.name = validatorList.contains("NAME")?"true":"false";
				this.pinyin = validatorList.contains("PINYIN")?"true":"false";
				this.mobile = validatorList.contains("MOBILE")?"true":"false";
				this.credentials = validatorList.contains("CARD_NUMBER")?"true":"false";
			}else{
				this.name = "false";
				this.pinyin = "false";
				this.mobile = "false";
				this.credentials = "false";
			}
		}
	}
	
	/**
	 * 构造游玩人字段验证列表--游玩人字段验证信息节点
	 * @return
	 */
	public String buildVisitCustomerValidator(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<firstVisitCustomer>");
		xmlStr.append(DistributionUtil.buildXmlElement("name", this.getName()));
		xmlStr.append(DistributionUtil.buildXmlElement("pinyin", this.getPinyin()));
		xmlStr.append(DistributionUtil.buildXmlElement("mobile", this.getMobile()));
		xmlStr.append(DistributionUtil.buildXmlElement("credentials", this.getCredentials()));
		xmlStr.append("</firstVisitCustomer>");		
		return xmlStr.toString();
	}
	
	/**
	 * 构造其他游玩人字段验证列表--游玩人字段验证信息节点
	 * @return
	 */
	public String buildVisitCustomerValidatorOther(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<otherVisitCustomer>");
		xmlStr.append(DistributionUtil.buildXmlElement("name", this.name));
		xmlStr.append(DistributionUtil.buildXmlElement("pinyin", this.pinyin));
		xmlStr.append(DistributionUtil.buildXmlElement("mobile", this.mobile));
		xmlStr.append(DistributionUtil.buildXmlElement("credentials", this.credentials));
		xmlStr.append("</otherVisitCustomer>");		
		return xmlStr.toString();
	}

	public String getName() {
		return StringUtil.replaceNullStr(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyin() {
		return StringUtil.replaceNullStr(pinyin);
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getMobile() {
		return StringUtil.replaceNullStr(mobile);
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCredentials() {
		return StringUtil.replaceNullStr(credentials);
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	
}
