package com.lvmama.sso.web.tuiguang;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 通用类：绑定手机和邮箱
 * @author ganyingwen
 *
 */
@Results({
	@Result(name = "index", location = "/WEB-INF/ftl/tuiguang/${pagePath}/index.ftl", type = "freemarker"),
	@Result(name = "bindView", location =
		"/WEB-INF/ftl/tuiguang/${pagePath}/bindMobileAndEmail.ftl", type = "freemarker"),
	@Result(name = "application1BindView", location =
		"/WEB-INF/ftl/tuiguang/${pagePath}/bindMobileAndEmail_1.ftl", type = "freemarker"),
	@Result(name = "application1Index", location = "http://www.lvmama.com/zt/promo/free-qiandaohu/index.php", type = "redirect"),
	@Result(name = "toBindSucc", location = "${bindSuccURL}", type = "redirect")
})
public class BindMobileAndEmailAction extends BaseAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1456545615616L;
	/**
	 * 登录的用户
	 */
	private UserUser users;
	/**
	 * 用户逻辑接口
	 */
	private UserUserProxy userUserProxy;
	/**
	 * 专题所在路径
	 */
	private String pagePath = "general";
	/**
	 * 跳转到绑定成功URL
	 */
	private String bindSuccURL;

    /**
     * 绑定手机和邮箱的前一页
     * @return  去绑定手机和邮箱的前一页
     */
    @Action("/bind/index")
    public String index() {
    	 users = (UserUser) getSession(Constant.SESSION_FRONT_USER);
    	 return "index";
    }

    /**
     * 绑定申请
     * http://login.lvmama.com/nsso/bind/application1.do?pagePath=membercard&bindSuccURL=http://www.lvmama.com/others/member_card/login2.php
     * @return 没绑定成功，返回绑定页面，绑定成功，跳到成功页面或跳转到其它(指定)绑定成功页面
     */
    @Action("/bind/application")
    public String application() {
    	users = (UserUser) getSession(Constant.SESSION_FRONT_USER);
    	if (users == null) {
    		return "index";
    	}
    	users = userUserProxy.getUserUserByUserNo(users.getUserId());
    	putSession(Constant.SESSION_FRONT_USER, users);

    	if (null  == users.getMobileNumber()
    			|| !"Y".equals(users.getIsMobileChecked())
				|| null == users.getEmail()
				|| !"Y".equals(users.getIsEmailChecked())) {
    		return "bindView";
    	}
    	return "toBindSucc";
    }

    /**
     * 绑定申请
     * @return 没绑定成功，返回绑定页面，绑定成功，跳到成功页面或跳转到其它(指定)绑定成功页面
     */
    @Action("/bind/application1")
    public String application1() {
    	users = (UserUser) getSession(Constant.SESSION_FRONT_USER);
    	if (users == null) {
    		return "application1Index";
    	}
    	users = userUserProxy.getUserUserByUserNo(users.getUserId());
    	putSession(Constant.SESSION_FRONT_USER, users);

    	if (null  == users.getMobileNumber()
    			|| !"Y".equals(users.getIsMobileChecked())
				|| null == users.getEmail()
				|| !"Y".equals(users.getIsEmailChecked())) {
    		return "application1BindView";
    	}
    	return "toBindSucc";
    }
    
	public UserUser getUsers() {
		return users;
	}

	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(final String pagePath) {
		this.pagePath = pagePath;
	}

	public String getBindSuccURL() {
		return bindSuccURL;
	}

	public void setBindSuccURL(final String bindSuccURL) {
		this.bindSuccURL = bindSuccURL;
	}
}
