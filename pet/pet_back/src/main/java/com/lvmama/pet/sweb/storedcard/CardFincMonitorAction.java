package com.lvmama.pet.sweb.storedcard;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;

/**
 * 储值卡财务监控.
 * @author sunruyi
 *
 */
@Results({
	@Result(name = "cardFincMonitor", location = "/WEB-INF/pages/back/stored/card_finc_monitor.jsp")
})
public class CardFincMonitorAction extends BackBaseAction {
	
	@Action(value = "/stored/cardFincMonitor")
	public String cardFincMonitor(){
		return "cardFincMonitor";
	}
}
