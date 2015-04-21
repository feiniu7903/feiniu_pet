package com.lvmama.comm.bee.service.ebooking;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkHousePrice;

public interface EbkHousePriceService {
	/**
	 * 添加房价变更信息.
	 * @param ebkHousePrice
	 */
	public Long insert(EbkHousePrice ebkHousePrice);
	
	/**
	 * 根据主键ID删除房价变更信息.
	 * @param housePriceId
	 * @return
	 */
	public int deleteEbkHousePriceByPrimaryKey(Long housePriceId);
	
	/**
	 * 查询房价变更信息列表.
	 * @param example
	 * @return
	 */
	public List<EbkHousePrice> findEbkHousePriceListByExample(Map<String, Object> example);
	
	/**
	 * 统计房价变更信息列表数量.
	 * @param example
	 * @return
	 */
	public int countEbkHousePriceListByExample(Map<String, Object> example);
	/**
	 * 更新房价变更信息.
	 * @param ehp
	 * @return
	 */
	public int updateByPrimaryKey(EbkHousePrice ehp);
	/**
	 * 根据housePriceId查询房价变更信息.
	 * @param housePriceId
	 * @return
	 */
	public EbkHousePrice selectByPrimaryKey(Long housePriceId); 
}
