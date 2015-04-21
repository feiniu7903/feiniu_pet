package com.lvmama.front.web.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.DicCommentLatitudeService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

/**
 * 获取某目的地的所有已审核点评
 * @author yuzhizeng
 *
 */
@ParentPackage("lvInputInterceptorPackage")
@ResultPath("/lvInputInterceptor")
@Results({
	@Result(name = "listAllCmtsOfPlace", location = "/WEB-INF/pages/comment/dest/listAllCmtsOfPlace.ftl", type = "freemarker"),
	@Result(name = "listPageAllComments", location = "/WEB-INF/pages/comment/dest/ifremaListCmtsOfDest.ftl", type = "freemarker"),
	@Result(name = "listPageBestCmts", location = "/WEB-INF/pages/comment/dest/ifremaListCmtsOfDest.ftl", type = "freemarker"),
	@Result(name = "listPageExperienceCmts", location = "/WEB-INF/pages/comment/dest/ifremaListCmtsOfDest.ftl", type = "freemarker"),
	@Result(name = "listCmtsOfDest", location = "/WEB-INF/pages/comment/dest/listCmtsOfDest.ftl", type = "freemarker")
})
public class ListCmtsOfDestAction extends CmtBaseAction{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7796503871404839392L;
	/**
	 * placeID
	 */
	private Long placeId;
	/**
	 * 获取景点的点评统计信息
	 */
	private CmtTitleStatisticsVO cmtCommentStatisticsVO;
	/**
	 * 维度平均分统计
	 */
	private List<CmtLatitudeStatistics> cmtLatitudeStatisticsList;
	/**
	 * 景区
	 */
	private Place place;
	/**
	 * 最新的点评列表
	 */
	private List<CommonCmtCommentVO> lastestCommentsList;
	/**
	 * 最新的用户当前景点三条点评
	 */
	private List<CommonCmtCommentVO> lastestUserPlaceCmtList;
	/**
	 * 当前页数
	 */
	private long currentPage = 1;
	/**
	 * 点评
	 */
	private List<CommonCmtCommentVO> cmtCommentVOList;
	/**
	 * 精华点评列表
	 */
	private List<CommonCmtCommentVO> bestCommentList;
	/**
	 * 景点页所有点评(扩展实体)
	 */
	private List<CommonCmtCommentVO> allCommentVOList;
	/**
	 * 景点页精华点评列表
	 */
	private List<CommonCmtCommentVO> pageOfBestCommentList;
	/**
	 * 景点页体验点评
	 */
	private List<CommonCmtCommentVO> experienceCommentList;
	
	/**
	 * 所有景点产品点评的当前页数
	 */
	private long allCommentsCurrentPage = 1;
	/**
	 * 所有景点精华点评的当前页数
	 */
	private long bestCmtsCurrentPage = 1;
	/**
	 * 所有景点点评的当前页数
	 */
	private long experienceCmtsCurrentPage = 1;
	
	/**
	 * 所有景点产品点评的数量
	 */
	private long allCommentsTotalRecords;
	/**
	 * 所有景点精华点评的数量
	 */
	private long bestCmtsTotalRecords;
	/**
	 * 所有景点点评的数量
	 */
	private long experienceCmtsTotalRecords;

	/**
	 * 所有景点产品页当前行数
	 */
	private long allCommentsCurrentRowNum;
	/**
	 * 所有景点精华点评页当前行数
	 */
	private long bestCmtsCurrentRowNum;
	/**
	 * 所有景点点评页当前行数
	 */
	private long experienceCmtsCurrentRowNum;
	
	/**
	 * 是否显示分页
	 */
	private String bestPageFlag = "Y";
	/**
	 * 每页记录数
	 */
	private long bestPageSize = 10;
	
	/**
	 * 分页配置信息
	 */
	private Page<CommonCmtCommentVO> bestPageConfig;
	
	/**
	 * 是否显示分页
	 */
	private String experiencePageFlag = "Y";
	/**
	 * 每页记录数
	 */
	private long experiencePageSize = 10;
	
	/**
	 * 分页配置信息
	 */
	private Page<CommonCmtCommentVO> experiencePageConfig;
	
