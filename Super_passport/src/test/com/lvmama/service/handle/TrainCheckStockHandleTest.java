package com.lvmama.service.handle;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.SupplierProductInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-passport-beans.xml" })
public class TrainCheckStockHandleTest {

	@Autowired
	private TrainCheckStockHandle trainCheckStockHandle;
	
	@Test
	public void testCheck() {
		List<SupplierProductInfo.Item> list = new ArrayList<SupplierProductInfo.Item>();
		SupplierProductInfo.Item item = new SupplierProductInfo.Item(736638L, DateUtil.toDate("2013-09-22", "yyyy-MM-dd"));
		list.add(item);
		trainCheckStockHandle.check(null, list);
	}

}
