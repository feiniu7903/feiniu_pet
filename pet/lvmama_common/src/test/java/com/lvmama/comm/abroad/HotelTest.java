package com.lvmama.comm.abroad;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.IHotel;
import com.lvmama.comm.abroad.vo.request.HotelReq;
import com.lvmama.comm.abroad.vo.response.HotelRes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class HotelTest extends BaseTest {
	@Autowired
	@Qualifier("abroadhotelsearchHotelService")
	private IHotel hotel;

	@Test
	public void searchHotelByName() {
		HotelReq req = new HotelReq();
		req.setCityId("11317");
		req.setHotelName("BEIJING ZHONG AN");
		HotelRes res=hotel.searchHotelByName(req, TransHotelContactTest.http_session_id);
		
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("SearhotleByName", HotelReq.class);
	    String xml = xstream.toXML(res);
	    System.out.println(xml); 

	}

}
