package com.lvmama.back.sweb.ord;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
@ParentPackage("json-default")
@Results( { @Result(name = "resource_approve", location = "/WEB-INF/pages/back/ord/resource_approve.jsp") })

/**
 * 资源审核action
 * @author MrZhu
 *
 */
public class ResourceApproveAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
