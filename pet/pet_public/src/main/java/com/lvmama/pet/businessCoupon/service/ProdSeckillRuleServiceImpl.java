package com.lvmama.pet.businessCoupon.service;


import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.pet.businessCoupon.dao.ProdSeckillRuleDAO;

/**
 * 
 * @author zenglei
 *
 */
public class ProdSeckillRuleServiceImpl implements ProdSeckillRuleService{
	private ProdSeckillRuleDAO prodSeckillRuleDAO;
	@Override
	public ProdSeckillRule insertProdSeckillRule(ProdSeckillRule entity) {
		 return prodSeckillRuleDAO.insertProdSeckillRule(entity);
	}
	@Override
	public List<ProdSeckillRule> selectByParam(Map<String, Object> param) {
		return prodSeckillRuleDAO.selectByParam(param);
	}
	
	@Override
	public List<ProdSeckillRule> querySeckillRule() {
		return prodSeckillRuleDAO.querySeckillRule();
	}
	
	@Override
	public void updateSeckillRule(ProdSeckillRule seckillRule) {
		prodSeckillRuleDAO.updateSeckillRule(seckillRule);		
	}
	
	@Override
	public Integer selectSeckillRuleRowCount(Map<String, Object> param) {
		return prodSeckillRuleDAO.selectRowCount(param);
	}

	@Override
	public Integer deleteFromProdseckillrule(Long businessCouponId) {
		// TODO Auto-generated method stub
		return prodSeckillRuleDAO.deleteFromProdseckillrule(businessCouponId);
	}	
	@Override
	public Integer deleteByPk(Long id) {
		// TODO Auto-generated method stub
		return prodSeckillRuleDAO.deleteByPk(id);
	}
	@Override
	public Integer updateByPk(ProdSeckillRule seckillRule) {
		// TODO Auto-generated method stub
		return prodSeckillRuleDAO.updateByPk(seckillRule);
	}
	public void setProdSeckillRuleDAO(ProdSeckillRuleDAO prodSeckillRuleDAO) {
		this.prodSeckillRuleDAO = prodSeckillRuleDAO;
	}
	@Override
	public List<ProdSeckillRule> queryValidSeckillRule(Map<String, Object> param) {
		return prodSeckillRuleDAO.queryValidSeckillRule(param);
	}

	@Override
	public List<ProdSeckillRule> queryNullitySeckillRule(Map<String, Object> param) {
		return prodSeckillRuleDAO.queryNullitySeckillRule(param);
	}
	@Override
	public void restoreStockSeckill(Map<String, Object> param) {
		this.prodSeckillRuleDAO.restoreStockSeckill(param);		
	}
	@Override
	public List<ProdSeckillRule> querySeckillRuleByClient(
			Map<String, Object> paramMap) {
		return prodSeckillRuleDAO.querySeckillRuleByClient(paramMap);
	}
	@Override
	public int minusStockSeckill(Map<String, Object> param) {
		return prodSeckillRuleDAO.minusStockSeckill(param);
	}
}
