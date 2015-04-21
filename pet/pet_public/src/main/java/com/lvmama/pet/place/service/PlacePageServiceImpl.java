package com.lvmama.pet.place.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.place.HotelTrafficInfo;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceCoordinateBaidu;
import com.lvmama.comm.pet.po.place.PlaceCoordinateGoogle;
import com.lvmama.comm.pet.po.place.PlaceHotel;
import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.po.place.PlaceHotelOtherRecommend;
import com.lvmama.comm.pet.po.place.PlaceHotelRoom;
import com.lvmama.comm.pet.po.place.PlaceLandMark;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductPropertySearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.place.PlaceActivityService;
import com.lvmama.comm.pet.service.place.PlaceCoordinateBaiduService;
import com.lvmama.comm.pet.service.place.PlaceCoordinateGoogleService;
import com.lvmama.comm.pet.service.place.PlaceHotelNoticeService;
import com.lvmama.comm.pet.service.place.PlaceHotelRecommendService;
import com.lvmama.comm.pet.service.place.PlaceHotelRoomService;
import com.lvmama.comm.pet.service.place.PlacePageService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.place.QuestionAnswerService;
import com.lvmama.comm.pet.service.search.ComSearchTranscodeService;
import com.lvmama.comm.pet.service.search.PlaceSearchInfoService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.pet.service.seo.SeoIndexPageService;
import com.lvmama.comm.pet.vo.PlaceVo;
import com.lvmama.comm.pet.vo.ProductList;
import com.lvmama.comm.pet.vo.ScenicProductAndBranchListVO;
import com.lvmama.comm.pet.vo.ViewPlaceCoordinate;
import com.lvmama.comm.pet.vo.place.DimensionVo;
import com.lvmama.comm.pet.vo.place.ScenicVo;
import com.lvmama.comm.search.vo.ComSearchTranscode;
import com.lvmama.comm.utils.GeoLocation;
import com.lvmama.comm.utils.ProductPriceComparator;
import com.lvmama.comm.utils.SeoUtils;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.enums.PlacePhotoTypeEnum;
import com.lvmama.comm.vo.enums.ProductSearchInfoTypeEnum;
import com.lvmama.comm.vo.enums.SeoIndexPageCodeEnum;
import com.lvmama.pet.mark.dao.MarkCouponDAO;
import com.lvmama.pet.place.dao.HotelTrafficInfoDAO;
import com.lvmama.pet.place.dao.PlaceCoordinateBaiduDao;
import com.lvmama.pet.place.dao.PlaceDAO;
import com.lvmama.pet.place.dao.PlaceHotelDAO;
import com.lvmama.pet.place.dao.PlaceLandMarkDAO;
import com.lvmama.pet.place.dao.PlacePhotoDAO;
import com.lvmama.pet.search.dao.PlaceSearchInfoDAO;
import com.lvmama.pet.search.dao.ProductPropertySearchInfoDAO;

public class PlacePageServiceImpl implements PlacePageService {
	@Autowired
	private PlaceDAO placeDAO;
	@Autowired
	private PlacePhotoDAO placePhotoDAO;
	@Autowired
	private PlaceHotelDAO placeHotelDAO;
	@Autowired
	private PlaceLandMarkDAO placeLandMarkDAO;
	@Autowired
	private HotelTrafficInfoDAO trafficInfoDAO;
	@Autowired
	private PlaceSearchInfoDAO placeSearchInfoDAO;
	@Autowired
	private MarkCouponDAO markCouponDAO;
	@Autowired
	private ProductPropertySearchInfoDAO productPropertySearchInfoDAO;
	@Autowired
	private PlaceCoordinateBaiduDao placeCoordinateBaiduDao;
	@Autowired
	private PlaceCoordinateGoogleService placeCoordinateGoogleService;
	@Autowired
	private PlaceCoordinateBaiduService placeCoordinateBaiduService;
	@Autowired
	private PlaceSearchInfoService placeSearchInfoService;
	@Autowired
	private ProductSearchInfoService productSearchInfoService;
	@Autowired
	private PlaceActivityService placeActivityService;
	@Autowired
	private RecommendInfoService recommendInfoService;
	@Autowired
	private CmtTitleStatistisService cmtTitleStatistisService;
	@Autowired
	private PlaceService placeService; 
	@Autowired
	private PlaceHotelNoticeService placeHotelNoticeService;
	@Autowired
	private QuestionAnswerService askService;
	@Autowired
	private CmtLatitudeStatistisService cmtLatitudeStatistisService;
	private SeoIndexPageService seoIndexPageService;
	private ComSearchTranscodeService comSearchTranscodeService;
	@Autowired
	private PlaceHotelRoomService placeHotelRoomService;
	@Autowired
	private PlaceHotelRecommendService placeHotelRecommendService;
	
