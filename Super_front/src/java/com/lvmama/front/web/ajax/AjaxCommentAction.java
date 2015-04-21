package com.lvmama.front.web.ajax;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.CmtReplyService;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtLatitudeVO;
import com.lvmama.comm.vo.comment.CmtProdTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CmtReplyVO;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.comment.PlaceCmtCommentVO;
import com.lvmama.comm.vo.comment.ProductCmtCommentVO;
import com.lvmama.front.web.comment.CmtBaseAction;

/**
 * Ajax的点评入口类
 *   任何和点评相关的Ajax方法都应该在此类中完成
 * @author Brian
 *
 */
public class AjaxCommentAction extends CmtBaseAction {
	/**
	 *  序列值
	 */
	private static final long serialVersionUID = 5967400581749467829L;
	/**
	 * 日志输入器
	 */
	private static final Log LOG = LogFactory.getLog(AjaxCommentAction.class);
	
	private Long commentId;
	
	/**
	 * 客户端IP点击点评有用的周期
	 */
	protected static final int IP_EXPIRY_MINUTES = 60 * 12; 
	
	/**
	 * 最新点评显示的条目数
	 */
	private int number = 10;
	
	/**
	 * placeID
	 */
	private Long placeId;
	private String productIDs;
	private String content;
	private CmtReplyService cmtReplyService;
	
	private int count = 0;
	private String placeIds;
	private String startDate;
	private String endDate;
	private String stage;
	private String getCount;
	private String getPlaceCmts;
	private String getProductCmts;
	/*
	 * 用户待点评的产品数
	 */
	private int waittingCommentNumber;
	private Long cmtCount;//点评数
	private Long userCount; //点评人数
	//点评列表
	private List<CommonCmtCommentVO> comments;
	private String userId;
	/**
	 * 获取待审核与审核通过状态最新的点评
	public void queryLastestComments() throws Exception {
		
		//获取最新的景点点评
		Map<String, Object> lastestCmtParams = new HashMap<String, Object>();
		lastestCmtParams.put("getPlaceCmts", "Y");
		lastestCmtParams.put("_startRow", 0);
		lastestCmtParams.put("_endRow", number);
		
		List<CmtCommentVO> cmtVO = getLastestComments(lastestCmtParams, null);
		printRtn(cmtVO);
	}
	 */
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 客户端点击点评"有用"
	 * 一客户端在12小时内只能点击一次
	 */
	@Action("/comment/ajax/addUsefulCountOfCmt")
	public void addUsefulCountOfCmt() throws Exception {
		AjaxUsefulCountOfCommentRtn rtn = new AjaxUsefulCountOfCommentRtn();
		String ip = InternetProtocol.getRemoteAddr(getRequest());
		if(commentId == null)
		{
			rtn.setIp(ip);
			rtn.setResult(false);
		}
		else
		{
			String key =  "PC_ADDUSERFULCOUNTOFCMT_KEY"+ip + "." + commentId;
			Object cache = MemcachedUtil.getInstance().get(key);
			if (null != cache) {
				rtn.setIp(ip);
				rtn.setResult(false);
			} else {
				LOG.debug("MemCache已经过期，需要重新获取");
				CommonCmtCommentVO cmtComment = cmtCommentService.getCmtCommentByKey(commentId);
				cmtCommentService.addUsefulCount(commentId);
				
				rtn.setCount(cmtComment.getUsefulCount() + 1);
				rtn.setIp(ip);
				rtn.setResult(true);
				MemcachedUtil.getInstance().set(key, IP_EXPIRY_MINUTES*60, rtn);
			}
		}
		printRtn(rtn);
	}
	
	/**
	 * 获取placeId的点评统计信息
	 */
	@Action("/comment/ajax/queryPlaceCmtStatistics")
	public void queryPlaceCmtStatistics() throws Exception {
		CmtTitleStatisticsVO vo = composeCmtTitleStatistics(cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(placeId));
		
		printRtn(vo);
	}	
	
