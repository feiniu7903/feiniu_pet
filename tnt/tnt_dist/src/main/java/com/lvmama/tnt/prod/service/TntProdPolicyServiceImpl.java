package com.lvmama.tnt.prod.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.prod.mapper.TntProdPolicyMapper;
import com.lvmama.tnt.prod.mapper.TntProductMapper;
import com.lvmama.tnt.prod.po.TntProdPolicy;
import com.lvmama.tnt.prod.po.TntProduct;
import com.lvmama.tnt.user.mapper.TntUserMapper;
import com.lvmama.tnt.user.po.TntUser;

@Repository("tntProdPolicyService")
public class TntProdPolicyServiceImpl implements TntProdPolicyService {

	@Autowired
	private TntProdPolicyMapper tntProdPolicyMapper;

	@Autowired
	private TntUserMapper tntUserMapper;

	@Autowired
	private TntProductMapper tntProductMapper;

	@Override
	public TntProdPolicy getByTarget(TntProdPolicy policy) {
		TntProdPolicy prodPolicy = tntProdPolicyMapper.queryByTarget(policy);
		return prodPolicy;
	}

	@Override
	public TntProdPolicy getByDist(TntProdPolicy policy) {
		TntProdPolicy prodPolicy = tntProdPolicyMapper.queryByDist(policy);
		return prodPolicy;
	}

	@Override
	public List<TntProdPolicy> listPolicyByBranchId(Long branchId,
			Map<Long, String> channelMap, Long productId) {
		List<TntProdPolicy> list = null;
		if (branchId != null && channelMap != null && !channelMap.isEmpty()) {
			list = new ArrayList<TntProdPolicy>();
			TntProdPolicy t = new TntProdPolicy();
			t.setProductType(TntConstant.PRODUCT_TYPE.TICKET.name());
			for (Long channelId : channelMap.keySet()) {
				TntProdPolicy tpp = getPolicyByBranchId(branchId, channelId,
						productId);
				if (tpp != null) {
					list.add(tpp);
				}
			}
		}
		return list;
	}

	@Override
	public TntProdPolicy getById(Long tntProdPolicyId) {
		return tntProdPolicyMapper.getById(tntProdPolicyId);
	}

	@Override
	public boolean updateById(TntProdPolicy tntProdPolicy) {
		return tntProdPolicyMapper.updateById(tntProdPolicy) > 0;
	}

