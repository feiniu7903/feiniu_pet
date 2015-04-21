package com.lvmama.pet.web.place;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceHistoryCookie;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComKeyDescService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.seo.SeoIndexPageService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.PlaceVo;
import com.lvmama.comm.utils.CommHeaderUtil;
import com.lvmama.comm.utils.HttpRequestDeviceUtils;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.SeoUtils;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

@Results({
	@Result(name = "forwardPlace", params = { "status", "301", "headers.Location", "/dest/${pinYinUrl}" }, type = "httpheader"),
	@Result(name = "forwardScenic", type="dispatcher", location = "/place/scenicNewAction.do"),
	@Result(name = "forwardHotel", type="dispatcher", location = "/newplace/hotel.do"),
	@Result(name = "redirectHolidayHotel",params={"status", "301", "headers.Location", "/hotel/v${id}"}, type="httpheader"),
	@Result(name = "navigation", type="freemarker", location = "/WEB-INF/pages/common/navigation.ftl"),
	@Result(name = "redirectDestTab",params={"status", "301", "headers.Location", "/dest/${pinYinUrl}/${currentTab}_tab"}, type="httpheader"),
	@Result(name = "redirectDestTabFrm",params={"status", "301", "headers.Location", "/dest/${pinYinUrl}/${currentTab}_tab_frm${fromDestId}"}, type="httpheader"),
	@Result(name = "wap_place_dest", location = "http://m.lvmama.com/clutter/place/${id}", type = "redirect"),
	@Result(name = "404", type="dispatcher",location="/error.jsp"),
	@Result(name = "error", type="dispatcher",location="/error.jsp")
})
public class DestBaseAction extends BaseAction{
	private static final long serialVersionUID = 968590876686145769L;
	private static final Log LOG = LogFactory.getLog(DestBaseAction.class);
	public static String FRONTEND = "FRONTEND";
	
	protected Long fromDestId;
	protected Place place;
	protected SeoIndexPage seoIndexPage;
	protected CmtTitleStatisticsVO cmtTitleStatisticsVO;//获取景点的点评统计信息
	protected List<CmtLatitudeStatistics> cmtLatitudeStatisticsList;//维度平均分统计
	protected List<CommonCmtCommentVO> cmtCommentVOList;//最新审核过的点评	
	protected boolean hasInitDestId;//用于判断SEO TDK是否要包含出发地信息
	protected ComKeyDescService comKeyDescService;
	
	
	protected PlaceService placeService;
	protected ProductSearchInfoService productSearchInfoService;
	private SeoIndexPageService seoIndexPageService;
	private Map<String,Object> navigationMap;
	protected List<Place> fromPlaceList = new ArrayList<Place>();
	protected List<Place> sonPlaceList = new ArrayList<Place>();
	private List<ProductSearchInfo> placeInfoPrdSearchList = new ArrayList<ProductSearchInfo>();
	private String mode;
	/**
	 * 当前点击主页面中的 tab
	 */
	protected String currentTab;
	private Long page;
	private Page pageConfig;
	
	private UserUserProxy userUserProxy;
	//点评服务接口
	private CmtCommentService cmtCommentService;
	//景区点评统计服务接口
	private CmtTitleStatistisService cmtTitleStatistisService;
	//点评维度统计服务接口
	private CmtLatitudeStatistisService cmtLatitudeStatistisService;
	
	/**
	 * 404错误的页面
	 */
	protected static final String ERROR_PAGE = "404";	
	
	/**
	 * 获取目的地公用数据
	 */
	public void getDestCommon(){
		
		
	}
	
	private Long id;
	
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Action("/place/head")
	public void head() {
		try {
			CommHeaderUtil.getHeadContent(getResponse().getWriter());
		} catch (IOException ioe) {
			
		}
	}
	
	
	
