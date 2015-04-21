/**
 * 
 */
package com.lvmama.pet.perm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.utils.StringUtil;

/**
 * @author zhangzhenhua
 * 
 */
public class PermUserDAO extends BaseIbatisDAO {

	private static final Log log = LogFactory.getLog(PermUserDAO.class);

	/**
	 * 根据真实姓名查找相关的用户
	 * 
	 * @param realName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PermUser> findPermUser(String realName) {
		log.debug("into findPermUser....realName is :" + realName);

		return super.queryForList(
				"PERM_USER.selectListByRealName", realName);
	}
	/**
	 * 根据名称模糊查询用户名或真实姓名的用户
	 * @author ZHANG Nan
	 * @param name 名称
	 * @return 用户集合
	 */
	@SuppressWarnings("unchecked")
	public List<PermUser> selectListByUserNameOrRealName(String name) {
		return super.queryForList("PERM_USER.selectListByUserNameOrRealName", name);
	}
	/**
	 * 根据参数查询所有的用户，并分页
	 * */
	@SuppressWarnings("unchecked")
	public List<PermUser> selectUsersByParams(Map<String, Object> params) {
		return super.queryForList(
				"PERM_USER.selectUsersByParams", params);
	}
	
	/**
	 * 根据参数查询所有的用户数量
	 * */
	public Long selectUsersCountByParams(Map<String, Object> params) {
		return (Long) super.queryForObject(
				"PERM_USER.selectUsersCountByParams", params);
	}
	
	/**
	 * 查询某个组子某个角色下面的用户ID列表
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> findRoleUserByOrg(Map<String, Object> params) {
		return super.queryForList("PERM_USER.findRoleUserByOrg", params);
	}
	
	/**
	 * 查找单条PermUser对象
	 * 
	 * @param permUser
	 * @return
	 */
	public PermUser getPermUserByUserId(Long userId) {
		log.debug("into getPermUser....permUser id is :" + userId);

		return (PermUser) super.queryForObject(
				"PERM_USER.selectByPrimaryKey", userId);
	}
	
	/**
	 * 获取新的用户编号
	 * @return
	 */
	public Long getNewPermUserNum(){
		return (Long)super.queryForObject("PERM_USER.getNewPermUserNum");
	}

	public Object addUser(PermUser permUser) {
		return super.insert("PERM_USER.addUser", permUser);
	}
	/**
	 * get PermUser By UserName
	 * 
	 * @param userName
	 * @return
	 */
	public PermUser getPermUserByParams(Map<String, Object> params) {
		Object userName = params.get("userName");
		Object employeeNum = params.get("employeeNum");
		Object managerId = params.get("managerId");
		if(StringUtils.isNotEmpty(userName+"")
		||StringUtils.isNotEmpty(employeeNum+"")
		||managerId != null){
			return (PermUser) super.queryForObject("PERM_USER.getPermUserByParams", params);
		}
		return null;
	}
	
	public PermUser getPermUserByUserName(String userName){
		return (PermUser) super.queryForObject("PERM_USER.getPermUserByUserName", userName);
	} 

	public Object updateUser(PermUser permUser) {
		return super.update("PERM_USER.updateUser",
				permUser);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PermUser> queryPermUserByParam(Map<String, Object> params) {
		List<PermUser> ret = null;
		ret = super.queryForList(
				"PERM_USER.selectByParam", params);
		return ret;
	}
	public Long queryPermUserByParamCount(Map<String, Object> params){
		return (Long)super.queryForObject(
				"PERM_USER.selectByParamCount", params);
	}
	
	/**
	 * 初始化密码
	 * @param permUser
	 * @return
	 */
	public Object initializePassword(PermUser permUser) {
		return super.update("PERM_USER.initializePassword", permUser);
	}

	public List<PermPermission> queryEnablePermissionsByUserIdPaging(Map<String, Object> params) {
		return super.queryForList("PERM_USER_PERMISSION.queryEnablePermissionsByUserIdPaging", params);
	}

	public Long queryEnablePermissionsByUserIdPagingCount(Map<String, Object> params) {
		return (Long) super.queryForObject("PERM_USER_PERMISSION.queryEnablePermissionsByUserIdPagingCount", params);
	}
	public void updateWorkStatus(String userName, String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("workStatus", status);
		update("PERM_USER.updateWorkStatus",map);
	}
	public List<PermUser> getOrderProcessUsersByLVCC(Map<String, Object> params){
		return queryForList("PERM_USER.getOrderProcessUsersByLVCC",params);
	}
}
