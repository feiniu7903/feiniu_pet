package com.lvmama.pet.sweb.fin.settlement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.fin.FinSupplierAllot;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.service.fin.FinSupplierAllotService;
import com.lvmama.comm.pet.service.perm.UserOrgAuditPermService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SETTLEMENT_COMPANY;
import com.lvmama.comm.vo.Constant.SETTLEMENT_PERIOD;
import com.lvmama.pet.sweb.fin.common.FinPageAction;

/**
 * 分单管理Action
 * 
 * @author zhangwenjun
 * 
 */
@Results(value={
		@Result(name="index", location="/WEB-INF/pages/back/fin/settlement/fin_supplier_allot/fin_supplier_allot_index.ftl"),
		@Result(name="detail", location="/WEB-INF/pages/back/fin/settlement/fin_supplier_allot/fin_supplier_allot_detail.ftl")
})
@Namespace("/fin/allot")
public class FinSupplierAllotAction extends FinPageAction {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 我方结算主体
	 */
	private SETTLEMENT_COMPANY[] settlementCompany;

	/**
	 * 结算周期
	 */
	private Constant.SETTLEMENT_PERIOD[] settlementPeriod;

	private FinSupplierAllotService finSupplierAllotService;
	
	// 权限roleId
	public static final Long ROLE_ID = 374L;
	
	// 权限接口
	private UserOrgAuditPermService userOrgAuditPermService;

	@Override
	public Map<String, Object> initRequestParameter() {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = getRequest();
		extractRequestParam(map,"supplierId",request);
		extractRequestParam(map,"status",request);
		extractRequestParam(map,"parymentTarget",request);
		extractRequestParam(map,"userName",request);
		extractRequestParam(map,"settlementPeriod",request);
		extractRequestParam(map,"settlementCompany",request);
		
		return map;
	}
	
	/**
	 * 进入分单管理
	 * 
	 * @return 管理页面
	 */
	@Action("index")
	public String advancedeposits() {
		settlementCompany = SETTLEMENT_COMPANY.values();
		settlementPeriod = SETTLEMENT_PERIOD.values();
		// 获取当前登陆人的名称
		PermUser user = getSessionUser();
		Long userId = user.getUserId();
		Boolean flag = userOrgAuditPermService.checkUserRole(userId, ROLE_ID);
		getRequest().setAttribute("flag", flag);
//		getRequest().setAttribute("flag", false);
		
		return "index";
	}
	
	/**
	 * 查询供应商的分单信息
	 * 
	 * @return 结果页面
	 */
	@Action("search")
	public void search() {
		Page<FinSupplierAllot> page = finSupplierAllotService.search(this.initSearchParameter());
		sendAjaxResultByJson(JSONObject.fromObject(page).toString());
	}
	
	/**
	 * 进入供应商详情页面
	 * 
	 * @param id
	 * @return
	 */
	@Action("searchSupplierDetail")
	public String searchSupplier() {
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		// 查询供应商详情
		FinSupplierMoney supplierInfo = finSupplierAllotService.searchSupplier(supplierId);
		// 结算对象集合
		List<SupSettlementTarget> targetList = finSupplierAllotService.searchTarget(supplierId);
		
		getRequest().setAttribute("supplierInfo", supplierInfo);
		getRequest().setAttribute("targetList", targetList);
		
		return "detail";
	}
	
	/**
	 * 根据用户名查询用户信息
	 */
	@Action("queryUser")
	public void queryUser(){
		String userName = getRequest().getParameter("userName");
		PermUser user = finSupplierAllotService.queryUser(userName);
		FinSupplierAllot allot = new FinSupplierAllot();
		allot.setRealName(user.getRealName());
		sendAjaxResultByJson(JSONObject.fromObject(allot).toString());
	}
	
