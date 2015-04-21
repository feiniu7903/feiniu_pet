package com.lvmama.clutter.web.recommend;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.clutter.service.IClientRecommendService;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.vo.Constant;

@Results({
		@Result(name = "hot", location = "/WEB-INF/pages/v3recommend/hot_v3.html", type = "freemarker"),
		@Result(name = "holidayRecommend", location = "/WEB-INF/pages/v3recommend/holiday_v3.html", type = "freemarker"),
		@Result(name = "sell", location = "/WEB-INF/pages/v3recommend/special_selling_v3.html", type = "freemarker") })
@Namespace("/mobile/recommend")
public class ClientRecommendAction extends BaseAction {
	private static final long serialVersionUID = 7894101684556617224L;
	@Autowired
	private IClientRecommendService api_com_recommend;
	private Long placeId;
	private String city;
	private String dataCode;
	private String latitude;
	private String longitude;

	private static Map<String, String> hotCityParam = new LinkedHashMap<String, String>();
	String HOT_CACHE = "CLIENT_RECOMMEND_HOT_CACHE";
	String SELL_CACHE = "CLIENT_RECOMMEND_SELL_CACHE";
	private static final String BAIDU_KEY = "CA152fe66fe4998de14538722d67fc60";

	@Action("/hot")
	public String hot() {
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isEmpty(city)){
			city = this.getCityByCoordinates();
		}
		this.initParams();

		HOT_CACHE += placeId;

		params.put("placeId", placeId);
		params.put("HOT_CACHE", HOT_CACHE);
		List<RecommendInfo> list = api_com_recommend.getHotList(params);
		if (list.isEmpty()) {
			Long beforeId = this.getPlaceId();
			this.setPlaceId(79L);
			HOT_CACHE = "CLIENT_RECOMMEND_HOT_CACHE";
			HOT_CACHE += placeId;

			params.put("placeId", placeId);
			params.put("HOT_CACHE", HOT_CACHE);
			list = api_com_recommend.getHotList(params);

			this.setPlaceId(beforeId);
		}
		getRequest().setAttribute("list", list);
		return "hot";
	}
	
	@Action("/holidayRecommend")
	public String holiday() {
		Map<String, Object> params = new HashMap<String, Object>();
		initHolidayPlaceId();
		
		String HOLIDAY_CACHE = Constant.HOLIDAY_CACHE + placeId + dataCode;

		params.put("placeId", placeId);
		params.put("dataCode", dataCode);
		params.put("HOLIDAY_CACHE", HOLIDAY_CACHE);
		List<RecommendInfo> list = api_com_recommend.getHolidayList(params);
		if (list.isEmpty()) {
			Long beforeId = this.getPlaceId();
			this.setPlaceId(79L);
			HOLIDAY_CACHE = Constant.HOLIDAY_CACHE + placeId + dataCode;

			params.put("placeId", placeId);
			params.put("dataCode", dataCode);
			params.put("HOLIDAY_CACHE", HOLIDAY_CACHE);
			list = api_com_recommend.getHolidayList(params);

			this.setPlaceId(beforeId);
		}
		getRequest().setAttribute("list", list);
		return "holidayRecommend";
	}

	@Action("/sell")
	public String sell() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if(StringUtils.isEmpty(city)){
				city = this.getCityByCoordinates();
			}
			this.initParams();
			SELL_CACHE += placeId;

			params.put("placeId", placeId);
			params.put("SELL_CACHE", SELL_CACHE);
			List<RecommendInfo> list = api_com_recommend.getSellList(params);
			if (list.isEmpty()) {
				Long beforeId = this.getPlaceId();
				this.setPlaceId(79L);
				SELL_CACHE = "CLIENT_RECOMMEND_SELL_CACHE";
				SELL_CACHE += placeId;

				params.put("placeId", placeId);
				params.put("SELL_CACHE", SELL_CACHE);
				list = api_com_recommend.getSellList(params);
				this.setPlaceId(beforeId);
			}
			getRequest().setAttribute("list", list);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "sell";
	}

	private void initHolidayPlaceId(){
		try {
			city = new String(city.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String provinceCode = api_com_recommend.getProvinceByCityName(city);
		Map<String,Long> provinceCodeHomeMap = PindaoPageUtils.getHomeMap();
		placeId = provinceCodeHomeMap.get(provinceCode);
		if (placeId == null || placeId == 0l) {
			placeId = 79L;
		}
	}
	
	private void initParams() {
		hotCityParam.put("北京", "1");
		hotCityParam.put("上海", "79");
		hotCityParam.put("杭州", "100");
		hotCityParam.put("南京", "82");
		hotCityParam.put("成都", "279");
		hotCityParam.put("广州", "229");
		hotCityParam.put("深圳", "231");
		hotCityParam.put("三亚", "272");

		if (!StringUtil.isEmptyString(city)) {
			try {
				city = new String(city.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Set<Entry<String, String>> entry = hotCityParam.entrySet();
			for (Entry<String, String> entry2 : entry) {
				if (city.contains(entry2.getKey())) {
					this.setPlaceId(Long.valueOf(entry2.getValue()));
				}
			}
		}
		if (placeId == null || placeId == 0l) {
			placeId = 79L;
		}
	}

	private String getCityByCoordinates(){
		try {
			if(!StringUtils.isEmpty(latitude) && !StringUtils.isEmpty(longitude)) {
				// 从百度地图获取数据 . 
				String url = "http://api.map.baidu.com/geocoder?location="+latitude+","+longitude+"&output=json&key="+BAIDU_KEY;
				String jsons =  HttpsUtil.requestGet(url);
				String cityFullName = JSONObject.fromObject(jsons).getJSONObject("result").getJSONObject("addressComponent").getString("city");
				return new String(cityFullName.substring(0, cityFullName.length()-1).getBytes(),"iso-8859-1");
			}else{
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			log.error("字符串转换错误...", e);
			return null;
		}
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public static Map<String, String> getHotCityParam() {
		return hotCityParam;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public static void setHotCityParam(Map<String, String> hotCityParam) {
		ClientRecommendAction.hotCityParam = hotCityParam;
	}

}
