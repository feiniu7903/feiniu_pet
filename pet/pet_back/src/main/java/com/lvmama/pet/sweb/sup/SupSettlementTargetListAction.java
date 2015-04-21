/**
 * 
 */
package com.lvmama.pet.sweb.sup;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;

/**
 * @author yangbin
 *
 */
public class SupSettlementTargetListAction extends BackBaseAction{

	SettlementTargetService settlementTargetService;
	
	@Action("/sup/target/settlementList")
	public String index(){
		return "index";
	}
}
