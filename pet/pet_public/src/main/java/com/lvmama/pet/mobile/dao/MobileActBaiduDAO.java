package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileActBaidu;

public class MobileActBaiduDAO extends BaseIbatisDAO {

    public MobileActBaiduDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        MobileActBaidu key = new MobileActBaidu();
        key.setId(id);
        int rows = super.delete("MOBILE_ACT_BAIDU.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(MobileActBaidu record) {
        Object newKey = super.insert("MOBILE_ACT_BAIDU.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(MobileActBaidu record) {
        Object newKey = super.insert("MOBILE_ACT_BAIDU.insertSelective", record);
        return (Long) newKey;
    }

    public MobileActBaidu selectByPrimaryKey(Long id) {
        MobileActBaidu key = new MobileActBaidu();
        key.setId(id);
        MobileActBaidu record = (MobileActBaidu) super.queryForObject("MOBILE_ACT_BAIDU.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileActBaidu record) {
        int rows = super.update("MOBILE_ACT_BAIDU.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileActBaidu record) {
        int rows = super.update("MOBILE_ACT_BAIDU.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 库存加1
     * @param params
     * @return
     */
    public boolean updateAddQuantityByParams(Map<String,Object> params) {
        int rows = super.update("MOBILE_ACT_BAIDU.updateAddQuantityByParams", params);
        return rows>0;
    }
    
    /**
     * 库存减1
     * @param params
     * @return
     */
    public boolean updateMinusQuantityByParams(Map<String,Object> params) {
        int rows = super.update("MOBILE_ACT_BAIDU.updateMinusQuantityByParams", params);
        return rows>0;
    }

    @SuppressWarnings("unchecked")
	public List<MobileActBaidu> queryMobileActBaiduList(
			Map<String, Object> param) {
		return (List<MobileActBaidu>)super.queryForList("MOBILE_ACT_BAIDU.queryMobileActBaiduList", param);
    	
	}
    
}