package com.lvmama.pet.sweb.recon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.fin.FinGlSubjectCfg;
import com.lvmama.comm.pet.service.fin.FinGLBizService;
import com.lvmama.comm.pet.service.fin.FinGlSubjectCfgService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant.FILIALE_NAME;
import com.lvmama.comm.vo.Constant.FIN_GL_ACCOUNT_TYPE;
import com.lvmama.comm.vo.Constant.FIN_SUBJECT_TYPE;
import com.lvmama.comm.vo.Constant.PRODUCT_TYPE;
import com.lvmama.comm.vo.Constant.RECON_GW_TYPE;
import com.lvmama.comm.vo.Constant.REGION_NAMES;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;

/**
 * 财务做账科目配置控制器
 * 
 * @author taiqichao
 * 
 */
@Results({ 
	@Result(name = "finGlSubjectCfgList", location = "/WEB-INF/pages/back/recon/fin_subject_cfg_list.jsp"),
	@Result(name = "finGlSubjectCfgEdit", location = "/WEB-INF/pages/back/recon/fin_subject_cfg_edit.jsp") })
public class FinGLSubjectCfgAction extends BackBaseAction {

	private static final long serialVersionUID = -1L;

	/**
	 * 科目配置ID
	 */
	private Long subjectConfigId;
	
	private FinGlSubjectCfg finGLSubjectCfg;
	
	private FinGlSubjectCfgService finGlSubjectCfgService;
	
	private FinGLBizService finGLServiceProxy;
	
	//做账类别
	private FIN_GL_ACCOUNT_TYPE[] accountTypes=FIN_GL_ACCOUNT_TYPE.values();
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
	
	/**
	 * 跳转到查询页面
	 * 
	 * @return
	 */
	@Action(value = "/recon/finGlSubjectCfgList")
	public String toFinGLSubjectCfgList() {
		return "finGlSubjectCfgList";
	}

	/**
	 * 处理分页查询
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/recon/queryFinGlSubjectCfg")
	public String doQuery() {
		pagination = initPage();
		pagination.setPageSize(20);
		Map<String, Object> parameters=new HashMap<String, Object>();
		if(null!=finGLSubjectCfg){
			if(StringUtils.isNotBlank(finGLSubjectCfg.getAccountType())){
				parameters.put("accountType", finGLSubjectCfg.getAccountType());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getConfig1())){
				parameters.put("config1", finGLSubjectCfg.getConfig1());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getConfig2())){
				parameters.put("config2", finGLSubjectCfg.getConfig2());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getConfig3())){
				parameters.put("config3", finGLSubjectCfg.getConfig3());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getConfig4())){
				parameters.put("config4", finGLSubjectCfg.getConfig4());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getConfig5())){
				parameters.put("config5", finGLSubjectCfg.getConfig5());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getConfig6())){
				parameters.put("config6", finGLSubjectCfg.getConfig6());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getConfig7())){
				parameters.put("config7", finGLSubjectCfg.getConfig7());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getBorrowSubjectCode())){
				parameters.put("borrowSubjectCode", finGLSubjectCfg.getBorrowSubjectCode());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getLendConfig1())){
				parameters.put("lendConfig1", finGLSubjectCfg.getLendConfig1());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getLendConfig2())){
				parameters.put("lendConfig2", finGLSubjectCfg.getLendConfig2());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getLendConfig3())){
				parameters.put("lendConfig3", finGLSubjectCfg.getLendConfig3());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getLendConfig4())){
				parameters.put("lendConfig4", finGLSubjectCfg.getLendConfig4());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getLendConfig5())){
				parameters.put("lendConfig5", finGLSubjectCfg.getLendConfig5());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getLendConfig6())){
				parameters.put("lendConfig6", finGLSubjectCfg.getLendConfig6());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getLendConfig7())){
				parameters.put("lendConfig7", finGLSubjectCfg.getLendConfig7());
			}
			if(StringUtils.isNotBlank(finGLSubjectCfg.getLendSubjectCode())){
				parameters.put("lendSubjectCode", finGLSubjectCfg.getLendSubjectCode());
			}
		}
		pagination.setTotalResultSize(finGlSubjectCfgService.queryCount(parameters));
		if (pagination.getTotalResultSize() > 0) {
			parameters.put("_startRow", pagination.getStartRows());
			parameters.put("_endRow", pagination.getEndRows());
			List<FinGlSubjectCfg> finList = finGlSubjectCfgService.queryForPage(parameters);
			pagination.setItems(finList);
		}
		pagination.buildUrl(getRequest());
		return "finGlSubjectCfgList";
	}
	
	
	/**
	 * 转到新增修改页
	 * @return
	 */
	@Action(value = "/recon/finSubjectCfg/edit")
	public String toEdit(){
		if(null!=subjectConfigId){
			finGLSubjectCfg=finGlSubjectCfgService.getFinGlSubjectCfg(subjectConfigId);
		}
		return "finGlSubjectCfgEdit";
	}
	
	
	
