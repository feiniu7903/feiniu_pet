package com.lvmama.pet.sweb.visa.document;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocument;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;
import com.lvmama.comm.pet.service.visa.VisaApplicationDocumentService;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.vo.Constant;

/**
 * 签证资料管理
 * @author Brian
 *
 */
@Results({
	@Result(name = "list", location = "/WEB-INF/pages/back/visa/document/list.jsp"),
	@Result(name = "add", location = "/WEB-INF/pages/back/visa/document/add.jsp"),
	@Result(name = "view", location = "/WEB-INF/pages/back/visa/document/view.jsp"),
	@Result(name = "copy", location = "/WEB-INF/pages/back/visa/document/copy.jsp"),
	@Result(name = "page", location = "/WEB-INF/pages/back/visa/document/page.jsp")
})
public class DocumentAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 5281368066239846011L;
	/**
	 * 签证材料远程服务
	 */
	private VisaApplicationDocumentService visaApplicationDocumentService;
	/**
	 * 查询条件——资料标识
	 */
	private Long documentId;
	/**
	 * 查询条件——国家
	 */
	private String country;  
	/**
	 * 查询条件——签证类型
	 */
	private String visaType;
	/**
	 * 查询条件——出签城市
	 */
	private String city;
	/**
	 * 查询条件——人群
	 */
	private String occupation;
	/**
	 * 签证资料
	 */
	private VisaApplicationDocument visaApplicationDocument;
	/**
	 * 签证资料明细
	 */
	private List<VisaApplicationDocumentDetails> visaApplicationDocumentDetailsList;
	private List<VisaApplicationDocument> documentlist;
	@Action("/visa/document/index")
	public String index() {
		Map<String,Object> param = initParam();
		pagination = initPage();
		
		param.put("_startRow", pagination.getStartRows() - 1);
		param.put("_endRow", pagination.getEndRows());
		
		pagination.setTotalResultSize(visaApplicationDocumentService.count(param));
		pagination.setItems(visaApplicationDocumentService.query(param));
		pagination.setUrl(WebUtils.getUrl(this.getRequest()));

		return "list";
	}
	
	@Action("/visa/document/query")
	public String query(){
		Map<String,Object> param = initParam();
		param.put("_startRow",0);
		param.put("_endRow",100);
		documentlist=visaApplicationDocumentService.query(param);
		return "page";
	}
	
	@Action("/visa/document/add")
	public String add() {
		return "add";
	}
	
	@Action("/visa/document/save")
	public void save() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null == visaApplicationDocument
				|| StringUtils.isBlank(visaApplicationDocument.getCountry())
				|| StringUtils.isBlank(visaApplicationDocument.getVisaType())
				|| StringUtils.isBlank(visaApplicationDocument.getCity())
				|| StringUtils.isBlank(visaApplicationDocument.getOccupation())) {
			json.put("message", "缺失必要数据，无法保存");
		}
		if (null == visaApplicationDocument.getDocumentId()) {
			visaApplicationDocument = visaApplicationDocumentService.insert(visaApplicationDocument.getCountry(), visaApplicationDocument.getVisaType(), visaApplicationDocument.getCity(), visaApplicationDocument.getOccupation(), getSessionUserNameAndCheck());
			if (null == visaApplicationDocument) {
				json.put("message", "该材料已经存在，无法重复保存");
			} else {
				json.put("success", true);
				json.put("message", "新增签证材料成功");
			}
		} else {
			json.put("success", true);
			json.put("message", "更新签证材料成功");
		}
		getResponse().getWriter().print(json.toString());
	}
	
	@Action("/visa/document/view")
	public String view() {
		if (null == documentId) {
			return ERROR;
		}
		visaApplicationDocument = visaApplicationDocumentService.queryByPrimaryKey(documentId);
		visaApplicationDocumentDetailsList = visaApplicationDocumentService.queryDetailsByDocumentId(documentId);
		
		return "view";
	}
	
	@Action("/visa/document/del")
	public void del() throws IOException {
		JSONObject json = new JSONObject();
		if (null != documentId) {
			visaApplicationDocumentService.delete(documentId, getSessionUserNameAndCheck());
		}
		json.put("success", true);
		getResponse().getWriter().print(json.toString());
	}
	
	@Action("/visa/document/preCopy")
	public String preCopy() {
		if (null == documentId) {
			return ERROR;
		}
		visaApplicationDocument = visaApplicationDocumentService.queryByPrimaryKey(documentId);
		return "copy";
	}
	
	@Action("/visa/document/copy")
	public void copy() throws IOException {
		JSONObject json = new JSONObject();
		if (null == documentId || null == visaApplicationDocument) {
			json.put("success", false);
			json.put("message", "缺失必要数据，无法保存");
		} else {
			visaApplicationDocument = visaApplicationDocumentService.copy(documentId, visaApplicationDocument, getSessionUserNameAndCheck());
			if (null == visaApplicationDocument) {
				json.put("success", false);
				json.put("message", "目标签证资料已存在，无法复制");
			} else {
				json.put("success", true);
				json.put("message", "复制成功");
			}
		}
		getResponse().getWriter().print(json.toString());
	}
	
	/**
	 * 初始化查询参数
	 * @return
	 */
	private Map<String, Object> initParam() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(country)) {
			param.put("country", country);
		}
		if (StringUtils.isNotBlank(visaType)) {
			param.put("visaType", visaType);
		}
		if (StringUtils.isNotBlank(city)) {
			param.put("city", city);
		}
		if (StringUtils.isNotBlank(occupation)) {
			param.put("occupation", occupation);
		}
		return param;
	}
	

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getVisaType() {
		return visaType;
	}
	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
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
	public VisaApplicationDocument getVisaApplicationDocument() {
		return visaApplicationDocument;
	}
	public void setVisaApplicationDocument(
			VisaApplicationDocument visaApplicationDocument) {
		this.visaApplicationDocument = visaApplicationDocument;
	}
	public Long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	public List<VisaApplicationDocumentDetails> getVisaApplicationDocumentDetailsList() {
		return visaApplicationDocumentDetailsList;
	}

	public void setVisaApplicationDocumentService(
			VisaApplicationDocumentService visaApplicationDocumentService) {
		this.visaApplicationDocumentService = visaApplicationDocumentService;
	}

	public List<VisaApplicationDocument> getDocumentlist() {
		return documentlist;
	}

}
