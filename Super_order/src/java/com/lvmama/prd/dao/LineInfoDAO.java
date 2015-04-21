package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.LineInfo;

public class LineInfoDAO extends BaseIbatisDAO {

    public LineInfoDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long lineInfoId) {
        LineInfo key = new LineInfo();
        key.setLineInfoId(lineInfoId);
        int rows = super.delete("LINE_INFO.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(LineInfo record) {
        Object newKey = super.insert("LINE_INFO.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(LineInfo record) {
        Object newKey = super.insert("LINE_INFO.insertSelective", record);
        return (Long) newKey;
    }

    public LineInfo selectByPrimaryKey(Long lineInfoId) {
        LineInfo key = new LineInfo();
        key.setLineInfoId(lineInfoId);
        LineInfo record = (LineInfo) super.queryForObject("LINE_INFO.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(LineInfo record) {
        int rows = super.update("LINE_INFO.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(LineInfo record) {
        int rows = super.update("LINE_INFO.updateByPrimaryKey", record);
        return rows;
    }
    
    public LineInfo selectByLineName(String key){
    	return selectByFullName(key);
    }
    
    public LineInfo selectLineInfoByFullName(String fullName){
    	LineInfo info = new LineInfo();
    	info.setFullName(fullName);
    	return (LineInfo) super.queryForObject("LINE_INFO.selectLineInfoByFullName",info);
    }
    
    @SuppressWarnings("unchecked")
	public List<LineInfo> selectAll(){
    	return super.queryForListForReport("LINE_INFO.selectAll");
    }
    
    @SuppressWarnings("unchecked")
	public List<LineInfo> selectAll(Map<String, Object> param){
    	return super.queryForListForReport("LINE_INFO.selectAll",param);
    }
  
    
    public LineInfo selectByFullName(String fullName){
    	LineInfo info = new LineInfo();
    	info.setFullName(fullName);
    	List<LineInfo> list = super.queryForList("LINE_INFO.selectByFullName", info);
    	if(list.isEmpty()){
    		return null;
    	}
        return list.get(0);
    }
    
    @SuppressWarnings("unchecked")
	public List<LineInfo> selectCheZhan(Map<String, Object> param){
    	return super.queryForListForReport("LINE_INFO.selectCheZhan",param);
    }
    
	public Long selectCheZhanCount(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return (Long) super.queryForObject("LINE_INFO.selectCheZhanCount",param);
	}
}