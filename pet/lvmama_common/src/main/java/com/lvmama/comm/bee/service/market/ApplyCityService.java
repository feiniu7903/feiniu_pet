package com.lvmama.comm.bee.service.market;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.market.ApplyCity;

public interface ApplyCityService {
	/**
	 * 查询所有的城市信息
	 * @return
	 */
	public List<ApplyCity> selectAllApplyCity();
	/**
	 * 根据id查询城市信息
	 * @param cityId
	 * @return
	 */
	public ApplyCity selectApplyCityBy(Map<String, Object> map);
	/**
	 * 查询总数量
	 * @return
	 */
	public Long getApplyCityPageCount();
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public List<ApplyCity> getApplyCityByPage(Map<String, Object> map);
	/**
	 * 添加城市信息
	 * @param city
	 * @return
	 */
	public Long addApplyCity(ApplyCity city);
	/**
	 * 根据id删除城市信息
	 * @param cityId
	 * @return
	 */
	public int delApplyCity(Long cityId);
}
