package com.lvmama.front.web.comment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

/**
 * 点评详情页
 * 
 * @author ganyingwen
 * 
 */
@ParentPackage("lvInputInterceptorPackage")
@ResultPath("/lvInputInterceptor")
@Results({
	@Result(name = "cmtIndexPage", location = "/comment/comment.do", type = "redirect"),
	@Result(name = "detailComment", location = "/WEB-INF/pages/comment/detailComment.ftl", type = "freemarker")
})
public class DetailCommentAction extends CmtBaseAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1203742123027149142L;
	/**
	 * 404错误的页面
	 */
	private static final String ERROR_PAGE = "404";
	/**
	 * 最新点评显示数目
	 */
	private static final int NUMBER_OF_LASTEST_COMMENTS = 3;
	
	/**
	 * 点评ID
	 */
	private Long commentId;
	/**
	 * 点评
	 */
	private CommonCmtCommentVO cmtComment;
	/**
	 * 该景区其它点评
	 */
	private List<CommonCmtCommentVO> otherCommentList;

	/**
	 * 景区
	 */
	private Place place;
	/**
	 * 产品
	 */
	private ProdProduct product;
	
	private CmtTitleStatisticsVO cmtTitleStatisticsVO;

	/**
	 * 最新的当前景点或产品的点评列表
	 */
	private List<CommonCmtCommentVO> lastestCmtList;
	
	/**
	 * 维度平均分统计
	 */
	private List<CmtLatitudeStatistics> cmtLatitudeStatisticsList;
	
	private Map<String,Object> navigationMap;
	
	/**
	 * 点评详情页
	 * @return 跳转页面
	 */
	@Action(
			value="/comment/detailCmt"		
			,interceptorRefs={
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					@InterceptorRef("defaultStack")
			}
	)
	public String detailCmt() {
		//获取用户登陆信息
		isLogin();
		//根据ID获得基本点评内容
		cmtComment = getCommentInfoById(this.commentId);
		
		if(cmtComment == null) {
			return "cmtIndexPage";
		}
		
		//填充点评的图片
		cmtComment = cmtCommentService.fillApprovedPicture(cmtComment);
		
		//添加点评的回复信息
		cmtComment = cmtCommentService.fillReply(cmtComment, null);
		 
		//最新的当前景点或产品的最新点评
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("_startRow", 1);
		parameters.put("_endRow", NUMBER_OF_LASTEST_COMMENTS);
		if(cmtComment.getProductId() != null){
			parameters.put("productId", cmtComment.getProductId());
		}else if(cmtComment.getPlaceId() != null){
			parameters.put("placeId", cmtComment.getPlaceId());
		}
		lastestCmtList = getLastestComments(parameters, null);
		if (cmtComment.getCmtType().equals(Constant.EXPERIENCE_COMMENT_TYPE)
				&& (null != cmtComment.getProductId())) {

			product = queryProductInfoByProductId(cmtComment.getProductId());
			cmtTitleStatisticsVO = cmtTitleStatistisService.getCmtTitleStatisticsByProductId(cmtComment.getProductId());
			cmtTitleStatisticsVO = composeCmtTitleStatistics(cmtTitleStatisticsVO);
			
			//获得某个产品的点评维度统计平均分
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("productId", cmtComment.getProductId());
			this.cmtLatitudeStatisticsList = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(params);
			
		} 
		else if(null != cmtComment.getPlaceId()){ //包含老体验点评(没ProductId)和普通点评Constant.EXPERIENCE_COMMON_TYPE
			place = placeService.queryPlaceByPlaceId(cmtComment.getPlaceId());
			cmtTitleStatisticsVO = cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(cmtComment.getPlaceId());
			cmtTitleStatisticsVO = composeCmtTitleStatistics(cmtTitleStatisticsVO);
			
			//获得某个景点的点评维度统计平均分
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("placeId", cmtComment.getPlaceId());
			this.cmtLatitudeStatisticsList = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(params);
		}
		
		//添加页面导航
		setNavigation(cmtComment.getPlaceId());
		return "detailComment";
	}

	/**
	 * 根据ID获得点评信息
	 * @param commentId
	 * @return
	 */
	private CommonCmtCommentVO getCommentInfoById(Long commentId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("commentId", commentId);
//		parameters.put("isAudits", new String[] {
//				Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name(),
//				Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name() });
		parameters.put("isHide", "displayall");//点评详情显示所有显示或隐藏的点评
		List<CommonCmtCommentVO> list = cmtCommentService.getCmtCommentList(parameters);
		
		if(list != null)
		{
			//如果是当前用户发表的点评，则不管审核状态，当前用户都能看到自己的点评详情页
		    //非当前用户发表的点评，则需要看审核状态
			List<CommonCmtCommentVO> filterList = new ArrayList<CommonCmtCommentVO>();
			for(int i = 0; i < list.size(); i++)
			{
				CommonCmtCommentVO filterComment = list.get(i);
				if(filterComment.getUserId()!=null && super.getUser()!= null && filterComment.getUserId().equals(super.getUser().getId()))
				{
					filterList.add(filterComment);
				}else{
					if(filterComment.getIsAudit().equals(Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name())&&filterComment.getIsHasSensitivekey().equals("N")){
						filterList.add(filterComment);
					}else if(filterComment.getIsAudit().equals(Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name()) && filterComment.getIsHide().equals("N")){
						filterList.add(filterComment);
					}
				}
				
			}
			list = filterList;
		}
		
		if (null == list || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 添加页面导航
	 * @param placeId
	 */
	private void setNavigation(Long placeId) {
		String key = "loadNavigation_" + placeId;
		navigationMap = (Map<String, Object>) MemcachedUtil.getInstance().get(key);
		if (navigationMap == null) {
			navigationMap = placeService.loadNavigation(placeId);
			MemcachedUtil.getInstance().set(key,
					MemcachedUtil.getDateAfter(60 * 60 * 24), navigationMap);
		}
	}
	
	/**
	 *  ------------------------------------  get and set property -------------------------------------------
	 */
	
	public CommonCmtCommentVO getCmtComment() {
		return cmtComment;
	}

	public void setCmtComment(CommonCmtCommentVO cmtComment) {
		this.cmtComment = cmtComment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<CommonCmtCommentVO> getOtherCommentList() {
		return otherCommentList;
	}

	public void setOtherCommentList(List<CommonCmtCommentVO> otherCommentList) {
		this.otherCommentList = otherCommentList;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public List<CommonCmtCommentVO> getLastestCmtList() {
		return lastestCmtList;
	}

	/**
	 * @param cmtLatitudeStatisticsList the cmtLatitudeStatisticsList to set
	 */
	public void setCmtLatitudeStatisticsList(
			List<CmtLatitudeStatistics> cmtLatitudeStatisticsList) {
		this.cmtLatitudeStatisticsList = cmtLatitudeStatisticsList;
	}

	/**
	 * @return the cmtLatitudeStatisticsList
	 */
	public List<CmtLatitudeStatistics> getCmtLatitudeStatisticsList() {
		return cmtLatitudeStatisticsList;
	}

	public ProdProduct getProduct() {
		return product;
	}

	public CmtTitleStatisticsVO getCmtTitleStatisticsVO() {
		return cmtTitleStatisticsVO;
	}

	public Map<String, Object> getNavigationMap() {
		return navigationMap;
	}
	
}
