package com.lvmama.comm.abroad;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.IGetHotelDetails;
import com.lvmama.comm.abroad.vo.request.HotelDetailsReq;
import com.lvmama.comm.abroad.vo.response.HotelDetailsRes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetHotelDetailsTest extends BaseTest{

	@Autowired
	@Qualifier("abroadhotelHotelDetailsService")
	private IGetHotelDetails getHotelDetails;
	@Test
	public void queryForObject(){
		HotelDetailsReq getHotelDetailsReq=new HotelDetailsReq();
		getHotelDetailsReq.setIDhotel("44905");
		getHotelDetailsReq.setLanguage("ENG");
		HotelDetailsRes getHotelDetailsRes=getHotelDetails.queryHotelDetailsInfo(getHotelDetailsReq);
		
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("GetHotelDetailsRes", HotelDetailsRes.class);
	    String xml = xstream.toXML(getHotelDetailsRes);
	    System.out.println(xml); 
		
	}
}
