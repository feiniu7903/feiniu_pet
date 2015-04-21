package com.lvmama.comm.pet.service.work;

import com.lvmama.comm.pet.vo.InvokeResult;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;

/**
 * 工单公用接口
 */
public interface PublicWorkOrderService {
	/**
	 * 创建工单，由系统调用。
	 * @param workOrderCreateParam 
	 * @return InvokeResult.code=0:发起工单成功，InvokeResult.code!=0:发起工单失败，InvokeResult.description：失败原因
	 */
	InvokeResult createWorkOrder(WorkOrderCreateParam workOrderCreateParam);
	/**
	 * 根据条件判断工单是否已经存在
	 * @param orderId  订单号	
	 * @param productId		产品id
	 * @param typeCode	 工单类型标识
	 * @param status  工单状态
	 * @return true已经存在，false不存在
	 */
	boolean isExists(Long orderId,Long productId,String typeCode,String status);
	
	/**
	 * 二维码申码/废码失败检查，判断是否已经产生工单<font color='red'>（二维码申码/废码失败检查专用）</font>
	 * @param orderId
	 * @param typeCode
	 * @param status
	 * @return true存在，false不存在
	 */
	boolean isExistsForPassport(Long orderId,String typeCode,String status);
}
