package com.lvmama.prd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.LineStationStation;

public class LineStationStationDAO extends BaseIbatisDAO {

    public LineStationStationDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long stationStationId) {
        LineStationStation key = new LineStationStation();
        key.setStationStationId(stationStationId);
        int rows = super.delete("LINE_STATION_STATION.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(LineStationStation record) {
        Object newKey = super.insert("LINE_STATION_STATION.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(LineStationStation record) {
        Object newKey = super.insert("LINE_STATION_STATION.insertSelective", record);
        return (Long) newKey;
    }

    public LineStationStation selectByPrimaryKey(Long stationStationId) {
        LineStationStation key = new LineStationStation();
        key.setStationStationId(stationStationId);
        LineStationStation record = (LineStationStation) super.queryForObject("LINE_STATION_STATION.selectByPrimaryKey", key);
        return record;
    }
    
    public LineStationStation selectByStationKey(String stationKey,String lineName){
    	LineStationStation record=new LineStationStation();
    	record.setStationKey(stationKey);
    	record.setLineName(lineName);
    	List<LineStationStation> list = super.queryForList("LINE_STATION_STATION.selectByStationKey",record);
    	if(!list.isEmpty()){
    		return list.get(0);
    	}
    	return null;
    }

    public int updateByPrimaryKeySelective(LineStationStation record) {
        int rows = super.update("LINE_STATION_STATION.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LineStationStation record) {
        int rows = super.update("LINE_STATION_STATION.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<LineStationStation> selectByLineInfoId(final Long lineInfoId){
    	return super.queryForList("LINE_STATION_STATION.selectByLineInfoId",lineInfoId);
    }
    
    @SuppressWarnings("unchecked")
	public List<LineStationStation> selectLineStationStationByPinyinKey(Map<String, Object> param){
    	return super.queryForList("LINE_STATION_STATION.selectLineStationStationByPinyinKey",param);
    }
}