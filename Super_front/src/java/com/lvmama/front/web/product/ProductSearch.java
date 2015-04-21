package com.lvmama.front.web.product;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.utils.UniformResourceLocator;
import com.lvmama.front.web.BaseAction;

@Results( { 
	@Result(name = "productSearch", location = "http://www.lvmama.com/search/productSearch!destSearch.do?fromDest=${fromDest}&toDest=${toDest}&isTicket=4", type = "redirect")
	})
public class ProductSearch  extends BaseAction {
	private String fromDest;
	private String toDest;
	
	@Action("/product/product_search")
	public String productSearch(){
		if(StringUtils.isNotEmpty(toDest)){
			this.toDest = UniformResourceLocator.decode(toDest);
			this.toDest = UniformResourceLocator.encode(toDest);
		}
		if(StringUtils.isNotEmpty(fromDest)){
			this.fromDest = UniformResourceLocator.decode(fromDest);
			this.fromDest = UniformResourceLocator.encode(fromDest);
		}
		return "productSearch";
	}
	public String getFromDest() {
		return fromDest;
	}
	public void setFromDest(String fromDest) {
		this.fromDest = fromDest;
	}
	public String getToDest() {
		return toDest;
	}
	public void setToDest(String toDest) {
		this.toDest = toDest;
	}
	
	
}
