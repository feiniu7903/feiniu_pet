package com.lvmama.comm.abroad;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.IDownOrder;
import com.lvmama.comm.abroad.vo.request.Adult;
import com.lvmama.comm.abroad.vo.request.Child;
import com.lvmama.comm.abroad.vo.request.DownOrderReq;
import com.lvmama.comm.abroad.vo.request.Room;
import com.lvmama.comm.abroad.vo.response.DownOrderRes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DownOrderTest extends BaseTest {
	@Autowired
	@Qualifier("abroadhoteldownOrderService")
	private IDownOrder downOrder;
	
//	@Test
	public void testDownOrder(){
		DownOrderReq req=new DownOrderReq();
		req.setUserID("4028807436680a820136b903b6e200c3");
		req.setUser_memo("客户端测试2");
		req.setHotelId("4099249");
		List<Room> rooms=new ArrayList<Room>();
		Room room=new Room();
		room.setIDroom("NDA5OTI0OSNJTkRWIyojKiMxIzAw");
		room.setQuantity("1");
		Room room2=new Room();
		room2.setIDroom("NDA5OTI0OSNEQlRXIyojKiMyIzAy");
		room2.setQuantity("1");
		
		rooms.add(room);
		rooms.add(room2);
		req.setRooms(rooms);
		req.setCheckIn("2012-06-25");
		req.setCheckOut("2012-06-26");
		req.setStayNights("1");
		req.setTotalPrice(236000);
		List<Adult> adults=new ArrayList<Adult>();
		Adult adult=new Adult();
		adult.setFirstName("ruan");
		adult.setLastName("xiequan");
		adult.setClientEmail("ruanxiequan@lvmma.com");
		adult.setMobilePhone("13946584380");
		adult.setIdentityType("身份证");
		adult.setIdentityNumb("33062165486465132489");
		
		Adult adult2=new Adult();
		adult2.setFirstName("ruan");
		adult2.setLastName("xiequa");
		adult2.setClientEmail("ruanxiequan@lvmma.com");
		adult2.setMobilePhone("13946584380");
		adult2.setIdentityType("身份证");
		adult2.setIdentityNumb("330621654864651329");
		
		Adult adult3=new Adult();
		adult3.setFirstName("ruan");
		adult3.setLastName("xiequ");
		adult3.setClientEmail("ruanxiequan@lvmma.com");
		adult3.setMobilePhone("13946584380");
		adult3.setIdentityType("身份证");
		adult3.setIdentityNumb("330621654864632489");
		
		adults.add(adult);
		adults.add(adult2);
		adults.add(adult3);
		
		req.setAdults(adults);
		DownOrderRes res=downOrder.downOrder(req,TransHotelContactTest.http_session_id);
		
		
		
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("DownOrderReq", DownOrderReq.class);
	    String xml = xstream.toXML(res);
	    System.out.println(xml); 

		
	}
//	@Test
	public void testDownOrder4Log(){
		DownOrderReq req=new DownOrderReq();
		req.setUserID("4028807436680a820136b903b6e200c3");
		req.setUser_memo("客户端测试2");
		req.setHotelId("4099249");
		List<Room> rooms=new ArrayList<Room>();
		Room room=new Room();
		room.setIDroom("MTI3NDA5I1RETjEjREUjREUjMiMxMA==");
		room.setQuantity("1");
		Room room2=new Room();
		room2.setIDroom("NDA5OTI0OSNEQlRXIyojKiMyIzAy");
		room2.setQuantity("1");
		
		rooms.add(room);
//		rooms.add(room2);
		req.setRooms(rooms);
		req.setCheckIn("2012-07-02");
		req.setCheckOut("2012-07-03");
		req.setStayNights("1");
		req.setTotalPrice(23000);
		List<Adult> adults=new ArrayList<Adult>();
		Adult adult=new Adult();
		adult.setFirstName("ruan");
		adult.setLastName("xiequan");
		adult.setClientEmail("ruanxiequan@lvmma.com");
		adult.setMobilePhone("13946584380");
		adult.setIdentityType("身份证");
		adult.setIdentityNumb("33062165486465132489");
		
		Adult adult2=new Adult();
		adult2.setFirstName("ding");
		adult2.setLastName("ming");
		adult2.setClientEmail("dingming@lvmma.com");
		adult2.setMobilePhone("13946584380");
		adult2.setIdentityType("身份证");
		adult2.setIdentityNumb("330621654864651329");
		
		Adult adult3=new Adult();
		adult3.setFirstName("ruan");
		adult3.setLastName("xiequ");
		adult3.setClientEmail("ruanxiequan@lvmma.com");
		adult3.setMobilePhone("13946584380");
		adult3.setIdentityType("身份证");
		adult3.setIdentityNumb("330621654864632489");
		
		adults.add(adult);
		adults.add(adult2);
//		adults.add(adult3);
		req.setAdults(adults);
		
		Child child=new Child();
		child.setAge("10");
		try {
			child.setBirthday(DateUtils.parseDate("2002-10-01", new String[]{"yyyy-MM-dd"}));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		child.setFirstName("xiao");
		child.setLastName("ming");
		child.setIdentityNumb("450112200210014238");
		child.setIdentityType("身份证");
		child.setMobilePhone("13845021956");
		List<Child> children=new ArrayList<Child>();
		children.add(child);
		req.setChild(children);
		
		DownOrderRes res=downOrder.downOrder(req,TransHotelContactTest.http_session_id);
		
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("DownOrderReq", DownOrderReq.class);
	    String xml = xstream.toXML(res);
	    System.out.println(xml); 

		
	}

	
//	@Test
	public void testDownOrderAdults4Log(){
		DownOrderReq req=new DownOrderReq();
		req.setUserID("4028807436680a820136b903b6e200c3");
		req.setUser_memo("客户端测试2");
		req.setHotelId("4261748");
		List<Room> rooms=new ArrayList<Room>();
		Room room=new Room();
		room.setIDroom("NDI2MTc0OCNKVVNVIyojKiMyIzAw");
		room.setQuantity("2");
		Room room2=new Room();
		room2.setIDroom("NDI2MTc0OCNTRDIzIyojKiMzIzAx");
		room2.setQuantity("2");
		
		rooms.add(room);
		rooms.add(room2);
		req.setRooms(rooms);
		req.setCheckIn("2012-07-02");
		req.setCheckOut("2012-07-05");
		req.setStayNights("3");
		req.setTotalPrice(4472000);
		List<Adult> adults=new ArrayList<Adult>();
		Adult adult=new Adult();
		adult.setFirstName("ruan");
		adult.setLastName("xiequan");
		adult.setClientEmail("ruanxiequan@lvmama.com");
		adult.setMobilePhone("13946584380");
		adult.setIdentityType("身份证");
		adult.setIdentityNumb("33062165486465132489");
		
		Adult adult2=new Adult();
		adult2.setFirstName("ding");
		adult2.setLastName("ming");
		adult2.setClientEmail("dingming@lvmma.com");
		adult2.setMobilePhone("13946584380");
		adult2.setIdentityType("身份证");
		adult2.setIdentityNumb("330621654864651329");
		
		Adult adult3=new Adult();
		adult3.setFirstName("ruan");
		adult3.setLastName("xiequ");
		adult3.setClientEmail("ruanxiequan@lvmma.com");
		adult3.setMobilePhone("13946584380");
		adult3.setIdentityType("身份证");
		adult3.setIdentityNumb("330621654864632489");
		
		
		
		Adult adult4=new Adult();
		adult4.setFirstName("ding");
		adult4.setLastName("jia");
		adult4.setClientEmail("dingjia@lvmma.com");
		adult4.setMobilePhone("13946584380");
		adult4.setIdentityType("身份证");
		adult4.setIdentityNumb("330621654864632404");
		
		Adult adult5=new Adult();
		adult5.setFirstName("ding");
		adult5.setLastName("yi");
		adult5.setClientEmail("dingyi@lvmma.com");
		adult5.setMobilePhone("13946584380");
		adult5.setIdentityType("身份证");
		adult5.setIdentityNumb("330621654864632405");
		
		Adult adult6=new Adult();
		adult6.setFirstName("ding");
		adult6.setLastName("bing");
		adult6.setClientEmail("dingbing@lvmma.com");
		adult6.setMobilePhone("13946584380");
		adult6.setIdentityType("身份证");
		adult6.setIdentityNumb("330621654864632406");
		
		Adult adult7=new Adult();
		adult7.setFirstName("he");
		adult7.setLastName("jia");
		adult7.setClientEmail("hejia@lvmma.com");
		adult7.setMobilePhone("13946584380");
		adult7.setIdentityType("身份证");
		adult7.setIdentityNumb("330621654864632407");
		
		Adult adult8=new Adult();
		adult8.setFirstName("he");
		adult8.setLastName("yi");
		adult8.setClientEmail("heyi@lvmma.com");
		adult8.setMobilePhone("13946584380");
		adult8.setIdentityType("身份证");
		adult8.setIdentityNumb("330621654864632408");
		
		Adult adult9=new Adult();
		adult9.setFirstName("he");
		adult9.setLastName("bing");
		adult9.setClientEmail("hebing@lvmma.com");
		adult9.setMobilePhone("13946584380");
		adult9.setIdentityType("身份证");
		adult9.setIdentityNumb("330621654864632409");
		
		Adult adult10=new Adult();
		adult10.setFirstName("he");
		adult10.setLastName("ding");
		adult10.setClientEmail("heding@lvmma.com");
		adult10.setMobilePhone("13946584380");
		adult10.setIdentityType("身份证");
		adult10.setIdentityNumb("330621654864632420");
		
		adults.add(adult);
		adults.add(adult2);
		adults.add(adult3);
		adults.add(adult4);
		adults.add(adult5);
		adults.add(adult6);
		adults.add(adult7);
		adults.add(adult8);
		adults.add(adult9);
		adults.add(adult10);
		req.setAdults(adults);
		
//		Child child=new Child();
//		child.setAge("10");
//		try {
//			child.setBirthday(DateUtils.parseDate("2002-10-01", new String[]{"yyyy-MM-dd"}));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		child.setFirstName("xiao");
//		child.setLastName("ming");
//		child.setIdentityNumb("450112200210014238");
//		child.setIdentityType("身份证");
//		child.setMobilePhone("13845021956");
//		List<Child> children=new ArrayList<Child>();
//		children.add(child);
//		req.setChild(children);
		
		DownOrderRes res=downOrder.downOrder(req,TransHotelContactTest.http_session_id);
		
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("DownOrderReq", DownOrderReq.class);
	    String xml = xstream.toXML(res);
	    System.out.println(xml); 

		
	}

	@Test
	public void testDownOrderForSingle(){
		DownOrderReq req=new DownOrderReq();
		req.setUserID("4028807436680a820136b903b6e200c3");
//		req.setUser_memo("客户端测试2");
		req.setHotelId("127335");
		List<Room> rooms=new ArrayList<Room>();
		Room room=new Room();
		room.setIDroom("MTI3MzM1I0RCQ00jKiMqIzIjMDA=");
		room.setQuantity("1");
		
		Room room2=new Room();
		room2.setIDroom("NDA5OTI0OSNEQlRXIyojKiMyIzAy");
		room2.setQuantity("1");
		
		rooms.add(room);
//		rooms.add(room2);
		req.setRooms(rooms);
//		req.setUser_memo("1;2");
		req.setCheckIn("2012-09-22");
		req.setCheckOut("2012-09-23");
		req.setStayNights("1");
		req.setTotalPrice(19000);
		List<Adult> adults=new ArrayList<Adult>();
		Adult adult=new Adult();
		adult.setFirstName("ruan");
		adult.setLastName("xiequan");
		adult.setClientEmail("ruanxiequan@lvmama.com");
		adult.setMobilePhone("13946584380");
		adult.setIdentityType("身份证");
		adult.setIdentityNumb("33062165486465132489");
		
		Adult adult2=new Adult();
		adult2.setFirstName("ruan");
		adult2.setLastName("xiequa");
		adult2.setClientEmail("ruanxiequan@lvmma.com");
		adult2.setMobilePhone("13946584380");
		adult2.setIdentityType("身份证");
		adult2.setIdentityNumb("330621654864651329");
		
		Adult adult3=new Adult();
		adult3.setFirstName("ruan");
		adult3.setLastName("xiequ");
		adult3.setClientEmail("ruanxiequan@lvmma.com");
		adult3.setMobilePhone("13946584380");
		adult3.setIdentityType("身份证");
		adult3.setIdentityNumb("330621654864632489");
		
		adults.add(adult);
		adults.add(adult2);
//		adults.add(adult3);
		
		req.setAdults(adults);
		DownOrderRes res=downOrder.downOrder(req,TransHotelContactTest.http_session_id);
		
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("DownOrderReq", DownOrderReq.class);
	    String xml = xstream.toXML(res);
	    System.out.println(xml); 
	}
}
