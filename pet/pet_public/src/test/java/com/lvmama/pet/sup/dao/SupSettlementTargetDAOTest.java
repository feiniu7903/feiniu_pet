package com.lvmama.pet.sup.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.vo.Constant;

@ContextConfiguration(locations = { "classpath*:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SupSettlementTargetDAOTest {
	@Autowired
	private SupSettlementTargetDAO supSettlementTargetDAO;
	
	@Test
	public void testGetMetaSettlementByMetaProductId() {
		List<SupSettlementTarget> s = supSettlementTargetDAO.getMetaSettlementByMetaProductId(111l,Constant.PRODUCT_BIZ_TYPE.BEE.name());
		System.out.println("test:"+s.size());
	}

}
