package com.lvmama.passport.processor.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
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
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.lingshan.model.CancelOrderBean;
import com.lvmama.passport.lingshan.model.GetSaleListBean;
import com.lvmama.passport.lingshan.model.HeaderBean;
import com.lvmama.passport.lingshan.model.SaveOrderDetailBean;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 灵山大佛
 * 
 * @author qiuguobin
 */
public class LingshanProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(LingshanProcessorImpl.class);
	
	private static String baseTemplateDir = "/com/lvmama/passport/lingshan/template";
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private ComLogService comLogService = (ComLogService) SpringBeanProxy.getBean("comLogService");
	
	private static enum ServiceName {
		SaveOrderDetail, CancelOrder, GetSaleList;
	}
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Lingshan apply serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		Long startTime = 0L;
		try {
			SaveOrderDetailBean saveOrderDetailBean = this.fillSaveOrderDetailBean(passCode);
			String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "saveOrderDetailReq.xml", saveOrderDetailBean);
			log.info("Lingshan apply reqXml: " + reqXml);
			startTime = System.currentTimeMillis();
			String resXml = WebServiceClient.call(WebServiceConstant.getProperties("lingshan.url"), new Object[] {reqXml}, ServiceName.SaveOrderDetail.name());
			log.info("Lingshan Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("Lingshan apply resXml: " + resXml);
			
			String rspCode = TemplateUtils.getElementValue(resXml, "//response/header/rspCode");
			String rspDesc = TemplateUtils.getElementValue(resXml, "//response/header/rspDesc");
			
			if ("0".equalsIgnoreCase(rspCode)) {
				passport.setCode(saveOrderDetailBean.getIdentityCard());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setComLogContent("供应商返回异常："+rspDesc);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				this.reapplySet(passport, passCode.getReapplyCount());
				log.info("Lingshan apply fail message: " + rspCode + " " + rspCode);
			}
		} catch (Exception e) {
			log.error("Lingshan Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Lingshan apply error message", e);
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
		log.info("Lingshan destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		Long startTime = 0L;
		try {
			CancelOrderBean cancelOrderBean = this.fillCancelOrderBean(passCode);
			String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "cancelOrderReq.xml", cancelOrderBean);
			log.info("Lingshan destroy reqXml: " + reqXml);
			startTime = System.currentTimeMillis();
			String resXml = WebServiceClient.call(WebServiceConstant.getProperties("lingshan.url"), new Object[] {reqXml}, ServiceName.CancelOrder.name());
			log.info("Lingshan destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("Lingshan destroy resXml: " + resXml);
			
			String rspCode = TemplateUtils.getElementValue(resXml, "//response/header/rspCode");
			String rspDesc = TemplateUtils.getElementValue(resXml, "//response/header/rspDesc");
			
			if ("0".equalsIgnoreCase(rspCode)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setComLogContent("供应商返回异常："+rspDesc);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("Lingshan destroy fail message: " + rspCode + " " + rspDesc);
			}
		} catch (Exception e) {
			log.error("Lingshan destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Lingshan destroy error message", e);
		}
		return passport;
	}

	/*
	 * 对接方订单状态返回，目前以1-8为已入园
	 */
	private static enum OrderStatus {
		NOT_ENTERED(0, "未入园"),
		ENTERED_BY_TICKET(1, "取票入园"),
		ENTERED_DIRECTLY(2, "直接入园"),
		CANCELLED(9, "已取消作废"),
		;
		
		private int value;
		private String name;
		
		private OrderStatus(int value, String name) {
			this.value = value;
			this.name = name;
		}
		
		public int getValue() {
			return value;
		}
		
		public String getName() {
			return name;
		}
	}
	
	private static Set<PassCode> unusedList = new HashSet<PassCode>();
	private static Set<PassCode> usedList = new HashSet<PassCode>();
	/**
	 * 更新订单履行状态
	 */
	@Override
	public Passport perform(PassCode passCode) {
		log.info("Lingshan perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				GetSaleListBean getSaleListBean = this.fillGetSaleListBean(passCode);
				String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "getSaleListReq.xml", getSaleListBean);
				log.info("Lingshan perform reqXml: " + reqXml);
				String resXml = WebServiceClient.call(WebServiceConstant.getProperties("lingshan.url"), new Object[] {reqXml}, ServiceName.GetSaleList.name());
				log.info("Lingshan perform resXml: " + resXml);
				
				String rspCode = TemplateUtils.getElementValue(resXml, "//response/header/rspCode");
				String rspDesc = TemplateUtils.getElementValue(resXml, "//response/header/rspDesc");
				
				if ("0".equalsIgnoreCase(rspCode)) {
					int orderStatus = Integer.parseInt(TemplateUtils.getElementValue(resXml, "//response/body/SaleList/Sales/orderStatus"));
					/*
					 * 对接方如果返回1-8的订单状态，代表已经入园
					 * 0、9为未入园
					 */
					if (orderStatus != OrderStatus.NOT_ENTERED.getValue() 
							&& orderStatus != OrderStatus.CANCELLED.getValue()) {
						passport = new Passport();
						passport.setChild("0");
						passport.setAdult("0");
						passport.setUsedDate(new Date());
						passport.setDeviceId("Lingshan");
						stopCheckout(passCode);
					}
				} else {
					stopCheckout(passCode);
					this.addComLog(passCode, rspDesc, "查询履行状态失败");
					log.info("Lingshan perform fail message: " + rspCode + " " + rspDesc);
				}
			} catch(Exception e) {
				log.error("Lingshan perform error message", e);
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
	
	private static enum SettlementType {
		XJ(1, "现结"),
		JZ(2, "记账"),
		FH(3, "返还"),
		YF(4, "预付"),
		;
		
		private int value;
		private String name;
		
		private SettlementType(int value, String name) {
			this.value = value;
			this.name = name;
		}
		
		public int getValue() {
			return value;
		}
		
		public String getName() {
			return name;
		}
	}
	
	private static enum PayType {
		SCENE_PAYMENT(1, "景区支付"),
		ONLINE_PAYMENT(2, "在线支付"),
		;
		
		private int value;
		private String name;
		
		private PayType(int value, String name) {
			this.value = value;
			this.name = name;
		}
		
		public int getValue() {
			return value;
		}
		
		public String getName() {
			return name;
		}
	}
	
	private static enum OperationMode {
		CANCEL_ADD(1, "新增"),
		MODIFY(2, "修改"),
		;
		
		private int value;
		private String name;
		
		private OperationMode(int value, String name) {
			this.value = value;
			this.name = name;
		}
		
		public int getValue() {
			return value;
		}
		
		public String getName() {
			return name;
		}
	}
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	private SaveOrderDetailBean fillSaveOrderDetailBean(PassCode passCode) throws NoSuchAlgorithmException {
		String serialNo = passCode.getSerialNo();
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson =OrderUtil.init().getContract(ordorder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		String productIdSupplier = itemMeta.getProductIdSupplier();
		String productTypeSupplier=itemMeta.getProductTypeSupplier();
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		if (StringUtils.isBlank(productTypeSupplier)) {
			throw new IllegalArgumentException("代理产品类型不能空");
		}
		String[] values = productTypeSupplier.split(",");
		if (values.length != 2) {
			throw new IllegalArgumentException("代理产品类型应由\"网络售价,结算方式\"组成");
		}
		float webPrice = Float.valueOf(values[0]);
		String settlementType=values[1]; 
		Date playDate = itemMeta.getVisitTime();
		int payType = PayType.ONLINE_PAYMENT.getValue();
		if (ordorder.isPayToSupplier()) {
			payType = PayType.SCENE_PAYMENT.getValue();
		}
		long touristsCount = itemMeta.getProductQuantity()*itemMeta.getQuantity();
		float totalPrice = webPrice * touristsCount;
		
		SaveOrderDetailBean saveOrderDetailBean = new SaveOrderDetailBean();
		this.fillHeaderBean(saveOrderDetailBean, ServiceName.SaveOrderDetail.name());
		saveOrderDetailBean.setSerialId(serialNo);
		saveOrderDetailBean.setProductId(productIdSupplier.trim());
		saveOrderDetailBean.setProductName(productIdSupplier.trim());
		saveOrderDetailBean.setSettlementType(String.valueOf(settlementType));
		saveOrderDetailBean.setPayType(String.valueOf(payType));
		saveOrderDetailBean.setPlayDate(DateFormatUtils.format(playDate, "yyyy-MM-dd"));
		saveOrderDetailBean.setLinkMan(ordperson.getName());
		saveOrderDetailBean.setIdentityCard(ordperson.getCertNo());
		saveOrderDetailBean.setLinkPhone(null);
		saveOrderDetailBean.setLinkMobile(ordperson.getMobile());
		saveOrderDetailBean.setVerifyCode(null);
		saveOrderDetailBean.setTouristsCount(String.valueOf(touristsCount));
		saveOrderDetailBean.setWebPrice(df.format(webPrice));
		saveOrderDetailBean.setTotalAmount(df.format(totalPrice));
		saveOrderDetailBean.setOperationMode(String.valueOf(OperationMode.CANCEL_ADD.getValue()));
		return saveOrderDetailBean;
	}

	private CancelOrderBean fillCancelOrderBean(PassCode passCode) throws NoSuchAlgorithmException {
		CancelOrderBean cancelOrderBean = new CancelOrderBean();
		this.fillHeaderBean(cancelOrderBean, ServiceName.CancelOrder.name());
		cancelOrderBean.setSerialId(passCode.getSerialNo());
		return cancelOrderBean;
	}

	private GetSaleListBean fillGetSaleListBean(PassCode passCode) throws NoSuchAlgorithmException {
		GetSaleListBean getSaleListBean = new GetSaleListBean();
		this.fillHeaderBean(getSaleListBean, ServiceName.GetSaleList.name());
		getSaleListBean.setSerialId(passCode.getSerialNo());
		getSaleListBean.setPlayDate(null);
		getSaleListBean.setPage("1");
		getSaleListBean.setPageSize("1");
		return getSaleListBean;
	}
	
	private void fillHeaderBean(HeaderBean headerBean, String serviceName) throws NoSuchAlgorithmException {
		String reqTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String accountID = WebServiceConstant.getProperties("lingshan.accountID");
		headerBean.setAccountID(accountID );
		headerBean.setServiceName(serviceName);
		headerBean.setDigitalSign(this.createSign(accountID, WebServiceConstant.getProperties("lingshan.password"), reqTime));
		headerBean.setReqTime(reqTime);
	}
	
	private String createSign(String accountID, String password, String reqTime) throws NoSuchAlgorithmException {
		return InnerMD5.encode(accountID + InnerMD5.encode(password) + reqTime);
	}
	
	private static class InnerMD5 {
		private static String[] hexCode = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
		private static String byteToHexString(byte b) {
			int n = b;
			if (n < 0) {
				n = 256 + n;
			}
			int d1 = n / 16;
			int d2 = n % 16;
			return hexCode[d1] + hexCode[d2];
		}
		
		private static String byteArrayToHexString(byte[] b) {
			String result = "";
			for (int i = 0; i < b.length; i++) {
				result = result + byteToHexString(b[i]);
			}
			return result;
		}
		
		public static String encode(String s) throws NoSuchAlgorithmException {
			MessageDigest md = null;
			md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());
			return byteArrayToHexString(md.digest());
		}
	}
}