	/**
	 * 获取特推产品：先取后台推荐的产品，若无推荐则调取各分类产品的第一款
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductSearchInfo> specialRecommendation(Long currentPlaceId,Long firtMatchFromDestId,String pageChannel,int currentPage,int prdSize) {
		// 特推产品
		List<ProductSearchInfo> specialRecommendationList=new ArrayList<ProductSearchInfo>();
		// 根据place以及station获取level1的block
		Map recommendMap = null;
		if (currentPlaceId != null) {
			recommendMap = recommendInfoService.getRecommendInfoByDataCodeAndPageChannel(currentPlaceId,pageChannel);
		}
		specialRecommendationList = new ArrayList<ProductSearchInfo>();
		List<RecommendInfo> RecommendList = null;
		if (recommendMap != null) {
			RecommendList = (List<RecommendInfo>) recommendMap.get(pageChannel + "_" + currentPlaceId);
		}
		// 没有推荐则当前目的地下取seq值大的每一款取一条 ，如果没有一个产品则取全站seq最大的

		if (RecommendList == null || RecommendList.size() == 0) {
			// 取本目的地下包含的产品
			List<ProductSearchInfo> tempProductSearchInfoList=null;
			Map<String, Object> specialPlaceTicketMap = this.getPlaceAndPrd(currentPlaceId, "2", "1", (int) 1, (int) 10, prdSize, (int) 1);
			List<Map<String, Object>> specialPlaceTicketList = (List<Map<String, Object>>) specialPlaceTicketMap.get("resultList");
			if (specialPlaceTicketList != null && specialPlaceTicketList.size() > 0) {
					Map<String, Object> specialPlaceTicket = specialPlaceTicketList.get(0);
					Place specialPlace = (Place) specialPlaceTicket.get("place");
					List<ProductSearchInfo> specialPlaceProduct = (List<ProductSearchInfo>) specialPlaceTicket.get("prdList");
					// 取一个景区和一条产品信息
					if (specialPlaceProduct != null && specialPlaceProduct.size() > 0) {
						ProductSearchInfo vsi = (ProductSearchInfo) specialPlaceProduct.get(0);
						if (vsi != null && vsi.getProdBranchSearchInfoList() != null) {
						for (ProdBranchSearchInfo vpp : vsi.getProdBranchSearchInfoList()) {
							if ("true".equals(vpp.getDefaultBranch())) {
								vsi.setSellPrice(vpp.getSellPrice());
								vsi.setMarketPrice(vpp.getMarketPrice());
								break;
							}
						}
						vsi.setLargeImage(specialPlace == null ? null : this.getPlacePhotoImg(specialPlace));
						vsi.setRecommendReason(specialPlace == null ? null : specialPlace.getRemarkes());
						vsi.setProductName(specialPlace == null ? null : specialPlace.getName());
						specialRecommendationList.add(vsi);
						}
					}
			}
			List<String> subProductTypes=new ArrayList<String>();
			subProductTypes.add(Constant.SUB_PRODUCT_TYPE.FREENESS.getCode());
			// 取当地自由行产品一条
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("placeId", currentPlaceId);
			param.put("subProductTypes", subProductTypes);
			param.put("selfPack", "false");
			param.put("startRows", 1);
			param.put("endRows", 1);
			tempProductSearchInfoList=productSearchInfoService.getProductByFromPlaceIdAndDestId(param);
			if(tempProductSearchInfoList!=null&&tempProductSearchInfoList.size()>0)
				specialRecommendationList.add(tempProductSearchInfoList.get(0));
			
			// 取一个酒店以及酒店产品作为特推显示
			String stage = "3";
			Map<String, Object> specPlaceHotelMap = this.getPlaceAndPrd(currentPlaceId, stage, "2", 1, 10, 1, (int) currentPage);
			List<Map<String, Object>> result = (List<Map<String, Object>>) specPlaceHotelMap.get("resultList");
			if (result != null && result.size() > 0) {
				List<ProductSearchInfo> prdList = (List<ProductSearchInfo>) result.get(0).get("prdList");
				Place p = (Place) result.get(0).get("place");
				if (prdList != null && prdList.size() > 0) {
					ProductSearchInfo vps = prdList.get(0);
					if (vps != null && vps.getProdBranchSearchInfoList() != null) {
						vps.setProductUrl("/dest/" + p.getPinYinUrl());
						for (ProdBranchSearchInfo vpp : vps.getProdBranchSearchInfoList()) {
							if ("true".equals(vpp.getDefaultBranch())) {
								vps.setSellPrice(vpp.getSellPrice());
								vps.setMarketPrice(vpp.getMarketPrice());
								break;
							}
						}
						vps.setLargeImage(p == null ? null : this.getPlacePhotoImg(p));
						vps.setRecommendReason(p == null ? null : p.getRemarkes());
						vps.setProductName(p == null ? null : p.getName());
						specialRecommendationList.add(vps);
					}
				} else {
					//logger.info("specialRecommendation:" + p.getName() + "无具体产品,特推不显示该酒店");
				}
			} else {
				//logger.info("specialRecommendation:该目的地下无酒店");
			}
			Place specialPlace = placeDAO.findByPlaceId(currentPlaceId);
			
			// 取跟团游产品一条-国内取：'GROUP','GROUP_LONG','SELFHELP_BUS';省级取：'GROUP','SELFHELP_BUS';大洲级：'GROUP_FOREIGN';

			param.clear();
			param.put("fromPlaceId", firtMatchFromDestId);
			param.put("placeId", currentPlaceId);
			param.put("productType", ProductSearchInfoTypeEnum.ROUTE.getCode());
			param.put("subProductTypes", getSubProductTypeForSurrounding(specialPlace));
			param.put("startRows", 1);
			param.put("endRows", 1);
			
			tempProductSearchInfoList=productSearchInfoService.getProductByFromPlaceIdAndDestId(param);
			if(tempProductSearchInfoList!=null&&tempProductSearchInfoList.size()>0)
				specialRecommendationList.add(tempProductSearchInfoList.get(0));
			
			// 查询路线取自由行（含交通）产品一条：长途自由行;出境取（出境跟团游、短途跟团游和自助巴士班，出境自由行）;省份取:GROUP_LONG,FREENESS_LONG;跟团游：短途跟团游，自助巴士班'GROUP','SELFHELP_BUS'
			param.put("subProductTypes", getSubProductTypeForDest2Dest(specialPlace));
			tempProductSearchInfoList=productSearchInfoService.getProductByFromPlaceIdAndDestId(param);
			if(tempProductSearchInfoList!=null&&tempProductSearchInfoList.size()>0)
				specialRecommendationList.add(tempProductSearchInfoList.get(0));

		} else {
			for (RecommendInfo Rec : RecommendList) {
				Long productId = new Long(Rec.getRecommObjectId());

				ProductSearchInfo vPrdSearchInfo = new ProductSearchInfo();
				vPrdSearchInfo.setProductName(Rec.getTitle());
				vPrdSearchInfo.setRecommendReason(Rec.getBakWord1());
				vPrdSearchInfo.setLargeImage(Rec.getImgUrl());
				vPrdSearchInfo.setToDest(Rec.getBakWord2());
				vPrdSearchInfo.setProductId(productId);
				vPrdSearchInfo.setMarketPrice(new BigDecimal(Rec.getMarketPrice()).multiply(new BigDecimal(100)).longValue());
				vPrdSearchInfo.setSellPrice(new BigDecimal(Rec.getMemberPrice()).multiply(new BigDecimal(100)).longValue());
				vPrdSearchInfo.setProductUrl(Rec.getUrl());
				specialRecommendationList.add(vPrdSearchInfo);
				// 只显示前六款推荐产品
				if (specialRecommendationList.size() == 6) {
					break;
				}
			}
		}
		return specialRecommendationList;
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public Map<String, Object> getDestInfoForRecommendProducts(final Long fromPlaceId, final Place currentPlace,final Long fromDestId) {
		Map<String, Object> recommendProducts=new HashMap<String, Object>();
		
	    //酒店、景点下获取产品数量
		int prdSize = 1;
		//周边跟团游推荐产品
		List<ProductSearchInfo> recSurroundingPrdSearchList = new ArrayList<ProductSearchInfo>();
		//当地自由行推荐产品
		List<ProductSearchInfo> recFreenessPrdSearchList = new ArrayList<ProductSearchInfo>();
		// 目的地到目的地推荐
		List<ProductSearchInfo> recDest2destPrdSearchList = new ArrayList<ProductSearchInfo>();
		// 酒店推荐
		List<Map<String, Object>> recHotelPrdSearchList = new ArrayList<Map<String, Object>>();
		// 景区推荐
		List<Map<String, Object>> recPlacePrdSearchList = new ArrayList<Map<String, Object>>();
	
		Map<String,Object> resultMap=getDestInfoForFreenessProducts(currentPlace.getPlaceId(),true);
		if(resultMap!=null&&!resultMap.isEmpty()){
			recFreenessPrdSearchList=(List<ProductSearchInfo>)resultMap.get("recFreenessPrdSearchList");
		}
		recommendProducts.put("recFreenessPrdSearchList", recFreenessPrdSearchList);
		
		// 跟团游-国内取：'GROUP','GROUP_LONG','SELFHELP_BUS';省级取：'GROUP','SELFHELP_BUS';大洲级：'GROUP_FOREIGN';
		resultMap.clear();
		resultMap=getDestInfoForSurroundProducts(fromPlaceId,currentPlace.getPlaceId(),getSubProductTypeForSurrounding(currentPlace),2);
		if(resultMap!=null&&!resultMap.isEmpty()){
			recSurroundingPrdSearchList=(List<ProductSearchInfo>)resultMap.get("returnList");
		}
		recommendProducts.put("recSurroundingPrdSearchList", recSurroundingPrdSearchList);
		// 查询路线取自由行（含交通）：长途自由行;出境取（出境跟团游、短途跟团游和自助巴士班，出境自由行）;省份取:GROUP_LONG,FREENESS_LONG;跟团游：短途跟团游，自助巴士班'GROUP','SELFHELP_BUS'
		resultMap.clear();
		//resultMap=getDestInfoForSurroundProducts(fromPlaceId,currentPlace.getPlaceId(),getSubProductTypeForDest2Dest(currentPlace),2);
		recDest2destPrdSearchList=this.getRecDest2destPrdSearchList(getSubProductTypeForDest2Dest(currentPlace),currentPlace,null);
		recommendProducts.put("recDest2destPrdSearchList", recDest2destPrdSearchList);

		// stage:1 目的地 stage: 2.景区类型 3.酒店
		// isTicket 2 酒店 1 门票 3 自由行 4 国内游
		// 查询酒店推荐以及酒店的房型产品
		int recPlaceSize = 2;
		resultMap.clear();
		resultMap = getDestInfoForHotelProducts(currentPlace.getPlaceId(),recPlaceSize);
		recHotelPrdSearchList = (List<Map<String, Object>>) resultMap.get("resultList");
		recommendProducts.put("recHotelPrdSearchList", recHotelPrdSearchList);
		resultMap.clear();
		//查询景区推荐以及景区下的门票产品
		resultMap = getDestInfoForTicketProducts(currentPlace.getPlaceId(),recPlaceSize);
		recPlacePrdSearchList = (List<Map<String, Object>>) resultMap.get("resultList");
		recommendProducts.put("recPlacePrdSearchList", recPlacePrdSearchList);
		
		return recommendProducts;
	}
	
	
	@SuppressWarnings("unused")
	@Override
	public Map<String, Object> getDestInfoForTicketProducts(final Long placeId,int prdSize) {
		Map<String, Object> cacheMap = new HashMap<String, Object>();
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentPlaceId", placeId);
		param.put("stage", Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode());
		param.put("startRows", 0);
		param.put("endRows", prdSize);
		param.put("hasTicket", "true");
		param.put("orderBy", "SEQ_NUM");
		List<PlaceSearchInfo> places = placeSearchInfoService.queryPlaceSearchInfoByParam(param);
		CmtTitleStatisticsVO cmtTitleStatistics=null;
		for (PlaceSearchInfo place : places) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			wrapperPlace(place);
			List<ProductSearchInfo> prdList = productSearchInfoService.getProductByPlaceIdAndType(1, 1, place.getPlaceId(), ProductSearchInfo.IS_TICKET.TICKET.getCode(), Constant.PLACE_STAGE.PLACE_FOR_DEST.getCode(), "FRONTEND");
			map.put("prdList", prdList);
			map.put("place", place);
			returnList.add(map);
		}
		if(places==null||places.isEmpty()){
			List<Place> placeList=placeDAO.getSonPlaceByParentPlaceId(placeId,new Long(prdSize),new Long(2));
			if(placeList!=null&&placeList.size()>0){
				Map<String, Object> map =null;
				for(Place place:placeList){
					map = new HashMap<String, Object>();
					map.put("place", place);
					wrapperPlace(place);
					map.put("prdList", null);
					returnList.add(map);
				}
			}
		}else{
			if(places.size()<10){
				param.put("hasTicket", "false");
				param.put("endRows", prdSize-places.size());
				completionScenic(returnList,param);
			}
		}
		cacheMap.put("resultList", returnList);
		return cacheMap;
	}
	private void wrapperPlace(Place place){
		CmtTitleStatisticsVO cmtTitleStatistics=cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(place.getPlaceId());
		if(cmtTitleStatistics!=null){
			place.setCommentCount(cmtTitleStatistics.getCommentCount());
			place.setAvgScore(cmtTitleStatistics.getAvgScore());
		}
	}
	private void wrapperPlace(PlaceSearchInfo place){
		CmtTitleStatisticsVO cmtTitleStatistics=cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(place.getPlaceId());
		if(cmtTitleStatistics!=null){
			place.setCommentCount(cmtTitleStatistics.getCommentCount());
			place.setAvgScore(cmtTitleStatistics.getAvgScore());
		}
	}
	private void completionScenic(List<Map<String, Object>> returnList,Map<String, Object> param){
		List<PlaceSearchInfo> placeList=placeSearchInfoService.queryPlaceSearchInfoByParam(param);
		Map<String, Object> map =null;
		for(PlaceSearchInfo place:placeList){
			map = new HashMap<String, Object>();
			map.put("place", place);
			wrapperPlace(place);
			map.put("prdList", null);
			returnList.add(map);
		}
	}
	
	@Override
	public Map<String, Object> getDestInfoForFreenessProducts(final Long placeId,boolean isRecommend) {
		Map<String, Object> cacheMap = new HashMap<String, Object>();
		// 取两条超级自由行
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("placeId", placeId);
		param.put("subProductTypes", new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS.name()});
		param.put("selfPack", "true");
		param.put("startRows", 0);
		param.put("endRows",2);
		List<ProductSearchInfo> list=productSearchInfoService.getProductByFromPlaceIdAndDestId(param);
		cacheMap.put("recFreenessSelfPackPrdList",list);
		if(isRecommend){
			if(list!=null){
				if(list.size()==2)
					cacheMap.put("recFreenessPrdSearchList",list);
				else{
					param.clear();
					param.put("placeId", placeId);
					param.put("subProductTypes", new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS.name()});
					param.put("selfPack", "");
					param.put("startRows", 0);
					param.put("endRows",2-list.size());
					list.addAll(productSearchInfoService.getProductByFromPlaceIdAndDestId(param));
					cacheMap.put("recFreenessPrdSearchList",list);
				}
			}else{
				
			}
		}else{
			// 10条目的地自由行产品
			param.clear();
			param.put("placeId", placeId);
			param.put("subProductTypes", new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS.name()});
			param.put("selfPack", "");
			param.put("startRows", 0);
			param.put("endRows",10);
			cacheMap.put("recFreenessPrdSearchList", productSearchInfoService.getProductByFromPlaceIdAndDestId(param));
		}
		
		
		
		return cacheMap;
	}
	
	@Override
	public Map<String, Object> getDestInfoForHotelProducts(final Long placeId,int prdSize) {
		Map<String, Object> cacheMap = new HashMap<String, Object>();
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
	
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentPlaceId", placeId);
		param.put("stage", Constant.PLACE_STAGE.PLACE_FOR_HOTEL.getCode());
		param.put("startRows", 0);
		param.put("endRows", prdSize);
		param.put("hasHotel", "true");
		param.put("orderBy", "SEQ_NUM");
		List<PlaceSearchInfo> places = placeSearchInfoService.queryPlaceSearchInfoByParam(param);		
		
		for (PlaceSearchInfo place : places) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("place", place);
			wrapperPlace(place);
			List<ProductSearchInfo> prdSearchHotelList = productSearchInfoService.getProductHotelByPlaceIdAndType(place.getPlaceId(),100, "FRONTEND", false);
			map.put("prdList", prdSearchHotelList);
			if(prdSearchHotelList!=null&&prdSearchHotelList.size()>0){
				Collections.sort(prdSearchHotelList, new ProductPriceComparator());
				place.setProductsPrice(String.valueOf(((ProductSearchInfo)prdSearchHotelList.get(0)).getSellPrice()));
			}
			List<ProductSearchInfo> singleRoomList = new ArrayList<ProductSearchInfo>();// 房型服务
			List<ProductSearchInfo> hotelSuitList = new ArrayList<ProductSearchInfo>();// 酒店套餐
			for (ProductSearchInfo viewProductSearchInfo : prdSearchHotelList) {
				if (viewProductSearchInfo.isHotelSuit()) {
					hotelSuitList.add(viewProductSearchInfo);
				} else {
					singleRoomList.add(viewProductSearchInfo);
				}
			}
			hotelSuitList.addAll(productSearchInfoService.selectFreenessProductsOfHotel(place.getPlaceId()));
			map.put("singleRoomList", singleRoomList);
			map.put("hotelSuitList", hotelSuitList);
			// 读取该酒店的周边景区
			List<PlaceCoordinateGoogle> placeCoordinates = placeCoordinateGoogleService.getCoordinateByPlace(place.getPlaceId(), Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode());
			map.put("coorPlace", placeCoordinates);
			returnList.add(map);
		}
		if(places==null||places.isEmpty()){
			List<Place> placeList=placeDAO.getSonPlaceByParentPlaceId(placeId,new Long(prdSize),new Long(3));
			if(placeList!=null&&placeList.size()>0){
				Map<String, Object> map =null;
				for(Place place:placeList){
					map = new HashMap<String, Object>();
					map.put("place", place);
					map.put("prdList", null);
					map.put("singleRoomList", null);
					map.put("hotelSuitList", null);
					map.put("coorPlace", null);
					returnList.add(map);
				}
			}
		}else{
			if(places.size()<10){
				param.put("hasHotel", "false");
				param.put("endRows", prdSize-places.size());
				List<PlaceSearchInfo> list = placeSearchInfoService.queryPlaceSearchInfoByParam(param);	
				if(list!=null&&list.size()>0){
					Map<String, Object> map =null;
					for(PlaceSearchInfo place:list){
						map = new HashMap<String, Object>();
						map.put("place", place);
						map.put("prdList", null);
						map.put("singleRoomList", null);
						map.put("hotelSuitList", null);
						map.put("coorPlace", null);
						returnList.add(map);
					}
				}
			}
		}
		cacheMap.put("resultList", returnList);
		return cacheMap;
	}
	
	@Override
	public Map<String, Object> getDestInfoForSurroundProducts(final Long fromPlaceId, final Long placeId, final String[] subProductType,int prdSize) {
		Map<String, Object> cacheMap = new HashMap<String, Object>();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("fromPlaceId", fromPlaceId);
		param.put("placeId", placeId);
		param.put("subProductTypes", subProductType);
		param.put("startRows", 0);
		param.put("endRows", prdSize);
		
		cacheMap.put("returnList", productSearchInfoService.getProductByFromPlaceIdAndDestId(param));
		return cacheMap;
	}
	
	/**
	 * @deprecated 垃圾代码，为了重用而重用，干掉它吧！GO!!!!!!
	 * 目的地酒店以及景点查询 并且包含其下的产品
	 * 
	 * @param id
	 *            目的地
	 * @param placeSize
	 *            设置返回目的地的数量
	 * @param stage
	 *            2.景区类型 3.酒店
	 * @param isTicket
	 *            2 酒店 1 门票 3 自由行 4 国内游
	 * @param rowsSize
	 *            景点或酒店获取数
	 * @param prdSize
	 *            每个景点/酒店下产品获取数量
	 * @param startRows
	 *            开始行
	 * @return
	 */
	public Map<String, Object> getPlaceAndPrd(Long id, String placeStage, String isTicket, int placeSize, int rowsSize, int prdSize, int startRows) {
		Map<String, Object> resultMap;
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
			resultMap = new HashMap<String, Object>();
			// 获取目的地下的景区或酒店
			try {
				Place place =new Place();
				place.setParentPlaceId(id);
				place.setStage(placeStage);
				place.setStartRows((long)startRows);
				place.setEndRows((long)rowsSize);
				List<Place> places = placeDAO.getSonPlaceByPlaceIdAndStage(place);
				if (places.size() > 0) {
					// 景区/酒店前台排序
					if ("2".equals(isTicket)) {
						Collections.sort(places, new Place.compareHotelNumDesc());
					} else {
						Collections.sort(places, new Place.compareTicketNumDesc());
					}
					// 返回指定数量的place
					for (int index = 0; index < places.size()&&index < placeSize; index++) {
						Place p = places.get(index);
						p.setImgUrl(this.getPlacePhotoImg(p));
						
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("place", p);
						// 酒店需要特殊处理返回 酒店信息 ProductSearchInfo
						if ("2".equals(isTicket)) {
							List<ProductSearchInfo> prdSearchHotelList = productSearchInfoService.getProductHotelByPlaceIdAndType(p.getPlaceId(),1000, "FRONTEND", false);
							map.put("prdList", prdSearchHotelList);
							List<ProductSearchInfo> singleRoomList = new ArrayList<ProductSearchInfo>();// 房型服务
							List<ProductSearchInfo> hotelSuitList = new ArrayList<ProductSearchInfo>();// 酒店套餐
							for (ProductSearchInfo viewProductSearchInfo : prdSearchHotelList) {
								if (viewProductSearchInfo.isHotelSuit()) {
									hotelSuitList.add(viewProductSearchInfo);
								} else {
									singleRoomList.add(viewProductSearchInfo);
								}
							}
							hotelSuitList.addAll(productSearchInfoService.selectFreenessProductsOfHotel(p.getPlaceId()));
							map.put("singleRoomList", singleRoomList);
							map.put("hotelSuitList", hotelSuitList);
							// 读取该酒店的周边景区
							List<PlaceCoordinateGoogle> placeCoordinates = placeCoordinateGoogleService.getCoordinateByPlace(p.getPlaceId(), Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode());
							map.put("coorPlace", placeCoordinates);
						} else {
							List<ProductSearchInfo> prdList=productSearchInfoService.getProductByPlaceIdAndType(prdSize, 1, p.getPlaceId(), isTicket, "1", "FRONTEND");
							map.put("prdList", prdList);
						}
						returnList.add(map);
					}
				}
				resultMap.put("resultList", returnList);

			} catch (Exception e) {
				e.printStackTrace();
			}
		return resultMap;
	}
	/**
	 * 获取当前目的地显示数据
	 * @param place
	 */
	public Map<String, Object> getAboutPlaceContent(Place place) {
		Map<String, Object> aboutPlaceContent=new HashMap<String, Object>();
		List<Place> seoDestList;
		List<Place> seoPersonDestList;
		List<Place> seoPlaceList;
		
		// 获取当前目的地同级目的地
		seoDestList = this.placeDAO.getPlaceBySameParentPlaceId(place.getParentPlaceId() == null ? null : Long.valueOf(place.getParentPlaceId()), 1L,null);
		aboutPlaceContent.put("seoDestList", seoDestList);
		//推荐的热门目的地及节庆专题
		aboutPlaceContent.putAll(this.hotDestRecommend(seoDestList,place));
		// 获取当前目的地下级目的地
		seoPersonDestList = this.placeDAO.getPlaceBySameParentPlaceId(Long.valueOf(place.getPlaceId()), 1L,null);
		aboutPlaceContent.put("seoPersonDestList", seoPersonDestList);
		// 获取当前目的地下的推荐景点
		seoPlaceList = placeDAO.getSonPlaceByParentPlaceId(place.getPlaceId(), 23, 2L);
		aboutPlaceContent.put("seoPlaceList", seoPlaceList);
		
		return aboutPlaceContent;
	}
	
