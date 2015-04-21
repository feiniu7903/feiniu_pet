package com.lvmama.tnt.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.prod.mapper.TntProdPolicyMapper;
import com.lvmama.tnt.prod.po.TntProdPolicy;
import com.lvmama.tnt.user.mapper.TntChannelMapper;
import com.lvmama.tnt.user.po.TntChannel;

@Repository("tntChannelService")
public class TntChannelServiceImpl implements TntChannelService {

	@Autowired
	private TntChannelMapper tntChannelMapper;

	@Autowired
	private TntProdPolicyMapper tntProdPolicyMapper;

	@Override
	public boolean insert(TntChannel t) {
		if (t != null && t.getChannelName() != null
				&& !t.getChannelName().isEmpty()) {
			return tntChannelMapper.insert(t) > 0;
		}
		return false;
	}

	@Override
	public Map<Long, String> getMap() {
		List<TntChannel> list = tntChannelMapper.selectList(new TntChannel());
		Map<Long, String> map = null;
		if (list != null && !list.isEmpty()) {
			map = new HashMap<Long, String>();
			for (TntChannel c : list) {
				map.put(c.getChannelId(), c.getChannelName());
			}
		}
		return map;
	}

	@Override
	public List<TntChannel> query(TntChannel t) {
		return tntChannelMapper.selectList(t);
	}

	@Override
	public List<TntChannel> getChannelPolicy() {
		List<TntChannel> channelPolicyList = new ArrayList<TntChannel>();
		channelPolicyList = tntChannelMapper.selectAll();
		channelPolicyList = policyBuild(channelPolicyList);
		return channelPolicyList;
	}

	private List<TntChannel> policyBuild(List<TntChannel> channelPolicyList) {
		for (TntChannel tntChannel : channelPolicyList) {
			Long channelId = tntChannel.getChannelId();
			TntProdPolicy tntProdPolicy = tntProdPolicyMapper
					.queryByTarget(new TntProdPolicy(
							TntConstant.PROD_TARGET_TYPE.CHANNEL.name(),
							channelId, TntConstant.PRODUCT_TYPE.TICKET.name()));
			tntChannel.setTntProdPolicy(tntProdPolicy);
		}
		return channelPolicyList;
	}

	@Override
	public boolean delete(Long channelId) {
		if (channelId != null) {
			return tntChannelMapper.delete(channelId) > 0;
		}
		return false;
	}

	@Override
	public boolean update(TntChannel t) {
		if (t != null) {
			return tntChannelMapper.update(t) > 0;
		}
		return false;
	}

	@Override
	public TntChannel getByChannelId(Long channelId) {
		return channelId != null ? tntChannelMapper.getById(channelId) : null;
	}
	
	@Override
	public TntChannel getChannelById(Long userId){
		return tntChannelMapper.getChannelById(userId);
	}
}
