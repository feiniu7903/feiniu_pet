/**
 * 
 */
package com.lvmama.comm.pet.po.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 持久化SESSION （目前移动客户端用户会使用）
 * @author liuyi
 *
 */
public class UserPersistentSession implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2540763774744314130L;
	
	private Long persistentSessionID;
	private String sessionKey;
	private Long userID;
	private Date expireDate;
	private Date createDate;
	
	public Long getPersistentSessionID() {
		return persistentSessionID;
	}
	public void setPersistentSessionID(Long persistentSessionID) {
		this.persistentSessionID = persistentSessionID;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
