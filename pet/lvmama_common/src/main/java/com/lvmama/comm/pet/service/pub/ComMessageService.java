package com.lvmama.comm.pet.service.pub;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComAnnouncement;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.po.pub.ComMessageReceivers;

public interface ComMessageService {
	/**
	 * 通过当前时间和用户编号-查询出用户下的公告信息.
	 * @param param (传参userId:用户编号,expiredTime:公告通知时间)
	 * @return
	 */
	public List<ComAnnouncement> queryComAnnouncementByUserIdOrgId(Map param);
	/**
	 * 系统警告事件-给用户发送指定消息.
	 * @param param
	 * @return
	 */
	public void addSystemComMessage(String eventType,String content,String operatorName);
	
	/**
	 * 查询出工具栏下面属于当前用户的公告.
	 * @param param
	 * @return
	 */
	public List<ComAnnouncement> queryToolsComAnnouncement(Map param);
	/**
	 * 查找消息.
	 * @param messageId
	 * @return
	 */
	public ComMessage getComMessageByPk(Long messageId);
	/**
	 * 消息分类发送指定人(更新).
	 * @param param
	 * @return
	 */
	public void updateComMessage(ComMessage comMessage);
	/**
	 * 消息分类发送指定人(插入).
	 * @param param
	 * @return
	 */
	public Long insertComMessage(ComMessage comMessage);
	/**
	 * 增加.
	 * @param comMessageReceivers
	 */
	public void addComAnnouncement(ComAnnouncement comAnnouncement) ;
	/**
	 * 综合查询,通过指定的参数做查询,返回数量.
	 * @param param
	 * @return
	 */
	public Long queryComAnnounceByParamCount(Map param);
	/**
	 * 综合查询,通过指定的参数做查询.例如:内容
	 * @param param
	 * @return
	 */
	public List<ComAnnouncement> queryComAnnounceByParam(Map param);
	
	/**
	 * 综合查询,通过指定的参数做查询,返回数量.
	 * @param param
	 * @return
	 */
	public Long queryComMessageByParamCount(Map param);
	/**
	 * 综合查询,通过指定的参数做查询.例如:内容
	 * @param param
	 * @return
	 */
	public List<ComMessage> queryComMessageByParamBeginTimeDesc(Map param);
	/**
	 * 综合查询,通过指定的参数做查询.例如:内容
	 * @param param
	 * @return
	 */
	public List<ComMessage> queryComMessageByParam(Map param);
	/**
	 * 通过主健更新.
	 * @param comMessageReceivers
	 */
	public void updateComMessageReceiversByPK(ComMessageReceivers comMessageReceivers) ;
	/**
	 * 消息分类发送指定人(插入).
	 * @param param
	 * @return
	 */
	public Long insertComMessageReceiver(ComMessageReceivers comMessageReceiver);
	/**
	 * 缩合查询,通过指定的参数做查询计数COUNT(*).例如:内容
	 * @param param
	 * @return
	 */
	public Long queryComMessageReceiverByParamCount(Map param);
	/**
	 * 缩合查询,通过指定的参数做查询.例如:内容
	 * @param param
	 * @return
	 */
	public List<ComMessageReceivers> queryComMessageReceiverByParam(Map param);
}
