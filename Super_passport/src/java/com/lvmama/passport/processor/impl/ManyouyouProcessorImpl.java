package com.lvmama.passport.processor.impl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.client.manyouyou.SubmitRequest;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 慢悠悠
 * 
 * @author tangJing
 */
public class ManyouyouProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor,ResendCodeProcessor,OrderPerformProcessor {
	private static final Log log = LogFactory.getLog(ManyouyouProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	/**
	 * 申请码
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Manyouyou apply serialNo: " + passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
		try {
			SubmitRequest request=this.buildOrder(passCode);
			Map<String, String> params = new HashMap<String, String>();
			params.put("method",request.getMethod());
			params.put("format",request.getFormat());
			params.put("_pid", request.get_pid());
			params.put("orders_id", request.getOrders_id());
			params.put("item_id", request.getItem_id());
			params.put("size", request.getSize());
			params.put("name", request.getName());
			params.put("mobile", request.getMobile());
			params.put("start_date", request.getStart_date());
			String sig = makeSign(params);
			params.put("_sig",sig);
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("manyouyou.url"), params);
			startTime = System.currentTimeMillis();
			log.info("Manyouyou applay serialNo :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				passport.setComLogContent("供应商返回异常："+ result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				this.reapplySet(passport, passCode.getReapplyCount());
			} else {
				log.info("Manyouyou applay resXml: " + result);
				String status = TemplateUtils.getElementValue(result,"//root/success");
				if (StringUtils.equals(status,"1")) {
					String partnerId = TemplateUtils.getElementValue(result,"//root/info/id");
					passport.setAddCode(partnerId);
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				}else {
					String errorn = TemplateUtils.getElementValue(result,"//root/errorn");
					passport.setComLogContent("供应商返回异常："+ convertErrorCode_Order(errorn));
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
					log.info("Manyouyou apply fail message: "+ convertErrorCode_Order(errorn));
				}
			}
		} catch (Exception e) {
			log.error("Manyouyou applay serialNo Error :"+ passCode.getSerialNo() + " UseTime:"+ (System.currentTimeMillis() - startTime) / 1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("Manyouyou applay message", e);
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
		log.info("Manyouyou destroy serialNo: " + passCode.getSerialNo());
		String pid=WebServiceConstant.getProperties("manyouyou.pid");
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			startTime=System.currentTimeMillis();
			Map<String, String> params = new HashMap<String, String>();
			params.put("method","item_refund");
			params.put("format","xml");
			params.put("_pid",pid);
			params.put("orders_id",passCode.getAddCode());
			String sign=makeSign(params);
			params.put("_sig",sign);
			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("manyouyou.url"), params);
			log.info("Manyouyou destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setComLogContent("供应商返回异常："+result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			}else{
				log.info("Manyouyou destroy resXml: " + result);
				String status = TemplateUtils.getElementValue(result, "//root/success");			
				if (StringUtils.equals(status, "1")) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					String errorn = TemplateUtils.getElementValue(result, "//root/errorn");
					passport.setComLogContent("供应商返回异常："+ convertErrorCode_Order(errorn));
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Manyouyou destroy fail message: " + convertErrorCode_Order(errorn));
				}
			}
		} catch (Exception e) {
			log.error("Manyouyou destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("Manyouyou destroy error message", e);
		}
		return passport;
	}
	

	@Override
	public Passport resend(PassCode passCode) {
		log.info("Manyouyou Resend SMS ：" + passCode.getSerialNo());
		String pid = WebServiceConstant.getProperties("manyouyou.pid");
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("method", "item_orders_resend");
			params.put("format", "xml");
			params.put("_pid", pid);
			params.put("orders_id", passCode.getAddCode());
			String sign = makeSign(params);
			params.put("_sig", sign);
			String result = HttpsUtil.requestPostForm(
					WebServiceConstant.getProperties("manyouyou.url"), params);
			if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
				passport.setComLogContent("供应商返回异常："+ result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
			} else {
				String status = TemplateUtils.getElementValue(result,"//root/success");
				if (StringUtils.equals(status, "1")) {
					passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				} else {
					String errorn = TemplateUtils.getElementValue(result,"//root/errorn");
					passport.setComLogContent("供应商返回异常：" +  convertErrorCode_Order(errorn));
					passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
					passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
					log.info("Manyouyou resend fail message: " +  convertErrorCode_Order(errorn));
				}
			}

		} catch (Exception e) {
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
			passport.setComLogContent(e.getMessage());
			log.error("ManyouyouProcessorImp resend Error", e);
		}
		passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
		return passport;
	}

	
	private static Set<PassCode> unusedList = new HashSet<PassCode>();
	private static Set<PassCode> usedList = new HashSet<PassCode>();
	
	/**
	 * 更新订单履行状态
	 */
	@Override
	public Passport perform(PassCode passCode) {
		log.info("manyouyou perform serialNo: " + passCode.getSerialNo());
		Passport passport = null;
		if (isNeedCheckout(passCode)) {
			try {
				String orderNo=passCode.getAddCode();
				String pid=WebServiceConstant.getProperties("manyouyou.pid");
				Map<String, String> params = new HashMap<String, String>();
				params.put("method","orders_list");
				params.put("format","xml");
				params.put("_pid",pid);
				params.put("orders_id",orderNo);
				String sign=makeSign(params);
				params.put("_sig",sign);
				String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("manyouyou.url"), params);
				if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
					log.info(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
				}else{
					log.info("manyouyou perform resXml: " + result);
					String status = TemplateUtils.getElementValue(result,"//root/success");
					if (StringUtils.equals(status,"1")) {
						String validNum=TemplateUtils.getElementValue(result,"//root/list/item/valid_amount");
						if (StringUtils.equals(validNum, "0")) {//已入园
							passport = new Passport();
							passport.setChild("0");
							passport.setAdult("0");
							passport.setUsedDate(new Date());
							passport.setDeviceId("manyouyou");
							stopCheckout(passCode);
						}
					}
				}
			} catch(Exception e) {
				log.error("manyouyou perform error message", e);
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
	
	

	private SubmitRequest buildOrder(PassCode passCode)throws Exception{
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			OrdPerson ordPerson =OrderUtil.init().getContract(ordOrder);
			OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
			Long count = itemMeta.getProductQuantity() * itemMeta.getQuantity();
			String productIdSupplier = itemMeta.getProductIdSupplier();
			String visiteTime = DateFormatUtils.format(ordOrder.getVisitTime(),"yyyy-MM-dd");
			if (StringUtils.isBlank(productIdSupplier)) {
				throw new IllegalArgumentException("代理产品编号不能空");
			}
			String _pid=WebServiceConstant.getProperties("manyouyou.pid");
			SubmitRequest req = new SubmitRequest();
			req.setMethod("item_orders");
			req.setFormat("xml");
			req.set_pid(_pid);
			req.setOrders_id(passCode.getSerialNo());
			req.setItem_id(productIdSupplier.trim());
			req.setSize(String.valueOf(count));
			req.setName(ordPerson.getName());
			req.setMobile(ordPerson.getMobile());
			req.setStart_date(visiteTime);
		return req;
	}
	
	public static String convertErrorCode_Order(String code_id){
		Map<String,String> codeMap=new HashMap<String, String>();
		codeMap.put("300500", "没有数据");
		codeMap.put("300501", "参数错误");
		codeMap.put("300502", "用户不存在");
		codeMap.put("300503", "用户未开通接口授权");
		codeMap.put("300504", "授权码错误");
		codeMap.put("300505", "票不存在");
		codeMap.put("300506", "票已过期");
		codeMap.put("300507", "票数不足");
		codeMap.put("300508", "获取分销商分组失败");
		codeMap.put("300509", "获取景区失败");
		codeMap.put("300510", "余额不足");
		codeMap.put("300511", "短信发送失败");
		codeMap.put("300512", "余额更新失败");
		codeMap.put("300513", "票数修改失败");
		codeMap.put("300514", "订单添加失败");
		codeMap.put("300515", "出票失败");
		codeMap.put("300516", "用户状态异常");
		codeMap.put("300517", "获取代理商信息失败");
		codeMap.put("300518", "订单不存在");
		codeMap.put("300519", "门票数不足");
		codeMap.put("300520", "订单修改失败");
		codeMap.put("300521", "不允许退票");
		codeMap.put("300522", "退票类型无效");
		codeMap.put("300523", "退票审核中,不允许重复申请");
		codeMap.put("300524", "余额信息修改失败");
		codeMap.put("300525", "余额记录添加失败");
		codeMap.put("300526", "产品未定价，无法下单");
		codeMap.put("300527", "管理员审核发送的产品必须发送短信");
		codeMap.put("300528", "导码产品不可以从淘宝下单");
		codeMap.put("300529", "获取码号与二维码失败");
		codeMap.put("300530", "导码状态更新失败");
		codeMap.put("300531", "购买数错误");
		codeMap.put("300532", "超过最晚下单时间");
		codeMap.put("300533", "没有跨站请求权限");
		codeMap.put("300534", "补发次数超出限制次数");
		return codeMap.get(code_id);
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
		String authcode=WebServiceConstant.getProperties("manyouyou.authcode");
		System.out.println("MD532 before strvalue:"+prestr);
		String sign =MD5.encode32(authcode+"&"+prestr+"&"+authcode);
		return sign;
	}
	
	
	public static void main(String[] args) {
		try {
//			Map<String, String> params = new LinkedHashMap<String, String>();
//			params.put("method", "item_orders");
//			params.put("format", "xml");
//			params.put("_pid", "32");
//			params.put("orders_id", "201308280953");
//			params.put("item_id", "512");
//			params.put("size", "1");
//			params.put("name", "汤静");
//			params.put("mobile", "15026847838");
//			params.put("start_date", "2013-08-29");
//			String sig = makeSign(params);
//			params.put("_sig", sig);
//			String result = HttpsUtil.requestPostForm(
//					WebServiceConstant.getProperties("manyouyou.url"), params);
//			System.out.println(result);
			
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("method","item_refund");
//			params.put("format","xml");
//			params.put("_pid","32");
//			params.put("orders_id","646670");
//			String sign=makeSign(params);
//			params.put("_sig",sign);
//			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("manyouyou.url"), params);

//			Map<String, String> params = new HashMap<String, String>();
//			params.put("method","orders_list");
//			params.put("format","xml");
//			params.put("_pid","32");
//			params.put("orders_id","646670");
//			String sign=makeSign(params);
//			params.put("_sig",sign);
//			String result = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("manyouyou.url"), params);
//			System.out.println(result);
			
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("method", "item_orders_resend");
//			params.put("format", "xml");
//			params.put("_pid", "32");
//			params.put("orders_id","734461");
//			String sign = makeSign(params);
//			params.put("_sig", sign);
//			String result = HttpsUtil.requestPostForm(
//					WebServiceConstant.getProperties("manyouyou.url"), params);
//			System.out.println(result);
		} catch (Exception e) {

		}
	}

}