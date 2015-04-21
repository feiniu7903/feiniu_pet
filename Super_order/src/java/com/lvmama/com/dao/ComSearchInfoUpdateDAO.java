package com.lvmama.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;

/**
 * 
 * 批量更新PRODUCT_SEARCH_INFO,PROD_BRANCH_SEARCH_INFO,PLACE_SEARCH_INFO
 * 
 * @author yanggan
 * 
 */
public class ComSearchInfoUpdateDAO extends BaseIbatisDAO {

	public void syncProductSearchInfo() {
		this.update("COM_SEARCH_INFO_UPDATE.syncProductSearchInfo");
	}
	
	public void syncProdBranchSearchInfo() {
		this.update("COM_SEARCH_INFO_UPDATE.syncProdBranchSearchInfo");
	}
	
	public void syncPlaceSearchInfo() {
		this.update("COM_SEARCH_INFO_UPDATE.syncPlaceSearchInfo");
	}
	
	/**
	 * 查询已经更新完成的ID
	 * 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<Long> searchUpdatedId(String updateType) {
		return queryForList("COM_SEARCH_INFO_UPDATE.searchUpdatedId",updateType);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> searchUpdatedIdExtCol(String updateType) {
		return queryForList("COM_SEARCH_INFO_UPDATE.searchUpdatedIdExtCol",updateType);
	}
	/**
	 * 删除已更新完成的ID
	 * @param updateType
	 */
	public void deleteUpdated(String updateType){
		this.delete("COM_SEARCH_INFO_UPDATE.deleteUpdated",updateType);
	}

	public void insert(Long updateId, String updateType) {
		this.insert(updateId, updateType, null);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insert(Long updateId, String updateType,String extCol) {
		Map map = new HashMap();
		map.put("updateId",updateId);
		map.put("updateType",updateType);
		map.put("extCol",extCol);
		this.insert("COM_SEARCH_INFO_UPDATE.insert",map);
	}
}
