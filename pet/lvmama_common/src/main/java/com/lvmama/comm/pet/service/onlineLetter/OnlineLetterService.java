package com.lvmama.comm.pet.service.onlineLetter;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.onlineLetter.LetterTemplate;
import com.lvmama.comm.pet.onlineLetter.LetterUserMessage;

/**
 * @author shangzhengyuan
 *	站内信服务接口类
 */
public interface OnlineLetterService {
	/**
	 * 插入站内信模板
	 * @param template
	 * @return
	 */
	Long insertTemplate(LetterTemplate template);
	
	/**
	 * 修改站内信模板
	 * @param template
	 * @return
	 */
	int updateTemplate(LetterTemplate template);
	
	/**
	 * 根据条件查询模板列表
	 * @param parameters
	 * @return
	 */
	List<LetterTemplate> queryTemplate(Map<String,Object> parameters);
	
	/**
	 * 根据条件查询模板总数
	 * @param parameters
	 * @return
	 */
	Long countTemplate(Map<String,Object> parameters);
	
	/**
	 * 批量插入站内信
	 * @param list
	 * @return
	 */
	List<LetterUserMessage> batchInsertUserLetter(List<LetterUserMessage> list);
	
	/**
	 * 根据站内信ID号更新已阅时间
	 * @param id
	 * @return
	 */
	int updateUserLetter(Long id);
	
	/**
	 * 根据条件批量删除站内信
	 * @param parameters
	 * @return
	 */
	int batchDeleteUserLetter(Map<String,Object> parameters);
	
	/**
	 * 根据条件查询站内信列表
	 * @param parameters
	 * @return
	 */
	List<LetterUserMessage> queryMessage(Map<String,Object> parameters);
	
	/**
	 * 根据条件查询站内信总数
	 * @param parameters
	 * @return
	 */
	Long countMessage(Map<String,Object> parameters);
}
