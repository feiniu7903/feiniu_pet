package com.lvmama.pet.pay.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayPaymentDetail;


public class PayPaymentDetailDAO extends BaseIbatisDAO {
	
	private static String CONFIG_NAME="PAY_PAYMENT_DETAIL";

	public Long savePayPaymentDetail(PayPaymentDetail payPaymentDetail){
		return (Long)super.insert(CONFIG_NAME+".insert", payPaymentDetail);
	}
	public int updatePayPaymentDetail(PayPaymentDetail payPaymentDetail){
		return super.update(CONFIG_NAME+".update", payPaymentDetail);
	}
	public PayPaymentDetail selectPaymentDetailByPK(Long paymentDetailId) {
		return (PayPaymentDetail)super.queryForObject(CONFIG_NAME+".selectPaymentDetailByPK", paymentDetailId);
	}
	public PayPaymentDetail selectPaymentDetailByPaymentId(String paymentId) {
		return (PayPaymentDetail)super.queryForObject(CONFIG_NAME+".selectPaymentDetailByPaymentId", paymentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<PayPaymentDetail> selectPayPaymentDetailByParamMap(Map<String, String> paramMap){
		return super.queryForList(CONFIG_NAME+".selectPayPaymentDetailByParamMap", paramMap);
	}	
	public Long selectPayPaymentDetailByParamMapCount(Map<String, String> paramMap){
		return (Long) super.queryForObject(CONFIG_NAME+".selectPayPaymentDetailByParamMapCount", paramMap);
	}
}
