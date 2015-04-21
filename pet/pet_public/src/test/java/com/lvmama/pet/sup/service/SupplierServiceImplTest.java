package com.lvmama.pet.sup.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.pet.sup.dao.SupSupplierDAO;

@ContextConfiguration(locations = { "classpath*:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SupplierServiceImplTest {

	@Autowired
	private SupSupplierDAO supSupplierDAO;
	
	@Test
	public void testGetSupSuppliers() {
		Map<String,String> map=new HashMap<String, String>();
		map.put("_startRow", "0");
		map.put("_endRow","20q ");
		List<SupSupplier> list = supSupplierDAO.getSupSuppliers(map);
		Assert.assertNotNull(list);
		System.out.println(list);
	}

}
