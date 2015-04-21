package com.lvmama.clutter.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;

import com.lvmama.clutter.model.MobileDest;
import com.lvmama.clutter.service.IClientOfflineCacheService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.TreeBean;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.search.VstClientPlaceService;

public class ClientOfflineCacheServiceImpl extends AppServiceImpl implements IClientOfflineCacheService {

	private VstClientPlaceService vstClientPlaceService;
	
	private String MOBILE_OFFLINE_CACHE="MOBILE_OFFLINE_CACHE";

	@Override
	public Map<String,Object> getPlaceSubjectCache(Map<String,Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

	@Override
	public Map<String,Object> getPlaceCitiesCache(Map<String,Object> param) {
		
		String method = param.get("method").toString();
		Object obj = MemcachedUtil.getInstance().get(MOBILE_OFFLINE_CACHE+method);
		if(obj==null){
			ClientPlaceSearchVO cpvo = new ClientPlaceSearchVO();
			List<String> productTypes = new ArrayList<String>();
			productTypes.add("TICKET");
			List<String> stages = new ArrayList<String>();
			stages.add("1");
			cpvo.setStage(stages);
			cpvo.setProductType(productTypes);
			if(param.get("keyword")!=null){
				cpvo.setKeyword(param.get("keyword").toString());
			}
			Map<String,Object> resultMap = getCities(cpvo,param);
			ClientPlaceSearchVO allSearchVoSubject = new ClientPlaceSearchVO();
			List<AutoCompletePlaceObject> allAcpoList = vstClientPlaceService.getClientSubjectByCity(allSearchVoSubject);
			List<String> allSubjects = new ArrayList<String>();
			if (allAcpoList!=null) {
				for (AutoCompletePlaceObject autoCompletePlaceObject : allAcpoList) {
					if("0".equals(autoCompletePlaceObject.getWords())){
						continue;
					}
					allSubjects.add(autoCompletePlaceObject.getWords());
				}	
			}
			resultMap.put("allSubject", allSubjects);
			
			
			JSONObject json = JSONObject.fromObject(resultMap);
			String version;
			try {
				version = MD5.encode(json.toString());
				resultMap.put("version", version);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MemcachedUtil.getInstance().set(MOBILE_OFFLINE_CACHE+method, 60*60*24, resultMap);
			return resultMap;
		} else {
			Map<String,Object> result = (Map<String,Object>)obj;
			if(this.isCheckVersion(param)){
				return this.getVersion(result);
			} 
			return result;
		}

	}
	
	
	
	public Map<String,Object> getTicketCities(Map<String,Object> param) {
		
		String method = param.get("method").toString();
		Object obj = MemcachedUtil.getInstance().get(MOBILE_OFFLINE_CACHE+method);
		if(obj==null){
			ClientPlaceSearchVO cpvo = new ClientPlaceSearchVO();
			List<String> productTypes = new ArrayList<String>();
			productTypes.add("TICKET");
			List<String> stages = new ArrayList<String>();
			stages.add("1");
			cpvo.setStage(stages);
			cpvo.setProductType(productTypes);
			if(param.get("keyword")!=null){
				cpvo.setKeyword(param.get("keyword").toString());
			}
			Map<String,Object> resultMap = getCities(cpvo,param);
		
			JSONObject json = JSONObject.fromObject(resultMap);
			String version;
			try {
				version = MD5.encode(json.toString());
				resultMap.put("version", version);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MemcachedUtil.getInstance().set(MOBILE_OFFLINE_CACHE+method, 60*60*24, resultMap);
			return resultMap;
		} else {
			Map<String,Object> result = (Map<String,Object>)obj;
			if(this.isCheckVersion(param)){
				return this.getVersion(result);
			} 
			return result;
		}

	}
	
	public Map<String,Object> getRouteFilterCache(Map<String,Object> param){
		List<Map<String,Object>> subProductTypefilterMap = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		Map<String,Object> allSubProductTypesMap = new HashMap<String,Object>();
		allSubProductTypesMap.put("title", "全部类型");
		allSubProductTypesMap.put("value", "");
		allSubProductTypesMap.put("type", "");
		subProductTypefilterMap.add(allSubProductTypesMap);
		
		Map<String,Object> freenessSubProductTypesMap = new HashMap<String,Object>();
		freenessSubProductTypesMap.put("title", "自由行(景+酒)");
		freenessSubProductTypesMap.put("value", "FREENESS");
		freenessSubProductTypesMap.put("type", "FREE_TOUR");
		subProductTypefilterMap.add(freenessSubProductTypesMap);
		
		Map<String,Object> freenessForeignSubProductTypesMap = new HashMap<String,Object>();
		freenessForeignSubProductTypesMap.put("title", "自由行(机+酒)");
		freenessForeignSubProductTypesMap.put("value", "FREENESS_FOREIGN,FREENESS_LONG");
		freenessForeignSubProductTypesMap.put("type", "");
		subProductTypefilterMap.add(freenessForeignSubProductTypesMap);
		
		Map<String,Object> groupSubProductTypesMap = new HashMap<String,Object>();
		groupSubProductTypesMap.put("title", "跟团游");
		groupSubProductTypesMap.put("value", "GROUP_LONG,GROUP_FOREIGN,GROUP,SELFHELP_BUS");
		groupSubProductTypesMap.put("type", "GROUP");
		subProductTypefilterMap.add(groupSubProductTypesMap);
		
	
		List<Map<String,Object>> sortListMap = new ArrayList<Map<String,Object>>();
		
		
		Map<String,Object> sortSeqMap = new HashMap<String,Object>();
		sortSeqMap.put("title", "默认");
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
		
		resultMap.put("routeTypes", subProductTypefilterMap);
		resultMap.put("subjects", new String[]{"全部主题"});
		resultMap.put("sortTypes", sortListMap);
		JSONObject json = JSONObject.fromObject(resultMap);
		try {
		String version = MD5.encode(json.toString());
		resultMap.put("version", version);
		if(param.get("checkVersion")!=null){
			Map<String,Object> versionMap  = new HashMap<String,Object>();
			versionMap.put("version", version);
			return versionMap;
		} 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return resultMap;
		
	}
	
	@Override
	public Map<String,Object> getGroupCitiesCache(Map<String,Object> param) {
		String key = MOBILE_OFFLINE_CACHE+param.get("method").toString();
		Object obj = MemcachedUtil.getInstance().get(key);
		if (obj==null){
			ClientRouteSearchVO searchVo = new ClientRouteSearchVO();
			searchVo.setChannel(Constant.CHANNEL.TUANGOU.name());
			List<String> productTypes = new ArrayList<String>();
			productTypes.add(Constant.PRODUCT_TYPE.TICKET.name());
			productTypes.add(Constant.PRODUCT_TYPE.ROUTE.name());
			searchVo.setProductType(productTypes);
			searchVo.setPage(1);
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			List<String> tempList = new ArrayList<String>();
			Map<String,Object> resultMap = new HashMap<String,Object>();
			Page<ProductBean> pageConfig = vstClientProductService.newRouteSearch(searchVo);
			Map<String,Object> destAllMap  = new HashMap<String,Object>();
			
			destAllMap.put("placeId", 0L);
			destAllMap.put("name", "全部目的地");
			list.add(destAllMap);
			
			int i=10;
			for (ProductBean bean : pageConfig.getAllItems()) {
				Place place = prodProductPlaceService.getToDestByProductId(bean.getProductId());
			
				if(place==null||place.getCity()==null){
					continue;
				}
				//System.out.println(place.getCity()+"|"+bean.getProductName()+"|"+bean.getProductId());
		
				if(!tempList.contains(place.getCity())){
					if(!StringUtil.isEmptyString(bean.getToDest())){
						Map<String,Object> map = new HashMap<String,Object>();
							i++;
						String cityName = "";
						map.put("placeId", i);
						map.put("name",place.getCity());
						cityName = place.getCity();
						list.add(map);
						tempList.add(cityName);
					}
				}
			}
			resultMap.put("cities", list);
		
			
			List<Map<String,Object>> productFilters = new ArrayList<Map<String,Object>>();
			
			
			Map<String,Object> allMap = new HashMap<String,Object>();
			allMap.put("title", "全部类型");
			allMap.put("value", "");
			productFilters.add(allMap);
			
			Map<String,Object> ticketMap = new HashMap<String,Object>();
			ticketMap.put("title", "门票");
			ticketMap.put("value", Constant.PRODUCT_TYPE.TICKET.name());
			productFilters.add(ticketMap);
			
			Map<String,Object> routeMap = new HashMap<String,Object>();
			routeMap.put("title", "线路");
			routeMap.put("value", Constant.PRODUCT_TYPE.ROUTE.name());
			productFilters.add(routeMap);
			
			
			List<Map<String,Object>> sortFilters = new ArrayList<Map<String,Object>>();
			
			Map<String,Object> sortOrderCountMap = new HashMap<String,Object>();
			sortOrderCountMap.put("title", "热门");
			sortOrderCountMap.put("value", "orderCount");
			sortFilters.add(sortOrderCountMap);
			
			
			Map<String,Object> sortPriceUpMap = new HashMap<String,Object>();
			sortPriceUpMap.put("title", "价格高");
			sortPriceUpMap.put("value", "priceDesc");
			sortFilters.add(sortPriceUpMap);
			
			Map<String,Object> sortPriceDownMap = new HashMap<String,Object>();
			sortPriceDownMap.put("title", "价格低");
			sortPriceDownMap.put("value", "priceAsc");
			sortFilters.add(sortPriceDownMap);
			
			resultMap.put("sortTypes", sortFilters);
			
			resultMap.put("productTypes", productFilters);
			JSONObject json = JSONObject.fromObject(resultMap);
			try {
			String version = MD5.encode(json.toString());
			resultMap.put("version", version);
			if(this.isCheckVersion(param)){
				return this.getVersion(resultMap);
			} 
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			MemcachedUtil.getInstance().set(key, 60*60*24*3, resultMap);
			return resultMap;
		} else {
			Map<String,Object> result = (Map<String,Object>)obj;
			if(this.isCheckVersion(param)){
				return this.getVersion(result);
			} 
			return result;
		}
		
		
	}
	
	
	

	
	private Map<String,Object> getCities(ClientPlaceSearchVO cpvo,Map<String,Object> param){
		String method = param.get("method").toString();
		Object obj = MemcachedUtil.getInstance().get(MOBILE_OFFLINE_CACHE+method);
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("hasTicket", "0");
		Map<Long,List<String>> resultSubjects = vstClientPlaceService.getCitiesSubjects(queryMap);
		if(resultSubjects==null){
			resultSubjects = new HashMap<Long,List<String>>();
		}
		TreeBean<PlaceBean> treeBean =  vstClientPlaceService.getChinaTreeByHasProduct(cpvo);
		List<MobileDest> mdList = new ArrayList<MobileDest>();
		
			for (TreeBean<PlaceBean> provinceBean : treeBean.getSubNode()) {
				MobileDest  mdProvince = new MobileDest();
				
				
				PlaceBean pb = (PlaceBean) provinceBean.getNode();
				mdProvince.setId(pb.getId());
				mdProvince.setName(pb.getName());
				mdProvince.setPinyin(pb.getPinYin());
				List<TreeBean> citylist = provinceBean.getSubNode();
				
				List<MobileDest> mdCities= new ArrayList<MobileDest>();
				if(citylist!=null&&citylist.size()!=0){
					for (TreeBean cityBean  : citylist) {
						if(cityBean!=null){
							PlaceBean pb2 = (PlaceBean) cityBean.getNode();
							if(pb2!=null) {
								MobileDest  mdCity = new MobileDest();
								mdCity.setId(pb2.getId());
								mdCity.setName(pb2.getName());
								mdCity.setPinyin(pb2.getPinYin());
								mdCity.setSubjects(resultSubjects.get(Long.valueOf(pb2.getId())));
								mdCities.add(mdCity);
							}
						}
					}
				} else {
					MobileDest  mdCity = new MobileDest();
					BeanUtils.copyProperties(mdProvince, mdCity);
				
					mdCity.setSubjects(resultSubjects.get(Long.valueOf(pb.getId())));
					mdCities.add(mdCity);
				}
				
				mdProvince.setCities(mdCities);
				mdList.add(mdProvince);
				
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("province", mdList);
			super.setSortDatas(resultMap);
			

			return resultMap;
	
		
		
		
	}




	public void setVstClientPlaceService(VstClientPlaceService vstClientPlaceService) {
		this.vstClientPlaceService = vstClientPlaceService;
	}


}
