package com.lvmama.clutter.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.clutter.service.IClientHotelService;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrder;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrderLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mobile.MobileHotelService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 艺龙订单状体修改 . 
 * @author qinzubo
 *
 */
public class MobileHotelOrderLogJob {
    
	private final Logger logger = Logger.getLogger(this.getClass());
	// 艺龙酒店返现截止日期 
	private static final String REFUND_END_DATE = ClutterConstant.getElongRefundEndDate();
	
	
	private MobileHotelService mobileHotelService;
	
	
	/**
	 * 调用
	 */
	private IClientHotelService  clientHotelService;
	
	/**
	 * sso服务
	 */
	protected UserUserProxy userUserProxy;
	
	/**
	 * 奖金账号
	 */
	CashAccountService cashAccountService;


	/**
	 * 定时方法. 
	 */
	public void updateOrderStatus() {
		if (Constant.getInstance().isJobRunnable() && ClutterConstant.isOcsJobRunnable()) {
			
			// 修复返现bug ，只修复一次 
			if(System.currentTimeMillis() < this.getDateTime("2014-01-08") 
					&& System.currentTimeMillis() > this.getDateTime("2014-01-06") ) { 
				initRefundBug();
			}
						
			// 艺龙酒店返现开始日期 
			String REFUND_START_DATE = ClutterConstant.getElongRefundStartDate();
			logger.info("......updateOrderStatus  start.....10......");
			int updateSize = 0;
			// 查询最近两个的订单
			Date date = new Date();
			// 减去30天 
			Date arrivaldateStart = DateUtil.dsDay_Date(date,-30);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("arrivaldateStart", arrivaldateStart);
			params.put("validStatus", "true"); // 查询有效状态的记录数 
			// 订单总数 
			Long orderCount = mobileHotelService.countMobileHotelOrderList(params);
			// 每次最多只能查询1000条数据 
			long size = orderCount/1000 ;
			if(orderCount%1000 != 0) {
				size++;
			}
			Page p = new Page(1000, 1);
			
			//Map<String,Object> hotelIdsMap = getActivityHotelIds();
			for(long i = 1; i <= size ;i++) {
				p.setPage(i);
				params.put("isPaging", "true"); // 是否使用分页
				params.put("startRows", p.getStartRows());
				params.put("endRows", p.getEndRows());
				List<MobileHotelOrder> orderList = mobileHotelService.queryMobileHotelOrderList(params);
				
				if(null != orderList && orderList.size() > 0) {
					for(int j = 0 ; j < orderList.size();j++) {
						try {
							MobileHotelOrder mho = orderList.get(j);
							if(null != mho && null != mho.getOrderId()) {
								// 查询elong订单状态， 根据订单id
								String eLongOrderStatus = getOrderStatus(mho.getOrderId()+"",0);
								if(StringUtils.isEmpty(eLongOrderStatus)) {
									continue;
								}
								updateSize++;
								// lvmama订单状态 
								String lvmamaOrderStatus = null == mho.getStatus()?"":mho.getStatus();
								// 如果状体不一致 ,则更新驴妈妈状态 
								if(!eLongOrderStatus.equals(lvmamaOrderStatus)) {
									mho.setStatus(eLongOrderStatus);
									mobileHotelService.updateMobileHotelOrder(mho);
									logger.info("......艺龙酒店订单状态修改.....from.."+lvmamaOrderStatus+"..to.."+eLongOrderStatus);
									saveHotelCommLog("ORDER_STATUS",mho.getOrderId(),"SYSTEM",Constant.ELONG_ORDER_LOG_TYPE.UPDATE_ORDER_STATUS.getCode(),
											Constant.ELONG_ORDER_LOG_TYPE.UPDATE_ORDER_STATUS.getCnName(),"订单状态从 ["+Constant.ELONG_ORDER_STATUS.getCnName(lvmamaOrderStatus)+"("+lvmamaOrderStatus+")]--> [" 
													+ Constant.ELONG_ORDER_STATUS.getCnName(eLongOrderStatus) +"("+eLongOrderStatus+")]",eLongOrderStatus);
									
									// 返现 逻辑 只有状态为C（已结帐）的返现，且是否返现状态不为Y； 
									// V4.1.0 版本 酒店奖金返现的截至暂时定为： YYYY-MM-DD 为 2014年3月31号。如果28号预定 下个月5号好入住的话 也会返现的。  
									if(System.currentTimeMillis() > this.getDateTime(REFUND_START_DATE) 
											&& System.currentTimeMillis() < this.getDateTime("2014-04-06") ) { //&& System.currentTimeMillis() > this.getDateTime("2013-12-12")) {
										initRefund(mho,REFUND_START_DATE);
									}
								}
							}
						}catch(Exception e) {
						  e.printStackTrace();	
						  logger.info("......updateOrderStatus  error.....1111......");
						}
					}
				}
				logger.info("......updateOrderStatus  success.....size......"+updateSize);
			}
		}
	}
	
