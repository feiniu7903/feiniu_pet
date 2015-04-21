package com.lvmama.pet.user.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.user.AnnHongbaoHj;
import com.lvmama.comm.pet.po.user.Annhongbao;
import com.lvmama.comm.pet.po.user.Annliping;
import com.lvmama.comm.pet.po.user.Annwinnerslist;
import com.lvmama.comm.pet.po.user.UserCertCode;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserGradeLog;
import com.lvmama.comm.pet.po.user.UserPointLog;
import com.lvmama.comm.pet.po.user.UserPointRule;
import com.lvmama.comm.pet.po.user.UserTopic;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.pet.vo.UserPointLogWithDescription;
import com.lvmama.comm.utils.ActivityUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.mark.dao.MarkMembershipCardCodeDAO;
import com.lvmama.pet.mark.dao.MarkMembershipCardDAO;
import com.lvmama.pet.mark.dao.MarkMembershipCardDiscountDAO;
import com.lvmama.pet.user.dao.AnnHongbaoHjDAO;
import com.lvmama.pet.user.dao.AnnwinnerslistDAO;
import com.lvmama.pet.user.dao.UserCertCodeDAO;
import com.lvmama.pet.user.dao.UserCooperationUserDAO;
import com.lvmama.pet.user.dao.UserGradeLogDAO;
import com.lvmama.pet.user.dao.UserPointLogDAO;
import com.lvmama.pet.user.dao.UserPointRuleDAO;
import com.lvmama.pet.user.dao.UserUserDAO;


class UserUserServiceImpl implements UserUserService {
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory.getLog(UserUserServiceImpl.class);
	
	@Autowired
	private UserUserDAO userUserDAO;
	@Autowired
	private UserPointLogDAO userPointLogDAO;
	@Autowired
	private UserGradeLogDAO userGradeLogDAO;
	@Autowired
	private UserCooperationUserDAO userCooperationUserDAO;
	@Autowired
	private UserCertCodeDAO userCertCodeDAO;
	@Autowired
	private UserPointRuleDAO userPointRulesDAO;

	@Autowired
	private MarkMembershipCardCodeDAO markMembershipCardCodeDAO;
	@Autowired
	private MarkMembershipCardDAO markMembershipCardDAO;
	@Autowired
	private MarkMembershipCardDiscountDAO markMembershipCardDiscountDAO;
	
	@Autowired
	private AnnwinnerslistDAO annwinnerslistDAO;
	
	@Autowired
	private AnnHongbaoHjDAO annHongbaoHjDAO;
	
	
	
	@Override
	public List<UserUser> queryUserUserKeyWordsLike(Map<String, Object> params) {
		return userUserDAO.queryUserUserKeyWordsLike(params);
	}

	@Override
	public UserUser getUserUserByPk(Long id) {
		debug("根据用户标识查找用户，标识为:" + id);
		return userUserDAO.getUsersByPk(id);
	}

	@Override
	public UserUser getUserUserByUserNo(String id) {
		debug("根据用户32为标识查找用户，32为标识为:" + id);
		return userUserDAO.getUsersByUserNo(id);
	}
	@Override
	public UserUser getUsersByMobOrNameOrEmailOrCard(final String account,USER_IDENTITY_TYPE type) {
		UserUser rtn=null;
		Map<String,Object> param=new HashMap<String, Object>();
		switch (type) {
		case EMAIL:
			param.put("email", account);
			break;
		case MEMBERSHIPCARD:
			param.put("memberShipCard", account);
			break;
		case MOBILE:
			param.put("mobileNumber", account);
			break;
		case USER_NAME:
			param.put("userName", account.toLowerCase());
			break;
		case WECHAT_ID:
			param.put("wechatId", account);
			break;
		default:
			return null;//表示类型不正确
		}
		
		param.put("isValid", "Y");
		rtn = userUserDAO.getUsersForObject(param);
		return rtn;
	}
	
