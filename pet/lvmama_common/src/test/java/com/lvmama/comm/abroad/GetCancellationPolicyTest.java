package com.lvmama.comm.abroad;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lvmama.comm.abroad.service.IGetCancellationPolicy;
import com.lvmama.comm.abroad.vo.request.CancellationPolicyMergeReq;
import com.lvmama.comm.abroad.vo.request.CancellationPolicyMergeRoom;
import com.lvmama.comm.abroad.vo.request.CancellationPolicyReq;
import com.lvmama.comm.abroad.vo.response.CancellationPolicyRes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetCancellationPolicyTest extends BaseTest{
	@Autowired
	@Qualifier("abroadhotelCancellationPolicyService")
	private IGetCancellationPolicy getCancellationPolicy;
//	@Test
	public void getCancellationPolicyAllTest() throws Exception{
		CancellationPolicyReq getCancellationPolicyReq=new CancellationPolicyReq();
		getCancellationPolicyReq.setIDroom("NDQ5MDUjREJUVyNEQiMqIzIjMDE=");
		
		CancellationPolicyRes getCancellationPolicyRes=getCancellationPolicy.queryCancellationPolicy(getCancellationPolicyReq,TransHotelContactTest.http_session_id);
		
//		 XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("GetCancellationPolicyRes", CancellationPolicyRes.class);
	    String xml = xstream.toXML(getCancellationPolicyRes);
	    System.out.println(xml); 
		
	}
	@Test
	public void getCancellationPolicyMergeTest() throws Exception{
		CancellationPolicyMergeReq cancellationPolicyMergeReq=new CancellationPolicyMergeReq();
		List<CancellationPolicyMergeRoom> rooms=new ArrayList<CancellationPolicyMergeRoom>();
		CancellationPolicyMergeRoom room1=new CancellationPolicyMergeRoom();
		CancellationPolicyMergeRoom room2=new CancellationPolicyMergeRoom();
		room1.setIDroom("NDQ5MDUjREJUVyNEQiMqIzIjMDE=");
		room1.setQuantity("1");
		room2.setIDroom("MTUzNTg3I0REQ1MjREIjREUjMiMwMg==");
		room2.setQuantity("1");
//		rooms.add(room2);
		rooms.add(room1);
		cancellationPolicyMergeReq.setRooms(rooms);
		
		CancellationPolicyRes getCancellationPolicyRes=getCancellationPolicy.queryCancellationMergePolicy(cancellationPolicyMergeReq,TransHotelContactTest.http_session_id);
		
//		 XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("GetCancellationPolicyRes", CancellationPolicyRes.class);
	    String xml = xstream.toXML(getCancellationPolicyRes);
	    System.out.println(xml); 
		
	}
}
