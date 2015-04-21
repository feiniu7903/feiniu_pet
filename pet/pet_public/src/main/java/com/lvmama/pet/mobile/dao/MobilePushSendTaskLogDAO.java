package com.lvmama.pet.mobile.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobilePushSendTaskLog;

public class MobilePushSendTaskLogDAO extends BaseIbatisDAO {

    public MobilePushSendTaskLogDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long mobilePushSendTaskLogId) {
        MobilePushSendTaskLog key = new MobilePushSendTaskLog();
        key.setMobilePushSendTaskLogId(mobilePushSendTaskLogId);
        int rows = super.delete("MOBILE_PUSH_SEND_TASK_LOG.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(MobilePushSendTaskLog record) {
        super.insert("MOBILE_PUSH_SEND_TASK_LOG.insert", record);
    }

    public void insertSelective(MobilePushSendTaskLog record) {
        super.insert("MOBILE_PUSH_SEND_TASK_LOG.insertSelective", record);
    }

    public MobilePushSendTaskLog selectByPrimaryKey(Long mobilePushSendTaskLogId) {
        MobilePushSendTaskLog key = new MobilePushSendTaskLog();
        key.setMobilePushSendTaskLogId(mobilePushSendTaskLogId);
        MobilePushSendTaskLog record = (MobilePushSendTaskLog) super.queryForObject("MOBILE_PUSH_SEND_TASK_LOG.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobilePushSendTaskLog record) {
        int rows = super.update("MOBILE_PUSH_SEND_TASK_LOG.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobilePushSendTaskLog record) {
        int rows = super.update("MOBILE_PUSH_SEND_TASK_LOG.updateByPrimaryKey", record);
        return rows;
    }
}