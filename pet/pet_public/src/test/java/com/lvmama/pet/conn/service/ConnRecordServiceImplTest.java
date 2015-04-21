package com.lvmama.pet.conn.service;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.conn.ConnRecord;
import com.lvmama.comm.pet.service.conn.ConnRecordService;
import com.lvmama.comm.pet.vo.Page;

@ContextConfiguration(locations = { "classpath*:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConnRecordServiceImplTest {
	@Autowired
	private ConnRecordService connRecordService;

	@Test
	public void testSaveConnRecord() {
		ConnRecord connRecord = new ConnRecord();
		connRecord.setCallTypeId(183L);
		connRecord.setUserId(1L);
		connRecord.setOperatorUserId("lv1236");
		connRecord.setFeedbackTime(new Date());
		connRecord.setMemo("conn record insert test");
		connRecord.setCreateDate(new Date());
		connRecord.setMobile("13044102235");
		connRecord.setPlaceName("test上海欢乐");
		connRecord.setCallBack("true");
		connRecord.setChannelId(2L);
		connRecord.setVisitTime(new Date());
		connRecord.setToPlaceName("四川");
		connRecord.setFromPlaceName("上海");
		connRecord.setDay(1L);
		connRecord.setQuantity(3L);
		connRecord.setProductId(2266L);
		connRecord.setProductZone("test");
		connRecord.setBusinessType("test business type");
		connRecord.setServiceType("serviceType");
		connRecord.setSubServiceType("subServiceType");
		
		Long connRecordId = connRecordService.saveConnRecord(connRecord);
		System.out.println("connRecordId:"+connRecordId);
		Assert.assertTrue((connRecordId>0?true:false));
	}

	@Test
	public void testQueryConnRecord() {
		String mobile = "13044102235";
		Page<ConnRecord> connRecordList = connRecordService.queryConnRecordPage(mobile, 10L, 1L);
		System.out.println("connRecordList:"+connRecordList.getItems().size());
		Assert.assertTrue((connRecordList.getItems().size()>0?true:false));
	}
}
