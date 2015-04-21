package com.ejingtong.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 订单相关人员信息，如联系人、取票人、旅游人
 * @author xuqun
 *
 */
@DatabaseTable(tableName="ORDER_PERSON")
public class OrderPerson implements Serializable {
	
	public OrderPerson(){
		
	}

//	ORDER_PERSON_ID
	
	private static final long serialVersionUID = 1537197241331150651L;

	private String fullAddress; // "",
	
	@DatabaseField(columnName="MOBILE_NO")
    private String mobile; // "13916420267",联系电话
    
    @DatabaseField(columnName="NAME")
    private String name; // "冉龙飞",
   
    @DatabaseField(columnName="ID_TYPE")
    private String cerType; //证件类型
    
    @DatabaseField(columnName="ID_NAME_CN")
    private String zhCertType; // 证件中文名
    
    @DatabaseField(columnName="ORDER_ID")
    private long orderId;
    
    @DatabaseField(columnName="ID_NO")
    private String certNo; //证件号码
    
    @DatabaseField(columnName="PERSON_TYPE")
    private String personType; // "TRAVELLER", 游客类型
    
    @DatabaseField(columnName="ORDER_PERSON_ID",id=true)
    private long personId; // 28679552,
    
   
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public String getZhCertType() {
		return zhCertType;
	}
	public void setZhCertType(String zhCertType) {
		this.zhCertType = zhCertType;
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
	
}
