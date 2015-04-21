package com.lvmama.back.web.abroadhotel.refundMent;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.po.AhotelOrdRefundment;
import com.lvmama.comm.abroad.po.AhotelOrdSaleService;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.abroad.service.IOrderPayment;
import com.lvmama.comm.abroad.vo.response.ReservationsOrder;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 订单退款增加对像类.
 * 
 */
public class AhotelOrdOrderRefundAddAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5833785526489626662L;
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	/**
	 * 窗口.
	 */
	private Window winOrdSaleAdd;
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
	private OrdOrder historyOrderDetail;
	/**
	 * 售后服务编号.
	 */
	private String saleServiceId;
	/**
	 * 综合查询封装.
	 */
	private Map<String, Object> searchRefundMent = new HashMap<String, Object>();
	private IOrderPayment abroadhotelOrderPaymentService;
	
	private String ordSaleContext;
	/**
	 * 是否被废弃订单.
	 */
	private String isCancelOrder;
	/**
	 * 废单原因.
	 */
	private String cancelResson;
	/**
	 * 购建售后处理对像.
	 */
	private boolean enabled=true;
	private OrdSaleService ordSalePo=new OrdSaleService();
	
	Listbox serviceTypeListbox;
	List<CodeItem> serviceTypes = new ArrayList<CodeItem>();
	Listbox refundTypeListbox;
	List<CodeItem> refundTypes = new ArrayList<CodeItem>();
	public void doBefore(){
		initServiceTypes();
		initRefundTypes();
		historyOrderDetail = abroadhotelOrderService.queryOrderByHotelOrderId(orderId.toString());
		if (historyOrderDetail.getOrderStatus().equals(Constant.ORDER_STATUS.CANCEL.name())){
			enabled = false;
		}
	}
	/**
	 * 初始化售后服务类型下拉框.
	 */
	private void initServiceTypes(){
		CodeItem codeItem = new CodeItem();
		codeItem.setCode("");
		codeItem.setName("--请选择--");
		serviceTypes.add(codeItem);
		codeItem = new CodeItem();
		codeItem.setCode("NORMAL");
		codeItem.setName("常规售后");
		serviceTypes.add(codeItem);
		codeItem = new CodeItem();
		codeItem.setCode("COMPLAINT");
		codeItem.setName("投诉");
		serviceTypes.add(codeItem);
	}
	/**
	 * 初始化退款类型.
	 */
	private void initRefundTypes(){
		CodeItem codeItem = new CodeItem();
		codeItem.setCode("");
		codeItem.setName("--请选择--");
		refundTypes.add(codeItem);
		codeItem = new CodeItem();
		codeItem.setCode("ORDER_REFUNDED");
		codeItem.setName("订单退款");
		refundTypes.add(codeItem);
	}
	
	public void doAfter() {
	}
	/**
	 * 增加退款认务.
	 * 
	 * @return
	 */
	public void addRefundMent() {
		String operatroId = String.valueOf(getSessionUser().getUserId());
		if (orderId != null) {
			//创建售后服务
			String serviceType = (String)serviceTypeListbox.getSelectedItem().getValue();
			AhotelOrdSaleService ordSevice = new AhotelOrdSaleService();
			ordSevice.setCreateTime(new Date());
			ordSevice.setOperatorName(this.getSessionUserName());
			ordSevice.setOrderId(Long.valueOf(this.orderId));
			ordSevice.setApplyContent(ordSalePo.getApplyContent());
			ordSevice.setServiceType(serviceType);
			ordSevice.setStatus("NORMAL");
			Long saleId = abroadhotelOrderService.insertAhotelOrdSaleService(ordSevice);
			if(isCancelOrder != null && isCancelOrder.equals("true")){
				ordRefundment.setRefundType(Constant.REFUND_TYPE.ORDER_REFUNDED.toString());
			} else {
				String refundType = (String)refundTypeListbox.getSelectedItem().getValue();
				ordRefundment.setRefundType(refundType);
			}
			
			if (isCancelOrder != null && isCancelOrder.equals("true")) {
				// 废单
				boolean flag = abroadhotelOrderPaymentService.refundCallback(orderId, operatroId, this.cancelResson);
				if (flag) {
					// 记录废单日志
					ReservationsOrder order = abroadhotelOrderService.queryAbroadHotelOrderByOrderId(orderId);
					if (order.getUserId().equals(operatroId)) {
						abroadhotelOrderService.saveComLog(
								"ABROADHOTEL_ORD_ORDER", Long.valueOf(orderId),
								getOperatorName(),
								Constant.COM_LOG_ORDER_EVENT.userCancel.name(),
								"用户取消", this.cancelResson);
					} else {
						abroadhotelOrderService.saveComLog(
								"ABROADHOTEL_ORD_ORDER", Long.valueOf(orderId),
								getOperatorName(),
								Constant.COM_LOG_ORDER_EVENT.cancel.name(),
								"后台取消", this.cancelResson);
					}
					
					//创建退款单： 以下的3种情况退款单会提交失败(订单取消.实付金额0，已经生成退款单)
					boolean isRefund = abroadhotelOrderService.applyRefund(
							Long.valueOf(orderId), saleId,
							ordRefundment.getAmount() * 100,
							ordRefundment.getRefundType(),
							Constant.REFUNDMENT_STATUS.UNVERIFIED.toString(),
							ordRefundment.getMemo(), this.getSessionUserName());
					if (!isRefund) {
						abroadhotelOrderService.deleteAhotelOrdSaleService(saleId + "");
						alert("提交海外酒店退款单失败！ 可能是未付款订单，取消订单，或者已经有退款单处理中！");
						return;
					}
					
					alert("废单成功");
					super.closeWindow();
				} else {
					alert("废单失败");
				}
			} else {
				alert("提交成功");
				super.refreshParent("refreshOrdRefundBtn");
				super.refreshParent("refreshOrdSaleBtn");
				super.closeWindow();
			}
		}
	}

	public boolean isenabled() {
		return enabled;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrdOrder getHistoryOrderDetail() {
		return historyOrderDetail;
	}

	public void setHistoryOrderDetail(OrdOrder historyOrderDetail) {
		this.historyOrderDetail = historyOrderDetail;
	}

	public String getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}

	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService) SpringBeanProxy
				.getBean("ordRefundMentService");
	}


	public Map<String, Object> getSearchRefundMent() {
		return searchRefundMent;
	}

	public void setSearchRefundMent(Map<String, Object> searchRefundMent) {
		this.searchRefundMent = searchRefundMent;
	}

	public String getOrdSaleContext() {
		return ordSaleContext;
	}
	public void setOrdSaleContext(String ordSaleContext) {
		this.ordSaleContext = ordSaleContext;
	}
	
	public Window getWinOrdSaleAdd() {
		return winOrdSaleAdd;
	}
	public void setWinOrdSaleAdd(Window winOrdSaleAdd) {
		this.winOrdSaleAdd = winOrdSaleAdd;
	}
	public OrdSaleService getOrdSalePo() {
		return ordSalePo;
	}
	public void setOrdSalePo(OrdSaleService ordSalePo) {
		this.ordSalePo = ordSalePo;
	}
	public String getIsCancelOrder() {
		return isCancelOrder;
	}
	public void setIsCancelOrder(String isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}
	public String getCancelResson() {
		return cancelResson;
	}
	public void setCancelResson(String cancelResson) {
		try {
			this.cancelResson = new String(cancelResson.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public AbroadhotelOrderService getAbroadhotelOrderService() {
		return abroadhotelOrderService;
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
	public Listbox getServiceTypeListbox() {
		return serviceTypeListbox;
	}

	public void setServiceTypeListbox(Listbox serviceTypeListbox) {
		this.serviceTypeListbox = serviceTypeListbox;
	}

	public List<CodeItem> getServiceTypes() {
		return serviceTypes;
	}

	public void setServiceTypes(List<CodeItem> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}
	public Listbox getRefundTypeListbox() {
		return refundTypeListbox;
	}
	public void setRefundTypeListbox(Listbox refundTypeListbox) {
		this.refundTypeListbox = refundTypeListbox;
	}
	public List<CodeItem> getRefundTypes() {
		return refundTypes;
	}
	public void setRefundTypes(List<CodeItem> refundTypes) {
		this.refundTypes = refundTypes;
	}
	
}
