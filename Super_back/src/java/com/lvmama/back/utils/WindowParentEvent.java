package com.lvmama.back.utils;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.api.Window;
/**
 * 
 * @author yuzhibing
 *
 */
public class WindowParentEvent {

	/**
	 * 触发父窗口事件
	 * @param window 当前父窗口
	 * @param parentName 父窗口名称
	 * @param eventName  时间名称
	 * @author yuzhibing
	 */
	public static void parentEvent(Window window,String parentName,String eventName){
		//Window parentWindow = (Window) window.getParent();
		Events.sendEvent(new Event(eventName, window.getFellow(parentName)));
	}
}
