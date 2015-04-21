package com.lvmama.comm.bee.service.complaint;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.NcComplaintType;
/**
 * 投诉类型信息
 * @author zhushuying
 *
 */
public interface NcComplaintTypeService {
	/**
	 * 查询总数量
	 * @return
	 */
	public Long getTypeCount();
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<NcComplaintType> getAllTypeByPage(Map<String, Object> map);
	/**
	 * 根据id查询投诉类型
	 * @param TypeId
	 * @return
	 */
	public NcComplaintType selectTypeById(Map<String, Object> map);
	/**
	 * 修改投诉类型
	 * @param Type
	 * @return
	 */
	public int updateComplaintType(NcComplaintType type);
	/**
	 * 添加投诉类型
	 * @param type
	 * @return
	 */
	public Long addComplaintType(NcComplaintType type);
}
