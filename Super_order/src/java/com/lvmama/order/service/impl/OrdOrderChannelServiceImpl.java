package com.lvmama.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.vo.ord.OrderChannelInfo;
import com.lvmama.comm.pet.service.ord.OrdOrderChannelService;
import com.lvmama.ord.dao.OrdOrderChannelDAO;

public class OrdOrderChannelServiceImpl implements OrdOrderChannelService {
	private OrdOrderChannelDAO ordOrderChannelDAO;
	
	@Override
	public void insert(Long orderId, String channel) {
 		if (null == orderId || StringUtils.isEmpty(channel)) {
			return;
		}
 		ordOrderChannelDAO.insert(new OrdOrderChannel(orderId, channel));	
	}
	
	@Override
	public void insert(final OrdOrderChannel orderChannel) {
 		if (null == orderChannel || null == orderChannel.getOrderId()) {
			return;
		}
		ordOrderChannelDAO.insert(orderChannel);	
	}

	@Override
	public List<OrderChannelInfo> queryOrder(String channel, Date createDate, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channel", channel);
		if(createDate!=null){
			map.put("createDate", createDate);
		}
		if (endDate != null) {
			map.put("endDate", endDate);
		}
		return ordOrderChannelDAO.findOrderChannel(map);
	}
	
	@Override
	public List<OrderChannelInfo> queryOrderByOrderId(Long orderId) {
		return ordOrderChannelDAO.queryByOrderId(orderId);
	}
	
	
	@Override
	public List<OrderChannelInfo> queryOrderChannelWhereOrderFinish(final Map<String, Object> parameters) {
		return ordOrderChannelDAO.queryOrderChannelWhereOrderFinish(parameters);
	}
	
	@Override
	public Long countOrderChannelWhereOrderFinish()
	{
		return ordOrderChannelDAO.countOrderChannelWhereOrderFinish();
	}

	@Override
	public OrderChannelInfo queryByOrderIdAndChannel(Long orderId, String channel) {
    	return ordOrderChannelDAO.queryByOrderIdAndChannel(orderId, channel);
    }
	
	public OrdOrderChannelDAO getOrdOrderChannelDAO() {
		return ordOrderChannelDAO;
	}

	public void setOrdOrderChannelDAO(OrdOrderChannelDAO ordOrderChannelDAO) {
		this.ordOrderChannelDAO = ordOrderChannelDAO;
	}
	
}
