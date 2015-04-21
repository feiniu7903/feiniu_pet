package com.lvmama.comm.abroad;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.IRoomDetail;
import com.lvmama.comm.abroad.vo.request.RoomDetailsReq;
import com.lvmama.comm.abroad.vo.response.RoomDetailsRes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RoomDetailTest extends BaseTest {
	@Autowired
	@Qualifier("abroadhotelgetRoomDetailsService")
	private IRoomDetail roomDetail;
	
	@Test
	public void testRoomDetail(){
		RoomDetailsReq req=new RoomDetailsReq();
		req.setIDroom("NDI0ODc0OCNJTlFVIyojKiMxIzAw");
		RoomDetailsRes res=roomDetail.getRoomDetail(req, TransHotelContactTest.http_session_id);
		
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("getRoomDetails", RoomDetailsRes.class);
	    String xml = xstream.toXML(res);
	    System.out.println(xml); 

	}

}
