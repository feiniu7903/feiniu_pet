package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileActBaiduStock;

public class MobileActBaiduStockDAO extends BaseIbatisDAO {

    public MobileActBaiduStockDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        MobileActBaiduStock key = new MobileActBaiduStock();
        key.setId(id);
        int rows = super.delete("MOBILE_ACT_BAIDU_STOCK.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(MobileActBaiduStock record) {
        Object newKey = super.insert("MOBILE_ACT_BAIDU_STOCK.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(MobileActBaiduStock record) {
        Object newKey = super.insert("MOBILE_ACT_BAIDU_STOCK.insertSelective", record);
        return (Long) newKey;
    }

    public MobileActBaiduStock selectByPrimaryKey(Long id) {
        MobileActBaiduStock key = new MobileActBaiduStock();
        key.setId(id);
        MobileActBaiduStock record = (MobileActBaiduStock) super.queryForObject("MOBILE_ACT_BAIDU_STOCK.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileActBaiduStock record) {
        int rows = super.update("MOBILE_ACT_BAIDU_STOCK.updateByPrimaryKeySelective", record);
        return rows;
    }

    public boolean updateByPrimaryKey(MobileActBaiduStock record) {
        int rows = super.update("MOBILE_ACT_BAIDU_STOCK.updateByPrimaryKey", record);
        return rows>0;
    }
    
    /**
     * 库存加1
     * @param params
     * @return
     */
    public boolean updateAddQuantityByParams(Map<String,Object> params) {
        int rows = super.update("MOBILE_ACT_BAIDU_STOCK.updateAddQuantityByParams", params);
        return rows>0;
    }
    
    /**
     * 库存减1
     * @param params
     * @return
     */
    public boolean updateMinusQuantityByParams(Map<String,Object> params) {
        int rows = super.update("MOBILE_ACT_BAIDU_STOCK.updateMinusQuantityByParams", params);
        return rows>0;
    }

	public List<MobileActBaiduStock> queryMobileActBaiduStockList(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryForList("MOBILE_ACT_BAIDU_STOCK.queryMobileActBaiduStockList", param);
	}

}