	/**
	 * 是否显示分页
	 */
	private String pageFlag = "Y";
	/**
	 * 每页记录数
	 */
	private long pageSize = 10;
	/**
	 * 页面设置显示点评数
	 */
	private long showNum = 4;
	
	/**
	 * 分页配置信息
	 */
	private Page<CommonCmtCommentVO> pageConfig;

	/**
	 * 点评维度接口
	 */
	private DicCommentLatitudeService dicCommentLatitudeService;
	
	private Map<String, Object> getParametersCommon(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("placeId", placeId);
		parameters.put("isAudit",  Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		parameters.put("getPlaceCmts", "Y");  //取景点点评
		parameters.put("createTime321", "true");
		return parameters;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 各个景点页/酒店页调用这个获取对应内嵌点评
	 */
	@Action(
			value="/comment/listCmtsOfDest"		
			,interceptorRefs={
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					@InterceptorRef("defaultStack")
			}
	)
	public String execute() throws Exception {	
		if (!findPlace()){
			return ERROR_PAGE;
		}
		//获得某个景点的点评维度统计平均分
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("placeId", placeId);
		cmtLatitudeStatisticsList = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(params);
		
		//获取该景点的点评统计信息
		cmtCommentStatisticsVO = cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(placeId);		
		
		Map<String, Object> parameters = getParametersCommon();
		parameters.put("_startRow", "0");
		parameters.put("_endRow", showNum);
		parameters.put("createTime321", "true");
		cmtCommentVOList = cmtCommentService.getCmtCommentList(parameters);

		cmtCommentVOList = composeUserImagOfComment(cmtCommentVOList);
		
		//获取该景点的点评统计信息
		cmtCommentStatisticsVO = cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(placeId);	
				
		return "listCmtsOfDest";
	}
	
	/**
	 * 目的地/景点/酒店点评列表页   大“IFRAME”获取的信息，点评总的数量统计，维度统计
	 * @return
	 */
	@Action(
			value="/comment/listCmtsOfDest/listAllCmtsOfPlace"		
			,interceptorRefs={
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					@InterceptorRef("defaultStack")
			}
	)
	public String listAllCmtsOfPlace() {
		if (!findPlace()){
			return ERROR_PAGE;
		}
		
		//获得某个景点的点评维度统计平均分
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("placeId", placeId);
		cmtLatitudeStatisticsList = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(params);
		
		
		//获取该景点的点评统计信息
		cmtCommentStatisticsVO = cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(placeId);	
		
		Map<String, Object> parameters = getParametersCommon();
		parameters.put("cmtType", "COMMON");
		allCommentsTotalRecords = cmtCommentService.getCommentTotalCount(parameters);	
		
		parameters.remove("cmtType");
		parameters.remove("getPlaceCmts");
		parameters.put("isBest",  "Y");
		bestCmtsTotalRecords = cmtCommentService.getCommentTotalCount(parameters);	
		
		parameters.clear();
		parameters = getParametersCommon();
		parameters.put("getPlaceCmts", "N");  //包含产品点评
		parameters.put("cmtType",  Constant.EXPERIENCE_COMMENT_TYPE);
		experienceCmtsTotalRecords = cmtCommentService.getCommentTotalCount(parameters);	

		return "listAllCmtsOfPlace";
	}
	

	/**
	 * 目的地/景点/酒店点评列表页   获取景点的普通点评
	 * @return
	 */
	@Action(
			value="/comment/listCmtsOfDest/getAllComments"		
			,interceptorRefs={
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					@InterceptorRef("defaultStack")
			}
	)
	public String getAllComments() {
		if (!findPlace()){
			return ERROR_PAGE;
		}
		
		Map<String, Object> parameters = getParametersCommon();
		allCommentsTotalRecords = cmtCommentService.getCommentTotalCount(parameters);
		
		long pageConfigNum = 120;
		pageConfigNum = Math.min(allCommentsTotalRecords, pageConfigNum);//控制页面显示记录数
		
		pageConfig = Page.page(pageConfigNum, pageSize, allCommentsCurrentPage);
		
		parameters.put("_startRow", (pageConfig.getStartRows()) + "");
		parameters.put("_endRow", pageConfig.getEndRows() + "");
		parameters.put("createTime321", "true");
		
		allCommentVOList = cmtCommentService.getCmtCommentList(parameters);
		
		allCommentVOList = composeUserImagOfComment(allCommentVOList);
		
		pageConfig.setItems(allCommentVOList);
		if (pageConfig.getItems().size() > 0) {
			this.pageConfig.setUrl("/comment/listCmtsOfDest!getAllComments.do?placeId=" + placeId + "&allCommentsCurrentPage=");
		}

		//当前页记录数
		allCommentsCurrentRowNum = allCommentVOList.size();
		
		UserUser user = getUser();
		if(user != null)
		{
			Long userId = getUser().getId();
			
			//获取用户的三条最新点评
			if(userId != null && pageConfig.getCurrentPage() == 1){
				lastestUserPlaceCmtList = getLastestUserPlaceCmts(userId, placeId); 
				allCommentsCurrentRowNum = allCommentsCurrentRowNum + lastestUserPlaceCmtList.size();
			}
			
			//获取点评发布的图片(暂不显示)
			//cmtCommentService.fillApprovedPicture(cmtCommentVOList);
		}
		return "listPageAllComments";
	}
	
	
	/**
	 * 目的地/景点/酒店点评列表页   分页的精华点评
	 * @return
	 */
	@Action(
			value="/comment/listCmtsOfDest/getListPageBestCmts"		
			,interceptorRefs={
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					@InterceptorRef("defaultStack")
			}
	)
	public String getListPageBestCmts() {
		if (!findPlace()){
			return ERROR_PAGE;
		}
		
		Map<String, Object> parameters = getParametersCommon();
		parameters.put("isBest",  "Y");
		parameters.remove("getPlaceCmts");
		bestCmtsTotalRecords = cmtCommentService.getCommentTotalCount(parameters);	
		bestPageConfig = Page.page(bestCmtsTotalRecords, bestPageSize, bestCmtsCurrentPage);
		
		parameters.put("_startRow", (bestPageConfig.getStartRows()) + "");
		parameters.put("_endRow", bestPageConfig.getEndRows() + "");
		parameters.put("createTime321", "true");
		pageOfBestCommentList = cmtCommentService.getCmtCommentList(parameters);
		pageOfBestCommentList = composeUserImagOfComment(pageOfBestCommentList);
		bestPageConfig.setItems(pageOfBestCommentList);
		if (bestPageConfig.getItems().size() > 0) {
			this.bestPageConfig.setUrl("/comment/listCmtsOfDest!getListPageBestCmts.do?placeId=" + placeId + "&bestCmtsCurrentPage=");
		}
		
		bestCmtsCurrentRowNum = pageOfBestCommentList.size(); 
		if (bestCmtsTotalRecords > bestPageSize) {
			bestPageFlag = "Y";
		} else {
			bestPageFlag = "N";
		}
		
		return "listPageBestCmts";
	}
	
	/**
	 * 目的地/景点/酒店点评列表页   分页的体验点评
	 * @return
	 */
	@Action(
			value="/comment/listCmtsOfDest/getListPageExperienceCmts"		
			,interceptorRefs={
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					@InterceptorRef("defaultStack")
			}
	)
	public String getListPageExperienceCmts() {
		if (!findPlace()){
			return ERROR_PAGE;
		}
		
		Map<String, Object> parameters = getParametersCommon();
		parameters.put("getPlaceCmts", "N");  //包含产品点评
		parameters.put("cmtType",  Constant.EXPERIENCE_COMMENT_TYPE);
		experienceCmtsTotalRecords = cmtCommentService.getCommentTotalCount(parameters);	
		
		long pageConfigNum = 120;
		pageConfigNum = Math.min(experienceCmtsTotalRecords, pageConfigNum);//控制页面显示记录数
		
		experiencePageConfig = Page.page(pageConfigNum, experiencePageSize, experienceCmtsCurrentPage);
		
		parameters.put("_startRow", (experiencePageConfig.getStartRows()) + "");
		parameters.put("_endRow", experiencePageConfig.getEndRows() + "");
		parameters.put("createTime321", "true");
		experienceCommentList = cmtCommentService.getCmtCommentList(parameters);
		experienceCommentList = composeUserImagOfComment(experienceCommentList);
		experiencePageConfig.setItems(experienceCommentList);
		if (experiencePageConfig.getItems().size() > 0) {
			this.experiencePageConfig.setUrl("/comment/listCmtsOfDest!getListPageExperienceCmts.do?placeId=" + placeId + "&experienceCmtsCurrentPage=");
		}
		
		experienceCmtsCurrentRowNum = experienceCommentList.size();
		if (pageConfigNum > experiencePageSize) {
			experiencePageFlag = "Y";
		} else {
			experiencePageFlag = "N";
		}
		 
		return "listPageExperienceCmts";
	}
	
	
	
//	/**
//	 * 景区最新点评
//	 * @return
//	 */
//	public String getNewComment () {
//		if (!findPlace()){
//			return ERROR_PAGE;
//		}
//		Map<String, Object> parameters = getParametersCommon();
//		parameters.put("_startRow", 1);
//		parameters.put("_endRow", 5);
//		cmtCommentVOList = cmtCommentService.getCmtCommentList(parameters);
//		
//		return "getNewComment";
//	}
//	
//	/**
//	 * 精彩点评
//	 * @return
//	 */
//	public String getBestComment1() {
//		bestCommentList = getRecomendComment(0, numOfBestCmtEntries);
//		return "listBestCmt";
//	}

	
	/**
	 * 用户最新点评
	 * @return
	public String getUserNewComment() {
		
		String userId = super.getUserId();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", userId);
		Long totalRecords = cmtCommentService.getCommentTotalCount(parameters);
		pageConfig = PageConfig.page(totalRecords, 5, currentPage);
		
		Map<String, Object> lastestCmtParams = new HashMap<String, Object>();
		lastestCmtParams.put("getPlaceCmts", "Y");  //取景点点评
		lastestCmtParams.put("_startRow", (int)pageConfig.getStartResult());
		lastestCmtParams.put("_endRow", (int)pageConfig.getCurrentRowNum());
		lastestCommentsList = getLastestComments(lastestCmtParams, userId);
		pageConfig.setUrl("/comment/listCmtsOfDest!getUserNewComment.do?userId="+userId+"&currentPage=");
		return "listUserNewCmt";
	}
	 */
	
	/**
	 * 查找景区
	 * @return
	 */
	private boolean findPlace() {
		if (null == placeId) {
			LOG.error("景区标识为空，无法填写景区的点评");
			return false;
		}
		place = placeService.queryPlaceByPlaceId(placeId);
		if (null == place) {
			LOG.error("placeId:" + placeId + "的景区无法找到，故无法填写景区的点评");
			return false;
		}
		return true;
	}

	/**
	 * 获取用户当前景点最新的三条点评
	 * @return
	 */
	private List<CommonCmtCommentVO> getLastestUserPlaceCmts(Long userId, long placeId) {
		
		Map<String, Object> lastestCmtParams = new HashMap<String, Object>();
		lastestCmtParams.put("userId", userId);
		lastestCmtParams.put("placeId", placeId);	
		lastestCmtParams.put("getPlaceCmts", "Y");  //取景点点评
		lastestCmtParams.put("_startRow", 1);
		lastestCmtParams.put("_endRow", 3);
		lastestCmtParams.put("createTime321", "createTime321");
		lastestCmtParams.put("isHide", "displayall");
		return cmtCommentService.getCmtCommentList(lastestCmtParams);
	}
	
	
	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public List<CommonCmtCommentVO> getCmtCommentVOList() {
		return cmtCommentVOList;
	}

	public CmtTitleStatisticsVO getCmtCommentStatisticsVO() {
		return cmtCommentStatisticsVO;
	}
	public String getPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}

	public long getPageSize() {
		return pageSize;
	}

	public Page<CommonCmtCommentVO> getPageConfig() {
		return pageConfig;
	}

	public void setPageConfig(Page<CommonCmtCommentVO> pageConfig) {
		this.pageConfig = pageConfig;
	}

	public void setDicCommentLatitudeService(
			DicCommentLatitudeService dicCommentLatitudeService) {
		this.dicCommentLatitudeService = dicCommentLatitudeService;
	}

	public Place getPlace() {
		return place;
	}

	public List<CommonCmtCommentVO> getBestCommentList() {
		return bestCommentList;
	}

	public List<CommonCmtCommentVO> getLastestCommentsList() {
		return lastestCommentsList;
	}

	public List<CommonCmtCommentVO> getLastestUserPlaceCmtList() {
		return lastestUserPlaceCmtList;
	}

	public long getAllCommentsTotalRecords() {
		return allCommentsTotalRecords;
	}

	public long getBestCmtsTotalRecords() {
		return bestCmtsTotalRecords;
	}

	public long getExperienceCmtsTotalRecords() {
		return experienceCmtsTotalRecords;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getAllCommentsCurrentPage() {
		return allCommentsCurrentPage;
	}

	public void setAllCommentsCurrentPage(long allCommentsCurrentPage) {
		this.allCommentsCurrentPage = allCommentsCurrentPage;
	}

	public long getBestCmtsCurrentPage() {
		return bestCmtsCurrentPage;
	}

	public void setBestCmtsCurrentPage(long bestCmtsCurrentPage) {
		this.bestCmtsCurrentPage = bestCmtsCurrentPage;
	}

	public long getExperienceCmtsCurrentPage() {
		return experienceCmtsCurrentPage;
	}

	public void setExperienceCmtsCurrentPage(long experienceCmtsCurrentPage) {
		this.experienceCmtsCurrentPage = experienceCmtsCurrentPage;
	}

	public long getAllCommentsCurrentRowNum() {
		return allCommentsCurrentRowNum;
	}

	public long getBestCmtsCurrentRowNum() {
		return bestCmtsCurrentRowNum;
	}

	public long getExperienceCmtsCurrentRowNum() {
		return experienceCmtsCurrentRowNum;
	}

	public List<CmtLatitudeStatistics> getCmtLatitudeStatisticsList() {
		return cmtLatitudeStatisticsList;
	}

	public List<CommonCmtCommentVO> getExperienceCommentList() {
		return experienceCommentList;
	}

	public void setExperienceCommentList(List<CommonCmtCommentVO> experienceCommentList) {
		this.experienceCommentList = experienceCommentList;
	}

	public String getBestPageFlag() {
		return bestPageFlag;
	}

	public void setBestPageFlag(String bestPageFlag) {
		this.bestPageFlag = bestPageFlag;
	}

	public long getBestPageSize() {
		return bestPageSize;
	}

	public void setBestPageSize(long bestPageSize) {
		this.bestPageSize = bestPageSize;
	}

	public Page<CommonCmtCommentVO> getBestPageConfig() {
		return bestPageConfig;
	}

	public void setBestPageConfig(Page<CommonCmtCommentVO> bestPageConfig) {
		this.bestPageConfig = bestPageConfig;
	}

	public String getExperiencePageFlag() {
		return experiencePageFlag;
	}

	public void setExperiencePageFlag(String experiencePageFlag) {
		this.experiencePageFlag = experiencePageFlag;
	}

	public long getExperiencePageSize() {
		return experiencePageSize;
	}

	public void setExperiencePageSize(long experiencePageSize) {
		this.experiencePageSize = experiencePageSize;
	}

	public Page<CommonCmtCommentVO> getExperiencePageConfig() {
		return experiencePageConfig;
	}

	public void setExperiencePageConfig(
			Page<CommonCmtCommentVO> experiencePageConfig) {
		this.experiencePageConfig = experiencePageConfig;
	}

	public List<CommonCmtCommentVO> getAllCommentVOList() {
		return allCommentVOList;
	}

	public void setAllCommentVOList(List<CommonCmtCommentVO> allCommentVOList) {
		this.allCommentVOList = allCommentVOList;
	}

	public List<CommonCmtCommentVO> getPageOfBestCommentList() {
		return pageOfBestCommentList;
	}

	public void setShowNum(long showNum) {
		this.showNum = showNum;
	}

	
}
