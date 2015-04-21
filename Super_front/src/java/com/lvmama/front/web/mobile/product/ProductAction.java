package com.lvmama.front.web.mobile.product;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.front.web.BaseAction;

public class ProductAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2567327216515526960L;
	@Action(value="/m/prod/index",results=@Result(name="prodIndex",location="/WEB-INF/pages/mobile/ticket/t_index.ftl"))
	public String toIndex(){
		return "prodIndex";
	}

}