	public UserUser queryForLogin(final String account,final String md5password){
		UserUser rtn = null;
		String loginType=null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		boolean user_validate_res = false;
		// 用户名查询
		if (!user_validate_res) {
			parameters.put("userPassword", md5password);
			parameters.put("userName", account.toLowerCase());
			parameters.put("isValid", "Y");
			user_validate_res = userUserDAO.queryForLoginValidate(parameters);
			loginType="USERNAME";
		}
		// 手机查询
		if (!user_validate_res) {
			parameters.clear();
			parameters.put("mobileNumber", account);
			parameters.put("userPassword", md5password);
			parameters.put("isValid", "Y");
			user_validate_res = userUserDAO.queryForLoginValidate(parameters);
			loginType="MOBILE";
		}
		// 邮箱查询
		if (!user_validate_res) {
			parameters.clear();
			parameters.put("email", account);
			parameters.put("userPassword", md5password);
			parameters.put("isValid", "Y");
			user_validate_res = userUserDAO.queryForLoginValidate(parameters);
			loginType="MAIL";
		}

		// 会员卡查询
		if (!user_validate_res) {
			parameters.clear();
			parameters.put("memberShipCard", account);
			parameters.put("userPassword", md5password);
			parameters.put("isValid", "Y");
			user_validate_res = userUserDAO.queryForLoginValidate(parameters);
			loginType="MBCARD";
		}
		
		// 随视微信id查询
		if (!user_validate_res) {
			parameters.clear();
			parameters.put("wechatId", account);
			parameters.put("userPassword", md5password);
			parameters.put("isValid", "Y");
			user_validate_res = userUserDAO.queryForLoginValidate(parameters);
		}
		if(user_validate_res){
			rtn = userUserDAO.getUsersForObject(parameters);
			rtn.setLoginType(loginType);
		}
		return rtn;
	}
	@Override
	public UserUser getUsersByMobOrNameOrEmailOrCard(final String account) {
		UserUser rtn = null;
		Map<String, Object> parameters = new HashMap<String, Object>();

		// 用户名查询
		if (null == rtn) {
			parameters.put("userName", account.toLowerCase());
			parameters.put("isValid", "Y");
			rtn = userUserDAO.getUsersForObject(parameters);
		}

		// 手机查询
		if (null == rtn) {
			parameters.clear();
			parameters.put("mobileNumber", account);
			parameters.put("isValid", "Y");
			rtn = userUserDAO.getUsersForObject(parameters);
		}


		// 邮箱查询
		if (null == rtn) {
			parameters.clear();
			parameters.put("email", account);
			parameters.put("isValid", "Y");
			rtn = userUserDAO.getUsersForObject(parameters);
		}

		// 会员卡查询
		if (null == rtn) {
			parameters.clear();
			parameters.put("memberShipCard", account);
			parameters.put("isValid", "Y");
			rtn = userUserDAO.getUsersForObject(parameters);
		}
		
		// 随视微信id查询
		if (null == rtn) {
			parameters.clear();
			parameters.put("wechatId", account);
			parameters.put("isValid", "Y");
			rtn = userUserDAO.getUsersForObject(parameters);
		}
		
		return rtn;
	}

	@Override
	public Long getAboutToExpiredUsersPoint(Serializable id) {
		if (null == id) {
			return null;
		}
		Long usedPoint = getUsedUsersPoint(id);//用户已用积分
		usedPoint = null == usedPoint ? 0L : usedPoint;
		
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("userId", id);
		para.put("getPoint", true);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		para.put("createdDate", cal.getTime());
		Long getPointBeforeThisYear = userPointLogDAO.getSumUserPoint(para);//今年1月1日0时前获取的积分
		getPointBeforeThisYear = null == getPointBeforeThisYear ? 0L : getPointBeforeThisYear;
		
		//已使用积分优先消耗年底过期积分
		Long aboutToExpiredPoint = 0L;
		if(getPointBeforeThisYear >= usedPoint){
			aboutToExpiredPoint = getPointBeforeThisYear - usedPoint;
		}else{
			aboutToExpiredPoint = 0L;
		}
		return aboutToExpiredPoint;
	}
	
