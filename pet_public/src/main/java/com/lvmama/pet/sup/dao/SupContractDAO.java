package com.lvmama.pet.sup.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.vo.Page;

/**
 * 合同
 * @author yuzhibing
 *
 */
public class SupContractDAO extends BaseIbatisDAO {

	public Long insert(SupContract record) {
		Long key = (Long) super.insert("SUP_CONTRACT.insert", record);
		return key;
	}
	
	public SupContract selectByPrimaryKey(Long contractId) {
		SupContract key = new SupContract();
		key.setContractId(contractId);
		SupContract record = (SupContract) super.queryForObject("SUP_CONTRACT.selectByPrimaryKey", key);
		return record;
	}

	public int updateByPrimaryKey(SupContract record) {
		int rows = super.update("SUP_CONTRACT.updateByPrimaryKey", record);
		return rows;
	}

	public List<SupContract> getSupContracts(Map param) {
		if (param.get("_startRow")==null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow")==null) {
			param.put("_endRow", 20);
		}
		return super.queryForList("SUP_CONTRACT.selectContract", param);
	}
	
	public void markIsValid(Map params) {
		super.update("SUP_CONTRACT.markIsValid", params);
	}
	
	public Integer selectRowCount(Map searchConds){
		Integer count = 0;
		count = (Integer) super.queryForObject("SUP_CONTRACT.selectRowCount",searchConds);
		return count;
	}
	
	public Page<SupContract> getSupContractByParam(Map<String,Object> param, Long pageSize, Long currentPage){
		Integer count = 0;
		count = (Integer) super.queryForObject("SUP_CONTRACT.selectRowCount",param);
		Page<SupContract> page = Page.page(count, pageSize, currentPage);
		if(page != null && count > 0){
			param.put("_startRow", page.getStartRows());
			param.put("_endRow", page.getEndRows());
			List<SupContract> list = super.queryForList("SUP_CONTRACT.selectContract", param);
			page.setItems(list);
		}
		return page;
	}
	/**
	 * 取得合同快到期数据
	 * @param params
	 * @return
	 */
	public List<SupContract> selectContractExpiredList(Map<String, Object> params){
		return super.queryForList("SUP_CONTRACT.selectContractExpiredList",params);
	}
}