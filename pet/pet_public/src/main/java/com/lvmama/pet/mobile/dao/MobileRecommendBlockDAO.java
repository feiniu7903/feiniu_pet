package com.lvmama.pet.mobile.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileRecommendBlock;


public class MobileRecommendBlockDAO extends BaseIbatisDAO {

    public MobileRecommendBlockDAO() {
        super();
    }

    /**
     * 综合查询. 
     * @param param
     * @return list
     */
    @SuppressWarnings("unchecked")
	public List<MobileRecommendBlock> queryMobileRecommendBlockByParam(Map<String, Object> param) {
		List<MobileRecommendBlock> mobileRecommendBlockList=(List<MobileRecommendBlock>)super.queryForList("MOBILE_RECOMMEND_BLOCK.queryMobileRecommendBlockByParam", param);
		return mobileRecommendBlockList;
	}

    /**
     * 根据id查询.
     * @param id
     * @return
     */
	public MobileRecommendBlock getMobileRecommendBlockById(Long id) {
		return selectByPrimaryKey(id);
	}

	/**
	 * 删除 ，根据id.
	 * @param id
	 * @return
	 */
    public int deleteByPrimaryKey(Long id) {
        MobileRecommendBlock key = new MobileRecommendBlock();
        key.setId(id);
        int rows = super.delete("MOBILE_RECOMMEND_BLOCK.deleteByPrimaryKey", key);
        return rows;
    }

	/**
	 * 删除 ，根据id.
	 * @param id
	 * @return
	 */
    public int deleteByParams(Map<String,Object> params) {
        int rows = super.delete("MOBILE_RECOMMEND_BLOCK.deleteMobileRecommendBlockByParam", params);
        return rows;
    }
    
    /**
     * 新增 - 插入所有的列.
     * @param record
     * @return
     */
    public Long insert(MobileRecommendBlock record) {
        Object newKey = super.insert("MOBILE_RECOMMEND_BLOCK.insert", record);
        return (Long) newKey;
    }

    /**
     * 新增 - 指定的列. 
     * @param record
     * @return
     */
    public Long insertSelective(MobileRecommendBlock record) {
        Object newKey = super.insert("MOBILE_RECOMMEND_BLOCK.insertSelective", record);
        return (Long) newKey;
    }

    /**
     * 更加主键查询. 
     * @param id
     * @return
     */
    public MobileRecommendBlock selectByPrimaryKey(Long id) {
        MobileRecommendBlock key = new MobileRecommendBlock();
        key.setId(id);
        MobileRecommendBlock record = (MobileRecommendBlock) super.queryForObject("MOBILE_RECOMMEND_BLOCK.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileRecommendBlock record) {
        int rows = super.update("MOBILE_RECOMMEND_BLOCK.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileRecommendBlock record) {
        int rows = super.update("MOBILE_RECOMMEND_BLOCK.updateByPrimaryKey", record);
        return rows;
    }
}