package com.lvmama.prd.service;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.service.prod.BeeProdProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.prod.ProdProductHead;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.comm.pet.po.search.ProdTrainCache;
import com.lvmama.comm.pet.service.search.ProdTrainCacheService;
import com.lvmama.prd.logic.CalendarUtilV2;
import com.lvmama.prd.logic.ProdProductBranchLogic;
import com.lvmama.prd.logic.SelfPackCalendarUtilV2;
import com.lvmama.prd.logic.SelfPackCalendarUtilV3;


public class BeeProdProductServiceImpl implements BeeProdProductService{
	private ProdProductService prodProductService;
	private ProdProductBranchService prodProductBranchService;
	private ProdProductBranchLogic prodProductBranchLogic;
	private ProdTimePriceDAO prodTimePriceDAO;
	
	private ProdTrainService prodTrainService;
	private ProdTrainCacheService prodTrainCacheService;
	 
	@Override
	public ProdProductBranch getProdBranchDetailByProdBranchId(
			Long prodBranchId, Date visitTime) {
		ProdProductBranch ppb = prodProductBranchLogic.getProdProductBranch(prodBranchId);
		if(ppb.getProdProduct().isTrain()){
			//检查是否存在销售类别时间价格表，不存在就添加
			if(prodProductBranchService.getProdTimePrice(prodBranchId, visitTime)==null){
				ProdTrainCache cache = prodTrainCacheService.get(prodBranchId, visitTime);
				if (cache != null) {
					prodTrainService.createTimePrice(ppb, visitTime,
							cache.getPrice());
				}
			}
		}
		return prodProductBranchLogic.fill(ppb, visitTime, false, true);
	}

	public Date selectNearBranchTimePriceByBranchId(Long prodBranchId,Date beginDay) {
		return prodTimePriceDAO.selectNearBranchTimePriceByBranchIdAndDay(prodBranchId,beginDay);
	}
	
	@Override
	public List<CalendarModel> getProductCalendarByProductId(Long productId) {
		CalendarUtilV2 calendarUtil = new CalendarUtilV2();
		return calendarUtil.selectSaleTimePriceByProductId(productId);
	}

	
	@Override
	public List<CalendarModel> getProductCalendarByBranchId(Long branchId) {
		CalendarUtilV2 calendarUtil = new CalendarUtilV2();
		return calendarUtil.selectSaleTimePrice(branchId);
	}
	
	public List<CalendarModel> getSelfProductCalendarByBranchIdAndDay(Long branchId, Date beginDay){
		CalendarUtilV2 calendarUtil = new SelfPackCalendarUtilV2(beginDay);
		return calendarUtil.selectSaleTimePrice(branchId);
	}
	
	@Override
	public boolean isSellProductByChannel(Long productId, String channel) {
		return prodProductService.isSellProductByChannel(productId, channel);
	}


	@Override
	public Date getProductsLastCancelTime(List<Long> branchIdList, Date visitTime) {
 		return this.prodProductService.getProductsLastCancelTime(branchIdList, visitTime);
	}


	@Override
	public List<ProdAssemblyPoint> getAssemblyPoints(Long productId) {
		return this.prodProductService.getAssemblyPoints(productId);
	}


	@Override
	public ProdProductHead getProdProductHeadByProductId(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<ProdProductBranch> getProdBranchList(Long productId,
			Long removeBranchId, Date visitTime) {
		return this.prodProductService.getProductBranchDetailByProductId(productId,removeBranchId,visitTime,"false",true);
	}


	@Override
	public List<ProdProductRelation> getRelatProduct(Long productId,
			Date visitTime) {
 		return this.prodProductService.getRelatProduct(productId, visitTime);
	}


	@Override
	public ProdProductBranch getProdBranchDetailByProdBranchId(
			Long prodBranchId, Date visitTime, boolean checkOnline) {
		return this.prodProductService.getProdBranchDetailByProdBranchId(prodBranchId, visitTime, checkOnline);
	}


	@Override
	public List<ProdProductBranch> getProdBranchListAndOnline(Long productId,
			Long removeBranchId, Date visitTime, boolean checkOnline) {
		return this.prodProductService.getProductBranchDetailByProductId(productId,removeBranchId,visitTime,"false",checkOnline);
	}
	
	public List<CalendarModel> getSelfProductCalendarByBranchIdAndTime(Long prodBranchId, Date startTime, Date endTime) {
		CalendarUtilV2 calendarUtilV2 = new  SelfPackCalendarUtilV3(startTime, endTime);
		return calendarUtilV2.selectSaleTimePrice(prodBranchId);
	}


	@Override
	public boolean isSellable(Long prodBranchId, Long quantity, Date visitTime) {
 		return this.prodProductService.isSellable(prodBranchId, quantity, visitTime);
	}


	@Override
	public List<CalendarModel> selectSaleTimePriceByProductId(Long productId) {
		CalendarUtilV2 c = new CalendarUtilV2();
		return c.selectSaleTimePriceByProductId(productId);
	}


	@Override
	public List<CalendarModel> selectSaleTimePrice(Long prodBranchId) {
		CalendarUtilV2 c = new CalendarUtilV2();
		return c.selectSaleTimePrice(prodBranchId);
	}
	
	
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}


	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}


	public void setProdProductBranchLogic(
			ProdProductBranchLogic prodProductBranchLogic) {
		this.prodProductBranchLogic = prodProductBranchLogic;
	}

	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}


	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}


	public void setProdTrainCacheService(ProdTrainCacheService prodTrainCacheService) {
		this.prodTrainCacheService = prodTrainCacheService;
	}
 
	
}
