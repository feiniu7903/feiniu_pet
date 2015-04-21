package com.lvmama.prd.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdTrainFetchInfo;

public class ProdTrainFetchInfoDAO extends BaseIbatisDAO {

    public ProdTrainFetchInfoDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long prodTrainFetchInfoId) {
        ProdTrainFetchInfo key = new ProdTrainFetchInfo();
        key.setProdTrainFetchInfoId(prodTrainFetchInfoId);
        int rows =super.delete("PROD_TRAIN_FETCH_INFO.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ProdTrainFetchInfo record) {
        Object newKey =super.insert("PROD_TRAIN_FETCH_INFO.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(ProdTrainFetchInfo record) {
        Object newKey =super.insert("PROD_TRAIN_FETCH_INFO.insertSelective", record);
        return (Long) newKey;
    }

    public ProdTrainFetchInfo selectByPrimaryKey(Long prodTrainFetchInfoId) {
        ProdTrainFetchInfo key = new ProdTrainFetchInfo();
        key.setProdTrainFetchInfoId(prodTrainFetchInfoId);
        ProdTrainFetchInfo record = (ProdTrainFetchInfo)super.queryForObject("PROD_TRAIN_FETCH_INFO.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ProdTrainFetchInfo record) {
        int rows =super.update("PROD_TRAIN_FETCH_INFO.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProdTrainFetchInfo record) {
        int rows =super.update("PROD_TRAIN_FETCH_INFO.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<ProdTrainFetchInfo> selectFetchInfoList(){
    	return super.queryForList("PROD_TRAIN_FETCH_INFO.selectFetchInfoList");
    }
    
    public Long selectCountFetchInfo(String fetchKey,Date visitTime){
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("fetchKey", fetchKey);
    	map.put("visitTime", visitTime);
    	return (Long)super.queryForObject("PROD_TRAIN_FETCH_INFO.selectCountFetchInfo",map);
    }
}