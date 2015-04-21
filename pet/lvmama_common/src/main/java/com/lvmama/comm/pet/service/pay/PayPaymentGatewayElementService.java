package com.lvmama.comm.pet.service.pay;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPaymentGatewayElement;

public interface PayPaymentGatewayElementService {
	
	/**
	 * 保存一个支付网关 线下支付时出现的可输入项 记录
	 * @author ZHANG Nan
	 * @param payPaymentGatewayElement
	 * @return
	 */
	public Long savePayPaymentGatewayElement(PayPaymentGatewayElement payPaymentGatewayElement);
	/**
	 * 修改一个支付网关 线下支付时出现的可输入项 记录
	 * @author ZHANG Nan
	 * @param payPaymentGatewayElement
	 * @return
	 */
	public int updatePayPaymentGatewayElement(PayPaymentGatewayElement payPaymentGatewayElement);
	/**
	 * 根据主键获取一个支付网关 线下支付时出现的可输入项 记录
	 * @author ZHANG Nan
	 * @param paymentGatewayElementId
	 * @return
	 */
	public PayPaymentGatewayElement selectPaymentGatewayElementByPK(Long paymentGatewayElementId);
	/**
	 * 根据支付网关获取一个支付网关 线下支付时出现的可输入项 记录
	 * @author ZHANG Nan
	 * @param gateway
	 * @return
	 */
	public PayPaymentGatewayElement selectPaymentGatewayElementByGateway(String gateway) ;
	/**
	 * 根据参数获取一批支付网关 线下支付时出现的可输入项 记录
	 * @author ZHANG Nan
	 * @param paramMap
	 * @return
	 */
	public List<PayPaymentGatewayElement> selectPayPaymentGatewayElementByParamMap(Map<String, String> paramMap);
	/**
	 * 根据参数获取一批支付网关 线下支付时出现的可输入项 记录 总数
	 * @author ZHANG Nan
	 * @param paramMap
	 * @return
	 */
	public Long selectPayPaymentGatewayElementByParamMapCount(Map<String, String> paramMap);
}