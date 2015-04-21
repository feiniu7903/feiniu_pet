/**
 * 
 */
package com.lvmama.shholiday.vo.order;

/**
 * @author yangbin
 *
 */
public class OrderStatus {

	private String currentStatusCode;
	private String originalStatusCode;
	
	private String notifyStyle;

	public String getCurrentStatusCode() {
		return currentStatusCode;
	}

	public String getOriginalStatusCode() {
		return originalStatusCode;
	}

	public String getNotifyStyle() {
		return notifyStyle;
	}

	public void setCurrentStatusCode(String currentStatusCode) {
		this.currentStatusCode = currentStatusCode;
	}

	public void setOriginalStatusCode(String originalStatusCode) {
		this.originalStatusCode = originalStatusCode;
	}

	public void setNotifyStyle(String notifyStyle) {
		this.notifyStyle = notifyStyle;
	}
	
	
	
}
