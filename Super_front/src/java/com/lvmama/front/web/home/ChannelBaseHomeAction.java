package com.lvmama.front.web.home;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.utils.CommHeaderUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.vo.Constant;

/**
 * 频道页基础类
 * 
 * @author nixianjun 2013-6-9
 * @version 1.0
 */
public abstract class ChannelBaseHomeAction extends BaseHomeAction {
	private static final long serialVersionUID = 8420403189081671847L;

	/**
	 * 默认的城市出发地
	 */
	protected static final Long DEFAULT_FROM_PLACE_ID = 79L;

	protected Long fromPlaceId;
	protected String fromPlaceCode;
	protected String fromPlaceName;
	protected String stationName;
	//参数Datacode
	protected String paramDataCode="";
	protected String paramDataSearchName="";
	//给推荐景点div用的属性
	protected List<RecommendInfo> recommendInfoForScenicList;


	// 团购服务
	private GroupDreamService groupDreamService;
	
	protected String provinceId;
	protected String cityId;
	protected String cityName;
	
	@Action("/homePage/head")
	public void head() {
		try {
			CommHeaderUtil.getHeadContent(getResponse().getWriter());
		} catch (IOException ioe) {
			
		}
	}
	

	
	/**
	 * 初始化并且设定seo
	 * @param containerCode 容器代号
	 * @param channel  频道页代号
	 * @author:nixianjun 2013-6-14
	 */
	@Deprecated
	public void init(String containerCode, String channel) {
		if (StringUtils.isBlank(provinceId)) {
			provinceId = (String) ServletUtil.getCookieValue(getRequest(),
					Constant.IP_PROVINCE_PLACE_ID);
			if (provinceId == null) {
				provinceId = (String) getRequest().getAttribute(
						Constant.IP_PROVINCE_PLACE_ID);
			}
		} else {
			ServletUtil.addCookie(getResponse(), Constant.IP_PROVINCE_PLACE_ID,
					provinceId, 30);
		}
		//如果cookie和请求都没有取到省provinceId
		if(StringUtils.isEmpty(provinceId)){
			provinceId=PindaoPageUtils.PROVINCE.shanghai.getCode();
		}
		 fromPlaceId=PindaoPageUtils.executeDataForPindao(provinceId,channel);
		 if(fromPlaceId==null){
			 fromPlaceId=DEFAULT_FROM_PLACE_ID;
		 }
		 if (fromPlaceCode == null) {
		     fromPlaceCode = PindaoPageUtils.PLACEID_PLACECODE.getPlacecode(fromPlaceId);
 		 }
		if(fromPlaceName==null){
			fromPlaceName = (String) getRequest().getAttribute(Constant.IP_FROM_PLACE_NAME);
		}
		if(stationName==null){
			stationName=PindaoPageUtils.PROVINCE.getCnName(provinceId);
		}
		super.initSeoIndexPage(channel);
	}
	/**
	 * 初期处理
	 * @param channel
	 * @author nixianjun 2013-12-25
	 */
	public void initExcute(String channel) {
		  calculationForfromPlaceId(channel);
		if(fromPlaceName==null){
			fromPlaceName = PindaoPageUtils.getFromPlaceName(stationName,channel);
		}
		super.initSeoIndexPage(channel);
	}
	
