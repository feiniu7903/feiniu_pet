package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;

public class MarkItem {

	private static List<BuyInfo.Item> itemList = new ArrayList<BuyInfo.Item>();
	//单门票   time1() 8/22 
	//21 售:10.0 结:10.0 市:50 库:不限
	// 22 售:11.0 结:10.0 市:50 库:不限 
	// 23 售:12.0 结:10.0 市:50 库:不限 
	// 24 售:13.0 结:10.0 市:50 库:不限 
	// 25 售:14.0 结:10.0 市:50 库:不限 
//	select * from prod_product p where p.product_id=30394
//	select * from meta_product m  where m.meta_product_id=10769
//
//	select mp.meta_product_id,t.*,mp.META_PRODUCT_ID,mp.PRODUCT_NAME,mp.PRODUCT_TYPE from META_PERFORM p, SUP_PERFORM_TARGET t,META_PRODUCT mp
//			where p.TARGET_ID = t.TARGET_ID and p.META_PRODUCT_ID = mp.META_PRODUCT_ID
//			 and t.VALID='Y' and  mp.meta_product_id=10769
	private static Item  ticket(Date time){
		Item item1 = new Item();
		item1.setProductId(36046);
		item1.setProductBranchId(2453);
		item1.setQuantity(2);
		item1.setVisitTime(time);
		item1.setFaxMemo("Test1");

		OrdTimeInfo timeInfo = new OrdTimeInfo();
		timeInfo.setProductId(item1.getProductId());
		timeInfo.setQuantity(1L);
		timeInfo.setVisitTime(time);
	
		List<OrdTimeInfo> timeInfoList = new ArrayList<OrdTimeInfo>();
		timeInfoList.add(timeInfo);
		item1.setTimeInfoList(timeInfoList);
		return item1;
	}
	//房差    time1() 8/22 
	//21 售:30.0 结:80.0 市:100 库:不限 
	//22 售:30.0 结:80.0 市:100 库:不限 
	//23 售:30.0 结:80.0 市:100 库:不限 
	//24 售:30.0 结:80.0 市:100 库:不限 
	//25 售:30.0 结:80.0 市:100 库:不限 
	private static Item  otherHotel(){
		//房差
		Item item = new Item();
		item.setProductId(33109);
//		item.setProductBranchId(productBranchId);
		item.setQuantity(1);
		item.setVisitTime(time1());
		item.setFaxMemo("Test1");

		OrdTimeInfo timeInfo = new OrdTimeInfo();
		timeInfo.setProductId(item.getProductId());
		timeInfo.setQuantity(1L);
		timeInfo.setVisitTime(time1());
	
		List<OrdTimeInfo> timeInfoList = new ArrayList<OrdTimeInfo>();
		timeInfoList.add(timeInfo);
		item.setTimeInfoList(timeInfoList);
		return item;
	}
	//保险  time1() 8/22
	//21 售:1.0 结:1.0 市:1 库:不限 
	//22 售:1.0 结:1.0 市:1 库:不限 
	//23 售:1.0 结:1.0 市:1 库:不限
	//24 售:1.0 结:1.0 市:1 库:不限 
	//25 售:1.0 结:1.0 市:1 库:不限 
	private static Item  Other(){
		//保险
		Item item = new Item();
		item.setProductId(30369);
		item.setQuantity(1);
		item.setVisitTime(time1());
		item.setFaxMemo("Test1");

		OrdTimeInfo timeInfo = new OrdTimeInfo();
		timeInfo.setProductId(item.getProductId());
		timeInfo.setQuantity(1L);
		timeInfo.setVisitTime(time1());
	
		List<OrdTimeInfo> timeInfoList = new ArrayList<OrdTimeInfo>();
		timeInfoList.add(timeInfo);
		item.setTimeInfoList(timeInfoList);
		return item;
	}
	//门票   
	static List markTicket(){
		itemList.clear();
		itemList.add(ticket(time2()));
		return itemList;
	}
	//门票+房差  
	static  public  List markTicketHotel(){
		itemList.clear();
		itemList.add(ticket(time1()));
		itemList.add(otherHotel());
		return itemList;
	}
	//门票+房差+保险
	static  List markTicketHotelOther(){
		itemList.clear();
		itemList.add(ticket(time1()));
		itemList.add(otherHotel());
		itemList.add(Other());
		return itemList;
	}
	
