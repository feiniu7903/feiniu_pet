package com.lvmama.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.prd.dao.ProdProductDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ComPlaceDAOTest {

	@Autowired
	ProdProductBranchService prodProductBranchService;

	@Autowired
	ProdProductDAO prodProductDao;

	@Test
	public void test() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productType", "TICKET");
		// System.err.println(comPlaceDAO.selectB2BProdCount(map));
		map.put("startRows", 1);
		map.put("endRows", 10);
		List<ProdProductBranch> list = prodProductBranchService.selectB2BProd(map);
		for (ProdProductBranch p : list) {
			System.err.println(p + "PPPPPPPPPP");
			System.err.println(p.getProdProduct());
		}
	}

	

}