	/**
	 * 保存科目配置
	 */
	@Action(value="/recon/finSubjectCfg/save")
	public void save(){
		if(null!=finGLSubjectCfg){
			if(null!=finGLSubjectCfg.getSubjectConfigId()){
				finGlSubjectCfgService.updateFinGlSubjectCfg(finGLSubjectCfg);
			}else{
				finGlSubjectCfgService.insertFinGlSubjectCfg(finGLSubjectCfg);
			}
		}
		JSONResult result = new JSONResult();
		result.put("success", true);
		result.output(getResponse());
	}
	
	
	/**
	 * 删除科目配置
	 */
	@Action(value="/recon/finSubjectCfg/remove")
	public void remove(){
		if(null!=subjectConfigId){
			finGlSubjectCfgService.removeFinGlSubjectCfg(subjectConfigId);
		}
		JSONResult result = new JSONResult();
		result.put("success", true);
		result.output(getResponse());
	}
	
	
	/**
	 * 临时初始化科目配置
	 */
	@Action(value="/recon/finSubjectCfg/init")
	public void init(){
		finGLServiceProxy.initCode();
		JSONResult result = new JSONResult();
		result.put("success", true);
		result.output(getResponse());
	}
	

	public void setFinGlSubjectCfgService(
			FinGlSubjectCfgService finGlSubjectCfgService) {
		this.finGlSubjectCfgService = finGlSubjectCfgService;
	}

	public FIN_GL_ACCOUNT_TYPE[] getAccountTypes() {
		return accountTypes;
	}

	public Long getSubjectConfigId() {
		return subjectConfigId;
	}

	public FinGlSubjectCfg getFinGLSubjectCfg() {
		return finGLSubjectCfg;
	}

	public FinGlSubjectCfgService getFinGlSubjectCfgService() {
		return finGlSubjectCfgService;
	}

	public FIN_SUBJECT_TYPE[] getSubjectTypes() {
		return subjectTypes;
	}

	public PRODUCT_TYPE[] getProductTypes() {
		return productTypes;
	}

	public RECON_GW_TYPE[] getReconGwTypes() {
		return reconGwTypes;
	}

	public SUB_PRODUCT_TYPE[] getSubProductTypes() {
		return subProductTypes;
	}

	public REGION_NAMES[] getRegionNames() {
		return regionNames;
	}

	public FILIALE_NAME[] getFilialeNames() {
		return filialeNames;
	}

	public void setFinGLSubjectCfg(FinGlSubjectCfg finGLSubjectCfg) {
		this.finGLSubjectCfg = finGLSubjectCfg;
	}

	public void setSubjectConfigId(Long subjectConfigId) {
		this.subjectConfigId = subjectConfigId;
	}

	public void setFinGLServiceProxy(FinGLBizService finGLServiceProxy) {
		this.finGLServiceProxy = finGLServiceProxy;
	}

	

}
