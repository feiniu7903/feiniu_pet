package com.lvmama.prd.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;

public class ProdChannelSmsDAO extends BaseIbatisDAO {

    public Long add(ProdChannelSms channelSms) {
    	return (Long)super.insert("PROD_CHANNEL_SMS.insert", channelSms);
    }
    
    public void deleteByPK(Long channelSmsId) {
    	super.delete("PROD_CHANNEL_SMS.deleteByPK", channelSmsId);
    }
    
    public void updateChannelSms(ProdChannelSms channelSms) {
    	super.update("PROD_CHANNEL_SMS.update", channelSms);
    }
    
    public ProdChannelSms selectByPrimaryKey(Long channelSmsId) {
        ProdChannelSms key = new ProdChannelSms();
        key.setChannelSmsId(channelSmsId);
        ProdChannelSms record = (ProdChannelSms) super.queryForObject("PROD_CHANNEL_SMS.selectByPrimaryKey", key);
        return record;
    }
    
    public List<ProdChannelSms> selectBychannelId(Long channelId){
    	List<ProdChannelSms> list=super.queryForList("PROD_CHANNEL_SMS.selectByChannelId",channelId);
    	return list;
    }
 
    public ProdChannelSms selectByTemplateIdAndChannelCode(String channelCode,String templateId){
		 ProdChannelSms key = new ProdChannelSms();
	     key.setChannelCode(channelCode);
	     key.setTemplateId(templateId);
    	List<ProdChannelSms> records = super.queryForList("PROD_CHANNEL_SMS.selectByTemplateIdAndChannelCode", key);
    	if (records!=null && records.size()>0){
    		return records.get(0);
    	}else{
    		return null;
    	}
    }
    
    /**
	 * 根据参数查询产品渠道短信模板信息
	 * */
	@SuppressWarnings("unchecked")
	public List<ProdChannelSms> getListByParams(Map<String, Object> params){
		return super.queryForList("PROD_CHANNEL_SMS.getListByParams", params);
	}
	
	/**
	 * 根据参数查询产品渠道所有的短信模板数量
	 * */
	public Long getListCountByParams(Map<String, Object> params){
		return Long.valueOf(super.queryForObject("PROD_CHANNEL_SMS.getListCountByParams",params).toString());
	}
	
	/**
	 * 更新产品渠道相关短信模板
	 * @param model
	 * @return
	 */
	public int updateProdChannelSms(ProdChannelSms model){
		return this.update("PROD_CHANNEL_SMS.updateProdChannelSms", model);
	}
	
	/**
	 * 根据super短信模板更新销售产品相关短信模板信息
	 * 
	 * @param template
	 * @return
	 */
	public int updateProdChannelSmsBySmsTemplate(ComSmsTemplate template) {
		return this.update(
				"PROD_CHANNEL_SMS.updateProdChannelSmsBySmsTemplate", template);
	}
	
	/**
	 * 修改渠道
	 * @param paramMap
	 * @return
	 */
	public int updateChannelByMap(Map<String, Object> paramMap){
		return super.update("PROD_CHANNEL_SMS.updateChannelByMap", paramMap);
	}
}