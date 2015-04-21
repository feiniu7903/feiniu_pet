package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdChannel;

public class ProdChannelDAO extends BaseIbatisDAO {

	public ProdChannelDAO() {
		super();
	}

	public ProdChannel selectByPrimaryKey(Long channelId) {
		ProdChannel key = new ProdChannel();
		key.setChannelId(channelId);
		ProdChannel record = (ProdChannel) super
				.queryForObject("PROD_CHANNEL.selectByPrimaryKey", key);
		return record;
	}

	@SuppressWarnings("unchecked")
	public List<ProdChannel> selectProdChannelByAll() {
		return super.queryForList("PROD_CHANNEL.selectProdChannelByAll");

	}
	
	public List<ProdChannel> searchChannel(Map param){
		return super.queryForList("PROD_CHANNEL.searchChannelByParam", param);
	}

}