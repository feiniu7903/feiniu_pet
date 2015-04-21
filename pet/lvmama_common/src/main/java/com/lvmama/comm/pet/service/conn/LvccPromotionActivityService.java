package com.lvmama.comm.pet.service.conn;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.conn.LvccPromotionActivity;

/**
 * 市场部推广活动
 * @author shihui
 *
 */
public interface LvccPromotionActivityService {
	Long insert(LvccPromotionActivity record);
	
	void update(LvccPromotionActivity record);
	
	LvccPromotionActivity selectByPrimaryKey(Long activityId);
	
	List<LvccPromotionActivity> selectByParams(Map<String, Object> params);
	
	Long selectByParamsCount(Map<String, Object> params);
	
	/**
	 * 将无效活动变为有效，先将有效的活动变为无效
	 * */
	void changeValid(Long activityId);
	
	/**
	 * 根据id和valid值来更新状态
	 * */
	void updateValidById(Long activityId, String valid);

	/**
	 * 查找出当前可用的活动
	 * */
	LvccPromotionActivity selectCurrentActivity();
	
	boolean checkNameIsExsited(LvccPromotionActivity activity);
}
