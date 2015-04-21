package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.clutter.utils.RefundUtils;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtPictureVO;

/**
 * 订单点评 .
 * 
 * @author qinzubo
 * 
 */
public class MobileOrderCmt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productName;
	private Long orderId;
	/**
	 * 返现金额
	 */
	private String cashRefund;
	private Float totalPrice; // 总额
	private int point = 100;
	private String imgUrl; // 图片
	private String visitTime;// 游玩时间
	/**
	 * 审核状态
	 */
	private String auditStatu;
	private String productType; // 产品类型
	private Long productId; // 订单所属产品id
	private Long placeId; // 订单所属景点id

	/******************* V3.1 ********************/
	/**
	 * 点评时间
	 */
	private String createTime;
	
	/**点评平均分**/
	private Float avgScore;
	
	/**总体分数**/
	private Integer score;
	
	/**点评内容**/
	private String cmtContent;

	/**
	 * 合同.
	 * 
	 * @return
	 */

	private boolean isGroupProduct;

	/**
	 * 下单渠道
	 */
	private String channel;

	/**
	 * 是否新的记录 。当前是根据3.1.0上线节点 . 客户端3.1.0新加返现，对于3.1.0以前的版本下的订单不显示返现信息.
	 */
	private boolean isNewOrder;

	/**
	 * 点评发布的图片
	 */
	private List<CmtPictureVO> cmtPictureList;

	/**
	 * 是否支持返现 只有手机端下的才显示
	 * 
	 * @return
	 */
	public boolean isCashRefundAble() {
		if (StringUtils.isEmpty(this.cashRefund)
				|| Long.valueOf(this.cashRefund) <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 返现金额.
	 * 
	 * @return
	 */
	public String getCashRefundYuan() {
		if (!isCashRefundAble()) {
			return "0";
		}
		Long clientCashRefund = Long.valueOf(this.cashRefund);
		// 门票 多返金额在提交订单时已经加上了，故这里不需要再加了。
		/*
		 * if(Constant.PRODUCT_TYPE.TICKET.getCode().equals(productType)) {
		 * clientCashRefund+= ClutterConstant.getMobileTiketRefund(); } else {
		 * // 线路 clientCashRefund+= ClutterConstant.getMobileRouteRefund(); }
		 */
		String cachRefundYuan = PriceUtil.convertToYuan(clientCashRefund) + "";
		if(cachRefundYuan.endsWith(".0")){
			cachRefundYuan = cachRefundYuan.substring(0, cachRefundYuan.length()-2);
		}else if(cachRefundYuan.endsWith(".00")){
			cachRefundYuan = cachRefundYuan.substring(0, cachRefundYuan.length()-3);
		}
		return cachRefundYuan;
	}

	public String getProductName() {
		return StringUtil.coverNullStrValue(productName);
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCashRefund() {
		return StringUtil.coverNullStrValue(cashRefund);
	}

	public void setCashRefund(String cashRefund) {
		this.cashRefund = cashRefund;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getAbsoluteImgUrl() {
		return StringUtils.isEmpty(this.getImgUrl()) ? Constant.DEFAULT_PIC
				: Constant.getInstance().getPrefixPic() + this.getImgUrl();
	}

	public String getAuditStatu() {
		return auditStatu;
	}

	/**
	 * 审核中文名
	 * 
	 * @return 审核中文名
	 */
	public String getZhAuditStatu() {
		if (Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name().equals(auditStatu)) {
			return "待审核";
		}
		if (Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(auditStatu)) {
			return "审核通过";
		}
		if (Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name().equals(auditStatu)) {
			return "审核未通过";
		}
		return "";
	}

	public void setAuditStatu(String auditStatu) {
		this.auditStatu = auditStatu;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 手机预订多返金额
	 * 
	 * @return
	 */
	public float getMobileCashRefund() {
		// 如果不是最新订单 (V3.1.0以后是新),则不显示多返的金额 .
		if (!isCashRefundAble() || StringUtils.isEmpty(channel)
				|| !isNewOrder()) {
			return 0f;
		}

		// 如果不是客户端渠道
		if (!Constant.CHANNEL.CLIENT.getCode().equalsIgnoreCase(channel)
				&& !Constant.CHANNEL.TOUCH.getCode().equalsIgnoreCase(channel)) {
			return 0f;
		}
		return RefundUtils.getClientMoreCashRefundByTotalCashRefund(
				Long.valueOf(cashRefund), productType);
		// return RefundUtils.getMoreMobileRefundYuan(Long.valueOf(cashRefund),
		// productType);
	}

	public boolean isGroupProduct() {
		return isGroupProduct;
	}

	public void setGroupProduct(boolean isGroupProduct) {
		this.isGroupProduct = isGroupProduct;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public boolean isNewOrder() {
		return isNewOrder;
	}

	public void setNewOrder(boolean isNewOrder) {
		this.isNewOrder = isNewOrder;
	}

	public List<CmtPictureVO> getCmtPictureList() {
		return cmtPictureList;
	}

	public void setCmtPictureList(List<CmtPictureVO> cmtPictureList) {
		this.cmtPictureList = cmtPictureList;
	}

	public String getCmtContent() {
		return cmtContent;
	}

	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}

	public Float getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
