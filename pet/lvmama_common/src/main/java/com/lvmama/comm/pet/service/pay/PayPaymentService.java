package com.lvmama.comm.pet.service.pay;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentDetail;
import com.lvmama.comm.pet.po.pay.PayPrePayment;
import com.lvmama.comm.pet.po.pay.PayTransaction;
import com.lvmama.comm.vo.CashPaymentComboVO;
import com.lvmama.comm.vo.PayAndPrePaymentVO;

public interface PayPaymentService {

	/**
	 * 支付成功的回调处理.
	 * @param payment
	 * @param success
	 * @return PayPayment
	 */
	List<PayPayment> callBackPayPayment(PayPayment payment,boolean success);
	
	/**
	 * 保存支付记录 .
	 * @param payment
	 * @return
	 */
	Long savePayment(PayPayment payment);
	/**
	 * 保存支付信息及支付扩展信息
	 * @author ZHANG Nan
	 * @param payment 支付信息
	 * @param payPaymentDetail 支付扩展信息
	 * @return 支付信息主键
	 */
	public Long savePaymentAndDetail(PayPayment payment,PayPaymentDetail payPaymentDetail);
	/**
	 * 保存支付记录和预授权支付记录  .
	 * @param payment
	 * @return
	 */
    boolean savePaymentAndPrePayment(final PayPayment payment,final PayPrePayment prePayment);

	/**
	 * 更新支付记录.
	 * @param payment
	 * @return
	 */
    boolean updatePayment(PayPayment payment);
	/**
	 * 更新支付信息及扩展信息
	 * @author ZHANG Nan
	 * @param payment 支付信息
	 * @param payPaymentDetail 支付扩展信息
	 * @return 成功或失败
	 */
	public boolean updatePaymentAndDetail(PayPayment payment,PayPaymentDetail payPaymentDetail) ;
	/**
	 * 更新支付记录和预授权支付记录.
	 * @param payment
	 * @param prePayment
	 * @return
	 */
    boolean updatePayPrePayment(final PayPrePayment prePayment);
    /**
     * 根据payment对象的paymentId获取prePayment对象并将payment的callBack_time更新到prePayment对象中
     * @author ZHANG Nan
     * @param payment
     */
	public void updatePrePayment(PayPayment payment);
	
	/**
	 * 按PK取PAYMENT
	 * @param paymentId
	 * @return
	 */
	PayPayment selectByPaymentId(Long paymentId);
	
	/**
	 * 查询出来需要通知而未通知的PAYMENT
	 * @return
	 */
	List<PayPayment> selectUnNotifiedPayment();
	/**
	 * 通过交易流水号+对象ID获取支付记录
	 * @author ZHANG Nan
	 * @param paymentTradeNo 交易流水号
	 * @param objectId 对象ID
	 * @return 支付记录
	 */
	public PayPayment selectByPaymentTradeNoAndObjectId(String paymentTradeNo,String objectId);
	/**
	 * 通过支付流ID查询一个支付流水记录.
	 * @param serial
	 * @return
	 */
	List<PayPayment> selectByPaymentTradeNo(String paymentTradeNo);
	/**
	 * 通过支付流ID和支付网关查询一个支付流水记录.
	 * 
	 * @param paymentTradeNo
	 * @return
	 */
	public List<PayPayment> selectByPaymentTradeNoAndGatewayPure(String paymentTradeNo,String gatewayIN,Boolean oriObjectIdIsNull);
	/**
	 * 通过支付流ID和支付网关查询一个支付流水记录.
	 * 
	 * @param paymentTradeNo
	 * @return
	 */
	 List<PayPayment> selectByPaymentTradeNoAndGateway(String paymentTradeNo,String gateway);
	/**
	 * 通过对账流水号和多个支付网关查询多个支付记录
	 * @author ZHANG Nan
	 * @param paymentTradeNo
	 * @param gateways
	 * @return
	 */
	List<PayPayment> selectByPaymentTradeNoAndGateways(String paymentTradeNo,String gateways);

	/**
	 * 通过支付Payment的ID查询预授权的支付流水记录.
	 * @param serial
	 * @return
	 */
	PayPrePayment selectPrePaymentByPaymentId(Long paymentId);
	
	/**
	 * 根据订单ID查询OrdPayment的记录数.
	 * @param orderId
	 * @return
	 */
	Long selectPaymentCount(Long orderId);

	/**
	 * 根据Serial查询PayPayment对象
	 * @param serial
	 * @return
	 */
	PayPayment selectBySerial(String serial);
	/**
	 * 根据objectId统计支付成功的支付金额总和.
	 * @param objectId
	 * @return
	 */
	Long sumPayedPayPaymentAmountByObjectId(Long objectId);
	/**
	 * 根据objectId查询所有的支付记录.
	 * @param objectId
	 * @return
	 */
	List<PayPayment> selectByObjectIdAndBizType(Long objectId, String bizType);
	
	
	/**
	  * 根据objectId查询所有的支付记录和预授权的记录.
	  * @param objectId
	  * @param bizType
	  * @return
	  */
	List<PayAndPrePaymentVO> selectPayAndPreByObjectIdAndBizType(Long objectId, String bizType);

	/**
	 * 根据objectId查询成功的支付记录数.
	 * @param objectId
	 * @return
	 */
	Long selectPaymentSuccessCount(Long objectId);

//	/**
//	  * 查询是储值卡支付的纪录.
//	  * 
//	  * @param objectId
//	  * @return
//	  */
//	Long selectCardPaymentSuccessCount(Long objectId);

	 /**
	  * 查询是储值卡支付的金额.
	  * @param orderId
	  * @return
	  */
	Long selectCardPaymentSuccessSumAmount(Long objectId);

