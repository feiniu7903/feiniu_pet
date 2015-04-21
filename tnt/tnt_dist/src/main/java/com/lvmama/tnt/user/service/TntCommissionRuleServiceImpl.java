package com.lvmama.tnt.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.mapper.TntCommissionRuleMapper;
import com.lvmama.tnt.user.po.TntCommissionRule;

@Repository("tntCommissionRuleService")
public class TntCommissionRuleServiceImpl implements TntCommissionRuleService {

	@Autowired
	private TntCommissionRuleMapper tntCommissionRuleMapper;

	@Override
	public List<TntCommissionRule> pageQuery(Page<TntCommissionRule> page) {
		if (page != null && page.getParam() != null)
			return tntCommissionRuleMapper.findPage(page);
		return null;
	}

	@Override
	public int count(TntCommissionRule tntCommissionRule) {
		if (tntCommissionRule != null)
			return tntCommissionRuleMapper.count(tntCommissionRule);
		return 0;
	}

	@Override
	public boolean insert(TntCommissionRule tntCommissionRule) {
		if (tntCommissionRule != null) {
			dealSales(tntCommissionRule);
			return tntCommissionRuleMapper.insert(tntCommissionRule) > 0;
		}
		return false;
	}

	private void dealSales(TntCommissionRule tntCommissionRule) {

	}

	@Override
	public boolean update(TntCommissionRule tntCommissionRule) {
		if (tntCommissionRule != null) {
			dealSales(tntCommissionRule);
			return tntCommissionRuleMapper.update(tntCommissionRule) > 0;
		}
		return false;
	}

	@Override
	public boolean deleteById(Long commissionRuleId) {
		if (commissionRuleId != null)
			return tntCommissionRuleMapper.delete(commissionRuleId) > 0;
		return false;
	}

	@Override
	public TntCommissionRule getById(Long commissionRuleId) {
		if (commissionRuleId != null)
			return tntCommissionRuleMapper.getById(commissionRuleId);
		return null;
	}

}
