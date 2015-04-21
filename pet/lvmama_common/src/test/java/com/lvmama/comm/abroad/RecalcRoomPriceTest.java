package com.lvmama.comm.abroad;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.IRecalcRoomPrice;
import com.lvmama.comm.abroad.vo.request.RecalcRoomPriceReq;
import com.lvmama.comm.abroad.vo.request.RecalcRoomPriceReqRoom;
import com.lvmama.comm.abroad.vo.response.RecalcRoomPriceRes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RecalcRoomPriceTest extends BaseTest{
	@Autowired
	@Qualifier("abroadhotelRecalcRoomPriceService")
	private IRecalcRoomPrice recalcRoomPrice;
	@Test
	public void recalcRoomPriceTest() throws Exception{
		RecalcRoomPriceReq recalcRoomPriceReq=new RecalcRoomPriceReq();
		
		List<RecalcRoomPriceReqRoom> rooms=new ArrayList<RecalcRoomPriceReqRoom>();
		RecalcRoomPriceReqRoom room=new RecalcRoomPriceReqRoom();
//		room.setIDBoardTypeAdults("DE");
		room.setIDroom("NDQ5MDUjREJUVyNEQiMqIzIjMDE=");
		room.setQuantity("1");
		rooms.add(room);
		RecalcRoomPriceReqRoom room2=new RecalcRoomPriceReqRoom();
		room2.setIDroom("NDAwNjI0OCNDMkRCI0NUIyojMiMwMQ==");
		room2.setQuantity("1");
//		rooms.add(room2);
		RecalcRoomPriceReqRoom room3=new RecalcRoomPriceReqRoom();
		room3.setIDroom("NDA5OTI0OSNEQkxJIyojKiMxIzAx");
		room3.setQuantity("1");
//		rooms.add(room3);
		RecalcRoomPriceReqRoom room4=new RecalcRoomPriceReqRoom();
		room4.setIDroom("NDA5OTI0OSNEQkxJIyojKiMxIzAx");
		room4.setQuantity("1");
//		rooms.add(room4);
		recalcRoomPriceReq.setRooms(rooms);
		RecalcRoomPriceRes recalcRoomPriceRes=recalcRoomPrice.recalcRoomPrice(recalcRoomPriceReq, TransHotelContactTest.http_session_id);
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("recalcRoomPriceRes", RecalcRoomPriceRes.class);
	    String xml = xstream.toXML(recalcRoomPriceRes);
	    System.out.println(xml); 
		
	}
}
