package com.lvmama.comm.pet.service.mobile;

import java.io.IOException;
import java.util.Date;

import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.vo.Page;


/**
 * 手机端秒杀规则相关
 * 
 * @author liudong
 * 
 */
public interface ClientSeckillService {
	
	/**
	 * 获取秒杀规则BEAN
	 * @throws IOException
	 * @param page 多少页
	 * @param pageSize 每页多少条
	 * @param startDate 
 	 * @param endDate 
	 * @return JASON格式
	 */
	public Page<ProdSeckillRule> seckillSearch(Integer page, Integer pageSize,Date startDate,Date endDate,Long productId,Long branchId) ;
		
	/** 
	* 获取指定类别的等待人数 
	* @param branchId 类别ID 
	* @param flag 是否需要减缓存中的等待人数 
	* @param number 如需要减(具体值) 
	* @return 
	*/
	public Long getWaitPeopleByMemcached(Long branchId,boolean flag,Long number);

	/** 
	* 获取指定类别中数据库中的产品数量 
	* @param branchId 
	* @param flag 是否需要减数据中的库存  
	* @param number 如需要减(具体值) 
	* @return 
	*/ 
	public Long getProductNumberByDb(Long branchId,boolean flag,Long number);

	/**
	 * 获取指定类别对应的秒杀规则
	 */
	public ProdSeckillRule getSeckillRuleByBranchId(Long branchId);
}
