package com.lvmama.tnt.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.order.mapper.TntOrderMapper;
import com.lvmama.tnt.order.po.TntOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TntAccountMapperTest {

	@Autowired
	private TntOrderMapper tntOrderMapper;

	@Test
	public void query() {
		TntOrder t = new TntOrder();
		t.setOrderId(1l);
		System.err.println(tntOrderMapper.selectOne(t));
	}
}
