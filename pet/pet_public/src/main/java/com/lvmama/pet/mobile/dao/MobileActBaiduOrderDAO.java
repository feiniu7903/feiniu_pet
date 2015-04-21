package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileActBaiduOrder;

public class MobileActBaiduOrderDAO extends BaseIbatisDAO {

    public MobileActBaiduOrderDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        MobileActBaiduOrder key = new MobileActBaiduOrder();
        key.setId(id);
        int rows = super.delete("MOBILE_ACT_BAIDU_ORDER.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(MobileActBaiduOrder record) {
        Object newKey = super.insert("MOBILE_ACT_BAIDU_ORDER.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(MobileActBaiduOrder record) {
        Object newKey = super.insert("MOBILE_ACT_BAIDU_ORDER.insertSelective", record);
        return (Long) newKey;
    }

    public MobileActBaiduOrder selectByPrimaryKey(Long id) {
        MobileActBaiduOrder key = new MobileActBaiduOrder();
        key.setId(id);
        MobileActBaiduOrder record = (MobileActBaiduOrder) super.queryForObject("MOBILE_ACT_BAIDU_ORDER.selectByPrimaryKey", key);
        return record;
    }
    
    public int updateByPrimaryKeySelective(MobileActBaiduOrder record) {
        int rows = super.update("MOBILE_ACT_BAIDU_ORDER.updateByPrimaryKeySelective", record);
        return rows;
    }
    
    public int deleteByUserId(String lvUserId,Long productid) {
    	  MobileActBaiduOrder key = new MobileActBaiduOrder();
          key.setLvUserid(lvUserId);
          key.setProductid(productid);
          int rows = super.delete("MOBILE_ACT_BAIDU_ORDER.deleteByUserId", key);
          return rows;
    }

    public int updateByPrimaryKey(MobileActBaiduOrder record) {
        int rows = super.update("MOBILE_ACT_BAIDU_ORDER.updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	public List<MobileActBaiduOrder> queryMobileActBaiduOrderList(
			Map<String, Object> param) {
		return (List<MobileActBaiduOrder>)super.queryForList("MOBILE_ACT_BAIDU_ORDER.queryMobileActBaiduOrderList", param);
    	
	}

    public Long countMobileActBaiduOrder(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_ACT_BAIDU_ORDER.countMobileActBaiduOrderList", param);
    }
}