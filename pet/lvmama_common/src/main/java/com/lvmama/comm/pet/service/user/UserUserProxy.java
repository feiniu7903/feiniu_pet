package com.lvmama.comm.pet.service.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.user.AnnHongbaoHj;
import com.lvmama.comm.pet.po.user.Annhongbao;
import com.lvmama.comm.pet.po.user.Annliping;
import com.lvmama.comm.pet.po.user.Annwinnerslist;
import com.lvmama.comm.pet.po.user.UserCertCode;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserGradeLog;
import com.lvmama.comm.pet.po.user.UserTopic;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.UserPointLogWithDescription;
import com.lvmama.comm.vo.Constant.USER_MEMBER_GRADE;

public interface UserUserProxy {
	/**
	 * 用户标识
	 */
	public static enum USER_IDENTITY_TYPE {
		/**
		 * 用户名
		 */
		USER_NAME,
		/**
		 * 手机
		 */
		MOBILE,
		/**
		 * 邮箱地址
		 */
		EMAIL,
		/**
		 * 会员卡
		 */
		MEMBERSHIPCARD,
		/**
		 * 随视微信id
		 */
		WECHAT_ID
	}
	
	/**
	 * 生成默认用户
	 * @return 默认用户
	 */
	UserUser genDefaultUser();
	
	/**
	 * 生成用户信息
	 * @param parameters 用户的相关信息
	 * @return 生成的用户
	 */
	UserUser generateUsers(final Map<String, Object> parameters);	
	
	/**
	 * 注册用户
	 * @param user 需要注册的用户
	 * @return 用户信息
	 */
	UserUser register(final UserUser user);
	
	/**
	 * 注册来自于第三方合作单位的用户
	 * @param user 需要注册的用户
	 * @param cooperationUsers  第三方合作单位信息
	 * @return 用户信息
	 */
	UserUser registerUserCooperationUser(UserUser user, UserCooperationUser cooperationUsers);
	
	/**
	 * 修改用户信息
	 * @param user 新的用户信息
	 */
	UserUser update(UserUser user);	

	/**
	 * 根据用户标识查找用户
	 * @param id 32位用户标识
	 * @return 用户信息
	 */
	UserUser getUserUserByPk(Long id);
	
	/**
	 * 根据用户的32位标识查找用户
	 * @param id 32位用户标识
	 * @return 用户信息
	 */
	UserUser getUserUserByUserNo(String id);
	
	/**
	 * 根据条件查找多个用户
	 * @param param 参数列表
	 * @return 用户列表
	 */
	List<UserUser> getUsers(final Map<String, Object> param);	
	
	/**
	 * 根据手机号/Email/用户名/会员卡号 获取用户信息
	 * @param value 手机号/Email/用户名/会员卡号
	 * @return 用户信息
	 */
	UserUser getUsersByMobOrNameOrEmailOrCard(String value);
	/**
	 * 登录查询账户
	 * @param account 手机号/Email/用户名/会员卡号
	 * @param MD5密码
	 * @return 用户信息 
	 */
	UserUser queryForLogin(final String account,final String md5password);
	/**
	 * 指定用户的手机号/Email/用户名/会员卡号 获取用户信息
	 * @param value
	 * @param type
	 * @return 用户信息
	 */
	UserUser getUsersByIdentity(final String value,USER_IDENTITY_TYPE type);
		
	/**
	 * 根据用户32位标识获取用户即将过期的积分
	 * @param id 32位用户标识
	 * @return 即将过期的积分值
	 */
	Long getAboutToExpiredUsersPoint(Serializable id);
	
	/**
	 * 根据用户32位标识获取用户使用过的积分
	 * @param id
	 * @return
	 */
	public Long getUsedUsersPoint(final Serializable id);
		
	/**
	 * 获取用户积分日志总数
	 * @param parameters 参数列表
	 * @return 日志总数
	 */
	Long getCountUserPointLog(Map<String, Object> parameters);
	
	/**
	 * 查询用户的积分记录
	 * @param parameters 参数列表
	 * @return 记录列表
	 */
	List<UserPointLogWithDescription> getPointLog(Map<String, Object> parameters);	
	
	/**
	 * 获取用户积分总数 
	 * @param parameters 参数列表
	 * @return 积分总数
	 */
	Long getSumUserPoint(Map<String, Object> parameters);
	
	/**
	 * 用户添加积分操作
	 * @param userNo 用户标识
	 * @param ruleId 添加积分规则
	 * @param point 积分数
	 * @param memo 备注
	 */
	void addUserPoint(Long userId, String ruleId, Long point, String memo);
	
	/**
	 * 用户标识是否唯一
	 * @param type 用户标识的类型。包括用户名，手机号和电子邮箱
	 * @param value 用户标识
	 * @return 唯一则返回真，否则返回假
	 */
	boolean isUserRegistrable(final USER_IDENTITY_TYPE type, final String value);		
	
	/**
	 * 查询除普通会员以外的会员等级到期的用户列表
	 * @param date 到期日期
	 * @return 用户列表
	 * 需要强调的是，原本此函数应该是查询所有会员等级到期的用户列表，但处于数据库性能及socket传输
	 * 性能的问题，传输大量普通会员参与简单的逻辑操作无实际意义。故权衡设计此方法，并请将此方法与
	 * expirationDateToNextYearForNormalGrade(Date date)搭配使用
	 */
	List<UserUser> getUsersByMemberGradeDateDue(Date date);
	
