package com.lvmama.pet.pay.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.vo.CashPaymentComboVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PayAndPrePaymentVO;

/**
 * 支付记录处理DAO.
 * @author liwenzhan
 *
 */
public class PayPaymentDAO extends BaseIbatisDAO {

	/**
	 * 保存支付记录 .
	 * @param payment
	 * @return
	 */
	public Long savePayment(PayPayment payment){
		return (Long)super.insert("PAY_PAYMENT.insert", payment);
	}
	
	/**
	 * 保存支付记录 .
	 * @param payment
	 * @return
	 */
	public void updatePayment(PayPayment payment){
		super.update("PAY_PAYMENT.update", payment);
	}

	/**
	 * 按PK取PAYMENT
	 * @param paymentId
	 * @return
	 */
	public PayPayment selectByPaymentId(Long paymentId){
		return (PayPayment) super.queryForObject("PAY_PAYMENT.selectByPaymentId", paymentId);
	}
	
	/**
	 * 按PK取PAYMENT
	 * @param paymentId
	 * @return
	 */
	public PayPayment selectAndLockByPaymentId(Long paymentId){
		return (PayPayment) super.queryForObject("PAY_PAYMENT.selectAndLockByPaymentId", paymentId);
	}
	
	/**
	 * 查询出来需要通知而未通知的PAYMENT
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PayPayment> selectUnNotifiedPayment(){
		return super.queryForList("PAY_PAYMENT.selectUnNotifiedPaymentList");
	}
	
	/**
	 * 通过交易流水号+对象ID获取支付记录
	 * @author ZHANG Nan
	 * @param paymentTradeNo 交易流水号
	 * @param objectId 对象ID
	 * @return 支付记录
	 */
	public PayPayment selectByPaymentTradeNoAndObjectId(String paymentTradeNo,String objectId){
		Map<String,String> map=new HashMap<String,String>();
		map.put("paymentTradeNo", paymentTradeNo);
		map.put("objectId", objectId);
		return (PayPayment) super.queryForObject("PAY_PAYMENT.selectByPaymentTradeNoAndObjectId", map);
	}
	/**
	 * 通过支付流ID查询一个支付流水记录.
	 * @param serial
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PayPayment> selectByPaymentTradeNo(String paymentTradeNo){
		return super.queryForList("PAY_PAYMENT.selectByPaymentTradeNo", paymentTradeNo);
	}
	/**
	 * 通过PaymentTradeNo判断支付记录是否已存在
	 * @author ZHANG Nan
	 * @param PaymentTradeNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isExistsByPaymentTradeNo(String PaymentTradeNo){
		List<PayPayment> payPaymentList=super.queryForList("PAY_PAYMENT.selectByPaymentTradeNo", PaymentTradeNo);
		if(payPaymentList!=null && payPaymentList.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据订单ID查询OrdPayment的记录数.
	 * @param objectId
	 * @return
	 */
	public Long selectPaymentCount(final Long objectId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectId", objectId);
		return (Long) super.queryForObject(
				"PAY_PAYMENT.selectPaymentCount", map);
	}
	
