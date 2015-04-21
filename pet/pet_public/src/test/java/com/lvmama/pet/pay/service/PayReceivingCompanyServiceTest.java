package com.lvmama.pet.pay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.pay.PayReceivingCompany;
import com.lvmama.comm.pet.service.pay.PayReceivingCompanyService;
import com.lvmama.comm.utils.StringUtil;

@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PayReceivingCompanyServiceTest {
	
	@Autowired
	PayReceivingCompanyService payReceivingCompanyService;
	

	@Test
	public void selectReceivingCompanyByPK() {
		Long receivingCompanyId=2L;
		PayReceivingCompany payReceivingCompany=payReceivingCompanyService.selectReceivingCompanyByPK(receivingCompanyId);
		System.out.println(StringUtil.printParam(payReceivingCompany));
	}

	@Test
	public void selectPayReceivingCompanyByParamMap() {
		Map<String,String> map=new HashMap<String,String>();
		map.put("status", "ENABLE");
		map.put("orderby", "CREATE_TIME");
		map.put("order", "ASC");
		List<PayReceivingCompany> payReceivingCompanyList=payReceivingCompanyService.selectPayReceivingCompanyByParamMap(map);
		for (PayReceivingCompany payReceivingCompany : payReceivingCompanyList) {
			System.out.println(StringUtil.printParam(payReceivingCompany));
		}
	}
}
