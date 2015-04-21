package com.lvmama.pet.fin.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.FinDeduction;

/**
 * 抵扣款流水DAO
 * 
 * @author yanggan
 * 
 */
@Repository
public class FinDeductionDAO extends BaseIbatisDAO {

	/**
	 * 新增抵扣款流水记录
	 * 
	 * @param fd
	 *            抵扣款流水记录
	 */
	public void insertFinDeduction(FinDeduction fd) {
		super.insert("FIN_DEDUCTION.insertFinDeduction",fd);
	}
	
	
	/**
	 * 查询抵扣款流水记录
	 * @param map
	 * @return
	 */
	public List<FinDeduction> searchRecord(Map<String, Object> map) {
		return super.queryForList("FIN_DEDUCTION.searchRecord", map);
	}
	
	/**
	 * 查询抵扣款流水记录条数
	 * @param map
	 * @return
	 */
	public Long searchRecordCount(Map<String, Object> map) {
		return (Long)super.queryForObject("FIN_DEDUCTION.searchRecordCount", map);
	}
	
}
