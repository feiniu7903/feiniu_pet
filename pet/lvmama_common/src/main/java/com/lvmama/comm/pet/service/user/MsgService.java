package com.lvmama.comm.pet.service.user;

import com.lvmama.comm.pet.po.user.UserCertCode;



/**
 * 消息服务接口
 *
 */
public interface MsgService {
	/**
	 * 保存站内信
	 * @param msgBody 消息体
	 */
	void saveMsg(UserCertCode msgBody);
	/**
	 * 保存站内信给用户
	 * @param msgInbox 消息与用户关联表
	 */
	void saveMsgToUser(UserCertCode msgInbox);
}
