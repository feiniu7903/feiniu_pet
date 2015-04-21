package com.lvmama.tnt.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.user.mapper.TntUserMaterialMapper;
import com.lvmama.tnt.user.po.TntUserMaterial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TntUserMaterialMapperTest {
	@Autowired
	private TntUserMaterialMapper tntUserMaterialMapper;

	// @Test
	public void query() {
		TntUserMaterial t = new TntUserMaterial();
		tntUserMaterialMapper.getById(1l);
	}

	@Test
	public void testUpdateStatus() {
		TntUserMaterial t = new TntUserMaterial();
		t.setMaterialStatus("1");
		t.setUserId(1l);
		tntUserMaterialMapper.updateStatus(t);
	}
}
