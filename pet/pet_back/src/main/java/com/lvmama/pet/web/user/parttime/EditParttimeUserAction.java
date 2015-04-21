package com.lvmama.pet.web.user.parttime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Messagebox;

import com.lvmama.comm.pet.po.pub.ComParttimeUser;
import com.lvmama.comm.pet.service.pub.ComParttimeUserService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.pet.web.user.imp.UserChannelBaseAction;

public class EditParttimeUserAction extends UserChannelBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -5767431005308793791L;
	private final ComParttimeUserService comParttimeUserservice = (ComParttimeUserService) SpringBeanProxy
			.getBean("comParttimeUserService");

	private ComParttimeUser comParttimeUser; // 兼职人员
	private String parttimeUserId; // 兼职人员标识

	@Override
	public void doBefore() {
		if (!StringUtils.isEmpty(parttimeUserId)) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", parttimeUserId);
			List<ComParttimeUser> list = comParttimeUserservice.query(m);
			comParttimeUser = (list != null && list.size() > 0) ? list.get(0)
					: new ComParttimeUser();
		}
		if (null == comParttimeUser) {
			comParttimeUser = new ComParttimeUser();
			comParttimeUser.setConfirmSms("感谢您参与了XXX活动，回复\"Y\"，即可成为驴妈妈会员，尽享门票、自由行、特色酒店、旅游团购、跟团游等超值优惠！【驴妈妈旅游网】");
			comParttimeUser.setSmsTemplate("欢迎您成为网站会员，您的用户名为${username}，密码为${password}。请登陆网址:http://www.lvmama.com 客服电话：1010-6060。我们将竭诚为您服务！");
			comParttimeUser.setMailTemplate("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>驴妈妈注册成功</title></head><body>感谢您注册驴妈妈旅游网！<br/>您的初始账号为：${username}<br/>初始密码为：${password}<br/>为了您的账户安全，请尽快登录网站，修改您的账号信息！<br/>网址：<a href=\"http://www.lvmama.com/\" target=\"_blank\">www.lvmama.com</a><br/>客服电话：1010-6060<br/></body></html>");
		} 
	}

	/**
	 * 新增促销员与渠道关系信息
	 */
	public void insertUserChannel() throws Exception {
		if (validate()) {
			comParttimeUser.setChannelId((Long)searchConds.get("channelId"));
			comParttimeUserservice.insert(comParttimeUser);
			Messagebox.show("创建促销员成功", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			super.refreshParent("search");
			super.closeWindow();
		} else {
			Messagebox.show("促销员已存在", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}
	
	/**
	 * 新增促销员与渠道关系信息
	 */
	public void update() throws Exception {
		comParttimeUserservice.update(comParttimeUser);
		Messagebox.show("更新促销员成功", "提示", Messagebox.OK,
				Messagebox.INFORMATION);
		super.refreshParent("search");
		super.closeWindow();
	}	

	/**
	 * 验证渠道标识，渠道名称是否为空
	 * 
	 * @param value
	 */
	@Override
	public boolean validate() {
		super.validate();
		try {
			if (comParttimeUser.getUserName() == null
					|| "".equals(org.apache.commons.lang3.StringUtils
							.trim(comParttimeUser.getUserName()))) {
				Messagebox.show("请输入促销员名称", "警告", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return false;
			}
			if (comParttimeUser.getPassWord() == null
					|| "".equals(org.apache.commons.lang3.StringUtils
							.trim(comParttimeUser.getPassWord()))) {
				Messagebox.show("请输入密码", "警告", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return false;
			}
			if (comParttimeUser.getChannelId() == null) {
				if (searchConds.get("channelId") == null
						|| "".equals(org.apache.commons.lang3.StringUtils
								.trim(searchConds.get("channelId").toString()))) {
					Messagebox.show("请选择渠道标识", "警告", Messagebox.OK,
							Messagebox.EXCLAMATION);
					return false;
				}
			}
			if (comParttimeUser.getCityId() == null) {
				Messagebox.show("请选择所属城市", "警告", Messagebox.OK,
							Messagebox.EXCLAMATION);
					return false;
			}
			if (comParttimeUser.getSmsTemplate() == null
					|| "".equals(org.apache.commons.lang3.StringUtils
							.trim(comParttimeUser.getSmsTemplate()))) {
				Messagebox.show("请定义确认的短信模板", "警告", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return false;
			}
			if (comParttimeUser.getConfirmSms() == null
					|| "".equals(org.apache.commons.lang3.StringUtils
							.trim(comParttimeUser.getConfirmSms()))) {
				Messagebox.show("请定义确认的短信模板", "警告", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return false;
			}
			if (comParttimeUser.getSmsTemplate() == null
					|| "".equals(org.apache.commons.lang3.StringUtils
							.trim(comParttimeUser.getSmsTemplate()))) {
				Messagebox.show("请定义注册成功的短信模板", "警告", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return false;
			}
			if (comParttimeUser.getMailTemplate() == null
					|| "".equals(org.apache.commons.lang3.StringUtils
							.trim(comParttimeUser.getMailTemplate()))) {
				Messagebox.show("请定义确认的邮件模板", "警告", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return false;
			}			
		} catch (Exception e) {

		}
		return true;
	}
	
	public void setParttimeUserId(String parttimeUserId) {
		this.parttimeUserId = parttimeUserId;
	}

	public String getCityId() {
		return comParttimeUser.getCityId();
	}
	
	public void changeCity(String cityId) {
		comParttimeUser.setCityId(cityId);
	}	
	
	public ComParttimeUser getComParttimeUser() {
		return comParttimeUser;
	}	
}
