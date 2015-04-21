package com.lvmama.tnt.user.mapper;

import java.util.List;

import com.lvmama.tnt.user.po.TntChannel;


public interface TntChannelMapper {

	public int insert(TntChannel entity);

	public List<TntChannel> selectList(TntChannel entity);

	public int delete(Long channelId);

	public int update(TntChannel t);

	public TntChannel getById(Long channelId);
	
	public List<TntChannel> selectAll();
	
	public TntChannel getChannelById(Long userId);
}
