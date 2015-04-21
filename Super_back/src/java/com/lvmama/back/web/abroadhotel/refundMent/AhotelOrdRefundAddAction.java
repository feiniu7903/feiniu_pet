package com.lvmama.back.web.abroadhotel.refundMent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Listbox;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.po.AhotelOrdRefundment;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.abroad.service.IOrderPayment;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 订单退款增加对像类.
 */
public class AhotelOrdRefundAddAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private UserUserProxy userUserProxy;
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	private IOrderPayment abroadhotelOrderPaymentService;
	/**
	 * 退款对象.
	 */
	private AhotelOrdRefundment ordRefundment = new AhotelOrdRefundment();
	/**
	 * 退款查询集合.
	 */
	private List<AhotelOrdRefundment> ordRefundmentList;
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
	/**
	 * 订单服务类型.
	 */
	private String serviceType;
	
	private String cancelResson;
	Listbox refundTypeListbox;
	List<CodeItem> refundTypes = new ArrayList<CodeItem>();
	/**
	 * 查询页面基本显示信息.
	 * 
	 * @return
	 */
	public void doBefore() {
		initRefundTypes();
		if (orderId != null) {
			this.historyOrderDetail = abroadhotelOrderService.queryOrderByHotelOrderId(orderId.toString());
			UserUser  user = userUserProxy.getUserUserByUserNo(historyOrderDetail.getUserId());
			historyOrderDetail.setUserId(user.getUserName());
		}
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
		codeItem = new CodeItem();
		codeItem.setCode("COMPENSATION");
		codeItem.setName("补偿");
		refundTypes.add(codeItem);
	}
	/**
	 * 退款综合查询.
	 * 
	 * @return
	 */
	public void ordRefundMentQuery() {
		ordRefundmentList = abroadhotelOrderService.findAhotelOrdRefundByParam(searchRefundMent, 0, 1);
	}

	/**
	 * 增加退款认务.
	 * 
	 * @return
	 */
	public void addRefundMent() {
			if (orderId != null) {
				if (ordRefundment.getMemo()==null) {
					alert("退款备注不能为空!");
					return;
				}
				String refundType = (String)refundTypeListbox.getSelectedItem().getValue();
				ordRefundment.setRefundType(refundType);
				
				if(Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.name().equalsIgnoreCase(refundType)){
					Map<String,Object> p = new HashMap<String,Object>();
					p.put("orderId", orderId);
					p.put("refundType", Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.name());
					Long count = abroadhotelOrderService.findAhotelOrdRefundByParamCount(p).longValue();
					
					if(count == 0L ){
						ZkMessage.showQuestion("您需要将此订单作废吗?", new ZkMsgCallBack() {
							public void execute() {
								boolean isCanceled = false;
								if(StringUtils.isEmpty(cancelResson)){
									cancelResson="旅客申请取消";
								}
								String operatroId = String.valueOf(getSessionUser().getUserId());
								isCanceled = abroadhotelOrderPaymentService.refundCallback(orderId, operatroId, cancelResson);
								if(isCanceled){
									String operatroName = getSessionUser().getUserName();
									boolean isRefund=abroadhotelOrderService.applyRefund(Long.valueOf(orderId),Long.valueOf(saleServiceId),ordRefundment.getAmount()*100, 
											ordRefundment.getRefundType(),Constant.REFUNDMENT_STATUS.UNVERIFIED.toString(), ordRefundment.getMemo(), operatroName);
									if(!isRefund){
										alert("提交退款单失败！ 可能是未付款订单，取消订单，或者已经有退款单处理中！");
										return;
									}
								} else {
									alert("废单失败");
									return;
								}
							}
						}, new ZkMsgCallBack() {
							public void execute() {
								
							}
						});
					} else {
						alert("提交退款单失败！ 可能是未付款订单，取消订单，或者已经有退款单处理中！");
						return;
					}
				} else {
					String operatroName = getSessionUser().getUserName();
					boolean isRefund=abroadhotelOrderService.applyRefund(Long.valueOf(orderId),Long.valueOf(saleServiceId),ordRefundment.getAmount()*100, 
							ordRefundment.getRefundType(),Constant.REFUNDMENT_STATUS.UNVERIFIED.toString(), ordRefundment.getMemo(), operatroName);
					if(!isRefund){
						alert("提交退款单失败！ 可能是未付款订单，取消订单，或者已经有退款单处理中！");
						return;
					}
				}
			}
	}

	public void doAfter() {
	}

	public AhotelOrdRefundment getOrdRefundment() {
		return ordRefundment;
	}

	public void setOrdRefundment(AhotelOrdRefundment ordRefundment) {
		this.ordRefundment = ordRefundment;
	}

	public List<AhotelOrdRefundment> getOrdRefundmentList() {
		return ordRefundmentList;
	}

	public void setOrdRefundmentList(List<AhotelOrdRefundment> ordRefundmentList) {
		this.ordRefundmentList = ordRefundmentList;
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

	public Map<String, Object> getSearchRefundMent() {
		return searchRefundMent;
	}

	public void setSearchRefundMent(Map<String, Object> searchRefundMent) {
		this.searchRefundMent = searchRefundMent;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @param cancelResson the cancelResson to set
	 */
	public void setCancelResson(String cancelResson) {
		this.cancelResson = cancelResson;
	}

	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
	}

	public void setAbroadhotelOrderPaymentService(
			IOrderPayment abroadhotelOrderPaymentService) {
		this.abroadhotelOrderPaymentService = abroadhotelOrderPaymentService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
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