	/**
	 * 初始化返现  
	 * @param mho
	 */
	public void initRefund(MobileHotelOrder mho,String REFUND_START_DATE) {
		// C（已结帐）F(已入住) 并且到下单日期截止到 REFUND_END_DATE (2014-02-20)
		try {
			if(null != mho
					&& !"Y".equals(mho.getIsRefund()) 
					&& mho.getTotalPrice() > 0 
					&& (mho.getStatus().equals(Constant.ELONG_ORDER_STATUS.C.getCode()) ||mho.getStatus().equals(Constant.ELONG_ORDER_STATUS.F.getCode()) )
					&& null != mho.getCreateTime() 
					&& mho.getCreateTime().getTime() < this.getDateTime(REFUND_END_DATE) // 
					&& mho.getCreateTime().getTime() > this.getDateTime(REFUND_START_DATE)) {
				//更加userNo获取userId; 
				UserUser user = userUserProxy.getUserUserByUserNo(mho.getUserId());
				if(null != user && null != user.getId()) {
					mho.setIsRefund("Y");
					mho.setRefundAmount(refundLogic(mho.getTotalPrice(),mho.getQuantity()));
					// 更新订单已返现
					mobileHotelService.updateMobileHotelOrder4Job(mho);
					logger.info("....101..mobileHotelOrderLogJob...initRefund...updateMobileHotelOrder...orderId=" + mho.getRefundAmount() + "...totalPrice=" + mho.getRefundAmount());
					
					// 更新奖金账户
					cashAccountService.returnBonus4ElongOrder(mho, user.getId());
					logger.info("....102..mobileHotelOrderLogJob...initRefund...returnBonus4ElongOrder...");
					
					// 日志
					//产生业务唯一标示:来源+驴途订单（自己生产的order_id）id+艺龙订单id(艺龙生产的订单id)
					String protectId=Constant.BonusOperation.ELONG_ORDER_REFUND.name() + mho.getLvHotelOrderId() + mho.getOrderId();
					saveHotelCommLog("ORDER_REFUND",mho.getOrderId(),"SYSTEM2",Constant.ELONG_ORDER_LOG_TYPE.UPDATE_ORDER_REFUND.getCode(),
							Constant.ELONG_ORDER_LOG_TYPE.UPDATE_ORDER_REFUND.getCnName(),
							"艺龙酒店返现:orderId="+mho.getOrderId()+";totalPrice="+mho.getTotalPrice()+";refundAmount="+mho.getRefundAmount(),protectId);
				} else {
					logger.info("..1001..mobileHotelOrderLogJob...can not find user by userNo..userNo="+mho.getUserId() + "...orderId..." + mho.getOrderId());
				}
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.info("....1002..mobileHotelOrderLogJob...initRefund...error...");
		}
		
	}
	
	/**
	 * 酒店bugs修复
	 * 
	 */
	private void initRefundBug() {
		Long[] orderIds = {75389375l}; // 非lvOrderId ,对应艺龙订单的id  。 
		Map<String,Object> params = new HashMap<String,Object>();
		for(int i = 0; i < orderIds.length;i++) {
			try {
				params.put("orderId", orderIds[i]);
				List<MobileHotelOrder> orderList = mobileHotelService.queryMobileHotelOrderList(params);
				if(null != orderList && orderList.size() > 0) {
					MobileHotelOrder mho = orderList.get(0);
					// 如果订单存在 ，且状态是C ，且isRefund=‘Y’，且有效范围在2012-01-02 - 2012-02-21 ，返现金额为0的，重新返现 
					if(null != mho
							&& "Y".equals(mho.getIsRefund())  // 已经返现
							&& mho.getRefundAmount() < 1      // 返现金额小于0
							&& mho.getStatus().equals(Constant.ELONG_ORDER_STATUS.C.getCode())){ // 状态已经结算了。
						//更加userNo获取userId; 
						UserUser user = userUserProxy.getUserUserByUserNo(mho.getUserId());
						if(null != user && null != user.getId()) {
							mho.setIsRefund("Y");
							mho.setRefundAmount(refundLogic(mho.getTotalPrice(),mho.getQuantity()));
							// 更新订单已返现
							mobileHotelService.updateMobileHotelOrder4Job(mho);
							logger.info("....301.艺龙酒店返现修复.mobileHotelOrderLogJob...initRefund...updateMobileHotelOrder...orderId=" + mho.getOrderId() + "...totalPrice=" + mho.getRefundAmount());
							
							// lvOrderId 大于 1753
							Long lvHotelOrderId = mho.getLvHotelOrderId();
							// 因为返现唯一性校验问题，这里只要是修复的数据lvHotelOrderId暂定为1000;
							mho.setLvHotelOrderId(1000l);
							cashAccountService.returnBonus4ElongOrder(mho, user.getId());
							logger.info("....302.艺龙酒店返现修复.mobileHotelOrderLogJob...initRefund...lvHotelOrderId=..."+lvHotelOrderId);
							
							// 日志
							//产生业务唯一标示:来源+驴途订单（自己生产的order_id）id+艺龙订单id(艺龙生产的订单id)
							String protectId=Constant.BonusOperation.ELONG_ORDER_REFUND.name() + mho.getLvHotelOrderId() + mho.getOrderId();
							saveHotelCommLog("ORDER_REFUND",mho.getOrderId(),"SYSTEM3",Constant.ELONG_ORDER_LOG_TYPE.UPDATE_ORDER_REFUND.getCode(),
									Constant.ELONG_ORDER_LOG_TYPE.UPDATE_ORDER_REFUND.getCnName(),
									"艺龙酒店返现数据修复:orderId="+mho.getOrderId()+";totalPrice="+mho.getTotalPrice()+";refundAmount="+mho.getRefundAmount(),protectId);
						} else {
							logger.info("..3001.艺龙酒店返现修复.mobileHotelOrderLogJob...can not find user by userNo..userNo="+mho.getUserId() + "...orderId..." + mho.getOrderId());
						}
						
					}
				}
			}catch(Exception e) {
			     e.printStackTrace();	
			     logger.error("....304.艺龙酒店返现修复.mobileHotelOrderLogJob...error=...");
			}
			
		}
		
	}
	
	/**
	 * 返现金额计算逻辑. 
	 * @param totalPrice 订单总额 ，单位分；
	 * @return long;
	 */
	private Long refundLogic(Long totalPrice,Long quantity) {
		// 返现率
	/* Long refundRate = ClutterConstant.getMobileOrder4ElongMaxRefund();
	   if(isActivityHotel(hotelId,hotelIds)) {
		   // 活动酒店 发现率 ，酒店在const.properties文件中配置的。 
		   refundRate = ClutterConstant.getElongRefundMoreRates();
		   logger.error("...艺龙酒店返现..活动酒店返现比率...="+ refundRate);
	   }*/
	   Long refund = 0l;
	   if(null != totalPrice && totalPrice > 0 && null != quantity && quantity > 0) {
		       // 单个产品价格 
		       long per_price = totalPrice/quantity;
			   Long tlong =  per_price * ClutterConstant.getMobileOrder4ElongMaxRefund() / 100;  // 单位fen
			   float yuan = PriceUtil.convertToYuan(tlong);
			   Double d = Math.ceil(yuan);
			   refund = d.longValue() * quantity*100;
	   }
	   return refund;
	}
	
	/**
	 * 是否参加活动的酒店 
	 * @param hotelId
	 * @return
	 */
	public boolean isActivityHotel(String hotelId,Map<String,Object> hotelIds) {
		try {
			if(!StringUtils.isEmpty(hotelId) && null != hotelIds && !hotelIds.isEmpty()) {
				String str = String.valueOf(hotelIds.get(hotelId));
				if(!StringUtils.isEmpty(str) && !"null".equals(str)) {
					return true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("...获取艺龙酒店返现率错误.." );
		}
		return false;
	}
	
	/**
	 * 是否参加活动的酒店 
	 * @param hotelId
	 * @return
	 */
	public Map<String,Object> getActivityHotelIds() {
		Map<String,Object> hotelIdMap = new HashMap<String,Object> ();
		try {
			String hotelIds = ClutterConstant.getElongRefundMoreHotelIds();
			if(!StringUtils.isEmpty(hotelIds)) {
				String[] arrayIds = hotelIds.split(",");
				if(null != arrayIds && arrayIds.length > 0) {
					for(int i = 0 ;i <arrayIds.length;i++ ) {
						hotelIdMap.put(arrayIds[i], arrayIds[i]);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("...获取艺龙酒店返现率错误.." );
		}
		return hotelIdMap;
	}
	
	/**
	 * 获取订单状态. 
	 * @param orderId
	 * @param tag 是否查询订单状体 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String getOrderStatus(String orderId,int tag) {
		// 
		try{
			Thread.currentThread().sleep(500l);
			logger.info("....获取艺龙订单详情等待0.5秒...");
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("....获取艺龙订单详情等待0.5秒时出错了...");
		}
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orderId",orderId);
			Map<String,Object> resultMap = clientHotelService.orderDetail(param);
			if(null != resultMap && null != resultMap.get("orderDetailResult")  ) {
				Map<String,Object> orderMap = (Map<String,Object>)resultMap.get("orderDetailResult") ;
				if(null != orderMap && null != orderMap.get("statusCode")) {
					return orderMap.get("statusCode").toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(tag == 0) {
				logger.info("....获取订单状体失败...getOrderStatus  error.....23.....orderId=="  + orderId);
				return getOrderStatus(orderId,1);
			} else {
				logger.info("....获取订单状体第二次失败...getOrderStatus  error.....24.....orderId=="  + orderId);
			}
		}
		
		return "";
	}
	
	/**
	 * 保存操作日志.
	 * @param objectType
	 * @param objectId
	 * @param operatorName  System
	 * @param logType
	 * @param logName
	 * @param content
	 */
	private void saveHotelCommLog(final String objectType, final Long objectId,
			final String operatorName, final String logType, final String logName,final String content,String c_status) {
		try {
			final MobileHotelOrderLog log = new MobileHotelOrderLog();
			log.setObjectType(objectType);
			log.setObjectId(objectId);
			log.setOperatorName(operatorName);
			log.setLogType(logType);
			log.setLogName(logName);
			log.setContent(content);
			log.setCreateTime(new Date());
			log.setMemo(c_status);
			mobileHotelService.insertMobileHotelOrderLog(log);
			logger.info("....saveHotelCommLog  success.....id......"+log.getLogId());
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("....saveHotelCommLog  error.....33.....objectid..." +objectId );
		}
	}

	/**
	 * 返回当前时间 毫秒 
	 * @param date
	 * @return
	 */
	public long getDateTime(String date) {
		if(StringUtils.isEmpty(date)) {
			return 0l;
		}
		Date d = com.lvmama.clutter.utils.DateUtil.parseDate(date,"yyyy-MM-dd");
		return d.getTime();
	}
	
	public void setMobileHotelService(MobileHotelService mobileHotelService) {
		this.mobileHotelService = mobileHotelService;
	}
	
	public void setClientHotelService(IClientHotelService clientHotelService) {
		this.clientHotelService = clientHotelService;
	}
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

}
