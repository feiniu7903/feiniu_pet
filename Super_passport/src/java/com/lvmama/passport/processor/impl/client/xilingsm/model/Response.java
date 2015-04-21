package com.lvmama.passport.processor.impl.client.xilingsm.model;

import org.dom4j.DocumentException;

import com.lvmama.comm.utils.TemplateUtils;

/**
 * Xiling snow mountain web service response object.
 * @author zuoxiaoshuai
 */
public class Response {

	/** The use date. */
	private String useDate;

	/** The phone number. */
	private String phoneNumber;

	/** The id card number. */
	private String idCardNumber;

	/** The total amount. */
	private String totalAmount;

	/** The total price. */
	private String totalPrice;

	/** The out no. */
	private String outNo;

	/** The indent code. */
	private String indentCode;

	/** The indent id. */
	private String indentId;

	/** The per fee. */
	private String perFee;

	/** The pay fee. */
	private String payFee;

	/** The status. */
	private String status;

	/** The synchro state. */
	private String synchroState;

	/** The error. */
	private String error;

	/** The result. */
	private String result;

	/** The check code. */
	private String checkCode;

	/**
	 * Privatization constructor.
	 */
	private Response() {}

	/**
	 * Get response object from XML string.
	 *
	 * @param result the result
	 * @return the response
	 * @throws DocumentException the document exception
	 */
	public static Response createInstanceByXML(String result) throws DocumentException {
		Response response = new Response();

		response.setError(TemplateUtils.getElementValue(result, "//response/error"));
		response.setIdCardNumber(TemplateUtils.getElementValue(result, "//response/idCardNumber"));
		response.setIndentCode(TemplateUtils.getElementValue(result, "//response/indentCode"));
		response.setIndentId(TemplateUtils.getElementValue(result, "//response/indentId"));
		response.setOutNo(TemplateUtils.getElementValue(result, "//response/outNo"));
		response.setPayFee(TemplateUtils.getElementValue(result, "//response/payFee"));
		response.setPerFee(TemplateUtils.getElementValue(result, "//response/perFee"));
		response.setPhoneNumber(TemplateUtils.getElementValue(result, "//response/phoneNumber"));
		response.setResult(TemplateUtils.getElementValue(result, "//response/result"));
		response.setStatus(TemplateUtils.getElementValue(result, "//response/status"));
		response.setSynchroState(TemplateUtils.getElementValue(result, "//response/synchroState"));
		response.setTotalAmount(TemplateUtils.getElementValue(result, "//response/totalAmount"));
		response.setTotalPrice(TemplateUtils.getElementValue(result, "//response/totalPrice"));
		response.setUseDate(TemplateUtils.getElementValue(result, "//response/useDate"));
		response.setCheckCode(TemplateUtils.getElementValue(result, "//response/checkCode"));

		return response;
	}

	/**
	 * Gets the use date.
	 *
	 * @return the use date
	 */
	public String getUseDate() {
		return useDate;
	}

	/**
	 * Sets the use date.
	 *
	 * @param useDate the new use date
	 */
	public void setUseDate(String useDate) {
		this.useDate = useDate;
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

	/**
	 * Gets the indent id.
	 *
	 * @return the indent id
	 */
	public String getIndentId() {
		return indentId;
	}

	/**
	 * Sets the indent id.
	 *
	 * @param indentId the new indent id
	 */
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}

	/**
	 * Gets the per fee.
	 *
	 * @return the per fee
	 */
	public String getPerFee() {
		return perFee;
	}

	/**
	 * Sets the per fee.
	 *
	 * @param perFee the new per fee
	 */
	public void setPerFee(String perFee) {
		this.perFee = perFee;
	}

	/**
	 * Gets the pay fee.
	 *
	 * @return the pay fee
	 */
	public String getPayFee() {
		return payFee;
	}

	/**
	 * Sets the pay fee.
	 *
	 * @param payFee the new pay fee
	 */
	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the synchro state.
	 *
	 * @return the synchro state
	 */
	public String getSynchroState() {
		return synchroState;
	}

	/**
	 * Sets the synchro state.
	 *
	 * @param synchroState the new synchro state
	 */
	public void setSynchroState(String synchroState) {
		this.synchroState = synchroState;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Gets the result(0:success,-1:fail).
	 *
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result the new result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Gets the check code.
	 *
	 * @return the check code
	 */
	public String getCheckCode() {
		return checkCode;
	}

	/**
	 * Sets the check code.
	 *
	 * @param checkCode the new check code
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
}
