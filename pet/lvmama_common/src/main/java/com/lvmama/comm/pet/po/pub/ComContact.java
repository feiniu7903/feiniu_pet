package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;

public class ComContact implements Comparable<ComContact>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -500838519655317712L;

	private Long contactId;

	private Date createTime;

	private Long contactRelationId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column COM_CONTACT.NAME
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	private String name;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column COM_CONTACT.SEX
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	private String sex;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column COM_CONTACT.TELEPHONE
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	private String telephone;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column COM_CONTACT.MOBILEPHONE
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	private String mobilephone;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column COM_CONTACT.EMAIL
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	private String email;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column COM_CONTACT.ADDRESS
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	private String address;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column COM_CONTACT.TITLE
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	private String title;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column COM_CONTACT.MEMO
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	private String memo;
	
	private String otherContact;
	
	private Long supplierId;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column COM_CONTACT.CONTACT_ID
	 * 
	 * @return the value of COM_CONTACT.CONTACT_ID
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public Long getContactId() {
		return contactId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column COM_CONTACT.CONTACT_ID
	 * 
	 * @param contactId
	 *            the value for COM_CONTACT.CONTACT_ID
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column COM_CONTACT.NAME
	 * 
	 * @return the value of COM_CONTACT.NAME
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column COM_CONTACT.NAME
	 * 
	 * @param name
	 *            the value for COM_CONTACT.NAME
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column COM_CONTACT.TELEPHONE
	 * 
	 * @return the value of COM_CONTACT.TELEPHONE
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column COM_CONTACT.TELEPHONE
	 * 
	 * @param telephone
	 *            the value for COM_CONTACT.TELEPHONE
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column COM_CONTACT.MOBILEPHONE
	 * 
	 * @return the value of COM_CONTACT.MOBILEPHONE
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public String getMobilephone() {
		return mobilephone;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column COM_CONTACT.MOBILEPHONE
	 * 
	 * @param mobilephone
	 *            the value for COM_CONTACT.MOBILEPHONE
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	
	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column COM_CONTACT.EMAIL
	 * 
	 * @return the value of COM_CONTACT.EMAIL
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column COM_CONTACT.EMAIL
	 * 
	 * @param email
	 *            the value for COM_CONTACT.EMAIL
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column COM_CONTACT.ADDRESS
	 * 
	 * @return the value of COM_CONTACT.ADDRESS
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column COM_CONTACT.ADDRESS
	 * 
	 * @param address
	 *            the value for COM_CONTACT.ADDRESS
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column COM_CONTACT.TITLE
	 * 
	 * @return the value of COM_CONTACT.TITLE
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column COM_CONTACT.TITLE
	 * 
	 * @param title
	 *            the value for COM_CONTACT.TITLE
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column COM_CONTACT.MEMO
	 * 
	 * @return the value of COM_CONTACT.MEMO
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column COM_CONTACT.MEMO
	 * 
	 * @param memo
	 *            the value for COM_CONTACT.MEMO
	 * 
	 * @ibatorgenerated Thu May 06 17:51:54 CST 2010
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getZhSex(){
		return Constant.SEX_CODE.getCnName(sex);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getContactRelationId() {
		return contactRelationId;
	}

	public void setContactRelationId(Long contactRelationId) {
		this.contactRelationId = contactRelationId;
	}

	@Override
	public int compareTo(ComContact c) {
		if (contactId < c.getContactId()) {
			return -1;
		} else if (contactId == c.getContactId()) {
			return 0;
		} else {
			return 1;
		}
	}

	public boolean equals(Object obj) {
		if (obj instanceof ComContact) {
			ComContact cc = (ComContact) obj;
			if (contactId == null) {
				return cc.getContactId() == null;
			} else {
				return contactId.equals(cc.getContactId());
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		if (contactId != null)
			return contactId.hashCode();
		else
			return 0;
	}

	@Override
	public String toString() {
		return joinInfo(false);
	}
	public String toHtml(){
		return joinInfo(true);
	}
	/**
	 * 把信息连接起来
	 * @param html
	 * @return
	 */
	private String joinInfo(boolean html){
		StringBuffer sb=new StringBuffer();
		sb.append(blank(html,name));
		sb.append(blank(html,telephone));
		sb.append(blank(html, title));
		sb.append(blank(html, getZhSex()));
		sb.append(blank(html, mobilephone));
		sb.append(blank(html, memo));
		sb.append(blank(html, address));
		sb.append(blank(html, email));
		sb.append(blank(html, otherContact));
		return sb.toString().trim();
	}
	private String blank(boolean html,String val){
		if(StringUtils.isEmpty(val)){
			return "";
		}
		
		if(html){
			return val+"&nbsp;";
		}else{
			return val+" ";
		}
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getOtherContact() {
		return otherContact;
	}

	public void setOtherContact(String otherContact) {
		this.otherContact = otherContact;
	}
		
}