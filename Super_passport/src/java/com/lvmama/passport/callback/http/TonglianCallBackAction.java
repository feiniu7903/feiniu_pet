package com.lvmama.passport.callback.http;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import com.lvmama.BackBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.processor.UsedCodeProcessor;
import com.lvmama.passport.utils.WebServiceConstant;

public class TonglianCallBackAction extends BackBaseAction{
	private static final long serialVersionUID = 1L;
	private static final String CHARSET_GBK = "GBK";
	private ComLogService comLogService;
	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
	private String timestamp;//接口调用时的时间：yyyy-MM-dd HH:mm:ss
	private String sign;//签名
	private String seller_id;//商户号
	private String method ;//send:创建订单，Pay:支付,resend:重新发码,cancel:取消,refund:退款，get:获取订单详情consumed:消费后回调
	private String order_quantity;//订单总数
	private String use_quantity;//已消费张数
	private String order_id;//通联订单号
	private String consume_pwd;//新产生的消费串码，如果没有新产生，可为空
	@Action("/TonglianCallBack")
	public void TonglianCallBack() {
		log.info("tonglianCallBack  timestamp"+timestamp);
		log.info("tonglianCallBack  sign"+sign);
		log.info("tonglianCallBack  seller_id"+seller_id);
		log.info("tonglianCallBack  method"+method);
		log.info("tonglianCallBack  orderQuantity"+order_quantity);
		log.info("tonglianCallBack  useQuantity"+use_quantity);
		log.info("tonglianCallBack  order_id"+order_id);
		log.info("tonglianCallBack  consume_pwd"+consume_pwd);
		String result= "{\"code\":300,\"msg\":\"推送失败\"}";
		try{
			if(sign!=null && seller_id!=null & method!=null & order_quantity!=null
					&use_quantity!=null & order_id!=null){
				if(checkSign()){
					if(StringUtils.equals(method,"consumed")){
						Map<String, Object> data = new HashMap<String, Object>();
						data.put("extId", order_id);
						PassCode passCode = passCodeService.getPassCodeByParams(data);
						if (passCode != null) {
							List<PassPortCode> passPortCodeList = this.passCodeService
									.queryProviderByCode(passCode.getCodeId());
							PassPortCode passPortCode = passPortCodeList.get(0);
								if (passPortCode != null) {
									// 履行对象
									Long targetId = passPortCode.getTargetId();
									Map<String, Object> params = new HashMap<String, Object>();
									params.put("targetId", targetId);
									List<PassDevice> passDeviceList = this.passCodeService
											.searchPassDevice(params);
									Passport passport = new Passport();
									passport.setSerialno(passCode.getSerialNo());
									passport.setPortId(targetId);
									passport.setOutPortId(targetId.toString());
									if (passDeviceList != null && passDeviceList.size() > 0) {
										passport.setDeviceId(passDeviceList.get(0).getDeviceNo().toString());
									}
									passport.setChild("0");
									passport.setAdult("0");
									passport.setUsedDate(new Date());
									// 更新履行状态
									String code = usedCodeProcessor.update(passport);
									if ("SUCCESS".equals(code)) {
										result = "{\"code\":200,\"msg\":\"推送成功\"}";
										String content="通联订单号:"+order_id+",订单总数量："+order_quantity+",已消费票数量："+use_quantity;
										addComLog(passCode,content,"消费后回调接口");
									}
								}
							} 
					 }else{
						 result= "{\"code\":200,\"msg\":\"推送成功\"}"; 
					 }
				}else{
					result= "{\"code\":300,\"msg\":\"签名错误\"}";
				}
			}else{
				result= "{\"code\":300,\"msg\":\"参数不能为空\"}";
			}
		}catch(Exception e){
			result= "{\"code\":300,\"msg\":\"请求异常\"}";
			log.info(e.getMessage());
			
		}
		log.info("result: " + result);
		sendAjaxResultByJson(result);
	}
	
	
	public boolean checkSign() throws Exception{
		Map<String,String> params=new HashMap<String,String>();
		params.put("timestamp", timestamp);
		params.put("seller_id",seller_id);
		params.put("method",method);
		params.put("order_id",order_id);
		params.put("order_quantity",order_quantity);
		params.put("use_quantity", use_quantity);
		params.put("consume_pwd", consume_pwd);
		String signValue=this.sign(WebServiceConstant.getProperties("tonglian.key"),params);
		if(StringUtils.equals(signValue,sign)){
			return true;
		}
		return false;
	}
	
	/**
	 * 计算签名
	 * @param secret
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public  String sign(String secret, Map<String,String> data) throws IOException {
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
	
	private  byte[] encryptMD5(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes(CHARSET_GBK));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse.getMessage());
		}
		return bytes;
	}
	
	private  String byte2hex(byte[] bytes) {
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
	
	public  boolean isNotEmpty(String... values) {

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

	public  boolean isEmpty(String value) {

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
	
	//记录回调日志信息
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
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
	
	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}
	

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setOrder_quantity(String order_quantity) {
		this.order_quantity = order_quantity;
	}


	public void setUse_quantity(String use_quantity) {
		this.use_quantity = use_quantity;
	}


	public void setConsume_pwd(String consume_pwd) {
		this.consume_pwd = consume_pwd;
	}


	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}


	
}
