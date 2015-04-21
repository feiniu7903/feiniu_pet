package com.lvmama.passport.processor.impl;

import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Date;

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
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.passport.youjianpingyao.model.GetBookListBean;
import com.lvmama.passport.youjianpingyao.model.HeaderBean;
import com.lvmama.passport.youjianpingyao.model.SaveOrderDetailBean;

/**
 * 又见平遥
 * 
 * @author xuweijun
 */
public class YouJianPingYaoProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(YouJianPingYaoProcessorImpl.class);
	
	private static String baseTemplateDir = "/com/lvmama/passport/youjianpingyao/template";
	private static String RSP_SUC = "0";
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private static enum ServiceName {
		SaveOrder, GetBookList, GetSaleList;
	}
	private static enum PayType {
		SCENE_PAYMENT(1, "景区现付"),
		ONLINE_PAYMENT(4, "在线支付"),
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
		ADD(1, "新增"),
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
	private static enum OrderStatus {
		NOT_ENTERED(0, "未入园"),
		ENTERED(1, "确定入园"),
		CANCELLED_BY_SCENE(8, "景区取消"),
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
	
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		Long startTime = 0L;
		log.info("Pingyao apply serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		try {
			SaveOrderDetailBean saveOrderDetailBean = this.fillSaveOrderDetailBean(passCode);
			String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "saveOrderDetailReq.xml", saveOrderDetailBean);
			log.info("Pingyao apply reqXml: " + reqXml);
			startTime = System.currentTimeMillis();
			String resXml = WebServiceClient.call(WebServiceConstant.getProperties("pingyao.url"), new Object[] {reqXml}, ServiceName.SaveOrder.name());
			log.info("Pingyao apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("Pingyao apply resXml: " + resXml);
			
			String rspCode = TemplateUtils.getElementValue(resXml, "//response/header/rspCode");
			String rspDesc = TemplateUtils.getElementValue(resXml, "//response/header/rspDesc");
			
			if (RSP_SUC.equalsIgnoreCase(rspCode)) {
				passport.setCode(saveOrderDetailBean.getIdentityCard());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setComLogContent("供应商返回异常："+rspDesc);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				this.reapplySet(passport, passCode.getReapplyCount());
				log.info("Pingyao apply fail message: " + rspCode + " " + rspCode);
			}
		} catch (Exception e) {
			log.error("Pingyao apply  serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Pingyao apply error message", e);
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
		return null;
	}

	/**
	 * 更新订单履行状态
	 */
	@Override
	public Passport perform(PassCode passCode) {
		log.info("Pingyao perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		try {
			GetBookListBean getBookListBean = this.fillGetBookListBean(passCode);
			String reqXml = TemplateUtils.fillFileTemplate(baseTemplateDir, "getBookListReq.xml", getBookListBean);
			log.info("Pingyao perform reqXml: " + reqXml);
			String resXml = WebServiceClient.call(WebServiceConstant.getProperties("pingyao.url"), new Object[] {reqXml}, ServiceName.GetBookList.name());
			log.info("Pingyao perform resXml: " + resXml);
			
			String rspCode = TemplateUtils.getElementValue(resXml, "//response/header/rspCode");
			String rspDesc = TemplateUtils.getElementValue(resXml, "//response/header/rspDesc");
			
			if (RSP_SUC.equalsIgnoreCase(rspCode)) {
				int orderStatus = Integer.parseInt(TemplateUtils.getElementValue(resXml, "//response/body/BookList/Bookings/orderStatus"));
				orderStatus = OrderStatus.ENTERED.getValue();
				if (orderStatus == OrderStatus.ENTERED.getValue()) {//已入园
					passport = new Passport();
					passport.setChild("0");
					passport.setAdult("0");
					passport.setUsedDate(new Date());
					passport.setDeviceId("Pingyao");
				}
			} else {
				log.info("Pingyao perform fail message: " + rspCode + " " + rspDesc);
			}
		} catch(Exception e) {
			log.error("Pingyao perform error message", e);
		}
		return passport;
	}
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	private SaveOrderDetailBean fillSaveOrderDetailBean(PassCode passCode) throws Exception {
		String serialNo = passCode.getSerialNo();
		//根据orderId或者整个order信息
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		//modify by tangjing 20140224 将获取有手机的游玩人或联系人信息改成获取联系人/取票人
		//凭身份证取票时会有取票人带了身份证而游客没带时，没法取票的情况
		OrdPerson ordperson = ordorder.getContact();
		//获取与passcode对应的订单产品Item
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		//供应商的产品ID(映射 门票项目ID)
		String productIdSupplier = itemMeta.getProductIdSupplier();
		if(productIdSupplier == null || "".equals(productIdSupplier)){
			throw new IllegalArgumentException("供应商产品ID映射的门票项目ID为空!");
		}
		//供应商的产品类型(映射 剧目场次)
		String productTypeSupplier = itemMeta.getProductTypeSupplier();
		if(productTypeSupplier == null || "".equals(productTypeSupplier)){
			throw new IllegalArgumentException("供应商产品ID映射的门票项目ID为空!");
		}
		
		int payType = PayType.ONLINE_PAYMENT.getValue();
		if (ordorder.isPayToSupplier()) {
			payType = PayType.SCENE_PAYMENT.getValue();
		}
		long bookNum = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		float bookAmount = itemMeta.getSettlementPriceYuan() * bookNum;
		
		SaveOrderDetailBean saveOrderDetailBean = new SaveOrderDetailBean();
		this.fillHeaderBean(saveOrderDetailBean, ServiceName.SaveOrder.name());
		saveOrderDetailBean.setSerialId(serialNo);
		saveOrderDetailBean.setShowDate(DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd"));
		saveOrderDetailBean.setScreenNo(productTypeSupplier);
		saveOrderDetailBean.setTicketID(productIdSupplier);
		saveOrderDetailBean.setBookNum(String.valueOf(bookNum));
		saveOrderDetailBean.setBookPrice(String.valueOf(itemMeta.getSettlementPriceYuan()));
		saveOrderDetailBean.setBookAmount(df.format(bookAmount));
		saveOrderDetailBean.setPayType(String.valueOf(payType));
		saveOrderDetailBean.setLinkMan(ordperson.getName());
		saveOrderDetailBean.setLinkPhone(ordperson.getTel());
		saveOrderDetailBean.setLinkMobile(ordperson.getMobile());
		saveOrderDetailBean.setIdentityCard(ordperson.getCertNo());
		saveOrderDetailBean.setOperationMode(String.valueOf(OperationMode.ADD.getValue()));
		saveOrderDetailBean.setTransCode(null);
		return saveOrderDetailBean;
	}

	private GetBookListBean fillGetBookListBean(PassCode passCode) throws NoSuchAlgorithmException {
		GetBookListBean getBookListBean = new GetBookListBean();
		this.fillHeaderBean(getBookListBean, ServiceName.GetBookList.name());
		
		//根据orderId或者整个order信息
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		//获取与passcode对应的订单产品Item
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(order, passCode);
		Date visitTime = itemMeta.getVisitTime();
		String vts = DateFormatUtils.format(visitTime, "yyyy-MM-dd");
		
		getBookListBean.setSerialId(passCode.getSerialNo());
		getBookListBean.setTransCode(null);
		getBookListBean.setDateFrom(vts + " 00:00:00");
		getBookListBean.setDateTo(vts + " 23:59:59");
		getBookListBean.setPage("1");
		getBookListBean.setPagesize("1");
		return getBookListBean;
	}
	
	private void fillHeaderBean(HeaderBean headerBean, String serviceName) throws NoSuchAlgorithmException {
		String reqTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		String accountID = WebServiceConstant.getProperties("pingyao.key");
		headerBean.setAccountID(accountID );
		headerBean.setServiceName(serviceName);
		headerBean.setDigitalSign(this.createSign(accountID, WebServiceConstant.getProperties("pingyao.password"), reqTime));
		headerBean.setReqTime(reqTime);
	}
	
	private String createSign(String accountID, String password, String reqTime) throws NoSuchAlgorithmException {
		return MD5.encode(accountID.toUpperCase() + MD5.encode(password).toUpperCase() + reqTime.toUpperCase()).toUpperCase();
	}
	
}
