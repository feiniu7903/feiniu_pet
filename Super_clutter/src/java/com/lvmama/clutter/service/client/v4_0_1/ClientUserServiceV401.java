package com.lvmama.clutter.service.client.v4_0_1;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.kayak.telpay.mpi.util.StringUtils;
import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobilePayment;
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
import com.lvmama.clutter.service.client.v3_2.ClientUserServiceV321;
import com.lvmama.clutter.service.impl.ClientUserServiceImpl;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.JSONUtil;
import com.lvmama.comm.pet.po.mobile.MobileOrderRelationSamsung;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.vo.Constant;

public class ClientUserServiceV401 extends ClientUserServiceV321 {
	//private static final Log log = LogFactory.getLog(ClientUserServiceImpl.class);
	private final Logger logger = Logger.getLogger(this.getClass());
	@Override
	public MobileOrder getOrder(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("orderId", param);
		MobileOrder mo = super.getMobileOrderByOrderId(
				Long.valueOf(param.get("orderId").toString()),
				String.valueOf(param.get("userNo")));
		boolean isTrain = isTrain(mo.getMainProductType(),
				mo.getMainSubProductType());
		// 如果不是火车票
		if (!isTrain) {
			super.initBonus(mo);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", param.get("userId"));
		List<UserCooperationUser> list = userCooperationUserService
				.getCooperationUsers(parameters);
		String channel = "";
		if (list != null && !list.isEmpty()) {
			UserCooperationUser cu = list.get(0);
			channel = cu.getCooperation();
		}
		MobilePayment mpalipay = new MobilePayment(
				Constant.PAYMENT_GATEWAY.ALIPAY_APP.name(), Constant
						.getInstance().getAliPayAppUrl());
		MobilePayment mpwap = new MobilePayment(
				Constant.PAYMENT_GATEWAY.ALIPAY_WAP.name(), Constant
						.getInstance().getAliPayWapUrl());
		MobilePayment mpupompTest = new MobilePayment("UPOMP_OTHER", Constant
				.getInstance().getUpompPayUrl());
		MobilePayment mpupomp = new MobilePayment(
				Constant.PAYMENT_GATEWAY.UPOMP.name(), Constant.getInstance()
						.getUpompPayUrl());
		MobilePayment tenpayWap = new MobilePayment("TENPAY_WAP", Constant
				.getInstance().getTenpayWapUrl());
		
		mo.getPaymentChannels().add(mpalipay);
		mo.getPaymentChannels().add(mpwap);
		mo.getPaymentChannels().add(mpupompTest);
		mo.getPaymentChannels().add(tenpayWap);
		mo.getPaymentChannels().add(mpupomp);
		// isTrain 如果是火车票 - 屏蔽银联支付 ,isWP8 屏蔽银联支付
		if ("ALIPAY".equals(channel) || "CLIENT_ANONYMOUS".equals(channel)
				|| isTrain || this.isHopeChannel(param, Constant.MOBILE_PLATFORM.WP8.name())) {
			mo.getPaymentChannels().remove(mpupomp);
			mo.getPaymentChannels().remove(mpupompTest);
			if ("ALIPAY".equals(channel)) {
				mo.getPaymentChannels().remove(tenpayWap);
			}
		}
		return mo;

	}
	
	/**
	 * 是否想要的渠道
	 * @param params  请求参数
	 * @param channel 想要的渠道
	 * @return
	 */
	public boolean isHopeChannel(Map<String,Object> params,String channel) {
		boolean b = false;
		try {
			if(null != params && null != params.get("firstChannel") 
					&& StringUtils.isNotEmpty(params.get("firstChannel").toString()) 
					&& channel.equalsIgnoreCase(params.get("firstChannel").toString())) {
				b = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	/**
	 * 发布ticket
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> issueTicket(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MobileOrder mobileOrder = this.getOrder(param);
		String serial = (String) param.get("serial");
		long orderId = Long.parseLong((String)param.get("orderId"));
		String mainProduceType = mobileOrder.getMainProductType();
		String ticketId = null;
		if(Constant.PRODUCT_TYPE.ROUTE.name().equals(mainProduceType)){
			ticketId = this.sendRoute(mobileOrder,serial);
		}else if(Constant.PRODUCT_TYPE.TICKET.name().equals(mainProduceType)){
			ticketId = this.sendTicket(mobileOrder,serial);
		}else{
			
		}
		
		//TODO 保存到数据库ticketId serial orderId
		MobileOrderRelationSamsung result = mobileClientService.selectByOrderId(orderId);
		if(null!=result){
			
		}else{
			MobileOrderRelationSamsung record = new MobileOrderRelationSamsung();
			record.setTicketid(ticketId);
			record.setSerial(serial);
			record.setOrderid(orderId);
			record.setCreateDate(Calendar.getInstance().getTime());
			record.setUpdateDate(Calendar.getInstance().getTime());
			mobileClientService.insert(record);
		}
		
		resultMap.put("ticketId", ticketId);
		return resultMap;
	}
	
	private String sendRoute(MobileOrder mobileOrder,String serial) {
		Data data = new Data();

		Head head = new Head();
		head.setVersion("1.1");
		if(!StringUtils.isEmpty(serial)){
			head.setSerial(serial);
		}else{
			head.setSerial(String.valueOf(System.currentTimeMillis()));
		}
		
		head.setSkinId("1646c78f-e61b-3823-a329-1c3fe04b5d4b");
		head.setKeywords(new String[] { "lvmama", "route" });
		head.setStorable(true);
		
		String arrivalDateStr = mobileOrder.getVisitTime();
		String format = "yyyy/MM/dd HH:mm";
		if(!StringUtils.isEmpty(arrivalDateStr)){
			try {
				ValidUntil validUntil = new ValidUntil();
				Date arrivalDateAddOne = DateUtils.addDays(DateUtils.parseDate(arrivalDateStr, "yyyy-MM-dd"), 1);
				validUntil.setValue(DateFormatUtils.format(arrivalDateAddOne,
						format));
				validUntil.setFormat(format);
				head.setValidUntil(validUntil);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
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
//		String icon = mobileOrder.getImgUrl();
//		if(!StringUtils.isEmpty(icon)){
//			// image
//			View main_middle_image_a = new View();
//			main_middle_image_a.setId("MAIN_MIDDLE_IMAGE_a");
//			
//			ElementDataImage image = new ElementDataImage();
//			String imgUrl = "http://pic.lvmama.com/pics/" + icon;
//			image.setValue(this.getBase64Image(imgUrl));
//			image.setType("jpg;base64");
//			image.setAlign("middle");
//
//			main_middle_image_a.setImage(image);
//			views.add(main_middle_image_a);
//		}
		
		//list text1
		View abstract_value2 = new View();
		abstract_value2.setId("ABSTRACT_VALUE2");

		ElementDataText abstract_value2Text = new ElementDataText();
		abstract_value2Text.setValue(mobileOrder.getVisitTime());
		abstract_value2Text.setAlign("middle");

		abstract_value2.setText(abstract_value2Text);
		views.add(abstract_value2);
		
		//list text2
		View abstract_value1 = new View();
		abstract_value1.setId("ABSTRACT_VALUE1");

		ElementDataText abstract_value1Text = new ElementDataText();
		abstract_value1Text.setValue(mobileOrder.getCityName()+"游玩");
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
		String cityName = mobileOrder.getCityName(); 
		if(StringUtils.isEmpty(cityName)){ 
			cityName = mobileOrder.getDestPlaceName(); 
		} 
		rightMiddleText.setValue(cityName);
		rightMiddleText.setAlign("left");

		main_middle_value1_c.setText(rightMiddleText);
		views.add(main_middle_value1_c);

		// 游玩人
		List<MobilePersonItem> persons =  mobileOrder.getListPerson();
		StringBuffer buffer = new StringBuffer();
		if(null!=persons&&persons.size()>0){//默认显示两个游玩人，如果大于2个则后面加“等”字
			for(int i=0;i<persons.size();i++){
				MobilePersonItem person = persons.get(i);
				if("TRAVELLER".equals(person.getPersonType())){
					buffer.append(person.getPersonName()+",");
					if(i>=2){
						buffer.append("等");
						break;
					}
				}
			}
			if(buffer.lastIndexOf(",")!=-1){//订单信息中包含游玩人
				buffer.deleteCharAt(buffer.lastIndexOf(","));
			}else{//订单信息中包含游玩人，显示订单联系人
				for(int i=0;i<persons.size();i++){
					MobilePersonItem person = persons.get(i);
					if("CONTACT".equals(person.getPersonType())){
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
		PostMethod postMethod = new PostMethod(
				"https://api.wallet.samsung.com/tkt/tickets");
		String authorization = new String(
				Base64.encodeBase64("28bc72a1026d4239900235983ce17961:b0bd12e3b7b74b2fa11f4492504f1675"
						.getBytes()));
		System.out.println(authorization);

		postMethod.setRequestHeader("X-TKT-Protocol-Version", "1.1");
		postMethod.setRequestHeader("Authorization", "Basic " + authorization);
		postMethod.setRequestHeader("Content-Type", "application/json");
		postMethod.setRequestHeader("charset", "UTF-8");

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
				JSONObject responseJsonObject = JSONObject.fromObject(strResponseBody);
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

	private String sendTicket(MobileOrder mobileOrder,String serial) {
		Data data = new Data();

		Head head = new Head();
		head.setVersion("1.1");
		if(!StringUtils.isEmpty(serial)){
			head.setSerial(serial);
		}else{
			head.setSerial(String.valueOf(System.currentTimeMillis()));
		}
		
		//head.setSkinId("edf1cae5-3a44-3c44-bd0d-42bed0844bc8");//之前的会员卡模板
		head.setSkinId("5add1e22-c284-36fe-a4f1-cd92edd689ac");
		head.setKeywords(new String[] { "lvmama", "ticket" });
		head.setStorable(true);
		
		String arrivalDateStr = mobileOrder.getVisitTime();
		String format = "yyyy/MM/dd HH:mm";
		if(!StringUtils.isEmpty(arrivalDateStr)){
			try {
				ValidUntil validUntil = new ValidUntil();
				Date arrivalDateAddOne = DateUtils.addDays(DateUtils.parseDate(arrivalDateStr, "yyyy-MM-dd"), 1);
				validUntil.setValue(DateFormatUtils.format(arrivalDateAddOne,
						format));
				validUntil.setFormat(format);
				head.setValidUntil(validUntil);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
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
		if(!StringUtils.isEmpty(icon)){
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
		
		//list text1
		View abstract_value2 = new View();
		abstract_value2.setId("ABSTRACT_VALUE2");

		ElementDataText abstract_value2Text = new ElementDataText();
		abstract_value2Text.setValue(mobileOrder.getVisitTime());
		abstract_value2Text.setAlign("middle");

		abstract_value2.setText(abstract_value2Text);
		views.add(abstract_value2);
		
		//list text2
		View abstract_value1 = new View();
		abstract_value1.setId("ABSTRACT_VALUE1");

		ElementDataText abstract_value1Text = new ElementDataText();
		abstract_value1Text.setValue(mobileOrder.getDestPlaceName()+"门票");
		abstract_value1Text.setAlign("middle");

		abstract_value1.setText(abstract_value1Text);
		views.add(abstract_value1);

		// top text
		View main_top_value1_a = new View();
		main_top_value1_a.setId("MAIN_TOP_VALUE1_a");

		ElementDataText middleText = new ElementDataText();
		String productName = mobileOrder.getProductName();
		if(!StringUtils.isEmpty(productName)){//门票名称去掉门票类型
			int index1 = productName.indexOf("(");
			if(index1!=-1){
				productName = productName.substring(0, index1);
			}
			int index2 = productName.indexOf("（");
			if(index2!=-1){
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
		View main_middle_value1_c = new View();
		main_middle_value1_c.setId("MAIN_BOTTOM_VALUE1_c");

		ElementDataText rightBottomText = new ElementDataText();
		rightBottomText.setValue(mobileOrder.getScenicOpenTime());
		rightBottomText.setAlign("left");

		main_middle_value1_c.setText(rightBottomText);
		views.add(main_middle_value1_c);
		
		// 联系人
		List<MobilePersonItem> persons =  mobileOrder.getListPerson();
		StringBuffer buffer = new StringBuffer();
		if(null!=persons&&persons.size()>0){//联系人
			for(int i=0;i<persons.size();i++){
				MobilePersonItem person = persons.get(i);
				if("CONTACT".equals(person.getPersonType())){
					buffer.append(person.getPersonName());
				}
			}
		}
		View main_aux1_value1_a = new View();
		main_aux1_value1_a.setId("MAIN_AUX1_VALUE1_a");

		ElementDataText leftBottomText = new ElementDataText();
		leftBottomText.setValue(buffer.toString());
		leftBottomText.setAlign("left");

		main_aux1_value1_a.setText(leftBottomText);
		views.add(main_aux1_value1_a);

		// 景区地址
		View main_aux2_value1_a = new View();
		main_aux2_value1_a.setId("MAIN_AUX2_VALUE1_a");

		ElementDataText leftAux1Text = new ElementDataText();
		leftAux1Text.setValue(mobileOrder.getAddress());
		leftAux1Text.setAlign("left");

		main_aux2_value1_a.setText(leftAux1Text);
		views.add(main_aux2_value1_a);

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
		PostMethod postMethod = new PostMethod(
				"https://api.wallet.samsung.com/tkt/tickets");
//		String authorization = new String(
//				Base64.encodeBase64("b425124d16124697a6d54f3dd1b501ef:8cbf2d40b8dc48dc994680455445cd35"
//						.getBytes()));
		String authorization = new String(
				Base64.encodeBase64("c364ee866cdd4eea8e90dd51f5e64fca:0d9daf4c620e408988f2eae4da5901ae"
						.getBytes()));
		System.out.println(authorization);

		postMethod.setRequestHeader("X-TKT-Protocol-Version", "1.1");
		postMethod.setRequestHeader("Authorization", "Basic " + authorization);
		postMethod.setRequestHeader("Content-Type", "application/json");
		postMethod.setRequestHeader("charset", "UTF-8");

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
				JSONObject responseJsonObject = JSONObject.fromObject(strResponseBody);
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

}
