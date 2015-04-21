package com.lvmama.comm.pet.service.fin;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.po.fin.FinDeduction;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.vo.Page;

/**
 * @author zhangwenjun
 */
@RemoteService("finDeductionService")
public interface FinDeductionService {
	
	Page<FinSupplierMoney> search(Map<String, Object> map);
	
	Integer updateDeduction(FinDeduction finDeduction);
	
	List<FinDeduction> searchRecord(Map<String, Object> map);
	
	Long searchRecordCount(Map<String, Object> map);
	
	void insertFinDeduction(FinDeduction finDeduction);
}
