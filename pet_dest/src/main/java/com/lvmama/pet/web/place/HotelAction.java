package com.lvmama.pet.web.place;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceCoordinateGoogle;
import com.lvmama.comm.pet.service.place.PlacePageService;
import com.lvmama.comm.pet.service.seo.SeoLinksService;
import com.lvmama.comm.pet.vo.ProductList;
import com.lvmama.comm.pet.vo.ProductSearchInfoHotel;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.enums.SeoIndexPageCodeEnum;

@Results({ 
	@Result(name = "hotel", type="freemarker", location = "/WEB-INF/pages/hotel/hotel.ftl")
})
public class HotelAction extends DestBaseAction{
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 9051553836202214281L;
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(HotelAction.class);
	/**
	 * 目的地页面远程服务
	 */
	private PlacePageService placePageService;
	/**
	 * 缓存数据
	 */
	private Map<String, Object> hotelPageInfoMap;
	
		
	/**
	 * 酒店目的地页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action("/newplace/hotel")
	@Override
	public String execute() {
		if (null == place
				|| null ==place.getPlaceId()
				|| !"Y".equalsIgnoreCase(place.getIsValid())
				|| !Constant.PLACE_STAGE.PLACE_FOR_HOTEL.getCode().equalsIgnoreCase(place.getStage())) {
			debug(LOG, "The place is null or it's not a hotel, redirect to error page.");
			return ERROR;
		}

		String hotelPageInfoKey = "hotelPageInfo_" + place.getPlaceId() + "_" + FRONTEND;
		hotelPageInfoMap = (Map<String, Object>) MemcachedUtil.getInstance().get(hotelPageInfoKey);
		if (null == hotelPageInfoMap || null == hotelPageInfoMap.get("place")) {
			debug(LOG, "The hotel information havn't in memcache, so need query from database.");
			hotelPageInfoMap = placePageService.getHotelPageInfo(place);
			MemcachedUtil.getInstance().set(hotelPageInfoKey, MemcachedUtil.ONE_HOUR, hotelPageInfoMap);
			comKeyDescService.insert(hotelPageInfoKey, "酒店dest产品列表:" + place.getPlaceId());
			debug(LOG, "Put the hotel information in memcahce.");
		}
		
		listCmtsOfDest(place.getPlaceId());
		
		//获取酒店首页seoTkd规则
		getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_HOTEL.getCode());
		
		return "hotel";
	}
	
	@Override
	public Place getPlace() {
		return null == hotelPageInfoMap ? null : (Place) hotelPageInfoMap.get("place");
	}

	@SuppressWarnings("unchecked")
	public List<ProductSearchInfoHotel> getSingleRoomList() {
		return null == hotelPageInfoMap ? null : (List<ProductSearchInfoHotel>) hotelPageInfoMap.get("singleRoomList");
	}

	@SuppressWarnings("unchecked")
	public List<ProductSearchInfoHotel> getHotelSuitList() {
		return null == hotelPageInfoMap ? null : (List<ProductSearchInfoHotel>) hotelPageInfoMap.get("hotelSuitList");
	}

	public ProductList getRecommendProductList() {
		return null == hotelPageInfoMap ? null : (ProductList) hotelPageInfoMap.get("recommendProductList");
	}

	@SuppressWarnings("unchecked")
	public List<Place> getHotelCoordinateList() {
		return null == hotelPageInfoMap ? null : (List<Place>) hotelPageInfoMap.get("hotelCoordinateList");
	}

	@SuppressWarnings("unchecked")
	public List<Place> getPlaceList() {
		return null == hotelPageInfoMap ? null : (List<Place>) hotelPageInfoMap.get("placeList");
	}

	@SuppressWarnings("unchecked")
	public List<PlaceCoordinateGoogle> getPlaceCoordinateList() {
		return null == hotelPageInfoMap ? null : (List<PlaceCoordinateGoogle>) hotelPageInfoMap.get("placeCoordinateList");
	}

	public void setPlacePageService(final PlacePageService placePageService) {
		this.placePageService = placePageService;
	}


}
