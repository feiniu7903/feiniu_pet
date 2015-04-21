package com.lvmama.pet.pay.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayPaymentAndRefundment;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.vo.PayRefundDetail;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 退款处理DAO.
 * @author liwenzhan
 *
 */
public class PayPaymentRefundmentDAO extends BaseIbatisDAO {

	/**
	 * 保存退款记录 .
	 * @param payment
	 * @return
	 */
	public Long savePayPaymentRefundment(PayPaymentRefundment payRefundment){
		return (Long)super.insert("PAY_PAYMENT_REFUNDMENT.insert", payRefundment);
	}
	
	/**
	 * 更新退款记录 .
	 * @param payment
	 * @return
	 */
	public void updatePayPaymentRefundment(PayPaymentRefundment payRefundment){
		super.update("PAY_PAYMENT_REFUNDMENT.update", payRefundment);
	}
	/**
	 * 更新退款网关.
	 * @param payment
	 * @return
	 */
	public void updatePayPaymentRefundmentRefundGateway(PayPaymentRefundment payRefundment){
		super.update("PAY_PAYMENT_REFUNDMENT.updateRefundGateway", payRefundment);
	}
	

	/**
	 * 根据支付的PaymentId查询出来 需要退款的PayPaymentRefundment
	 * @return
	 */
	public List<PayPaymentRefundment> selectPayPaymentRefundmentListByPaymentId(Long paymentId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paymentId", paymentId);
		List<PayPaymentRefundment> paymentRefundmentList = super.queryForList(
				"PAY_PAYMENT_REFUNDMENT.selectByParams", map);
		return paymentRefundmentList;
	}
	
	/**
	 * 根据订单ID和退款状态查询退款明细记录.
	 * @return
	 */
	public List<PayPaymentRefundment> selectPayRefundmentListByObjectIdAndStatus(Long objectId,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectId", objectId);
		map.put("status", status);
		List<PayPaymentRefundment> paymentRefundmentList =  super.queryForList(
				"PAY_PAYMENT_REFUNDMENT.selectByParams", map);
		return paymentRefundmentList;
	}
	
	/**
	 *  根据订单ID查询处有效和需要自动退款的退款明细记录.
	 * @param objectId
	 * @return
	 */
	public List<PayPaymentRefundment> selectValidPayRefundmentListByObjectIdAndObjectType(Long objectId, String objectType){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectId", objectId);
		map.put("objectType", objectType);
		map.put("status", Constant.PAY_REFUNDMENT_SERIAL_STATUS.CREATE.name());
		List<PayPaymentRefundment> paymentRefundmentList =super.queryForList(
				"PAY_PAYMENT_REFUNDMENT.selectValidPayRefundmentListByObjectIdAndObjectType", map);
		return paymentRefundmentList;
	}
	/**
	 *  根据订单ID查询和业务类型查询已经成功退款的记录.
	 * @param objectId
	 * @return
	 */
	public List<PayPaymentRefundment> selectRefundListByOrderIdAndBizType(Long orderId, String bizType){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("bizType", bizType);
		map.put("status", Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
		List<PayPaymentRefundment> paymentRefundmentList =super.queryForList(
				"PAY_PAYMENT_REFUNDMENT.selectRefundListByOrderIdAndBizType", map);
		return paymentRefundmentList;
	}
	
	/**
	 *  根据订单ID查询处有效和允许退款的退款明细记录.
	 * @param objectId
	 * @return
	 */
	public List<PayPaymentRefundment> selectValidPayRefundmentListByObjectIdAndBizType(Long objectId, String bizType,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectId", objectId);
		map.put("bizType", bizType);
		map.put("status", status);
		List<PayPaymentRefundment> paymentRefundmentList =super.queryForList(
				"PAY_PAYMENT_REFUNDMENT.selectValidPayRefundmentListByObjectIdAndBizType", map);
		return paymentRefundmentList;
	}
	/**
	 * 根据订单objectId,objectType,bizType查询退款明细记录.
	 * @param objectId
	 * @param objectType
	 * @param bizType
	 * @return
	 */
	public List<PayPaymentRefundment> selectPayRefundmentListByObjectIdAndObjectTypeAndBizType(Long objectId,String objectType, String bizType){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectId", objectId);
		map.put("bizType", bizType);
		map.put("objectType", objectType);
		List<PayPaymentRefundment> paymentRefundmentList =super.queryForList(
				"PAY_PAYMENT_REFUNDMENT.selectByParams", map);
		return paymentRefundmentList;
	}
	
	/**
	 *  查询出有效和允许退款的退款明细记录.
	 * @return
	 */
	public List<PayPaymentRefundment> selectValidPayRefundmentListBy(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Constant.PAY_REFUNDMENT_SERIAL_STATUS.CREATE.name());
		map.put("isAllowRefund", Constant.PAYMENT_GATEWAY_IS_ALLOW_REFUND.TRUE.name());
		List<PayPaymentRefundment> paymentRefundmentList =super.queryForList(
				"PAY_PAYMENT_REFUNDMENT.selectByParams", map);
		return paymentRefundmentList;
	}

