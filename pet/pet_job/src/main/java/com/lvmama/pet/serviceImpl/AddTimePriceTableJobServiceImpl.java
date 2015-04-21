package com.lvmama.pet.serviceImpl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MemcachedSeckillUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.service.AddTimePriceJobService;


/**
 * 秒杀相关商品、时间价格缓存
 * 
 * 修改时间价格表、或着还原时间价格
 * @author zenglei
 *
 */
public class AddTimePriceTableJobServiceImpl implements AddTimePriceJobService,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 671715187801579987L;

	private static final Log log = LogFactory.getLog(AddTimePriceTableJobServiceImpl.class);
	
	private ProdSeckillRuleService prodSeckillRuleService;
	
	private ProdProductBranchService prodProductBranchService;
	
	protected ProductHeadQueryService productServiceProxy;
	
	private int endTime = 10;  //在开始前  多少分钟内，进行更新时间价格
	
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		
		log.info(" =========== AddTimePriceTableJob  is run =========== job run ? "+Constant.getInstance().isJobRunnable());

		TaskResult  result = new TaskResult();
		
		if(Constant.getInstance().isJobRunnable()){
			
			prodSeckillRuleService = (ProdSeckillRuleService) SpringBeanProxy.getBean("prodSeckillRuleService");
			
			prodProductBranchService = (ProdProductBranchService) SpringBeanProxy.getBean("prodProductBranchService");
			
			productServiceProxy = (ProductHeadQueryService) SpringBeanProxy.getBean("productServiceProxy");
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			Date date = new Date();
			
			//秒杀结束的商品
			paramMap.put("nowDate",date);
			
			List<ProdSeckillRule> nullitySeckillRuleList = prodSeckillRuleService.queryNullitySeckillRule(paramMap);
			if(null != nullitySeckillRuleList && nullitySeckillRuleList.size() > 0){
				
				ProdSeckillRule seckillRule = new ProdSeckillRule();
				
				for (ProdSeckillRule prodSeckillRule : nullitySeckillRuleList) {
					//在秒杀结束十分钟内，进行修改
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(prodSeckillRule.getEndTime());
					calendar.add(Calendar.MINUTE, endTime);
					
					if(date.before(calendar.getTime())){
					
						if(prodSeckillRule.getBranchId() != null){

							if(prodSeckillRule.getTimePriceStatus().equals("1")){
								//加上优惠价格
								paramMap.clear();
								paramMap.put("branchId", prodSeckillRule.getBranchId());
								paramMap.put("price",  -prodSeckillRule.getReducePrice());
								prodProductBranchService.updateTimePriceForBranchId(paramMap);
								
								log.info(" =========== AddTimePriceTableJob   add timePrice: branchId:"+prodSeckillRule.getBranchId() +
										" =========== reducePrice:"+prodSeckillRule.getReducePrice());
								
								//将秒杀规则 是否更新过时间价格表标示进行  还原
								seckillRule.setId(prodSeckillRule.getId());
								seckillRule.setTimePriceStatus("0");
								prodSeckillRuleService.updateSeckillRule(seckillRule);
								
								log.info(" =========== AddTimePriceTableJob   update SeckillRule timePriceStatus 0: prodSeckillRule.getId:"+prodSeckillRule.getId());
							}
						}else{
							continue;
						}
					}
					//更新缓存
					List<CalendarModel> cmList = productServiceProxy.getProductCalendarByBranchId(prodSeckillRule.getBranchId());
					
					MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.TIMEPRICETABLE_BRANCHID+prodSeckillRule.getBranchId().toString(), cmList);
					log.info(" ============ AddTimePriceTableJob MemcachedSeckill : " + Constant.SECKILL.TIMEPRICETABLE_BRANCHID+prodSeckillRule.getBranchId().toString() +"---"+cmList.size());
				}
			}
		}
		
		result.setStatus(1);
		
		return result;
	}
}
