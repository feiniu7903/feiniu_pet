/**
 * 
 */
package com.lvmama.pet.comment.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

/**
 * @author liuyi
 *
 */
public class CmtCommentDAO extends BaseIbatisDAO {
	
	
	public Long insert(final CommonCmtCommentVO cmtComment) {
		
		if(cmtComment.getCreatedTime() == null){
			cmtComment.setCreatedTime(new Date());
		}
		return (Long) super.insert("CMT_COMMENT.insert", cmtComment);
	}

	@SuppressWarnings("unchecked")
	public List<CommonCmtCommentVO> getCmtCommentList(final Map<String, Object> parameters) {
		
		if(MemcachedUtil.getInstance().get("HOT_EVENT_DISABLE_CmtComment") !=null){
			return new ArrayList<CommonCmtCommentVO>();
		}
		
		if(parameters.size() == 0)
		{
			return new ArrayList<CommonCmtCommentVO>();
		}
		else
		{
			return super.queryForList("CMT_COMMENT.query", parameters);
		}
		
		
	}
	
	public CommonCmtCommentVO getCmtCommentByKey(final Long value) {
		Map<String,Long> params = new HashMap<String, Long>();
		params.put("commentId", value);
		return (CommonCmtCommentVO) super.queryForObject("CMT_COMMENT.query", params);
	}

	public int update(final CommonCmtCommentVO cmtComment) {
		return super.update("CMT_COMMENT.update", cmtComment);
	}

	public int addUsefulCount(final Map<String, Object> parameters) {
		
		if(parameters.size() == 0) 
		{
			return -1;
		}
		else
		{
			return super.update("CMT_COMMENT.addUsefulCount", parameters);
		}
	}

	public Long getCommentTotalCount(final Map<String, Object> parameters) {
		
		if(parameters.size() == 0)
		{
			return 0l;
		}
		else
		{
			return (Long) super.queryForObject("CMT_COMMENT.totalCount", parameters);
		}
	}
	
	public int updateExperienceComment(final Map<String, Object> parameters) {
		if(parameters.size() == 0)
		{
			return -1;
		}
		else
		{
			return super.update("CMT_COMMENT.updateExperienceComment", parameters);
		}
	}

	public void addLvmamaReply(final Long commentId) {
		super.update("CMT_COMMENT.addLvmamaReply", commentId);
	}

	public void removeLvmamaReply(final Long commentId) {
		super.update("CMT_COMMENT.removeLvmamaReply", commentId);
	}

	public void addMerchantReply(final Long commentId) {
		super.update("CMT_COMMENT.addMerchantReply", commentId);
	}

	public void removeMerchantReply(final Long commentId) {
		super.update("CMT_COMMENT.removeMerchantReply", commentId);
	}
	
	
	public void addReply(final Long commentId) {
		super.update("CMT_COMMENT.addReply", commentId);
	}

	public void removeReply(final Long commentId) {
		super.update("CMT_COMMENT.removeReply", commentId);
	}
	

	public Long getCommentUserCount(final Map<String, Object> parameters) {
		if(parameters.size() == 0)
		{
			return 0l;
		}
		else
		{
			return (Long) super.queryForObject("CMT_COMMENT.cmtUserCount", parameters);
		}
	}
	
	//当前订单能返现的点评,游玩完4月内的互动体验点评
	@SuppressWarnings("unchecked")
	public List<OrderAndComment> queryCommentAndOrderOnPeriod(final Map<String, Object> parameters) {
		if(parameters.size() == 0){
			return null;
		} else {
			parameters.put("isAudit", "AUDIT_SUCCESS");
			List<OrderAndComment> list = super.queryForList("CMT_COMMENT.queryCommentAndOrderOnPeriod", parameters);
			return list;
		}
	}
	
	//当前订单能返现的点评,游玩完4月内的互动体验点评总数
	public Long queryCountOfCommentAndOrderOnPeriod(final Map<String, Object> parameters) {
		
		parameters.put("isAudit", "AUDIT_SUCCESS");
		return (Long) super.queryForObject("CMT_COMMENT.queryCountOfCommentAndOrderOnPeriod", parameters);
	}
	
	//当前订单能返现的点评,没时间限制(游玩后写的体验点评)
	@SuppressWarnings("unchecked")
	public  List<OrderAndComment> selectCanRefundComment(final Map<String, Object> parameters) {
		if(parameters.size() == 0){
			return null;
		} else {
			parameters.put("isAudit", "AUDIT_SUCCESS");
			return super.queryForList("CMT_COMMENT.selectCanRefundComment", parameters);
		}
	}
	
	//根据内容审核条件返回点评总数
	public Long queryCountOfCommentReview(final Map<String, Object> parameters){
	    return (Long) super.queryForObject("CMT_COMMENT.selectCmtCountByReviewParam",parameters);
	}
	
	//修改点评内容审核类型
	public void updateReviewStatusByComment(CommonCmtCommentVO cmt){
	    super.update("CMT_COMMENT.updateCmtCommentReviewStatus",cmt);
	}
	
	//根据内容审核条件返回点评信息
	@SuppressWarnings("unchecked")
	public List<CommonCmtCommentVO> selectParamByComment(final Map<String, Object> parameters){
	    return super.queryForList("CMT_COMMENT.selectCmtCommentReviewContent",parameters);
	}
}
