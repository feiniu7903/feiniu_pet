package com.lvmama.search.lucene.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.ProductBranchBean;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.search.vo.VerPlaceBean;
@SuppressWarnings("unchecked")
@Repository 
public class IndexDAO extends BaseIbatisDAO {

	public List<PlaceBean> getPlace(String... placeId){
		Map<String,Object> paramMap = new HashMap<String,Object>(); 
		paramMap.put("beginRow", 0);    
		paramMap.put("endRow", 10000);  
		paramMap.put("placeId", placeId); 
		return this.queryForListForReport("createIndex.getPlaceIndexDate",paramMap);
	}
	
	public List<PlaceBean> getPlaceIndexDate(int beginRow, int endRow) {
		Map<String,Object> paramMap = new HashMap<String,Object>(); 
		paramMap.put("beginRow", beginRow);    
		paramMap.put("endRow", endRow);   
//		String id[]={"157994"};
//		paramMap.put("placeId", id); 
		return this.queryForListForReport("createIndex.getPlaceIndexDate", paramMap);
	}

	public Integer countPlaceIndex(){
		return  (Integer) this.queryForObject("createIndex.countPlaceIndex");
	}

	public List<PlaceHotelBean> getPlaceHotel(String... placeId){
		Map<String,Object> paramMap = new HashMap<String,Object>(); 
		paramMap.put("beginRow", 0);    
		paramMap.put("endRow", 10000);  
		paramMap.put("placeId", placeId); 
		return this.queryForListForReport("createIndex.getPlaceHotelIndexData",paramMap);
	}
	
	public List<PlaceHotelBean> getPlaceHotelIndexData(int beginRow,int endRow){
		Map<String,Object> paramMap = new HashMap<String,Object>(); 
		paramMap.put("beginRow", beginRow);    
		paramMap.put("endRow", endRow);    
		return this.queryForListForReport("createIndex.getPlaceHotelIndexData", paramMap);
	}
	
	public Integer countPlaceHotelIndex(){
		return  (Integer) this.queryForObject("createIndex.countPlaceHotelIndex");
	}
	
	public List<ProductBean> getProduct(String... productId) {
		Map<String,Object> paramMap = new HashMap<String,Object>(); 
		paramMap.put("beginRow", 0);    
		paramMap.put("endRow", 10000);  
		paramMap.put("productId", productId); 
		return this.queryForListForReport("createIndex.getProductIndexDate",paramMap);
	}
	public List<ProductBean> getProductIndexDate(int beginRow, int endRow) {
		Map<String,Object> paramMap = new HashMap<String,Object>(); 
		paramMap.put("beginRow", beginRow);    
		paramMap.put("endRow", endRow);    
//		String id[]={"80039"};
//		paramMap.put("placeId", id); 
		return this.queryForListForReport("createIndex.getProductIndexDate", paramMap);
	}
	
	public Integer countProductIndex() {
		return (Integer) this.queryForObject("createIndex.countProductIndex", null);
	}
	
	
	public List<ProductBranchBean> getProdBranch(String... prodBranchId) {
		Map<String,Object> paramMap = new HashMap<String,Object>(); 
		paramMap.put("beginRow", 0);    
		paramMap.put("endRow", 10000);  
		paramMap.put("prodBranchId", prodBranchId); 
		return this.queryForListForReport("createIndex.getProductBranchIndexDate",paramMap);
	}
	public List<ProductBranchBean> getProductBranchIndexDate(int beginRow, int endRow) {
		Map<String,Object> paramMap = new HashMap<String,Object>(); 
		paramMap.put("beginRow", beginRow);    
		paramMap.put("endRow", endRow);    
		return this.queryForListForReport("createIndex.getProductBranchIndexDate", paramMap);
	}

	public Integer countProductBranchIndex() {
		Integer count = (Integer) this.queryForObject("createIndex.countProductBranchIndex", null);
		return count;
	}
	public List<ProductBean> getProductMidType(HashMap hashMap) {
		return this.queryForListForReport("createIndex.getProductMidType",hashMap);
	}
/*
 * 获得产品中值
 */
	public ProductBean getMidproductBean(ProductBean paraBean) {
		
		return (ProductBean)this.queryForObject("createIndex.getMidproductBean",paraBean);
	}
	public List<PlaceBean> getPlaceMidType(HashMap hashMap) {
		return this.queryForListForReport("createIndex.getPlaceMidType",hashMap);
	}
	/*
	 * 获得place中值
	 */
	public PlaceBean getMidPlaceBean(PlaceBean placeBean) {
		return (PlaceBean)this.queryForObject("createIndex.getMidPlaceBean",placeBean);
	}

	public List<ProductBean> getAllProductTypeHasSale(HashMap hashMap) {
		return this.queryForListForReport("createIndex.getAllProductTypeHasSale",hashMap);
	}
	/*
	 * 获得没有销量的product的类型
	 */
	public List<ProductBean> getNoSaleProductType(HashMap hashMap) {
		return this.queryForListForReport("createIndex.getNoSaleProductType",hashMap);
	}


	
}
