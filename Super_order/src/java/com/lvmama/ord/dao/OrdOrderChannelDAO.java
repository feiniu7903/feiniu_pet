package com.lvmama.ord.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.vo.ord.OrderChannelInfo;

public class OrdOrderChannelDAO extends BaseIbatisDAO {
    public void insert(OrdOrderChannel record) {
        super.insert("ORD_ORDER_CHANNEL.insert", record);
    }
    
    public List<OrderChannelInfo> queryByOrderId(Long orderId) {
    	return super.queryForList("ORD_ORDER_CHANNEL.queryByOrderId", orderId);
    }
    
    public List<OrderChannelInfo> findOrderChannel(Map param){
    	return super.queryForList("ORD_ORDER_CHANNEL.queryOrderChannel", param);
    }
    
    
    public List<OrderChannelInfo> queryOrderChannelWhereOrderFinish(final Map<String, Object> parameters){
    	return super.queryForList("ORD_ORDER_CHANNEL.queryOrderChannelWhereOrderFinish", parameters);
    }
    
    
    public Long countOrderChannelWhereOrderFinish(){
    	return (Long)super.queryForObject("ORD_ORDER_CHANNEL.countOrderChannelWhereOrderFinish");
    }
    
    public OrderChannelInfo queryByOrderIdAndChannel(Long orderId, String channel) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("orderId", orderId);
    	params.put("channel", channel);
    	return (OrderChannelInfo) super.queryForObject("ORD_ORDER_CHANNEL.queryByOrderIdAndChannel", params);
    }
}
