package com.lvmama.pet.sweb.comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtReplyService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtReplyVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

/**
 * 点评回复的查询
 * 
 * @author yuzhizeng
 * 
 */
@Results({ 
	@Result(name = "reply", location = "/WEB-INF/pages/back/comment/reply.jsp")
	})
public class ListReplyAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -3202195878343773015L;
	/**
	 * 日志记录器
	 */
	private static final Logger LOG = Logger.getLogger(ListReplyAction.class);
	/**
	 * 点评回复的逻辑层
	 */
	private CmtReplyService cmtReplyService;
	/**
	 * 点评服务接口
	 */
	private CmtCommentService cmtCommentService;
	/**
	 * 点评回复列表
	 */
	private List<CmtReplyVO> cmtReplyList = new ArrayList<CmtReplyVO>();
	/**
	 * 查询条件
	 */
	private Map<String, Object> parameters = new HashMap<String, Object>();

	/**
	 * 批处理按钮是否可编辑,true为无效
	 */
	private Boolean batchButtonDisabled = true;
	
	private UserUserProxy userUserProxy;
	private PlaceService placeService;
	private ProdProductService prodProductService;
	
	private String isAudit;
	private String replyType = Constant.CMT_REPLY_TYPE.CUSTOMER.toString();
	private String userName;
	private Long commentId;
	private Date startDate;
	private Date endDate;
	private String orderBy;
	/**
	 * 第一次进入页面就装载所有待审核的
	 */
	@Action(value="/commentManager/listReply")
	public String execute() {
		parameters = new HashMap<String, Object>();
		parameters.put("replyType",replyType);
		parameters.put("isAudit",isAudit);
		parameters.put("userName",userName);
		parameters.put("commentId",commentId);
		parameters.put("startDate",startDate);
		parameters.put("endDate",endDate);
		parameters.put("orderBy",orderBy);
		Long count = cmtReplyService.count(parameters);
		pagination = Page.page(10, page);
		pagination.setTotalResultSize(count);
		String url = WebUtils.getUrl(getRequest());
		pagination.setUrl(url);
		parameters.put("_startRow", pagination.getStartRows());
		parameters.put("_endRow", pagination.getEndRows());
		cmtReplyList = cmtReplyService.query(parameters);
		pagination = Page.page(count, 10L, this.page);
		pagination.buildUrl(getRequest());
		pagination.setItems(cmtReplyList);
		return "reply";
	}
	@Action(value="/commentManager/batchAuditReply")
	public void batchAudit(){
		String replyStr = getRequest().getParameter("replyStr");
		String auditStatus = getRequest().getParameter("auditStatus");
		if(!StringUtil.isEmptyString(replyStr) && !StringUtil.isEmptyString(auditStatus)){
			String[] replyArray = replyStr.split(",");
			StringBuffer errorStr = new StringBuffer();
			for(String reply:replyArray){
				CmtReplyVO cmtReply = this.cmtReplyService.queryCmtReplyByKey(Long.parseLong(reply));
				String oldAuditStatus = cmtReply.getIsAudit(); //老审核状态
				cmtReply.setIsAudit(auditStatus);
				if("AUDIT_SUCCESS".equals(auditStatus)){
				    cmtReply.setReviewStatus(Long.valueOf(Constant.REVIEW_STATUS.white.getCode()));
				    cmtReply.setIsHide("N");
				} 
				if("AUDIT_FAILED".equals(auditStatus)){
				    cmtReply.setReviewStatus(Long.valueOf(Constant.REVIEW_STATUS.black.getCode()));
				    cmtReply.setIsHide("Y");
				}
				int num = cmtReplyService.update(cmtReply);
				if (num == 0) {
					errorStr.append(cmtReply.getReplyId() + ",");
					LOG.info("回复号为 " + cmtReply.getReplyId() + " 审核失败");
					continue;
				}else{
					//站内消息
					if("AUDIT_SUCCESS".equalsIgnoreCase(cmtReply.getIsAudit())&&cmtReply.getUserId()!=null){
						CommonCmtCommentVO  cmtVo = cmtCommentService.getCmtCommentByKey(cmtReply.getCommentId());
						String subjectName="";
						if(cmtVo.getProductId()!=null){
							ProdProduct product = prodProductService.getProdProductById(cmtVo.getProductId());
							subjectName=product.getProductName();
						}else{
							Place place = placeService.queryPlaceByPlaceId(cmtVo.getPlaceId());
							subjectName=place.getName();
						}
						UserUser user=userUserProxy.getUserUserByPk(cmtVo.getUserId());
						String subject ="您发表的点评有新的网友回复";
						String message ="亲爱的 "+user.getUserName()+"：<br/>"+
										"<p>有网友对您发表的< "+subjectName+" >点评进行了回复，详情请点击:" +
										"<a target='_blank' href='http://www.lvmama.com/comment/"+cmtReply.getCommentId()+"'>http://www.lvmama.com/comment/"+cmtReply.getCommentId()+"</a></p><br/>";
						String type ="COMMENT";
						CommentUtil.synchLetter(subject,message,type,user.getUserNo());
					}
				}
				LOG.info("回复号 " + cmtReply.getReplyId() + " 审核成功");
				hanldReplyCount(cmtReply.getCommentId(), oldAuditStatus, auditStatus);	
			}
			if(!StringUtil.isEmptyString(errorStr.toString())){
				sendAjaxResultByJson("{\"errorText\":\"" + errorStr + "\",\"success\":false}");
			}else{
				sendAjaxResultByJson("{\"errorText\":\"\",\"success\":true}");
			}
		}
	}
	private void hanldReplyCount(Long commentId, String oldAuditStatus,
			String newAuditStatus) {
		if (Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name().equals(oldAuditStatus)) {
			if (Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name().equals(
					newAuditStatus)) {
				// 现在的逻辑是审核通过，然后再加回复数, 审核失败就什么也不用做
				// cmtCommentService.removeReply(commentId);
			} else if (Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(
					newAuditStatus)) {
				cmtCommentService.addReplyCount(commentId);
			}
		} else if (Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name().equals(
				oldAuditStatus)
				&& Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(
						newAuditStatus)) {
			cmtCommentService.addReplyCount(commentId);
		} else if (Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(
				oldAuditStatus)
				&& Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name().equals(
						newAuditStatus)) {
			// 现在的逻辑是审核通过，然后再加回复数, 审核失败就什么也不用做
			cmtCommentService.removeReplyCount(commentId);
		}
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public List<CmtReplyVO> getCmtReplyList() {
		return cmtReplyList;
	}

	public void setCmtReplyService(CmtReplyService cmtReplyService) {
		this.cmtReplyService = cmtReplyService;
	}

	public void setCmtReplyList(List<CmtReplyVO> cmtReplyList) {
		this.cmtReplyList = cmtReplyList;
	}
	public Boolean getBatchButtonDisabled() {
		return batchButtonDisabled;
	}
	public void setBatchButtonDisabled(Boolean batchButtonDisabled) {
		this.batchButtonDisabled = batchButtonDisabled;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	public String getReplyType() {
		return replyType;
	}

	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	
}