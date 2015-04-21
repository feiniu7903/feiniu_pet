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
import com.lvmama.pet.service.SeckillRuleJobService;;

/**
 * 加载秒杀规则
 * 
 * @author zenglei
 *
 */
public class SeckillRuleJobServiceImpl implements SeckillRuleJobService,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3504195507132359258L;

	private static final Log log = LogFactory.getLog(SeckillRuleJobServiceImpl.class);
	
	private ProdSeckillRuleService prodSeckillRuleService;
	
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		
		 log.info(" =========== SeckillRuleJob  is run =========== job run ? :"+Constant.getInstance().isJobRunnable());
		 TaskResult  result = new TaskResult();
		 
		 if(Constant.getInstance().isJobRunnable()){
			 
			 Map<String,Object> paramMap = new HashMap<String, Object>();
		 	 paramMap.put("nowDate",new Date());
		 	 
		 	 prodSeckillRuleService = (ProdSeckillRuleService) SpringBeanProxy.getBean("prodSeckillRuleService");
		 	 
			 List<ProdSeckillRule> seckillRuleList = prodSeckillRuleService.queryValidSeckillRule(paramMap);
			 if(null != seckillRuleList){
				//加载秒杀规则存入到
				 for (ProdSeckillRule prodSeckillRule : seckillRuleList) {
					 MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.SECKILL_RULE+prodSeckillRule.getBranchId().toString(),
							prodSeckillRule);
					 MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.SECKILL_RULE_BY_PRODUCTID+prodSeckillRule.getProductId().toString(),
							prodSeckillRule);
				 	log.info(" =========== SeckillRuleJob MemcachedSeckill:  =========== seckillId:"+ prodSeckillRule.getId() +"   "+Constant.SECKILL.SECKILL_RULE+prodSeckillRule.getBranchId().toString()+" : " + 
				 			prodSeckillRule.getId()+" time : "+prodSeckillRule.getStartTime().toLocaleString()+" - " +prodSeckillRule.getEndTime().toLocaleString());
				 }
			 }
		}
		result.setStatus(1);
		return result;
	}
	
}
