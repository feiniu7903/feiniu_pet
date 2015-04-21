package com.lvmama.bee.web.eplace;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;

@Results( {
	@Result(name = "passOrderIndex", location = "/WEB-INF/pages/eplace/passport/passOrderIndex.jsp")
	}
)
public class PassOrderIndexAction extends EbkBaseAction {
	
	@Action("/eplace/passOrderIndex")
	public String passOrderIndex(){
		return "passOrderIndex";
	}
}
