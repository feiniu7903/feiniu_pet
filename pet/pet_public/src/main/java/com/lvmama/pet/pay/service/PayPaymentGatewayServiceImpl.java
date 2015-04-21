package com.lvmama.pet.pay.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPaymentGateway;
import com.lvmama.comm.pet.po.pay.PayPaymentGatewayElement;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayService;
import com.lvmama.pet.pay.dao.PayPaymentGatewayDAO;
import com.lvmama.pet.pay.dao.PayPaymentGatewayElementDAO;

public class PayPaymentGatewayServiceImpl implements PayPaymentGatewayService {
	
	
	private PayPaymentGatewayDAO payPaymentGatewayDAO;
	private PayPaymentGatewayElementDAO payPaymentGatewayElementDAO;
	/**
	 * 保存一个网关
	 * @author ZHANG Nan
	 * @param payPaymentGateway 网关对象
	 * @return 主键
	 */
	@Override
	public Long savePayPaymentGateway(PayPaymentGateway payPaymentGateway) {
		return payPaymentGatewayDAO.savePayPaymentGateway(payPaymentGateway);
	}
	/**
	 * 保存一个网关及 控制该网关在线下支付时可输入项
	 * @author ZHANG Nan
	 * @param payPaymentGateway
	 * @param payPaymentGatewayElement
	 * @return
	 */
	public Long savePayPaymentGatewayAndElement(PayPaymentGateway payPaymentGateway,PayPaymentGatewayElement payPaymentGatewayElement) {
		Long payPaymentGatewayId=payPaymentGatewayDAO.savePayPaymentGateway(payPaymentGateway);
		if(payPaymentGatewayId!=null && payPaymentGatewayId>0 && payPaymentGatewayElement!=null){
			payPaymentGatewayElementDAO.savePayPaymentGatewayElement(payPaymentGatewayElement);
		}
		return payPaymentGatewayId;
	}
	/**
	 * 通过paymentGatewayId修改网关其它属性
	 * @author ZHANG Nan
	 * @param payPaymentGateway 网关对象
	 * @return 影响行数
	 */
	@Override
	public int updatePayPaymentGateway(PayPaymentGateway payPaymentGateway) {
		return payPaymentGatewayDAO.updatePayPaymentGateway(payPaymentGateway);
	}
	/**
	 * 修改网关及 控制该网关在线下支付时可输入项对象
	 * @author ZHANG Nan
	 * @param payPaymentGateway
	 * @param payPaymentGatewayElement
	 * @return
	 */
	public int updatePayPaymentGatewayAndElement(PayPaymentGateway payPaymentGateway,PayPaymentGatewayElement payPaymentGatewayElement) {
		int rows=payPaymentGatewayDAO.updatePayPaymentGateway(payPaymentGateway);
		if(payPaymentGatewayElement!=null && payPaymentGatewayElement.getPaymentGatewayElementId()!=null){
			payPaymentGatewayElementDAO.updatePayPaymentGatewayElement(payPaymentGatewayElement);	
		}
		else{
			payPaymentGatewayElementDAO.savePayPaymentGatewayElement(payPaymentGatewayElement);
		}
		return rows; 
	}
	
	/**
	 * 通过paymentGatewayId获取网关对象
	 * @author ZHANG Nan
	 * @param payGatewayId 主键
	 * @return 网关对象
	 */
	@Override
	public PayPaymentGateway selectPaymentGatewayByPK(Long paymentGatewayId) {
		return payPaymentGatewayDAO.selectPaymentGatewayByPK(paymentGatewayId);
	}
	/**
	 * 通过gateway获取网关对象
	 * @author ZHANG Nan
	 * @param gateway 网关code
	 * @return 网关对象
	 */
	public PayPaymentGateway selectPaymentGatewayByGateway(String gateway) {
		return payPaymentGatewayDAO.selectPaymentGatewayByGateway(gateway);
	}
	/**
	 * 通过网关参数条件获取集合
	 * @author ZHANG Nan
	 * @param paramMap 网关参数
	 * @return 网关集合
	 */
	@Override
	public List<PayPaymentGateway> selectPayPaymentGatewayByParamMap(Map<String, String> paramMap) {
		return payPaymentGatewayDAO.selectPayPaymentGatewayByParamMap(paramMap);
	}
	/**
	 * 通过网关参数条件获取集合总数
	 * @author ZHANG Nan
	 * @param paramMap
	 * @return
	 */
	@Override
	public Long selectPayPaymentGatewayByParamMapCount(Map<String, String> paramMap){
		return payPaymentGatewayDAO.selectPayPaymentGatewayByParamMapCount(paramMap);
	}
	
	
	
	public void setPayPaymentGatewayDAO(PayPaymentGatewayDAO payPaymentGatewayDAO) {
		this.payPaymentGatewayDAO = payPaymentGatewayDAO;
	}
	public void setPayPaymentGatewayElementDAO(PayPaymentGatewayElementDAO payPaymentGatewayElementDAO) {
		this.payPaymentGatewayElementDAO = payPaymentGatewayElementDAO;
	}
}
