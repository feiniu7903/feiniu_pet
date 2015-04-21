package com.lvmama.comm.pet.po.money;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author liwenzhan
 *
 */
public class StoredCardBatch implements Serializable{
	/**
	 * 序列化.
	 */
	private static final long serialVersionUID = 8321155697847069184L;
	/**
	 * 批次ID.
	 */
	private Long batchId;
	/**
	 * 批次号.
	 */
	private String batchNo;
	/**
	 * 数量.
	 */
	private Long cardCount;
	/**
	 * 面值(分).
	 */
	private Long amount;
	/**
	 * 开始卡号.
	 */
	private String beginSerialNo;
	/**
	 * 结束卡号 .
	 */
	private String endSerialNo;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 过期时间.
	 */
	private Date overTime;
	/**
	 * 操作者.
	 */
	private String operator;
	
	/**
	 * 
	 */
	private String status;
	
	/**
	 * 面额(元).
	 */
	private float amountFloat;
	
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Long getCardCount() {
		return cardCount;
	}
	public void setCardCount(Long cardCount) {
		this.cardCount = cardCount;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getBeginSerialNo() {
		return beginSerialNo;
	}
	public void setBeginSerialNo(String beginSerialNo) {
		this.beginSerialNo = beginSerialNo;
	}
	public String getEndSerialNo() {
		return endSerialNo;
	}
	public void setEndSerialNo(String endSerialNo) {
		this.endSerialNo = endSerialNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getOverTime() {
		return overTime;
	}
	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public float getAmountFloat() {
		if (this.amount != null) {
			this.amountFloat = PriceUtil.convertToYuan(amount.longValue());
		}
		return amountFloat;
	}
	public void setAmountFloat(float amountFloat) {
		this.amountFloat = amountFloat;
	}
	public String getStatus() {
		return status;
	}
	public String getZhStatus() {		
		return Constant.STORED_CARD_STATUS.getCnName(status);

	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
