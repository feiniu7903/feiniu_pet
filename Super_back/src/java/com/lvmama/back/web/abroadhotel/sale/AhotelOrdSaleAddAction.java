package com.lvmama.back.web.abroadhotel.sale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.lvmama.back.web.BaseAction;
import com.lvmama.back.web.ord.refundMent.OrdOrderRefundAddAction;
import com.lvmama.comm.abroad.po.AhotelOrdSaleService;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;

/**
 * 订单售后服务类.
 */
@SuppressWarnings("unused")
public class AhotelOrdSaleAddAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String orderId;
	private OrdOrderRefundAddAction ordSaleContext;
	private OrdSaleService ordSalePo=new OrdSaleService();
	private OrdSaleServiceService ordSaleServiceService;
	private List<AhotelOrdSaleService> ordSaleServiceList;
	private HashMap<String,Object> searchSaleAddMap=new HashMap<String,Object>();
	Listbox serviceTypeListbox;
	List<CodeItem> serviceTypes  = new ArrayList<CodeItem>();
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	
	/**
	 * 初始化查询参数.
	 */
	@SuppressWarnings("unchecked")
	public void doBefore() {
		Map map=new HashMap();
		map.put("orderId", orderId);
		ordSaleServiceList=abroadhotelOrderService.findFullAhotelOrdSaleServiceByParam(map);
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
	 * 售后服务,增加对象.
	 * 
	 * @return
	 */
	public void  addOrderSale()throws IOException {
		try {
			if(this.orderId!=null){
				String serviceType = (String)serviceTypeListbox.getSelectedItem().getValue();
				String operatroId = String.valueOf(getSessionUser().getUserId());
				AhotelOrdSaleService ordSevice=new AhotelOrdSaleService();
				ordSevice.setCreateTime(new Date());
				ordSevice.setOperatorName(this.getSessionUserName());
				ordSevice.setOrderId(Long.valueOf(this.orderId));
				ordSevice.setApplyContent(ordSalePo.getApplyContent());
				ordSevice.setServiceType(serviceType);
				ordSevice.setStatus("NORMAL");
				abroadhotelOrderService.insertAhotelOrdSaleService(ordSevice);
				Map map=new HashMap();
				map.put("orderId", orderId);
				ordSaleServiceList=abroadhotelOrderService.findFullAhotelOrdSaleServiceByParam(map);
				alert("提交成功");
				super.closeWindow();
			}
		} catch (Exception e) {
			alert("提交失败");
				e.printStackTrace();
		}
	}
	
	public void changeServiceType(String serviceType) {
		ordSalePo.setServiceType(serviceType);
	}
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public HashMap<String, Object> getSearchSaleAddMap() {
		return searchSaleAddMap;
	}
	public void setSearchSaleAddMap(HashMap<String, Object> searchSaleAddMap) {
		this.searchSaleAddMap = searchSaleAddMap;
	}
	public OrdSaleService getOrdSalePo() {
		return ordSalePo;
	}

	public void setOrdSalePo(OrdSaleService ordSalePo) {
		this.ordSalePo = ordSalePo;
	}

	public void setOrdSaleServiceService(OrdSaleServiceService ordSaleServiceService) {
		this.ordSaleServiceService = ordSaleServiceService;
	}

	public List<AhotelOrdSaleService> getOrdSaleServiceList() {
		return ordSaleServiceList;
	}

	public void setOrdSaleServiceList(List<AhotelOrdSaleService> ordSaleServiceList) {
		this.ordSaleServiceList = ordSaleServiceList;
	}

	public OrdOrderRefundAddAction getOrdSaleContext() {
		if(this.getOrdSaleContext()!=null){
			ordSaleContext.setWinOrdSaleAdd((Window)this.getComponent());
		}
		return ordSaleContext;
	}

	public void setOrdSaleContext(OrdOrderRefundAddAction ordSaleContext) {
		this.ordSaleContext = ordSaleContext;
	}

	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
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
}
