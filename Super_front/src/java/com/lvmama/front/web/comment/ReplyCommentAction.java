package com.lvmama.front.web.comment;


import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.comment.CmtActivity;
import com.lvmama.comm.pet.po.comment.CmtRecommendPlace;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.CmtActivityService;
import com.lvmama.comm.pet.service.comment.CmtReplyService;
import com.lvmama.comm.vo.comment.CmtReplyVO;

@Results({
	@Result(name = "replyCommentSuccess", location = "/WEB-INF/pages/comment/writeReplySucceed.ftl", type = "freemarker")
})
public class ReplyCommentAction extends CmtBaseAction{
	/**
	 * 序列
	 */
	private static final long serialVersionUID = -3630352167278631431L;
	/**
	 * 404错误的页面
	 */
	private static final String ERROR_PAGE = "404";	
	/**
	 * 点评ID
	 */
	private Long commentId;
	/**
	 * 回复内容
	 */
	private String content;
	/**
	 * 回复点评服务接口
	 */
	private CmtReplyService cmtReplyService;
	/**
	 * 点评活动
	 */
	private CmtActivity cmtActivity;
	/**
	 * 点评招募景点
	 */
	private List<CmtRecommendPlace> recommendPlaceList = new ArrayList<CmtRecommendPlace>();
	/**
	 * 点评活动的Service
	 */
	private CmtActivityService cmtActivityService;
	
	@Action("/comment/replyComment")
	public String replyComment() {
		UserUser user = getUser();
		if (null == commentId || null == user) {
			return ERROR_PAGE;
		}
		
		/*增加回复记录*/
		CmtReplyVO cmtReply = new CmtReplyVO();
		cmtReply.setCommentId(commentId);
		cmtReply.setContent(changeContent(content));
		cmtReply.setUserName(user.getUserName());
		cmtReply.setUserId(user.getId());
		cmtReplyService.insert(cmtReply);
		
		//活动广告
		cmtActivity = cmtActivityService.queryById(Long.valueOf(2));
		
		recommendPlaceList = getRecruitPlaceCommentList();
		
		return "replyCommentSuccess";
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCmtReplyService(CmtReplyService cmtReplyService) {
		this.cmtReplyService = cmtReplyService;
	}

	public Long getCommentId() {
		return commentId;
	}

	public CmtActivity getCmtActivity() {
		return cmtActivity;
	}

	public List<CmtRecommendPlace> getRecommendPlaceList() {
		return recommendPlaceList;
	}

	public void setCmtActivityService(CmtActivityService cmtActivityService) {
		this.cmtActivityService = cmtActivityService;
	}

}
