package com.lvmama.back.web.ord.refundMent;

import java.util.Calendar;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.VstDistributorService;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;

/**
 * 订单退款增加对像类.
 * 
 * @author huangl
 */
public class OrdRefundSaleAddAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单子项编号.
	 */
	private String orderId;
	
	/**
	 * 业务系统编码
	 */
	private String sysCode;
	
	/**
	 * 我的历史订单详细信息
	 */
	private OrdOrder historyOrderDetail;
	/**
	 * 售后服务编号.
	 */
	private String saleServiceId;
	/**
	 * 订单服务类型.
	 */
	private String serviceType;
	/**
	 * 只读类型
	 */
	private String editabled = "true";

	/**
	 * 查询页面基本显示信息.
	 * 
	 * @return
	 */
	public void doBefore() {
		if (orderId != null) {
			if (Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
				VstOrdOrderService vstOrdOrderService = (VstOrdOrderService) SpringBeanProxy.getBean("vstOrdOrderService");
				VstDistributorService vstDistributorService = (VstDistributorService) SpringBeanProxy.getBean("vstDistributorService");
				VstOrdOrderVo order = vstOrdOrderService.getVstOrdOrderVo(new Long(orderId));
//				System.out.println("order.getDistributorId()="+order.getDistributorId());
//				if(order.getDistributorId()!=null) {
//					order.setDistributorName(vstDistributorService.getDistributorName(order.getDistributorId()));	
//					System.out.println("order.getDistributorName()="+order.getDistributorName());
//				}
				initializeHistoryDetail(order);
			} else {
				this.historyOrderDetail = this.getOrderServiceProxy()
						.queryOrdOrderByOrderId(new Long(orderId));
			}
		}
	}

	private void initializeHistoryDetail(VstOrdOrderVo order) {
		this.historyOrderDetail = new OrdOrder();
		this.historyOrderDetail.setOrderId(order.getOrderId());
		this.historyOrderDetail.setCreateTime(order.getCreateTime());
		UserUserProxy userUserProxy=(UserUserProxy)SpringBeanProxy.getBean("userUserProxy");
		UserUser userUser=userUserProxy.getUserUserByUserNo(order.getUserId());
  		if(null!=userUser){
  			this.historyOrderDetail.setRealName(userUser.getRealName());
  			this.historyOrderDetail.setGender(userUser.getGender());
  			this.historyOrderDetail.setUserName(userUser.getUserName());
  			this.historyOrderDetail.setMobileNumber(userUser.getMobileNumber());
  		}
		this.historyOrderDetail.setOughtPay(order.getOughtAmount());
		this.historyOrderDetail.setActualPay(order.getActualAmount());
		this.historyOrderDetail.setPaymentStatus(order.getPaymentStatus());
		this.historyOrderDetail.setOrderStatus(order.getOrderStatus());
		this.historyOrderDetail.setUserMemo(order.getRemark());
		this.historyOrderDetail.setZhPaymentChannel(order.getDistributorCode());
		//this.historyOrderDetail.setChannel(order.getDistributorName());
		//新系统没有approveStatus字段，resourceStatus与其对应 TODO
		if("AMPLE".equalsIgnoreCase(order.getResourceStatus())) {	//资源满足
			this.historyOrderDetail.setApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED.name());
		}else if("LOCK".equalsIgnoreCase(order.getResourceStatus())) {	//资源不满足
			this.historyOrderDetail.setApproveStatus(Constant.ORDER_APPROVE_STATUS.RESOURCEFAIL.name());
		}else if("UNVERIFIED".equalsIgnoreCase(order.getResourceStatus())) {	//未审核
			this.historyOrderDetail.setApproveStatus(Constant.ORDER_APPROVE_STATUS.UNVERIFIED.name());
		}
		//支付等待时间
		this.historyOrderDetail.setLastCancelTime(order.getLastCancelTime());
		this.historyOrderDetail.setApproveTime(order.getApproveTime());
		if(order.getWaitPaymentTime()!=null ) {
  			Calendar calendar = Calendar.getInstance();
  			calendar.setTime(order.getWaitPaymentTime());	
  			int waitPaymentTimeMinute = calendar.get(Calendar.MINUTE);
  			this.historyOrderDetail.setWaitPayment(waitPaymentTimeMinute+0L);
		}
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderService getOrderServiceProxy() {
		return (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	}

	public OrdOrder getHistoryOrderDetail() {
		return historyOrderDetail;
	}

	public void setHistoryOrderDetail(OrdOrder historyOrderDetail) {
		this.historyOrderDetail = historyOrderDetail;
	}

	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService) SpringBeanProxy
				.getBean("ordRefundMentService");
	}

	public String getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getEditabled() {
		return editabled;
	}

	public void setEditabled(String editabled) {
		this.editabled = editabled;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
}
