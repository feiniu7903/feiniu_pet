/**
 * 
 */
package com.lvmama.comm.vo.comment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 通用点评父类
 * @author liuyi
 *
 */
public class CommonCmtCommentVO implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5368214289087414746L;
	/**
	 * 点评主键
	 */
	private Long commentId;
	/**
	 * 创建日期
	 */
	private Date createdTime;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 是否精评
	 */
	private String isBest = "N";
	/**
	 * 是否审核
	 */
	private String isAudit = Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name();
	/**
	 * 审核时间
	 */
	private Date auditTime;
	/**
	 * 点评内容
	 */
	private String content;
	/**
	 * 有用数
	 */
	private Long usefulCount = 0L;
	/**
	 * 虚假有用数
	 */
	private Long shamUsefulCount;
	/**
	 * 点评类型
	 */
	private String cmtType = Constant.COMMON_COMMENT_TYPE;
	/**
	 * 点评功能类型(常规,投诉,建议)
	 */
	private String cmtEffectType = Constant.CMT_EFFECT_TYPE.NORMAL.getCode();
	
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 是否隐藏显示
	 */
	private String isHide = "N";
	
	/**
	 * 是否需要后台管理关注
	 */
	private String needManageAttention = "Y";
	
	/**
	 * 回复数
	 */
	private Long replyCount = 0L;
	/**
	 * 驴妈妈商家回复数
	 */
	private Long lvmamaReplyCount = 0L;
	
	/**
	 * 普通商家回复数
	 */
	private Long merchantReplyCount = 0L;
	
	/**
	 * 平均分
	 */
	private Float avgScore;
	/**
	 * 用户的图片
	 */
	private String userImg;
	
	/**
	 * 景点或产品图片
	 */
	private String smallImage;
	
	/**
	 * 上传图片数
	 */
	private Long pictureCount;	
	
	/**
	 * 景区ID
	 */
	private Long placeId;
	
	/**
	 * 产品ID
	 */
	private Long productId;
	
	
	/**
	 * 订单ID
	 */
	private Long orderId;
	
	/**
	 * 返现金额
	 */
	private Long cashRefund;
	
	/**
	 * 来源渠道,默认前台
	 */
	private String channel = Constant.CHANNEL.FRONTEND.getCode();
	
	/**
	 * 总体点评
	 */
	private CmtLatitudeVO sumaryLatitude;
	/**
	 * 点评维度
	 */
	private List<CmtLatitudeVO> cmtLatitudes;
	/**
	 * 商家点评回复
	 */
	private List<CmtReplyVO> replies;
	/**
	 * 商家回复
	 */
	private List<CmtReplyVO> merchantReplies;
	/**
	 * lvmama回复
	 */
	private List<CmtReplyVO> lvmamaReplies;
