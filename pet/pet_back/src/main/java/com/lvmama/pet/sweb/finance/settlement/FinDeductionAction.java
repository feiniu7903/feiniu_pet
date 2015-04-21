package com.lvmama.pet.sweb.finance.settlement;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.fin.FinDeduction;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.service.fin.FinanceDeductionService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.Pagination;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vst.service.VstSuppSupplierService;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;

/**
 * 抵扣款管理
 * @author zhangwenjun
 *
 */
@Results(value={
		@Result(name="index", location="/WEB-INF/pages/back/finance/settlement/fin_deduction/fin_deduction_index.ftl"),
		@Result(name="record", location="/WEB-INF/pages/back/finance/settlement/fin_deduction/fin_deduction_record.ftl")
})
@Namespace("/finance/deduction")
public class FinDeductionAction extends FinancePageAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private FinanceDeductionService financeDeductionService;
	@Autowired
	private VstSuppSupplierService vstSuppSupplierService;
	private Page<FinDeduction> finDeductionPage = new Page<FinDeduction>();

	@Override
	public Map<String, Object> initRequestParameter() {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = getRequest();
		extractRequestParam(map, "supplier", request);
		
		return map;
	}
	
	@Action("index")
	public String index(){
		
		return "index";
	}

	/**
	 * 查看抵扣款
	 */
	@Action("search")
	public void search(){
		Page<FinSupplierMoney> page = financeDeductionService.search(this.initSearchParameter());
		if(null!=page && null!=page.getItems() && !page.getItems().isEmpty()){
			for(FinSupplierMoney finSupplierMoney:page.getItems()){
				Long supplierId = finSupplierMoney.getSupplierId();
				VstSuppSupplierVo vstSuppSupplierVo =  vstSuppSupplierService.findVstSuppSupplierById(supplierId);
				if(null!=vstSuppSupplierVo){
					finSupplierMoney.setSupplierName(vstSuppSupplierVo.getSupplierName());
				}
			}
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(page).toString());
	}
	
	/**
	 * 修改抵扣款
	 */
	@Action("updateDeduction")
	public void updateDeduction(){
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		Long amount = PriceUtil.convertToFen(getRequest().getParameter("amount"));
		String type = getRequest().getParameter("type");
		FinDeduction finDeduction = new FinDeduction();
		finDeduction.setSupplierId(supplierId);
		finDeduction.setAmount(amount);
		finDeduction.setType(type);
		finDeduction.setCreator(getSessionUserName());
		financeDeductionService.updateDeduction(finDeduction);
	}
	
	/**
	 * 查看流水
	 * @return
	 */
	@Action("searchRecord")
	public String searchRecord(){
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", supplierId);
		Long totalResultSize = financeDeductionService.searchRecordCount(params);
		finDeductionPage.setTotalResultSize(totalResultSize);
		finDeductionPage.setCurrentPage(this.page);
		//初始化分页信息
		finDeductionPage.buildUrl(getRequest());
		params.put("skipResults", finDeductionPage.getStartRows() - 1);
		params.put("maxResults", finDeductionPage.getEndRows());
		finDeductionPage.setItems(financeDeductionService.searchRecord(params));
		getRequest().setAttribute("finDeductionPage", finDeductionPage);
//		List<FinDeduction> result = financeDeductionService.searchRecord(this.initSearchParameter());
//		getRequest().setAttribute("result", result);
		String paginationHtml = Pagination.pagination(finDeductionPage);
		getRequest().setAttribute("paginationHtml", paginationHtml);
		return "record";
	}
	public Page<FinDeduction> getFinDeductionPage() {
		return finDeductionPage;
	}

	public void setFinDeductionPage(Page<FinDeduction> finDeductionPage) {
		this.finDeductionPage = finDeductionPage;
	}

}
