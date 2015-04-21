package com.lvmama.ebk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkFaxSend;
import com.lvmama.comm.bee.po.ebooking.EbkTask;

public class EbkFaxSendDAO extends BaseIbatisDAO {

    public int deleteByPrimaryKey(Long ebkFaxSendId) {
        EbkFaxSend key = new EbkFaxSend();
        key.setEbkFaxSendId(ebkFaxSendId);
        int rows = super.delete("EBK_FAX_SEND.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(EbkFaxSend record) {
        Object newKey = super.insert("EBK_FAX_SEND.insert", record);
        return (Long) newKey;
    }
    public Long insertRepair(EbkFaxSend record) {
        Object newKey = super.insert("EBK_FAX_SEND.insertRepair", record);
        return (Long) newKey;
    }
    public Long insertSelective(EbkFaxSend record) {
        Object newKey = super.insert("EBK_FAX_SEND.insertSelective", record);
        return (Long) newKey;
    }

    public EbkFaxSend selectByPrimaryKey(Long ebkFaxSendId) {
    	return (EbkFaxSend) super.queryForObject("EBK_FAX_SEND.selectByPrimaryKey", ebkFaxSendId);
    }
    
    public Integer getEbkFaxSendCountByParams(Map<String,Object> params) {
    	Integer record = (Integer) super.queryForObject("EBK_FAX_SEND.getEbkFaxSendCountByParams", params);
    	return record;
    }
    
    @SuppressWarnings("unchecked")
	public List<EbkFaxSend> queryEbkFaxSendByParams(Map<String,Object> params) {
    	return (List<EbkFaxSend>) super.queryForList("EBK_FAX_SEND.queryEbkFaxSendByParams", params);
    }

    public int updateByPrimaryKeySelective(EbkFaxSend record) {
        int rows = super.update("EBK_FAX_SEND.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(EbkFaxSend record) {
        int rows = super.update("EBK_FAX_SEND.updateByPrimaryKey", record);
        return rows;
    }
}