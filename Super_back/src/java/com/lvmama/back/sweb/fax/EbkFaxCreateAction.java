/**
 * 
 */
package com.lvmama.back.sweb.fax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.service.ebooking.CertificateService;
import com.lvmama.comm.bee.service.ord.OrderService;


/**
 * 
 * @author yangbin
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/fax/create_fax.jsp")
})
public class EbkFaxCreateAction {

	private CertificateService certificateServiceProxy;
	private Long orderId;
	private String messageType;
	private String orderItemMetaId;

	private OrderService orderServiceProxy;
	
	@Action("/ebk/fax/backCreateFax")
	public String go(){
		return "input";
	}
	
	@Action("/ebk/fax/backCreateFaxSubmit")
	public String submit(){
		if(CertificateService.ORDER_MODIFY_SETTLEMENT_PRICE.equals(messageType)) {
			if(orderItemMetaId != null) {
				orderItemMetaId = orderItemMetaId.replaceAll("\\s", "");
				// 查找需要处理的订单正常只有个订单，结算价修改会有多个。
				List<OrdOrder> ordList = fillOrderList(orderItemMetaId);
				//凭证生成 
				for (OrdOrder ord : ordList) {
					certificateServiceProxy.createSupplierCertificate(ord,CertificateService.ORDER_MODIFY_SETTLEMENT_PRICE,null, orderItemMetaId);
				}
			}
		} else {
			certificateServiceProxy.createSupplierCertificate(orderId, messageType, null, null);
		}
		return "input";
	}
	/**
	 * 因订单结算价格修改消息传的是订单子子项ID列表，所以要从中取出对应的订单列表来
	 * <br>如果存在订单特殊备注，不进入
	 * @author: ranlongfei 2013-4-9 下午6:01:13
	 * @param message
	 * @return
	 */
	private List<OrdOrder> fillOrderList(String ids) {
		//结算价格的消息只有附加信息中的子子项ID
		List<OrdOrder> ordList = new ArrayList<OrdOrder>();
		if("".equals(ids)){
			return ordList;
		}
		Set<Long> orderIds = new HashSet<Long>();
		for(String id : ids.split(",")) {
			if(id == null || "".equals(id)) {
				continue;
			}
			OrdOrderItemMeta item = orderServiceProxy.queryOrdOrderItemMetaBy(Long.valueOf(id));
			orderIds.add(item.getOrderId());
		}
		for(Long oId : orderIds) {
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(oId);
			ordList.add(ordOrder);
		}
		return ordList;
	}
	public void setCertificateServiceProxy(
			CertificateService certificateServiceProxy) {
		this.certificateServiceProxy = certificateServiceProxy;
	}

	

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(String orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
	
}
