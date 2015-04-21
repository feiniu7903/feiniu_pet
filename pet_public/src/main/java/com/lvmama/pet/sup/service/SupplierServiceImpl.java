/**
 * 
 */
package com.lvmama.pet.sup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.po.sup.SupSupplierAptitude;
import com.lvmama.comm.pet.po.sup.SupSupplierAssess;
import com.lvmama.comm.pet.service.fin.FinSupplierMoneyService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.LogHelper;
import com.lvmama.comm.utils.LogObject;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.sup.dao.SupSupplierAptitudeDAO;
import com.lvmama.pet.sup.dao.SupSupplierAssessDAO;
import com.lvmama.pet.sup.dao.SupSupplierDAO;

/**
 * 供应商接口实现类
 * @author yangbin
 *
 */
public class SupplierServiceImpl extends BaseService implements SupplierService {

	private SupSupplierDAO supSupplierDAO;
	private FinSupplierMoneyService finSupplierMoneyService;
	private SupSupplierAptitudeDAO supSupplierAptitudeDAO;
	private SupSupplierAssessDAO supSupplierAssessDAO;
	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.sup.SupplierService#addSupplier(com.lvmama.comm.pet.po.sup.SupSupplier, java.lang.String)
	 */
	@Override
	public Long addSupplier(SupSupplier supplier, String operatorName) {
		Long id = supSupplierDAO.insert(supplier);
		if(supplier.getAdvancedpositsAlert()!=null || supplier.getForegiftsAlert()!=null){
			FinSupplierMoney finSupplierMoney = new FinSupplierMoney();
			finSupplierMoney.setSupplierId(supplier.getSupplierId());
			finSupplierMoney.setAdvanceDepositAlert(supplier.getAdvancedpositsAlert());
			finSupplierMoney.setDepositAlert(supplier.getForegiftsAlert());
			finSupplierMoneyService.updateSupplierMoney(finSupplierMoney);
		}
		return id;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.sup.SupplierService#getSupplier(java.lang.Long)
	 */
	@Override
	public SupSupplier getSupplier(Long supplierId) {
		SupSupplier supplier = supSupplierDAO.selectByPrimaryKey(supplierId);
		supplier.setChildSupplierList(supSupplierDAO.getChildSuppliers(supplierId));
		supplier.setParentSupplier(supSupplierDAO.selectByPrimaryKey(supplier.getParentId()));
		return supplier;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.sup.SupplierService#getSupSuppliers(java.util.Map)
	 */
	@Override
	public List<SupSupplier> getSupSuppliers(Map param) {
		return supSupplierDAO.getSupSuppliers(param);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.sup.SupplierService#updateSupplier(com.lvmama.comm.pet.po.sup.SupSupplier, java.lang.String)
	 */
	@Override
	public void updateSupplier(SupSupplier ss, String operatorName) {
		supSupplierDAO.updateByPrimaryKey(ss);
		if(ss.getAdvancedpositsAlert()!=null || ss.getForegiftsAlert()!=null){
			FinSupplierMoney finSupplierMoney = new FinSupplierMoney();
			finSupplierMoney.setSupplierId(ss.getSupplierId());
			finSupplierMoney.setAdvanceDepositAlert(ss.getAdvancedpositsAlert());
			finSupplierMoney.setDepositAlert(ss.getForegiftsAlert());
			finSupplierMoneyService.updateSupplierMoney(finSupplierMoney);
		}
	}
	 
	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.sup.SupplierService#deleteSupplier(java.util.Map)
	 */
	@Override
	public void deleteSupplier(Map params) {
		supSupplierDAO.markIsValid(params);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.sup.SupplierService#selectRowCount(java.util.Map)
	 */
	@Override
	public Long selectRowCount(Map searchConds) {
		return supSupplierDAO.selectRowCount(searchConds);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.sup.SupplierService#getSupplierForParent(java.lang.String)
	 */
	@Override
	public List<SupSupplier> getSupplierForParent(String supplierName) {
		return supSupplierDAO.getSupplierForParent(supplierName);
	}

	public void setSupSupplierDAO(SupSupplierDAO supSupplierDAO) {
		this.supSupplierDAO = supSupplierDAO;
	}

	public void setSupSupplierAptitudeDAO(
			SupSupplierAptitudeDAO supSupplierAptitudeDAO) {
		this.supSupplierAptitudeDAO = supSupplierAptitudeDAO;
	}

	public void setSupSupplierAssessDAO(SupSupplierAssessDAO supSupplierAssessDAO) {
		this.supSupplierAssessDAO = supSupplierAssessDAO;
	}

	@Override
	public void insertSupSupplierAptitude(
			SupSupplierAptitude supSupplierAptitude,
			String operatorName) {
		SupSupplierAptitude ssa=null;
		if(supSupplierAptitude.getSupplierAptitudeId()==null){
			ssa=supSupplierAptitudeDAO.selectBySupplierId(supSupplierAptitude.getSupplierId());
			if(ssa!=null){
				supSupplierAptitude.setSupplierAptitudeId(ssa.getSupplierAptitudeId());				
			}
		}else{
			ssa=supSupplierAptitudeDAO.selectByPrimaryKey(supSupplierAptitude.getSupplierAptitudeId());
		}
		
		LogHelper<SupSupplierAptitude> log;
		if(supSupplierAptitude.getSupplierAptitudeId()!=null){
			this.supSupplierAptitudeDAO.updateByPrimaryKey(supSupplierAptitude);
			log=new LogHelper<SupSupplierAptitude>(SupSupplierAptitude.class,ssa,supSupplierAptitude);
		}else{
			this.supSupplierAptitudeDAO.insert(supSupplierAptitude);
			log=new LogHelper<SupSupplierAptitude>(SupSupplierAptitude.class,supSupplierAptitude);
		}
		
		String content=LogObject.makeSupplierAptitudeLog(log);
		insertLog("SUP_SUPPLIER_APTITUDE", supSupplierAptitude.getSupplierId(), 
				supSupplierAptitude.getSupplierAptitudeId(), operatorName, 
				Constant.COM_LOG_SUPPLIER_EVENT.updateSupAptitude.name(), 
				"更新供应商资质",content, "SUP_SUPPLIER");
	}

	@Override
	public void insertSupSupplierAssess(SupSupplierAssess supSupplierAssess) {
		this.supSupplierAssessDAO.insert(supSupplierAssess);
		SupSupplier ss=getSupplier(supSupplierAssess.getSupplierId());
		ss.setAddAssessPoint(supSupplierAssess.getAssessPoints());
		supSupplierDAO.updateByPrimaryKey(ss);
		insertLog("SUP_SUPPLIER_ASSESS", ss.getSupplierId(), supSupplierAssess.getSupplierAssessId(),
				supSupplierAssess.getOperatorName(), Constant.COM_LOG_SUPPLIER_EVENT.updateSupAssess.name(), "供应商资质考核",
				"加分说明:"+supSupplierAssess.getAssessMemo()+", 分数:"+supSupplierAssess.getAssessPoints(), "SUP_SUPPLIER");
	}

	@Override
	public SupSupplierAptitude getSupplierAptitudeBySupplierId(Long supplierId) {
		return supSupplierAptitudeDAO.selectBySupplierId(supplierId);
	}
	
	public SupSupplierAptitude getSupplierAptitudeByPrimaryKey(Long pk){
		return supSupplierAptitudeDAO.selectByPrimaryKey(pk);
	}

	@Override
	public List<SupSupplierAssess> selectSupplierAssessBySupplierId(
			Long supplierId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		return this.supSupplierAssessDAO.selectByParam(map);
	}

	@Override
	public Map<Long,SupSupplier> getSupSupplierBySupplierId(List<Long> supplierId) {
		List<SupSupplier> supSupplierList = this.supSupplierDAO.getSupSupplierBySupplierId(supplierId);
		Map<Long, SupSupplier> map = new HashMap<Long, SupSupplier>();
		for (int i = 0; i < supSupplierList.size(); i++) {
			SupSupplier supplier = supSupplierList.get(i);
			map.put(supplier.getSupplierId(), supplier);
		}
		return map;
	}

	@Override
	public Page<SupSupplier> getSupSupplierByParam(Map param, Long pageSize,
			Long currentPage) {
		return this.supSupplierDAO.getSupSupplierByParam(param, pageSize, currentPage);
	}

	@Override
	public List<SupSupplier> getSupplierByParam(Map<String, Object> param) {
		return this.supSupplierDAO.getSupplierByParam(param);
	}

	public void setFinSupplierMoneyService(FinSupplierMoneyService finSupplierMoneyService) {
		this.finSupplierMoneyService = finSupplierMoneyService;
	}


}
