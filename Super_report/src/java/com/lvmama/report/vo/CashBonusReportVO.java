package com.lvmama.report.vo;

import java.io.Serializable;
import java.util.Map;

import com.lvmama.comm.vo.Constant;

public class CashBonusReportVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 期初金额（分）
	 */
	private Long beginAmount;
	
	/**
	 * 期末金额（分）
	 */
	private Long endAmount;
	
	/**
	 * 消耗金额(分)
	 */
	private Long consumeAmount;
	
	/**
	 * 新增金额(键:返现类型@see com.lvmama.comm.vo.Constant.BonusOperation，值:新增金额)
	 */
	private Map<String, Long> addedAmountMap;
	
	private Long totalAddedAmount;

	
	public CashBonusReportVO(){
	}


	public Long getBeginAmount() {
		return beginAmount;
	}
	
	public Long getBeginAmountYuan(){
		return beginAmount/100;
	}


	public void setBeginAmount(Long beginAmount) {
		this.beginAmount = beginAmount;
	}


	public Long getEndAmount() {
		return endAmount;
	}
	
	public Long getEndAmountYuan(){
		return endAmount/100;
	}


	public void setEndAmount(Long endAmount) {
		this.endAmount = endAmount;
	}


	public Long getConsumeAmount() {
		return consumeAmount;
	}
	
	public Long getConsumeAmountYuan(){
		return consumeAmount/100;
	}


	public void setConsumeAmount(Long consumeAmount) {
		this.consumeAmount = consumeAmount;
	}


	public Map<String, Long> getAddedAmountMap() {
		return addedAmountMap;
	}


	public void setAddedAmountMap(Map<String, Long> addedAmountMap) {
		this.addedAmountMap = addedAmountMap;
	}

	public String getAddedAmountStr(){
		return render("\n");
	}


	private String render(String br) {
		StringBuilder strBuilder=new StringBuilder();
		for (Map.Entry<String, Long> entry : addedAmountMap.entrySet()) {
			strBuilder.append(Constant.BonusOperation.getCnName(entry.getKey())).append(entry.getValue()/100).append(br);
		}
		strBuilder.append("合计:").append(this.getTotalAddedAmount()/100);
		return strBuilder.toString();
	}
	
	
	public String getAddedAmountHtml(){
		return render("<br/>");
	}

	public Long getTotalAddedAmount() {
		return totalAddedAmount;
	}


	public void setTotalAddedAmount(Long totalAddedAmount) {
		this.totalAddedAmount = totalAddedAmount;
	}
	
	
}
