package com.lvmama.clutter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.clutter.service.IClientSearchService;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.DateUtil;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class ClientSearchServiceImpl extends AppServiceImpl implements
		IClientSearchService {

	private static final Log log = LogFactory
			.getLog(ClientSearchServiceImpl.class);

	public Page<ProductBean> listRoute(Map<String, Object> params) {
		if (isFreness(params)) {
			ArgCheckUtils.validataRequiredArgs("toDest", "page", params);
		} else {
			ArgCheckUtils.validataRequiredArgs("fromDest", "toDest", "page",
					params);
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

		if (params.get("placeId") != null) {
			searchVo.setCityId(params.get("placeId").toString());
		}

		searchVo.setPage(Integer.valueOf(params.get("page").toString()));

		if (params.get("searchType") != null) {
			if (params.get("subProductType") != null
					&& !"".equals(params.get("subProductType"))) {
				String searchType = params.get("searchType").toString();
				if (this.isNotOverSeaTravel(searchType)) {
					if (params.get("fromDest") != null) {
						String fromDest = params.get("fromDest").toString();
						if (fromDest.contains(",")) {
							fromDest = fromDest.substring(0,
									fromDest.lastIndexOf(","));
							params.put("fromDest", fromDest);
						}
					}
				}
			}
		}

		if (params.get("subProductType") != null) {
			if (this.isFreness(params)) {
				params.put("fromDest", null);
			}
			searchVo.setSubProductType(ClientUtils.convetToList(params.get(
					"subProductType").toString()));
		} else {
			searchVo.setSubProductType(new ArrayList<String>());
		}

		if (params.get("fromDest") != null) {
			searchVo.setFromDest(params.get("fromDest").toString());
		}

		if (params.get("toDest") != null) {
			searchVo.setToDest(StringUtil.subStringStrNoSuffix(params.get("toDest").toString(), 60));
		}

		if (params.get("sort") != null) {
			searchVo.setSort(params.get("sort").toString());
		} else {//添加默认seq排序
			searchVo.setSort("seq");
		}

		if (params.get("day") != null) {
			searchVo.setVisitDay(params.get("day").toString());
		}

		if (params.get("keyword") != null) {
			searchVo.setKeyword(StringUtil.subStringStrNoSuffix(params.get("keyword").toString(),60));
		}

		Page<ProductBean> pageConfig = vstClientProductService
				.routeSearch(searchVo);
		return pageConfig;
	}

	public Map<String, Object> routeSearch(Map<String, Object> params) {
		if (isFreness(params)) {
			ArgCheckUtils.validataRequiredArgs("toDest", "page", params);
		} else {
			ArgCheckUtils.validataRequiredArgs("fromDest", "toDest", "page",
					params);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();

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

		if (params.get("searchType") != null) {
			if (params.get("subProductType") != null
					&& !"".equals(params.get("subProductType"))) {
				String searchType = params.get("searchType").toString();
				if (this.isNotOverSeaTravel(searchType)) {
					if (params.get("fromDest") != null) {
						String fromDest = params.get("fromDest").toString();
						if (fromDest.contains(",")) {
							fromDest = fromDest.substring(0,
									fromDest.lastIndexOf(","));
							params.put("fromDest", fromDest);
						}
					}
				}
			}
		}

		if (params.get("subProductType") != null) {
			if (this.isFreness(params)) {
				params.put("fromDest", null);
			}
			searchVo.setSubProductType(ClientUtils.convetToList(params.get(
					"subProductType").toString()));
		} else {
			searchVo.setSubProductType(new ArrayList<String>());
		}

		if (params.get("fromDest") != null) {
			searchVo.setFromDest(params.get("fromDest").toString());
		}

		if (params.get("toDest") != null) {
			searchVo.setToDest(StringUtil.subStringStrNoSuffix(params.get("toDest").toString(), 60));
		}

		if (params.get("sort") != null) {
			searchVo.setSort(params.get("sort").toString());
		} 
		if (params.get("day") != null) {
			searchVo.setVisitDay(params.get("day").toString());
		}

		if (params.get("keyword") != null) {
			searchVo.setKeyword(StringUtil.subStringStrNoSuffix(params.get("keyword").toString(),60));
		}

		Page<ProductBean> pageConfig = vstClientProductService
				.routeSearch(searchVo);
		List<MobileProductTitle> mpList = new ArrayList<MobileProductTitle>();
		Map<String, String> visitMap = new HashMap<String, String>();

		for (ProductBean productBean : pageConfig.getAllItems()) {
			if (!StringUtil.isEmptyString(productBean.getRouteTopic())) {
				String[] topics = productBean.getRouteTopic().split(",");
				for (String string : topics) {
					String str = StringUtils.trimAllWhitespace(string);
					visitMap.put(str, str);
				}
			}

		}

		List<Map<String, Object>> topicList = new ArrayList<Map<String, Object>>();
		Iterator<Entry<String, String>> it = visitMap.entrySet().iterator();

		// subjectList列表
		Map<String, Object> t_m = new HashMap<String, Object>();
		t_m.put("title", "全部主题");
		t_m.put("value", "");
		topicList.add(t_m);
		while (it.hasNext()) {
			Map<String, Object> m = new HashMap<String, Object>();
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			String key = entry.getKey();
			m.put("title", key);
			m.put("value", key);
			topicList.add(m);
		}

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

		resultMap.put("subjects", topicList);
		resultMap.put("datas", mpList);
		resultMap.put("isLastPage", isLastPage(pageConfig));
		return resultMap;
	}

	// || Constant.CLIENT_RECOMMEND_TAG.LDISTANCE_TRAVEL.name().equals(key)
	protected boolean isNotOverSeaTravel(String key) {
		if (Constant.CLIENT_RECOMMEND_TAG.FREE_TOUR.name().equals(key)
				|| Constant.CLIENT_RECOMMEND_TAG.GROUP.name().equals(key)) {
			return true;
		}
		return false;
	}

	public Map<String, Object> routeAutoComplete(Map<String, Object> params) {

		ArgCheckUtils.validataRequiredArgs("keyword", params);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// ClientPlaceSearchVO searchVo = new ClientPlaceSearchVO();
		ClientRouteSearchVO searchVo = new ClientRouteSearchVO();
		// searchVo.setStage(ClientUtils.convetToList("1,2"));

		/**
		 * 补全所有目的地关键字
		 */
		if (params.get("subProductType") != null) {
			if (this.isFreness(params)) {
				params.put("fromDest", null);
			}
			searchVo.setSubProductType(ClientUtils.convetToList(params.get(
					"subProductType").toString()));
		}

		if (params.get("fromDest") != null) {
			searchVo.setFromDest(params.get("fromDest").toString());
		}

		List<String> productTypes = new ArrayList<String>();
		productTypes.add(Constant.PRODUCT_TYPE.ROUTE.name());
		searchVo.setProductType(productTypes);
		searchVo.setChannel(Constant.CHANNEL.CLIENT.name());
		// searchVo.setKeyword(params.get("keyword").toString());
		searchVo.setToDest(StringUtil.subStringStrNoSuffix(params.get("keyword").toString(), 60));
		// List<AutoCompletePlaceObject> list =
		// clientPlaceService.getAutoCompletePlace(searchVo);
		List<AutoCompletePlaceObject> list = vstClientProductService
				.getAutoComplete(searchVo);
		List<Map<String, Object>> keyList = new ArrayList<Map<String, Object>>();
		Map<String, Object> tempMap = new HashMap<String, Object>();

		if (list != null) {
			for (AutoCompletePlaceObject bean : list) {
				tempMap.put(bean.getShortId(), bean);
			}
		}
		Iterator<Entry<String, Object>> it = tempMap.entrySet().iterator();

		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			AutoCompletePlaceObject bean = (AutoCompletePlaceObject) entry
					.getValue();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", bean.getWords());
			map.put("id", bean.getShortId());
			map.put("stage", bean.getStage());
			keyList.add(map);
		}

		resultMap.put("datas", keyList);
		return resultMap;
	}

	public Map<String, Object> placeAutoComplete(Map<String, Object> params) {
		ArgCheckUtils.validataRequiredArgs("keyword", params);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		ClientPlaceSearchVO searchVo = new ClientPlaceSearchVO();

		searchVo.setStage(ClientUtils.convetToList("1,2"));
		List<String> productTypes = new ArrayList<String>();
		productTypes.add(Constant.PRODUCT_TYPE.TICKET.name());
		searchVo.setProductType(productTypes);
		searchVo.setChannel(Constant.CHANNEL.CLIENT.name());
		searchVo.setKeyword(StringUtil.subStringStrNoSuffix(params.get("keyword").toString(), 60));
		List<AutoCompletePlaceObject> list = vstClientPlaceService
				.getAutoCompletePlace(searchVo);
		List<Map<String, Object>> keyList = new ArrayList<Map<String, Object>>();
		if (list != null) {
			for (AutoCompletePlaceObject autoCompletePlaceObject : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", autoCompletePlaceObject.getWords());
				map.put("id", autoCompletePlaceObject.getShortId());
				map.put("stage", autoCompletePlaceObject.getStage());
				keyList.add(map);

			}
		}
		resultMap.put("datas", keyList);
		return resultMap;

	}

	@Override
	public Map<String, Object> placeSearch(Map<String, Object> params) {
		Page<PlaceBean> pageConfig = this.getPlaceSearchList(params);
		List<MobilePlace> mplaceList = new ArrayList<MobilePlace>();

		for (PlaceBean placeBean : pageConfig.getItems()) {
			mplaceList.add(convertToMobilePlace(placeBean));
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("datas", mplaceList);
		resultMap.put("isLastPage", isLastPage(pageConfig));
		return resultMap;

	}

	protected Page<PlaceBean> getPlaceSearchList(Map<String, Object> params) {
		ClientPlaceSearchVO searchVo = new ClientPlaceSearchVO();
		if (params.get("keyword")!=null) {
			ArgCheckUtils.validataRequiredArgs("stage", "page", "keyword",
					params);
		} else {
			ArgCheckUtils.validataRequiredArgs("stage", "page", "windage",
					params);
			searchVo.setLongitude(params.get("longitude").toString());
			searchVo.setLatitude(params.get("latitude").toString());
			searchVo.setWindage(params.get("windage").toString());
		}

		if (params.get("keyword") != null) {
			searchVo.setKeyword(StringUtil.subStringStrNoSuffix(params.get("keyword").toString(), 60));
		}
		if (params.get("page") != null) {
			searchVo.setPage(Integer.parseInt(params.get("page").toString()));
		}

		if (params.get("subject") != null) {
			searchVo.setSubject(params.get("subject").toString());
		}

		if (params.get("sort") != null) {
			searchVo.setSort(params.get("sort").toString());
		}

		if (params.get("pageSize") != null) {
			searchVo.setPageSize(Integer.parseInt(params.get("pageSize")
					.toString()));
		}

		if (params.get("stage") != null) {
			searchVo.setStage(ClientUtils.convetToList(params.get("stage")
					.toString()));
		}
		
		if(params.get("productType")!=null){
			searchVo.setProductType(ClientUtils.convetToList(params.get("productType").toString()));
		} else {//默认有门票
			/**
			 * 必须含有门票
			 */
			searchVo.setProductType(ClientUtils.convetToList("TICKET"));
		}
		
		if(params.get("hasRoute")!=null){
			searchVo.setProductType(ClientUtils.convetToList(Constant.PRODUCT_TYPE.ROUTE.name()));
		}
		// searchVo.setChannel(Constant.CHANNEL.CLIENT.name());

		Page<PlaceBean> pageConfig = vstClientPlaceService.placeSearch(searchVo);
		return pageConfig;
	}

	public Page<PlaceBean> listPlace(Map<String, Object> params) {
		return this.getPlaceSearchList(params);
	}

	public Map<String, Object> shakePlace(Map<String, Object> params) {

		ClientPlaceSearchVO searchVo = new ClientPlaceSearchVO();
		searchVo.setPage(1);
		searchVo.setPageSize(60);
		searchVo.setStage(ClientUtils.convetToList("2"));
		searchVo.setSort("salse");
		searchVo.setProductType(ClientUtils
				.convetToList(Constant.PRODUCT_TYPE.TICKET.name()));

		if (params.get("keyword") != null) {
			searchVo.setKeyword(params.get("keyword").toString());
		} else {
			searchVo.setKeyword("上海");
		}

		Page<PlaceBean> pageConfig = vstClientPlaceService.placeSearch(searchVo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		/*
		 * List<MobilePlace> mplaceList = new ArrayList<MobilePlace>();
		 * 
		 * for (PlaceBean placeBean : pageConfig.getItems()) {
		 * 
		 * mplaceList.add(this.convertToMobilePlace(placeBean)); }
		 * resultMap.put("datas", mplaceList);
		 */
		resultMap.put("isLastPage", isLastPage(pageConfig));

		List<PlaceBean> placeList = pageConfig.getItems();
		MobilePlace pb = getRandomPlaceBean(placeList,
				String.valueOf(params.get("placeId")));
		resultMap.put("datas", pb);
		return resultMap;
	}

	/**
	 * 获取随机数据.
	 * 
	 * @param placeList
	 * @param placeId
	 * @return
	 */
	public MobilePlace getRandomPlaceBean(List<PlaceBean> placeList,
			String placeId) {
		PlaceBean pb = null;
		if (null != placeList && placeList.size() > 0) {
			int size = placeList.size();
			int index = getRandom(size);
			pb = placeList.get(index);
			if (null != placeId && !"null".equals(placeId)) {
				if (pb.getId().equals(placeId)) { // 如果上次已经摇到.
					placeList.remove(pb);
					return getRandomPlaceBean(placeList, placeId);
				} else {
					return this.convertToMobilePlace(pb);
				}
			}
		}
		return this.convertToMobilePlace(pb);
	}

	/**
	 * 获取随机数.
	 * 
	 * @param size
	 * @return
	 */
	public int getRandom(int size) {
		Random random = new Random();
		return random.nextInt(size);
	}

	protected MobilePlace convertToMobilePlace(PlaceBean placeBean) {
		MobilePlace mp = new MobilePlace();

		mp.setCanOrderToday(placeBean.canOrderTodayCurrentTimeForPlace());
		
		mp.setOrderTodayAble(placeBean.getTodayOrderAble());//新今日可订

		mp.setId(Long.valueOf(placeBean.getId()));
		mp.setAddress(placeBean.getAddress());
		mp.setMarketPriceYuan(Float.valueOf(placeBean.getMarketPrice()));
		if (!StringUtil.isEmptyString(placeBean.getSellPrice())) {
			mp.setSellPriceYuan((PriceUtil.convertToYuan(Long.valueOf(placeBean
					.getSellPrice()))));
		}
		// mp.setJuli(String.valueOf(placeBean.getBoost()));
		mp.setFreenessNum(placeBean.getFreenessNum());
		mp.setRouteNum(placeBean.getRouteNum());
		mp.setName(placeBean.getName());
		mp.setBaiduLatitude(Double.valueOf(placeBean.getLatitude()));
		mp.setBaiduLongitude(Double.valueOf(placeBean.getLongitude()));
		mp.setMiddleImage(placeBean.getMiddleImage());
		mp.setCmtStarts(placeBean.getAvgScore() + "");
		/*********** V3.1 **********/
		// 主题类型
		// mp.setSubject(placeBean.getPlaceMainTitel());
		// 返现金额 (是分 还是元)
		// mp.setMaxCashRefund(StringUtil.isEmptyString(placeBean.getCashRefund())?0l:PriceUtil.convertToFen(placeBean.getCashRefund()));
		// 优惠 - 景点不显示惠
		// mp.setHasBusinessCoupon(ClientUtils.hasBusinessCoupon(placeBean.getTagList()));
		mp.setWeiXinActivity("Y".equals(placeBean.getShareweixin()));
		return mp;
	}

	@Override
	public String productSearch(Map<String, Object> params) {
		// TODO Auto-generated method stub
		ArgCheckUtils.validataRequiredArgs("productType", params);
		ClientRouteSearchVO searchVo = new ClientRouteSearchVO();
		String[] typs = params.get("productType").toString().split(",");
		List<String> productTypes = new ArrayList<String>();
		for (String string : typs) {
			productTypes.add(string);
		}
		searchVo.setProductType(productTypes);
		searchVo.setChannel(Constant.CHANNEL.CLIENT.name());
		vstClientProductService.routeSearch(searchVo);
		return null;
	}

	@Override
	public List<Object> getCities(Map<String, Object> params) {
		String d = ClutterConstant.getProperty("cities");
		List<Object> list = new ArrayList<Object>();
		JSONArray j = JSONArray.fromObject(d);
		for (int i = 0; i < j.size(); i++) {
			Object jo = j.get(i);
			list.add(jo);

		}
		return list;
	}

}
