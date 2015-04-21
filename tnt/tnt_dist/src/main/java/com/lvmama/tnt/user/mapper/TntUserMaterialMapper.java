package com.lvmama.tnt.user.mapper;

import java.util.List;
import java.util.Map;

import com.lvmama.tnt.user.po.TntUserMaterial;

/**
 * 分销商资料
 * 
 * @author gaoxin
 * 
 */
public interface TntUserMaterialMapper {

	public int insert(TntUserMaterial tntUserMaterial);

	public int update(TntUserMaterial tntUserMaterial);

	public List<TntUserMaterial> query(Map<String, Object> map);

	public int queryCount(Map<String, Object> map);

	public TntUserMaterial getById(Long materialId);

	public List<TntUserMaterial> queryByUserId(Long userId);

	public int updateStatus(TntUserMaterial tntUserMaterial);

}
