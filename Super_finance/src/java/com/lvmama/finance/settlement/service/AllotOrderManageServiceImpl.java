package com.lvmama.finance.settlement.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.finance.BaseService;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.settlement.ibatis.dao.AllotOrderManageDAO;
import com.lvmama.finance.settlement.ibatis.po.AllotOrderManage;
import com.lvmama.finance.settlement.ibatis.po.FinSupplierAllot;
import com.lvmama.finance.settlement.ibatis.po.SettlementTarget;
import com.lvmama.finance.settlement.ibatis.po.SupplierInfo;
import com.lvmama.finance.settlement.ibatis.po.User;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;

@Service
public class AllotOrderManageServiceImpl extends BaseService implements AllotOrderManageService {

	private final String LOG_OBJECT_TYPE = "FIN_SUPPLIER_ALLOT";
	
	@Autowired
	private AllotOrderManageDAO allotOrderManageDAO;
	
	@Override
	public Page<SimpleSupplier> search() {
		return allotOrderManageDAO.search();
	}

	@Override
	public void update(Long supplierId, String userName) {
		// 根据供应商id查询供应商分单信息
		FinSupplierAllot finSupplierAllot = allotOrderManageDAO.queryById(supplierId);
		/**
		 *  如果查询到的供应商分单信息为null，则表示是添加
		 *  	不为null，则表示是更新
		 */
		if(null == finSupplierAllot){
			FinSupplierAllot supplierAllot = new FinSupplierAllot();
			// 供应商id
			supplierAllot.setSupplierId(supplierId);
			// 分配的用户名
			supplierAllot.setUserName(userName);
			// 获取当前登陆人的名称
			HttpSession session = FinanceContext.getSession();
			PermUser user = (PermUser) session.getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
			// 创建人姓名
			supplierAllot.setCreatorName(user.getUserName());
			// 指派时间
			allotOrderManageDAO.add(supplierAllot);// 添加日志
			this.log(supplierAllot.getSupplierId(), this.LOG_OBJECT_TYPE, "USER_NAME", "添加", "添加指派人" + supplierAllot.getUserName());
		} else {
			// 分配的用户名
			finSupplierAllot.setUserName(userName);
			allotOrderManageDAO.update(finSupplierAllot);// 添加日志
			this.log(supplierId, this.LOG_OBJECT_TYPE, "USER_NAME", "修改", "修改指派人" + finSupplierAllot.getUserName());
		}
	}

	@Override
	public void delete(Long supplierId) {
		// 根据供应商id查询供应商分单信息
		FinSupplierAllot finSupplierAllot = allotOrderManageDAO.queryById(supplierId);
		if(null != finSupplierAllot){
			// 根据供应商id删除供应商分单信息
			allotOrderManageDAO.delete(finSupplierAllot.getSupplierId());
			// 添加日志
			this.log(supplierId, this.LOG_OBJECT_TYPE, "USER_NAME", "删除", "删除指派人" + finSupplierAllot.getUserName());
		}
	}

	@Override
	public User queryUser(String userName) {
		return allotOrderManageDAO.queryUser(userName);
	}

	@Override
	public List<AllotOrderManage> exportAllot() {
		return allotOrderManageDAO.exportAllot();
	}

	@Override
	public SupplierInfo searchSupplier(Long id) {
		return allotOrderManageDAO.searchSupplier(id);
	}

	@Override
	public List<SettlementTarget> searchTarget(Long id) {
		return allotOrderManageDAO.searchTarget(id);
	}

}
