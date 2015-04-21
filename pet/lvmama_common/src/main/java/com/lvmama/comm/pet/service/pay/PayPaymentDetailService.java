package com.lvmama.comm.pet.service.pay;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPaymentDetail;

public interface PayPaymentDetailService {
	/**
	 * 保存支付扩展信息
	 * @author ZHANG Nan
	 * @param payPaymentDetail 支付扩展信息对象
	 * @return 主键
	 */
	public Long savePayPaymentDetail(PayPaymentDetail payPaymentDetail);
	/**
	 * 修改支付扩展信息
	 * @author ZHANG Nan
	 * @param payPaymentDetail 支付扩展信息对象
	 * @return 影响行数
	 */ 
	public int updatePayPaymentDetail(PayPaymentDetail payPaymentDetail);
	/**
	 * 根据主键查询支付扩展信息
	 * @author ZHANG Nan
	 * @param paymentDetailId 主键
	 * @return 支付扩展信息对象
	 */
	public PayPaymentDetail selectPaymentDetailByPK(Long paymentDetailId);
	/**
	 * 根据支付ID查询支付扩展信息
	 * @author ZHANG Nan
	 * @param paymentId 支付ID
	 * @return 支付扩展信息对象
	 */
	public PayPaymentDetail selectPaymentDetailByPaymentId(String paymentId) ;
	/**
	 * 根据参数查询支付扩展信息
	 * @author ZHANG Nan
	 * @param paramMap 查询参数
	 * @return 支付扩展信息对象集合
	 */
	public List<PayPaymentDetail> selectPayPaymentDetailByParamMap(Map<String, String> paramMap);
	/**
	 * 根据参数查询支付扩展信息-总数
	 * @author ZHANG Nan
	 * @param paramMap 查询参数
	 * @return 支付扩展信息对象集合-总数
	 */
	public Long selectPayPaymentDetailByParamMapCount(Map<String, String> paramMap);
	/**
	 * 资金转移同步修改支付扩展信息表
	 * @author ZHANG Nan
	 * @param oldPaymentId
	 * @param newPaymentId
	 */
	public void transferPaymentDetail(Long oldPaymentId,Long newPaymentId);
}