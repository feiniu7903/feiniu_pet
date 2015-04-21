/**
 * 
 */
package com.lvmama.pet.comment.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.comment.CmtBusinessUser;
import com.lvmama.comm.pet.service.comment.CmtBusinessUserService;
import com.lvmama.pet.comment.dao.CmtBusinessUserDAO;

/**
 * @author liuyi
 *
 */
public class CmtBusinessUserServiceImpl implements CmtBusinessUserService {

	private CmtBusinessUserDAO cmtBusinessUserDAO;
	
	/**
	 * 获取商家用户
	 * @param param
	 * @return
	 */
	@Override
	public CmtBusinessUser getCmtBusinessUser(Map param) {
		List<CmtBusinessUser> cmtBusinessUsers = cmtBusinessUserDAO.query(param);
		if(null != cmtBusinessUsers && cmtBusinessUsers.size() == 1)
		{
			return cmtBusinessUsers.get(0);
		}
		else
		{
			return null;
		}
	}
	
	public List<CmtBusinessUser> queryUser(Map param)
	{
		return  cmtBusinessUserDAO.query(param);
	}
	
	/**
	 * 新增用户
	 * @param cmtBusinessUser
	 * @return
	 */
	public long addNewUser(CmtBusinessUser cmtBusinessUser)
	{
		return cmtBusinessUserDAO.insert(cmtBusinessUser);
	}
	
	/**
	 * 新增用户和PLACE的关系
	 * @param cmtBusinessUser
	 * @return
	 */
	public long addNewUserPlaceRelation(CmtBusinessUser cmtBusinessUser)
	{
		return cmtBusinessUserDAO.insertPlaceRelation(cmtBusinessUser);
	}
	
	/**
	 * 删除用户和PLACE的关系
	 * @param cmtBusinessUser
	 * @return
	 */
	public int deleteUserPlaceRelation(CmtBusinessUser cmtBusinessUser)
	{
		return cmtBusinessUserDAO.deletePlaceRelation(cmtBusinessUser);
	}
	
	
	/**
	 * 更新用户
	 * @param cmtBusinessUser
	 * @return
	 */
	public int updateUser(CmtBusinessUser cmtBusinessUser)
	{
		return cmtBusinessUserDAO.update(cmtBusinessUser);
	}
	
	/**
	 *  获得商户用户数
	 * @param param
	 * @return
	 */
	public long getUserCount(Map param)
	{
		return cmtBusinessUserDAO.getUserCount(param);
	}

	public CmtBusinessUserDAO getCmtBusinessUserDAO() {
		return cmtBusinessUserDAO;
	}

	public void setCmtBusinessUserDAO(CmtBusinessUserDAO cmtBusinessUserDAO) {
		this.cmtBusinessUserDAO = cmtBusinessUserDAO;
	}


}
