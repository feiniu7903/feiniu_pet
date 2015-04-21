package com.lvmama.comm.pet.service.prod;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.ant.service.AntProdProductService;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.service.prod.BeeProdProductService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.prod.ProdProductHead;

public class ProductServiceProxy implements ProductHeadQueryService{
	private AntProdProductService antProdProductService;
	private PetProdProductService petProdProductService;
	private BeeProdProductService beeProdProductService;
	private ProdProductHeadService prodProductHeadService;
	 
	@Override
	public ProdProductBranch getProdBranchDetailByProdBranchId(
			Long prodBranchId, Date visitTime) {
		return beeProdProductService.getProdBranchDetailByProdBranchId(prodBranchId, visitTime);
	}

	public Date selectNearBranchTimePriceByBranchId(Long prodBranchId,Date beginDay) {
		return beeProdProductService.selectNearBranchTimePriceByBranchId(prodBranchId,beginDay);
	}
	@Override
	public List<CalendarModel> getProductCalendarByProductId(Long productId) {
		return beeProdProductService.getProductCalendarByProductId(productId);
	}

	@Override
	public List<CalendarModel> getProductCalendarByBranchId(Long branchId) {
		return beeProdProductService.getProductCalendarByBranchId(branchId);
	}
	
	public List<CalendarModel> getSelfProductCalendarByBranchIdAndDay(Long branchId, Date today) {
		return beeProdProductService.getSelfProductCalendarByBranchIdAndDay(branchId,today);
	}

	@Override
	public ProdProductHead getProdProductHeadByProductId(Long productId) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean isSellProductByChannel(Long productId, String channel) {
		return beeProdProductService.isSellProductByChannel(productId, channel);
	}

	@Override
	public Date getProductsLastCancelTime(List<Long> branchIdList,
			Date visitTime) {
		return this.beeProdProductService.getProductsLastCancelTime(branchIdList, visitTime);
	}

	@Override
	public List<ProdAssemblyPoint> getAssemblyPoints(Long productId) {
		return this.beeProdProductService.getAssemblyPoints(productId);
	}

	@Override
	public List<ProdProductBranch> getProdBranchList(Long productId,
			Long removeBranchId, Date visitTime) {
		return this.beeProdProductService.getProdBranchList(productId, removeBranchId, visitTime);
	}

	@Override
	public List<ProdProductRelation> getRelatProduct(Long productId,
			Date visitTime) {
		return this.beeProdProductService.getRelatProduct(productId, visitTime);
	}

	@Override
	public ProdProductBranch getProdBranchDetailByProdBranchId(
			Long prodBranchId, Date visitTime, boolean checkOnline) {
		return this.beeProdProductService.getProdBranchDetailByProdBranchId(prodBranchId, visitTime, checkOnline);
	}

	@Override
	public List<ProdProductBranch> getProdBranchListAndOnline(Long productId,
			Long removeBranchId, Date visitTime, boolean checkOnline) {
		 
		return this.beeProdProductService.getProdBranchListAndOnline(productId,removeBranchId,visitTime,checkOnline);
	}

	@Override
	public boolean isSellable(Long prodBranchId, Long quantity, Date visitTime) {
		return this.beeProdProductService.isSellable(prodBranchId, quantity, visitTime);
	}
	
	@Override
	public List<CalendarModel> getSelfProductCalendarByBranchIdAndTime(
			Long branchId, Date startTime, Date endTime) {
		return this.beeProdProductService.getSelfProductCalendarByBranchIdAndTime(branchId, startTime, endTime);
	}
	
	public void setAntProdProductService(AntProdProductService antProdProductService) {
		this.antProdProductService = antProdProductService;
	}

	public void setPetProdProductService(PetProdProductService petProdProductService) {
		this.petProdProductService = petProdProductService;
	}

	public void setBeeProdProductService(BeeProdProductService beeProdProductService) {
		this.beeProdProductService = beeProdProductService;
	}

	public void setProdProductHeadService(
			ProdProductHeadService prodProductHeadService) {
		this.prodProductHeadService = prodProductHeadService;
	}
}
