package com.lvmama.passport.disney;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.passport.disney.model.Event;
import com.lvmama.passport.disney.model.EventResponse;
import com.lvmama.passport.disney.model.OrderRespose;
import com.lvmama.passport.disney.model.Show;
import com.lvmama.passport.disney.model.ShowResponse;
import com.lvmama.passport.utils.WebServiceConstant;

public class DisneyUtil {
	static DisneyUtil disneyUtil ;
	public static DisneyUtil init(){
		if(disneyUtil==null){
			disneyUtil = new DisneyUtil();
		}
		return disneyUtil;
	}
	
	

	public EventResponse getEvents()throws Exception{
		String agentId=WebServiceConstant.getProperties("disney.agentId");
		String requestTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		JSONObject param=new JSONObject(); 
		param.put("agentId", agentId);
		param.put("requestTime",requestTime);
		String message=param.toString();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("message", message);
		paramMap.put("signature",makeSign(message));
		String jsonResult = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("disney.url")+"/OTA/GetEvents", paramMap);
		System.out.println("Disney events http post response" + jsonResult);
		return paseEventResponse(jsonResult);
	}
	
	public String getShows(String eventId,String fromdate,String todate)throws Exception{
		String agentId=WebServiceConstant.getProperties("disney.agentId");
		String requestTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		JSONObject param=new JSONObject(); 
		param.put("agentId", agentId);
		param.put("requestTime",requestTime);
		param.put("eventId",eventId);
		param.put("fromDate",fromdate);
		param.put("toDate",todate);
		String message=param.toString();
		System.out.println(message);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("message", message);
		paramMap.put("signature",makeSign(message));
		String jsonResult = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("disney.url")+"/OTA/GetShows", paramMap);
		System.out.println("Disney getShows http post response" + jsonResult);
		return jsonResult;
	}
	
	
	public void getTickets(String eventId,String showId)throws Exception{
		String agentId=WebServiceConstant.getProperties("disney.agentId");
		String requestTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		JSONObject param=new JSONObject(); 
		param.put("agentId", agentId);
		param.put("requestTime",requestTime);
		param.put("eventId",eventId);
		param.put("showId",showId);
		String message=param.toString();
		System.out.println(message);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("message", message);
		paramMap.put("signature",makeSign(message));
		String jsonResult = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("disney.url")+"/OTA/GetTickets", paramMap);
		System.out.println("Disney getTickets http post response" + jsonResult);
	}
	
	
	public void getPickupDetails(String eventId)throws Exception{
		String agentId=WebServiceConstant.getProperties("disney.agentId");
		String requestTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		JSONObject param=new JSONObject(); 
		param.put("agentId", agentId);
		param.put("requestTime",requestTime);
		param.put("eventId", eventId);
		String message=param.toString();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("message", message);
		paramMap.put("signature",makeSign(message));
		String jsonResult = HttpsUtil.requestPostForm(WebServiceConstant.getProperties("disney.url")+"/OTA/GetPickupDetails", paramMap);
		System.out.println("Disney events http post response" + jsonResult);
	}

	
	private String makeSign(String message)throws Exception{
		String key=WebServiceConstant.getProperties("disney.key");
		String text = message + "||" + key;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(text.getBytes("UTF-8"));
		String sign = Hex.encodeHexString(hash);
		return sign;
	}
	
	public OrderRespose parseOrderStatusResponse(String response)throws Exception {
		JSONObject orderdetail = new JSONObject(response);
		OrderRespose res=new OrderRespose();
		String responseCode=orderdetail.getString("responseCode");
		res.setResponseCode(responseCode);
		if(StringUtils.equals(responseCode, "0000")){
			res.setReservationNo(orderdetail.getString("reservationNo"));
			res.setVoucherNo(orderdetail.getString("voucherNo"));
			res.setConfirmationLetter(orderdetail.getString("confirmationLetter"));
		}
		return res;
		
	}
	
	public EventResponse paseEventResponse(String response)throws Exception {
		JSONObject result = new JSONObject(response);
		EventResponse res=new EventResponse();
		String responseCode=result.getString("responseCode");
		res.setResponseCode(responseCode);
		List<Event> eventList = new ArrayList<Event>();
		if(StringUtils.equals(responseCode, "0000")){
			if (result.has("events")) {
				JSONArray jsonArray = new JSONArray(result.getString("events"));
				for (int i = 0; i < jsonArray.length(); i++) {
					Event event=new Event();
					JSONObject ev = jsonArray.getJSONObject(i);
					event.setEventId(ev.getString("eventId"));
					JSONObject name = new JSONObject(ev.getString("name"));
					event.setName(name.getString("sc"));
					JSONObject remark = new JSONObject(ev.getString("remark"));
					event.setRemark(remark.getString("sc"));
					eventList.add(event);
				}
			}
		}
		res.setEvents(eventList);
		return res;
	}
	
	public ShowResponse paseShowResponse(String response)throws Exception {
		JSONObject result = new JSONObject(response);
		ShowResponse res=new ShowResponse();
		String responseCode=result.getString("responseCode");
		res.setResponseCode(responseCode);
		List<Show> showList = new ArrayList<Show>();
		if(StringUtils.equals(responseCode, "0000")){
			if (result.has("shows")) {
				JSONArray jsonArray = new JSONArray(result.getString("shows"));
				for (int i = 0; i < jsonArray.length(); i++) {
					Show show=new Show();
					JSONObject sw = jsonArray.getJSONObject(i);
					show.setShowId(sw.getString("showId"));
					show.setShowDateTime(sw.getString("showDateTime"));
					show.setCutoffDateTime(sw.getString("cutoffDateTime"));
					showList.add(show);
				}
			}
		}
		res.setShows(showList);
		return res;
	}
}
