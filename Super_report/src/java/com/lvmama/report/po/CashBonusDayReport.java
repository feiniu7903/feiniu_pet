package com.lvmama.report.po;

/**
 * 奖金账户日报
 * @author taiqichao
 *
 */
public class CashBonusDayReport {
	
	
	/**
	 * 日期.
	 */
	private String createDate;
	/**
	 * 累计现金账户余额：历史到当日所有会员奖金账户中余额总和.<br>
	 * 以分为单位
	 */
	private Long totalSumCash;
	
	
	public CashBonusDayReport(){
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public Long getTotalSumCash() {
		return totalSumCash;
	}


	public void setTotalSumCash(Long totalSumCash) {
		this.totalSumCash = totalSumCash;
	}
	
	
	
	


}
