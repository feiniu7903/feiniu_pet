package com.lvmama.clutter.service.client.v4_0;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.clutter.service.client.v3_1.ClientRecommendServiceV31;
import com.lvmama.clutter.utils.ClientRecommendProperties;
import com.lvmama.comm.pet.po.mobile.MobileRecommendBlock;
import com.lvmama.comm.utils.MemcachedUtil;

/**
 * 推荐 
 * @author qinzubo
 *
 */
public class ClientRecommendServiceV40 extends ClientRecommendServiceV31 {
	protected final static String HOT_CITY_GUO_WAI = "韩国,泰国,香港,长滩岛,日本,巴厘岛,塞班岛,美国,马尔代夫,普吉岛,澳大利亚,毛里求斯,意大利,日韩邮轮,马来西亚,法国";
	protected final static String HOT_CITY_GUONEI = "上海,杭州,常州,无锡,苏州,天目湖,千岛湖,南京,三亚,厦门,丽江,哈尔滨,昆明,北京,长白山,桂林,西安";
	
	/**
	 * 首页推荐 3.4 
	 */
	@Override
	public Map<String,Object> getIndexRecommend(Map<String, Object> params) {
		Long blockId = getBlockIdByProperties("6");
		params.put("parentId", blockId);
		params.put("currentDate", new Date()); // 只有首页推荐有该时间 .当前时间 .
		params.put("isValid", "Y"); // 是否有效
		params.put("count", 100);
		params.put("page", 1);
		if ("IPAD".equals(params.get("firstChannel"))) {
			return getHDCommonRecommendInfo(params);
		} else {
			return getCommonRecommendInfo(params);
		}
	}

	/**
	 * 自由行列表 - 地图 3.4
	 */
	@Override
	public Map<String,Object> getFreetripRecommend4Map(Map<String, Object> params) {
		Long blockId = getBlockIdByProperties("7");
		params.put("parentId", blockId);
		return getCommonRecommendInfo(params, "2");
	}

	/**
	 * 目的地城市配置国内 
	 */
	@Override
	public Map<String, Object> getInternalDestRecommendCities(
			Map<String, Object> param) {
		return initRecommendCities(param,"8");
	}

	/**
	 * 目的地城市配置国外 
	 */
	@Override
	public Map<String, Object> getOverseasDestRecommendCities(
			Map<String, Object> param) {
		return initRecommendCities(param,"9");
	}
	
	/**
	 * 初始化城市推荐信息.
	 * @param params
	 */
	public Map<String,Object> initRecommendCities(Map<String,Object> params,String blockIdstr) {
		Long blockId = getBlockIdByProperties(blockIdstr);
		params.put("parentId", blockId);
		// 根据拼音排序
		params.put("orderByPin", "true");
		// 从缓存中获取信息.
		String memcacheKey = getMemcacheKeyByParams(params);
		Object obj = getMemecachedInfo(memcacheKey);
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == obj) {
			// 查询子块
			List<MobileRecommendBlock> blockList = mobileRecommendBlockService.queryMobileRecommendBlockByParam(params);
			List<Map<String, Object>> recommendMapList = new ArrayList<Map<String, Object>>(); // 存放每个字块的列表
			// 字块数据 .
			if (null != blockList && !blockList.isEmpty()) {
				for (int i = 0; i < blockList.size(); i++) {
					MobileRecommendBlock mb = blockList.get(i);
					if(null == mb || mb.getId() == null || StringUtils.isEmpty(mb.getBlockName())) {
						continue;
					}
					Map<String, Object> t_map = new HashMap<String, Object>();
					t_map.put("name", StringUtils.isEmpty(mb.getBlockName())?"":mb.getBlockName());
					t_map.put("subName",null == mb.getReserve2() ? "" : mb.getReserve2());
					t_map.put("pinyin", StringUtils.isEmpty(str2UpperCase(mb.getReserve1()))?"":str2UpperCase(mb.getReserve1()));
					// 国外 9 
					if("9".equals(blockIdstr)) {
						t_map.put("isHot", HOT_CITY_GUO_WAI.contains(null==mb.getBlockName()?"":mb.getBlockName()));
					} else {
						t_map.put("isHot", HOT_CITY_GUONEI.contains(null==mb.getBlockName()?"":mb.getBlockName()));
					}
					t_map.put("id", mb.getId());
					recommendMapList.add(t_map);
				}
			}
			map.put("datas", recommendMapList);
			if (recommendMapList != null && !recommendMapList.isEmpty()) {
				MemcachedUtil.getInstance().set(
						MOBILE_RECOMMEND_CACHE + memcacheKey,
						MOBILE_MEMCACHE_SECOND, recommendMapList);
			}
		} else {
			map.put("datas", obj);
		}

		return initVersion(map, params);
	}
	
	/**
	 * 从recommend_block.properties配置文件中获取blockId 
	 * @param blockIdstr
	 * @return
	 */
	public Long getBlockIdByProperties(String blockIdstr) {
		Long blockId = -1l;
		try {
			String bstr = ClientRecommendProperties.getBlockId(blockIdstr);
			if(!StringUtils.isEmpty(bstr)) {
				blockId = Long.valueOf(bstr);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return blockId;
	}
}
