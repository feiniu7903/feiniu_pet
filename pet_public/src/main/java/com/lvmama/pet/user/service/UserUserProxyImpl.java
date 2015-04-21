package com.lvmama.pet.user.service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.user.AnnHongbaoHj;
import com.lvmama.comm.pet.po.user.Annhongbao;
import com.lvmama.comm.pet.po.user.Annliping;
import com.lvmama.comm.pet.po.user.Annwinnerslist;
import com.lvmama.comm.pet.po.user.UserCertCode;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserGradeLog;
import com.lvmama.comm.pet.po.user.UserTopic;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.mark.MarkMembershipCardDiscountService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pub.ComEmailTemplateService;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.UserPointLogWithDescription;
import com.lvmama.comm.utils.MemberGradeUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.USER_MEMBER_GRADE;

/**
 * 用户逻辑层的代理类
 *
 * @author Brian
 * 
 */
class UserUserProxyImpl implements UserUserProxy {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(UserUserProxyImpl.class);
	@Autowired
	private UserUserService userUserService;
	
	@Autowired
	private SmsRemoteService smsRemoteService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ComSmsTemplateService comSmsTemplateService;
	
	@Autowired
	private ComEmailTemplateService comEmailTemplateService;
	
	@Autowired
	private MarkMembershipCardDiscountService markMembershipCardDiscountService;
	
	@Autowired
	private UserActionCollectionService userActionCollectionService;
	
	@Autowired
	private CashAccountService cashAccountService;
	@Autowired
	private MarkCouponService markCouponService;

	@Override
	public UserUser getUserUserByPk(final Long id) {
		if (null == id) {
			debug("用户标识为空，不予处理");
			return null;
		}
 		UserUser user = userUserService.getUserUserByPk(id);
 		user = bindBonusInfo(user);
 		return user;
	}
	
	@Override
	public UserUser getUserUserByUserNo(final String id) {
		if (StringUtils.isEmpty(id)) {
			debug("用户标识为空，不予处理");
			return null;
		}
 		UserUser user = userUserService.getUserUserByUserNo(id);
 		user = bindBonusInfo(user);
 		return user;
	}

	@Override
	public UserUser getUsersByMobOrNameOrEmailOrCard(final String value) {
		UserUser user = null;
		if (StringUtils.isEmpty(value)) {
			debug("用户账号为空，不予处理");
			return user;
		} else {
			user = userUserService.getUsersByMobOrNameOrEmailOrCard(value);
	 		user = bindBonusInfo(user);
	 		return user;
		}
	}
	@Override
	public UserUser queryForLogin(final String account,final String md5password){
		UserUser user = null;
		if (StringUtils.isEmpty(account)|| StringUtils.isEmpty(md5password)) {
			debug("用户账号/密码为空，不予处理");
			return user;
		} else {
			user = userUserService.queryForLogin(account, md5password);
	 		user = bindBonusInfo(user);
	 		return user;
		}
	}
	@Override
	public UserUser getUsersByIdentity(String value, USER_IDENTITY_TYPE type) {
		if(StringUtils.isEmpty(value)||type==null){
			debug("参数为空");
			return null;
		}else{
			UserUser user = userUserService.getUsersByMobOrNameOrEmailOrCard(value,type);
			user = bindBonusInfo(user);
			return user;
		}
	}

	public void setUserUserService(final UserUserService userUserService) {
		this.userUserService = userUserService;
	}

	@Override
	public Long getAboutToExpiredUsersPoint(final Serializable id) {
		if (null == id) {
			debug("用户账号为空，不予处理");
			return null;
		} else {
			return userUserService.getAboutToExpiredUsersPoint(id);
		}		
	}
	
	@Override
	public Long getUsedUsersPoint(final Serializable id) {
		if (null == id) {
			debug("用户账号为空，不予处理");
			return null;
		} else {
			return userUserService.getUsedUsersPoint(id);
		}		
	}

	@Override
	public Long getCountUserPointLog(final Map<String, Object> parameters) {
		return userUserService.getCountUserPointLog(parameters);
	}

	@Override
	public List<UserPointLogWithDescription> getPointLog(
			final Map<String, Object> parameters) {
		return userUserService.getPointLog(parameters);
	}

