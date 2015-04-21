package com.lvmama.pet.web.place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtReplyService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtReplyVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

@Results({ 
	@Result(name = "paginationOfComments", type="freemarker", location = "/WEB-INF/pages/comment/paginationOfComments.ftl"),
	@Result(name = "paginationOfCommentsNew", type="freemarker", location = "/WEB-INF/pages/comment/paginationOfCommentsNew.ftl"),
	@Result(name = "replyPage", type="freemarker", location = "/WEB-INF/pages/comment/replyPage.ftl")
})
public class CommentAction extends PaginationAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4568889697257954672L;
	/**
	 * 日志输出器
	 */
	private static Log LOG = LogFactory.getLog(CommentAction.class);
	private List<CommonCmtCommentVO> cmtCommentVOList;
	private List<CmtReplyVO> cmtReplyVOList;
	private Long placeId; 
	private String type;
	//点评服务接口
	private CmtCommentService cmtCommentService;
	//点评回复接口
	private CmtReplyService cmtReplyService;
	
	/**
	 * 获取点评数
	 * @throws Exception
	 */
	@Action("/newplace/getCommentCount")
	public void getCommentCount() throws Exception {
		if (null == placeId) {
			LOG.error("景点标识出错，返回空");
			this.getResponse().getWriter().print("{}");
			return;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("placeId", placeId);
		parameters.put("isAudit",  Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name()); //审核通过的
		parameters.put("cmtType", "COMMON");
		long commonCommentCount = cmtCommentService.getCommentTotalCount(parameters);
		
		parameters.put("cmtType", "EXPERIENCE");
		long experienceCommentCount = cmtCommentService.getCommentTotalCount(parameters);
		
		parameters.remove("cmtType");
		parameters.put("isBest", "Y");
		long isBestCommentCount = cmtCommentService.getCommentTotalCount(parameters);
		
		this.getResponse().getWriter().print("{\"commonCommentCount\":\"" + commonCommentCount + "\",\"isBestCommentCount\":\"" + isBestCommentCount + "\",\"experienceCommentCount\":\"" + experienceCommentCount + "\"}");
	}
	
	/**
	 * 含有分页功能的点评
	 * @return
	 */
	@Action("/newplace/paginationOfComments")
	public String getPaginationOfComments() {
		try {
			placeId = Long.parseLong(this.getRequestParameter("placeId"));
		} catch (Exception e) {
			LOG.error("景点标识出错，返回空");
			return null;
		}
		
		try {
			startRow = Long.parseLong(this.getRequestParameter("startRow"));
		} catch (Exception e) {
			LOG.error("景点标识出错，返回空");
			return null;
		}
		this.paginationOfCommentsCommon();

		return "paginationOfComments";
	}
	/**
	 * 含有分页功能的点评
	 * @return
	 */
	@Action("/newplace/paginationOfCommentsNew")
	public String paginationOfCommentsNew() {
		try {
			placeId = Long.parseLong(this.getRequestParameter("placeId"));
		} catch (Exception e) {
			LOG.error("景点标识出错，返回空");
			return null;
		}
		
		try {
			startRow = Long.parseLong(this.getRequestParameter("startRow"));
		} catch (Exception e) {
			LOG.error("景点标识出错，返回空");
			return null;
		}
		this.paginationOfCommentsCommon();

		return "paginationOfCommentsNew";
	}
	
	/**
	 * 含有分页功能的点评
	 * @return
	 */
	private void paginationOfCommentsCommon() {

		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("placeId", placeId);
		parameters.put("isAudit",  Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name()); //审核通过的
		parameters.put("createTime321", "true"); //按时间倒序
		parameters.put("_startRow", startRow + 1);
		parameters.put("_endRow", startRow + DEFAULT_PAGE_SIZE);//默认景点页获取5条点评
		if (StringUtils.isNotBlank(getRequestParameter("cmtType"))) {
			parameters.put("cmtType", getRequestParameter("cmtType"));
			type = getRequestParameter("cmtType"); 
		}
		if (StringUtils.isNotBlank(getRequestParameter("isBest"))) {
			parameters.put("isBest", getRequestParameter("isBest"));
			type = "Best";
		}
		
		totalCount = cmtCommentService.getCommentTotalCount(parameters);
		cmtCommentVOList = cmtCommentService.getCmtCommentList(parameters);
		//设定回复数据list
		cmtCommentVOList=replyDataSet(cmtCommentVOList);
 	}
	/**
	 * 设置每个点评的回复数据
	 * 
	 * @author nixianjun 2013-8-6
	 */
	private List<CommonCmtCommentVO> replyDataSet(List<CommonCmtCommentVO> oldCmtCommentVOList) {
		List<CommonCmtCommentVO> cmtList=new ArrayList<CommonCmtCommentVO>();
		for(CommonCmtCommentVO cmtVo:oldCmtCommentVOList){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("commentId", cmtVo.getCommentId());
			params.put("isAudit", "AUDIT_SUCCESS");
			params.put("orderBy", "DESC");
			params.put("_startRow", 0);
			params.put("_endRow", 3);
			List<CmtReplyVO> repList = cmtReplyService.query(params);
			if(null!=repList&&(!repList.isEmpty())){
				cmtVo.setCmtReplyVOList(repList);
			}
			cmtList.add(cmtVo);
		 }
		return cmtList;
	}
	
	@Action("/newplace/getReply")
	public String getReply() {
		if (null == this.getRequestParameter("commentId")) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("commentId", getRequestParameter("commentId"));
		params.put("isAudit", "AUDIT_SUCCESS");
		params.put("orderBy", "DESC");
		params.put("_startRow", 0);
		params.put("_endRow", 3);
		
		cmtReplyVOList = cmtReplyService.query(params);
		return "replyPage";
	}

	public CmtCommentService getCmtCommentService() {
		return cmtCommentService;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public List<CommonCmtCommentVO> getCmtCommentVOList() {
		return cmtCommentVOList;
	}

	public void setCmtCommentVOList(List<CommonCmtCommentVO> cmtCommentVOList) {
		this.cmtCommentVOList = cmtCommentVOList;
	}

	public List<CmtReplyVO> getCmtReplyVOList() {
		return cmtReplyVOList;
	}

	public void setCmtReplyService(CmtReplyService cmtReplyService) {
		this.cmtReplyService = cmtReplyService;
	}

	public String getType() {
		return type;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
}
