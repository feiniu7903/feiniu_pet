package com.lvmama.prd.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;

public class ProdAssemblyPointDAO extends BaseIbatisDAO {

    public ProdAssemblyPointDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long assemblyPointId) {
        ProdAssemblyPoint key = new ProdAssemblyPoint();
        key.setAssemblyPointId(assemblyPointId);
        int rows = super.delete("PROD_ASSEMBLY_POINT.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ProdAssemblyPoint record) {
        super.insert("PROD_ASSEMBLY_POINT.insert", record);
    }

    public void insertSelective(ProdAssemblyPoint record) {
        super.insert("PROD_ASSEMBLY_POINT.insertSelective", record);
    }

    public ProdAssemblyPoint selectByPrimaryKey(Long assemblyPointId) {
        ProdAssemblyPoint key = new ProdAssemblyPoint();
        key.setAssemblyPointId(assemblyPointId);
        ProdAssemblyPoint record = (ProdAssemblyPoint) super.queryForObject("PROD_ASSEMBLY_POINT.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ProdAssemblyPoint record) {
        int rows = super.update("PROD_ASSEMBLY_POINT.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProdAssemblyPoint record) {
        int rows = super.update("PROD_ASSEMBLY_POINT.updateByPrimaryKey", record);
        return rows;
    }
}