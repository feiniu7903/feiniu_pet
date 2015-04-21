package com.lvmama.jinjiang;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.jinjiang.service.JinjiangProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-passport-beans.xml" })
public class jinjiangTest {
	Log log = LogFactory.getLog(jinjiangTest.class);
	@Autowired
	JinjiangClient jinjiangClient;
	@Autowired
	JinjiangProductService jinjiangProductService;

	/**
	 * 入库指定产品
	 */
	@Test
	public void testSaveProductUnStocked() {
		String lineCode = "25789E38-5982-43B9-9D91-5783C39868F8";
		try {
			jinjiangProductService.saveProductUnStocked(lineCode);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把对接的产品保存到临时表中
	 */
	@Test
	public void testSaveTempStockProduct() {
		try {
			jinjiangProductService.saveTempStockProduct();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新产品时间价格
	 */
	@Test
	public void testUpdateProductTimePrice() {
		Date updateTimeEnd = new Date();
		Date updateTimeStart = DateUtils.addYears(updateTimeEnd, -1);
		String lineCode = "test-0011_1";
		try {
			jinjiangProductService.updateProductTimePrice(lineCode, updateTimeStart, updateTimeEnd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新全部的产品时间价格
	 */
	@Test
	public void testUpdateAllProductTimePrices() {
		Date updateTimeEnd = new Date();
		Date updateTimeStart = DateUtils.addYears(updateTimeEnd, -1);
		try {
			jinjiangProductService.updateAllProductTimePrices(updateTimeStart,
					updateTimeEnd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
