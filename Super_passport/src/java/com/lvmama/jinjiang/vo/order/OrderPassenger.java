package com.lvmama.jinjiang.vo.order;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;

import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.IdentityCardUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class OrderPassenger extends OrdPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3172331212682567688L;

	private List<?> orderAdditions;
	private String accompany;
	private String singleRoom;
	private String uniqueId;
	private String refundAmountType;
	private long factRefundAmount;
	
	public String getShholidayCardType(){
		SH_HOLIDAY_CART_TYPE t = SH_HOLIDAY_CART_TYPE.ID_CARD;
		if(this.getCertType()!=null){
			t = EnumUtils.getEnum(SH_HOLIDAY_CART_TYPE.class, this.getCertType());
		}
		if(t==null){
			return "";
		}
		return t.getVal();
	}
	public String getPassengerBrithday(){
		Date brithday = this.getBrithday();
		if(brithday==null && this.getCertNo()!=null){
			brithday = IdentityCardUtil.getDate(this.getCertNo());
		}
		if(brithday!=null){
			return DateUtil.formatDate(brithday, "yyyyMMdd");
		}
		return "";
	}
	public String getSex(){
		return "F".equalsIgnoreCase(this.getGender()) ? "F" : "M";
	}
	
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	
	public String getSingleRoom() {
		return singleRoom;
	}

	public void setSingleRoom(String singleRoom) {
		this.singleRoom = singleRoom;
	}

	public String getRefundAmountType() {
		return refundAmountType;
	}

	public void setRefundAmountType(String refundAmountType) {
		this.refundAmountType = refundAmountType;
	}

	public float getFactRefundAmount() {
		return PriceUtil.convertToYuan(factRefundAmount);
	}

	public void setFactRefundAmount(long factRefundAmount) {
		this.factRefundAmount = factRefundAmount;
	}

	public String getPassengerType(){
		Date brithday = this.getBrithday();
		if(Constant.CERT_TYPE.ID_CARD.getCode().equals(this.getCertType())&&this.getCertNo()!=null){
			brithday = IdentityCardUtil.getDate(this.getCertNo());
		}
		if(brithday!=null){
			int age =getAge(brithday);
			if(age==-100){
				return null;
			}
			if(age<2){
				return "INF";
			}if(age>=2 && age<=12){
				return "CHD";
			}
		}
		return "ADU";
	}
	static enum SH_HOLIDAY_CART_TYPE{
		GANGAO("4"),
		HUIXIANG("6"),
		HUZHAO("2"),
		ID_CARD("1"),
		JUNGUAN("3"),
		OTHER("10"),
		TAIBAO("5");
		
		private String val;
		SH_HOLIDAY_CART_TYPE(String val){
			this.val=val;
		}
		String getVal() {
			return val;
		}
		
		
	}

	public boolean isMe(OrdPerson per) {
		if(StringUtils.equals(this.getName(), per.getName()) &&
				(StringUtils.equals(this.getCertNo(), per.getCertNo()) 
						|| StringUtils.equals(DateUtils.formatDate(this.getBrithday(), "yyyyMMdd"), DateUtils.formatDate(per.getBrithday(), "yyyyMMdd")) )){
			return true;
		}
		return false;
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
