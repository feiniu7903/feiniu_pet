package com.lvmama.comm.bee.service.com;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.pub.ComJobContent;

public interface ComJobContentService {

	/**
	 * 取超过计划时间的任务
	 * @param type
	 * @param date
	 * @return
	 */
	List<ComJobContent> select(ComJobContent.JOB_TYPE type,Date date);
	
	void delete(final Long pk);
		
	void add(ComJobContent record);
	
	List<ComJobContent> selectByParams(ComJobContent.JOB_TYPE type, Date beginDate, Date endDate);
}
