package com.lvmama.comm.pet.service.mobile;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.mobile.MobileRecommendInfo;

/**
 * 推荐元素信息.
 * @author qinzubo
 *
 */
public interface MobileRecommendInfoService {
	
	/**
	 * 新增推荐信息.
	 * @param rinfo  推荐信息对象
	 * @return  持久化后的推荐信息对象
	 */
	MobileRecommendInfo insertMobileRecommendInfo(MobileRecommendInfo rinfo);
	
	/**
	 * 更新推荐信息.
	 * @param rinfo  要更新的对象 
	 * @return   更新后的对象
	 */
	MobileRecommendInfo updateMobileRecommendInfo(MobileRecommendInfo rinfo);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键
	 * @return  对象
	 */
	MobileRecommendInfo selectMobileRecommendInfoById(Long id);
	
	/**
     * 查询列表 
     * 支持   主键(id), 标题(recommendTitle)，状态(isValid)，链接地址(recommendImageUrl) ，
     *      类型(recommendType),开始时间(startDate),结束时间（endTime）
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileRecommendInfo> queryMobileRecommendInfoList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileRecommendInfoList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param id
	 * @return 删除的条数
	 */
	int deleteMobileRecommendInfoById(Long id);
	
	
	/**
	 * 更新状态 ，
	 * @param param  isValid 和 id 两个参数 
	 * @return true or false 
	 */
	boolean updateStatus(String isValid,Long id) ;
	
	
	/**
	 * 更新排序值，
	 * @param param  seq 和 id 两个参数 
	 * @return true or false 
	 */
	boolean updateSeq(String seq,Long id) ;
}
