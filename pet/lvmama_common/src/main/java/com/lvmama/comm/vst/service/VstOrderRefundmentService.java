package com.lvmama.comm.vst.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;

/**
 * 针对vst系统的退款相关服务
 * @author taiqichao
 *
 */
public interface VstOrderRefundmentService {
	
	
	/**
	 * 根据订单号和状态查询退款单
	 * @param orderId
	 * @param status
	 * @return
	 */
	List<OrdRefundment> findOrderRefundmentByOrderIdStatus(Long orderId,String status);
	
	/**
	 * 根据退款单ID查询退款明细
	 * @param refundmentId
	 */
	List<OrdRefundMentItem> queryOrdRefundmentItemById(Long refundmentId);
	

	/**
	 * 根据订单子子项ID查询退款单明细
	 * @param orderItemMetaId
	 * @return
	 */
	List<OrdRefundMentItem> findOrderRefundMentItemByOrderItemMetaId(Long orderItemMetaId);
	
	
	 /**
	  * 查询记录
	  * @param ordOrderAmountApply
	  * @return
	  */
   List<OrdOrderAmountApply> selectByOrdOrderAmountApply(Map<String, Object> parameter);
	
	
   /**
	 * 根据ID查询{@link OrdRefundment}.
	 *
	 * @param refundmentId
	 *            ID
	 * @return {@link OrdRefundment}
	 */
	OrdRefundment queryOrdRefundmentById(Long refundmentId);
	
	/**
	 * 根据refundmentId更新status.
	 *
	 * @param refundmentId
	 *            ID
	 * @param status
	 *            status
	 * @return <code>true</code>代表更新成功，<code>false</code>代表更新失败
	 */
	boolean updateOrdRefundmentStatusById(Long refundmentId, String status);
	
	
	/**
	 * 更新退款单
	 * @param ordRefundment
	 * @return
	 */
	boolean updateOrdRefundment(OrdRefundment ordRefundment);
	
	
}
