package com.lvmama.order.service;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdInvoiceRelation;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.utils.Pair;

/**
 * 订单发票服务接口.
 *
 * <pre></pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public interface OrderInvoiceService {
	/**
	 * 红冲操作更改red_flag为true	 * 
	 */
	boolean updateRedFlag(OrdInvoice record,String operatorId);
	
		
	/**
	 * 新增OrdInvoice.
	 *
	 * @param invoice
	 *            发票对象
	 * @param orderIds
	 *            订单ID列表
	 * @param operatorId
	 *            操作人ID
	 *            
	 * @return 返回新增加的发票
	 * @throws InvoiceException 无法处理的发票抛出的异常
	 */
	OrdInvoice insert(Pair<Invoice,Person> invoice, List<Long> orderIds,String operatorId)throws InvoiceException;
	
	/**
	 * 添加多张发票
	 * @param invoices
	 * @param orderId
	 * @param operatorId
	 * @return
	 * @throws InvoiceException
	 */
	void insert(List<Pair<Invoice,Person>> invoices,Long orderId,String operatorId)throws InvoiceException;
	/**
	 * 删除OrdInvoice.
	 *
	 * @param invoiceId
	 *            发票ID
	 * @param operatorId
	 *            操作人ID
	 *            
	 * @return 删除发票是否成功
	 */
	boolean delete(Long invoiceId, String operatorId);
	
	/**
	 * 修改OrdInvoice.
	 *
	 * @param ordInvoice
	 *            发票对象
	 * @param operatorId
	 *            操作人ID
	 *            
	 * @return 修改发票是否成功
	 */
	boolean update(OrdInvoice ordInvoice, String operatorId);
	
	/**
	 * 修改OrdInvoice.
	 *
	 * @param ordInvoice
	 *            发票对象
	 * @param operatorId
	 *            操作人ID
	 * @param updateContent
	 * 				日志
	 *            
	 * @return 修改发票是否成功
	 */
	boolean update(OrdInvoice ordInvoice, String operatorId, String updateContent);
	
	/**
	 * 查询OrdInvoice.
	 * 
	 * @param orderId
	 *            订单ID
	 *            
	 * @return <pre>
	 * 指定订单ID的OrdInvoice列表，如果指定订单ID没有对应OrdInvoice， 则返回元素数为0的OrdInvoice列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * 
	 */
	List<OrdInvoice> queryInvoiceByOrderId(Long orderId);
	
	/**
	 * 查询OrdInvoice.
	 * 
	 * @param status
	 *            发票状态
	 *            
	 * @return <pre>
	 * 指定订单ID的OrdInvoice列表，如果指定订单ID没有对应OrdInvoice， 则返回元素数为0的OrdInvoice列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * 
	 */
	List<OrdInvoice> queryInvoiceByStatus(String status);
	
	/**
	 * 修改invoiceNo和billDate字段.
	 * 
	 * @param invoiceNo
	 *            发票号
	 * @param billDate
	 *            开票日期
	 * @param invoiceId
	 *            发票ID
	 * @param operatorId
	 *            操作人ID
	 *            
	 * @return 修改是否成功
	 * 
	 */
	boolean update(String invoiceNo, Date billDate, Long invoiceId, String operatorId);
	
	/**
	 * 修改status字段.
	 * 
	 * @param status
	 *            发票状态（未开票、已开票、作废）
	 * @param invoiceId
	 *            发票ID
	 * @param operatorId
	 *            操作人ID,如果为SYSTEM时为自动操作
	 *            
	 * @return 修改是否成功
	 * 
	 */
	boolean update(String status, Long invoiceId, String operatorId);
	
	
	boolean updateExpressNo(Long invoiceId,String expressNo,String operatorId);
	
	/**
	 * 查询OrdInvoice.
	 * 
	 * @param invoiceId
	 *            主键
	 *            
	 * @return OrdInvoice
	 * 
	 */
	OrdInvoice selectByPrimaryKey(Long invoiceId);
	
	/**
	 * 查询发票订单间关联表.
	 * @param invoiceId ord_invoice主键
	 * @return
	 */
	List<OrdInvoiceRelation> selectInvoiceRelationListByInvoiceId(Long invoiceId);
	
	/**
	 * 修改是否需要发票NeedInvoice字段.
	 * 
	 * @param needInvoice
	 *            是否需要发票
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 *            
	 * @return 修改是否成功
	 * 
	 */
	boolean updateNeedInvoice(String needInvoice, Long orderId, String operatorId);
	
	/**
	 * 取订单的退款补偿金额
	 * @param orderId
	 * @return
	 */
	long getSumCompensationAndRefundment(Long orderId);
	
	/**
	 * 获取订单退款金额
	 * 
	 * @param orderId
	 * @return
	 */
	public long getRefundAmountByOrderId(Long orderId, String sysCode);
	
	/**
	 * 查询一个订单存在几张发票
	 * @param orderId
	 * @return
	 */
	long selectInvoiceCountByOrderId(final Long orderId);
	
	/**
	 * 查询已经开票我的订单金额
	 * @param orderId
	 * @param excludeInvoiceId 
	 */
	long getOrderInvoiceAmountNotInvoiceId(final Long orderId,final Long excludeInvoiceId);
}