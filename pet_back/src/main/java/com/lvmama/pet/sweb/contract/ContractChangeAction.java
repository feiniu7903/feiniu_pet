package com.lvmama.pet.sweb.contract;

import java.io.File;
import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupContractChange;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.utils.LogObject;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.CONTRACT_TYPE;
/**
 * 合同变更
 * @author yuzhibing
 *
 */
@Results({ @Result(name = "success", location = "/WEB-INF/pages/back/contract/contract_change.jsp") })
public class ContractChangeAction  extends BackBaseAction {
	private Long contractId;
	private SupContractService supContractService;
	private SupContract supContract;
	private SupContractChange supContractChange;
	private File file;
	private ComLogService comLogRemoteService;
	private Date contractEndDate;
	private Long contractChangeId;
	private Long fsId;
	
	@Action("/contract/change")
	public String execute(){
		this.supContract = this.supContractService.getContract(contractId);
		return this.SUCCESS;
	}
	
	/**
	 * 保存变更单
	 * */
	@Action("/contract/save")
	public void saveContratChange(){
		JSONResult result=new JSONResult();
		try{
			Long contractId = supContractChange.getContractId();
			//顺延合同需更改合同截止日期
			if(Constant.CONTRACT_TYPE.DELAY_CONTRACT.name().equals(supContractChange.getChangeType())){
				if(contractEndDate != null) {
					SupContract contract = supContractService.getContract(contractId);
					if(contractEndDate.before(contract.getEndDate())) {
						throw new Exception("顺延到期时间必须在合同到期时间之后！");
					}
					contract.setEndDate(contractEndDate);
					SupContract oldContract = supContractService
							.getContractById(contractId);
					supContractService.updateContract(contract);

					LogObject.updateSupContractLog(contract, oldContract,
							getSessionUserNameAndCheck(), comLogRemoteService);
				} else {
					throw new Exception("顺延到期时间不能为空！");
				}
			}
			
			supContractChange.setOperatorName(this.getSessionUserName());
			long contractChangeId = supContractService.insertContractChange(supContractChange);		
			
			SupContractChange contractChange = this.supContractService.getSupContractChangeById(contractChangeId);
			LogObject.addSupContractChangeLog(contractChange, getSessionUserNameAndCheck(), comLogRemoteService);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 上传变更单附件图
	 * */
	@Action("/contract/updateContractChangeFile")
	public void updateContractChangeFile() {
		JSONResult result=new JSONResult();
		try{
			SupContractChange contractChange = supContractService.getSupContractChangeById(contractChangeId);
			contractChange.setFsId(fsId);
			supContractService.updateContractChange(contractChange);
			LogObject.updateSupContractChangeLog(contractChange, getSessionUserNameAndCheck(), comLogRemoteService);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
 
	public CONTRACT_TYPE[] getContractType(){
		return Constant.CONTRACT_TYPE.values();
	}
	
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public SupContract getSupContract() {
		return supContract;
	}

	public void setSupContract(SupContract supContract) {
		this.supContract = supContract;
	}

	public void setSupContractService(SupContractService supContractService) {
		this.supContractService = supContractService;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setSupContractChange(SupContractChange supContractChange) {
		this.supContractChange = supContractChange;
	}

	public SupContractChange getSupContractChange() {
		return supContractChange;
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public void setContractChangeId(Long contractChangeId) {
		this.contractChangeId = contractChangeId;
	}

	public void setFsId(Long fsId) {
		this.fsId = fsId;
	}
}
