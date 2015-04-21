package com.lvmama.back.web.abroadhotel.sale;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.utils.StringUtil;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.po.AhotelOrdSaleService;
import com.lvmama.comm.abroad.po.AhotelOrdSaleServiceDeal;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;

/**
 * 订单售后服务类.
 * 
 * @author huangl
 */
@SuppressWarnings("unused")
public class AhotelOrdSaleDealAddAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1183889081044444541L;

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
	private AhotelOrdSaleServiceDeal ordSaleDealPo=new AhotelOrdSaleServiceDeal();
	/**
	 * 
	 */
	private List<AhotelOrdSaleService> ordSaleServiceList;
	/**
	 */
	private List<AhotelOrdSaleServiceDeal> ordSaleServiceDealList;
	
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	
	/**
	 * 订单服务查询类型.
	 */
	private String serviceType;
	/**
	 * 传真结束原因.
	 */
	private String saleReason;
	/**
	 * 初始化查询参数.
	 */
	@SuppressWarnings("unchecked")
	public void doBefore() {
		if (this.saleServiceId != null) {
			Map map=new HashMap();
			map.put("saleServiceId", saleServiceId);
			ordSaleServiceDealList=abroadhotelOrderService.getAhotelOrdSaleServiceAllByParam(map);
			Map saleMap=new HashMap();
			saleMap.put("saleServiceId", saleServiceId);
			ordSaleServiceList=this.abroadhotelOrderService.findFullAhotelOrdSaleServiceByParam(saleMap);
		}		
	}
	
	/**
	 * 售后服务处理结果,增加对象.
	 * 
	 * @return
	 */
	public void  addOrderSaleDeal()throws IOException {
		if (this.saleServiceId != null) {
			try {
				AhotelOrdSaleServiceDeal ordSaleDeal=new AhotelOrdSaleServiceDeal();
				ordSaleDeal.setCreateTime(new Date());
				ordSaleDeal.setOperatorName(this.getSessionUserName());
				ordSaleDeal.setDealContent(this.ordSaleDealPo.getDealContent());
				ordSaleDeal.setSaleServiceId(Long.valueOf(saleServiceId));
				abroadhotelOrderService.addAhotelOrdSaleServiceDeal(ordSaleDeal);
				Map map=new HashMap();
				map.put("saleServiceId", saleServiceId);
				ordSaleServiceDealList=abroadhotelOrderService.getAhotelOrdSaleServiceAllByParam(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将售后服务处理状态修改为结束.
	 */
	public void ordSaleFinish(){
		if(!StringUtil.isEmptyString(saleServiceId)){
			AhotelOrdSaleService ahotelOrdSaleService = selectAhotelService();
			ahotelOrdSaleService.setStatus("FINISH");
			ahotelOrdSaleService.setReason(this.saleReason);
			abroadhotelOrderService.updateAhotelOrdSaleService(ahotelOrdSaleService);
		}
	}
	/**
	 * 将用户的售后服务类型转换为投拆类型.
	 */
	public void updateOrdSaleServiceType(String status){
		if(!StringUtil.isEmptyString(saleServiceId)){
			//记录用户点击转换投拆时日志.
			AhotelOrdSaleServiceDeal ordSaleDeal=new AhotelOrdSaleServiceDeal();
			ordSaleDeal.setCreateTime(new Date());
			ordSaleDeal.setOperatorName(this.getSessionUserName());
			ordSaleDeal.setDealContent("客服:"+this.getSessionUserName()+",将其售后状态变更!");
			ordSaleDeal.setSaleServiceId(Long.valueOf(saleServiceId));
			abroadhotelOrderService.addAhotelOrdSaleServiceDeal(ordSaleDeal);
			//进行转换投拆或常规售后.
			AhotelOrdSaleService ahotelOrdSaleService = selectAhotelService();
			ahotelOrdSaleService.setServiceType(status);
			abroadhotelOrderService.updateAhotelOrdSaleService(ahotelOrdSaleService);
		}
	}

	private AhotelOrdSaleService selectAhotelService() {
		Map map=new HashMap();
		map.put("saleServiceId", saleServiceId);
		List<AhotelOrdSaleService> list=abroadhotelOrderService.findFullAhotelOrdSaleServiceByParam(map);
		AhotelOrdSaleService ahotelOrdSaleService = new AhotelOrdSaleService();
		if(list!=null&&list.size()>0){
			ahotelOrdSaleService=list.get(0);
		}
		return ahotelOrdSaleService;
	}

	
	

	public String getSaleServiceId() {
		return saleServiceId;
	}

	public void setSaleServiceId(String saleServiceId) {
		this.saleServiceId = saleServiceId;
	}

	public String getCanCloseWin() {
		return canCloseWin;
	}

	public void setCanCloseWin(String canCloseWin) {
		this.canCloseWin = canCloseWin;
	}

	public String getEditabled() {
		return editabled;
	}

	public void setEditabled(String editabled) {
		this.editabled = editabled;
	}

	public AhotelOrdSaleServiceDeal getOrdSaleDealPo() {
		return ordSaleDealPo;
	}

	public void setOrdSaleDealPo(AhotelOrdSaleServiceDeal ordSaleDealPo) {
		this.ordSaleDealPo = ordSaleDealPo;
	}

	public List<AhotelOrdSaleService> getOrdSaleServiceList() {
		return ordSaleServiceList;
	}

	public void setOrdSaleServiceList(List<AhotelOrdSaleService> ordSaleServiceList) {
		this.ordSaleServiceList = ordSaleServiceList;
	}

	public List<AhotelOrdSaleServiceDeal> getOrdSaleServiceDealList() {
		return ordSaleServiceDealList;
	}

	public void setOrdSaleServiceDealList(
			List<AhotelOrdSaleServiceDeal> ordSaleServiceDealList) {
		this.ordSaleServiceDealList = ordSaleServiceDealList;
	}

	public AbroadhotelOrderService getAbroadhotelOrderService() {
		return abroadhotelOrderService;
	}

	public void setAbroadhotelOrderService(
			AbroadhotelOrderService abroadhotelOrderService) {
		this.abroadhotelOrderService = abroadhotelOrderService;
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

}
