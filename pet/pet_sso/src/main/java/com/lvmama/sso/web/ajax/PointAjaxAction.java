package com.lvmama.sso.web.ajax;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.sso.utils.UserIdConver;
import com.lvmama.sso.vo.AjaxRtnBaseBean;
import com.lvmama.sso.web.AbstractLoginAction;

/**
 * 此Action类是供外部调用积分系统所使用到的。
 * 此类会根据参数的不同而相应地产生不同的积分消息供积分处理器处理，故不应该滥用。
 * 对外开放的函数有：
 *  发表攻略 http://login.lvmama.com/nsso/ajax/point/addGuildPoint.do?objectId=功率标识&userId=用户标识
 *  发表功率手工加分 http://login.lvmama.com/nsso/ajax/point/addGuildPoint.do?objectId=功率标识&userId=用户标识&point=分数&subEvent=CUSTOMIZED
 *  发表主要板块帖子 http://login.lvmama.com/nsso/ajax/point/addPostPoint.do?objectId=帖子标识&userId=用户标识&subEvent=EMPHASIS_POST
 *  发表非主要板块帖子 http://login.lvmama.com/nsso/ajax/point/addPostPoint.do?objectId=帖子标识&userId=用户标识&subEvent=NON_EMPHASIS_POST
 *  回复主要板块帖子 http://login.lvmama.com/nsso/ajax/point/addPostPoint.do?objectId=帖子标识&userId=用户标识&subEvent=REPLY_EMPHASIS_POST
 *  一级加精 http://login.lvmama.com/nsso/ajax/point/addPostPoint.do?objectId=帖子标识&userId=用户标识&subEvent=FIRST_ESSENTIAL_POST
 *  二级加精 http://login.lvmama.com/nsso/ajax/point/addPostPoint.do?objectId=帖子标识&userId=用户标识&subEvent=SECOND_ESSENTIAL_POST
 *  三级加精 http://login.lvmama.com/nsso/ajax/point/addPostPoint.do?objectId=帖子标识&userId=用户标识&subEvent=THIRD_ESSENTIAL_POST
 *  手工帖子加分 http://login.lvmama.com/nsso/ajax/point/addPostPoint.do?objectId=帖子标识&userId=用户标识&point=分数&subEvent=CUSTOMIZED
 *  删帖 http://login.lvmama.com/nsso/ajax/point/addPostPoint.do?objectId=帖子标识&userId=用户标识&subEvent=DELETE_POST
 *  线上活动  http://login.lvmama.com/nsso/ajax/point/addOnlineActivityPoint.do?memo=许愿树(活动备注)&point=5&userId=5c7ac09534796a490134797027540004(用户标识)
 * 
 * @author Brian
 *
 */
