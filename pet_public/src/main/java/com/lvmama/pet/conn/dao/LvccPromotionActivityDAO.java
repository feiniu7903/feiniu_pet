package com.lvmama.pet.conn.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.conn.LvccPromotionActivity;

public class LvccPromotionActivityDAO extends BaseIbatisDAO {

	public LvccPromotionActivityDAO() {
		super();
	}
	
	public Long insert(LvccPromotionActivity record) {
		return (Long) super.insert("LVCC_PROMOTION_ACTIVITY.insert",record);
	}
	
	public void update(LvccPromotionActivity record) {
		super.update("LVCC_PROMOTION_ACTIVITY.update",record);
	}
	
	public Long selectByParamsCount(Map<String, Object> params) {
		return (Long) super.queryForObject("LVCC_PROMOTION_ACTIVITY.selectByParamsCount", params);
	}
	
	public List<LvccPromotionActivity> selectByParams(Map<String, Object> params) {
		return super.queryForList("LVCC_PROMOTION_ACTIVITY.selectByParams", params);
	}
	
	public LvccPromotionActivity selectByPrimaryKey(Long activityId) {
		return (LvccPromotionActivity) super.queryForObject("LVCC_PROMOTION_ACTIVITY.selectByPrimaryKey", activityId);
	}
	
	public void updateValidToN() {
		super.update("LVCC_PROMOTION_ACTIVITY.updateValidToN");
	}
	
	public void updateValidById(LvccPromotionActivity activity) {
		super.update("LVCC_PROMOTION_ACTIVITY.updateValidById",activity);
	}
	
	public LvccPromotionActivity selectCurrentActivity() {
		return (LvccPromotionActivity) super.queryForObject("LVCC_PROMOTION_ACTIVITY.selectCurrentActivity");
	}
	
	public Long checkNameIsExsited(LvccPromotionActivity activity) {
		return  (Long) super.queryForObject("LVCC_PROMOTION_ACTIVITY.checkNameIsExsited", activity);
	}
}
