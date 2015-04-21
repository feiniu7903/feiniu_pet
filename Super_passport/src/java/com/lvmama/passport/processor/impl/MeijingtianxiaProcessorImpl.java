package com.lvmama.passport.processor.impl;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
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
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.client.shandong.TwoDimensionCode;
import com.lvmama.passport.processor.impl.util.EncryptUtil;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
/**
 * 美景天下
 */
public class MeijingtianxiaProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor {
	private static final Log log = LogFactory.getLog(MeijingtianxiaProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Meijingtianxiaapply serialNo: " + passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		try {
			Map<String, String> params =this.buildOrderParams(passCode);
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("mjtx.url")+"Agent_SubmitOrder", params);
			startTime = System.currentTimeMillis();
			log.info("Meijingtianxia applay serialNo :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+ result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				log.info("Meijingtianxia applay resXml: " + result);
				String status = TemplateUtils.getElementValue(result,"//Response/Head/Result");
				if (StringUtils.equals(status,"true")) {
					String secretKey=WebServiceConstant.getProperties("mjtx.authcode");
					String rspBody = TemplateUtils.getElementValue(result, "//Response/Body");
					String decBody=EncryptUtil.DES3Decrypt(EncryptUtil.BASE64Decrypt(rspBody),secretKey);
					log.info("Meijingtianxia decBody resXml:"+decBody);
					String checkCode= TemplateUtils.getElementValue(decBody,"//SubmitOrderResult/ManualCheckCode/string");
					String ticketCode= TemplateUtils.getElementValue(decBody,"//SubmitOrderResult/ETicketCode/string");
					String orderKeyId=TemplateUtils.getElementValue(decBody,"//SubmitOrderResult/OrderKeyId");
					passport.setCode("BASE64");
					passport.setAddCode(checkCode);
					passport.setExtId(orderKeyId);
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					sendImg(passport,ticketCode);
				}else {
					String code = TemplateUtils.getElementValue(result,"//Response/Head/StatusCode");
					String errorn = TemplateUtils.getElementValue(result,"//Response/Head/Message"); 
					passport.setComLogContent("供应商返回异常："+ code +"|"+ errorn);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					this.reapplySet(passport, passCode.getReapplyCount());
					log.info("Meijingtianxia apply fail message: "+ code +"|"+ errorn);
				}
			}
		} catch (Exception e) {
			log.error("Meijingtianxiaapplay serialNo Error :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Meijingtianxiaapplay message", e);
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
		log.info("Meijingtianxia destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		Long startTime = 0L;
		try {
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
			String orderNo=passCode.getExtId();
			String _scode=WebServiceConstant.getProperties("mjtx.scode");
			String pid=WebServiceConstant.getProperties("mjtx._pid");
			String _ts = System.currentTimeMillis() + "";
			_ts = _ts.substring(0, 10);
			Map<String, String> params = new HashMap<String, String>();
			params.put("format","xml");
			params.put("_scode",_scode );
			params.put("_ts", _ts);
			params.put("_pid",pid);
			params.put("orderkeyid",orderNo);
			params.put("num",String.valueOf(count));
			String sign=makeSign(params);
			params.put("_sign",sign);
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("mjtx.url")+"Agent_OrderRetreat", params);
			startTime = System.currentTimeMillis();
			if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
			log.info(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常：" + result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			} else {
				log.info("Meijingtianxia destroy resXml: " + result);
				String isSuccess = TemplateUtils.getElementValue(result,"//Response/Head/Result");
				if (StringUtils.equals(isSuccess,"true")) {
					passport.setComLogContent("退票请求已发送，供应商处于人工审核中,请留意后续处理结果！");
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				} else {
					String code = TemplateUtils.getElementValue(result,"//Response/Head/StatusCode");
					String errorn = TemplateUtils.getElementValue(result,"//Response/Head/Message"); 
					passport.setComLogContent("供应商返回异常："+ code +"|"+ errorn);
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Meijingtianxia destroy fail message: "+ code +"|"+ errorn);
				}
			}
		} catch (Exception e) {
			log.error("Meijingtianxia destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Meijingtianxia destroy error message", e);
		}
		return passport;
	}
	

	private Map<String, String> buildOrderParams(PassCode passCode)throws Exception{
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdPerson ordPerson = OrderUtil.init().getContract(ordOrder);
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
			String productIdSupplier = itemMeta.getProductIdSupplier();
			String  visiteTime= DateFormatUtils.format(ordOrder.getVisitTime(),"yyyy-MM-dd");
			if (StringUtils.isBlank(productIdSupplier)) {
				throw new IllegalArgumentException("代理产品编号不能空");
			}
			String _scode=WebServiceConstant.getProperties("mjtx.scode");
			String pid=WebServiceConstant.getProperties("mjtx._pid");
			String _ts = System.currentTimeMillis() + "";
			_ts = _ts.substring(0, 10);
			Map<String, String> params = new HashMap<String, String>();
			params.put("format","xml");
			params.put("_scode", _scode);//商户编码
			params.put("_ts", _ts);//10位时间戳
			params.put("_pid", pid);//合作伙伴ID
			params.put("orderkeyid",passCode.getSerialNo());//订单号
			params.put("productversionkeyid",productIdSupplier.trim());//要购买的票ID
			params.put("productversionprice",String.valueOf(itemMeta.getSettlementPriceYuan()));//要购买的票结算价
			params.put("tripdate",visiteTime);//游玩日期
			params.put("mobilenumbertogeteticket", ordPerson.getMobile());//购票人电话
			params.put("totalticketquantity",String.valueOf(count));//购票数量
			String sign = makeSign(params);
			params.put("_sign",sign);
			
		return params;
	}
	
	/**
	 * 生成签名信息
	 * @return
	 */
	public static String makeSign(Map<String, String> params)throws Exception {
		ArrayList<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		boolean first = true;
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = params.get(key);
				if (first) {
					prestr = prestr + key + "=" + value;
					first = false;
				} else {
					prestr = prestr + "&" + key + "=" + value;
				}
		}
		System.out.println(prestr);
		String authcode=WebServiceConstant.getProperties("mjtx.authcode");
		String sign =MD5.encode32(prestr+authcode);
		return sign;
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