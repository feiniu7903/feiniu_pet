package com.lvmama.passport.processor.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
 * 锦绣中华接口实现
 * @author dingming
 *
 */
public class JXZHProcessorImpl implements ApplyCodeProcessor,DestroyCodeProcessor, OrderPerformProcessor, ResendCodeProcessor {
	
	private static final Log log = LogFactory.getLog(JXZHProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private ComLogService comLogService = (ComLogService) SpringBeanProxy.getBean("comLogService");
	
	@Override
	public Passport apply(PassCode passCode) {
		log.info("JXZH applyCode serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());

		Long startTime = 0L;
		try {
			Map<String,String> requestParams=buildApplyOrderParams(passCode);
			startTime  = System.currentTimeMillis();
			log.info("JXZH apply request:"+printLogInfo(requestParams));
			String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("jinxiuzhonghua.url")+"OtherOrder", requestParams);
			log.info("JXZH Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("JXZH apply jsonResult: " + jsonResult);
			JSONObject jsonObj = JSONObject.fromObject(jsonResult);
			JSONObject order=null;
			String code=null;;
			if(jsonObj.has("Result")){
				order=jsonObj.getJSONObject("Result").getJSONArray("Data").getJSONObject(0);
				if(order!=null){
					code=order.has("OtherOrder")?order.getString("OtherOrder"):null;
				}
			}
			if(code!=null&&code.length()==10){
				passport.setExtId(code);
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				passport.setComLogContent("供应商返回异常：" + "return code=" + code + ",Message=" + getOrderErrorMsg(code));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				this.reapplySet(passport, passCode.getReapplyCount());
				log.info("JXZH apply fail message: " + code + " " + getOrderErrorMsg(code));
			}
		} catch (Exception e) {
			log.error("JXZH Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("JXZH apply error message", e);
		}
			return passport;
	}

	/**
	 * 重新申码
	 */
	private void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}

	/**
	 * 重发短信
	 */
	@Override
	public Passport resend(PassCode passCode) {
		log.info("JXZH resend serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		passport.setSerialno(passCode.getSerialNo());
		
		Long startTime = 0L;
		try {
			Map<String,String> params=new HashMap<String, String>();
			params.put("OrderID", passCode.getExtId());
			startTime  = System.currentTimeMillis();
			log.info("JXZH resend request:"+printLogInfo(params));
			String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("jinxiuzhonghua.url")+"MobileMsg", params);
			log.info("JXZH resend serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("JXZH resend jsonResult: " + jsonResult);
			
			JSONObject jsonObj = JSONObject.fromObject(jsonResult);
			JSONObject result=null;
			String code=null;;
			if(jsonObj.has("Result")){
				result=jsonObj.getJSONObject("Result").getJSONArray("Data").getJSONObject(0);
				if(result!=null){
					code=result.has("MobileMsg")?result.getString("MobileMsg"):null;
				}
			}
			if(code!=null&&code.equals("1")){
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				passport.setComLogContent("供应商返回异常："+getResendErrorMsg(code));
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("JXZH resend fail message: " + code + " " + getResendErrorMsg(code));
			}
		} catch (Exception e) {
			log.error("JXZH resend serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage().substring(0,200));
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("JXZH resend error message", e);
		}
		return passport;
	}

	private static Set<PassCode> unusedList = new HashSet<PassCode>();
	private static Set<PassCode> usedList = new HashSet<PassCode>();
	
	@Override
	public Passport perform(PassCode passCode) {
		log.info("JXZH perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		Long startTime = 0L;
		if (isNeedCheckout(passCode)) {
			try {
				Map<String,String> params=new HashMap<String, String>();
				params.put("Loginname", WebServiceConstant.getProperties("jinxiuzhonghua.userName"));
				params.put("Password", WebServiceConstant.getProperties("jinxiuzhonghua.password"));
				params.put("OrderBeginTime", "1990-01-01");
				params.put("OrderEndTime", "2090-01-01");
				params.put("GoParkBeginTime", "1990-01-01");
				params.put("GoParkEndTime", "2090-01-01");
				params.put("OrderState", "0");
				params.put("orderNo", passCode.getExtId());
				startTime  = System.currentTimeMillis();
				log.info("JXZH perform request:"+printLogInfo(params));
				String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("jinxiuzhonghua.url")+"GetOrderList", params);
				log.info("JXZH perform serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
				log.info("JXZH perform jsonResult: " + jsonResult);
				
				JSONObject jsonObj = JSONObject.fromObject(jsonResult);
				JSONObject result=null;
				String code=null;;
				if(jsonObj.has("Result")){
					result=jsonObj.getJSONObject("Result").getJSONArray("Data").getJSONObject(0);
					if(result!=null){
						code=result.has("statecode")?result.getString("statecode"):null;
					}
				}
				
				if(code!=null&&code.equals("已入园")){
					passport = new Passport();
					passport.setChild("0");
					passport.setAdult("0");
					passport.setUsedDate(new Date());
					passport.setDeviceId("JXZH");
					stopCheckout(passCode);
				}else{
					stopCheckout(passCode);
					this.addComLog(passCode, result.has("statecode")?result.getString("statecode"):"没有订单状态属性", "查询履行状态失败");
					log.info("JXZH perform fail message: " + code);
				}
			} catch (Exception e) {
				log.error("JXZH perform error message", e);
			}
		}
		return passport;
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
	
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("JXZH destroy serialNo: " + passCode.getSerialNo());
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		Long startTime = 0L;
		
		try {
			Map<String,String> params=new HashMap<String, String>();
			buildHeadParams(params);
			params.put("OrderID", passCode.getExtId());
			startTime  = System.currentTimeMillis();
			log.info("JXZH destroy request:"+printLogInfo(params));
			String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("jinxiuzhonghua.url")+"CancelOrder", params);
			log.info("JXZH destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("JXZH destroy jsonResult: " + jsonResult);
			
			JSONObject jsonObj = JSONObject.fromObject(jsonResult);
			JSONObject result=null;
			String code=null;;
			if(jsonObj.has("Result")){
				result=jsonObj.getJSONObject("Result").getJSONArray("Data").getJSONObject(0);
				if(result!=null){
					code=result.has("CancelOrder")?result.getString("CancelOrder"):null;
				}
			}
			if(code!=null&&code.equals("0")){
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				passport.setComLogContent("订单取消失败");
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("JXZH destroy fail message: " + code + "订单取消失败");
			}
		} catch (Exception e) {
			log.error("JXZH destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("JXZH destroy error message", e);
		}
		return passport;
	}
	
	/**
	 * 构建申码下单请求参数
	 * @param passCode
	 * @return
	 * @throws IOException
	 */
	private Map<String, String>  buildApplyOrderParams(PassCode passCode){
		String serialNo = passCode.getSerialNo();
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson =OrderUtil.init().getContract(ordorder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		String inDate = DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd");
		
		String productIdSupplier = itemMeta.getProductIdSupplier();
		if (StringUtils.isBlank(productIdSupplier)) {
			throw new IllegalArgumentException("代理产品编号不能空");
		}
		Map<String,String> params=new HashMap<String,String>();
		buildHeadParams(params);
		params.put("otherorderNO", serialNo);
		params.put("ProductID", productIdSupplier);
		params.put("GoParkDate",inDate);
		params.put("Name", ordperson.getName());
		params.put("Card", ordperson.getCertNo()==null?"":ordperson.getCertNo().substring(ordperson.getCertNo().length()-8, ordperson.getCertNo().length()));
		params.put("Mobile", ordperson.getMobile());
		params.put("TicketNum", itemMeta.getAdultQuantity().toString());
		params.put("ChildNum", itemMeta.getChildQuantity().toString());
		params.put("OldNum", "0");
		return params;
	}
	
	/**
	 * 打印map参数log
	 * @param params
	 * @return
	 */
	private static String printLogInfo(Map<String,String> params){
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
	
	/**
	 * 构建请求头
	 * @param map
	 * @return
	 */
	private void buildHeadParams(Map<String,String> map){
		map.put("UserName", WebServiceConstant.getProperties("jinxiuzhonghua.userName"));
		map.put("Password", WebServiceConstant.getProperties("jinxiuzhonghua.password"));
	}
	
	/**
	 * 下单错误信息封装
	 * @param code
	 * @return
	 */
	private String getOrderErrorMsg(String code){
		Map<String,String> codeMsg=new HashMap<String, String>();
		codeMsg.put("-1", "帐号密码不正确");
		codeMsg.put("-27", "余额不足");
		codeMsg.put("-3", "此产品已经下线");
		codeMsg.put("-4", "不在有效期范围内");
		codeMsg.put("-5", "日期无效");
		codeMsg.put("-6", "输入证件号码");
		codeMsg.put("-7", "输入客人姓名");
		codeMsg.put("-8", "人数输入错误");
		codeMsg.put("-9", "其它原因");
		codeMsg.put("-41", "5秒内不能下相同的订单");
		codeMsg.put("-42", "该订单已经成功下单");
		codeMsg.put("-99", "身份证不是8位");
		return codeMsg.get(code);
	}
	
	/**
	 * 下单错误信息封装
	 * @param code
	 * @return
	 */
	private String getResendErrorMsg(String code){
		Map<String,String> codeMsg=new HashMap<String, String>();
		codeMsg.put("-1", "找不到该订单");
		codeMsg.put("-2", "不在有效期");
		codeMsg.put("-3", "订单是取消状态");
		codeMsg.put("-4", "客人已经入园");
		return codeMsg.get(code);
	}
	
	public static void main(String[] args) {
	
		/*Map<String,String> params=new HashMap<String, String>();
		params.put("UserName", WebServiceConstant.getProperties("jinxiuzhonghua.userName"));
		params.put("Password", WebServiceConstant.getProperties("jinxiuzhonghua.password"));
		log.info("JXZH getTicketList request:"+printLogInfo(params));
		String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("jinxiuzhonghua.url")+"GetTicketList", params);
		System.out.println(jsonResult);*/
		
		
		
		/*Map<String,String> params=new HashMap<String,String>();
		params.put("UserName", WebServiceConstant.getProperties("jinxiuzhonghua.userName"));
		params.put("Password", WebServiceConstant.getProperties("jinxiuzhonghua.password"));
		params.put("otherorderNO", "20140409263472");
		params.put("ProductID", "7");
		params.put("GoParkDate","2014-04-15");
		params.put("Name", "测试");
		params.put("Card", "09037817");
		params.put("Mobile", "15102195697");
		params.put("TicketNum", "1");
		params.put("ChildNum", "0");
		params.put("OldNum", "0");
		
		log.info("JXZH getTicketList request:"+printLogInfo(params));
		String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("jinxiuzhonghua.url")+"OtherOrder", params);
		System.out.println(jsonResult);*/
		
		
		/*String jsonResult="{\"Status\":200,\"Result\":{\"Count\":1,\"Data\":[{\"OtherOrder\":\"6030050866\"}]}}";
		JSONObject jsonObj = JSONObject.fromObject(jsonResult);
		JSONObject result=jsonObj.getJSONObject("Result");
		JSONArray  array=result.getJSONArray("Data");
		JSONObject order=array.getJSONObject(0);
		String data=order.getString("OtherOrder");
		System.out.println(data);*/
		
		/*Map<String,String> params=new HashMap<String, String>();
		params.put("OrderID", "7179050867");
		log.info("JXZH resend request:"+printLogInfo(params));
		String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("jinxiuzhonghua.url")+"MobileMsg", params);
		System.out.println(jsonResult);*/
		
		Map<String,String> params=new HashMap<String, String>();
		params.put("Loginname", WebServiceConstant.getProperties("jinxiuzhonghua.userName"));
		params.put("Password", WebServiceConstant.getProperties("jinxiuzhonghua.password"));
		params.put("OrderBeginTime", "1990-01-01");
		params.put("OrderEndTime", "2090-01-01");
		params.put("GoParkBeginTime", "1990-01-01");
		params.put("GoParkEndTime", "2090-01-01");
		params.put("OrderState", "0");
		params.put("orderNo", "7309050878");
		log.info("JXZH perform request:"+printLogInfo(params));
		String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("jinxiuzhonghua.url")+"GetOrderList", params);
		System.out.println(jsonResult);
		
		/*Map<String,String> params=new HashMap<String, String>();
		params.put("UserName", WebServiceConstant.getProperties("jinxiuzhonghua.userName"));
		params.put("Password", WebServiceConstant.getProperties("jinxiuzhonghua.password"));
		params.put("OrderID", "7179050867");
		log.info("JXZH destroy request:"+printLogInfo(params));
		String jsonResult=HttpsUtil.requestPostForm(WebServiceConstant.getProperties("jinxiuzhonghua.url")+"CancelOrder", params);
		System.out.println(jsonResult);*/
	}

}
