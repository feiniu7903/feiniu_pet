package com.lvmama.pet.job.mobile.hotel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonParser;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrder;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrderLog;
import com.lvmama.comm.pet.service.mobile.MobileHotelService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 艺龙订单状体修改 -改定时任务已经废除，已移动到super_clutter中. 
 * @author qinzubo
 *
 */
public class MobileHotelOrderLogJob {
    
	private final Logger logger = Logger.getLogger(this.getClass());
	// 常量 
	private static String ELONG_ORDER_DETAIL_URL = "http://m.lvmama.com/clutter/router/rest.do?method=api.com.hotel.orderDetail";
	@Autowired
	private MobileHotelService mobileHotelService;
	
	/**
	 * 定时方法. 
	 */
	public void updateOrderStatus() {
		if (Constant.getInstance().isJobRunnable()) {
			logger.info("......updateOrderStatus  start.....10......");
			int updateSize = 0;
			// 查询最近两个的订单
			Date date = new Date();
			// 减去30天 
			Date arrivaldateStart = DateUtil.dsDay_Date(date,-30);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("arrivaldateStart", arrivaldateStart);
			// 订单总数 
			Long orderCount = mobileHotelService.countMobileHotelOrderList(params);
			// 每次最多只能查询1000条数据 
			long size = orderCount/1000 ;
			if(orderCount%1000 != 0) {
				size++;
			}
			Page p = new Page(1000, 1);
			for(long i = 1; i <= size ;i++) {
				p.setPage(i);
				params.put("isPaging", "true"); // 是否使用分页
				params.put("startRows", p.getStartRows());
				params.put("endRows", p.getEndRows());
				List<MobileHotelOrder> orderList = mobileHotelService.queryMobileHotelOrderList(params);
				
				if(null != orderList && orderList.size() > 0) {
					for(int j = 0 ; j < orderList.size();j++) {
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
								saveHotelCommLog("ORDER_STATUS",mho.getOrderId(),"SYSTEM","UPDATE_ORDER_STATUS",
										"更新订单状体","订单状态从 ["+Constant.ELONG_ORDER_STATUS.getCnName(lvmamaOrderStatus)+"("+lvmamaOrderStatus+")]--> [" + Constant.ELONG_ORDER_STATUS.getCnName(eLongOrderStatus) +"("+eLongOrderStatus+")]");
							}
						}
					}
				}
				logger.info("......updateOrderStatus  success.....size......"+updateSize);
			}
		}
		
	}
	
	/**
	 * 获取订单状态. 
	 * @param orderId
	 * @param tag 是否查询订单状体 
	 * @return
	 */
	public String getOrderStatus(String orderId,int tag) {
		String url = ELONG_ORDER_DETAIL_URL+"&orderId="+orderId;
		try {
			String jsons = HttpsUtil.requestGet(url);
			JsonParser jsonparer = new JsonParser();//初始化解析json格式的对象
			String status = jsonparer.parse(jsons).getAsJsonObject().get("data").getAsJsonObject().get("orderDetailResult").getAsJsonObject().get("statusCode").getAsString();
			logger.info("......getOrderStatus  success.....21......orderid="+orderId+"..status="+status);
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			if(tag == 0) {
				logger.info("....第一次获取订单状体失败...getOrderStatus  error.....22......url=="+url);
				return getOrderStatus(orderId,1);
			}
			logger.info("....第二次获取订单状体失败...getOrderStatus  error.....23......url=="+url);
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
			final String operatorName, final String logType, final String logName,final String content) {
		try {
			final MobileHotelOrderLog log = new MobileHotelOrderLog();
			log.setObjectType(objectType);
			log.setObjectId(objectId);
			log.setOperatorName(operatorName);
			log.setLogType(logType);
			log.setLogName(logName);
			log.setContent(content);
			log.setCreateTime(new Date());
			mobileHotelService.insertMobileHotelOrderLog(log);
			logger.error("....saveHotelCommLog  success.....id......"+log.getLogId());
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("....saveHotelCommLog  error.....33......");
		}
	}

	public void setMobileHotelService(MobileHotelService mobileHotelService) {
		this.mobileHotelService = mobileHotelService;
	}
	

}