	//-----------------------------------------------------------------------
	//单房型 多天     time1() 8/22   time2() 8/23   time3() 8/24
	//21 售:2.0 结:10.0 市:20 库:不限 
	//22 售:3.0 结:10.0 市:20 库:不限 
	//23 售:4.0 结:10.0 市:20 库:不限
	//24 售:5.0 结:10.0 市:20 库:不限 
	//25 售:30.0 结:10.0 市:20 库:不限 
	private static Item singleHotel(){
		Item item = new Item();
		item.setProductId(36075);
		item.setQuantity(1);
		item.setVisitTime(time1());
		item.setFaxMemo("Test1");
		
		OrdTimeInfo timeInfo1 = new OrdTimeInfo();
		timeInfo1.setProductId(item.getProductId());
		timeInfo1.setQuantity(1L);
		timeInfo1.setVisitTime(time1());
		
		OrdTimeInfo timeInfo2 = new OrdTimeInfo();
		timeInfo2.setProductId(item.getProductId());
		timeInfo2.setQuantity(1L);
		timeInfo2.setVisitTime(time2());
		
		OrdTimeInfo timeInfo3 = new OrdTimeInfo();
		timeInfo3.setProductId(item.getProductId());
		timeInfo3.setQuantity(1L);
		timeInfo3.setVisitTime(time3());
		
		List<OrdTimeInfo> timeInfoList = new ArrayList<OrdTimeInfo>();
		timeInfoList.add(timeInfo1);
		timeInfoList.add(timeInfo2);
		timeInfoList.add(timeInfo3);
		item.setTimeInfoList(timeInfoList);
		return item;
	}
	//单房型 多天   
	  static List marksingleHotel(){
		itemList.clear();
		itemList.add(singleHotel());
		return itemList;
	}
	//单房型+房差  
	  static List marksingleHotelHotel(){
		itemList.clear();
		itemList.add(singleHotel());
		itemList.add(otherHotel());
		return itemList;
	}
	//单房型+房差+保险
	  static List marksingleHotelHotelOther(){
		itemList.clear();
		itemList.add(singleHotel());
		itemList.add(otherHotel());
		itemList.add(Other());
		return itemList;
	}
	//------------------------------------------------------------------
	//套房
	//21 售:1500.0 结:15.0 市:25 库:不限 
	//22 售:100.0 结:15.0 市:25 库:不限 
	//23 售:1500.0 结:15.0 市:25 库:不限 
	//24 售:1500.0 结:15.0 市:25 库:不限 
	//25 售:1500.0 结:15.0 市:25 库:不限 
	private static Item Hotel(){
		Item item = new Item();
		item.setProductId(30230);
		item.setQuantity(1);
		item.setVisitTime(time1());
		item.setFaxMemo("Test1");
		
		OrdTimeInfo timeInfo = new OrdTimeInfo();
		timeInfo.setProductId(item.getProductId());
		timeInfo.setQuantity(1L);
		timeInfo.setVisitTime(time1());
		
		List<OrdTimeInfo> timeInfoList = new ArrayList<OrdTimeInfo>();
		timeInfoList.add(timeInfo);
		item.setTimeInfoList(timeInfoList);
		return item;
	}
	
	//套房   
	  static List markHotel(){
		itemList.clear();
		itemList.add(Hotel());
		return itemList;
	}
	//套房+房差
	  static List markHotelHotel(){
		itemList.clear();
		itemList.add(Hotel());
		itemList.add(otherHotel());
		return itemList;
	}
	//套房+房差+其他
	  static List markHotelHotelOther(){
		itemList.clear();
		itemList.add(Hotel());
		itemList.add(otherHotel());
		itemList.add(Other());
		return itemList;
	}
	//----------------------------------------------------------------------------
	//多个门票
	  static List markMoreTicket(){
		itemList.clear();
		itemList.add(ticket(time1()));
		itemList.add(ticket(time2()));
		itemList.add(ticket(time3()));
		return itemList;
	}
	//多个门票+房差
	  static List markMoreTicketHotel(){
		itemList.clear();
		itemList.add(ticket(time1()));
		itemList.add(ticket(time2()));
		itemList.add(ticket(time3()));
		itemList.add(otherHotel());
		return itemList;
	}
	//多个门票+房差+其他
	  static List markMoreTicketHotelOther(){
		itemList.clear();
		itemList.add(ticket(time1()));
		itemList.add(ticket(time2()));
		itemList.add(ticket(time3()));
		itemList.add(otherHotel());
		itemList.add(Other());
		return itemList;
	}
	
	//------------------------------------------------------------------
	//门票+单房型
	  static List markTicketSingleHotel(){
		itemList.clear();
		itemList.add(singleHotel());
		itemList.add(ticket(time1()));
		return itemList;
	}
	//门票+单房型+房差
	  static  List markTicketSingleHotelHotel(){
		itemList.clear();
		itemList.add(singleHotel());
		itemList.add(ticket(time1()));
		itemList.add(otherHotel());
		return itemList;
	}
	//门票+单房型+房差+其他
	  static  List markTicketSingleHotelHotelOther(){
		itemList.clear();
		itemList.add(singleHotel());
		itemList.add(ticket(time1()));
		itemList.add(otherHotel());
		itemList.add(Other());
		return itemList;
	}
	//--------------------------------------------------------------
	//门票+套房
	  static List markTicketMoreHotel(){
		itemList.clear();
		itemList.add(Hotel());
		itemList.add(ticket(time1()));
		return itemList;
	}
	//门票+套房+房差
	  static List markTicketHotelHotel(){
		itemList.clear();
		itemList.add(Hotel());
		itemList.add(ticket(time1()));
		itemList.add(otherHotel());
		return itemList;
	}
	//门票+套房+房差+保险
	  static List markTicketHotelHotelOther(){
		itemList.clear();
		itemList.add(Hotel());
		itemList.add(ticket(time1()));
		itemList.add(otherHotel());
		itemList.add(Other());
		return itemList;
	}
	
	
	
	private static Date time1(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011);
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.DATE, 23);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	private static Date time2(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011);
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.DATE, 24);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	private static Date time3(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011);
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.DATE, 25);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public void setItemList(
			List<BuyInfo.Item> itemList) {
		this.itemList = itemList;
	}

}
