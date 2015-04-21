package com.lvmama.front.web.seckill;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdCouponIntervalService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedSeckillUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 获取秒杀相关 memcached 
 * 
 * @author zenglei
 *
 */
public class SeckillMemcachedUtil {
	
	
	private ProdSeckillRuleService prodSeckillRuleService = getProdSeckillRuleService();
	
	private ProductHeadQueryService productServiceProxy = getProductServiceProxy();	
	
	private OrderService orderServiceProxy = getOrderServiceProxy();
	
	private ProdCouponIntervalService prodCouponIntervalService = getProdCouponIntervalService();
	
	private ProductSearchInfoService productSearchInfoService = getProductSearchInfoService();
	
	public static SeckillMemcachedUtil seckillMemcached = null;
	
	public static SeckillMemcachedUtil getSeckillMemcachedUtil(){
		if(seckillMemcached == null){
			synchronized (SeckillMemcachedUtil.class) {
				if(seckillMemcached == null){
					seckillMemcached = new SeckillMemcachedUtil();
				}
				
			}
		}
		return seckillMemcached;
	}
	
	
	/**
	 * 获取指定类别中缓存中的产品数量
	 * @param branchId
	 * @param flag 减
	 * @param number
	 * @return
	 */
	public Long getProductNumberByMemcached(Long branchId,boolean flag,Long number){
		
		if(branchId == null){
			return null;
		}
		Object productNumber = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.PRODUCT_REPERTORY_NUMBER+branchId.toString());
		if(productNumber != null){ 
			if(flag){
				return MemcachedSeckillUtil.getMemCachedSeckillClient().decr(Constant.SECKILL.PRODUCT_REPERTORY_NUMBER+branchId.toString(),number);
			}
			return Long.valueOf(StringUtils.trim(productNumber+""));
		}else{
			
			synchronized (this) {
				productNumber = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.PRODUCT_REPERTORY_NUMBER+branchId.toString());
				if(productNumber != null){
					if(flag){
						return MemcachedSeckillUtil.getMemCachedSeckillClient().decr(Constant.SECKILL.PRODUCT_REPERTORY_NUMBER+branchId.toString(),number);
					}
					return Long.valueOf(StringUtils.trim(productNumber+""));
				}
				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("nowDate", new Date());
				paramMap.put("branchId", branchId);
				
				List<ProdSeckillRule> seckillRuleList = prodSeckillRuleService.queryValidSeckillRule(paramMap);
				
				if(seckillRuleList != null && seckillRuleList.size() > 0){
					
					ProdSeckillRule seckillRule = seckillRuleList.get(0);
					
					Long temp = seckillRule.getAmount();
					
					MemcachedSeckillUtil.getMemCachedSeckillClient().storeCounter(Constant.SECKILL.PRODUCT_REPERTORY_NUMBER+seckillRule.getBranchId().toString(),
							temp+1);
					
					if(flag){
						return MemcachedSeckillUtil.getMemCachedSeckillClient().decr(Constant.SECKILL.PRODUCT_REPERTORY_NUMBER+branchId.toString(),number);
					}else{
						return temp+1;
					}
				}
			}
		}
		return 0L;
	}
	
	/**
	 * 获取指定类别的秒杀规则
	 * @param branchId
	 * @return
	 */
	public ProdSeckillRule getSeckillRule(Long branchId){
		
		if(branchId == null){
			return null;
		}
		
		Object seckillRuleM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.SECKILL_RULE+branchId.toString());
		
		if(seckillRuleM != null){
			return (ProdSeckillRule)seckillRuleM;
		}else{
			
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
					return seckillRule;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取指定类别的秒杀规则
	 * @param productId
	 * @return
	 */
	public ProdSeckillRule getSeckillRuleByProductId(Long productId){
		
		if(productId == null){
			return null;
		}
		
		Object seckillRuleM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.SECKILL_RULE_BY_PRODUCTID+productId.toString());
		
		if(seckillRuleM != null){
			return (ProdSeckillRule)seckillRuleM;
		}else{
			
			synchronized (this) {
				
				seckillRuleM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.SECKILL_RULE_BY_PRODUCTID+productId.toString());
				
				if(seckillRuleM != null){
					return (ProdSeckillRule)seckillRuleM;
				}	
				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("nowDate", new Date());
				paramMap.put("productId", productId);
				
				List<ProdSeckillRule> seckillRuleList = prodSeckillRuleService.queryValidSeckillRule(paramMap);
	
				if(seckillRuleList != null && seckillRuleList.size() > 0){
					ProdSeckillRule seckillRule = seckillRuleList.get(0);
					MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.SECKILL_RULE_BY_PRODUCTID+productId.toString(),
							seckillRule);
					return seckillRule;
				}
			}
		}
		return null;
	}
	
	/**
>>>>>>> .merge-right.r75639
	 * 获取指定类别时间价格
	 * @param branchId
	 * @return
	 */
	public List<CalendarModel> getCalendarModel(Long productId,Long branchId){
		if(branchId == null){
			return null;
		}
		Object cmListM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.TIMEPRICETABLE_BRANCHID+branchId.toString());
		if(cmListM != null){
			return (List<CalendarModel>)cmListM;
		}else{
			
			synchronized (this) {
			
				cmListM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.TIMEPRICETABLE_BRANCHID+branchId.toString());
				
				if(cmListM != null){
					return (List<CalendarModel>)cmListM;
				}
				
				List<CalendarModel> cmList = productServiceProxy.getProductCalendarByBranchId(branchId);
				
				/*cmList = favorService.fillFavorParamsInfoForCalendar(null,branchId, cmList);//填充优惠信息
				//填充促优惠标签
				cmList = fillCuCouponFlagForCalendar(productId, branchId, cmList);*/
				
				if(cmList != null){
					MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.TIMEPRICETABLE_BRANCHID+branchId.toString(), cmList);
					return cmList;
				}
				
			}
		}
		return null;
	}
	
	/**
	 * 获取指定类别的等待人数
	 * @param branchId 类别ID
	 * @param flag	是否需要减缓存中的等待人数
	 * @param number  如需要减(具体值)
	 * @return
	 */
	public Long getWaitPeopleByMemcached(Long branchId,boolean flag,Long number){
		if(branchId == null){
			return null;
		}
		Object waitPeopleM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+branchId.toString());
		
		if(waitPeopleM != null){
			if(flag){
				return MemcachedSeckillUtil.getMemCachedSeckillClient().decr(Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+branchId.toString(),number);
			}
			return Long.valueOf(StringUtils.trim(waitPeopleM+""));
		}else{
			
			synchronized (this) {
				
				waitPeopleM = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+branchId.toString());
				
				if(waitPeopleM != null){
					if(flag){
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
		return null;
	}
	
	public Long getUnpayOrderForSeckill(Long branchId){
		
		if(branchId == null){
			return 0L;
		}
		
		Object unpayOrderSize = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.SECKILL_UNPAY_ORDER+branchId.toString());
		
		if(unpayOrderSize != null){
			
			return Long.valueOf(StringUtils.trim(unpayOrderSize+""));
			
		}else{
			
			synchronized (this) {
				
				unpayOrderSize = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.SECKILL_UNPAY_ORDER+branchId.toString());
				
				if(unpayOrderSize != null){
					return Long.valueOf(StringUtils.trim(unpayOrderSize+""));
				}
				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("nowDate", new Date());
				paramMap.put("branchId", branchId);
				
				List<ProdSeckillRule> seckillRuleList = prodSeckillRuleService.queryValidSeckillRule(paramMap);
	
				if(seckillRuleList != null && seckillRuleList.size() > 0){
					ProdSeckillRule seckillRule = seckillRuleList.get(0);
					
					if(new Date().after(seckillRule.getStartTime())){
						
						paramMap.clear();
						paramMap.put("seckillId", seckillRule.getId());
						paramMap.put("status", Constant.PAYMENT_STATUS.UNPAY.getCode());
						
						List<OrdOrder> orderList = orderServiceProxy.queryOrderBySeckill(paramMap);
						
						if(orderList != null && orderList.size() >= 0){
							unpayOrderSize = orderList.size();
						}
						
					}
					MemcachedSeckillUtil.getInstance().set(Constant.SECKILL.SECKILL_UNPAY_ORDER+seckillRule.getBranchId().toString(),
							unpayOrderSize);
					return Long.valueOf(StringUtils.trim(unpayOrderSize+""));
				}
			}
		}
		return 0L;
	}
	
	/**
	 * 
	 * @param cmList
	 * @param product
	 * @return
	 */
	@SuppressWarnings("unused")
	public String getValidateCode(){
		
		Object codeList = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.SECKILL_VALIDATE_CODE.toString());
		
		if(codeList == null){
			synchronized (this) {
				
				codeList = MemcachedSeckillUtil.getInstance().get(Constant.SECKILL.SECKILL_VALIDATE_CODE.toString());
				
				if(codeList == null){
					//随机取产品类型
					long isTicket = Math.round(Math.random()*3+1);
					
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("isTicket", isTicket);
					param.put("startRows", 0);
					param.put("endRows", 100);
					
					List<String> validateCodeList = new ArrayList<String>();
					
					List<ProductSearchInfo> searchInfoList = productSearchInfoService.queryProductSearchInfoByParam(param);
					if(searchInfoList != null){
						for (ProductSearchInfo productSearchInfo : searchInfoList) {
							String hanziString = productSearchInfo.getProductName().replaceAll("[^\u4e00-\u9fa5]","");
							if(hanziString.length() >= 4){
								validateCodeList.add(hanziString.substring(0,4));
							}
						}
					}
					codeList = validateCodeList;
					MemcachedSeckillUtil.getMemCachedSeckillClient().set(Constant.SECKILL.SECKILL_VALIDATE_CODE.toString(), validateCodeList);
				}
			}
		}
		Random random = new Random();
		if(codeList == null){
			String[] defaultCode = {"上海旅游","旅游上海","杭州旅游","旅游杭州","苏州旅游","旅游苏州","韩国旅游","旅游韩国"};
			return defaultCode[random.nextInt(defaultCode.length)];
		}else{
			List<String> list = (List<String>)codeList;
			return list.get(random.nextInt(list.size()));
		}
	}
	
	public String getJSONTimePrice(List<CalendarModel> cmList,ProdProduct product){
		if(CollectionUtils.isEmpty(cmList) || product == null){
			return "";
		}else{
			String[] month ={"1","2","3","4","5","6","7","8","9","10","11","12"};
			JSONObject objM=new JSONObject();
			boolean isMultiJourney = false;
			if(product.isRoute()){
				ProdRoute pr = (ProdRoute) product;
				isMultiJourney = pr.hasMultiJourney();
			}
				// 循环判断月份
				for (int m = 0; m < month.length; m++) {
					JSONArray array = new JSONArray();
					for (int k = 0; k < cmList.size(); k++) {
						CalendarModel cm = cmList.get(k);
						TimePrice[][] calendar = cm.getCalendar();
						for (int i = 0; i < calendar.length; i++) {
							TimePrice[] objH = calendar[i];
							for (int j = 0; j < objH.length; j++) {
								JSONObject obj = new JSONObject();
								TimePrice tp = objH[j];
								if ((DateUtil.getDateTime("M", tp.getSpecDate())).equals(month[m])) {
										obj.put("date", DateUtil.getDateTime("yyyy-MM-dd", tp.getSpecDate()));
										long dayStock = tp.getDayStock();
										if (dayStock == -1 || dayStock == 0|| dayStock > 0|| tp.isOnlyForLeave() == true|| tp.getOverSale().equals("true")) {
											 JSONObject j1=excecuteGetJsonTimeData(tp,dayStock,isMultiJourney);
											 obj.putAll(j1);
										} else {
											obj.put("number", "");
											obj.put("price", "");
											obj.put("active", "");
										 }
									   if(!array.contains(obj)){
										   array.add(obj);
									   }
								 }
							}
						}
					}
					if (array != null && !array.equals("")) {
						objM.put(month[m], array);
					}
 				}
			return objM.toString();
		}
	}
	private JSONObject excecuteGetJsonTimeData(TimePrice tp,long dayStock,boolean isMultiJourney){
		JSONObject obj = new JSONObject();
		if ((!tp.isNeedResourceConfirm() && dayStock == -1) || dayStock > 9) {
					if (isMultiJourney && tp.getMultiJourneyId() == null) { } else {
						obj.put("number", "充足");
						obj.put("price",
								tp.getPriceInt());
						if ((tp.getFavorJsonParams() != null && !tp
								.getFavorJsonParams()
								.equals(""))
								|| (tp.getCuCouponFlag() > 0)) {
							obj.put("active", "促");
						} else {
							obj.put("active", "");
						}
					 }
			} else if (dayStock > -1&& dayStock != 0) {
						if (isMultiJourney
								&& tp.getMultiJourneyId() == null) {
						} else {
							obj.put("number", dayStock);
							obj.put("price",
									tp.getPriceInt());
							if ((tp.getFavorJsonParams() != null && !tp
									.getFavorJsonParams()
									.equals(""))
									|| (tp.getCuCouponFlag() > 0)) {
								obj.put("active", "促");
							} else {
								obj.put("active", "");
							}
						}
			} else if (((tp.isOverSaleAble() || tp.isNeedResourceConfirm()) && dayStock == -1)|| (tp.isOverSaleAble() && dayStock == 0)) {
							if (isMultiJourney
									&& tp.getMultiJourneyId() == null) {

								} else {
									obj.put("number", "");
									obj.put("price",
											tp.getPriceInt());
									if ((tp.getFavorJsonParams() != null && !tp
											.getFavorJsonParams()
											.equals(""))
											|| (tp.getCuCouponFlag() > 0)) {
										obj.put("active", "促");
									} else {
										obj.put("active", "");
									}
								}
				} else if (!tp.isSellable(1)) {
				obj.put("number", "售完");
				obj.put("price", tp.getPriceInt());
				obj.put("active", "");
			   }
	   return obj;
	}
	/**
	 * 获取该产品当前7个月范围促优惠时间标示
	 * @param productId
	 * @param branchId
	 * @param List<CalendarModel> list
	 * @return
	 */
	private List<CalendarModel> fillCuCouponFlagForCalendar(Long productId, Long branchId, List<CalendarModel> cmList){
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, -1);
		Date startDate = cal.getTime();
		cal.add(Calendar.MONTH, 7);
		Date endDate = cal.getTime();
		Map<String,Object> dateParam = new HashMap<String,Object>();
		dateParam.put("startDate", startDate);
		dateParam.put("endDate", endDate);
		cmList = prodCouponIntervalService.fillCuCouponFlagForCalendar(productId, branchId, cmList, dateParam);
		return cmList;
	}
	
	private ProdSeckillRuleService getProdSeckillRuleService() {
		Object object = SpringBeanProxy.getBean("prodSeckillRuleService");
		return object == null ? null : (ProdSeckillRuleService)object;
	}

	private ProductHeadQueryService getProductServiceProxy() {
		Object object = SpringBeanProxy.getBean("productServiceProxy");
		return object == null ? null : (ProductHeadQueryService)object;
	}

	private OrderService getOrderServiceProxy() {
		Object object = SpringBeanProxy.getBean("orderServiceProxy");
		return object == null ? null : (OrderService)object;
	}

	private ProdCouponIntervalService getProdCouponIntervalService() {
		Object object = SpringBeanProxy.getBean("prodCouponIntervalService");
		return object == null ? null : (ProdCouponIntervalService)object;
	}
	
	private ProductSearchInfoService getProductSearchInfoService(){
		Object object = SpringBeanProxy.getBean("productSearchInfoService");
		return object == null ? null : (ProductSearchInfoService)object;
	}
}
