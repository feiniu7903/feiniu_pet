package com.lvmama.com.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pub.ComJobContent;

public class ComJobContentDAO extends BaseIbatisDAO {

    public ComJobContentDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long comJobContentId) {
        ComJobContent key = new ComJobContent();
        key.setComJobContentId(comJobContentId);
        int rows = super.delete("COM_JOB_CONTENT.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ComJobContent record) {
        Object newKey = super.insert("COM_JOB_CONTENT.insert", record);
        return (Long) newKey;
    }
    
    
    public List<ComJobContent> selectList(Map<String,Object> params){
    	return super.queryForList("COM_JOB_CONTENT.selectList",params);
    }

   

    public ComJobContent selectByPrimaryKey(Long comJobContentId) {
        ComJobContent key = new ComJobContent();
        key.setComJobContentId(comJobContentId);
        ComJobContent record = (ComJobContent) super.queryForObject("COM_JOB_CONTENT.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ComJobContent record) {
        int rows = super.update("COM_JOB_CONTENT.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ComJobContent record) {
        int rows = super.update("COM_JOB_CONTENT.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<ComJobContent> selectByParams(Map<String,Object> params) {
    	return super.queryForList("COM_JOB_CONTENT.selectByParams",params);
    }
    
}