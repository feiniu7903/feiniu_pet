package com.lvmama.pet.pay.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayPrePayment;

/**
 * 预授权支付记录处理DAO.
 * @author liwenzhan
 *
 */
public class PayPrePaymentDAO extends BaseIbatisDAO {

	/**
	 * 保存预授权支付记录 .
	 * @param payment
	 * @return
	 */
	public Long savePrePayment(PayPrePayment prePayment){
		return (Long)super.insert("PAY_PRE_PAYMENT.insert", prePayment);
	}
	
	/**
	 * 保存预授权支付记录 .
	 * @param payment
	 * @return
	 */
	public void updatePrePayment(PayPrePayment prePayment){
		super.update("PAY_PRE_PAYMENT.update", prePayment);
	}

	/**
	 * 通过支付流ID查询一个支付流水记录.
	 * @param serial
	 * @return
	 */
	public PayPrePayment selectPrePaymentByPaymentId(Long paymentId){
		return (PayPrePayment) super.queryForObject(
				"PAY_PRE_PAYMENT.selectByPaymentId", paymentId);
	}
	
	/**
	 *  根据条件查询相关记录.
	 * @param objectId
	 * @return
	 */
	public List<PayPrePayment> selectPrePaymentListByMap(final  Map<String, Object> map){
		return (List) super.queryForList(
				"PAY_PRE_PAYMENT.selectPrePaymentListBy", map);
		
	}
	
}
