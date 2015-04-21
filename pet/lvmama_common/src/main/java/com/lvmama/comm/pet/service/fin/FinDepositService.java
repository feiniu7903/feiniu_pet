package com.lvmama.comm.pet.service.fin;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.po.fin.FinDeposit;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.vo.Page;

/**
 * 供应商押金
 * 
 * @author zhangwenjun
 * 
 */
@RemoteService("finDepositService")
public interface FinDepositService {

	/**
	 * 查询供应商押金信息
	 * 
	 * @return
	 */
	Page<FinSupplierMoney> searchDeposit(Map<String,Object> map);
	
	/**
	 * 查询供应商担保函信息
	 * 
	 * @return
	 */
	Page<FinSupplierMoney> searchGuaranteeLimit(Map<String,Object> map);

	/**
	 * 增加押金
	 * 
	 * @param fincForegifts
	 */
	void addDeposit(FinDeposit fincDeposit);

	/**
	 * 查询供应商押金预警
	 * 
	 * @return
	 */
	Page<FinSupplierMoney> searchForegiftWarning(Map<String,Object> map);

	/**
	 * 查询流水记录
	 * 
	 * @param type
	 *            类型 押金/担保函
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	List<FinDeposit> searchForegiftRecord(Map<String,Object> map);
	Long searchForegiftRecordCount(Map<String,Object> map);

	/**
	 * 更新供应商币种
	 * @param supplierId
	 * @param currency
	 */
	void updateSupplierCurrency(FinDeposit fincDeposit);
//
//	/**
//	 * 查询押金金额
//	 * 
//	 * @param type
//	 *            类型 押金/担保函
//	 * @param supplierId
//	 *            供应商ID
//	 * @return
//	 */
//	Double searchAmount(String type, Long supplierId);

	/**
	 * 查询供应商的信息
	 * 
	 * @param supplierId
	 *            供应商ID
	 * @return
	 */
	FinSupplierMoney searchSupplier(Long supplierId);
}