	/**
	 * 获取placeId的点评排行
	 * @author nixianjun 2014 3 17
	 */
	@Action("/comment/ajax/queryPlaceCmtSort")
	public void queryPlaceCmtSort() throws Exception {
		//初试化查询条件(上月所有的通过审核的景点点评数据)
		Map<String, Object> parameters = super.InitParamOfHotCommentListByStage();
		//获取景点数据
		Object cacheIndexOfHotScenicSpot = MemcachedUtil.getInstance().get("INDEX_OF_HOT_SCENIC_SPOT");
		if (null == cacheIndexOfHotScenicSpot) {
			LOG.debug("MemCache中获取景点热评列表不存在或已经过期，需要重新获取");

			parameters.put("stage", Constant.STAGE_OF_SCENIC_SPOT);
			indexOfHotScenicSpot = cmtTitleStatistisService.queryHotCommentStatisticsOfPlace(parameters);
			for(int i = 0; i < indexOfHotScenicSpot.size(); i++)
			{
				indexOfHotScenicSpot.set(i, composeCmtTitleStatistics(indexOfHotScenicSpot.get(i)));
			}
 		} else {
			LOG.debug("从MemCache中获取景点热评列表");
			indexOfHotScenicSpot = (List<CmtTitleStatisticsVO>) cacheIndexOfHotScenicSpot;
		}
		printRtn(indexOfHotScenicSpot);
	}	
	/**
	 * 获取placeId的点评统计信息
	 */
	@Action("/comment/ajax/addReply")
	public void addReply() throws Exception {
		AjaxRtnBean rtn = new AjaxRtnBean(false,"");
		UserUser user = getUser();
		if (null == commentId || null == user) {
			printRtn(rtn);
		}
		
		/*增加回复记录*/
		CmtReplyVO cmtReply = new CmtReplyVO();
		cmtReply.setCommentId(commentId);
		String contentUtf= new String(getRequest().getParameter("content").toString().getBytes("iso8859_1"), "UTF-8");  
		cmtReply.setContent(changeContent(contentUtf));
		cmtReply.setUserName(user.getUserName());
		cmtReply.setUserId(user.getId());
		cmtReplyService.insert(cmtReply);		
		
		rtn.setSuccess(true);
		printRtn(rtn);
	}
	
	/**
	 * 获取productId的点评统计信息
	@Action("/comment/ajax/queryProductCmtStatistics")
	public void queryProductCmtStatistics() throws Exception {
		CmtCommentStatisticsVO vo = cmtTitleStatistisService.getCmtTitleStatisticsByProductId(productId);
		printRtn(vo);
	}*/
	
