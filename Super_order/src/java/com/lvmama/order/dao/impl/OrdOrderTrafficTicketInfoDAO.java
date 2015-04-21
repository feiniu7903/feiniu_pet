package com.lvmama.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.python.antlr.PythonParser.return_stmt_return;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;

public class OrdOrderTrafficTicketInfoDAO extends BaseIbatisDAO {

    public OrdOrderTrafficTicketInfoDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long trafficTicketInfoId) {
        OrdOrderTrafficTicketInfo key = new OrdOrderTrafficTicketInfo();
        key.setTrafficTicketInfoId(trafficTicketInfoId);
        int rows = super.delete("ORD_ORDER_TRAFFIC_TICKET_INFO.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(OrdOrderTrafficTicketInfo record) {
        Object newKey = super.insert("ORD_ORDER_TRAFFIC_TICKET_INFO.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(OrdOrderTrafficTicketInfo record) {
        Object newKey = super.insert("ORD_ORDER_TRAFFIC_TICKET_INFO.insertSelective", record);
        return (Long) newKey;
    }

    public OrdOrderTrafficTicketInfo selectByPrimaryKey(Long trafficTicketInfoId) {
        OrdOrderTrafficTicketInfo key = new OrdOrderTrafficTicketInfo();
        key.setTrafficTicketInfoId(trafficTicketInfoId);
        OrdOrderTrafficTicketInfo record = (OrdOrderTrafficTicketInfo) super.queryForObject("ORD_ORDER_TRAFFIC_TICKET_INFO.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(OrdOrderTrafficTicketInfo record) {
        int rows = super.update("ORD_ORDER_TRAFFIC_TICKET_INFO.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(OrdOrderTrafficTicketInfo record) {
        int rows = super.update("ORD_ORDER_TRAFFIC_TICKET_INFO.updateByPrimaryKey", record);
        return rows;
    }
    public long selectSumPriceByTraffic(final Long orderTrafficId){
    	OrdOrderTrafficTicketInfo record = new OrdOrderTrafficTicketInfo();
    	record.setOrderTrafficId(orderTrafficId);
		Long total = (Long)super.queryForObject("ORD_ORDER_TRAFFIC_TICKET_INFO.selectSumPriceByTraffic",record);
		if(total==null){
			return 0L;
		}
		return total;
    }

	/**
	 * 根据供应商orderId，获取所有出票信息
	 * @param supplierOrderId
	 * @return
	 */
	public List<OrdOrderTrafficTicketInfo> getAllTicketsByOrderId(String supplierOrderId) {
		// TODO Auto-generated method stub
		return super.queryForList("ORD_ORDER_TRAFFIC_TICKET_INFO.getAllTicketsByOrderId", supplierOrderId);
	}

	/**
	 * 根据Id更新所有退票的信息
	 * @param ticketIds
	 * @param flowId
	 */
	public void updateDrawbackTicketById(String ticketIds, String flowId) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("ticketIds", ticketIds);map.put("flowId", flowId);
		super.update("ORD_ORDER_TRAFFIC_TICKET_INFO.updateDrawbackTicketById", map);
	}

	/**
	 * 判断该流水号下是否有退票
	 * @param billNo
	 * @return
	 */
	public boolean isFlowExistsInTickets(String billNo) {
		List list = super.queryForList("ORD_ORDER_TRAFFIC_TICKET_INFO.isFlowExistsInTickets", billNo);
		if(list != null && list.size() > 0)
			return true;
		return false;
	}

	public float getPayAccountByRefundId(Long orderTrafficId) {
		// TODO Auto-generated method stub
		Object account = super.queryForObject("ORD_ORDER_TRAFFIC_TICKET_INFO.getPayAccountByRefundId", orderTrafficId);
		float result = 0;
		try {
			result = Float.parseFloat(account.toString());
		} catch (Exception e) {
			if(account == null)
				throw new RuntimeException("未找到该流水号对应的退票信息");
			else
				throw new RuntimeException("该流水号对应退票金额必须转化为数字");
		}
		return result;
	}

	public OrdOrderTrafficTicketInfo getTicketInfoById(Long ticketId) {
		// TODO Auto-generated method stub
		return (OrdOrderTrafficTicketInfo)super.queryForObject("ORD_ORDER_TRAFFIC_TICKET_INFO.getTicketInfoById", ticketId);
	}

	public int getTicketNoRefundNumById(Long orderTrafficId) {
		// TODO Auto-generated method stub
		return (Integer)super.queryForObject("ORD_ORDER_TRAFFIC_TICKET_INFO.getTicketNoRefundNumById", orderTrafficId);
	}

	public float getTicketPriceByTrafficId(Long orderTrafficId) {
		List<OrdOrderTrafficTicketInfo> tickets = super.queryForList("ORD_ORDER_TRAFFIC_TICKET_INFO.getTicketPriceByTrafficId", orderTrafficId);
		if(tickets != null && tickets.size() > 0){
			return tickets.get(0).getPrice();
		}else
			return 0f;
	}
}