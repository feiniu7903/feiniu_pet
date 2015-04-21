package com.lvmama.ebk.dao;

import java.util.List;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkExtraProdConfig;

public class EbkExtraProdConfigDAO extends BaseIbatisDAO{

    public Long insertEbkExtraProdConfigDO(EbkExtraProdConfig ebkExtraProdConfigDO) {
        Object ID = super.insert("EBK_EXTRA_PROD_CONFIG.insert", ebkExtraProdConfigDO);
        return (Long) ID;
    }

    public Integer countEbkExtraProdConfigDOByTerm(EbkExtraProdConfig ebkExtraProdConfigDO) {
        Integer count = (Integer) super.queryForObject("EBK_EXTRA_PROD_CONFIG.countByDOTerm", ebkExtraProdConfigDO);
        return count;
    }

    public Integer updateEbkExtraProdConfigDO(EbkExtraProdConfig ebkExtraProdConfigDO) {
        int result = super.update("EBK_EXTRA_PROD_CONFIG.update", ebkExtraProdConfigDO);
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<EbkExtraProdConfig> findListByTerm(EbkExtraProdConfig ebkExtraProdConfigDO) {
        List<EbkExtraProdConfig> list = super.queryForList("EBK_EXTRA_PROD_CONFIG.findListByDO", ebkExtraProdConfigDO);
        return list;
    }

    public EbkExtraProdConfig findEbkExtraProdConfigDOByPrimaryKey(Long id) {
    	EbkExtraProdConfig ebkExtraProdConfigDO = (EbkExtraProdConfig) super.queryForObject("EBK_EXTRA_PROD_CONFIG.findByPrimaryKey", id);
        return ebkExtraProdConfigDO;
    }

    public Integer deleteEbkExtraProdConfigDOByPrimaryKey(Long id) {
        Integer rows = (Integer)super.delete("EBK_EXTRA_PROD_CONFIG.deleteByPrimaryKey", id);
        return rows;
    }

}