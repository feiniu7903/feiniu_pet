package com.lvmama.comm.abroad;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.IGetAvailAccom;
import com.lvmama.comm.abroad.vo.request.AvailAccomAdvancedReq;
import com.lvmama.comm.abroad.vo.request.AvailAccomAdvancedRoom;
import com.lvmama.comm.abroad.vo.response.AvailAccomAdvancedRes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetAvailAccomAdvancedTest extends BaseTest {
	@Autowired
	@Qualifier("abroadhotelAvailAccomService")
	private IGetAvailAccom getAvailAccom;
//	@Test
	public void GetAvailAccomAdvancedReqSort(){
		AvailAccomAdvancedReq availAccomReq=new AvailAccomAdvancedReq();
		availAccomReq.setCheckIn(DateUtils.addDays(new Date(),0));
		availAccomReq.setCheckOut(DateUtils.addDays(new Date(),1));
		List<AvailAccomAdvancedRoom> rooms=new ArrayList<AvailAccomAdvancedRoom>();
		AvailAccomAdvancedRoom room=new AvailAccomAdvancedRoom();
		room.setNumAdults(2);
		
		room.setNumChildren(1);
		room.setRoomQuantity(1);
		room.setFirstChildAge("10");
		room.setSecondChildAge("5");
		
		
		AvailAccomAdvancedRoom room2=new AvailAccomAdvancedRoom();
		room2.setNumAdults(2);
		room2.setNumChildren(1);
		room2.setFirstChildAge("8");
//		room.setNumChildren(2);
		room2.setRoomQuantity(1);
		rooms.add(room);
//		rooms.add(room2);
		availAccomReq.setRooms(rooms);
//		availAccomReq.setAccomProdType("X");
//		availAccomReq.setCityId("5014");
		availAccomReq.setCityId("6430");
//		availAccomReq.setCityId("10180");
		availAccomReq.setCountryId("CN");
//		availAccomReq.setHotel("FE");
		List<int[]> pris=new ArrayList<int[]>();
		int[] p1={0,30000};
//		pris.add(p1);
//		availAccomReq.setPrices(pris);
		
		List<String> stars=new ArrayList<String>();
//		stars.add("3");
//		stars.add("5");
		availAccomReq.setStars(stars);
		
		AvailAccomAdvancedRes reqObj=getAvailAccom.queryAvailAccomAdvancedList(availAccomReq,TransHotelContactTest.http_session_id, 0, 100);
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("GetAvailAccomAdvancedRes", AvailAccomAdvancedRes.class);
	    String xml = xstream.toXML(reqObj);
//	    System.out.println(xml); 
//	    Object obj=xstream.fromXML(xml);
	    System.out.println(xml);
	}
	
	
//	@Test
	public void GetAvailAccomAdvancedReqSortAdultOneDayForLog(){
		AvailAccomAdvancedReq availAccomReq=new AvailAccomAdvancedReq();
		availAccomReq.setCheckIn(DateUtils.addDays(new Date(),1));
		availAccomReq.setCheckOut(DateUtils.addDays(new Date(),2));
		List<AvailAccomAdvancedRoom> rooms=new ArrayList<AvailAccomAdvancedRoom>();
		AvailAccomAdvancedRoom room=new AvailAccomAdvancedRoom();
		room.setNumAdults(1);
		room.setRoomQuantity(1);
		rooms.add(room);
		availAccomReq.setHotelId("32308");
		availAccomReq.setCityId("6396");
		availAccomReq.setRooms(rooms);
//		availAccomReq.setAccomProdType("X");
//		availAccomReq.setCityId("5014");
//		availAccomReq.setCityId("6430");
//		availAccomReq.setCityId("10180");
//		availAccomReq.setCountryId("CN");
//		availAccomReq.setHotel("FE");
		
		AvailAccomAdvancedRes reqObj=getAvailAccom.queryAvailAccomAdvancedList(availAccomReq,TransHotelContactTest.http_session_id, 0, 100);
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("GetAvailAccomAdvancedRes", AvailAccomAdvancedRes.class);
	    String xml = xstream.toXML(reqObj);
//	    System.out.println(xml); 
//	    Object obj=xstream.fromXML(xml);
	    System.out.println(xml);
	}
	
	
	
//	@Test
	public void GetAvailAccomAdvancedReqSortForLog(){
		AvailAccomAdvancedReq availAccomReq=new AvailAccomAdvancedReq();
		availAccomReq.setCheckIn(DateUtils.addDays(new Date(),5));
		availAccomReq.setCheckOut(DateUtils.addDays(new Date(),6));
		List<AvailAccomAdvancedRoom> rooms=new ArrayList<AvailAccomAdvancedRoom>();
		AvailAccomAdvancedRoom room=new AvailAccomAdvancedRoom();
		room.setNumAdults(2);
		
		room.setNumChildren(1);
		room.setRoomQuantity(1);
		room.setFirstChildAge("10");
//		room.setSecondChildAge("5");
		
		
		AvailAccomAdvancedRoom room2=new AvailAccomAdvancedRoom();
		room2.setNumAdults(2);
		room2.setNumChildren(1);
		room2.setFirstChildAge("8");
//		room.setNumChildren(2);
		room2.setRoomQuantity(1);
		rooms.add(room);
//		rooms.add(room2);
		availAccomReq.setRooms(rooms);
//		availAccomReq.setAccomProdType("X");
//		availAccomReq.setCityId("5014");
//		availAccomReq.setCityId("6430");
		availAccomReq.setCityId("10180");
//		availAccomReq.setCountryId("CN");
//		availAccomReq.setHotel("FE");
		List<int[]> pris=new ArrayList<int[]>();
		int[] p1={0,30000};
//		pris.add(p1);
//		availAccomReq.setPrices(pris);
		
		List<String> stars=new ArrayList<String>();
//		stars.add("3");
//		stars.add("5");
		availAccomReq.setStars(stars);
		
		AvailAccomAdvancedRes reqObj=getAvailAccom.queryAvailAccomAdvancedList(availAccomReq,TransHotelContactTest.http_session_id, 0, 100);
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("GetAvailAccomAdvancedRes", AvailAccomAdvancedRes.class);
	    String xml = xstream.toXML(reqObj);
//	    System.out.println(xml); 
//	    Object obj=xstream.fromXML(xml);
	    System.out.println(xml);
	}
//	@Test
	public void GetAvailAccomAdvancedReqAdultsForLog() throws Exception{
		AvailAccomAdvancedReq availAccomReq=new AvailAccomAdvancedReq();
		availAccomReq.setCheckIn(DateUtils.addDays(new Date(),3));
		availAccomReq.setCheckOut(DateUtils.addDays(new Date(),4));
		List<AvailAccomAdvancedRoom> rooms=new ArrayList<AvailAccomAdvancedRoom>();
		AvailAccomAdvancedRoom room=new AvailAccomAdvancedRoom();
		room.setNumAdults(1);
		
		room.setNumChildren(1);
		room.setRoomQuantity(1);
		room.setFirstChildAge("10");
//		room.setSecondChildAge("5");
		
		
		AvailAccomAdvancedRoom room2=new AvailAccomAdvancedRoom();
		room2.setNumAdults(1);
//		room2.setNumChildren(1);
//		room2.setFirstChildAge("8");
//		room.setNumChildren(2);
		room2.setRoomQuantity(1);
//		rooms.add(room);
		rooms.add(room2);
		availAccomReq.setRooms(rooms);
//		availAccomReq.setCityId("5014");
//		availAccomReq.setAccomProdType("X");
//		availAccomReq.setCityId("5014");
//		availAccomReq.setCityId("72717");
//		availAccomReq.setCityId("6396");
		availAccomReq.setCityId("10180");
//		availAccomReq.setCountryId("CN");
//		availAccomReq.setHotel("FE");
		List<int[]> pris=new ArrayList<int[]>();
		int[] p1={0,50000};
		int[] p2={50000,99900};
		int[] p3={100000,149900};
		int[] p4={150000};
		pris.add(p1);
		pris.add(p2);
		pris.add(p3);
		pris.add(p4);
//		availAccomReq.setPrices(pris);
		
		List<String> stars=new ArrayList<String>();
		stars.add("3");
		stars.add("5");
//		availAccomReq.setStars(stars);
		
		AvailAccomAdvancedRes reqObj=getAvailAccom.queryAvailAccomAdvancedList(availAccomReq,TransHotelContactTest.http_session_id, 0, 100);
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("GetAvailAccomAdvancedRes", AvailAccomAdvancedRes.class);
	    String xml = xstream.toXML(reqObj);
//	    System.out.println(xml); 
//	    Object obj=xstream.fromXML(xml);
	    System.out.println(xml);
	    
	    FileOutputStream out=new FileOutputStream(new File("d:/ddddd"+System.currentTimeMillis()+".txt"));
	    out.write(xml.getBytes("utf-8"));
	    out.flush();
	    out.close();
	}
	
	@Test
	public void GetAvailAccomAdvancedReqAdultsForLog2() throws Exception{
		AvailAccomAdvancedReq availAccomReq=new AvailAccomAdvancedReq();
		availAccomReq.setCheckIn(DateUtils.addDays(new Date(),3));
		availAccomReq.setCheckOut(DateUtils.addDays(new Date(),4));
		List<AvailAccomAdvancedRoom> rooms=new ArrayList<AvailAccomAdvancedRoom>();
		AvailAccomAdvancedRoom room=new AvailAccomAdvancedRoom();
		room.setNumAdults(1);
		
		room.setNumChildren(1);
		room.setRoomQuantity(1);
		room.setFirstChildAge("10");
//		room.setSecondChildAge("5");
		
		
		AvailAccomAdvancedRoom room2=new AvailAccomAdvancedRoom();
		room2.setNumAdults(2);
//		room2.setNumChildren(1);
//		room2.setFirstChildAge("8");
//		room.setNumChildren(2);
		room2.setRoomQuantity(1);
//		rooms.add(room);
		rooms.add(room2);
		availAccomReq.setRooms(rooms);
//		availAccomReq.setCityId("5014");
//		availAccomReq.setAccomProdType("X");
		availAccomReq.setCityId("10180");
//		availAccomReq.setCityId("72717");
//		availAccomReq.setCityId("6396");
//		availAccomReq.setCityId("7607");
//		availAccomReq.setCountryId("CN");
//		availAccomReq.setHotel("FE");
		List<int[]> pris=new ArrayList<int[]>();
		int[] p1={0,50000};
		int[] p2={50000,99900};
		int[] p3={100000,149900};
		int[] p4={150000};
		pris.add(p1);
		pris.add(p2);
		pris.add(p3);
		pris.add(p4);
//		availAccomReq.setPrices(pris);
		
		List<String> stars=new ArrayList<String>();
		stars.add("3");
		stars.add("5");
//		availAccomReq.setStars(stars);
		
		availAccomReq.setAccomProdType("U");
		
		AvailAccomAdvancedRes reqObj=getAvailAccom.queryAvailAccomAdvancedList(availAccomReq,TransHotelContactTest.http_session_id, 0, 100);
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("GetAvailAccomAdvancedRes", AvailAccomAdvancedRes.class);
	    String xml = xstream.toXML(reqObj);
//	    System.out.println(xml); 
//	    Object obj=xstream.fromXML(xml);
	    System.out.println(xml);
	    
	    FileOutputStream out=new FileOutputStream(new File("d:/ddddd"+System.currentTimeMillis()+".txt"));
	    out.write(xml.getBytes("utf-8"));
	    out.flush();
	    out.close();
	}
}
