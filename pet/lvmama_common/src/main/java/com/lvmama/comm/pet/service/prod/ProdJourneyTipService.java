package com.lvmama.comm.pet.service.prod;

import java.util.List;

import com.lvmama.comm.pet.po.prod.ProdJourneyTip;

/**
 * 页面展示 之行程贴士
 * 
 * @author zhangxin
 * 
 */
public interface ProdJourneyTipService {
	
	/**
	 * 获取当前页面的所有行程贴士列表
	 * 
	 * @param pageId
	 * @return
	 */
	public List<ProdJourneyTip> getViewJourneyTipsByJourneyId(Long journeyId);

	/**
	 * 加载一个行程小贴士
	 * @param tipId
	 * @return
	 */
	public ProdJourneyTip loadViewJourneyTips(Long tipId);

	/**
	 * 新增一个行程小贴士
	 * @param viewJourneyTips
	 */
	public void insertViewJourney(ProdJourneyTip viewJourneyTips);

	/**
	 * 更新一个已存在行程小贴士
	 * @param viewJourneyTips
	 */
	public void updateViewJourneyTips(ProdJourneyTip viewJourneyTips);

	/**
	 * 删除行程小贴士
	 * @param tipId
	 */
	public void deleteViewJourneyTip(Long tipId);
}
