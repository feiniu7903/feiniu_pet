package com.lvmama.comm.pet.service.fin;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.po.fin.FinSupplierAllot;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.vo.Page;

/**
 * 分单管理
 * @author zhangwenjun
 *
 */
@RemoteService("finSupplierAllotService")
public interface FinSupplierAllotService {
	/**
	 * 查询供应商预存款信息
	 * 
	 */
	Page<FinSupplierAllot> search(Map<String, Object> map);
	
	/**
	 * 查询供应商详情
	 * @param id
	 * @return
	 */
	FinSupplierMoney searchSupplier(Long id);
	
	/**
	 * 查询供应商结算对象
	 * @param id
	 * @return
	 */
	List<SupSettlementTarget> searchTarget(Long id);
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 */
	PermUser queryUser(String userName);

	/**
	 * 添加/修改供应商分单信息
	 * 
	 * @param fincAdvancedeposits
	 *            预存款信息
	 */
	void update(FinSupplierAllot finSupplierAllot);
	
	/**
	 * 删除供应商分单信息
	 * @param supplierId
	 */
	void delete(Long supplierId);
	
	/**
	 * 导出所有的分单信息
	 * @return
	 */
	List<FinSupplierAllot> exportAllot(Map<String, Object> map);
	
}
