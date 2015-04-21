/**
 * 
 */
package com.lvmama.pet.sup.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupContractChange;
import com.lvmama.comm.pet.po.sup.SupContractFs;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.CONTRACT_AUDIT;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.sup.dao.SupContractChangeDAO;
import com.lvmama.pet.sup.dao.SupContractDAO;
import com.lvmama.pet.sup.dao.SupContractFsDAO;
import com.lvmama.pet.sup.dao.SupSupplierDAO;

/**
 * @author yangbin
 *
 */
public class SupContractServiceImpl extends BaseService implements SupContractService{

	private SupContractDAO supContractDAO;
	private SupContractChangeDAO supContractChangeDAO;
	private SupSupplierDAO supSupplierDAO;
	private SupContractFsDAO supContractFsDAO;
	
	

	@Override
	public ResultHandle updateContractAudlt(Long contractId,
			CONTRACT_AUDIT status, String operatorName) {
		Assert.notNull(contractId);
		Assert.notNull(status);		
		ResultHandle handle=new ResultHandle();
		SupContract contractEntity = supContractDAO.selectByPrimaryKey(contractId);
		if(contractEntity==null){
			handle.setMsg("合同不存在");
			return handle;
		}
		if(status.equals(Constant.CONTRACT_AUDIT.PASS)){
			if(Constant.CONTRACT_AUDIT.PASS.name().equals(contractEntity.getContractAudit())){
				handle.setMsg("当前的审核状态不可以再审核");
				return handle;
			}	
			
			List<SupContractFs> contractFs = this.supContractFsDAO.getSupContractFsByContractId(contractEntity.getContractId());
			if(contractFs.isEmpty() || contractFs.size() <= 0){
				handle.setMsg("纸质合同没有提交,不可以操作");
				return handle;
			}
		}
		contractEntity.setContractAudit(status.name());		
		supContractDAO.updateByPrimaryKey(contractEntity);
		
		insertLog("SUP_CONTRACT", null, contractId, operatorName, Constant.COM_LOG_SUPPLIER_EVENT.updateContractAudit.name(), 
				"更改合同审核状态", "变更合同审核状态 为："+status.getCnName(), null);		
		return handle;		
	}

	@Override
	public List<SupContractFs> getSupContractFsByContractId(Long contractId) {
		return supContractFsDAO.getSupContractFsByContractId(contractId);
	}

	@Override
	public SupContractFs insertSucContractFs(SupContractFs contractFs) {
		Long id = supContractFsDAO.insert(contractFs);
		contractFs.setSupContractFsId(id);
		return contractFs;
	}
	@Override
	public Long addContract(SupContract contract) {
		SupSupplier ss = supSupplierDAO.selectByPrimaryKey(contract.getSupplierId());		
		contract.setContractName(DateUtil.formatDate(contract.getSignDate(), "yyyyMMdd") + ss.getSupplierName() + contract.getZhContractType());
		return supContractDAO.insert(contract);
	}

	@Override
	public void updateContract(SupContract contract) {
		supContractDAO.updateByPrimaryKey(contract);
	}

	@Override
	public SupContract getContract(Long contractId) {
		return supContractDAO.selectByPrimaryKey(contractId);
	}
 
	@Override
	public SupContract getContractById(Long contractId) {
		return getContract(contractId);
	}

	@Override
	public void deleteContract(Map params) {
		supContractDAO.markIsValid(params);
	}
 
	@Override
	public long insertContractChange(SupContractChange supContractChange) {
		return supContractChangeDAO.insert(supContractChange);
	}

	public void setSupContractChangeDAO(SupContractChangeDAO supContractChangeDAO) {
		this.supContractChangeDAO = supContractChangeDAO;
	}

	public void setSupContractDAO(SupContractDAO supContractDAO) {
		this.supContractDAO = supContractDAO;
	}

	@Override
	public List<SupContractChange> selectChangesByContractId(Long contractId) {
		return supContractChangeDAO.selectByContractId(contractId);
	}

	@Override
	public Page<SupContract> getSupContractByParam(Map<String,Object> param,
			Long pageSize, Long currentPage) {
		return this.supContractDAO.getSupContractByParam(param, pageSize, currentPage);
	}

	@Override
	public SupContractChange getSupContractChangeById(Long contractChangeId) {
		return supContractChangeDAO.selectByPrimaryKey(contractChangeId);
	}

	public void setSupSupplierDAO(SupSupplierDAO supSupplierDAO) {
		this.supSupplierDAO = supSupplierDAO;
	}

	public void setSupContractFsDAO(SupContractFsDAO supContractFsDAO) {
		this.supContractFsDAO = supContractFsDAO;
	}

	@Override
	public List<SupContract> selectContractBySupplierId(Long supplierId) {
		if(supplierId==null){
			return Collections.emptyList();
		}
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("supplierId", supplierId);
		return supContractDAO.getSupContracts(param);
	}

	@Override
	public long updateContractChange(SupContractChange supContractChange) {
		return supContractChangeDAO.updateFsIdByContractChangeId(supContractChange);
	}

	/**
	 * 取得合同快到期数据
	 * @param params
	 * @return
	 */
	@Override
	public List<SupContract> selectContractExpiredList(Map<String, Object> params){
		return supContractDAO.selectContractExpiredList(params);
	}
}
