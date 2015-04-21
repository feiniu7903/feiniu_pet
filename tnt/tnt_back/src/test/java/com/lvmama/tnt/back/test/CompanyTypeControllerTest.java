package com.lvmama.tnt.back.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntCompanyType;
import com.lvmama.tnt.user.service.TntCompanyTypeService;
import com.lvmama.tnt.user.service.TntCompanyTypeUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class CompanyTypeControllerTest {

	@Autowired
	private TntCompanyTypeService tntCompanyTypeService;

	@Autowired
	private TntCompanyTypeUserService tntCompanyTypeUserService;

	// @Test
	public void add() {
		TntCompanyType t = new TntCompanyType();
		t.setCompanyTypeName("111");
		tntCompanyTypeService.insert(t);
	}

	// @Test
	public void queryAndUPdateAndDelete() {
		TntCompanyType t = new TntCompanyType();
		Page<TntCompanyType> p = Page.page(1, t);
		List<TntCompanyType> list = tntCompanyTypeUserService
				.queryWithUserTotal(p);
		String code = "aaa";
		if (list != null) {
			for (TntCompanyType c : list) {
				long id = c.getCompanyTypeId();
				System.err.println(c);
				c.setCompanyTypeCode(code);
				tntCompanyTypeService.update(c);
				c = tntCompanyTypeService.get(id);
				assertEquals(code, c.getCompanyTypeCode());
				tntCompanyTypeService.delete(id);
			}
		}
		list = tntCompanyTypeUserService.queryWithUserTotal(p);
		assertTrue(list == null || list.isEmpty());
	}

	@Test
	public void test() {
		tntCompanyTypeUserService.isContainUser(11l);
	}
}
