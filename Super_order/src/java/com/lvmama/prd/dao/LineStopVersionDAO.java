package com.lvmama.prd.dao;

import java.util.Date;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.LineStopVersion;

public class LineStopVersionDAO extends BaseIbatisDAO {

    public LineStopVersionDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long lineStopVersionId) {
        LineStopVersion key = new LineStopVersion();
        key.setLineStopVersionId(lineStopVersionId);
        int rows = super.delete("LINE_STOP_VERSION.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(LineStopVersion record) {
        Object newKey = super.insert("LINE_STOP_VERSION.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(LineStopVersion record) {
        Object newKey = super.insert("LINE_STOP_VERSION.insertSelective", record);
        return (Long) newKey;
    }

    public LineStopVersion selectByPrimaryKey(Long lineStopVersionId) {
        LineStopVersion key = new LineStopVersion();
        key.setLineStopVersionId(lineStopVersionId);
        LineStopVersion record = (LineStopVersion) super.queryForObject("LINE_STOP_VERSION.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(LineStopVersion record) {
        int rows = super.update("LINE_STOP_VERSION.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LineStopVersion record) {
        int rows = super.update("LINE_STOP_VERSION.updateByPrimaryKey", record);
        return rows;
    }
    
    public LineStopVersion selectByLineInfoIdValidTime(Long lineInfoId,Date validTime){
    	LineStopVersion version = new LineStopVersion();
    	version.setLineInfoId(lineInfoId);
    	version.setStartValidTime(validTime);
    	LineStopVersion record = (LineStopVersion) super.queryForObject("LINE_STOP_VERSION.selectByLineInfoIdValidTime", version);
        return record;
    }
}