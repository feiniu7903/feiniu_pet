package com.lvmama.pet.conn.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.conn.LvccChannel;
import com.lvmama.comm.pet.service.conn.LvccChannelService;
import com.lvmama.pet.conn.dao.LvccChannelDAO;

public class LvccChannelServiceImpl implements LvccChannelService{
	private LvccChannelDAO lvccChannelDAO;

	public void setLvccChannelDAO(LvccChannelDAO lvccChannelDAO) {
		this.lvccChannelDAO = lvccChannelDAO;
	}
	
	@Override
	public Long insert(LvccChannel record) {
		return lvccChannelDAO.insert(record);
	}

	@Override
	public void delete(Long channelId) {
		lvccChannelDAO.delete(channelId);
	}

	@Override
	public List<LvccChannel> selectAll() {
		return lvccChannelDAO.selectAll();
	}

	@Override
	public List<LvccChannel> selectByIds(Long[] channelIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelIds", channelIds);
		return lvccChannelDAO.selectByIds(params);
	}

	@Override
	public boolean checkNameIsExsited(LvccChannel channel) {
		Long count = lvccChannelDAO.checkNameIsExsited(channel);
		return count > 0 ? true : false;
	}
	
}
