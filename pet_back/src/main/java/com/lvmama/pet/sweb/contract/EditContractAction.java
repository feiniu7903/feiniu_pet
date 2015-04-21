package com.lvmama.pet.sweb.contract;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.sup.FinAccountingEntity;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupContractChange;
import com.lvmama.comm.pet.po.sup.SupContractFs;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.sup.FinAccountingEntityService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.CopyUtil;
import com.lvmama.comm.utils.LogObject;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

/**
 * 创建或修改合同(供应商、结算对象)
 * 
 * @author shihui
 * 
 */
@Results({
		@Result(name = "edit_contract", location = "/WEB-INF/pages/back/contract/edit_contract.jsp"),
		@Result(name = "contract_detail", location = "/WEB-INF/pages/back/contract/contract_detail.jsp") })
public class EditContractAction extends BackBaseAction {
	private static final long serialVersionUID = 4780667014323041782L;

	private SupContractService supContractService;

	private SupplierService supplierService;

	private PlaceCityService placeCityService;

	private List<ComCity> cityList = Collections.emptyList();

	private SupSupplier supplier;

	private SupSettlementTarget settlementTarget;

	private SupContract contract;

	private SettlementTargetService settlementTargetService;

	private Long contractId;

	private String supSupplierName;

	private List<SupSettlementTarget> settlementTargetList;

	private List<SupContractChange> contractChangeList;

	private FinAccountingEntityService finAccountingEntityService;

	private String path;

	private String fileName;

	private FSClient fsClient;

	private ComLogService comLogRemoteService;

	private ContactService contactService;

	private PermUserService permUserService;

	private String search;
	
	private String managerName;

	private List<SupContractFs> supContractFsList;
	
	@Action("/contract/edit_index")
	public String index() {
		return "edit_contract";
	}

