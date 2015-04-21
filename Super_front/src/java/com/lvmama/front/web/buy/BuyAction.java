package com.lvmama.front.web.buy;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.vo.ViewBuyInfo;
import com.lvmama.front.web.BaseAction;

@Results({
	@Result(name="purse",location="/purse/fill.do",type="redirect")
})
public class BuyAction extends BaseAction {
	
	private ViewBuyInfo buyInfo = new ViewBuyInfo();
	
	@Action("/buy/buy")
	public String execute() throws Exception {
		return SUCCESS;
	}

	@Action("/purse/buy")
	public String ticket() throws Exception {
		putSession("buyInfo", buyInfo);
		return "purse";
	}

	public ViewBuyInfo getBuyInfo() {
		return buyInfo;
	}
	
	public ViewBuyInfo getBuyinfo() {
		return buyInfo;
	}
	
}
