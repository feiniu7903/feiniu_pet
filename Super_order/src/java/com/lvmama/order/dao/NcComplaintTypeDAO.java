package com.lvmama.order.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.NcComplaintType;
/**
 * 投诉类型
 * @author zhushuying
 *
 */
public class NcComplaintTypeDAO extends BaseIbatisDAO {
	/**
	 * 分页查询信息
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<NcComplaintType> queryAllTypeByPage(Map<String, Object> map) {
		return super.queryForList("NC_COMPLAINT_TYPE.selectAllTypeByPage",map);
	}
	/**
	 * 查询总数量
	 * @return
	 */
	public long getTypeCount() {
		return (Long)super.queryForObject("NC_COMPLAINT_TYPE.getTypePageCount");
	}
	/**
	 * 根据id查询投诉类型
	 * @param TypeId
	 * @return
	 */
	public NcComplaintType selectTypeById(Map<String, Object> map) {
		return (NcComplaintType) super.queryForObject("NC_COMPLAINT_TYPE.getTypeById", map);
	}
	/**
	 * 修改投诉类型
	 * @param Type
	 * @return
	 */
	public int updateType(NcComplaintType type) {
		return super.update("NC_COMPLAINT_TYPE.updateTypeById", type);
	}
	/**
	 * 添加投诉类型信息
	 * @param type
	 * @return
	 */
	public Long addComplaintType(NcComplaintType type) {
		return (Long) super.insert("NC_COMPLAINT_TYPE.insertType", type);
	}
}