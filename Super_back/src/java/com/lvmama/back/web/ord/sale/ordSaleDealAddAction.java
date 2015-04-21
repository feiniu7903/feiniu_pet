package com.lvmama.back.web.ord.sale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.utils.StringUtil;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.ord.OrdSaleServiceDeal;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceServiceDeal;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant.COMPLAINT_SYS_CODE;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.service.VstSuppSupplierService;
import com.lvmama.comm.vst.vo.VstOrdOrderItem;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;

/**
 * 订单售后服务类.
 * 
 * @author huangl
 */
@SuppressWarnings("unused")
public class ordSaleDealAddAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 售后服务接口.
	 */
	private OrdSaleServiceServiceDeal ordSaleServiceServiceDeal;
	/**
	 * 服务编号.
	 */
	private String saleServiceId;
	
	private String canCloseWin;
	/**
	 * 只读
	 */
	private String editabled = "true";
	/**
	 * 
	 */
	private OrdSaleServiceDeal ordSaleDealPo=new OrdSaleServiceDeal();
	/**
	 * 
	 */
	private OrdSaleServiceService ordSaleServiceService;
	/**
	 * 
	 */
	private List<OrdSaleService> ordSaleServiceList;
	/**
	 */
	private List<OrdSaleServiceDeal> ordSaleServiceDealList;
	/**
	 * 订单服务查询类型.
	 */
	private String serviceType;
	/**
	 * 传真结束原因.
	 */
	private String saleReason;
	/**
	 * 查询该订单下所有的供应商名称
	 */
	private List<SupSupplier> supList;
	
	private MetaProductService metaProductService;
	
	private VstSuppSupplierService vstSuppSupplierService;
	
	private VstOrdOrderService vstOrdOrderService;
	
	private SupplierService supplierService;
	
	private Long supplierId; //供应商Id
	
	/**
	 * 初始化查询参数.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doBefore() {
		if (this.saleServiceId != null) {
			Map map=new HashMap();
			map.put("saleServiceId", saleServiceId);
			ordSaleServiceDealList=this.getOrdSaleServiceServiceDeal().getOrdSaleServiceAllByParam(map);
			Map saleMap=new HashMap();
			saleMap.put("saleServiceId", saleServiceId);
			ordSaleServiceList=this.getOrdSaleServiceService().getOrdSaleServiceAllByParam(saleMap);
			if(ordSaleServiceList.size()>0){
				
				OrdSaleService ordSaleService=ordSaleServiceList.get(0);
				
				//查询供应商
				if(COMPLAINT_SYS_CODE.SUPER.getCode().equals(ordSaleService.getSysCode())){
					List<OrdOrderItemMeta> list=metaProductService.queryOrderItemMetaIdByOrderId(ordSaleService.getOrderId());
					if(list.size()>0){
						supList=new ArrayList<SupSupplier>();
						List<SupSupplier> supplierList=new ArrayList<SupSupplier>();
						for (OrdOrderItemMeta orderItemMeta : list) {
							SupSupplier supplier=supplierService.getSupplier(orderItemMeta.getSupplierId());
							supplierList.add(supplier);
						}
						supList=checkSupplier(supplierList);
					}
				}else if(COMPLAINT_SYS_CODE.VST.getCode().equals(ordSaleService.getSysCode())){
					VstOrdOrderVo ordVo = vstOrdOrderService.getVstOrdOrderVo(ordSaleService.getOrderId());
					supList=new ArrayList<SupSupplier>();
					List<Long> supplierIds=new ArrayList<Long>();
		    		for (VstOrdOrderItem item : ordVo.getVstOrdOrderItems()) {
		    			if (null!=item.getSupplierId()) {
		    				VstSuppSupplierVo supplier = vstSuppSupplierService.findVstSuppSupplierById(item.getSupplierId());
		    				if(null!=supplier){
		    					SupSupplier supplierVo=new SupSupplier();
			    				supplierVo.setSupplierId(supplier.getSupplierId());
			    				supplierVo.setSupplierName(supplier.getSupplierName());
			    				if(!supplierIds.contains(supplier.getSupplierId())){
			    					supplierIds.add(supplier.getSupplierId());
			    					supList.add(supplierVo);
			    				}
		    				}
		    			}
		    		}
				}
			}
		}		
	}

	/**
	 * 去除重发的供应商
	 * @param supplierList
	 * @return
	 */
	private List<SupSupplier> checkSupplier(List<SupSupplier> supplierList) {
		for (int i = 0; i < supplierList.size() - 1; i++) {
			for (int j = supplierList.size() - 1; j > i; j--) {
				if (supplierList.get(j).getSupplierId().equals(supplierList.get(i).getSupplierId())) {
					supplierList.remove(j);
				}
			}
		}
		return supplierList;
	}
	
	/**
	 * 售后服务处理结果,增加对象.
	 * 
	 * @return
	 */
	public void  addOrderSaleDeal()throws IOException {
		if (this.saleServiceId != null) {
			try {
				OrdSaleServiceDeal ordSaleDeal=new OrdSaleServiceDeal();
				ordSaleDeal.setCreateTime(new Date());
				ordSaleDeal.setOperatorName(this.getSessionUserName());
				ordSaleDeal.setDealContent(this.ordSaleDealPo.getDealContent());
				ordSaleDeal.setSaleServiceId(Long.valueOf(saleServiceId));
				Map<String,Object> saleMap=new HashMap<String,Object>();
				saleMap.put("saleServiceId", saleServiceId);
				List<OrdSaleService> ordSaleServiceList=this.getOrdSaleServiceService().getOrdSaleServiceAllByParam(saleMap);
				querySupplierName(ordSaleDeal,ordSaleServiceList.get(0));
				this.getOrdSaleServiceServiceDeal().addOrdSaleServiceDeal(ordSaleDeal);
				Map map=new HashMap();
				map.put("saleServiceId", saleServiceId);
				ordSaleServiceDealList=this.getOrdSaleServiceServiceDeal().getOrdSaleServiceAllByParam(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取供应商名称
	 * @param ordSaleDeal
	 */
	private void querySupplierName(OrdSaleServiceDeal ordSaleDeal,OrdSaleService ordsaleservice) {
		if(COMPLAINT_SYS_CODE.SUPER.getCode().equals(ordsaleservice.getSysCode())){
			if (supplierId != null && supplierId > 0) {
				SupSupplier supplier=supplierService.getSupplier(supplierId); //根据供应商id查询
				ordSaleDeal.setSupplierName(supplier.getSupplierName());
			}
		}else if(COMPLAINT_SYS_CODE.VST.getCode().equals(ordsaleservice.getSysCode())){
			if (supplierId != null && supplierId > 0) {
				VstSuppSupplierVo supplier = vstSuppSupplierService.findVstSuppSupplierById(supplierId);
				if(null!=supplier){
					ordSaleDeal.setSupplierName(supplier.getSupplierName());
				}
			}
		}
	}
	
	/**
	 * 获取供应商id
	 * @param value
	 */
	public void changeSupplier(Long value) {
		if (value!=0 && value>0) {
			supplierId=value;
		}
	}
	
	public void changeSaleReason(String value){
		if(value!=null){
			saleReason=value;
		}
	}

	/**
	 * 将售后服务处理状态修改为结束.
	 */
	public void ordSaleFinish(){
		if(!StringUtil.isEmptyString(saleServiceId)){
			OrdSaleService ordsaleservice=this.getOrdSaleServiceService().getOrdSaleServiceByPk(Long.valueOf(saleServiceId));
			ordsaleservice.setStatus("FINISH");
			ordsaleservice.setReason(this.saleReason);
			ordsaleservice.setFinishTime(new Date());
			ordsaleservice.setOperatorName(this.getSessionUserName());
			this.getOrdSaleServiceService().updateOrdSaleService(ordsaleservice);
			//新增结束人记录
			OrdSaleServiceDeal ordSaleDeal=new OrdSaleServiceDeal();
			ordSaleDeal.setCreateTime(new Date());
			ordSaleDeal.setOperatorName(this.getSessionUserName());
			ordSaleDeal.setDealContent(this.ordSaleDealPo.getDealContent() + "，售后处理结束!");
			ordSaleDeal.setSaleServiceId(Long.valueOf(saleServiceId));
			
			Map<String,Object> saleMap=new HashMap<String,Object>();
			saleMap.put("saleServiceId", saleServiceId);
			List<OrdSaleService> ordSaleServiceList=this.getOrdSaleServiceService().getOrdSaleServiceAllByParam(saleMap);
			querySupplierName(ordSaleDeal,ordSaleServiceList.get(0));
			
			this.getOrdSaleServiceServiceDeal().addOrdSaleServiceDeal(ordSaleDeal);
		}
	}
	
	/**
	 * 将用户的售后服务类型转换为投拆类型.
	 */
	public void updateOrdSaleServiceType(String status){
		if(!StringUtil.isEmptyString(saleServiceId)){
			//记录用户点击转换投拆时日志.
			OrdSaleServiceDeal ordSaleDeal=new OrdSaleServiceDeal();
			ordSaleDeal.setCreateTime(new Date());
			ordSaleDeal.setOperatorName(this.getSessionUserName());
			ordSaleDeal.setDealContent("客服:"+this.getSessionUserName()+",将其售后状态变更!");
			ordSaleDeal.setSaleServiceId(Long.valueOf(saleServiceId));
			this.getOrdSaleServiceServiceDeal().addOrdSaleServiceDeal(ordSaleDeal);
			//进行转换投拆或常规售后.
			OrdSaleService ordsaleservice=this.getOrdSaleServiceService().getOrdSaleServiceByPk(Long.valueOf(saleServiceId));
			ordsaleservice.setServiceType(status);
			this.getOrdSaleServiceService().updateOrdSaleService(ordsaleservice);
		}
	}

	public OrdSaleServiceServiceDeal getOrdSaleServiceServiceDeal() {
		return (OrdSaleServiceServiceDeal)SpringBeanProxy.getBean("ordSaleServiceServiceDeal");
	}

	public void setOrdSaleServiceServiceDeal(
			OrdSaleServiceServiceDeal ordSaleServiceServiceDeal) {
		this.ordSaleServiceServiceDeal = ordSaleServiceServiceDeal;
	}

	public String getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}


	public OrdSaleServiceService getOrdSaleServiceService() {
		return ordSaleServiceService;
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

	public List<OrdSaleServiceDeal> getOrdSaleServiceDealList() {
		return ordSaleServiceDealList;
	}

	public void setOrdSaleServiceDealList(
			List<OrdSaleServiceDeal> ordSaleServiceDealList) {
		this.ordSaleServiceDealList = ordSaleServiceDealList;
	}

	public OrdSaleServiceDeal getOrdSaleDealPo() {
		return ordSaleDealPo;
	}

	public void setOrdSaleDealPo(OrdSaleServiceDeal ordSaleDealPo) {
		this.ordSaleDealPo = ordSaleDealPo;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSaleReason() {
		return saleReason;
	}

	public void setSaleReason(String saleReason) {
		this.saleReason = saleReason;
	}

	public String getEditabled() {
		return editabled;
	}

	public void setEditabled(String editabled) {
		this.editabled = editabled;
	}

	public void setCanCloseWin(String canCloseWin) {
		this.canCloseWin = canCloseWin;
	}

	public String getCanCloseWin() {
		return canCloseWin;
	}

	public List<SupSupplier> getSupList() {
		return supList;
	}

	public void setSupList(List<SupSupplier> supList) {
		this.supList = supList;
	}

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public void setVstSuppSupplierService(
			VstSuppSupplierService vstSuppSupplierService) {
		this.vstSuppSupplierService = vstSuppSupplierService;
	}

	public void setVstOrdOrderService(VstOrdOrderService vstOrdOrderService) {
		this.vstOrdOrderService = vstOrdOrderService;
	}
	

	


}
