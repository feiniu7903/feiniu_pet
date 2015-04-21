package com.lvmama.client.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.client.ComClientLog;

public class ComClientLogDAO extends BaseIbatisDAO {

    public ComClientLogDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long logId) {
        ComClientLog key = new ComClientLog();
        key.setLogId(logId);
        int rows = super.delete("COM_CLIENT_LOG.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ComClientLog record) {
        Object newKey = super.insert("COM_CLIENT_LOG.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(ComClientLog record) {
        Object newKey = super.insert("COM_CLIENT_LOG.insertSelective", record);
        return (Long) newKey;
    }

    public ComClientLog selectByPrimaryKey(Long logId) {
        ComClientLog key = new ComClientLog();
        key.setLogId(logId);
        ComClientLog record = (ComClientLog) super.queryForObject("COM_CLIENT_LOG.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ComClientLog record) {
        int rows = super.update("COM_CLIENT_LOG.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ComClientLog record) {
        int rows = super.update("COM_CLIENT_LOG.updateByPrimaryKey", record);
        return rows;
    }
}