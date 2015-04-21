package com.lvmama.operate.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.edm.EdmSubscribeBatchJob;

/**
 * EDM邮件发送批次信息持久类
 * 
 * @author likun
 * @createDate 2013/11/21
 */

public class EdmSubscribeBatchJobDAO extends BaseIbatisDAO {
	private static final String EDM_SUBSCRIBE_BATCH_SPACE = "EDM_SUBSCRIBE_BATCH_JOB.";
	private static final String INSERT = EDM_SUBSCRIBE_BATCH_SPACE + "insert";
	@SuppressWarnings("unused")
	private static final String DELETE = EDM_SUBSCRIBE_BATCH_SPACE + "delete";
	private static final String UPDATE = EDM_SUBSCRIBE_BATCH_SPACE + "update";
	@SuppressWarnings("unused")
	private static final String GETMODELLISTCOUNT = EDM_SUBSCRIBE_BATCH_SPACE
			+ "getModelListCount";
	private static final String GETMODELLIST = EDM_SUBSCRIBE_BATCH_SPACE
			+ "getModelList";

	/**
	 * 添加
	 * 
	 * @param obj
	 * @return
	 */
	public EdmSubscribeBatchJob insert(EdmSubscribeBatchJob obj) {
		insert(INSERT, obj);
		return obj;
	}

	/**
	 * 更新
	 * 
	 * @param obj
	 */
	public int update(EdmSubscribeBatchJob obj) {
		return update(UPDATE, obj);
	}

	/**
	 * 查询列表
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EdmSubscribeBatchJob> getModelList(Map<Object, Object> paramMap) {
		return queryForList(GETMODELLIST, paramMap);
	}

	public Object searchBySql(String sql) {
		return queryForList(EDM_SUBSCRIBE_BATCH_SPACE + "searchBySql", sql);
	}
}