//	
	/**
	 * 点评发布的图片
	 */
	private List<CmtPictureVO> cmtPictureList;
	/**
	 * 点评是否含有关键词
	 */
	private String isHasSensitivekey="N";
	/**
	 * 回复点评list
	 */
	private List<CmtReplyVO> cmtReplyVOList;
	
	//发点评用户端IP(用来限制批量发点评)
	private String reqIp;
	
	private long reviewStatus = 5;
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 四舍五入获取平均分
	 * @return
	 */
	public int getIntegerOfAvgScore() {
		return Integer.parseInt(Math.round(this.avgScore) + "");
	}
	
	/**
	 * 内容长度
	 * @return
	 */
	public int getContentSize() {
		if (null == content) {
			return 0;
		} else {
			return content.length();
		}
	}
	
	/*
	 * 支持点评内容的页面换行 
	 * */
	public String getContentFixBR(){
		if(null == content || "".equals(content)) 
		{ 
			return content; 
		} 
		String contentStr = content;
		contentStr = contentStr.replaceAll("\r\n", "<br/>");
		contentStr = contentStr.replaceAll("&lt;br/&gt;", "<br/>");
		contentStr = contentStr.replaceAll("\r", "<br/>"); 
		contentStr = contentStr.replaceAll("\n", "<br/>"); 
		contentStr = contentStr.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"); 
		contentStr = contentStr.replaceAll(" ", "&nbsp;"); 
		return contentStr;
	}
	
	
	/*
	 * 支持专题直接一行显示点评内容
	 * */
	public String getContentDelEnter(){
		if(null == content || "".equals(content)) 
		{ 
			return content; 
		} 
		String contentStr = content;
		contentStr = contentStr.replaceAll("&lt;br&gt;", ""); 
		contentStr = contentStr.replaceAll("<br/>", ""); 
		contentStr = contentStr.replaceAll("\r\n", ""); 
		contentStr = contentStr.replaceAll("\r", ""); 
		contentStr = contentStr.replaceAll("\n", "");
		contentStr = contentStr.replaceAll("\t", ""); 
		contentStr = contentStr.replaceAll(" ", "");
		contentStr = contentStr.replaceAll("\'", "");
		return contentStr;
	}
	
	/**
	 *  获取积分 (详细见规则:http://www.lvmama.com/points/help#1)
	 * @return Long
	 */
	public int getPoint() {
		int point = 0;
		
		//普通点评送50积分
		if(Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(isAudit)){
			point = 50;
			//精华点评追加150
			if("Y".equalsIgnoreCase(getIsBest())){
				point = point + 150;
			}
			//发表体验点评送100
			if(Constant.EXPERIENCE_COMMENT_TYPE.equals(cmtType)){
				point = point + 50;
			}
		}
		return point;
	}
	
	/**
	 * 是否隐藏显示点评中文名
	 * @return 隐藏显示点评中文名
	 */
	public String getChIsHide() {
		if ("Y".equals(isHide)) {
			return "是";
		}
		if ("N".equals(isHide)) {
			return "否";
		}
		return "";
	}
	
	public String getFormatterAuditTime() {
		if(auditTime != null)
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(auditTime);
		}
		else
		{
			return null;
		}
	}
	
	public String getFormatterCreatedTime(){
		
		if(createdTime != null)
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(createdTime);
		}
		else
		{
			return null;
		}
	}
	
	public String getSimpleCreatedTime() {
		
		if(createdTime != null)
		{
			return new SimpleDateFormat("MM-dd HH:mm").format(createdTime);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 后台点评管理页用户名为空显示"匿名"
	 * 隐藏手机号账号
	 * @return userName
	 */
	public String getUserNameExp() {
		if(userName == null){
			return "匿名";
		}
		return StringUtil.replaceOrCutUserName(16, getUserName());
	}
	
	/**
	 * 审核中文名
	 * @return 审核中文名
	 */
	public String getChAudit() {
		if (Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name().equals(isAudit)) {
			return "待审核";
		}
		if (Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(isAudit)) {
			return "审核通过";
		}
		if (Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name().equals(isAudit)) {
			return "审核末通过";
		}
		return "";
	}
	/**
	 * 点评类型中文名
	 * @return 点评类型中文名
	 */
	public String getChCmtType() {
		if (Constant.EXPERIENCE_COMMENT_TYPE.equals(cmtType)) {
			return "体验点评";
		}
		if (Constant.COMMON_COMMENT_TYPE.equals(cmtType)) {
			return "普通点评";
		}
		return "";
	}
	
	/**
	 * 是否精华点评中文名
	 * @return 精华点评中文名
	 */
	public String getChIsBest() {
		if ("Y".equals(isBest)) {
			return "是";
		}
		if ("N".equals(isBest)) {
			return "否";
		}
		return "";
	}
	
	
	public CmtLatitudeVO getSumaryLatitude() {
		if (null == sumaryLatitude && null != cmtLatitudes) {
			for (CmtLatitudeVO vo : cmtLatitudes) {
				if ("FFFFFFFFFFFFFFFFFFFFFFFFFFFF".equalsIgnoreCase(vo.getLatitudeId())) {
					sumaryLatitude = vo;
					break;
				}
			}
			if (null != sumaryLatitude) {
				cmtLatitudes.remove(sumaryLatitude);
			}
		}
		
		return sumaryLatitude;
	}
	
	public List<CmtLatitudeVO> getCmtLatitudes() {
		if(this.cmtLatitudes!= null && this.cmtLatitudes.size() > 0)
		{
			//排序维度
			Collections.sort(this.cmtLatitudes,new CmtLatitudeVO());
		}
		return cmtLatitudes;
	}
	
	public float getCashRefundYuan() {
		if (null == this.cashRefund) {
			return 0f;
		} else {
			return PriceUtil.convertToYuan(this.cashRefund.longValue());
		}
	}	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getCommentIdUrl() {
		return "http://www.lvmama.com/comment/" + commentId;
	}
	
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(final Long commentId) {
		this.commentId = commentId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(final Date createdTime) {
		this.createdTime = createdTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(final Long userId) {
		this.userId = userId;
	}
	public String getIsBest() {
		return isBest;
	}
	public void setIsBest(final String isBest) {
		this.isBest = isBest;
	}
	public String getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(final String isAudit) {
		this.isAudit = isAudit;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(final String content) {
		this.content = content;
	}
	public Long getUsefulCount() {
		return usefulCount;
	}
	public void setUsefulCount(final Long usefulCount) {
		this.usefulCount = usefulCount;
	}
	public String getCmtType() {
		return cmtType;
	}
	public void setCmtType(final String cmtType) {
		this.cmtType = cmtType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(final String userName) {
		this.userName = userName;
	}
	public Long getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(final Long replyCount) {
		this.replyCount = replyCount;
	}
	public String getIsHide() {
		return isHide;
	}
	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}
	public Long getShamUsefulCount() {
		return shamUsefulCount;
	}
	public void setShamUsefulCount(Long shamUsefulCount) {
		this.shamUsefulCount = shamUsefulCount;
	}
	public void setNeedManageAttention(String needManageAttention) {
		this.needManageAttention = needManageAttention;
	}
	public String getNeedManageAttention() {
		return needManageAttention;
	}
	/**
	 * @param lvmamaReplyCount the lvmamaReplyCount to set
	 */
	public void setLvmamaReplyCount(Long lvmamaReplyCount) {
		this.lvmamaReplyCount = lvmamaReplyCount;
	}
	/**
	 * @return the lvmamaReplyCount
	 */
	public Long getLvmamaReplyCount() {
		return lvmamaReplyCount;
	}
	/**
	 * @param merchantReplyCount the merchantReplyCount to set
	 */
	public void setMerchantReplyCount(Long merchantReplyCount) {
		this.merchantReplyCount = merchantReplyCount;
	}
	/**
	 * @return the merchantReplyCount
	 */
	public Long getMerchantReplyCount() {
		return merchantReplyCount;
	}	
	public Float getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	
	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}
	
	
	public Long getPictureCount() {
		return pictureCount;
	}
	public void setPictureCount(Long pictureCount) {
		this.pictureCount = pictureCount;
	}
	
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getCashRefund() {
		return cashRefund;
	}

	public void setCashRefund(Long cashRefund) {
		this.cashRefund = cashRefund;
	}
	
	public List<CmtReplyVO> getReplies() {
		return replies;
	}
	public void setReplies(List<CmtReplyVO> replies) {
		this.replies = replies;
	}
	public List<CmtPictureVO> getCmtPictureList() {
		return cmtPictureList;
	}
	public void setCmtPictureList(List<CmtPictureVO> cmtPictureList) {
		this.cmtPictureList = cmtPictureList;
	}
	
	public List<CmtReplyVO> getMerchantReplies() {
		return merchantReplies;
	}

	public void setMerchantReplies(List<CmtReplyVO> merchantReplies) {
		this.merchantReplies = merchantReplies;
	}

	public List<CmtReplyVO> getLvmamaReplies() {
		return lvmamaReplies;
	}

	public void setLvmamaReplies(List<CmtReplyVO> lvmamaReplies) {
		this.lvmamaReplies = lvmamaReplies;
	}
	public void setCmtLatitudes(List<CmtLatitudeVO> cmtLatitudes) {
	      this.cmtLatitudes = cmtLatitudes;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCnNameCmtEffectType() {
		return Constant.CMT_EFFECT_TYPE.getCnName(getCmtEffectType()).toString();
	}
	
	public String getCmtEffectType() {
		return cmtEffectType;
	}

	public void setCmtEffectType(String cmtEffectType) {
		this.cmtEffectType = cmtEffectType;
	}

	public String getIsHasSensitivekey() {
		return isHasSensitivekey;
	}

	public void setIsHasSensitivekey(String isHasSensitivekey) {
		this.isHasSensitivekey = isHasSensitivekey;
	}

	public List<CmtReplyVO> getCmtReplyVOList() {
		return cmtReplyVOList;
	}

	public void setCmtReplyVOList(List<CmtReplyVO> cmtReplyVOList) {
		this.cmtReplyVOList = cmtReplyVOList;
	}

	public String getReqIp() {
		return reqIp;
	}

	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}

    public long getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(long reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

}
