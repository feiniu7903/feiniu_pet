package com.lvmama.passport.processor.impl;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.client.wuxiloch.model.Order;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 *  无锡太湖鼋头渚服务商接口
 *  
 * @author haofeifei
 *
 */
public class WuXiLochYuanTouZhuProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor,OrderPerformProcessor{

	// 日志工具对象
	private static final Log log = LogFactory.getLog(WuXiLochYuanTouZhuProcessorImpl.class);
	// 订单服务接口
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	/** 在线支付*/
	private static String PAYMENT_ID = "4";
	private ComLogService comLogService  = (ComLogService) SpringBeanProxy.getBean("comLogService");
	/**
	 * 创建订单---申请二维码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("WuXiLochYuanTouZhu Apply Code:" + passCode.getSerialNo());
		// 通关系统数据参数对象
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		// 取出信息并保存
		Long startTime = 0L;
		try {
			// 发送申码请求
			String result = this.doApplyOrder(passCode);
			log.info("Apply result:" +result);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				//网络延迟导致申码失败
				String codeStr = doQueryOrder(passCode);
				String order_status=TemplateUtils.getElementValue(codeStr,"//div/atwuxi_terminal_get_response/order_status");//订单状态
				String code = TemplateUtils.getElementValue(codeStr,"//div/atwuxi_terminal_get_response/code");//取票码
				String result_code = TemplateUtils.getElementValue(codeStr, "//div/atwuxi_terminal_get_response/result_code");//返回异常码
				if (StringUtils.equals(order_status, "0")) {
					passport.setCode(code);
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());//状态
				} else {
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setComLogContent("供应商返回异常：" + convertErrorCode_Order(result_code));
					this.reapplySet(passport, passCode.getReapplyCount(), "FAILED");
					log.info("WuXiLochYuanTouZhu Apply returned Status Error message:" + convertErrorCode_Order(result_code));
				}
			} else {
				String ticket_code = TemplateUtils.getElementValue(result, "//div/atwuxi_terminal_get_response/ticket_code");
				String result_code = TemplateUtils.getElementValue(result, "//div/atwuxi_terminal_get_response/result_code");
				if (StringUtils.equals(result_code, "101")) {
					passport.setCode(ticket_code);
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());//状态
				} else {
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setComLogContent("供应商返回异常：" + convertErrorCode_Order(result_code));
					this.reapplySet(passport, passCode.getReapplyCount(), "FAILED");
					log.info("WuXiLochYuanTouZhu Apply returned Status Error message:" + convertErrorCode_Order(result_code));
				}
			}
		} catch (Exception e) {
			log.error("WuXiLochYuanTouZhu Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(e.toString());
			this.reapplySet(passport, passCode.getReapplyCount(), "FAILED");
			log.error("WuXiLochYuanTouZhu Apply Error message:", e);
		}
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		passport.setSendOrderid(true);
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		return passport;
	}

	/**
	 * 退订单---废除二维码
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("HangZhouZoom Destoy Code :" + passCode.getSerialNo());
		// 通关系统数据参数对象(保存传回来的数据并记录)
		Passport passport = new Passport();
		// 发送请求信息类
		Long startTime = 0L;
		try {
			// 发送废单请求
			startTime = System.currentTimeMillis();
			String result = this.doDestroyOrder(passCode);
			log.info("WuXiLochYuanTouZhu destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("WuXiLochYuanTouZhu Destoy Code Response:"+result);
			
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
				passport.setComLogContent("供应商返回异常：" + result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			} else {
				// 解析废单请求返回的信息 0 失败 1 成功 orderId 订单号
				String result_code = TemplateUtils.getElementValue(result, "//div/atwuxi_terminal_get_response/result_code");
				if (StringUtils.equals(result_code, "100")) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());// 设置状态（成功）	
				}else{
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
					passport.setComLogContent("供应商返回异常：" + convertErrorCode_Order(result_code));
					log.info("WuXiLochYuanTouZhuClient Destoy Error, message:" + convertErrorCode_Order(result_code));
				}
			}
		} catch (Exception e) {
			log.error("WuXiLochYuanTouZhuClient destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());// 设置状态（失败）
			passport.setComLogContent(e.toString());
			log.error("WuXiLochYuanTouZhuClient Destoy Error, message:", e);
		}
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		return passport;
	}

	
	/**
	 * 重新申请二维码
	 */
	private void reapplySet(Passport passport, long times, String error) {
		OrderUtil.init().reapplySet(passport, times);
	}

