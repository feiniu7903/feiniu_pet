package com.lvmama.passport.processor.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 北京欢乐谷
 * 
 * @author yanzhirong
 * 
 */
public class BeijingHLGProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(BeijingHLGProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private static Map<String, String> errorMap = new HashMap<String, String>();

	private static String ORDER_TYPE = "1";
	private static String TICKET_TYPE = "1";//1：电子票，2：实体票
	private static String SUCCESS = "1";// 成功返回值

	private static enum OrderStatus {
		REFUND_SUCCESS("5", "退款成功"),
		REFUND_IN("4", "申请退款中"),
		VALIDATED("3", "已验证"),
		ACCEPTED("2", "已受理"),
		PAYED("1", "已付款"),
		;

		private String value;
		private String name;

		private OrderStatus(String value, String name) {
			this.value = value;
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	private static String getErrorMessage(String errorCode) {
		if (errorMap.isEmpty()) {
			errorMap.put("-1", "下单失败");
			errorMap.put("-2", "超过单笔上线");
			errorMap.put("-3", "超过单日上线");
			errorMap.put("-4", "超过总数上线");
			errorMap.put("-5", "余额不足");
			errorMap.put("-6", "景点编号为空或者无效");
			errorMap.put("-7", "门票编号为空或者无效");
			errorMap.put("-8", "游玩人手机号码位数无效或者为空");
			errorMap.put("-9", "游玩人身份证号码为空或者位数无效");
			errorMap.put("-10", "预定数量为空或者小于0");
			errorMap.put("-11", "预定时间格式无效，或者为空小于今天");
			errorMap.put("-12", "游玩人姓名为空");
			errorMap.put("-13", "订单类型无效或者为空");
			errorMap.put("-14", "超过有效期预定时间");
			errorMap.put("-15", "用户名为空");
			errorMap.put("-16", "密码为空");
			errorMap.put("-17", "用户名或密码错误");
			errorMap.put("-18", "地址没有填写（仅当 type 为 2实体票的时候）");
			errorMap.put("-19", "该门票下的 type 和您传入的 type 不一致");
			errorMap.put("-20", "email 的格式不正确");
			errorMap.put("-21", "动作（ action ）为空");
			errorMap.put("-22", "动作（ action ）不正确");
			errorMap.put("-23", "订单号为空");
			errorMap.put("-24", "订单号不存在");
			errorMap.put("-25", "申请退款理由为空");
			errorMap.put("-26", "当前订单的状态不能申请退款");
			errorMap.put("-27", "外部订单编号为空");
			errorMap.put("-28", "景区 编号和门票不匹配 编号（该 门票不属于该景区）");
		}
		return errorMap.get(errorCode);
	}

	/**
	 * 申请码，下单
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Beijinghlg apply serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		Long startTime = 0L;
		try {
			Map<String, String> paramMap = this.fillOrderRequestMap(passCode);
			log.info("Beijinghlg apply http post form req data: " + paramMap);
			startTime  = System.currentTimeMillis();
			String responseCode = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("beijinghlg.url"), paramMap);
			log.info("Beijinghlg Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("Beijinghlg apply http post response code: " + responseCode);
			if (responseCode != null && SUCCESS.equals(responseCode.substring(0, 1))) {
				passport.setCode(paramMap.get("credentialsNumber"));
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				passport.setExtId(responseCode.substring(2));// 票管家的订单编号
			} else {
				passport.setComLogContent("供应商返回异常：" + getErrorMessage(responseCode));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("Beijinghlg apply fail message: " + responseCode);
			}
		} catch (Exception e) {
			log.error("Beijinghlg Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Beijinghlg apply error message", e);
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
	 * 废码，退单
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("Beijinghlg destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		Long startTime = 0L;
		try {
			Map<String, String> paramMap = this.fillCancelOrderRequestMap(passCode);
			log.info("Beijinghlg destroy http post form req data: " + paramMap);
			startTime  = System.currentTimeMillis();
			String responseCode = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("beijinghlg.url"), paramMap);
			log.info("Beijinghlg destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("Beijinghlg destroy http post response code: " + responseCode);
			if (responseCode != null && SUCCESS.equals(responseCode)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setComLogContent("供应商返回异常：" + getErrorMessage(responseCode));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("Beijinghlg destroy fail message: " + responseCode);
			}

		} catch (Exception e) {
			log.error("Beijinghlg destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Beijinghlg destroy error message", e);
		}

		return passport;
	}

	/**
	 * 更新订单履行状态
	 */
	@Override
	public Passport perform(PassCode passCode) {
		log.info("Beijinghlg perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		try {
			Map<String, String> paramMap = this.fillPerformRequestMap(passCode);
			log.info("Beijinghlg perform http post form req data: " + paramMap);
			String responseCode = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("beijinghlg.url"), paramMap);
			log.info("Beijinghlg perform http post response code: " + responseCode);
			if (Integer.parseInt(responseCode) > 0) {
				int orderStatus = Integer.parseInt(responseCode);
				if (orderStatus == Integer.parseInt(OrderStatus.PAYED.getValue())) {
					passport = new Passport();
					passport.setChild("0");
					passport.setAdult("0");
					passport.setUsedDate(new Date());
					passport.setDeviceId("Beijinghlg");
				}
			} else {
				log.info("Beijinghlg perform fail message: " + getErrorMessage(responseCode));
			}
		} catch (Exception e) {
			log.error("Beijinghlg perform error message", e);
		}
		return passport;
	}

	/**
	 * 封装发送请求的下单map对象
	 */
	private Map<String, String> fillOrderRequestMap(PassCode passCode) throws NoSuchAlgorithmException {
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordPerson = OrderUtil.init().getContract(ordOrder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("scenicid", itemMeta.getProductTypeSupplier());// 景区ID
		paramMap.put("ticketid", itemMeta.getProductIdSupplier());// 门票ID
		paramMap.put("password", WebServiceConstant.getProperties("beijinghlg.passWord"));
		paramMap.put("username", WebServiceConstant.getProperties("beijinghlg.userName"));
		paramMap.put("orderType", ORDER_TYPE);
		paramMap.put("phoneNumber", ordPerson.getMobile());
		paramMap.put("credentialsNumber", ordPerson.getCertNo());
		paramMap.put("totalAmount", String.valueOf(itemMeta.getProductQuantity() * itemMeta.getQuantity()));// 预订数量
		paramMap.put("orderTime", DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd"));//游玩日期
		paramMap.put("name", ordPerson.getName());
		paramMap.put("type", TICKET_TYPE);
		paramMap.put("email", ordPerson.getEmail());
		paramMap.put("action", "add");
		paramMap.put("otherOrderNumber", passCode.getSerialNo());// 传入订单号(流水号)
		return paramMap;
	}

	/**
	 * 封装发送请求的申请退款map对象
	 */
	private Map<String, String> fillCancelOrderRequestMap(PassCode passCode) throws NoSuchAlgorithmException {
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("password", WebServiceConstant.getProperties("beijinghlg.passWord"));
		paramMap.put("username", WebServiceConstant.getProperties("beijinghlg.userName"));
		paramMap.put("orderNumber", passCode.getExtId());
		paramMap.put("action", "refund");
		paramMap.put("reason", StringUtils.defaultString(ordOrder.getCancelReason(), "无"));
		return paramMap;
	}

	/**
	 * 
	 * 封装发送请求的履行map对象
	 */
	private Map<String, String> fillPerformRequestMap(PassCode passCode) throws NoSuchAlgorithmException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("password", WebServiceConstant.getProperties("beijinghlg.passWord"));
		paramMap.put("username", WebServiceConstant.getProperties("beijinghlg.userName"));
		paramMap.put("orderNumber", passCode.getExtId());
		paramMap.put("action", "status");
		return paramMap;
	}
}
