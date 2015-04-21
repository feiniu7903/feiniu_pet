package com.lvmama.order.dao;

import java.util.List;

import com.lvmama.comm.bee.po.ord.BonusConfig;

public interface BonusConfigDAO {
	List<BonusConfig> selectBonusConfig();
}