	@Override
	public boolean isUserRegistrable(final USER_IDENTITY_TYPE type, final String value) {
		return userUserService.isUserRegistrable(type, value);
	}

	@Override
	public UserUser genDefaultUser() {
		UserUser users = null;
		do {
			users = UserUserUtil.genDefaultUser();
		} while (!isUserRegistrable(USER_IDENTITY_TYPE.USER_NAME,
				users.getUserName()));
		return users;
	}

	@Override
	public UserUser generateUsers(final Map<String, Object> parameters) {
		UserUser user = null;
		
		if (null != parameters.get("mobileNumber")) {
			String mobileNumber = (String)parameters.get("mobileNumber");			
			user = UserUserUtil.genDefaultUserByMobile(mobileNumber);
			debug("直接使用手机号产生的用户, mobileNumber=" + mobileNumber);
		} else {
			user = genDefaultUser();
		}
		
		try {
			for (Entry<String, Object> entry : parameters.entrySet()) {
				debug("尝试将" + entry.getKey() + "设置成" + entry.getValue());
				BeanUtils.setProperty(user, entry.getKey(), entry.getValue());
			}
		} catch (IllegalAccessException iae) {
			LOG.warn("Exception：" + iae.getMessage());
			return null;
		} catch (InvocationTargetException ite) {
			LOG.warn("Exception：" + ite.getMessage());
			return null;
		}
		return user;
	}

	@Override
	public UserUser register(final UserUser user) {
		UserUser u = userUserService.register(user);
		userActionCollectionService.save(u.getId(), user.getRegisterIp(),user.getRegisterPort(),"REGISTER", null);
		cashAccountService.createCashAccountByUserId(user.getId());//生成存款账户
		return u;
	}
	
	@Override
	public UserUser registerUserCooperationUser(final UserUser user, final UserCooperationUser cooperationUsers) {
		UserUser genUser = userUserService.registerUserCooperationUser(user, cooperationUsers);
		cashAccountService.createCashAccountByUserId(user.getId());//生成存款账户
		return genUser;
	}
	
	@Override
	public Long getSumUserPoint(final Map<String, Object> parameters) {
		return userUserService.getSumUserPoint(parameters);
	}

	@Override
	public void addUserPoint(final Long userId, final String ruleId, final Long point,
			final String memo) {
		userUserService.addUserPoint(userId, ruleId, point, memo);	
	}	

