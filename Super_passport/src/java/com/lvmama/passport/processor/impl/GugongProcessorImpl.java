package com.lvmama.passport.processor.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.client.gugong.GugongConstant;
import com.lvmama.passport.processor.impl.client.gugong.GugongHTTPUtil;
import com.lvmama.passport.processor.impl.client.gugong.GugongOrder;
import com.lvmama.passport.processor.impl.client.gugong.GugongOrderResponse;
import com.lvmama.passport.processor.impl.util.OrderUtil;
/**
 * 故宫对接--接口实现
 * 
 * @author zhangkexing
 */
public class GugongProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor {
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private PayPaymentService payPaymentService = (PayPaymentService) SpringBeanProxy.getBean("payPaymentService");
	private ComLogService comLogService = (ComLogService) SpringBeanProxy.getBean("comLogService");
	private static final Log log = LogFactory.getLog(GugongProcessorImpl.class);
	private String cardNum;

	@Override
	public Passport apply(PassCode passCode) {
		log.info("Gugong Apply Code: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		try {
			sendOrder(passCode, passport);
		} catch (Exception e) {
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(e.getMessage());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Gugong Apply Exception message:", e);
		}

		return passport;
	}

	/**
	 * 重新申请码处理
	 * 
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}

	@Override
	public Passport destroy(PassCode passCode) {
		log.info("Gugong Destroy Code: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		passport.setSerialno(passCode.getSerialNo());
		try {
			GugongOrderResponse response =GugongHTTPUtil.getRefundResponse(passCode);
			if(response.getStatus()==0){
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				passport.setComLogContent("供应商返回异常:"+GugongConstant.getInstance().getCodeMsg(response.getResultcode()));
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			}
		} catch (Exception e) {
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setComLogContent(e.getMessage());
			log.error("Gugong Destroy Exception:".concat(e.getMessage()));
		}
		return passport;
	}

	private Passport sendOrder(PassCode passCode, Passport passport) {
		String orderInfo = buildOrderInfo(passCode);
		log.info("GugongOrderRequest:"+orderInfo);
		GugongOrderResponse response = null;
		Long startTime = 0L;
		try {
			if(orderInfo.isEmpty()){
				throw new RuntimeException("由于未获取到支付信息，未生成订单信息");
			}
			startTime = System.currentTimeMillis();
			response = GugongHTTPUtil.sendOrder(orderInfo);
			log.info("Gugong Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
		} catch (ClientProtocolException e) {
			this.reapplySet(passport, passCode.getReapplyCount());
			passport.setComLogContent("故宫对接推单：地址" + GugongConstant.GUGONG_URLS.GUGONG_URLS_ORDER.getUrl() + " 客户端协议异常" + e);
			log.error(e);
		} catch (ParseException e) {
			this.reapplySet(passport, passCode.getReapplyCount());
			passport.setComLogContent("解析永乐方返回的推单结果：异常" + e);
			log.error(e);
		} catch (IOException e) {
			this.reapplySet(passport, passCode.getReapplyCount());
			passport.setComLogContent("故宫对接推单：地址" + GugongConstant.GUGONG_URLS.GUGONG_URLS_ORDER.getUrl() + " 网络异常" + e);
			log.error(e);
		}catch(Exception e){
			log.error("Gugong Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			this.reapplySet(passport, passCode.getReapplyCount());
			passport.setComLogContent(e.getMessage());
			log.error(e);
		}
		boolean flag=false;
		if (response!= null) {
			if (response.isSuccess()) {
				String ylorderId=response.getYlorderid();
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				passport.setSerialno(passCode.getSerialNo());
				passport.setAddCode(ylorderId);
				passport.setExtId(ylorderId);
				passport.setCode(cardNum);// 故宫产品code则存放证件号
				passport.setMessageWhenApplySuccess("");//清空状态描述信息
				flag=true;
			}
		}
		if(!flag){
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			// 如果推单失败原因是由永乐方业务代码不成功，而非系统异常，申码失败且不重申码
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			if(response!=null){
				passport.setComLogContent(GugongConstant.getInstance().getCodeMsg(response.getResultcode()));
			}else{
				passport.setComLogContent("供应商返回信息为空");
			}
		}
		return passport;
	}

	private String buildOrderInfo(PassCode passCode) {
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		// 取票人/联系人的证件号
		cardNum=ordorder.getContact().getCertNo();
		List<PayPayment> payList = payPaymentService.selectByObjectIdAndBizType(ordorder.getOrderId(), Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
		for(PayPayment payment : payList){
			if(payment.isSuccess()){
				GugongOrder gugongorder = new GugongOrder(payment, ordorder,passCode.getSerialNo());
				return gugongorder.getJSON();
			}
		}
		
		return null;
	}

	@Override
	public Passport perform(PassCode passCode) {
		log.info("Gugong perform serialNo: " + passCode.getSerialNo());
		log.info("Gugong getOrder: " + passCode.getSerialNo());

		Passport passport = null;
		GugongOrderResponse response = null;
		try {
			response = GugongHTTPUtil.getOrderStatus(passCode);
		} catch (Exception e) {
			log.error(e);
		} 
		if (response != null) {
			//永乐方已经出票，驴妈妈自动履行
			if (response.isSuccess() && response.getStatus()==2) {
				passport = new Passport();
				passport.setChild("0");
				passport.setAdult("0");
				passport.setUsedDate(new Date());
				passport.setDeviceId("Gugong");
			}else{
				String codeMsg = GugongConstant.getInstance().getOrderStatus(response.getStatus());
				log.error("Gugong auto perform failed: ".concat(codeMsg));
			}
		} else {
			this.addComLog(passCode, GugongConstant.getInstance().getCodeMsg(response.getResultcode()), "查询永乐方订单状态失败");
			log.error("Gugong auto perform failed: ".concat(GugongConstant.getInstance().getCodeMsg(response.getResultcode())));
		}

		return passport;
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
