package com.lvmama.comm.bee.service.view;

import java.util.List;

import com.lvmama.comm.bee.po.prod.ViewJourneyTips;

/**
 * 页面展示 之行程贴士
 * 
 * @author zhangxin
 * 
 */
public interface ViewPageJourneyTipsService {
	
	/**
	 * 获取当前页面的所有行程贴士列表
	 * 
	 * @param pageId
	 * @return
	 */
	public List<ViewJourneyTips> getViewJourneyTipsByJourneyId(Long journeyId);

	/**
	 * 加载一个行程小贴士
	 * @param tipId
	 * @return
	 */
	public ViewJourneyTips loadViewJourneyTips(Long tipId);

	/**
	 * 新增一个行程小贴士
	 * @param viewJourneyTips
	 */
	public Long insertViewJourney(ViewJourneyTips viewJourneyTips);

	/**
	 * 更新一个已存在行程小贴士
	 * @param viewJourneyTips
	 */
	public void updateViewJourneyTips(ViewJourneyTips viewJourneyTips);

	/**
	 * 删除行程小贴士
	 * @param tipId
	 */
	public void deleteViewJourneyTip(Long tipId);
}
