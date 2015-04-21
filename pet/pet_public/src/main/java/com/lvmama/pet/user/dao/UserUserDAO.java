package com.lvmama.pet.user.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserUser;


/**
 * 
 * 用户的数据库操作接口实现类
 *
 */
public class UserUserDAO extends BaseIbatisDAO {
	/**
	 * 保存用户信息
	 * @param userUsers 用户
	 */
	public void save(final UserUser userUsers) {
		if (null != userUsers && null != userUsers.getEmail()) {
			userUsers.setEmail(userUsers.getEmail().toLowerCase());
		}
		if (null != userUsers && null != userUsers.getUserName()) {
			userUsers.setUserName(userUsers.getUserName().toLowerCase());
		}
		super.insert("USER_USER.insert", userUsers);
	}

	/**
	 * 更新用户信息
	 * @param userUsers 用户
	 */
	public void update(final UserUser userUsers) {
		if (null != userUsers && null != userUsers.getEmail()) {
			userUsers.setEmail(userUsers.getEmail().toLowerCase());
		}
		if (null != userUsers && null != userUsers.getUserName()) {
			userUsers.setUserName(userUsers.getUserName().toLowerCase());
		}
		super.update("USER_USER.update", userUsers);
	}
	
	/**
	 * 根据用户的标识查找用户
	 * 
	 * @param userId 用户标识
	 * @return 用户信息
	 */
	public UserUser getUsersByPk(final Long userId) {
		Object o = super.queryForObject(
				"USER_USER.queryUsersByPk", userId);
		if (o instanceof UserUser) {
			return (UserUser) o;
		}
		return null;
	}	
	
	/**
	 * 根据用户的32位标识查找用户
	 * 根据用户的user_no来查找用户
	 * @param userNo 用户号
	 * @return 用户信息
	 */
	public UserUser getUsersByUserNo(final String userNo) {
		Object o = super.queryForObject(
				"USER_USER.queryUsersByUserNo", userNo);
		if (o instanceof UserUser) {
			return (UserUser) o;
		}
		return null;
	}
	
	/**
	 * 根据查询条件查找唯一的用户
	 * @param parameters 查询条件
	 * @return 用户信息
	 */
	@SuppressWarnings("rawtypes")
	public UserUser getUsersForObject(final Map<String, Object> parameters) {
		if (null == parameters
				|| (StringUtils.isEmpty((String) parameters.get("userName"))
						&& StringUtils.isEmpty((String) parameters.get("mobileNumber"))
						&& StringUtils.isEmpty((String) parameters.get("email"))
						&& StringUtils.isEmpty((String) parameters.get("memberShipCard"))
						&& StringUtils.isEmpty((String) parameters.get("wechatId")))) {
			return null;
		}
		if (StringUtils.isNotEmpty((String) parameters.get("userName"))) {
			parameters.put("userName", ((String) parameters.get("userName")).toLowerCase());
		}
		if (StringUtils.isNotEmpty((String) parameters.get("email"))) {
			parameters.put("email", ((String) parameters.get("email")).toLowerCase());
		}
		List list = super.queryForList("USER_USER.query",
				parameters);
		if (list.isEmpty()) {
			return null;
		} else {
			return (UserUser) list.get(0);
		}
	}
	public boolean queryForLoginValidate(final Map<String, Object> parameters){
		if (null == parameters
				|| (StringUtils.isEmpty((String) parameters.get("userName"))
						&& StringUtils.isEmpty((String) parameters.get("mobileNumber"))
						&& StringUtils.isEmpty((String) parameters.get("email"))
						&& StringUtils.isEmpty((String) parameters.get("memberShipCard"))
						&& StringUtils.isEmpty((String) parameters.get("wechatId")))) {
			return false;
		}
		if (StringUtils.isNotEmpty((String) parameters.get("userName"))) {
			parameters.put("userName", ((String) parameters.get("userName")).toLowerCase());
		}
		if (StringUtils.isNotEmpty((String) parameters.get("email"))) {
			parameters.put("email", ((String) parameters.get("email")).toLowerCase());
		}
		Integer res = (Integer)super.queryForObject("USER_USER.queryForLoginValidate",parameters);
		return res == null ? false : true;
	}
	
