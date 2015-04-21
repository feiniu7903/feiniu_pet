package com.lvmama.comm.abroad.vo.response;


public class CancelReservationRes extends ErrorRes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5204707088249511032L;
	/**取消id*/
	private String IDCancellation;
	/**价格*/
	private Long Price;
	/**净价*/
	private Long NetAmount;
	/**总价*/
	private Long TotalAmount;
	/**币种*/
	private String Currency;
	/**备注*/
	private String remark;
	/**
	 * 取消id
	 * @return
	 */
	public String getIDCancellation() {
		return IDCancellation;
	}
	public void setIDCancellation(String iDCancellation) {
		IDCancellation = iDCancellation;
	}
	/**
	 * 价格
	 * @return
	 */
	public Long getPrice() {
		return Price;
	}
	public void setPrice(Long price) {
		Price = price;
	}
	/**
	 * 净价<p>
	 * NetAmount: it shows you the Net price to pay to transhotel
	 * @return
	 */
	public Long getNetAmount() {
		return NetAmount;
	}
	public void setNetAmount(Long netAmount) {
		NetAmount = netAmount;
	}
	/**
	 * 总价<p>
	 * TotalAmount: Shows the price the customer has to pay to the agency (include commissions).
	 * @return
	 */
	public Long getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		TotalAmount = totalAmount;
	}
	/**
	 * 币种
	 * @return
	 */
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		Currency = currency;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
