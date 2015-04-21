package com.lvmama.passport.processor.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.client.lingnan.model.OrderLocator;
import com.lvmama.passport.processor.impl.client.lingnan.model.OrderRequestInfo;
import com.lvmama.passport.processor.impl.client.lingnan.model.OrderResponseInfo;
import com.lvmama.passport.processor.impl.client.lingnan.model.OrderSoap;
import com.lvmama.passport.processor.impl.util.OrderUtil;

/**
 * 智慧景区-岭南印象
 *
 * */
public class LingnanProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(LingnanProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private ComLogService comLogService = (ComLogService) SpringBeanProxy.getBean("comLogService");
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		Long startTime = 0L;
		try {
			OrderLocator locator=new OrderLocator();
			OrderSoap soap=locator.getOrderSoap();
			OrderRequestInfo order=buildOrderRequestInfo(passCode);
			printApplyOrderInfoLog(order.getContactName(),order.getMobile(),order.getTicketId(),order.getPlanGoDate(),order.getTicketNum());
			startTime  = System.currentTimeMillis();
			OrderResponseInfo result=soap.add(order);
			log.info("lingnan Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("lingnan apply submitOrderRes: " + result.getStatus()+"|入园号："+result.getCheckinCode()+"|对方订单号 ："+result.getOrderId());
			passport.setExtId(result.getOrderId().toString());
			passport.setCode(result.getCheckinCode());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
	
		} catch (Exception e) {
			log.error("lingnan Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("lingnan apply error message", e);
		}
		return passport;
	}
	
	/**
	 * 重新申码
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}
	
	/**
	 * 废码
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("lingnan destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		Long startTime = 0L;
		try {
			OrderLocator locator=new OrderLocator();
			OrderSoap soap=locator.getOrderSoap();
			startTime  = System.currentTimeMillis();
			OrderResponseInfo result=soap.cancel(passCode.getExtId(),"游客取消出游");
			log.info("lingnan destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			String status=result.getStatus().toString();
			if(StringUtils.equals(convertStatus_Order(status),"取消")){
			passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}
		} catch (Exception e) {
			log.error("lingnan destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("lingnan destroy error message", e);
		}
		return passport;
	}
	
	private static Set<PassCode> unusedList = new HashSet<PassCode>();
	private static Set<PassCode> usedList = new HashSet<PassCode>();
	/**
	 * 更新订单履行状态
	 */
	@Override
	public Passport perform(PassCode passCode) {
		log.info("lingnan perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				OrderLocator locator=new OrderLocator();
				OrderSoap soap=locator.getOrderSoap();
				OrderResponseInfo result=soap.getInfo(passCode.getExtId());
				String status=result.getStatus().toString();
				if (StringUtils.equals(convertStatus_Order(status),"已验票")) {
						passport = new Passport();
						passport.setChild("0");
						passport.setAdult("0");
						passport.setUsedDate(new Date());
						passport.setDeviceId("lingnan");
						stopCheckout(passCode);
				} else {
					stopCheckout(passCode);
					this.addComLog(passCode, result.toString(), "查询履行状态失败");
					log.info("lingnan perform fail message: " + result);
				}
			} catch(Exception e) {
				log.error("lingnan perform error message", e);
			}
		}
		return passport;
	}
	
	private boolean isPassCodeUnused(PassCode passCode) {
		if (!usedList.contains(passCode)) {
			unusedList.add(passCode);
			return true;
		}
		return false;
	}
	
	private boolean isNeedCheckout(PassCode passCode) {
		return "SUCCESS".equals(passCode.getStatus()) && isPassCodeUnused(passCode);
	}
	
	private void stopCheckout(PassCode passCode) {
		unusedList.remove(passCode);
		usedList.add(passCode);
	}
	
	private OrderRequestInfo buildOrderRequestInfo(PassCode passCode) throws Exception {
		String serialNo = passCode.getSerialNo();
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson =OrderUtil.init().getContract(ordorder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		String productIdSupplier = itemMeta.getProductIdSupplier();
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		String requestTimeStr = formatter.format(itemMeta.getVisitTime());
		OrderRequestInfo submitOrderBean = new OrderRequestInfo();
		submitOrderBean.setTicketId(Integer.valueOf(productIdSupplier.trim()));
		submitOrderBean.setContactName(ordperson.getName());
		submitOrderBean.setTicketNum(Integer.valueOf(String.valueOf(count)));
		submitOrderBean.setPlanGoDate(requestTimeStr);
		submitOrderBean.setMobile(ordperson.getMobile());
		submitOrderBean.setMemberOrderId(serialNo);
		return submitOrderBean;
	}
	
	public static String convertStatus_Order(String code_id){
		Map<String,String> codeMap=new HashMap<String, String>();
		codeMap.put("10", "待处理");
		codeMap.put("20", "确认中");
		codeMap.put("30", "已确认");
		codeMap.put("40", "已收款");
		codeMap.put("50", "已预留");
		codeMap.put("60", "出票");
		codeMap.put("70", "取消");
		codeMap.put("80", "已验票");
		codeMap.put("90", "核算");
		codeMap.put("100", "结算");
		return codeMap.get(code_id);
	}
	
	private void printApplyOrderInfoLog(String name,String mobile,int productId,String planGoDate,int ticknum){
		log.info("Lingnan ApplyCode productId: " + productId);
		log.info("Lingnan ApplyCode name: " + name);
		log.info("Lingnan ApplyCode mobile: " + mobile);
		log.info("Lingnan ApplyCode visitTime: " + planGoDate);
		log.info("Lingnan ApplyCode ticknum: " + ticknum);
	}
	
	private void addComLog(PassCode passCode, String logContent, String logName) {
		ComLog log = new ComLog();
		log.setObjectType("PASS_CODE");
		log.setParentId(passCode.getOrderId());
		log.setObjectId(passCode.getCodeId());
		log.setOperatorName("SYSTEM");
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
		log.setLogName(logName);
		log.setContent(logContent);
		comLogService.addComLog(log);
	}
}
