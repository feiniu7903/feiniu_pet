package com.lvmama.pet.serviceImpl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MemcachedSeckillUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.service.MinusTimePriceJobService;


/**
 * 秒杀相关商品、时间价格缓存
 * 
 * 修改时间价格表、或着还原时间价格
 * @author zenglei
 *
 */
public class MinusTimePriceTableJobServiceImpl implements MinusTimePriceJobService,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8120137283236856303L;

	private static final Log log = LogFactory.getLog(MinusTimePriceTableJobServiceImpl.class);
	
	private ProdSeckillRuleService prodSeckillRuleService;
	
	private ProdProductBranchService prodProductBranchService;
	
	protected ProductHeadQueryService productServiceProxy;
	
	private int startTime = 20;  //在开始前  多少分钟内，进行更新时间价格
	
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		
		log.info(" =========== MinusTimePriceTableJob  is run =========== job run ? "+Constant.getInstance().isJobRunnable());
		
		TaskResult  result = new TaskResult();
		
		if(Constant.getInstance().isJobRunnable()){
			
			prodSeckillRuleService = (ProdSeckillRuleService) SpringBeanProxy.getBean("prodSeckillRuleService");
			
			prodProductBranchService = (ProdProductBranchService) SpringBeanProxy.getBean("prodProductBranchService");
			
			productServiceProxy = (ProductHeadQueryService) SpringBeanProxy.getBean("productServiceProxy");
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			Date date = new Date();
			
			//待秒杀 或着 正在秒杀的商品
			paramMap.put("nowDate",date);
			
			List<ProdSeckillRule> seckillRuleList = prodSeckillRuleService.queryValidSeckillRule(paramMap);
			
			if(null != seckillRuleList && seckillRuleList.size() > 0){
				
				ProdSeckillRule seckillRule = new ProdSeckillRule();
				
				for (ProdSeckillRule prodSeckillRule : seckillRuleList) {
					//在秒杀开始半小时前，进行修改
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(prodSeckillRule.getStartTime());
					calendar.add(Calendar.MINUTE, -startTime);
					
					//秒杀开始前半小时、
					if(date.after(calendar.getTime())){
						if(prodSeckillRule.getBranchId() != null){
							
							//如果没有更新过时间价格
							if(prodSeckillRule.getTimePriceStatus().equals("0")){
								
								//减去优惠价格
								paramMap.clear();
								paramMap.put("branchId", prodSeckillRule.getBranchId());
								paramMap.put("price", prodSeckillRule.getReducePrice());
								prodProductBranchService.updateTimePriceForBranchId(paramMap);
								
								log.info(" =========== MinusTimePriceTableJob   minus timePrice: branchId:"+prodSeckillRule.getBranchId() +
										" =========== reducePrice:"+prodSeckillRule.getReducePrice());
								
								//将秒杀规则 是否更新过时间价格表进行标示
								seckillRule.setId(prodSeckillRule.getId());
								seckillRule.setTimePriceStatus("1");
								prodSeckillRuleService.updateSeckillRule(seckillRule);
								
								log.info(" =========== MinusTimePriceTableJob   update SeckillRule timePriceStatus 1: prodSeckillRule.getId:"+prodSeckillRule.getId());
								
							}
							
						}else{
							continue;
						}
					}
					
					List<CalendarModel> cmList = productServiceProxy.getProductCalendarByBranchId(prodSeckillRule.getBranchId());
					
					MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.TIMEPRICETABLE_BRANCHID+prodSeckillRule.getBranchId().toString(), cmList);
					log.info(" ============ MinusTimePriceTableJob MemcachedSeckill : " + Constant.SECKILL.TIMEPRICETABLE_BRANCHID+prodSeckillRule.getBranchId().toString() +"---"+cmList.size());
				}
			}
			
		}
		result.setStatus(1);
		return result;
	}
}
