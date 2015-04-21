package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;

public class MarkBuyInfo {
	
	public static BuyInfo markBuyInfo(List list){
		BuyInfo buyInfo = new BuyInfo();
		
		buyInfo.setChannel("FRONTEND");
		buyInfo.setUserId("UnitTest");
		buyInfo.setPaymentTarget("TOLVMAMA");
		buyInfo.setUserMemo("UnitTest");
//		buyInfo.setResourceConfirmStatus("true");
		
		buyInfo.setItemList(list);
	   
	    
		Person person1 = new Person();
		person1.setPersonType("traveller");
		person1.setName("www");
		person1.setMobile("13918066110");
		person1.setEmail("sericwu@hotmail.com");
		Person person2 = new Person();
		person2.setPersonType("traveller");
		person2.setName("www");
		person2.setFax("13918066110");
		person2.setMemo("sericwu@hotmail.com");
		List<Person> personList = new ArrayList<Person>();
		personList.add(person1);
		personList.add(person2);
		buyInfo.setPersonList(personList);
		
    return buyInfo;
	}
	
	
}
