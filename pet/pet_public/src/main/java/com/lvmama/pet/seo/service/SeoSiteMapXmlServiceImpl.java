package com.lvmama.pet.seo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.service.seo.SeoSiteMapXmlService;
import com.lvmama.pet.place.dao.PlaceDAO;
import com.lvmama.pet.search.dao.ProductSearchInfoDAO;

/**
 * @author zuozhengpeng
 *
 */
public class SeoSiteMapXmlServiceImpl implements SeoSiteMapXmlService {
	private PlaceDAO placeDAO;
	private ProductSearchInfoDAO productSearchInfoDAO;
	
	@Override
	public Long queryPlaceAllCount(String stage) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("isValid", "Y");
		param.put("stage", stage);
		return placeDAO.countPlaceList(param);
	}
	
	@Override
	public Map<String, Object> queryPlaceAllMap(int totalResultSize,int totalWrite, int xmlCouter, int resultCounter, int countPage,String stage) {
		Map<String, Object> placeAllMap =new HashMap<String, Object>();
		List<Place> placeList;
		long startRows=0;
		long endRows=0;
		for (int j = 1; j < totalWrite; j++) {
			for (int i = 1; i <= xmlCouter; i++) {
				placeList = new ArrayList<Place>();
				countPage = countPage + 1;
				
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("stage", stage);
				if(startRows==0){
					startRows=startRows(totalResultSize,resultCounter,countPage);
				}else{
					startRows=endRows+1;
				}
				 endRows=endRows(totalResultSize,resultCounter,countPage,startRows);
				
				if(startRows>endRows){
					break;
				}
				 
				param.put("startRows", startRows);
				param.put("endRows", endRows);
				placeList = placeDAO.queryPlaceAllMap(param);
				
				placeAllMap.put(totalWrite+"_"+resultCounter+"_"+countPage+"_"+stage+"_"+i, placeList);
			}
		}
		return placeAllMap;
	}

	@Override
	public Long queryPlaceProductAllCount() {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("isValid", "Y");
		return productSearchInfoDAO.countProductSearchInfoByParam(param);
	}

	@Override
	public Map<String, Object> queryPlaceProductAllMap(int totalResultSize,int totalWrite, int xmlCouter, int resultCounter, int countPage) {
		Map<String, Object> productIdAllMap =new HashMap<String, Object>();
		List<ProductSearchInfo> productIdList;
		long startRows=0;
		long endRows=0;
		for (int j = 1;j < totalWrite; j++) {
			productIdList = new ArrayList<ProductSearchInfo>();
			for (int i = 1; i <= xmlCouter; i++) {
				countPage = countPage + 1;
				
				Map<String, Object> param=new HashMap<String, Object>();
				param.put("isValid", "Y");
				if(startRows==0){
					startRows=startRows(totalResultSize,resultCounter,countPage);
				}else{
					startRows=endRows+1;
				}
				endRows=endRows(totalResultSize,resultCounter,countPage,startRows);
				
				if(startRows>endRows){
					break;
				}
				
				param.put("startRows", startRows);
				param.put("endRows", endRows);
				productIdList = (List<ProductSearchInfo>)productSearchInfoDAO.queryProductSearchInfoByParam(param);
				productIdAllMap.put(totalWrite+"_"+xmlCouter+"_"+resultCounter+"_"+countPage+"_"+i, productIdList);
			}
		}
		return productIdAllMap;
	}
	
	@Override
	public List<Place> queryPlacePage(Map<String, Object> param) {
		return placeDAO.queryPlaceAllMap(param);
	}
	
	@Override
	public List<ProductSearchInfo> queryProductPage(Map<String, Object> param) {
		return productSearchInfoDAO.queryProductSearchInfoByParam(param);
	}

	private long startRows(long totalResultSize,long pageSize,long currentPage){
		long startRows=(long) (currentPage - 1) * pageSize + 1;
		if(startRows>=totalResultSize){
			startRows=totalResultSize;
		}
		return startRows;
	}
	
	private long endRows(long totalResultSize,long pageSize,long currentPage,long startRows){
		long totalPageNum=0;
		if (totalResultSize % pageSize > 0) {
			totalPageNum= (long) totalResultSize / pageSize + 1;
		} else {
			totalPageNum=(long) totalResultSize / pageSize;
		}
		long endRows = 0;
		if(currentPage==totalPageNum){
			endRows = totalResultSize;
		}else {
			endRows = startRows+pageSize - 1;
		}
		if(endRows>=totalResultSize){
			endRows=totalResultSize;
		}
		return endRows;
	}

	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}

	public void setProductSearchInfoDAO(ProductSearchInfoDAO productSearchInfoDAO) {
		this.productSearchInfoDAO = productSearchInfoDAO;
	}

	

}
