package com.lvmama.comm.pet.service.pay;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPaymentGateway;
import com.lvmama.comm.pet.po.pay.PayPaymentGatewayElement;

public interface PayPaymentGatewayService {
	/**
	 * 保存一个网关
	 * @author ZHANG Nan
	 * @param payPaymentGateway 网关对象
	 * @return 主键
	 */
	public Long savePayPaymentGateway(PayPaymentGateway payPaymentGateway);
	/**
	 * 保存一个网关及 控制该网关在线下支付时可输入项
	 * @author ZHANG Nan
	 * @param payPaymentGateway
	 * @param payPaymentGatewayElement
	 * @return
	 */
	public Long savePayPaymentGatewayAndElement(PayPaymentGateway payPaymentGateway,PayPaymentGatewayElement payPaymentGatewayElement) ;
	/**
	 * 通过paymentGatewayId修改网关其它属性
	 * @author ZHANG Nan
	 * @param payPaymentGateway 网关对象
	 * @return 影响行数
	 */
	public int updatePayPaymentGateway(PayPaymentGateway payPaymentGateway);
	/**
	 * 修改网关及 控制该网关在线下支付时可输入项对象
	 * @author ZHANG Nan
	 * @param payPaymentGateway
	 * @param payPaymentGatewayElement
	 * @return
	 */
	public int updatePayPaymentGatewayAndElement(PayPaymentGateway payPaymentGateway,PayPaymentGatewayElement payPaymentGatewayElement) ;
	/**
	 * 通过paymentGatewayId获取网关对象
	 * @author ZHANG Nan
	 * @param payGatewayId 主键
	 * @return 网关对象
	 */
	public PayPaymentGateway selectPaymentGatewayByPK(Long paymentGatewayId);
	/**
	 * 通过gateway获取网关对象
	 * @author ZHANG Nan
	 * @param gateway 网关code
	 * @return 网关对象
	 */
	public PayPaymentGateway selectPaymentGatewayByGateway(String gateway) ;
	/**
	 * 通过网关参数条件获取集合
	 * @author ZHANG Nan
	 * @param paramMap 网关参数
	 * @return 网关集合
	 */
	public List<PayPaymentGateway> selectPayPaymentGatewayByParamMap(Map<String, String> paramMap);
	/**
	 * 通过网关参数条件获取集合总数
	 * @author ZHANG Nan
	 * @param paramMap
	 * @return
	 */
	public Long selectPayPaymentGatewayByParamMapCount(Map<String, String> paramMap);
}