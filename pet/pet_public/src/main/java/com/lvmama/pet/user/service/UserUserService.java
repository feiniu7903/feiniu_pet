package com.lvmama.pet.user.service;

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
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.pet.vo.UserPointLogWithDescription;

/**
 * 用户服务的逻辑层类
 * @author Brian
 *
 */
public interface UserUserService {
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

	UserUser getUsersByMobOrNameOrEmailOrCard(String value,USER_IDENTITY_TYPE type);
		
	/**
	 * 根据用户32位标识获取用户即将过期的积分
	 * @param id 32位用户标识
	 * @return 即将过期的积分值
	 */
	Long getAboutToExpiredUsersPoint(Serializable id);
	
	/**
	 * 获得用户已使用积分
	 * @param id
	 * @return
	 */
	Long getUsedUsersPoint(Serializable id); 
		
	/**
	 * 获取用户积分日志总数
	 * @param parameters 参数列表
	 * @return 日志总数
	 */
	Long getCountUserPointLog(final Map<String, Object> parameters);
	
	/**
	 * 获取用户积分总数
	 * @param parameters 查询条件
	 * @return 积分总数
	 */
	Long getSumUserPoint(final Map<String, Object> parameters);
	
	/**
	 * 查询用户的积分记录
	 * @param parameters 参数列表
	 * @return 记录列表
	 */
	List<UserPointLogWithDescription> getPointLog(final Map<String, Object> parameters);	
	
	/**
	 * 判断用户是否存在
	 * @param type 用户标识的类型。包括用户名，手机号和电子邮箱
	 * @param value 用户标识
	 * @return 唯一则返回真，否则返回假
	 */
	boolean isUserRegistrable(final USER_IDENTITY_TYPE type, final String value);	
	
	/**
	 * 注册用户
	 * @param user 需要注册的用户
	 * @return 用户信息
	 */
	UserUser register(final UserUser user);
	
	/**
	 * 注册第三方公司的用户
	 * @param user
	 * @param cooperationUsers
	 * @return
	 */
	UserUser registerUserCooperationUser(UserUser user, UserCooperationUser cooperationUsers);
	
	/**
	 * 延长普通会员的有效期至下一年
	 * @param date 到期日期
	 * 此方法处于getUsersByMemberGradeDateDue(Date date)的性能优化而设计的，并应与其搭配使用。
	 */
	void expirationDateToNextYearForNormalGrade(Date date);
	
	/**
	 * 根据查询参数获取用户列表
	 * @param params 查询参数
	 * @return 用户列表
	 */
	List<UserUser> query(Map<String, Object> params);
	/**
	 * 根据用户的userName%,mobileNumber%,email%,memberShipCard查询用户列表
	 * 
	 * @author: ranlongfei 2012-8-29 下午4:16:23
	 * @param params
	 * @return
	 */
	List<UserUser> queryUserUserKeyWordsLike(Map<String, Object> params);

	/**
	 * 变更用户等级，并记录日志
	 * @param userId 用户标识
	 * @param grade 等级
	 * @param memo 备注
	 * @param operater 操作人
	 */
	void updateMemberGrade(final Long userId, final String grade, final String memo, final String operater);
	
	/**
	 * 更新用户信息
	 * @param user 用户信息
	 * @return 更新后的用户信息
	 */
	UserUser update(UserUser user); 
	
	/**
	 * 保存用户的激活信息(手机或邮件或未来各种)
	 * @param userEmailCode  激活信息
	 */
	void saveCertCode(UserCertCode userCertCode);
	
	/**
	 * 根据加密码找到所需的激活码的记录(手机或邮件或未来各种)
	 * @param type 验证类型：EMAIL/MOBILE
	 * @param code 加密码
	 * @return 激活记录
	 */
	UserCertCode queryCertCode(String type, String code);
	
	/**
	 * 基于用户身份信息查找激活记录
	 */
	public UserCertCode queryCertIdentity(final String type, final String identityTarget);
	
	public UserCertCode queryCertCode(final String type,final String identityTarget,final String code);
	
	/**
	 * 删除用户的激活码记录
	 * @param type
	 * @param code
	 */
	void deleteCertCode(Map<String, Object> params);
	
	/**
	 * 查询用户等级日志
	 * @param userId  用户标识
	 * @return 用户等级日志
	 */
	List<UserGradeLog> queryUserGradeLogs(Serializable userId);
	
	/**
	 * 添加用户积分
	 * @param userNo
	 * @param ruleId
	 * @param point
	 * @param memo
	 */
	void addUserPoint(Long userNo, String ruleId, Long point, String memo);
	
	/**
	 * 通过ID NO LIST获取USER LIST
	 * @param idList
	 * @return
	 */
	List<UserUser> getUsersListByUserNoList(final List<String> idList);
	
	List<UserUser> queryUserUserByUserId(final List<Long> idList);
	
    /**
	 * 绑定用户和会员卡号
	 * @param userId 用户标识
	 * @param code 会员卡号
     * @return 是否绑定成功
     */
	boolean bindingUserAndMembershipCardCode(Long userId, String code);
	
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
	
	public void insertWinnerslist(final Annwinnerslist annList);
	public List<Annwinnerslist> queryWinnerslistByparam(final Map map);

	public Annliping queryAnnliping(Long lpDengjiang);

	void updateWinnerslist(Annwinnerslist annlist);

	UserTopic queryUserTopicById(Long userId);

	List<Annhongbao> queryAnnhongbaoByParam(Map<String, Object> map);

	int updateAnnhongbao(Annhongbao ann);
	
	public List<AnnHongbaoHj> queryAnnHongbaoHj(Map<String, Object> param);
	
	public Long selectSumMoneyByUserId(Map<String,Object> param);
	
	public void saveAnnHongbaoHJ(AnnHongbaoHj annHongbaoHj);

	/**
	 * 秒杀控制发红包
	 * @param ann
	 * @return
	 */
	int minUpdateAnnHongbao(Map ann);

	int minUpdateAnnliping(Map param);
	
	public Annliping queryAnnlipingByParam(Map<String, Object> param);
	
	public int updateAnnliping(Map<String,Object> param);
}
