package com.lvmama.comm.pet.po.ebkpush;

import java.io.Serializable;


/**
 * 订单相关人员信息，如联系人、取票人、旅游人
 * @author xuqun
 *
 */
public class OrderPerson implements Serializable {
	
	public OrderPerson(){
		
	}

//	ORDER_PERSON_ID
	
	private static final long serialVersionUID = 1537197241331150651L;

	private String fullAddress; // "",
	

    private String mobile; // "13916420267",联系电话
    
  
    private String name; // "冉龙飞",
   
 
    private String cerType; //证件类型
    
   
    private String zhCertType; // 证件中文名
    

    private long orderId;
    
 
    private String certNo; //证件号码
    

    private String personType; // "TRAVELLER", 游客类型
    
  
    private Long personId; // 28679552,
    
   
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
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
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
