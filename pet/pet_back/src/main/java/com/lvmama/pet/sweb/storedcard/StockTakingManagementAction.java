package com.lvmama.pet.sweb.storedcard;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;

/**
 * 盘库.
 * @author sunruyi
 *
 */
@Results({
	@Result(name = "stockTakingManagement", location = "/WEB-INF/pages/back/stored/stockTaking_management.jsp")
})
public class StockTakingManagementAction extends BackBaseAction {
	
	@Action(value = "/aa/stored/stockTakingManagement")
	public String stockTakingManagement(){
		return "stockTakingManagement";
	}
	
	@Action(value = "/aa/stored/batchGenerate")
	public String batchGenerate(){
		return "batchGenerate";
	}
}