	/**
	 * 新增供应商、结算对象和合同
	 * */
	@Action("/contract/doAddContract")
	public void doAddContract() {
		JSONResult result = new JSONResult();
		try {
			long supplierId = 0l, targetId = 0l;
			// 新增供应商
			if (supplier.getSupplierId() == null) {
				if (supplier != null) {
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("supplierNameEq", supplier.getSupplierName());			
					List<SupSupplier> list=supplierService.getSupSuppliers(param);
					if(!list.isEmpty()){			
						throw new IllegalArgumentException("供应商名称名称重复");
					}		
					supplierId = supplierService.addSupplier(supplier,
							getSessionUserNameAndCheck());
					SupSupplier supp = this.supplierService
							.getSupplier(supplierId);
					LogObject.addSupplierLog(supp,
							getSessionUserNameAndCheck(), comLogRemoteService);
				}
			} else {
				// 关联已有供应商
				supplierId = supplier.getSupplierId();
			}
			// 新增结算对象
			if (supplierId != 0l && supplier.getSupplierId() == null) {
				if (settlementTarget != null) {
					settlementTarget.setSupplierId(supplierId);
					if (!"PERORDER".equals(settlementTarget
							.getSettlementPeriod())) {
						settlementTarget.setAdvancedDays(0L);
					}
					targetId = settlementTargetService.addSettlementTarget(
							settlementTarget, getSessionUserNameAndCheck());

					//SupSettlementTarget targ = this.settlementTargetService
					//		.getSettlementTargetById(targetId);
					//LogObject.addSupSettlementTargetLog(targ,
					//		getSessionUserNameAndCheck(), comLogRemoteService);
				}
			}
			// 保存联系人
			if (supplierId != 0l && supplier.getSupplierId() == null
					&& targetId != 0l) {
				HttpSession seesion = getRequest().getSession();
				Object obj = seesion.getAttribute("CONTACT_KEY");
				List<ComContact> contactList = null;
				if (obj != null) {
					contactList = (List<ComContact>) obj;
					for (ComContact contact : contactList) {
						contact.setContactId(null);
						contact.setSupplierId(supplierId);
						contactService.addContact(contact,
								getSessionUserNameAndCheck());

					}

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("supplierId", supplierId);
					contactList = contactService
							.getContactByPersonTimeCompany(map);

					contactService.saveContactRelation(contactList, targetId,
							"SUP_SETTLEMENT_TARGET");

					seesion.removeAttribute("CONTACT_KEY");
				}
			}

			if (supplierId != 0l) {
				// 新增合同
				if (contract != null) {
					contract.setSupplierId(supplierId);
					if (supplier.getSupplierId() == null) {
						contract.setBindSupplierType(Constant.CONTACT_SUPPLIER_TYPE.CREATE
								.name());
					} else {
						contract.setBindSupplierType(Constant.CONTACT_SUPPLIER_TYPE.BIND
								.name());
					}
					contract.setContractStatus(Constant.CONTRACT_STATUS.NORMAL
							.name());
					contract.setOperateName(getSessionUserNameAndCheck());
					long contractId = supContractService.addContract(contract);

					SupContract con = this.supContractService
							.getContract(contractId);
					LogObject.addSupContractLog(con,
							getSessionUserNameAndCheck(), comLogRemoteService);
				}
			}
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}

	/**
	 * 展示合同基本信息和变更单列表及供应商信息
	 * */
	@Action("/contract/contractDetail")
	public String contractDetail() {
		if (contractId != null) {
			contract = supContractService.getContractById(contractId);
			if(contract.getManagerId() != null) {
				managerName = permUserService.getPermUserByUserId(contract.getManagerId()).getRealName();
			}
			// 变更单列表
			contractChangeList = supContractService
					.selectChangesByContractId(contractId);

			supplier = supplierService.getSupplier(contract.getSupplierId());
			if (supplier.getParentId() != null) {
				supSupplierName = supplierService.getSupplier(
						supplier.getParentId()).getSupplierName();
			}
			HashMap<Object, Object> params = new HashMap<Object, Object>();
			params.put("supplierId", supplier.getSupplierId());
			settlementTargetList = settlementTargetService
					.findSupSettlementTarget(params);
			
			supContractFsList = this.supContractService.getSupContractFsByContractId(contractId);
		}
		return "contract_detail";
	}

	/**
	 * 修改合同基本信息信息
	 * */
	@Action("/contract/doEditContract")
	public void doEditContract() {
		JSONResult result = new JSONResult();
		try {
			if (contract != null) {
				// 审核通过不能修改
				if (contract.getContractAudit() != null
						&& contract.getContractAudit().equals(
								Constant.CONTRACT_AUDIT.PASS.name())) {
					result.raise("审核通过后不能修改！");
				} else {
					SupContract con = supContractService
							.getContractById(contract.getContractId());
					con = CopyUtil.copy(con, contract, getRequest()
							.getParameterNames(), "contract.");

					SupContract oldContract = supContractService
							.getContractById(con.getContractId());

					supContractService.updateContract(con);
					LogObject.updateSupContractLog(con, oldContract,
							getSessionUserNameAndCheck(), comLogRemoteService);
				}
			}
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}

	@Action("/contract/downLoad")
	public void downLoad() {
		try {
			if (StringUtils.isNotEmpty(path)) {
				OutputStream os = null;
				try {
					ComFile resultFile = fsClient.downloadFile(Long
							.valueOf(path));
					getResponse().setHeader("Content-Disposition",
							"attachment; filename=" + new String(resultFile.getFileName().getBytes(Charset.defaultCharset()),"ISO-8859-1"));
					getResponse().setContentType(getContentType(resultFile.getFileName()));
					os = getResponse().getOutputStream();
					
					IOUtils.copy(resultFile.getInputStream(), os);
					os.flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
					IOUtils.closeQuietly(os);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取文件类型
	 * @param filename
	 * @return
	 */
	private String getContentType(String filename){
		String contentType="";
		if(filename.contains(".")){
			String suffix=filename.substring(filename.lastIndexOf('.')+1).toLowerCase();
			if(suffix.equals("jpg")){
				contentType="image/jpeg";
			}else if(suffix.equals("zip")){
				contentType="application/zip";
			}else if(suffix.equals("rar")){
				contentType="application/x-rar-compressed";
			}
		}	
		return contentType;
	}
	
	/**
	 * 再次提交审核
	 * */
	@Action("/contract/resubmitVerify")
	public void resubmitVerify(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(contractId);
			ResultHandle handle=supContractService.updateContractAudlt(contractId,
					Constant.CONTRACT_AUDIT.UNVERIFIED, getSessionUserNameAndCheck());
			if(handle.isFail()){
				result.raise(handle.getMsg());
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	public List<ComProvince> getProvinceList() {
		return placeCityService.getProvinceList();
	}

	public Constant.SUPPLIER_TYPE[] getSupplierTypeList() {
		return Constant.SUPPLIER_TYPE.values();
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

	public List<FinAccountingEntity> getFinAccountingEntityList() {
		return finAccountingEntityService.selectEntityList();
	}

	public Constant.CONTRACT_TYPE[] getContractTypesList() {
		return Constant.CONTRACT_TYPE.values();
	}

	public void setSupContractService(SupContractService supContractService) {
		this.supContractService = supContractService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public List<ComCity> getCityList() {
		return cityList;
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

	public SupContract getContract() {
		return contract;
	}

	public void setContract(SupContract contract) {
		this.contract = contract;
	}

	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getSupSupplierName() {
		return supSupplierName;
	}

	public List<SupSettlementTarget> getSettlementTargetList() {
		return settlementTargetList;
	}

	public List<SupContractChange> getContractChangeList() {
		return contractChangeList;
	}

	public void setFinAccountingEntityService(
			FinAccountingEntityService finAccountingEntityService) {
		this.finAccountingEntityService = finAccountingEntityService;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	public Constant.SETTLEMENT_COMPANY[] getSettlementCompanyList() {
		return Constant.SETTLEMENT_COMPANY.values();
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public List<SupContractFs> getSupContractFsList() {
		return supContractFsList;
	}
}
