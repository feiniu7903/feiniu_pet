package com.lvmama.comm.bee.po.meta;

import java.io.Serializable;
import java.util.Date;

public class MetaTravelCode implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8448452994026980404L;

	private Long metaTravelCodeId;

    private String supplierProductId;

    private Date specDate;

    private String travelCodeId;

    private String travelCode;
    
    private String supplierChannel;
    
    private String productBranch;

    public Long getMetaTravelCodeId() {
        return metaTravelCodeId;
    }

    public void setMetaTravelCodeId(Long metaTravelCodeId) {
        this.metaTravelCodeId = metaTravelCodeId;
    }

    public String getSupplierProductId() {
        return supplierProductId;
    }

    public void setSupplierProductId(String supplierProductId) {
        this.supplierProductId = supplierProductId == null ? null : supplierProductId.trim();
    }

    public Date getSpecDate() {
        return specDate;
    }

    public void setSpecDate(Date specDate) {
        this.specDate = specDate;
    }

    public String getTravelCodeId() {
        return travelCodeId;
    }

    public void setTravelCodeId(String travelCodeId) {
        this.travelCodeId = travelCodeId == null ? null : travelCodeId.trim();
    }

    public String getTravelCode() {
        return travelCode;
    }

    public void setTravelCode(String travelCode) {
        this.travelCode = travelCode == null ? null : travelCode.trim();
    }

	public String getSupplierChannel() {
		return supplierChannel;
	}

	public void setSupplierChannel(String supplierChannel) {
		this.supplierChannel = supplierChannel;
	}

	public String getProductBranch() {
		return productBranch;
	}

	public void setProductBranch(String productBranch) {
		this.productBranch = productBranch;
	}
}