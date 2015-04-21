package com.lvmama.comm.bee.service.com;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.pub.ComSmsHistory;

public interface ISmsHistoryService {
	ComSmsHistory getSmsHistoryByKey(Long smsId);
	public List<ComSmsHistory> getCertSmsContent(OrdOrder order);
	public Integer selectRowCount(Map searchConds);
}
