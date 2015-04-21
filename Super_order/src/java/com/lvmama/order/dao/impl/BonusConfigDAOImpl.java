package com.lvmama.order.dao.impl;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.BonusConfig;
import com.lvmama.order.dao.BonusConfigDAO;

public class BonusConfigDAOImpl extends BaseIbatisDAO  implements BonusConfigDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<BonusConfig> selectBonusConfig() {
			return (List<BonusConfig>) super.queryForList("BONUS_CONFIG.select");
	}

}
