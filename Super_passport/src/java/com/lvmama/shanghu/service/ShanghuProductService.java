package com.lvmama.shanghu.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.pet.service.work.WorkOrderService;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.shanghu.model.TimePriceBean;
import com.lvmama.passport.shanghu.ShanghuUtil;
import com.lvmama.passport.utils.WebServiceConstant;

public class ShanghuProductService {
	private static final Log log = LogFactory.getLog(ShanghuProductService.class);
	private WorkOrderService workOrderService;
	private MetaProductBranchService metaProductBranchService;
	private MetaProductService metaProductService;
	private ProdProductService prodProductService;
	private ProdProductBranchService prodProductBranchService;
	private PublicWorkOrderService publicWorkOrderService;
	public boolean makeTimePrice() {
		try{ 
			
			String supplierId=WebServiceConstant.getProperties("shanghutiangui.supplierId");
			List<MetaProductBranch> metaProductBranchList = metaProductBranchService.selectMetaProductBranchBySupplierId(Long.valueOf(supplierId));
			for (MetaProductBranch metaProductBranch : metaProductBranchList) {
				String productNo=metaProductBranch.getProductIdSupplier();
				if(!StringUtils.isBlank(productNo)){
					Date startDate = DateUtil.getFirstdayOfMonth(new Date());
					for (int i = 0; i < 2; i++) {
						List<TimePriceBean> timePrice=ShanghuUtil.init().getTimePriceInfo(productNo,startDate);
						for(int j=0;j<timePrice.size();j++){
							insertMetaTimePrices(metaProductBranch, timePrice.get(j));
						}
						startDate= DateUtils.addMonths(startDate, 1);
					}
				}
			}
			return true;
		}catch(Exception e){
			createWorkOrder(e.getMessage());
			log.error(e);
			return false;
		}
	}
	
	public boolean makeTimePriceByProductNoandDate(String prodNo) {
		try {
			String supplierId = WebServiceConstant.getProperties("shanghutiangui.supplierId");
			List<MetaProductBranch> metaProductBranchList = metaProductBranchService.selectMetaProductBranchBySupplierType(Long.valueOf(supplierId),null, prodNo);
			if (!metaProductBranchList.isEmpty()) {
				MetaProductBranch metaProductBranch = metaProductBranchList.get(0);
				Date startDate = DateUtil.getFirstdayOfMonth(new Date());
				for (int i = 0; i < 2; i++) {
					List<TimePriceBean> timePrice = ShanghuUtil.init().getTimePriceInfo(prodNo, startDate);
					for (int j = 0; j < timePrice.size(); j++) {
						insertMetaTimePrices(metaProductBranch,timePrice.get(j));
					}
					startDate = DateUtils.addMonths(startDate, 1);
				}
			}
			return true;
		} catch (Exception e) {
			createWorkOrder(e.getMessage());
			log.error(e);
			return false;
		}
	}
	private TimePrice mockTimePrice(MetaProductBranch metaProductBranch, TimePriceBean timePrice) {
		TimePrice metaTimePrice = new TimePrice();
		metaTimePrice.setProductId(metaProductBranch.getMetaProductId());
		metaTimePrice.setMetaBranchId(metaProductBranch.getMetaBranchId());
		metaTimePrice.setBeginDate(DateUtil.toDate(timePrice.getDate(), "yyyy-MM-dd"));
		metaTimePrice.setEndDate(DateUtil.toDate(timePrice.getDate(), "yyyy-MM-dd"));
		metaTimePrice.setSettlementPriceF(timePrice.getSettlementPrice());
		metaTimePrice.setMarketPrice(Long.valueOf(timePrice.getMarketPrice()));
		metaTimePrice.setPrice(Long.valueOf(timePrice.getSalePrice()));
		metaTimePrice.setAheadHour(timePrice.getStartDay());
		if(StringUtils.equals(String.valueOf(timePrice.getCancelDay()),"0")){
			metaTimePrice.setCancelStrategy(Constant.CANCEL_STRATEGY.FORBID.name());
		}else{
			metaTimePrice.setCancelStrategy(Constant.CANCEL_STRATEGY.ABLE.name());
			metaTimePrice.setCancelHour(timePrice.getCancelDay());
		}
		
		metaTimePrice.setOverSale("false");
		metaTimePrice.setResourceConfirm("false");
		if(StringUtils.equals(timePrice.getRemainNum(),"0")){
			metaTimePrice.setDayStock(Long.valueOf("-1"));
		}else{
			metaTimePrice.setDayStock(Long.valueOf(timePrice.getRemainNum()));
		}
		return metaTimePrice;
	}
	
	private void insertMetaTimePrices(MetaProductBranch metaProductBranch, TimePriceBean timePrice){
		
		String resultHead = "常熟尚湖对接：同步时间价格数据时异常，";
		try{
			TimePrice metaTimePrice = mockTimePrice(metaProductBranch,timePrice);
			metaProductService.saveTimePrice(metaTimePrice, metaProductBranch.getMetaBranchId(), "SYSTEM");
		}catch(Exception e){
			createWorkOrder(e.getMessage());
			log.error(resultHead+e.getMessage());
		}
	}

	public void createWorkOrder(String workOrderContent){
		WorkOrderCreateParam param = new WorkOrderCreateParam();
		param.setWorkTaskContent("常熟尚湖对接同步时间价格数据异常:"+workOrderContent);
		param.setProcessLevel(Constant.WORK_ORDER_PROCESS_LEVEL.PROMPTLY.getCode());
		param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.LLKYC.getWorkOrderTypeCode());
		publicWorkOrderService.createWorkOrder(param);
	}

	public WorkOrderService getWorkOrderService() {
		return workOrderService;
	}

	public void setWorkOrderService(WorkOrderService workOrderService) {
		this.workOrderService = workOrderService;
	}

	public MetaProductBranchService getMetaProductBranchService() {
		return metaProductBranchService;
	}

	public void setMetaProductBranchService(MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public ProdProductService getProdProductService() {
		return prodProductService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public ProdProductBranchService getProdProductBranchService() {
		return prodProductBranchService;
	}

	public void setProdProductBranchService(ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setPublicWorkOrderService(PublicWorkOrderService publicWorkOrderService) {
		this.publicWorkOrderService = publicWorkOrderService;
	}

}
