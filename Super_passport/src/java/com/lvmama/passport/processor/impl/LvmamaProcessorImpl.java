package com.lvmama.passport.processor.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.zxing.WriterException;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.QRcode;

/**
 * LVMAMA码服务
 * 
 * @author zhangkexing
 * 
 */
public class LvmamaProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor {
	private static Log log = LogFactory.getLog(LvmamaProcessorImpl.class);
	private PassCodeService passCodeService = (PassCodeService) SpringBeanProxy.getBean("passCodeService");
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private OrderItemMetaAperiodicService orderItemMetaAperiodicService = (OrderItemMetaAperiodicService) SpringBeanProxy.getBean("orderItemMetaAperiodicService");
	
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Lvmama Apply Code Request:" + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setExtId(passCode.getSerialNo());
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		try {
		
			applyCode(passport, passCode);
			saveCodeImageInPassCode(passCode, passport.getAddCode());
		} catch (Exception e) {
			log.error("LVMAMA apply Exception", e);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(e.getMessage());
			reapplySet(passport, passCode.getReapplyCount(), e.getMessage());
		}
		return passport;
	}

	private void saveCodeImageInPassCode(PassCode passCode , String addCode) throws WriterException, IOException {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		QRcode.generate(addCode, byteOutputStream);
		
		byte[] image = byteOutputStream.toByteArray();
		passCode.setCodeImage(image);
		passCodeService.updatePassCode(passCode);
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
		log.info("LVMAMA Destroy Code Request:" + passCode.getSerialNo());
		Passport passport = new Passport();
		String outStance = passCode.getSerialNo();
		try {
			destroyCode(passport, outStance);
		} catch (Exception e) {
			  log.error("LVMAMA Destroy  Exception", e);
			  passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			  passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			  passport.setComLogContent(e.getMessage());
		}
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		return passport;
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
	private void applyCode(Passport passport, PassCode passCode) throws Exception {
		try {

			Long orderId = passCode.getOrderId();
			
			List<PassPortCode> passPortCodeList = passCode.getPassPortList();
			List<Long> excludeTargetList = new ArrayList<Long>();
			excludeTargetList.add(8594l);//排除的通关点 ，南京明孝陵，发送8位长度
			excludeTargetList.add(8064l);//排除的通关点 ，上海寻梦园，发送8位长度
			
			int length = 6;
			for (PassPortCode passPortCode : passPortCodeList) {
				if(excludeTargetList.contains(passPortCode.getTargetId())){
					length = 8;
					break;
				}
			}
			//获取辅助码
			String addCode = getAddCode(length);
			if(orderId != null) {
				OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
				//不定期订单，从密码券表取券号替换辅助码
				if(order != null && order.IsAperiodic()) {
					OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicService.selectFirstOrderAperiodicByOrderId(orderId);
					if(aperiodic != null) {
						addCode = aperiodic.getPasswordCertificate();
					}
				}
			}
			passport.setAddCode(addCode);
			//对辅助码MD5加密保存
			passport.setAddCodeMd5(MD5.encode(addCode));
			//获取手机短信中，用于生成图形二维码的辅助码代码
			//2013-09-06变更为BASE64彩信
			String code = getCode(passport);
			passport.setCode(code);
			passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			log.info("LVMAMA ApplyCode addCode:" + addCode);
			log.info("LVMAMA ApplyCode code:" + code);
		} catch (Exception e) {
			throw e;
		}
	}

	public String getCode(Passport passport) {
		return "BASE64";
//		String code = Constant.getInstance().getProperty("lvmama.tcode.url")+RandomFactory.generateMixed(10);
//		if(!hasExisting("code", code))
//			return code;
//		else
//			return getCode(passport);
	}

	private synchronized String getAddCode(int length){
		Long code = RandomFactory.generateLong(length);
		String codeStr = code.toString();
		if(!hasExisting("add_code",codeStr))
			return codeStr;
		else
			return getAddCode(length);
	}
	
	private boolean hasExisting(String codeType,String code){
		return  passCodeService.checkCodeHasExisting(codeType,code);
	}
		
	/**
	 * 取消订单
	 * 
	 * @param passport
	 * @param ordOrder
	 * @param outStance
	 * @param sid
	 */
	private void destroyCode(Passport passport, String outStance) {
		try {
			boolean executed = passCodeService.destoryCode(outStance,Constant.PASSCODE_STATUS.DESTROYED.name());
			if(executed){
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				passport.setComLogContent("serialNo:"+outStance+" 废单失败 ");
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			}
		} catch (Exception e) {
			log.error("LVMAMA destroyCode Exception", e);
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
