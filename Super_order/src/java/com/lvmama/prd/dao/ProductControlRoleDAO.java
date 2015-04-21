package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.meta.ProductControlRole;
import com.lvmama.comm.pet.po.perm.PermUser;

public class ProductControlRoleDAO extends BaseIbatisDAO {

	static final String NAME_SPACE = "PRODUCT_CONTROL_ROLE";
	
	@SuppressWarnings("unchecked")
	public List<ProductControlRole> getListByCondition(
			Map<String, Object> condition) {
		if (condition.get("_startRow")==null) {
			condition.put("_startRow", 0);
		}
		if (condition.get("_endRow")==null) {
			condition.put("_endRow", 20);
		}
		return (List<ProductControlRole>) super.queryForList(getStatementName("getListByCondition"), condition);
	}

	public Long countByCondition(Map<String, Object> condition) {
		return (Long) super.queryForObject(getStatementName("countByCondition"), condition);
	}
	
	public void save(ProductControlRole role) {
		super.insert(getStatementName("save"), role);
	}
	
	public ProductControlRole find(ProductControlRole role) {
		return (ProductControlRole) super.queryForObject(getStatementName("find"), role);
	}

	@SuppressWarnings("unchecked")
	public List<PermUser> getUserListByMap(Map<String, String> map) {
		return (List<PermUser>) super.queryForList(getStatementName("getUserListByMap"), map);
	}

	public void update(ProductControlRole role) {
		super.update(getStatementName("update"), role);
	}
	
	public void remove(ProductControlRole role) {
		super.delete(getStatementName("deleteControlByPrimaryKey"), role);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductControlRole> findListByUserId(ProductControlRole role) {
		return (List<ProductControlRole>) super.queryForList(getStatementName("findByUserId"), role);
	}
	
	private String getStatementName(String sqlId) {
		return NAME_SPACE + "." + sqlId;
	}

}