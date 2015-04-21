package com.lvmama.pet.sweb.recon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.fin.FinGLSubjectConfig;
import com.lvmama.comm.pet.service.fin.FinGLSubjectConfigService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant.FILIALE_NAME;
import com.lvmama.comm.vo.Constant.FIN_SUBJECT_TYPE;
import com.lvmama.comm.vo.Constant.PRODUCT_TYPE;
import com.lvmama.comm.vo.Constant.RECON_GW_TYPE;
import com.lvmama.comm.vo.Constant.REGION_NAMES;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;

@Results({
	@Result(name="queryFinGLSubjectConfig",location="/WEB-INF/pages/back/recon/queryFinSubjectConfig.jsp")
})
public class FinGLSubjectConfigAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2382384187139375886L;
	
	private String config1;
	private String config2;
	private String subjectCode;
	private String subjectType;
	private Map<String, Object> params = new HashMap<String,Object>();
	
	private FinGLSubjectConfig finGLSubjectConfig;
	//系统入账设置类型
	private FIN_SUBJECT_TYPE[] subjectTypes=FIN_SUBJECT_TYPE.values();
	//产品类型
	private PRODUCT_TYPE[] productTypes = PRODUCT_TYPE.values();
	//对账网关
	private RECON_GW_TYPE[] reconGwTypes = RECON_GW_TYPE.values();
	//产品子类型
	private SUB_PRODUCT_TYPE[] subProductTypes = SUB_PRODUCT_TYPE.values();
	//区域
	private REGION_NAMES[] regionNames = REGION_NAMES.values();
	//公司
	private FILIALE_NAME[] filialeNames = FILIALE_NAME.values();
	
	private FinGLSubjectConfigService  finGLSubjectConfigService;

	private ComLogService comLogService;
	/**
	 * 跳转到查询页面
	 * 
	 * @return
	 */
	@Action(value = "/recon/finGLSubjectConfig")
	public String gotoPage() {
		return "queryFinGLSubjectConfig";
	}
	
	@Action(value = "/recon/queryFinGLSubjectConfig")
	public String doQuery() {
		// 分页初始化
		pagination = initPage();
		pagination.setPageSize(20);
		getParams();
		pagination.setTotalResultSize(finGLSubjectConfigService.selectRowCount(params));
		if (pagination.getTotalResultSize() > 0) {
			params.put("_startRow", pagination.getStartRows());
			params.put("_endRow", pagination.getEndRows());
			List<FinGLSubjectConfig> finList = finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(params);
			pagination.setItems(finList);
		}
		pagination.buildUrl(getRequest());
		return "queryFinGLSubjectConfig";
	}
	
	@Action(value="/recon/saveFinGLSubjectConfig")
	public void save(){
		if(null!=finGLSubjectConfig){
			if(null!=finGLSubjectConfig.getSubjectConfigId()){
				finGLSubjectConfigService.update(finGLSubjectConfig);
			}else{
				finGLSubjectConfigService.insert(finGLSubjectConfig);
			}
			comLogService.insert("FIN_GL_SUBJECT_CONFIG", null,
					finGLSubjectConfig.getSubjectConfigId(),
					getSessionUserName(), "saveFinGLSubjectConfig", "保存财务配置",
					finGLSubjectConfig.toString(), null);
		}
		JSONResult result = new JSONResult();
		result.put("success", true);
		result.output(getResponse());
	}
	@Action(value="/recon/deleteFinGLSubjectConfig")
	public void delete(){
		if(null!=finGLSubjectConfig){
			finGLSubjectConfigService.delete(finGLSubjectConfig.getSubjectConfigId());
			comLogService.insert("FIN_GL_SUBJECT_CONFIG", null,
					finGLSubjectConfig.getSubjectConfigId(),
					getSessionUserName(), "saveFinGLSubjectConfig", "删除财务配置",
					finGLSubjectConfig.toString(), null);
		}
		JSONResult result = new JSONResult();
		result.put("success", true);
		result.output(getResponse());
	}

	public Map<String, Object> getParams() {
		setparams("config1",config1);
		setparams("config2",config2);
		setparams("subjectCode",subjectCode);
		setparams("subjectType",subjectType);
		return params;
	}

	private void setparams(String key,String value){
		if(!StringUtil.isEmptyString(value)){
			params.put(key, value);
		}
	}
	public FinGLSubjectConfig getFinGLSubjectConfig() {
		return finGLSubjectConfig;
	}

	public void setFinGLSubjectConfig(FinGLSubjectConfig finGLSubjectConfig) {
		this.finGLSubjectConfig = finGLSubjectConfig;
	}

	public FinGLSubjectConfigService getFinGLSubjectConfigService() {
		return finGLSubjectConfigService;
	}

	public void setFinGLSubjectConfigService(
			FinGLSubjectConfigService finGLSubjectConfigService) {
		this.finGLSubjectConfigService = finGLSubjectConfigService;
	}

	public FIN_SUBJECT_TYPE[] getSubjectTypes() {
		return subjectTypes;
	}

	public void setSubjectTypes(FIN_SUBJECT_TYPE[] subjectTypes) {
		this.subjectTypes = subjectTypes;
	}

	public String getConfig1() {
		return config1;
	}

	public void setConfig1(String config1) {
		this.config1 = config1;
	}

	public String getConfig2() {
		return config2;
	}

	public void setConfig2(String config2) {
		this.config2 = config2;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public PRODUCT_TYPE[] getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(PRODUCT_TYPE[] productTypes) {
		this.productTypes = productTypes;
	}

	public SUB_PRODUCT_TYPE[] getSubProductTypes() {
		return subProductTypes;
	}

	public void setSubProductTypes(SUB_PRODUCT_TYPE[] subProductTypes) {
		this.subProductTypes = subProductTypes;
	}

	public REGION_NAMES[] getRegionNames() {
		return regionNames;
	}

	public void setRegionNames(REGION_NAMES[] regionNames) {
		this.regionNames = regionNames;
	}

	public RECON_GW_TYPE[] getReconGwTypes() {
		return reconGwTypes;
	}

	public void setReconGwTypes(RECON_GW_TYPE[] reconGwTypes) {
		this.reconGwTypes = reconGwTypes;
	}

	public FILIALE_NAME[] getFilialeNames() {
		return filialeNames;
	}

	public void setFilialeNames(FILIALE_NAME[] filialeNames) {
		this.filialeNames = filialeNames;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
}
