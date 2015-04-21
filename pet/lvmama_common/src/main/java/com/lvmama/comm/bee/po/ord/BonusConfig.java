package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class BonusConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8528021262489195225L;
	private Long bonusConfigId;
	private String type;
	private Long minProfit;
	private Long maxProfit;
	private Long amount;
	private Long percent;

	public Long getBonusConfigId() {
		return bonusConfigId;
	}

	public void setBonusConfigId(Long bonusConfigId) {
		this.bonusConfigId = bonusConfigId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getMinProfit() {
		return minProfit;
	}

	public void setMinProfit(Long minProfit) {
		this.minProfit = minProfit;
	}

	public Long getMaxProfit() {
		return maxProfit;
	}

	public void setMaxProfit(Long maxProfit) {
		this.maxProfit = maxProfit;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getPercent() {
		return percent;
	}

	public void setPercent(Long percent) {
		this.percent = percent;
	}

}