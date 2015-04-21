package com.lvmama.prd.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.BounsReturnScale;
import com.lvmama.comm.bee.service.prod.BounsReturnScaleService;
import com.lvmama.prd.dao.BounsReturnScaleDAO;

public class BounsReturnScaleServiceImpl implements BounsReturnScaleService {

	private BounsReturnScaleDAO bounsReturnScaleDAO;
	
	@Override
	public List<BounsReturnScale> getAll() {
		return bounsReturnScaleDAO.getAll();
	}

	@Override
	public BounsReturnScale getBonusScaleByType(Map<String, String> param) {
		return bounsReturnScaleDAO.getByType(param);
	}

	public void setBounsReturnScaleDAO(BounsReturnScaleDAO bounsReturnScaleDAO) {
		this.bounsReturnScaleDAO = bounsReturnScaleDAO;
	}

}