	/**
	 * 获取目的地 热门推荐数据
	 * 
	 * @return
	 */
	private Map<String, Object> hotDestRecommend(List<Place> sameDestList,Place place) {
		Map<String, Object> hotDestRecommendMap=new HashMap<String, Object>();
		String pageChannel="dest";
		List<RecommendInfo> hotRecommandPlaceList= new ArrayList<RecommendInfo>();
		List<RecommendInfo> commonPlaceTopicList= new ArrayList<RecommendInfo>();
		
		// 根据place以及station获取level1的block
		Map<String, List<RecommendInfo>> recommendMap = recommendInfoService.getRecommendInfoByDataCodeAndPageChannel(place.getPlaceId(), pageChannel);
		List<RecommendInfo> hotRecommendList = null;
		if (recommendMap != null) {
			hotRecommendList = (List<RecommendInfo>) recommendMap.get(pageChannel + "_R_" + place.getPlaceId());
			commonPlaceTopicList = (List<RecommendInfo>) recommendMap.get(pageChannel + "_TOPIC_" + place.getPlaceId());
		}

		// 在PC后台新增的目的地模块，可人为进行推荐，当人为在后台进行推荐了热门搜索后，此处自动调取后台推荐的内容
		// 替换此处自动推荐的内容。当无人为推荐时，按照系统自动推荐的来呈现
		if (hotRecommendList == null || hotRecommendList.size() <= 0) {
			// 取当前目的地的同级目的地列表中前6个目的地，如果前6个目的地中包含了当前目的地，则去除当前目的地，向后进一个，保证除当前目的地的6个目的地
			RecommendInfo vri = null;
			for (Place p : sameDestList) {
				if (!p.getPlaceId().equals(place.getPlaceId())) {
					vri = new RecommendInfo();
					vri.setRecommendInfoId(p.getPlaceId());
					vri.setUrl("http://www.lvmama.com/dest/" + p.getPinYinUrl());
					vri.setTitle(p.getName());
					hotRecommandPlaceList.add(vri);
				}
				if (hotRecommandPlaceList.size() == 6)
					break;
			}

		} else {
			hotRecommandPlaceList = hotRecommendList;
		}
		hotDestRecommendMap.put("hotRecommandPlaceList", hotRecommandPlaceList);
		hotDestRecommendMap.put("commonPlaceTopicList", commonPlaceTopicList);
		return hotDestRecommendMap;

	}
	
