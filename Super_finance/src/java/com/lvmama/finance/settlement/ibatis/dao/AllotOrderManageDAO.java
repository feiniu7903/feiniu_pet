package com.lvmama.finance.settlement.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.settlement.ibatis.po.AllotOrderManage;
import com.lvmama.finance.settlement.ibatis.po.FinSupplierAllot;
import com.lvmama.finance.settlement.ibatis.po.SettlementTarget;
import com.lvmama.finance.settlement.ibatis.po.SupplierInfo;
import com.lvmama.finance.settlement.ibatis.po.User;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;

@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AllotOrderManageDAO extends PageDao {

	/**
	 * 查询供应商分单信息
	 * @return
	 */
	public Page<SimpleSupplier> search() {
		return queryForPageFin("FINSUPPLIERALLOT.search", FinanceContext.getPageSearchContext().getContext());
	}
	
	/**
	 * 根据供应商id查询供应商分单信息
	 * @param supplierId
	 * @return
	 */
	public FinSupplierAllot queryById(Long supplierId){
		return (FinSupplierAllot)queryForObject("FINSUPPLIERALLOT.queryById", supplierId);
	}
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 */
	public User queryUser(String userName){
		return (User)queryForObject("FINSUPPLIERALLOT.queryUser", userName);
	}
	
	/**
	 * 新增供应商分单信息
	 * @param finSupplierAllot
	 */
	public void add(FinSupplierAllot finSupplierAllot){
		insert("FINSUPPLIERALLOT.add", finSupplierAllot);
	}
	
	/**
	 * 更新供应商分单信息（指派时间和指派人）
	 * @param finSupplierAllot
	 */
	public void update(FinSupplierAllot finSupplierAllot){
		insert("FINSUPPLIERALLOT.update", finSupplierAllot);
	}
	
	/**
	 * 删除供应商分单信息
	 * @param supplierId
	 */
	public void delete(Long supplierId){
		delete("FINSUPPLIERALLOT.delete", supplierId);
	}

	/**
	 * 导出所有的分单信息
	 * @return
	 */
	public List<AllotOrderManage> exportAllot() {
		return queryForListForReport("FINSUPPLIERALLOT.exportAllot", FinanceContext.getPageSearchContext().getContext());
	}

	/**
	 * 查询供应商详情
	 * @param id
	 * @return
	 */
	public SupplierInfo searchSupplier(Long id) {
		Map map = new HashMap();
		map.put("id", id);
		return (SupplierInfo)queryForObject("FINSUPPLIERALLOT.searchSupplier", map);
	}
	
	/**
	 * 查询查询供应商结算对象
	 * @param id
	 * @return
	 */
	public List<SettlementTarget> searchTarget(Long id) {
		Map map = new HashMap();
		map.put("id", id);
		List<SettlementTarget> targetList = queryForList("FINSUPPLIERALLOT.searchTarget", map);
		return targetList;
	}

}