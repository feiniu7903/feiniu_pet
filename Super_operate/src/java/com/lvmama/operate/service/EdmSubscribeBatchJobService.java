package com.lvmama.operate.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author likun
 * @date 2013/11/21
 */
import com.lvmama.comm.pet.po.edm.EdmSubscribeBatchJob;

/**
 * EDM邮件发送批次服务接口
 */

public interface EdmSubscribeBatchJobService {
	/**
	 * 添加
	 * 
	 * @param obj
	 * @return
	 */
	public EdmSubscribeBatchJob insert(EdmSubscribeBatchJob obj);

	/**
	 * 更新
	 * 
	 * @param obj
	 */
	public int update(EdmSubscribeBatchJob obj);

	/**
	 * 查询列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<EdmSubscribeBatchJob> getModelList(Map<Object, Object> paramMap);
	
	public Object searchBySql(String sql);
}