	/**
	 *  根据订单ID和状态查询支付成功的记录.
	 * @param objectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PayPayment> selectPaymentListByParas(final Long objectId, String bizType){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectId",objectId);
		map.put("status", Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
		map.put("bizType", bizType);
		map.put("orderby", "AMOUNT");
		return super.queryForList("PAY_PAYMENT.selectPaymentListByParas", map);
		
	}
	/**
	 *  根据条件查询相关记录.
	 * @param objectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PayPayment> selectPaymentListByMap(final  Map<String, Object> map){
		map.put("orderby", "CREATE_TIME");
		map.put("order", "DESC");
		return super.queryForList("PAY_PAYMENT.selectPaymentListByParas", map);
	}
	
	/**
	 *  预授权扣款请求补偿查询
	 * @param objectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PayPayment> selectNewPrePayData(final  Map<String, Object> map){
		map.put("orderby", "CREATE_TIME");
		map.put("order", "DESC");
		return super.queryForList("PAY_PAYMENT.selectNewPrePayData", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<PayAndPrePaymentVO> selectPaymentListByParasToRecon(final  Map<String, Object> map){
		return super.queryForListForReport("PAY_PAYMENT.selectPaymentListByParasToRecon", map);
	}
	
	public PayPayment selectBySerial(String serial) {
		return (PayPayment) super.queryForObject(
				"PAY_PAYMENT.selectBySerial", serial);
	}
	
	public Long sumPayedOrdPaymentAmountByObjectId(Long objectId) {
		return (Long) super.queryForObject(
				"PAY_PAYMENT.sumPayedOrdPaymentAmountByObjectId", objectId);
	}
	/**
	 * 
	 * @param objectId
	 * @param objectType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PayPayment> selectByObjectIdAndBizType(Long objectId, String objectType){
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("objectId", objectId);
		paras.put("bizType", objectType);
		return super.queryForList("PAY_PAYMENT.selectByObjectIdAndBizType", paras);
	}
	/**
	  * 根据objectId查询所有的支付记录和预授权的记录.
	  * @param objectId
	  * @param bizType
	  * @return
	  */
	@SuppressWarnings("unchecked")
	public List<PayAndPrePaymentVO> selectPayAndPreByObjectIdAndBizType(Long objectId, String bizType){
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("objectId", objectId);
		paras.put("bizType", bizType);
		return super.queryForList("PAY_PAYMENT.selectPreAndPayByObjectIdAndBizType", paras);
	}
	
	/**
	 * 获取与objectId订单一起合并支付的订单的支付信息
	 * @author ZHANG Nan
	 * @param objectId 订单号
	 * @param paymentTradeNo 对账流水号
	 * @param gatewayTradeNo 网关交易号
	 * @param paymentGateway 支付网关
	 * @return 与objectId订单一起合并支付的订单的支付信息
	 */
	@SuppressWarnings("unchecked")
	public List<PayAndPrePaymentVO> getOtherMergePayListByPayment(Long objectId,String paymentTradeNo,String gatewayTradeNo,String paymentGateway){
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("objectId", objectId);
		paras.put("paymentTradeNo", paymentTradeNo);
		paras.put("gatewayTradeNo",gatewayTradeNo);
		paras.put("paymentGateway", paymentGateway);
		//合并支付目前选择支付网关的范围 只包括类型为ONLINE的
		paras.put("gatewayType", Constant.PAYMENT_GATEWAY_TYPE.ONLINE.name());
		return super.queryForList("PAY_PAYMENT.getOtherMergePayListByPayment", paras);
	}
	
	public Long selectPaymentSuccessCount(Long objectId) {
		return (Long) super.queryForObject("PAY_PAYMENT.selectPaymentSuccessCount", objectId);
	}

	 /**
	  * 查询是储值卡支付的金额.
	  * @param orderId
	  * @return
	  */
	public Long selectCardPaymentSuccessSumAmount(Long objectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectId", objectId);
		map.put("paymentGateway", Constant.PAYMENT_GATEWAY.STORED_CARD.name());
		map.put("status", Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());

		return (Long) super.queryForObject(
				"PAY_PAYMENT.selectPaymentSumAmount", map);
	}

	 /**
	  * 查询某支付网关当天所有支付记录数.
	  * @param params
	  * @return
	  */
	 public Long selectPaymentCountByGateway(String gateway) {
	  Map<String,Object> map = new HashMap<String,Object>();
	  map.put("paymentGateway", gateway);
	  map.put("createTime", new Date());
	  return (Long) super.queryForObject(
	    "PAY_PAYMENT.selectPaymentCount", map);
	 }

