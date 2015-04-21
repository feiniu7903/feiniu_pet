package com.lvmama.search.action.web;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.utils.CommHeaderUtil;

@Namespace("/")
public class HeaderAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	@Action("head")
	public void head() throws IOException {
			CommHeaderUtil.getHeadContent(getResponse().getWriter());
	}
}
