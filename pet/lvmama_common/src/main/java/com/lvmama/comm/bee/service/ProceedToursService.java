package com.lvmama.comm.bee.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProceedTours;

public interface ProceedToursService {
	
	/**
	 * 添加并更新
	 * @param pt
	 */
	void addition(ProceedTours pt);
	
	/**
	 * 减除并更新
	 * @param pt
	 */
	void subtraction(ProceedTours pt);
	
	/**
	 * 更新状态
	 * @param pt
	 */
	void updateStatus(ProceedTours pt);
	
	/**
	 * 计数
	 * @param parameters
	 */
	Long countProceedTours(Map<String,Object> parameters);
	
	/**
	 * 查询
	 * @param parameters
	 * @return
	 */
	List<ProceedTours> query(Map<String, Object> parameters);
}
