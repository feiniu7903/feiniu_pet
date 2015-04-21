package com.lvmama.comm.pet.service.user;

import com.lvmama.comm.pet.po.user.UserPasswordRecall;
/**
 * 用户密码找回记录逻辑接口
 *
 * @author Brian
 *
 */
public interface UserPasswordRecallService {
	/**
	 * 插入记录
	 * @param recall 用户找回密码记录
	 * @return 记录标识
	 */
	Long insert(UserPasswordRecall recall);

	/**
	 * 根据ID查询用户密码找回记录
	 * @param id 主键
	 * @return UserPasswordRecall
	 */
	UserPasswordRecall getUserPasswordRecall(Long id);
	
	/**
	 * 设置该记录已被使用
	 * @param id 主键
	 */
	void setIsUsed(Long id);
}