	 /**
	  * 查询某支付网关当天所有支付记录数.
	  * @param params
	  * @return
	  */
	Long selectPaymentCountByGateway(String gateway);

	/**
	 * 根据订单号、支付网关、支付记录状态查询支付记录.
	 * 
	 * @param objectId
	 *            订单号.
	 * @param paymentGateway
	 *            支付网关.
	 * @param status
	 *            支付状态.
	 * @return List&lt;OrdPayment&gt;.
	 */
	List<PayPayment> selectPayPaymentByObjectIdAndPaymentGateway(Long objectId, String paymentGateway, String status);
	
	/**
     * 根据驴妈妈与银行或支付平台的交易流水号查询驴妈妈与客户的支付流水号.
     * @param paymentTradeNo 
     * @return 
     */
	String selectSerialByPaymentTradeNo(String paymentTradeNo);

	/**
	 * 根据订单号查询储值卡成功支付成功的所有记录.
	 * @param objectId
	 * @return
	 */
	List<PayPayment> selectCardPayedPayPaymentListByObjectId(Long objectId);
	/**
	 * 保存PayTransaction.
	 * @param payTransaction
	 * @return 
	 */
	Long savePayTransaction(PayTransaction payTransaction);
	/**
	 * 根据订单ID获取{@link FincTransaction}.
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link FincTransaction}，
	 * 如果指定订单ID没有对应{@link FincTransaction}，则返回<code>null</code>
	 * </pre>
	 */
	List<PayTransaction> selectPayTransactionByObjectId(Long orderId);

	/**
	 * 订单已通过的预授权的处理
	 * @param objectId
	 * @return
	 */
	List<PayPayment> createPrePaymentCompleteData(final Long objectId,final String bizType);
	
	/**
	 * 预授权扣款请求补偿查询
	 * @param objectId
	 * @return
	 */
	List<PayPayment> selectNewPrePayData(final Long objectId,final String bizType);

	/**
	 * 
	 * @param params
	 * @return
	 */
	List<PayTransaction> selectByParams(Map params);
	
	/**
	 * 转移支付记录
	 * @param orgObjectId 
	 *      老的订单号
	 * @param newObjectId 
	 *      新的订单号
	 * @param bizType
	 *      业务类型
	 * @parm objectType
	 *      对象类型
	 * @return
	 *      更新后的支付记录列表
	 * 
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;将订单号为<code>orgObjectId</code>的支付记录变成订单号<code>newObjectId</new>的支付记录，这种转换<br/>
	 * 将是无条件的，即无论支付是否成功都会进行转移。</p>
	 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;需要注意，函数本身并不校验新旧订单是否存在或其状态是否合理，也不关心支付<br/>迁移所带来的任何状态修改的操作。</p>
	 */
	List<PayPayment> transferPayment(Long orgObjectId, Long newObjectId, String bizType, String objectType);
	/**
	 * 通过PaymentTradeNo判断支付记录是否已存在
	 * @author ZHANG Nan
	 * @param PaymentTradeNo
	 * @return
	 */
	public boolean isExistsByPaymentTradeNo(String PaymentTradeNo);
	/**
	 * 现金收款监控
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 现金收款监控集合
	 */
	public List<CashPaymentComboVO> selectPayPaymentAndDetailByParamMap(Map<String, String> paramMap);
	/**
	 * 现金收款监控-总数
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 现金收款监控集合-总数
	 */
	public Long selectPayPaymentAndDetailByParamMapCount(Map<String, String> paramMap);
	/**
	 * 获取现金支付的收款金额与已审核通过的收款金额(只计算pay_payment_detail表中存在的支付数据)
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 现金收款联合对象
	 */
	public Map<String,Long> selectPayPaymentAuditAmountByParamMap(Map<String, String> paramMap);
	/**
	 * 获取与objectId订单一起合并支付的订单的支付信息
	 * @author ZHANG Nan
	 * @param objectId 订单号
	 * @param paymentTradeNo 对账流水号
	 * @param gatewayTradeNo 网关交易号
	 * @param paymentGateway 支付网关
	 * @return 与objectId订单一起合并支付的订单的支付信息
	 */
	public List<PayAndPrePaymentVO> getOtherMergePayListByPayment(Long objectId,String paymentTradeNo,String gatewayTradeNo,String paymentGateway);
	/**
	 * 根据支付网关+对账日期+状态 获取成功支付的记录用于预先插入到对账结果表中
	 * @author ZHANG Nan
	 */
	public List<PayAndPrePaymentVO> selectPaymentListByParasToRecon(String paymentGatewayIN,Date reconDate);
	
	/**
	 * 其它收款监控
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 现金收款监控集合
	 */
	public List<CashPaymentComboVO> selectOtherPayPaymentAndDetailByParamMap(Map<String, String> paramMap);
	/**
	 * 其它收款监控-总数
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 现金收款监控集合-总数
	 */
	public Long selectOtherPayPaymentAndDetailByParamMapCount(Map<String, String> paramMap);
	 /**
	  * 根据订单号、支付网关、支付记录状态(多个状态)查询支付记录.
	  * @param orderId 订单号.
	  * @param paymentGateway 支付网关.
	  * @param status 支付状态.
	  * @return List&lt;OrdPayment&gt;.
	  */
	public List<PayPayment> selectPayPaymentByObjectIdAndPaymentGatewayAndStatuss(Long objectId, String paymentGateway, String statuss);
	
	/**
     * 根据电话支付订单号、支付网关查询电话支付来电电话号码.
     * @param orderId 订单号
     * @param paymentGateway 支付网关
     * @return payMobileNum
    */
    public String selectPayMobileNumByPaymentOrderNoAndPaymentGateway(Long orderId, String paymentGateway);
    
	
}