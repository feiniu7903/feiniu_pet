package com.lvmama.clutter.web.html5;


import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.model.MobileBaiDuExpand;
import com.lvmama.clutter.web.BaseAction;

@ParentPackage("clutterCreateOrderInterceptorPackage")
@ResultPath("/clutterCreateOrderInterceptor")
@Results({ 
	@Result(name = "baidu_expand", location = "/WEB-INF/pages/common/footer.html", type="freemarker")
})
@Namespace("/mobile/html5")
public class BaiDuExpandAction extends BaseAction {
	private static final long serialVersionUID = -616495937367208915L;
	private String hmtPixel;
	@Action("baidu_expand")
	public String baiDuExpand(){
		MobileBaiDuExpand mobileBaiDuExpand=new MobileBaiDuExpand("cb09ebb4692b521604e77f4bf0a61013");
		mobileBaiDuExpand.setDomainName("lvmama.com");

		mobileBaiDuExpand.setHttpServletObjects(this.getRequest(), this.getResponse());

		hmtPixel = mobileBaiDuExpand.trackPageview();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("hmtPixel", hmtPixel);
		this.sendAjaxResult(jsonObj.toString());
		return null;
	}
	public String getHmtPixel() {
		return hmtPixel;
	}
	public void setHmtPixel(String hmtPixel) {
		this.hmtPixel = hmtPixel;
	}
	
}