	@Override
	public List<UserUser> getUsersByMemberGradeDateDue(final Date date) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer gradeBuff = new StringBuffer();
		for (USER_MEMBER_GRADE grade : USER_MEMBER_GRADE.values()) {
			if (!USER_MEMBER_GRADE.NORMAL.name().equals(grade.name())) {
				gradeBuff.append("\'" + grade.name() + "\',");
			}
		}
		if (gradeBuff.length() > 0) {
			gradeBuff.setLength(gradeBuff.length() - 1);
			params.put("grades", gradeBuff.toString());
		}
		if (date != null) {
			params.put("levelValidityDateEnd", date);
		}
		return userUserService.query(params);
	}

	@Override
	public void expirationDateToNextYearForNormalGrade(final Date date) {
		userUserService.expirationDateToNextYearForNormalGrade(date);
	}

	@Override
	public void updateMemberGradeBySystem(final UserUser user,
			final USER_MEMBER_GRADE grade, final float amount) {
		USER_MEMBER_GRADE currentGrade =  MemberGradeUtil.getUserMemberGrade(user.getGrade());

		userUserService.updateMemberGrade(user.getId(), grade.getGrade(), null, "SYSTEM");

		if (MemberGradeUtil.compareGrade(currentGrade, grade) < 0
				&& null != user.getMobileNumber()
				&& StringUtil.validMobileNumber(user.getMobileNumber())) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 0);
			calendar.add(Calendar.MINUTE, 0);
			calendar.add(Calendar.SECOND, 0);

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userName", user.getUserName());
			parameters.put("month", calendar.get(Calendar.MONTH) + 1);
			parameters.put("day", calendar.get(Calendar.DAY_OF_MONTH));
			parameters.put("amount", amount);
			parameters.put("oldGrade", currentGrade.getChGrade());
			parameters.put("newGrade", grade.getChGrade());
			
			Map<String, Float> coefficientMap = new HashMap<String, Float>(); 
			coefficientMap = MemberGradeUtil.getCoefficientByGrade(grade.getGrade());
			parameters.put("coeOfNetwork", coefficientMap.get("NETWORK"));
			parameters.put("coeOfTelephone", coefficientMap.get("TELEPHONE"));

			try {
				smsRemoteService.sendSmsInWorking(
						comSmsTemplateService.getSmsContent(Constant.SMS_SSO_TEMPLATE.SMS_MEMBER_GRADE_UPGRADE_REMIND.name(), parameters), user.getMobileNumber());
			} catch (Exception e) {
				LOG.error("send mobile:" + user.getMobileNumber() + " error!.result:" + e.getMessage());
			}
		} else {
			LOG.error("user or mobile number is empty.");
		}
	}
	
	@Override
	public void updateMemberGradeByManual(final UserUser user,
			final USER_MEMBER_GRADE grade, final String memo, final String operator) {
		userUserService.updateMemberGrade(user.getId(), grade.getGrade(), memo, operator);
	}
	
	@Override
	public List<UserGradeLog> queryUserGradeLogs(final Serializable userId) {
		return userUserService.queryUserGradeLogs(userId);
	}
	
	@Override
	public String getMailHostAddress(final String email) {
		String userMailHost = "";
		if (StringUtils.isNotBlank(email)) {
			Properties properties = new Properties();
			try {
				FileReader fileReader = new FileReader(UserUserProxyImpl.class.getResource("/mailWWW.properties").getFile());
				properties.load(fileReader);
				fileReader.close();
			} catch (IOException e) {
				LOG.error("error read properties:" + e.getMessage());
			}
			userMailHost = properties.getProperty(email.substring(email.indexOf("@")));
			if (StringUtil.isEmptyString(userMailHost)) {
				userMailHost = "http://www." + email.substring(email.indexOf("@") + 1);
			}			
		}
		return userMailHost;
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
	public UserUser update(UserUser user) {
		userUserService.update(user);
		return user;
	}

	@Override
	public List<UserUser> getUsers(final Map<String, Object> params) {
		if (params.get("maxRows")!=null) {
			params.put("_startRow", "0");
			params.put("_endRow", params.get("maxRows"));
		}
		List<UserUser> userList = userUserService.query(params);
		if(!userList.isEmpty()) {
			for (int i = 0; i < userList.size(); i++) {
				UserUser usr = userList.get(i);
				CashAccount ca = cashAccountService.queryCashAccountByUserId(usr.getId());
				if(ca != null) {
					usr.setCashFrozen(!ca.isAccountValid());
				}
			}
		}
		return userList;
	}

	@Override
	public List<UserUser> getUsersListByUserNoList(List<String> idList) {
		if(idList.size() < 1000)// 如果数据量太多，我们就不查询了，会导致SQL出错
		{
			return userUserService.getUsersListByUserNoList(idList);
		}
		else
		{
			LOG.error("getUsersListByUserNoList id list is >= 1000 limit");
			return new ArrayList<UserUser>();
		}
		
	}
	
	@Override
	public List<UserUser> queryUserUserByUserId(List<Long> userIdList) {		
		if(userIdList.size() < 1000)// 如果数据量太多，我们就不查询了，会导致SQL出错
		{
			return userUserService.queryUserUserByUserId(userIdList);
		}
		else
		{
			LOG.error("queryUserUserByUserId userIdList is >= 1000 limit");
			return new ArrayList<UserUser>();
		}
	}

	@Override
	public UserUser login(String username, String password) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return null;
		}
		UserUser user = getUsersByMobOrNameOrEmailOrCard(username);
		try {
			if (user!= null && user.getUserPassword().equals(UserUserUtil.encodePassword(password))) {
				return user;
			}
		} catch (NoSuchAlgorithmException e) {
			
		}
		return null;
	}

	@Override
	public void saveUserCertCode(final UserCertCode userCertCode) {
		userUserService.saveCertCode(userCertCode);		
	}
	
	@Override
	public void deleteUserCertCode(final Map<String, Object> params) {
		userUserService.deleteCertCode(params);		
	}
	
	@Override
	public UserCertCode queryUserCertCode(final USER_IDENTITY_TYPE type, final String code, final boolean autoDelete) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("query secrect password for activting "+type+","+code);
		}
		UserCertCode userCertCode  = userUserService.queryCertCode(type.name(), code);
		if (null != userCertCode && autoDelete) {
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", userCertCode.getType());
			param.put("code", userCertCode.getCode());
			
			userUserService.deleteCertCode(param);
		}
		return userCertCode;
	}

	@Override
	public boolean validateAuthenticationCode(final USER_IDENTITY_TYPE type, final String code,final String identityTarget) {
		if (code == null) {
			return false;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("query secrect password for activting " + type + "," + code);
		}
		UserCertCode userCertCode  = userUserService.queryCertCode(type.name(), identityTarget, code);
		
		if(userCertCode != null && userCertCode.getIdentityTarget()!=null)
		{
			if(userCertCode.getIdentityTarget().equals(identityTarget))
			{
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("type", userCertCode.getType());
				param.put("code", userCertCode.getCode());
				userUserService.deleteCertCode(param);
				LOG.info("user cert code was deleted: " + identityTarget + ", " + code);
				return true;
			}
			else
			{
				LOG.error("user cert code not exist: " + identityTarget + ", " + code);
				return false;
			}
		}
		else
		{
			LOG.error("user cert code not exist: " + identityTarget + ", " + code);
			return false;
		}
	}

	
	@Override
	public boolean bindingUserAndMembershipCardCode2(final Long userId, final String code) {
//		if (userUserService.bindingUserAndMembershipCardCode(userId, code)) {
//			UserUser user = getUserUserByPk(userId);
//			
//			Map<String,Object> parameters = new HashMap<String,Object>();
//			parameters.put("cardCode", code); 
//			List<MarkMembershipCardDiscountDetails> discounts = markMembershipCardDiscountService.query(parameters);
//			for (MarkMembershipCardDiscount discount : discounts) {
//				tempBackendManagerRemoteService.bindingUserAndCouponCode(user.getUserNo(), tempBackendManagerRemoteService.generateNewMarkCouponCode(discount.getCouponId()));
//			}			
//			return true;
//		} else {
//			return false;
//		}	
		return userUserService.bindingUserAndMembershipCardCode(userId, code);
	}
	
	private UserUser bindBonusInfo(UserUser user) {
		if (user != null) {
			CashAccount cashAccount = cashAccountService.queryCashAccountByUserId(user.getId());
			if (cashAccount != null) {
				user.setAwardBalance(cashAccount.getBonusBalance());
			} else {
				user.setAwardBalance(0l);
			}
		}
		return user;
	}

	@Override
	public List<UserUser> queryUserUserKeyWordsLike(Map<String, Object> params) {
		return userUserService.queryUserUserKeyWordsLike(params);
	}

    @Override
	public void unBindingMobile(final Long userId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("start unbind mobile");
		}
    	if (null != userId) {
    		if (LOG.isDebugEnabled()) {
    			LOG.debug("start unbind mobile "+userId);
    		}
    		this.userUserService.unBindingMobile(userId);
    	}
	}
	
    @Override
	public void unBindingEmail(final Long userId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("start unbind email");
		}
    	if (null != userId) {
    		if (LOG.isDebugEnabled()) {
    			LOG.debug("start unbind email "+userId);
    		}
    		this.userUserService.unBindingEmail(userId);
    	}
	}	
    
    @Override
	public void insertWinnerslist(Annwinnerslist annList) {
    	this.userUserService.insertWinnerslist(annList);
    }
    @Override
    public boolean insertMinDazhuanPanWinnerslist(Annwinnerslist annList,int jieguoDengji,int shuliang) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("start insertMinDazhuanPanWinnerslist:"+jieguoDengji+":"+shuliang);
		}
    	Map param=new HashMap();
    	param.put("lpDengjiang", jieguoDengji);
    	param.put("shuliang", shuliang);
    	if(userUserService.minUpdateAnnliping(param) == 1){
    		if (LOG.isDebugEnabled()) {
    			LOG.debug("start insertWinnerslist"+annList.toString());
    		}
    		this.userUserService.insertWinnerslist(annList);
    		return true;
    	}
    	return false;
    }
    
    @Override
	public List<Annwinnerslist> queryWinnerslistBylimit(long  size) {
    	Map map=new HashMap();
  	    map.put("startRows", 0);
  	    map.put("endRows",  size);
	 return	userUserService.queryWinnerslistByparam(map);
	}
    
    @Override
	public List<Annwinnerslist> queryWinnerslistByMap(Map<String, Object> param) {
	 return	userUserService.queryWinnerslistByparam(param);
	}
    
    @Override
	public List<Annwinnerslist> queryWinnerslistByUserId(Long userId) {
        Map map=new HashMap();
        map.put("startRows", 0);
        map.put("endRows", "20");
        map.put("userId", userId);
	 return	userUserService.queryWinnerslistByparam(map);
	}
    
    @Override
   	public List<Annwinnerslist> queryWinnerslistByUserIdAndProject(Long userId,String projectName) {
           Map<String, Object> map=new HashMap<String, Object>();
           map.put("userId", userId);
           map.put("projectName", projectName);
           map.put("createDate", new Date());
   	 return	userUserService.queryWinnerslistByparam(map);
   	}

    
    @Override
	public Annliping queryAnnliping(Long lpDengjiang) {
		return	userUserService.queryAnnliping(lpDengjiang);
	}

	@Override
	public void updateWinnerslist(Annwinnerslist annlist) {
		userUserService.updateWinnerslist(annlist);
	}
	@Override
	public UserTopic queryUserTopicById(Long userId){
		return (UserTopic) userUserService.queryUserTopicById(userId);
	}

	@Override
	public List<Annhongbao> queryAnnhongbaoByParam(Map<String, Object> map) {
 		return (List<Annhongbao>)userUserService.queryAnnhongbaoByParam(map);
	}
	@Override
	public int updateAnnhongbao(Annhongbao ann) {
		return userUserService.updateAnnhongbao(ann);
	}
	
	@Override
	public List<AnnHongbaoHj> queryAnnHongbaoHj(Map<String, Object> param) {
		return userUserService.queryAnnHongbaoHj(param);
	}
	@Override
	public Long selectSumMoneyByUserId(Map<String,Object> param){
		return userUserService.selectSumMoneyByUserId(param);
	}
	
	@Override
	public void saveAnnHongbaoHJ(AnnHongbaoHj annHongbaoHj){
		userUserService.saveAnnHongbaoHJ(annHongbaoHj);
	}
 

	@Override
	public void updateReWriteShengyuJinE(Annhongbao nextann,
			Annhongbao annjieguo) {
		userUserService.updateAnnhongbao(nextann);
		userUserService.updateAnnhongbao(annjieguo);
	}
 
	@Override
	public boolean saveAnnHongbaoUpdate(AnnHongbaoHj annHongbaoHj,
			Map<String, Object> param,int i,long userId) {
		if(userUserService.minUpdateAnnHongbao(param) == 1){
			userUserService.saveAnnHongbaoHJ(annHongbaoHj);
			cashAccountService.returnBonusForPCActivity((long) (i *100),userId,Constant.ACTIVITY_LIST.ANNIVERSARY.name());
			return true;
		}
		return false;
	}

	@Override
	public Annliping queryAnnlipingByParam(Map<String, Object> param) {
		return userUserService.queryAnnlipingByParam(param);
	}
	
	/**
	 * 童心童乐抽奖
	 */
	@Override
	public boolean insertZTchoujiang(Annwinnerslist annwinner,String lpType,UserUser user, int shuliang) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lpId", annwinner.getLpId());
		param.put("shuliang", shuliang);
		if(userUserService.updateAnnliping(param)==1){
			userUserService.insertWinnerslist(annwinner);
			if(lpType != null && lpType.equals("0")){
//				cashAccountService.returnBonusForPCActivity((long) (500),userId,Constant.ACTIVITY_LIST.TONGXINTONGLE.name());
				MarkCouponCode markCouponCode = markCouponService
						.generateSingleMarkCouponCodeByCouponId(4564L);
				markCouponService.bindingUserAndCouponCode(user,markCouponCode.getCouponCode());
			}
			return true;
		}
		return false;
	}
}
