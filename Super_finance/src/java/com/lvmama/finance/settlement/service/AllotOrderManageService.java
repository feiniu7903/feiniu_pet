package com.lvmama.finance.settlement.service;

import java.util.List;

import com.lvmama.finance.base.Page;
import com.lvmama.finance.settlement.ibatis.po.AllotOrderManage;
import com.lvmama.finance.settlement.ibatis.po.SettlementTarget;
import com.lvmama.finance.settlement.ibatis.po.SupplierInfo;
import com.lvmama.finance.settlement.ibatis.po.User;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;

/**
 * 预存管理Service
 * 
 * @author yanggan
 * 
 */
public interface AllotOrderManageService {
	/**
	 * 查询供应商预存款信息
	 * 
	 * @return
	 */
	Page<SimpleSupplier> search();
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 */
	User queryUser(String userName);

	/**
	 * 添加/修改供应商分单信息
	 * 
	 * @param fincAdvancedeposits
	 *            预存款信息
	 */
	void update(Long supplierId, String userName);
	
	/**
	 * 删除供应商分单信息
	 * @param supplierId
	 */
	void delete(Long supplierId);
	
	/**
	 * 导出所有的分单信息
	 * @return
	 */
	List<AllotOrderManage> exportAllot();
	
	/**
	 * 查询供应商详情
	 * @param id
	 * @return
	 */
	SupplierInfo searchSupplier(Long id);
	
	/**
	 * 查询供应商结算对象
	 * @param id
	 * @return
	 */
	List<SettlementTarget> searchTarget(Long id);
	
}
