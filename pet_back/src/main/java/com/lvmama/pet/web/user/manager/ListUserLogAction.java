package com.lvmama.pet.web.user.manager;

import java.util.ArrayList;
import java.util.List;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.pet.web.BaseAction;

/**
 * 用户管理日志ACTION
 * 
 * @author shihui
 * 
 */
public class ListUserLogAction extends BaseAction {

	private static final long serialVersionUID = -2731494031083440344L;

	private Long uuId;

	private List<ComLog> comLogList = new ArrayList<ComLog>();

	/**
	 * 日志接口
	 */
	private ComLogService comLogService;

	protected void doBefore() throws Exception {
		comLogList = comLogService.queryByObjectId("USER_USER", uuId);
	}

	public List<ComLog> getComLogList() {
		return comLogList;
	}

	public void setComLogList(List<ComLog> comLogList) {
		this.comLogList = comLogList;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public Long getUuId() {
		return uuId;
	}

	public void setUuId(Long uuId) {
		this.uuId = uuId;
	}
}
