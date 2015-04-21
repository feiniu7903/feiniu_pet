package com.lvmama.comm.pet.service.perm;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;

public interface PermUserService {

	/**
	 * 根据真实姓名查找相关的用户
	 * 
	 * @param realName
	 * @return
	 */
	public List<PermUser> findPermUser(String realName);
	/**
	 * 根据名称模糊查询用户名或真实姓名的用户
	 * @author ZHANG Nan
	 * @param name 名称
	 * @return 用户集合
	 */
	public List<PermUser> selectListByUserNameOrRealName(String name);

	/**
	 * 查询PermUser
	 * 
	 * @param userId
	 * @return
	 */
	public PermUser getPermUserByUserId(Long userId);
	
	/**
	 * 查询PermUser
	 * 
	 * @param userName
	 * @return
	 */
	public PermUser getPermUserByParams(Map<String, Object> params);
	
	public PermUser getPermUserByUserName(String userName);
	/**
	 * 获取新的用户编号
	 * @return
	 */
	public Long getNewPermUserNum();

	/**
	 * 登录验证
	 * 
	 * @param permUser
	 * @return
	 */
	public PermUser login(PermUser permUser);

	/**
	 * 根据参数查询用户
	 * */
	List<PermUser> selectUsersByParams(Map<String, Object> params);
	
	/**
	 * 根据参数查询所有的用户数量
	 * */
	public Long selectUsersCountByParams(Map<String, Object> params);

	/**
	 * 查询某个组子某个角色下面的用户ID列表
	 * 
	 * @param params
	 * @return
	 */
	public List<Long> findRoleUserByOrg(Map<String, Object> params);
	
	/**
	 * 新增用户
	 * */
	Long addUser(PermUser permUser);
	

	/**
	 * 修改用户信息
	 * */
	boolean updateUser(PermUser permUser);
	/**
	 * 通过参数查询.
	 * @param params
	 * @return
	 */
	public List<PermUser> queryPermUserByParam(Map<String, Object> params);
	/**
	 * 通过参数查询.(计数COUNT*)
	 * @param params
	 * @return
	 */
	public Long queryPermUserByParamCount(Map<String, Object> params);

	
	/**
	 * @author lipengcheng
	 * 初始化密码
	 * @param permUser
	 * @return
	 */
	public boolean initializePassword(PermUser permUser);

	/**
	 * 根据参数(用户id)查询权限
	 * */
	List<PermPermission> queryEnablePermissionsByUserIdPaging(Map<String, Object> params);

	/**
	 * 根据参数(用户id)查询权限数量
	 * */
	Long queryEnablePermissionsByUserIdPagingCount(Map<String, Object> params);
	
	void updateWorkStatus(String userName, String status);
	public List<PermUser> getOrderProcessUsersByLVCC(Map<String, Object> params);
}
