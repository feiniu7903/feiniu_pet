package com.lvmama.pet.sup.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sup.FinAccountingEntity;

public class FinAccountingEntityDAO extends BaseIbatisDAO {

    public FinAccountingEntityDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long accountingEntityId) {
        FinAccountingEntity key = new FinAccountingEntity();
        key.setAccountingEntityId(accountingEntityId);
        int rows = super.delete("FIN_ACCOUNTING_ENTITY.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(FinAccountingEntity record) {
        Object newKey = super.insert("FIN_ACCOUNTING_ENTITY.insert", record);
        return (Long) newKey;
    }
 
    public FinAccountingEntity selectByPrimaryKey(Long accountingEntityId) {
        FinAccountingEntity key = new FinAccountingEntity();
        key.setAccountingEntityId(accountingEntityId);
        FinAccountingEntity record = (FinAccountingEntity) super.queryForObject("FIN_ACCOUNTING_ENTITY.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(FinAccountingEntity record) {
        int rows = super.update("FIN_ACCOUNTING_ENTITY.updateByPrimaryKeySelective", record);
        return rows;
    }
    
    public List<FinAccountingEntity> selectEntityList() {
        return super.queryForList("FIN_ACCOUNTING_ENTITY.selectEntityList");
    }
 
}