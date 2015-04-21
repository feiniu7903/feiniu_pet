package com.lvmama.order.dao.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pub.ComAudit;
import com.lvmama.order.dao.OrderAuditDAO;

/**
 * 订单领单DAO实现类.
 *
 * <pre>
 * 封装订单领单CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.BaseIbatisDao
 * @see com.lvmama.com.po.ComAudit
 * @see com.lvmama.order.dao.OrderAuditDAO
 */
public final class OrderAuditDAOImpl extends BaseIbatisDAO implements
		OrderAuditDAO {
	public Long insert(final ComAudit record) {
		Object newKey = super.insert("ORD_AUDIT.insert",
				record);
		return (Long) newKey;
	}

	public List<ComAudit> selectComAuditByParamMap(
			final Map<String, String> params) {
		return super.queryForList(
				"ORD_AUDIT.selectByParamMap", params, 0, 1);
	}

	public int updateByPrimaryKey(final ComAudit record) {
		int rows = super.update(
				"ORD_AUDIT.updateByPrimaryKey", record);
		return rows;
	}

	@SuppressWarnings("unchecked")
	public boolean canGoingBack(Map<String, String> params) {
		List<ComAudit> list = super.queryForList("ORD_AUDIT.selectByParamMap", params);
		if("".equals(list.get(0).getIsRecycle())||list.get(0).getIsRecycle()==null){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean canRecycle(Map<String, String> params) {
		List<ComAudit> list = super.queryForList("ORD_AUDIT.selectByParamMap", params);
		if(list.size()==0){
			return false;
		}
		ComAudit ca = list.get(0);
		if("true".equals(ca.getIsRecycle())&&(ca.getAssignUserId()!=null||!"".equals(ca.getAssignUserId()))){
			return true;
		}
		return false;
	}
	/**
	 * 取得分单总数
	 */
	public Long selectComAuditCountByParams(Map<String, Object> params){
		return  (Long) queryForObject("ORD_AUDIT.selectComAuditCountByParams",params);
	}
}
