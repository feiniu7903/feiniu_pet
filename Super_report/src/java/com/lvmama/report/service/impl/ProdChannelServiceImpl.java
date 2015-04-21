package com.lvmama.report.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdChannel;
import com.lvmama.report.dao.ProdChannelDAO;
import com.lvmama.report.service.ProdChannelService;

public class ProdChannelServiceImpl implements ProdChannelService {
	
	private ProdChannelDAO prodChannelDAO; 

	@Override
	public List<ProdChannel> searchChannel(Map param) {
		return prodChannelDAO.searchChannel(param);
	}

	@Override
	public ProdChannel getProdChannelById(Long channelId) {
		return prodChannelDAO.selectByPrimaryKey(channelId);
	}

	public void setProdChannelDAO(ProdChannelDAO prodChannelDAO) {
		this.prodChannelDAO = prodChannelDAO;
	}
 	
}
