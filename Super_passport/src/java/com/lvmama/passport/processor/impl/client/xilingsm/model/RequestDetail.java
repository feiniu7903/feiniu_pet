package com.lvmama.passport.processor.impl.client.xilingsm.model;

/**
 * Xiling snow mountain web service request detail object.
 * @author zuoxiaoshuai
 */
public class RequestDetail {

	/** The ticket price id. */
	private String ticketPriceId;

	/** The amount. */
	private String amount;

	/**
	 * Gets the ticket price id.
	 *
	 * @return the ticket price id
	 */
	public String getTicketPriceId() {
		return ticketPriceId;
	}

	/**
	 * Sets the ticket price id.
	 *
	 * @param ticketPriceId the new ticket price id
	 */
	public void setTicketPriceId(String ticketPriceId) {
		this.ticketPriceId = ticketPriceId;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
