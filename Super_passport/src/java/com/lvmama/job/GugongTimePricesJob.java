package com.lvmama.job;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
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
import com.lvmama.passport.processor.impl.client.gugong.GugongConstant;
import com.lvmama.passport.processor.impl.client.gugong.GugongHTTPUtil;
import com.lvmama.passport.processor.impl.client.gugong.GugongTimePrice;
import org.apache.http.ParseException;
public class GugongTimePricesJob {
	private static final Log log = LogFactory.getLog(GugongTimePricesJob.class);

	private WorkOrderService workOrderService;
	private MetaProductBranchService metaProductBranchService;
	private MetaProductService metaProductService;
	private ProdProductService prodProductService;
	private ProdProductBranchService prodProductBranchService;
	private PublicWorkOrderService publicWorkOrderService;
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			Date flagDay = DateUtil.getDateAfterDays(DateUtil.getTodayYMDDate(), 1);
			Date lastDay = DateUtil.getDateAfterDays(flagDay, 5);
			while (flagDay.before(lastDay)) {
				String workOrderContent = "";
				GugongTimePrice gugongTimePrice = null;
				try {
					gugongTimePrice = GugongHTTPUtil.getTimePrices(DateUtil.formatDate(flagDay, "yyyy-MM-dd"));
				} catch (ClientProtocolException e) {
					workOrderContent = "请求永乐方时间价格客户端协议异常" + e;
					log.error(workOrderContent);
				} catch (ParseException e) {
					workOrderContent = "解析永乐方返回的时间价格异常" + e;
					log.error(workOrderContent);
				} catch (IOException e) {
					workOrderContent = "请求永乐方时间价格网络异常" + e;
					log.error(workOrderContent);
				} catch (Exception e) {
					workOrderContent = e.getMessage();
					log.error(e);
				}
				if (gugongTimePrice == null || !gugongTimePrice.isSuccess()) {
					createWorkOrder(workOrderContent);
				} else {
					if (gugongTimePrice.isSuccess()) {
						makeTimePrice(gugongTimePrice);
					}
				}
				flagDay = DateUtil.getDateAfterDays(flagDay, 1);
			}
		}
	}

	public boolean makeTimePrice(GugongTimePrice gugongTimePrice) {
		try{ 

			List<MetaProductBranch> metaProductBranchList = metaProductBranchService.selectMetaProductBranchBySupplierId(GugongConstant.supplierId);
			for(MetaProductBranch metaBranch : metaProductBranchList){
				insertMetaAndProdTimePrices(metaBranch,gugongTimePrice) ;
			}
			return true;
		}catch(Exception e){
			createWorkOrder(e.getMessage());
			log.error(e);
			return false;
		}
	}

	private void insertMetaAndProdTimePrices(MetaProductBranch metaProductBranch, GugongTimePrice gugongTimePrice){
		
		String resultHead = "故宫对接：同步时间价格数据时异常，";
		try{
			TimePrice metaTimePrice = mockTimePrice(metaProductBranch,gugongTimePrice);
			metaProductService.saveTimePrice(metaTimePrice, metaProductBranch.getMetaBranchId(), "SYSTEM");
			
			List<ProdProductBranch> prodProductBranchList = prodProductBranchService.getProductBranchByMetaProdBranchId(metaProductBranch.getMetaBranchId());
			ProdProductBranch prodProductBranch = prodProductBranchList != null && prodProductBranchList.size() > 0 ? prodProductBranchList.get(0) : null;
			if(prodProductBranch!=null){
				insertProdTimePrice(prodProductBranch, metaTimePrice);
			}else{
				log.info("productBranch is null!");
			}
			
		}catch(Exception e){
			createWorkOrder(e.getMessage());
			log.error(resultHead+e);
		}
	}
	
	private TimePrice mockTimePrice(MetaProductBranch metaProductBranch, GugongTimePrice gugongTimePrice) {
		TimePrice metaTimePrice = new TimePrice();

		metaTimePrice.setProductId(metaProductBranch.getMetaProductId());
		metaTimePrice.setMetaBranchId(metaProductBranch.getMetaBranchId());
		
		metaTimePrice.setBeginDate(DateUtil.toDate(gugongTimePrice.getIntodate(), "yyyy-MM-dd"));
		metaTimePrice.setEndDate(DateUtil.toDate(gugongTimePrice.getIntodate(), "yyyy-MM-dd"));
		
		metaTimePrice.setSettlementPriceF(gugongTimePrice.getSettlementPrice(metaProductBranch, metaTimePrice));
		metaTimePrice.setMarketPrice(gugongTimePrice.getMarketPrice(metaProductBranch, metaTimePrice));
		metaTimePrice.setPrice(gugongTimePrice.getMarketPrice(metaProductBranch, metaTimePrice));

		metaTimePrice.setRatePrice(GugongConstant.RATE);
		metaTimePrice.setPriceType(Constant.PRICE_TYPE.FIXED_PRICE.name());

		metaTimePrice.setAheadHourFloat(GugongConstant.aheadHours);

		metaTimePrice.setOverSale("false");
		metaTimePrice.setResourceConfirm("false");
		metaTimePrice.setDayStock(gugongTimePrice.getTotalnumber());
		metaTimePrice.setCancelStrategy(Constant.CANCEL_STRATEGY.MANUAL.name());

		metaTimePrice.setEarliestUseTime(GugongConstant.earliestUseTime);
		metaTimePrice.setLatestUseTime(GugongConstant.latestUseTime);
		
		return metaTimePrice;
	}

	private void insertProdTimePrice(ProdProductBranch prodProductBranch, TimePrice metaTimePrice) {
		try {
			log.info("saveProdTimePrice ProductId:"+prodProductBranch.getProductId());
			TimePrice prodTimePrice = new TimePrice();
			prodTimePrice.setWeekOpen("false");
			prodTimePrice.setProductId(prodProductBranch.getProductId());
			prodTimePrice.setProdBranchId(prodProductBranch.getProdBranchId());

			prodTimePrice.setAheadHour((long)GugongConstant.aheadHours);
			prodTimePrice.setPriceType(Constant.PRICE_TYPE.FIXED_PRICE.name());
			prodTimePrice.setRatePrice(GugongConstant.RATE);
			prodTimePrice.setPrice(metaTimePrice.getPrice());
			prodTimePrice.setMarketPrice(metaTimePrice.getMarketPrice());
			
			prodTimePrice.setBeginDate(metaTimePrice.getBeginDate());
			prodTimePrice.setEndDate(metaTimePrice.getEndDate());
			prodProductService.saveTimePrice(prodTimePrice, prodProductBranch.getProductId(), "SYSTEM");
		} catch (Exception e) {
			createWorkOrder(e.getMessage());
			log.error("故宫对接：增加销售产品（类别ID:" + prodProductBranch.getProdBranchId() + "）的时间价格数据时异常" + e);
			
		}
	}

	public void createWorkOrder(String workOrderContent){
		WorkOrderCreateParam param = new WorkOrderCreateParam();
		param.setWorkTaskContent("故宫同步时间价格数据异常"+workOrderContent);
		param.setProcessLevel(Constant.WORK_ORDER_PROCESS_LEVEL.PROMPTLY.getCode());
		param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.GGYC.getWorkOrderTypeCode());
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
