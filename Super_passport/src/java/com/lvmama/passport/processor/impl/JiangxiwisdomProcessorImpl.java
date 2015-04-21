package com.lvmama.passport.processor.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.client.jiangxiwisdom.JiangxiwisdomClient;
import com.lvmama.passport.processor.impl.client.jiangxiwisdom.model.Order;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 江西智慧
 * 
 * @author tangJing
 */
public class JiangxiwisdomProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(JiangxiwisdomProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private static final String SUCCESS="0000";
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Jiangxi wisdom apply serialNo: " + passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		try {
			Order order=this.buildOrder(passCode);
			String result = JiangxiwisdomClient.applyCodeRequest(order);
			startTime = System.currentTimeMillis();
			log.info("Jiangxi wisdom applay serialNo :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+ result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				log.info("Jiangxi wisdom applay resXml: " + result);
				List<String> msgSend = TemplateUtils.getElementValues(result,"//result/orderPass");
				if (msgSend.size() != 0) {
					String msg = msgSend.get(0);
					if (msg.length()>0) {
						String orderNo = TemplateUtils.getElementValue(result,"//result/orderNo");
						String orderPass = TemplateUtils.getElementValue(result, "//result/orderPass");
						passport.setExtId(orderNo);
						passport.setCode(orderPass);
						passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					} else {
						passport.setComLogContent(convertErrorCode_Order(msg));
						passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
						log.info("Jiangxi wisdom  apply fail message: "+ convertErrorCode_Order(msg));
						this.reapplySet(passport, passCode.getReapplyCount());
					}
				} else {
					String status = TemplateUtils.getElementValue(result,"//result");
					passport.setComLogContent("供应商返回异常："+ convertErrorCode_Order(status));
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					log.info("Jiangxi wisdom apply fail message: "+ convertErrorCode_Order(status));
				}
			}
		} catch (Exception e) {
			log.error("Jiangxi wisdom applay serialNo Error :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Jiangxi wisdom applay message", e);
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
		long startTime=0L;
		log.info("Jiangxi wisdom destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			String orderNo=passCode.getSerialNo();
			String action="";
			//预付款客户 是 退回 景区现付的订单  就是使用关闭
			if(ordOrder.isPayToLvmama()){
				action="2"; //2 订单退回
			}else{
				action="3"; //3 订单关闭
			}
			startTime=System.currentTimeMillis();
			String result =JiangxiwisdomClient.orderRequest(action,orderNo);
			log.info("Jiangxi wisdom destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常："+result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			}else{
				log.info("Jiangxi wisdom destroy resXml: " + result);
				String status = TemplateUtils.getElementValue(result, "//result");			
				if (StringUtils.equals(status, SUCCESS)) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					passport.setComLogContent("供应商返回异常："+convertErrorCode_Order(status));
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Jiangxi wisdom destroy fail message: " +convertErrorCode_Order(status));
				}
			}
		} catch (Exception e) {
			log.error("Jiangxi wisdom destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Jiangxi wisdom destroy error message", e);
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
		log.info("Jiangxi wisdom perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				String orderNo=passCode.getSerialNo();
				String result =JiangxiwisdomClient.orderRequest("5", orderNo);//1订单提交 2订单退 3订单关闭  4订单恢复 5订单查询
				if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
					log.info(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				}else{
					log.info("Jiangxi wisdom perform resXml: " + result);
					List<String> status = TemplateUtils.getElementValues(result,"//OrderModel/Status");
					if (status.size()!=0) {
						if (StringUtils.equals(status.get(0), "已取票")) {//已入园
							passport = new Passport();
							passport.setChild("0");
							passport.setAdult("0");
							passport.setUsedDate(new Date());
							passport.setDeviceId("Jiangxi wisdom");
							stopCheckout(passCode);
						}
					}
				}
			} catch(Exception e) {
				log.error("Jiangxi wisdom perform error message", e);
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
	
	private Order buildOrder(PassCode passCode)throws Exception{
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdPerson ordPerson =OrderUtil.init().getContract(ordOrder);
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
			String productIdSupplier = itemMeta.getProductIdSupplier();
			String agentNo = WebServiceConstant.getProperties("jiangxiwisdom.agentNo");
			String visiteTime = DateFormatUtils.format(ordOrder.getVisitTime(),"yyyy-MM-dd");
			if (StringUtils.isBlank(productIdSupplier)) {
				throw new IllegalArgumentException("代理产品编号不能空");
			}
			String u8Name = new String(ordPerson.getName().getBytes(),"utf-8");
			Order order = new Order();
			order.setAction("1"); //1订单提交 2订单退 3订单关闭  4订单恢复 5订单查询
			order.setAgentNo(agentNo);
			order.setOrderNo(passCode.getSerialNo());
			order.setCustName(u8Name);
			order.setCustMobile(ordPerson.getMobile());
			order.setCustId(ordPerson.getCertNo());
			order.setTicketNo(productIdSupplier);
			order.setBuyNum(String.valueOf(count));
			if (ordOrder.isPayToLvmama()) {
				order.setIsDestine("0");
			} else {
				order.setIsDestine("1"); //0 或1，1为景区现付 默认值0
			}
			order.setArriveDate(visiteTime);
		return order;
	}
	
	public static String convertErrorCode_Order(String code_id){
		Map<String,String> codeMap=new HashMap<String, String>();
		codeMap.put("0000", "操作成功");
		codeMap.put("0001", "代理商不存在");
		codeMap.put("0002", "代理商被锁定");
		codeMap.put("0003", "操作超时,通讯时间超过120秒");
		codeMap.put("0004", "签名错误");
		codeMap.put("0005", "无效操作");
		codeMap.put("1000", "订单信息不存在");
		codeMap.put("1001", "通讯参数不完整");
		codeMap.put("1002", "流水账号重复");
		codeMap.put("1003", "票务信息不存在或被锁定");
		codeMap.put("1004", "票务信息库存不足");
		codeMap.put("1005", "订单不存在");
		codeMap.put("1006", "订单状态更改失败");
		codeMap.put("1007", "代理商短信发送,平台不进行发送");
		codeMap.put("1008", "短信发送失败");
		codeMap.put("1010", "到达时间有误");
		codeMap.put("9999", "服务器错误");
		return codeMap.get(code_id);
	}
}