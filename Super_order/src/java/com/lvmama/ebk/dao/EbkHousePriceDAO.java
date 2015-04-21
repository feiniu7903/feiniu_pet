package com.lvmama.ebk.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkHousePrice;

public class EbkHousePriceDAO extends BaseIbatisDAO {

    public int countByExample(Map<String, Object> example) {
        Integer count = (Integer)  super.queryForObject("EBK_HOUSE_PRICE.countByExample", example);
        return count.intValue();
    }

    public int deleteByPrimaryKey(Long housePriceId) {
        int rows = super.delete("EBK_HOUSE_PRICE.deleteByPrimaryKey", housePriceId);
        return rows;
    }

    public Long insert(EbkHousePrice record) {
    	super.insert("EBK_HOUSE_PRICE.insert", record);
    	return record.getHousePriceId();
    }

    @SuppressWarnings("unchecked")
	public List<EbkHousePrice> selectByExample(Map<String, Object> example) {
        List<EbkHousePrice> list = super.queryForList("EBK_HOUSE_PRICE.selectByExample", example);
        return list;
    }

    public EbkHousePrice selectByPrimaryKey(Long housePriceId) {
        EbkHousePrice record = (EbkHousePrice) super.queryForObject("EBK_HOUSE_PRICE.selectByPrimaryKey", housePriceId);
        return record;
    }

    public int updateByPrimaryKey(EbkHousePrice record) {
        int rows = super.update("EBK_HOUSE_PRICE.updateByPrimaryKeySelective", record);
        return rows;
    }

}