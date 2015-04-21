package com.lvmama.pet.pay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.pay.PayReceivingBank;
import com.lvmama.comm.pet.service.pay.PayReceivingBankService;
import com.lvmama.comm.utils.StringUtil;

@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PayReceivingBankServiceTest {
	
	@Autowired
	PayReceivingBankService payReceivingBankService;
	

	@Test
	public void selectReceivingBankByPK() {
		Long receivingBankId=2L;
		PayReceivingBank payReceivingBank=payReceivingBankService.selectReceivingBankByPK(receivingBankId);
		System.out.println(StringUtil.printParam(payReceivingBank));
	}

	@Test
	public void selectPayReceivingBankByParamMap() {
		Map<String,String> map=new HashMap<String,String>();
		map.put("status", "ENABLE");
		map.put("orderby", "CREATE_TIME");
		map.put("order", "ASC");
		List<PayReceivingBank> payReceivingBankList=payReceivingBankService.selectPayReceivingBankByParamMap(map);
		for (PayReceivingBank payReceivingBank : payReceivingBankList) {
			System.out.println(StringUtil.printParam(payReceivingBank));
		}
	}
}
