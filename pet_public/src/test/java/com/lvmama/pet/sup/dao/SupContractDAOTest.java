package com.lvmama.pet.sup.dao;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.vo.Page;
@ContextConfiguration(locations = { "classpath*:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SupContractDAOTest {
	@Autowired
	private SupContractDAO supContractDAO;
	@Test
	public void testGetSupContractByParam() {
		
		Page<SupContract>  page = supContractDAO.getSupContractByParam(new HashMap<String, Object>(), 10L, 1L);
		System.out.println(page.getItems().size());
	}

}