	/**
	 * 更新履行状态
	 */
	@Override
	public Passport perform(PassCode passCode) {
		log.info("WuXiLochYuanTouZhu wisdom perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				String result=doQueryOrder(passCode);
				if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
					log.info(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				}else{
					log.info("WuXiLochYuanTouZhu perform resXml: " + result);
					String order_status = TemplateUtils.getElementValue(result,"//div/atwuxi_terminal_get_response/order_status");
					String return_num = TemplateUtils.getElementValue(result,"//div/atwuxi_terminal_get_response/return_num");
						if (StringUtils.equals(order_status, "1")) {//交易成功，已全部入园
							passport = new Passport();
							passport.setChild("0");//儿童数
							passport.setAdult("0");//成人数
							passport.setUsedDate(new Date());//离线刷码时间
							passport.setDeviceId("wuXiLochYuanTouZhu");//设备号
							stopCheckout(passCode);
						}else if(StringUtils.equals(order_status, "4")){//部分入园
							passport = new Passport();
							passport.setPartPerform(true);
							passport.setChild("0");//儿童数
							passport.setAdult("0");//成人数
							passport.setUsedDate(new Date());//离线刷码时间
							passport.setDeviceId("wuXiLochYuanTouZhu");//设备号
							this.passLogs(passCode.getOrderId()," 已退票数量："+return_num, passCode.getCodeId());
							stopCheckout(passCode);
						}
						
				}
			} catch(Exception e) {
				log.error("WuXiLochYuanTouZhu perform error message", e);
			}
		}
		return passport;
	}
	/**
	 * 通关日志
	 * @param orderId
	 * @param content
	 * @param codeId
	 */
	private void passLogs(Long orderId,String content,Long codeId){
		ComLog log = new ComLog();
		log.setObjectType("PASS_CODE");
		log.setParentId(orderId);
		log.setObjectId(codeId);
		log.setOperatorName("SYSTEM");
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
		log.setLogName("设备刷码通关");
		log.setContent(content);
		comLogService.addComLog(log);
	}
	/**
	 * 从供应商创建订单
	 * 
	 * @param passCode
	 * @return
	 */
	private String doApplyOrder(PassCode passCode) throws Exception {
		Map<String,String> requestMap = new HashMap<String, String>();
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordPerson =OrderUtil.init().getContract(ordOrder);
		/** 售价 */
		float price = 0.0f;
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
		for (OrdOrderItemProd itemProd : ordOrder.getOrdOrderItemProds()) {
			if (itemProd.getOrderItemProdId().longValue()==itemMeta.getOrderItemId()) {
				price=itemProd.getPriceYuan();
				break;
			}
		}
		/**购买数量 */
		Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		/** 结算价*/
		Float cost = itemMeta.getSettlementPriceToYuan();
		String productIdSupplier = itemMeta.getProductIdSupplier(); // 采购产品 ——类别——代理产品编号
		String productTypeSupplier=itemMeta.getProductTypeSupplier();//代理产品类型
		String visiteTime = DateFormatUtils.format(ordOrder.getVisitTime(),"yyyy-MM-dd");
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		Date useDate = DateUtil.toDate(visiteTime, "yyyy-MM-dd");
		Order ord=new Order();
		ord.setExp_time(useDate.getTime()); //门票使用时间，用来判断能不能取票 、有没有过期
		ord.setNum(Integer.valueOf(count.toString()));
		ord.setCost(Double.valueOf(cost));
		ord.setGoods_id(Integer.valueOf(productTypeSupplier));//产品编号
		ord.setSpec_id(Integer.valueOf(productIdSupplier));//成人票儿童票类型
		
		//支付给Lvmama
		if(ordOrder.isPayToLvmama()){
			ord.setPrice(Double.valueOf(price));
		}else{//景区支付支付给供应商
			ord.setPrice(0d);
		}
		String phone = ordPerson.getMobile(); 
		String orderSn = passCode.getSerialNo();
		String action="createOrder";
		String key=WebServiceConstant.getProperties("wuxiloch.key");
		requestMap.put("phone", phone);
		requestMap.put("exp_time", String.valueOf(useDate.getTime()));
		requestMap.put("paymentid", PAYMENT_ID);
		requestMap.put("orderSn",orderSn);
		requestMap.put("action", "createOrder");
		String ordStr ="[" +JsonUtil.getJsonString4JavaPOJO(ord)+"]";
		requestMap.put("order", ordStr);
		String keyStr=MD5.encode(key+action+orderSn);
		//requestMap.put("key", keyStr);
		requestMap.put("key", keyStr);
		return request(requestMap);
	}
	
	/**
	 * 从供应商取消订单
	 * @param passCode
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	private String doDestroyOrder(PassCode passCode) throws Exception {
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordPerson =OrderUtil.init().getContract(ordOrder);
		String phone = ordPerson.getMobile(); 
		String orderSn = passCode.getSerialNo(); 
		String key=WebServiceConstant.getProperties("wuxiloch.key");
		String action="cancelOrder";
		Map<String,String> requestMap = new HashMap<String, String>();
		requestMap.put("phone", phone);
		requestMap.put("orderSn",orderSn);
		requestMap.put("action", "cancelOrder");
		String keyStr=MD5.encode(key+action+orderSn);
		requestMap.put("key", keyStr);
		return request(requestMap);
	}
	
	
	/**
	 * 更新履行状态
	 * @param passCode
	 * @return
	 * @throws Exception 
	 */
	private String doQueryOrder(PassCode passCode) throws Exception{
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordPerson =OrderUtil.init().getContract(ordOrder);
		String phone = ordPerson.getMobile(); 
		String orderSn = passCode.getSerialNo(); 
		String key=WebServiceConstant.getProperties("wuxiloch.key");
		String action="queryOrder";
		Map<String,String> requestMap = new HashMap<String, String>();
		requestMap.put("phone", phone);
		requestMap.put("orderSn", orderSn);
		requestMap.put("action", "queryOrder");
		String keyStr=MD5.encode(key+action+orderSn);
		requestMap.put("key", keyStr);
		//requestMap.put("key", key);
		return request(requestMap);
	}
	private String request(Map<String,String> requestMap){
		requestMap.put("rstType","2");
		requestMap.put("xmlType","2");
		requestMap.put("ota_check_num",WebServiceConstant.getProperties("wuxiloch.ota_check_num"));
		requestMap.put("OTAcode", WebServiceConstant.getProperties("wuxiloch.OTAcode"));
		String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("wuxiloch.url"), requestMap);
		return result;
	}

	
	private static Set<PassCode> unusedList = new HashSet<PassCode>();
	private static Set<PassCode> usedList = new HashSet<PassCode>();
	
	private boolean isNeedCheckout(PassCode passCode) {
		return "SUCCESS".equals(passCode.getStatus()) && isPassCodeUnused(passCode);
	}
	private boolean isPassCodeUnused(PassCode passCode) {
		if (!usedList.contains(passCode)) {
			unusedList.add(passCode);
			return true;
		}
		return false;
	}
	private void stopCheckout(PassCode passCode) {
		unusedList.remove(passCode);
		usedList.add(passCode);
	}
	
	private static String convertErrorCode_Order(String code_id){
		Map<String,String> codeMap=new HashMap<String, String>();
		codeMap.put("002", "交易成功，无法退票");
		codeMap.put("003", "已经退票，无法退票");
		codeMap.put("004", "已经取票，并且部分退票，无法退票");
		codeMap.put("006", "使用日期是今天或者是今天之前，不能退");
		codeMap.put("100", "退票成功");
		codeMap.put("005", "数据异常订单号未查到对应票信息");
		codeMap.put("001", "传入参数有错");
		codeMap.put("009", "ota参数错误");
		codeMap.put("-1", "已退票");
		codeMap.put("0", "待取票");
		codeMap.put("1", "交易成功");
		codeMap.put("2", "禁用");
		codeMap.put("3", "已过期");
		codeMap.put("4", "部分退款");
		return codeMap.get(code_id);
	}
	
}