	/**
	 * 景区和酒店的转向分发
	 * @return
	 */
	@Action("/newplace/dest")
	public String forwardDestIndex(){
		if (this.place == null) {
			return ERROR_PAGE;
		}
		//如果是目的地，则跳转到新目的地页面
		if ("1".equals(this.place.getStage())) {
			return "forwardPlace";
		} else if ("2".equals(this.place.getStage())) {
			/**
			 * 过略故宫
			 */
			
			LOG.info("1:request refrence is " +getRequest().getHeader("Referer")+" request u-e is "+getRequest().getHeader("User-Agent")+" wap_to_lvmama is "+this.getCookieValue("wap_to_lvmama"));
			
	 		if (null == this.getCookieValue("wap_to_lvmama")&&HttpRequestDeviceUtils.isMobileDevice(getRequest())&&!"100441".equals(place.getPlaceId())) {
	 			LOG.info("2: is mobile web brower redirect request u-e is "+getRequest().getHeader("User-Agent"));
	 			return "wap_place_dest";
	 		}
	 		
	 		LOG.info("2： is pc web brower");
			return "forwardScenic";
		} else if ("3".equals(this.place.getStage())) {
			//当stage 是酒店类型的时候 301跳转酒店的URL地址 /hotel/v酒店id
			id = place.getPlaceId();
			return "redirectHolidayHotel";
	    }
		return ERROR_PAGE;

	}
	
	/**
	 * 景区和酒店的转向分发
	 * @return
	 */
	@Action("/newplace/destUrl")
	public String forwardDestUrlIndex(){
		if (this.place == null) {
			return ERROR_PAGE;
		}
		
		return "forwardPlace";
	}
	
	/**
	 * @deprecated 都不存在了，为什么还活着？
	 * 老的目的地tab列表页已经删除，此方法跳转到现有的非列表页tab
	 * @return
	 */
	@Action("/newplace/tabRedirect")
	public String tabRedirect(){
		if (place == null) {
			return ERROR_PAGE;
		}
		if(fromDestId!=null){
			return "redirectDestTabFrm";
		}
		return "redirectDestTab";
	}
	
	/**
	 * 形如（_12_1_1）的产品列表页面的url跳转
	 * @return
	 */
	@Action("/newplace/moreProductList")
	public String productByTypeList() {
		if (place == null) {
			return ERROR_PAGE;
		}
		if ("2".equals(place.getStage())||"3".equals(place.getStage())) {
			return "forwardPlace";
		}
		if (StringUtils.isNotEmpty(mode)) {
			if (mode.equalsIgnoreCase("hotel")||mode.equalsIgnoreCase("ticket")||mode.equalsIgnoreCase("package")) {
				currentTab="freeness";
				return "redirectDestTab";
			} else if (mode.equalsIgnoreCase("line")) {
				currentTab="dest2dest";
				return "redirectDestTabFrm";
			}
		}
		return "forwardPlace";
	}
	
	/**
	 * 获取seo默认规则
	 * @param pageCode 页面标识
	 */
	public void getSeoIndexPageRegular(Place currentPlace, String pageCode){
		getSeoIndexPageRegular(currentPlace, pageCode, null);
	}
	
	/**
	 * 获取seo默认规则
	 * @param pageCode 页面标识
	 */
	public void getSeoIndexPageRegular(Place currentPlace, String pageCode, Place fromPlace){
		if(currentPlace == null)
		{
			return;
		}
		String key="getSeoIndexPageRegular_"+pageCode+"_id_"+currentPlace.getPlaceId();
		Object obj=MemcachedUtil.getInstance().get(key);
		if(obj==null){
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("placeId", currentPlace.getPlaceId());
			List<PlaceVo> placeVoList=placeService.queryPlaceAndParent(param);
			if(placeVoList!=null&&placeVoList.size()>0){
				PlaceVo placeVo=placeVoList.get(0);
				seoIndexPage=seoIndexPageService.getSeoIndexPageByPageCode(pageCode);
				if(seoIndexPage!=null){
					seoIndexPage.setSeoTitle(SeoUtils.getSeoIndexPageRegular(placeVo, seoIndexPage.getSeoTitle()));
					seoIndexPage.setSeoKeyword(SeoUtils.getSeoIndexPageRegular(placeVo, seoIndexPage.getSeoKeyword()));
					seoIndexPage.setSeoDescription(SeoUtils.getSeoIndexPageRegular(placeVo, seoIndexPage.getSeoDescription()));
				}
				MemcachedUtil.getInstance().set(key,MemcachedUtil.ONE_HOUR, seoIndexPage);
				comKeyDescService.insert(key, "获取seo默认规则:" + getId());
			}
		}else{
			seoIndexPage=(SeoIndexPage)obj;
		}
		
		if(fromPlace != null)
		{
			getFromPlaceSeoIndexPageRegular(fromPlace);
		}
		
		if( ("products".equals(currentTab) && "1".equals(currentPlace.getStage())) 
				|| ("ticket".equals(currentTab) && "2".equals(currentPlace.getStage()))
				|| ("ticket".equals(currentTab) && "3".equals(currentPlace.getStage())))
		{
			//目前currentTab=products 的只有国内目的地首页，所以该替换逻辑只对国内目的地首页有用
			seoIndexPage.setSeoTitle(SeoUtils.getPlaceSeoTitle(currentPlace.getSeoTitle(), seoIndexPage.getSeoTitle()));
			seoIndexPage.setSeoKeyword(SeoUtils.getPlaceSeoKeyword(currentPlace.getSeoKeyword(), seoIndexPage.getSeoKeyword()));
			seoIndexPage.setSeoDescription(SeoUtils.getPlaceSeoDescription(currentPlace.getSeoDescription(), seoIndexPage.getSeoDescription()));
		}
	}
	