	/**
	 * 获得产品点评列表
	 * @throws IOException
	 */
	@Action("/comment/ajax/getProductComments")
	public void getProductComments() throws IOException 
	{
		List<AjaxRtnComment> ajaxRtnCommentList = new ArrayList<AjaxRtnComment>();
		if(this.productIDs != null)//为了保护如果睿广取不到产品ID，但是用户还是看了相关产品评论页
		{
			String[] productIDsArray = this.productIDs.split(",");
			List<String> productIDsList = new ArrayList<String>();
			for (String productID : productIDsArray) {
				productIDsList.add(productID);
			}
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("productIds", productIDsList);
			parameters.put("isAudit", Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
			parameters.put("_startRow", 0);
			parameters.put("_endRow", 5);
			parameters.put("createTime321", "true");
			List<CommonCmtCommentVO> commentList = cmtCommentService.getCmtCommentList(parameters);
			commentList = cmtCommentService.composeUserImagOfComment(commentList);
			
			for(int i = 0; i < commentList.size(); i++)
			{
				CommonCmtCommentVO cmtCommentVO = commentList.get(i);
				AjaxRtnComment ajaxRtnComment = convertCmtCommentVOToAjaxRtnComment((ProductCmtCommentVO)composeComment(cmtCommentVO));
				ajaxRtnCommentList.add(ajaxRtnComment);
			}
			long commentCount = cmtCommentService.getCommentTotalCount(parameters);
			printRtn(commentCount,ajaxRtnCommentList);
		}
		else
		{
			printRtn(0,ajaxRtnCommentList);
		}

	}
	
	/**
	 * 获得产品点评数量
	 * @return
	 * @throws IOException
	 */
	@Action("/comment/ajax/getProductCommentsCount")
	public void getProductCommentsCount() throws IOException 
	{
		String[] productIDsArray = getProductIDs().split(",");
		List<String> productIDsList = new ArrayList<String>();
		for (String productID : productIDsArray) {
			productIDsList.add(productID);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productIds", productIDsList);
		parameters.put("isAudit", Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		
		long productCommentsCount = cmtCommentService.getCommentTotalCount(parameters);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("productCommentsCount", Long.valueOf(productCommentsCount));
		printRtn(jsonObject);

	}
	/**
	 * 获取点评数量和参与人数，此接口供PHP调用
	 * @author liudong
	 * @throws IOException 
	 */
	@Action("/comment/ajax/getCmtAndUserCount")
	public void getCmtAndUserCount() throws IOException{
		Map<String,Object> parameters = handleParameters();
		JSONObject jsonObject = new JSONObject();
		String key="";
		if(startDate!=null){
			 key= "COMMENT_COUNT_JSONOBJECT"+startDate;
		}else{
			key="COMMENT_COUNT_JSONOBJECT";
		}
		Object obj = MemcachedUtil.getInstance().get(key);
		if(obj!=null&&obj instanceof JSONObject){
			LOG.debug(" Get data of "+key+" from MemCache");
			jsonObject = (JSONObject)obj;
		}else{
			 cmtCount = cmtCommentService.getCommentTotalCount(parameters);
			 userCount = cmtCommentService.getCommentUserCount(parameters);
			jsonObject.put("cmtCount", cmtCount);
			jsonObject.put("userCount", userCount);
			MemcachedUtil.getInstance().set(key, 60*5, jsonObject);
		}
		printRtn(jsonObject);
	}
	/**
	 * 获取待点评产品数，此接口供PHP调用
	 * @author liudong
	 * @throws IOException 
	 */
	@Action("/comment/ajax/prodsCountOfWaitingForCmt")
	public void prodsCountOfWaitingForCmt() throws IOException{
		JSONObject jsonObject = new JSONObject();
		if(!StringUtils.isEmpty(userId)){
			String key = "WAITING_CMT_JSONOBJECT"+userId;
			Object obj = MemcachedUtil.getInstance().get(key);
			if(obj!=null&&obj instanceof JSONObject){
				LOG.debug(" Get data of "+key+" from MemCache");
				jsonObject = (JSONObject)obj;
			}else{
				setWaittingCommentNumber();
				jsonObject.put("waittingCommentNumber", waittingCommentNumber);
				MemcachedUtil.getInstance().set(key, 60*5, jsonObject);
			}
		}
		printRtn(jsonObject);
	}
	
	/**
	 * 获取最新点评，此接口供PHP调用
	 * @author liudong
	 * @throws IOException 
	 */
	@Action("/comment/ajax/getNewComment")
	public void getNewComment() throws IOException{
		Map<String,Object> parameters = handleParameters();
		List<AjaxRtnComment> ajaxRtnCommentList = new ArrayList<AjaxRtnComment>();
		String key = "NEW_COMMENT_LIST"+count;
		Object obj = MemcachedUtil.getInstance().get(key);
		if(obj!=null&&obj instanceof java.util.Collection){
			LOG.debug(" Get data of "+key+" from MemCache");
			ajaxRtnCommentList = (List<AjaxRtnComment>)obj;
		}else{
			comments = cmtCommentService.getCmtCommentList(parameters);
			for(int i = 0; i < comments.size(); i++)
			{
				CommonCmtCommentVO cmtCommentVO = comments.get(i);
				if(cmtCommentVO.getProductId()!=null){
					AjaxRtnComment ajaxRtnComment = convertCmtCommentVOToAjaxRtnComment((ProductCmtCommentVO)composeComment(cmtCommentVO));
					ajaxRtnCommentList.add(ajaxRtnComment);
				}else if(cmtCommentVO.getPlaceId()!=null){
					AjaxRtnComment ajaxRtnComment = convertCmtCommentVOToAjaxRtnComment2((PlaceCmtCommentVO)composeComment(cmtCommentVO));
					ajaxRtnCommentList.add(ajaxRtnComment);
				}
				
			}
			MemcachedUtil.getInstance().set(key, 60*5, ajaxRtnCommentList);	
		}
		
		printRtn(count,ajaxRtnCommentList);
	}
	
	/**
	 * 设置待点评产品信息
	 * @return
	 */
	private void setWaittingCommentNumber()
	{
		List<CmtProdTitleStatisticsVO> needProductCommentInfoList = new ArrayList<CmtProdTitleStatisticsVO>();
		//获取可点评的订单信息
		List<OrderAndComment> canCommentOrderProductList = orderServiceProxy.selectCanCommentOrderProductByUserNo(userId);
		
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
		waittingCommentNumber = needProductCommentInfoList.size();
	}
//	/**
//	 * 根据关键字搜索景区或目的地
//	 * @throws IOException
//	 */
//	public void getJsonByKeyWordSel() throws IOException {
//		String keyWord = getRequest().getParameter("q");
//		String stage = getRequest().getParameter("stage");
//		/*String superName = new String(keyWord.getBytes("iso-8859-1"), "utf-8");*/
//		List<Place> places = placeService.getPlacesByKeyword(stage, keyWord);
//		String result = "";		
//		for (Place place:places) {				
//			if(place.getId() != null && place.getName() != null && place.getStage() != null) {
//				String city = place.getCity()!=null ? place.getCity():"";
//				String captal = place.getProvice() != null ? place.getProvice() : "";
//				if("1".equals(place.getStage())) {
//					result+="<li><label><input type='radio' name='placeId' value='"+place.getId()+"'/>"+place.getName()+" - "+captal+"</label></li>\n";
//				} else {
//					result+="<li><label><input type='radio' name='placeId' value='"+place.getId()+"'/>"+place.getName()+" - "+captal+","+city+"</label></li>\n";
//				}
//			}			
//		}		
//		getResponse().setContentType("text/json; charset=gb2312");
//		getResponse().getWriter().write(result);
//	}
	
	/**
	 * 转换CmtCommentVO到AJAX对象
	 * @param cmtCommentVO
	 * @return
	 */
	private  AjaxRtnComment convertCmtCommentVOToAjaxRtnComment(ProductCmtCommentVO cmtCommentVO)
	{
		AjaxRtnComment ajaxRtnComment = new AjaxRtnComment();
		ajaxRtnComment.setCommentId(cmtCommentVO.getCommentId());
		ajaxRtnComment.setUserName(StringUtil.replaceOrCutUserName(16,cmtCommentVO.getUserName()));//隐藏手机号账号
		ajaxRtnComment.setUserImg(cmtCommentVO.getUserImg());
		ajaxRtnComment.setCashRefundYuan(cmtCommentVO.getCashRefundYuan());
		ajaxRtnComment.setPoint(cmtCommentVO.getPoint());
		ajaxRtnComment.setContent(cmtCommentVO.getContent());
		ajaxRtnComment.setReplyCount(cmtCommentVO.getReplyCount());
		ajaxRtnComment.setProductId(cmtCommentVO.getProductId());
		ajaxRtnComment.setProductName(cmtCommentVO.getProductName());
		ajaxRtnComment.setFormatterCreatedDate(cmtCommentVO.getFormatterCreatedTime());
		ajaxRtnComment.setSumaryLatitude(convertCmtLatitudeVOToAjaxRtnCommentLatitude(cmtCommentVO.getSumaryLatitude()));
		ajaxRtnComment.setShamUsefulCount(cmtCommentVO.getShamUsefulCount());
		ajaxRtnComment.setSellable(String.valueOf(cmtCommentVO.getProductOfCommentSellable()));
		ajaxRtnComment.setLvmamaReplyCount(cmtCommentVO.getLvmamaReplyCount());
		ajaxRtnComment.setProductChannel(cmtCommentVO.getProductChannel());
		List<AjaxRtnCommentLatitude>  ajaxRtnCommentLatitudeList = new ArrayList<AjaxRtnCommentLatitude>();
		List<CmtLatitudeVO> cmtLatitudeVOList = cmtCommentVO.getCmtLatitudes();
		if(cmtLatitudeVOList != null)
		{
			for(int j = 0; j < cmtLatitudeVOList.size(); j++)
			{
				ajaxRtnCommentLatitudeList.add(convertCmtLatitudeVOToAjaxRtnCommentLatitude(cmtLatitudeVOList.get(j)));
			}
		}
		ajaxRtnComment.setCmtLatitudes(ajaxRtnCommentLatitudeList);
		return ajaxRtnComment;
	}
	
	/**
	 * 转换CmtCommentVO到AJAX对象
	 * @param cmtCommentVO
	 * @return
	 */
	private  AjaxRtnComment convertCmtCommentVOToAjaxRtnComment2(PlaceCmtCommentVO cmtCommentVO)
	{
		AjaxRtnComment ajaxRtnComment = new AjaxRtnComment();
		ajaxRtnComment.setCommentId(cmtCommentVO.getCommentId());
		if(cmtCommentVO.getUserName()!=null){
			ajaxRtnComment.setUserName(StringUtil.replaceOrCutUserName(16,cmtCommentVO.getUserName()));//隐藏手机号账号
		}else{
			ajaxRtnComment.setUserName("匿名");//用户名为空，显示匿名
		}	
		ajaxRtnComment.setUserImg(cmtCommentVO.getUserImg());
		ajaxRtnComment.setCashRefundYuan(cmtCommentVO.getCashRefundYuan());
		ajaxRtnComment.setPoint(cmtCommentVO.getPoint());
		ajaxRtnComment.setContent(cmtCommentVO.getContent());
		ajaxRtnComment.setReplyCount(cmtCommentVO.getReplyCount());
		ajaxRtnComment.setPlaceId(cmtCommentVO.getPlaceId());
		ajaxRtnComment.setPlaceName(cmtCommentVO.getPlaceName());
		ajaxRtnComment.setStage(cmtCommentVO.getStage());
		ajaxRtnComment.setPinYinUrl(cmtCommentVO.getPinYinUrl());
		ajaxRtnComment.setFormatterCreatedDate(cmtCommentVO.getFormatterCreatedTime());
		ajaxRtnComment.setSumaryLatitude(convertCmtLatitudeVOToAjaxRtnCommentLatitude(cmtCommentVO.getSumaryLatitude()));
		ajaxRtnComment.setShamUsefulCount(cmtCommentVO.getShamUsefulCount());
		ajaxRtnComment.setLvmamaReplyCount(cmtCommentVO.getLvmamaReplyCount());
		List<AjaxRtnCommentLatitude>  ajaxRtnCommentLatitudeList = new ArrayList<AjaxRtnCommentLatitude>();
		List<CmtLatitudeVO> cmtLatitudeVOList = cmtCommentVO.getCmtLatitudes();
		if(cmtLatitudeVOList != null)
		{
			for(int j = 0; j < cmtLatitudeVOList.size(); j++)
			{
				ajaxRtnCommentLatitudeList.add(convertCmtLatitudeVOToAjaxRtnCommentLatitude(cmtLatitudeVOList.get(j)));
			}
		}
		ajaxRtnComment.setCmtLatitudes(ajaxRtnCommentLatitudeList);
		return ajaxRtnComment;
	}
	/**
	 * 输出返回码
	 * @param bean
	 * @throws IOException
	 */
	private void printRtn(final long commentCount, final List<AjaxRtnComment> lists) throws IOException {
		ServletActionContext.getResponse().setContentType("text/json; charset=utf-8");
		if (ServletActionContext.getRequest().getParameter("jsoncallback") == null) {
			String ajaxCommentResult = "{\"commentCount\":\""+commentCount+"\",\"data\":"+ JSONArray.fromObject(lists).toString()+"}";
			ServletActionContext.getResponse().getWriter().print(ajaxCommentResult);
			
		} else {
			ServletActionContext.getResponse().getWriter().print(ServletActionContext.getRequest().getParameter("jsoncallback") + "(" + JSONArray.fromObject(lists).toString()+ ")");
		}
	}
	 
	/**
	 * 转换CmtLatitudeVO到AJAX对象
	 * @param cmtLatitudeVO
	 * @return
	 */
	private AjaxRtnCommentLatitude convertCmtLatitudeVOToAjaxRtnCommentLatitude(CmtLatitudeVO cmtLatitudeVO)
	{
		if(cmtLatitudeVO != null)
		{
			AjaxRtnCommentLatitude ajaxRtnCommentLatitude = new AjaxRtnCommentLatitude();
			ajaxRtnCommentLatitude.setCmtLatitudeId(cmtLatitudeVO.getCmtLatitudeId());
			ajaxRtnCommentLatitude.setCommentId(cmtLatitudeVO.getCommentId());
			ajaxRtnCommentLatitude.setLatitudeId(cmtLatitudeVO.getLatitudeId());
			ajaxRtnCommentLatitude.setLatitudeName(cmtLatitudeVO.getLatitudeName());
			ajaxRtnCommentLatitude.setScore(cmtLatitudeVO.getScore());
			return ajaxRtnCommentLatitude;
		}
		else
		{
			return null;
		}
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
	public void setNumber(final int number) {
		this.number = number;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setNumOfRecommendPlace(int numOfRecommendPlace) {
		this.numOfRecommendPlace = numOfRecommendPlace;
	}

	public void setStageOfRecommendPlace(int stageOfRecommendPlace) {
		this.stageOfRecommendPlace = stageOfRecommendPlace;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setProductIDs(String productIDs) {
		this.productIDs = productIDs;
	}

	public String getProductIDs() {
		return productIDs;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCmtReplyService(CmtReplyService cmtReplyService) {
		this.cmtReplyService = cmtReplyService;
	}

	public String getStartDate() {
		return startDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getEndDate() {
		return endDate;
	}
	
}
