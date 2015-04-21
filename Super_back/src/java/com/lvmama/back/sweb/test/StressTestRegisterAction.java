package com.lvmama.back.sweb.test;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProducer;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.ComIpsService;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@ParentPackage("json-default")
public class StressTestRegisterAction extends ActionSupport {

    /**
     * 用户邮箱主站地址
     */
    private String userMailHost;

    /**
     * 激活状态 0:成功，1：已激活，2:激活出错
     */
    private int activeState = 0;

    private boolean firstCheck = false;
    private UserUser sessionRegisterUser;
    /**
     * 激活类型
     */
    private String activeMailType;
    /**
     * 用户的标识
     */
    protected Long userId;

    /**
     * 用户的会员卡号
     */
    protected String membershipCard;
    /**
     * 用户的手机号
     */
    protected String mobile;
    /**
     * 用户的邮箱
     */
    protected String email;
    /**
     * 用户手机号或者邮箱地址
     */
    protected String mobileOrEMail;
    /**
     * 用户名
     */
    protected String userName;
    /**
     * 密码
     */
    protected String password;
    /**
     * 渠道
     */
    protected String channel;
    /**
     * SSO系统的消息生成者
     */

    protected SSOMessageProducer ssoMessageProducer;

    /**
     * 用户服务类
     */

    protected UserUserProxy userUserProxy;
    /**
     * 工具类。通过IP地址获取访问主机所在地
     */

    protected ComIpsService comIpsService;


    /**
     * 城市ID
     */
    protected String cityId;

    private Map<String, Object> jsonMap = new HashMap<String, Object>();

