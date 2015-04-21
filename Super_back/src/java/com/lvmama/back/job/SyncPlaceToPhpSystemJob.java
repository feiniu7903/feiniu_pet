/**
 * 
 */
package com.lvmama.back.job;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlaceService;

/**
 * 同步目的地给PHP系统
 * @author liuyi
 *
 */
public class SyncPlaceToPhpSystemJob {
	
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(SyncPlaceToPhpSystemJob.class);
	PlaceService placeService;
	
	
	public void run() {
		Map<String, Object> param = new HashMap<String, Object>();
		Date startDate =new Date(System.currentTimeMillis()/86400000*86400000-(23-Calendar.ZONE_OFFSET)*3600000);  
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		param.put("startUpdateDate", startDate);
		param.put("endUpdateDate", calendar.getTime());
		List<Place> placeList = placeService.queryPlaceListByParam(param);
		Collections.sort(placeList,new PlaceComparator());
		JSONArray array = new JSONArray();
		for(int i = 0; i < placeList.size(); i++){
			Place place = placeList.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("place_id", place.getPlaceId());
			jsonObject.put("placename",  place.getName());
			jsonObject.put("parent_id", place.getParentPlaceId());
			jsonObject.put("pinyin", place.getPinYin());
			jsonObject.put("pinyin_url", place.getPinYinUrl());
			jsonObject.put("template", place.getTemplate());
			jsonObject.put("type", place.getStage());
			jsonObject.put("place_type", place.getPlaceType());
			if("Y".equals(place.getIsValid())){
				jsonObject.put("canceled", 0);
			}else{
				jsonObject.put("canceled", 1);
			}
			//酒店不需要同步
			if (!"3".equals(place.getStage())) {
				array.add(jsonObject);
			}
		}
		JSONObject returnJSONObject = new JSONObject();
		returnJSONObject.put("data", array);
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
		PostMethod postMethod = new PostMethod("http://www.lvmama.com/guide/syncplace/app/index.php");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gb2312");  
		NameValuePair[] nameValuePairList = new NameValuePair[2];
		nameValuePairList[0] = new NameValuePair();
		nameValuePairList[0].setName("actValue");
		nameValuePairList[0].setValue(returnJSONObject.toString());
		
		nameValuePairList[1] = new NameValuePair();
		nameValuePairList[1].setName("action");
		nameValuePairList[1].setValue("upAreaInfo");
		postMethod.addParameters(nameValuePairList);
		int status  = 0;
		try {
			status  = httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			LOG.error(e, e);
		} catch (IOException e) {
			LOG.error(e, e);
		}
		System.out.println(status);
	}


	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
}


class PlaceComparator implements Comparator<Place> {
	@Override
	public int compare(Place o1, Place o2) {
		return o1.getUpdateTime().compareTo(o2.getUpdateTime());
	}  
}
