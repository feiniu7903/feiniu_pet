/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.money;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.vo.CashAccountVO;

/**
 * CashAccount 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */
//@ContextConfiguration(locations = { "classpath*:/applicationContext-pet-public-beans.xml" })
//@RunWith(SpringJUnit4ClassRunner.class)
public class CashAccountServiceTest{
	@Autowired
	private CashAccountService cashAccountService;
	
	@Before
	public void doBefore(){
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext-pet-public-beans.xml");
		cashAccountService = context.getBean(CashAccountService.class);
	}
	
//	@Test
	public void insert(){
		CashAccount cashAccount=new CashAccount();
		Long id=cashAccountService.insert(cashAccount);
		Assert.assertNotNull(id);
	}
	//@Test
	public void queryMoneyAccount(){
		CashAccountVO cashAccountVO=cashAccountService.queryMoneyAccountByUserNo("40288a8d2256a4480122582e880813dc");
		Assert.assertNotNull(cashAccountVO);
	}
	
	@Test
	public void testQueryMoneyDrawCount(){
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyDrawRelate().setUserNo("cc");
		long count=cashAccountService.queryMoneyDrawCount(compositeQuery);
		Assert.assertEquals(0L, count);
	}
}
