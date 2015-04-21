package com.lvmama.clutter.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.seo.RecommendInfo;

public interface IClientRecommendService {
	/**
	 * 获得攻略推荐城市数据 - 国外
	 * @return
	 */
	Map<String, Object> getGuideRecommendCities(Map<String ,Object> params);
	
	/**
	 * 获得攻略推荐城市数据 - 国内
	 * @return
	 */
	Map<String, Object> getGuideRecommendCitiesInternal(Map<String, Object> params);
	
	/**
	 * 获取度假所在目的地周边推荐目的地数据
	 * @return
	 */
	Map<String ,Object> getRouteArroundRecommendCities(Map<String ,Object> params);
	
	/**
	 * 获得自由行区域推荐城市数据
	 * @return
	 */
	Map<String,Object> getCitiesArea(Map<String,Object> param);
	
	/**
	 * 获得首页推荐数据 
	 * @return m m = {datas:[{mobileRecommend1},{mobileRecommend2}.....],islastpage:true}
	 */
    Map<String,Object> getFocusRecommend(Map<String,Object> param);
    
    /**
     * 获取出发地信息（度假 3 ，攻略国外4 ， 攻略国内5）. 
     * @return
     */
    Map<String,Object> getDepaturePlace(Map<String,Object> param);

    /**
     * 查询出发地
     * @param params
     * @param type
     * @return
     */
    Map<String,Object> getRouteFromDest(Map<String ,Object> params);
    /**
     * 分类查询目的地
     * @param params
     * @return
     */
    Map<String,Object> getRouteToDest(Map<String,Object> params);
    
    /**
     * 客户端推荐. 
     * @param params
     * @return
     */
    Map<String,Object> getClientRecommend(Map<String,Object> params);
	
    /**
     * ipad首页限时特卖
     * @param params
     * @return
     */
    Map<String,Object> getSellData(Map<String,Object> params);
    
    /**
     * ipad首页热销排行
     * @param params
     * @return
     */
    Map<String,Object> getHotData(Map<String,Object> params);
    
    /**
     * ipad度假首页限时特卖轮播
     * @param params
     * @return
     */
    Map<String,Object> getSellDataGroupByType(Map<String,Object> params);
    
    /**
     * ipad度假首页热销排行轮播
     * @param params
     * @return
     */
    Map<String,Object> getHotDataGroupByType(Map<String,Object> params);
    
    /**
     * 获取首页限时特卖数据
     * @param params
     * @return
     */
    List<RecommendInfo> getHotList(Map<String, Object> params);
    
    /**
     * 获取首页热销排行数据
     * @param params
     * @return
     */
    List<RecommendInfo> getSellList(Map<String, Object> params);
    
    
    /**
     * 目的地城市配置 国内 
     * @param param
     * @return
     */
    Map<String,Object> getInternalDestRecommendCities(Map<String,Object> param);
    
    /**
     * 目的地城市配置 国外 
     * @param param
     * @return
     */
    Map<String,Object> getOverseasDestRecommendCities(Map<String,Object> param);
    
    
    /**
     * 获取度假推荐数据
     * @param params
     * @return
     */
    Map<String, Object> getHolidayData(Map<String, Object> params);
    
    /**
     * 获取度假推荐数据
     * @param params
     * @return
     */
    List<RecommendInfo> getHolidayList(Map<String, Object> params);
    
    /**
     * 获取首页推荐 for app 3.4
     * @param param
     * @return
     */
    Map<String,Object> getIndexRecommend(Map<String,Object> param);
    
    /**
     * 自由行列表 - 地图
     * @param param
     * @return
     */
    Map<String,Object> getFreetripRecommend4Map(Map<String,Object> param) ;
    
    /**
     * 根据城市名称获取省份编号
     * @param param
     * @return
     */
    String getProvinceByCityName(String city) ;
}