	/**
	 * 国内自由行(含交通) 和 省份级的各地到
	 */
	public Map<String,Object> createDest2DestProduct(String[] subProductTypes,Place place,Long fromDestId) {
		Map<String, Object> dest2DestProductMap=new HashMap<String, Object>();
		//超级自由行推荐产品
		List<ProductSearchInfo> recFreenessSelfPackPrdList = new ArrayList<ProductSearchInfo>();
		// 周边跟团游推荐产品
		List<ProductSearchInfo> recSurroundingPrdSearchList = new ArrayList<ProductSearchInfo>();
		//目的地到目的地推荐
		List<ProductSearchInfo> recDest2destPrdSearchList = new ArrayList<ProductSearchInfo>();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("fromPlaceId", fromDestId);
		param.put("placeId", place.getPlaceId());
		param.put("subProductTypes", subProductTypes);
		param.put("selfPack", String.valueOf(Boolean.TRUE));
		param.put("startRows", 1);
		param.put("endRows",2);
		recFreenessSelfPackPrdList = productSearchInfoService.getProductByFromPlaceIdAndDestId(param);
		dest2DestProductMap.put("recFreenessSelfPackPrdList", recFreenessSelfPackPrdList);
		
		param.put("startRows", 1);
		param.put("endRows",10);
		if ("PROVINCE".equals(place.getPlaceType()) || "ZZQ".equals(place.getPlaceType())) {
			// 取跟团游 10条记录
			param.put("subProductTypes", new String[]{Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name()});
			param.put("selfPack", String.valueOf(Boolean.FALSE));
			recDest2destPrdSearchList.addAll(productSearchInfoService.getProductByFromPlaceIdAndDestId(param));
 
			// 取自由行 10条记录/
			param.put("subProductTypes", new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name()});
			recSurroundingPrdSearchList=productSearchInfoService.getProductByFromPlaceIdAndDestId(param);
			dest2DestProductMap.put("recSurroundingPrdSearchList", recSurroundingPrdSearchList);
		} else {
			// 取subProductType 10条记录
			param.put("selfPack",String.valueOf(Boolean.FALSE));
			dest2DestProductMap.put("recSurroundingPrdSearchList", productSearchInfoService.getProductByFromPlaceIdAndDestId(param));
		}
		dest2DestProductMap.put("recDest2destPrdSearchList", recDest2destPrdSearchList);
		return dest2DestProductMap;
	}
	public List<ProductSearchInfo> getRecDest2destPrdSearchList(String[] subProductTypes,Place place,Long fromDestId){
		Map<String,Object> param=new HashMap<String,Object>();
		//ProductSearchInfo viewProductSearchInfo=new ProductSearchInfo();
		param.put("fromPlaceId", fromDestId);
		param.put("placeId", place.getPlaceId());
		param.put("subProductTypes", subProductTypes);
		param.put("selfPack", String.valueOf(Boolean.FALSE));
		param.put("startRows", 1);
		param.put("endRows",2);
		//目的地到目的地推荐
		List<ProductSearchInfo> recDest2destPrdSearchList = productSearchInfoService.getProductByFromPlaceIdAndDestId(param);
		return recDest2destPrdSearchList;
	}
	public String getPlacePhotoImg(Place place){
		PlacePhoto	placePhoto=new PlacePhoto();
		placePhoto.setPlaceId(place.getPlaceId());
		placePhoto.setType(PlacePhotoTypeEnum.LARGE.getCode());
		List<PlacePhoto>  p = (List<PlacePhoto>) placePhotoDAO.queryByPlacePhoto(placePhoto);
		if ( p != null && p.size()>0&& StringUtils.isNotEmpty(p.get(0).getImagesUrl())) {
		  return p.get(0).getImagesUrl();
		}
		return Place.DEFAULT_PIC;
	}
	
	public String getPlacePhotoLargeImg(Place place){
		PlacePhoto	placePhoto=new PlacePhoto();
		placePhoto.setPlaceId(place.getPlaceId());
		placePhoto.setType(PlacePhotoTypeEnum.LARGE.getCode());
		List<PlacePhoto>  p = (List<PlacePhoto>) placePhotoDAO.queryByPlacePhoto(placePhoto);
		if ( p != null && p.size()>0&& StringUtils.isNotEmpty(p.get(0).getImagesUrl())) {
		  return p.get(0).getImagesUrl();
		}
		return Place.DEFAULT_PIC;
	}
	/**
	 * @deprecated
	 * 国内取：
	 * 'GROUP','GROUP_LONG','SELFHELP_BUS';省级取：'GROUP','SELFHELP_BUS';大洲级：'GROUP_FOREIGN
	 * ' ;
	 * @param place
	 * @return
	 */
	public String[] getSubProductTypeForSurrounding(Place place) {
		if (null != place && (Constant.PLACE_TYPE.ZZQ.name().equalsIgnoreCase(place.getPlaceType()) || Constant.PLACE_TYPE.PROVINCE.name().equalsIgnoreCase(place.getPlaceType()))) {
			return new String[]{Constant.SUB_PRODUCT_TYPE.GROUP.name(), Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name()};
		} else {
			return new String[]{Constant.SUB_PRODUCT_TYPE.GROUP.name(), Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name(), Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name()};
		}
	}

	/**
	 * @deprecated
	 * 查询路线取自由行（含交通）：长途自由行;出境取（出境跟团游、短途跟团游和自助巴士班）;省份取:GROUP_LONG,
	 * FREENESS_LONG;跟团游：短途跟团游，自助巴士班'GROUP','SELFHELP_BUS'
	 * 
	 * @param place
	 * @return
	 */
	public String[] getSubProductTypeForDest2Dest(Place place) {
		
		if (null != place 
				&& (Constant.PLACE_TYPE.PROVINCE.name().equalsIgnoreCase(place.getPlaceType()) 
						|| Constant.PLACE_TYPE.ZZQ.name().equalsIgnoreCase(place.getPlaceType()))) {
			return new String[]{Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name(), Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name()};	
		}
		
		if (null != place && Place.PLACE_TEMPLATE.TEMPLATE_ABROAD.name().equalsIgnoreCase(place.getTemplate())) {
			return new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name(), Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name(),Constant.SUB_PRODUCT_TYPE.GROUP.name()};	
		}
		return new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name()};
	}

	/**
	 * @deprecated
	 * 出境跟团游： 大洲级跟团游：
	 * 
	 * @param place
	 * @return
	 */
	public String[] getSubProductTypeForDest2destGroup(Place place) {
		if (null != place && Constant.PLACE_STAGE.PLACE_FOR_DEST.getCode().equalsIgnoreCase(place.getStage())) {
			return new String[]{Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name()};
		} else {
			return new String[]{Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name(),Constant.SUB_PRODUCT_TYPE.GROUP.name()};
		}
	}
	
	@Override
	public Map<String,Object>  getHotelPageInfo(final Place place) {
		String channel = "FRONT";
		Map<String, Object> hotelPageInfoMap = new HashMap<String, Object>();
		List<ProductSearchInfo> singleRoomList = new ArrayList<ProductSearchInfo>();// 房型服务
		List<ProductSearchInfo> hotelSuitList = new ArrayList<ProductSearchInfo>();// 酒店套餐
		ProductList recommendProductList; // 相关推荐产品
		List<Place> hotelCoordinateList;
		List<Place> placeList;//当前目的相关景区
		List<PlaceCoordinateBaidu> placeCoordinateList;//周边景区
		
		setPlaceLargePhoto(place);
		if(null != place && StringUtils.isNotBlank(place.getRemarkes())) {
			StringBuilder desc = new StringBuilder(com.lvmama.comm.utils.StringUtil.filterOutHTMLTags(place.getRemarkes()));
			if(desc.length() > 90){
				desc.setLength(90);
				desc.append("...");
			}
			place.setRemarkes(desc.toString());
		}
		hotelPageInfoMap.put("place", place);
		
		
		// 取上级目的地下的自由行产品	
		Long relationPlaceId = null == place.getParentPlaceId() ? place.getPlaceId() : place.getParentPlaceId();
		recommendProductList = productSearchInfoService.getProductByPlaceIdAnd4Type(relationPlaceId,10, channel);
		hotelPageInfoMap.put("recommendProductList", recommendProductList);
	   
		//取同城酒店
		hotelCoordinateList=placeDAO.getPlaceInfoBySameParentPlaceId(relationPlaceId ,"3", 10L);//10条
		hotelPageInfoMap.put("hotelCoordinateList", hotelCoordinateList);
		
		List<ProductSearchInfo> hotelProductList = productSearchInfoService.getProductHotelByPlaceIdAndType(place.getPlaceId(), 1000, channel, false);
		for (ProductSearchInfo viewProductSearchInfo : hotelProductList) {
			if (viewProductSearchInfo.isHotelSuit()) {
				hotelSuitList.add(viewProductSearchInfo);
			} else {
				singleRoomList.add(viewProductSearchInfo);
			}
		}
		hotelSuitList.addAll(productSearchInfoService.selectFreenessProductsOfHotel(place.getPlaceId()));
		hotelPageInfoMap.put("hotelSuitList", hotelSuitList);
		hotelPageInfoMap.put("singleRoomList", singleRoomList);

		placeList = placeDAO.getPlaceInfoBySameParentPlaceId(place.getParentPlaceId(), "2",3L);// 取3条
		hotelPageInfoMap.put("placeList", placeList);
		
		placeCoordinateList = placeCoordinateBaiduService.getCoordinateByPlace(place.getPlaceId(), Long.parseLong(Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode()));
		hotelPageInfoMap.put("placeCoordinateList", placeCoordinateList);

		return hotelPageInfoMap;
	}  
	
		@Override
	public Map<String,Object>  getHolidayHotelPageInfo (final Place place){
		Map<String, Object> hotelPageInfoMap = new HashMap<String, Object>();
		
			List<ProductSearchInfo> singleRoomList = new ArrayList<ProductSearchInfo>();// 房型服务
			List<ProductSearchInfo> holidayHotelTuanGouList = new ArrayList<ProductSearchInfo>();// 团购
			List<ProductSearchInfo> holiDayHotelFrontList = new ArrayList<ProductSearchInfo>();  //	前台可售
			PlaceHotel placeHotel = new PlaceHotel();	//酒店信息
			PlaceSearchInfo placeSearchInfo = new PlaceSearchInfo(); //酒店附加信息
			List<PlacePhoto> placePhotoList = new ArrayList<PlacePhoto>(); //图片
			List<HotelTrafficInfo> trafficInfoList = new ArrayList<HotelTrafficInfo>(); //交通信息
			List<PlaceHotelRoom> hotelRoomList = new ArrayList<PlaceHotelRoom>(); //房型介绍
			PlaceHotelNotice recommend = new PlaceHotelNotice(); //一句话推荐
			List<PlaceHotelNotice> hotelNoticeList = new ArrayList<PlaceHotelNotice>();//公告
			List<PlaceHotelOtherRecommend> otherRecommendList = new ArrayList<PlaceHotelOtherRecommend>(); //特色服务与玩法介绍
			List<PlaceLandMark> landMarkList = new ArrayList<PlaceLandMark>(); //附近设施
			//产品ID 或 着 类别
			Map<String,List<MarkCoupon>> markCouponMap = new HashMap<String, List<MarkCoupon>>(); //优惠券
			//酒店附加信息
			placeSearchInfo = placeSearchInfoService.getPlaceSearchInfoByPlaceId(place.getPlaceId());
			//分转化为元
			if(placeSearchInfo.getProductsPrice() != null){
				if(placeSearchInfo.getProductsPrice().matches("^[0-9]+")){
					BigDecimal decimal = BigDecimal.valueOf(Long.valueOf(placeSearchInfo.getProductsPrice())).divide(new BigDecimal(100));
					placeSearchInfo.setProductsPrice(decimal.toString());
				}
			}
			hotelPageInfoMap.put("placeSearchInfo", placeSearchInfo);
			if(placeSearchInfo.getLatitude() != null && placeSearchInfo.getLongitude() != null){
				//附近设施
				GeoLocation gl = GeoLocation.fromDegrees(placeSearchInfo.getLatitude(),placeSearchInfo.getLongitude());
				//附近范围
				GeoLocation[] minMaxGL = gl.boundingCoordinates(30);
				Map<String,Double> mapGeoLocation = new HashMap<String, Double>();
				mapGeoLocation.put("limit",8D);
				mapGeoLocation.put("latitude",placeSearchInfo.getLatitude());
				mapGeoLocation.put("longitude",placeSearchInfo.getLongitude());
				//纬度
				mapGeoLocation.put("minLongitude",minMaxGL[0].getLongitudeInDegrees());
				mapGeoLocation.put("maxinLongitude",minMaxGL[1].getLongitudeInDegrees());
				//经度
				mapGeoLocation.put("minLatitude",minMaxGL[0].getLatitudeInDegrees());
				mapGeoLocation.put("maxLatitude",minMaxGL[1].getLatitudeInDegrees());
				landMarkList = placeLandMarkDAO.queryPlaceLandMarkByGeoLocation(mapGeoLocation);
				hotelPageInfoMap.put("landMarkList", landMarkList);
			}
			
			//酒店信息
			placeHotel = placeHotelDAO.searchPlaceHotel(place.getPlaceId());
			hotelPageInfoMap.put("placeHotel",placeHotel);
			//单房型
			singleRoomList = productSearchInfoService.queryProductBranchByPlaceId(place.getPlaceId(),Constant.CHANNEL.FRONTEND.getCode(),1000);
			hotelPageInfoMap.put("singleRoomList", singleRoomList);
			
			//套餐与自由行
			holidayHotelTuanGouList = productSearchInfoService.selectProductsOfHotel(place.getPlaceId(),Constant.CHANNEL.TUANGOU.getCode());
			for (int i = 0; i < holidayHotelTuanGouList.size(); i++) {
				ProductPropertySearchInfo p = productPropertySearchInfoDAO.getProductPropertySearchInfoByProductId(holidayHotelTuanGouList.get(i).getProductId());
				if(p != null)holidayHotelTuanGouList.get(i).setTopic(p.getRouteTopic()==null?null:p.getRouteTopic().replaceAll("[u4E00-u9FA5^~]",""));
			}
			hotelPageInfoMap.put("holidayHotelTuanGouList", holidayHotelTuanGouList);
			holiDayHotelFrontList = productSearchInfoService.selectProductsOfHotel(place.getPlaceId(),Constant.CHANNEL.FRONTEND.getCode());
			for (int i = 0; i < holiDayHotelFrontList.size(); i++) {
				ProductPropertySearchInfo p = productPropertySearchInfoDAO.getProductPropertySearchInfoByProductId(holiDayHotelFrontList.get(i).getProductId());
				if(p != null)holiDayHotelFrontList.get(i).setTopic(p.getRouteTopic()==null?null:p.getRouteTopic().replaceAll("[u4E00-u9FA5^~]",""));
			}
			hotelPageInfoMap.put("holiDayHotelFrontList", holiDayHotelFrontList);
			
			//优惠券、绑定产品ID或着产品类型
			Map<String,Object> parameters = null;
			Map<String,List<MarkCoupon>> tempMap = new HashMap<String, List<MarkCoupon>>();
			//类别优惠
			String[] subType = {"SINGLE_ROOM","FREENESS","HOTEL_SUIT"};
			for (int i = 0; i < subType.length; i++) {
				parameters = new HashMap<String, Object>();
				parameters.put("subProductType",subType[i]);
				parameters.put("couponIds",Constant.COUPON_BATH_NUMBER);
				List<MarkCoupon> markCouponList = markCouponDAO.selectProductValidMarkCoupon(parameters);
				if(markCouponList != null && markCouponList.size() > 0){
					tempMap.put(subType[i],markCouponList);
				}
			}
			//产品优惠
			List<ProductSearchInfo> longList = new ArrayList<ProductSearchInfo>();
			if(singleRoomList != null && singleRoomList.size() > 0){
				for (int i = 0; i < singleRoomList.size(); i++) {
					longList.add(singleRoomList.get(i));
				}
			}
			if(holidayHotelTuanGouList != null && holidayHotelTuanGouList.size() > 0){
				for (int i = 0; i < holidayHotelTuanGouList.size(); i++) {
					longList.add(holidayHotelTuanGouList.get(i));
				}
			}
			if(holiDayHotelFrontList != null && holiDayHotelFrontList.size() > 0){
				for (int i = 0; i < holiDayHotelFrontList.size(); i++) {
					longList.add(holiDayHotelFrontList.get(i));
				}
			}
			for (int i = 0; i < longList.size(); i++) {
				//查询该产品是否能使用优惠券
				if(longList.get(i).getCouponAble() != null && longList.get(i).getCouponAble().equals("true")){
					parameters = new HashMap<String, Object>();
					parameters.put("productId",longList.get(i).getProductId());
					parameters.put("couponIds",Constant.COUPON_BATH_NUMBER);
					List<MarkCoupon> allMarkCouponList = new ArrayList<MarkCoupon>();
					List<MarkCoupon> markCouponList = markCouponDAO.selectProductValidMarkCoupon(parameters);
					//将该类别下 的优惠添加至产品下
					if(tempMap.get(longList.get(i).getSubProductType()) != null)
						allMarkCouponList.addAll(tempMap.get(longList.get(i).getSubProductType()));
					if(markCouponList != null && markCouponList.size() > 0)
						allMarkCouponList.addAll(markCouponList);
					if(allMarkCouponList.size() > 0){
						markCouponMap.put(longList.get(i).getProductId().toString(), allMarkCouponList);
					}
				}
			}
			hotelPageInfoMap.put("markCouponMap",markCouponMap);
			//图片
			PlacePhoto placePhoto = new PlacePhoto();
			placePhoto.setPlaceId(place.getPlaceId());
			placePhoto.setType("LARGE");
			placePhotoList = placePhotoDAO.queryByPlacePhoto(placePhoto);
			hotelPageInfoMap.put("placePhotoList", placePhotoList);
			//交通信息
			trafficInfoList = trafficInfoDAO.queryByPlaceId(place.getPlaceId());
			hotelPageInfoMap.put("trafficInfoList", trafficInfoList);
			//房型介绍
			PlaceHotelRoom room = new PlaceHotelRoom();
			room.setPlaceId(place.getPlaceId());
			hotelRoomList = placeHotelRoomService.queryAllPlaceHotelRoom(room);
			hotelPageInfoMap.put("hotelRoomList", hotelRoomList);
			//公告 
			PlaceHotelNotice notice=new PlaceHotelNotice();
			notice.setPlaceId(place.getPlaceId());
			notice.setNoticeType(PlaceUtils.ALL);
			notice.setValidNotice("true");
			hotelNoticeList = placeHotelNoticeService.queryByHotelNotice(notice);
			hotelPageInfoMap.put("hotelNoticeList", hotelNoticeList);
			//一句话推荐
			notice.setNoticeType(PlaceUtils.RECOMMEND);
			List<PlaceHotelNotice> notileList = new ArrayList<PlaceHotelNotice>();
			notileList = placeHotelNoticeService.queryByHotelNotice(notice);
			if(notileList.size() > 0){
				recommend = notileList.get(0);
			}
			hotelPageInfoMap.put("recommend",recommend);
			//特色与玩法介绍
			PlaceHotelOtherRecommend otherRecommend = new PlaceHotelOtherRecommend();
			otherRecommend.setPlaceId(place.getPlaceId());
			otherRecommendList = placeHotelRecommendService.queryAllPlaceHotelRecommend(otherRecommend);
			hotelPageInfoMap.put("otherRecommendList", otherRecommendList);
			
			//获取酒店详情页seoTkd规则
			SeoIndexPage seoIndexPage=seoIndexPageService.getSeoIndexPageByPageCode(SeoIndexPageCodeEnum.CH_DEST_HOTEL_HOLIDAY_DETAILS.getCode());
			if(seoIndexPage!=null){
				if(place.getSeoTitle() != null) 
					seoIndexPage.setSeoTitle(place.getSeoTitle() == "" ? SeoUtils.getSeoIndexPageHoliday(placeSearchInfo,seoIndexPage.getSeoTitle()) : place.getSeoTitle());
				else seoIndexPage.setSeoTitle(SeoUtils.getSeoIndexPageHoliday(placeSearchInfo,seoIndexPage.getSeoTitle()));
				if(place.getSeoKeyword() != null)
					seoIndexPage.setSeoKeyword(place.getSeoKeyword() == "" ? SeoUtils.getSeoIndexPageHoliday(placeSearchInfo,seoIndexPage.getSeoKeyword()) : place.getSeoKeyword());
				else seoIndexPage.setSeoKeyword(SeoUtils.getSeoIndexPageHoliday(placeSearchInfo,seoIndexPage.getSeoKeyword()));
				if(place.getSeoDescription() != null)
					seoIndexPage.setSeoDescription(place.getSeoDescription() == "" ? SeoUtils.getSeoIndexPageHoliday(placeSearchInfo, seoIndexPage.getSeoDescription()) : place.getSeoDescription());
				else seoIndexPage.setSeoDescription(SeoUtils.getSeoIndexPageHoliday(placeSearchInfo, seoIndexPage.getSeoDescription()));
			}
			hotelPageInfoMap.put("seoIndexPage", seoIndexPage);
		return hotelPageInfoMap;
	}  
	/**
	 * 设置目的地的大图片
	 * @param place 需要设置的目的地
	 * @return 目的地
	 * <p>查询目的地的所有大图片列表，然后设置为目的地的图片列表后返回目的地。当目的地无效时，此方法将返回<code>Null</code>.
	 */
	private Place setPlaceLargePhoto (final Place place) {
		if (null != place && "Y".equalsIgnoreCase(place.getIsValid())) {
			PlacePhoto	placePhoto=new PlacePhoto();
			placePhoto.setPlaceId(place.getPlaceId());
			placePhoto.setType(PlacePhotoTypeEnum.LARGE.getCode());
			place.setPlacePhoto((List<PlacePhoto>) placePhotoDAO.queryByPlacePhoto(placePhoto));
			return place;
		} 
		return null;
	}

	
	@Override
	public List<PlaceSearchInfo> getVicinityByPlace(final Long placeId, final String subject, final Long stage, final Long limit) {
		PlaceSearchInfo  nowPlace= placeSearchInfoService.getPlaceSearchInfoByPlaceId(placeId);
		if (null != nowPlace && null != nowPlace.getLongitude() && null != nowPlace.getLatitude()) {
			
			//获取位置
			GeoLocation nowLocation=GeoLocation.fromDegrees(nowPlace.getLatitude(), nowPlace.getLongitude());
			//附近范围
			GeoLocation[] minMaxGL =nowLocation.boundingCoordinates(100);//100公里范围圈；
			DimensionVo d = new DimensionVo();
			d.setLatitude(nowPlace.getLatitude());
			d.setLongitude(nowPlace.getLongitude());
			//纬度
			d.setMinLongitude(minMaxGL[0].getLongitudeInDegrees());
			d.setMaxinLongitude(minMaxGL[1].getLongitudeInDegrees());
			//经度
			d.setMinLatitude(minMaxGL[0].getLatitudeInDegrees());
			d.setMaxLatitude(minMaxGL[1].getLatitudeInDegrees());
			d.setStage(stage);
			if (null != subject) {
				d.setPlaceFirstSubject(subject);
			}
			List<PlaceSearchInfo> placeList= placeSearchInfoDAO.queryVicinityPlaceSearchInfoListByDimension(d);
			List<PlaceSearchInfo> result=new ArrayList<PlaceSearchInfo>() ;
			for(PlaceSearchInfo p:placeList){
				//周边dest位置
				GeoLocation geo=GeoLocation.fromDegrees(p.getLatitude(), p.getLongitude());
				p.setDistance((float) (geo.distanceTo(nowLocation)));
				if(p.getDistance()>0L&&p.getPlaceId()!=placeId){
					result.add(p);
				}
			}
			//排序
			Collections.sort(result, new Comparator<PlaceSearchInfo>(){
				public int compare(PlaceSearchInfo o1, PlaceSearchInfo o2) {
					if (0!=o1.getDistance()&&0!=o2.getDistance()&& o1.getDistance() !=  o2.getDistance() ) {
 							try{
								return o1.getDistance()>= o2.getDistance()? 1 : -1;
							}catch(Exception e){
								return 0;
							 }
 					}  
					return 1;
				}
			});
			if (null != limit) {
			if(result.size()>limit){
				result=result.subList(0, (int) (limit-1));
			}
			}
			return result;
			 
		}
		
		return null;
	}

	@Override
	public List<PlacePhoto> getPlacePhotoListByPlacePhoto(PlacePhoto placePhoto) {
		return placePhotoDAO.queryByPlacePhoto(placePhoto);
	}

	private Long getcomSearchTranscodeByKeyWord(String keyword){
		if(StringUtils.isNotBlank(keyword)){
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("keyword", keyword);
			ComSearchTranscode comSearchTranscod=comSearchTranscodeService.getSearchByParam(p);
		    return null == comSearchTranscod ? null : comSearchTranscod.getCodeId();
		}else {
			return null;
		}
	}
	
	@Override
	public ScenicVo getScenicPageMainInfo(Place scenicPlace) {
		ScenicVo scenic=new ScenicVo();
		
		//主题 地址汉字搜索转码
		if(null!=getcomSearchTranscodeByKeyWord(scenicPlace.getFirstTopic())){
			scenic.setFirstTopicCodeId(getcomSearchTranscodeByKeyWord(scenicPlace.getFirstTopic()));
		}
		if(null!=getcomSearchTranscodeByKeyWord(scenicPlace.getScenicSecondTopic())){
			scenic.setScenicSecondTopicCodeId(getcomSearchTranscodeByKeyWord(scenicPlace.getScenicSecondTopic()));
		}
		
 		// 景区 图片
		PlacePhoto	placePhoto=new PlacePhoto();
		placePhoto.setPlaceId(scenicPlace.getPlaceId());
		placePhoto.setType(PlacePhotoTypeEnum.LARGE.getCode());
		scenic.setPlacePhoto((List<PlacePhoto>) this.getPlacePhotoListByPlacePhoto(placePhoto));
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("placeId", scenicPlace.getPlaceId());
 		param.put("timeValid", "true");
		//景区活动
		scenic.setPlaceActivity(placeActivityService.queryPlaceActivityListByParam(param));
		
		// 获取当前景点的上级目的地(父级，爷爷级)
		if (scenicPlace.getParentPlaceId() != null) {
			Place fatherPlace = placeService.queryPlaceAndComSearchTranscodeByPlaceId(scenicPlace.getParentPlaceId());
			if (null != fatherPlace) {
				scenic.setFatherPlace(fatherPlace);
				Place grandfatherPlace = placeService.queryPlaceAndComSearchTranscodeByPlaceId(fatherPlace.getParentPlaceId());
				if(null!=grandfatherPlace){
					scenic.setGrandfatherPlace(grandfatherPlace);
				}
			}		
		}
		
		/**
		 * 打折门票
		 */
		//门票类别
		List<ProdBranchSearchInfo> prodBranchSearchInfoAllList = productSearchInfoService.getProdBranchSearchInfoByParam(scenicPlace.getPlaceId(), "1", PlaceUtils.FRONTEND);
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("placeId", scenicPlace.getPlaceId());
		pm.put("isTicket", ProductSearchInfo.IS_TICKET.TICKET.getCode());
		pm.put("channel", PlaceUtils.FRONTEND);
 		//门票产品
		List<ProductSearchInfo> productTicketList = productSearchInfoService.queryProductSearchInfoByParam(pm);
		List<ScenicProductAndBranchListVO> singleList = new ArrayList<ScenicProductAndBranchListVO>();
		List<ScenicProductAndBranchListVO> unionList = new ArrayList<ScenicProductAndBranchListVO>();
		List<ScenicProductAndBranchListVO> suitList = new ArrayList<ScenicProductAndBranchListVO>();
		for(ProductSearchInfo ticket:productTicketList){
			//单门票或者通票
			if (Constant.SUB_PRODUCT_TYPE.SINGLE.name().equalsIgnoreCase(ticket.getSubProductType())
					|| Constant.SUB_PRODUCT_TYPE.WHOLE.name().equalsIgnoreCase(ticket.getSubProductType())) {
				ScenicProductAndBranchListVO vo=new ScenicProductAndBranchListVO();
				vo.setProductSearchInfo(ticket);//产品
				List<ProdBranchSearchInfo> ticketBranchList=new ArrayList<ProdBranchSearchInfo>();
 				for(ProdBranchSearchInfo  prodBranch:prodBranchSearchInfoAllList){
					if(prodBranch.getProductId().equals(vo.getProductSearchInfo().getProductId())){
						ticketBranchList.add(prodBranch);
					}
				}
 				vo.setBranchSearchInfo(ticketBranchList);//类别list
 				singleList.add(vo);
			}
			//联票
			if (Constant.SUB_PRODUCT_TYPE.UNION.name().equalsIgnoreCase(ticket.getSubProductType())) {
				ScenicProductAndBranchListVO vo=new ScenicProductAndBranchListVO();
				vo.setProductSearchInfo(ticket);//产品
				List<ProdBranchSearchInfo> ticketBranchList=new ArrayList<ProdBranchSearchInfo>();
 				for(ProdBranchSearchInfo  prodBranch:prodBranchSearchInfoAllList){
					if(prodBranch.getProductId().equals(vo.getProductSearchInfo().getProductId())){
						ticketBranchList.add(prodBranch);
					}
				}
 				vo.setBranchSearchInfo(ticketBranchList);//类别list
 				unionList.add(vo);
			}
			
			//套票
			if (Constant.SUB_PRODUCT_TYPE.SUIT.name().equalsIgnoreCase(ticket.getSubProductType())) {
				ScenicProductAndBranchListVO vo=new ScenicProductAndBranchListVO();
				vo.setProductSearchInfo(ticket);//产品
				List<ProdBranchSearchInfo> ticketBranchList=new ArrayList<ProdBranchSearchInfo>();
 				for(ProdBranchSearchInfo  prodBranch:prodBranchSearchInfoAllList){
					if(prodBranch.getProductId().equals(vo.getProductSearchInfo().getProductId())){
						ticketBranchList.add(prodBranch);
					}
				}
 				vo.setBranchSearchInfo(ticketBranchList);//类别list
 				suitList.add(vo);
			}
		}
		Map<String, List<ScenicProductAndBranchListVO>> ticketProductList = new HashMap<String, List<ScenicProductAndBranchListVO>>();
		ticketProductList.put("SINGLE", singleList);
		ticketProductList.put("UNION", unionList);
		ticketProductList.put("SUIT", suitList);
		scenic.setTicketProductList(ticketProductList);
		
		
		
		/**
		 * 目的地自由行+酒店套餐
		 */
		List<ProductSearchInfo> freeNessAndHotelSuitProductList=getFreeNessAndHotelSuit(scenicPlace);
		scenic.setFreeNessAndHotelSuitProductList(freeNessAndHotelSuitProductList);
		/**
		 * 短途跟团游+bus
		 */
		List<ProductSearchInfo>  groupAndBusList=getGroupAndBus(scenicPlace);
		List<String> groupAndBusTabNameList=new ArrayList<String>();
		for(ProductSearchInfo p:groupAndBusList){
			if(StringUtils.isNotEmpty(p.getFromDest())&&!groupAndBusTabNameList.contains(p.getFromDest().trim())){
				groupAndBusTabNameList.add(p.getFromDest().trim());//获取几个出发点
			}
		}
		//设定跟团游出发点
		scenic.setGroupAndBusTabNameList(groupAndBusTabNameList);
		//设定跟团游数据根据出发点
		Map<String,List<ProductSearchInfo>> groupAndBusDataMap=new HashMap<String, List<ProductSearchInfo>>();
		for(String tabName:scenic.getGroupAndBusTabNameList()){
			List<ProductSearchInfo> prInfo=new ArrayList<ProductSearchInfo>();
			for(ProductSearchInfo p2:groupAndBusList){
					if(StringUtils.isNotEmpty(tabName)&&StringUtils.isNotEmpty(p2.getFromDest())&&tabName.equals(p2.getFromDest())){
						prInfo.add(p2);
					}
			 }
			if(prInfo!=null&&prInfo.size()>0){
				groupAndBusDataMap.put(tabName, prInfo);
			}
		}
		scenic.setGroupAndBusDataMap(groupAndBusDataMap);
		
		/**
		 * 公告
		 */
		PlaceHotelNotice notice=new PlaceHotelNotice();
		notice.setPlaceId(scenicPlace.getPlaceId());
		notice.setNoticeType(PlaceUtils.SCENIC);
		notice.setValidNotice("true");
		List<PlaceHotelNotice> noticeList= placeHotelNoticeService.queryByHotelNotice(notice);
		if(null!=noticeList&&noticeList.size()>0)scenic.setNoticeList(noticeList);
		
		/**
		 * 获取景区首页（ticket）seoTkd规则
		 *
		 */
 		SeoIndexPage defaultseoIndexPage=seoIndexPageService.getSeoIndexPageByPageCode(SeoIndexPageCodeEnum.CH_DEST_SCENIC.getCode());
		if(null!=defaultseoIndexPage){
			scenic.setSeoPublicContent(defaultseoIndexPage.getSeoContent());
		}
 		PlaceVo placeVo=new PlaceVo();
		placeVo.setPlace(scenicPlace);
		if(null!=scenic.getFatherPlace())placeVo.setParentPlace(scenic.getFatherPlace());
		if(defaultseoIndexPage!=null){
			defaultseoIndexPage.setSeoTitle(SeoUtils.getSeoIndexPageRegular(placeVo, defaultseoIndexPage.getSeoTitle()));
			defaultseoIndexPage.setSeoKeyword(SeoUtils.getSeoIndexPageRegular(placeVo, defaultseoIndexPage.getSeoKeyword()));
			defaultseoIndexPage.setSeoDescription(SeoUtils.getSeoIndexPageRegular(placeVo, defaultseoIndexPage.getSeoDescription()));
		}
		if(StringUtils.isNotEmpty(scenicPlace.getSeoTitle())){
               scenic.setSeoTitle(scenicPlace.getSeoTitle());
		}else{
			 scenic.setSeoTitle(defaultseoIndexPage.getSeoTitle());
		}
		if(StringUtils.isNotEmpty(scenicPlace.getSeoKeyword())){
            scenic.setSeoKeyword(scenicPlace.getSeoKeyword());
		}else{
			 scenic.setSeoKeyword(defaultseoIndexPage.getSeoKeyword());
		}
		if(StringUtils.isNotEmpty(scenicPlace.getSeoDescription())){
            scenic.setSeoDescription(scenicPlace.getSeoDescription());
		}else{
			 scenic.setSeoDescription(defaultseoIndexPage.getSeoDescription());
		}
//		if(StringUtils.isNotEmpty(scenicPlace.getSeoContent())){
//			scenic.setSeoPrivateContent(scenicPlace.getSeoContent());
//		}
		
		//父级的兄弟 给seo相关推荐用
		if (scenic.getGrandfatherPlace().getPlaceId()!= null){
		  List<Place> fatherBrotherList=placeService.getPlaceInfoBySameParentPlaceId(scenic.getGrandfatherPlace().getPlaceId() , Constant.PLACE_STAGE.PLACE_FOR_DEST.getCode(), null);
		  scenic.setFatherBrotherList(fatherBrotherList);
		} 
		
        //点评统计
		scenic.setCmtLatitudeStatisticsList(cmtLatitudeStatistisService.getLatitudeStatisticsList(pm));
		//问答
		scenic.setQaCount(askService.QueryCountAskByPlaceId(scenicPlace.getPlaceId()));
		return scenic;
	}

	/**
	 * 目的地自由行+酒店套餐
	 * @param scenicPlace
	 * @return
	 * @author:nixianjun 2013-7-10
	 */
	@Override
	public List<ProductSearchInfo> getFreeNessAndHotelSuit(Place scenicPlace){
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("placeId", scenicPlace.getPlaceId());
		List<String> list=new ArrayList<String>();
		list.add(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.getCode());
		list.add(Constant.SUB_PRODUCT_TYPE.FREENESS.getCode());
		pm.put("subProductTypes", list);
		pm.put("channel", PlaceUtils.FRONTEND);
 		//门票产品
		return  productSearchInfoService.queryProductSearchInfoByParam(pm);
	}
	/**
	 * 短途跟团游，bus
	 * @param scenicPlace
	 * @return
	 * @author:nixianjun 2013-7-10
	 */
	@Override
	public List<ProductSearchInfo> getGroupAndBus(Place scenicPlace){
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("placeId", scenicPlace.getPlaceId());
		List<String> list=new ArrayList<String>();
		list.add(Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.getCode());
		list.add(Constant.SUB_PRODUCT_TYPE.GROUP.getCode());
		pm.put("subProductTypes", list);
		pm.put("channel", PlaceUtils.FRONTEND);
		pm.put("orderField", "subProductTypeBusFirst");
 		return  productSearchInfoService.queryProductSearchInfoByParam(pm);
	}

	@Override
	public PlaceHotel searchPlaceHotel(Long placeId) {
		return placeHotelDAO.searchPlaceHotel(placeId);
	}
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public void setPlaceHotelNoticeService(
			PlaceHotelNoticeService placeHotelNoticeService) {
		this.placeHotelNoticeService = placeHotelNoticeService;
	}

	public void setSeoIndexPageService(SeoIndexPageService seoIndexPageService) {
		this.seoIndexPageService = seoIndexPageService;
	}

	public void setComSearchTranscodeService(
			ComSearchTranscodeService comSearchTranscodeService) {
		this.comSearchTranscodeService = comSearchTranscodeService;
	}
}
