package com.lvmama.pet.fin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.pet.po.fin.FinSupplierAllot;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.service.fin.FinSupplierAllotService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinSupplierAllotDAO;

/**
 * 分单管理
 * @author zhangwenjun
 *
 */
@Service("finSupplierAllotService")
public class FinSupplierAllotServiceImpl extends BaseService implements FinSupplierAllotService {

	@Autowired
	private FinSupplierAllotDAO finSupplierAllotDAO;
	
	@Override
	public Page<FinSupplierAllot> search(Map<String, Object> map) {
		return finSupplierAllotDAO.search(map);
	}

	@Override
	public FinSupplierMoney searchSupplier(Long id) {
		return finSupplierAllotDAO.searchSupplier(id);
	}

	@Override
	public List<SupSettlementTarget> searchTarget(Long id) {
		return finSupplierAllotDAO.searchTarget(id);
	}

	@Override
	public PermUser queryUser(String userName) {
		return finSupplierAllotDAO.queryUser(userName);
	}

	@Override
	public void update(FinSupplierAllot finSupplierAllot) {
		// 根据供应商id查询供应商分单信息
		FinSupplierAllot fsa = finSupplierAllotDAO.queryById(finSupplierAllot.getSupplierId());
		
		/**
		 *  如果查询到的供应商分单信息为null，则表示是添加
		 *  	不为null，则表示是更新
		 */
		if(null == fsa){
			finSupplierAllotDAO.add(finSupplierAllot);
			// 添加日志
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.FIN_SUPPLIER_ALLOT.name(), 
					null, finSupplierAllot.getSupplierId(), finSupplierAllot.getCreateUser(), 
					"添加指派人", "添加指派人", 
					"将供应商指派给：" + finSupplierAllot.getUserName(), null);
		} else {
			finSupplierAllotDAO.update(finSupplierAllot);
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
		FinSupplierAllot finSupplierAllot = finSupplierAllotDAO.queryById(supplierId);
		if(null != finSupplierAllot){
			// 根据供应商id删除供应商分单信息
			finSupplierAllotDAO.delete(finSupplierAllot.getSupplierId());
			// 添加日志
			super.insertLog(Constant.COM_LOG_OBJECT_TYPE.FIN_SUPPLIER_ALLOT.name(), 
					null, finSupplierAllot.getSupplierId(), finSupplierAllot.getCreateUser(), 
					"取消指派人", "取消指派人", 
					"取消指派给：" + finSupplierAllot.getUserName(), null);
		}
	}

	@Override
	public List<FinSupplierAllot> exportAllot(Map<String, Object> map) {
		return finSupplierAllotDAO.exportAllot(map);
	}

}
