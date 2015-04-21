package com.lvmama.insurance.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.ins.InsInsurant;

public class InsInsurantDAO extends BaseIbatisDAO {
	public InsInsurant insert(final InsInsurant insurant) {
		super.insert("INS_INSURANT.insert", insurant);
		return insurant;
	}
	
	public void update(final InsInsurant insurant) {
		super.update("INS_INSURANT.update", insurant);	
	}
	
	public void delete(final Map<String,Object> parameters) {
		super.delete("INS_INSURANT.delete", parameters);
	}
	
	public InsInsurant queryInsInsurantByPK(final Serializable id) {
		return (InsInsurant) super.queryForObject("INS_INSURANT.queryByPK", id);
	}
	
	/**
	 * 查询保单，并返回List
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InsInsurant> queryInsInsurant(final Map<String,Object> parameters) {
		return super.queryForList("INS_INSURANT.query", parameters);
	}
	
	/**
	 * 查询保单数量
	 * @param parameters
	 * @return
	 */
	public Long countInsInsurant(final Map<String,Object> parameters) {
		return (Long) super.queryForObject("INS_INSURANT.count",parameters);
	}

}
