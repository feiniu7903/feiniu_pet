package com.lvmama.comm.abroad.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.abroad.po.AhotelOrdPayment;
import com.lvmama.comm.abroad.po.AhotelOrdRefundment;
import com.lvmama.comm.abroad.po.AhotelOrdSaleService;
import com.lvmama.comm.abroad.po.AhotelOrdSaleServiceDeal;
import com.lvmama.comm.abroad.vo.request.ReservationsOrderReq;
import com.lvmama.comm.abroad.vo.response.ReservationsOrder;
import com.lvmama.comm.bee.po.ord.OrdOrder;


public interface AbroadhotelOrderService {

	
	/**
	 * 新增支付记录.
	 * @param payment
	 */
	void insertAhotelOrdPayment(AhotelOrdPayment payment);
	
	/**
	 * 更新支付记录.
	 * @param payment
	 */
	void updateAhotelOrdPayment(AhotelOrdPayment payment);

	/**
	 * 通过支付流ID查询一个支付流水记录
	 */
	 AhotelOrdPayment selectAhotelOrdPaymentBySerial(String serial);
	/**
	 * 新增退款记录.
	 * @param payment
	 */
	Long insertAhotelOrdRefundment(AhotelOrdRefundment refundment);
	
	/**
	 * 更新退款记录.
	 * @param payment
	 */
	void updateAhotelOrdRefundment(AhotelOrdRefundment refundment);
	/**
	 * 新增订单售后服务记录.
	 * @param ordSaleService
	 * @return
	 */
	Long insertAhotelOrdSaleService(AhotelOrdSaleService ordSaleService);
	
	/**
	 * 更新订单售后服务记录.
	 * @param payment
	 */
	void updateAhotelOrdSaleService(AhotelOrdSaleService ordSaleService);
	
	/**
	 * 删除订单退款服务.
	 * @param ordSaleId
	 */
	void deleteAhotelOrdSaleService(String ordSaleId);
	
	
	/**
	 * 根据海外酒店组装的数据取酒店订单列表.
	 * @param orderId
	 * @return
	 */
	 List<ReservationsOrder> queryOrderByParms(ReservationsOrderReq reservationsOrderReq);
	
	/**
	 * 根据海外酒店的订单号 查出海外酒店的订单,并组装成 order对象,属性不全.
	 *  <pre>
	 *  OrderId,setOughtPay,ActualPay,PaymentChannel,PaymentStatus,CreateTime,ApproveTime,WaitPayment
	 *  </pre>
	 * @param orderId
	 * @return
	 */
	OrdOrder queryOrderByHotelOrderId(String orderId);
	
	/**
	 * 海外酒店订单的线下支付. 
	 * @param ordPayment
	 * @param operatorId
	 */
	void offlinePay(AhotelOrdPayment ordPayment); 
	/**
	 * 酒店订单支付完成处理.
	 * @param AhotelOrdPayment
	 */
	void paymentSuccess(AhotelOrdPayment ordPayment);
	
	/**
	 * 申请退款.
	 * @param orderId 订单ID
	 * @param saleServiceId 售后服务ID
	 * @param amount 退款金额，以分为单位
	 * @param refundType  退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus  退款状态
	 * @param reason  退款原因
	 * @param operateName  操作人
	 * @return <code>true</code>代表申请原路退款成功，<code>false</code> 代表申请原路退款失败
	 */
	public boolean applyRefund(final Long orderId, final Long saleServiceId,final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName);
	/**
	 * 修改退款单.
	 * 
	 * @param refundmentId
	 *            退款单ID
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表修改退款单成功，<code>false</code> 代表修改退款单失败
	 */
	public boolean updateRefund(final Long refundmentId,final Long amount,
			final String refundType, final String refundStatus,
			final String reason, final String operatorName);
	
	
	/**
	 * 退款单查询(分页).
	 * @param param
	 * @param skipResults
	 * @param maxResults
	 * @return
	 */
	List<AhotelOrdRefundment> findAhotelOrdRefundByParam(Map param,int skipResults,int maxResults);
	/**
	 * 退款单查询(分页).
	 * @param param
	 * @param skipResults
	 * @param maxResults
	 * @return
	 */
	Long findAhotelOrdRefundByParamCount(Map param);
	
	
	