	/**
	 * 根据退款的流水查询出来 需要退款的PayPaymentRefundment
	 * @return
	 */
	public PayPaymentRefundment selectPayPaymentRefundmentListBy(String serial){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serial", serial);
		List<PayPaymentRefundment> paymentRefundmentList = super.queryForList(
				"PAY_PAYMENT_REFUNDMENT.selectByParams", map);
		PayPaymentRefundment payPaymentRefundment = new PayPaymentRefundment();
	    if(paymentRefundmentList!=null&&paymentRefundmentList.size()>0){
	    	payPaymentRefundment=paymentRefundmentList.get(0);
	    }
		return payPaymentRefundment;
		
	}
	/**
	 * 根据退款的流水查询出来 PayPaymentRefundment
	 * @param serial
	 * @param status
	 * @return payPaymentRefundment 如果没有返回new PayPaymentRefundment()
	 */
	public PayPaymentRefundment selectPayPaymentRefundmentListBySerial(String serial,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serial", serial);
		map.put("status", status);
		List<PayPaymentRefundment> paymentRefundmentList = super.queryForList(
				"PAY_PAYMENT_REFUNDMENT.selectByParams", map);
		PayPaymentRefundment payPaymentRefundment = new PayPaymentRefundment();
	    if(paymentRefundmentList!=null&&paymentRefundmentList.size()>0){
	    	payPaymentRefundment=paymentRefundmentList.get(0);
	    }
		return payPaymentRefundment;
		
	}

	public PayPaymentRefundment selectPaymentRefundmentByPK(Long payRefundmentId) {
		return (PayPaymentRefundment)super.queryForObject("PAY_PAYMENT_REFUNDMENT.selectPaymentRefundmentByPK", payRefundmentId);
	}
	
	/**
	 * 查询退款及预授权明细记录.
	 * @return
	 */
	public List<PayRefundDetail> selectPayRefundDetailList(Map param,int skipResults,int maxResults){
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		param.put("skipResults", skipResults);
		param.put("maxResults", maxResults);
		param.put("objectType", Constant.PAYMENT_OBJECT_TYPE.CASH_MONEY_DRAW.name());
		List<PayRefundDetail> payRefundDetailList = super.queryForListForReport(
				"PAY_PAYMENT_REFUNDMENT.selectPayRefundDetailByParam", param);
		return payRefundDetailList;
	}

	/**
	 *  查询退款及预授权明细记录数量.
	 * @return
	 */
	public Long selectPayRefundDetailCount(Map param){
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		param.put("objectType", Constant.PAYMENT_OBJECT_TYPE.CASH_MONEY_DRAW.name());
		Long payRefundDetailCount = (Long) super.queryForObject(
				"PAY_PAYMENT_REFUNDMENT.selectPayRefundDetailByParamCount", param);
		return payRefundDetailCount;
	}

	/**
	 *  根据订单ID查询退款及预授权明细记录数量.
	 * @return
	 */
	public PayRefundDetail selectPayRefundDetailByPaymentRefundmentId(Long paymentRefundmentId) {
		Map param = new HashMap();
		param.put("objectType", Constant.PAYMENT_OBJECT_TYPE.CASH_MONEY_DRAW.name());
		param.put("paymentRefundmentId", paymentRefundmentId);
		PayRefundDetail payRefundDetail = (PayRefundDetail) super.queryForObject(
				"PAY_PAYMENT_REFUNDMENT.selectPayRefundDetailByPaymentRefundmentId", param);
		return payRefundDetail;
	}
	/**
	 * 通过支付Id获取当前这笔支付退款金额
	 * @author ZHANG Nan
	 * @param paymentId
	 * @return
	 */
	public Long getRefunmentAmountByPaymentId(String paymentId){
		return (Long) super.queryForObject("PAY_PAYMENT_REFUNDMENT.getRefunmentAmountByPaymentId",paymentId);
	}
	/**
	 * 查询出有效的支付的明晰退款单纪录.
	 * @param map
	 * @return
	 */
	public List<PayPaymentRefundment> selectUnRefundedPaymentByGateWay(String paymentGateway){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paymentGateway", paymentGateway);
		map.put("today", DateUtil.accurateToDay(new Date()));
		List<PayPaymentRefundment> objectList =  super.queryForList("PAY_PAYMENT_REFUNDMENT.selectUnRefundedPaymentByGateWay", map);
		return objectList;
	}
	
	
	public List<PayPaymentAndRefundment> selectPaymentAndRefundByParams(Map<String, Object> map){
		return super.queryForListForReport("PAY_PAYMENT_REFUNDMENT.selectPaymentAndRefundByParams", map);
	}	
}
