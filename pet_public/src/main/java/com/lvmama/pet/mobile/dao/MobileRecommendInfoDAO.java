package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileRecommendInfo;

public class MobileRecommendInfoDAO extends BaseIbatisDAO {

    public MobileRecommendInfoDAO() {
        super();
    }

    /**
     * 删除.
     * @param id 主键
     * @return 删除条数
     */
    public int deleteByPrimaryKey(Long id) {
        MobileRecommendInfo key = new MobileRecommendInfo();
        key.setId(id);
        int rows = super.delete("MOBILE_RECOMMEND_INFO.deleteByPrimaryKey", key);
        return rows;
    }
    
    /**
     * 删除.
     * @param id 主键
     * @param blockId
     * @return 删除条数
     */
    public int deleteByParam(Map<String,Object> params) {
        int rows = super.delete("MOBILE_RECOMMEND_INFO.deleteByparams", params);
        return rows;
    }

    /**
     * 删除 某个block根节点下所有的blockInfo.
     * @param blockId  根节点 
     * @return 删除条数
     */
    public int deleteMobileRecommendInfoByBlockIdAndSon(Long id) {
        int rows = super.delete("MOBILE_RECOMMEND_INFO.deleteMobileRecommendInfoByBlockIdAndSon", id);
        return rows;
    }
    
    
    /**
     * 新增. 
     * @param record 
     * @return 持久化后的对象 
     */
    public MobileRecommendInfo insert(MobileRecommendInfo record) {
        super.insert("MOBILE_RECOMMEND_INFO.insert", record);
        return record;
    }

    /**
     * 指定列查询. 
     * @param record
     * @return
     */
    public Long insertSelective(MobileRecommendInfo record) {
        Object newKey = super.insert("MOBILE_RECOMMEND_INFO.insertSelective", record);
        return (Long) newKey;
    }

    /**
     * 主键查询 . 
     * @param id
     * @return
     */
    public MobileRecommendInfo selectByPrimaryKey(Long id) {
        MobileRecommendInfo key = new MobileRecommendInfo();
        key.setId(id);
        MobileRecommendInfo record = (MobileRecommendInfo) super.queryForObject("MOBILE_RECOMMEND_INFO.selectByPrimaryKey", key);
        return record;
    }

    /**
     * 只修改指定的列. 
     * @param record
     * @return 
     */
    public int updateByPrimaryKeySelective(MobileRecommendInfo record) {
        int rows = super.update("MOBILE_RECOMMEND_INFO.updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * 修改. 
     * @param record 对象
     * @return 修改的条数
     */
    public int updateByPrimaryKey(MobileRecommendInfo record) {
        int rows = super.update("MOBILE_RECOMMEND_INFO.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 更新状态.  
     * @param record 对象
     * @return 修改的条数
     */
    public int updateIsValidById(Map<String,Object> record) {
        int rows = super.update("MOBILE_RECOMMEND_INFO.updateRecommendInfoStatus", record);
        return rows;
    }
    
    /**
     * 更新排序值.  
     * @param record 对象
     * @return 修改的条数
     */
    public int updateSeq(Map<String,Object> record) {
        int rows = super.update("MOBILE_RECOMMEND_INFO.updateSeq", record);
        return rows;
    }
    
    /**
     * 根据id查找. 
     * @param id
     * @return obj 
     */
    public MobileRecommendInfo findByMobileRecommendInfoId(Long id) {
    	return (MobileRecommendInfo)super.queryForObject("MOBILE_RECOMMEND_INFO.findByMobileRecommendInfoId", id);
    }
    
    /**
     * 查询列表 . 
     * 支持   id, 标题，状态，链接地址 ，时间范围   
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<MobileRecommendInfo> queryRecommendInfoList(Map<String,Object> param) {
    	if(null == param || param.isEmpty()) {
    		return null ;
    	}
    	return (List<MobileRecommendInfo>)super.queryForList("MOBILE_RECOMMEND_INFO.queryRecommendInfoList", param);
    }
    
    /**
     * 查询记录数. 
     * @param param
     * @return
     */
    public Long countMobileRecommendInfoList(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_RECOMMEND_INFO.countRecommendInfoList", param);
    }
}