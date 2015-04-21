package com.lvmama.passport.web.pass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.api.Bandbox;
import org.zkoss.zul.api.Textbox;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPort;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.spring.SpringBeanProxy;
/**
 * 通关点下拉组件
 * @author chenlinjun
 *
 */
public class MacroChoosePassport extends ZkBaseAction {

	private static final long serialVersionUID = 1L;
	private Bandbox bandPassport;
	private List<PassPort> passPortList;
	
	private Textbox targetId;
	
	private PassCodeService passCodeService=(PassCodeService)SpringBeanProxy.getBean("passCodeService");

	public void changePassport(InputEvent event) {
		String name = event.getValue();
		System.out.println("SupplierName:" + name);
		Map<String,String> param = new HashMap<String,String>();
		if (name!=null && !"".equals(name)) {
			param.put("name", name);
		}
		param.put("maxRows", "10");
		this.passPortList=passCodeService.getPassportByName(param);
	}
	
	public void doAfterCompose(Component win) throws Exception {
		// super.doAfterCompose(win);
		// win.setVariable(win.getId(), this, true);
		Components.wireVariables(win, this);
		// register onXxx event listeners
		Events.addEventListeners(win, this);
		// auto forward
		Components.addForwards(win, this);
		
		if (targetId!=null && targetId.getValue()!=null && !targetId.getValue().equals("")) {
			Long portId=Long.parseLong(this.targetId.getValue());
			System.out.println("in macro : supplierid="+portId);
			PassPort passPort = passCodeService.getPassportByPortId(portId);
			bandPassport.setValue(passPort.getName());
		}
	}

	public Bandbox getBandPassport() {
		return bandPassport;
	}

	public void setBandPassport(Bandbox bandPassport) {
		this.bandPassport = bandPassport;
	}

	public List<PassPort> getPassPortList() {
		return passPortList;
	}

	public void setPassPortList(List<PassPort> passPortList) {
		this.passPortList = passPortList;
	}

	public Textbox getTargetId() {
		return targetId;
	}

	public void setTargetId(Textbox targetId) {
		this.targetId = targetId;
	}


	
}