	/**
	 * 处理设置fromPlaceId
	 * @return
	 * @author nixianjun 2013-12-24
	 * @throws UnsupportedEncodingException 
	 */
	protected void calculationForfromPlaceId(String channel)  {
		//切分站逻辑--请求有参数逻辑
	    if(StringUtils.isNotBlank(cityId)&&StringUtils.isNotBlank(provinceId)&&StringUtils.isNotBlank(stationName)){
			
			ServletUtil.addCookie(getResponse(), Constant.IP_CITY_PLACE_ID,
					cityId, 30);
			ServletUtil.addCookie(getResponse(), Constant.IP_PROVINCE_PLACE_ID,
					provinceId, 30);
			try {
				ServletUtil.addCookie(getResponse(), Constant.IP_CITY_NAME, URLEncoder.encode(stationName,"utf-8"), 30);
			} catch (UnsupportedEncodingException e) {
 				e.printStackTrace();
			}
		}else{
			/**
			 * 先取cookie里的值，再去请求里的值
			 */
			cityId = (String) ServletUtil.getCookieValue(getRequest(),
					Constant.IP_CITY_PLACE_ID);
			provinceId = (String) ServletUtil.getCookieValue(getRequest(),
					Constant.IP_PROVINCE_PLACE_ID);
			try {
				if(null==ServletUtil.getCookie(getRequest(), Constant.IP_CITY_NAME)){
					stationName="";
				}else{
					stationName = java.net.URLDecoder.decode(ServletUtil.getCookie(getRequest(), Constant.IP_CITY_NAME).getValue(),"utf-8");
				}
			} catch (UnsupportedEncodingException e) {
 				e.printStackTrace();
			}
			if (StringUtils.isBlank(cityId)) {
				cityId = (String) getRequest().getAttribute(
						Constant.IP_CITY_PLACE_ID);
			}
			if (StringUtils.isBlank(provinceId )) {
				provinceId = (String) getRequest().getAttribute(
						Constant.IP_PROVINCE_PLACE_ID);
			}
			if(StringUtils.isBlank(stationName)){
				stationName= (String) getRequest().getAttribute(
						Constant.IP_CITY_NAME);
			}
		}
	    /**
	     *如果是空，默认是上海
	     * 最后处理
	     */
	    if (StringUtils.isBlank(cityId)) {
			cityId =PindaoPageUtils.CITY.shanghai.getCode();
		}
		if (StringUtils.isBlank(provinceId )) {
			provinceId = PindaoPageUtils.PROVINCE.shanghai.getCode();
		}
		if(StringUtils.isBlank(stationName)){
			stationName= PindaoPageUtils.CITY.shanghai.getCnName();
		}
	    if(cityId.equals(PindaoPageUtils.CITY.shenzhen.getCode())){
			fromPlaceId=PindaoPageUtils.SZ_PLACEID;
		}else if(cityId.equals(PindaoPageUtils.CITY.chongqin.getCode())){
			fromPlaceId=PindaoPageUtils.CQ_PLACEID;
		}else if(cityId.equals(PindaoPageUtils.CITY.suzhou.getCode()) && channel.equals(Constant.CHANNEL_ID.CH_FREETOUR.name())){
            fromPlaceId=PindaoPageUtils.SUZ_PLACEID;
        }else if(cityId.equals(PindaoPageUtils.CITY.wuxi.getCode()) && channel.equals(Constant.CHANNEL_ID.CH_FREETOUR.name())){
            fromPlaceId=PindaoPageUtils.WX_PLACEID;
        }else{
			fromPlaceId=PindaoPageUtils.executeDataForPindao(provinceId,channel);
		}
		if(fromPlaceId==null){
			 fromPlaceId=DEFAULT_FROM_PLACE_ID;
		 }
		 if (fromPlaceCode == null) {
		     fromPlaceCode = PindaoPageUtils.PLACEID_PLACECODE.getPlacecode(fromPlaceId);
 		 }
  	}

	
	/**
	 * 获取各个频道页所推荐的内容()
	 * @param channelPage 频道名，对于recommend_block表的PAGE_CHANNEL字段
	 * @param defaultCommonBlockId 默认的块号，代表着全国统一推荐位的块号
	 * @param containerCode 频道页在容器中的标识代码
	 * @param fromPlaceId 当前用户所选择的出发地
	 * @param defaultFromPlaceId 默认选择出发地。 当fromPlaceId所在区域无相关数据时，需要使用默认出发地的数据填充
	 * @author nixianjun
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, List<RecommendInfo>> getRecommentInfoResult(final String channelPage, final Long defaultCommonBlockId, final String containerCode, final Long fromPlaceId, final Long defaultFromPlaceId) {
		String key = "getRecommentInfoResult_" + channelPage + "_" + defaultCommonBlockId + "_" + containerCode + "_" + fromPlaceId;
		Map<String, List<RecommendInfo>> recommendInfoMap = (Map<String, List<RecommendInfo>>) MemcachedUtil.getInstance().get(key);
		
		if (recommendInfoMap == null) {
			LOG.info("MemCache beginning:" + key);
			recommendInfoMap = recommendInfoService.getRecommendInfoMap(containerCode, defaultCommonBlockId, fromPlaceId, channelPage);
			
			if (null != fromPlaceId && fromPlaceId.longValue() != DEFAULT_FROM_PLACE_ID.longValue()) {
  				Map<String, List<RecommendInfo>> defaultRecommendInfoMap = recommendInfoService.getRecommendInfoMap(containerCode, null, defaultFromPlaceId, channelPage);
				//将默认出发地存在的数据，但当前出发地不存在的数据进行复制。以便分公司不维护数据时，使用总部的数据
				if (null != defaultRecommendInfoMap && !defaultRecommendInfoMap.isEmpty()) {  
					Set<String> keys = defaultRecommendInfoMap.keySet();
					for (String _key : keys) {
						List<RecommendInfo> _currentValue = recommendInfoMap.get(_key); 
						List<RecommendInfo> _defaultValue = defaultRecommendInfoMap.get(_key);
						if ((null == _currentValue || _currentValue.isEmpty()) && null != _defaultValue && !_defaultValue.isEmpty()) {
							recommendInfoMap.put(_key, _defaultValue);
						}
					}
				}
			}
			MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,recommendInfoMap);
			if (null == MemcachedUtil.getInstance().get(key)) {
				LOG.info("SAVE MemCache Failure:" + key);
			}
			return recommendInfoMap;
		}else {
			return recommendInfoMap;
		}
 	}
	/**
	 * map设定两个最新团购产品
	 * 
	 * @param tuanGouName
	 * @param productType
	 * @param subProductTypeList
	 * @param defaultFromPlaceId 
	 * @author:nixianjun 2013-6-9
	 * 
	 */
	protected void getLastTuanGou(String tuanGouName, String productType,List<String> subProductTypeList, Long defaultFromPlaceId) {
		    List<ProdProduct> prodProductList=getTuanGouProdProduct(productType,subProductTypeList,this.fromPlaceId);//本站推荐 团购
			if (prodProductList != null && !prodProductList.isEmpty()
					 ) {
				if( prodProductList.size() >= 2){
					map.put(tuanGouName, prodProductList.subList(0, 2));
				}else if( prodProductList.size() >= 1){
					map.put(tuanGouName, prodProductList.subList(0, 1));
				}
			}else if(defaultFromPlaceId!=null&&this.fromPlaceId!=defaultFromPlaceId){
			    List<ProdProduct> defaultprodProductList=getTuanGouProdProduct(productType,subProductTypeList,defaultFromPlaceId);//默认站推荐团购
			    if (defaultprodProductList != null && !defaultprodProductList.isEmpty()
					 ) {
			    	if(defaultprodProductList.size() >= 2){
			    		map.put(tuanGouName, defaultprodProductList.subList(0, 2));
			    	}else if(defaultprodProductList.size() >= 1){
			    		map.put(tuanGouName, defaultprodProductList.subList(0, 1));
			    	}
			    }
			}
	}
	/**
	 * 通过出发点得到团购频道的团购产品
	 * @param productType
	 * @param subProductTypeList
	 * @param fromPlaceIdParam
	 * @return
	 * @author:nixianjun 2013-6-18
	 */
	private List<ProdProduct> getTuanGouProdProduct(String productType,List<String> subProductTypeList, Long fromPlaceIdParam){
		
		String key ="getTuanGouProdProduct_"+productType+"_"+subProductTypeList+"_"+fromPlaceIdParam;
	   List<ProdProduct> memList = (List<ProdProduct>) MemcachedUtil.getInstance().get(key);
	   if(null==memList){
		   // 得到团购频道产品id列表
		   List<Long> productIdList = getTuanGouChannelProdcutIds(fromPlaceIdParam);
		   // 根据推荐信息找到团购产品
		   if (productIdList != null && !productIdList.isEmpty()
				   && productIdList.size() > 0) {
			   Map<String, Object> param = new HashMap<String, Object>();
			   param.put("productType", productType);
			   param.put("createTimeSort", "desc");
			   param.put("productIdList", productIdList);
			   if(subProductTypeList!=null&&!subProductTypeList.isEmpty()){
				   param.put("subProductTypeList", subProductTypeList);
			   }
			   List<ProdProduct> prodProductList = groupDreamService
					   .queryOnlineProductByParams(param);
			   
			   MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR, prodProductList);
			   if (null == MemcachedUtil.getInstance().get(key)) {
					LOG.info("SAVE MemCache Failure:" + key);
				}
			   return prodProductList;
		   }else {
			   return null;
		   }
	   }else{
		   return memList;
	   }
	}

	/**
	 * 通过出发点得到团购频道产品id列表
	 * 
	 * @return
	 * @author:nixianjun 2013-6-9
	 */
	private List<Long> getTuanGouChannelProdcutIds(Long fromPlaceIdParam) {
		Long tuangou_commonBlockId = PindaoPageUtils.TUANGOU_COMMONBLOCKID;
		String tuangou_channelPage = PindaoPageUtils.TUANGOU_CHANNELPAGE;
		String tuangou_containerCode = PindaoPageUtils.TUANGOU_CONTAINERCODE;
		Map<String, List<RecommendInfo>> groupMap = recommendInfoService
				.getRecommendInfoMap(tuangou_containerCode,
						tuangou_commonBlockId, fromPlaceIdParam,
						tuangou_channelPage);
		List<RecommendInfo> topList = groupMap.get(tuangou_channelPage
				+ "_products");
		List<Long> productIdList = new ArrayList<Long>();
		if (topList != null && topList.size() > 0) {
			for (RecommendInfo rec : topList) {
				if (rec != null && rec.getRecommObjectId() != null) {
					productIdList.add(Long.valueOf(rec.getRecommObjectId()));
				}
			}
		}
		return productIdList;
	}
	
    
    /**
     * 通过datacode获取对应模块值list
     * @param channelPage
     * @param commonBlockId
     * @param containerCode
     * @param dataCode 
     * @return
     * @author:nixianjun 2013-7-2
     */
	protected  List<RecommendInfo> getRecommendInfoData(final String channelPage, final Long commonBlockId, final String containerCode,String dataCode) {
		  Map<String, List<RecommendInfo>> r=getRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, DEFAULT_FROM_PLACE_ID);
		  if(null!=r&&null!=r.get(channelPage+"_"+dataCode)){
			  return  r.get(channelPage+"_"+dataCode);
		  }else {
			  return null;
		  }
    }
	
	public Long getFromPlaceId() {
		return fromPlaceId;
	}

	public void setFromPlaceId(Long fromPlaceId) {
		this.fromPlaceId = fromPlaceId;
	}

	public void setFromPlaceCode(String fromPlaceCode) {
		this.fromPlaceCode = fromPlaceCode;
	}

	public String getFromPlaceCode() {
		return fromPlaceCode;
	}

	public String getFromPlaceName() {
		return fromPlaceName;
	}

	public void setFromPlaceName(String fromPlaceName) {
		this.fromPlaceName = fromPlaceName;
	}

	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}

	public String getParamDataCode() {
		return paramDataCode;
	}



	public void setParamDataCode(String paramDataCode) {
		this.paramDataCode = paramDataCode;
	}



	public List<RecommendInfo> getRecommendInfoForScenicList() {
		return recommendInfoForScenicList;
	}



	public void setRecommendInfoForScenicList(
			List<RecommendInfo> recommendInfoForScenicList) {
		this.recommendInfoForScenicList = recommendInfoForScenicList;
	}

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

	public String getParamDataSearchName() {
		return paramDataSearchName;
	}

	public void setParamDataSearchName(String paramDataSearchName) {
		this.paramDataSearchName = paramDataSearchName;
	}


	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}


	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}


	/**
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}


	/**
	 * @param stationName the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	public Map<String, String> getProvinceMap(){
		return PindaoPageUtils.PROVINCE.getMap();
	}



	public String getCityId() {
		return cityId;
	}



	public void setCityId(String cityId) {
		this.cityId = cityId;
	}



	public String getCityName() {
		return cityName;
	}



	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public Map<String, List<RecommendInfo>>  getRecommendInfoMap(){
		return (Map<String, List<RecommendInfo>>) map.get("recommendInfoMainList");
	}
}