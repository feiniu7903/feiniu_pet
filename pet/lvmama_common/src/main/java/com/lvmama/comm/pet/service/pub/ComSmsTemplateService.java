package com.lvmama.comm.pet.service.pub;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;

public interface ComSmsTemplateService {
	
	List<ComSmsTemplate> selectAllSmsTempalteList(String templateType);

	List<ComSmsTemplate> selectAllSmsTempalteList();
	
	ComSmsTemplate selectSmsTemplateByPrimaryKey(String templateId);
	
	/**
	 * 根据参数替换短信模板的变量，并返回内容
	 * @param templateId 短信模板标识
	 * @param parameters 参数
	 * @return 被替换变量的内容
	 */
	String getSmsContent(String templateId, Map<String, Object> parameters);
	
	/**
	 * 根据参数查询短信模板信息
	 * */
	public List<ComSmsTemplate> getListByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询所有的短信模板数量
	 * */
	public Long getListCountByParams(Map<String, Object> params);
	
	/**
	 * 根据短信模板id删除模板
	 * @param templateId
	 * @return
	 */
	public int deleteByPrimaryKey(String templateId);

	/**
	 * 插入短信模板
	 * @param record
	 */
	public void insert(ComSmsTemplate record);

	/**
	 * 根据模板的id查询模板
	 * @param templateId
	 * @return
	 */
	public ComSmsTemplate selectByPrimaryKey(String templateId);

	/**
	 * 根据短信模板的主键更新短信模板
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKey(ComSmsTemplate record);
	
	/**
	 * 批量修改短信渠道
	 * @param paramMap
	 * @return
	 */
	public int updateBatchChannel(Map<String, Object> paramMap);
	
}
