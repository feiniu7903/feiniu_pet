/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.pet.BaseTest;
import com.lvmama.pet.money.dao.CashMoneyDrawDAO;
/**
 * CashMoneyDraw 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashMoneyDrawServiceTest extends BaseTest{
	@Autowired
	private CashMoneyDrawDAO cashMoneyDrawDAO;
	@Test
	public void insert(){
		CashMoneyDraw cashMoneyDraw=new CashMoneyDraw();
		Long id=cashMoneyDrawDAO.insert(cashMoneyDraw);
		Assert.assertNotNull(id);
	}
}
