package com.lvmama.clutter.service.client.v5_0;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.clutter.service.client.v4_0_1.ClientSearchServiceV401;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.MobileSearchUtils;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class ClientSearchServiceV50 extends ClientSearchServiceV401 {

	/**
	 * 线路综合搜索 
	 */
	@Override
	public Map<String, Object> routeSearch(Map<String, Object> params) {
		if(super.isSearchIds(params)){
			params.put("fromDest", null);
		} else if(super.isSearchKeyword(params)){
			ArgCheckUtils.validataRequiredArgs("fromDest","keyword", "page", params);
		} else if (isFreness(params)) {
			ArgCheckUtils.validataRequiredArgs("toDest", "page", params);
		} else {
			ArgCheckUtils.validataRequiredArgs("fromDest", "toDest", "page",
					params);
		}
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		String toDest = "";
		String keyword = "";
		String subProductType  = "";
		try {
			toDest = getT(params, "toDest", String.class, false);
			keyword = getT(params, "keyword", String.class, false);
			subProductType = getT(params, "subProductType", String.class, false);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		ClientRouteSearchVO searchVo = this.initSearchInfo(params);
		/***
		 * v5.0修改 sb的需求
		 */
		if("邮轮".equals(toDest)){
			searchVo.setFromDest("上海");
		}
		
		
		if("邮轮".equals(keyword)){
			searchVo.setToDest(keyword);
			searchVo.setFromDest("上海");
			searchVo.setKeyword(null);
		}
		Page<ProductBean> pageConfig = vstClientProductService.newRouteSearch(searchVo);

		
		/**
		 * 根据产品类型构建数据
		 */
		if(StringUtil.isNotEmptyString(subProductType)){
			List<ProductBean> pbList = super.searchBySubProductTypes(params.get("subProductType").toString(), pageConfig.getAllItems());
			pageConfig = new Page<ProductBean>(pbList.size(), searchVo.getPageSize(), searchVo.getPage());
			pageConfig.setAllItems(pbList);
			
		}
		
		super.getTypeCount(pageConfig.getAllItems(), toDest, resultMap);
		
		/**
		 * 构建筛选数据
		 */
		MobileSearchUtils.initFilterParam2Request_ROUTE_forClient(toDest, resultMap, pageConfig.getAllItems());
		
		// 结构数据 封装
		List<MobileProductTitle> mpList = this.inintMobileProductTitle(pageConfig);

		resultMap.put("datas", mpList);
		resultMap.put("isLastPage", isLastPage(pageConfig));
		resultMap.put("sorts", this.getRouteSortType());
		
		return resultMap;
	}
	
	
	@Override
	public Map<String, Object> placeSearch(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
		return super.placeSearch(params);
	}
	
	/**
	 * 初始化查询参数  
	 * @param params
	 * @return
	 */
	protected ClientRouteSearchVO initSearchInfo(Map<String,Object> params) {
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
		
		if (params.get("placeId") != null) {
			searchVo.setCityId(params.get("placeId").toString());
			
		}

		searchVo.setPage(Integer.valueOf(params.get("page").toString()));

		searchVo.setFromDest(fromDest);
		searchVo.setToDest(toDest);

		if (StringUtil.isEmptyString(toDest)) {
			if(searchVo.getToDest()!=null && searchVo.getToDest().contains("市")) {
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
		
	
		if(params.get("ids")!=null){
			StringTokenizer st = new StringTokenizer(params.get("ids").toString(), ",");
			List<Long> productIds = new ArrayList<Long>();
			while (st.hasMoreTokens()) {
				String productId = st.nextToken();
				productIds.add(Long.valueOf(productId));
			}
				
			searchVo.setProductIds(productIds);
		}
		
		return searchVo;
	}

	/**
	 * 初始化产品列表信息 
	 * @param pageConfig
	 * @return
	 */
	public List<MobileProductTitle> inintMobileProductTitle(Page<ProductBean> pageConfig) {
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
				ProductSearchInfo psi = this.productSearchInfoService.queryProductSearchInfoByProductId(productBean.getProductId());
				// 初始化优惠，抵扣等信息 
				ClientUtils.initMobileProductTitle4V50(psi,mp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mpList.add(mp);
		}
		
		return mpList;
	}

}
