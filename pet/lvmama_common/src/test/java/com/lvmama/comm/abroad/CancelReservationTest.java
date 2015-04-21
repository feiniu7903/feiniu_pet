package com.lvmama.comm.abroad;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.ICancelReservation;
import com.lvmama.comm.abroad.vo.request.CancelReservationReq;
import com.lvmama.comm.abroad.vo.response.CancelReservationRes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class CancelReservationTest extends BaseTest {
	@Autowired
	@Qualifier("abroadhotelCancelReservationService")
	private ICancelReservation cancelReservation;
	@Test
	public void cancelReservationTest(){
		CancelReservationReq req=new CancelReservationReq();
		req.setOrderId("1661");
		req.setUserId("0");
		req.setRemark("测试取消订单--rxq");
		
		CancelReservationRes cancelReservationRes=cancelReservation.cancelReservation(req, TransHotelContactTest.http_session_id);
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("CancelReservationRes", CancelReservationRes.class);
	    String xml = xstream.toXML(cancelReservationRes);
	    System.out.println(xml);
	}
}
