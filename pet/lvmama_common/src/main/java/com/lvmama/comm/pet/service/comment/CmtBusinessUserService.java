/**
 * 
 */
package com.lvmama.comm.pet.service.comment;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.comment.CmtBusinessUser;


/**
 * @author liuyi
 * 商家用户接口
 */
public interface CmtBusinessUserService {
	
	/**
	 * 获取商家用户
	 * @param param
	 * @return
	 */
	CmtBusinessUser getCmtBusinessUser(Map param);
	
	List<CmtBusinessUser> queryUser(Map param);
	
	/**
	 * 新增用户
	 * @param cmtBusinessUser
	 * @return
	 */
	long addNewUser(CmtBusinessUser cmtBusinessUser);
	
	/**
	 * 新增用户和PLACE的关系
	 * @param cmtBusinessUser
	 * @return
	 */
	long addNewUserPlaceRelation(CmtBusinessUser cmtBusinessUser);
	
	/**
	 * 删除用户和PLACE的关系
	 * @param cmtBusinessUser
	 * @return
	 */
	int deleteUserPlaceRelation(CmtBusinessUser cmtBusinessUser);
	
	/**
	 * 更新用户
	 * @param cmtBusinessUser
	 * @return
	 */
	int updateUser(CmtBusinessUser cmtBusinessUser);
	
	/**
	 * 获得商户用户数
	 * @param param
	 * @return
	 */
	long getUserCount(Map param);
}
