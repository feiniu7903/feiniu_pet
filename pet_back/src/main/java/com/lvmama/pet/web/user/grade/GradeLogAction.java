package com.lvmama.pet.web.user.grade;

import java.util.List;

import com.lvmama.comm.pet.po.user.UserGradeLog;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;


/**
 * 用户等级Action
 * @author yangchen
 */
public class GradeLogAction extends com.lvmama.pet.web.BaseAction {

	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -5590942859878909139L;
	/** 用户ID **/
	private String uuId;
	/** 日志列表 **/
	private List<UserGradeLog> userGradeLogList;
	/** SSO用户信息远程服务 */
	private UserUserProxy userUserProxy = (UserUserProxy) SpringBeanProxy
			.getBean("userUserProxy");

	@Override
	public void doBefore() {
		userGradeLogList = userUserProxy.queryUserGradeLogs(uuId);
	}

	public List<UserGradeLog> getUserGradeLogList() {
		return userGradeLogList;
	}

	public void setUserGradeLogList(final List<UserGradeLog> userGradeLogList) {
		this.userGradeLogList = userGradeLogList;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(final String uuId) {
		this.uuId = uuId;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
}
