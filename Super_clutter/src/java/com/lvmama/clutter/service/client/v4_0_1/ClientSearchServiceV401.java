package com.lvmama.clutter.service.client.v4_0_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.springframework.util.StringUtils;

import com.lvmama.clutter.model.MobileDest;
import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.clutter.model.MobileTree;
import com.lvmama.clutter.service.client.v4_0.ClientSearchServiceV40;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.MobileSearchUtils;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;
import com.tenpay.api.common.util.CommonUtil;
public class ClientSearchServiceV401 extends ClientSearchServiceV40 {

	public static String FREE_TOUR =SUB_PRODUCT_TYPE.FREENESS.getCode();
	public static String FREENESS_LONG = "FREENESS_FOREIGN,FREENESS_LONG";
	public static String GROUP ="GROUP_LONG,GROUP_FOREIGN,GROUP,SELFHELP_BUS";
	public static String GROUP_ARROUND ="GROUP,SELFHELP_BUS";
	@Override
	public Map<String, Object> placeSearch(Map<String, Object> params) {
		
			return super.placeSearch(params);
	}

	@Override
	public Page<ProductBean> listRoute(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.listRoute(params);
	}


	
	@Override
	public Map<String, Object> routeSearch(Map<String, Object> params) {
		if (isFreness(params)) {
			ArgCheckUtils.validataRequiredArgs("toDest", "page", params);
		}  else {
			ArgCheckUtils.validataRequiredArgs("fromDest", "toDest", "page",
					params);
		}
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		String fromDest = "";
		String toDest = "";
		String subProductType = "";
		try {
			fromDest = getT(params, "fromDest", String.class, false);
			toDest = getT(params, "toDest", String.class, false);
			subProductType =  getT(params, "subProductType", String.class, false);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		
		ClientRouteSearchVO searchVo = new ClientRouteSearchVO();
		searchVo.setChannel(Constant.CHANNEL.CLIENT.name());
		List<String> productTypes = new ArrayList<String>();
		productTypes.add(Constant.PRODUCT_TYPE.ROUTE.name());
		searchVo.setProductType(productTypes);

		if (params.get("pageSize") != null) {
			searchVo.setPageSize(Integer.parseInt(params.get("pageSize")
					.toString()));
		}

		if (params.get("subject") != null) {
			searchVo.setSubject(params.get("subject").toString());
		}
		
		if (params.get("palceId") != null) {
			searchVo.setPlaceId(params.get("palceId").toString());
		}

		searchVo.setPage(Integer.valueOf(params.get("page").toString()));

		searchVo.setFromDest(fromDest);
		searchVo.setToDest(toDest);

		if (StringUtil.isEmptyString(toDest)) {
			if(searchVo.getToDest().contains("市")) {
				searchVo.setToDest(searchVo.getToDest().replaceAll("市", ""));
			}
//			searchVo.setKeyword(searchVo.getToDest());
//			searchVo.setToDest(null);
		}
		
		
		if (subProductType!=null&&subProductType.equals(GROUP_ARROUND)) {
				searchVo.setFromDest(toDest);
				searchVo.setToDest(null);
		}
		

		if (params.get("sort") != null) {
			searchVo.setSort(params.get("sort").toString());
		} 
		if (params.get("day") != null) {
			searchVo.setVisitDay(params.get("day").toString());
		}

		if (params.get("keyword") != null) {
			searchVo.setKeyword(params.get("keyword").toString());
		}
		
		
		if (params.get("visitDay") != null) {
			String visitDay = params.get("visitDay").toString();
			if(visitDay.contains("天")){
				visitDay  = visitDay.replace("天", "");
			}
			searchVo.setVisitDay(visitDay);
		}
		
		if (params.get("traffic") != null) {
			searchVo.setTraffic(params.get("traffic").toString());
		}
		
		if (params.get("playLine") != null) {
			searchVo.setPlayLine(params.get("playLine").toString());
		}
		
		if (params.get("playFeature") != null) {
			searchVo.setPlayFeature(params.get("playFeature").toString());
		}
		
		if (params.get("hotelType") != null) {
			searchVo.setHotelType(params.get("hotelType").toString());
		}
		
		if (params.get("hotelLocation") != null) {
			searchVo.setHotelLocation(params.get("hotelLocation").toString());
		}
		
		if (params.get("playBrand") != null) {
			searchVo.setPlayBrand(params.get("playBrand").toString());
		}
		
		if (params.get("playNum") != null) {
			searchVo.setPlayNum(params.get("playNum").toString());
		}
		
		if (params.get("scenicPlace") != null) {
			searchVo.setScenicPlace(params.get("scenicPlace").toString());
		}
		
		if (params.get("landTraffic") != null) {
			searchVo.setLandTraffic(params.get("landTraffic").toString());
		}
		
		if (params.get("landFeature") != null) {
			searchVo.setLandFeature(params.get("landFeature").toString());
		}
		
		if (params.get("city") != null) {
			searchVo.setCity(params.get("city").toString());
		}
	
		Page<ProductBean> pageConfig = vstClientProductService
				.newRouteSearch(searchVo);


		
		/**
		 * 根据产品类型构建数据
		 */
		if(params.get("subProductType")!=null){
			
			List<ProductBean> pbList = this.searchBySubProductTypes(params.get("subProductType").toString(), pageConfig.getAllItems());
			pageConfig = new Page<ProductBean>(pbList.size(), searchVo.getPageSize(), searchVo.getPage());
			pageConfig.setAllItems(pbList);
			
		}
		
		this.getTypeCount(pageConfig.getAllItems(), toDest, resultMap);
		
		/**
		 * 构建筛选数据
		 */
		MobileSearchUtils.initFilterParam2Request_ROUTE_forClient(toDest, resultMap, pageConfig.getAllItems());
		
		List<MobileProductTitle> mpList = new ArrayList<MobileProductTitle>();

		for (ProductBean productBean : pageConfig.getItems()) {
			// 过滤掉超级自由行
			if ("true".equals(productBean.getSelfPack())) {
				continue;
			}
			MobileProductTitle mp = new MobileProductTitle();
			mp.setProductName(productBean.getProductName());
			mp.setMarketPriceYuan(productBean.getMarketPrice());
			mp.setSmallImage(productBean.getSmallImage());
			mp.setSellPriceYuan(productBean.getSellPrice());
			mp.setProductId(productBean.getProductId());
			mp.setClientOnly(Constant.CHANNEL.CLIENT.name().equals(
					productBean.getProductChannel()));
			// 返现金额 分
			mp.setMaxCashRefund(null == productBean.getCashRefund() ? 0l
					: PriceUtil.convertToFen(productBean.getCashRefund()));
			mp.setCmtNum(productBean.getCmtNum()); // 点评数
			mp.setSubProductType(productBean.getSubProductType());// 主题类型
			mp.setVisitDay(productBean.getVisitDay() + ""); // 天数
			try {
				ProductSearchInfo psi = this.productSearchInfoService
						.queryProductSearchInfoByProductId(productBean
								.getProductId());
				mp.setHasBusinessCoupon(ClientUtils.hasBusinessCoupon(psi)); // 优惠
			} catch (Exception e) {
				e.printStackTrace();
			}

			mpList.add(mp);
		}
		resultMap.put("datas", mpList);
		resultMap.put("isLastPage", isLastPage(pageConfig));
		resultMap.put("sorts", this.getRouteSortType());
		
		return resultMap;
	}

	protected List<Map<String,Object>> getRouteSortType(){
		List<Map<String,Object>> sortListMap = new ArrayList<Map<String,Object>>();

		Map<String,Object> sortSeqMap = new HashMap<String,Object>();
		sortSeqMap.put("title", "驴妈妈推荐");
		sortSeqMap.put("value", "seq");
		sortListMap.add(sortSeqMap);
		
		Map<String,Object> sortPriceUpMap = new HashMap<String,Object>();
		sortPriceUpMap.put("title", "价格从高到低");
		sortPriceUpMap.put("value", "priceDesc");
		sortListMap.add(sortPriceUpMap);
		
		Map<String,Object> sortPriceDownMap = new HashMap<String,Object>();
		sortPriceDownMap.put("title", "价格从低到高");
		sortPriceDownMap.put("value", "priceAsc");
		sortListMap.add(sortPriceDownMap);

		return sortListMap;
	}
	
	
	protected List<ProductBean> searchBySubProductTypes(String subProductTypes,List<ProductBean> searchList){
		List<ProductBean> list = new ArrayList<ProductBean>();
		Iterator<ProductBean> it = searchList.iterator();
		String tempSubProductTypes = subProductTypes;
		while(it.hasNext()){
			ProductBean pb = it.next();
			String tempsubProductType = pb.getSubProductType();
			if(Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(tempsubProductType)){
				tempsubProductType="mddzyx";
			}
			if(Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(tempSubProductTypes)){
				tempSubProductTypes="mddzyx";
			}
			if(tempSubProductTypes.contains(tempsubProductType)){
				list.add(pb);
			}
		}
		return list;
	}

	
	
	protected void getTypeCount(List<ProductBean> list,String toDest,Map<String,Object> resultMap){
		List<Map<String,Object>> subProductTypefilterMap = new ArrayList<Map<String,Object>>();
		
	
			Map<String,Object> allSubProductTypesMap = new HashMap<String,Object>();
			allSubProductTypesMap.put("title", "全部类型");
			allSubProductTypesMap.put("value", "");
			allSubProductTypesMap.put("type", "");
			allSubProductTypesMap.put("size", "");
			subProductTypefilterMap.add(allSubProductTypesMap);

			Map<String,Object> freenessSubProductTypesMap = new HashMap<String,Object>();
			freenessSubProductTypesMap.put("title", "自由行(景+酒)");
			freenessSubProductTypesMap.put("value", SUB_PRODUCT_TYPE.FREENESS.getCode());
			freenessSubProductTypesMap.put("type", "FREE_TOUR");
			subProductTypefilterMap.add(freenessSubProductTypesMap);
	
			Map<String,Object> freenessForeignSubProductTypesMap = new HashMap<String,Object>();
			freenessForeignSubProductTypesMap.put("title", "自由行(机+酒)");
			freenessForeignSubProductTypesMap.put("value", "FREENESS_FOREIGN,FREENESS_LONG");
			freenessForeignSubProductTypesMap.put("type", "FREENESS_LONG");
			subProductTypefilterMap.add(freenessForeignSubProductTypesMap);
	
			Map<String,Object> groupSubProductTypesMap = new HashMap<String,Object>();
			groupSubProductTypesMap.put("title", "跟团游");
			groupSubProductTypesMap.put("value", "GROUP_LONG,GROUP_FOREIGN,GROUP,SELFHELP_BUS");
			groupSubProductTypesMap.put("type", "GROUP");
			subProductTypefilterMap.add(groupSubProductTypesMap);
			/**
			 * 搜索当地跟团游
			 */
			if(StringUtil.isNotEmptyString(toDest)){
				ClientRouteSearchVO searchVo = new ClientRouteSearchVO();
				searchVo.setChannel(Constant.CHANNEL.CLIENT.name());
				searchVo.setFromDest(toDest);
				searchVo.setProductType(ClientUtils.convetToList(Constant.PRODUCT_TYPE.ROUTE.name()));
				searchVo.setSubProductType(ClientUtils.convetToList(GROUP_ARROUND));
				Page<ProductBean> pageConfig = vstClientProductService
						.newRouteSearch(searchVo);
				
			if(pageConfig.getAllItems().size()>0){
				Map<String,Object> arroundGroupSubProductTypesMap = new HashMap<String,Object>();
				arroundGroupSubProductTypesMap.put("title", toDest+"当地跟团游");
				arroundGroupSubProductTypesMap.put("value", "GROUP,SELFHELP_BUS");
				arroundGroupSubProductTypesMap.put("type", "GROUP_ARROUND");
				subProductTypefilterMap.add(arroundGroupSubProductTypesMap);
			}
			}
			
		resultMap.put("filterSubProductTypeDatas", subProductTypefilterMap);
		
	}

	@Override
	public Map<String, Object> routeAutoComplete(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.routeAutoComplete(params);
	}

	@Override
	public Map<String, Object> placeAutoComplete(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.placeAutoComplete(params);
	}
	
	@Override
	public Page<PlaceBean> listPlace(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.listPlace(params);
	}

	@Override
	public Map<String, Object> shakePlace(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.shakePlace(params);
	}

	@Override
	public String productSearch(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.productSearch(params);
	}

	@Override
	public List<Object> getCities(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getCities(params);
	}
  
}
