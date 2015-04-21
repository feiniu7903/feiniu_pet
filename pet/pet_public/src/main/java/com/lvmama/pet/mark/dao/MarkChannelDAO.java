package com.lvmama.pet.mark.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkChannel;
import com.lvmama.comm.pet.vo.mark.MarkChannelVO;

public class MarkChannelDAO extends BaseIbatisDAO {
	/**
	 * 新增销售渠道
	 * @param markDistChannel
	 * @return 新增的渠道
	 */
    public MarkChannel insert(final MarkChannel markDistChannel) {
    	super.insert("MARK_CHANNEL.insert", markDistChannel);
        return markDistChannel;
    }	
	
    /**
     * 删除销售渠道
     * @param channelId  渠道标识
     * @return 渠道
     */
    public void deleteByPrimaryKey(final Long channelId) {
        MarkChannel key = new MarkChannel();
        key.setChannelId(channelId);
        super.delete("MARK_CHANNEL.deleteByPrimaryKey", key);
    }
    
    /**
     * 保存销售渠道
     * @param markDistChannel
     */
    public void update(MarkChannel markDistChannel) {
    	super.update("MARK_CHANNEL.update", markDistChannel);
    }    
    
    /**
     * 根据标识查找销售渠道
     * @param channelId 渠道标识
     * @return 渠道
     */
    public MarkChannel queryByPrimaryKey(final Long channelId) {
        return (MarkChannel) super.queryForObject("MARK_CHANNEL.queryByPrimaryKey", channelId);
    }
    
    /**
     * 根据查询条件查找销售渠道
     * @param parameters 查询条件
     * @return 销售渠道列表
     */
    @SuppressWarnings("unchecked")
	public List<MarkChannel> query(final Map<String, Object> parameters) {
        return (List<MarkChannel>) super.queryForList("MARK_CHANNEL.query", parameters);
    }
  
    /**
     * 根据查询条件查找复杂销售渠道
     * @param parameters 查询条件
     * @return 复杂销售渠道列表
     */
    @SuppressWarnings("unchecked")    
    public List<MarkChannelVO> queryComplexVO(final Map<String, Object> parameters) {
        return (List<MarkChannelVO>) super.queryForList("MARK_CHANNEL.queryComplexVO", parameters);
    }  
    
    /**
     * 根据查询条件查询销售渠道的总数
     * @param parameters 查询条件
     * @return 销售渠道的总数
     */
	public Long countComplexVO(final Map<String, Object> parameters) {
        return (Long) super.queryForObject("MARK_CHANNEL.count", parameters);
    }
    
    /**
     * 根据查询条件查询会员卡渠道
     * @param parameters
     * @return
     */
//    @SuppressWarnings("unchecked")
//	public List<MemberCardChannel> search(Map<String,Object> parameters){
//    	return super.queryForList("MARK_CHANNEL.search", parameters);
//    }    



//    public Long insertSelective(MarkDistChannel record) {
//        Object newKey = super.insert("MARK_CHANNEL.insertSelective", record);
//        return (Long) newKey;
//    }
//
//
//
//    public int updateByPrimaryKeySelective(MarkDistChannel record) {
//        int rows = super.update("MARK_CHANNEL.updateByPrimaryKeySelective", record);
//        return rows;
//    }
//

    /**
     * 更新渠道的父ID
     * @param record
     * @return
     */
//    public int updateFatherIdByLayer(MarkDistChannel record){
//   	 int rows = super.update("MARK_CHANNEL.updateFatherIdByLayer", record);
//        return rows;
//   }
//    public List<MarkDistChannel> selectAll(){
//    	return super.queryForList("MARK_CHANNEL.selectAll");
//    }

}
