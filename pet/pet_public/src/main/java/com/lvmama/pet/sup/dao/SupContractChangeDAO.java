package com.lvmama.pet.sup.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.SupContractChange;

public class SupContractChangeDAO extends BaseIbatisDAO {

    public SupContractChangeDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long contractChangeId) {
        SupContractChange key = new SupContractChange();
        key.setContractChangeId(contractChangeId);
        int rows = super.delete("SUP_CONTRACT_CHANGE.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(SupContractChange record) {
        Object newKey = super.insert("SUP_CONTRACT_CHANGE.insert", record);
        return (Long) newKey;
    }
 
    public SupContractChange selectByPrimaryKey(Long contractChangeId) {
        SupContractChange key = new SupContractChange();
        key.setContractChangeId(contractChangeId);
        SupContractChange record = (SupContractChange) super.queryForObject("SUP_CONTRACT_CHANGE.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(SupContractChange record) {
        int rows = super.update("SUP_CONTRACT_CHANGE.updateByPrimaryKeySelective", record);
        return rows;
    }
    
    public List<SupContractChange> selectByContractId(Long contractId) {
    	return super.queryForList("SUP_CONTRACT_CHANGE.selectByContractId", contractId);
    }
    
    public int updateFsIdByContractChangeId(SupContractChange supContractChange) {
    	 int rows = super.update("SUP_CONTRACT_CHANGE.updateFsIdByContractChangeId", supContractChange);
         return rows;
     }
  
}