package com.lvmama.passport.web.pass;

import java.util.Date;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassDeviceService;
import com.lvmama.passport.utils.ZkMessage;

/**
 * 设备编号管理
 * 
 * @author chenlinjun
 * 
 */
@SuppressWarnings("unchecked")
public class PassDeviceEditAction extends ZkBaseAction {
	private static final long serialVersionUID = 9211381080877867260L;
	private PassDevice passDevice;
	private PassCodeService passCodeService;
	private boolean isNew;
	private Long deviceId;

	/**
	 * 判断是在新增还是修改
	 */
	protected void doBefore() throws Exception {
		if (deviceId == null) {
			passDevice = new PassDevice();
			isNew = true;
		} else {
			passDevice=passCodeService.getPaasDeviceByDeviceId(deviceId);
			isNew = false;
		}
	}

	/**
	 * 新增或修改
	 */
	public void doSave() {
		if (validate()) {
			passDevice.setCreateDate(new Date());
			if (isNew) {
				PassDevice temp=this.passCodeService.getPaasDeviceByDeviceNoAndProviderId(passDevice.getDeviceNo(), passDevice.getProviderId());
				if(temp==null){
					this.passCodeService.addPaasDevice(passDevice);
					this.closeWindow();
				}else{
					ZkMessage.showWarning("该设备编号："+passDevice.getDeviceNo()+" 已经存在！");
				}
			} else {
				this.passCodeService.updatePaasDevice(passDevice);
				this.closeWindow();
			}
		}

	}

	public boolean validate() {
		if (passDevice.getTargetId() == null) {
			ZkMessage.showWarning("您必须输入通关点编号信息");
			return false;
		}else if("NO_TYPE".equalsIgnoreCase(passDevice.getDeviceType())){
			ZkMessage.showWarning("您必须输入设备类型信息");
			return false;
		}
		return true;
	}

	public PassDevice getPassDevice() {
		return passDevice;
	}

	public void setPassDevice(PassDevice passDevice) {
		this.passDevice = passDevice;
	}
 
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

}
