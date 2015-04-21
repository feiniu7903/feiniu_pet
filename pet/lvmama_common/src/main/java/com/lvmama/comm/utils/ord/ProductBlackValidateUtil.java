package com.lvmama.comm.utils.ord;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrderBlack;
import com.lvmama.comm.bee.po.prod.ProdBlackList;
import com.lvmama.comm.bee.service.ord.OrdOrderBlackService;
import com.lvmama.comm.pet.service.prod.ProdBlackListService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.StringUtil;

public class ProductBlackValidateUtil implements Serializable{

	/**
	 * 
	 */
	private static final Log log = LogFactory.getLog(ProductBlackValidateUtil.class);
	
	private static final long serialVersionUID = -3619063197100933130L;

	private ProdBlackListService prodBlackListService = getProdBlackListService();
	
	private OrdOrderBlackService ordOrderBlackService = getOrdOrderBlackService();
	
	public static ProductBlackValidateUtil blackValidate = null;
	
	public static ProductBlackValidateUtil getProductBlackValidateUtil(){
		if(blackValidate == null){
			synchronized (ProductBlackValidateUtil.class) {
				if(blackValidate == null){
					blackValidate = new ProductBlackValidateUtil();
				}
			}
		}
		return blackValidate;
	}
	
	private String userType = "2";
	private String imeiType = "3";
	private String mobileType = "4";
	
	/**
	 * 依据产品ID、手机号码校验   陶、
	 * 是否属于黑名单以及是否达下单上限
	 * @param productId
	 * @param mobile
	 * @return
	 */
	public boolean validateBlackByMobileAndProduct(Long productId,String mobile){
		ProdBlackList blackList = this.isBlackProductTao(productId, mobile);
		if(blackList != null){
			log.info("===========find blackList validateBlackByMobileAndProduct ===========productId:"+productId+"====mobile:"+mobile);
			return this.validateBlackByOrder(blackList,productId,mobile,null,null);
		}
		return true;
	}
	/**
	 * 依据产品ID、用户ID校验   驴途
	 * 是否属于黑名单以及是否达下单上限
	 * @param productId
	 * @param mobile
	 * @return
	 */
	public boolean validateBlackByUserAndProductForProds(Long productId,Long userId){
		ProdBlackList blackList = this.isBlackProductProds(productId, userType);
		if(blackList != null){
			log.info("===========find blackList validateBlackByUserAndProduct ===========productId:"+productId+"====userId:"+userId);
			return this.validateBlackByOrder(blackList,productId,null,userId,null);
		}
		return true;
	}
	/**
	 * 依据产品ID、IMEI校验   驴途
	 * 是否属于黑名单以及是否达下单上限
	 * @param productId
	 * @param mobile
	 * @return
	 */
	public boolean validateBlackByImeiAndProductForProds(Long productId,String imei){
		ProdBlackList blackList = this.isBlackProductProds(productId, imeiType);
		if(blackList != null){
			log.info("===========find blackList validateBlackByUserAndProduct ===========productId:"+productId+"====imei:"+imei);
			return this.validateBlackByOrder(blackList,productId,null,null,imei);
		}
		return true;
	}
	/**
	 * 依据产品ID、手机号码校验   驴途
	 * 是否属于黑名单以及是否达下单上限
	 * @param productId
	 * @param mobile
	 * @return
	 */
	public boolean validateBlackByMoblieAndProductForProds(Long productId,String mobile){
		ProdBlackList blackList = this.isBlackProductProds(productId, mobileType);
		if(blackList != null){
			log.info("===========find blackList validateBlackByUserAndProduct ===========productId:"+productId+"====mobile:"+mobile);
			return this.validateBlackByOrder(blackList,productId,mobile,null,null);
		}
		return true;
	}
	
