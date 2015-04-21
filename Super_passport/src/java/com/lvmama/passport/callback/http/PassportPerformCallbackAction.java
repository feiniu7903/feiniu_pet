package com.lvmama.passport.callback.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lvmama.BackBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassDeviceService;
import com.lvmama.comm.bee.service.pass.PassPortCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.passport.processor.UsedCodeProcessor;
import com.lvmama.passport.processor.impl.client.shouxihu.ShouxihuUtil;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHRequestBody;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHRequestInfo;

/**
 * 第三方回调驴妈妈接口实现
 * @author lipengcheng
 *
 */
public class PassportPerformCallbackAction extends BackBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3227406740860332975L;
	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
	private String orderId;
	private String date;
	private String adultQuantity;
	private String childQuantity;
	private String xml;
	
	@Action("/performCallback")
	public void performCallback() {
		log.info("orderId: " + orderId);
		log.info("date: " + date);
		log.info("adultQuantity: " + adultQuantity);
		log.info("childQuantity: " + childQuantity);
		String result = "0";
		if (adultQuantity == null || "".equals(adultQuantity)) {
			adultQuantity = "0";
		}
		if (childQuantity == null || "".equals(childQuantity)) {
			childQuantity = "0";
		}
		try {
			Date useDate = DateUtil.toDate(date, "yyyyMMddHHmmss");
			if (orderId != null && useDate != null) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("extId", orderId.trim());
				PassCode passCode = passCodeService.getPassCodeByParams(data);
				if (passCode != null) {
					List<PassPortCode> passPortCodeList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
					PassPortCode passPortCode = passPortCodeList.get(0);
					if (passPortCode != null) {
						// 履行对象
						Long targetId = passPortCode.getTargetId();
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("targetId", targetId);
						List<PassDevice> passDeviceList = this.passCodeService.searchPassDevice(params);
						Passport passport = new Passport();
						passport.setSerialno(passCode.getSerialNo());
						passport.setPortId(targetId);
						passport.setOutPortId(targetId.toString());
						if (passDeviceList != null && passDeviceList.size() > 0) {
							passport.setDeviceId(passDeviceList.get(0).getDeviceNo().toString());
						}
						passport.setChild(childQuantity);
						passport.setAdult(adultQuantity);
						passport.setUsedDate(useDate);
						// 更新履行状态
						String code = usedCodeProcessor.update(passport);
						if ("SUCCESS".equals(code)) {
							result = "1";
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("performCallback error: ", e);
		}
		log.info("result: " + result);
		sendAjaxMsg(result);
	}

	@Action("/syncOrderForWaterCube")
	public void syncOrderForWaterCube() {
		String result = "error";
		log.info("Watercube requestXml: " + xml);
		if (xml != null && !"".equals(xml)) {
			try {
				Document dom = DocumentHelper.parseText(xml);
				Element root = dom.getRootElement();
				Element order = root.element("order");
				String orderNum = order.element("order_num").getText();
				String useDate = order.element("use_time").getText();
				PassCode passCode = null;
				if (orderNum != null && !"".equals(orderNum)) {
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("extId", orderNum.trim());
					passCode = passCodeService.getPassCodeByParams(data);
				}
				if (passCode != null) {
					List<PassPortCode> passPortCodeList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
					PassPortCode passPortCode = passPortCodeList.get(0);
					if (passPortCode != null) {
						// 履行对象
						Long targetId = passPortCode.getTargetId();
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("targetId", targetId);
						List<PassDevice> passDeviceList = this.passCodeService.searchPassDevice(params);
						Passport passport = new Passport();
						passport.setSerialno(passCode.getSerialNo());
						passport.setPortId(targetId);
						passport.setOutPortId(targetId.toString());
						if (passDeviceList != null && passDeviceList.size() > 0) {
							passport.setDeviceId(passDeviceList.get(0).getDeviceNo().toString());
						}
						passport.setChild("0");
						passport.setAdult("0");
						passport.setUsedDate(DateUtil.toDate(useDate, "yyyy-MM-dd HH:mm:ss"));

						// 更新履行状态
						String code = usedCodeProcessor.update(passport);
						if ("SUCCESS".equals(code)) {
							result = "success";
						}
					}
				}
			} catch (Exception e) {
				log.error("sync error: ", e);
			}
		}
		log.info("Watercube responseXml: " + result);
		sendAjaxMsg(result);
	}
	
	@Action("/performCallbackForShouxihu")
	public void performCallbackForShouxihu() {
		String result = "FALIED";
		String requestXml = this.getRequestData();
		log.info("shouxihu perform callback requestXml: " + requestXml);
		SXHRequestInfo requestInfo = ShouxihuUtil.callBackInfo(requestXml);
		SXHRequestBody body = requestInfo.getBody();
		String orderId = body.getSerialId();
		Date useDate = DateUtil.toDate(body.getTicketDate(), "yyyy-MM-dd HH:mm:ss");
		if (orderId != null && useDate != null) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("extId", orderId.trim());
			PassCode passCode = passCodeService.getPassCodeByParams(data);
			if (passCode != null) {
				List<PassPortCode> passPortCodeList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
				PassPortCode passPortCode = passPortCodeList.get(0);
				if (passPortCode != null) {
					// 履行对象
					Long targetId = passPortCode.getTargetId();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("targetId", targetId);
					List<PassDevice> passDeviceList = this.passCodeService.searchPassDevice(params);
					Passport passport = new Passport();
					passport.setSerialno(passCode.getSerialNo());
					passport.setPortId(targetId);
					passport.setOutPortId(targetId.toString());
					if (passDeviceList != null && passDeviceList.size() > 0) {
						passport.setDeviceId(passDeviceList.get(0).getDeviceNo().toString());
					}
					passport.setChild("0");
					passport.setAdult("0");
					passport.setUsedDate(useDate);
					// 更新履行状态
					result = usedCodeProcessor.update(passport);
				}
			}
		}
		log.info("shouxihu perform callback result: " + result);
	}
	
	/**
	 * 获得请求数据
	 * 
	 * @return
	 */
	private String getRequestData() {
		StringBuilder result = new StringBuilder();
		InputStream input = null;
		BufferedReader in = null;
		try {
			input = this.getRequest().getInputStream();
			in = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			String line = "";
			while ((line = in.readLine()) != null) {
				result.append(line.trim());
			}
			log.info("callback request:" + result.toString());
		} catch (Exception ex) {
			log.error("callback request error:", ex);
		} finally {
			try {
				if (input != null)
					input.close();
				if (in != null)
					in.close();
			} catch (Exception ex) {
				log.error("callback request error:", ex);
			}
		}
		return result.toString();
	}
	
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
 
	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}
  
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAdultQuantity() {
		return adultQuantity;
	}

	public void setAdultQuantity(String adultQuantity) {
		this.adultQuantity = adultQuantity;
	}

	public String getChildQuantity() {
		return childQuantity;
	}

	public void setChildQuantity(String childQuantity) {
		this.childQuantity = childQuantity;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
	
}