	/**
	 * 目的地页面带有出发地的替换
	 * @param fromPlaceSeoIndexPage
	 * @param fromPlace
	 * @return
	 */
	private void getFromPlaceSeoIndexPageRegular(Place fromPlace){
		if(seoIndexPage!=null){
			seoIndexPage.setSeoTitle(SeoUtils.getFromPlaceSeoIndexPageRegular(fromPlace, seoIndexPage.getSeoTitle()));
			seoIndexPage.setSeoKeyword(SeoUtils.getFromPlaceSeoIndexPageRegular(fromPlace, seoIndexPage.getSeoKeyword()));
			seoIndexPage.setSeoDescription(SeoUtils.getFromPlaceSeoIndexPageRegular(fromPlace, seoIndexPage.getSeoDescription()));
		}
	}

	/**
	 * 获取页面树形结构目的地
	 * @return 
	 * @author:nixianjun 2013-7-8
	 */
	@SuppressWarnings("unchecked")
	@Action("/place/navigation")
	public String navigation() {
		String key=PlaceUtils.LOADNAVIGATION_MEMCACHED_PREFIX_KEY + getId();
		navigationMap=(Map<String,Object>)MemcachedUtil.getInstance().get(key);
		if(navigationMap==null){
			navigationMap=placeService.loadNavigation(getId()); 
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, navigationMap);
 		}
		return "navigation";
	}
	
	/**
	 * 根据用户传入的参数和IP进行判断取出默认的出发地ID
	 * 
	 * @param hasFromPlace
	 * @param fromPlaceList
	 * @return
	 */
	protected boolean setDefaultFormPlace(boolean hasFromPlace,List<Place> fromPlaceList) {
		if(this.fromDestId == null) {
			this.fromDestId = (Long)getRequestAttribute(Constant.IP_FROM_PLACE_ID);
			if (fromPlaceList != null && fromPlaceList.size() > 0) {
				for (Place cp : fromPlaceList) {
					if (fromDestId.equals(cp.getPlaceId())) {
						hasFromPlace = Boolean.TRUE;
						break;
					}
				}
				if (!hasFromPlace) {
					this.setFromDestId(Constant.DEFAULT_IP_FROM_PLACE_ID);
					for (Place cp : fromPlaceList) {
						if (fromDestId.equals(cp.getPlaceId())) {
							hasFromPlace = Boolean.TRUE;
							break;
						}
					}
				}
				if (!hasFromPlace) {
					this.setFromDestId(fromPlaceList.get(0).getPlaceId());
					hasFromPlace = Boolean.TRUE;
				}
			}
		} else {
			if (fromPlaceList != null && fromPlaceList.size() > 0) {
				for (Place comP : fromPlaceList) {
					if (fromDestId.equals(comP.getPlaceId())) {
						hasFromPlace = Boolean.TRUE;
						break;
					}
				}
			}
		}
		return hasFromPlace;
	}
	
	/**
	 * 获取该景点的点评统计信息
	 * @param placeId
	 */
	private CmtTitleStatisticsVO fillCmtTitleStatisticsVO(Long placeId){
		Object cacheCmtTitleStatis = MemcachedUtil.getInstance().get(
				"placePageInfo_cmtTitleStatis_" + placeId);
		if (null != cacheCmtTitleStatis) {
			LOG.debug("从MemCache中获取景点 " + placeId + " 点评统计信息");
		} else {
			LOG.debug("MemCache中获取景点 " + placeId + " 点评统计信息不存在或已经过期，需要重新获取");
			CmtTitleStatisticsVO cmtTitleStatisticsVO = cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(placeId);
			cacheCmtTitleStatis = cmtTitleStatisticsVO;
			if (null != cacheCmtTitleStatis) {
				MemcachedUtil.getInstance().set("placePageInfo_cmtTitleStatis_" + placeId,MemcachedUtil.ONE_HOUR,cacheCmtTitleStatis);
				comKeyDescService.insert("placePageInfo_cmtTitleStatis_" + placeId, "获取该景点的点评统计信息："+placeId);
			}
		}
		return (CmtTitleStatisticsVO)cacheCmtTitleStatis;
	}
	
	/**
	 * 获得某个景点的4个基本点评维度统计平均分
	 * @param placeId
	 */
	private List<CmtLatitudeStatistics> fillCmtLatitudeStatisticsList(Long placeId){
		Object cacheCmtLatitudeStat = MemcachedUtil.getInstance().get(
				"placePageInfo_cmtLatitudeStat_" + placeId);
		if (null != cacheCmtLatitudeStat) {
			LOG.debug("从MemCache中获取景点 " + placeId + " 基本点评维度统计信息");
		} else {
			LOG.debug("MemCache中获取景点 " + placeId + " 基本点评维度统计信息不存在或已经过期，需要重新获取");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("placeId", placeId);
			List<CmtLatitudeStatistics> cmtLatitudeStatisticsList = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(params);
			cacheCmtLatitudeStat = cmtLatitudeStatisticsList;
			if (null != cacheCmtLatitudeStat) {
				MemcachedUtil.getInstance().set("placePageInfo_cmtLatitudeStat_" + placeId,MemcachedUtil.ONE_HOUR,cacheCmtLatitudeStat);
				comKeyDescService.insert("placePageInfo_cmtLatitudeStat_" + placeId, "获得某个景点的4个基本点评维度统计平均分："+placeId);
			}
		}
		return (List<CmtLatitudeStatistics>) cacheCmtLatitudeStat;
	}
	
	/**
	 * 获取该景点的最新审核过的10条景点点评
	 * @param placeId
	 */
	private List<CommonCmtCommentVO> fillCmtCommentVOList(Long placeId){
		Object cacheCmtCommentVOList = MemcachedUtil.getInstance().get(
				"placePageInfo_cmtCommentVOList_" + placeId);
		if (null != cacheCmtCommentVOList) {
			LOG.debug("从MemCache中获取景点 " + placeId + " 的最新点评");
		} else {
			LOG.debug("MemCache中获取景点 " + placeId + " 的最新点评不存在或已经过期，需要重新获取");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("placeId", placeId);
			parameters.put("isAudit",  Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
			parameters.put("getPlaceCmts", "Y");  //取景点点评
			parameters.put("createTime321", "true");
			parameters.put("_startRow", "0");
			parameters.put("_endRow", 10);//默认景点页获取10条点评
			parameters.put("createTime321", "true");
			List<CommonCmtCommentVO> cmtCommentVOList = cmtCommentService.getCmtCommentList(parameters);
			cmtCommentVOList = composeUserImagOfComment(cmtCommentVOList);
			cacheCmtCommentVOList = cmtCommentVOList;
			if (null != cacheCmtCommentVOList) {
				MemcachedUtil.getInstance().set("placePageInfo_cmtCommentVOList_" + placeId,MemcachedUtil.ONE_HOUR,cacheCmtCommentVOList);
				comKeyDescService.insert("placePageInfo_cmtCommentVOList_" + placeId, "获取该景点的最新审核过的10条景点点评"+placeId);
			}
		}
		return (List<CommonCmtCommentVO>) cacheCmtCommentVOList;
	}
	
	//给点评设置用户图像
	private List<CommonCmtCommentVO> composeUserImagOfComment(List<CommonCmtCommentVO> cmtCommentList){
		/*if(cmtCommentList != null && cmtCommentList.size() > 0 && cmtCommentList.size() < 1000){
			List<Long> userIdList = new ArrayList<Long>();
			for(CommonCmtCommentVO vo : cmtCommentList){
				userIdList.add(vo.getUserId());
			}
			List<UserUser> userUserList = userUserProxy.queryUserUserByUserId(userIdList);
			
			if(userUserList != null && userUserList.size() > 0){
				//设置用户图像给CmtCommentVO
				if(cmtCommentList != null && cmtCommentList.size() > 0){
				    for (int i = 0; i < cmtCommentList.size(); i++) {
				    	for(UserUser userUser : userUserList){
				    		CommonCmtCommentVO cmtVo = (CommonCmtCommentVO)cmtCommentList.get(i);
				    		if(cmtVo.getUserId() != null && (cmtVo.getUserId().longValue() == userUser.getId().longValue())){
				    			cmtCommentList.get(i).setUserImg(userUser.getImageUrl());
				    		}
				    	}
					}
				}
			}
		}*/
		return cmtCommentList;
	}
	
	/**
	 * 填充景点页点评信息
	 * @param placeId
	 * @throws Exception
	 */
	protected void listCmtsOfDest(Long placeId) {	
		if (null != placeId) {
			// 获取该景点的点评统计信息
			cmtTitleStatisticsVO = fillCmtTitleStatisticsVO(placeId);

			// 获得某个景点的4个基本点评维度统计平均分
			cmtLatitudeStatisticsList = fillCmtLatitudeStatisticsList(placeId);
			
			//获取该景点的最新审核过的10条景点点评
			cmtCommentVOList = fillCmtCommentVOList(placeId);			
		}
	}	
	
	/**
	 * 打印调试的信息
	 * @param log 日志输出器
	 * @param message 调试信息
	 */
	protected void debug(final Log log, final String message) {
		if (null != log && log.isDebugEnabled() && StringUtils.isNotBlank(message)) {
			log.debug(message);
		}
	}

	public Long getId() {
		if (null != place) {
			return place.getPlaceId();
		} else {
			return null;
		}
	}

	public Map<String, Object> getNavigationMap() {
		return navigationMap;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public SeoIndexPage getSeoIndexPage() {
		return seoIndexPage;
	}

	public void setSeoIndexPageService(SeoIndexPageService seoIndexPageService) {
		this.seoIndexPageService = seoIndexPageService;
	}

	public Long getFromDestId() {
		return fromDestId;
	}

	public void setFromDestId(Long fromDestId) {
		this.fromDestId = fromDestId;
	}

	public List<Place> getFromPlaceList() {
		return fromPlaceList;
	}

	public void setProductSearchInfoService(ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}
	public String getCurrentTab() {
		if (currentTab == null) {
			return "products";
		}
		return currentTab;
	}

	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Page getPageConfig() {
		return pageConfig;
	}

	public void setPageConfig(Page pageConfig) {
		this.pageConfig = pageConfig;
	}

	public CmtTitleStatisticsVO getCmtTitleStatisticsVO() {
		return cmtTitleStatisticsVO;
	}

	public List<CmtLatitudeStatistics> getCmtLatitudeStatisticsList() {
		return cmtLatitudeStatisticsList;
	}

	public List<CommonCmtCommentVO> getCmtCommentVOList() {
		return cmtCommentVOList;
	}

	public List<Place> getSonPlaceList() {
		return sonPlaceList;
	}

	public List<ProductSearchInfo> getPlaceInfoPrdSearchList() {
		return placeInfoPrdSearchList;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public void setCmtTitleStatistisService(
			CmtTitleStatistisService cmtTitleStatistisService) {
		this.cmtTitleStatistisService = cmtTitleStatistisService;
	}

	public void setCmtLatitudeStatistisService(
			CmtLatitudeStatistisService cmtLatitudeStatistisService) {
		this.cmtLatitudeStatistisService = cmtLatitudeStatistisService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
	/**
	 * 根据来源不同显示不同的热线电话
	 * @return
	 */
	public String getShowDifferntHotLine() {
		String tele = this.getRequest().getParameter("tele");
		if (null != tele) {
			Cookie cookie = new Cookie("tele", tele);
			cookie.setDomain(".lvmama.com");
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			getResponse().addCookie(cookie);	
		} else {
			tele =getCookieValue("tele");
		}
		return tele;
	}	

	public boolean isHasInitDestId() {
		return hasInitDestId;
	}

	public void setHasInitDestId(boolean hasInitDestId) {
		this.hasInitDestId = hasInitDestId;
	}

	public void setComKeyDescService(ComKeyDescService comKeyDescService) {
		this.comKeyDescService = comKeyDescService;
	}
	public boolean isLogin(){
		UserUser user = (UserUser)getSession(Constant.SESSION_FRONT_USER);
		if(user!=null){
			return true;
		}
		return false;
	}
	
	public String getPinYinUrl(){
		return place.getPinYinUrl();
	}
	public List<PlaceHistoryCookie> getHistoryCookie(){
		Cookie cookie = ServletUtil.getCookie(getRequest(),Constant.HOLIDAY_HOTEL_PLACEID);
		List<PlaceHistoryCookie> historyCookieList = new ArrayList<PlaceHistoryCookie>();
		try {
			//查询cookie中的己有浏览记录
			if(cookie != null){
				JSONArray cookieList = JSONArray.fromObject(URLDecoder.decode(cookie.getValue(),"UTF-8"));
				for (int i = 0; i < cookieList.size(); i++) {
					historyCookieList.add((PlaceHistoryCookie)JSONObject.toBean(cookieList.getJSONObject(i),PlaceHistoryCookie.class));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return historyCookieList;
	}
	public void setHistoryCookie(Map<String, Object> hotelPageInfoMap){
		try {
			PlaceSearchInfo placeSearch = hotelPageInfoMap.get("placeSearchInfo") == null ? null : (PlaceSearchInfo) hotelPageInfoMap.get("placeSearchInfo");
			if(placeSearch != null){
				PlaceHistoryCookie historyCookie = new PlaceHistoryCookie();
				historyCookie.setPlaceId(placeSearch.getPlaceId().toString());
				historyCookie.setName(placeSearch.getName());
				historyCookie.setProductsPrice(placeSearch.getProductsPrice());
				//获取第一张大图作为缩略图
				List<PlacePhoto> photoList = hotelPageInfoMap.get("placePhotoList") == null ? null: (List<PlacePhoto>) hotelPageInfoMap.get("placePhotoList");
				if(photoList != null){
					historyCookie.setImageUrl(photoList.get(0).getImagesUrl());
				}
				Cookie cookie = ServletUtil.getCookie(getRequest(),Constant.HOLIDAY_HOTEL_PLACEID);
				JSONArray cookieList = new JSONArray();
				//新纪录标识
				boolean flag = true;
				if(cookie != null){
					cookieList = JSONArray.fromObject(URLDecoder.decode(cookie.getValue(),"UTF-8"));
					//如果新记录己存在COOKIE中，刚将新记录移至第一位
					for (int i = 0; i < cookieList.size(); i++) {
						PlaceHistoryCookie historyTemp = (PlaceHistoryCookie)JSONObject.toBean(cookieList.getJSONObject(i),PlaceHistoryCookie.class);
						if(historyTemp.getPlaceId().equals(placeSearch.getPlaceId().toString())){
							cookieList.remove(i);
							cookieList.add(0, historyCookie);
							flag = false;
							break;
						}
					}
					//cookie中达到最大值时，清除超过最大值的记录
					if(cookieList.size() >= Constant.HOLIDAY_HOTEL_HISTORY_SIZE){
						for (int i = Constant.HOLIDAY_HOTEL_HISTORY_SIZE-1; i < cookieList.size(); i++) {
							cookieList.remove(i);
						}
					}
				}
				//将新记录、排在第一位
				if(flag)cookieList.add(0,historyCookie);
				//加入cookie
				ServletUtil.addCookie(getResponse(),Constant.HOLIDAY_HOTEL_PLACEID,URLEncoder.encode(cookieList.toString(),"UTF-8"),30,false);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