	/**
	 * 延长普通会员的有效期至下一年
	 * @param date 到期日期
	 * 此方法处于getUsersByMemberGradeDateDue(Date date)的性能优化而设计的，并应与其搭配使用。
	 */
	void expirationDateToNextYearForNormalGrade(Date date);
	
	/**
	 * 系统自动对用户等级进行调级
	 * @param user 用户
	 * @param grade 调级后等级
	 * @param amount 调级金额
	 */
	void updateMemberGradeBySystem(UserUser user, USER_MEMBER_GRADE grade, float amount);
	
    /**
     * 查询用户等级日志
     * @param userId 用户标识
     * @return 用户等级日志
     */
	List<UserGradeLog> queryUserGradeLogs(Serializable userId);
	
    /**
     * 调整用户会员等级
     * @param user 用户
     * @param grade 调整后的等级
     * @param memo 备注
     * @param operator 操作者
     */
	void updateMemberGradeByManual(UserUser user, USER_MEMBER_GRADE grade, String memo, String operator);

	/**
	 * 通过ID NO LIST获取USER LIST
	 * @param idList
	 * @return
	 */
	List<UserUser> getUsersListByUserNoList(List<String> idList);
	
	/**
	 * 用户登录判断，仅供wap端使用
	 * @deprecated 它真的需要吗？
	 * @param username
	 * @param password
	 * @return
	 */
	UserUser login(String username, String password);
	
	
	/**
	 * 保存激活码
	 * @param Code
	 */
	void saveUserCertCode(UserCertCode userCertCode);
	
	
	/**
	 * 删除激活码
	 * @param Code
	 */
	void deleteUserCertCode(Map<String, Object> params);
	
	/**
	 * 查询激活记录
	 * @param type 激活类型
	 * @param code 激活码
	 * @param autoDelete 是否需要删除此条记录。如果设置为true，那么此条激活记录将会被删除
	 * @return 返回指定的激活记录。
	 */
	UserCertCode queryUserCertCode(USER_IDENTITY_TYPE type, String code, boolean autoDelete);
	
	/**
	 * 验证identityTarget和验证码是否匹配
	 * @param type
	 * @param code
	 * @param identityTarget
	 * @return 是否请求的验证信息是否正确，需要注意的是，一旦返回true，那么验证信息将不会再被保存
	 */
	boolean validateAuthenticationCode(USER_IDENTITY_TYPE type, String code, String identityTarget);
	

	/**
	 * 
	 * @param userIdList
	 * @return
	 */
	List<UserUser> queryUserUserByUserId(List<Long> userIdList);
	
	/**
	 * 绑定用户和会员卡的关系
	 * @ TODO 此类应该存在，只是由于优惠券尚未迁移需要使用远程调用，故放在userClient类中实现发放优惠券的功能。
	 * @param userId 用户标识
	 * @param code 会员卡号
	 * @return 绑定是否成功
	 */
	boolean bindingUserAndMembershipCardCode2(Long userId, String code);
	
	/**
	 * 根据email地址返回email地址所在的网页登陆地址
	 * @param email 
	 * @return 网页登陆地址
	 */
	String getMailHostAddress(String email);
	/**
	 * 根据用户的userName%,mobileNumber%,email%,memberShipCard查询用户列表
	 * 
	 * @author: ranlongfei 2012-8-29 下午4:27:36
	 * @param params
	 * @return
	 */
	List<UserUser> queryUserUserKeyWordsLike(Map<String, Object> params);
	
	/**
	 * 解绑手机
	 * @param userId 用户标识
	 */
	void unBindingMobile(final Long userId);
	
	/**
	 * 解绑邮箱
	 * @param userId 用户标识
	 */
	public void unBindingEmail(final Long userId);

	void insertWinnerslist(Annwinnerslist annList);

	List<Annwinnerslist> queryWinnerslistBylimit(long size);

	Annliping queryAnnliping(Long lpDengjiang);

	void updateWinnerslist(Annwinnerslist annlist);

	List<Annwinnerslist> queryWinnerslistByUserId(Long userId);

	UserTopic queryUserTopicById(Long userId);	
	
	/**
	 * 查询红包
	 * @param map
	 * @return
	 */
	List<Annhongbao> queryAnnhongbaoByParam(Map<String, Object> map);
 
	/**
	 * 更改红包
	 * @param ann
	 * @return
	 */
	int updateAnnhongbao(Annhongbao ann);
	
	public List<AnnHongbaoHj> queryAnnHongbaoHj(Map<String, Object> param);

	Long selectSumMoneyByUserId(Map<String, Object> param);

	void saveAnnHongbaoHJ(AnnHongbaoHj annHongbaoHj);


	void updateReWriteShengyuJinE(Annhongbao nextann, Annhongbao annjieguo);


	boolean saveAnnHongbaoUpdate(AnnHongbaoHj annHongbaoHj,
			Map<String, Object> param, int i, long userId);

	/**
	 * 插入大转盘获奖，减去礼品库存
	 * @param annList
	 * @param jieguoDengji
	 * @param shuliang
	 * @return
	 */
	boolean insertMinDazhuanPanWinnerslist(Annwinnerslist annList,
			int jieguoDengji, int shuliang);
	
	public Annliping queryAnnlipingByParam(Map<String, Object> param);

	List<Annwinnerslist> queryWinnerslistByMap(Map<String, Object> param);

	List<Annwinnerslist> queryWinnerslistByUserIdAndProject(Long userId,
			String projectName);

	boolean insertZTchoujiang(Annwinnerslist annwinner, String lpType,
			UserUser user, int shuliang);
}
