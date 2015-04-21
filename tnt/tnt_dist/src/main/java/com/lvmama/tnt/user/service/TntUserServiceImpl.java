package com.lvmama.tnt.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.PAY_TYPE;
import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;
import com.lvmama.tnt.user.mapper.TntUserDetailMapper;
import com.lvmama.tnt.user.mapper.TntUserMapper;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;

@Repository("tntUserService")
public class TntUserServiceImpl implements TntUserService {

	@Autowired
	private TntUserMapper tntUserMapper;

	@Autowired
	private TntUserDetailMapper tntUserDetailMapper;

	@Override
	@Transactional
	public long insert(TntUser tntUser) {
		tntUserMapper.insert(tntUser);
		if (tntUser.getUserId() > 0) {
			tntUser.getDetail().setUserId(tntUser.getUserId());
			tntUserDetailMapper.insert(tntUser.getDetail());
		}
		return tntUser.getUserId();
	}

	@Override
	@Transactional
	public void update(TntUser tntUser) {
		tntUserMapper.update(tntUser);
		tntUserDetailMapper.update(tntUser.getDetail());
	}

	public void updatePassword(TntUser tntUser) {
		TntUser u = new TntUser();
		u.setUserId(tntUser.getUserId());
		u.setLoginPassword(tntUser.getLoginPassword());
		tntUserMapper.update(u);
	}

	@Override
	public void updateDetail(TntUserDetail detail) {
		tntUserDetailMapper.update(detail);
	}

	@Override
	public void del(Long userId) {

	}

	@Override
	public List<TntUser> query(Map<String, Object> map) {
		return tntUserMapper.query(map);
	}

	@Override
	public int queryCount(Map<String, Object> map) {
		return tntUserMapper.queryCount(map);
	}

	public TntUser queryUserByUserName(String userName) {
		return tntUserMapper.getUserByUserName(userName);
	}

	@Override
	public TntUser findWithDetail(TntUser tntUser) {
		return tntUserMapper.selectOneWithDetail(tntUser);
	}

	@Override
	public List<TntUser> queryWithDetail(TntUser tntUser) {
		return tntUserMapper.selectListWithDetail(tntUser);
	}

	@Override
	public int queryWithDetailCount(TntUser tntUser) {
		return tntUserMapper.countWithDetail(tntUser);
	}

	@Override
	public TntUser findWithDetailByUserName(String userName) {
		TntUser t = new TntUser();
		t.setUserName(userName);
		return findWithDetail(t);
	}

	@Override
	public TntUser findWithDetailByUserId(Long userId) {
		TntUser t = new TntUser(userId);
		return findWithDetail(t);
	}

	private boolean checkBeforeInfoApply(TntUserDetail t) {
		return t != null
				&& (TntConstant.USER_INFO_STATUS.isWaiting(t.getInfoStatus())
						|| TntConstant.USER_INFO_STATUS.isReWaiting(t
								.getInfoStatus()) || TntConstant.USER_INFO_STATUS
							.isReject(t.getInfoStatus()))
				|| TntConstant.USER_INFO_STATUS.isReReject(t.getInfoStatus());
	}

	private boolean checkBeforeInfoApply(Long userId) {
		TntUserDetail detail = tntUserDetailMapper.getByUserId(userId);
		return checkBeforeInfoApply(detail);
	}

	@Override
	public boolean agree(Long userId) {
		if (userId != null) {
			TntUserDetail detail = tntUserDetailMapper.getByUserId(userId);
			if (checkBeforeInfoApply(detail)) {
				String status = getAgreeStatus(detail.getInfoStatus());
				detail = new TntUserDetail();
				detail.setUserId(userId);
				detail.setInfoStatus(status);
				return tntUserDetailMapper.update(detail) > 0;
			}
		}
		return false;
	}

	private String getAgreeStatus(String status) {
		return TntConstant.USER_INFO_STATUS.isWaiting(status)
				|| TntConstant.USER_INFO_STATUS.isReject(status) ? TntConstant.USER_INFO_STATUS.NEEDACTIVE
				.getValue() : TntConstant.USER_INFO_STATUS.ACTIVED.getValue();
	}

	private String getRejectStatus(String status) {
		return TntConstant.USER_INFO_STATUS.isWaiting(status) ? TntConstant.USER_INFO_STATUS.REJECT
				.getValue() : TntConstant.USER_INFO_STATUS.REREJECT.getValue();
	}

	@Override
	public boolean reject(Long userId, String failReason) {
		if (userId != null && failReason != null) {
			TntUserDetail detail = tntUserDetailMapper.getByUserId(userId);
			if (checkBeforeInfoApply(detail)) {
				String status = getRejectStatus(detail.getInfoStatus());
				detail = new TntUserDetail();
				detail.setUserId(userId);
				detail.setInfoStatus(status);
				detail.setFailReason(failReason);
				return tntUserDetailMapper.update(detail) > 0;
			}
		}
		return false;
	}

	@Override
	public boolean reject(TntUser tntUser) {
		if (tntUser != null) {
			TntUserDetail detail = tntUser.getDetail();
			if (detail != null) {
				Long userId = tntUser.getUserId();
				String failReason = detail.getFailReason();
				return reject(userId, failReason);
			}
		}
		return false;
	}

	@Override
	public List<TntUser> queryPageWithDetail(Page<TntUser> page) {
		return tntUserMapper.fetchPageWithDetail(page);
	}

