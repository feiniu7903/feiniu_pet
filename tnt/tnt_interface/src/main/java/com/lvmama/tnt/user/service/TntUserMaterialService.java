package com.lvmama.tnt.user.service;

import java.util.List;
import java.util.Map;

import com.lvmama.tnt.user.po.TntUserMaterial;

public interface TntUserMaterialService {

	public Long insert(TntUserMaterial tntUserMaterial);

	public void update(TntUserMaterial tntUserMaterial);

	public List<TntUserMaterial> query(Map<String, Object> map);

	public int queryCount(Map<String, Object> map);

	public List<TntUserMaterial> queryByUserId(Long userId);

	/**
	 * 审核通过，当materialId不为null时是单个审核
	 * 
	 * @param userId
	 * @param materialId
	 * @return
	 */
	public boolean agree(Long userId, Long materialId);

	/**
	 * 资料审核不通过
	 * 
	 * @param userId
	 * @return
	 */
	public boolean reject(Long userId, String reason);

	/**
	 * 单个审核不通过
	 * 
	 * @param materialId
	 * @return
	 */
	public boolean singlReject(Long materialId, String reason);
}
