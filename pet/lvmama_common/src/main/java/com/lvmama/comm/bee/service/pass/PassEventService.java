package com.lvmama.comm.bee.service.pass;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.pass.PassEvent;

/**
 * @author shihui
 * */
public interface PassEventService {
	/**
	 * 按条件查询
	 * 
	 * @param查询参数
	 */

	List<PassEvent> queryEvents(Map<String, Object> params);

	/**
	 * 新增
	 * 
	 * @param Event对象
	 */
	void addEvent(PassEvent event);

	/**
	 * 修改
	 */
	void updateEvent(PassEvent event);
	/**
	 * 修改事件状态
	 */
	void updateEventStauts(PassEvent event);
	/**
	 * 按PK查询
	 * 
	 * @param ID
	 */
	PassEvent selectPassEventByEventId(Long eventId);
}
