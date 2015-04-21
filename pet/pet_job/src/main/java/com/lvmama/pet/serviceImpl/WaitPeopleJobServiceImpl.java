package com.lvmama.pet.serviceImpl;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MemcachedSeckillUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.service.WaitPeopleJobService;

/**
 * 加载可进入填写订单页人数
 * 
 * 5秒更新一次
 * 
 * @author zenglei
 *
 */
public class WaitPeopleJobServiceImpl implements WaitPeopleJobService,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1269913200582474972L;

	private static final Log log = LogFactory.getLog(WaitPeopleJobServiceImpl.class);
	
	private ProdSeckillRuleService prodSeckillRuleService;
	
	private OrderService orderServiceProxy;
	
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		
		log.info(" =========== WaitPeopleJob is run =========== job run ? "+Constant.getInstance().isJobRunnable());
		
		TaskResult result = new TaskResult();
		
		if(Constant.getInstance().isJobRunnable()){
			
			prodSeckillRuleService = (ProdSeckillRuleService) SpringBeanProxy.getBean("prodSeckillRuleService");
			
			orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
			
			Map<String,Object> paramMap = new HashMap<String, Object>();
		 	paramMap.put("nowDate",new Date());
		 	
			List<ProdSeckillRule> seckillRuleList = prodSeckillRuleService.queryValidSeckillRule(paramMap);
			
			//将每个秒杀、还剩余的库存，存入到memcached计数器中
			if(null != seckillRuleList){
				for (ProdSeckillRule prodSeckillRule : seckillRuleList) {
					
					Object waitMultiple = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.WAIT_MULTIPLE+prodSeckillRule.getBranchId().toString());
					
					Long multiple = 0L;
					if(waitMultiple == null){
						multiple = prodSeckillRule.getIpBuyLimit() == null ? 1L : prodSeckillRule.getIpBuyLimit();
						MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.WAIT_MULTIPLE+prodSeckillRule.getBranchId().toString(), prodSeckillRule.getIpBuyLimit());
					}else{
						multiple = Long.valueOf(waitMultiple+"");
					}
					
					MemcachedSeckillUtil.getMemCachedSeckillClient().storeCounter(Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+prodSeckillRule.getBranchId().toString(),
							Long.valueOf(prodSeckillRule.getAmount()*multiple)+1);
					
					log.info(" =========== WaitPeopleJob MemcachedSkill:  ===========seckillId:"+ prodSeckillRule.getId() +"    "+Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+prodSeckillRule.getBranchId().toString()+" : " + 
							prodSeckillRule.getAmount());
					
					int unpayOrderSize = 0;
					if(new Date().after(prodSeckillRule.getStartTime())){
						paramMap.clear();
						paramMap.put("seckillId", prodSeckillRule.getId());
						paramMap.put("status", Constant.PAYMENT_STATUS.UNPAY.getCode());
						
						List<OrdOrder> orderList = orderServiceProxy.queryOrderBySeckill(paramMap);
						if(orderList != null && orderList.size() >= 0){
							unpayOrderSize = orderList.size();
						}
					}
					MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.SECKILL_UNPAY_ORDER+prodSeckillRule.getBranchId().toString(),
							unpayOrderSize);
					log.info("=========== UnpayOrderJob MemcachedSkill:  ===========UNPAY:"+ prodSeckillRule.getId() +"    "+Constant.SECKILL.SECKILL_UNPAY_ORDER+prodSeckillRule.getBranchId().toString()+" : " + 
							unpayOrderSize);
				}
			}
			
		}
		
		result.setStatus(1);
		return result;
	}
}
