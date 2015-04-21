package com.lvmama.clutter.service.impl.pad.v1_0;

import java.util.List;
import java.util.Map;

import com.lvmama.clutter.model.MobileRecommend;
import com.lvmama.clutter.service.client.v3_1.ClientRecommendServiceV31;
import com.lvmama.comm.pet.po.mobile.MobileRecommendBlock;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.vo.Page;

public class ClientRecommendServicePadV10 extends ClientRecommendServiceV31{

	@Override
	public Map<String, Object> getClientRecommend(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getClientRecommend(params);
	}

	@Override
	public Map<String, Object> getFocusRecommend(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getFocusRecommend(param);
	}

	@Override
	public Map<String, Object> getCitiesArea(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getCitiesArea(params);
	}

	@Override
	public Map<String, Object> getRouteArroundRecommendCities(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getRouteArroundRecommendCities(params);
	}

	@Override
	public Map<String, Object> getGuideRecommendCities(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getGuideRecommendCities(params);
	}

	@Override
	public Map<String, Object> getGuideRecommendCitiesInternal(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getGuideRecommendCitiesInternal(params);
	}

	@Override
	public Map<String, Object> getDepaturePlace(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getDepaturePlace(params);
	}

	@Override
	public List<Map<String, Object>> getBlockInfo(
			List<MobileRecommendBlock> mrbList) {
		// TODO Auto-generated method stub
		return super.getBlockInfo(mrbList);
	}

	@Override
	public Map<String, Object> getCommonRecommendInfo(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getCommonRecommendInfo(params);
	}

	@Override
	public Map<String, Object> getCommonRecommendInfo(
			Map<String, Object> params, String type) {
		// TODO Auto-generated method stub
		return super.getCommonRecommendInfo(params, type);
	}

	@Override
	public Map<String, Object> getRouteToDest(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getRouteToDest(params);
	}

	@Override
	public Map<String, Object> getRouteFromDest(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getRouteFromDest(params);
	}

	@Override
	public Map<String, Object> getDestCommonRecommendInfo(
			Map<String, Object> params, String type) {
		// TODO Auto-generated method stub
		return super.getDestCommonRecommendInfo(params, type);
	}

	@Override
	public Map<String, Object> getDestStrategyCommonRecommendInfo(
			Map<String, Object> params, String type) {
		// TODO Auto-generated method stub
		return super.getDestStrategyCommonRecommendInfo(params, type);
	}

	@Override
	public Map<String, Object> getCommonRecommendCities(
			Map<String, Object> params, long defCount, long defPage, String type) {
		// TODO Auto-generated method stub
		return super.getCommonRecommendCities(params, defCount, defPage, type);
	}

	@Override
	public List<MobileRecommend> getMobileRecommendList(
			Map<String, Object> params, Page p) {
		// TODO Auto-generated method stub
		return super.getMobileRecommendList(params, p);
	}

	@Override
	public List<MobileRecommend> getClentRecommendList(
			Map<String, Object> params, Page p) {
		// TODO Auto-generated method stub
		return super.getClentRecommendList(params, p);
	}

	@Override
	public List<MobileRecommend> getMobileRecommendList(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getMobileRecommendList(params);
	}

	@Override
	public Map<String, Object> getSellData(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getSellData(params);
	}

	@Override
	public Map<String, Object> getHotData(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getHotData(params);
	}

	@Override
	public Map<String, Object> getSellDataGroupByType(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getSellDataGroupByType(params);
	}

	@Override
	public Map<String, Object> getHotDataGroupByType(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getHotDataGroupByType(params);
	}

	@Override
	public List<RecommendInfo> getHotList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getHotList(params);
	}

	@Override
	public List<RecommendInfo> getSellList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getSellList(params);
	}

	@Override
	public Map<String, Object> getHDCommonRecommendInfo(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getHDCommonRecommendInfo(params);
	}

}
