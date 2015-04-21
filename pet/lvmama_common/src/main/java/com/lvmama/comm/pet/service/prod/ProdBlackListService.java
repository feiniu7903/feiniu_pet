package com.lvmama.comm.pet.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdBlackList;

/**
 * 黑名单
 * @author zenglei
 *
 */
public interface ProdBlackListService {
	/**
	 * 新增黑名单
	 * @param prodBlackList
	 */
	public void insertBlackList(List<ProdBlackList> prodBlackLists);
	
	/**
	 * 修改黑名单
	 */
	public void updateBlackList(ProdBlackList prodBlackList);
	
	/**
	 * 删除黑名单
	 */
	public void deleteBlackList(ProdBlackList prodBlackList);
	/**
	 * 查询黑名单  by iphone
	 */
	public List<ProdBlackList> queryBlackListByParam(Map<String, Object> param);
	/**
	 * 查询黑名单
	 */
	public List<ProdBlackList> queryBlackListByBlacks(ProdBlackList prodBlackList);
	/**
	 * 
	 */
	public Integer selectRowCount(Map<String, Object> param);
}
