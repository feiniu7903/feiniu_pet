package com.lvmama.report.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.UserRegisterChannelMV;

/**
 * 用户注册渠道的DAO
 * @author Brian
 *
 */
public class UserRegisterChannelMVDAO extends BaseIbatisDAO {
	
	/**
	 * 获取所有用户注册渠道
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserRegisterChannelMV> getUserRegisterChannelMV() {
		return super.queryForList("USER_REGISTER_CHANNEL_MV.getUserRegisterChannelMV");
	}
}
