/**
 * 
 */
package com.lvmama.comm.pet.service.info;

import java.util.List;

import com.lvmama.comm.pet.po.info.InfoHelpCenter;
import com.lvmama.comm.pet.po.info.InfoQuesType;
import com.lvmama.comm.pet.po.info.InfoQuesTypeHierarchy;

/**
 * 帮助中心Service.
 * @author yangbin
 *
 */
public interface InfoService {
	
	
	/**
	 * 读取一个帮助文件
	 * @param pk
	 * @return
	 */
	InfoHelpCenter getInfoHelpById(final Long pk);

	/**
	 * 保存或更新记录.
	 * id不为空时表示更新.
	 * @param info
	 */
	void saveHelp(InfoHelpCenter info);
	
	/**
	 * 删除一条帮助文档记录
	 * @param infoHelpId 记录ID
	 */
	void removeHelp(final Long infoHelpId);
	
	/**
	 * 查询帮助中心数据列表.
	 * @param infoHelpCenter
	 * @return
	 */
	List<InfoHelpCenter> queryInfoHelpList(final InfoHelpCenter infoHelpCenter);
	
	/**
	 * 查询一个类目列表.
	 * @param objectType 指定的类型
	 * @return
	 */
	List<InfoQuesTypeHierarchy> queryInfoQuesTypeHierarchyByObjectType(final String objectType);
	
	/**
	 * 查询一个类目的子列表
	 * @param objectType
	 * @param parentType
	 * @return
	 */
	List<InfoQuesTypeHierarchy> queryInfoQuesTypeHierarchyByObjectType(final String objectType,final Long parentType);
	
	/**
	 * 查询类目列表，不包含树形结构信息
	 * @param objectType
	 * @return
	 */
	List<InfoQuesType> queryInfoQuesTypeList(final String objectType);
	
	/**
	 * 查询一个InfoQuesType对象，对象为valid='Y'.
	 * @param typeId
	 * @return
	 */
	InfoQuesType queryInfoQuestByPK(final Long typeId);
	
	/**
	 * 保存
	 * @param infoQuesTypeHierarchy
	 */
	void saveQuestionTypeInHierarchy(final InfoQuesTypeHierarchy infoQuesTypeHierarchy);
	
	/**
	 * 删除一条记录.
	 * 直接在valid上标记为N.
	 * @param typeId
	 */
	void removeQuestionType(final Long typeId);
	
	/**
	 * 保存InfoQuesType,因为树形关系的原因不要保存InfoQuesTypeHierarchy相关的对象
	 * @param type
	 * @return
	 */
	Long saveInfoType(InfoQuesType type);
	/*
	public void updateFAQ(InfoNormalQues faq);
	
	public void insertFAQ(InfoNormalQues faq);
	
	public void removeFAQ(Long id);
	
	public InfoNormalQues getFAQByPk(Long id);*/




}
