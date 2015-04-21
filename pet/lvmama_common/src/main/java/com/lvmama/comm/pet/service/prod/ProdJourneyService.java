package com.lvmama.comm.pet.service.prod;

import java.util.List;

import com.lvmama.comm.pet.po.prod.ProdJourney;
import com.lvmama.comm.pet.po.pub.CodeItem;

/**
 * 页面展示 之行程
 * @author MrZhu
 *
 */
public interface ProdJourneyService {
	/**
	 * 获取所有的景点
	 * @param pageId
	 * @return
	 */
	public List<CodeItem> getProdTarget(Long pageId);

	/**
	 * 获取当前页面的所有行程列表
	 * @param pageId
	 * @return
	 */
	public List<ProdJourney> getViewJourneysByProductId(Long productId);
	
	/**
	 * 加载一个行程
	 * @param journeyId
	 * @return
	 */
	public ProdJourney loadViewJourney(Long journeyId);
	
	/**
	 * 新增一个行程
	 * @param viewJourney
	 */
	public void insertViewJourney(ProdJourney viewJourney,String operatorName);
	
	/**
	 * 更新一个已存在行程
	 * @param viewJourney
	 */
	public void updateViewJourney(ProdJourney viewJourney,String operatorName);
	
	public void deleteViewJourney(Long journeyId,String operatorName);
}
