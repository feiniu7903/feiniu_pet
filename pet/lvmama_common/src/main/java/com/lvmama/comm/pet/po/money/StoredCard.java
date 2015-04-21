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
public class StoredCard implements Serializable{
    /**
	 * 序列化.
	 */
	private static final long serialVersionUID = -5047337363120070345L;
	/**
     * 储值卡ID.
     */
	private Long storedCardId;
	/**
	 * 批次号.
	 */
	private String cardBatchNo;
	/**
	 * 卡号.
	 */
	private String cardNo;
	/**
	 * 流水号.
	 */
	private String serialNo;
	/**
	 * 面值(分).
	 */
	private Long amount;
	/**
	 * 余额(分).
	 */
	private Long balance;
	/**
	 * 激活状态 .
	 */
	private String activeStatus;
	/**
	 * 在库状态 .
	 */
	private String stockStatus;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 过期时间.
	 */
	private Date overTime;
	/**
	 * 入库ID.
	 */
	private Long intoStockId;
	/**
	 * 入库时间.
	 */
	private Date intoTime;
	/**
	 * 出库.
	 */
	private Long outStockId;
	/**
	 * 出库时间.
	 */
	private Date outTime;
	/**
	 * 卡常规状态.
	 */
	private String status;
	
	/**
	 * 面额(元).
	 */
	private float amountFloat;
	/**
	 * 余额(元).
	 */
	private float balanceFloat;
	
	/**
	 * 类型（0。老卡，1.新卡）
	 */
	private Integer type;
	/**
	 * 密码
	 */
	private String password;
	
	private String outCode;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOutCode() {
		return outCode;
	}

	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	/**
	 * 判断卡能不能延期
	 * @return
	 */
	public boolean isChangeOverTime(){
		boolean isKey=false;
		if(Constant.STORED_CARD_ENUM.NORMAL.name().equals(status) || Constant.STORED_CARD_ENUM.FINISHED.name().equals(status)){
			isKey=true;
		}
		return isKey;
		
	}
	
	public boolean isActive(){
		boolean isKey=false;
		if(Constant.STORED_CARD_ENUM.NORMAL.name().equals(status) && this.outStockId!=null && this.outStockId > 0){
			isKey=true;
		}
		return isKey;
	}
	
	/**
	 * 判断该卡能不能进行支付.
	 * 
	 * @return
	 */
	public boolean isPayable() {
		boolean isPayable = false;
		if (Constant.STORED_CARD_ENUM.NORMAL.name().equals(status)
				&& Constant.STORED_CARD_ENUM.ACTIVE.name().equals(activeStatus)
				&& Constant.STORED_CARD_ENUM.OUT_STOCK.name().equals(stockStatus)
				&& this.balance > 0) {
			isPayable = true;
		}
		return isPayable;
	}
	
	/**
	 * 判断改卡是不是可以作废.
	 * @return
	 */
	public boolean isCancel(){
		boolean isKey=false;
		if(Constant.STORED_CARD_ENUM.NORMAL.name().equals(status)&&
				 Constant.STORED_CARD_ENUM.NO_STOCK.name().equals(stockStatus)&&
				   Constant.STORED_CARD_ENUM.UNACTIVE.name().equals(activeStatus)){
			isKey=true;
		}
		return isKey;
	}
	
	
	public Long getStoredCardId() {
		return storedCardId;
	}
	public void setStoredCardId(Long storedCardId) {
		this.storedCardId = storedCardId;
	}
	public String getCardBatchNo() {
		return cardBatchNo;
	}
	public void setCardBatchNo(String cardBatchNo) {
		this.cardBatchNo = cardBatchNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	/**
	 * 获取 激活状态 .
	 * @return
	 */
	public String getActiveStatus() {
		return activeStatus;
	}
	public String getZhActiveStatus() {
		return Constant.STORED_CARD_ACTIVE_STATUS.getCnName(activeStatus);
	}
    /**
     * 设置 激活状态 .
     * @param activeStatus
     */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

    /**
     * 获取在库状态 .
     * @return
     */
	public String getStockStatus() {
		return stockStatus;
	}
	public String getZhStockStatus() {
		return Constant.STORED_CARD_STOCK_STATUS.getCnName(stockStatus);
	}
	/**
	 * 设置在库状态 .
	 * @param stockStatus
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
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
	public Long getIntoStockId() {
		return intoStockId;
	}
	public void setIntoStockId(Long intoStockId) {
		this.intoStockId = intoStockId;
	}
	public Date getIntoTime() {
		return intoTime;
	}
	public void setIntoTime(Date intoTime) {
		this.intoTime = intoTime;
	}
	public Long getOutStockId() {
		return outStockId;
	}
	public void setOutStockId(Long outStockId) {
		this.outStockId = outStockId;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	/**
	 * 获取卡常规状态.
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	public String getZhStatus() {
		return Constant.STORED_CARD_STATUS.getCnName(status);
	}
	
	/**
	 * 设置卡常规状态.
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
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
	public float getBalanceFloat() {
		if (this.balance != null) {
			this.balanceFloat = PriceUtil.convertToYuan(this.balance.longValue());
		}
		return balanceFloat;
	}
	public void setBalanceFloat(float balanceFloat) {
		this.balanceFloat = balanceFloat;
	}
	
	
	
}
