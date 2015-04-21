package com.lvmama.pet.pub.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComParttimeUser;

public class ComParttimeUserDAO extends BaseIbatisDAO {
	/**
     * 根据渠道类型或渠道标识查询促销员与渠道关系
     * @param parameters
     * @return
     */
    public List<ComParttimeUser> query(Map<String,Object> parameters){
    	return super.queryForList("COM_PARTTIME_USER.query", parameters);
    }
    /**
     * 根据渠道类型或渠道标识查询促销员与渠道关系总数
     * @param parameters
     * @return
     */
    public Long count(Map<String,Object> parameters){
    	return (Long)super.queryForObject("COM_PARTTIME_USER.count", parameters);
    }

    /**
     * 新增促销员与渠道关系信息
     * @param parameters
     */
    public void insert(ComParttimeUser user){
    	super.insert("COM_PARTTIME_USER.insert", user);	
    }
    /**
     * 根据促销员ID更新用户与渠道关系信息
     * @param parameters
     * @return
     */
    public void update(ComParttimeUser user){
    	super.update("COM_PARTTIME_USER.update", user);
    }
    /**
     * 获取渠道的路径
     * @param channelId
     * @return
     */
    public String getChannelName(Long channelId) {
    	String a = (String) super.queryForObject("COM_PARTTIME_USER.getChannelName", channelId);
    	System.out.println("a:" + a);
    	 return a;
    }
}
