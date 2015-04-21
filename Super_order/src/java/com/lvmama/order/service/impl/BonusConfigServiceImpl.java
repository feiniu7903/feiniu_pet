package com.lvmama.order.service.impl;

import java.util.List;

import com.lvmama.comm.bee.po.ord.BonusConfig;
import com.lvmama.comm.bee.service.ord.BonusConfigService;
import com.lvmama.order.dao.BonusConfigDAO;

public class BonusConfigServiceImpl implements BonusConfigService {
	private BonusConfigDAO bonusConfigDAO;
	
	public void setBonusConfigDAO(BonusConfigDAO bonusConfigDAO) {
		this.bonusConfigDAO = bonusConfigDAO;
	}

	@Override
	public List<BonusConfig> selectBonusConfig() {
		return bonusConfigDAO.selectBonusConfig();
	}

}
