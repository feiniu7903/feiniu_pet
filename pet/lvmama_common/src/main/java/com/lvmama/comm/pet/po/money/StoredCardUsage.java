package com.lvmama.comm.pet.po.money;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author liwenzhan
 *
 */
public class StoredCardUsage implements Serializable{
	/**
	 * 序列化.
	 */
	private static final long serialVersionUID = 4435920918260612228L;
	/**
     * 消费记录ID.
     */
	private Long usageId;
	/**
	 * 储值卡ID.
	 */
	private Long cardId;
	/**
	 * 订单ID.
	 */
	private Long orderId;
	/**
	 * 消费金额(分).
	 */
	private Long amount;
	
	/**
	 * 消费金额(元).
	 */
	private float amountFloat;
	/**
	 * 消费类型.
	 */
	private String usageType;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 操作者.
	 */
	private String operator;

	/**
	 * 支付流水号.
	 */
	private String serial;
	
	/**
	 * 产品名字
	 */
	private String productName;
	/**
	 * 产品支付金额
	 */
	private Float orderPayFloat;

	public Long getUsageId() {
		return usageId;
	}
	public void setUsageId(Long usageId) {
		this.usageId = usageId;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	
	public float getAmountFloat() {
		if (this.amount != null) {
			this.amountFloat = PriceUtil.convertToYuan(this.amount.longValue());
		}
		return amountFloat;
	}
	public void setAmountFloat(float amountFloat) {
		this.amountFloat = amountFloat;
	}
	public String getUsageType() {
		return usageType;
	}
	
	public String getZhUsageType() {
		return Constant.STORED_CARD_USAGE_STATUS.getCnName(usageType);
	}
	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCnCreateTime(){
		return DateUtil.formatDate(this.createTime, "yyyy年MM月dd日");
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Float getOrderPayFloat() {
		return orderPayFloat;
	}
	public void setOrderPayFloat(Float orderPayFloat) {
		this.orderPayFloat = orderPayFloat;
	}

	
}
