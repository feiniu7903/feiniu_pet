/**
 * 
 */
package com.lvmama.pet.job.quartz;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;

/**
 * @author liuyi
 *
 */
public class AutoDeleteExpiredUserCertCodeJob implements Runnable{
	
	private static final Log log = LogFactory.getLog(AutoDeleteExpiredUserCertCodeJob.class);
	
	private UserUserProxy userUserProxy;
	
	@Override
	public void run()
	{
		if (Constant.getInstance().isJobRunnable() && Constant.getInstance().isJobRunnable("AutoDeleteExpiredUserCertCodeJob")) {
			log.info("auto delete expired cert code begin");
			//定时删除1小时前的激活码
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("30MinutesBefore", "true");
			userUserProxy.deleteUserCertCode(parameters);
			log.info("auto delete expired cert code end");
		}
	}

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

}
