package com.lvmama.prd.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.LineStops;

public class LineStopsDAO extends BaseIbatisDAO {

    public LineStopsDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long lineStopsId) {
        LineStops key = new LineStops();
        key.setLineStopsId(lineStopsId);
        int rows = super.delete("LINE_STOPS.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(LineStops record) {
        Object newKey = super.insert("LINE_STOPS.insert", record);
        return (Long) newKey;
    }

    public LineStops selectByPrimaryKey(Long lineStopsId) {
        LineStops key = new LineStops();
        key.setLineStopsId(lineStopsId);
        LineStops record = (LineStops) super.queryForObject("LINE_STOPS.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKey(LineStops record) {
        int rows = super.update("LINE_STOPS.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 查询一个车次的某个版本，eq为true是指定的日期的版本，false时表示在指定日期之前的最后一个版本
     * @param lineInfoId
     * @param visitTime
     * @param eq
     * @return
     */
    public List<LineStops> selectByLineInfoId(final Long lineInfoId,Date visitTime,boolean eq){
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("lineInfoId", lineInfoId);
    	map.put("visitTime", visitTime);
    	if(eq){
    		map.put("visitTimeEq", visitTime);
    	}else{
    		map.put("visitTimeNotEq", visitTime);
    	}
    	return super.queryForList("LINE_STOPS.selectByLineInfoId",map);
    }
    
    public void deleteByVersionId(final Long versionId){
    	LineStops stop = new LineStops();
    	stop.setLineStopVersionId(versionId);
    	super.delete("LINE_STOPS.deleteByVersionId",stop);
    }

	@SuppressWarnings("unchecked")
	public List<LineStops> selectCheZhanStops(Map<String, Object> param) {
		return super.queryForListForReport("LINE_STOPS.selectCheZhanStops",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<LineStops> selectZhanZhanStops(Map<String, Object> param) {
		return super.queryForListForReport("LINE_STOPS.selectZhanZhanStops",param);
	}

	@SuppressWarnings("unchecked")
	public List<LineStops> selectLineStopsCheci(Map<String, Object> param) {
		return super.queryForList("LINE_STOPS.selectLineStopsCheci",param);
	}
	
}