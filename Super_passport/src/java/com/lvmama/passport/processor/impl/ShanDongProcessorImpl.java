package com.lvmama.passport.processor.impl;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.comm.vo.PassportConstant.PASSCODE_SMS_SENDER;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.client.shandong.TwoDimensionCode;
import com.lvmama.passport.processor.impl.client.shandong.WebserviceLocator;
import com.lvmama.passport.processor.impl.client.shandong.WebservicePortType;
import com.lvmama.passport.processor.impl.client.shandong.model.CancelResult;
import com.lvmama.passport.processor.impl.client.shandong.model.OrderBean;
import com.lvmama.passport.processor.impl.client.shandong.model.OrderResult;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceConstant;


public class ShanDongProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor{
	/** 日志对象 */
	private static final Log log = LogFactory.getLog(ShanDongProcessorImpl.class);
	/** 订单服务*/
	private static OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	
	private static Map<String, String> applyOrderErrorMap = new HashMap<String, String>();
	private static Map<String, String> cancelOrderErrorMap = new HashMap<String, String>();
	
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Shandong Apply Code: " + passCode.getSerialNo());
		Passport passport = this.applyCode(passCode);
		passport.setSendSms(PASSCODE_SMS_SENDER.LVMAMA.name());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSerialno(passCode.getSerialNo());
		return passport;
	}

	/**
	 * 废码
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("Shandong Destoy Code :" + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		passport.setSerialno(passCode.getSerialNo());
		Long startTime = 0L;
		try {
			WebservicePortType port=new WebserviceLocator().getwebserviceHttpPort();
			String versionNo=WebServiceConstant.getProperties("shandong.versionNo");
			String otaName=WebServiceConstant.getProperties("shandong.otaName");
			String kid=WebServiceConstant.getProperties("shandong.kid");
			String key=WebServiceConstant.getProperties("shandong.key");
			String sign=MD5.encode(versionNo+kid+otaName+passCode.getExtId()+key);
			startTime = System.currentTimeMillis();
			CancelResult result=port.cancellOrder(versionNo,kid,otaName,passCode.getExtId(),sign);
			String code=result.getStateCode();
			log.info("Shandong Destoy responseCode:"+code);
			log.info("Shandong Destoy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if (StringUtils.equals(code,"001")) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				String errorMsg = getCancelOrderErrorMsg(code);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				if (errorMsg != null) {
					passport.setComLogContent("供应商返回异常：" + errorMsg);
				} else {
					passport.setComLogContent("供应商返回异常：" + code);
				}
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("Shandong Destoy Code error: " + errorMsg);
			}
		} catch (Exception e) {
			log.error("Shandong Destoy serialNo Error:" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.error("Shandong Destoy Code error", e);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setComLogContent(e.getMessage());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
		}
		return passport;
	}
	
	public Passport applyCode(PassCode passCode){
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdOrderItemMeta item =OrderUtil.init().getItemMeta(ordOrder, passCode);
		if (item != null) {
			Long startTime = 0L;
			try {
				String productId = item.getProductIdSupplier();
				String strategyId = item.getProductTypeSupplier();
				Assert.notNull(productId,"productIdSupplier is null!");
				Assert.notNull(productId,"productTypeSupplier is null!");
				String orderSource=WebServiceConstant.getProperties("shandong.orderSource");
				String versionNo=WebServiceConstant.getProperties("shandong.versionNo");
				String kid=WebServiceConstant.getProperties("shandong.kid");
				String otaName=WebServiceConstant.getProperties("shandong.otaName");
				String key=WebServiceConstant.getProperties("shandong.key");
				OrdPerson ordperson = OrderUtil.init().getContract(ordOrder);
				String name=ordperson.getName();
				String mobile = passCode.getMobile();
				Long count = item.getProductQuantity() * item.getQuantity();
				OrderBean bean=new OrderBean();
				bean.setOta_order_id(passCode.getSerialNo());
				bean.setProduct_info_id(productId.trim());
				bean.setStrategy_id(strategyId.trim());
				if (ordOrder.IsAperiodic()) {
					log.info("isAperiodic is true");
					//不定期的开始时间和结束时间
					Calendar validBeginTime = Calendar.getInstance();
					validBeginTime.setTime(new Date());
					Calendar ValidEndTime = Calendar.getInstance();
					ValidEndTime.setTime(ordOrder.getValidEndTime());
					bean.setBegin_time(validBeginTime);
					bean.setEnd_time(ValidEndTime);
					
				} else {
					log.info("isAperiodic is false");
					//普通的门票开始和结束时间就是游玩时间
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(ordOrder.getVisitTime());
					bean.setBegin_time(calendar);
					bean.setEnd_time(calendar);
				}
				bean.setCounts(count.intValue());
				bean.setOrder_source(orderSource);
				bean.setPurchaser_name(name);
				bean.setPurchaser_phone(mobile);
				printApplyOrderInfoLog(productId,strategyId,bean.getBegin_time().getTime(),bean.getEnd_time().getTime(),name,mobile,count);
				String sign=MD5.encode(versionNo+kid+otaName+key);
				startTime  = System.currentTimeMillis();
				WebservicePortType port=new WebserviceLocator().getwebserviceHttpPort();
				OrderResult result=port.saveOrder(versionNo,false,kid,otaName,bean,sign);
				String codeStr=result.getCodeStr();
				log.info("Shandong Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				log.info("Shandong ApplyCode codeStr:" + codeStr +"|message:"+result.getMessage()+"|orderId:"+result.getOrderId()+"|stateCode:"+result.getStateCode());
				String errorMsg = getApplyOrderErrorMsg(result.getStateCode());
				if (StringUtils.equals(result.getStateCode(),"001")) {
					passport.setCode("BASE64");
					passport.setAddCode(codeStr);
					passport.setExtId(result.getOrderId());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					sendImg(passport,codeStr);
				} else {
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					passport.setComLogContent("供应商返回异常："+errorMsg);
					log.info("Shandong Apply Error code: " + result.getStateCode() + "errorMsg: " + errorMsg);
					this.reapplySet(passport, passCode.getReapplyCount(), "FAILED");
				}
			}catch(Exception e){
				log.error("Shandong Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				log.error("Shandong Apply Error : ", e);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent(e.getMessage());
				this.reapplySet(passport, passCode.getReapplyCount(), e.getMessage());
			}
		}
		return passport;
	}
	
	/**
	 * 重新申请码处理
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport,long times,String error ){
		OrderUtil.init().reapplySet(passport, times);
	}
	
	/**
	 * 下订信息日志
	 */
	private void printApplyOrderInfoLog(String productId,String strategyId,Date beginTime,Date endTime,String name,String mobile,Long count){
		String bgtime=DateFormatUtils.format(beginTime,"yyyy-MM-dd");
		String edtime=DateFormatUtils.format(endTime,"yyyy-MM-dd");
		log.info("Shandong ApplyCode productId: " + productId);
		log.info("Shandong ApplyCode strategyId: " + strategyId);
		log.info("Shandong ApplyCode visitTime: " + bgtime);
		log.info("Shandong ApplyCode endTime: " + edtime);
		log.info("Shandong ApplyCode name: " + name);
		log.info("Shandong ApplyCode mobile: " + mobile);
		log.info("Shandong ApplyCode count: " + count);
	}
	
	/**
	 * 取得下单失败原因信息
	 * @param code
	 * @return
	 */
	private static String getApplyOrderErrorMsg(String code) {
		if(applyOrderErrorMap.isEmpty()) {
			applyOrderErrorMap.put("001","成功");
			applyOrderErrorMap.put("002", "下单失败：超出电子票数量");
			applyOrderErrorMap.put("003", "下单失败：景区未提供产 品于ota ");
			applyOrderErrorMap.put("004", "下单失败：未设定游玩时间或者游玩时间过期");
			applyOrderErrorMap.put("005", "下单失败：门票产品过期");
			applyOrderErrorMap.put("006", "下单失败：不存在的门票");
			applyOrderErrorMap.put("007", "下单失败：签名错误");
			applyOrderErrorMap.put("008", "下单失败：账户错误");
		}
		return applyOrderErrorMap.get(code);
	}
	
	/**
	 * 取得废单失败原因信息
	 * @param code
	 * @return
	 */
	private static String getCancelOrderErrorMsg(String code) {
		if(cancelOrderErrorMap.isEmpty()) {
			cancelOrderErrorMap.put("001", "取消订单成功");
			cancelOrderErrorMap.put("002", "订单不存在");
			cancelOrderErrorMap.put("003", "二维码电子票已经使用");
			cancelOrderErrorMap.put("004", "签名错误");
			cancelOrderErrorMap.put("005", "错误的账户");
		}
		return cancelOrderErrorMap.get(code);
	}
	
	/**
	 * 申码图片
	 * @param passport
	 * @param passCode
	 */
	private void sendImg(Passport passport,String codeStr){
		try {
			log.info("sendImg codeStr:"+codeStr);
			String imageType="png";
			int size=7;
			byte[] imagebt = getImageCode(codeStr,imageType,size);
			if (imagebt != null) {
				passport.setCodeImage(imagebt);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setComLogContent("ShandongProcessorImpl_sendImg image error");
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			}
		} catch (Exception e) {
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
		}
	}
	
	
	
	/**
	 * 用指定的字符串生成二维码图片
	 * @param encoderContent
	 * @return
	 * @throws Exception
	 */
	private byte[] getImageCode(String encoderContent,String imageType,int size) throws Exception {
		TwoDimensionCode handler = new TwoDimensionCode();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        handler.encoderQRCode(encoderContent, out,imageType,size);
        byte[] imageByte=out.toByteArray();
		return imageByte;
	}
}
