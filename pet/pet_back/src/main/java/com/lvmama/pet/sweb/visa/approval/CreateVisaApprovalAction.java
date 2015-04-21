package com.lvmama.pet.sweb.visa.approval;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.service.visa.VisaApprovalService;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name=  "showVisaApproval", location = "/WEB-INF/pages/back/visa/approval/showVisaApproval.jsp"),
	@Result(name=  "showDeleteApproval", location = "/WEB-INF/pages/back/visa/approval/showDeleteApproval.jsp")
})
public class CreateVisaApprovalAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 5233624539817627746L;
	/**
	 * 签证审核资料远程服务
	 */
	private VisaApprovalService visaApprovalService;
	private OrderService orderServiceProxy;
	
	private String country;
	private String visaType;
	private Long orderId;
	private String city;
	
	/**
	 * @deprecated
	 * @return
	 */
	@Action("/visa/approval/showVisaApproval")
	public String showVisaApproval() {
		return "showVisaApproval";	
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	@Action("/visa/approval/showDeleteApproval")
	public String showDeleteApproval() {
		return "showDeleteApproval";	
	}

	/**
	 * @deprecated
	 * @return
	 */
	@Action("/visa/approval/saveDeleteApproval")
	public void saveDeleteApproval() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != orderId) {
			visaApprovalService.deleteApprovalByOrderId(orderId);
			json.put("success", true);
		}
		getResponse().getWriter().print(json.toString());
	}	
	
	
	/**
	 * @deprecated
	 * @throws IOException
	 */
	@Action("/visa/approval/saveVisaApproval")
	public void saveVisaApproval() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != orderId && StringUtils.isNotBlank(country) && StringUtils.isNotBlank(visaType) && StringUtils.isNotBlank(city)) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if (null != order) {
				boolean flag = visaApprovalService.createVisaApproval(order, null, country, visaType, city, 20);
				if (flag) {
					json.put("success", true);
				} else {
					json.put("message", "未知原因导致导入失败");
				}
				
			} else {
				json.put("message", "无法找到订单相关信息");
			}
		} else {
			json.put("message", "数据不完整，无法进行导入");
		}
		getResponse().getWriter().print(json.toString());
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public Map<String, String> getVisaTypeList() {
		Map<String,String> map = Constant.VISA_TYPE.BUSINESS_VISA.getMap();
		map.put("", "----请选择----");
		return map;
	}
	/**
	 * @deprecated
	 * @return
	 */	
	public Map<String, String> getVisaCityList() {
		Map<String,String> map = Constant.VISA_CITY.SH_VISA_CITY.getMap();
		map.put("", "----请选择----");
		return map;
	}
	
	/**
	 * @deprecated
	 * @throws IOException
	 */
	@Action("/visa/approval/getOrderItemName")
	public void getOrderItemName() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		OrdOrder order = this.orderServiceProxy.queryOrdOrderByOrderId(this.orderId);
		if (null != order) {
			OrdOrderItemProd mainProd = order.getMainProduct();
			json.put("productId", mainProd.getProductId());
			json.put("productName", mainProd.getProductName());
			json.put("success", true);
		}
		getResponse().getWriter().print(json.toString());
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setVisaApprovalService(VisaApprovalService visaApprovalService) {
		this.visaApprovalService = visaApprovalService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
}
