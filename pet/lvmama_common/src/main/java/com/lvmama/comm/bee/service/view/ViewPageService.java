package com.lvmama.comm.bee.service.view;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.pet.po.pub.ComCondition;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

public interface ViewPageService {
	
	List<ComCondition> getAllConditions(List<ProdProduct> products);
	
	Integer countByParams(Map params);

	List findByParams(Map params);

	int deleteByPrimaryKey(Long pageId);

	ResultHandle addViewPage(ViewPage record);
	
	/**
	 * 获取页面时会同时把页面的内容查询出来
	 * @param pageId
	 * @return
	 */
	ViewPage getViewPage(Long pageId);
	
	boolean isProductUsed(Long productId);
	
	boolean isProductUnUsed(Long productId);
	
	public int updateValidByProductId(Long productId);

	int update(ViewPage record);
	
	void saveViewContent(ViewPage viewPage,String operatorName);
	/**
	 * 修改是否有效状态
	 */
	void deleteViewPage(Map params);
	
	public Integer selectRowCount(Map searchConds);
	
	/**
	 * 复制展示产品
	 */
	Long copyPage(Long srcPageId, Long productId);

	ViewPage selectByProductId(Long productId);
	
	ViewPage getViewPageByProductId(Long productId);
	
	ViewContent getViewContentByMultiJourneyId(Long multiJourneyId, String contentType);
	
	/**
	 * 查询多行程关联内容
	 * @param multiJourneyId 多行程id
	 * @return 关联内容集合
	 */
	List<ViewContent> getViewContentByMultiJourneyId(Long multiJourneyId);
	
	void insertViewContent(ViewContent content);
	
	void updateViewContent(ViewContent content);
	
	ViewContent getViewContentByContentId(Long contentId);
}
