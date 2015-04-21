package com.lvmama.tnt.user.service;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntCommissionRule;

public interface TntCommissionRuleService {

	public List<TntCommissionRule> pageQuery(Page<TntCommissionRule> page);

	public int count(TntCommissionRule tntCommissionRule);

	public boolean insert(TntCommissionRule tntCommissionRule);

	public boolean update(TntCommissionRule tntCommissionRule);

	public boolean deleteById(Long commissionRuleId);

	public TntCommissionRule getById(Long commissionRuleId);
}
