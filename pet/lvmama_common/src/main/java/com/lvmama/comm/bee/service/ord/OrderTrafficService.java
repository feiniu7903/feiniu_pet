/**
 * 
 */
package com.lvmama.comm.bee.service.ord;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficRefund;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.BaseVo;

/**
 * 火车票订单对接供应商的处理
 * @author yangbin
 *
 */
public interface OrderTrafficService {

	/**
	 * 锁定一个订单子子项，表示已经在开票的状态了
	 * @param supplierOrderId 供应商订单ID
	 * @return
	 */
	public ResultHandleT<Boolean> lockOrder(final String supplierOrderId);
	
	/**
	 * 开票结果
	 * @return
	 */
	public ResultHandleT<BaseVo> ticketIssueResult(final String supplierOrderId,List<OrdOrderTrafficTicketInfo> list);
	
	/**
	 * 按订单子子项读取一个供应商订单信息
	 * @param orderItemMetaId
	 * @return
	 */
	public OrdOrderTraffic getTrafficByOrderItemMetaId(final Long orderItemMetaId);
	
	OrdOrderTraffic getTrafficBySupplierOrderId(final String supplierOrderId);
	
	public void updateSupplierOrderId(final Long orderTrafficId,final String supplierOrderId);
	
	public OrdOrderTraffic makeTrafficOrder(OrdOrderTraffic traffic);
	
	public ResultHandleT<OrdOrderTraffic> updateFailStatus(final Long orderTrafficId,String failMessage);
	
	public long getTrafficAmount(final Long orderTrafficId);
	
	public void updateRefumentStatus(final Long orderTrafficId,Constant.ORDER_TRAFFIC_REFUMENT refument);
	/**
	 * 添加退款记录
	 * @param supplierOrderId
	 * @param refund
	 * @return
	 */
	public ResultHandleT<BaseVo> addRefundInfo(final String supplierOrderId,OrdOrderTrafficRefund refund);
	
	public OrdOrderTrafficRefund getTrafficRefund(final Long orderTrafficId,String billNo);

	/**
	 * 根据订单Id获取所有出票信息
	 * @param supplierOrderId
	 * @return
	 */
	public Map<String, OrdOrderTrafficTicketInfo> getAllTicketsByOrderId(String supplierOrderId);

	/**
	 * 根据退票Id更新车票状态为退票
	 * @param string
	 */
	public void updateDrawbackTicketById(String ticketIds, String flowId);

	/**
	 * 根据票Id获取票信息
	 * @param ticketId
	 * @return
	 */
	public OrdOrderTrafficTicketInfo getTicketInfoById(Long ticketId);

	/**
	 * 根据Id获取火车票信息
	 * @param orderTrafficId
	 * @return
	 */
	public OrdOrderTraffic gettrafficById(Long orderTrafficId);

	/**
	 * 根据退款流水号获取退款信息
	 * @param refundId
	 * @return
	 */
	public OrdOrderTrafficRefund getTrafficRefundByRefundId(Long refundId);
	/**
	 * 获取火车票订单未退票数量
	 * @param orderTrafficId
	 * @return
	 */
	public int getTicketNoRefundNum(Long orderTrafficId);
	/**
	 * 获取订单子子项
	 * @param orderItemMetaId
	 * @return
	 */
	public OrdOrderItemMeta getOrdItemMetaById(Long orderItemMetaId);
	
	
	long selectSumRefundByTraffic(final Long trafficId);
}
