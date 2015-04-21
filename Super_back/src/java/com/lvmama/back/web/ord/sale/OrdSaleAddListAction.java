package com.lvmama.back.web.ord.sale;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;

/**
 * 订单跳转售后服务类.
 * 
 * @author huangl
 */
@SuppressWarnings("unused")
public class OrdSaleAddListAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单编号.
	 */
	private String orderId;
	/**
	 * 用户售后处理集合.
	 */
	private List<OrdSaleService> ordSaleServiceList;
	/**
	 * 退款查询集合.
	 */
	private List<OrdRefundment> ordRefundmentList;
	/**
	 * 售后服务编号.
	 */
	private String saleServiceId;
	
	private String sysCode;
	
	private ComLogService comLogService;
	
	private PassCodeService passCodeService;
	
	private OrderService orderServiceProxy;
	
	private PermUserService permUserService;
	
	private Long refundmentId;
	
	private String message;
	
	private ProdProductService prodProductService;

	/**
	 * 服务类型.
	 */
	private String serviceType;
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * 初始化查询参数.
	 */
	@SuppressWarnings("unchecked")
	public void doBefore() {
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("sysCode", sysCode);
		ordRefundmentList = this.getOrdRefundMentService()
				.findOrdRefundByParam(map, 0, 10);
	}

	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService) SpringBeanProxy
				.getBean("ordRefundMentService");
	}
	
	// add by shihui
	public void checkPassCodeStatus(Long refundmentId, String sysCode) {
		if(refundmentId == null || StringUtils.isEmpty(sysCode)) {
			alert("数据为空！");
			return;
		}
		Map map = new HashMap();
		map.put("refundmentId", refundmentId);
		map.put("sysCode", sysCode);
		List<OrdRefundment> ordRefundmentList = this.getOrdRefundMentService().findOrdRefundByParam(map, 0, 1);
		if(ordRefundmentList.size()>0){
			OrdRefundment ordrefundment=(OrdRefundment)ordRefundmentList.get(0);
			//判断该订单是否为废码失败订单
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", PassportConstant.PASSCODE_DESTROY_STATUS.UNDESTROYED.name());
			params.put("orderId", ordrefundment.getOrderId());
			List<PassCode> pcList = passCodeService.selectPassCodeListByOrderIdAndStatus(params);
			//有废码失败
			if(!pcList.isEmpty()) {
				OrdOrder orderDetail = orderServiceProxy.queryOrdOrderByOrderId(ordrefundment.getOrderId());
				if(orderDetail != null) {
					ProdProduct product = prodProductService.getProdProduct(orderDetail.getMainProduct().getProductId());
					if(product != null) {
						PermUser managerUser = permUserService.getPermUserByUserId(product.getManagerId());
						String message = "当前订单为系统对接订单，且废码失败，请先联系业务产品经理 ";
						if(managerUser != null) {
							message += "【" +managerUser.getRealName() + "】";							
						}
						message += " ，最终核对是否可以退款。";
						map.put("message", message);
						try {
							showWindow("/ord/refundMent/checkPassCodeConfirm.zul", map, "400px", "100px");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						alert("产品不存在！");
						return;
					}
				} else {
					alert("订单不存在！");
					return;
				}
			} else {
				updateOrdRefundSuccess(refundmentId, sysCode);
			}
		}
	}
	
	public void updateOrdRefundSuccess(Long refundmentId, String sysCode){
		if(refundmentId == null || StringUtils.isEmpty(sysCode)) {
			alert("数据为空！");
			return;
		}
		Map map = new HashMap();
		map.put("refundmentId", refundmentId);
		map.put("sysCode", sysCode);
		List ordRefundmentList = this.getOrdRefundMentService().findOrdRefundByParam(map, 0, 1);
		if(ordRefundmentList.size()>0){
			OrdRefundment ordrefundment=(OrdRefundment)ordRefundmentList.get(0);
			if(Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.name().equals(ordrefundment.getStatus())){
				alert("该退款单已经审核通过了!");
				return;
			}else if(Constant.REFUNDMENT_STATUS.UNVERIFIED.name().equals(ordrefundment.getStatus())){
				ordrefundment.setApproveTime(new Date());
				ordrefundment.setStatus(Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.name());
				this.getOrdRefundMentService().updateOrdRefundmentByPK(ordrefundment);
				comLogService.insert("ORD_REFUNDMENT", ordrefundment.getOrderId(), refundmentId, 
						super.getOperatorName(), Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), 
						"退款单审核通过", "退款单审核通过", "ORD_ORDER");
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
		List ordRefundmentList = this.getOrdRefundMentService().findOrdRefundByParam(map, 0, 1);
		if(ordRefundmentList.size()>0){
			OrdRefundment ordrefundment=(OrdRefundment)ordRefundmentList.get(0);
			ordrefundment.setApproveTime(new Date());
			ordrefundment.setStatus(Constant.REFUNDMENT_STATUS.CANCEL.name());
			this.getOrdRefundMentService().updateOrdRefundmentByPK(ordrefundment);
			comLogService.insert("ORD_REFUNDMENT", ordrefundment.getOrderId(), refundmentId, 
					super.getOperatorName(), Constant.COM_LOG_CASH_EVENT.updateOrderRefundment.name(), 
					"退款单取消", "退款单取消", "ORD_ORDER");
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

	public OrdSaleServiceService getOrdSaleServiceService() {
		return (OrdSaleServiceService) SpringBeanProxy
				.getBean("ordSaleServiceService");
	}

	public List<OrdSaleService> getOrdSaleServiceList() {
		return ordSaleServiceList;
	}

	public void setOrdSaleServiceList(List<OrdSaleService> ordSaleServiceList) {
		this.ordSaleServiceList = ordSaleServiceList;
	}

	public List<OrdRefundment> getOrdRefundmentList() {
		return ordRefundmentList;
	}

	public void setOrdRefundmentList(List<OrdRefundment> ordRefundmentList) {
		this.ordRefundmentList = ordRefundmentList;
	}

	public String getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}
	
	public void queryRefundment(){
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("sysCode", sysCode);
		ordRefundmentList = this.getOrdRefundMentService().findOrdRefundByParam(map, 0, 10);
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public Long getRefundmentId() {
		return refundmentId;
	}

	public void setRefundmentId(Long refundmentId) {
		this.refundmentId = refundmentId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	
}
