/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.money.CashFreezeQueue;
import com.lvmama.pet.BaseTest;
import com.lvmama.pet.money.dao.CashFreezeQueueDAO;
/**
 * CashFreezeQueue 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashFreezeQueueServiceTest extends BaseTest{
	@Autowired
	private CashFreezeQueueDAO cashFreezeQueueDAO;
	@Test
	public void insert(){
		CashFreezeQueue cashFreezeQueue=new CashFreezeQueue();
		Long id=cashFreezeQueueDAO.insert(cashFreezeQueue);
		Assert.assertNotNull(id);
	}
}
