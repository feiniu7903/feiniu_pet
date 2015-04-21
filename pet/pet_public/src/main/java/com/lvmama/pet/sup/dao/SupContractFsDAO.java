package com.lvmama.pet.sup.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.SupContractFs;

/**
 * 合同
 * @author yuzhibing
 *
 */
public class SupContractFsDAO extends BaseIbatisDAO {

	public Long insert(SupContractFs record) {
		Long key = (Long) super.insert("SUP_CONTRACT_FS.insert", record);
		return key;
	}
	public List<SupContractFs> getSupContractFsByContractId(Long contractId){
		return super.queryForList("SUP_CONTRACT_FS.getSupContractFsByContractId",contractId);
	}
}