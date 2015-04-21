package com.lvmama.clutter.service;

import java.util.List;
import java.util.Map;

import com.lvmama.clutter.model.MobileTree;

public interface IClientOtherService {

	/**
	 * 意见反馈
	 * 
	 * @param param
	 * @return
	 */
	void subSuggest(Map<String, Object> param);

	/**
	 * 通过位置所在的城市获得对于数据库的数据信息
	 * 
	 * @param param
	 * @return
	 */
	Map<String, Object> getNameByLocation(Map<String, Object> param);

	/**
	 * 活动团购城市数据
	 * 
	 * @param param
	 * @return
	 */
	Map<String, Object> getGroupOnCities(Map<String, Object> param);

	/**
	 * 检测版本
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	com.lvmama.clutter.model.MobileVersion checkVersion(
			Map<String, Object> param) throws Exception;

	/**
	 * 是否已经送优惠券
	 * @param param udid&secondChannel
	 * @return
	 * @throws Exception
	 */
	boolean isGivedCoupon(Map<String, Object> param) throws Exception;

	/**
	 * 摇优惠券
	 * 
	 * @param param
	 * @return resultCode 中奖：1,未中奖：0,当天无摇奖机会了：-1,活动结束：-2
	 */
	Map<String, Object> rollMarkCoupon(Map<String, Object> param)
			throws Exception;

	/**
	 * 增加消息推送设备
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> addMobilePushDevice(Map<String, Object> param)
			throws Exception;
	/**
	 * 活动城市省份tree
	 * @param param
	 * @return
	 */
	Map<String,Object> getProvinceTree(Map<String, Object> param);

	
	/**
	 * 2014 世界杯活动，生成幸运码
	 * @param param
	 * @return
	 */
	Map<String,Object> generatorLuckyCode(Map<String, Object> param);
	
	/**
	 * 获取活动信息 
	 * @param param
	 * @return
	 */
	Map<String,Object> getFifaInfo(Map<String, Object> param);
	
	/**
	 * 获取活动列表数据
	 * @param param
	 * @return
	 */
	Map<String,Object> getFifaList(Map<String, Object> param);

	Map<String, Object> queryTheWinningUser4Fifa(Map<String, Object> params);

}
