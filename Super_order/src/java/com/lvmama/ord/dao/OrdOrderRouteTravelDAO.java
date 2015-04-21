package com.lvmama.ord.dao;
/**
 * @author shangzhengyuan
 * @description 订单行程固化
 * @version 在线预售权
 * @time 20120727
 */
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderRouteTravel;

public class OrdOrderRouteTravelDAO extends BaseIbatisDAO {
	private final static String SQL_SPACE = "ORD_ORDER_ROUTE_TRAVEL.";
	private final static String INSERT = SQL_SPACE+"insert";
	private final static String QUERY  = SQL_SPACE+"query";
	
	/**
	 * 插入电子合同签约日志记录
	 */
	public OrdOrderRouteTravel insert(OrdOrderRouteTravel object){
		Long id= (Long)super.insert(INSERT,object);
		object.setRouteTravelId(id);
		return object;
	}
	
	/**
	 * 根据条件查询签约日志列表
	 * @param parameters
	 * @return
	 */
	public List<OrdOrderRouteTravel> query(Map<String,Object> parameters){
		List<OrdOrderRouteTravel> queryForList = super.queryForList(QUERY,parameters);
		return queryForList;
	}
	public String queryContentByOrderId(Long orderId){
		String travel = (String)queryForObject(SQL_SPACE+"queryContentByOrderId", orderId);
		return travel;
	}
}
