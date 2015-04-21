package com.lvmama.comm.abroad;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.ICity;
import com.lvmama.comm.abroad.vo.request.CityReq;
import com.lvmama.comm.abroad.vo.response.CityRes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class CityTest extends BaseTest {
	@Autowired
	@Qualifier("abroadhotelsearchCityService")
	private ICity city;

	@Test
	public void searchCityByName() {
		long start=System.currentTimeMillis();
		CityReq req = new CityReq();
		req.setCityName("newyo");
		CityRes res=city.searchCityByName(req,TransHotelContactTest.http_session_id);
		
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("SearcityByName", CityRes.class);
	    String xml = xstream.toXML(res);
	    System.out.println(xml); 
	    System.out.println(res.getCities().size());
	    long end=System.currentTimeMillis();
	    System.out.println("start="+start+" end="+end +"  long=" +(end-start));
	}

}
