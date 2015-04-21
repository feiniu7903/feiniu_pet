/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.money.CashRefundment;
import com.lvmama.pet.BaseTest;
import com.lvmama.pet.money.dao.CashRefundmentDAO;
/**
 * CashRefundment 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashRefundmentServiceTest extends BaseTest{
	@Autowired
	private CashRefundmentDAO cashRefundmentDAO;
	@Test
	public void insert(){
		CashRefundment cashRefundment=new CashRefundment();
		cashRefundment.setAmount(10000L);
		cashRefundment.setCashAccountId(9L);
		cashRefundment.setCreateTime(new Date());
		cashRefundment.setSerial("10000");
		cashRefundment.setRefundmentType("REFUND");
		Long id=cashRefundmentDAO.insert(cashRefundment);
		Assert.assertNotNull(id);
	}
}
