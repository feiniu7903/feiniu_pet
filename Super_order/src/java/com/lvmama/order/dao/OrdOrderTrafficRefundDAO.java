package com.lvmama.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficRefund;

public class OrdOrderTrafficRefundDAO extends BaseIbatisDAO {

    public OrdOrderTrafficRefundDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long orderTrafficRefundId) {
        OrdOrderTrafficRefund key = new OrdOrderTrafficRefund();
        key.setOrderTrafficRefundId(orderTrafficRefundId);
        int rows = super.delete("ORD_ORDER_TRAFFIC_REFUND.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(OrdOrderTrafficRefund record) {
        Object newKey = super.insert("ORD_ORDER_TRAFFIC_REFUND.insert", record);
        return (Long) newKey;
    }

    public OrdOrderTrafficRefund selectByPrimaryKey(Long orderTrafficRefundId) {
        OrdOrderTrafficRefund key = new OrdOrderTrafficRefund();
        key.setOrderTrafficRefundId(orderTrafficRefundId);
        OrdOrderTrafficRefund record = (OrdOrderTrafficRefund) super.queryForObject("ORD_ORDER_TRAFFIC_REFUND.selectByPrimaryKey", key);
        return record;
    }


    public int updateByPrimaryKey(OrdOrderTrafficRefund record) {
        int rows = super.update("ORD_ORDER_TRAFFIC_REFUND.updateByPrimaryKey", record);
        return rows;
    }
    
    public long selectCountByBillNo(String billNo){
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("billNo", billNo);
    	return (Long)super.queryForObject("ORD_ORDER_TRAFFIC_REFUND.selectCountByBillNo",map);
    }
    
    public Long selectSumRefundByTraffic(Long orderTrafficId){
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("orderTrafficId", orderTrafficId);
    	try{
    		Long total = (Long)super.queryForObject("ORD_ORDER_TRAFFIC_REFUND.selectSumRefundByTraffic",map);
    		if(total==null){
    			total=0L;
    		}
    		return total;
    	}catch(NullPointerException ex){
    		return 0L;
    	}
    }
    
    public List<OrdOrderTrafficRefund> selectByParam(Map<String,Object> param){
    	return super.queryForList("ORD_ORDER_TRAFFIC_REFUND.selectByParam",param);
    }

	public OrdOrderTrafficRefund getTrafficRefundByRefundId(Long refundId) {
		// TODO Auto-generated method stub
		return (OrdOrderTrafficRefund)super.queryForObject("ORD_ORDER_TRAFFIC_REFUND.getTrafficRefundByRefundId", refundId);
	}

}