	 /**
	  * 根据订单号、支付网关、支付记录状态查询支付记录.
	  * @param orderId 订单号.
	  * @param paymentGateway 支付网关.
	  * @param status 支付状态.
	  * @return List&lt;OrdPayment&gt;.
	  */
	 @SuppressWarnings("unchecked")
	public List<PayPayment> selectPayPaymentByObjectIdAndPaymentGateway(
	   Long objectId, String paymentGateway, String status) {
	  Map<String, Object> map = new HashMap<String, Object>();
	  map.put("objectId", objectId);
	  map.put("paymentGateway", paymentGateway);
	  map.put("status", status);
	  return super.queryForList("PAY_PAYMENT.selectCardPayedPayPaymentListBy", map);
	 }

	 /**
	  * 根据订单号、支付网关、支付记录状态(多个状态)查询支付记录.
	  * @param orderId 订单号.
	  * @param paymentGateway 支付网关.
	  * @param status 支付状态.
	  * @return List&lt;OrdPayment&gt;.
	  */
	 @SuppressWarnings("unchecked")
	public List<PayPayment> selectPayPaymentByObjectIdAndPaymentGatewayAndStatuss(Long objectId, String paymentGateway, String statuss) {
	  Map<String, Object> map = new HashMap<String, Object>();
	  map.put("objectId", objectId);
	  map.put("paymentGateway", paymentGateway);
	  map.put("statuss", statuss);
	  return super.queryForList("PAY_PAYMENT.selectCardPayedPayPaymentListBy", map);
	 }

	  /**
	     * 根据驴妈妈与银行或支付平台的交易流水号查询驴妈妈与客户的支付流水号.
	     * @param paymentTradeNo 
	     * @return 
	     */
	 public String selectSerialByPaymentTradeNo(String paymentTradeNo) {
	  return (String) super.queryForObject(
	    "PAY_PAYMENT.selectSerialByPaymentTradeNo", paymentTradeNo);
	 }

	/**
	 * 根据订单号查询储值卡成功支付成功的所有记录.
	 * @param objectId
	 * @return
	 */
	public List<PayPayment> selectCardPayedPayPaymentListByObjectId(
			Long objectId) {
		return this.selectPayPaymentByObjectIdAndPaymentGateway(objectId,
				Constant.PAYMENT_GATEWAY.STORED_CARD.name(),
				Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
	}
	
	@SuppressWarnings("unchecked")
	public List<CashPaymentComboVO> selectPayPaymentAndDetailByParamMap(Map<String, String> paramMap){
		return super.queryForListForReport("PAY_PAYMENT.selectPayPaymentAndDetailByParamMap", paramMap);
	}	
	public Long selectPayPaymentAndDetailByParamMapCount(Map<String, String> paramMap){
		return (Long) super.queryForObject("PAY_PAYMENT.selectPayPaymentAndDetailByParamMapCount", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,BigDecimal> selectPayPaymentAuditAmountByParamMap(Map<String, String> paramMap){
		return (Map<String,BigDecimal>) super.queryForObject("PAY_PAYMENT.selectPayPaymentAuditAmountByParamMap", paramMap);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CashPaymentComboVO> selectOtherPayPaymentAndDetailByParamMap(Map<String, String> paramMap){
		return super.queryForListForReport("PAY_PAYMENT.selectOtherPayPaymentAndDetailByParamMap", paramMap);
	}	
	public Long selectOtherPayPaymentAndDetailByParamMapCount(Map<String, String> paramMap){
		return (Long) super.queryForObject("PAY_PAYMENT.selectOtherPayPaymentAndDetailByParamMapCount", paramMap);
	}
	
	/**
     * 根据电话支付订单号和网关支付类型查询电话支付来电电话号码.
     * @param orderId
     * @param paymentGateway
     * @return payMobileNum
    */
    public String selectPayMobileNumByPaymentOrderNoAndPaymentGateway(Long orderId, String paymentGateway) {
    	Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("objectId", orderId);
		paras.put("paymentGateway", paymentGateway);
       return (String) super.queryForObject(
       "PAY_PAYMENT.selectPayMobileNumByPaymentOrderNoAndPaymentGateway", paras);
    }
    
}
