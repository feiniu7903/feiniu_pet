/**
 * 
 */
package com.lvmama.comm.bee.vo.train;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

/**
 * 
 * @author yangbin
 *
 */
public class MessageHeader implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1087278746132999938L;
	private String messageIdentity;
	private Date timeStamp;
	
	public String getMessageIdentity() {
		return messageIdentity;
	}
	public void setMessageIdentity(String messageIdentity) {
		this.messageIdentity = messageIdentity;
	}
	public String getTimeStamp() {
		return  DateUtil.formatDate(timeStamp,TIMESTAMP_FORMAT);
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	public void makeInfo(String service){
		timeStamp  = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(service);
		sb.append("_");
		sb.append(timeStamp.getTime());
		messageIdentity = sb.toString();
	}
	
	private final String TIMESTAMP_FORMAT="yyyy-MM-dd'T'HH:mm:ss";
}
