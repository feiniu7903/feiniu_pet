package com.lvmama.prd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;

public class ViewPageDAO extends BaseIbatisDAO{

	public Integer countByParams(Map params) {
		return (Integer) super.queryForObject("VIEW_PAGE.countByParams", params);
	}
 
	public List findByParams(Map params) {
		if (params.get("_startRow")==null) {
			params.put("_startRow", 0);
		}
		if (params.get("_endRow")==null) {
			params.put("_endRow", 20);
		}
		return super.queryForList("VIEW_PAGE.selectByParams", params);
	}
	public Integer countByProductId(Long productId) {
		ViewPage key = new ViewPage();
		key.setProductId(productId);
		return (Integer) super.queryForObject("VIEW_PAGE.countByProductId", key);
	}
	
	public Integer countDeleteByProductId(Long productId) {
		ViewPage key = new ViewPage();
		key.setProductId(productId);
		return (Integer) super.queryForObject("VIEW_PAGE.countDeleteByProductId", key);
	}
	
	public int updateValidByProductId(Long productId) {		
		return super.update("VIEW_PAGE.updateValidByProductId", productId);
	}
	
	public int deleteByPrimaryKey(Long pageId) {
		ViewPage key = new ViewPage();
		key.setPageId(pageId);
		int rows = super.delete("VIEW_PAGE.deleteByPrimaryKey", key);
		return rows;
	}
	/**
	 * 删除销售产品全部内容
	 * 
	 * @author: ranlongfei 2012-8-2 下午2:36:32
	 * @param pageId
	 * @return
	 */
	public int deleteViewContentByPageId(Long pageId) {
		ViewContent key = new ViewContent();
		key.setPageId(pageId);
		int rows = super.delete("VIEW_CONTENT.deleteByPageId", key);
		return rows;
	}

	public Long insert(ViewPage record) {
		Object newKey = super.insert("VIEW_PAGE.insert", record);
		return (Long) newKey;
	}

	public ViewPage selectByPrimaryKey(Long pageId) {
		ViewPage key = new ViewPage();
		key.setPageId(pageId);
		ViewPage record = (ViewPage) super.queryForObject("VIEW_PAGE.selectByPrimaryKey", key);
		List<ViewContent> contentList = super.queryForList("VIEW_CONTENT.getViewContentBypageId", pageId);
		if(record!=null && contentList.size()>0){
			record.initContents(contentList);
		}
		return record;
	}
	public ViewPage getViewPageByProductId(Long productId) {
		ViewPage record = (ViewPage) super.queryForObject("VIEW_PAGE.getViewPageByProductId", productId);
		if(record!=null){
			List<ViewContent> contentList = super.queryForList("VIEW_CONTENT.getViewContentBypageId", record.getPageId());
			record.initContents(contentList);
		}
		return record;
	}

	public int updateByPrimaryKey(ViewPage record) {
		int rows = super.update("VIEW_PAGE.updateByPrimaryKey", record);
		return rows;
	}

	public Long insertViewContent(ViewContent content) {
		return (Long) super.insert("VIEW_CONTENT.insert", content);
	}
	
	public void updateViewContent(ViewContent content) {
		super.update("VIEW_CONTENT.updateByPrimaryKey", content);
	}

	public void deleteByPageId(Map params) {
		super.delete("VIEW_PAGE.markIsValid", params);
	}
 	
	public Integer selectRowCount(Map searchConds){
		Integer count = 0;
		count = (Integer) super.queryForObject("VIEW_PAGE.selectRowCount",searchConds);
		return count;
	}

	public ViewPage selectByProductId(Long productId) {
		return (ViewPage) super.queryForObject("VIEW_PAGE.selectByProductId", productId);
	}
	
	public ViewContent getViewContentByMultiJourneyId(Map<String, Object> params) {
		return (ViewContent) super.queryForObject("VIEW_CONTENT.getViewContentByMultiJourneyId", params);
	}
	
	/**
	 * 查询多行程关联的内容
	 * @param multiJourneyId 多行程id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewContent> getMJViewContentByMultiJourneyId(Long multiJourneyId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("multiJourneyId", multiJourneyId);
		return super.queryForList("VIEW_CONTENT.getMJViewContentByMultiJourneyId", params);
	}
	
	
	public ViewContent getViewContentByContentId(Long contentId) {
		return (ViewContent) super.queryForObject("VIEW_CONTENT.getViewContentByContentId", contentId);
	}
}