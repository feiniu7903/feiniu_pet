/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.money.CashChange;
import com.lvmama.pet.BaseTest;
import com.lvmama.pet.money.dao.CashChangeDAO;
/**
 * CashChange 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashChangeServiceTest extends BaseTest{
	@Autowired
	private CashChangeDAO cashChangeDAO;
	@Test
	public void insert(){
		CashChange cashChange=new CashChange();
		Long id=cashChangeDAO.insert(cashChange);
		Assert.assertNotNull(id);
	}
}
