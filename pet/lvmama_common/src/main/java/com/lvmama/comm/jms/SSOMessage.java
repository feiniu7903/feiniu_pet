package com.lvmama.comm.jms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;


public class SSOMessage implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 8728458240328180794L;
    
	//事件类型
	private SSO_EVENT event;
	//事件子类型
	private SSO_SUB_EVENT subEvent;
	//事件相关的用户标识
	private Long userId;
	//其他未确定的属性
	private Map<String,Object> attributes;
	
	/**
	 * Constructor
	 * @param event 事件
	 */
	public SSOMessage(SSO_EVENT event) {
		this.event = event;
		attributes = new HashMap<String,Object>();
	}
	
	/**
	 * Constructor
	 * @param event 事件
	 * @param userId 用户标识
	 */
	public SSOMessage(SSO_EVENT event, Long userId) {
		this(event);
		this.userId = userId;
	}
	
	/**
	 * Constructor
	 * @param event 事件
	 * @param subEvent 子事件
	 * @param userId 用户标识
	 */
	public SSOMessage(SSO_EVENT event, SSO_SUB_EVENT subEvent, Long userId) {
		this(event,userId);
		this.subEvent = subEvent;
	}	
	
	/**
	 * 返回事件
	 * @return
	 */
	public SSO_EVENT getEvent() {
		return event;
	}
	
	/**
	 * 返回子事件
	 * @return
	 */
	public SSO_SUB_EVENT getSubEvent() {
		return subEvent;
	}
	
	/**
	 * 设置子事件
	 * @param subEvent
	 */
	public void setSubEvent(SSO_SUB_EVENT subEvent) {
		this.subEvent = subEvent;
	}
	
	/**
	 * 设置用户标识
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	/**
	 * 获取用户标识
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * 返回属性值
	 * @param key 属性名
	 * @return
	 */
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	
	/**
	 * 设置属性值
	 * @param key 属性名
	 * @param value 属性值
	 */
	public void putAttribute(String key, Object value) {
		attributes.put(key, value);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("event", event)
			.append("setSubEvent", subEvent)
			.append("userId", userId)
			.append("attributes", attributes).toString();
	}

}
