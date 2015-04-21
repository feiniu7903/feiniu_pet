package com.lvmama.clutter.service.client.v3_1;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.clutter.model.MobileRecommend;
import com.lvmama.clutter.service.impl.ClientRecommendServiceImpl;
import com.lvmama.clutter.utils.ClientRecommendProperties;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.MemcachedUtil;

/**
 * v3.1 客户端推荐
 * @author qinzubo
 *
 */
public class ClientRecommendServiceV31 extends ClientRecommendServiceImpl{

	@Override
	public Map<String, Object> getClientRecommend(Map<String, Object> params) {
		Long blockId = Long.valueOf(ClientRecommendProperties.getClientRecommendBlockId()); // 获取block的id. 
		params.put("parentId", blockId);
		params.put("currentDate", new Date()); // 只有首页推荐有该时间 .当前时间 . 
		params.put("isValid", "Y");  // 是否有效  
		
		// 从缓存中获取信息. 
		String memcacheKey = getMemcacheKeyByParams(params)+(null == params.get("page")?"":params.get("page").toString())
				+params.get("lvversion")+(null==params.get("firstChannel")?"":params.get("firstChannel").toString());
		
		Object obj = getMemecachedInfo(memcacheKey);
		Map<String,Object> map = new HashMap<String,Object>();
		if(null == obj) {
			// 初始化ids
			//String[] ids = getIds(params,"1");
			List<Map<String,Object>> recommendMapList = new ArrayList<Map<String,Object>>(); // 存放每个字块的列表 . 
			Page p =  initPage(params,100,1);
			params.put("isPaging", "true"); // 是否使用分页 
			params.put("startRows", p.getStartRows());
			params.put("endRows", p.getEndRows());
			String os = String.valueOf(params.get("firstChannel"));
			if("null".equals(os)) {
				os = "";
			}
			
			// 暂时这样 ，后期修改
			Map<String,Long> blockInfoMap = new HashMap<String,Long>();
			blockInfoMap.put("IPHONE",674l);
			blockInfoMap.put("ANDROID",675l);
			blockInfoMap.put("IPAD",676l);
			blockInfoMap.put("WP8",677l);
			
			// 如果存在 
			if(!StringUtils.isEmpty(os)) {
				Long id = blockInfoMap.get(os.toUpperCase());
				if(null != id && id > 0) {
					Map<String,Object> t_map = new HashMap<String,Object>();
					params.put("recommendBlockId", id);
					List<MobileRecommend> list = getClentRecommendList(params,p);
					t_map.put("data", list); // 列表数据 
					t_map.put("isLastPage", !p.hasNext()); // 列表数据 
					t_map.put("name", os); 
					t_map.put("id",id);
					recommendMapList.add(t_map);
				}
			}
			map.put("datas", recommendMapList);
			if(recommendMapList!=null && !recommendMapList.isEmpty()){
				MemcachedUtil.getInstance().set(MOBILE_RECOMMEND_CACHE+memcacheKey, MOBILE_MEMCACHE_SECOND, recommendMapList);
			}
		} else {
			map.put("datas",obj);
		}
		return initVersion(map,params);
	}
	
	

	@Override
	public Map<String, Object> getFocusRecommend(Map<String, Object> param) {
		return super.getFocusRecommend(param);
	}
	
	
	/*@Override
	public Map<String, Object> getGuideRecommendCities(
			Map<String, Object> params) {
		return super.getGuideRecommendCities(params);
	}

	@Override
	public Map<String, Object> getGuideRecommendCitiesInternal(
			Map<String, Object> params) {
		return super.getGuideRecommendCitiesInternal(params);
	}

	@Override
	public Map<String, Object> getRouteArroundRecommendCities(
			Map<String, Object> params) {
		return super.getRouteArroundRecommendCities(params);
	}

	@Override
	public Map<String, Object> getCitiesArea(Map<String, Object> param) {
		return super.getCitiesArea(param);
	}

	

	@Override
	public Map<String, Object> getDepaturePlace(Map<String, Object> param) {
		return super.getDepaturePlace(param);
	}

	@Override
	public Map<String, Object> getRouteFromDest(Map<String, Object> params) {
		return super.getRouteFromDest(params);
	}

	@Override
	public Map<String, Object> getRouteToDest(Map<String, Object> params) {
		return super.getRouteToDest(params);
	}
	*/
	
	

}
