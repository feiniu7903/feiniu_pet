package com.lvmama.passport.processor.impl;
import java.io.ByteArrayOutputStream;
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
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.client.sanyakuxiu.orderRequest;
import com.lvmama.passport.processor.impl.client.shandong.TwoDimensionCode;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 三亚酷秀
 * @author tangJing
 */
public class SanyakuxiuProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor,OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(SanyakuxiuProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Sanyakuxiu apply serialNo: " + passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		try {
			orderRequest request=this.buildOrder(passCode);
			String uname=WebServiceConstant.getProperties("sanya.uname");
			String pass=WebServiceConstant.getProperties("sanya.pass");
			Map<String, String> params = new HashMap<String, String>();
			params.put("format","xml");//返回文件格式，见format 参数说明
			params.put("uname",uname);
			params.put("pass",pass);
			params.put("goodid",request.getGoodid());
			params.put("goodnum",request.getGoodnum());
			params.put("ordername",request.getOrderId());
			params.put("tel",request.getTel());
			params.put("tuanname",request.getTuanname());
			params.put("salesPeople",request.getSalesPeople());
			params.put("sendway",request.getSendway());
			startTime = System.currentTimeMillis();
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("sanya.order.send.url"), params);
			log.info("Sanyakuxiu applay serialNo :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+ result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				log.info("Sanyakuxiu applay resXml: " + result);
				String status = TemplateUtils.getElementValue(result,"//root/status");
				String cardPwd= TemplateUtils.getElementValue(result,"//root/cardPwd");
				if (StringUtils.equals(status,"3")) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					passport.setCode("BASE64");
					passport.setAddCode(cardPwd);
					sendImg(passport, cardPwd);
				}else {
					String errorn = TemplateUtils.getElementValue(result,"//root/message");
					passport.setComLogContent("供应商返回异常："+ errorn);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					log.info("Sanyakuxiu apply fail message: "+ errorn);
				}
			}
		} catch (Exception e) {
			log.error("Sanyakuxiu applay serialNo Error :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Sanyakuxiu applay message", e);
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
		log.info("Sanyakuxiu destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			String uname=WebServiceConstant.getProperties("sanya.uname");
			String pass=WebServiceConstant.getProperties("sanya.pass");
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
			Map<String, String> params = new HashMap<String, String>();
			params.put("format","xml");
			params.put("uname",uname);
			params.put("pass",pass);
			params.put("goodid",itemMeta.getProductIdSupplier());
			params.put("goodnum",String.valueOf(count));
			params.put("orderid",passCode.getSerialNo());
			params.put("note","游客取消出游");
			startTime=System.currentTimeMillis();
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("sanya.order.refund.url"), params);
			log.info("Sanyakuxiu destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常："+result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			}else{
				log.info("Sanyakuxiu destroy resXml: " + result);
				String status = TemplateUtils.getElementValue(result, "//root/status");			
				if (StringUtils.equals(status, "10")) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					String errorn = TemplateUtils.getElementValue(result, "//root/message");
					passport.setComLogContent("供应商返回异常："+ errorn);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Sanyakuxiu destroy fail message: " + errorn);
				}
			}
		} catch (Exception e) {
			log.error("Sanyakuxiu destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Sanyakuxiu destroy error message", e);
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
		log.info("Sanyakuxiu perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				String uname=WebServiceConstant.getProperties("sanya.uname");
				String pass=WebServiceConstant.getProperties("sanya.pass");
				Map<String, String> params = new HashMap<String, String>();
				params.put("format","xml");
				params.put("uname",uname);
				params.put("pass",pass);
				params.put("orderid",passCode.getSerialNo());
				String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("sanya.order.search.url"), params);
				if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
					log.info(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				}else{
					log.info("Sanyakuxiu perform resXml: " + result);
					String status = TemplateUtils.getElementValue(result,"//root/status");
					if (StringUtils.equals(status,"5")) {
							passport = new Passport();
							passport.setChild("0");
							passport.setAdult("0");
							passport.setUsedDate(new Date());
							passport.setDeviceId("Sanyakuxiu");
							stopCheckout(passCode);
					}
				}
			} catch(Exception e) {
				log.error("Sanyakuxiu perform error message", e);
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

	
	private orderRequest buildOrder(PassCode passCode)throws Exception{
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdPerson ordPerson = OrderUtil.init().getContract(ordOrder);
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
			String productIdSupplier = itemMeta.getProductIdSupplier();
			if (StringUtils.isBlank(productIdSupplier)) {
				throw new IllegalArgumentException("代理产品编号不能空");
			}
			orderRequest req = new orderRequest();
			req.setGoodid(productIdSupplier);
			req.setGoodnum(String.valueOf(count));
			req.setOrderId(passCode.getSerialNo());
			req.setTel(ordPerson.getMobile());
			req.setSendway("1");//1.一个码号包含全部张数 2.一个码号代表一张
		return req;
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
				passport.setComLogContent("SanyakuxiuProcessorImpl_sendImg image error");
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