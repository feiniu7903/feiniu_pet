package com.lvmama.passport.processor.impl;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ParseException;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.client.beizhu.BeizhuConstant;
import com.lvmama.passport.processor.impl.client.beizhu.BeizhuHTTPUtil;
import com.lvmama.passport.processor.impl.client.beizhu.BeizhuOrder;
import com.lvmama.passport.processor.impl.client.beizhu.BeizhuResponse;
import com.lvmama.passport.processor.impl.util.OrderUtil;
/**
 * 贝竹--对接实现
 * 
 * @author zhangkexing
 */
public class BeizhuProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor {
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private ComLogService comLogService = (ComLogService) SpringBeanProxy.getBean("comLogService");
	private static final Log log = LogFactory.getLog(BeizhuProcessorImpl.class);
	

	@Override
	public Passport apply(PassCode passCode) {
		log.info("Beizhu Apply Code: " + passCode.getSerialNo());
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
			log.error("Beizhu Apply Exception message:", e);
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
		log.info("Beizhu Destroy Code: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		passport.setSerialno(passCode.getSerialNo());
		try {
			
			BeizhuOrder beizhuOrder = buildOrderInfo(passCode);
			beizhuOrder.setNum("0");//门票数量(数量为0，表示退票，返回结果值为为表示取消订单成功)
			
			BeizhuResponse response = BeizhuHTTPUtil.cancelOrder(beizhuOrder);

			if(response!=null && response.operateOrderSuccess()) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setComLogContent("贝竹对接方返回："+BeizhuConstant.getCodeMsg(response.getRes()));
			}
		} catch (Exception e) {
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setComLogContent(e.getMessage());
			log.error("Beizhu Destroy Exception:".concat(e.getMessage()));
		}
		return passport;
	}

	private Passport sendOrder(PassCode passCode, Passport passport) {
		Long startTime = 0L;
		BeizhuResponse response = null;
		BeizhuOrder beizhuOrder = null;
		
		try {
			beizhuOrder = buildOrderInfo(passCode);
			startTime = System.currentTimeMillis();
			response = BeizhuHTTPUtil.sendOrder(beizhuOrder);
			
			log.info("Beizhu Apply serialNo : " +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
		} catch (ParseException e) {
			this.reapplySet(passport, passCode.getReapplyCount());
			passport.setComLogContent("解析贝竹对接方返回的推单结果：异常" + e);
			log.error(e);
		} catch(Exception e){
			log.error("Beizhu Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			this.reapplySet(passport, passCode.getReapplyCount());
			passport.setComLogContent(e.getMessage());
			log.error(e);
		}

		if (response!= null) {
			if (response.operateOrderSuccess()) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				passport.setSerialno(passCode.getSerialNo());
				passport.setAddCode(response.getOrder_id());
				passport.setCode(beizhuOrder.getSfz());// 贝竹产品code则存放证件号
			} else {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				// 如果推单失败原因是由贝竹对接方业务代码不成功，而非系统异常，申码失败且不重申码
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.error("贝竹对接方返回："+BeizhuConstant.getCodeMsg(response.getRes()));
				passport.setComLogContent(BeizhuConstant.getCodeMsg(response.getRes()));
			}
		}
		return passport;
	}

	private BeizhuOrder buildOrderInfo(PassCode passCode) {
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		// 取票人/联系人的证件号
		String cardNum = ordorder.getContact().getCertNo();
		String phone = ordorder.getContact().getMobile();
		
		OrdOrderItemMeta itemMeta = null;
		for (OrdOrderItemMeta item : ordorder.getAllOrdOrderItemMetas()) {
			if (item.getOrderItemMetaId().longValue() == (passCode.getObjectId().longValue())) {
				itemMeta = item;
				break;
			}
		}
		if(itemMeta == null )
			return null;
		
		//代理产品编号(对接方的产品编号)
		String productIdSupplier = itemMeta.getProductIdSupplier();
		//购买份数
		Long num = itemMeta.getProductQuantity() * itemMeta.getQuantity();		
		BeizhuOrder beizhuorder = new BeizhuOrder();
		beizhuorder.setPhone(phone);
		beizhuorder.setOther_id(passCode.getSerialNo()); //驴妈妈申码流水号
		beizhuorder.setSfz(cardNum);
		beizhuorder.setGid(productIdSupplier);
		beizhuorder.setNum(String.valueOf(num));
		beizhuorder.setPlantime(DateUtil.getFormatDate(ordorder.getVisitTime(), "yyyy-MM-dd"));
		//8:景区到付，16：在线支付
		if (ordorder.isPayToLvmama()) {
			beizhuorder.setState("16");
		} else {
			beizhuorder.setState("8");
		}
		return beizhuorder;
		
	}

	@Override
	public Passport perform(PassCode passCode) {
		log.info("Beizhu perform serialNo: " + passCode.getSerialNo());
		log.info("Beizhu getOrder: " + passCode.getSerialNo());

		Passport passport = null;
		BeizhuResponse response = null;
		try {
			response = BeizhuHTTPUtil.getOrderStatus(passCode);
		} catch (Exception e) {
			log.error(e);
		} 
		if (response != null) {
			//贝竹对接方已经出票，驴妈妈自动履行
			if (response.used()) {
				passport = new Passport();
				passport.setChild("0");
				passport.setAdult("0");
				passport.setUsedDate(new Date());
				passport.setDeviceId("Beizhu");
			}else if(!response.canceled() && !response.unused()){ //如果一个订单在贝竹方没有被履行，并且状态既不是未履行，又不是已经取消，则订单一定是异常的
				String codeMsg = BeizhuConstant.getCodeMsg(response.getRes());
				log.error("Beizhu auto perform failed: ".concat(codeMsg));
			}
		} else {
			this.addComLog(passCode, BeizhuConstant.getCodeMsg(response.getRes()), "查询贝竹对接方订单状态失败");
			log.error("Beizhu auto perform failed: ".concat(BeizhuConstant.getCodeMsg(response.getRes())));
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
