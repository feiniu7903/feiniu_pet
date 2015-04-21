/**
 * 
 */
package com.lvmama.front.web.ajax;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

/**
 * @author liuyi
 *
 */
public class AjaxRtnComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3559266787452898695L;
	
	/**
	 * 点评主键
	 */
	private Long commentId;
	/**
	 * 景区ID
	 */
	private Long placeId;
	/**
	 * 创建日期
	 */
	private Date createdDate;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 是否推荐
	 */
	private String isRecomend = "N";
	/**
	 * 是否精评
	 */
	private String isBest = "N";

	
	private Date auditTime;
	/**
	 * 点评标题
	 */
	private String title;
	/**
	 * 点评内容
	 */
	private String content;
	/**
	 * 专题直接一行显示点评内容
	 */
	private String ContentDelEnter;
	/**
	 * 返现金额
	 */
//	private Long cashRefundYuan;
	private float cashRefundYuan;
	/**
	 * 有用数
	 */
	private Long usefulCount = 0l;
	/**
	 * 点评类型
	 */
	private String cmtType = "1";
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 回复数
	 */
	private Long replyCount =0L;
	/**
	 * 景区名称
	 */
	private String placeName;
	/**
	 * place的类型
	 */
	private String stage;
	/**
	 * 平均分
	 */
	private Float avgScore;
	/**
	 * 用户的图片
	 */
	private String userImg;
	/**
	 * 总体点评
	 */
	private AjaxRtnCommentLatitude sumaryLatitude;
	/**
	 * 点评维度
	 */
	private List<AjaxRtnCommentLatitude> cmtLatitudes;


	/**
	 * 带逻辑的拼音URL
	 */
	private String pinYinUrl;
	/**
	 * 产品ID
	 */
	private Long productId;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 是否隐藏显示
	 */
	private String isHide = "N";
	/**
	 * 虚假有用数
	 */
	private Long shamUsefulCount;
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 上传图片数
	 */
	private int pictureCount;
	/**
	 * 订单ID
	 */
	private Long orderId;
	/**
	 * 订单NO
	 */
	private Long orderNo;
	/**
	 * 是否需要后台管理关注
	 */
	private String needManageAttention = "Y";
	
	/**
	 * 积分
	 */
	private int point;
	
	private String formatterCreatedDate;
	
	private String sellable;
	
	/**
	 * 驴妈妈商家回复数
	 */
	private Long lvmamaReplyCount = 0L;
	
	/**
	 * 产品渠道
	 */
	private String productChannel;
	

	/**
	 *   * * * * get and  set property * * * *  
	 * */
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIsRecomend() {
		return isRecomend;
	}
	public void setIsRecomend(String isRecomend) {
		this.isRecomend = isRecomend;
	}
	public String getIsBest() {
		return isBest;
	}
	public void setIsBest(String isBest) {
		this.isBest = isBest;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
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
	
	public void setContent(String content) {
		this.content = content;
	}
	public float getCashRefundYuan() {
		return cashRefundYuan;
	}
	public void setCashRefundYuan(float cashRefundYuan) {
		this.cashRefundYuan = cashRefundYuan;
	}
	public Long getUsefulCount() {
		return usefulCount;
	}
	public void setUsefulCount(Long usefulCount) {
		this.usefulCount = usefulCount;
	}
	public String getCmtType() {
		return cmtType;
	}
	public void setCmtType(String cmtType) {
		this.cmtType = cmtType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(Long replyCount) {
		this.replyCount = replyCount;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
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
	public List<AjaxRtnCommentLatitude> getCmtLatitudes() {
		return cmtLatitudes;
	}
	public void setCmtLatitudes(List<AjaxRtnCommentLatitude> cmtLatitudes) {
		this.cmtLatitudes = cmtLatitudes;
	}

	public String getPinYinUrl() {
		return pinYinUrl;
	}
	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getPictureCount() {
		return pictureCount;
	}
	public void setPictureCount(int pictureCount) {
		this.pictureCount = pictureCount;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public void setNeedManageAttention(String needManageAttention) {
		this.needManageAttention = needManageAttention;
	}

	public String getNeedManageAttention() {
		return needManageAttention;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getPoint() {
		return point;
	}
	
	public void setSumaryLatitude(AjaxRtnCommentLatitude sumaryLatitude) {
		this.sumaryLatitude = sumaryLatitude;
	}
	public AjaxRtnCommentLatitude getSumaryLatitude() {
		return sumaryLatitude;
	}
	
	public void setFormatterCreatedDate(String formatterCreatedDate) {
		this.formatterCreatedDate = formatterCreatedDate;
	}
	public String getFormatterCreatedDate() {
		return formatterCreatedDate;
	}
	
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
	/**
	 * @param sellable the sellable to set
	 */
	public void setSellable(String sellable) {
		this.sellable = sellable;
	}
	/**
	 * @return the sellable
	 */
	public String getSellable() {
		return sellable;
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
	 * @param productChannel the productChannel to set
	 */
	public void setProductChannel(String productChannel) {
		this.productChannel = productChannel;
	}
	/**
	 * @return the productChannel
	 */
	public String getProductChannel() {
		return productChannel;
	}

}
