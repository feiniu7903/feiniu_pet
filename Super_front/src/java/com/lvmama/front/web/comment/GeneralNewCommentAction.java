package com.lvmama.front.web.comment;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.comment.PlaceCmtCommentVO;
import com.lvmama.comm.vo.comment.ProductCmtCommentVO;

/**
 * 通用的获取最新点评的Action。此类试图尝试实现网站所有各种获取最新列表的实现
 *
 */
@Results({
	@Result(name = "SUCCESS", location = "/WEB-INF/pages/comment/generalComment/${targetPage}.ftl", type = "freemarker"),
	@Result(name = "count", location = "/WEB-INF/pages/comment/generalComment/count_js.ftl", type = "freemarker")
})
public class GeneralNewCommentAction extends CmtBaseAction {
	
	private static final Log LOG = LogFactory.getLog(GeneralNewCommentAction.class);
	
	private int count = 0;
	private List<CommonCmtCommentVO> comments;
	private String placeIds;
	private String startDate;
	private String endDate;
	private String stage;
	private Long amount = 0L;
	private String getCount;
	private String placeId;
	private String name = "targetName";
	private String targetPage = "list_js";
	private String getPlaceCmts;
	private String getProductCmts;
	
	private List<CmtLatitudeStatistics> cmtLatitudeStatis;
	private CmtTitleStatisticsVO cmtTitleStatisticsVO;
	
	/**
	 * 获取点评列表
	 * @return
	 * @throws Exception
	 */
	@Action("/comment/generalNewComment/getComment")
	public String getComment() throws Exception{
		
		Map<String,Object> parameters = handleParameters();			
		comments = cmtCommentService.getCmtCommentList(parameters);
		
		for(int i = 0; i < comments.size(); i++)
		{
			CommonCmtCommentVO cmtComment = comments.get(i);
			comments.set(i, composeComment(cmtComment));
		}
		
		return "SUCCESS";
	}
	
	/**
	 * 获取点评总数
	 * @return
	 */
	@Action("/comment/generalNewComment/getCommentCount")
	public String getCommentCount(){
		Map<String,Object> parameters = handleParameters();
		amount = cmtCommentService.getCommentTotalCount(parameters);
		return "count";
	}
	
	/**
	 * 点评人数
	 * @return
	 */
	@Action("/comment/generalNewComment/getCmtUserCount")
	public String getCmtUserCount() {
		Map<String,Object> parameters = handleParameters();
		amount = cmtCommentService.getCommentUserCount(parameters);
		return "count";
	}
	
	/**
	 * 获取placeId的点评及维度统计信息
	 */
	@Action("/comment/generalNewComment/queryPlaceCmtLatitudeStatis")
	public String queryPlaceCmtLatitudeStatis() throws Exception {
		
		//获取该景点的点评统计信息
		Object cache_cmtStatis = MemcachedUtil.getInstance().get("WENQUANZT_OF_CMT_STATIS_" + placeId);
		if (null != cache_cmtStatis) {
			LOG.debug(" Get data of WENQUANZT_OF_CMT_STATIS_"+ placeId +"(key) from MemCache");
			cmtTitleStatisticsVO = (CmtTitleStatisticsVO)cache_cmtStatis;
		} else {
			cmtTitleStatisticsVO = cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(Long.parseLong(placeId));	
			cache_cmtStatis = cmtTitleStatisticsVO;
			if (null != cache_cmtStatis) {
				MemcachedUtil.getInstance().set("WENQUANZT_OF_CMT_STATIS_" + placeId, 60*12*60, cache_cmtStatis);
			}
		}
		
		//获得某个景点的4个基本点评维度统计平均分
		Object cache_cmtLati = MemcachedUtil.getInstance().get("WENQUANZT_OF_CMT_LATI_" + placeId);
		if (null != cache_cmtLati) {
			LOG.debug(" Get data of WENQUANZT_OF_CMT_LATI_"+ placeId +"(key) from MemCache");
			cmtLatitudeStatis = (List<CmtLatitudeStatistics>)cache_cmtLati;
			
		} else {
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("placeId", placeId);
			cmtLatitudeStatis = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(params);
			cache_cmtLati = cmtLatitudeStatis;
			if (null != cache_cmtLati) {
				MemcachedUtil.getInstance().set("WENQUANZT_OF_CMT_LATI_" + placeId, 60*12*60, cache_cmtLati);
			}
		}
		
		return "SUCCESS";
	}
	
	/**
	 * 处理查询参数
	 * @return
	 */
	private Map<String,Object> handleParameters(){
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("isAudit", Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		if (null != placeIds) {
			List<Long> productIds = new ArrayList<Long>();
			String[] ids = placeIds.split(",");
			for (String id : ids) {
				productIds.add(new Long(id));
			}
			parameters.put("placeIds", productIds);
		}
		if (null != startDate) {
			Date start = convertDate(startDate);
			parameters.put("startDate", start);
		}		
		if (null != endDate) {
			Date end = convertDate(endDate);
			parameters.put("endDate", end);
		}		
		if (0 != count) {
			parameters.put("_startRow", 1);
			parameters.put("_endRow", count);
		}
		if (null != stage) {
			parameters.put("stage", stage);
		} 
		if (null != getCount) {
			parameters.put("getCount", getCount);
		} 
		if (null != getPlaceCmts) {
			parameters.put("getPlaceCmts", getPlaceCmts);
		} 
		if (null != getProductCmts) {
			parameters.put("getProductCmts", getProductCmts);
		} 
	 
		parameters.put("createTime321", "Y");
		return parameters;
	}
	
	/**
	 * 字符串日期转换正式日期
	 * @param str
	 * @return
	 */
	private Date convertDate(String str){		
		String pattern="yyyy-MM-dd";
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		try{
			return sdf.parse(str);
		}catch(Exception ex){
			return null;
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List getComments() {
		return comments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}

	public String getPlaceIds() {
		return placeIds;
	}

	public void setPlaceIds(String placeIds) {
		this.placeIds = placeIds;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getAmount() {
		return amount;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getGetCount() {
		return getCount;
	}

	public void setGetCount(String getCount) {
		this.getCount = getCount;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public List<CmtLatitudeStatistics> getCmtLatitudeStatis() {
		return cmtLatitudeStatis;
	}

	public CmtTitleStatisticsVO getCmtTitleStatisticsVO() {
		return cmtTitleStatisticsVO;
	}

	public void setCmtTitleStatisticsVO(CmtTitleStatisticsVO cmtTitleStatisticsVO) {
		this.cmtTitleStatisticsVO = cmtTitleStatisticsVO;
	}

	public void setGetPlaceCmts(String getPlaceCmts) {
		this.getPlaceCmts = getPlaceCmts;
	}

	public void setGetProductCmts(String getProductCmts) {
		this.getProductCmts = getProductCmts;
	}
	
}

