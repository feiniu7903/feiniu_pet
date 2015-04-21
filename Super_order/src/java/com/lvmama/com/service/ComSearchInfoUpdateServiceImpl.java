package com.lvmama.com.service;

import java.util.List;

import com.lvmama.com.dao.ComSearchInfoUpdateDAO;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;

public class ComSearchInfoUpdateServiceImpl implements ComSearchInfoUpdateService {
	
	private ComSearchInfoUpdateDAO comSearchInfoUpdateDAO;
	
	private void insert(Long updateId, String updateType) {
		comSearchInfoUpdateDAO.insert(updateId,updateType);
	}
	@Override
	public void syncProductSearchInfo(){
		comSearchInfoUpdateDAO.syncProductSearchInfo();
	}
	
	@Override
	public void syncPlaceSearchInfo() {
		comSearchInfoUpdateDAO.syncPlaceSearchInfo();
		
	}
	@Override
	public void syncProdBranchSearchInfo(){
		comSearchInfoUpdateDAO.syncProdBranchSearchInfo();
	}
	public List<Long> deleteUpdated(String updateType){
		List<Long> list = comSearchInfoUpdateDAO.searchUpdatedId(updateType);
		comSearchInfoUpdateDAO.deleteUpdated(updateType);
		return list;
	}
	public List<String> deleteUpdatedWithExtCol(String updateType){
		List<String> list = comSearchInfoUpdateDAO.searchUpdatedIdExtCol(updateType);
		comSearchInfoUpdateDAO.deleteUpdated(updateType);
		return list;
	}
	public void setComSearchInfoUpdateDAO(ComSearchInfoUpdateDAO comSearchInfoUpdateDAO) {
		this.comSearchInfoUpdateDAO = comSearchInfoUpdateDAO;
	}

	@Override
	public void placeUpdated(Long placeId,String stage) {
		comSearchInfoUpdateDAO.insert(placeId,"PLACE_SEARCH_INFO",stage);
	}

	@Override
	public void productUpdated(Long productId) {
		this.insert(productId, "PRODUCT_SEARCH_INFO");
	}

	@Override
	public void productBranchUpdated(Long prodBranchId) {
		this.insert(prodBranchId, "PROD_BRANCH_SEARCH_INFO");
	}
	@Override
	public void verHotelUpdated(Long verHotelId) {
		// TODO Auto-generated method stub
		
	}

	

	
}
