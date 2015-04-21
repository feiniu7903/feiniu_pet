/**
 * 
 */
package com.lvmama.comm.bee.vo.train;


/**
 * @author yangbin
 *
 */
public class RequestHeader extends MessageHeader{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2672065917373352642L;
	private String userName;
	private String userKey;
	private String serviceName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
