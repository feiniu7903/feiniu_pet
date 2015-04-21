package com.lvmama.tnt.user.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntCommissionRule;

/**
 * 返佣规则
 * 
 * @author gaoxin
 * @version 1.0
 */
@Repository
public interface TntCommissionRuleMapper {

	public int insert(TntCommissionRule entity);

	public List<TntCommissionRule> findPage(Page<TntCommissionRule> page);

	public int count(TntCommissionRule entity);

	public int update(TntCommissionRule tntCommissionRule);

	public int delete(Long commissionRuleId);

	public TntCommissionRule getById(Long commissionRuleId);

}
