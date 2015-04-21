package com.lvmama.pet.fin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.FinSupplierAllot;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.vo.Page;

/**
 * 分担管理
 * @author zhangwenjun
 *
 */
@Repository
public class FinSupplierAllotDAO extends BaseIbatisDAO {

	/**
	 * 查询供应商分单信息
	 * @return
	 */
	public Page<FinSupplierAllot> search(Map<String, Object> map) {
		return super.queryForPage("FIN_SUPPLIER_ALLOT.search", map);
	}

	/**
	 * 查询供应商详情
	 * @param id
	 * @return
	 */
	public FinSupplierMoney searchSupplier(Long id) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("id", id);
		return (FinSupplierMoney)super.queryForObject("FIN_SUPPLIER_ALLOT.searchSupplierDetail", map);
	}
	
	/**
	 * 查询查询供应商结算对象
	 * @param id
	 * @return
	 */
	public List<SupSettlementTarget> searchTarget(Long id) {
		Map map = new HashMap();
		map.put("id", id);
		List<SupSettlementTarget> targetList = super.queryForList("FIN_SUPPLIER_ALLOT.searchTarget", map);
		return targetList;
	}
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 */
	public PermUser queryUser(String userName){
		return (PermUser)super.queryForObject("FIN_SUPPLIER_ALLOT.queryUser", userName);
	}
	
	/**
	 * 根据供应商id查询供应商分单信息
	 * @param supplierId
	 * @return
	 */
	public FinSupplierAllot queryById(Long supplierId){
		return (FinSupplierAllot)super.queryForObject("FIN_SUPPLIER_ALLOT.queryById", supplierId);
	}
	
	/**
	 * 新增供应商分单信息
	 * @param finSupplierAllot
	 */
	public void add(FinSupplierAllot finSupplierAllot){
		super.insert("FIN_SUPPLIER_ALLOT.add", finSupplierAllot);
	}
	
	/**
	 * 更新供应商分单信息（指派时间和指派人）
	 * @param finSupplierAllot
	 */
	public void update(FinSupplierAllot finSupplierAllot){
		super.insert("FIN_SUPPLIER_ALLOT.update", finSupplierAllot);
	}
	
	/**
	 * 删除供应商分单信息
	 * @param supplierId
	 */
	public void delete(Long supplierId){
		super.delete("FIN_SUPPLIER_ALLOT.delete", supplierId);
	}

	/**
	 * 导出所有的分单信息
	 * @return
	 */
	public List<FinSupplierAllot> exportAllot(Map<String, Object> map) {
		return super.queryForListForReport("FIN_SUPPLIER_ALLOT.exportAllot", map);
	}

}