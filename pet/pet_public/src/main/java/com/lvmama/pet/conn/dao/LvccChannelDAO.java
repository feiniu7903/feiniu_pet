package com.lvmama.pet.conn.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.conn.LvccChannel;

public class LvccChannelDAO extends BaseIbatisDAO {

	public LvccChannelDAO() {
		super();
	}
	
	public Long insert(LvccChannel record) {
		return (Long) super.insert("LVCC_CHANNEL.insert",record);
	}
	
	public void delete(Long channelId) {
		super.delete("LVCC_CHANNEL.delete", channelId);
	}
	
	public List<LvccChannel> selectAll() {
		return super.queryForList("LVCC_CHANNEL.selectAll");
	}
	
	public List<LvccChannel> selectByIds(Map<String, Object> params) {
		return  super.queryForList("LVCC_CHANNEL.selectByIds", params);
	}
	
	public Long checkNameIsExsited(LvccChannel channel) {
		return  (Long) super.queryForObject("LVCC_CHANNEL.checkNameIsExsited", channel);
	}
}
