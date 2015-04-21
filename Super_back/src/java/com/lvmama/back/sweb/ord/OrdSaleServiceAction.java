package com.lvmama.back.sweb.ord;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.ord.OrdSaleServiceDeal;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceServiceDeal;
import com.lvmama.comm.vo.Constant;

@ParentPackage("json-default")
@Results( { @Result(name = "order_list", location = "/WEB-INF/pages/back/ord/ord_list.jsp") })
/**
 * 订单售后服务类.
 * 
 * @author huangl
 */
public class OrdSaleServiceAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 售后管理服务
	 */
	private OrdSaleServiceService ordSaleServiceService;
	private OrderService orderServiceProxy;
	private OrdSaleServiceServiceDeal ordSaleServiceServiceDeal;
	private String orderId;
	private OrdSaleService ordSaleService;
	private OrdSaleServiceDeal ordSaleServiceDeal;
	private List<OrdSaleService> ordSaleServiceList;
	private List<OrdSaleServiceDeal> ordSaleServiceDealList;
	List<CodeItem> channelList;
	private String tab="1";
	private String jsonMsg;
	private String saleServiceId;
	private String serviceType;
	private String permId;
	/**
	 * 售后服务,增加对象.
	 * 
	 * @return
	 */
	@Action(value="/ordSale/addOrderSaleDeal",results=@Result(type="json",name="addOrderSaleDeal",params={"includeProperties","jsonMsg.*"}))
	public String  addOrderSaleDeal()throws IOException {
		if (this.saleServiceId != null) {
			try {
				ordSaleServiceDeal=new OrdSaleServiceDeal();
				ordSaleServiceDeal.setCreateTime(new Date());
				ordSaleServiceDeal.setOperatorName(this.getOperatorName());
				ordSaleServiceDeal.setDealContent(this.getRequest().getParameter("ord_applyContent"));
				ordSaleServiceDeal.setSaleServiceId(Long.valueOf(saleServiceId));
				ordSaleServiceServiceDeal.addOrdSaleServiceDeal(ordSaleServiceDeal);
				this.jsonMsg="true";
			} catch (Exception e) {
				e.printStackTrace();
				this.jsonMsg="true";
			}
		}
		return "addOrderSaleDeal";
	}
	/**
	 * 中转售后处理结果.
	 * @return
	 */
	@Action(value = "/ordSale/tranitOrdDeal", results = @Result(name = "tranitOrdDeal", location = "/WEB-INF/pages/back/ord/sale/ord_sale_deal_add.jsp"))
	public String tranitOrdDeal() {
		if (saleServiceId != null) {
			Map map=new HashMap();
			map.put("saleServiceId", saleServiceId);
			ordSaleServiceList=this.ordSaleServiceService.getOrdSaleServiceAllByParam(map);
		}
		return "tranitOrdDeal";
	}
	/**
	 * 查询售后.
	 * @return
	 * @throws ParseException 
	 */
	@Action(value = "/ordSale/queryOrderSale", results = @Result(name = "queryOrderSale", location = "/WEB-INF/pages/back/ord/sale/ord_sale_query.jsp"))
	public String queryOrderSale() throws ParseException{
		return "queryOrderSale";
	}
	/**
	 * 展示当前订单下,所有的用户售后服务内容.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/ordSale/showOrderSaleDealList", results = @Result(name = "showOrderSaleList", location = "/WEB-INF/pages/back/ord/sale/ord_sale_deal_list.jsp"))
	public String showOrderSaleDealList(){
		if (this.saleServiceId != null) {
			Map map=new HashMap();
			map.put("saleServiceId", saleServiceId);
			ordSaleServiceDealList=this.ordSaleServiceServiceDeal.getOrdSaleServiceAllByParam(map);
		}
		return "showOrderSaleList";
	}
	
	/**
	 * 展示当前订单下,所有的用户售后服务内容.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/ordSale/showOrderSaleList", results = @Result(name = "showOrderSaleList", location = "/WEB-INF/pages/back/ord/sale/ord_sale_list.jsp"))
	public String showOrderSaleList(){
		if (this.orderId != null) {
			Map map=new HashMap();
			map.put("orderId", orderId);
			ordSaleServiceList=this.ordSaleServiceService.getOrdSaleServiceAllByParam(map);
		}
		return "showOrderSaleList";
	}
	
	
	/**
	 * 售后服务,增加对象.
	 * 
	 * @return
	 */
	@Action(value="/ordSale/addOrderSale",results=@Result(type="json",name="addOrderSale",params={"includeProperties","jsonMsg.*"}))
	public String  addOrderSale()throws IOException {
		if (this.orderId != null) {
			try {
				ordSaleService=new OrdSaleService();
				ordSaleService.setCreateTime(new Date());
				ordSaleService.setSysCode(Constant.COMPLAINT_SYS_CODE.SUPER.getCode());
				ordSaleService.setOperatorName(getOperatorName());
				ordSaleService.setOrderId(Long.valueOf(this.orderId));
				ordSaleService.setApplyContent(this.getRequest().getParameter("ord_applyContent"));
				ordSaleService.setServiceType(this.getRequest().getParameter("channelId"));
				ordSaleServiceService.addOrdSaleService(ordSaleService);
				orderServiceProxy.updateNeedSaleService("true", Long.valueOf(orderId), this.getOperatorName());
				this.jsonMsg="true";
			} catch (Exception e) {
				e.printStackTrace();
				this.jsonMsg="true";
			}
		}
		return "addOrderSale";
	}
	/**
	 * 申请售后服务,调转售后服务申请页面.
	 * 
	 * @return
	 */
	@Action(value = "/ordSale/transitOrderSaleAdd", results = @Result(name = "transitOrderSaleAdd", location = "/WEB-INF/pages/back/ord/sale/ord_sale_add.jsp"))
	public String transitOrderSaleAdd() {
		if (this.orderId != null) {
			ordSaleService = new OrdSaleService();
			ordSaleService.setOrderId(Long.parseLong(orderId));
			Map map=new HashMap();
			map.put("orderId", orderId);
			ordSaleServiceList=this.ordSaleServiceService.getOrdSaleServiceAllByParam(map);
			channelList = CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.SERVICE_TYPE.name());
		}
		return "transitOrderSaleAdd";
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrdSaleServiceService getOrdSaleServiceService() {
		return ordSaleServiceService;
	}

	public void setOrdSaleServiceService(
			OrdSaleServiceService ordSaleServiceService) {
		this.ordSaleServiceService = ordSaleServiceService;
	}

	public OrdSaleService getOrdSaleService() {
		return ordSaleService;
	}

	public void setOrdSaleService(OrdSaleService ordSaleService) {
		this.ordSaleService = ordSaleService;
	}
	public List<OrdSaleService> getOrdSaleServiceList() {
		return ordSaleServiceList;
	}
	public void setOrdSaleServiceList(List<OrdSaleService> ordSaleServiceList) {
		this.ordSaleServiceList = ordSaleServiceList;
	}
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
	}
	public List<CodeItem> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<CodeItem> channelList) {
		this.channelList = channelList;
	}
	public String getJsonMsg() {
		return jsonMsg;
	}
	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}
	public OrdSaleServiceDeal getOrdSaleServiceDeal() {
		return ordSaleServiceDeal;
	}
	public void setOrdSaleServiceDeal(OrdSaleServiceDeal ordSaleServiceDeal) {
		this.ordSaleServiceDeal = ordSaleServiceDeal;
	}
	public List<OrdSaleServiceDeal> getOrdSaleServiceDealList() {
		return ordSaleServiceDealList;
	}
	public void setOrdSaleServiceDealList(
			List<OrdSaleServiceDeal> ordSaleServiceDealList) {
		this.ordSaleServiceDealList = ordSaleServiceDealList;
	}
	public String getSaleServiceId() {
		return saleServiceId;
	}
	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}
	public OrdSaleServiceServiceDeal getOrdSaleServiceServiceDeal() {
		return ordSaleServiceServiceDeal;
	}
	public void setOrdSaleServiceServiceDeal(
			OrdSaleServiceServiceDeal ordSaleServiceServiceDeal) {
		this.ordSaleServiceServiceDeal = ordSaleServiceServiceDeal;
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getPermId() {
		return permId;
	}
	public void setPermId(String permId) {
		this.permId = permId;
	}
}