	/**
	 * 检查是否达下单上限
	 * @param prodBlackList
	 * @param productId
	 * @param mobile
	 * @param userId
	 * @param imei
	 * @return
	 */
	private boolean validateBlackByOrder(ProdBlackList prodBlackList,Long productId,String mobile,Long userId,String imei){
		if(prodBlackList == null){
			return true;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		//结算 时间范围
		if(prodBlackList.getBlackCirculation().equals("0")){
			param.put("startTime", CalendarForBlack.NowDayBegin());
			param.put("endTime", CalendarForBlack.NowDayEnd());
		}else if(prodBlackList.getBlackCirculation().equals("1")){
			param.put("startTime", CalendarForBlack.NowWeekBegin());
			param.put("endTime", CalendarForBlack.NowWeekEnd());
		}else if(prodBlackList.getBlackCirculation().equals("2")){
			param.put("startTime", CalendarForBlack.NowMonthBegin());
			param.put("endTime", CalendarForBlack.NowMonthEnd());
		}else if(prodBlackList.getBlackCirculation().equals("3")){
			param.put("startTime", CalendarForBlack.NowYearBegin());
			param.put("endTime", CalendarForBlack.NowYearEnd());
		}
		//指定手机号码
		if(prodBlackList.getBlackIsPhone().equals("0") || prodBlackList.getBlackIsPhone().equals("1")){
			param.put("productId", productId);
			param.put("mobile", mobile);
			Integer in = ordOrderBlackService.queryOrderBlackByParam(param);
			if(in >= prodBlackList.getBlackLimit()){
				return false;
			}
		}else if(prodBlackList.getBlackIsPhone().equals("2")){
			if(userId == null){
				return true;
			}
			param.put("userId", userId);
			param.put("productIds", prodBlackList.getBlackPhoneNum().split(","));
			Integer in = ordOrderBlackService.queryOrderBlackByParam(param);
			if(in >= prodBlackList.getBlackLimit()){
				return false;
			}
		}else if(prodBlackList.getBlackIsPhone().equals("3")){
			if(StringUtil.isEmptyString(imei)){
				return true;
			}
			param.put("imei", imei);
			param.put("productIds", prodBlackList.getBlackPhoneNum().split(","));
			Integer in = ordOrderBlackService.queryOrderBlackByParam(param);
			if(in >= prodBlackList.getBlackLimit()){
				return false;
			}
		}else if(prodBlackList.getBlackIsPhone().equals("4")){
			if(StringUtil.isEmptyString(mobile)){
				return true;
			}
			param.put("mobile", mobile);
			param.put("productIds", prodBlackList.getBlackPhoneNum().split(","));
			Integer in = ordOrderBlackService.queryOrderBlackByParam(param);
			if(in >= prodBlackList.getBlackLimit()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 单用户、单手机、单设备 驴途
	 * @param productId
	 * @param valiType
	 * @return
	 */
	public ProdBlackList isBlackProductProds(Long productId,String valiType){
		if(productId == null || valiType == null){
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		//查找当前有效的用户限制
		param.put("validateTime", new Date());
		param.put("blackIsPhone", valiType);
		List<ProdBlackList> blackListsForUser = prodBlackListService.queryBlackListByParam(param);
		if(blackListsForUser != null  && blackListsForUser.size() > 0){
			for (ProdBlackList prodBlackList : blackListsForUser) {
				if(prodBlackList.getBlackPhoneNum().indexOf(productId.toString()) != -1){
					return prodBlackList;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 */
	public ProdBlackList isBlackProductTao(Long productId,String mobile){
		if(productId == null || mobile == null){
			return null;
		}
		//查找当前有效的用户限制
		Map<String, Object> param = new HashMap<String, Object>();
		//是否指定手机
		param.clear();
		param.put("productId", productId);
		param.put("blackIsPhone", "0");
		param.put("blackPhoneNum", mobile);
		param.put("validateTime", new Date());
		
		List<ProdBlackList> blackLists = new ArrayList<ProdBlackList>();
		
		blackLists = prodBlackListService.queryBlackListByParam(param);
		
		if(blackLists.size() > 0){
			//并且判断时分秒是否在 指定时间
			if(CalendarForBlack.compareDateByHms(blackLists.get(0).getBlackStartTime(), blackLists.get(0).getBlackEndTime(), new Date())){
				return blackLists.get(0);
			}
			return null;
		}
		//是否针对产品
		param.clear();
		param.put("productId", productId);
		param.put("blackIsPhone", "1");
		param.put("validateTime", new Date());
		blackLists = prodBlackListService.queryBlackListByParam(param);
		if(blackLists.size() > 0){
			//并且判断时分秒是否在 指定时间
			if(CalendarForBlack.compareDateByHms(blackLists.get(0).getBlackStartTime(), blackLists.get(0).getBlackEndTime(), new Date())){
				return blackLists.get(0);
			}
			return null;
		}
		return null;
	}
	
	/**
	 * 是否是黑名单产品
	 * @param productId
	 * @param mobile
	 * @return
	 */
	public ProdBlackList isBlackProduct(Long productId,String mobile){
		if(productId == null || mobile == null){
			return null;
		}
		Date nowDate = new Date();
		String[] isPhones = {"2","3","4"};
		//查找当前有效的用户限制
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("validateTime", nowDate);
		param.put("blackIsPhones", isPhones);
		
		List<ProdBlackList> blackListsForUser = prodBlackListService.queryBlackListByParam(param);
		if(blackListsForUser != null  && blackListsForUser.size() > 0){
			for (ProdBlackList prodBlackList : blackListsForUser) {
				if(prodBlackList.getBlackPhoneNum().indexOf(productId.toString()) != -1){
					return prodBlackList;
				}
			}
		}
		
		if(mobile != null){
			//是否指定手机
			param.clear();
			param.put("productId", productId);
			param.put("blackIsPhone", "0");
			param.put("blackPhoneNum", mobile);
			param.put("validateTime", nowDate);
			
			List<ProdBlackList> blackLists = new ArrayList<ProdBlackList>();
			
			blackLists = prodBlackListService.queryBlackListByParam(param);
			
			if(blackLists.size() > 0){
				//并且判断时分秒是否在 指定时间
				if(CalendarForBlack.compareDateByHms(blackLists.get(0).getBlackStartTime(), blackLists.get(0).getBlackEndTime(), nowDate)){
					return blackLists.get(0);
				}
				return null;
			}
			//是否针对产品
			param.clear();
			param.put("productId", productId);
			param.put("blackIsPhone", "1");
			param.put("validateTime", nowDate);
			blackLists = prodBlackListService.queryBlackListByParam(param);
			if(blackLists.size() > 0){
				//并且判断时分秒是否在 指定时间
				if(CalendarForBlack.compareDateByHms(blackLists.get(0).getBlackStartTime(), blackLists.get(0).getBlackEndTime(), nowDate)){
					return blackLists.get(0);
				}
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 新增黑名订单订单记录
	 * @param productId
	 * @param mobile
	 * @param orderId
	 * @param userId
	 * @param imei
	 * @return
	 */
	public boolean insertBlackOrder(Long productId,String mobile,Long orderId,Long userId,String imei){
		if(orderId != null){
			ProdBlackList blackList = this.isBlackProduct(productId, mobile);
			if(blackList != null){
				log.info("=========== blackOrder insertBlackOrder =====" +
						"======productId:"+productId+"====mobile:"+mobile+"===orderId:"+orderId +" ====userId:"+userId+"===imei:"+imei);
				OrdOrderBlack orderBlack = new OrdOrderBlack();
				orderBlack.setOrderId(orderId);
				orderBlack.setMobile(mobile);
				orderBlack.setProductId(productId);
				orderBlack.setUserId(userId);
				orderBlack.setImei(imei);
				ordOrderBlackService.insertOrdOrderBlack(orderBlack);
				return true;
			}
		}
		return false;
	}
	
	private ProdBlackListService getProdBlackListService() {
		Object object = SpringBeanProxy.getBean("prodBlackListService");
		return object == null ? null : (ProdBlackListService)object;
	}
	
	private OrdOrderBlackService getOrdOrderBlackService() {
		Object object = SpringBeanProxy.getBean("ordOrderBlackService");
		return object == null ? null : (OrdOrderBlackService)object;
	}
	
	static class CalendarForBlack{
		public static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		public static SimpleDateFormat fmtYmd = new SimpleDateFormat("yyyy-MM-dd");
		public static SimpleDateFormat ftmHms = new SimpleDateFormat("HH:mm:ss");
		/**
		 * 当前时间、周一
		 * @return
		 */
		public static Date NowWeekBegin(){
			try {
				Calendar c = Calendar.getInstance();
				c.setFirstDayOfWeek(Calendar.MONDAY);
				c.setTime(new Date());
				c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
				return fmt.parse(fmtYmd.format(c.getTime())+" 00:00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 当前时间、周日
		 * @return
		 */
		public static Date NowWeekEnd(){
			try {
				Calendar c = Calendar.getInstance();
		        c.setFirstDayOfWeek(Calendar.MONDAY);
		        c.setTime(new Date());
		        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
				return fmt.parse(fmtYmd.format(c.getTime())+" 23:59:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 当前时间、月初
		 * @return
		 */
		public static Date NowMonthBegin(){
			try {
				Calendar c = Calendar.getInstance();
				c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
				return fmt.parse(fmtYmd.format(c.getTime())+" 00:00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 当前时间、月末
		 * @return
		 */
		public static Date NowMonthEnd(){
			try {
				Calendar c = Calendar.getInstance();
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH)); 
				return fmt.parse(fmtYmd.format(c.getTime())+" 23:59:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
		public static Date NowDayBegin(){
			try {
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				return fmt.parse(fmtYmd.format(c.getTime())+" 00:00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
		public static Date NowDayEnd(){
			try {
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				return fmt.parse(fmtYmd.format(c.getTime())+" 23:59:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
		public static Date NowYearBegin(){
			try {
				Calendar c = Calendar.getInstance(); 
				Calendar c1 = Calendar.getInstance();
				c1.clear();
				c1.set(Calendar.YEAR, c.get(Calendar.YEAR));
				return fmt.parse(fmtYmd.format(c1.getTime())+" 00:00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
		public static Date NowYearEnd(){
			try {
				Calendar c = Calendar.getInstance();
				 Calendar c1 = Calendar.getInstance();  
			     c1.clear();  
			     c1.set(Calendar.YEAR, c.get(Calendar.YEAR));  
			     c1.roll(Calendar.DAY_OF_YEAR, -1);
				return fmt.parse(fmtYmd.format(c1.getTime())+" 23:59:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		}
		public static boolean compareDateByHms(Date startTime,Date endTime,Date nowTime){
			int start = startTime.getHours()*60*60 + startTime.getMinutes()*60 + startTime.getSeconds();
			int now = nowTime.getHours()*60*60 + nowTime.getMinutes()*60 + nowTime.getSeconds();
			int end = endTime.getHours()*60*60 + endTime.getMinutes()*60 + endTime.getSeconds();
			if(start <= now && now <= end){
				return true;
			}
			return false;
		}
	}
}