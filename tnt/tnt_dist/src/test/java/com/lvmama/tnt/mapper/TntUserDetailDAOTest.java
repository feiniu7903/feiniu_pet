package com.lvmama.tnt.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.user.mapper.TntUserDetailMapper;
import com.lvmama.tnt.user.po.TntUserDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-tnt-dist-database.xml" })
public class TntUserDetailDAOTest {

	@Autowired
	private TntUserDetailMapper tntUserDetailMapper;

	// @Test
	public void query() {
		TntUserDetail t = new TntUserDetail();
		List<TntUserDetail> list = tntUserDetailMapper
				.selectCompanyTypeTotalList(t);
		if (list != null) {
			for (TntUserDetail d : list) {
				System.err.println(d.getCompanyTypeId() + ":" + d.getTotal());
			}
		}
	}

	@Test
	public void test() {
		TntUserDetail t = new TntUserDetail();
		t.setCompanyTypeId(11l);
		List<Long> list = tntUserDetailMapper.containTypeUsers(t);
		System.err.println(list);
	}
}