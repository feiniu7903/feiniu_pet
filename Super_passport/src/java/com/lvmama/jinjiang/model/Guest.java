package com.lvmama.jinjiang.model;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.EnumUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.IdentityCardUtil;

/**
 * 游客
 * @author chenkeke
 *
 */
public class Guest {
	private String name;
	private String sex;
	private String category;
	private String mobile;
	private String certificationType;
	private String certificationNumber;
	private Date birthday;
	private String otherContactInfo;
	private String priceCategory;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return "F".equals(sex)?"FEMALE":"MALE";
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCategory() {
		Date brithday = this.getBirthday();
		if(JIN_JIANG_CARD_TYPE.ID_CARD.getJinJiangCardType().equals(this.getCertificationType())&&this.getCertificationNumber()!=null){
			brithday = IdentityCardUtil.getDate(this.getCertificationNumber());
		}
		if(brithday!=null){
			int age =getAge(brithday);
			if(age==-100){
				return null;
			}
			if( age<=12){
				return "CHILD";
			}
		}
		return "ADULT";
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCertificationType() {
		JIN_JIANG_CARD_TYPE t = JIN_JIANG_CARD_TYPE.ID_CARD;
		if(certificationType!=null){
			t = EnumUtils.getEnum(JIN_JIANG_CARD_TYPE.class, certificationType);
		}
		return t.getJinJiangCardType();
	}
	public void setCertificationType(String certificationType) {
		this.certificationType = certificationType;
	}
	public String getCertificationNumber() {
		return certificationNumber;
	}
	public void setCertificationNumber(String certificationNumber) {
		this.certificationNumber = certificationNumber;
	}
	public Date getBirthday() {
		Date newbrithday = this.birthday;
		if(newbrithday==null && this.getCertificationNumber()!=null){
			newbrithday = IdentityCardUtil.getDate(this.getCertificationNumber());
		}
		return newbrithday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getOtherContactInfo() {
		return otherContactInfo;
	}
	public void setOtherContactInfo(String otherContactInfo) {
		this.otherContactInfo = otherContactInfo;
	}
	public String getPriceCategory() {
		return priceCategory;
	}
	public void setPriceCategory(String priceCategory) {
		this.priceCategory = priceCategory;
	}
	
	static enum JIN_JIANG_CARD_TYPE{
		CUSTOMER_SERVICE_ADVICE("NOTHING","客服联系我"),
		ERTONG("OTHER","儿童无证件"),
		GANGAO("GANGAO","港澳通行证"),
		HUIXIANG("OTHER","回乡证"),
		HUZHAO("PASSPORT","护照"),
		ID_CARD("NATIONALID","身份证"),
		JUNGUAN("MILITARYID","军官证"),
		OTHER("OTHER","其他"),
		TAIBAO("TAIWAN","台胞证");
	
		String cnName;
		String jinJiangCardType;
		
		JIN_JIANG_CARD_TYPE(String jinJiangCardType,String cnName){
			this.jinJiangCardType = jinJiangCardType;
			this.cnName=cnName;
		}
		public String getCnName() {
			return cnName;
		}
		public void setCnName(String cnName) {
			this.cnName = cnName;
		}
		public String getJinJiangCardType() {
			return jinJiangCardType;
		}
		public void setJinJiangCardType(String jinJiangCardType) {
			this.jinJiangCardType = jinJiangCardType;
		}
	}
	
	public  int getAge(Date birthDay) {
		 int age =0;
		try{
			Calendar cal = Calendar.getInstance();

	        if (cal.before(birthDay)) {
	           return -100;
	        }

	        int yearNow = cal.get(Calendar.YEAR);
	        int monthNow = cal.get(Calendar.MONTH)+1;
	        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
	       
	        cal.setTime(birthDay);
	        int yearBirth = cal.get(Calendar.YEAR);
	        int monthBirth = cal.get(Calendar.MONTH)+1;
	        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
	        age = yearNow - yearBirth;
	        if (monthNow <= monthBirth) {
	            if (monthNow == monthBirth) {
	                if (dayOfMonthNow < dayOfMonthBirth) {
	                    age--;
	                }
	            } else {
	                age--;
	            }
	        }

		}catch(Exception e){
			
		}
		return age;
   } 
}
