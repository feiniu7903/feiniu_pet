package com.lvmama.passport.processor.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.client.carnival.CarnivalClient;
import com.lvmama.passport.processor.impl.client.carnival.model.Coupon;
import com.lvmama.passport.processor.impl.client.carnival.model.Ret;
import com.lvmama.passport.processor.impl.util.OrderUtil;
/**
 * 南京嘉年华
 * 
 * @author gaoxin
 * 
 */
public class CarnivalProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor {
	private static Log log = LogFactory.getLog(CarnivalProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");

	@Override
	public Passport apply(PassCode passCode) {
		log.info("Carnival Apply Code Request:" + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setExtId(passCode.getSerialNo());
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		Map<String, String> map=getQuantity(ordOrder, passCode.getObjectId());
		String quantity = map.get("volume");
		String outSampleID=map.get("productId");
		log.info("Carnival quantity:"+quantity);
		log.info("Carnival outSampleID:"+outSampleID);
		String outStance = passCode.getSerialNo();
		try {
			String sid = initApply(passport);
			if (sid == null) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				reapplySet(passport, passCode.getReapplyCount(), "FAILED");
			} else {
				applyCode(passport, quantity, passCode.getMobile(), outStance, sid, outSampleID, passCode);
			}
		} catch (Exception e) {
			log.error("Carnival Destroy Exception", e);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(e.getMessage());
			reapplySet(passport, passCode.getReapplyCount(), e.getMessage());
		}
		return passport;
	}

	/**
	 * 重新申请码处理
	 * 
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport, long times, String error) {
		OrderUtil.init().reapplySet(passport, times);
	}

	@Override
	public Passport destroy(PassCode passCode) {
		log.info("Carnival Destroy Code Request:" + passCode.getSerialNo());
		Passport passport = new Passport();
		String outStance = passCode.getSerialNo();
		try {
			String sid = initApply(passport);
			if (sid == null) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			} else {
				destroyCode(passport, outStance, sid);
			}
		} catch (Exception e) {
			  log.error("Carnival Destroy  Exception", e);
			  passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			  passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			  passport.setComLogContent(e.getMessage());
		}
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		return passport;
	}

	/**
	 * 申请sid、握手、登陆
	 * 
	 * @param passport
	 * @return
	 */
	private String initApply(Passport passport) throws Exception {
		Ret ret = new Ret();
		String sid = null;
		ret = CarnivalClient.getSid();
		sid = ret.getData().getSid();
		if (sid != null) {
			ret = CarnivalClient.check(sid);
			if ("0".equals(ret.getCode())) {
				ret = CarnivalClient.getLogin(sid);
				if (!"0".equals(ret.getCode())) {
					log.info("Carnival login: " + ret.getData().getText());
					passport.setComLogContent("供应商返回异常："+"code: "+ret.getCode()+",message: "+ret.getData().getText());
					sid = null;
				}
			} else {
				log.info("Carnival check: " + ret.getData().getText());
				passport.setComLogContent("供应商返回异常："+"code: "+ret.getCode()+",message: "+ret.getData().getText());
				sid = null;
			}
		} else {
			log.error("sid is null");
			passport.setComLogContent("sid is null");
		}
		return sid;
	}

	/**
	 * 下订单
	 * 
	 * @param passport
	 * @param ordOrder
	 * @param volume
	 * @param mobile
	 * @param outStance
	 * @param sid
	 */	
	private void applyCode(Passport passport, String volume, String mobile, String outStance, String sid, String outSampleID, PassCode passCode) {
		Long startTime = 0L;
		try {
			startTime  = System.currentTimeMillis();
			Ret ret = CarnivalClient.getTransaction(sid, volume, mobile, outStance , outSampleID);
			log.info("Carnival Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if ("0".equals(ret.getCode())) {
				String codes = "";
				for (Coupon coup : ret.getData().getNewCoupons()) {
					codes += coup.getToken() + ",\n";
				}
				passport.setCode(codes);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				log.info("Carnival ApplyCode:" + ret.getData().getText());
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+"code: "+ret.getCode()+",message: "+ret.getData().getText());
				reapplySet(passport, passCode.getReapplyCount(), "FAILED");
			}
		} catch (Exception e) {
			log.error("Carnival Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.error("Carnival ApplyCode Exception", e);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(e.getMessage());
			reapplySet(passport, passCode.getReapplyCount(), "FAILED");
		}
	}

	/**
	 * 取消订单
	 * 
	 * @param passport
	 * @param ordOrder
	 * @param outStance
	 * @param sid
	 */
	private void destroyCode(Passport passport, String outStance, String sid) {
		Long startTime = 0L;
		try {
			startTime  = System.currentTimeMillis();
			Map<String, String> map = CarnivalClient.doDestroyOrder(sid, outStance);
			log.info("Carnival destroyCode serialNo :" +outStance +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if ("0".equals(map.get("code"))) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				log.info(map.get("message"));
				passport.setComLogContent("供应商返回异常："+map.get("message"));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			}
		} catch (Exception e) {
			log.error("Carnival destroyCode serialNo Error :" +outStance +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.error("Carnival destroyCode Exception", e);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		}
	}

	/**
	 * 获取订购份数
	 * 
	 * @param ordOrder 订单
	 * @param passCode 订单子子项id
	 * @return
	 */
	private Map<String,String> getQuantity(OrdOrder ordOrder, Long passCode) {
		String volume = "";
		Map<String,String> map=new HashMap<String, String>();
		for (OrdOrderItemMeta ordOrderItemMeta : ordOrder.getAllOrdOrderItemMetas()) {
			if (passCode.longValue() == ordOrderItemMeta.getOrderItemMetaId().longValue()) {
				String productIdSupplier = ordOrderItemMeta.getProductIdSupplier();
				if (productIdSupplier != null && !"".equals(productIdSupplier)) {
					volume = String.valueOf(ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity());
					map.put("volume", volume);
					map.put("productId", productIdSupplier);
					break;
				} else {
					log.info("productIdSupplier is null!");
				}
			}
		}
		return map;
	}

}
