package com.lvmama.pet.businessCoupon.dao;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;

/**
 * 
 * @author zenglei
 *
 */
public class ProdSeckillRuleDAO extends BaseIbatisDAO{
	/**
     * 插入数据
     * @param record
     * @return
     */
	 public ProdSeckillRule insertProdSeckillRule(ProdSeckillRule prodSeckillRule) {
	    	super.insert("PROD_SECKILL_RULE.insert", prodSeckillRule);
	        return prodSeckillRule;
	 }
	 
	 public List<ProdSeckillRule> querySeckillRule() {
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("nowDate",new Date());
			return super.queryForList("PROD_SECKILL_RULE.querySeckillRule",paramMap);
	 }
	 
	 public void updateSeckillRule(ProdSeckillRule seckillRule){
		 	super.update("PROD_SECKILL_RULE.updateSeckillRule",seckillRule);
	 } 
	 public Integer updateByPk(ProdSeckillRule seckillRule){
		 Integer rows =  super.update("PROD_SECKILL_RULE.updateByPk",seckillRule);
	     return rows;
		
	 }
	 /**
	   * 根据map参数筛选数据
	   * @param param
	   * @return
	   */
	@SuppressWarnings("unchecked")
	public List<ProdSeckillRule> selectByParam(Map<String,Object> param){
		 return super.queryForList("PROD_SECKILL_RULE.selectByParam", param);
	 }
	 
	public Integer selectRowCount(Map<String,Object> param){
		Integer count = 0;
		count = (Integer) super.queryForObject("PROD_SECKILL_RULE.selectCountByParam",param);
		return count;
	}
	
	public Integer deleteFromProdseckillrule(Long businessCouponId){
		return super.delete("PROD_SECKILL_RULE.deleteFromProdseckillrule", businessCouponId);
	}
	public Integer deleteByPk(Long id){
		return super.delete("PROD_SECKILL_RULE.deleteByPk", id);
	}
	public List<ProdSeckillRule> queryValidSeckillRule(Map<String, Object> paramMap) {
		 return super.queryForList("PROD_SECKILL_RULE.queryValidSeckillRule",paramMap);
	}
	 
	public List<ProdSeckillRule> queryNullitySeckillRule(Map<String, Object> paramMap){
	 	return super.queryForList("PROD_SECKILL_RULE.queryNullitySeckillRule",paramMap);
	}
	public List<ProdSeckillRule> querySeckillRuleByClient(Map<String, Object> paramMap){
	 	return super.queryForList("PROD_SECKILL_RULE.querySeckillRuleByClient",paramMap);
	}
	public void restoreStockSeckill(Map<String, Object> param){
		super.update("PROD_SECKILL_RULE.restoreStockSeckill",param);
	}
	public int minusStockSeckill(Map<String, Object> param){
		return super.update("PROD_SECKILL_RULE.minusStockSeckill",param);
	}
}
