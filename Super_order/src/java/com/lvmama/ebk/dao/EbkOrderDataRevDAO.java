package com.lvmama.ebk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;

public class EbkOrderDataRevDAO extends BaseIbatisDAO {

    public int deleteByPrimaryKey(Long dataId) {
        EbkOrderDataRev key = new EbkOrderDataRev();
        key.setDataId(dataId);
        int rows = super.delete("EBK_ORDER_DATA_REV.deleteByPrimaryKey", key);
        return rows;
    }
    
    public List<EbkOrderDataRev> queryEbkOrderDataRevList(Map<String,Object> params){
    	return super.queryForList("EBK_ORDER_DATA_REV.queryEbkOrderDataRevList",params);
    }

    public Long insert(EbkOrderDataRev record) {
        Object newKey = super.insert("EBK_ORDER_DATA_REV.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(EbkOrderDataRev record) {
        Object newKey = super.insert("EBK_ORDER_DATA_REV.insertSelective", record);
        return (Long) newKey;
    }

    public EbkOrderDataRev selectByPrimaryKey(Long dataId) {
        EbkOrderDataRev key = new EbkOrderDataRev();
        key.setDataId(dataId);
        EbkOrderDataRev record = (EbkOrderDataRev) super.queryForObject("EBK_ORDER_DATA_REV.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(EbkOrderDataRev record) {
        int rows = super.update("EBK_ORDER_DATA_REV.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(EbkOrderDataRev record) {
        int rows = super.update("EBK_ORDER_DATA_REV.updateByPrimaryKey", record);
        return rows;
    }
    
    public void deleteAllByEbkCertificateId(final Long ebkCertificateId){
    	super.delete("EBK_ORDER_DATA_REV.deleteAllByEbkCertificateId",ebkCertificateId);
    }
    public void deleteTicketAllByEbkCertificateId(final Long ebkCertificateId,final Long orderId){
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("orderId", orderId);
    	param.put("ebkCertificateId", ebkCertificateId);
    	super.delete("EBK_ORDER_DATA_REV.deleteTicketAllByEbkCertificateId",param);
    }
    
}