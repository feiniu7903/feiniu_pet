package com.lvmama.pet.fin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinSupplierAllot;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.service.fin.FinanceSupplierAllotService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinanceSupplierAllotDAO;

/**
 * 分单管理
 * @author zhangwenjun
 *
 */
@Service("financeSupplierAllotService")
@HessianService("financeSupplierAllotService")
public class FinanceSupplierAllotServiceImpl extends BaseService implements FinanceSupplierAllotService {

	@Autowired
	private FinanceSupplierAllotDAO financeSupplierAllotDAO;
	
	@Override
	public Page<FinSupplierAllot> search(Map<String, Object> map) {
		return financeSupplierAllotDAO.search(map);
	}

	@Override
	public FinSupplierMoney searchSupplier(Long id) {
		return financeSupplierAllotDAO.searchSupplier(id);
	}

	@Override
	public List<SupSettlementTarget> searchTarget(Long id) {
		return financeSupplierAllotDAO.searchTarget(id);
	}

	@Override
	public PermUser queryUser(String userName) {
		return financeSupplierAllotDAO.queryUser(userName);
	}

	@Override
	public void update(FinSupplierAllot finSupplierAllot) {
		// 根据供应商id查询供应商分单信息
		FinSupplierAllot fsa = financeSupplierAllotDAO.queryById(finSupplierAllot.getSupplierId());
		
		/**
		 *  如果查询到的供应商分单信息为null，则表示是添加
		 *  	不为null，则表示是更新
		 */
		if(null == fsa){
			financeSupplierAllotDAO.add(finSupplierAllot);
			// 添加日志
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.FIN_SUPPLIER_ALLOT.name(), 
					null, finSupplierAllot.getSupplierId(), finSupplierAllot.getCreateUser(), 
					"添加指派人", "添加指派人", 
					"将供应商指派给：" + finSupplierAllot.getUserName(), null);
		} else {
			financeSupplierAllotDAO.update(finSupplierAllot);
			// 添加日志
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.FIN_SUPPLIER_ALLOT.name(), 
					null, finSupplierAllot.getSupplierId(), finSupplierAllot.getCreateUser(), 
					"修改指派人", "修改指派人", 
					"将供应商指派给：" + finSupplierAllot.getUserName(), null);
		}
	}

	@Override
	public void delete(Long supplierId) {
		// 根据供应商id查询供应商分单信息
		FinSupplierAllot finSupplierAllot = financeSupplierAllotDAO.queryById(supplierId);
		if(null != finSupplierAllot){
			// 根据供应商id删除供应商分单信息
			financeSupplierAllotDAO.delete(finSupplierAllot.getSupplierId());
			// 添加日志
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.FIN_SUPPLIER_ALLOT.name(), 
					null, finSupplierAllot.getSupplierId(), finSupplierAllot.getCreateUser(), 
					"取消指派人", "取消指派人", 
					"取消指派给：" + finSupplierAllot.getUserName(), null);
		}
	}

	@Override
	public List<FinSupplierAllot> exportAllot(Map<String, Object> map) {
		return financeSupplierAllotDAO.exportAllot(map);
	}

}
