package com.lvmama.pet.conn.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.conn.LvccPromotionActivity;
import com.lvmama.comm.pet.service.conn.LvccPromotionActivityService;
import com.lvmama.pet.conn.dao.LvccPromotionActivityDAO;

public class LvccPromotionActivityServiceImpl implements LvccPromotionActivityService{
	
	private LvccPromotionActivityDAO  lvccPromotionActivityDAO;
	
	public void setLvccPromotionActivityDAO(
			LvccPromotionActivityDAO lvccPromotionActivityDAO) {
		this.lvccPromotionActivityDAO = lvccPromotionActivityDAO;
	}

	@Override
	public Long insert(LvccPromotionActivity record) {
		return lvccPromotionActivityDAO.insert(record);
	}

	@Override
	public void update(LvccPromotionActivity record) {
		lvccPromotionActivityDAO.update(record);
	}

	@Override
	public List<LvccPromotionActivity> selectByParams(Map<String, Object> params) {
		return lvccPromotionActivityDAO.selectByParams(params);
	}

	@Override
	public LvccPromotionActivity selectByPrimaryKey(Long activityId) {
		return lvccPromotionActivityDAO.selectByPrimaryKey(activityId);
	}

	@Override
	public Long selectByParamsCount(Map<String, Object> params) {
		return lvccPromotionActivityDAO.selectByParamsCount(params);
	}

	@Override
	public void updateValidById(Long activityId, String valid) {
		LvccPromotionActivity activity = new LvccPromotionActivity();
		activity.setActivityId(activityId);
		activity.setValid(valid);
		lvccPromotionActivityDAO.updateValidById(activity);
	}

	@Override
	public void changeValid(Long activityId) {
		lvccPromotionActivityDAO.updateValidToN();
		updateValidById(activityId, "Y");
	}

	@Override
	public LvccPromotionActivity selectCurrentActivity() {
		return lvccPromotionActivityDAO.selectCurrentActivity();
	}

	@Override
	public boolean checkNameIsExsited(LvccPromotionActivity activity) {
		Long count = lvccPromotionActivityDAO.checkNameIsExsited(activity);
		return count > 0 ? true : false;
	}
}
