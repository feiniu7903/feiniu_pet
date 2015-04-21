package com.lvmama.comm.pet.service.mark;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.mark.MarkChannel;
import com.lvmama.comm.pet.vo.mark.MarkChannelVO;

public interface MarkChannelService {
	
	/**
	 * select By PrimaryKey
	 * 
	 * @param channelId
	 * @return
	 */
	public MarkChannel selectByPrimaryKey(Long channelId);

	/*
	 * 根据查询条件查询销售渠道
	 * 
	 * @param parameters
	 * 
	 * @return
	 */
	public List<MarkChannel> search(Map<String, Object> parameters);
	
	/**
	 * 根据查询条件查询复杂的销售渠道
	 * @param parameters 查询条件
	 * @return 复杂的查询渠道
	 */
	public List<MarkChannelVO> searchComplexVO(Map<String, Object> parameters);

	/**
	 * 根据查询条件计算复杂销售渠道的总数
	 * 
	 * @param parameters
	 * @return
	 */
	public Long countComplexVO(Map<String, Object> parameters);

	/**
	 * 根据传入参数插入渠道
	 * 
	 * @param record
	 * @return
	 */
	public Long insertMarkDicChannelWithLog(MarkChannel markDicChannel, String operatorName);

	/**
	 * 根据渠道ID更新相关信息
	 * 
	 * @param markCouponDAO
	 */
	public void updateMarkDicChannelByPrimaryKeyWithLog(MarkChannel markDicChannel, String operatorName);
	
	/**
	 * 根据渠道ID更新父渠道
	 * 
	 * @param markCouponDAO
	 */
//	public int updateFatherIdByLayer(MarkDictChannel record, String operatorName);

	/**
	 * 根据渠道ID查询是否有已绑定渠道的被激活的会员卡
	 * 
	 * @param channelId
	 * @return
	 */
//	public Long activeCount(Long channelId);
}
