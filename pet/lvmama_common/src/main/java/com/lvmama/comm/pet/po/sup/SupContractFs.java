package com.lvmama.comm.pet.po.sup;

import java.io.Serializable;
import java.util.Date;

public class SupContractFs implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3350530416095792942L;

	private Long supContractFsId;

    private Long contractId;

    private String fsName;

    private Long fsId;
    
    private Date createTime;

	public Long getSupContractFsId() {
		return supContractFsId;
	}

	public void setSupContractFsId(Long supContractFsId) {
		this.supContractFsId = supContractFsId;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getFsName() {
		return fsName;
	}

	public void setFsName(String fsName) {
		this.fsName = fsName;
	}

	public Long getFsId() {
		return fsId;
	}

	public void setFsId(Long fsId) {
		this.fsId = fsId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
     
}