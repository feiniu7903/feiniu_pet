package com.lvmama.passport.processor.impl.client.xilingsm.model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.MD5;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * Xiling snow mountain web service request object.
 *
 * @author zuoxiaoshuai
 */
public class Request {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(Request.class);

	/** The Constant DATE_FORMAT. */
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	/** Batch NO. */
	private String batchNo;

	/** Visit time. */
	private Date useDate;

	/** Link man. */
	private String linkman;

	/** Phone NO. */
	private String phoneNumber;

	/** People ID. */
	private String idCardNumber;

	/** Total count. */
	private String totalAmount;

	/** Total price. */
	private String totalPrice;

	/** The only marked. */
	private String outNo;

	/** Indent code . */
	private String indentCode;

	/** Detail list of request. */
	private List<RequestDetail> details;

	/**
	 * Get apply XML string from request object.
	 *
	 * @return the buy ticket request xml
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public String getBuyTicketRequestXML() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		StringBuilder buffer = new StringBuilder("\r\n<request>");
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		buffer
			.append("\r\n\t<userName>")
			.append(WebServiceConstant.getProperties("xilingsm.username"))
			.append("</userName>")
			.append("\r\n\t<activePW>")
			.append(getActivePW())
			.append("</activePW>")
			.append("\r\n\t<batchNo>")
			.append(batchNo)
			.append("</batchNo>")
			.append("\r\n\t<useDate>")
			.append(useDate == null ? "" : format.format(useDate))
			.append("</useDate>")
			.append("\r\n\t<linkman>")
			.append(StringUtils.trimToEmpty(linkman))
			.append("</linkman>")
			.append("\r\n\t<phoneNumber>")
			.append(StringUtils.trimToEmpty(phoneNumber))
			.append("</phoneNumber>")
			.append("\r\n\t<idCardNumber>")
			.append(StringUtils.trimToEmpty(idCardNumber))
			.append("</idCardNumber>")
			.append("\r\n\t<totalAmount>")
			.append(totalAmount)
			.append("</totalAmount>")
			.append("\r\n\t<totalPrice>")
			.append(totalPrice)
			.append("</totalPrice>")
			.append("\r\n\t<outNo>")
			.append(outNo)
			.append("</outNo>")
			.append("\r\n\t<details>");

		if (this.details != null) {
			for (RequestDetail detail : details) {
				buffer.append("\r\n\t\t<detail>");
				buffer
					.append("\r\n\t\t\t<ticketPriceId>")
					.append(detail.getTicketPriceId())
					.append("</ticketPriceId>")
					.append("\r\n\t\t\t<amount>")
					.append(detail.getAmount())
					.append("</amount>");
				buffer.append("\r\n\t\t</detail>");
			}
		}

		buffer.append("\r\n\t</details>").append("\r\n</request>");
		log.info("++++++++++++++++++++++++ Applay Code Request Xml:" + buffer);
		return buffer.toString();
	}

	/**
	 * Get password.<br>
	 * Password format is MD5(account + MD5(password) + batchNO).
	 *
	 * @return password
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	private String getActivePW() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		StringBuilder buffer = new StringBuilder();
		buffer
			.append(WebServiceConstant.getProperties("xilingsm.username"))
			.append(MD5.encode(WebServiceConstant.getProperties("xilingsm.password")))
			.append(batchNo);

		String activePW = MD5.encode(buffer.toString());

		return activePW;
	}

	/**
	 * Get destroy XML string from request object.
	 *
	 * @return the buy ticket request xml
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public String getReturnTicketRequestXML() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		StringBuilder buffer = new StringBuilder("\r\n<request>");
		buffer
			.append("\r\n\t<userName>")
			.append(WebServiceConstant.getProperties("xilingsm.username"))
			.append("</userName>")
			.append("\r\n\t<activePW>")
			.append(getActivePW())
			.append("</activePW>")
			.append("\r\n\t<batchNo>")
			.append(batchNo)
			.append("</batchNo>")
			.append("\r\n\t<indentCode>")
			.append(indentCode)
			.append("</indentCode>")
			.append("\r\n\t<outNo>")
			.append(outNo)
			.append("</outNo>")
			.append("\r\n\t<totalPrice>")
			.append(totalPrice)
			.append("</totalPrice>");

		buffer.append("\r\n</request>");
		log.info("++++++++++++++++++++++++ Applay Code Request Xml:" + buffer);
		return buffer.toString();
	}

	/**
	 * Get query XML string from request object.
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public String getQueryRequestXML() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		StringBuilder buffer = new StringBuilder("\r\n<request>");
		buffer
			.append("\r\n\t<userName>")
			.append(WebServiceConstant.getProperties("xilingsm.username"))
			.append("</userName>")
			.append("\r\n\t<activePW>")
			.append(getActivePW())
			.append("</activePW>")
			.append("\r\n\t<batchNo>")
			.append(batchNo)
			.append("</batchNo>")
			.append("\r\n\t<indentCode>")
			.append(indentCode)
			.append("</indentCode>");

		buffer.append("\r\n</request>");
		log.info("++++++++++++++++++++++++ Query Request Xml:" + buffer);
		return buffer.toString();
	}

	/**
	 * Gets the use date.
	 *
	 * @return the use date
	 */
	public Date getUseDate() {
		return useDate;
	}

	/**
	 * Sets the use date.
	 *
	 * @param useDate the new use date
	 */
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	/**
	 * Gets the linkman.
	 *
	 * @return the linkman
	 */
	public String getLinkman() {
		return linkman;
	}

	/**
	 * Sets the linkman.
	 *
	 * @param linkman the new linkman
	 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber the new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the id card number.
	 *
	 * @return the id card number
	 */
	public String getIdCardNumber() {
		return idCardNumber;
	}

	/**
	 * Sets the id card number.
	 *
	 * @param idCardNumber the new id card number
	 */
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	/**
	 * Gets the total amount.
	 *
	 * @return the total amount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Sets the total amount.
	 *
	 * @param totalAmount the new total amount
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * Gets the total price.
	 *
	 * @return the total price
	 */
	public String getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Sets the total price.
	 *
	 * @param totalPrice the new total price
	 */
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * Gets the out no.
	 *
	 * @return the out no
	 */
	public String getOutNo() {
		return outNo;
	}

	/**
	 * Sets the out no.
	 *
	 * @param outNo the new out no
	 */
	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public List<RequestDetail> getDetails() {
		return details;
	}

	/**
	 * Sets the details.
	 *
	 * @param details the new details
	 */
	public void setDetails(List<RequestDetail> details) {
		this.details = details;
	}

	/**
	 * Gets the batch no.
	 *
	 * @return the batch no
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * Sets the batch no.
	 *
	 * @param batchNo the new batch no
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * Gets the indent code.
	 *
	 * @return the indent code
	 */
	public String getIndentCode() {
		return indentCode;
	}

	/**
	 * Sets the indent code.
	 *
	 * @param indentCode the new indent code
	 */
	public void setIndentCode(String indentCode) {
		this.indentCode = indentCode;
	}
}
