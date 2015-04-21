/**
 * 
 */
package com.lvmama.comm.vo.comment;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 点评回复类
 * @author liuyi
 *
 */
public class CmtReplyVO implements java.io.Serializable {
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 8695266901559076163L;
	/**
	 * 回复标识
	 */
	private Long replyId;
	/**
	 * 点评标识
	 */
	private Long commentId;
	/**
	 * 用户名ID
	 */
	private Long userId;
	/**
	 * 回复类型
	 */
	private String replyType = Constant.CMT_REPLY_TYPE.CUSTOMER.name();
	/**
	 * 审核状态
	 */
	private String isAudit = Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name();
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 景区名称
	 */
	private String placeName;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 内容审核状态 默认为灰色
	 */
	private long reviewStatus = 3;
	/**
	 * 前台是否隐藏 默认显示
	 */
	private String isHide = "N";
	
	/**
	 * 是否可审核(回复信息只有在审核通过后才不能再操作)
	 */
	public Boolean isAuditFlog() {
		Boolean isAuditFlog = Boolean.FALSE;
		
		if (Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name().equals(this.isAudit)) {
			isAuditFlog = Boolean.FALSE;
		} 
		if (Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name().equals(this.isAudit)) {
			isAuditFlog = Boolean.FALSE;
		}
		if (Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(this.isAudit)) {
			isAuditFlog = Boolean.TRUE;
		}
		return isAuditFlog;
	}
	
	public String getFormattedCreateTime() {
		if (null != createTime) {
			return new SimpleDateFormat( "yyyy-MM-dd").format(createTime);
		} 
		return null;
	}
	
	public String getChReplyType() {
		if (Constant.CMT_REPLY_TYPE.CUSTOMER.name().equals(this.replyType)) {
			return "用户回复";
		} 
		if (Constant.CMT_REPLY_TYPE.MERCHANT.name().equals(this.replyType)) {
			return "商家回复";
		}
		if (Constant.CMT_REPLY_TYPE.LVMAMA.name().equals(this.replyType)) {
			return "驴妈妈回复";
		}
		return replyType;
	}
	
	public String getChIsAudit() {
		if (Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name().equals(this.isAudit)) {
			return "待审核";
		} 
		if (Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name().equals(this.isAudit)) {
			return "审核未通过";
		}
		if (Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(this.isAudit)) {
			return "审核通过";
		}
		return isAudit;
	}	
	
	/**
	 *  获取主题名称
	 * @return String
	 */
	public String getSubjectName(){
		return (getPlaceName() != null ? getPlaceName() : "") + (getProductName() != null ? getProductName() : "");
	}
	
	/*
	 * 支持点评内容的页面换行 
	 * */
	public String getContentFixBR(){
		if(null == content || "".equals(content)) 
		{ 
			return content; 
		} 
		content = content.replaceAll("\r\n", "<br/>"); 
		content = content.replaceAll("\r", "<br/>"); 
		content = content.replaceAll("\n", "<br/>"); 
		content = content.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"); 
		content = content.replaceAll(" ", "&nbsp;"); 
		return content;
	}
	
	/*
	 * 支持专题直接一行显示点评内容
	 * */
	public String getContentDelEnter(){
		if(null == content || "".equals(content)) 
		{ 
			return content; 
		} 
		content = content.replaceAll("\r\n", "&nbsp;"); 
		content = content.replaceAll("\r", "&nbsp;"); 
		content = content.replaceAll("\n", "&nbsp;");
		content = content.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"); 
		content = content.replaceAll(" ", "&nbsp;"); 
		return content;
	}
	
	/**
	 * 隐藏手机号账号
	 * @return
	 */
	public String getUserNameWithoutMobile() {
		return StringUtil.replaceOrCutUserName(16, getUserName());
	}
	
	public Long getReplyId() {
		return replyId;
	}
	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getReplyType() {
		return replyType;
	}
	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}
	public String getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

    public long getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(long reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getIsHide() {
        return isHide;
    }

    public void setIsHide(String isHide) {
        this.isHide = isHide;
    }
	
}
