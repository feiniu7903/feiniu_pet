/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.service.work;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.work.WorkGroup;
/**
 * WorkGroup 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @update dingming
 * @version 1.0
 * @since 1.0
 */

public interface WorkGroupService{
	/**
	 * 持久化对象
	 * @param workGroup
	 * @return
	 */
	public Long insert(WorkGroup workGroup);
	/**
	 * 根据主键id查询
	 */
	public WorkGroup getWorkGroupById(Long id);
	
	/**
	 * 更新组织信息
	 */
	public int updateWorkGroup(WorkGroup workGroup);
	/**
	 * 分页总条数
	 */
	public Long getWorkGroupPageCount(Map<String, Object> params);
	/**
	 * 查询组织信息带分页
	 */
	public List<WorkGroup> getWorkGroupPage(Map<String, Object> params);
	
	public List<WorkGroup> getWorkGroupWithDepartment(Map<String, Object> params);
	
	public List<WorkGroup> queryWorkGroupByParam(Map<String, Object> params);
	/**
	 * 查询组织名称
	 * @return
	 */
	public List<WorkGroup> queryWorkGroupName();
}
