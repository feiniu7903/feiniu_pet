package com.lvmama.tnt.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.helpcenter.mapper.TntFAQMapper;
import com.lvmama.tnt.user.po.TntFAQ;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TntFAQMapperTest {

	@Autowired
	private TntFAQMapper tntFAQMapper;

	@Test
	public void pageQuery() {
		TntFAQ t = new TntFAQ();

		// 参数1查询类型，参数2 pageSize
		Page<TntFAQ> page = Page.page(1, t);

		// 排序
		page.asc("typeName");
		page.asc("companyTypeId");
		List<TntFAQ>

		// 查询
		list = tntFAQMapper.fetchPage(page);

		System.err.println(list);

	}

	@Test
	public void count() {
		TntFAQ t = new TntFAQ();
		// t.setChannelId(1l);
		// t.setCompanyTypeId(1l);
		// t.setCompanyTypeName("companyTypeName");

		long num = tntFAQMapper.findCount(t);
		System.err.println(num);

	}

}