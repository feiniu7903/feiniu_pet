package com.lvmama.comm.pet.service.ord;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.vo.ord.OrderChannelInfo;


public interface OrdOrderChannelService {
	void insert(Long orderId, String channel);
	void insert(final OrdOrderChannel orderChannel);
	List<OrderChannelInfo> queryOrder(String channel, Date createDate, Date endDate);
	List<OrderChannelInfo> queryOrderChannelWhereOrderFinish(Map<String, Object> parameters);
	List<OrderChannelInfo> queryOrderByOrderId(Long orderId);
	Long countOrderChannelWhereOrderFinish();
	OrderChannelInfo queryByOrderIdAndChannel(Long orderId, String channel);
}
