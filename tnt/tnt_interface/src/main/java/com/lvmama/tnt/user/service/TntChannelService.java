package com.lvmama.tnt.user.service;

import java.util.List;
import java.util.Map;

import com.lvmama.tnt.user.po.TntChannel;

/**
 * 分销商信息
 * 
 * @author gaoxin
 * 
 */
public interface TntChannelService {

	/**
	 * 新增渠道
	 * 
	 * @param userDto
	 * @return
	 */
	public boolean insert(TntChannel t);

	public Map<Long, String> getMap();

	public List<TntChannel> query(TntChannel t);

	public boolean delete(Long channelId);

	public boolean update(TntChannel t);

	public TntChannel getByChannelId(Long channelId);

	/**
	 * 渠道规则列表
	 * @return
	 */
	public List<TntChannel> getChannelPolicy();
	/**
	 * 根据用户Id查找渠道
	 * @param userId
	 * @return
	 */
	public TntChannel getChannelById(Long userId);

}
