package com.lvmama.pet.ebkpush.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.ebkpush.EbkPushMessage;

public class EbkPushMessageDAO extends BaseIbatisDAO {

    public EbkPushMessageDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        EbkPushMessage key = new EbkPushMessage();
        key.setId(id);
        int rows = super.delete("EBK_PUSH_MESSAGE.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(EbkPushMessage record) {
        Object newKey = super.insert("EBK_PUSH_MESSAGE.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(EbkPushMessage record) {
        Object newKey = super.insert("EBK_PUSH_MESSAGE.insertSelective", record);
        return (Long) newKey;
    }

    public EbkPushMessage selectByPrimaryKey(Long id) {
        EbkPushMessage key = new EbkPushMessage();
        key.setId(id);
        EbkPushMessage record = (EbkPushMessage) super.queryForObject("EBK_PUSH_MESSAGE.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(EbkPushMessage record) {
        int rows = super.update("EBK_PUSH_MESSAGE.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(EbkPushMessage record) {
        int rows = super.update("EBK_PUSH_MESSAGE.updateByPrimaryKey", record);
        return rows;
    }
    
    public Long countTodayMsgByDeviceId(String udid){
    	return (Long) super.queryForObject("EBK_PUSH_MESSAGE.countTodyMsgByDeviceId",udid);
    }
    
    public Long getMessageIdSeq(){
    	return (Long) super.queryForObject("EBK_PUSH_MESSAGE.getMessageIdSeq");
    }
    
    public Long countTodyTimeOutMsgByDeviceId(String udid){
    	return (Long) super.queryForObject("EBK_PUSH_MESSAGE.countTodyTimeOutMsgByDeviceId",udid);
    }
    
    public List<EbkPushMessage> selectByParams(Map<String,Object> param){
    	return (List<EbkPushMessage>)super.queryForList("EBK_PUSH_MESSAGE.selectByParam", param);
    }
    
    public Long selectCountByParam(Map<String,Object> param){
    	return  (Long) super.queryForObject("EBK_PUSH_MESSAGE.selectCountByParam",param);
    }

	public List<EbkPushMessage> selectPushMessage(Map<String,String> params) {
		return (List<EbkPushMessage>)queryForList("EBK_PUSH_MESSAGE.selectPushMessage", params);
	}
	public int deleteHistoryDate(String udid) {
		return this.delete("EBK_PUSH_MESSAGE.deleteHistoryDate", udid);
	}
}