package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileActivityFifaLuckycode;

public class MobileActivityFifaLuckycodeDAO extends BaseIbatisDAO {

    public MobileActivityFifaLuckycodeDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        MobileActivityFifaLuckycode key = new MobileActivityFifaLuckycode();
        key.setId(id);
        int rows = super.delete("MOBILE_ACTIVITY_FIFA_LUCKYCODE.deleteByPrimaryKey", key);
        return rows;
    }

    public MobileActivityFifaLuckycode insert(MobileActivityFifaLuckycode record) {
        super.insert("MOBILE_ACTIVITY_FIFA_LUCKYCODE.insert", record);
        return record;
    }

    public Long insertSelective(MobileActivityFifaLuckycode record) {
        Object newKey = super.insert("MOBILE_ACTIVITY_FIFA_LUCKYCODE.insertSelective", record);
        return (Long) newKey;
    }

    public MobileActivityFifaLuckycode selectByPrimaryKey(Long id) {
        MobileActivityFifaLuckycode key = new MobileActivityFifaLuckycode();
        key.setId(id);
        MobileActivityFifaLuckycode record = (MobileActivityFifaLuckycode) super.queryForObject("MOBILE_ACTIVITY_FIFA_LUCKYCODE.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(MobileActivityFifaLuckycode record) {
        int rows = super.update("MOBILE_ACTIVITY_FIFA_LUCKYCODE.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileActivityFifaLuckycode record) {
        int rows = super.update("MOBILE_ACTIVITY_FIFA_LUCKYCODE.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 查询幸运码列表 . 
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<MobileActivityFifaLuckycode> getMobileActivityFifaLuckycodeListByPrarms(Map<String,Object> params){
    	return (List<MobileActivityFifaLuckycode>)super.queryForList("MOBILE_ACTIVITY_FIFA_LUCKYCODE.queryMobileActivityFifaLuckycodeList", params);
    	
    }
    
    /**
     * 查询中奖用户列表 . 
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<MobileActivityFifaLuckycode> queryTheWinningUser4Fifa(Map<String,Object> params){
    	return (List<MobileActivityFifaLuckycode>)super.queryForList("MOBILE_ACTIVITY_FIFA_LUCKYCODE.queryTheWinningUser4Fifa", params);
    	
    }
    
    /**
     * 查询幸运码总记录数. 
     * @param param
     * @return
     */
    public Long countMobileActivityFifaLuckycode(Map<String,Object> param){
    	return (Long) super.queryForObject("MOBILE_ACTIVITY_FIFA_LUCKYCODE.countMobileActivityFifaLuckycodeList", param);
    }
	/**
     * 获取世界杯活动下一个序列号
     * @param param
     * @return
     */
	public Long getMobileActSeqNextval(Map<String, Object> param) {
		return (Long) super.queryForObject("MOBILE_ACTIVITY_FIFA_LUCKYCODE.selectMafSeqNextval", param);
	}

	public Long selectMafLuckyCodeSeqCurrval(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return (Long) super.queryForObject("MOBILE_ACTIVITY_FIFA_LUCKYCODE.selectMafLuckyCodeSeqCurrval", param);
	}
    
}