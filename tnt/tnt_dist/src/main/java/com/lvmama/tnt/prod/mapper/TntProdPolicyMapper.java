package com.lvmama.tnt.prod.mapper;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.prod.po.TntProdPolicy;

/**
 * B2B分销产品策略表
 * 
 * @author gaoxin
 * @version 1.0
 */
public interface TntProdPolicyMapper {

	public int insert(TntProdPolicy entity);

	public List<TntProdPolicy> findPage(Page<TntProdPolicy> page);

	public int count(TntProdPolicy entity);

	public int updateById(TntProdPolicy tntProdPolicy);

	public int delete(Long tntProdPolicyId);

	public TntProdPolicy getById(Long tntProdPolicyId);

	/**
	 * 根据target 查询
	 * 
	 * @param prodPolicy
	 * @return
	 */
	public TntProdPolicy queryByTarget(TntProdPolicy prodPolicy);

	public TntProdPolicy queryByDist(TntProdPolicy prodPolicy);

	public List<TntProdPolicy> queryPolicy(Page<TntProdPolicy> page);

	public List<TntProdPolicy> queryDistPolicy(Page<TntProdPolicy> page);

	public int queryPolicyCount(TntProdPolicy t);

	public int queryDistPolicyCount(TntProdPolicy t);

	public TntProdPolicy getPolicy(TntProdPolicy t);

	public Long getPriceByRule(String rule);

}
