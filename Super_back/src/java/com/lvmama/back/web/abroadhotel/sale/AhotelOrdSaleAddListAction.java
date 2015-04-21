package com.lvmama.back.web.abroadhotel.sale;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.po.AhotelOrdRefundment;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.vo.Constant;

/**
 * 订单跳转售后服务类.
 */
@SuppressWarnings("unused")
public class AhotelOrdSaleAddListAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单编号.
	 */
	private String orderId;
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	/**
	 * 退款查询集合.
	 */
	private List<AhotelOrdRefundment> ordRefundmentList;
	/**
	 * 售后服务编号.
	 */
	private String saleServiceId;
	/**
	 * 初始化查询参数.
	 */
	@SuppressWarnings("unchecked")
	public void doBefore() {
		Map map = new HashMap();
		map.put("orderId", orderId);
		ordRefundmentList = abroadhotelOrderService.findAhotelOrdRefundByParam(map, 0, 10);
	}
	
	public void updateOrdRefundSuccess(Long refundmentId){
		Map map = new HashMap();
		map.put("refundmentId", refundmentId);
		List<AhotelOrdRefundment> ordRefundmentList = abroadhotelOrderService.findAhotelOrdRefundByParam(map, 0, 1);
		if(ordRefundmentList.size()>0){
			AhotelOrdRefundment ordrefundment = ordRefundmentList.get(0);
			if(Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.toString().equals(ordrefundment.getStatus())){
				alert("该退款单已经审核通过了!");
				return;
			}else if(Constant.REFUNDMENT_STATUS.UNVERIFIED.toString().equals(ordrefundment.getStatus())){
				ordrefundment.setApproveTime(new Date());
				ordrefundment.setStatus(Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.toString());
				abroadhotelOrderService.updateAhotelOrdRefundment(ordrefundment);
				alert("该退款单已经成功审核通过!");
				return;
			}else{
				alert("不能被修改的状态!");
				return;
			}
		}
	}
	
	public void updateOrdRefundCancel(Long refundmentId){
		Map map = new HashMap();
		map.put("refundmentId", refundmentId);
		List<AhotelOrdRefundment> ordRefundmentList = abroadhotelOrderService.findAhotelOrdRefundByParam(map, 0, 1);
		if(ordRefundmentList.size()>0){
			AhotelOrdRefundment ordrefundment = ordRefundmentList.get(0);
			ordrefundment.setApproveTime(new Date());
			ordrefundment.setStatus(Constant.REFUNDMENT_STATUS.CANCEL.toString());
			abroadhotelOrderService.updateAhotelOrdRefundment(ordrefundment);
			alert("该退款单已经成功取消!");
			return;
		}
	}


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public List<AhotelOrdRefundment> getOrdRefundmentList() {
		return ordRefundmentList;
	}

	public void setOrdRefundmentList(List<AhotelOrdRefundment> ordRefundmentList) {
		this.ordRefundmentList = ordRefundmentList;
	}

	public String getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}

	public AbroadhotelOrderService getAbroadhotelOrderService() {
		return abroadhotelOrderService;
	}

	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
	}
	
}
