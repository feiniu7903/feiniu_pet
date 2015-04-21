package com.lvmama.clutter.message;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobilePersonItem;
import com.lvmama.clutter.model.sumsang.Alert;
import com.lvmama.clutter.model.sumsang.Data;
import com.lvmama.clutter.model.sumsang.DateAlert;
import com.lvmama.clutter.model.sumsang.ElementDataBarcode;
import com.lvmama.clutter.model.sumsang.ElementDataImage;
import com.lvmama.clutter.model.sumsang.ElementDataText;
import com.lvmama.clutter.model.sumsang.GeofenceAlert;
import com.lvmama.clutter.model.sumsang.Head;
import com.lvmama.clutter.model.sumsang.P;
import com.lvmama.clutter.model.sumsang.Partner;
import com.lvmama.clutter.model.sumsang.ValidUntil;
import com.lvmama.clutter.model.sumsang.View;
import com.lvmama.clutter.service.IClientUserService;
import com.lvmama.clutter.utils.JSONUtil;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.mobile.MobileOrderRelationSamsung;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.vo.Constant;

public class OrderMessageProcesser implements MessageProcesser {
	private Logger logger = Logger.getLogger(this.getClass());
	protected MobileClientService mobileClientService;

	protected IClientUserService clientUserService;

	@Override
	public void process(Message message) {
		logger.info("订单处理jms类型："+message.getEventType());
		if (message.isOrderCancelMsg()) {
			logger.info("==================cancal order process=================");
			long orderId = message.getObjectId();
			
			MobileOrderRelationSamsung result = mobileClientService
					.selectByOrderId(orderId);
			if(null!=result){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("orderId", orderId);
				param.put("serial", result.getSerial());
				try {
					this.updateTicket(param);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 更新ticket
	 * 
	 * @param param
	 * @return
	 */
	public void updateTicket(Map<String, Object> param) {
		MobileOrder mobileOrder = clientUserService.getOrder(param);
		String serial = (String) param.get("serial");
		String mainProduceType = mobileOrder.getMainProductType();
		if (Constant.PRODUCT_TYPE.ROUTE.name().equals(mainProduceType)) {
			this.sendRoute(mobileOrder, serial);
		} else if (Constant.PRODUCT_TYPE.TICKET.name().equals(mainProduceType)) {
			this.sendTicket(mobileOrder, serial);
		} else {

		}
	}

	private String sendRoute(MobileOrder mobileOrder, String serial) {
		Data data = new Data();

		Head head = new Head();
		head.setVersion("1.1");
		if (!StringUtils.isEmpty(serial)) {
			head.setSerial(serial);
		} else {
			head.setSerial(String.valueOf(System.currentTimeMillis()));
		}

		head.setSkinId("1646c78f-e61b-3823-a329-1c3fe04b5d4b");
		head.setKeywords(new String[] { "lvmama", "route" });
		head.setStorable(true);

		String format = "yyyy/MM/dd HH:mm";
		ValidUntil validUntil = new ValidUntil();
		Date arrivalDateRedOne = DateUtils.addDays(Calendar.getInstance()
				.getTime(), -1);
		validUntil.setValue(DateFormatUtils.format(arrivalDateRedOne, format));
		validUntil.setFormat(format);
		head.setValidUntil(validUntil);
		data.setHead(head);

		// views
		List<View> views = new ArrayList<View>();

		// barcode
		View main_barcode_a = new View();
		main_barcode_a.setId("MAIN_BARCODE_a");

		ElementDataBarcode barcode = new ElementDataBarcode();
		barcode.setCaption(String.valueOf(mobileOrder.getOrderId()));
		barcode.setType("QRCODE");
		barcode.setValue(String.valueOf(mobileOrder.getOrderId()));

		main_barcode_a.setBarcode(barcode);
		views.add(main_barcode_a);

		// list text1
		View abstract_value2 = new View();
		abstract_value2.setId("ABSTRACT_VALUE2");

		ElementDataText abstract_value2Text = new ElementDataText();
		abstract_value2Text.setValue(mobileOrder.getVisitTime());
		abstract_value2Text.setAlign("middle");

		abstract_value2.setText(abstract_value2Text);
		views.add(abstract_value2);

		// list text2
		View abstract_value1 = new View();
		abstract_value1.setId("ABSTRACT_VALUE1");

		ElementDataText abstract_value1Text = new ElementDataText();
		abstract_value1Text.setValue(mobileOrder.getCityName() + "游玩");
		abstract_value1Text.setAlign("middle");

		abstract_value1.setText(abstract_value1Text);
		views.add(abstract_value1);

		// 订单号
		View main_top_value1_a = new View();
		main_top_value1_a.setId("MAIN_TOP_VALUE1_a");

		ElementDataText leftTopText = new ElementDataText();
		leftTopText.setValue(String.valueOf(mobileOrder.getOrderId()));
		leftTopText.setAlign("left");

		main_top_value1_a.setText(leftTopText);
		views.add(main_top_value1_a);

		// from text
		View main_middle_value1_a = new View();
		main_middle_value1_a.setId("MAIN_MIDDLE_VALUE2_a");

		ElementDataText leftMiddleText = new ElementDataText();
		leftMiddleText.setValue(mobileOrder.getFromPlaceName());
		leftMiddleText.setAlign("left");

		main_middle_value1_a.setText(leftMiddleText);
		views.add(main_middle_value1_a);

		// dest text
		View main_middle_value1_c = new View();
		main_middle_value1_c.setId("MAIN_MIDDLE_VALUE2_c");

		ElementDataText rightMiddleText = new ElementDataText();
		rightMiddleText.setValue(mobileOrder.getDestPlaceName());
		rightMiddleText.setAlign("left");

		main_middle_value1_c.setText(rightMiddleText);
		views.add(main_middle_value1_c);

		// 游玩人
		List<MobilePersonItem> persons = mobileOrder.getListPerson();
		StringBuffer buffer = new StringBuffer();
		if (null != persons && persons.size() > 0) {// 默认显示两个游玩人，如果大于2个则后面加“等”字
			for (int i = 0; i < persons.size(); i++) {
				MobilePersonItem person = persons.get(i);
				if ("TRAVELLER".equals(person.getPersonType())) {
					buffer.append(person.getPersonName() + ",");
					if (i >= 2) {
						buffer.append("等");
						break;
					}
				}
			}
			if (buffer.lastIndexOf(",") != -1) {// 订单信息中包含游玩人
				buffer.deleteCharAt(buffer.lastIndexOf(","));
			} else {// 订单信息中包含游玩人，显示订单联系人
				for (int i = 0; i < persons.size(); i++) {
					MobilePersonItem person = persons.get(i);
					if ("CONTACT".equals(person.getPersonType())) {
						buffer.append(person.getPersonName());
					}
				}
			}
		}
		View main_bottom_value1_a = new View();
		main_bottom_value1_a.setId("MAIN_BOTTOM_VALUE1_a");

		ElementDataText leftBottomText1 = new ElementDataText();
		leftBottomText1.setValue(buffer.toString());
		leftBottomText1.setAlign("left");

		main_bottom_value1_a.setText(leftBottomText1);
		views.add(main_bottom_value1_a);

		// 游玩时间
		View main_aux1_value1_c = new View();
		main_aux1_value1_c.setId("MAIN_AUX1_VALUE1_a");

		ElementDataText rightText = new ElementDataText();
		rightText.setValue(mobileOrder.getVisitTime());
		rightText.setAlign("left");

		main_aux1_value1_c.setText(rightText);
		views.add(main_aux1_value1_c);

		data.setView(views);

		List<Alert> alerts = new ArrayList<Alert>();
		Alert geofenceAlert = new Alert();
		GeofenceAlert geofence = new GeofenceAlert();
		geofence.setAltitude(null);
		geofence.setLatitude(mobileOrder.getBaiduLatitude());
		geofence.setLongitude(mobileOrder.getBaiduLongitude());
		geofence.setRangeinmeter(10);

		geofenceAlert.setId("ALERT1");
		geofenceAlert.setGeofence(geofence);

		alerts.add(geofenceAlert);

		Alert timeAlert = new Alert();
		DateAlert date = new DateAlert();
		date.setBeforeinmin(5);
		date.setFormat(format);
		String visitTime = mobileOrder.getVisitTime();
		Date visitDate = null;
		try {
			visitDate = DateUtils.parseDate(visitTime, "yyyy-MM-dd");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (null != visitDate) {
			date.setValue(DateFormatUtils.format(
					DateUtils.addDays(visitDate, -2), format));
		}
		timeAlert.setDate(date);
		alerts.add(timeAlert);

		data.setAlerts(alerts);

		List<Partner> partners = new ArrayList<Partner>();
		Partner partner = new Partner();
		P p = new P();
		partner.setPartner(p);
		partners.add(partner);
		data.setPartners(partners);

		System.out.println(data);

		HttpClient httpClient = new HttpClient();
//		PostMethod postMethod = new PostMethod(
//				"https://api.wallet.samsung.com/tkt/tickets");
		PostMethod postMethod = new PostMethod(MessageFormat.format("https://api.wallet.samsung.com/tkt/tickets?serial={0}", serial));
		String authorization = new String(
				Base64.encodeBase64("28bc72a1026d4239900235983ce17961:b0bd12e3b7b74b2fa11f4492504f1675"
						.getBytes()));
		System.out.println(authorization);

		postMethod.setRequestHeader("X-TKT-Protocol-Version", "1.1");
		postMethod.setRequestHeader("Authorization", "Basic " + authorization);
		postMethod.setRequestHeader("Content-Type", "application/json");
		postMethod.setRequestHeader("charset", "UTF-8");
		// 修改ticket需要的property
		postMethod.setRequestHeader("X-HTTP-Method-Override", "put");

		String jsonDataStr = JSONUtil.jsonPropertyFilter(data).toString();
		System.out.println(jsonDataStr);
		RequestEntity requestEntity = new ByteArrayRequestEntity(
				jsonDataStr.getBytes());
		postMethod.setRequestEntity(requestEntity);
		try {
			int statusCode = httpClient.executeMethod(postMethod);

			System.out.println(statusCode);
			String strResponseBody = new String(postMethod.getResponseBody());
			try {
				JSONObject responseJsonObject = JSONObject
						.fromObject(strResponseBody);
				String ticketId = (String) responseJsonObject.get("ticketId");
				return ticketId;
			} catch (Exception e) {
				throw new LogicException("线路同步失败");
			}
		} catch (Exception e) {
			throw new LogicException("线路同步失败");
		} finally {
			postMethod.releaseConnection();
		}
	}

	private String sendTicket(MobileOrder mobileOrder, String serial) {
		Data data = new Data();

		Head head = new Head();
		head.setVersion("1.1");
		if (!StringUtils.isEmpty(serial)) {
			head.setSerial(serial);
		} else {
			head.setSerial(String.valueOf(System.currentTimeMillis()));
		}

		// head.setSkinId("edf1cae5-3a44-3c44-bd0d-42bed0844bc8");//之前的会员卡模板
		head.setSkinId("5add1e22-c284-36fe-a4f1-cd92edd689ac");
		head.setKeywords(new String[] { "lvmama", "ticket" });
		head.setStorable(true);

		String format = "yyyy/MM/dd HH:mm";
		ValidUntil validUntil = new ValidUntil();
		Date arrivalDateRedOne = DateUtils.addDays(Calendar.getInstance()
				.getTime(), -1);
		validUntil.setValue(DateFormatUtils.format(arrivalDateRedOne, format));
		validUntil.setFormat(format);
		head.setValidUntil(validUntil);
		data.setHead(head);

		// views
		List<View> views = new ArrayList<View>();

		// barcode
		View main_barcode_a = new View();
		main_barcode_a.setId("MAIN_BARCODE_a");

		ElementDataBarcode barcode = new ElementDataBarcode();
		barcode.setCaption(String.valueOf(mobileOrder.getOrderId()));
		barcode.setType("QRCODE");
		barcode.setValue(String.valueOf(mobileOrder.getOrderId()));

		main_barcode_a.setBarcode(barcode);
		views.add(main_barcode_a);

		// image
		String icon = mobileOrder.getImgUrl();
		if (!StringUtils.isEmpty(icon)) {
			// image
			View main_middle_image_a = new View();
			main_middle_image_a.setId("MAIN_MIDDLE_IMAGE_a");

			ElementDataImage image = new ElementDataImage();
			String imgUrl = "http://pic.lvmama.com/pics/" + icon;
			image.setValue(this.getBase64Image(imgUrl));
			image.setType("jpg;base64");
			image.setAlign("middle");

			main_middle_image_a.setImage(image);
			views.add(main_middle_image_a);
		}

		// list text1
		View abstract_value2 = new View();
		abstract_value2.setId("ABSTRACT_VALUE2");

		ElementDataText abstract_value2Text = new ElementDataText();
		abstract_value2Text.setValue(mobileOrder.getVisitTime());
		abstract_value2Text.setAlign("middle");

		abstract_value2.setText(abstract_value2Text);
		views.add(abstract_value2);

		// list text2
		View abstract_value1 = new View();
		abstract_value1.setId("ABSTRACT_VALUE1");

		ElementDataText abstract_value1Text = new ElementDataText();
		abstract_value1Text.setValue(mobileOrder.getDestPlaceName() + "门票");
		abstract_value1Text.setAlign("middle");

		abstract_value1.setText(abstract_value1Text);
		views.add(abstract_value1);

		// top text
		View main_top_value1_a = new View();
		main_top_value1_a.setId("MAIN_TOP_VALUE1_a");

		ElementDataText middleText = new ElementDataText();
		String productName = mobileOrder.getProductName();
		if (!StringUtils.isEmpty(productName)) {// 门票名称去掉门票类型
			int index1 = productName.indexOf("(");
			if (index1 != -1) {
				productName = productName.substring(0, index1);
			}
			int index2 = productName.indexOf("（");
			if (index2 != -1) {
				productName = productName.substring(0, index2);
			}
		}
		middleText.setValue(productName);
		middleText.setAlign("middle");

		main_top_value1_a.setText(middleText);
		views.add(main_top_value1_a);

		// 游玩时间
		View main_middle_value1_a = new View();
		main_middle_value1_a.setId("MAIN_BOTTOM_VALUE1_a");

		ElementDataText leftText = new ElementDataText();
		leftText.setValue(mobileOrder.getVisitTime());
		leftText.setAlign("left");

		main_middle_value1_a.setText(leftText);
		views.add(main_middle_value1_a);

		// 开园时间
		View main_bottom_value1_c = new View();
		main_bottom_value1_c.setId("MAIN_BOTTOM_VALUE1_c");

		ElementDataText rightBottomText = new ElementDataText();
		rightBottomText.setValue(mobileOrder.getScenicOpenTime());
		rightBottomText.setAlign("left");

		main_bottom_value1_c.setText(rightBottomText);
		views.add(main_bottom_value1_c);

		// 景区地址
		View main_aux1_value1_a = new View();
		main_aux1_value1_a.setId("MAIN_AUX1_VALUE1_a");

		ElementDataText leftAux1Text = new ElementDataText();
		leftAux1Text.setValue(mobileOrder.getAddress());
		leftAux1Text.setAlign("left");

		main_aux1_value1_a.setText(leftAux1Text);
		views.add(main_aux1_value1_a);

		data.setView(views);

		List<Alert> alerts = new ArrayList<Alert>();
		Alert geofenceAlert = new Alert();
		GeofenceAlert geofence = new GeofenceAlert();
		geofence.setAltitude(null);
		geofence.setLatitude(mobileOrder.getBaiduLatitude());
		geofence.setLongitude(mobileOrder.getBaiduLongitude());
		geofence.setRangeinmeter(10);

		geofenceAlert.setId("ALERT1");
		geofenceAlert.setGeofence(geofence);

		alerts.add(geofenceAlert);

		Alert timeAlert = new Alert();
		DateAlert date = new DateAlert();
		date.setBeforeinmin(5);
		date.setFormat(format);
		String visitTime = mobileOrder.getVisitTime();
		Date visitDate = null;
		try {
			visitDate = DateUtils.parseDate(visitTime, "yyyy-MM-dd");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (null != visitDate) {
			date.setValue(DateFormatUtils.format(
					DateUtils.addDays(visitDate, -2), format));
		}
		timeAlert.setDate(date);
		alerts.add(timeAlert);

		data.setAlerts(alerts);

		List<Partner> partners = new ArrayList<Partner>();
		Partner partner = new Partner();
		P p = new P();
		partner.setPartner(p);
		partners.add(partner);
		data.setPartners(partners);

		System.out.println(data);

		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(MessageFormat.format("https://api.wallet.samsung.com/tkt/tickets?serial={0}", serial));
		// String authorization = new String(
		// Base64.encodeBase64("b425124d16124697a6d54f3dd1b501ef:8cbf2d40b8dc48dc994680455445cd35"
		// .getBytes()));
		String authorization = new String(
				Base64.encodeBase64("c364ee866cdd4eea8e90dd51f5e64fca:0d9daf4c620e408988f2eae4da5901ae"
						.getBytes()));
		System.out.println(authorization);

		postMethod.setRequestHeader("X-TKT-Protocol-Version", "1.1");
		postMethod.setRequestHeader("Authorization", "Basic " + authorization);
		postMethod.setRequestHeader("Content-Type", "application/json");
		postMethod.setRequestHeader("charset", "UTF-8");
		// 修改ticket需要的property
		postMethod.setRequestHeader("X-HTTP-Method-Override", "put");

		String jsonDataStr = JSONUtil.jsonPropertyFilter(data).toString();
		System.out.println(jsonDataStr);
		RequestEntity requestEntity = new ByteArrayRequestEntity(
				jsonDataStr.getBytes());
		postMethod.setRequestEntity(requestEntity);
		try {
			int statusCode = httpClient.executeMethod(postMethod);

			System.out.println(statusCode);
			String strResponseBody = new String(postMethod.getResponseBody());
			try {
				JSONObject responseJsonObject = JSONObject
						.fromObject(strResponseBody);
				String ticketId = (String) responseJsonObject.get("ticketId");
				return ticketId;
			} catch (Exception e) {
				throw new LogicException("门票同步失败");
			}
		} catch (Exception e) {
			throw new LogicException("门票同步失败");
		} finally {
			postMethod.releaseConnection();
		}
	}

	private String getBase64Image(String imgUrl) {
		try {
			URL url = new URL(imgUrl);
			InputStream is = url.openStream();
			BufferedImage bi = ImageIO.read(is);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", baos);
			byte[] bytes = baos.toByteArray();

			return new String(Base64.encodeBase64(bytes)).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void setMobileClientService(MobileClientService mobileClientService) {
		this.mobileClientService = mobileClientService;
	}

	public void setClientUserService(IClientUserService clientUserService) {
		this.clientUserService = clientUserService;
	}

}
