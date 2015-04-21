package com.lvmama.pet.user.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserCertCode;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;

/**
 * 激活码的数据库操作类(邮件或手机或未来各种激活码)
 * @author Brian
 *
 */
public class UserCertCodeDAO extends BaseIbatisDAO {
    /**
     * 日志打印
     */
	private static final Log LOG = LogFactory.getLog(UserCertCodeDAO.class);

	/**
	 * 插入或更新用户的激活记录
	 * @param userCertCode 激活记录
	 */
	public void insert(final UserCertCode userCertCode) {
		if (StringUtils.isEmpty(userCertCode.getIdentityTarget()) || StringUtils.isEmpty(userCertCode.getType())) {
			LOG.warn("userCertCode parameter is null");
			return;
		}
		LOG.info(userCertCode.getType()+","+userCertCode.getIdentityTarget()+userCertCode.getCode());

		delete(userCertCode.getType(), userCertCode.getCode());
		if (LOG.isDebugEnabled()) {
			LOG.debug("success delete" + userCertCode.getIdentityTarget() + " history record");
			LOG.debug("try to insert Code:" + userCertCode);
		}
		super.insert("USER_CERT_CODE.insert", userCertCode);
		if (LOG.isDebugEnabled()) {
			LOG.debug("success insert Code:" + userCertCode);
		}
	}

	/**
	 * 根据用户的激活码找到用户的激活记录
	 * @param type  EMAIL/MOBILE
	 * @param code
	 * @return
	 */
	public UserCertCode queryByTypeAndCode(final String type, final String code) {
		UserCertCode userCertCode = null;
		debug("query " + code + " record");
		if (null != code && null != type) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", type);
			param.put("code", code);
			if(USER_IDENTITY_TYPE.EMAIL.name().equals(type))
			{
				param.put("30MinutesRange", "true");
			}
			else if(USER_IDENTITY_TYPE.MOBILE.name().equals(type))
			{
				param.put("15MinutesRange", "true");
			}
			userCertCode = (UserCertCode) super.queryForObject("USER_CERT_CODE.queryByTypeAndCode", param);
		}
		if (null != userCertCode) {
			debug("success find record" + userCertCode);
		}
		return userCertCode;
	}
	
	/**
	 * 根据用户身份信息找到用户的激活记录
	 * @param type  EMAIL/MOBILE
	 * @param code
	 * @return
	 */
	public UserCertCode queryByTypeAndIdentity(final String type, final String identityTarget) {
		UserCertCode userCertCode = null;
		debug("query " + identityTarget + " record");
		if (null != identityTarget && null != type) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", type);
			param.put("identityTarget", identityTarget);
			userCertCode = (UserCertCode) super.queryForObject("USER_CERT_CODE.queryByTypeAndIdentityTarget", param);
		}
		if (null != userCertCode) {
			debug("success find record" + userCertCode);
		}
		return userCertCode;
	}
	
	/**
	 * 根据用户身份信息找到用户的激活记录
	 * @param type  EMAIL/MOBILE
	 * @param code
	 * @return
	 */
	public UserCertCode queryByTypeAndIdentityAndCode(final String type, final String identityTarget,final String code) {
		UserCertCode userCertCode = null;
		debug("query " + identityTarget + " record");
		if (null != identityTarget && null != type && null != code) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", type);
			param.put("identityTarget", identityTarget);
			param.put("code", code);

			userCertCode = (UserCertCode) super.queryForObject("USER_CERT_CODE.queryByTypeAndIdentityTargetAndCode", param);
		}
		if (null != userCertCode) {
			debug("success find record" + userCertCode);
		}
		return userCertCode;
	}
	
	/**
	 * 根据用户的激活码和类型删除激活记录
	 * @param type 激活类型
	 * @param code 激活码
	 */
	private void delete(final String type, final String code) {
		if (null != type && null != code) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", type);
			param.put("code", code);
			delete(param);	
		}
				
	}
	
	/**
	 * 删除激活码
	 * @param param
	 */
	public void delete(Map<String, Object> param)
	{
		super.delete("USER_CERT_CODE.delete", param);		
	}
	

	/**
	 * 打印调式信息
	 * @param message 调式信息
	 */
	private void debug(final String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}
}
