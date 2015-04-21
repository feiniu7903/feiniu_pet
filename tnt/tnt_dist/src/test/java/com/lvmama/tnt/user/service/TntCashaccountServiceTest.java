package com.lvmama.tnt.user.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.cashaccount.po.TntCashRecharge;
import com.lvmama.tnt.cashaccount.service.TntCashaccountService;
import com.lvmama.tnt.comm.vo.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TntCashaccountServiceTest {
	
	@Autowired
	TntCashaccountService tntCashaccountService;
	
	@Test
	public void query_test(){
//		TntCashAccount t = new TntCashAccount();
//		t.setUserId(381L);
//		Page<TntCashAccount> p = Page.page(1, t);
//		p = p.desc("CASH_ACCOUNT_ID");
//		Long count = tntCashaccountService.count(t);
//		List<TntCashAccount> list = tntCashaccountService.pageQuery(p);
//		
		TntCashRecharge t = new TntCashRecharge();
		Page<TntCashRecharge> p = Page.page(1, t);
		p = p.desc("CASH_RECHARGE_ID");
		Long count = tntCashaccountService.countCashRecharge(t);
		List<TntCashRecharge> list = tntCashaccountService.pageQueryCashRecharge(p);
		
//		
//		TntCashCommission t = new TntCashCommission();
//		t.setCashAccountId(1L);
//		Page<TntCashCommission> p = Page.page(1, t);
//		Long count = tntCashaccountService.countCashCommission(t);
//		List<TntCashCommission> list = tntCashaccountService.pageQueryCashCommission(p);
		
//		TntCashRefundment t = new TntCashRefundment();
//		t.setCashAccountId(1L);
//		Page<TntCashRefundment> p = Page.page(1, t);
//		Long count = tntCashaccountService.countCashRefundment(t);
//		List<TntCashRefundment> list = tntCashaccountService.pageQueryCashRefundment(p);
		
//		TntCashMoneyDraw t = new TntCashMoneyDraw();
//		Page<TntCashMoneyDraw> p = Page.page(1, t);
//		p = p.desc("MONEY_DRAW_ID");
//		Long count = tntCashaccountService.countCashMoneyDraw(t);
//		List<TntCashMoneyDraw> list = tntCashaccountService.pageQueryCashMoneyDraw(p);
		
//		TntCashFreezeQueue t = new TntCashFreezeQueue();
//		Page<TntCashFreezeQueue> p = Page.page(1, t);
//		t.setStatus(TntConstant.FREEZE_STATUS.FREEZE.name());
//		t.getTntUser().setUserName("admin");
//		p = p.desc("FREEZE_QUEUE_ID");
//		Long count = tntCashaccountService.countCashFreeze(t);
//		List<TntCashFreezeQueue> list = tntCashaccountService.pageQueryCashFreeze(p);
		System.out.println(count);
		Assert.assertNotNull(list);
	}

	@Test
	public void addRecharge_test(){
		TntCashRecharge tntCashRecharge = new TntCashRecharge();
		tntCashRecharge.setAmount(2424L);
		tntCashRecharge.setBankAccount("");
		tntCashRecharge.setCashAccountId(441L);
		tntCashaccountService.addRecharge(tntCashRecharge);
//		
//		TntCashRefundment tntCashRefundment = new TntCashRefundment();
//		tntCashRefundment.setAmount(1323L);
//		tntCashRefundment.setCashAccountId(1L);
//		tntCashRefundment.setSerial("2342");
//		tntCashaccountService.addRefundment(tntCashRefundment);
		
//		TntCashCommission tntCashCommission = new TntCashCommission();
//		tntCashCommission.setCommisRate(12L);
//		tntCashCommission.setCashAccountId(1L);
//		tntCashaccountService.addCommission(tntCashCommission);
	}
}
