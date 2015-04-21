package com.lvmama.front.web.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.pet.po.comment.CmtActivity;
import com.lvmama.comm.pet.po.comment.CmtRecommendPlace;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.pet.service.comment.CmtActivityService;
import com.lvmama.comm.pet.service.seo.SeoIndexPageService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtProdTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CmtSpecialSubjectVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;


/**
 * 点评首页
 * @author ganyingwen
 *
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/comment/index/index.ftl", type = "freemarker"),
	@Result(name = "indexPageOfBestCmtList", location = "/WEB-INF/pages/comment/index/indexPageOfBestCmtList.ftl", type = "freemarker")
})
public final class IndexCommentAction extends CmtBaseAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7315170818106348441L;
	private static final Log LOG = LogFactory.getLog(IndexCommentAction.class);
	/**
	 * 最新点评的显示条目数
	 */
	private static final int NUMBER_OF_LASTEST_COMMENTS = 20;
	/**
	 * 产品精华点评的显示条目数
	 */
	private static final int NUMBER_OF_BEST_COMMENTS = 30;

	/**
	 * 点评活动
	 */
	private CmtActivity cmtActivity;
	
	/**
	 * 显示的产品精华点评分页
	 */
	private Page<CommonCmtCommentVO> bestCmtsPageConfig;
	
	/**
	 * 最新的点评列表
	 */
	private List<CommonCmtCommentVO> lastestCmtList;

	/**
	 * 点评招募景点
	 */
	private List<CmtRecommendPlace> recommendPlaceList = new ArrayList<CmtRecommendPlace>();
	/**
	 * 专题
	 */
	private List<CmtSpecialSubjectVO> cmtSpecialSubjectList = new ArrayList<CmtSpecialSubjectVO>();
	
	/**
	 * seo类
	 */
	private SeoIndexPage seoIndexPage;
	/*
	 * 用户待点评的产品数
	 */
	private int prodsCountOfWaitingForCmt;
	/**
	 * 每页记录数
	 */
	private long pageSize = 20;
	/**
	 * 每页记录数
	 */
	private long currentPage = 1;
	/**
	 * 点评活动的Service
	 */
	private CmtActivityService cmtActivityService;
	/**
	 * seo专题
	 */
	private SeoIndexPageService seoIndexPageService;
	
	private OrderService orderServiceProxy;
	//后台LP推荐城市游记
	private static final Long BLOCKID = 15035l;
	private static final String STATION = "LP";
	
	//城市自游志
	private Map<String, List<RecommendInfo>> recommendInfoMap;
	
	@SuppressWarnings("unchecked")
	@Override
	@Action("/comment/comment")
	public String execute() throws Exception {	
		
		if("REPEAT_CMT_ORDER".equalsIgnoreCase(errorNo)){
			errorText = "重复点评订单";
		}
		if(OVER.equalsIgnoreCase(errorNo)){
			errorText = "对不起，您已超过当天普通点评发表次数上限，请次日再试";
		}
		
		//cmtSpecialSubjectList = getIndexPageOfCmtSpecialSubjectList();
		recommendPlaceList = getRecruitPlaceCommentList();
		//获取点评活动
		cmtActivity = cmtActivityService.queryById(Long.valueOf(1));
		
		//获取城市自游志
		//recommendInfoMap = getYouJiRecInfoByBlockIdAndStationFromCache(BLOCKID, STATION);
		
		//获取上周热门景区排行/上周热门酒店排行
		fillHotCommentListByStage();
		
		getIndexPageOfBestCmtList();
		
		fillHotCommentListByStage();
		seoIndexPage = seoIndexPageService.getSeoIndexPageByPageCode("CH_COMMENT");
		
		//获取最新的景点点评
		lastestCmtList = getIndexPageOfLastestCmtList();
		
		//用户待点评的产品数
		if(isLogin()){
			setWaittingCommentNumber();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 设置待点评产品信息
	 * @return
	 */
	private void setWaittingCommentNumber()
	{
		List<CmtProdTitleStatisticsVO> needProductCommentInfoList = new ArrayList<CmtProdTitleStatisticsVO>();
		//获取可点评的订单信息
		List<OrderAndComment> canCommentOrderProductList = getOrderServiceProxy().selectCanCommentOrderProductByUserNo(getUser().getUserId());
		
		for(int i = 0; i < canCommentOrderProductList.size(); i++)
		{
			OrderAndComment canCommentOrderProduct = canCommentOrderProductList.get(i);
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("orderId", canCommentOrderProduct.getOrderId());
			parameters.put("productId", canCommentOrderProduct.getProductId());
			parameters.put("isHide", "displayall");
			List<CommonCmtCommentVO> cmtCommentList = cmtCommentService.getCmtCommentList(parameters);
			if(cmtCommentList == null || cmtCommentList.size() == 0)//该订单产品未被点评过，可以点评
			{
				CmtProdTitleStatisticsVO cmtProdTitleStatisticsVO = CommentUtil.composeProdTitleStatistics(canCommentOrderProduct);
				needProductCommentInfoList.add(cmtProdTitleStatisticsVO);
			}
		}
		prodsCountOfWaitingForCmt = needProductCommentInfoList.size();
	}
	
	/**
	 * 获取点评首页景点的最新点评
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	private List<CommonCmtCommentVO> getIndexPageOfLastestCmtList(){
		Object cache = MemcachedUtil.getInstance().get("CMT_INDEX_PAGE_OF_LASTEST_LIST");
		if (null != cache) {
			LOG.debug("从MemCache中获取点评首页的最新点评列表");
			return (List<CommonCmtCommentVO>) cache;
		} else {
			LOG.debug("MemCache中获取点评首页的最新点评列表不存在或已经过期，需要重新获取");
			Map<String, Object> lastestCmtParams = new HashMap<String, Object>();
			lastestCmtParams.put("getPlaceCmts", "Y");
			lastestCmtParams.put("_startRow", 0);
			lastestCmtParams.put("_endRow", NUMBER_OF_LASTEST_COMMENTS);
			lastestCmtParams.put("audit7day", true);
			List<CommonCmtCommentVO> commonCmtCommentVOList = getLastestComments(lastestCmtParams, null);
			if (null != commonCmtCommentVOList) {
				for(int i = 0; i < commonCmtCommentVOList.size(); i++)
				{
					CommonCmtCommentVO commonCmtCommentVO = commonCmtCommentVOList.get(i);
					commonCmtCommentVOList.set(i, composeComment(commonCmtCommentVO));
				}
				MemcachedUtil.getInstance().set("CMT_INDEX_PAGE_OF_LASTEST_LIST", 60*60, commonCmtCommentVOList);
			}
			return commonCmtCommentVOList;
		}
	}

	/**
	 * 获取分页功能的精华点评
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page getIndexPageOfBestCmts(long pageSize, long currentPage){

		int num = 0;
		List<CommonCmtCommentVO> bestCmts = getIndexPageOfBestCmts();
		if(bestCmts.size() > 0){
			if(bestCmts.size() < NUMBER_OF_BEST_COMMENTS){
				num = bestCmts.size();
			}else{
				num = NUMBER_OF_BEST_COMMENTS;
			}
			bestCmtsPageConfig = Page.page(num, pageSize, currentPage);
			int _startRow = (int)bestCmtsPageConfig.getStartRows();
			int _endRow = (int)bestCmtsPageConfig.getEndRows();
			bestCmtsPageConfig.setItems(bestCmts.subList(_startRow, _endRow));
			bestCmtsPageConfig.setUrl("/comment/comment!getIndexPageOfBestCmtList.do?currentPage=");
		}
		
		return bestCmtsPageConfig;
	}
	
	/**
	 * 获取30笔景点最新的精华点评
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<CommonCmtCommentVO> getIndexPageOfBestCmts(){

		Object cache = MemcachedUtil.getInstance().get("CMT_INDEX_PAGE_OF_BEST_CMTS");
		if (false && null != cache) {
			LOG.debug("从MemCache中获取点评首页的精华点评列表");
			return (List<CommonCmtCommentVO>) cache;
		} else {
			LOG.debug("MemCache中获取点评首页的精华点评列表不存在或已经过期，需要重新获取");
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("_startRow", 0);
			parameters.put("_endRow", NUMBER_OF_BEST_COMMENTS);
			parameters.put("isBest", "Y");
			parameters.put("getProductCmts", "Y"); // 获取产品点评
			parameters.put("isAudit", Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
			parameters.put("createTime321", "true");
			List<CommonCmtCommentVO> commonCmtCommentVOList = cmtCommentService.getCmtCommentList(parameters);
			if (null != commonCmtCommentVOList) {	
				for(int i = 0; i < commonCmtCommentVOList.size(); i++)
				{
					CommonCmtCommentVO commonCmtCommentVO = commonCmtCommentVOList.get(i);
					commonCmtCommentVOList.set(i, composeComment(commonCmtCommentVO));
				}
				MemcachedUtil.getInstance().set("CMT_INDEX_PAGE_OF_BEST_CMTS", 60 * 2 * 60, commonCmtCommentVOList);
			}
			return commonCmtCommentVOList;
		}
	}
	
	/**
	 * 首页ifrema引入产品精华点评
	 * @return
	 */
	@Action("/comment/comment!getIndexPageOfBestCmtList")
	public String getIndexPageOfBestCmtList() {

		bestCmtsPageConfig = getIndexPageOfBestCmts(pageSize, currentPage);

		return "indexPageOfBestCmtList";
	}
	
	/**
	 *  ----------------------  get and set property -------------------------------
	 */
	
	public List<CmtRecommendPlace> getRecommendPlaceList() {
		return recommendPlaceList;
	}

	public List<CmtSpecialSubjectVO> getCmtSpecialSubjectList() {
		return cmtSpecialSubjectList;
	}

	public List<CommonCmtCommentVO> getLastestCmtList() {
		return lastestCmtList;
	}

	public SeoIndexPage getSeoIndexPage() {
		return seoIndexPage;
	}

	public int getProdsCountOfWaitingForCmt() {
		return prodsCountOfWaitingForCmt;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public Page<CommonCmtCommentVO> getBestCmtsPageConfig() {
		return bestCmtsPageConfig;
	}

	public void setCmtActivityService(CmtActivityService cmtActivityService) {
		this.cmtActivityService = cmtActivityService;
	}

	public void setCmtActivity(CmtActivity cmtActivity) {
		this.cmtActivity = cmtActivity;
	}

	public CmtActivity getCmtActivity() {
		return cmtActivity;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public SeoIndexPageService getSeoIndexPageService() {
		return seoIndexPageService;
	}

	public void setSeoIndexPageService(SeoIndexPageService seoIndexPageService) {
		this.seoIndexPageService = seoIndexPageService;
	}

	public Map<String, List<RecommendInfo>> getRecommendInfoMap() {
		return recommendInfoMap;
	}

	public static String getStation() {
		return STATION;
	}
}
