package com.lvmama.tnt.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.mapper.TntContractAttachMapper;
import com.lvmama.tnt.user.mapper.TntContractMapper;
import com.lvmama.tnt.user.po.TntContract;
import com.lvmama.tnt.user.po.TntContractAttach;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-tnt-dist-database.xml" })
public class TntContractMapperTest {

	@Autowired
	private TntContractMapper tntContractMapper;

	@Autowired
	private TntContractAttachMapper tntContractAttachMapper;

	// @Test
	public void query() {
		TntContract t = new TntContract();
		List<TntContract> list = tntContractMapper.selectList(t);
		System.err.println(list);
	}

	// @Test
	public void queryAttach() {
		TntContractAttach t = new TntContractAttach();
		tntContractAttachMapper.selectByPrimaryKey(1l);
		// List<TntContractAttach> list = tntContractAttachMapper.selectList(t);
		// System.err.println(list);
	}

	// @Test
	public void testDelete() {
		tntContractMapper.deleteById(1l);
	}

	@Test
	public void pageQuery() {
		TntContract t = new TntContract();
		t.setContractId(1l);
		t.setUserId(1l);
		Page<TntContract> page = Page.page(1, t);
		tntContractMapper.fetchPageWithAttach(page);
		tntContractMapper.count(t);
		tntContractMapper.updateStatus(t);
	}
}