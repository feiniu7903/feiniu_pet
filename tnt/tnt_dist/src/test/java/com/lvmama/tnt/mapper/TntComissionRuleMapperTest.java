package com.lvmama.tnt.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.mapper.TntCommissionRuleMapper;
import com.lvmama.tnt.user.po.TntCommissionRule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TntComissionRuleMapperTest {

	@Autowired
	private TntCommissionRuleMapper tntCommissionRuleMapper;

	@Test
	public void query() {
		TntCommissionRule t = new TntCommissionRule();
		Page<TntCommissionRule> p = Page.page(1, t);
		List<TntCommissionRule> list = tntCommissionRuleMapper.findPage(p);
		if (list != null) {
			for (TntCommissionRule a : list) {
				tntCommissionRuleMapper.update(a);
				tntCommissionRuleMapper.delete(a.getCommissionRuleId());
			}
		}
	}

	// @Test
	public void insert() {
		TntCommissionRule t = new TntCommissionRule();
		t.setDiscountRate(1);
		t.setMaxSales(1l);
		t.setMinSales(1l);
		t.setPayOnline("1");
		t.setProductType("1");
		t.setSubProductType("1");
		tntCommissionRuleMapper.insert(t);

	}

}