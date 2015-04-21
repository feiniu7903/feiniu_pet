package com.lvmama.pet.sweb.shop.shopOrder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.pet.service.shop.ShopOrderService;
import com.lvmama.comm.pet.vo.ShopOrderVO;
import com.lvmama.comm.vo.Constant;

@Results({ 
	@Result(name = "showDetail", location = "/WEB-INF/pages/back/shop/shopOrder/shopOrderDetail.jsp"),
	@Result(name = "editRemark", location = "/WEB-INF/pages/back/shop/shopOrder/editRemark.jsp")
	})
public class EditShopOrderAction extends BackBaseAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5691370513503626465L;
	/**
	 * 订单服务
	 */
	private ShopOrderService shopOrderService;
	/**
	 * 订单明细
	 */
	private ShopOrder shopOrder;
	/**
	 * 订单ID
	 */
	private Long orderId;

	private String operation;
	
	private String remark;
	
	@Action(value="/shop/shopOrder/shopOrderDetail")
	public String shopOrderDetail() {
		if (shopOrder == null) {
			shopOrder = (ShopOrderVO) shopOrderService.queryShopOrderByKey(orderId);
			remark = shopOrder.getRemark();
		}
		return operation;
	}
 
	/**
	 * 更新
	 * @throws UnsupportedEncodingException 
	 */
	@Action(value="/shop/shopOrder/updateShopOrder")
	public void update() throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null == orderId) {
			param.put("success", false);
			param.put("errorMessage", "操作失败");			
		} else {
			
			if(orderId != null){
				shopOrder = (ShopOrderVO) shopOrderService.queryShopOrderByKey(orderId);
				shopOrder.setRemark(new String(remark.getBytes("ISO8859-1"),"UTF-8"));
				shopOrderService.updata(shopOrder);
			}
			param.put("success", true);
			param.put("errorMessage", "操作失败");	
		}
			
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}
	}


	public void setShopOrderService(final ShopOrderService shopOrderService) {
		this.shopOrderService = shopOrderService;
	}

	public ShopOrder getShopOrder() {
		return shopOrder;
	}

	public void setShopOrder(final ShopOrder shopOrder) {
		this.shopOrder = shopOrder;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}

