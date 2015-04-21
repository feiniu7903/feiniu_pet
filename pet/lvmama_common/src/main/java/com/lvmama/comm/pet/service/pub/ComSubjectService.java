package com.lvmama.comm.pet.service.pub;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComSubject;


public interface ComSubjectService {
	
	/**
	 * 查找主题列表
	 * 
	 * @param usedBy
	 * @param count
	 * @return
	 */
	public List<ComSubject> findComSubjects(ComSubject comSubject, int count);
	
	/**
	 * 根据ID查找主题详情
	 * 
	 * @param comSubject
	 * @return
	 */
	public ComSubject getComSubjectById(ComSubject comSubject);
	
	/**
	 * 删除
	 * 
	 * @param comSubject
	 */
	public void deleteComSubject(ComSubject comSubject);
	
	/**
	 * 保存或者删除
	 * 
	 * @param comSubject
	 */
	public void saveOrUpdateComSubject(ComSubject comSubject);
	
	/**
	 * 批量修改主题的seq
	 * 
	 * @param subjectSeqs
	 */
	public void updateSeqs(String subjectSeqs);
	
	/**
	 * 更新引用次数
	 * @param subjectName
	 */
	public void updateUsedCount(String subjectName);
	
	public List<ComSubject> querySubjectListByType(String subjectType,int count);

}