	/**
	 * 售后服务查询.
	 * @param param
	 * @return
	 */
	List<AhotelOrdSaleService> findFullAhotelOrdSaleServiceByParam(Map param);
	/**
	 * 售后服务查询(分页).
	 * @param param
	 * @param skipResults
	 * @param maxResults
	 * @return
	 */
	List<AhotelOrdSaleService> findAhotelOrdSaleServiceByParam(Map param,int skipResults,int maxResults);
	/**
	 * 售后服务查询(分页).
	 * @param param
	 * @param skipResults
	 * @param maxResults
	 * @return
	 */
	Long findAhotelOrdSaleServiceByParamCount(Map param);
	
	/**
	 * 退款单金额.
	 * @param param
	 * @return
	 */
	java.math.BigDecimal findAhotelOrdfundByParamSumAmount(Map param);
	
	/**
	 *  获取海外酒店支付记录DAO.
	 * @return
	 */
	//AhotelOrdPaymentDAO getAhotelOrdPaymentDAO();
	/**
	 *  设置海外酒店支付记录DAO.
	 * @param ahotelOrdPaymentDAO
	 */
	//void setAhotelOrdPaymentDAO(AhotelOrdPaymentDAO ahotelOrdPaymentDAO);
	/**
	 * 获取海外酒店订单服务.
	 * @return
	 */
	IReservationsOrder getReservationsOrder();


	/**
	 * 获取海外酒店退款记录DAO.
	 * @return
	 */
	//AhotelOrdRefundMentDAO getAhotelOrdRefundMentDAO();

	/**
	 * 设置海外酒店退款记录DAO.
	 * @param ahotelOrdRefundMentDAO
	 */
	//void setAhotelOrdRefundMentDAO(AhotelOrdRefundMentDAO ahotelOrdRefundMentDAO);

	/**
	 * 获取海外酒店售后服务DAO.
	 * @return
	 */
	//AhotelOrdSaleServiceDAO getAhotelOrdSaleServiceDAO();

	/**
	 * 设置海外酒店售后服务DAO.
	 * @param ahotelOrdSaleServiceDAO
	 */
	//void setAhotelOrdSaleServiceDAO(AhotelOrdSaleServiceDAO ahotelOrdSaleServiceDAO);
	/**
	 * 根据海外酒店的订单号 查出海外酒店的订单.
	 * @param orderId
	 * @return
	 */
	ReservationsOrder queryAbroadHotelOrderByOrderId(String orderId);
	

	//AhotelOrdSaleServiceDealDAO getAhotelOrdSaleServiceDealDAO();

	//void setAhotelOrdSaleServiceDealDAO(AhotelOrdSaleServiceDealDAO ahotelOrdSaleServiceDealDAO);
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	List<AhotelOrdSaleServiceDeal> getAhotelOrdSaleServiceAllByParam(Map params);

	
	Long addAhotelOrdSaleServiceDeal(AhotelOrdSaleServiceDeal ordSaleServiceDeal);
	/**
	 * 保存操作日志.
	 * @param objectType 表名.
	 * @param objectId 表ID.
	 * @param operatorName 操作人.
	 * @param logType 日志类型码.
	 * @param logName 日志名称.
	 * @param content 日志内容.
	 */
	void saveComLog(String objectType, Long objectId, String operatorName,String logType, String logName, String content);
	/**
	 * 更新退款单审核状态.
	 * 
	 * @param refundmentId
	 *            退款单ID
	 * @param status
	 *            审核状态（拒绝/通过）
	 * @param memo
	 *            审核原因
	 * @param operatorName
	 *            操作人
	 * @return <code>true</code>代表更新退款单审核状态成功，<code>false</code> 代表更新退款单审核状态失败
	 */
	boolean updateOrderRefundmentApproveStatus(Long refundmentId,String status, String memo, String operatorName);
	/**
	 * 根据退款单ID查询退款单.
	 * @param refundmentId
	 * @return
	 */
	AhotelOrdRefundment findAhotelOrdRefundByRefundmentId(Long refundmentId);
	/**
	 * 根据订单号查询该订单所有支付信息.
	 * @param orderNo
	 * @return
	 */
	List<AhotelOrdPayment> queryAhotelOrdPaymentByOrderId(Long orderId);
	/**
	 * 根据订单号查询已经退款成功的退款单.
	 * @param orderId 订单号.
	 * @return AhotelOrdRefundment
	 */
	AhotelOrdRefundment findHasRefundedOrderRefundmentByOrderId(Long orderId);
	
	boolean rejectAbroadHotelCashRefundment(Long refundmentId, String memo, String operatorName);	
}