public class PointAjaxAction extends AbstractLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8662427381163818682L;
	/**
	 * 标识
	 */
	private String objectId;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 用户标识
	 */
	private Long userId;
	/**
	 * 子事件
	 */
	private String subEvent;
	/**
	 * 积分
	 */
	private Long point;
	
	
	
	/**
	 * 社区加分
	 * @throws IOException IO错误
	 */
	@Action("/ajax/point/addTravelPoint")
	public void addTravelPoint() throws IOException {
		if (StringUtils.isEmpty(objectId) || null == userId) {
			printRtn(this.getRequest(), getResponse(), new AjaxRtnBaseBean(false, "无法获取到指定的功率标识或用户标识"));
			return;
		}
		SSOMessage message = new SSOMessage(Constant.SSO_EVENT.TRIP, null, userId);
		message.putAttribute("memo", objectId);

		if (Constant.SSO_SUB_EVENT.TRIP_SYS_POINTS_SEND.name().equalsIgnoreCase(subEvent)) {
			message.setSubEvent(Constant.SSO_SUB_EVENT.TRIP_SYS_POINTS_SEND);
			message.putAttribute("point", point);
		}else if (Constant.SSO_SUB_EVENT.TRIP_SHARE_POINTS_SEND.name().equalsIgnoreCase(subEvent)) {
			message.setSubEvent(Constant.SSO_SUB_EVENT.TRIP_SHARE_POINTS_SEND );
			message.putAttribute("point", point);
		}

		ssoMessageProducer.sendMsg(message);

		printRtn(this.getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
	}
	
	/**
	 * 攻略加分
	 * @throws IOException IO错误
	 */
	@Action("/ajax/point/addGuildPoint")
	public void addGuidePoint() throws IOException {
		if (StringUtils.isEmpty(objectId) || null == userId) {
			printRtn(this.getRequest(), getResponse(), new AjaxRtnBaseBean(false, "无法获取到指定的功率标识或用户标识"));
			return;
		}
		SSOMessage message = new SSOMessage(Constant.SSO_EVENT.GUIDE, null, userId);
		message.putAttribute("memo", objectId);

		if (Constant.SSO_SUB_EVENT.CUSTOMIZED.name().equalsIgnoreCase(subEvent)) {
			message.setSubEvent(Constant.SSO_SUB_EVENT.CUSTOMIZED);
			message.putAttribute("point", point);
		}

		ssoMessageProducer.sendMsg(message);

		printRtn(this.getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
	}

	/**
	 * 论坛加分
	 * @throws IOException IO错误
	 */
	@Action("/ajax/point/addPostPoint")
	public void addPostPoint() throws IOException {
		if (StringUtils.isEmpty(objectId) || null == userId) {
			printRtn(this.getRequest(), getResponse(), new AjaxRtnBaseBean(false, "无法获取到指定的功率标识或用户标识"));
			return;
		}
		SSOMessage message = new SSOMessage(Constant.SSO_EVENT.POST, null, userId);
		message.putAttribute("memo", objectId);

		if (Constant.SSO_SUB_EVENT.EMPHASIS_POST.name().equalsIgnoreCase(subEvent)) {
			message.setSubEvent(Constant.SSO_SUB_EVENT.EMPHASIS_POST);
		}
		if (Constant.SSO_SUB_EVENT.NON_EMPHASIS_POST.name().equalsIgnoreCase(subEvent)) {
			message.setSubEvent(Constant.SSO_SUB_EVENT.NON_EMPHASIS_POST);
		}
		if (Constant.SSO_SUB_EVENT.REPLY_EMPHASIS_POST.name().equalsIgnoreCase(subEvent)) {
			message.setSubEvent(Constant.SSO_SUB_EVENT.REPLY_EMPHASIS_POST);
		}
		if (Constant.SSO_SUB_EVENT.FIRST_ESSENTIAL_POST.name().equalsIgnoreCase(subEvent)) {
			message.setSubEvent(Constant.SSO_SUB_EVENT.FIRST_ESSENTIAL_POST);
		}
		if (Constant.SSO_SUB_EVENT.SECOND_ESSENTIAL_POST.name().equalsIgnoreCase(subEvent)) {
			message.setSubEvent(Constant.SSO_SUB_EVENT.SECOND_ESSENTIAL_POST);
		}
		if (Constant.SSO_SUB_EVENT.THIRD_ESSENTIAL_POST.name().equalsIgnoreCase(subEvent)) {
			message.setSubEvent(Constant.SSO_SUB_EVENT.THIRD_ESSENTIAL_POST);
		}
		if (Constant.SSO_SUB_EVENT.CUSTOMIZED.name().equalsIgnoreCase(subEvent)) {
			message.setSubEvent(Constant.SSO_SUB_EVENT.CUSTOMIZED);
			message.putAttribute("point", point);
		}
		if (Constant.SSO_SUB_EVENT.DELETE_POST.name().equalsIgnoreCase(subEvent)) {
			message.setSubEvent(Constant.SSO_SUB_EVENT.DELETE_POST);
		}
		ssoMessageProducer.sendMsg(message);

		printRtn(this.getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
	}

	/**
	 * 线上活动积分奖励
	 * @throws IOException IO错误
	 */
	@Action("/ajax/point/addOnlineActivityPoint")
	public void addOnlineActivityPoint() throws IOException {
		
		if (null == userId) {
			printRtn(this.getRequest(), getResponse(), new AjaxRtnBaseBean(false, "无法获取到用户标识"));
			return;
		}
		UserUser user = userUserProxy.getUserUserByPk(userId);
		if (null == user) {
			printRtn(this.getRequest(), getResponse(), new AjaxRtnBaseBean(false, "没有获取到用户"));
			return;
		}
		SSOMessage message = new SSOMessage(Constant.SSO_EVENT.ONLINE_ACTIVITY, null, userId);
		message.putAttribute("memo", memo);
		message.putAttribute("point", point);
		ssoMessageProducer.sendMsg(message);

		printRtn(this.getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
	}
	
	
	/**
	 * 输出返回码
	 * @param request request
	 * @param response response
	 * @param bean Ajax返回的基本Bean
	 * @throws IOException IOException
	 */
	private void printRtn(final HttpServletRequest request,
			final HttpServletResponse response, final AjaxRtnBaseBean bean) throws IOException {
		response.setContentType("text/json; charset=gb2312");
		if (request.getParameter("jsoncallback") == null) {
			response.getWriter().print(JSONObject.fromObject(bean));
		} else {
			getResponse().getWriter().print(getRequest().getParameter(
					"jsoncallback") + "(" + JSONObject.fromObject(bean) + ")");
		}
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setUserId(String userId) {
		this.userId=UserIdConver.converNoToId(userUserProxy, userId);
	}

	public void setSubEvent(String subEvent) {
		this.subEvent = subEvent;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public void setMemo(String memo) {
		try {
			this.memo = new String(memo.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		} 
	}

}
