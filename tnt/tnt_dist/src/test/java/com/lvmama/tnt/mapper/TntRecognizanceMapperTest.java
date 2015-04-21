package com.lvmama.tnt.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.recognizance.mapper.TntRecognizanceChangeMapper;
import com.lvmama.tnt.recognizance.mapper.TntRecognizanceMapper;
import com.lvmama.tnt.recognizance.po.TntRecognizance;
import com.lvmama.tnt.recognizance.po.TntRecognizanceChange;
import com.lvmama.tnt.recognizance.service.TntRecognizanceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TntRecognizanceMapperTest {

	@Autowired
	private TntRecognizanceMapper tntRecognizanceMapper;

	@Autowired
	private TntRecognizanceChangeMapper tntRecognizanceChangeMapper;

	@Autowired
	private TntRecognizanceService tntRecognizanceService;

	@Test
	public void query() {
		TntRecognizance t = new TntRecognizance();
		t.setUserName("aa");
		Page<TntRecognizance> p = Page.page(1, t);
		List<TntRecognizance> list = tntRecognizanceMapper.findPage(p);
		System.err.println(list);
	}

	// @Test
	public void queryDetail() {
		TntRecognizanceChange t = new TntRecognizanceChange();
		t.setRecognizanceId(1l);
		Page<TntRecognizanceChange> p = Page.page(1, t);
		List<TntRecognizanceChange> list = tntRecognizanceChangeMapper
				.findPage(p);
		System.err.println(list);
	}

	// @Test
	public void test() {
		TntRecognizance t = new TntRecognizance();
		t.setUserName("111");
		tntRecognizanceMapper.count(t);
	}
}
