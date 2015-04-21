package com.lvmama.report.service;

import java.util.List;

import com.lvmama.report.po.UserRegisterChannelMV;

public interface UserRegisterChannelService {
    /**
     * 获取用户所有的注册渠道
     * @return
     */
	List<UserRegisterChannelMV> getUserRegisterChannelMV();
}
