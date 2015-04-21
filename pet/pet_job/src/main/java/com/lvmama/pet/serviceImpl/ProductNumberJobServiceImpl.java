package com.lvmama.pet.serviceImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MemcachedSeckillUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.service.ProductNumberJobService;

/**
 * 加载库存数量
 * 
 * @author zenglei
 *
 */

public class ProductNumberJobServiceImpl implements ProductNumberJobService,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1821694725392250968L;

	private static final Log log = LogFactory.getLog(ProductNumberJobServiceImpl.class);
	
	private ProdSeckillRuleService prodSeckillRuleService;
	

	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		
		log.info(" =========== ProductNumberJob  is run =========== job run ? "+Constant.getInstance().isJobRunnable());
		
		TaskResult  result = new TaskResult();
		
		if(Constant.getInstance().isJobRunnable()){
			
			Map<String,Object> paramMap = new HashMap<String, Object>();
		 	paramMap.put("nowDate",new Date());
		 	
		 	prodSeckillRuleService = (ProdSeckillRuleService) SpringBeanProxy.getBean("prodSeckillRuleService");
		 	
			List<ProdSeckillRule> seckillRuleList = prodSeckillRuleService.queryValidSeckillRule(paramMap);
			
			//将每个秒杀、还剩余的库存，存入到memcached计数器中
			if(null != seckillRuleList){
				for (ProdSeckillRule prodSeckillRule : seckillRuleList) {
					MemcachedSeckillUtil.getMemCachedSeckillClient().storeCounter(Constant.SECKILL.PRODUCT_REPERTORY_NUMBER+prodSeckillRule.getBranchId().toString(),
							prodSeckillRule.getAmount()+1);
					log.info(" =========== ProductNumberJob  memcachedSeckill:  ===========seckillId:"+ prodSeckillRule.getId() +"   "+Constant.SECKILL.PRODUCT_REPERTORY_NUMBER+prodSeckillRule.getBranchId()+" : " + 
							prodSeckillRule.getAmount());
				}
			}
				
		}
		result.setStatus(1);
		
		return result;
	}
}
