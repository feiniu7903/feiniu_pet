package com.lvmama.comm.pet.service.businessCoupon;

import java.util.List;
import java.util.Map;


import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;

/**
 * 秒杀规则
 * 
 * @author zenglei
 *
 */
public interface ProdSeckillRuleService {
	/**
	 * 保存记录
	 * @param entity 保存对象
	 * @return void
	 */
	ProdSeckillRule insertProdSeckillRule(ProdSeckillRule entity);

	/**
	 * 根据查询条件返回所有符合条件的特会规则
	 * @param param 查询条件
	 * @return 
	 */
	List<ProdSeckillRule> selectByParam(Map<String,Object> param); 

	
	/**
	 * 当前时间，适用的秒杀规则
	 * @return
	 */
	public List<ProdSeckillRule> querySeckillRule();
	
	/**
	 * 更新秒杀规则
	 */
	public void updateSeckillRule(ProdSeckillRule seckillRule);

	/**
	 * 根据查询条件统计特卖会批次总数
	 * @param param 查询条件
	 * @return 条目数
	 */
	Integer selectSeckillRuleRowCount(Map<String,Object> param);	
	/**
	 * 更新秒杀规则，根据ID
	 */
	public Integer updateByPk(ProdSeckillRule seckillRule);
	
	/**
	 * 根据优惠ID删除规则
	 *  @return 条目数
	 */
	Integer deleteFromProdseckillrule(Long businessCouponId);
	/**
	 * 根据规则ID删除规则
	 */
	public Integer deleteByPk(Long id);		
	
	/**
	 * 当前时间，有效的适用秒杀规则
	 * @return
	 */
	public List<ProdSeckillRule> queryValidSeckillRule(Map<String, Object> param);
	
	/**
	 * 当前时间，无效的适用秒杀规则
	 */
	public List<ProdSeckillRule> queryNullitySeckillRule(Map<String, Object> param);
	
	/**
	 * 还原库存
	 */
	public void restoreStockSeckill(Map<String, Object> param);
	/**
	 * 客户端查询秒杀规则
	 * */
	public List<ProdSeckillRule> querySeckillRuleByClient(Map<String, Object> paramMap);
	/**
	 * 减库存
	 */
	public int minusStockSeckill(Map<String, Object> param);
}
