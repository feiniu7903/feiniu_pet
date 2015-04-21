package com.lvmama.passport.service;


/**
 * @author ShiHui
 */
public interface PassBusinessService {
	
	/**
	 * 处理申请
	 * @param codeId
	 * @return
	 */
	boolean onApply(Long codeId);
	
	/**
	 * 处理事件
	 * @param eventId
	 * @return
	 */
	boolean onEvent(Long eventId);
	 
}
