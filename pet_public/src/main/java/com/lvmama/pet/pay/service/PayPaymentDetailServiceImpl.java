package com.lvmama.pet.pay.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPaymentDetail;
import com.lvmama.comm.pet.service.pay.PayPaymentDetailService;
import com.lvmama.pet.pay.dao.PayPaymentDetailDAO;

public class PayPaymentDetailServiceImpl implements PayPaymentDetailService {
	
	
	private PayPaymentDetailDAO payPaymentDetailDAO;

	@Override
	public Long savePayPaymentDetail(PayPaymentDetail payPaymentDetail) {
		return payPaymentDetailDAO.savePayPaymentDetail(payPaymentDetail);
	}

	@Override
	public int updatePayPaymentDetail(PayPaymentDetail payPaymentDetail) {
		return payPaymentDetailDAO.updatePayPaymentDetail(payPaymentDetail);
	}

	@Override
	public PayPaymentDetail selectPaymentDetailByPK(Long paymentDetailId) {
		return payPaymentDetailDAO.selectPaymentDetailByPK(paymentDetailId);
	}

	@Override
	public PayPaymentDetail selectPaymentDetailByPaymentId(String paymentId) {
		return payPaymentDetailDAO.selectPaymentDetailByPaymentId(paymentId);
	}

	@Override
	public List<PayPaymentDetail> selectPayPaymentDetailByParamMap(Map<String, String> paramMap) {
		return payPaymentDetailDAO.selectPayPaymentDetailByParamMap(paramMap);
	}

	@Override
	public Long selectPayPaymentDetailByParamMapCount(Map<String, String> paramMap) {
		return payPaymentDetailDAO.selectPayPaymentDetailByParamMapCount(paramMap);
	}

	
	public void setPayPaymentDetailDAO(PayPaymentDetailDAO payPaymentDetailDAO) {
		this.payPaymentDetailDAO = payPaymentDetailDAO;
	}
	/**
	 * 资金转移同步修改支付扩展信息表
	 * @author ZHANG Nan
	 * @param oldPaymentId
	 * @param newPaymentId
	 */
	public void transferPaymentDetail(Long oldPaymentId,Long newPaymentId){
		if(oldPaymentId!=null && newPaymentId!=null){
		PayPaymentDetail payPaymentDetail=payPaymentDetailDAO.selectPaymentDetailByPaymentId(oldPaymentId+"");
			if(payPaymentDetail!=null){
				payPaymentDetail.setPaymentId(newPaymentId);
				payPaymentDetailDAO.savePayPaymentDetail(payPaymentDetail);
			}
		}
	}
}
