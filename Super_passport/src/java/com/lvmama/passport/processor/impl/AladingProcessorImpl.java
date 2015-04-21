package com.lvmama.passport.processor.impl;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.impl.client.shandong.TwoDimensionCode;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 上海辰山植物园 (阿拉丁)
 * 
 * @author dingming
 * 
 */
public class AladingProcessorImpl implements ApplyCodeProcessor,OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(AladingProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private ComLogService comLogService = (ComLogService) SpringBeanProxy.getBean("comLogService");

	@Override
	public Passport apply(PassCode passCode) {
		log.info("Alading applyCode serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		Long startTime = 0L;
		try {
			OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdPerson ordperson = OrderUtil.init().getContract(ordorder);
			CompositeQuery compositeQuery = new CompositeQuery();
			compositeQuery.getPageIndex().setBeginIndex(0);
			compositeQuery.getPageIndex().setEndIndex(1000000000);
			List<String> targetIdlist = this.buildTargetId(passCode.getPassPortList());
			String targetIds = this.join(",", targetIdlist.iterator());
			compositeQuery.getMetaPerformRelate().setTargetId(targetIds);
			compositeQuery.getMetaPerformRelate().setOrderId(ordorder.getOrderId());
			List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
			
			Map<String, String> params = new HashMap<String, String>();
			for (OrdOrderItemMeta ordOrderItemMeta : orderItemMetas) {
				if (StringUtils.isBlank(ordOrderItemMeta.getProductIdSupplier())) {
					throw new IllegalArgumentException("代理产品编号不能空");
				}
				String ticketId=ordOrderItemMeta.getProductIdSupplier();
				Long quantity =ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity();
				params.put(ticketId,String.valueOf(quantity));
			}
			
			String ticketParam=sortTicketParam(params);
			StringBuilder targetUrl=new StringBuilder();
			targetUrl=targetUrl.append(WebServiceConstant.getProperties("alading.url")).append("order.ashx?");
			String signString = "MerchantId="+ WebServiceConstant.getProperties("alading.merchantId") + "&"
					+ ticketParam+"&Mobile="
					+ ordperson.getMobile()+"&SerialNumber="
					+ passCode.getSerialNo();
			String sign = null;
			
			sign = getSign(signString);
			targetUrl=targetUrl.append(signString).append("&sign=").append(sign);
			log.info("Alading order request: "+targetUrl.toString());
			startTime  = System.currentTimeMillis();
			String jsonResult = HttpsUtil.requestGet(targetUrl.toString());
			log.info("Alading order response: "+jsonResult);
			JSONObject jsonObj = JSONObject.fromObject(jsonResult);
			String returnCode = null;
			returnCode = jsonObj.getString("ReturnCode");
			String errorMsg = null;
			if (jsonObj.has("ErrroMsg")) {
				errorMsg = jsonObj.getString("ErrroMsg");
			}
			if (returnCode.equals("0")) {
				JSONArray jsonArray = jsonObj.getJSONArray("Codes");
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject temp = jsonArray.getJSONObject(i);
					if ("1".equals(temp.getString("CodeType"))) {
						String imageType="png";
						int size=7;
						byte[] imagebt = getImageCode(temp.getString("CodeString"),imageType,size);
						if (imagebt != null) {
							passport.setCodeImage(imagebt);
						}
						passport.setExtId(temp.getString("TicketSerial"));
						passport.setCode("BASE64");
						passport.setAddCode(temp.getString("AssistCode"));
						passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
					}
				}
			} else {
				passport.setComLogContent("供应商返回异常：" + "return code=" + returnCode + ",Message=" + errorMsg);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				this.reapplySet(passport, passCode.getReapplyCount());
			}
		} catch (NoSuchAlgorithmException e) {
			log.error("Alading resend serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Alading resend error message", e);
		} catch (Exception e) {
			log.error("Alading resend serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Alading resend error message", e);
		}
		return passport;
	}
	
	/**
	 * 重新申码
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}
	
	private String sortTicketParam(Map<String, String> params)throws Exception {
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
		return prestr;
	}
	
	private List<String> buildTargetId(List<PassPortCode> passPortCodeList) {
		List<String> targetIdlist = new ArrayList<String>();
		for (PassPortCode passPortCode : passPortCodeList) {
			targetIdlist.add(passPortCode.getTargetId().toString());
		}
		return targetIdlist;
	}
	
	@SuppressWarnings("rawtypes")
	private String join(String seperator, Iterator objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext()) {
			buf.append(objects.next());
		}
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}

	private static Set<PassCode> unusedList = new HashSet<PassCode>();
	private static Set<PassCode> usedList = new HashSet<PassCode>();

	@Override
	public Passport perform(PassCode passCode) {
		log.info("Alading perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdPerson ordperson = OrderUtil.init().getContract(ordorder);
            StringBuilder targetUrl=new StringBuilder();
            targetUrl=targetUrl.append(WebServiceConstant.getProperties("alading.url")).append("queryOrder.ashx?");
			String signString = "MerchantId="+ WebServiceConstant.getProperties("alading.merchantId")
					+ "&Mobile=" + ordperson.getMobile() + "&SerialNumber="
					+ passCode.getSerialNo() + "&TicketSerial="
					+ passCode.getExtId();
			String sign = null;
			try {
				sign = getSign(signString);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			targetUrl=targetUrl.append(signString).append("&sign=").append(sign);
			log.info("Alading queryOrder request: " + targetUrl.toString());
			String jsonResult = HttpsUtil.requestGet(targetUrl.toString());
			log.info("Alading queryOrder response: " + jsonResult);
			JSONObject jsonObj = JSONObject.fromObject(jsonResult);
			String returnCode = jsonObj.getString("ReturnCode");
			String errorMsg = null;
			if (jsonObj.has("ErrroMsg")) {
				errorMsg = jsonObj.getString("ErrroMsg");
			}
			if (returnCode.equals("0")&& jsonObj.getString("State").equals("2")) { // 已使用
				passport = new Passport();
				passport.setChild("0");
				passport.setAdult("0");
				passport.setUsedDate(new Date());
				passport.setDeviceId("Alading");
				stopCheckout(passCode);
			} else {
				stopCheckout(passCode);
				this.addComLog(passCode, jsonObj.getString("Msg"), "查询履行状态失败");
				log.info("Alading perform fail message: " + returnCode + " " + errorMsg);
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
		return "SUCCESS".equals(passCode.getStatus())
				&& isPassCodeUnused(passCode);
	}

	private void stopCheckout(PassCode passCode) {
		unusedList.remove(passCode);
		usedList.add(passCode);
	}

	private static String getSign(String targetUrl)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] bytes = md.digest((targetUrl.toLowerCase() + WebServiceConstant
				.getProperties("alading.key")).toLowerCase().getBytes());

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

	public static void main(String[] args) throws NoSuchAlgorithmException {
//		 getSign("MerchantId=100001&A=1&Mobile=18621635708&SerialNumber=20140313263380");
//		String res = HttpsUtil.requestGet("http://180.153.108.78:8090/order.ashx?MerchantId=100001&A=1&Mobile=18621635708&SerialNumber=20140313263380&sign=4A970C5FE726A6C43C1B273E0462CC29");
//		System.out.println(res);
	}
}