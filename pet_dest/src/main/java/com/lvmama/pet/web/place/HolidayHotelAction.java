package com.lvmama.pet.web.place;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.place.HotelTrafficInfo;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceCoordinateGoogle;
import com.lvmama.comm.pet.po.place.PlaceHistoryCookie;
import com.lvmama.comm.pet.po.place.PlaceHotel;
import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.po.place.PlaceHotelOtherRecommend;
import com.lvmama.comm.pet.po.place.PlaceHotelRoom;
import com.lvmama.comm.pet.po.place.PlaceLandMark;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.pet.po.seo.SeoLinks;
import com.lvmama.comm.pet.service.place.PlacePageService;
import com.lvmama.comm.pet.service.seo.SeoLinksService;
import com.lvmama.comm.pet.vo.ProductSearchInfoHotel;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.vo.Constant;

@Results({ 
	@Result(name = "hotel", type="freemarker", location = "/WEB-INF/pages/hotel/holidayHotel.ftl")
})
public class HolidayHotelAction extends DestBaseAction{
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
	 * 历史记录
	 */
	private List<PlaceHistoryCookie> historyCookieList;
	
	private SeoLinksService seoLinksService;
		
	/**
	 * 酒店目的地页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action("/newplace/holidayHotel")
	@Override
	public String execute() {
		if (null == place
				|| null ==place.getPlaceId()
				|| !"Y".equalsIgnoreCase(place.getIsValid())
				|| !Constant.PLACE_STAGE.PLACE_FOR_HOTEL.getCode().equalsIgnoreCase(place.getStage())) {
			debug(LOG, "The place is null or it's not a hotel, redirect to error page.");
			return ERROR;
		}
		hotelPageInfoMap = (Map<String, Object>) MemcachedUtil.getInstance().get(Constant.HOLIDAY_HOTEL_PLACEID+place.getPlaceId());
		if (null == hotelPageInfoMap || null == hotelPageInfoMap.get("placeSearchInfo") || null == hotelPageInfoMap.get("placeHotel")) {
			debug(LOG, "The hotel information havn't in memcache, so need query from database.");
			hotelPageInfoMap = placePageService.getHolidayHotelPageInfo(place);
			MemcachedUtil.getInstance().set(Constant.HOLIDAY_HOTEL_PLACEID+place.getPlaceId(), MemcachedUtil.ONE_HOUR, hotelPageInfoMap);
			comKeyDescService.insert(Constant.HOLIDAY_HOTEL_PLACEID+place.getPlaceId(), "酒店dest产品列表:" + place.getPlaceId());
			debug(LOG, "Put the hotel information in memcahce.");
		}
		//查询历史记录
		historyCookieList = getHistoryCookie();
		//将需要的酒店信息存入Cookie
		setHistoryCookie(hotelPageInfoMap);
		
		
		/**
		 * seo友情链接
		 * @author nixianjun 8.27
		 */
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("placeId",place.getPlaceId());
		map.put("location",Constant.PLACE_SEOLINKS.INDEX.getCode());
		hotelPageInfoMap.put("seoLinksList", PlaceUtils.removeRepeatData(seoLinksService.batchQuerySeoLinksByParam(map)));
 
		return "hotel";
	}
	
	public PlaceHotel getPlaceHotel() {
		return null == hotelPageInfoMap ? null : (PlaceHotel) hotelPageInfoMap.get("placeHotel");
	}

	@SuppressWarnings("unchecked")
	public List<ProductSearchInfoHotel> getSingleRoomList() {
		return null == hotelPageInfoMap ? null : (List<ProductSearchInfoHotel>) hotelPageInfoMap.get("singleRoomList");
	}
	
	@SuppressWarnings("unchecked")
	public List<Place> getPlaceList() {
		return null == hotelPageInfoMap ? null : (List<Place>) hotelPageInfoMap.get("placeList");
	}

	@SuppressWarnings("unchecked")
	public List<PlaceCoordinateGoogle> getPlaceCoordinateList() {
		return null == hotelPageInfoMap ? null : (List<PlaceCoordinateGoogle>) hotelPageInfoMap.get("placeCoordinateList");
	}
	
	@SuppressWarnings("unchecked")
	public List<PlaceHotelRoom> getHotelRoomList(){
		return null == hotelPageInfoMap ? null : (List<PlaceHotelRoom>) hotelPageInfoMap.get("hotelRoomList");
	} 
	
	@SuppressWarnings("unchecked")
	public List<PlaceHotelNotice> getHotelNoticeList(){
		return null == hotelPageInfoMap ? null : (List<PlaceHotelNotice>) hotelPageInfoMap.get("hotelNoticeList");
	}
	
	public PlaceHotelNotice getRecommend(){
		return null == hotelPageInfoMap ? null : (PlaceHotelNotice) hotelPageInfoMap.get("recommend");
	}
	
	@SuppressWarnings("unchecked")
	public List<PlaceHotelOtherRecommend> getOtherRecommendList(){
		return null == hotelPageInfoMap ? null : (List<PlaceHotelOtherRecommend>) hotelPageInfoMap.get("otherRecommendList");
	}
	public PlaceSearchInfo getPlaceSearchInfo(){
		return null == hotelPageInfoMap ? null :(PlaceSearchInfo) hotelPageInfoMap.get("placeSearchInfo");
	}
	@SuppressWarnings("unchecked")
	public List<ProductSearchInfo> getHolidayHotelTuanGouList(){
		return null == hotelPageInfoMap ? null : (List<ProductSearchInfo>) hotelPageInfoMap.get("holidayHotelTuanGouList");
	}
	@SuppressWarnings("unchecked")
	public List<ProductSearchInfo> getHoliDayHotelFrontList(){
		return null == hotelPageInfoMap ? null : (List<ProductSearchInfo>) hotelPageInfoMap.get("holiDayHotelFrontList");
	}
	@SuppressWarnings("unchecked")
	public List<PlacePhoto> getPlacePhotoList(){
		return null == hotelPageInfoMap ? null : (List<PlacePhoto>) hotelPageInfoMap.get("placePhotoList");
	}
	@SuppressWarnings("unchecked")
	public List<HotelTrafficInfo> getTrafficInfoList(){
		return null == hotelPageInfoMap ? null :(List<HotelTrafficInfo>) hotelPageInfoMap.get("trafficInfoList");
	}
	@SuppressWarnings("unchecked")
	public List<PlaceLandMark> getLandMarkList(){
		return null == hotelPageInfoMap ? null :(List<PlaceLandMark>) hotelPageInfoMap.get("landMarkList");
	}
	public SeoIndexPage getSeoIndexPage(){
		return null == hotelPageInfoMap ? null : (SeoIndexPage) hotelPageInfoMap.get("seoIndexPage");
	}
	public List<SeoLinks> getSeoLinksList(){
		return null == hotelPageInfoMap ? null : (List<SeoLinks>) hotelPageInfoMap.get("seoLinksList");
	}
	public List<PlaceHistoryCookie> getHistoryCookieList() {
		return historyCookieList;
	}
	public Map<Long,List<MarkCoupon>> getMarkCouponMap(){
		return null == hotelPageInfoMap ? null : (Map<Long,List<MarkCoupon>>) hotelPageInfoMap.get("markCouponMap");
	}
	public void setPlacePageService(final PlacePageService placePageService) {
		this.placePageService = placePageService;
	}

	/**
	 * @param seoLinksService the seoLinksService to set
	 */
	public void setSeoLinksService(SeoLinksService seoLinksService) {
		this.seoLinksService = seoLinksService;
	}
}
