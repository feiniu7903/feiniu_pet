/**
 * 
 */
package com.lvmama.comm.bee.vo.train;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yangbin
 *
 */
public class ResponseHeader extends MessageHeader{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8068284376230607041L;
	private String status;
	private String message;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public boolean hasSuccess(){
		return StringUtils.equalsIgnoreCase("Success", status);
	}
}
