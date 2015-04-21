package com.lvmama.front.web.comment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.comment.CmtRecommendPlace;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.comment.CmtRecommendPlaceService;
import com.lvmama.comm.pet.service.comment.CmtSpecialSubjectService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.comment.DicCommentLatitudeService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.search.PlaceSearchInfoService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtSpecialSubjectVO;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.front.web.BaseAction;


@Results({
	@Result(name = "404", type="dispatcher",location="/404.jsp"),
	@Result(name = "paramError", location = "/comment/comment.do", type = "redirect")
})
public class CmtBaseAction extends BaseAction {
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 5322646978214412705L;
	/**
	 * 景区点评统计服务接口
	 */
	protected CmtTitleStatistisService cmtTitleStatistisService;
	/**
	 * 点评招募服务接口
	 */
	private CmtRecommendPlaceService cmtRecommendPlaceService;
	/**
	 * 点评服务接口
	 */
	protected CmtCommentService cmtCommentService;
	/**
	 * 放在MemCache中的对象的默认过期分钟数
	 */
	protected static final int EXPIRY_MINUTES = 60 * 12; 
	/**
	 * 点评招募目的地数目
	 */
	protected int numOfRecommendPlace = 3;
	/**
	 * 点评招募目的地类型
	 */
	protected int stageOfRecommendPlace = 2;
	/**
	 * 首页精华点评的显示条目数
	 */
	protected int numOfBestCmtEntries = 3;

	/**
	 * 404错误的页面
	 */
	static final String ERROR_PAGE = "error";
	
	/**
	 * 404错误的页面
	 */
	static final String OVER = "OVER";
	
	/**
	 * 热评景区和酒店的列表
	 */
	protected List<CmtTitleStatisticsVO> indexOfHotScenicSpot = new ArrayList<CmtTitleStatisticsVO>();
	protected List<CmtTitleStatisticsVO> indexOfHotHotel = new ArrayList<CmtTitleStatisticsVO>();
	
	/**
	 * 首页热门景区和酒店的显示条目数
	 */
	private static final int NUNBER_OF_HOT_PLACES_ENTRIES = 10;
	/**
	 * nsso的远程调用接口
	 */
	protected UserUserProxy userUserProxy;
	
	protected UserUser users;
	private String userId;
	/**
	 * 专题管理逻辑接口
	 */
	protected CmtSpecialSubjectService cmtSpecialSubjectService;
	
	protected String errorText;
	
	protected String errorNo;
	
	protected PlaceService placeService;
	
	protected OrderService orderServiceProxy;
	
	protected CmtLatitudeStatistisService cmtLatitudeStatistisService;
	
	/**
	 * 点评维度接口
	 */
	protected DicCommentLatitudeService dicCommentLatitudeService;
	
	protected PlaceSearchInfoService placeSearchInfoService;
	
	protected ProdProductService prodProductService;
	/**
	 * SUPER后台推送
	 */
	protected RecommendInfoService recommendInfoService;
	
