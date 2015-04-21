package com.lvmama.tnt.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.mapper.TntCompanyTypeMapper;
import com.lvmama.tnt.user.po.TntCompanyType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TntCommanyTypeDAOTest {

	@Autowired
	private TntCompanyTypeMapper tntCompanyTypeMapper;

	@Test
	public void pageQuery() {
		TntCompanyType t = new TntCompanyType();
		// 参数1查询类型，参数2 pageSize
		Page<TntCompanyType> page = Page.page(1, t);

		// 排序
		page.desc("company_type_id");
		List<TntCompanyType>

		// 查询
		list = tntCompanyTypeMapper.fetchPage(page);

		for (TntCompanyType a : list) {
			System.err.println(a.getCompanyTypeId());
		}

	}

	// @Test
	public void count() {
		TntCompanyType t = new TntCompanyType();
		// t.setChannelId(1l);
		// t.setCompanyTypeId(1l);
		// t.setCompanyTypeName("companyTypeName");

		long num = tntCompanyTypeMapper.findCount(t);
		System.err.println(num);

	}

}