	@Override
	public TntUser queryUserByMobileOrEmail(String mobile, String email) {
		TntUser user = new TntUser();
		user.setMobileNumber(mobile);
		user.setEmail(email);
		List<TntUser> users = tntUserMapper.queryUserByMobileOrEmail(user);
		return users != null ? users.get(0) : null;
	}

	private boolean checkBeforeFinalApprove(TntUserDetail t) {
		return t != null
				&& TntConstant.USER_INFO_STATUS.isAgreed(t.getInfoStatus())
				&& TntConstant.USER_FINAL_STATUS.isWaiting(t.getFinalStatus());
	}

	private boolean checkBeforeFinalApprove(Long userId) {
		if (userId != null) {
			TntUserDetail detail = tntUserDetailMapper.getByUserId(userId);
			return checkBeforeFinalApprove(detail);
		}
		return false;
	}

	@Override
	public boolean finalAgree(Long userId) {
		boolean flag = checkBeforeFinalApprove(userId);
		if (flag) {
			TntUserDetail t = new TntUserDetail();
			t.setUserId(userId);
			t.setFinalStatus(TntConstant.USER_FINAL_STATUS.DOING.getValue());
			return tntUserDetailMapper.update(t) > 0;
		}
		return false;
	}

	@Override
	public boolean finalReject(Long userId, String reason) {
		if (checkBeforeFinalApprove(userId)) {
			TntUserDetail t = new TntUserDetail();
			t.setUserId(userId);
			t.setFailReason(reason);
			t.setFinalStatus(TntConstant.USER_FINAL_STATUS.REJECT.getValue());
			return tntUserDetailMapper.update(t) > 0;
		}
		return false;
	}

	@Override
	public boolean canFinalApprove(Long userId) {
		return checkBeforeFinalApprove(userId);
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
		return false;
	}

	/**
	 * 手机是否唯一
	 * 
	 * @param mobile
	 *            手机号
	 * @return 唯一手机返回真，否则返回假
	 */
	private boolean isMobileRegistrable(final String mobile) {
		if (!StringUtil.validMobileNumber(mobile)) {
			return false;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("mobile", mobile);
		return !checkUsersForObject(parameters);
	}

	/**
	 * 用户名是否唯一
	 * 
	 * @param account
	 *            用户名
	 * @return 唯一返回真，否则返回假
	 */
	private boolean isNameRegistrable(final String account) {
		if (!StringUtil.validNickname(account)) {
			return false;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userName", account.toLowerCase());
		return !checkUsersForObject(parameters);
	}

	/**
	 * 邮件地址是否唯一
	 * 
	 * @param email
	 *            邮件地址
	 * @return 唯一返回真，否则返回假
	 */
	private boolean isEmailRegistrable(final String email) {
		if (!StringUtil.validEmail(email)) {
			return false;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("email", email);
		return !checkUsersForObject(parameters);
	}

	/**
	 * 根据查询条件查询的用户是否存在 参数 email userName mobile
	 * 
	 * @param parameters
	 *            查询条件
	 * @return 是否存在
	 */
	public boolean checkUsersForObject(Map<String, Object> parameters) {
		if (StringUtils.isNotEmpty((String) parameters.get("email"))) {
			parameters.put("email",
					((String) parameters.get("email")).toLowerCase());
		}
		Integer num = (Integer) tntUserMapper.checkUnique(parameters);
		if (num > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean setContractDate(TntUserDetail detail) {
		if (detail != null && detail.getUserId() != null
				&& detail.getContractStartDate() != null
				&& detail.getContractEndDate() != null) {
			return tntUserDetailMapper.update(detail) > 0;
		}
		return false;
	}

	@Override
	public boolean endContract(Long userId, String reason) {
		if (userId != null) {
			TntUserDetail t = new TntUserDetail();
			t.setUserId(userId);
			t.setFailReason(reason);
			t.setFinalStatus(TntConstant.USER_FINAL_STATUS.END.getValue());
			return tntUserDetailMapper.update(t) > 0;
		}
		return false;
	}

	@Override
	public boolean repeatDoing(Long userId) {
		if (userId != null) {
			TntUserDetail t = new TntUserDetail();
			t.setUserId(userId);
			t.setFinalStatus(TntConstant.USER_FINAL_STATUS.DOING.getValue());
			return tntUserDetailMapper.update(t) > 0;
		}
		return false;
	}

	@Override
	public boolean activeUser(Long userId) {
		if (userId != null) {
			TntUserDetail userDetail = new TntUserDetail();
			userDetail.setUserId(userId);
			userDetail.setIsEmailChecked(Boolean.TRUE.toString());
			userDetail.setInfoStatus(TntConstant.USER_INFO_STATUS.ACTIVED
					.name());
			return tntUserDetailMapper.update(userDetail) > 0;
		}
		return false;
	}

	@Override
	public boolean setPayType(Long userId, String type, String reason) {
		if (userId != null && type != null) {
			TntUserDetail t = new TntUserDetail();
			t.setUserId(userId);
			t.setPayType(type);
			return tntUserDetailMapper.update(t) > 0;
		}
		return false;
	}

	@Override
	public Map<String, String> getPayTypeMap() {
		return PAY_TYPE.toMap();
	}

	public void updateMaterialStatus(TntUserDetail detail) {
		tntUserDetailMapper.update(detail);
	}

}
