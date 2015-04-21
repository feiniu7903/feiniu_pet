/**
 * 
 */
package com.lvmama.service.handle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.LineStationStation;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.train.InventoryInfo;
import com.lvmama.comm.bee.vo.train.SeatInventory;
import com.lvmama.comm.pet.service.search.ProdTrainCacheService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.SupplierProductInfo;
import com.lvmama.comm.vo.SupplierProductInfo.Item;
import com.lvmama.comm.vo.train.product.TicketInventoryReqVo;
import com.lvmama.comm.vo.train.product.TicketInventoryRspVo;
import com.lvmama.service.CheckStockHandle;
import com.lvmama.train.service.TrainClient;
import com.lvmama.train.service.request.TicketInventoryRequest;
import com.lvmama.train.service.response.TicketInventoryResponse;

/**
 * @author yangbin
 *
 */
public class TrainCheckStockHandle implements CheckStockHandle{
	private static final Log logger =LogFactory.getLog(TrainCheckStockHandle.class);
	private MetaProductBranchService metaProductBranchService;
	private MetaProductService metaProductService;
	private ProdTrainService prodTrainService;
	private ProdTrainCacheService prodTrainCacheService;
	private ProdProductBranchService prodProductBranchService;
	private ProdProductService prodProductService;
	
	private final TrainClient client = new TrainClient();
	
	@Override
	public List<Item> check(BuyInfo byInfo,List<Item> list) {
		Map<String,InventoryInfo> map = new HashMap<String, InventoryInfo>();
		for(Item item : list){
			MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(item.getMetaBranchId());
			String key=metaBranch.getMetaProductId()+"_"+metaBranch.getStationStationId();
			if(map.containsKey(key)){
				InventoryInfo info = map.get(key);
				for(SeatInventory si:info.getSeatInventoies()){
					if(si.hasBookable()){
						item.setStock(SupplierProductInfo.STOCK.AMPLE);
					}else{
						item.setStock(SupplierProductInfo.STOCK.LACK);
					}
				}
			}else{
				LineStationStation lss=prodTrainService.getStationStationDetailById(metaBranch.getStationStationId());
				TicketInventoryReqVo vo = new TicketInventoryReqVo();
				vo.setDepartStation(lss.getDepartureStation().getStationName());
				vo.setArriveStation(lss.getArrivalStation().getStationName());
				vo.setDepartDate(DateUtil.formatDate(item.getVisitTime(), "yyyy-MM-dd"));
				vo.setTrainId(lss.getLineName());
//				vo.setTicketClass(metaBranch.getBerth());
				
				TicketInventoryRequest request = new TicketInventoryRequest(vo);
				TicketInventoryResponse response = client.execute(request);
				if(response.isSuccess()){
					TicketInventoryRspVo rspVo = (TicketInventoryRspVo)response.getRsp().getVo();
					InventoryInfo info = rspVo.arrange(lss.getLineName());
					map.put(key, info);
					List<MetaProductBranch> branchList = metaProductBranchService.selectBranchListByProductId(metaBranch.getMetaProductId());
					List<ProdProductBranch> prodList = prodProductBranchService.selectProdBranchsByStationStation(lss.getStationStationId());
					for(SeatInventory si : info.getSeatInventoies()){
						//根据坐席类型获取该坐席子项所有类型票(成人票、儿童票)
						List<ProdProductBranch> ppbs = getProductBranch(prodList, si.getCatalog());
						if(StringUtils.equals(metaBranch.getBerth(),si.getCatalog())){
							if(si.hasBookable()){
								item.setStock(SupplierProductInfo.STOCK.AMPLE);
							}else{
								item.setStock(SupplierProductInfo.STOCK.LACK);
								if(ppbs != null){
									for(ProdProductBranch ppb : ppbs)
										closeTimePrice(metaBranch.getMetaBranchId(),item.getVisitTime(),ppb);
								}
							}
						}
						else{
							if(!si.hasBookable()){
								//根据坐席类型获取该坐席子子项所有类型票(成人票、儿童票)
								List<MetaProductBranch> branchs = getMetaBranch(branchList,si.getCatalog());
								if(branchs != null){
									for(MetaProductBranch branch : branchs){
										if(ppbs != null){
											for(ProdProductBranch ppb : ppbs)
												closeTimePrice(branch.getMetaBranchId(),item.getVisitTime(),ppb);
										}
									}
								}
							}
						}
					}
					if(SupplierProductInfo.STOCK.NONE.equals(item.getStock())){
						item.setStock(SupplierProductInfo.STOCK.LACK);
						if(CollectionUtils.isEmpty(info.getSeatInventoies())){
							for(ProdProductBranch ppb : prodList)
								closeTimePrice(metaBranch.getMetaBranchId(),item.getVisitTime(),ppb);
						}
					}
				}
			}
		}
		return list;
	}
	
	private List<ProdProductBranch> getProductBranch(List<ProdProductBranch> branchList,String branchType){
		List<ProdProductBranch> result = null;
		for(ProdProductBranch ppb : branchList){
//			logger.info(ppb.getProdBranchId());
			if(StringUtils.equals(ppb.getBerth(),branchType)){
				if(result == null)
					result = new ArrayList<ProdProductBranch>();
				result.add(ppb);
			}
		}
		return result;
	}
	
	private List<MetaProductBranch> getMetaBranch(List<MetaProductBranch> branchList,String branchType){
		List<MetaProductBranch> result = null;
		for(MetaProductBranch mpb:branchList){
			if(StringUtils.equals(mpb.getBerth(),branchType)){
				if(result == null)
					result = new ArrayList<MetaProductBranch>();
				result.add(mpb);
			}
		}
		return result;
	}
	
	private void closeTimePrice(Long metaBranchId,Date visitTime,ProdProductBranch ppb){
		TimePrice timePrice = metaProductService.getMetaTimePriceByIdAndDate(metaBranchId, visitTime);
		if(timePrice!=null){
			TimePrice bean = new TimePrice();
			bean.setBeginDate(visitTime);
			bean.setEndDate(visitTime);
//			bean.setClose("true");
			bean.setDayStock(0);
			metaProductService.updateTrainTimePrice(bean, metaBranchId);
		}
		
		
		TimePrice prodTimePrice=prodProductService.getTimePriceByProdId(ppb.getProductId(), ppb.getProdBranchId(), visitTime);
		if(prodTimePrice!=null){
//			timePrice= new TimePrice();
//			timePrice.setProdBranchId(ppb.getProdBranchId());
//			timePrice.setProductId(ppb.getProductId());
//			timePrice.setClose("true");
//			timePrice.setBeginDate(visitTime);
//			timePrice.setEndDate(visitTime);
//			prodProductService.saveTimePrice(timePrice, ppb.getProductId(), "System");
		}
		List<Long> ids = new ArrayList<Long>();
		ids.add(ppb.getProdBranchId());
		prodTrainCacheService.markSoldout(ids, visitTime);
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}

	public void setProdTrainCacheService(ProdTrainCacheService prodTrainCacheService) {
		this.prodTrainCacheService = prodTrainCacheService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

}
