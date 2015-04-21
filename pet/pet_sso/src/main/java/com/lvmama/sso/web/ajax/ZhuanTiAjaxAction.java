package com.lvmama.sso.web.ajax;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.vo.Constant;
import com.lvmama.sso.vo.AjaxRtnBaseBean;
import com.lvmama.sso.web.AbstractLoginAction;

import edu.yale.its.tp.cas.util.DomainConstant;


/**
 * 专题类活动所需要调用的Ajax请求
* 项目名称：branch_super_nsso
* 类名称：ZhuanTiAjaxAction
* 类描述：暂无
* 创建人：Brian
* 创建时间：2012-6-18 下午1:50:06
* 修改人：Brian
* 修改时间：2012-6-18 下午1:50:06
* 修改备注：
* @version
 */
public class ZhuanTiAjaxAction extends AbstractLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7789803660420287714L;
	/**
	 * 远程SMS服务
	 */
	private SmsRemoteService smsRemoteService;

//	/**
//	 * 发送短信
//	 * @deprecated
//	 * @throws Exception 远程调用错误
//	 */
//	@Action("/ajax/sendSms")
//	public void sendSms() throws Exception {
//		String mobile = this.getRequest().getParameter("mobile");
//		String content = this.getRequest().getParameter("content");
//		if (StringUtils.isEmpty(mobile) || StringUtil.isEmptyString(content)) {
//			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "发送失败"));
//			return;
//		} else {
//			smsRemoteService.sendSmsInWorking("感谢您参与驴妈妈携手Jeep指南者\"畅快之旅-载TA去旅行\"活动，您的抽奖代码是"
//		+ content + "请妥善保存。30元优惠券代码为：A0181875499738800，可在订购门票线路时直接使用", mobile);
//			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
//		}
//	}
//
//	/**
//	 * 发送短信
//	 * @deprecated
//	 * @throws Exception 远程调用错误
//	 */
//	@Action("/ajax/sendSms2")
//	public void sendSms2() throws Exception {
//		String mobile = this.getRequest().getParameter("mobile");
//		if (StringUtils.isEmpty(mobile)) {
//			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "发送失败"));
//			return;
//		} else {
//			smsRemoteService.sendSmsInWorking("感谢您参与驴妈妈携手Jeep自由客自驾游活动，135元组合优惠券（5元门票代码：A0122748148753125、30元线路代码：A0122748627834415、100元出境代码：A0122744050888862）， 进口硬派SUV急速风景等你体验。", mobile);
//			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
//		}
//	}	
//
//	/**
//	 * 发送短信
//	 * @deprecated
//	 * @throws Exception 远程调用错误
//	 */
//	@Action("/ajax/sendSms3")
//	public void sendSms3() throws Exception {
//		String mobile = this.getRequest().getParameter("mobile");
//		String content = this.getRequest().getParameter("content");
//		if (StringUtils.isEmpty(mobile) || StringUtil.isEmptyString(content)) {
//			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(false, "发送失败"));
//			return;
//		} else {
//			smsRemoteService.sendSmsInWorking("您的幸运号码为" + content +"，凭此号码将有机会获得由鄱阳湖国家湿地公园提供的“你加油我加水”的亲水减压活动奖品，敬请关注，祝您好运。-做回自然的孩子【驴妈妈旅游网】", mobile);
//			printRtn(getRequest(), getResponse(), new AjaxRtnBaseBean(true, ""));
//		}
//	}
//
//	/**
//	 * 亲水活动的手机注册
//	 * @throws Exception
//	 */
//	@Action("/ajax/qinshui/registerMobile")
//	public void qinshuiActivity() throws Exception {
//		String mobile = this.getRequest().getParameter("mobile");
//		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(true, "");
//		if (StringUtils.isEmpty(mobile)) {
//			rtn.setSuccess(false);
//			rtn.setErrorText("手机号为空，无法进行有效的注册");
//			printRtn(getRequest(), getResponse(), rtn);
//			return;
//		}
//		if (userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobile)) {
//			UserUser user = UserUserUtil.genDefaultUserByMobile(mobile);
//			if (null == user) {
//				rtn.setSuccess(false);
//				rtn.setErrorText("由于不可预知的错误，注册失败");
//				printRtn(getRequest(), getResponse(), rtn);
//				return;
//			}
//			try {
//				user = userUserProxy.register(user);
//				try {
//					ssoMessageProducer.sendMsg(new SSOMessage(
//							SSO_EVENT.REGISTER, SSO_SUB_EVENT.SILENT, user.getId()));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				rtn.setErrorText(user.getUserId());
//			} catch (Exception e) {
//				LOG.error("为用户" + user + "注册失败!\t" + e.getMessage());
//				rtn.setSuccess(false);
//				rtn.setErrorText("由于不可预知的数据库错误，注册失败");
//				printRtn(getRequest(), getResponse(), rtn);
//				return;
//			}
//		} else {
//			UserUser user = userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobile);
//			if (null != user) {
//				rtn.setErrorText(user.getUserId());
//			} else {
//				rtn.setSuccess(false);
//				rtn.setErrorText("由于不可预知的数据库错误，注册失败");
//				printRtn(getRequest(), getResponse(), rtn);
//				return;
//			}
//		}
//		smsRemoteService.sendSmsInWorking("恭喜您成功领取驴妈妈8元特惠门票优惠券，请登陆www.lvmama.com至“我的优惠券”查看。客服咨询电话：1010-6060【驴妈妈旅游网】", mobile);
//
//		printRtn(getRequest(), getResponse(), rtn);
//	}
//
//	/**
//	 * HOLA特力和乐活动
//	 * @throws Exception 错误
//	 */
//	@Action("/ajax/holaActivity")
//	public void holaActivity() throws Exception {
//		String mobile = this.getRequest().getParameter("mobile");
//		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(true, "");
//		if (StringUtils.isEmpty(mobile)) {
//			if (LOG.isDebugEnabled()) {
//				LOG.debug("提供了一个空的手机号，无法完成有效的注册");
//				rtn.setSuccess(false);
//				rtn.setErrorText("手机号为空，无法进行有效的注册");
//				printRtn(getRequest(), getResponse(), rtn);
//				return;
//			}
//		}
//
//		smsRemoteService.sendSms("驴妈妈携手HOLA特力和乐夏季送好礼:8/22前凭此短信至HOLA任一门店可免费领取价值30元发带三件套一份,数量有限,领完即止。", mobile);
//
//		printRtn(getRequest(), getResponse(), rtn);
//	}
	
	/**
	 * 机票频道的发送短信接口
	 * @throws Exception
	 */
	@Action("/ajax/fightTicketSms")
	public void fightTicketSms() throws Exception {
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(false, "禁止访问");
		String clientIP = InternetProtocol.getRemoteAddr(getRequest());
		if ("221.122.126.90".equals(clientIP) || "180.169.51.82".equals(clientIP) || CheckIpAddress(clientIP)) {
			String mobile = this.getRequest().getParameter("mobile");
			String content = new String(this.getRequest().getParameter("content").getBytes("ISO-8859-1"), "UTF-8"); 
			if (StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(content)) {
				smsRemoteService.sendSms(content, mobile, 5, "FightTicket", new java.util.Date());
				rtn.setSuccess(true);
				rtn.setErrorText("");
			}
		}
		printRtn(getRequest(), getResponse(), rtn);
	}
	/**
     * 内网IP限定
     * */
	 public boolean CheckIpAddress(String Ip){
	    	String regex =Constant.getInstance().getProperty("PHP.IP.REGEX");
	    	String[] ips = regex.split(",");
	    	//匹配Ip
	    	for(int i=0;i<ips.length;i++){
			  if (Ip.matches(ips[i])) {
		             return true;
		         } 
	    	}
	       
	    	return false;
	    }
	/**
	 * 
	 * @throws Exception
	 */
	@Action("/ajax/head")
	public void getHead() throws Exception {
		com.lvmama.comm.utils.CommHeaderUtil.getHeadContent(this.getResponse().getWriter());
	}
	
	public void bindingEmail() throws Exception {
		AjaxRtnBaseBean rtn = new AjaxRtnBaseBean(false, "参数不全");
		String userId = this.getRequestParameter("uid");
		String email = this.getRequestParameter("email");
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(email)) {
			UserUser user = userUserProxy.getUserUserByUserNo(userId);
			if (null != user 
					&& StringUtils.isBlank(user.getEmail())
			        && userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.EMAIL, email)) {
				user.setEmail(email);
				userUserProxy.update(user);
				
				Cookie tck = new Cookie("EMV", "U");
				tck.setSecure(false);
				tck.setMaxAge(-1);
				tck.setDomain(DomainConstant.DOMAIN);
				tck.setPath("/");
				this.getResponse().addCookie(tck);
				
				rtn.setSuccess(true);
				rtn.setErrorText("");
			}
			rtn.setErrorText("用户邮箱不符合更新条件");
		}
		
		printRtn(getRequest(), getResponse(), rtn);
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

	public void setSmsRemoteService(final SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}
}
