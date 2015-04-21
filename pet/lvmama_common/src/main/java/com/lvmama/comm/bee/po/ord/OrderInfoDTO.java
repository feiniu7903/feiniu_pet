package com.lvmama.comm.bee.po.ord;

import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;

/**
 * 初始化订单DTO.
 * 
 * @author liwenzhan
 * @author sunruyi
 * @see com.lvmama.common.mark.po.MarkCoupon
 * @see com.lvmama.common.mark.po.MarkCouponCode
 * @see com.lvmama.comm.bee.po.ord.OrdOrder
 * @see com.lvmama.comm.bee.po.ord.OrdOrderAmountItem
 * @see com.lvmama.common.ord.service.po.BuyInfo
 */
public class OrderInfoDTO extends OrdOrder {
	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = -8464296257022334067L;

	/**
	 * 团号.
	 */
	private String travelCode;

	/**
	 * 购买信息.
	 */
	private BuyInfo buyInfo;
	/**
	 * 操作者.
	 */
	private String operatorId;
	/**
	 * 优惠对象.
	 */
	private MarkCoupon markCoupon = new MarkCoupon();
	/**
	 * B类优惠券.
	 */
	private MarkCouponCode markCouponCode = new MarkCouponCode();
	/**
	 * 订单价格明细.
	 */
	private OrdOrderAmountItem couponAmountItem = new OrdOrderAmountItem();
	/**
	 * 订单价格明细.
	 */
	private OrdOrderAmountItem amountItem = new OrdOrderAmountItem();

	/**
	 * 加载购买信息.
	 * 
	 * @return 购买信息
	 */
	public BuyInfo getBuyInfo() {
		return buyInfo;
	}

	/**
	 * 设置购买信息.
	 * 
	 * @param buyInfo
	 *            购买信息
	 */
	public void setBuyInfo(BuyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}

	/**
	 * 加载操作者.
	 * 
	 * @return operatorId
	 */
	public String getOperatorId() {
		return operatorId;
	}

	/**
	 * 设置操作者.
	 * 
	 * @param operatorId
	 */
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * 加载优惠对象.
	 * 
	 * @param markCoupon
	 */
	public MarkCoupon getMarkCoupon() {
		return markCoupon;
	}

	/**
	 * 设置优惠对象.
	 * 
	 * @param markCoupon
	 */
	public void setMarkCoupon(MarkCoupon markCoupon) {
		this.markCoupon = markCoupon;
	}

	/**
	 * 加载B类优惠券.
	 * 
	 * @return markCouponCode
	 */
	public MarkCouponCode getMarkCouponCode() {
		return markCouponCode;
	}

	/**
	 * 设置B类优惠券.
	 * 
	 * @param markCouponCode
	 */

	public void setMarkCouponCode(MarkCouponCode markCouponCode) {
		this.markCouponCode = markCouponCode;
	}

	/**
	 * 加载价格明细.
	 * 
	 * @param couponAmountItem
	 */
	public OrdOrderAmountItem getCouponAmountItem() {
		return couponAmountItem;
	}

	/**
	 * 设置时价格明细.
	 * 
	 * @param couponAmountItem
	 */
	public void setCouponAmountItem(OrdOrderAmountItem couponAmountItem) {
		this.couponAmountItem = couponAmountItem;
	}

	/**
	 * 加载价格明细.
	 * 
	 * @param amountItem
	 */
	public OrdOrderAmountItem getAmountItem() {
		return amountItem;
	}

	/**
	 * 设置时价格明细.
	 * 
	 * @param amountItem
	 */
	public void setAmountItem(OrdOrderAmountItem amountItem) {
		this.amountItem = amountItem;
	}

	/**
	 * 加载团号.
	 * 
	 * @param travelCode
	 */
	public String getTravelCode() {
		return travelCode;
	}

	/**
	 * 设置团号.
	 * 
	 * @param travelCode
	 */
	public void setTravelCode(String travelCode) {
		this.travelCode = travelCode;
	}
}
