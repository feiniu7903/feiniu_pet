package com.lvmama.eplace.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.PassPortAuthResources;


@SuppressWarnings("unchecked")
public class PassPortAuthResourcesDAO extends BaseIbatisDAO {
	
	public List<PassPortAuthResources> selectSecondItems(Long parentId){
		return super.queryForList("PASS_PORT_AUTH_RESOURCES.selectSecondItems", parentId);
	}
	
	public List<PassPortAuthResources> selectSecondResourceByParentId(Long userId, String category,Long parentId){
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("category", category);
		params.put("parentId", parentId);
		return super.queryForList("PASS_PORT_AUTH_RESOURCES.selectSecondResourceByParentId", params);
	}
	
	public List<String> selectCategoryByAdmin(){
		return super.queryForList(
				"PASS_PORT_AUTH_RESOURCES.selectCategoryByAdmin");
	}
	public List<String> selectCategoryByUserId(Long userId) {
		Map<String,Object> parm=new HashMap<String,Object>();
		parm.put("userId", userId);
		return super.queryForList(
				"PASS_PORT_AUTH_RESOURCES.selectCategoryByUserId", parm);
	}

	public List<PassPortAuthResources> getResourcesByUserAndCategory(
			Long userId, String category) {
		Map params = new HashMap();
		params.put("userId", userId);
		params.put("category", category);
		return super.queryForList(
						"PASS_PORT_AUTH_RESOURCES.AuthResources_selectByUserAndCategory",
						params);
	}

	/**
	 * 查询资源地址
	 * 
	 * @param parms
	 * @return
	 */
	public List<PassPortAuthResources> selectByParms(Map parms) {
		return super.queryForList(
				"PASS_PORT_AUTH_RESOURCES.AuthResources_selectByParms", parms);
	}

	/**
	 * 添加资源
	 * 
	 * @param resource
	 */
	public void insertResource(PassPortAuthResources resource) {
		super.insert(
				"PASS_PORT_AUTH_RESOURCES.AuthResources_insert", resource);
	}

	/**
	 * 编辑资源
	 * 
	 * @param resource
	 */
	public void updateResource(PassPortAuthResources resource) {
		super.update(
				"PASS_PORT_AUTH_RESOURCES.AuthResources_updateByPrimaryKey",
				resource);
	}

	/**
	 * 删除资源
	 * 
	 * @param resourceId
	 */
	public void deleteResource(Long resourceId) {
		super.delete(
				"PASS_PORT_AUTH_RESOURCES.AuthResources_deleteByPrimaryKey",
				resourceId);
	}
}