	/**
	 * 获取最新点评
	 * CommonCmtCommentVO 已经完成内部景点或产品属性的组装
	 * @return
	 */
	protected List<CommonCmtCommentVO> getLastestComments(Map<String, Object> parameters, String userId) {
		
		if (StringUtils.isNotEmpty("userId")) {
			parameters.put("userId", userId);
		}
		parameters.put("isAudit", Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		parameters.put("createTime321", "true");
		List<CommonCmtCommentVO> list = cmtCommentService.getCmtCommentList(parameters);
		
		if(list != null && list.size() > 0){
			List<CommonCmtCommentVO> lastestCommentList = new ArrayList<CommonCmtCommentVO>();
			for(CommonCmtCommentVO vo : list){
				lastestCommentList.add(composeComment(vo));//属性的组装
			} 
			return lastestCommentList;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取目的地.点评招募列表(点评数为0)
	 * @return
	 */
	protected List<CmtRecommendPlace> getRecruitPlaceCommentList(){
		List<CmtRecommendPlace> cmtRecommendPlaceList =  cmtRecommendPlaceService.queryRecommendPlace();
		for(int i = 0; i < cmtRecommendPlaceList.size(); i++)
		{
			CmtRecommendPlace cmtRecommendPlace = cmtRecommendPlaceList.get(i);
			Place place = placeService.queryPlaceByPlaceId(cmtRecommendPlace.getPlaceId());
			cmtRecommendPlace.setPlaceName(place.getName());
			cmtRecommendPlace.setPinYinUrl(place.getPinYinUrl());
			PlaceSearchInfo placeSearchInfo = placeSearchInfoService.getPlaceSearchInfoByPlaceId(place.getPlaceId());
			cmtRecommendPlace.setPlaceLargeImage(placeSearchInfo.getLargeImage());
		}
		return cmtRecommendPlaceList;
	}
	
	/**
	 * 获取推荐的点评
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<CommonCmtCommentVO> getRecomendComment(int _startRow, int _endRow) {
		Object cache = MemcachedUtil.getInstance().get("INDEX_OF_RECOMEND_COMMENT_LIST");
		if (null != cache) {
			LOG.debug("从MemCache中获取推荐点评列表");
		} else {
			LOG.debug("MemCache中获取推荐点评列表不存在或已经过期，需要重新获取");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isRecomend", "Y");
			parameters.put("_startRow", _startRow);
			parameters.put("_endRow", _endRow);
			parameters.put("createTime321", "true");
			cache = cmtCommentService.getCmtCommentList(parameters);
			MemcachedUtil.getInstance().set("INDEX_OF_RECOMEND_COMMENT_LIST", EXPIRY_MINUTES*60, cache);
		}
		return (List<CommonCmtCommentVO>) cache;
	}
	
	/**
	 * 根据景区类型填充指定城市的热评列表
	 * 
	 * 查询时间内 的点评数量,排序,取10条,取点评数
	 * 获取上周热门景区排行/上周热门酒店排行
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void fillHotCommentListByStage() {

		//初试化查询条件(上月所有的通过审核的景点点评数据)
		Map<String, Object> parameters = InitParamOfHotCommentListByStage();
		
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
			MemcachedUtil.getInstance().set("INDEX_OF_HOT_SCENIC_SPOT", EXPIRY_MINUTES*60, indexOfHotScenicSpot);
		} else {
			LOG.debug("从MemCache中获取景点热评列表");
			indexOfHotScenicSpot = (List<CmtTitleStatisticsVO>) cacheIndexOfHotScenicSpot;
		}
		
		//获取酒点数据
		Object cacheIndexOfHotHotel = MemcachedUtil.getInstance().get("INDEX_OF_HOT_HOTEL");
		if (null == cacheIndexOfHotHotel) {
			LOG.debug("MemCache中获取酒点热评列表不存在或已经过期，需要重新获取");

			parameters.put("stage", Constant.STAGE_OF_HOTEL);
			indexOfHotHotel = cmtTitleStatistisService.queryHotCommentStatisticsOfPlace(parameters);
			for(int i = 0; i < indexOfHotHotel.size(); i++)
			{
				indexOfHotHotel.set(i, composeCmtTitleStatistics(indexOfHotHotel.get(i)));
			}
			MemcachedUtil.getInstance().set("INDEX_OF_HOT_HOTEL", EXPIRY_MINUTES*60, indexOfHotHotel);
		} else {
			LOG.debug("从MemCache中获取酒点热评列表");
			indexOfHotHotel = (List<CmtTitleStatisticsVO>) cacheIndexOfHotHotel;
		}
	}
	
	public Map<String, Object> InitParamOfHotCommentListByStage(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date endDate = cal.getTime();
		cal.add(Calendar.MONTH, -8);
		Date startDate = cal.getTime();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("startDate", startDate);
		parameters.put("endDate", endDate);
		parameters.put("_startRow", 0);
		parameters.put("_endRow", NUNBER_OF_HOT_PLACES_ENTRIES);
		parameters.put("isAudit",
				Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		
		return parameters;
	}
	
	/**
	 * 获取点评首页专题内容前五笔记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<CmtSpecialSubjectVO> getIndexPageOfCmtSpecialSubjectList(){
		Object cache = MemcachedUtil.getInstance().get("CMT_INDEX_PAGE_OF_SPECIAL_SUBJECT_LIST");
		if (null != cache) {
			LOG.debug("从MemCache中获取点评首页的专题内容");
		} else {
			LOG.debug("MemCache中获取点评首页的专题内容不存在或已经过期，需要重新获取");
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("_startRow", "0");
			params.put("_endRow", "5");
			cache = getCmtSpecialSubjectService().query(params);
			if (null != cache) {
				MemcachedUtil.getInstance().set("CMT_INDEX_PAGE_OF_SPECIAL_SUBJECT_LIST", EXPIRY_MINUTES*60, cache);
			}
		}
		return (List<CmtSpecialSubjectVO>) cache;
	}
	
	/**
	 * 从cache(1天)获取城市游志
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, List<RecommendInfo>> getYouJiRecInfoByBlockIdAndStationFromCache(Long blockId, String station) {
		
		Object cache = MemcachedUtil.getInstance().get("YOUJI_RECOMMEND_INFO_OF_INDEX");
		if (null != cache) {
			LOG.debug("get RECOMMEND_INFO_OF_INDEX from MemCache");
		} else {
			cache = recommendInfoService.getRecommendInfoByParentBlockIdAndPageChannel(blockId, station);
			if(cache != null)
			{
				MemcachedUtil.getInstance().set("YOUJI_RECOMMEND_INFO_OF_INDEX", 60 * 24 * 60, cache);
			}
		}
		return (Map<String, List<RecommendInfo>>) cache;
	}
	
	/**
	 * 过滤内容
	 * @param content
	 * @return
	 */
	protected String changeContent(final String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}  else {
			String contentStr = StringUtil.filterOutHTMLTags(content);
			return contentStr;
		}
	}	
	
	public boolean isLogin() {
		users = getUser();
		if (users == null) {
			debug("用户USERID：" + userId + "号不存在");
			userId = null;
			return false;
		}
		else
		{
			userId = users.getUserId();
			return true;
		}	
	}
	
	public UserUser getUser() {
		UserUser users = (UserUser)getSession(com.lvmama.comm.vo.Constant.SESSION_FRONT_USER);
		if (users == null) {
			debug("user not exists");
		}
		return users;
	}
	
	
	/**
	 * 输出返回码
	 * @param request request
	 * @param response response
	 * @param object Ajax返回的对象
	 * @throws IOException IOException
	 */
	protected void printRtn(final Object object) throws IOException {
		String json = null;
		getResponse().setContentType("text/json; charset=utf-8");
		if (null == object) {
			return;
		} else {
			if (object instanceof java.util.Collection) {
				json = JSONArray.fromObject(object).toString();
			} else {
				json = JSONObject.fromObject(object).toString();
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("返回对象:" + json);
			}
		}
		if (getRequest().getParameter("jsoncallback") == null) {
			getResponse().getWriter().print(json);
		} else {
				getResponse().getWriter().print(getRequest().getParameter(
						"jsoncallback") + "(" +json + ")");				
		}
	}
	
	protected CmtTitleStatisticsVO composeCmtTitleStatistics(CmtTitleStatisticsVO cmtTitleStatisticsVO)
	{
		if (null != cmtTitleStatisticsVO && null != cmtTitleStatisticsVO.getProductId()) {
			ProdProduct product = queryProductInfoByProductId(cmtTitleStatisticsVO.getProductId());
			return CommentUtil.composeProdTitleStatistics(cmtTitleStatisticsVO, product);
		} 
		else if(null != cmtTitleStatisticsVO && null != cmtTitleStatisticsVO.getPlaceId()){ //包含老体验点评(没ProductId)和普通点评Constant.EXPERIENCE_COMMON_TYPE
			PlaceSearchInfo placeSearchInfo = placeSearchInfoService.getPlaceSearchInfoByPlaceId(cmtTitleStatisticsVO.getPlaceId());
			return CommentUtil.composePlaceTitleStatistics(cmtTitleStatisticsVO, placeSearchInfo);
		}else{
			//默认处理，不可能进这个流程
			return cmtTitleStatisticsVO;
		}
	}
	
	/**
	 * 只要传入commonCmtCommentVO对象自动内部完成景点或产品属性的组装
	 * @param commonCmtCommentVO
	 * @return
	 */
	protected CommonCmtCommentVO composeComment(CommonCmtCommentVO commonCmtCommentVO){
		if (commonCmtCommentVO.getCmtType().equals(Constant.EXPERIENCE_COMMENT_TYPE)
				&& (null != commonCmtCommentVO.getProductId())) {
				ProdProduct product = queryProductInfoByProductId(commonCmtCommentVO.getProductId());
				List<ProdProductChannel> channelList = queryProductChannelListByProductId(commonCmtCommentVO.getProductId());
				return CommentUtil.composeProductComment(commonCmtCommentVO, product, channelList);
		} 
		else if(null != commonCmtCommentVO.getPlaceId()){ //包含老体验点评(没ProductId)和普通点评Constant.EXPERIENCE_COMMON_TYPE
			Place place = placeService.queryPlaceByPlaceId(commonCmtCommentVO.getPlaceId());
			return CommentUtil.composePlaceComment(commonCmtCommentVO, place);
		}else{
			//默认处理，不可能进这个流程
			return commonCmtCommentVO;
		}
	}
	
	
	/**
	 * 查询点评所对应的产品
	 * @param productId
	 * @return
	 */
	protected ProdProduct queryProductInfoByProductId(Long productId){
		if(productId == null)
			return null;
		ProdProduct product = prodProductService.getProdProductById(productId);
		if(product == null)
		{
			log.error("get product for comment is null "+productId);
		}
		return product;
	}
	
	/**
	 * 查询产品对应渠道列表
	 * @param productId
	 * @return
	 */
	protected List<ProdProductChannel> queryProductChannelListByProductId(Long productId)
	{
		if(productId == null)
			return null;
		return prodProductService.getProductChannelByProductId(productId);
	}

	//设置用户图像
	protected List<CommonCmtCommentVO> composeUserImagOfComment(List<CommonCmtCommentVO> cmtCommentList){
		/*if(cmtCommentList != null && cmtCommentList.size() > 0 && cmtCommentList.size() < 1000){
			List<Long> userIdList = new ArrayList<Long>();
			for(CommonCmtCommentVO vo : cmtCommentList){
				userIdList.add(vo.getUserId());
			}
			List<UserUser> userUserList = userUserProxy.queryUserUserByUserId(userIdList);
			
			//设置用户图像给CmtCommentVO
			if(cmtCommentList != null && cmtCommentList.size() > 0){
			    for (int i = 0; i < cmtCommentList.size(); i++) {
			    	for(UserUser userUser : userUserList){
			    		CommonCmtCommentVO cmtVo = cmtCommentList.get(i);
			    		if(cmtVo.getUserId() != null && (cmtVo.getUserId().longValue() == userUser.getId().longValue())){
			    			cmtCommentList.get(i).setUserImg(userUser.getImageUrl());
			    		}
			    	}
				}
			}
		}*/
		return cmtCommentList;
	}
	
	protected void debug(final String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}
	
	/**
	 *  ------------------------------------  get and set property -------------------------------------------
	 */
	
	public Long getCmtCount() {
		return cmtCommentService.getCommentTotalCount(null);
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public void setNumOfRecommendPlace(int numOfRecommendPlace) {
		this.numOfRecommendPlace = numOfRecommendPlace;
	}

	public void setStageOfRecommendPlace(int stageOfRecommendPlace) {
		this.stageOfRecommendPlace = stageOfRecommendPlace;
	}

	public void setNumOfBestCmtEntries(int numOfBestCmtEntries) {
		this.numOfBestCmtEntries = numOfBestCmtEntries;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserUser getUsers() {
		return users;
	}


	public List<CmtTitleStatisticsVO> getIndexOfHotScenicSpot() {
		return indexOfHotScenicSpot;
	}

	public List<CmtTitleStatisticsVO> getIndexOfHotHotel() {
		return indexOfHotHotel;
	}

	/**
	 * @param cmtSpecialSubjectService the cmtSpecialSubjectService to set
	 */
	public void setCmtSpecialSubjectService(CmtSpecialSubjectService cmtSpecialSubjectService) {
		this.cmtSpecialSubjectService = cmtSpecialSubjectService;
	}

	/**
	 * @return the cmtSpecialSubjectService
	 */
	public CmtSpecialSubjectService getCmtSpecialSubjectService() {
		return cmtSpecialSubjectService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public void setCmtTitleStatistisService(
			CmtTitleStatistisService cmtTitleStatistisService) {
		this.cmtTitleStatistisService = cmtTitleStatistisService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public int getNumOfRecommendPlace() {
		return numOfRecommendPlace;
	}

	public int getStageOfRecommendPlace() {
		return stageOfRecommendPlace;
	}

	public int getNumOfBestCmtEntries() {
		return numOfBestCmtEntries;
	}

	public CmtLatitudeStatistisService getCmtLatitudeStatistisService() {
		return cmtLatitudeStatistisService;
	}

	public void setCmtLatitudeStatistisService(
			CmtLatitudeStatistisService cmtLatitudeStatistisService) {
		this.cmtLatitudeStatistisService = cmtLatitudeStatistisService;
	}
	
	public void setCmtRecommendPlaceService(CmtRecommendPlaceService cmtRecommendPlaceService) {
		this.cmtRecommendPlaceService = cmtRecommendPlaceService;
	}
	
	public void setDicCommentLatitudeService(
			DicCommentLatitudeService dicCommentLatitudeService) {
		this.dicCommentLatitudeService = dicCommentLatitudeService;
	}

	public PlaceSearchInfoService getPlaceSearchInfoService() {
		return placeSearchInfoService;
	}

	public void setPlaceSearchInfoService(PlaceSearchInfoService placeSearchInfoService) {
		this.placeSearchInfoService = placeSearchInfoService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setRecommendInfoService(RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

}
