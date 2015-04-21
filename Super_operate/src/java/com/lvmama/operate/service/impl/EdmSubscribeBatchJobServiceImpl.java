package com.lvmama.operate.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeBatchJob;
import com.lvmama.operate.dao.EdmSubscribeBatchJobDAO;
import com.lvmama.operate.service.EdmSubscribeBatchJobService;

/**
 * 
 * @author likun
 * @date 2013/11/21
 */
public class EdmSubscribeBatchJobServiceImpl implements
		EdmSubscribeBatchJobService {

	/**
	 * 添加
	 * 
	 * @param obj
	 * @return
	 */
	public EdmSubscribeBatchJob insert(EdmSubscribeBatchJob obj) {
		return this.edmSubscribeBatchJobDAO.insert(obj);
	}

	/**
	 * 更新
	 * 
	 * @param obj
	 */
	public int update(EdmSubscribeBatchJob obj) {
		return this.edmSubscribeBatchJobDAO.update(obj);
	}

	/**
	 * 查询列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<EdmSubscribeBatchJob> getModelList(Map<Object, Object> paramMap) {
		return this.edmSubscribeBatchJobDAO.getModelList(paramMap);
	}
	public Object searchBySql(String sql){
		return this.edmSubscribeBatchJobDAO.searchBySql(sql);
	}
	
	private EdmSubscribeBatchJobDAO edmSubscribeBatchJobDAO;

	public EdmSubscribeBatchJobDAO getEdmSubscribeBatchJobDAO() {
		return edmSubscribeBatchJobDAO;
	}

	public void setEdmSubscribeBatchJobDAO(
			EdmSubscribeBatchJobDAO edmSubscribeBatchJobDAO) {
		this.edmSubscribeBatchJobDAO = edmSubscribeBatchJobDAO;
	}

}
