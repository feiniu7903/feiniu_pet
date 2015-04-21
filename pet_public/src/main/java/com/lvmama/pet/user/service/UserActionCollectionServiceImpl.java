package com.lvmama.pet.user.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.user.UserActionCollection;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.pet.user.dao.UserActionCollectionDAO;
import com.lvmama.pet.user.dao.UserUserDAO;

public class UserActionCollectionServiceImpl implements UserActionCollectionService {
	@Autowired
	private UserActionCollectionDAO userActionCollectionDAO;
	@Autowired
	private UserUserDAO userUserDAO;
	

	@Override
	@Deprecated
	public void save(Long userId, String ipAddr, Long remotePort, String action, String memo) {
		UserActionCollection uac = new UserActionCollection();
		uac.setAction(action);
		uac.setIp(ipAddr);
		uac.setMemo(memo);
		uac.setUserId(userId);
		uac.setPort(remotePort);
		userActionCollectionDAO.save(uac);
	}

	@Override
	@Deprecated
	public void save(String userNo, String ipAddr, Long remotePort, String action, String memo) {
		UserUser user = userUserDAO.getUsersByUserNo(userNo);
		if (null != user) {
			this.save(user.getUserId(), ipAddr, remotePort, action, memo);
		}
	}

	@Override
	@Deprecated
	public void save(Long userId, String ipAddr, String action, String memo) {
		this.save(userId, ipAddr, null, action, memo);
	}

	@Override
	@Deprecated
	public void save(String userNo, String ipAddr, String action, String memo) {
		UserUser user = userUserDAO.getUsersByUserNo(userNo);
		if (null != user) {
			this.save(user.getUserId(), ipAddr, null, action, memo);
		}

	}

	@Override
	public void save(Long userId, String ipAddr, Long remotePort,String action, String memo, String loginType, String loginChannel,String referer) {
		UserActionCollection uac = new UserActionCollection();
		uac.setAction(action);
		uac.setIp(ipAddr);
		uac.setMemo(memo);
		uac.setUserId(userId);
		uac.setPort(remotePort);
		uac.setLoginType(loginType);
		uac.setLoginChannel(loginChannel);
		uac.setReferer(referer);
		userActionCollectionDAO.save(uac);
		
	}

	@Override
	public void save(String userNo, String ipAddr, Long remotePort,String action, String memo, String loginType, String loginChannel,String referer) {
		UserUser user = userUserDAO.getUsersByUserNo(userNo);
		if (null != user) {
			this.save(user.getUserId(), ipAddr, remotePort, action, memo,loginType,loginChannel,referer);
		}
		
	}

}
