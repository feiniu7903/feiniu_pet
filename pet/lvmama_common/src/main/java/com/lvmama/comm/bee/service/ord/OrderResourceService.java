package com.lvmama.comm.bee.service.ord;

import java.util.Date;

import com.lvmama.comm.vo.Constant.ORDER_RESOURCE_STATUS;

/**
 * 订单资源服务.
 *
 * <pre>
 * 封装订单资源更改
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.comm.vo.Constant.ORDER_RESOURCE_STATUS
 */
public interface OrderResourceService {
	/**
	 * 更改采购产品订单子项资源状态.
	 *
	 * @param orderItemId
	 *            采购产品订单子项ID
	 * @param resourceStatus
	 *            更改后的采购产品订单子项资源状态
	 * @param operatorId
	 *            操作人ID
	 * @param retentionTime 资源保留时间
	 * @param reason		资源不通过-原因
	 *            <pre>
	 * 当所有需要资源确认的采购产品订单子项资源状态一致时，订单资源状态也随之更改
	 * </pre>
	 * @return <pre>
	 * <code>true</code>代表更改采购产品订单子项资源状态成功，<code>false</code>代表更改采购产品订单子项资源状态失败
	 * </pre>
	 */
	boolean updateOrderItemResource(Long orderItemId,
			ORDER_RESOURCE_STATUS resourceStatus, String operatorId, Date retentionTime,String resourceLackReason);
}
