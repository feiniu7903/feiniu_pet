package com.lvmama.back.web.ord.sale;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Window;

import com.lvmama.back.web.BaseAction;
import com.lvmama.back.web.ord.refundMent.OrdOrderRefundAddAction;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.mark.MarkChannel;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 订单售后服务类.
 * 
 * @author huangl
 */
@SuppressWarnings("unused")
public class OrdSaleAddAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String orderId;
	private String sysCode;
	private OrdOrderRefundAddAction ordSaleContext;
	private OrdSaleService ordSalePo=new OrdSaleService();
	private OrdSaleServiceService ordSaleServiceService;
	private List<OrdSaleService> ordSaleServiceList;
	private HashMap<String,Object> searchSaleAddMap=new HashMap<String,Object>();
	/**
	 * 初始化查询参数.
	 */
	@SuppressWarnings("unchecked")
	public void doBefore() {
		Map map=new HashMap();
		map.put("orderId", orderId);
		if(StringUtils.isEmpty(sysCode)){
			sysCode=Constant.COMPLAINT_SYS_CODE.SUPER.getCode();
		}
		map.put("sysCode", sysCode);
		ordSaleServiceList=this.getOrdSaleServiceService().getOrdSaleServiceAllByParam(map);
	}
	
	/**
	 * 售后服务,增加对象.
	 * 
	 * @return
	 */
	public void  addOrderSale()throws IOException {
		try {
			if(this.orderId!=null){
				OrdSaleService ordSevice=new OrdSaleService();
				ordSevice.setCreateTime(new Date());
				ordSevice.setOperatorName(this.getSessionUserName());
				ordSevice.setOrderId(Long.valueOf(this.orderId));
				if(StringUtils.isEmpty(sysCode)){
					sysCode=Constant.COMPLAINT_SYS_CODE.SUPER.getCode();
				}
				ordSevice.setSysCode(sysCode);
				ordSevice.setApplyContent(ordSalePo.getApplyContent());
				ordSevice.setServiceType(ordSalePo.getServiceType());
				ordSevice.setStatus("NORMAL");
				ordSaleServiceService.addOrdSaleService(ordSevice);
				this.getOrderServiceProxy().updateNeedSaleService("true", Long.valueOf(orderId), this.getOperatorName());
				
				Map map=new HashMap();
				map.put("orderId", orderId);
				map.put("sysCode", sysCode);
				ordSaleServiceList=this.getOrdSaleServiceService().getOrdSaleServiceAllByParam(map);
				alert("提交成功");
				super.refreshParent(getComponent(), "refreshTreeBtn");
				super.closeWindow();
			}
		} catch (Exception e) {
			alert("提交失败");
				e.printStackTrace();
		}
	}
	public void changeStatus(String value) {
		if (Constant.SERVICE_TYPE.COMPLAINT.name().equals(value)) {
			ordSalePo.setMessage("录入新投诉请关闭本窗口，点击“申请新投诉入口”");
		} else{
			ordSalePo.setMessage("");
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

	public OrderService getOrderServiceProxy() {
		return (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	}
	
	public OrdSaleServiceService getOrdSaleServiceService() {
		return (OrdSaleServiceService)SpringBeanProxy.getBean("ordSaleServiceService");
	}

	public void setOrdSaleServiceService(OrdSaleServiceService ordSaleServiceService) {
		this.ordSaleServiceService = ordSaleServiceService;
	}

	public List<OrdSaleService> getOrdSaleServiceList() {
		return ordSaleServiceList;
	}

	public void setOrdSaleServiceList(List<OrdSaleService> ordSaleServiceList) {
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

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	
}
