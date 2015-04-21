package com.lvmama.passport.web.pass;

import java.util.List;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassPortCodeService;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.utils.ZkMessage;

/**
 * @author ShiHui
 */
public class PassPortCodeAction extends ZkBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2405143621721026055L;
	/**
	 * 通关点ID
	 */
	private Long codeId;
	/**
	 * 通关信息列表
	 */
	private List<PassPortCode> portCodeList;
	private PassCodeService passCodeService;

	/**
	 * 页面显示前查询
	 */
	protected void doBefore() throws Exception {
		portCodeList = passCodeService.queryPassPortCodes(codeId);
	}

	/**
	 * 修改使用状态
	 * 
	 * @param portId
	 * @param codeId
	 */
	public void updateStatus( Long codeId,Long targetId) {
		PassPortCode passPortCode = new PassPortCode();
		passPortCode.setCodeId(codeId);
		passPortCode.setTargetId(targetId);
		passPortCode.setStatus(PassportConstant.PASSCODE_USE_STATUS.USED.name());
		passCodeService.updatePassPortCode(passPortCode);
		ZkMessage.showInfo("修改成功");
	}
 
	public List<PassPortCode> getPortCodeList() {
		return portCodeList;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

}
