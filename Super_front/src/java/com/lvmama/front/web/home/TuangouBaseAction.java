package com.lvmama.front.web.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdTagService;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.tools.taglib.PageConfig;
import com.lvmama.front.web.seckill.SeckillMemcachedUtil;

/**
 * 团购
 * 
 * @author yifan
 * 
 */
@Results({ @Result(name = "success", location = "/WEB-INF/pages/tuangouNew/tuangouIndex.ftl", type = "freemarker"),
@Result(name = "tuangouList", location = "/WEB-INF/pages/tuangouNew/tuangouList.ftl", type = "freemarker")})
public class TuangouBaseAction extends ToPlaceOnlyTemplateHomeAction {
	private static final long serialVersionUID = 3794668774194856232L;
	private List<Map<String, Object>> groupPrdList = new ArrayList<Map<String, Object>>();
	private ComPictureService comPictureService;
	//点评
	private CmtCommentService cmtCommentService;
	private CmtLatitudeStatistisService cmtLatitudeStatistisService;
	private ProdTagService prodTagService;
	private ProdProductPlaceService prodProductPlaceService;
	private GroupDreamService groupDreamService;
	private ProdSeckillRuleService prodSeckillRuleService;
	
	private Map<String, List<RecommendInfo>> groupMap;
	private Map<Long, Object> recommendPrdMap = new HashMap<Long, Object>();
	public final String[] cityArr = {"北京", "北海","大连","贵州","桂林","哈尔滨","杭州","九寨沟","昆明","千岛湖","青岛","三亚","上海","四川","台湾","天津","西安","西藏","厦门","云南","张家界","巴厘岛","韩国","普吉岛","日本","塞班岛","泰国"};
	/**
	 * 频道 : 门票=ticket 酒店=hotel 自由行=free 跟团游=group 长途游=long 出境游=foreign
	 **/
	private String type = "all";
	/** 地区筛选 */
	private String city = "all";
	/** 排序参数 ,默认=空 ,热门='hot',价格='price' */
	private String sort = "all";
	private String tgType = "all";
	/** 当前页数 */
	private int page = 1;
	/** 分页对象 */
	private PageConfig<Map<String, Object>> pageConfig;
	/** 产品每页显示数量 */
	private int pageSize = 30;
	/** 目的地集合 */
	private List<Map<String, Object>> cities = new ArrayList<Map<String,Object>>();
	private Long commonBlockId = 14516L;
	private String channelPage = "tuangou";
	private String containerCode = "TUANGOU_RECOMMEND";
	// 默认的城市出发地
    protected static final Long DEFAULT_FROM_PLACE_ID = 79L;
	// 首页各类产品最多显示条数
	public static final int PRODUCTS_MAXCOUNT = 40;
	public static final int RECOMMEND_PRODUCT = 4;
	private Map<String, List<Map<String, Object>>> indexMap = new HashMap<String, List<Map<String, Object>>>();
	private List<RecommendInfo> recommendBanner;
	private List<RecommendInfo> recommendPicList;
	private List<RecommendInfo> recommendFocus;
	private Map<Long, Map<String, Object>> recommendProdMap;
	private Map<String,Object> dataCountMap = new HashMap<String, Object>();
	private Map<String,Object> citymap = new HashMap<String, Object>();
	private Map<String,Object> allcitymap = new HashMap<String, Object>();
	private Map<String,Object> selParamMap = new HashMap<String, Object>();
	private PlaceService placeService;
	private List<Map<String, Object>> recommMap = new ArrayList<Map<String, Object>>();
	/**
	 * 团购首页
	 */
	@Action("/homePage/tuangouIndex")
	public String execute() throws Exception {
		this.setSessionPlace();
		super.init(Constant.CHANNEL_ID.CH_TUANGOU.name());
		this.initTuangou();
		this.setTuanGouData();
		return SUCCESS;
	}
	
	
    
	/**
	 * 团购列表页
	 * @return
	 */
	@Action("/homePage/tuangouList")
	public String tuangouList(){
		this.setSessionPlace();
		super.init("CH_TUANGOULIST");
		this.initTuangou();
		super.comSeoIndexPage.setSeoTitle(this.getSeoIndexPageRegular(super.comSeoIndexPage.getSeoTitle()));
		super.comSeoIndexPage.setSeoDescription(this.getSeoIndexPageRegular(super.comSeoIndexPage.getSeoDescription()));
		super.comSeoIndexPage.setSeoKeyword(this.getSeoIndexPageRegular(super.comSeoIndexPage.getSeoKeyword()));
		this.setTuanGouList();
		this.setSelectParamMap();
		return "tuangouList";
	}
	
