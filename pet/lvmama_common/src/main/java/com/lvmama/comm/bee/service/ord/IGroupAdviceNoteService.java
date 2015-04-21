package com.lvmama.comm.bee.service.ord;

import com.lvmama.comm.utils.json.ResultHandleT;

public interface IGroupAdviceNoteService {

	/**
	 * 发送出团通知书邮件、短信、提醒
	 * @param orderId
	 * @param operatorName
	 * @return isFail表示操作异常，returnContent false表示没有操作，true表示有更新过出团状态操作
	 */
	public abstract ResultHandleT<Boolean> sendGroupAdviceNote(Long orderId,
			String operatorName);
	/**
	 * 获取模板内容
	 * @param propertyName
	 * @param key
	 * @return
	 */
	public String getPropertyValue(String propertyName,String key);

}