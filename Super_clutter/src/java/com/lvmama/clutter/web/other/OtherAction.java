package com.lvmama.clutter.web.other;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@Results({ 
	@Result(name = "limitPay", location = "/WEB-INF/pages/other/limitPay.html", type="freemarker")
})
@Namespace("/mobile/other")
public class OtherAction extends BaseAction{
	private static final String LV_SEND_MSG_KEY = "lv_send_msg_key"; // 一分钟最多发送 
	private static final String LV_SEND_MSG_KEY_DAYS = "lv_send_msg_key_days"; // 一天最多3次 
	/**
	 * 发送短信内容.
	 */
	private static final String LV_CONTENT = "下载驴妈妈旅游客户端，返现优惠正等您来拿。http://m.lvmama.com/download?ch=duanxin【驴妈妈】";
	
	/**
	 * 手机 .
	 */
	private String mobile;
	

	/**
	 * 短信服务. 
	 */
	SmsRemoteService smsRemoteService;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8760949769254927456L;

	@Action("limitPay")
	public String limitPay(){
		String bank =  this.getRequest().getParameter("bank");
		this.getRequest().setAttribute("bank", Constant.MARK_PAYMENT_CHANNEL.getCnName(bank));
		return "limitPay";
	}
	
	/**
	 * 发送短信 . 
	 * @return
	 */
	@Action("sendMsg")
	public void sendMessage(){
		
		String jsons = "{msg:\"短信发送成功！\",code:\"1\"}";
		// 校验手机号 ！
		if(!StringUtil.validMobileNumber(mobile)) {
			jsons = "{msg:\"请输入正确的手机号码\",code:\"-1\"}";
		} else {
			String key = LV_SEND_MSG_KEY + mobile;
			String das_key = LV_SEND_MSG_KEY_DAYS+ mobile;
			int count = 0;
			Object tobject = MemcachedUtil.getInstance().get(key);
			Object daysObject = MemcachedUtil.getInstance().get(das_key);
			// 一分钟最多一次 ；
			if(null != tobject) {
				jsons = "{msg:\"已经发送下载地址到您的手机，如果没有收到，请您一分钟后再试。\",code:\"-1\"}";
				this.printRtn(getRequest(),getResponse(),jsons);
				return ;
			}
			
			// 一天最多3次!
			if(null != daysObject) {
				int t_cout =Integer.valueOf(daysObject.toString());
				if(t_cout >= 3) {
					jsons = "{msg:\"已超过你的当天发送次数，如果没有收到，请您明天再试。\",code:\"-1\"}";
					this.printRtn(getRequest(),getResponse(),jsons);
					return ;
				} else {
					count = t_cout;
				}
			}
			
			//发送短信. 
			try{
				smsRemoteService.sendSms(LV_CONTENT, mobile);
				count = count + 1;
				MemcachedUtil.getInstance().set(key,60,count );
				// 缓存1天
				MemcachedUtil.getInstance().set(das_key,3600*24,count );
			}catch(Exception e){
				e.printStackTrace();
				jsons = "{msg:\"短信发送失败,请重新输入手机号码。\",code:\"-1\"}";
			}
		}
		this.printRtn(getRequest(),getResponse(),jsons);
	}
	
	private void printRtn(final HttpServletRequest request,
			final HttpServletResponse response,String jsons){
		if (request.getParameter("jsoncallback") == null) {
			super.sendAjaxResultByJson(JSONObject.fromObject(jsons).toString());
		} else {
			super.sendAjaxResultByJson(getRequest().getParameter("jsoncallback") + "(" + JSONObject.fromObject(jsons) + ")");
		}
	}
	
	public SmsRemoteService getSmsRemoteService() {
		return smsRemoteService;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
