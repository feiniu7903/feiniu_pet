package com.lvmama.back.sweb.ord;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.ResponseMessage;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;

@Results( {
		@Result(name = "ord_Inventory", location = "/WEB-INF/pages/back/ord/inventory/ord_inventory.jsp") })
/**
 * 用于后台库存操作监控.
 * 
 * @author huangl
 */
public class OrdInventoryAction extends BaseAction {
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 我的审核任务和历史订单详细信息
	 */
	private OrdOrder orderDetail;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
	/**
	 * 发票列表
	 */
	private List<OrdInvoice> invoiceList;
	/**
	 * 操作日志
	 * */
	private List<ComLog> comLogs;
	/**
	 * 操作日志服务.
	 */
	private ComLogService comLogService;
	/**
	 * 查看，显示该该订单的详细内容，利用异步返回数据
	 */
	@Action("/ordInventory/tansitShowOrder")
	public String tansitShowOrder() {
		if (orderId != null) {
			this.orderDetail = orderServiceProxy
					.queryOrdOrderByOrderId(new Long(orderId));
			// 如果需要发票
			if (orderDetail.getNeedInvoice() != null) {
				if (orderDetail.getNeedInvoice().equals("true")) {
					this.invoiceList = orderServiceProxy
							.queryInvoiceByOrderId(orderId);
				}
			}
			this.comLogs = comLogService.queryByParentId(Constant.COM_LOG_OBJECT_TYPE.ORD_ORDER.name(), orderId);
		}
		return "ord_Inventory";
	}
	
	/**
	 * 用户点击页面恢复按钮，将订单支付状态变更为正常的，并扣除相应的库存数等.
	 */
	@Action("/ordInventory/restoreOrder")
	public void restoreOrder() {
		if (orderId != null) {
			try {
				ResponseMessage responseMessage=orderServiceProxy.restoreOrder(orderId, this.getOperatorName());
				this.sendAjaxMsg("{status:'"+responseMessage.getResponse()+"'}");
			} catch (RuntimeException e) {
				e.printStackTrace();
				this.sendAjaxMsg("{status:false}");
			}
		}	
	}
	 
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public OrdOrder getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrdOrder orderDetail) {
		this.orderDetail = orderDetail;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public List<OrdInvoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<OrdInvoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public List<ComLog> getComLogs() {
		return comLogs;
	}

	public void setComLogs(List<ComLog> comLogs) {
		this.comLogs = comLogs;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
}
