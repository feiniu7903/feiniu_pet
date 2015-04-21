package com.lvmama.pet.sweb.visa.product;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "list", location = "/WEB-INF/pages/back/visa/product/list.jsp"),
	@Result(name = "add", location = "/WEB-INF/pages/back/visa/product/add.jsp"),
	@Result(name = "view", location = "/WEB-INF/pages/back/visa/product/view.jsp")
})
public class VisaProductAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -3673004960466069986L;
	
	@Action("/prod/visa/index")
	public String index() {
		Map<String,Object> param = initParam();
		pagination = initPage();
		
		param.put("_startRow", pagination.getStartRows() - 1);
		param.put("_endRow", pagination.getEndRows());
		
//		pagination.setTotalResultSize(visaApplicationDocumentService.count(param));
//		pagination.setItems(visaApplicationDocumentService.query(param));
		pagination.setUrl(WebUtils.getUrl(this.getRequest()));

		return "list";
	}
	
	@Action("/prod/visa/add")
	public String add() {
		return "add";
	}	
	
	public Map<String, String> getVisaTypeList() {
		Map<String,String> map = Constant.VISA_TYPE.BUSINESS_VISA.getMap();
		map.put("", "----请选择----");
		return map;
	}
	public Map<String, String> getVisaCityList() {
		Map<String,String> map = Constant.VISA_CITY.SH_VISA_CITY.getMap();
		map.put("", "----请选择----");
		return map;
	}
	public Map<String, String> getVisaOccupationList() {
		Map<String,String> map = Constant.VISA_OCCUPATION.VISA_FOR_EMPLOYEE.getMap();
		map.put("", "----请选择----");
		return map;
	}	
	
	/**
	 * 初始化查询参数
	 * @return
	 */
	private Map<String, Object> initParam() {
		Map<String, Object> param = new HashMap<String, Object>();

		return param;
	}	
}
