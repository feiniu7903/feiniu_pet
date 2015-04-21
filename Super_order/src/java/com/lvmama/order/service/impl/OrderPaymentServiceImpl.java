package com.lvmama.order.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayTransaction;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.logic.TransactionLogic;
import com.lvmama.order.service.OrderPaymentService;

public class OrderPaymentServiceImpl implements OrderPaymentService{
	private TransactionLogic transactionLogic;
	private OrderPersonDAO orderPersonDAO;
	private PayPaymentService payPaymentService;
	/**
	 * 日志DAO.
	 */
	private ComLogDAO comLogDAO;

	public void addOrdPayment(PayPayment payment) {
		payPaymentService.savePayment(payment);
	}
	
	
	/**
	 * 支付对象.
	 * @param orderId
	 * @param paymentWay
	 * @param status
	 * @return
	 */
	public PayPayment getPaymentByPramas(final Long orderId,final String paymentWay,final String status) {
		List<PayPayment> paymentList = payPaymentService.selectByObjectIdAndBizType(orderId, com.lvmama.comm.vo.Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
		PayPayment payment= new PayPayment();
		if(paymentList!=null&&paymentList.size()>0){
			for(int a=0;a<paymentList.size();a++){
				PayPayment	op = paymentList.get(a);
				if(op.getPaymentGateway().equals(paymentWay)&&op.getStatus().equals(status)){
					payment = op;
				}
			}
		}
		return payment;
	}
	
	
	public List<OrdPerson>  getOrdPersons(OrdPerson pars){
		return this.orderPersonDAO.getOrdPersons(pars);
	}
	
	public List<PayTransaction> findByParams(Map<String,Object> params){
		return payPaymentService.selectByParams(params);
	}
	
	public OrdOrder newPaymentSuccess(PayPayment ordPayment){
		return transactionLogic.newPaymentSuccess(ordPayment);
	}
	
	@Override
	public OrdOrder newTransferPaymentSuccess(final List<PayPayment> payPayments) {
		OrdOrder order = transactionLogic.newTransferPaymentSuccess(payPayments);
		return order;
	}
	
	public void setTransactionLogic(TransactionLogic transactionLogic) {
		this.transactionLogic = transactionLogic;
	}

	public PayPayment selectBySerial(String serial) {
		return payPaymentService.selectBySerial(serial);
	}

	public PayPayment findOrdPaymentByPrimaryKey(Long paymentId) {
		return payPaymentService.selectByPaymentId(paymentId);
	}

	public void updateOrderPayment(PayPayment payment) {
		payPaymentService.updatePayment(payment);
	}

	public void setOrderPersonDAO(OrderPersonDAO orderPersonDAO) {
		this.orderPersonDAO = orderPersonDAO;
	}

	@Override
	public long findPaymentSuccessCount(Long orderId) {
		return payPaymentService.selectPaymentSuccessCount(orderId);
	}

	/**
	 * 查询是储值卡支付的金额.
	 * 
	 * @param orderId
	 * @return
	 */
	@Override
	public Long selectCardPaymentSuccessSumAmount(Long orderId){
		return payPaymentService.selectCardPaymentSuccessSumAmount(orderId);
	}

	/**
	 * 根据支付网关查询当天支付记录数.
	 * @param gateway 支付网关
	 * @return 记录数
	 */
	@Override
	public Long selectPaymentCountByParams(String gateway) {
		return payPaymentService.selectPaymentCountByGateway(gateway);
	}
	/**
	 * 根据订单号、支付网关、支付记录状态查询支付记录.
	 * @param orderId 订单号.
	 * @param paymentGateway 支付网关.
	 * @param status 支付状态.
	 * @return List&lt;OrdPayment&gt;.
	 */
	@Override
	public List<PayPayment> selectPayPaymentByObjectIdAndPaymentGateway(
			Long orderId, String paymentGateway, String status) {
		return payPaymentService.selectPayPaymentByObjectIdAndPaymentGateway(orderId, paymentGateway, status);
	}
	/**
	 * 保存操作日志.
	 * @param objectType 表名.
	 * @param objectId 表ID.
	 * @param operatorName 操作人.
	 * @param logType 日志类型码.
	 * @param logName 日志名称.
	 * @param content 日志内容.
	 */
	private void saveComLog(final String objectType, final Long objectId, final String operatorName,
			final String logType, final String logName, final String content) {
		final ComLog log = new ComLog();
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		log.setContent(content);
		comLogDAO.insert(log);
	}


	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}


	@Override
	public String findSerialByPaymentTradeNo(String paymentTradeNo) {
		return payPaymentService.selectSerialByPaymentTradeNo(paymentTradeNo);
	}


	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
   
}
