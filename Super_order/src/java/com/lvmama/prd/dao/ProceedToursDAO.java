package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProceedTours;

public class ProceedToursDAO extends BaseIbatisDAO {
	/**
	 * 新增
	 * @param pt
	 */
	public void insert(final ProceedTours pt) {
		super.insert("PROCEED_TOURS.insert",pt);
	}
	
	/**
	 * 累加人数并更新
	 * @param pt
	 */
	public void additionVisitorsAndUpdate(final ProceedTours pt) {
		super.update("PROCEED_TOURS.addition",pt);
	}
	
	/**
	 * 减除人数并更新
	 * @param pt
	 */
	public void subtractionVisitorsAndUpdate(final ProceedTours pt) {
		super.update("PROCEED_TOURS.subtraction",pt);
	}
	
	/**
	 * 更新状态
	 * @param pt
	 */
	public void updateStatus(final ProceedTours pt) {
		super.update("PROCEED_TOURS.updateStatus",pt);
	}
	
	/**
	 * 查询
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProceedTours> query(final Map<String,Object> parameters) {
		return super.queryForList("PROCEED_TOURS.query", parameters);
	}
	
	public Long count(final Map<String,Object> parameters) {
		return (Long) super.queryForObject("PROCEED_TOURS.count", parameters);
	}
	
}