	/**
	 * 添加/修改供应商分单信息
	 * 
	 * 
	 * @return
	 */
	@Action("update")
	public void update(){
		FinSupplierAllot finSupplierAllot = new FinSupplierAllot();
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierIdAdd"));
		String userName = getRequest().getParameter("userNameAdd");
		finSupplierAllot.setSupplierId(supplierId);
		finSupplierAllot.setUserName(userName);
		finSupplierAllot.setCreateUser(getSessionUserName());
		
		finSupplierAllotService.update(finSupplierAllot);
	}
	
	/**
	 * 删除供应商分单信息
	 * @param supplierId
	 */
	@Action("delete")
	public void delete(){
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		finSupplierAllotService.delete(supplierId);
	}
	
	/**
	 * 批量修改供应商分单信息
	 * @param supplierId
	 */
	@Action("updateBatch")
	public void updateBatch(){
		String supplierIds = getRequest().getParameter("supplierIds");
		String[] supplierArray = supplierIds.split(",");
		String userNameBatch = getRequest().getParameter("userNameBatch");
		// 循环修改分单信息
		for(int i=0; i<supplierArray.length; i++){
			FinSupplierAllot finSupplierAllot = new FinSupplierAllot();
			Long supplierId = Long.parseLong(supplierArray[i]);
			finSupplierAllot.setSupplierId(supplierId);
			finSupplierAllot.setUserName(userNameBatch);
			finSupplierAllot.setCreateUser(getSessionUserName());
			finSupplierAllotService.update(finSupplierAllot);
		}
	}
	
	/**
	 * 删除供应商分单信息
	 * @param supplierId
	 */
	@Action("deleteBatch")
	public void deleteBatch(){
		String supplierIds = getRequest().getParameter("supplierIds");
		String[] supplierArray = supplierIds.split(",");
		// 循环删除分单信息
		for(int i=0; i<supplierArray.length; i++){
			finSupplierAllotService.delete(Long.parseLong(supplierArray[i]));
		}
	}

	/**
	 * 导出
	 */
	@Action("exportAllot")
	public void export(){
			String templatePath = "/WEB-INF/resources/template/allot_detail.xls";
			String fileName = "allot_detail" + DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
			Map<String, Object> map = new HashMap<String, Object>();
			List<FinSupplierAllot> list = finSupplierAllotService.exportAllot(this.initSearchParameter());
			if(list.size()==0){
				FinSupplierAllot a = new FinSupplierAllot();
				list.add(a);
			}
			map.put("list",list);
			this.exportXLS(map, templatePath, fileName);
	}
	
//	/**
//	 * 进入供应商详情页面
//	 * 
//	 * @param id
//	 * @return
//	 */
//	@Action("sub/search/{id}")
//	public String sub_search(@PathVariable("id") Integer id, Model model) {
//		model.addAttribute("init", true);
//		model.addAttribute("settlementId", id);
//		return "settlement/allotOrderManage/supplierDetail";
//	}

	public FinSupplierAllotService getFinSupplierAllotService() {
		return finSupplierAllotService;
	}

	public void setFinSupplierAllotService(
			FinSupplierAllotService finSupplierAllotService) {
		this.finSupplierAllotService = finSupplierAllotService;
	}

	public SETTLEMENT_COMPANY[] getSettlementCompany() {
		return settlementCompany;
	}

	public void setSettlementCompany(SETTLEMENT_COMPANY[] settlementCompany) {
		this.settlementCompany = settlementCompany;
	}

	public UserOrgAuditPermService getUserOrgAuditPermService() {
		return userOrgAuditPermService;
	}

	public void setUserOrgAuditPermService(
			UserOrgAuditPermService userOrgAuditPermService) {
		this.userOrgAuditPermService = userOrgAuditPermService;
	}

	public Constant.SETTLEMENT_PERIOD[] getSettlementPeriod() {
		return settlementPeriod;
	}

	public void setSettlementPeriod(Constant.SETTLEMENT_PERIOD[] settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}
	
}
