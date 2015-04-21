package com.lvmama.pet.sweb.contract;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.sup.FinAccountingEntity;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupContractFs;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.sup.FinAccountingEntityService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.LogObject;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
/**
 * 合同审核
 * @author yuzhibing
 *
 */
@Results({ 
	@Result(name = "contract_approve", location = "/WEB-INF/pages/back/contract/contract_approve.jsp")
	,@Result(name = "contract_approve_list", location = "/WEB-INF/pages/back/contract/contract_approve_list.jsp"),
	@Result(name = "upload_contract", location="/WEB-INF/pages/back/contract/upload_contract.jsp"),
	@Result(name = "reject_content", location="/WEB-INF/pages/back/contract/reject_content.jsp")
	})
public class ContractApproveAction extends BackBaseAction{
	private static final long serialVersionUID = 2075978675919483266L;
	private SupContractService supContractService;
	private SupplierService supplierService;
	private SettlementTargetService settlementTargetService;
	private SupContract contract;
	private SupContractFs contractFs;
	private List<SupContractFs> supContractFsList;
	private SupSupplier supplier;
	private SupSettlementTarget settlementTarget;
	private PlaceCityService placeCityService;
	private List<ComCity> cityList = Collections.emptyList();
	private FinAccountingEntityService finAccountingEntityService;
	private String province;
	private String supSupplierName;
	private List<SupSettlementTarget> settlementTargetList;
	private String supplierName;
	private String managerName;
	private PermUserService permUserService;
	private ComLogService comLogRemoteService;
	private String content;
	
	@Action("/contract/toContractList")
	public String toContractList(){
		return "contract_approve_list";
	}
	
	@Action("/contract/approve/list")
	public String approveList(){
		Map<String,Object> param = buildParams();
		param.put("contractAudit", Constant.CONTRACT_AUDIT.UNVERIFIED.name());//读取未审核的
		this.pagination = supContractService.getSupContractByParam(param, 10L, this.page);
		this.pagination.setUrl(WebUtils.getUrl(this.getRequest()));
		return "contract_approve_list";
	}
	
