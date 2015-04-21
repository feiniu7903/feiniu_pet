package com.lvmama.comm.pet.service.user;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.user.UserCooperationUser;

/**
 * 合作用户服务接口
 *
 */
public interface UserCooperationUserService {
	/**
	 * 查找
	 * @param parameters 参数
	 * @return 合作用户列表
	 */
	List<UserCooperationUser> getCooperationUsers(final Map<String, Object> parameters);
	
	/**
	 * 保存
	 * @param ucu 合作单位的信息
	 */
	void save(UserCooperationUser ucu);
	/**
	 * update
	 * @param ucu 合作单位的信息
	 */
	void update(final UserCooperationUser userCooperationUsers);

}
