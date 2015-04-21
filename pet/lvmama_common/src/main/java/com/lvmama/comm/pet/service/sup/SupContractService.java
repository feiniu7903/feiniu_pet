package com.lvmama.comm.pet.service.sup;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupContractChange;
import com.lvmama.comm.pet.po.sup.SupContractFs;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

public interface SupContractService {

	/**
	 * 添加合同
	 * @param contract
	 */
	Long addContract(SupContract contract);
	
	/**
	 * 更新合同
	 * @param contract
	 */
	void updateContract(SupContract contract);
	
	/**
	 * 变更合同审核
	 * @param contractId
	 * @param status
	 * @param operatorName
	 * @return
	 */
	ResultHandle updateContractAudlt(Long contractId,Constant.CONTRACT_AUDIT status,String operatorName);
	
	/**
	 * 根据ID获取合同
	 * @param contractId
	 * @return 指定合同
	 */
	SupContract getContract(Long contractId);
	 
	/**
	 * 查询一个合同
	* @Title: getContractById
	* @Description:
	* @param
	* @return SupContract    返回类型
	* @throws
	 */
	 SupContract getContractById(Long contractId);
	/**
     * 修改是否有效状态
	 * @param id
     */
	 void deleteContract(Map params);
	 
	 /**
	  * insert合同变更
	  * @param supContractChange
	  */
	 long insertContractChange(SupContractChange supContractChange);
	 
	 /**
	  * 根据合同编号查询变更单
	  * */
	 List<SupContractChange> selectChangesByContractId(Long contractId);
	 Page<SupContract> getSupContractByParam(Map<String,Object> param, Long pageSize, Long currentPage);
	 /**根据id查询变更单*/
	 SupContractChange getSupContractChangeById(Long contractChangeId);
	 /**
	  * 
	  * @param contractId
	  * @return
	  */
	 List<SupContractFs> getSupContractFsByContractId(Long contractId);
	 SupContractFs insertSucContractFs(SupContractFs contractFs);
	 List<SupContract> selectContractBySupplierId(Long supplierId);
	 
	 /**
	  * update合同变更
	  * @param supContractChange
	  */
	 long updateContractChange(SupContractChange supContractChange);

	/**
	 * 取得合同快到期数据
	 * @param params
	 * @return
	 */
	List<SupContract> selectContractExpiredList(Map<String, Object> params);
}