	@Override
	public ResultGod<TntProdPolicy> saveOrUpdate(TntProdPolicy tntProdPolicy) {
		ResultGod<TntProdPolicy> result = new ResultGod<TntProdPolicy>();
		result.setSuccess(true);
		if (tntProdPolicy != null) {
			if (tntProdPolicy.getTntPolicyId() != null) {
				tntProdPolicyMapper.updateById(tntProdPolicy);
			} else {
				tntProdPolicyMapper.insert(tntProdPolicy);
			}
			result.setResult(tntProdPolicy);
		} else {
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	public TntProdPolicy getPolicyByBranchId(Long branchId, Long channelId,
			Long productId) {
		TntProdPolicy tpp = null;
		if (branchId != null) {
			TntProdPolicy t = new TntProdPolicy();
			t.setBranchId(branchId);
			t.setProductId(productId);
			t.setProductType(TntConstant.PRODUCT_TYPE.TICKET.name());
			t.setTargetType(TntConstant.PROD_TARGET_TYPE.PROD_CHANNEL.name());
			t.setTargetId(channelId);
			tpp = tntProdPolicyMapper.queryByTarget(t);
			if (tpp == null) {
				t.setBranchId(null);
				t.setProductId(null);
				t.setTargetType(TntConstant.PROD_TARGET_TYPE.CHANNEL.name());
				tpp = tntProdPolicyMapper.queryByTarget(t);
				if (tpp != null) {
					tpp.setTntPolicyId(null);
					tpp.setBranchId(branchId);
					tpp.setProductId(productId);
					tpp.setTargetType(TntConstant.PROD_TARGET_TYPE.PROD_CHANNEL
							.name());
				}
			}
			if (tpp != null)
				tpp.setChannelId(tpp.getTargetId());
		}
		return tpp;
	}

	@Override
	public List<TntProdPolicy> queryPolicy(Page<TntProdPolicy> page) {
		if (page != null) {
			return tntProdPolicyMapper.queryPolicy(page);
		}
		return null;
	}

	@Override
	public int queryPolicyCount(TntProdPolicy t) {
		if (t != null) {
			return tntProdPolicyMapper.queryPolicyCount(t);
		}
		return 0;
	}

	@Override
	public TntProdPolicy getPolicyByUserBranchId(Long branchId, Long userId,
			Long channelId, Long productId) {
		TntProdPolicy t = null;
		if (branchId != null && userId != null) {
			t = new TntProdPolicy();
			t.setBranchId(branchId);
			t.setProductId(productId);
			t.setChannelId(channelId);
			TntUser user = new TntUser();
			user.setUserId(userId);
			t.setUser(user);
			t = tntProdPolicyMapper.getPolicy(t);
			if (t == null) {
				t = new TntProdPolicy();
			}
			if (userId != null) {
				TntUser exist = this.tntUserMapper.selectOneWithDetail(user);
				if (exist != null) {
					t.setUser(exist);
				}
			}
			if (t.getProductId() == null)
				t.setProductId(productId);
			if (t.getBranchId() == null)
				t.setBranchId(branchId);
			if (userId != null) {
				t.setTargetId(userId);
				t.setTargetType(TntConstant.PROD_TARGET_TYPE.PROD_DISTRIBUTOR
						.name());
			}
		}
		return t;
	}

	@Override
	public List<TntProdPolicy> queryDistPolicy(Page<TntProdPolicy> page) {
		if (page != null) {
			return tntProdPolicyMapper.queryDistPolicy(page);
		}
		return null;
	}

	@Override
	public int queryDistPolicyCount(TntProdPolicy t) {
		if (t != null) {
			return tntProdPolicyMapper.queryDistPolicyCount(t);
		}
		return 0;
	}

	@Override
	public boolean insert(TntProdPolicy tntProdPolicy) {
		boolean success = true;
		if (tntProdPolicy != null) {
			success = tntProdPolicyMapper.insert(tntProdPolicy) > 0;
		} else {
			success = false;
		}
		return success;
	}

	@Override
	public Long calculatePrice(Long branchId, Long userId, Long sellprice,
			Long settlePrice) {
		boolean payToLvmama = false;
		List<TntProduct> products = tntProductMapper.getByBranchId(branchId);
		if (products != null && !products.isEmpty()) {
			TntProduct product = products.get(0);
			payToLvmama = product != null && product.getIsPayToLvmama();
		}
		return calculatePrice(branchId, userId, sellprice, settlePrice,
				payToLvmama);
	}

	@Override
	public Long calculatePrice(Long branchId, Long userId, Long sellPrice,
			Long settlePrice, boolean isPayToLvmama) {
		if (isPayToLvmama) {
			try {
				String rule = getCalculateRule(branchId, userId);
				if (rule != null) {
					Long price = getPriceByRule(rule, sellPrice, settlePrice);
					return price;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sellPrice;
	}

	@Override
	public String getCalculateRule(Long branchId, Long userId) {
		String rule = null;
		TntProdPolicy t = this.getPolicyByUserBranchId(branchId, userId, null,
				null);
		if (t != null) {
			if (t.getDiscount() == null) {
				t.setDiscount(0l);
			}
			rule = t.getCalPriceRule();
		}
		return rule;
	}

	@Override
	public Long getPriceByRule(String rule) {
		Long price = TntUtil.eval(rule);
		if (price == null) {
			price = tntProdPolicyMapper.getPriceByRule(rule);
		}
		return price;
	}

	@Override
	public Long getPriceByRule(String rule, Long sellPrice, Long settlePrice) {
		if (rule != null && sellPrice != null && settlePrice != null) {
			if (settlePrice > sellPrice) {
				settlePrice = sellPrice;
			}
			rule = rule.replaceAll("销售价", sellPrice + "");
			rule = rule.replaceAll("结算价", settlePrice + "");
			return getPriceByRule(rule);
		}
		return sellPrice;
	}

}
