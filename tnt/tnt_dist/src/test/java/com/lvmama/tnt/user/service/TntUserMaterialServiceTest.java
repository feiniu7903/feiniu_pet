package com.lvmama.tnt.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.user.po.TntUserMaterial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TntUserMaterialServiceTest {

	@Autowired
	TntUserMaterialService tntUserMaterialService;
	
	@Test
	public void testInsert(){
		TntUserMaterial mate = new TntUserMaterial();
		mate.setMaterialName("ewqre");
		mate.setMaterialType(TntConstant.USER_MATERIAL_TYPE.COMPANY_BANK.name());
		mate.setUserId(1L);
		tntUserMaterialService.insert(mate);
	}
	
	@Test
	public void testQuery(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("_start", 2);
		map.put("_end", 3);
		List<TntUserMaterial> t = tntUserMaterialService.query(map);
		System.out.println("sfad");
	}
}