	/**
	 * 弹出审核页面，加载供应商、结算对象和合同信息
	 * */
	@Action("/contract/approve")
	public String execute(){
		this.contract = this.supContractService.getContract(contract.getContractId());
		this.supplier = this.supplierService.getSupplier(contract.getSupplierId());
		if(contract.isCreateSupplier()){
			this.settlementTarget = this.settlementTargetService.getSupSettlementTargetBySupplierId(supplier.getSupplierId());
		}else{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("supplierId", contract.getSupplierId());
			settlementTargetList = this.settlementTargetService.findSupSettlementTarget(map);
		}
		ComCity city = placeCityService.selectCityByPrimaryKey(supplier.getCityId());
		this.province = city.getProvinceId();
		cityList = placeCityService.getCityListByProvinceId(province);
		
		if(supplier.getParentId() != null && supplier.getParentId() > 0) {
			supSupplierName = supplierService.getSupplier(supplier.getParentId()).getSupplierName();
		}
		if(contract.getManagerId() != null) {
			managerName = permUserService.getPermUserByUserId(contract.getManagerId()).getRealName();
		}
		supContractFsList = this.supContractService.getSupContractFsByContractId(contract.getContractId());
		return "contract_approve";
	}
	@Action("/contract/uploadContract")
	public String uploadContract(){
		SupContract contractEntity = supContractService.getContract(contract.getContractId());
		Assert.notNull(contractEntity,"合同不存在");
		if(Constant.CONTRACT_AUDIT.PASS.name().equals(contractEntity.getContractAudit())){
			throw new IllegalArgumentException("当前的审核状态不可以上传文件");
		}
		return "upload_contract";
	}
	@Action("/contract/saveContractFile")
	public void saveContractFile(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(contractFs);
			Assert.notNull(contractFs.getContractId(),"合同不存在");
			Assert.notNull(contractFs.getFsId(),"合同不可以为空");
			
			SupContract contractEntity = supContractService.getContract(contractFs.getContractId());
			Assert.notNull(contractEntity,"合同不存在");
			if(Constant.CONTRACT_AUDIT.PASS.name().equals(contractEntity.getContractAudit())){
				throw new IllegalArgumentException("当前的审核状态不可以上传文件");
			}
			SupContractFs newcontractFs = new SupContractFs();
			newcontractFs.setContractId(contractFs.getContractId());
			newcontractFs.setFsName(contractFs.getFsName());
			newcontractFs.setFsId(contractFs.getFsId());
			contractFs = supContractService.insertSucContractFs(newcontractFs);
			
			//修改为带审核
			contractEntity.setContractAudit(Constant.CONTRACT_AUDIT.UNVERIFIED.name());
			supContractService.updateContract(contractEntity);
			//记日志
			LogObject.addSupContractFsLog(contractFs,
					getSessionUserNameAndCheck(), comLogRemoteService);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 审核通过
	 * */
	@Action("/contract/doApproveContract")
	public void doApproveContract(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(contract);
			ResultHandle handle=supContractService.updateContractAudlt(contract.getContractId(),
					Constant.CONTRACT_AUDIT.PASS, getSessionUserNameAndCheck());
			
			if(handle.isFail()){
				result.raise(handle.getMsg());
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 弹出驳回页面
	 * */
	@Action("/contract/toRejectPage")
	public String toRejectPage() {
		return "reject_content";
	}
	
	@Action("/contract/doRejectContract")
	public void doRejectContract() {
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(contract);
			ResultHandle handle=supContractService.updateContractAudlt(contract.getContractId(),
					Constant.CONTRACT_AUDIT.REJECTED, getSessionUserNameAndCheck());
			if(handle.isFail()){
				result.raise(handle.getMsg());
			} else {
				LogObject.addContractRejectedLog(contract.getContractId(),content, getSessionUserNameAndCheck(), comLogRemoteService);
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}

	private Map<String, Object> buildParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		if(contract == null){
			return params;
		}
		if (StringUtils.isNotEmpty(contract.getContractNo())) {
			params.put("contractNo", contract.getContractNo());
		}
		if (contract.getSupplierId() != null) {
			params.put("supplierId", contract.getSupplierId());
		}
		
		if (contract.getSupplierName() != null) {
			params.put("supplierName", contract.getSupplierName());
		}

		if (StringUtils.isNotEmpty(contract.getOperateName())) {
			params.put("operateName", contract.getOperateName());
		}
		return params;
	}

	public void setSupContractService(SupContractService supContractService) {
		this.supContractService = supContractService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}
	
	public Constant.SUPPLIER_TYPE[] getSupplierTypeList() {
		return Constant.SUPPLIER_TYPE.values();
	}
	
	public List<ComProvince> getProvinceList() {
		return placeCityService.getProvinceList();
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public List<ComCity> getCityList() {
		return cityList;
	}
	
	public Constant.SETTLEMENT_PERIOD[] getSettlementPeriodList() {
		return Constant.SETTLEMENT_PERIOD.values();
	}
	
	public Constant.SETTLEMENT_TARGET_TYPE[] getSettlementTargetTypeList() {
		return Constant.SETTLEMENT_TARGET_TYPE.values();
	}
	
	public Constant.SETTLEMENT_PAYMENT_TYPE[] getPaymentTypeList() {
		return Constant.SETTLEMENT_PAYMENT_TYPE.values();
	}
	
	public Constant.CONTRACT_TYPE[] getContractTypesList() {
		return Constant.CONTRACT_TYPE.values();
	}
	
	public List<FinAccountingEntity> getFinAccountingEntityList() {
		return finAccountingEntityService.selectEntityList();
	}

	public void setFinAccountingEntityService(
			FinAccountingEntityService finAccountingEntityService) {
		this.finAccountingEntityService = finAccountingEntityService;
	}

	public String getProvince() {
		return province;
	}

	public String getSupSupplierName() {
		return supSupplierName;
	}

	public SupContract getContract() {
		return contract;
	}

	public void setContract(SupContract contract) {
		this.contract = contract;
	}

	public SupSupplier getSupplier() {
		return supplier;
	}

	public void setSupplier(SupSupplier supplier) {
		this.supplier = supplier;
	}

	public SupSettlementTarget getSettlementTarget() {
		return settlementTarget;
	}

	public void setSettlementTarget(SupSettlementTarget settlementTarget) {
		this.settlementTarget = settlementTarget;
	}

	public List<SupSettlementTarget> getSettlementTargetList() {
		return settlementTargetList;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public SupContractFs getContractFs() {
		return contractFs;
	}

	public void setContractFs(SupContractFs contractFs) {
		this.contractFs = contractFs;
	}

	public List<SupContractFs> getSupContractFsList() {
		return supContractFsList;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
