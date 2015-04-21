package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileVersion;

public class MobileVersionDAO extends BaseIbatisDAO {

    public MobileVersionDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        MobileVersion key = new MobileVersion();
        key.setId(id);
        int rows = super.delete("MOBILE_VERSION.deleteByPrimaryKey", key);
        return rows;
    }

    public MobileVersion insert(MobileVersion record) {
        super.insert("MOBILE_VERSION.insert", record);
        return record;
    }

    public Long insertSelective(MobileVersion record) {
        Object newKey = super.insert("MOBILE_VERSION.insertSelective", record);
        return (Long) newKey;
    }

    public MobileVersion selectByPrimaryKey(Long id) {
        MobileVersion key = new MobileVersion();
        key.setId(id);
        MobileVersion record = (MobileVersion) super.queryForObject("MOBILE_VERSION.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileVersion record) {
        int rows = super.update("MOBILE_VERSION.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileVersion record) {
        int rows = super.update("MOBILE_VERSION.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     *  更新审核状态. 
     * @param params  isAuditing   id 
     * @return int
     */
    public int updateAuditing(Map<String,Object> params) {
    	 int rows = super.update("MOBILE_VERSION.updateAuditing", params);
         return rows;
    }
    
    /**
     * 查询列表.  
     * @param params  
     * @return list
     */
    @SuppressWarnings("unchecked")
	public List<MobileVersion> queryMobileVersionList(Map<String,Object> params) {
    	if(null == params || params.isEmpty()) {
    		return null ;
    	}
    	return super.queryForList("MOBILE_VERSION.queryMobileVersionList", params);
    }
    
    /**
     * 查询记录数. 
     * @param param
     * @return
     */
    public Long countMobileVersionList(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_VERSION.countMobileVersionList", param);
    } 
}