    @Action(value = "/stressTest/registerUser",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String verification() {
        try {
            if (!StringUtils.isEmpty(membershipCard)
                    && !userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MEMBERSHIPCARD, membershipCard)) {
                throw new Exception("Fail to register by membershipCard \"" + membershipCard + "\"!");
            }
            if (!(StringUtil.validUserName(userName) &&
                    userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.USER_NAME, userName))) {
                throw new Exception("Fail to register by userName \"" + userName + "\"!");
            }
            if (StringUtils.isEmpty(password)) {
                throw new Exception("Fail to register using empty password!");
            }
            if (!StringUtils.isEmpty(mobile)) {
                if (!(StringUtil.validMobileNumber(mobile) && userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile))) {
                    throw new Exception("Fail to register by mobile \"" + mobile + "\"!");
                }
            }
            if (!StringUtils.isEmpty(email)) {
                if (!(StringUtil.validEmail(email) && userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.EMAIL, email))) {
                    throw new Exception("Fail to register by mail \"" + email + "\"!");
                }
            }
            if (!StringUtils.isEmpty(mobileOrEMail)) {
                if (StringUtil.validMobileNumber(mobileOrEMail)) {
                    if (userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobileOrEMail)) {
                        mobile = mobileOrEMail;
                    } else {
                        throw new Exception("Fail to register by mobile \"" + mobile + "\"!");
                    }
                } else {
                    if (StringUtil.validEmail(mobileOrEMail)) {
                        if (userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.EMAIL, mobileOrEMail)) {
                            email = mobileOrEMail;
                        } else {
                            throw new Exception("Fail to register by email \"" + email + "\"!");
                        }
                    }
                }
            }
            if (StringUtils.isEmpty(mobile) && StringUtils.isEmpty(email)) {
                throw new Exception("Fail to register using empty mobile or email");
            }


            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("userName", userName);
            parameters.put("nickName", userName);
            parameters.put("realPass", password);
            parameters.put("registerIp", InternetProtocol.getRemoteAddr(getRequest()));
            try {
                parameters.put("userPassword", UserUserUtil.encodePassword(password));
            } catch (NoSuchAlgorithmException nsae) {
                //LOG.error("对用户密码进行加密时出错，明文密码为" + password + ",错误信息:" + nsae.getMessage());
                nsae.printStackTrace();
            }
            if (!StringUtils.isEmpty(mobile)) {
                parameters.put("mobileNumber", mobile);
            }
            if (!StringUtils.isEmpty(email)) {
                parameters.put("email", email);
            }
            if (!StringUtils.isEmpty(membershipCard)) {
                parameters.put("memberShipCard", membershipCard);
            }
            if (!StringUtils.isEmpty(cityId)) {
                parameters.put("cityId", cityId);
            }
            if (!StringUtils.isEmpty(channel)) {
                parameters.put("channel", channel);
            }
            //losc优先于channel
            String losc = this.getCookieValue("oUC");
            if (!StringUtils.isEmpty(losc) && losc.length() >= 6) {
                parameters.put("channel", losc.substring(0, 6));
            }

            //产生用户信息
            sessionRegisterUser = userUserProxy.generateUsers(parameters);
            //保存用户信息至Session
            putSession(Constant.SESSION_REGISTER_USER, sessionRegisterUser);

            //邮件注册用户的页面流向

            sessionRegisterUser.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
            sessionRegisterUser.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
            sessionRegisterUser = userUserProxy.register(sessionRegisterUser);
            try {
                //发送注册成功的短信
                ssoMessageProducer.sendMsg(new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.NORMAL, sessionRegisterUser.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            userId = sessionRegisterUser.getId();
            jsonMap.put("user",sessionRegisterUser);

//            generalLogin(sessionRegisterUser);
            handleUserMailHost();
            jsonMap.put("status", SUCCESS);

        } catch (Exception e) {
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;


    }


    /**
     * 获取用户信息收集服务
     *
     * @return
     */
    private UserActionCollectionService getUserActionCollectionService() {
        return (UserActionCollectionService) SpringBeanProxy.getBean("userActionCollectionService");
    }


    /**
     * 根据用户邮箱，获得邮箱主站地址
     */
    private void handleUserMailHost() {
        userMailHost = userUserProxy.getMailHostAddress(email);
    }

    public String getUserMailHost() {
        return userMailHost;
    }

    public void setUserMailHost(final String userMailHost) {
        this.userMailHost = userMailHost;
    }


    public int getActiveState() {
        return activeState;
    }

    public UserUser getSessionRegisterUser() {
        return sessionRegisterUser;
    }

    public void setSessionRegisterUser(UserUser sessionRegisterUser) {
        this.sessionRegisterUser = sessionRegisterUser;
    }

    public String getActiveMailType() {
        return activeMailType;
    }

    public void setActiveMailType(String activeMailType) {
        this.activeMailType = activeMailType;
    }

    public boolean getFirstCheck() {
        return firstCheck;
    }

    public void setFirstCheck(boolean firstCheck) {
        this.firstCheck = firstCheck;
    }

    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * 获取Cookie的值
     *
     * @param cookieName Cookie名字
     * @return 值
     */
    protected String getCookieValue(final String cookieName) {
        Cookie[] cookies = this.getRequest().getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void putSession(String key, Object obj) {
        ServletUtil.putSession(getRequest(), getResponse(), key, obj);
    }

    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }


    public void setActiveState(int activeState) {
        this.activeState = activeState;
    }

    public boolean isFirstCheck() {
        return firstCheck;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(String membershipCard) {
        this.membershipCard = membershipCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileOrEMail() {
        return mobileOrEMail;
    }

    public void setMobileOrEMail(String mobileOrEMail) {
        this.mobileOrEMail = mobileOrEMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public SSOMessageProducer getSsoMessageProducer() {
        return ssoMessageProducer;
    }

    public void setSsoMessageProducer(SSOMessageProducer ssoMessageProducer) {
        this.ssoMessageProducer = ssoMessageProducer;
    }

    public UserUserProxy getUserUserProxy() {
        return userUserProxy;
    }

    public void setUserUserProxy(UserUserProxy userUserProxy) {
        this.userUserProxy = userUserProxy;
    }

    public ComIpsService getComIpsService() {
        return comIpsService;
    }

    public void setComIpsService(ComIpsService comIpsService) {
        this.comIpsService = comIpsService;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

    public void setJsonMap(Map<String, Object> jsonMap) {
        this.jsonMap = jsonMap;
    }

}
