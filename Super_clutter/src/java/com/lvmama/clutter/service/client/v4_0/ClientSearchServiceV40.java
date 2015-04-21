package com.lvmama.clutter.service.client.v4_0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import com.lvmama.clutter.model.MobileDest;
import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.clutter.model.MobileTree;
import com.lvmama.clutter.service.client.v3_1.ClientSearchSeviceForV31;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.StringUtil;
public class ClientSearchServiceV40 extends ClientSearchSeviceForV31 {

	
	@Override
	public Map<String, Object> placeSearch(Map<String, Object> params) {
		
		Page<PlaceBean> pageConfig = this.getPlaceSearchList(params);
		List<MobilePlace> mplaceList = new ArrayList<MobilePlace>();

		for (PlaceBean placeBean : pageConfig.getItems()){
			mplaceList.add(convertToMobilePlace(placeBean));
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		
		
		resultMap.put("datas", mplaceList);
		resultMap.put("isLastPage", isLastPage(pageConfig));
		resultMap.put("cities", this.groupCities(pageConfig));
		resultMap.put("subjects", this.initSubjects(pageConfig));
		if(String.valueOf(params.get("page")).equals("1")){
			super.setSortDatas(resultMap);
		}
		
		return resultMap;
	}
	
	
	private List<String> initSubjects(Page<PlaceBean> pageConfig){
		Map<String,Integer> subjects = new LinkedHashMap<String, Integer>();
		for (PlaceBean bean : pageConfig.getAllItems()) {
			String DestSubject = bean.getDestSubjects();
			if (DestSubject != null && !"".equals(DestSubject)) {
				StringTokenizer st = new StringTokenizer(DestSubject, ",");
				while (st.hasMoreTokens()) {
					String subject1 = st.nextToken();
					if (subjects.get(subject1)==null) {
						subjects.put(subject1, 1);
					} else {
						Integer sum = subjects.get(subject1)+1;
						subjects.put(subject1, sum);
					}
				}
			}
				
		}
	     
		List<String> subjectsList = new ArrayList<String>();
		Iterator<Entry<String,Integer>>  it = subjects.entrySet().iterator();
		while (it.hasNext()){
			Entry<String,Integer> entry = it.next();
			subjectsList.add(entry.getKey());
		}
		return subjectsList;
	}
	
	private List<MobileTree> groupCities(Page<PlaceBean> pageConfig){
		Map<String,MobileDest> param = super.initCityTree();
		//System.out.println(JSONObject.fromObject(param).toString());
		//System.out.println(JSONObject.fromObject(param).toString());
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		for (PlaceBean bean : pageConfig.getAllItems()) {
			//Place p = placeService.queryPlaceByPlaceId(Long.valueOf(bean.getCityId()));
			map.put(bean.getCityId(), bean.getCity());
			//System.out.println("cityId:"+bean.getCityId()+" name:"+bean.getCity());
				
		}
		List<MobileTree> resultList = new ArrayList<MobileTree>();
		Iterator<Entry<String, Object>> it =  map.entrySet().iterator();
		
		Map<String,MobileTree> citiesgrop = new LinkedHashMap<String, MobileTree>();
		while(it.hasNext()){
			Entry<String, Object> e = it.next();
			String key = e.getKey();
			Object value = e.getValue();
			MobileDest province = param.get(key);
			if(province==null){
				continue;
			}
			if(citiesgrop.get(province.getId())==null){
				MobileTree provinceCityTree = new MobileTree();
				provinceCityTree.setKey(province.getId());
				provinceCityTree.setValue(province.getName());
				List<MobileTree> cityTrees = provinceCityTree.getList();
				MobileTree city = new MobileTree();
				city.setKey(key);
				city.setValue(String.valueOf(value));
				cityTrees.add(city);
				citiesgrop.put(province.getId(), provinceCityTree);
			} else {
				MobileTree provinceCityTree = citiesgrop.get(province.getId());
				List<MobileTree> cityTrees = provinceCityTree.getList();
				MobileTree city = new MobileTree();
				city.setKey(key);
				city.setValue(String.valueOf(value));
				cityTrees.add(city);
			}
			}
		
		Iterator<Entry<String,MobileTree>>  cities =  citiesgrop.entrySet().iterator();
		while(cities.hasNext()){
			Entry<String,MobileTree> mit = cities.next();
			resultList.add(mit.getValue());
		}
		return resultList;
		
	}
	
	
	protected Page<PlaceBean> getPlaceSearchList(Map<String, Object> params) {
		ClientPlaceSearchVO searchVo = new ClientPlaceSearchVO();
		if (super.isSearchIds(params)) {
			//do nothing
		} else if (params.get("keyword")!=null)  {
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
		if (params.get("placeId") != null) {
			searchVo.setCityId(params.get("placeId").toString());
		}
		
		if(params.get("ids")!=null){
			StringTokenizer st = new StringTokenizer(params.get("ids").toString(), ",");
			List<Long> placeIds = new ArrayList<Long>();
			while (st.hasMoreTokens()) {
				String placeId = st.nextToken();
				placeIds.add(Long.valueOf(placeId));
			}
				
			searchVo.setPlaceIds(placeIds);
		}
		
		/**
		 * 必须含有门票
		 */
		if(params.get("productType") != null){
			searchVo.setProductType(ClientUtils.convetToList(params.get("productType").toString()));
		} else {
			searchVo.setProductType(ClientUtils.convetToList("TICKET"));
		}
		
		if (params.get("hasFreeness") != null) {
			searchVo.setHasFreenes(true);
			searchVo.setProductType(null);
		}
		// searchVo.setChannel(Constant.CHANNEL.CLIENT.name());

		Page<PlaceBean> pageConfig = vstClientPlaceService.placeSearch(searchVo);
		return pageConfig;
	}

	@Override
	public Page<ProductBean> listRoute(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.listRoute(params);
	}

	@Override
	public Map<String, Object> routeSearch(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.routeSearch(params);
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
