package com.lvmama.prd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.LineStation;

public class LineStationDAO extends BaseIbatisDAO {

    public LineStationDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long stationId) {
        LineStation key = new LineStation();
        key.setStationId(stationId);
        int rows = super.delete("LINE_STATION.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(LineStation record) {
        Object newKey = super.insert("LINE_STATION.insert", record);
        return (Long) newKey;
    }
    
    public LineStation selectByPrimaryKey(Long stationId) {
        LineStation key = new LineStation();
        key.setStationId(stationId);
        LineStation record = (LineStation) super.queryForObject("LINE_STATION.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(LineStation record) {
        int rows = super.update("LINE_STATION.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LineStation record) {
        int rows = super.update("LINE_STATION.updateByPrimaryKey", record);
        return rows;
    }
    
    public LineStation selectByNameAndPinyin(String name, String pinyin){
    	LineStation record = new LineStation();
    	record.setStationName(name);
    	if(pinyin != null)
    		record.setStationPinyin(pinyin);
    	List<LineStation> list = super.queryForList("LINE_STATION.selectByNameAndPinyin", record);
    	if(list.isEmpty()){
    		return null;
    	}
    	return list.get(0);
    }
    
    public Integer selectCountByPinyin(String pinyin) {
    	LineStation record = new LineStation();
    	record.setOldStationPinyin(pinyin);
    	return (Integer) super.queryForObject("LINE_STATION.selectCountByPinyin", record);
    }
    
    public List<LineStation> selectAll(){
    	return super.queryForListForReport("LINE_STATION.selectAll");
    }
    
    public List<LineStation> selectLineStationByParam(Map<String,Object> param){
    	return super.queryForList("LINE_STATION.selectLineStationByParam",param);
    }
    
    public List<LineStation> selectStationByParam(Map<String,Object> param){
    	return super.queryForListForReport("LINE_STATION.selectStationByParam",param);
    }
    
    public List<LineStation> selectLineStationByLineInfoId(final Long lineInfoId){
    	return super.queryForList("LINE_STATION.selectLineStationByLineInfoId",lineInfoId);
    }
    
    public LineStation getLineStationByStationPinyin(final String pinyin){
    	LineStation station = new LineStation();
    	station.setStationPinyin(pinyin);
    	return (LineStation) super.queryForObject("LINE_STATION.getLineStationByStationPinyin",station);
    }

	public Map<String, LineStation> getLineStationByNames(String name) {
		// TODO Auto-generated method stub
		List<LineStation> lineStations = super.queryForList("LINE_STATION.getLineStationByNames", name);
		Map<String, LineStation> result = null;
		for(LineStation lineStation : lineStations){
			if(result == null)
				result = new HashMap<String, LineStation>();
			result.put(lineStation.getStationName(), lineStation);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<LineStation> selectLineStationByChezhan(Map<String, Object> param){
    	return super.queryForList("LINE_STATION.selectLineStationByChezhan",param);
    }
	
}