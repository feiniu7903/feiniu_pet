package com.lvmama.prd.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ViewJourneyTips;

/**
 * 行程小贴士底层操作类
 * 
 * @author zhangxin
 * 
 */
public class ViewJourneyTipDAO extends BaseIbatisDAO {

	/**根据主键TIP_ID删除行程小贴士
	 * @param tipId
	 * @return
	 */
	public int deleteByPrimaryKey(Long tipId) {
		ViewJourneyTips viewJourneyTips = new ViewJourneyTips();
		viewJourneyTips.setTipId(tipId);
		int rows = super.delete("VIEW_JOURNEY_TIPS.deleteByPrimaryKey", viewJourneyTips);
		return rows;
	}
	
	/**
	 * 
	 * @param journeyId
	 */
	public void deleteByJourneyId(Long journeyId){
		ViewJourneyTips record=new ViewJourneyTips();
		record.setJourneyId(journeyId);
		super.delete("VIEW_JOURNEY_TIPS.deleteByJourneyId",record);
	}

	/**保存行程小贴士信息
	 * @param viewJourneyTips
	 */
	public Long insert(ViewJourneyTips viewJourneyTips) {
		return (Long) super.insert("VIEW_JOURNEY_TIPS.insert",viewJourneyTips);
	}

	/**根据主键TIP_ID查询行程小贴士
	 * @param tipId
	 * @return
	 */
	public ViewJourneyTips selectByPrimaryKey(Long tipId) {
		ViewJourneyTips viewJourneyTips = new ViewJourneyTips();
		viewJourneyTips.setJourneyId(tipId);
		ViewJourneyTips result = (ViewJourneyTips) super.queryForObject("VIEW_JOURNEY_TIPS.selectByPrimaryKey", viewJourneyTips);
		return result;
	}

	/**更新行程小贴士信息
	 * @param viewJourneyTips
	 * @return
	 */
	public int updateViewJourneyTips(ViewJourneyTips viewJourneyTips) {
		int rows = super.update("VIEW_JOURNEY_TIPS.updateViewJourneyTips", viewJourneyTips);
		return rows;
	}

	/**根据行程ID查询该行程下的所有小贴士
	 * @param journeyId 行程ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewJourneyTips> getViewJourneyTipsByJourneyId(long journeyId) {
		List<ViewJourneyTips> viewJourneyTipList = super.queryForList("VIEW_JOURNEY_TIPS.getViewJourneyTipsByJourneyId",journeyId);
		return viewJourneyTipList;
	}

}
