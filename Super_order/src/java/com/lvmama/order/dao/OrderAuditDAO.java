package com.lvmama.order.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.pub.ComAudit;

/**
 * 订单领单DAO接口.
 *
 * <pre>
 * 封装订单领单CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.com.po.ComAudit
 */
public interface OrderAuditDAO {
	Long insert(ComAudit record);

	List<ComAudit> selectComAuditByParamMap(Map<String, String> params);

	int updateByPrimaryKey(ComAudit record);
	
	boolean canGoingBack(Map<String, String> params);
	
	boolean canRecycle(Map<String, String> params);

	/**
	 * 取得分单数据的个数
	 * @param params
	 * @return
	 */
	public Long selectComAuditCountByParams(Map<String, Object> params);
}
