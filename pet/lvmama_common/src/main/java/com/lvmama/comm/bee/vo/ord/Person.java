package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * Person.
 *
 * <pre></pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public final class Person implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5108727765875587057L;
	/**
	 * 主键.
	 */
	private long personId;
	/**
	 * 省
	 */
	private String province;
	
	/**
	 * 市
	 */
	private String city;
	/**
	 * 姓名.
	 */
	private String name;
	/**
	 * 电话号码.
	 */
	private String tel;
	/**
	 * 手机号码.
	 */
	private String mobile;
	/**
	 * email.
	 */
	private String email;
	/**
	 * qq号码.
	 */
	private String qq;
	/**
	 * 地址.
	 */
	private String address;
	/**
	 * 邮编.
	 */
	private String postcode;
	/**
	 * 备注.
	 */
	private String memo;
	/**
	 * 传真号码.
	 */
	private String fax;
	/**
	 * 传真给.
	 */
	private String faxTo;
	/**
	 * person类型.
	 */
	private String personType;
	/**
	 * 证件号码.
	 */
	private String certNo;
	/**
	 * 证件类型.
	 */
	private String certType;
	/**
	 * receiverId.
	 */
	private String receiverId;
	
	private Date brithday;
	
	private String needInsure;
	
	private String gender;
	
	private String pinyin;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getZhGender() {
		if(StringUtils.isNotEmpty(this.gender)) {
			return "F".equalsIgnoreCase(this.gender) ? "女" : "男";
		}
		return "";
	}

	public String getNeedInsure() {
		return needInsure;
	}

	public void setNeedInsure(String needInsure) {
		this.needInsure = needInsure;
	}

	public Date getBrithday() {
		return brithday;
	}
	
	public String getZhBrithday() {
		return DateUtil.getFormatDate(brithday, "yyyy-MM-dd");
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}

	/**
	 * getPersonId.
	 *
	 * @return 主键
	 */
	public long getPersonId() {
		return personId;
	}

	/**
	 * setPersonId.
	 *
	 * @param personId
	 *            主键
	 */
	public void setPersonId(final long personId) {
		this.personId = personId;
	}

	/**
	 * getName.
	 *
	 * @return 姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * setName.
	 *
	 * @param name
	 *            姓名
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * getTel.
	 *
	 * @return 电话号码
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * setTel.
	 *
	 * @param tel
	 *            电话号码
	 */
	public void setTel(final String tel) {
		this.tel = tel;
	}

	/**
	 * getMobile.
	 *
	 * @return 手机号码
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * setMobile.
	 *
	 * @param mobile
	 *            手机号码
	 */
	public void setMobile(final String mobile) {
		this.mobile = mobile;
	}

	/**
	 * getEmail.
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * setEmail.
	 *
	 * @param email
	 *            email
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * getQq.
	 *
	 * @return qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * setQq.
	 *
	 * @param qq
	 *            qq
	 */
	public void setQq(final String qq) {
		this.qq = qq;
	}

	/**
	 * getAddress.
	 *
	 * @return 地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * setAddress.
	 *
	 * @param address
	 *            地址
	 */
	public void setAddress(final String address) {
		this.address = address;
	}

	/**
	 * getPostcode.
	 *
	 * @return 邮编
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * setPostcode.
	 *
	 * @param postcode
	 *            邮编
	 */
	public void setPostcode(final String postcode) {
		this.postcode = postcode;
	}

	/**
	 * getMemo.
	 *
	 * @return 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * setMemo.
	 *
	 * @param memo
	 *            备注
	 */
	public void setMemo(final String memo) {
		this.memo = memo;
	}

	/**
	 * getFax.
	 *
	 * @return 传真号码
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * setFax.
	 *
	 * @param fax
	 *            传真号码
	 */
	public void setFax(final String fax) {
		this.fax = fax;
	}

	/**
	 * getFaxTo.
	 *
	 * @return 传真给
	 */
	public String getFaxTo() {
		return faxTo;
	}

	/**
	 * setFaxTo.
	 *
	 * @param faxTo
	 *            传真给
	 */
	public void setFaxTo(final String faxTo) {
		this.faxTo = faxTo;
	}

	/**
	 * getPersonType.
	 *
	 * @return person类型
	 */
	public String getPersonType() {
		return personType;
	}

	/**
	 * setPersonType.
	 *
	 * @param personType
	 *            person类型
	 */
	public void setPersonType(final String personType) {
		this.personType = personType;
	}
	
	

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * getCertNo.
	 *
	 * @return 证件号码
	 */
	public String getCertNo() {
		return certNo;
	}

	/**
	 * setCertNo.
	 *
	 * @param certNo
	 *            证件号码
	 */
	public void setCertNo(final String certNo) {
		this.certNo = certNo;
	}

	/**
	 * getCertType.
	 *
	 * @return 证件类型
	 */
	public String getCertType() {
		return certType;
	}

	/**
	 * setCertType.
	 *
	 * @param certType
	 *            证件类型
	 */
	public void setCertType(final String certType) {
		this.certType = certType;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getZhCertType(){
		if(this.certType!=null){
			return Constant.CERT_TYPE.getCnName(certType);
		}
		return "";
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	public String getPersonMd5(){
		String content = this.getName()+(getMobile()==null?"":getMobile())+(getCertType()==null?"":getCertType())
				+(getCertNo()==null?"":getCertNo());
		try {
			return MD5.encode(content);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean isCertIsNull() {
		if(StringUtil.isEmptyString(getCertType())){
			return true;
		}
		return false;
	}
	
	public String getFullAddress(){
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotEmpty(province)){
			sb.append(province);
		}
		if(StringUtils.isNotEmpty(city)){
			sb.append(" ");
			sb.append(city);
		}
		if(StringUtils.isNotEmpty(address)){
			sb.append(" ");
			sb.append(address);
		}
		return sb.toString();
	}
}
