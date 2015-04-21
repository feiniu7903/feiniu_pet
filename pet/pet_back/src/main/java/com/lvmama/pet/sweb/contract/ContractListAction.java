package com.lvmama.pet.sweb.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.sup.FinAccountingEntity;
import com.lvmama.comm.pet.service.sup.FinAccountingEntityService;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.vo.Constant;

/**
 * 合同列表页
 * 
 * @author shihui
 * 
 */
@Results({ @Result(name = "contract_list", location = "/WEB-INF/pages/back/contract/contract_list.jsp") })
public class ContractListAction extends BackBaseAction {
	private static final long serialVersionUID = 4780667014323041782L;

	private SupContractService supContractService;

	private String contractNo;

	private String arranger;

	private String partyA;

	private Long supplierId;

	private Long managerId;

	private String supplierName;
	
	private String orderby;
	
	private String contractAudit;
	
	private FinAccountingEntityService finAccountingEntityService;

	@Action("/contract/index")
	public String index() {
		return "contract_list";
	}

	/**
	 * 合同列表查询
	 * */
	@Action("/contract/contractList")
	public String contractList() {
		Map<String, Object> params = buildParams();
		this.pagination = supContractService.getSupContractByParam(params, 10L, this.page);
		this.pagination.buildUrl(getRequest());
		return "contract_list";
	}

	private Map<String, Object> buildParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(contractNo)) {
			params.put("contractNo", contractNo);
		}
		if (supplierId != null) {
			params.put("supplierId", supplierId);
		}

		if (StringUtils.isNotEmpty(arranger)) {
			params.put("arranger", arranger);
		}

		if (StringUtils.isNotEmpty(partyA)) {
			params.put("partyA", partyA);
		}

		if (managerId != null) {
			params.put("managerId", managerId);
		}
		
		if(StringUtils.isNotEmpty(orderby)) {
			params.put("orderby", orderby);
		}
		
		if(StringUtils.isNotEmpty(contractAudit)) {
			params.put("contractAudit", contractAudit);
		}
		return params;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getArranger() {
		return arranger;
	}

	public void setArranger(String arranger) {
		this.arranger = arranger;
	}

	public String getPartyA() {
		return partyA;
	}

	public void setPartyA(String partyA) {
		this.partyA = partyA;
	}

	public void setSupContractService(SupContractService supContractService) {
		this.supContractService = supContractService;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public List<FinAccountingEntity> getFinAccountingEntityList() {
		return finAccountingEntityService.selectEntityList();
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void setFinAccountingEntityService(
			FinAccountingEntityService finAccountingEntityService) {
		this.finAccountingEntityService = finAccountingEntityService;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public Constant.CONTRACT_AUDIT[] getContractAuditList() {
		return Constant.CONTRACT_AUDIT.values();
	}

	public String getContractAudit() {
		return contractAudit;
	}

	public void setContractAudit(String contractAudit) {
		this.contractAudit = contractAudit;
	}
}
