package com.lvmama.comm.pet.po.lvmamacard;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 驴妈妈最新的储蓄卡
 * @author nixianjun
 *
 */
public class LvmamaStoredCard implements Serializable{
	private Log logger=LogFactory.getLog(LvmamaStoredCard.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 134523L;
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
	 * 面值（分）
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
	 * 类型（0。老卡，1.新卡）
	 */
	private Integer type;
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 明文
	 */
	private String desPassword;
	
	/**
	 * 备注
	 */
	private String beizhu;
	
	private Long notused;
	private Long used;
	private Long freeze;
	
	private String outCode;
	
	/**
	 * 绑定的用户id
	 * @return
	 * @author nixianjun 2014-2-12
	 */
	private Long userId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getStockStatus() {
		return stockStatus;
	}
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
	public String getCnOverTime() {
		return DateUtil.formatDate(overTime,"yyyy/MM/dd");
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	 * 元
	 * @return
	 * @author nixianjun 2013-11-28
	 */
	public float getAmountFloat() {
		float amountFloat=0f;
		if (this.amount != null) {
			 amountFloat = PriceUtil.convertToYuan(amount.longValue());
		}
		return amountFloat;
	}
	/**
	 * 元
	 * @return
	 * @author nixianjun 2013-11-28
	 */
	public float getBalanceFloat() {
		float balanceFloat=0f;
		if (this.balance != null) {
			 balanceFloat = PriceUtil.convertToYuan(this.balance.longValue());
		}
		return balanceFloat;
	}
	
	public float getUsedMoneyFloat(){
		float f=0f;
		if(this.amount != null&&this.balance != null){
			f=PriceUtil.convertToYuan((this.amount-this.balance));
		}
		return f;
	}
	
	public String getCnStatus() {
		return Constant.CARD_STATUS.getCnName(status);
	}
	public Long getNotused() {
		return notused;
	}
	public void setNotused(Long notused) {
		this.notused = notused;
	}
	public Long getUsed() {
		return used;
	}
	public void setUsed(Long used) {
		this.used = used;
	}
	public Long getFreeze() {
		return freeze;
	}
	public void setFreeze(Long freeze) {
		this.freeze = freeze;
	}
	public String getDesPassword() {
		return desPassword;
	}
	public void setDesPassword(String desPassword) {
		this.desPassword = desPassword;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
}
