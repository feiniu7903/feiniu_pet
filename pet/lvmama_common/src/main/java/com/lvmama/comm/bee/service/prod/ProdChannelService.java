package com.lvmama.comm.bee.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.pet.po.prod.ProdChannel;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;

public interface ProdChannelService {
	
	List<ProdChannelSms> selectChannelSmsByChannelId(Long channelId);
	
	List<ProdChannel> selectProdChannelByAll();
	
	boolean isExistsChannelSms(Long channelId, String templateId);
	
	ProdChannelSms getChannelSmsTemplate(String channelCode, String templateId);
	
	Long addChannelSms(Long channelId, String templateId);
	
	void deleteChannelSms(Long channelSmsId);
	
	void updateChannelSms(ProdChannelSms channelSms);
	
	List<ProdChannel> searchChannel(Map param);
	
	ProdChannel getProdChannelById(Long channelId);
	
	/**
	 * 根据参数查询产品渠道短信模板信息
	 * */
	public List<ProdChannelSms> getProdChannelSmsListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询产品渠道所有的短信模板数量
	 * */
	public Long getProdChannelSmsListCountByParams(Map<String, Object> params);
	
	/**
	 * 添加产品渠道短信模板
	 * @param model
	 * @return
	 */
	public Long add(ProdChannelSms model);
	
	/**
	 * 更新产品渠道相关短信模板
	 * @param model
	 * @return
	 */
	public int updateProdChannelSms(ProdChannelSms model);
	
	/**
	 * 批量修改短信渠道
	 * @param paramMap
	 * @return
	 */
	public int updateBatchChannel(Map<String, Object> paramMap);
}
