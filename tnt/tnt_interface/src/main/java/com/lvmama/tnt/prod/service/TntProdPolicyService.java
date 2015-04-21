package com.lvmama.tnt.prod.service;

import java.util.List;
import java.util.Map;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.prod.po.TntProdPolicy;

/**
 * 减价策略
 * 
 * @author gaoxin
 * 
 */
public interface TntProdPolicyService {
	/**
	 * 根据target 查询策略 targetType targetId productId branchId
	 * 
	 * @param policy
	 * @return
	 */
	public TntProdPolicy getByTarget(TntProdPolicy policy);

	public TntProdPolicy getByDist(TntProdPolicy policy);

	public List<TntProdPolicy> listPolicyByBranchId(Long branchId,
			Map<Long, String> channelMap, Long productId);

	public List<TntProdPolicy> queryPolicy(Page<TntProdPolicy> page);

	public List<TntProdPolicy> queryDistPolicy(Page<TntProdPolicy> page);

	public int queryPolicyCount(TntProdPolicy t);

	public int queryDistPolicyCount(TntProdPolicy t);

	public TntProdPolicy getPolicyByBranchId(Long branchId, Long channelId,
			Long productId);

	public TntProdPolicy getPolicyByUserBranchId(Long branchId, Long userId,
			Long channelId, Long productId);

	/**
	 * 根据销售价和结算价计算分销价格
	 * 
	 * @param branchId
	 * @param userId
	 * @param sellPrice
	 * @param settlePrice
	 * @return
	 */
	public Long calculatePrice(Long branchId, Long userId, Long sellPrice,
			Long settlePrice);

	public Long calculatePrice(Long branchId, Long userId, Long sellPrice,
			Long settlePrice, boolean isPayToLvmama);

	public String getCalculateRule(Long branchId, Long userId);

	public TntProdPolicy getById(Long tntProdPolicyId);

	public boolean updateById(TntProdPolicy tntProdPolicy);

	public ResultGod<TntProdPolicy> saveOrUpdate(TntProdPolicy tntProdPolicy);

	public boolean insert(TntProdPolicy tntProdPolicy);

	public Long getPriceByRule(String rule);

	public Long getPriceByRule(String rule, Long sellPrice, Long settlePrice);

}
