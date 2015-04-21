package com.lvmama.clutter.utils;


import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.Person;


public class UserReceiverUtils {
	

	
	public static  boolean isUpdateMobile(Person person,UsrReceivers usrReceivers){
		if (person.isCertIsNull()) {
			if (!person.getMobile().equals(usrReceivers.getMobileNumber())){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isUpdateCardNumber(Person person,UsrReceivers usrReceivers){
		if (!person.isCertIsNull()) {
			if(person.getCertType().equals(usrReceivers.getCardType())){
				return true; 
			} else if(!person.getCertType().equals(usrReceivers.getCardType())){
				return false;
			} else if (!person.getMobile().equals(usrReceivers.getMobileNumber())){
				return true;
			}
			
		}
		return false;
	}
	

}