	/**
	 * 初始化团购频道信息
	 */
	@SuppressWarnings("unchecked")
	private void initTuangou(){
		
		//获取推荐数据
        if (null != fromPlaceId && fromPlaceId.longValue() != DEFAULT_FROM_PLACE_ID.longValue()) {
            // 如果出发地已经是上海，则不需要使用默认出发地当做备用字段,不是上海的分站
            putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, DEFAULT_FROM_PLACE_ID.longValue());
        } else {
            putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, null);
        }
		groupMap = (Map<String, List<RecommendInfo>>) map.get("recommendInfoMainList");
	}
	
	/**
	 * 获取团购列表页数据
	 */
	private void setTuanGouList(){
		List<RecommendInfo> topList = groupMap.get(channelPage + "_productsnew");
		Map<Long, Map<String, Object>> returnProductMap = getProductList(topList,"productsnew");
		this.setCityMap(topList, returnProductMap);
		citymap.clear();
		String proudctType,subProductType;
		ProdProduct pp;
		if(topList!=null && topList.size()>0){
			for (RecommendInfo rec : topList) {
				Map<String, Object> returnMap = returnProductMap.get(Long
						.valueOf(rec.getRecommObjectId()));
				if (returnMap != null) {
					pp = (ProdProduct) returnMap.get("prodProduct");
					proudctType = pp.getProductType();
					subProductType = pp.getSubProductType();
					returnMap.put("viewRecommendInfo", rec);
					returnMap.put("isRecommendPrd", true);
					returnMap.put("seq", rec.getSeq());
					recommendPrdMap.put(Long.valueOf(rec.getRecommObjectId()),
							rec.getRecommObjectId());
					if (tgType.equals("all")
							|| (rec.getBakWord1() != null
							&& rec.getBakWord1().equals("1") && tgType.equals("xsms"))
							|| (rec.getBakWord2() != null
							&& rec.getBakWord2().equals("1") && tgType.equals("hyr"))
							|| (rec.getBakWord3() != null
							&& rec.getBakWord3().equals("1") && tgType.equals("zyk"))
							|| (rec.getBakWord4() != null
							&& rec.getBakWord4().equals("1") && tgType.equals("wxzq"))) {
						this.selectRec(returnMap, proudctType, subProductType,
								rec);
					}
				}
			}
		}
		//小于一页则在底部推荐对应的推荐产品
        if((pageSize/2)>groupPrdList.size() && !(city.equals("all") && type.equals("all") && tgType.equals("all"))){
        	if(topList != null && topList.size() > 0){
				for (RecommendInfo rec : topList) {
				    if(recommMap.size()>=pageSize/2){
	                    break;
	                }
					Map<String, Object> returnMap = returnProductMap.get(Long
							.valueOf(rec.getRecommObjectId()));
					if (returnMap != null) {
						pp = (ProdProduct) returnMap.get("prodProduct");
						proudctType = pp.getProductType();
						subProductType = pp.getSubProductType();
						returnMap.put("viewRecommendInfo", rec);
						returnMap.put("isRecommendPrd", true);
						returnMap.put("seq", rec.getSeq());
						recommendPrdMap.put(Long.valueOf(rec.getRecommObjectId()),
								rec.getRecommObjectId());
						this.selectTopRec(returnMap, proudctType,
								subProductType, rec, false);
					}
				}
        	}
        }
		this.setNavigation(topList, returnProductMap);
		recommendFocus = initRecommendInfo("_focusnew");
		this.dataSoft();
	}
	
	/**
	 * 选择推荐的产品
	 * @param returnMap
	 * @param proudctType
	 * @param subProductType
	 * @param rec
	 */
	@SuppressWarnings("unchecked")
    private void selectTopRec(Map<String, Object> returnMap, String proudctType,
            String subProductType, RecommendInfo rec,boolean flag) {
        if (!tgType.equals("all")) {
            if (tgType.equals("xsms")) {
                if (!(rec.getBakWord1() != null && rec.getBakWord1()
                        .equals("1"))) {
                    flag = true;
                }
            } else if (tgType.equals("hyr")) {
                if (!(rec.getBakWord2() != null && rec.getBakWord2()
                        .equals("1"))) {
                    flag = true;
                }
            } else if (tgType.equals("zyk")) {
                if (!(rec.getBakWord3() != null && rec.getBakWord3()
                        .equals("1"))) {
                    flag = true;
                }
            } else if (tgType.equals("wxzq")) {
                if (!(rec.getBakWord4() != null && rec.getBakWord4()
                        .equals("1"))) {
                    flag = true;
                }
            }
        }
        if (!type.equals("all")) {
            if (type.equalsIgnoreCase("ticket")){
                if(!type.equalsIgnoreCase(proudctType)){
                    flag = true;
                }
            } else if(type.equalsIgnoreCase("hotel")){
                if(!type.equalsIgnoreCase(proudctType)){
                    flag=true;
                }
            }else if (type.equalsIgnoreCase("china") && !(subProductType
                    .equalsIgnoreCase("GROUP_LONG") || subProductType
                    .equalsIgnoreCase("FREENESS_LONG"))) {
                flag = true;
            } else if (type.equalsIgnoreCase("surround") && !(subProductType
                    .equalsIgnoreCase("FREENESS")
                    || subProductType.equalsIgnoreCase("SELFHELP_BUS") || subProductType
                        .equalsIgnoreCase("GROUP"))) {
                flag = true;
            } else if (type.equalsIgnoreCase("abroad") && !(subProductType
                    .equalsIgnoreCase("GROUP_FOREIGN") || subProductType
                    .equalsIgnoreCase("FREENESS_FOREIGN"))) {
                flag = true;
            }
        }
        if((!flag) && !city.equals("all")){
            List<Place> recommendPrdPlace = (List<Place>) returnMap
                    .get("recommendPrdPlace");
            for (Place place : recommendPrdPlace) {
                if ("true".equalsIgnoreCase(place.getTo())) {
                    if (place.getPlaceType() != null) {
                        if (place.getPlaceType().equalsIgnoreCase("DOMESTIC")
                                || place.getPlaceType().equalsIgnoreCase("ABROAD")) {
                            if (!city.equalsIgnoreCase(place.getParentPinYin())) {
                                flag = true;
                            }
                        } else {
                            if (!city.equalsIgnoreCase(place.getPinYin())) {
                                flag = true;
                            }
                        }
                    }
                }
            }
        }
        if(flag){
            recommMap.add(returnMap);
        }
    }   
	
    private String getSeoIndexPageRegular(String seoStr){
        String result=seoStr==null?"":seoStr;
        if(type.equalsIgnoreCase("ticket")){
            result = result.replace( "{type}","门票");
        }else if(type.equalsIgnoreCase("hotel")){
        	result = result.replace( "{type}","酒店");
        }else if(type.equalsIgnoreCase("surround")){
        	result = result.replace( "{type}","周边游");
        }else if(type.equalsIgnoreCase("abroad")){
        	result = result.replace( "{type}","出境游");
        }else if(type.equalsIgnoreCase("china")){
        	result = result.replace( "{type}","国内游");
        }else {
        	result = result.replace( "{type}","");
        }
        if(sort.equalsIgnoreCase("price")){
        	result = result.replace("{sort}", "特价");
        }else if(sort.equalsIgnoreCase("priced")){
        	result = result.replace("{sort}", "打折");
        }else if(sort.equalsIgnoreCase("hot")){
        	result = result.replace("{sort}", "哪里好，哪个好");
        }else if(sort.equalsIgnoreCase("hotd")){
        	result = result.replace("{sort}", "有哪些");
        }else if(sort.equalsIgnoreCase("newtuan")){
        	result = result.replace("{sort}", "限时");
        }else if(sort.equalsIgnoreCase("newtuand")){
        	result = result.replace("{sort}", "不限时");
        }else if(sort.equalsIgnoreCase("diff")){
        	result = result.replace("{sort}", "优惠，打折");
        }else if(sort.equalsIgnoreCase("diffd")){
        	result = result.replace("{sort}", "折扣，优惠");
        }
        else{
        	result = result.replace("{sort}", "推荐");
        }
        result = result.replace("{page}", "第" + page + "页");
        if(city.equalsIgnoreCase("all")){
        	result = result.replace("{city}", "全国");
        }else{
        	Map<String,Object> param = new HashMap<String, Object>();
        	param.put("pinYin", city);
        	List<Place> list = placeService.queryPlaceListByParam(param);
        	if(list!=null && list.size()>0){
        		result = result.replace("{city}", list.get(0).getName());
        	}else
        		result = result.replace("{city}", "");
        }
        return result;
     }
	
	/**
	 * 选择类型符合的产品
	 * @param returnMap
	 * @param proudctType
	 * @param subProductType
	 * @param rec
	 */
	@SuppressWarnings("unchecked")
	private void selectRec(Map<String, Object> returnMap, String proudctType,
			String subProductType, RecommendInfo rec) {
		List<Place> recommendPrdPlace = (List<Place>) returnMap
				.get("recommendPrdPlace");
		if (city.equals("all") && type.equals("all")) {
			for (Place place : recommendPrdPlace) {
				if ("true".equalsIgnoreCase(place.getTo())) {
					if(place.getPlaceType()!=null){
						if (place.getPlaceType().equalsIgnoreCase("DOMESTIC")
								|| place.getPlaceType().equalsIgnoreCase("ABROAD")) {
							citymap.put(place.getParentPinYin(),place.getParentPlaceName());
						} else {
							citymap.put(place.getPinYin(), place.getName());
						}
					}
				} 
			}
			groupPrdList.add(returnMap);
		} else if (city.equals("all")
				&& ((type.equalsIgnoreCase(proudctType)
						|| (type.equalsIgnoreCase("china") && (subProductType.equalsIgnoreCase("GROUP_LONG") || subProductType.equalsIgnoreCase("FREENESS_LONG")))
						|| (type.equalsIgnoreCase("surround") && (subProductType.equalsIgnoreCase("FREENESS") || subProductType.equalsIgnoreCase("SELFHELP_BUS") || subProductType.equalsIgnoreCase("GROUP")))
						|| (type.equalsIgnoreCase("abroad") && (subProductType.equalsIgnoreCase("GROUP_FOREIGN") || subProductType.equalsIgnoreCase("FREENESS_FOREIGN")))))) {
			for (Place place : recommendPrdPlace) {
				if ("true".equalsIgnoreCase(place.getTo())) {
					if(place.getPlaceType()!=null){
						if (place.getPlaceType().equalsIgnoreCase("DOMESTIC") 
								|| place.getPlaceType().equalsIgnoreCase("ABROAD")) {
							citymap.put(place.getParentPinYin(),place.getParentPlaceName());
						} else {
							citymap.put(place.getPinYin(), place.getName());
						}
					}
				}
			}
			groupPrdList.add(returnMap);
		} else if(!city.equals("all")){
			if (type.equals("all")
					|| type.equalsIgnoreCase(proudctType)
					|| (type.equalsIgnoreCase("china") && (subProductType.equalsIgnoreCase("GROUP_LONG") || subProductType.equalsIgnoreCase("FREENESS_LONG")))
					|| (type.equalsIgnoreCase("surround") && (subProductType.equalsIgnoreCase("FREENESS") || subProductType.equalsIgnoreCase("SELFHELP_BUS") || subProductType.equalsIgnoreCase("GROUP")))
					|| (type.equalsIgnoreCase("abroad") && (subProductType.equalsIgnoreCase("GROUP_FOREIGN") || subProductType.equalsIgnoreCase("FREENESS_FOREIGN")))) {
				for (Place place : recommendPrdPlace) {
					if ("true".equalsIgnoreCase(place.getTo())) {
						if(place.getPlaceType()!=null){
							if (place.getPlaceType().equalsIgnoreCase(
									"DOMESTIC")
									|| place.getPlaceType().equalsIgnoreCase(
											"ABROAD")) {
								if (city.equalsIgnoreCase(place
										.getParentPinYin())) {
									groupPrdList.add(returnMap);
									citymap.put(place.getParentPinYin(),
											place.getParentPlaceName());
								}
							} else {
								if (city.equalsIgnoreCase(place.getPinYin())) {
									groupPrdList.add(returnMap);
									citymap.put(place.getPinYin(),
											place.getName());
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 查询团购首页数据
	 */
	private void setTuanGouData() {
		// 产品列表频道List
		List<RecommendInfo> topList = initRecommendInfo("_productsnew");
		// 加载团购产品的产品价格上下线等数据
		Map<Long, Map<String, Object>> returnProductMap = getProductList(topList,"productsnew");
		if (topList != null && topList.size() > 0) {
			this.setIndexMapOther(topList, returnProductMap,"_productsnew");
		}
		this.setCityMap(topList, returnProductMap);
		this.setNavigation(topList, returnProductMap);
		
		// 推荐产品列表List
        List<RecommendInfo> recommendProdList = initRecommendInfo("_recommend_products");
        Map<Long, Map<String, Object>> recommendProdMap = getProductList(recommendProdList,"recommend_products");
		if (recommendProdList != null && recommendProdList.size() > 0) {
            this.setIndexMapData(recommendProdList, recommendProdMap,"_recommend_products",RECOMMEND_PRODUCT);
        }
		//推荐广告位置List
		recommendPicList = initRecommendInfo("_recommend_picture");
		//通栏广告
		//recommendBanner = initRecommendInfo("_banner");
		//焦点图推荐
		recommendFocus = initRecommendInfo("_focusnew");
	}
	
	/**
	 * 获取团购子项
	 * @param recommendInfoName
	 * @return
	 */
	private List<RecommendInfo> initRecommendInfo(String recommendInfoName){
	    return groupMap.get(channelPage + recommendInfoName);
	}
	
	
	
	/**
	 * 查询所有目的地城市
	 * @param topList
	 * @param returnProductMap
	 */
	@SuppressWarnings("unchecked")
	private void setCityMap(List<RecommendInfo> topList,
			Map<Long, Map<String, Object>> returnProductMap){
		List<Place> recommendPrdPlace;
		if(topList == null)
			return ;
		for (RecommendInfo rec : topList) {
			Map<String, Object> returnMap = returnProductMap.get(Long.valueOf(rec.getRecommObjectId()));
			if(returnMap!= null){
				recommendPrdPlace = new ArrayList<Place>();
				if(returnMap.get("recommendPrdPlace")!=null){
					recommendPrdPlace = (List<Place>)returnMap.get("recommendPrdPlace");
				}
				for (Place place : recommendPrdPlace) {
					if("true".equalsIgnoreCase(place.getTo())){
						if(place.getPlaceType()!=null){
							if (place.getPlaceType().equalsIgnoreCase(
									"DOMESTIC") || place.getPlaceType().equalsIgnoreCase("ABROAD")) {
								citymap.put(place.getParentPinYin(),place.getParentPlaceName());
								allcitymap.put(place.getParentPinYin(),place.getParentPlaceName());
							} else {
								allcitymap.put(place.getPinYin(),place.getName());
								citymap.put(place.getPinYin(), place.getName());
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取列表页选择的条件
	 */
	private void setSelectParamMap() {
		if ((!city.equals("all")) && this.cities.size() == 1) {
			selParamMap.put("city", cities.get(0).get("cityName"));
			selParamMap.put("cityUrl", "all/" + type + "-all-" + tgType + "-1");
		}
		if (!type.equalsIgnoreCase("all")) {
			if (type.equalsIgnoreCase("ticket")) {
				selParamMap.put("type", "景点门票");
			} else if (type.equalsIgnoreCase("surround")) {
				selParamMap.put("type", "周边游");
			} else if (type.equalsIgnoreCase("china")) {
				selParamMap.put("type", "国内游");
			} else if (type.equalsIgnoreCase("abroad")) {
				selParamMap.put("type", "出境游");
			} else if (type.equalsIgnoreCase("hotel")) {
				selParamMap.put("type", "酒店");
			}
			selParamMap.put("typeUrl", city + "/all-all-" + tgType + "-1");
		}
	}

	/**
	 * 设置导航栏数据
	 * @param returnMap
	 */
	@SuppressWarnings("unchecked")
	private void setNavigation (List<RecommendInfo> topList,
			Map<Long, Map<String, Object>> returnProductMap){
		int allTypeCount=0,allCityCount=0,ticketCount=0,hotelCount=0,freenessCount=0,grouplongCount=0,groupForeign=0;
		String proudctType,subProductType;
		ProdProduct pp;
		int cityKeyCount;
		Map<String, Object> cityTempMap;
		for (String key : citymap.keySet()) {
			cityKeyCount = 0;
			cityTempMap = new HashMap<String, Object>();
			for (RecommendInfo rec : topList) {
				Map<String, Object> returnMap = returnProductMap.get(Long
						.valueOf(rec.getRecommObjectId()));
				if (returnMap != null) {
					if (tgType.equals("all")
							|| (rec.getBakWord1()!=null && rec.getBakWord1().equals("1") && tgType.equals("xsms"))
							|| (rec.getBakWord2()!=null && rec.getBakWord2().equals("1") && tgType.equals("hyr"))
							|| (rec.getBakWord3()!=null && rec.getBakWord3().equals("1") && tgType.equals("zyk"))
							|| (rec.getBakWord4()!=null && rec.getBakWord4().equals("1") && tgType.equals("wxzq"))) {
						pp = (ProdProduct) returnMap.get("prodProduct");
						proudctType = pp.getProductType();
						subProductType = pp.getSubProductType();
						List<Place> recommendPrdPlace = (List<Place>) returnMap
								.get("recommendPrdPlace");
						if (type.equals("all")
								|| type.equalsIgnoreCase(proudctType)
								|| (type.equalsIgnoreCase("china") && (subProductType.equalsIgnoreCase("GROUP_LONG") || subProductType.equalsIgnoreCase("FREENESS_LONG")))
								|| (type.equalsIgnoreCase("surround") && (subProductType.equalsIgnoreCase("FREENESS") || subProductType.equalsIgnoreCase("SELFHELP_BUS") || subProductType.equalsIgnoreCase("GROUP")))
								|| (type.equalsIgnoreCase("abroad") && (subProductType.equalsIgnoreCase("GROUP_FOREIGN") || subProductType.equalsIgnoreCase("FREENESS_FOREIGN")))) {
							for (Place place : recommendPrdPlace) {
								if ("true".equalsIgnoreCase(place.getTo())) {
									if(place.getPlaceType()!=null){
										if (place.getPlaceType()
												.equalsIgnoreCase("DOMESTIC")
												|| place.getPlaceType()
														.equalsIgnoreCase(
																"ABROAD")) {
											if (key.equals(place
													.getParentPinYin())) {
												allCityCount++;
												cityKeyCount++;
											}
										} else {
											if (key.equals(place.getPinYin())) {
												allCityCount++;
												cityKeyCount++;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			cityTempMap.put("cityPinYin", key);
			cityTempMap.put("cityName", citymap.get(key));
			cityTempMap.put("cityCount", cityKeyCount);
			cities.add(cityTempMap);
		}
		
		boolean flag;
		if(topList != null){
			for (RecommendInfo rec : topList) {
				Map<String, Object> returnMap = returnProductMap.get(Long
						.valueOf(rec.getRecommObjectId()));
				if (returnMap != null) {
					if (tgType.equals("all")
							|| (rec.getBakWord1() != null
							&& rec.getBakWord1().equals("1") && tgType.equals("xsms"))
							|| (rec.getBakWord2() != null
							&& rec.getBakWord2().equals("1") && tgType.equals("hyr"))
							|| (rec.getBakWord3() != null
							&& rec.getBakWord3().equals("1") && tgType.equals("zyk"))
							|| (rec.getBakWord4() != null 
							&& rec.getBakWord4().equals("1") && tgType.equals("wxzq"))) {
						pp = (ProdProduct) returnMap.get("prodProduct");
						proudctType = pp.getProductType();
						subProductType = pp.getSubProductType();
						flag = true;
						if (!city.equals("all")) {
							List<Place> recommendPrdPlace = (List<Place>) returnMap.get("recommendPrdPlace");
							flag = false;
							for (Place place : recommendPrdPlace) {
								if ("true".equalsIgnoreCase(place.getTo())) {
									if (place.getPlaceType() != null) {
										if (place.getPlaceType()
												.equalsIgnoreCase("DOMESTIC")
												|| place.getPlaceType()
														.equalsIgnoreCase(
																"ABROAD")) {
											if (city.equals(place
													.getParentPinYin())) {
												flag = true;
											}
										} else {
											if (city.equals(place.getPinYin())) {
												flag = true;
											}
										}
									}
								}
							}
						}
						if (flag) {
							if ("TICKET".equalsIgnoreCase(proudctType)) {// 门票
								allTypeCount++;
								ticketCount++;
							}
							if ("HOTEL".equalsIgnoreCase(proudctType)) {// 酒店
								allTypeCount++;
								hotelCount++;
							}
							if (("FREENESS".equalsIgnoreCase(subProductType) 
									|| "SELFHELP_BUS".equalsIgnoreCase(subProductType))
									|| "GROUP".equalsIgnoreCase(subProductType)) {// 周边游
								allTypeCount++;
								freenessCount++;
							}
							if ("GROUP_LONG".equalsIgnoreCase(subProductType)
									|| "FREENESS_LONG".equalsIgnoreCase(subProductType)) {// 国内游
								allTypeCount++;
								grouplongCount++;
							}
							if (("GROUP_FOREIGN".equalsIgnoreCase(subProductType) 
									|| "FREENESS_FOREIGN".equalsIgnoreCase(subProductType))) {// 出境
								allTypeCount++;
								groupForeign++;
							}
						}
					}
				}
			}
		}
		dataCountMap.put("allTypeCount", allTypeCount);
		dataCountMap.put("allCityCount", allCityCount);
		dataCountMap.put("ticketCount", ticketCount);
		dataCountMap.put("hotelCount", hotelCount);
		dataCountMap.put("freenessCount", freenessCount);
		dataCountMap.put("grouplongCount", grouplongCount);
		dataCountMap.put("groupForeign", groupForeign);
	}
	

	/**
	 * 团购首页各类型的团购产品
	 * 
	 * @param topList
	 * @param returnProductMap
	 */
	private void setIndexMapData(List<RecommendInfo> topList,
			Map<Long, Map<String, Object>> returnProductMap,String returnName,int maxCount) {
		// 秒杀、无线专区、周游客、微旅游、其他 类型产品list集合
		List<Map<String, Object>> msPrdList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> wxzqPrdList = new ArrayList<Map<String, Object>>();
//		List<Map<String, Object>> zykPrdList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> wlyPrdList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> otherPrdList = new ArrayList<Map<String, Object>>();
		if(topList != null){
		for (RecommendInfo rec : topList) {
			// 查询产品信息 和推荐产品所关联的标的(类型：直辖市、特别行政区、城市、出境目的地)
			Map<String, Object> returnMap = returnProductMap.get(Long.valueOf(rec.getRecommObjectId()));
			if (returnMap != null) {
				returnMap.put("viewRecommendInfo", rec);
				returnMap.put("isRecommendPrd", true);
				returnMap.put("seq", rec.getSeq());
				recommendPrdMap.put(Long.valueOf(rec.getRecommObjectId()),rec.getRecommObjectId());
				if (rec.getBakWord1() != null && rec.getBakWord1().equals("1") && msPrdList.size() < maxCount) {
						msPrdList.add(returnMap);
				} else if (rec.getBakWord2() != null && rec.getBakWord2().equals("1")&&wlyPrdList.size() < maxCount) {
						wlyPrdList.add(returnMap);
//				} else if (rec.getBakWord3() != null && rec.getBakWord3().equals("1")&&zykPrdList.size() < maxCount) {
//						zykPrdList.add(returnMap);
				} else if (rec.getBakWord4() != null && rec.getBakWord4().equals("1")&&wxzqPrdList.size() < maxCount) {
						wxzqPrdList.add(returnMap);
				} else {
				    if((maxCount*2)%3 == 0){//是三的倍数
				        if (otherPrdList.size() < (maxCount*2))
						otherPrdList.add(returnMap);
				    }else{
				        if (otherPrdList.size() < (maxCount*2)-(maxCount*2)%3)
	                        otherPrdList.add(returnMap);
				    }
				}
			}
		}
		}
		indexMap.put("msPrdList"+returnName, msPrdList);
//		indexMap.put("zykPrdList"+returnName, zykPrdList);
		indexMap.put("wlyPrdList"+returnName, wlyPrdList);
		indexMap.put("wxzqPrdList"+returnName, wxzqPrdList);
	}
	
	private void setIndexMapOther(List<RecommendInfo> topList,
			Map<Long, Map<String, Object>> returnProductMap,String returnName) {
		List<Map<String, Object>> otherPrdList = new ArrayList<Map<String, Object>>();
		if(topList != null){
			for (RecommendInfo rec : topList) {
				// 查询产品信息 和推荐产品所关联的标的(类型：直辖市、特别行政区、城市、出境目的地)
				Map<String, Object> returnMap = returnProductMap.get(Long
						.valueOf(rec.getRecommObjectId()));
				if (returnMap != null) {
					returnMap.put("viewRecommendInfo", rec);
					returnMap.put("isRecommendPrd", true);
					returnMap.put("seq", rec.getSeq());
					recommendPrdMap.put(Long.valueOf(rec.getRecommObjectId()),
							rec.getRecommObjectId());
					if (otherPrdList.size() < PRODUCTS_MAXCOUNT) {
						otherPrdList.add(returnMap);
					}
				}
			}
		}
		indexMap.put("otherPrdList"+returnName, otherPrdList);
	}

	/**
	 * 对匹配的结果排序
	 */
	private void dataSoft() {
		String _sort = this.sort;
		if (sort.toLowerCase().equals("all")) {
			Collections.sort(groupPrdList, new seqCompare());
		} else if (sort.toLowerCase().equals("hot") || sort.equalsIgnoreCase("hotd")) {
			Collections.sort(groupPrdList, new sellHotCompare());
			this.sort = sort.equals("hot")?"hotd":"hot";
			this.getRequest().setAttribute("sort",sort);
		} else if (sort.toLowerCase().equals("price") || sort.equalsIgnoreCase("priced")) {
			Collections.sort(groupPrdList, new sellPriceCompare());
			this.sort = sort.equals("price")?"priced":"price";
			this.getRequest().setAttribute("sort",sort);
		} else if (sort.toLowerCase().equals("newtuan") || sort.equalsIgnoreCase("newtuand")){
			Collections.sort(groupPrdList, new sellNewTuanCompare());
			this.sort = sort.equals("newtuan")?"newtuand":"newtuan";
			this.getRequest().setAttribute("sort",sort);
		} else if (sort.toLowerCase().equals("diff") || sort.equalsIgnoreCase("diffd")){
			Collections.sort(groupPrdList, new sellDiffCompare());
			this.sort = sort.equals("diff")?"diffd":"diff";
			this.getRequest().setAttribute("sort",sort);
		}
		pageConfig = new PageConfig<Map<String, Object>>(groupPrdList.size(),
				pageSize, page);
		List<Map<String, Object>> groupPrdListParse = new ArrayList<Map<String, Object>>();
		if (groupPrdList.size() > 0) {

			for (int i = pageConfig.getStartResult(); i < pageConfig
					.getCurrentRowNum(); i++) {
				groupPrdListParse.add(groupPrdList.get(i));
			}
		}
		pageConfig.setItems(groupPrdListParse);
		initTicketPageUrl(_sort);
	}

	private void setSessionPlace() {
		HttpSession session = getRequest().getSession(true);
		if (getFromPlaceId() != null) {
			session.setAttribute("fromPCode", getFromPlaceCode());
			session.setAttribute("fromPid", getFromPlaceId());
			session.setAttribute("fromPName", getFromPlaceName());
		} else if ((Long) session.getAttribute("fromPid") != null) {
			super.fromPlaceCode = (String) session.getAttribute("fromPCode");
			super.fromPlaceId = (Long) session.getAttribute("fromPid");
			super.fromPlaceName = (String) session.getAttribute("fromPName");
		}
	}

	private class seqCompare implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			long value1 = (Long) o1.get("seq");
			long value2 = (Long) o2.get("seq");
			return (int) (value2 - value1);
		}
	}

	private class sellPriceCompare implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			long value1 = ((ProdProduct) o1.get("prodProduct")).getSellPrice();
			long value2 = ((ProdProduct) o2.get("prodProduct")).getSellPrice();
			return sort.equals("price")?(int) (value1 - value2):(int) (value2 - value1);
		}
	}
	
	private class sellNewTuanCompare implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			long value1 = (((ProdProduct) o1.get("prodProduct"))
					.getOnlineTime().getTime()) / 100000;// long数据溢出int,统一转换
			long value2 = (((ProdProduct) o2.get("prodProduct"))
					.getOnlineTime().getTime()) / 100000;
			return sort.equals("newtuan") ? (int) (value2 - value1) : (int) (value1 - value2);
		}
	}
	
	private class sellDiffCompare  implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			long value1 = (((ProdProduct) o1.get("prodProduct"))
					.getOfflineTime().getTime()) / 100000;// long数据溢出int,统一转换
			long value2 = (((ProdProduct) o2.get("prodProduct"))
					.getOfflineTime().getTime()) / 100000;
			return sort.equals("diff")?(int) (value1 - value2):(int) (value2 - value1);
		}
	}
	

	private class sellHotCompare implements Comparator<Map<String, Object>> {
		public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			long value1 = (Long) o1.get("orderCount");
			long value2 = (Long) o2.get("orderCount");
			return sort.equals("hot")?(int) (value2 - value1):(int) (value1 - value2);
		}
	}

	/**
	 * 格式化URL链接,传回页面
	 */
	private void initTicketPageUrl(String _sort) {
		pageConfig.setUrl("http://www.lvmama.com/tuangou/" + this.getCity() + "/" + this.getType()
				+ "-" + _sort + "-" + this.getTgType() + "-");
	}

	/**
	 * 查询产品的价格等实时数据
	 * 
	 * @param topList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<Long, Map<String, Object>> getProductList(
			List<RecommendInfo> topList,String str) {
		List<Long> productIdList = new ArrayList<Long>();
		Map<String, Object> recommendPic = new  HashMap<String, Object>();
		if (topList != null && topList.size() > 0) {
			for (RecommendInfo rec : topList) {
				if (rec != null && rec.getRecommObjectId() != null) {
				    List<ComPicture>  comPic = getComPictureService().getPictureByPageId(Long.valueOf(rec.getRecommObjectId()));
				    if(null != comPic && comPic.size()>0){
				        recommendPic.put("comPicture"+rec.getRecommObjectId(), "http://pic.lvmama.com/pics/"+comPic.get(0).getPictureUrl());
				    }else{
				        recommendPic.put("comPicture"+rec.getRecommObjectId(),rec.getImgUrl());
				    }
					productIdList.add(Long.valueOf(rec.getRecommObjectId()));
				}
			}
		}
		List<ProdProduct> prodProductList = this
				.queryOnlineProductInProductIds(productIdList, channelPage,
						commonBlockId, containerCode, fromPlaceId,str);

		Map<Long, Map<String, Object>> returnMap = new HashMap<Long, Map<String, Object>>();
		Map<String, Object> parameters ;
		for (ProdProduct prod : prodProductList) {
		    long productId = prod.getProductId();
			Map<String, Object> inReturnMap = new HashMap<String, Object>();
			ProdProductTag prodProductTag;
			inReturnMap.put("prodProduct", prod);
			inReturnMap.put("orderCount", prod.getOrderCount());
			inReturnMap.put("pageId", prod.getPageId());
			inReturnMap.put("managerRecommend", prod.getManagerRecommend());
			inReturnMap.put("recommendPrdPlace", prod.getPlaceList());
			String picUrl = (String)recommendPic.get("comPicture"+productId);
			inReturnMap.put("comPictureUrl",picUrl);
			// 取标的内容 酒店：酒店所在城市、酒店名称，景区：景区所在城市，景区名称，线路：出发地，目的地
			for(Place ppp : prod.getPlaceList()){
				if ("TICKET".equals(prod.getProductType()) || "HOTEL".equals(prod.getProductType())) {// 门票
					if("true".equalsIgnoreCase(ppp.getTo())){
						inReturnMap.put("recommendPrdTo", ppp.getName());
						inReturnMap.put("recommendPrdFrom", ppp.getParentPlaceName());
					}
				}else{
					if("true".equalsIgnoreCase(ppp.getTo())){
						inReturnMap.put("recommendPrdTo", ppp.getName());
					}
					if("true".equalsIgnoreCase(ppp.getFrom())){
						inReturnMap.put("recommendPrdFrom", ppp.getName());
					}
				}
			}

			if (prod.getMarketPriceYuan() > 0) {
				inReturnMap.put("discount",new BigDecimal(prod.getSellPriceYuan() / prod.getMarketPriceYuan() * 10).setScale(1,BigDecimal.ROUND_FLOOR).doubleValue());
			}
			long offlineTime = 0;
			if (prod.getOfflineTime() != null) {
				offlineTime = prod.getOfflineTime().getTime();
			} else {
				offlineTime = -1;
			}
			inReturnMap.put("diff", offlineTime - System.currentTimeMillis());
			
			parameters = new HashMap<String, Object>();
			parameters.put("productId", prod.getProductId());
			parameters.put("isAudit","AUDIT_SUCCESS");
			//查询审核通过的所有点评通过的条数
			Long cmtCountByProdId = cmtCommentService.getCommentTotalCount(parameters);
			inReturnMap.put("cmtCount",cmtCountByProdId);
			
			 
    		 String avgScoreKey = "cmtLatitudeStatisticsList_avgScore_" + productId;
    		 Float avgScore = (Float) MemcachedUtil.getInstance().get(avgScoreKey);
    		 if(null == avgScore){
    		     parameters.remove("isAudit");
                 List<CmtLatitudeStatistics> cmtLatitudeStatisticsList = cmtLatitudeStatistisService.getLatitudeStatisticsList(parameters);
                 if(null != cmtLatitudeStatisticsList && cmtLatitudeStatisticsList.size()>0 ){
                     for (CmtLatitudeStatistics cls : cmtLatitudeStatisticsList) {
                         if("FFFFFFFFFFFFFFFFFFFFFFFFFFFF".equals(cls.getLatitudeId())){
                             avgScore = cls.getAvgScore()*20;//讲平均分提升为百分比模式 原5分满分
                             MemcachedUtil.getInstance().set(avgScoreKey,MemcachedUtil.TWO_HOUR, avgScore);
                         }
                    }
                 }
                 if(null == avgScore){
                     avgScore =100F;
                 }
    		 }
             inReturnMap.put("avgScore",avgScore);
            
			 returnMap.put(productId, inReturnMap);
		}
		long reducePrice;
		if(topList != null){
			for (RecommendInfo rec : topList) {
				// 查询产品信息 和推荐产品所关联的标的(类型：直辖市、特别行政区、城市、出境目的地)
				Map<String, Object> _temp = returnMap.get(Long.valueOf(rec.getRecommObjectId()));
				if (_temp != null) {
					if(rec.getBakWord1()!=null && rec.getBakWord1().equals("1")){
						ProdProduct pp = (ProdProduct) _temp.get("prodProduct");
						Object obj = SeckillMemcachedUtil.getSeckillMemcachedUtil().getSeckillRuleByProductId(pp.getProductId());
						if(obj != null){
						    ProdSeckillRule psr =((ProdSeckillRule) obj);
    						reducePrice = psr.getReducePrice();
    						pp.setSellPrice(pp.getSellPrice()-reducePrice);
    						_temp.put("prodProduct", pp);
						
//    						后台维护了秒杀规则，才能有虚拟销量 目前是这样的。团购产品需要到秒杀后台做二期才能做
//    						String keyVs = "query_ProductByTuangou_Virtual_Sales_and_OrderCount_" + productId;
//                          List<ProdSeckillRule> prodSeckillRule = (List<ProdSeckillRule>) MemcachedUtil.getInstance().get(keyVs);
                            long vsCount ;
//                            if (prodSeckillRule == null) {
//                                Map paramMap = new HashMap<String,Object>();
//                                //加上虚拟销量 
//                                paramMap.put("productId", productId); 
//                                prodSeckillRule = prodSeckillRuleService.selectByParam(paramMap);
//                            }
//                            if(null != prodSeckillRule && prodSeckillRule.size()>0){
//                              vsCount= prodSeckillRule.get(0).getUserBuyLimit();//虚拟销量字段
                                vsCount = pp.getOrderCount()+psr.getUserBuyLimit();  
//                              MemcachedUtil.getInstance().set(keyVs,MemcachedUtil.TWO_HOUR, vsCount);
                                _temp.put("orderCount",vsCount);
                                //秒杀规则的倒计时
                                Long seckillMilliSeconds= psr.getEndTime().getTime()-new Date().getTime();
                                if(seckillMilliSeconds>0){
                                   _temp.put("diff",seckillMilliSeconds);
                                }
//                            }
                          returnMap.put(pp.getProductId(), _temp);
						}
					}
				}
			}
		}
		
		return returnMap;
	}

	@SuppressWarnings({ "unchecked"})
	public List<ProdProduct> queryOnlineProductInProductIds(
			List<Long> productIdList, String channelPage, Long commonBlockId,
			String containerCode, Long fromPlaceId,String _str) {
		String key = "queryNewOnlineProductInProductIds_" + channelPage + "_"
				+ commonBlockId + "_" + containerCode + "_" + _str + "_" + fromPlaceId;
		List<ProdProduct> prodProductList = (List<ProdProduct>) MemcachedUtil.getInstance().get(key);
		if (prodProductList == null) {
			prodProductList = new ArrayList<ProdProduct>();
			if (productIdList.size() > 0) {
				prodProductList = groupDreamService
						.queryOnlineProductInProductIds(productIdList);
				String key_2 = "";
				List<Place> placelist; 
				for (ProdProduct prod : prodProductList) {
					key_2 = "TuanNewComPlaceByProductId_" + prod.getProductId();
					placelist = (List<Place>) MemcachedUtil.getInstance().get(key_2);
					if(placelist == null){
						placelist = prodProductPlaceService.getNewComPlaceByProductId(prod.getProductId());
						MemcachedUtil.getInstance().set(key_2, 3600 * 24, placelist);
					}
					prod.setPlaceList(placelist);
				}
				if(prodProductList != null){
					LOG.info("***" + key + "***");
					MemcachedUtil.getInstance().set(key,MemcachedUtil.TWO_HOUR, prodProductList);
				}
			}
		}
		return prodProductList;
	}

	public List<Map<String, Object>> getGroupPrdList() {
		return groupPrdList;
	}

	public void setGroupPrdList(List<Map<String, Object>> groupPrdList) {
		this.groupPrdList = groupPrdList;
	}

	public ComPictureService getComPictureService() {
		return comPictureService;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	public ProdProductPlaceService getProdProductPlaceService() {
		return prodProductPlaceService;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public GroupDreamService getGroupDreamService() {
		return groupDreamService;
	}

	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}

	public Map<String, List<RecommendInfo>> getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map<String, List<RecommendInfo>> groupMap) {
		this.groupMap = groupMap;
	}

	public Map<Long, Object> getRecommendPrdMap() {
		return recommendPrdMap;
	}

	public void setRecommendPrdMap(Map<Long, Object> recommendPrdMap) {
		this.recommendPrdMap = recommendPrdMap;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageConfig<Map<String, Object>> getPageConfig() {
		return pageConfig;
	}

	public void setPageConfig(PageConfig<Map<String, Object>> pageConfig) {
		this.pageConfig = pageConfig;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public List<RecommendInfo> getRecommendBanner() {
		return recommendBanner;
	}

	public void setRecommendBanner(List<RecommendInfo> recommendBanner) {
		this.recommendBanner = recommendBanner;
	}

	public List<Map<String, Object>> getCities() {
		return cities;
	}

	public void setCities(List<Map<String, Object>> cities) {
		this.cities = cities;
	}

	public Long getCommonBlockId() {
		return commonBlockId;
	}

	public void setCommonBlockId(Long commonBlockId) {
		this.commonBlockId = commonBlockId;
	}

	public String getChannelPage() {
		return channelPage;
	}

	public void setChannelPage(String channelPage) {
		this.channelPage = channelPage;
	}

	public String getContainerCode() {
		return containerCode;
	}

	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}

	public Map<String, List<Map<String, Object>>> getIndexMap() {
		return indexMap;
	}

	public void setIndexMap(Map<String, List<Map<String, Object>>> indexMap) {
		this.indexMap = indexMap;
	}

	public Map<String, Object> getDataCountMap() {
		return dataCountMap;
	}

	public void setDataCountMap(Map<String, Object> dataCountMap) {
		this.dataCountMap = dataCountMap;
	}



    public CmtCommentService getCmtCommentService() {
        return cmtCommentService;
    }

    public void setCmtCommentService(CmtCommentService cmtCommentService) {
        this.cmtCommentService = cmtCommentService;
    }

    public CmtLatitudeStatistisService getCmtLatitudeStatistisService() {
        return cmtLatitudeStatistisService;
    }

    public void setCmtLatitudeStatistisService(
            CmtLatitudeStatistisService cmtLatitudeStatistisService) {
        this.cmtLatitudeStatistisService = cmtLatitudeStatistisService;
    }

    public ProdTagService getProdTagService() {
        return prodTagService;
    }

    public void setProdTagService(ProdTagService prodTagService) {
        this.prodTagService = prodTagService;
    }
    
	public String getTgType() {
		return tgType;
	}

	public void setTgType(String tgType) {
		this.tgType = tgType;
	}

	public Map<String, Object> getSelParamMap() {
		return selParamMap;
	}

	public void setSelParamMap(Map<String, Object> selParamMap) {
		this.selParamMap = selParamMap;
	}

	public PlaceService getPlaceService() {
		return placeService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public String[] getCityArr() {
		return cityArr;
	}
	
    public Map<String, Object> getAllcitymap() {
		return allcitymap;
	}

	public void setAllcitymap(Map<String, Object> allcitymap) {
		this.allcitymap = allcitymap;
	}

	public List<RecommendInfo> getRecommendPicList() {
		return recommendPicList;
	}

	public void setRecommendPicList(List<RecommendInfo> recommendPicList) {
		this.recommendPicList = recommendPicList;
	}

	public Map<Long, Map<String, Object>> getRecommendProdMap() {
        return recommendProdMap;
    }

    public void setRecommendProdMap(Map<Long, Map<String, Object>> recommendProdMap) {
        this.recommendProdMap = recommendProdMap;
    }

	public List<RecommendInfo> getRecommendFocus() {
		return recommendFocus;
	}

	public void setRecommendFocus(List<RecommendInfo> recommendFocus) {
		this.recommendFocus = recommendFocus;
	}

    public List<Map<String, Object>> getRecommMap() {
        return recommMap;
    }

    public void setRecommMap(List<Map<String, Object>> recommMap) {
        this.recommMap = recommMap;
    }
    
    public ProdSeckillRuleService getProdSeckillRuleService() {
        return prodSeckillRuleService;
    }
    
    public void setProdSeckillRuleService(
            ProdSeckillRuleService prodSeckillRuleService) {
        this.prodSeckillRuleService = prodSeckillRuleService;
    }
    
}
