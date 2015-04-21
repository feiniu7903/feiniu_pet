package com.lvmama.clutter.service.impl.pad.v1_0;

import java.util.List;
import java.util.Map;

import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.clutter.service.client.v3_1.ClientSearchSeviceForV31;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.PlaceBean;

public class ClientSearchServicePadV10 extends ClientSearchSeviceForV31{

	@Override
	public Map<String, Object> placeSearch(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.placeSearch(params);
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
	protected Page<PlaceBean> getPlaceSearchList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getPlaceSearchList(params);
	}

	@Override
	public Map<String, Object> shakePlace(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.shakePlace(params);
	}

	@Override
	public MobilePlace getRandomPlaceBean(List<PlaceBean> placeList,
			String placeId) {
		// TODO Auto-generated method stub
		return super.getRandomPlaceBean(placeList, placeId);
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
