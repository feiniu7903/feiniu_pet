package com.lvmama.comm.pet.po.money;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;

/**
 * 奖金返现记录
 * 
 * @author taiqichao
 * @version 1.0
 */

public class CashBonusReturn implements Serializable {

	private static final long serialVersionUID = -247050389903919619L;

	/**
	 * 主键ID
	 */
	private Long returnId;
	
	/**
	 * 用户ID
	 */
	private Long cashAccountId;

	/**
	 * 来源
	 * 
	 * @see com.lvmama.comm.vo.Constant.BonusOperation
	 */
	private String comeFrom;

	/**
	 * 业务ID
	 */
	private String businessId;

	/**
	 * 返现奖金（分）
	 */
	private Long bonus;
	/**
	 * 返现奖金（元）
	 */
	private float bonusYuan;
	/**
	 * 返现时间
	 */
	private Date createDate;
	private String productName;
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 实例化奖金返现对象
	 */
	public CashBonusReturn() {
		this.createDate=new Date();
	}
	
	/**
	 * 获取主键ID
	 * 
	 * @return
	 */
	public Long getReturnId() {
		return returnId;
	}
	
	/**
	 * 设置主键ID
	 * 
	 * @param returnId
	 *            主键ID
	 */
	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}

	/**
	 * 获取用户ID
	 * 
	 * @return
	 */
	public Long getCashAccountId() {
		return cashAccountId;
	}

	/**
	 * 设置用户ID
	 * 
	 * @param cashAccountId
	 *            用户ID
	 */
	public void setCashAccountId(Long cashAccountId) {
		this.cashAccountId = cashAccountId;
	}

	/**
	 * 获取来源
	 * 
	 * @return
	 */
	public String getComeFrom() {
		return comeFrom;
	}

	/**
	 * 设置来源
	 * 
	 * @param comeFrom
	 *            来源 @see com.lvmama.comm.vo.Constant.BonusOperation
	 */
	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	/**
	 * 获取业务ID
	 * 
	 * @return
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * 设置业务ID
	 * 
	 * @param businessId
	 *            业务ID
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * 获取返现奖金
	 * 
	 * @return
	 */
	public Long getBonus() {
		return bonus;
	}

	/**
	 * 设置返现奖金
	 * 
	 * @param bonus
	 *            返现奖金
	 */
	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}

	/**
	 * 获取返现时间
	 * 
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置返现时间
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取返现奖金（元）
	 * @return
	 */
	public float getBonusYuan() {
		if(bonus!=null){
			this.bonusYuan=PriceUtil.convertToYuan(bonus.longValue());
		}
		return bonusYuan;
	}
	/**
	 * 设置返现奖金（元）
	 * @param bonusFloat
	 */
	public void setBonusYuan(float bonusYuan) {
		this.bonusYuan = bonusYuan;
	}
	
}