	/**
	 * 根据用户的user_name,email,mobile_number,membership_card,wechatid查询用户列表
	 * 
	 * @author: ranlongfei 2012-8-29 下午2:31:09
	 * @param parameters
	 * @return 用户列表
	 */
	@SuppressWarnings("unchecked")
	public List<UserUser> queryUserUserKeyWordsLike(final Map<String, Object> parameters) {
		if(StringUtils.isEmpty((String) parameters.get("userName"))
				&& StringUtils.isEmpty((String) parameters.get("mobileNumber"))
				&& StringUtils.isEmpty((String) parameters.get("email"))
				&& StringUtils.isEmpty((String) parameters.get("memberShipCard"))) {
			return null;
		}
		if (StringUtils.isNotEmpty((String) parameters.get("userName"))) {
			parameters.put("userName", ((String) parameters.get("userName")).toLowerCase());
		}
		if (StringUtils.isNotEmpty((String) parameters.get("email"))) {
			parameters.put("email", ((String) parameters.get("email")).toLowerCase());
		}
		return super.queryForList("USER_USER.queryUserUserKeyWordsLike", parameters);
	}
	/**
	 * 根据查询条件查找用户列表
	 * @param parameters  查询条件
	 * @return 用户列表
	 */
	@SuppressWarnings("unchecked")
	public List<UserUser> query(final Map<String, Object> parameters) {
		if (StringUtils.isNotEmpty((String) parameters.get("userName"))) {
			parameters.put("userName", ((String) parameters.get("userName")).toLowerCase());
		}
		if (StringUtils.isNotEmpty((String) parameters.get("likeUserName"))) {
			parameters.put("likeUserName", ((String) parameters.get("likeUserName")).toLowerCase());
		}
		if (StringUtils.isNotEmpty((String) parameters.get("email"))) {
			parameters.put("email", ((String) parameters.get("email")).toLowerCase());
		}
		return super.queryForList("USER_USER.query", parameters);
	}
	
	/**
	 * 根据用户标识的集合查找用户信息
	 * @param idList 用户标识的集合
	 * @return 用户信息集合
	 */
	@SuppressWarnings("unchecked")
	public List<UserUser> getUsersListByPkList(final List<String> idList)
	{
		List<UserUser> userList = new ArrayList<UserUser>();
		if(idList != null && idList.size() > 0)
		{
			String idsSql = "";
			for(int i = 0; i < idList.size(); i++)
			{
				idsSql += "\'"+idList.get(i)+"\'";
				if(i < idList.size()-1)
				{
					idsSql += ",";
				}
			}
			userList =super.queryForList("USER_USER.queryUsersListByPkList",idsSql);
		}
		return userList;
	}	
	
	/**
	 * 用户会员等级升级
	 * @param userNo  用户标识
	 * @param grade 等级
	 */
	public void userMemberGradeUpdate(final Long userId, final String grade) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("grade", grade);
		super.update("USER_USER.userMemberGradeUpdate", param);
	}
	
	/**
	 * 根据查询条件查询的用户是否存在
	 * @param parameters 查询条件
	 * @return 是否存在
	 */
	public boolean checkUsersForObject(final Map<String, Object> parameters) {
		if (StringUtils.isNotEmpty((String) parameters.get("userName"))) {
			parameters.put("userName", ((String) parameters.get("userName")).toLowerCase());
		}
		if (StringUtils.isNotEmpty((String) parameters.get("email"))) {
			parameters.put("email", ((String) parameters.get("email")).toLowerCase());
		}
		Integer num = (Integer) super.queryForObject("USER_USER.count", parameters);
		if(num > 0){
			return true;
		}else {
			return false;
		}
	}

	
	/**
	 * 刷新用户积分
	 * @param userNo 用户标识
	 */
	public void refreshUsersPoint(final Long userId) {
		if (null == userId) {
			return;
		}
		super.update("USER_USER.updatePoint", userId);
	}
	
	/**
	 * @deprecated  ???
	 * 获取用户最后登录时间
	 * @param userId 用户标识
	 * @return 用户最后登录时间
	 */
	public Date getUserLastLoginDate(final String userNo) {
		return (Date) super.queryForObject("USER_USER.selectUserLastLoginDate", userNo);
	}

    /**
     * 获取用户正在途中的钱
     * @param userId 用户标识
     * @return 途中的钱
     */
	@SuppressWarnings("unchecked")
	public Long getTotalProcessCash(final String userId) {
		List<Long> processCashList = (List<Long>) super
				.queryForObject("USER_USER.processCash", userId);
		if (processCashList == null) {
			return 0L;
		}
		if (processCashList.size() == 0) {
			return 0L;
		} else {
			long total = 0L;
			for (Long cash : processCashList) {
				total += cash;
			}
			return total;
		}
	}

    /**
     * 在指定日期内普通等级即将过期的用户顺移到下一年
     * @param date 会员等级过期日期
     */
	public void expirationDateToNextYearForNormalGrade(Date date){
		if (null == date) {
			return;
		}
		super.update("USER_USER.expDateToNextYearForNormalGrade", date);
	}
	
	@SuppressWarnings("unchecked")
	public  List<UserUser> queryUserUserByUserId(List<Long> ids) {
		if (null != ids && !ids.isEmpty()) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ids", ids);
			return super.queryForList("USER_USER.queryUserUserByUserId", parameters);
		} else {
			return new ArrayList<UserUser>();
		}
	}
	
	/**
	 * 手机解绑
	 * @param userId 用户标识
	 */
	public void unBindingMobile(final Long userId) {
		super.update("USER_USER.unBindingMobileByUserId", userId);
	}
	
	/**
	 * 邮箱解绑
	 * @param userId 用户标识
	 */
	public void unBindingEmail(final Long userId) {
		super.update("USER_USER.unBindingEmailByUserId", userId);
	}	
}
