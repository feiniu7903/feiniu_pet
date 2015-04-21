package com.lvmama.comm.pet.po.sup;

import java.io.Serializable;

public class FinAccountingEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2159741894112631730L;

	private Long accountingEntityId;

    private Long gatewayId;

    private String name;

    private Long parentId;

    private String branch;

    public Long getAccountingEntityId() {
        return accountingEntityId;
    }

    public void setAccountingEntityId(Long accountingEntityId) {
        this.accountingEntityId = accountingEntityId;
    }

    public Long getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(Long gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch == null ? null : branch.trim();
    }
}