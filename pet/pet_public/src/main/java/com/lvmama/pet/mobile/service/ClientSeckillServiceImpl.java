package com.lvmama.pet.mobile.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.pet.service.mobile.ClientSeckillService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.MemcachedSeckillUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author liudong
 *
 */
public class ClientSeckillServiceImpl implements ClientSeckillService{
	
	private static final Log log = LogFactory.getLog(ClientSeckillServiceImpl.class);
	
	@Resource
	private ProdSeckillRuleService prodSeckillRuleService;
	@Override
	public Page<ProdSeckillRule> seckillSearch(Integer page, Integer pageSize,
			Date startDate, Date endDate,Long productId,Long branchId) {
		log.info(" =============== lvtu  seckillSearch  page:"+page+"---pageSize:"+pageSize+"---startDate:"+startDate
				+"---endDate:"+endDate+"---productId:"+productId+"---branchId:"+branchId);
		List<ProdSeckillRule> allMatchList = new ArrayList<ProdSeckillRule>();
		Map<String,Object> paramMap = new HashMap<String, Object>();
		if (startDate!=null) {
			paramMap.put("startDate", startDate);
		}
		if (startDate!=null) {
			paramMap.put("endDate", endDate);
		}	
		if (productId!=null) {
			paramMap.put("productId", productId);
		}
		if (branchId!=null) {
			paramMap.put("branchId", branchId);
		}
		List<ProdSeckillRule> list =prodSeckillRuleService.querySeckillRuleByClient(paramMap);
		allMatchList.addAll(list);
		Page<ProdSeckillRule> pageConfigClient = new Page<ProdSeckillRule>(list.size(), pageSize, page);
		pageConfigClient.setAllItems(allMatchList);
		return pageConfigClient;
	}

	@Override
	public Long getWaitPeopleByMemcached(Long branchId, boolean flag,
			Long number) {
		log.info("=============== lvtu getWaitPeopleByMemcached branchId:"+branchId+"---flag:"+flag+"---number:"+number);
		if(branchId == null){
			return 0L;
		}else{
			
			Object waitPeopleM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+branchId.toString());
			if(waitPeopleM != null){
				if(flag){
					if(number == null) return 0L;
					return MemcachedSeckillUtil.getMemCachedSeckillClient().decr(Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+branchId.toString(),number);
				}
				log.info("=============== fetch WaitPeopleByMemcached in memcached:"+waitPeopleM);
				return Long.valueOf(StringUtils.trim(waitPeopleM+""));
			}else{
				
				synchronized (this) {
					
					waitPeopleM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+branchId.toString());
					
					if(waitPeopleM != null){
						if(flag){
							if(number == null) return 0L;
							return MemcachedSeckillUtil.getMemCachedSeckillClient().decr(Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+branchId.toString(),number);
						}
						return Long.valueOf(StringUtils.trim(waitPeopleM+""));
					}
					
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("nowDate", new Date());
					paramMap.put("branchId", branchId);
					
					List<ProdSeckillRule> seckillRuleList = prodSeckillRuleService.queryValidSeckillRule(paramMap);
		
					if(seckillRuleList != null && seckillRuleList.size() > 0){
						ProdSeckillRule seckillRule = seckillRuleList.get(0);
						
						Long temp = seckillRule.getAmount();
						
						//等待人数 倍率
						Object waitMultiple = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.WAIT_MULTIPLE+seckillRule.getBranchId().toString());
						Long multiple = 0L;
						
						if(waitMultiple == null){
							multiple = seckillRule.getIpBuyLimit() == null ? 1L : seckillRule.getIpBuyLimit();
							MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.WAIT_MULTIPLE+seckillRule.getBranchId().toString(), seckillRule.getIpBuyLimit());
						}else{
							multiple = Long.valueOf(StringUtils.trim(waitMultiple+""));
						}
						
						MemcachedSeckillUtil.getMemCachedSeckillClient().storeCounter(Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+branchId.toString(),
								Long.valueOf(temp*multiple)+1);
						
						if(flag){
							return MemcachedSeckillUtil.getMemCachedSeckillClient().decr(Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+branchId.toString(),number);
						}else{
							return Long.valueOf(temp*multiple)+1;
						}
					}
				}
			}
		}
		return 0L;
	}

	@Override
	public Long getProductNumberByDb(Long branchId, boolean flag, Long number) {
		log.info("=============== lvtu getProductNumberByDb branchId:"+branchId+"---flag:"+flag+"---number:"+number);
		if(branchId == null){
			return 0L;
		}
		Object seckillRuleM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.SECKILL_RULE+branchId.toString());
		
		if(seckillRuleM == null){
			
			synchronized (this) {
				
				seckillRuleM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.SECKILL_RULE+branchId.toString());
				
				if(seckillRuleM == null){
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("nowDate", new Date());
					paramMap.put("branchId", branchId);
					
					List<ProdSeckillRule> seckillRuleList = prodSeckillRuleService.queryValidSeckillRule(paramMap);
					
					if(seckillRuleList != null && seckillRuleList.size() > 0){
						seckillRuleM = seckillRuleList.get(0);
						
						MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.SECKILL_RULE+branchId.toString(),
								seckillRuleM);
					}
				}	
				
			}
		}
		ProdSeckillRule seckillRule = (ProdSeckillRule)seckillRuleM;
		if(flag){
			
			if(number == null){
				return 0L;
			}else{
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("id", seckillRule.getId());
				param.put("decreaseStock", number);
				return (long) prodSeckillRuleService.minusStockSeckill(param);
			}	
		}else{
			seckillRule.getAmount();
		}
		return 0L;
	}

	public void setProdSeckillRuleService(
			ProdSeckillRuleService prodSeckillRuleService) {
		this.prodSeckillRuleService = prodSeckillRuleService;
	}

	@Override
	public ProdSeckillRule getSeckillRuleByBranchId(Long branchId) {
		log.info("=============== lvtu getSeckillRuleByBranchId branchId:"+branchId);
		if(branchId == null){
			return null;
		}
		
		Object seckillRuleM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.SECKILL_RULE+branchId.toString());
		
		if(seckillRuleM != null){
			log.info("=============== fetch ProdSeckillRule in memcached:"+branchId);
			return (ProdSeckillRule)seckillRuleM;
		}else{
			log.info("=============== fetch ProdSeckillRule in DB:"+branchId);
			synchronized (this) {
				
				seckillRuleM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.SECKILL_RULE+branchId.toString());
				
				if(seckillRuleM != null){
					return (ProdSeckillRule)seckillRuleM;
				}	
				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("nowDate", new Date());
				paramMap.put("branchId", branchId);
				
				List<ProdSeckillRule> seckillRuleList = prodSeckillRuleService.queryValidSeckillRule(paramMap);
	
				if(seckillRuleList != null && seckillRuleList.size() > 0){
					ProdSeckillRule seckillRule = seckillRuleList.get(0);
					MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.SECKILL_RULE+branchId.toString(),
							seckillRule);
					log.info("fetch ProdSeckillRule in DB:"+JSONObject.fromObject(seckillRule));
					return seckillRule;
				}
			}
		}
		return null;
	}
	
}
