package com.lvmama.comm.bee.service.view;

import java.util.List;

import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewJourneyPlace;
import com.lvmama.comm.pet.po.pub.CodeItem;

/**
 * 页面展示 之行程
 * @author MrZhu
 *
 */
public interface ViewPageJourneyService {
	/**
	 * 获取所有的景点
	 * @param pageId
	 * @return
	 */
	public List<CodeItem> getProdTarget(Long pageId);
	
	/**
	 * 获取已经选择了的景点
	 * @param journeyId
	 * @return
	 */
	public List<ViewJourneyPlace> getSelectedProdTarget(Long journeyId);
	/**
	 * 获取当前页面的所有行程列表
	 * @param pageId
	 * @return
	 */
	public List<ViewJourney> getViewJourneysByProductId(Long productId);
	
	/**
	 * 加载一个行程
	 * @param journeyId
	 * @return
	 */
	public ViewJourney loadViewJourney(Long journeyId);
	
	/**
	 * 新增一个行程
	 * @param viewJourney
	 */
	public void insertViewJourney(ViewJourney viewJourney,String operatorName);
	
	/**
	 * 更新一个已存在行程
	 * @param viewJourney
	 */
	public void updateViewJourney(ViewJourney viewJourney,String operatorName);
	
	public void deleteViewJourney(Long journeyId,String operatorName);
	
	public List<ViewJourney> getViewJourneyByMultiJourneyId(Long multiJourneyId);
	
	public void insertMultiViewJourney(ViewJourney viewJourney,String operatorName);
}
