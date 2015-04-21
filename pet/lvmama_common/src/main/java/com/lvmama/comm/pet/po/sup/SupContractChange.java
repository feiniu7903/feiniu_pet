package com.lvmama.comm.pet.po.sup;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class SupContractChange implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3714001159957463456L;

	private Long contractChangeId;

    private Long contractId;

    private String changeType;

    private String changeMemo;
    
    private Long fsId;
    
    private Date createTime;
    
    private String operatorName;
    
    public Long getContractChangeId() {
        return contractChangeId;
    }

    public void setContractChangeId(Long contractChangeId) {
        this.contractChangeId = contractChangeId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType == null ? null : changeType.trim();
    }

    public String getChangeMemo() {
        return changeMemo;
    }

    public void setChangeMemo(String changeMemo) {
        this.changeMemo = changeMemo == null ? null : changeMemo.trim();
    }

	public Long getFsId() {
		return fsId;
	}

	public void setFsId(Long fsId) {
		this.fsId = fsId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
	public String getZhChangeType() {
		return Constant.CONTRACT_TYPE.getCnName(changeType);
	}
}