	@Override
	public Long getUsedUsersPoint(Serializable id) {
		if (null == id) {
			return null;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", id);
		parameters.put("usedPoint", true);
		Long usedPoint = userPointLogDAO.getSumUserPoint(parameters);
		if(usedPoint != null)
		{
			usedPoint = Math.abs(usedPoint);
		}
		else
		{
			usedPoint = 0l;
		}
		
		return usedPoint;
	}
	

	@Override
	public Long getCountUserPointLog(Map<String, Object> parameters) {
		if (null == parameters || null == parameters.get("userId")) {
			LOG.error("cann't find user id");
			return null;
		}
		return userPointLogDAO.getCountUserPointLog(parameters);
	}
	
	@Override
	public Long getSumUserPoint(final Map<String, Object> parameters) {
		return userPointLogDAO.getSumUserPoint(parameters);
	}

	@Override
	public List<UserPointLogWithDescription> getPointLog(
			Map<String, Object> parameters) {
		if (null == parameters 
				|| (null == parameters.get("userId") && null == parameters.get("userNo"))) {
			LOG.error("cann't find user id");
			return null;
		}
		return userPointLogDAO.getUserPointLog(parameters);
	}

	@Override
	public boolean isUserRegistrable(USER_IDENTITY_TYPE type, String value) {
		if (type.equals(USER_IDENTITY_TYPE.MOBILE)) {
			return isMobileRegistrable(value);
		}
		if (type.equals(USER_IDENTITY_TYPE.USER_NAME)) {
			return isNameRegistrable(value);
		}
		if (type.equals(USER_IDENTITY_TYPE.EMAIL)) {
			return isEmailRegistrable(value);
		}
		if (type.equals(USER_IDENTITY_TYPE.MEMBERSHIPCARD)) {
			return isMembershipRegistrable(value);
		}
		return false;

	}

	@Override
	public UserUser register(UserUser user) {
		if (null != user.getUserName()) {
			user.setUserName(user.getUserName().toLowerCase());
		}
		userUserDAO.save(user);
		return user;
	}
	
	@Override
	public UserUser registerUserCooperationUser(final UserUser user, final UserCooperationUser cooperationUsers) {
		userCooperationUserDAO.save(cooperationUsers);
		if("TENCENTQQ".equals(cooperationUsers.getCooperation())){
			//腾讯QQ使用特殊用户名
			user.setUserName(user.getUserName()+cooperationUsers.getCooperationUserId());
		} else if("CLIENT_ANONYMOUS".equals(cooperationUsers.getCooperation())){
			//客户端匿名账号使用特殊
			user.setUserName(user.getUserName()+cooperationUsers.getCooperationUserId());
		}
		register(user);
		cooperationUsers.setUserId(user.getId());
		userCooperationUserDAO.update(cooperationUsers);
		return user;
	}
	
	@Override
	public List<UserUser> query(Map<String, Object> params) {
		return userUserDAO.query(params);
	}

	@Override
	public void expirationDateToNextYearForNormalGrade(Date date) {
		userUserDAO.expirationDateToNextYearForNormalGrade(date);	
	}
	
	@Override
	public void updateMemberGrade(final Long userId, final String grade,
			final String memo, final String operater) {

		// 更新会员等级
		userUserDAO.userMemberGradeUpdate(userId, grade);

		// 写日志
		UserGradeLog newLog = new UserGradeLog();
		newLog.setOperateName(operater);
		newLog.setUserId(userId);
		newLog.setGrade(grade);
		newLog.setMemo(memo);
		userGradeLogDAO.insert(newLog);
	}
	
	@Override
	public List<UserGradeLog> queryUserGradeLogs(final Serializable userId) {
		return userGradeLogDAO.queryLogByUserId(userId);
	}
	
	@Override
	public UserUser update(UserUser user) {
		userUserDAO.update(user);
		return user;
	}
	
	@Override
	public void saveCertCode(final UserCertCode userCertCode) {
		if (null != userCertCode && null != userCertCode.getIdentityTarget() && null!= userCertCode.getType()) {
			userCertCodeDAO.insert(userCertCode);
		} else {
			debug("邮件激活信息为空或者无法找到用户信息,丢弃保存");
		}
	}
	
	@Override
	public UserCertCode queryCertCode(final String type, final String code) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("query secrect password for activting "+type +","+ code);
		}
		return userCertCodeDAO.queryByTypeAndCode(type, code);
	}
	
	@Override
	public UserCertCode queryCertCode(final String type,final String identityTarget,final String code) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("query secrect password for activting "+type +","+ code);
		}
		return userCertCodeDAO.queryByTypeAndIdentityAndCode(type,identityTarget,code);
	}
	
	
	@Override
	public UserCertCode queryCertIdentity(final String type, final String identityTarget) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("query secrect password for activting "+type +","+ identityTarget);
		}
		return userCertCodeDAO.queryByTypeAndIdentity(type, identityTarget);
	}
	

	public void deleteCertCode(final Map<String, Object> params) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("delete secrect password for activting ");
		}
		userCertCodeDAO.delete(params);
	}	

	@Override
	public void addUserPoint(final Long userId, final String ruleId, final Long point, final String memo) {
		if (null == userId || StringUtils.isEmpty(ruleId)) {
			LOG.info("userId或ruleId为空，无法继续执行后续操作");
			return;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("用户" + userId + "因为" + ruleId + "添加积分，默认添加积分为" + point);
		}

		UserUser user = userUserDAO.getUsersByPk(userId);

		if (null != user && LOG.isDebugEnabled()) {
			LOG.debug("find user:" + user);
		}
		if (null == user) {
			LOG.info(userId + "用户不存在，无法继续执行后续操作");
			return;
		}

		UserPointRule dupr = userPointRulesDAO.getRulesByID(ruleId);
		if (null != dupr && LOG.isDebugEnabled()) {
			LOG.debug("find user point rule:" + dupr);
		}
		if (null == dupr) {
			LOG.info(ruleId + "规则不存在或无效，无法继续执行后续操作");
			return;
		}
		
		UserPointLog userPointLog = new UserPointLog();
		if("POINT_FOR_LOGIN".equals(dupr.getRuleId()) && ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
			userPointLog.setPoint(2000l); //5周年固定2000分
		}else{
			userPointLog.setPoint(null == dupr.getPoint() ? (null == point ? 0L : point) : dupr.getPoint());
		}
		userPointLog.setRuleId(ruleId);
		userPointLog.setUserId(userId);
		userPointLog.setMemo(memo);

		userPointLogDAO.insert(userPointLog);
		userUserDAO.refreshUsersPoint(userPointLog.getUserId());
		/*LOG.info("用户" + userPointLog.getUserId() + "因为" + userPointLog.getRuleId()
				+ 	"添加了积分" + userPointLog.getPoint().longValue());*/
	}
	
	@Override
	public List<UserUser> getUsersListByUserNoList(final List<String> idList) {
		return userUserDAO.getUsersListByPkList(idList);
	}
	
	@Override
	public List<UserUser> queryUserUserByUserId(final List<Long> idList) {
		return userUserDAO.queryUserUserByUserId(idList);
	}
	
	@Override
	public boolean bindingUserAndMembershipCardCode(Long userId, String code) {
		if (StringUtils.isEmpty(code) || null == userId) {
			debug("MemberShip card code or userId is empty, cann't binding");
			return false;
		}
		
		if (isMembershipRegistrable(code)) {
			UserUser user =userUserDAO.getUsersByPk(userId);
			if (null == user
					|| null == user.getMemberShipCard()
					|| (null != user.getMemberShipCard() && !code.endsWith(user.getMemberShipCard()))) {
				debug("User havn't binded membership card!");
				return false;
			} else {
				markMembershipCardCodeDAO.use(code);
				markMembershipCardDAO.active(code);
				
				return true;
			}
		} else {
			debug("Cann't find valid membership card code!");
			return false;
		}
	}
	
	@Override
	public void unBindingMobile(final Long userId) {
		userUserDAO.unBindingMobile(userId);
	}
	
	@Override
	public void unBindingEmail(final Long userId) {
		userUserDAO.unBindingEmail(userId);
	}	
	

	/**
	 * 手机是否唯一
	 *
	 * @param mobile 手机号
	 * @return 唯一手机返回真，否则返回假
	 */
	private boolean isMobileRegistrable(final String mobile) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("检查手机" + mobile + "是否为可被注册的手机");
		}
		if (!StringUtil.validMobileNumber(mobile)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(mobile + "未能通过正则表达式检验，将返回false");
			}
			return false;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug(mobile + "通过了正则表达式，继续以下的校验");
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("mobileNumber", mobile);
		parameters.put("isValid", "Y");
		return !userUserDAO.checkUsersForObject(parameters);
	}

	/**
	 * 用户名是否唯一
	 *
	 * @param account 用户名
	 * @return 唯一返回真，否则返回假
	 */
	private boolean isNameRegistrable(final String account) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("检查昵称" + account + "是否为可被注册的昵称");
		}
		if (!StringUtil.validNickname(account)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(account + "未能通过敏感词检验，将返回false");
			}
			return false;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug(account + "通过了敏感词检验，继续以下的校验");
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userName", account.toLowerCase());
		parameters.put("isValid", "Y");
		return !userUserDAO.checkUsersForObject(parameters);
	}

	/**
	 * 邮件地址是否唯一
	 *
	 * @param email 邮件地址
	 * @return 唯一返回真，否则返回假
	 */
	private boolean isEmailRegistrable(final String email) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("检查邮件地址" + email + "是否为可被注册的邮箱");
		}
		if (!StringUtil.validEmail(email)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(email + "未能通过正则表达式检验，将返回false");
			}
			return false;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug(email + "通过了正则表达式，继续以下的校验");
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("email", email);
		parameters.put("isValid", "Y");
		return !userUserDAO.checkUsersForObject(parameters);
	}

	/**
	 * 会员卡是否唯一且可用
	 *
	 * @param membershipCard 会员卡
	 * @return 唯一返回真，否则返回假
	 */
	private boolean isMembershipRegistrable(final String membershipCard) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("检查会员卡" + membershipCard + "是否为可使用");
		}
		if (!StringUtil.validMembershipCard(membershipCard)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(membershipCard + "未能通过正则表达式检验，将返回false");
			}
			return false;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug(membershipCard + "通过了正则表达式，继续以下的校验");
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cardCode", membershipCard);
		parameters.put("used", "FALSE");
		Long count = markMembershipCardCodeDAO.count(parameters);
		if (null != count && count.longValue() > 0L) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 打印调试信息
	 * @param message
	 */
	private void debug(final String message) {
		if (!StringUtils.isEmpty(message) && LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}

	@Override
	public void insertWinnerslist(Annwinnerslist annList) {
		annwinnerslistDAO.save(annList);
	}

	@Override
	public List<Annwinnerslist> queryWinnerslistByparam(Map map) {
	 return	annwinnerslistDAO.queryAnnwinnerslist(map);
	}
	@Override
	public Annliping queryAnnliping(Long lpDengjiang) {
		return	annwinnerslistDAO.queryAnnliping(lpDengjiang);
	}

	@Override
	public void updateWinnerslist(Annwinnerslist annlist) {
		 	annwinnerslistDAO.updateWinnerslist(annlist);
	}
	@Override
	public UserTopic queryUserTopicById(Long userId){
		return (UserTopic) annwinnerslistDAO.queryUserTopicById(userId);
	}

	@Override
	public List<Annhongbao> queryAnnhongbaoByParam(Map<String, Object> map) {
 		return (List<Annhongbao>)annwinnerslistDAO.queryAnnhongbaoByParam(map);
	}

	@Override
	public int updateAnnhongbao(Annhongbao ann) {
		return annwinnerslistDAO.updateAnnhongbao(ann);
	}
	@Override
	public int minUpdateAnnHongbao(Map ann) {
		return annwinnerslistDAO.minUpdateAnnHongbao(ann);
	}
	
	@Override
	public List<AnnHongbaoHj> queryAnnHongbaoHj(Map<String, Object> param) {
		return annHongbaoHjDAO.query(param);
	}

	@Override
	public Long selectSumMoneyByUserId(Map<String, Object> param) {
		return annHongbaoHjDAO.selectSumMoneyByUserId(param);
	}

	@Override
	public void saveAnnHongbaoHJ(AnnHongbaoHj annHongbaoHj) {
		annHongbaoHjDAO.saveAnnHongbaoHJ(annHongbaoHj);
	}

	@Override
	public int minUpdateAnnliping(Map param) {
		return annwinnerslistDAO.minUpdateAnnliping(param);
	}

	@Override
	public Annliping queryAnnlipingByParam(Map<String, Object> param) {
		return annwinnerslistDAO.queryAnnlipingByParam(param);
	}

	@Override
	public int updateAnnliping(Map<String, Object> param) {
		return annwinnerslistDAO.updateAnnliping(param);
	}
}
