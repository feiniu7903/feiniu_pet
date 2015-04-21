package com.lvmama.comm.bee.po.op;

import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.utils.DateUtil;

public class GroupBudgetLog extends ComLog{

	public String getCreateTimeStr() {
		if(super.getCreateTime() != null){
			return DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", super.getCreateTime());
		}
		return "";
	}
}
