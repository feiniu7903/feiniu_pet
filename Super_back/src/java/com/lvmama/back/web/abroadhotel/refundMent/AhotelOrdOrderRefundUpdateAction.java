package com.lvmama.back.web.abroadhotel.refundMent;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.po.AhotelOrdRefundment;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.abroad.vo.response.ReservationsOrder;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 订单退款增加对像类.
 */
public class AhotelOrdOrderRefundUpdateAction extends BaseAction {
	private UserUserProxy userUserProxy;
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	/**
	 * 退款对象.
	 */
	private AhotelOrdRefundment ordRefundment = new AhotelOrdRefundment();
	/**
	 * 订单子项编号.
	 */
	private String orderId;
	/**
	 * 我的历史订单详细信息
	 */
	private ReservationsOrder historyOrderDetail;
	/**
	 * 售后服务编号.
	 */
	private String saleServiceId;
	/**
	 * 退款编号.
	 */
	private String refundmentId;
	/**
	 * 订单服务类型 .
	 */
	private String serviceType;
	
	private OrdSaleService ordSalePo=new OrdSaleService();
	@SuppressWarnings("unchecked")
	public void doBefore(){
		historyOrderDetail= abroadhotelOrderService.queryAbroadHotelOrderByOrderId(orderId);
		UserUser  user = userUserProxy.getUserUserByUserNo(historyOrderDetail.getUserId());
		historyOrderDetail.setUserId(user.getUserName());
		Map map=new HashMap();
		map.put("refundmentId",refundmentId);
		ordRefundment=(AhotelOrdRefundment)abroadhotelOrderService.findAhotelOrdRefundByParam(map, 0, 1).get(0);
		if(ordRefundment.getAmount()!=0){
			ordRefundment.setAmount(ordRefundment.getAmount()/100);
		}
	}
	
	public void doAfter() {
	}
	/**
	 * 增加退款认务.
	 * 
	 * @return
	 */
	public void updateRefundMent() {
			if (orderId != null) {
					boolean isRefund = abroadhotelOrderService.updateRefund(ordRefundment.getRefundmentId(),ordRefundment.getAmount()*100, ordRefundment.getRefundType(), Constant.REFUNDMENT_STATUS.UNVERIFIED.toString(), ordRefundment.getMemo(), this.getSessionUserName());
					if(isRefund){
						alert("修改成功");
					}else{
						alert("修改失败");
					}
			}
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getRefundmentId() {
		return refundmentId;
	}

	public void setRefundmentId(String refundmentId) {
		this.refundmentId = refundmentId;
	}

	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
	}

	public AhotelOrdRefundment getOrdRefundment() {
		return ordRefundment;
	}

	public void setOrdRefundment(AhotelOrdRefundment ordRefundment) {
		this.ordRefundment = ordRefundment;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public ReservationsOrder getHistoryOrderDetail() {
		return historyOrderDetail;
	}

	public void setHistoryOrderDetail(ReservationsOrder historyOrderDetail) {
		this.historyOrderDetail = historyOrderDetail;
	}

	public String getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}
	public OrdSaleService getOrdSalePo() {
		return ordSalePo;
	}
	public void setOrdSalePo(OrdSaleService ordSalePo) {
		this.ordSalePo = ordSalePo;
	}

}
