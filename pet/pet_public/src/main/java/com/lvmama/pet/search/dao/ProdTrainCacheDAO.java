package com.lvmama.pet.search.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.search.ProdTrainCache;

public class ProdTrainCacheDAO extends BaseIbatisDAO {

    public ProdTrainCacheDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long timePriceId) {
        ProdTrainCache key = new ProdTrainCache();
        key.setTimePriceId(timePriceId);
        int rows = super.delete("PROD_TRAIN_CACHE.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ProdTrainCache record) {
        Object newKey = super.insert("PROD_TRAIN_CACHE.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(ProdTrainCache record) {
        Object newKey = super.insert("PROD_TRAIN_CACHE.insertSelective", record);
        return (Long) newKey;
    }
    
    public void updatePrice(ProdTrainCache record){
    	super.update("PROD_TRAIN_CACHE.updatePrice",record);
    }

    public ProdTrainCache selectByPrimaryKey(Long timePriceId) {
        ProdTrainCache key = new ProdTrainCache();
        key.setTimePriceId(timePriceId);
        ProdTrainCache record = (ProdTrainCache) super.queryForObject("PROD_TRAIN_CACHE.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ProdTrainCache record) {
        int rows = super.update("PROD_TRAIN_CACHE.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProdTrainCache record) {
        int rows = super.update("PROD_TRAIN_CACHE.updateByPrimaryKey", record);
        return rows;
    }
    
    public int removeNotValidTrains(Date date){
    	return super.delete("PROD_TRAIN_CACHE.removeNotValidTrains", date);
    }
    
    public void markSoldout(final Long prodBranchId,Date date){
    	ProdTrainCache cache = new ProdTrainCache();
    	cache.setSoldout("true");
    	cache.setProdBranchId(prodBranchId);
    	cache.setVisitTime(date);
    	super.update("PROD_TRAIN_CACHE.makeSoldout",cache);
    }
    
    
    public Long selectCountCache(Map<String,Object> map){
    	return (Long)super.queryForObject("PROD_TRAIN_CACHE.selectCountCache",map);
    }
    
    public List<ProdTrainCache> selectByParam(Map<String,Object> param){
    	return super.queryForList("PROD_TRAIN_CACHE.selectByParam",param);
    }
    
    public ProdTrainCache selectByBranchIdAndVisitTime(final Long prodBranchId,Date visitTime){
    	Map<String,Object> param = new HashMap<String, Object>();
    	param.put("prodBranchId", prodBranchId);
    	param.put("visitTime", visitTime);
    	return (ProdTrainCache)super.queryForObject("PROD_TRAIN_CACHE.selectByBranchIdAndVisitTime",param);
    }
    
    public void copyData(Date oldDate,Date newDate){
    	Map<String,Date> param = new HashMap<String, Date>();
    	param.put("visitTime", oldDate);
    	param.put("newVisitTime", newDate);
    	super.insert("PROD_TRAIN_CACHE.copyData",param);
    }

	public ProdTrainCache selectByCache(ProdTrainCache cache) {
		// TODO Auto-generated method stub
		return (ProdTrainCache)super.queryForObject("PROD_TRAIN_CACHE.selectByCache", cache);
	}
	
	public Long selectCount(ProdTrainCache cache){
		return (Long)super.queryForObject("PROD_TRAIN_CACHE.selectCount", cache);
	}
	
	public long selectCountByDate(Date date){
		return (Long)super.queryForObject("PROD_TRAIN_CACHE.selectCountByDate",date);
	}
	
	public ProdTrainCache selectLastCache(Date date){
		return (ProdTrainCache)super.queryForObject("PROD_TRAIN_CACHE.selectLastCache",date);
	}
}