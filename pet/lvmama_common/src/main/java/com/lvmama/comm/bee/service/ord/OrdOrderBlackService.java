package com.lvmama.comm.bee.service.ord;

import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderBlack;

public interface OrdOrderBlackService {
	public void insertOrdOrderBlack(OrdOrderBlack ordOrderBlack);
	
	public int queryOrderBlackByParam(Map<String, Object> param);
}
