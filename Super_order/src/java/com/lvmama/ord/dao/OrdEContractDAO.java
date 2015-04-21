package com.lvmama.ord.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdEContract;

/**
 * 订单电子合同的数据库操作类
 * @author Brian
 *
 */
public class OrdEContractDAO extends BaseIbatisDAO {
	/**
	 * 插入电子合同记录
	 * @param ordEContract
	 */
	public Long insert(final OrdEContract ordEContract) {
		return (Long) super.insert("ORD_ECONTRACT.insert", ordEContract);
	}
	
	/**
	 * 更新电子合同记录
	 * @param ordEContract
	 */
	public void update(final OrdEContract ordEContract) {
		super.insert("ORD_ECONTRACT.update", ordEContract);
	}
	
	/**
	 * 根据主键查找电子合同记录
	 * @param id
	 * @return
	 */
	public OrdEContract queryByPK(final String id) {
		return (OrdEContract) super.queryForObject("ORD_ECONTRACT.queryByPK", id);
	}
	/**
	 * 根据条件查询合同记录
	 * @param parameters
	 * @return
	 */
	public List<OrdEContract> query(Map<String, Object> parameters) {
		return (List<OrdEContract>)super.queryForList("ORD_ECONTRACT.query",parameters);
	}
	
	/**
	 * 根据订单号查询电子合同记录
	 * @param orderId
	 * @return
	 */
	public OrdEContract queryByOrderId(final Long orderId) {
		return (OrdEContract) super.queryForObject("ORD_ECONTRACT.queryByOrderId", orderId); 
	}
	/**
	 * 根据订单号查询是否有修改过的日志记录
	 */
	public Long existByOrderId(final Long orderId){
		return (Long)super.queryForObject("ORD_ECONTRACT.existByOrderId",orderId);
	}

	
}
