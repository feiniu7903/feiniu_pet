package com.lvmama.passport.processor.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

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
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 老虎滩(娃哈哈)
 * @author dingming
 *
 */
public class TongLianProcessorImpl implements ApplyCodeProcessor,DestroyCodeProcessor, ResendCodeProcessor, OrderPerformProcessor {
	
	private static final String CHARSET_GBK = "GBK";
	
	private static final Log log = LogFactory.getLog(TongLianProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private ComLogService comLogService = (ComLogService) SpringBeanProxy.getBean("comLogService");


	/**
	 * 申码
	 */
	@Override
	public Passport apply(PassCode passCode) {
	log.info("TongLian applyCode serialNo: " + passCode.getSerialNo());
	Passport passport = new Passport();
	passport.setSerialno(passCode.getSerialNo());
	passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
	passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());

	Long startTime = 0L;	
	try {
		Map<String,String> requestParams=buildApplyOrderParams(passCode);
		startTime  = System.currentTimeMillis();
		log.info("TongLian apply request:"+printLogInfo(requestParams));
		String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("tonglian.url"), requestParams);
		log.info("TongLian Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
		log.info("TongLian apply jsonResult: " + jsonResult);
		JSONObject jsonObj = JSONObject.fromObject(jsonResult);
		String code = jsonObj.has("code")==true?jsonObj.getString("code"):null;
		String msg = jsonObj.has("msg")==true?jsonObj.getString("msg"):null;
		String order_id =jsonObj.has("order_id")==true?jsonObj.getString("order_id"):null;
		if(code!=null&&code.equals("200")){
			Map<String,String> map=buildPayOrderParams(passCode,order_id);
			log.info("TongLian pay request:"+printLogInfo(map));
			String res=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("tonglian.url"), map);
			log.info("TongLian pay jsonResult: " + res);
			JSONObject response = JSONObject.fromObject(res);
			String payCode = response.has("code")==true?response.getString("code"):null;
			String payMsg = response.has("msg")==true?response.getString("msg"):null;
			if(payCode!=null&&payCode.equals("200")){
				passport.setExtId(order_id);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				Map<String,String> paras=buildCancelOrderParams(passCode,order_id);
				String cancelRes=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("tonglian.url"), paras);
				log.info("TongLian cancel order jsonResult: " + cancelRes);
				JSONObject cancelJSON = JSONObject.fromObject(cancelRes);
				passport.setComLogContent("供应商返回异常：" + "return code=" + payCode + ",Message=" + payMsg+",对方订单取消结果: "+cancelJSON.getString("code")+":"+cancelJSON.getString("msg"));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				this.reapplySet(passport, passCode.getReapplyCount());
				log.info("TongLian pay fail message: " + payCode + " " + payMsg);
			}
		}else{
			passport.setComLogContent("供应商返回异常：" + "return code=" + code + ",Message=" + msg);
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.info("TongLian apply fail message: " + code + " " + msg);
		}
	} catch (IOException e) {
		log.error("TongLian Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
		passport.setComLogContent(e.toString());
		passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
		this.reapplySet(passport, passCode.getReapplyCount());
		log.error("TongLian apply error message", e);
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
	 * 重发短信
	 */
	@Override
	public Passport resend(PassCode passCode) {
		log.info("TongLian resend serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		
		Long startTime = 0L;
		try {
			Map<String,String> requestParams=buildResendCodeParams(passCode);
			startTime  = System.currentTimeMillis();
			log.info("TongLian resend request:"+printLogInfo(requestParams));
			String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("tonglian.url"), requestParams);
			log.info("TongLian resend serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("TongLian resend jsonResult: " + jsonResult);
			
			JSONObject jsonObj = JSONObject.fromObject(jsonResult);
			String code = jsonObj.has("code")==true?jsonObj.getString("code"):null;
			String msg = jsonObj.has("msg")==true?jsonObj.getString("msg"):null;
			if(code!=null&&code.equals("200")){
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				passport.setComLogContent("供应商返回异常："+msg);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("TongLian resend fail message: " + code + " " + msg);
			}
		} catch (IOException e) {
			log.error("TongLian resend serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("TongLian resend error message", e);
		}
		
		return passport;
	}


	/**
	 * 废码
	 */
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("TongLian destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		Long startTime = 0L;
		
		try {
			Map<String,String> requestParams=buildDestroyCodeParams(passCode);
			startTime  = System.currentTimeMillis();
			log.info("TongLian destroy request:"+printLogInfo(requestParams));
			String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("tonglian.url"), requestParams);
			log.info("TongLian destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("TongLian destroy jsonResult: " + jsonResult);
			
			JSONObject jsonObj = JSONObject.fromObject(jsonResult);
			String code = jsonObj.has("code")==true?jsonObj.getString("code"):null;
			String msg = jsonObj.has("msg")==true?jsonObj.getString("msg"):null;
			
			if(code!=null&&code.equals("200")){
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				passport.setComLogContent("供应商返回异常："+msg);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("TongLian destroy fail message: " + code + " " + msg);
			}
		} catch (IOException e) {
			log.error("TongLian destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("TongLian destroy error message", e);
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
		log.info("BeijingLYW perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		Long startTime = 0L;
		if (isNeedCheckout(passCode)) {
			try {
				Map<String,String> requestParams=buildGetOrderInfoParams(passCode);
				startTime  = System.currentTimeMillis();
				log.info("TongLian perform request:"+printLogInfo(requestParams));
				String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("tonglian.url"), requestParams);
				log.info("TongLian perform serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				log.info("TongLian perform jsonResult: " + jsonResult);
				
				JSONObject jsonObj = JSONObject.fromObject(jsonResult);
				String code = jsonObj.has("code")==true?jsonObj.getString("code"):null;
				String msg = jsonObj.has("msg")==true?jsonObj.getString("msg"):null;
				String status=jsonObj.has("Status")==true?jsonObj.getString("Status"):null;
				
				if((code!=null&&code.equals("200"))&&(status!=null&&status.equals("2"))){
					passport = new Passport();
					passport.setChild("0");
					passport.setAdult("0");
					passport.setUsedDate(new Date());
					passport.setDeviceId("TongLian");
					stopCheckout(passCode);
				}else{
					stopCheckout(passCode);
					this.addComLog(passCode, jsonObj.getString("Msg"), "查询履行状态失败");
					log.info("TongLian perform fail message: " + code + " " + msg);
				}
			} catch (IOException e) {
				log.error("TongLian perform error message", e);
			}
		}
		return passport;
	}
	

	/**
	 * 构建申码下单请求参数
	 * @param passCode
	 * @return
	 * @throws IOException
	 */
	private Map<String, String>  buildApplyOrderParams(PassCode passCode) throws IOException {
		String serialNo = passCode.getSerialNo();
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson =OrderUtil.init().getContract(ordorder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
		Long settlementPrice = itemMeta.getSettlementPrice();
		String timastamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		
		String productIdSupplier = itemMeta.getProductIdSupplier();
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		Map<String,String> params=new HashMap<String,String>();
		params.put("timestamp", timastamp);
		params.put("out_order_id",serialNo);
		params.put("mobile", ordperson.getMobile());
		params.put("num", count.toString());
		params.put("method", "send");
		params.put("seller_id", WebServiceConstant.getProperties("tonglian.sellerId"));
		params.put("sub_outer_iid", productIdSupplier);
		params.put("total_fee", settlementPrice.toString());
	
		String signValue=sign(WebServiceConstant.getProperties("tonglian.key"),params);
		params.put("sign", signValue);
		return params;
	}
	
	/**
	 * 构建重发短信请求参数
	 * @param passCode
	 * @return
	 * @throws IOException
	 */
	private Map<String, String> buildResendCodeParams(PassCode passCode) throws IOException {
		String serialNo = passCode.getSerialNo();
		String timastamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		Map<String,String> params=new HashMap<String,String>();
		params.put("timestamp", timastamp);
		params.put("out_order_id",serialNo);
		params.put("order_id",passCode.getExtId());
		params.put("method", "resend");
		params.put("seller_id", WebServiceConstant.getProperties("tonglian.sellerId"));
	
		String signValue=sign(WebServiceConstant.getProperties("tonglian.key"),params);
		params.put("sign", signValue);
		return params;
	}
	
	/**
	 * 构建废单请求操作  (已支付订单的退单操作)
	 * @param passCode
	 * @return
	 * @throws IOException
	 */
	private Map<String, String> buildDestroyCodeParams(PassCode passCode) throws IOException {
		String serialNo = passCode.getSerialNo();
		String timastamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		
		Map<String,String> params=new HashMap<String,String>();
		params.put("timestamp", timastamp);
		params.put("out_order_id",serialNo);
		params.put("order_id",passCode.getExtId());
		params.put("method", "refund");
		params.put("seller_id", WebServiceConstant.getProperties("tonglian.sellerId"));
	
		String signValue=sign(WebServiceConstant.getProperties("tonglian.key"),params);
		params.put("sign", signValue);
		return params;
	}
	
	/**
	 * 构建获取订单详情请求参数
	 * @param passCode
	 * @return
	 * @throws IOException
	 */
	private Map<String, String> buildGetOrderInfoParams(PassCode passCode) throws IOException {
		String serialNo = passCode.getSerialNo();
		String timastamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		
		Map<String,String> params=new HashMap<String,String>();
		params.put("timestamp", timastamp);
		params.put("out_order_id",serialNo);
		params.put("order_id",passCode.getExtId());
		params.put("method", "get");
		params.put("seller_id", WebServiceConstant.getProperties("tonglian.sellerId"));
	
		String signValue=sign(WebServiceConstant.getProperties("tonglian.key"),params);
		params.put("sign", signValue);
		return params;
	}
	
	/**
	 * 构建支付通知接口请求参数
	 * @param passCode
	 * @param orderId
	 * @return
	 * @throws IOException
	 */
	private Map<String, String> buildPayOrderParams(PassCode passCode,String orderId) throws IOException {
		String serialNo = passCode.getSerialNo();
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		Long settlementPrice = itemMeta.getSettlementPrice();
		String timastamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		
		Map<String,String> params=new HashMap<String,String>();
		params.put("timestamp", timastamp);
		params.put("order_id",orderId);
		params.put("out_order_id",serialNo);
		params.put("method", "Pay");
		params.put("seller_id", WebServiceConstant.getProperties("tonglian.sellerId"));
		params.put("total_fee", settlementPrice.toString());
	
		String signValue=sign(WebServiceConstant.getProperties("tonglian.key"),params);
		params.put("sign", signValue);
		return params;
	}
	
	/**
	 * 构建取消订单请求参数(未支付订单的取消订单操作)
	 * @param passCode
	 * @param orderId
	 * @return
	 * @throws IOException
	 */
	private Map<String, String> buildCancelOrderParams(PassCode passCode,String orderId) throws IOException {
		String serialNo = passCode.getSerialNo();
		String timastamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		
		Map<String,String> params=new HashMap<String,String>();
		params.put("timestamp", timastamp);
		params.put("order_id",orderId);
		params.put("out_order_id",serialNo);
		params.put("method", "cancel");
		params.put("seller_id", WebServiceConstant.getProperties("tonglian.sellerId"));
	
		String signValue=sign(WebServiceConstant.getProperties("tonglian.key"),params);
		params.put("sign", signValue);
		return params;
	}

	/**
	 * 打印map参数log
	 * @param params
	 * @return
	 */
	private String printLogInfo(Map<String,String> params){
		Set<Entry<String, String>> entrys=params.entrySet();
		StringBuilder paras = new StringBuilder();
		for(Entry<String, String> entry:entrys){
			paras.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		paras.deleteCharAt(paras.length()-1);
		return paras.toString();
	}
	
	private boolean isNeedCheckout(PassCode passCode) {
		return "SUCCESS".equals(passCode.getStatus()) && isPassCodeUnused(passCode);
	}
	
	private boolean isPassCodeUnused(PassCode passCode) {
		if (!usedList.contains(passCode)) {
			unusedList.add(passCode);
			return true;
		}
		return false;
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
	/**
	 * 计算签名
	 * @param secret
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public  static String sign(String secret, Map<String,String> data) throws IOException {
		//把字典按Key的字母顺序排序
		Map<String, String> sortedParams = new TreeMap<String, String>();
		sortedParams.putAll(data);
		Set<Entry<String, String>> paramSet = sortedParams.entrySet();

		//把所有参数名和参数值串在一起
		StringBuilder query = new StringBuilder(secret);
		for (Entry<String, String> param : paramSet) {
			if (isNotEmpty(param.getKey(), param.getValue())) {
				query.append(param.getKey()).append(param.getValue());
			}
		}

		//使用MD5加密
		byte[] bytes = encryptMD5(query.toString());

		//把二进制转化为大写的十六进制
		return byte2hex(bytes);
	}
	
	private static byte[] encryptMD5(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes(CHARSET_GBK));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse.getMessage());
		}
		return bytes;
	}
	
	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}

	/**
	 * 
	 * @param values
	 * @return
	 */
	public static boolean isNotEmpty(String... values) {

		boolean result = true;

		if (null == values || 0 == values.length) {
			return false;
		}

		if (null != values && 0 < values.length) {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}

		return result;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {

		boolean result = true;

		if (null != value) {
			int length = value.length();
			for (int i = 0; i < length; i++) {
				if (false == (Character.isWhitespace(value.charAt(i)))) {
					result = false;
				}
			}
		}

		return result;
	}
	
	public static void main(String[] args) throws IOException {
		/*Map<String,String> params=new HashMap<String,String>();
		params.put("timestamp", "2014-03-18 09:30:34");
		params.put("out_order_id","140318093095");
		params.put("mobile", "15000339906");
		params.put("num", "1");
		params.put("method", "send");
		params.put("seller_id", "2673557716");
		params.put("sub_outer_iid", "1721402260250");
		params.put("total_fee", "200");
		String signValue=sign(WebServiceConstant.getProperties("tonglian.key"),params);
		System.out.println(signValue);
		params.put("sign", signValue);*/
		
/*		Map<String,String> params=new HashMap<String,String>();
		params.put("timestamp", "2014-03-18 09:40:36");
		params.put("order_id","140318001902"); 
		params.put("method", "resend");
		params.put("seller_id", "2673557716");
		String signValue=sign(WebServiceConstant.getProperties("tonglian.key"),params);*/
		
		Map<String,String> params=new HashMap<String,String>();
		params.put("timestamp",  "2014-03-18 09:42:36");
		params.put("out_order_id","140318093095");
		params.put("order_id","140318001902");
		params.put("method", "get");
		params.put("seller_id", WebServiceConstant.getProperties("tonglian.sellerId"));
		String signValue=sign(WebServiceConstant.getProperties("tonglian.key"),params);
		
		/*Map<String,String> params=new HashMap<String,String>();
		params.put("timestamp", "2014-03-18 10:30:36");
		params.put("order_id","140318001902");
		params.put("out_order_id","140318093095");
		params.put("method", "Pay");
		params.put("seller_id", WebServiceConstant.getProperties("tonglian.sellerId"));
		params.put("total_fee", "200");
		String signValue=sign(WebServiceConstant.getProperties("tonglian.key"),params);*/
		
		params.put("sign", signValue);
		
		String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("tonglian.url"), params);
		System.out.println(jsonResult);
	}
}
