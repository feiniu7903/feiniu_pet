package com.lvmama.clutter.web.aboutus;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;


@Results({ 
	@Result(name = "about_us", location = "/WEB-INF/pages/aboutus/about_us.html", type="freemarker"),
	@Result(name = "contact_us", location = "/WEB-INF/pages/aboutus/contact_us.html", type="freemarker"),
	@Result(name = "suggestion", location = "/WEB-INF/pages/aboutus/suggestion.html", type="freemarker")
})
@Namespace("/mobile")
public class AboutusAction extends BaseAction {
	@Action("about_us")
	public String aboutus(){
		return "about_us";
	}
	@Action("contact_us")
	public String contactus(){
		return "contact_us";
	}
	@Action("suggestion")
	public String suggestion(){
		return "suggestion";
	}
}
